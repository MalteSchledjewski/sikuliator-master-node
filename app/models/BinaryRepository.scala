package models

import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.{Await, Future}

/**
  * Created by Malte on 05.02.2016.
  */
object BinaryRepository extends HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import driver.api._

  def getResultImageBinary(resultImageId: Long): Future[Option[Array[Byte]]] = {
    val projects: Future[Seq[Tables.StorageResultimageRow]] = dbConfig.db.run(Tables.StorageResultimage.filter(_.resultimageid=== resultImageId).result)
    projects.flatMap((rows: Seq[Tables.StorageResultimageRow]) =>
      Future {
        rows.headOption.map((row: Tables.StorageResultimageRow) => {
          row.binary
        })
      }
    )
  }

  def getReferenceImageBinary(referenceImageVersionId: Long): Future[Option[Array[Byte]]] = {
    val projects: Future[Seq[Tables.StorageReferenceimageRow]] = dbConfig.db.run(Tables.StorageReferenceimage.filter(_.referenceimageverisonid=== referenceImageVersionId).result)
    projects.flatMap((rows: Seq[Tables.StorageReferenceimageRow]) =>
      Future {
        rows.headOption.map((row: Tables.StorageReferenceimageRow) => {
          row.binary
        })
      }
    )
  }


  def getTestExecutableBinary(testExecutableId: Long): Future[Option[Array[Byte]]] = {
    val projects: Future[Seq[Tables.StorageTestexecutableRow]] = dbConfig.db.run(Tables.StorageTestexecutable.filter(_.testexecutableid=== testExecutableId).result)
    projects.flatMap((rows: Seq[Tables.StorageTestexecutableRow]) =>
      Future {
        rows.headOption.map((row: Tables.StorageTestexecutableRow) => {
          row.binary
        })
      }
    )
  }


}
