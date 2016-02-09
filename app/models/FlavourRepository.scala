package models

import models.ProjectsRepository._
import org.postgresql.util.PSQLException
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json._
import slick.driver.JdbcProfile

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

import scala.util.Try
import scala.util.Success
import scala.util.Failure


import slick.dbio.DBIO

/**
  * Created by Malte on 10.01.2016.
  */
object FlavourRepository extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._


  def getFlavourStubs(projectId : Long): Future[Option[Seq[FlavourStub]]] =
  {
    val flavours: Future[Try[Seq[Tables.FlavoursRow]]] = dbConfig.db.run(Tables.Flavours.filter(_.project === projectId).result.asTry)
    flavours.map[Option[Seq[FlavourStub]]](
      {
        case Success(rows) =>
          Some(rows.toList.map((row: Tables.FlavoursRow) => FlavourStub(row.flavourid, row.name)))
        case Failure(e) =>
          None
      }
    )
  }


  def getFlavourName(flavourID : Long): Future[Option[String]] =
  {
    val flavours: Future[Try[Seq[Tables.FlavoursRow]]] = dbConfig.db.run(Tables.Flavours.filter(_.flavourid === flavourID).result.asTry)
    flavours.map[Option[String]](
      {
        case Success(rows) =>
          rows.headOption.map((row: Tables.FlavoursRow) => row.name)
        case Failure(e) =>
          None
      }
    )
  }


  def createFlavour(projectid: Long, flavourName: String): Future[Option[FlavourStub]] = {
    val action = (Tables.Flavours returning Tables.Flavours.map(_.flavourid)) += Tables.FlavoursRow(0, flavourName, projectid)
    dbConfig.db.run(action.asTry).map[Option[FlavourStub]](
      {
        case Success(flavourId) =>
          Some[FlavourStub](FlavourStub(flavourId, flavourName))
        case Failure(e) =>
          None
      }
    )
  }


  def getCreateFlavourTestTree(flavourId :Long) : Future[Try[JsValue]] = {

    def flatten[T](xs: Seq[Try[T]]): Try[Seq[T]] = {
      val (ss: Seq[Success[T]]@unchecked, fs: Seq[Failure[T]]@unchecked) =
        xs.partition(_.isSuccess)

      if (fs.isEmpty) Success(ss map (_.get))
      else Failure[Seq[T]](fs(0).exception) // Only keep the first failure
    }

    def buildTree(
                   sortedRowsGroupedByParent :  Map[Option[Long], Seq[Tables.Flavourtesttreeinnernodes#TableElementType]],
                   sortedTreeNodeRowsByNode :Map[Long, Seq[(Tables.FlavourtesttreeleavesRow, Tables.TestsRow)]],
                   categoryName : String,
                   rootNodeId : Option[Long],
                   isRoot : Boolean
                 )
                  : Try[JsValue] =
    {
      // try to build subtree
      val maybeSubCategories = sortedRowsGroupedByParent.find(_._1 == rootNodeId).map((entry) => entry._2)
      val maybeSubIdsAndNames =  maybeSubCategories.map((subCategories) => subCategories.map((subCategory)=>( subCategory.nodeid,subCategory.categoryname)))
      val maybeSubcategories = maybeSubIdsAndNames.map(
        (subIdsAndNames) => flatten(subIdsAndNames.map(
        (subIdAndName) =>
          {
            buildTree(sortedRowsGroupedByParent,sortedTreeNodeRowsByNode,subIdAndName._2,Some(subIdAndName._1),false)
          }
        ))
      ).getOrElse(Success(Seq[JsValue]()))

      maybeSubcategories.map[JsValue](
        (subCategoryEntries) =>
          {
            val subCategoriesJson = JsonHelper.concatAsJsonArray(subCategoryEntries)
            if (isRoot)
            {
              // just the array of categories
              subCategoriesJson
            }
            else
              {
                // get all tests for this category
                val testNodeGroup = sortedTreeNodeRowsByNode.find(_._1 == rootNodeId.get)
                val maybeTestStubList = testNodeGroup.map((entry) => entry._2.map((testEntry) => TestStub(testEntry._2.testid, testEntry._2.name)))

                val testStubArray = maybeTestStubList.map(
                  (testStubList) =>
                    JsonHelper.concatAsJsonArray(
                      testStubList.map( (stub) => TestStub.testStubWrites.writes(stub))
                    )
                ).getOrElse(JsArray())

                // create JsValue
                Json.obj(
                  "name" -> categoryName,
                  "subcategories" -> subCategoriesJson,
                  "tests" -> testStubArray
                )
              }
          }
      )

    }


    val eventualJoinRows = dbConfig.db.run(Tables.Flavourtesttreeinnernodes.filter(_.flavour === flavourId).result.asTry)
    eventualJoinRows.flatMap[Try[JsValue]](
      {
        case Failure(e) =>
          Future{Failure(e)}
        case Success(rows) =>
          {
            val sortedRowsGroupedByParent = rows.groupBy(_.parent).transform((k,v) => v.sortBy(_.index))
            val maybeRoot = sortedRowsGroupedByParent.find(_._1.isEmpty)
            maybeRoot match {
              case None => Future{Failure(new IllegalStateException)}
              case Some(rootEntry) =>
                {
                  val maybeTreeNodeRows = dbConfig.db.run(
                      Tables.Flavourtesttreeinnernodes.
                      join(Tables.Flavourtesttreeleaves).on(_.nodeid === _.nodeid).
                      join(Tables.Tests).on(_._2.testid === _.testid).
                      filter(_._1._1.flavour === flavourId).
                      map((row) => (row._1._2, row._2)).
                      result.asTry
                  )

                  maybeTreeNodeRows.map[Try[JsValue]](
                    {
                    case Failure(f) =>
                      Failure(f)
                    case Success(treeNodeRows) =>
                        {
                          val sortedTreeNodeRowsByNode = treeNodeRows.groupBy(_._1.nodeid).transform((k,v) => v.sortBy(_._1.index))
                          buildTree(sortedRowsGroupedByParent,sortedTreeNodeRowsByNode,"",rootEntry._1,true)
                        }
                    }
                  )
                }
            }
          }
      }
    )
  }






}
