package models

import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * Created by Malte on 05.02.2016.
  */
object SequenceVersionRepository  extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._


  def getSequenceVersionStubs(projectId : Long, sequenceId : Long): Future[Option[Seq[SequenceVersionStub]]] =
  {
    val sequenceVersionStubs: Future[Try[Seq[Tables.SequenceversionsRow]]] = dbConfig.db.run(Tables.Sequenceversions.filter(_.sequence === sequenceId).result.asTry)
    sequenceVersionStubs.map[Option[Seq[SequenceVersionStub]]](
      {

        case Success(rows) =>
          Some(rows.toList.map((row: Tables.SequenceversionsRow) => SequenceVersionStub(row.sequenceversionid, row.parent)))
        case Failure(e) =>
          None
      }
    )
  }


  def getSequenceVersion(sequenceVersionId : Long): Future[Option[SequenceVersion]] =
  {
    val sequenceVersionResult: Future[Try[Seq[Tables.SequenceversionsRow]]] = dbConfig.db.run(Tables.Sequenceversions.filter(_.sequenceversionid === sequenceVersionId).result.asTry)
    sequenceVersionResult.map[Option[SequenceVersion]](
      {
        case Success(rows) =>
          rows.headOption.map(
            (row: Tables.SequenceversionsRow) => SequenceVersion(row.sequenceversionid,row.sequence,row.specification,row.timecreated,row.parent)
          )
        case Failure(e) =>
          None
      }
    )
  }



  def getSequenceVersionsForFlavour(flavourId : Long): Future[Option[Seq[(Long,SequenceVersion)]]] =
  {
    val eventualRows = dbConfig.db.run(Tables.Flavourtosequence.join(Tables.Sequences).on(_.sequenceid === _.sequenceid).join(Tables.Sequenceversions).on(_._1.sequenceversionid === _.sequenceversionid).filter(_._1._1.flavourid === flavourId).result.asTry)
    eventualRows.map[Option[Seq[(Long,SequenceVersion)]]](
      {
        case Success(rows) =>
          Some(rows.toList.map((row) => (row._1._2.sequenceid,SequenceVersion(row._2.sequenceversionid,row._1._2.sequenceid, row._2.specification, row._2.timecreated,row._2.parent))))
        case Failure(e) =>
          None
      }
    )
  }


  def createSequenceVersion(sequenceId :Long, parent : Option[Long], spec : String) : Future[Option[Long]] =
  {
    val insertAction = (Tables.Sequenceversions.map(
      sequenceVersionTable => (sequenceVersionTable.sequence, sequenceVersionTable.parent, sequenceVersionTable.specification)
    )
      returning Tables.Sequenceversions.map(_.sequenceversionid)) += (sequenceId,parent,spec)
    dbConfig.db.run(insertAction.asTry).map(
      {
        case Success(result) =>
          Some(result)
        case Failure(e) =>
          None
      }
    )
  }




}
