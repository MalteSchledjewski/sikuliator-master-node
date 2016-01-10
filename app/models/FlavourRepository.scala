package models

import models.ProjectsRepository._
import org.postgresql.util.PSQLException
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
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


  def getFlavourStubs(projectId : Long): Future[Seq[FlavourStub]] =
  {
    val flavours: Future[Seq[Tables.FlavoursRow]] = dbConfig.db.run(Tables.Flavours.filter(_.project === projectId).result)
    flavours.flatMap( (rows : Seq[Tables.FlavoursRow]) =>
      Future {
        rows.toList.map((row: Tables.FlavoursRow) => FlavourStub(row.flavourid, row.name))
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
}
