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
object SequenceRepository  extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  import driver.api._


  def getSequenceStubs(projectId : Long): Future[Option[Seq[SequenceStub]]] =
  {
    val sequences: Future[Try[Seq[Tables.SequencesRow]]] = dbConfig.db.run(Tables.Sequences.filter(_.project === projectId).result.asTry)
    sequences.map[Option[Seq[SequenceStub]]](
      {

        case Success(rows) =>
          Some(rows.toList.map((row: Tables.SequencesRow) => SequenceStub(row.sequenceid, row.name)))
        case Failure(e) =>
          None
      }
    )
  }


  def createSequence(projectId : Long,name : String):Future[Option[Long]] =
  {
    val insertAction = (Tables.Sequences.map(
      sequenceTable => (sequenceTable.project,sequenceTable.name)
    ) returning Tables.Sequences.map(_.sequenceid)) += (projectId,name)
    val sequenceId = dbConfig.db.run(insertAction.asTry)
    sequenceId.map{
      case Success(id) =>
        Some(id)
      case Failure(e) =>
        None
    }
  }

  def getSequence(projectId : Long, sequenceId : Long): Future[Option[ReusableSequence]] =
  {
    val sequenceSqlResult: Future[Try[Seq[Tables.SequencesRow]]] = dbConfig.db.run(Tables.Sequences.filter(sequence => sequence.project === projectId && sequence.sequenceid === sequenceId).result.asTry)
    val sequence : Future[Option[SequenceStub]] = sequenceSqlResult.map[Option[SequenceStub]](
      {

        case Success(rows) =>
          rows.headOption.flatMap[SequenceStub]( (row : Tables.SequencesRow ) => Some(SequenceStub(row.sequenceid, row.name)))
        case Failure(e) =>
          None
      }
    )

    val sequenceVersion : Future[Option[Seq[SequenceVersionStub]]] = SequenceVersionRepository.getSequenceVersionStubs(projectId,sequenceId)

    sequence.flatMap[Option[ReusableSequence]](
      {
        case None => Future{None}
        case Some(sequenceStub : SequenceStub) =>
          sequenceVersion.map[Option[ReusableSequence]](
            {
              case None => None
              case Some(versionStubs :Seq[SequenceVersionStub] ) =>
                Some(ReusableSequence(sequenceId,sequenceStub.name,versionStubs))
            }
          )
      }
    )
  }

}
