package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Executions.schema, Executiontoreferenceimageversion.schema, ExecutionToResultimage.schema, Executiontosequence.schema, Flavours.schema, Flavourtesttreeinnernodes.schema, Flavourtesttreeleaves.schema, Flavourtoreferenceimageversion.schema, Flavourtosequence.schema, Flavourtotestversion.schema, Projects.schema, Referenceimages.schema, Referenceimageversions.schema, Resultimages.schema, Sequences.schema, Sequenceversions.schema, StorageReferenceimage.schema, StorageResultimage.schema, StorageTestexecutable.schema, Testexecutables.schema, Testexecutions.schema, Testexecutiontreeinnernodes.schema, Testexecutiontreeleaves.schema, Tests.schema, Testversions.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Executions
   *  @param executionid Database column executionid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param testversion Database column testversion SqlType(int8)
   *  @param status Database column status SqlType(text)
   *  @param result Database column result SqlType(text), Default(None)
   *  @param starttime Database column starttime SqlType(timestamptz), Default(None)
   *  @param durationseconds Database column durationseconds SqlType(int4), Default(None) */
  case class ExecutionsRow(executionid: Long, testversion: Long, status: String, result: Option[String] = None, starttime: Option[java.sql.Timestamp] = None, durationseconds: Option[Int] = None)
  /** GetResult implicit for fetching ExecutionsRow objects using plain SQL queries */
  implicit def GetResultExecutionsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]], e4: GR[Option[Int]]): GR[ExecutionsRow] = GR{
    prs => import prs._
    ExecutionsRow.tupled((<<[Long], <<[Long], <<[String], <<?[String], <<?[java.sql.Timestamp], <<?[Int]))
  }
  /** Table description of table executions. Objects of this class serve as prototypes for rows in queries. */
  class Executions(_tableTag: Tag) extends Table[ExecutionsRow](_tableTag, "executions") {
    def * = (executionid, testversion, status, result, starttime, durationseconds) <> (ExecutionsRow.tupled, ExecutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(executionid), Rep.Some(testversion), Rep.Some(status), result, starttime, durationseconds).shaped.<>({r=>import r._; _1.map(_=> ExecutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column executionid SqlType(bigserial), AutoInc, PrimaryKey */
    val executionid: Rep[Long] = column[Long]("executionid", O.AutoInc, O.PrimaryKey)
    /** Database column testversion SqlType(int8) */
    val testversion: Rep[Long] = column[Long]("testversion")
    /** Database column status SqlType(text) */
    val status: Rep[String] = column[String]("status")
    /** Database column result SqlType(text), Default(None) */
    val result: Rep[Option[String]] = column[Option[String]]("result", O.Default(None))
    /** Database column starttime SqlType(timestamptz), Default(None) */
    val starttime: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("starttime", O.Default(None))
    /** Database column durationseconds SqlType(int4), Default(None) */
    val durationseconds: Rep[Option[Int]] = column[Option[Int]]("durationseconds", O.Default(None))
  }
  /** Collection-like TableQuery object for table Executions */
  lazy val Executions = new TableQuery(tag => new Executions(tag))

  /** Entity class storing rows of table Executiontoreferenceimageversion
   *  @param executionid Database column executionid SqlType(int8)
   *  @param referenceimageid Database column referenceimageid SqlType(int8)
   *  @param referenceimageversionid Database column referenceimageversionid SqlType(int8) */
  case class ExecutiontoreferenceimageversionRow(executionid: Long, referenceimageid: Long, referenceimageversionid: Long)
  /** GetResult implicit for fetching ExecutiontoreferenceimageversionRow objects using plain SQL queries */
  implicit def GetResultExecutiontoreferenceimageversionRow(implicit e0: GR[Long]): GR[ExecutiontoreferenceimageversionRow] = GR{
    prs => import prs._
    ExecutiontoreferenceimageversionRow.tupled((<<[Long], <<[Long], <<[Long]))
  }
  /** Table description of table executiontoreferenceimageversion. Objects of this class serve as prototypes for rows in queries. */
  class Executiontoreferenceimageversion(_tableTag: Tag) extends Table[ExecutiontoreferenceimageversionRow](_tableTag, "executiontoreferenceimageversion") {
    def * = (executionid, referenceimageid, referenceimageversionid) <> (ExecutiontoreferenceimageversionRow.tupled, ExecutiontoreferenceimageversionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(executionid), Rep.Some(referenceimageid), Rep.Some(referenceimageversionid)).shaped.<>({r=>import r._; _1.map(_=> ExecutiontoreferenceimageversionRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column executionid SqlType(int8) */
    val executionid: Rep[Long] = column[Long]("executionid")
    /** Database column referenceimageid SqlType(int8) */
    val referenceimageid: Rep[Long] = column[Long]("referenceimageid")
    /** Database column referenceimageversionid SqlType(int8) */
    val referenceimageversionid: Rep[Long] = column[Long]("referenceimageversionid")

    /** Primary key of Executiontoreferenceimageversion (database name executiontoreferenceimageversion_flavourid_sequenceid_pk) */
    val pk = primaryKey("executiontoreferenceimageversion_flavourid_sequenceid_pk", (executionid, referenceimageid))

    /** Foreign key referencing Referenceimages (database name executiontoreferenceimageversion_referenceimages_referenceimage) */
    lazy val referenceimagesFk = foreignKey("executiontoreferenceimageversion_referenceimages_referenceimage", referenceimageid, Referenceimages)(r => r.referenceimageid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Referenceimageversions (database name executiontoreferenceimageversion_referenceimageversions_referen) */
    lazy val referenceimageversionsFk = foreignKey("executiontoreferenceimageversion_referenceimageversions_referen", referenceimageversionid, Referenceimageversions)(r => r.referenceimageversionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Testexecutions (database name executiontoreferenceimageversion_executions_flavourid_fk) */
    lazy val testexecutionsFk = foreignKey("executiontoreferenceimageversion_executions_flavourid_fk", executionid, Testexecutions)(r => r.testexecutionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Executiontoreferenceimageversion */
  lazy val Executiontoreferenceimageversion = new TableQuery(tag => new Executiontoreferenceimageversion(tag))

  /** Entity class storing rows of table ExecutionToResultimage
   *  @param executionid Database column executionid SqlType(int8)
   *  @param resultimageid Database column resultimageid SqlType(int8) */
  case class ExecutionToResultimageRow(executionid: Long, resultimageid: Long)
  /** GetResult implicit for fetching ExecutionToResultimageRow objects using plain SQL queries */
  implicit def GetResultExecutionToResultimageRow(implicit e0: GR[Long]): GR[ExecutionToResultimageRow] = GR{
    prs => import prs._
    ExecutionToResultimageRow.tupled((<<[Long], <<[Long]))
  }
  /** Table description of table execution_to_resultimage. Objects of this class serve as prototypes for rows in queries. */
  class ExecutionToResultimage(_tableTag: Tag) extends Table[ExecutionToResultimageRow](_tableTag, "execution_to_resultimage") {
    def * = (executionid, resultimageid) <> (ExecutionToResultimageRow.tupled, ExecutionToResultimageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(executionid), Rep.Some(resultimageid)).shaped.<>({r=>import r._; _1.map(_=> ExecutionToResultimageRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column executionid SqlType(int8) */
    val executionid: Rep[Long] = column[Long]("executionid")
    /** Database column resultimageid SqlType(int8) */
    val resultimageid: Rep[Long] = column[Long]("resultimageid")

    /** Foreign key referencing Executions (database name execution_to_resultimage_executions_executionid_fk) */
    lazy val executionsFk = foreignKey("execution_to_resultimage_executions_executionid_fk", executionid, Executions)(r => r.executionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Resultimages (database name execution_to_resultimage_resultimages_resultimageid_fk) */
    lazy val resultimagesFk = foreignKey("execution_to_resultimage_resultimages_resultimageid_fk", resultimageid, Resultimages)(r => r.resultimageid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (resultimageid) (database name execution_to_resultImage_resultImageID_uindex) */
    val index1 = index("execution_to_resultImage_resultImageID_uindex", resultimageid, unique=true)
  }
  /** Collection-like TableQuery object for table ExecutionToResultimage */
  lazy val ExecutionToResultimage = new TableQuery(tag => new ExecutionToResultimage(tag))

  /** Entity class storing rows of table Executiontosequence
   *  @param executionid Database column executionid SqlType(int8)
   *  @param sequenceid Database column sequenceid SqlType(int8)
   *  @param sequenceversionid Database column sequenceversionid SqlType(int8) */
  case class ExecutiontosequenceRow(executionid: Long, sequenceid: Long, sequenceversionid: Long)
  /** GetResult implicit for fetching ExecutiontosequenceRow objects using plain SQL queries */
  implicit def GetResultExecutiontosequenceRow(implicit e0: GR[Long]): GR[ExecutiontosequenceRow] = GR{
    prs => import prs._
    ExecutiontosequenceRow.tupled((<<[Long], <<[Long], <<[Long]))
  }
  /** Table description of table executiontosequence. Objects of this class serve as prototypes for rows in queries. */
  class Executiontosequence(_tableTag: Tag) extends Table[ExecutiontosequenceRow](_tableTag, "executiontosequence") {
    def * = (executionid, sequenceid, sequenceversionid) <> (ExecutiontosequenceRow.tupled, ExecutiontosequenceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(executionid), Rep.Some(sequenceid), Rep.Some(sequenceversionid)).shaped.<>({r=>import r._; _1.map(_=> ExecutiontosequenceRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column executionid SqlType(int8) */
    val executionid: Rep[Long] = column[Long]("executionid")
    /** Database column sequenceid SqlType(int8) */
    val sequenceid: Rep[Long] = column[Long]("sequenceid")
    /** Database column sequenceversionid SqlType(int8) */
    val sequenceversionid: Rep[Long] = column[Long]("sequenceversionid")

    /** Primary key of Executiontosequence (database name executiontosequence_executionid_sequenceid_pk) */
    val pk = primaryKey("executiontosequence_executionid_sequenceid_pk", (executionid, sequenceid))

    /** Foreign key referencing Sequences (database name executiontosequence_sequences_sequenceid_fk) */
    lazy val sequencesFk = foreignKey("executiontosequence_sequences_sequenceid_fk", sequenceid, Sequences)(r => r.sequenceid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Sequenceversions (database name executiontosequence_sequenceversions_sequenceversionid_fk) */
    lazy val sequenceversionsFk = foreignKey("executiontosequence_sequenceversions_sequenceversionid_fk", sequenceversionid, Sequenceversions)(r => r.sequenceversionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Testexecutions (database name executiontosequence_executions_flavourid_fk) */
    lazy val testexecutionsFk = foreignKey("executiontosequence_executions_flavourid_fk", executionid, Testexecutions)(r => r.testexecutionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Executiontosequence */
  lazy val Executiontosequence = new TableQuery(tag => new Executiontosequence(tag))

  /** Entity class storing rows of table Flavours
   *  @param flavourid Database column flavourid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param project Database column project SqlType(int8) */
  case class FlavoursRow(flavourid: Long, name: String, project: Long)
  /** GetResult implicit for fetching FlavoursRow objects using plain SQL queries */
  implicit def GetResultFlavoursRow(implicit e0: GR[Long], e1: GR[String]): GR[FlavoursRow] = GR{
    prs => import prs._
    FlavoursRow.tupled((<<[Long], <<[String], <<[Long]))
  }
  /** Table description of table flavours. Objects of this class serve as prototypes for rows in queries. */
  class Flavours(_tableTag: Tag) extends Table[FlavoursRow](_tableTag, "flavours") {
    def * = (flavourid, name, project) <> (FlavoursRow.tupled, FlavoursRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(flavourid), Rep.Some(name), Rep.Some(project)).shaped.<>({r=>import r._; _1.map(_=> FlavoursRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column flavourid SqlType(bigserial), AutoInc, PrimaryKey */
    val flavourid: Rep[Long] = column[Long]("flavourid", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column project SqlType(int8) */
    val project: Rep[Long] = column[Long]("project")

    /** Foreign key referencing Projects (database name flavours___fkproject) */
    lazy val projectsFk = foreignKey("flavours___fkproject", project, Projects)(r => r.projectid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Index over (name) (database name Flavours__name) */
    val index1 = index("Flavours__name", name)
  }
  /** Collection-like TableQuery object for table Flavours */
  lazy val Flavours = new TableQuery(tag => new Flavours(tag))

  /** Entity class storing rows of table Flavourtesttreeinnernodes
   *  @param nodeid Database column nodeid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param flavour Database column flavour SqlType(int8)
   *  @param parent Database column parent SqlType(int8), Default(None)
   *  @param index Database column index SqlType(int8)
   *  @param categoryname Database column categoryname SqlType(text) */
  case class FlavourtesttreeinnernodesRow(nodeid: Long, flavour: Long, parent: Option[Long] = None, index: Long, categoryname: String)
  /** GetResult implicit for fetching FlavourtesttreeinnernodesRow objects using plain SQL queries */
  implicit def GetResultFlavourtesttreeinnernodesRow(implicit e0: GR[Long], e1: GR[Option[Long]], e2: GR[String]): GR[FlavourtesttreeinnernodesRow] = GR{
    prs => import prs._
    FlavourtesttreeinnernodesRow.tupled((<<[Long], <<[Long], <<?[Long], <<[Long], <<[String]))
  }
  /** Table description of table flavourtesttreeinnernodes. Objects of this class serve as prototypes for rows in queries. */
  class Flavourtesttreeinnernodes(_tableTag: Tag) extends Table[FlavourtesttreeinnernodesRow](_tableTag, "flavourtesttreeinnernodes") {
    def * = (nodeid, flavour, parent, index, categoryname) <> (FlavourtesttreeinnernodesRow.tupled, FlavourtesttreeinnernodesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(nodeid), Rep.Some(flavour), parent, Rep.Some(index), Rep.Some(categoryname)).shaped.<>({r=>import r._; _1.map(_=> FlavourtesttreeinnernodesRow.tupled((_1.get, _2.get, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column nodeid SqlType(bigserial), AutoInc, PrimaryKey */
    val nodeid: Rep[Long] = column[Long]("nodeid", O.AutoInc, O.PrimaryKey)
    /** Database column flavour SqlType(int8) */
    val flavour: Rep[Long] = column[Long]("flavour")
    /** Database column parent SqlType(int8), Default(None) */
    val parent: Rep[Option[Long]] = column[Option[Long]]("parent", O.Default(None))
    /** Database column index SqlType(int8) */
    val index: Rep[Long] = column[Long]("index")
    /** Database column categoryname SqlType(text) */
    val categoryname: Rep[String] = column[String]("categoryname")

    /** Foreign key referencing Flavours (database name flavourtesttreeinnernodes_flavours_flavourid_fk) */
    lazy val flavoursFk = foreignKey("flavourtesttreeinnernodes_flavours_flavourid_fk", flavour, Flavours)(r => r.flavourid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (flavour,index,parent) (database name flavourtesttreeinnernodes_flavour_index_parent_pk) */
    val index1 = index("flavourtesttreeinnernodes_flavour_index_parent_pk", (flavour, index, parent), unique=true)
  }
  /** Collection-like TableQuery object for table Flavourtesttreeinnernodes */
  lazy val Flavourtesttreeinnernodes = new TableQuery(tag => new Flavourtesttreeinnernodes(tag))

  /** Entity class storing rows of table Flavourtesttreeleaves
   *  @param nodeid Database column nodeid SqlType(int8)
   *  @param testid Database column testid SqlType(int8)
   *  @param index Database column index SqlType(int8) */
  case class FlavourtesttreeleavesRow(nodeid: Long, testid: Long, index: Long)
  /** GetResult implicit for fetching FlavourtesttreeleavesRow objects using plain SQL queries */
  implicit def GetResultFlavourtesttreeleavesRow(implicit e0: GR[Long]): GR[FlavourtesttreeleavesRow] = GR{
    prs => import prs._
    FlavourtesttreeleavesRow.tupled((<<[Long], <<[Long], <<[Long]))
  }
  /** Table description of table flavourtesttreeleaves. Objects of this class serve as prototypes for rows in queries. */
  class Flavourtesttreeleaves(_tableTag: Tag) extends Table[FlavourtesttreeleavesRow](_tableTag, "flavourtesttreeleaves") {
    def * = (nodeid, testid, index) <> (FlavourtesttreeleavesRow.tupled, FlavourtesttreeleavesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(nodeid), Rep.Some(testid), Rep.Some(index)).shaped.<>({r=>import r._; _1.map(_=> FlavourtesttreeleavesRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column nodeid SqlType(int8) */
    val nodeid: Rep[Long] = column[Long]("nodeid")
    /** Database column testid SqlType(int8) */
    val testid: Rep[Long] = column[Long]("testid")
    /** Database column index SqlType(int8) */
    val index: Rep[Long] = column[Long]("index")

    /** Primary key of Flavourtesttreeleaves (database name flavourtesttreeleaves_nodeid_index_pk) */
    val pk = primaryKey("flavourtesttreeleaves_nodeid_index_pk", (nodeid, index))

    /** Foreign key referencing Flavourtesttreeinnernodes (database name flavourtesttreeleaves_flavourtesttreeinnernodes_nodeid_fk) */
    lazy val flavourtesttreeinnernodesFk = foreignKey("flavourtesttreeleaves_flavourtesttreeinnernodes_nodeid_fk", nodeid, Flavourtesttreeinnernodes)(r => r.nodeid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Tests (database name flavourtesttreeleaves_tests_testid_fk) */
    lazy val testsFk = foreignKey("flavourtesttreeleaves_tests_testid_fk", testid, Tests)(r => r.testid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Flavourtesttreeleaves */
  lazy val Flavourtesttreeleaves = new TableQuery(tag => new Flavourtesttreeleaves(tag))

  /** Entity class storing rows of table Flavourtoreferenceimageversion
   *  @param flavourid Database column flavourid SqlType(int8)
   *  @param referenceimageid Database column referenceimageid SqlType(int8)
   *  @param referenceimageversionid Database column referenceimageversionid SqlType(int8) */
  case class FlavourtoreferenceimageversionRow(flavourid: Long, referenceimageid: Long, referenceimageversionid: Long)
  /** GetResult implicit for fetching FlavourtoreferenceimageversionRow objects using plain SQL queries */
  implicit def GetResultFlavourtoreferenceimageversionRow(implicit e0: GR[Long]): GR[FlavourtoreferenceimageversionRow] = GR{
    prs => import prs._
    FlavourtoreferenceimageversionRow.tupled((<<[Long], <<[Long], <<[Long]))
  }
  /** Table description of table flavourtoreferenceimageversion. Objects of this class serve as prototypes for rows in queries. */
  class Flavourtoreferenceimageversion(_tableTag: Tag) extends Table[FlavourtoreferenceimageversionRow](_tableTag, "flavourtoreferenceimageversion") {
    def * = (flavourid, referenceimageid, referenceimageversionid) <> (FlavourtoreferenceimageversionRow.tupled, FlavourtoreferenceimageversionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(flavourid), Rep.Some(referenceimageid), Rep.Some(referenceimageversionid)).shaped.<>({r=>import r._; _1.map(_=> FlavourtoreferenceimageversionRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column flavourid SqlType(int8) */
    val flavourid: Rep[Long] = column[Long]("flavourid")
    /** Database column referenceimageid SqlType(int8) */
    val referenceimageid: Rep[Long] = column[Long]("referenceimageid")
    /** Database column referenceimageversionid SqlType(int8) */
    val referenceimageversionid: Rep[Long] = column[Long]("referenceimageversionid")

    /** Primary key of Flavourtoreferenceimageversion (database name flavourtoreferenceimageversion_flavourid_sequenceid_pk) */
    val pk = primaryKey("flavourtoreferenceimageversion_flavourid_sequenceid_pk", (flavourid, referenceimageid))

    /** Foreign key referencing Flavours (database name flavourtoreferenceimageversion_flavours_flavourid_fk) */
    lazy val flavoursFk = foreignKey("flavourtoreferenceimageversion_flavours_flavourid_fk", flavourid, Flavours)(r => r.flavourid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Referenceimages (database name flavourtoreferenceimageversion_referenceimages_referenceimageid) */
    lazy val referenceimagesFk = foreignKey("flavourtoreferenceimageversion_referenceimages_referenceimageid", referenceimageid, Referenceimages)(r => r.referenceimageid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Referenceimageversions (database name flavourtoreferenceimageversion_referenceimageversions_reference) */
    lazy val referenceimageversionsFk = foreignKey("flavourtoreferenceimageversion_referenceimageversions_reference", referenceimageversionid, Referenceimageversions)(r => r.referenceimageversionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Flavourtoreferenceimageversion */
  lazy val Flavourtoreferenceimageversion = new TableQuery(tag => new Flavourtoreferenceimageversion(tag))

  /** Entity class storing rows of table Flavourtosequence
   *  @param flavourid Database column flavourid SqlType(int8)
   *  @param sequenceid Database column sequenceid SqlType(int8)
   *  @param sequenceversionid Database column sequenceversionid SqlType(int8) */
  case class FlavourtosequenceRow(flavourid: Long, sequenceid: Long, sequenceversionid: Long)
  /** GetResult implicit for fetching FlavourtosequenceRow objects using plain SQL queries */
  implicit def GetResultFlavourtosequenceRow(implicit e0: GR[Long]): GR[FlavourtosequenceRow] = GR{
    prs => import prs._
    FlavourtosequenceRow.tupled((<<[Long], <<[Long], <<[Long]))
  }
  /** Table description of table flavourtosequence. Objects of this class serve as prototypes for rows in queries. */
  class Flavourtosequence(_tableTag: Tag) extends Table[FlavourtosequenceRow](_tableTag, "flavourtosequence") {
    def * = (flavourid, sequenceid, sequenceversionid) <> (FlavourtosequenceRow.tupled, FlavourtosequenceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(flavourid), Rep.Some(sequenceid), Rep.Some(sequenceversionid)).shaped.<>({r=>import r._; _1.map(_=> FlavourtosequenceRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column flavourid SqlType(int8) */
    val flavourid: Rep[Long] = column[Long]("flavourid")
    /** Database column sequenceid SqlType(int8) */
    val sequenceid: Rep[Long] = column[Long]("sequenceid")
    /** Database column sequenceversionid SqlType(int8) */
    val sequenceversionid: Rep[Long] = column[Long]("sequenceversionid")

    /** Primary key of Flavourtosequence (database name flavourtosequence_flavourid_sequenceid_pk) */
    val pk = primaryKey("flavourtosequence_flavourid_sequenceid_pk", (flavourid, sequenceid))

    /** Foreign key referencing Flavours (database name flavourtosequence_flavours_flavourid_fk) */
    lazy val flavoursFk = foreignKey("flavourtosequence_flavours_flavourid_fk", flavourid, Flavours)(r => r.flavourid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Sequences (database name flavourtosequence_sequences_sequenceid_fk) */
    lazy val sequencesFk = foreignKey("flavourtosequence_sequences_sequenceid_fk", sequenceid, Sequences)(r => r.sequenceid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Sequenceversions (database name flavourtosequence_sequenceversions_sequenceversionid_fk) */
    lazy val sequenceversionsFk = foreignKey("flavourtosequence_sequenceversions_sequenceversionid_fk", sequenceversionid, Sequenceversions)(r => r.sequenceversionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Flavourtosequence */
  lazy val Flavourtosequence = new TableQuery(tag => new Flavourtosequence(tag))

  /** Entity class storing rows of table Flavourtotestversion
   *  @param flavourid Database column flavourid SqlType(int8)
   *  @param testid Database column testid SqlType(int8)
   *  @param testversionid Database column testversionid SqlType(int8) */
  case class FlavourtotestversionRow(flavourid: Long, testid: Long, testversionid: Long)
  /** GetResult implicit for fetching FlavourtotestversionRow objects using plain SQL queries */
  implicit def GetResultFlavourtotestversionRow(implicit e0: GR[Long]): GR[FlavourtotestversionRow] = GR{
    prs => import prs._
    FlavourtotestversionRow.tupled((<<[Long], <<[Long], <<[Long]))
  }
  /** Table description of table flavourtotestversion. Objects of this class serve as prototypes for rows in queries. */
  class Flavourtotestversion(_tableTag: Tag) extends Table[FlavourtotestversionRow](_tableTag, "flavourtotestversion") {
    def * = (flavourid, testid, testversionid) <> (FlavourtotestversionRow.tupled, FlavourtotestversionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(flavourid), Rep.Some(testid), Rep.Some(testversionid)).shaped.<>({r=>import r._; _1.map(_=> FlavourtotestversionRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column flavourid SqlType(int8) */
    val flavourid: Rep[Long] = column[Long]("flavourid")
    /** Database column testid SqlType(int8) */
    val testid: Rep[Long] = column[Long]("testid")
    /** Database column testversionid SqlType(int8) */
    val testversionid: Rep[Long] = column[Long]("testversionid")

    /** Primary key of Flavourtotestversion (database name flavourtotestversion_flavourid_sequenceid_pk) */
    val pk = primaryKey("flavourtotestversion_flavourid_sequenceid_pk", (flavourid, testid))

    /** Foreign key referencing Flavours (database name flavourtotestversion_flavours_flavourid_fk) */
    lazy val flavoursFk = foreignKey("flavourtotestversion_flavours_flavourid_fk", flavourid, Flavours)(r => r.flavourid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Tests (database name flavourtotestversion_tests_testid_fk) */
    lazy val testsFk = foreignKey("flavourtotestversion_tests_testid_fk", testid, Tests)(r => r.testid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Testversions (database name flavourtotestversion_testversions_testversionid_fk) */
    lazy val testversionsFk = foreignKey("flavourtotestversion_testversions_testversionid_fk", testversionid, Testversions)(r => r.testversionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Flavourtotestversion */
  lazy val Flavourtotestversion = new TableQuery(tag => new Flavourtotestversion(tag))

  /** Entity class storing rows of table Projects
   *  @param projectid Database column projectid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text) */
  case class ProjectsRow(projectid: Long, name: String)
  /** GetResult implicit for fetching ProjectsRow objects using plain SQL queries */
  implicit def GetResultProjectsRow(implicit e0: GR[Long], e1: GR[String]): GR[ProjectsRow] = GR{
    prs => import prs._
    ProjectsRow.tupled((<<[Long], <<[String]))
  }
  /** Table description of table projects. Objects of this class serve as prototypes for rows in queries. */
  class Projects(_tableTag: Tag) extends Table[ProjectsRow](_tableTag, "projects") {
    def * = (projectid, name) <> (ProjectsRow.tupled, ProjectsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(projectid), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> ProjectsRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column projectid SqlType(bigserial), AutoInc, PrimaryKey */
    val projectid: Rep[Long] = column[Long]("projectid", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")

    /** Index over (name) (database name Projects__name) */
    val index1 = index("Projects__name", name)
  }
  /** Collection-like TableQuery object for table Projects */
  lazy val Projects = new TableQuery(tag => new Projects(tag))

  /** Entity class storing rows of table Referenceimages
   *  @param referenceimageid Database column referenceimageid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param project Database column project SqlType(int8)
   *  @param name Database column name SqlType(text) */
  case class ReferenceimagesRow(referenceimageid: Long, project: Long, name: String)
  /** GetResult implicit for fetching ReferenceimagesRow objects using plain SQL queries */
  implicit def GetResultReferenceimagesRow(implicit e0: GR[Long], e1: GR[String]): GR[ReferenceimagesRow] = GR{
    prs => import prs._
    ReferenceimagesRow.tupled((<<[Long], <<[Long], <<[String]))
  }
  /** Table description of table referenceimages. Objects of this class serve as prototypes for rows in queries. */
  class Referenceimages(_tableTag: Tag) extends Table[ReferenceimagesRow](_tableTag, "referenceimages") {
    def * = (referenceimageid, project, name) <> (ReferenceimagesRow.tupled, ReferenceimagesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(referenceimageid), Rep.Some(project), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> ReferenceimagesRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column referenceimageid SqlType(bigserial), AutoInc, PrimaryKey */
    val referenceimageid: Rep[Long] = column[Long]("referenceimageid", O.AutoInc, O.PrimaryKey)
    /** Database column project SqlType(int8) */
    val project: Rep[Long] = column[Long]("project")
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")

    /** Foreign key referencing Projects (database name referenceimages___fkproject) */
    lazy val projectsFk = foreignKey("referenceimages___fkproject", project, Projects)(r => r.projectid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Referenceimages */
  lazy val Referenceimages = new TableQuery(tag => new Referenceimages(tag))

  /** Entity class storing rows of table Referenceimageversions
   *  @param referenceimageversionid Database column referenceimageversionid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param url Database column url SqlType(text)
   *  @param referenceimage Database column referenceimage SqlType(int8)
   *  @param timecreated Database column timecreated SqlType(timestamptz)
   *  @param parent Database column parent SqlType(int8), Default(None) */
  case class ReferenceimageversionsRow(referenceimageversionid: Long, url: String, referenceimage: Long, timecreated: java.sql.Timestamp, parent: Option[Long] = None)
  /** GetResult implicit for fetching ReferenceimageversionsRow objects using plain SQL queries */
  implicit def GetResultReferenceimageversionsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[Long]]): GR[ReferenceimageversionsRow] = GR{
    prs => import prs._
    ReferenceimageversionsRow.tupled((<<[Long], <<[String], <<[Long], <<[java.sql.Timestamp], <<?[Long]))
  }
  /** Table description of table referenceimageversions. Objects of this class serve as prototypes for rows in queries. */
  class Referenceimageversions(_tableTag: Tag) extends Table[ReferenceimageversionsRow](_tableTag, "referenceimageversions") {
    def * = (referenceimageversionid, url, referenceimage, timecreated, parent) <> (ReferenceimageversionsRow.tupled, ReferenceimageversionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(referenceimageversionid), Rep.Some(url), Rep.Some(referenceimage), Rep.Some(timecreated), parent).shaped.<>({r=>import r._; _1.map(_=> ReferenceimageversionsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column referenceimageversionid SqlType(bigserial), AutoInc, PrimaryKey */
    val referenceimageversionid: Rep[Long] = column[Long]("referenceimageversionid", O.AutoInc, O.PrimaryKey)
    /** Database column url SqlType(text) */
    val url: Rep[String] = column[String]("url")
    /** Database column referenceimage SqlType(int8) */
    val referenceimage: Rep[Long] = column[Long]("referenceimage")
    /** Database column timecreated SqlType(timestamptz) */
    val timecreated: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("timecreated")
    /** Database column parent SqlType(int8), Default(None) */
    val parent: Rep[Option[Long]] = column[Option[Long]]("parent", O.Default(None))

    /** Foreign key referencing Referenceimages (database name referenceimageversions___fkreferenceimage) */
    lazy val referenceimagesFk = foreignKey("referenceimageversions___fkreferenceimage", referenceimage, Referenceimages)(r => r.referenceimageid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Referenceimageversions (database name referenceimageversions_referenceimageversions_referenceimagever) */
    lazy val referenceimageversionsFk = foreignKey("referenceimageversions_referenceimageversions_referenceimagever", parent, Referenceimageversions)(r => Rep.Some(r.referenceimageversionid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (url) (database name ReferenceImageVersions_URL_uindex) */
    val index1 = index("ReferenceImageVersions_URL_uindex", url, unique=true)
  }
  /** Collection-like TableQuery object for table Referenceimageversions */
  lazy val Referenceimageversions = new TableQuery(tag => new Referenceimageversions(tag))

  /** Entity class storing rows of table Resultimages
   *  @param resultimageid Database column resultimageid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param url Database column url SqlType(text)
   *  @param project Database column project SqlType(int8)
   *  @param timecreated Database column timecreated SqlType(timestamptz) */
  case class ResultimagesRow(resultimageid: Long, name: String, url: String, project: Long, timecreated: java.sql.Timestamp)
  /** GetResult implicit for fetching ResultimagesRow objects using plain SQL queries */
  implicit def GetResultResultimagesRow(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[ResultimagesRow] = GR{
    prs => import prs._
    ResultimagesRow.tupled((<<[Long], <<[String], <<[String], <<[Long], <<[java.sql.Timestamp]))
  }
  /** Table description of table resultimages. Objects of this class serve as prototypes for rows in queries. */
  class Resultimages(_tableTag: Tag) extends Table[ResultimagesRow](_tableTag, "resultimages") {
    def * = (resultimageid, name, url, project, timecreated) <> (ResultimagesRow.tupled, ResultimagesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(resultimageid), Rep.Some(name), Rep.Some(url), Rep.Some(project), Rep.Some(timecreated)).shaped.<>({r=>import r._; _1.map(_=> ResultimagesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column resultimageid SqlType(bigserial), AutoInc, PrimaryKey */
    val resultimageid: Rep[Long] = column[Long]("resultimageid", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column url SqlType(text) */
    val url: Rep[String] = column[String]("url")
    /** Database column project SqlType(int8) */
    val project: Rep[Long] = column[Long]("project")
    /** Database column timecreated SqlType(timestamptz) */
    val timecreated: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("timecreated")

    /** Foreign key referencing Projects (database name resultimages___fkproject) */
    lazy val projectsFk = foreignKey("resultimages___fkproject", project, Projects)(r => r.projectid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Resultimages */
  lazy val Resultimages = new TableQuery(tag => new Resultimages(tag))

  /** Entity class storing rows of table Sequences
   *  @param sequenceid Database column sequenceid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param project Database column project SqlType(int8) */
  case class SequencesRow(sequenceid: Long, name: String, project: Long)
  /** GetResult implicit for fetching SequencesRow objects using plain SQL queries */
  implicit def GetResultSequencesRow(implicit e0: GR[Long], e1: GR[String]): GR[SequencesRow] = GR{
    prs => import prs._
    SequencesRow.tupled((<<[Long], <<[String], <<[Long]))
  }
  /** Table description of table sequences. Objects of this class serve as prototypes for rows in queries. */
  class Sequences(_tableTag: Tag) extends Table[SequencesRow](_tableTag, "sequences") {
    def * = (sequenceid, name, project) <> (SequencesRow.tupled, SequencesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(sequenceid), Rep.Some(name), Rep.Some(project)).shaped.<>({r=>import r._; _1.map(_=> SequencesRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column sequenceid SqlType(bigserial), AutoInc, PrimaryKey */
    val sequenceid: Rep[Long] = column[Long]("sequenceid", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column project SqlType(int8) */
    val project: Rep[Long] = column[Long]("project")

    /** Foreign key referencing Projects (database name sequences___fkproject) */
    lazy val projectsFk = foreignKey("sequences___fkproject", project, Projects)(r => r.projectid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Sequences */
  lazy val Sequences = new TableQuery(tag => new Sequences(tag))

  /** Entity class storing rows of table Sequenceversions
   *  @param sequenceversionid Database column sequenceversionid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param sequence Database column sequence SqlType(int8)
   *  @param specification Database column specification SqlType(text)
   *  @param timecreated Database column timecreated SqlType(timestamptz)
   *  @param parent Database column parent SqlType(int8), Default(None) */
  case class SequenceversionsRow(sequenceversionid: Long, sequence: Long, specification: String, timecreated: java.sql.Timestamp, parent: Option[Long] = None)
  /** GetResult implicit for fetching SequenceversionsRow objects using plain SQL queries */
  implicit def GetResultSequenceversionsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[Long]]): GR[SequenceversionsRow] = GR{
    prs => import prs._
    SequenceversionsRow.tupled((<<[Long], <<[Long], <<[String], <<[java.sql.Timestamp], <<?[Long]))
  }
  /** Table description of table sequenceversions. Objects of this class serve as prototypes for rows in queries. */
  class Sequenceversions(_tableTag: Tag) extends Table[SequenceversionsRow](_tableTag, "sequenceversions") {
    def * = (sequenceversionid, sequence, specification, timecreated, parent) <> (SequenceversionsRow.tupled, SequenceversionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(sequenceversionid), Rep.Some(sequence), Rep.Some(specification), Rep.Some(timecreated), parent).shaped.<>({r=>import r._; _1.map(_=> SequenceversionsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column sequenceversionid SqlType(bigserial), AutoInc, PrimaryKey */
    val sequenceversionid: Rep[Long] = column[Long]("sequenceversionid", O.AutoInc, O.PrimaryKey)
    /** Database column sequence SqlType(int8) */
    val sequence: Rep[Long] = column[Long]("sequence")
    /** Database column specification SqlType(text) */
    val specification: Rep[String] = column[String]("specification")
    /** Database column timecreated SqlType(timestamptz) */
    val timecreated: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("timecreated")
    /** Database column parent SqlType(int8), Default(None) */
    val parent: Rep[Option[Long]] = column[Option[Long]]("parent", O.Default(None))

    /** Foreign key referencing Sequences (database name sequenceversions___fksequence) */
    lazy val sequencesFk = foreignKey("sequenceversions___fksequence", sequence, Sequences)(r => r.sequenceid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Sequenceversions (database name sequenceversions_sequenceversions_sequenceversionid_fk) */
    lazy val sequenceversionsFk = foreignKey("sequenceversions_sequenceversions_sequenceversionid_fk", parent, Sequenceversions)(r => Rep.Some(r.sequenceversionid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Sequenceversions */
  lazy val Sequenceversions = new TableQuery(tag => new Sequenceversions(tag))

  /** Entity class storing rows of table StorageReferenceimage
   *  @param referenceimageverisonid Database column referenceimageverisonid SqlType(int8), PrimaryKey
   *  @param binary Database column binary SqlType(bytea) */
  case class StorageReferenceimageRow(referenceimageverisonid: Long, binary: Array[Byte])
  /** GetResult implicit for fetching StorageReferenceimageRow objects using plain SQL queries */
  implicit def GetResultStorageReferenceimageRow(implicit e0: GR[Long], e1: GR[Array[Byte]]): GR[StorageReferenceimageRow] = GR{
    prs => import prs._
    StorageReferenceimageRow.tupled((<<[Long], <<[Array[Byte]]))
  }
  /** Table description of table storage_referenceimage. Objects of this class serve as prototypes for rows in queries. */
  class StorageReferenceimage(_tableTag: Tag) extends Table[StorageReferenceimageRow](_tableTag, "storage_referenceimage") {
    def * = (referenceimageverisonid, binary) <> (StorageReferenceimageRow.tupled, StorageReferenceimageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(referenceimageverisonid), Rep.Some(binary)).shaped.<>({r=>import r._; _1.map(_=> StorageReferenceimageRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column referenceimageverisonid SqlType(int8), PrimaryKey */
    val referenceimageverisonid: Rep[Long] = column[Long]("referenceimageverisonid", O.PrimaryKey)
    /** Database column binary SqlType(bytea) */
    val binary: Rep[Array[Byte]] = column[Array[Byte]]("binary")

    /** Foreign key referencing Referenceimageversions (database name storage_referenceimage_referenceimageversions_referenceimagever) */
    lazy val referenceimageversionsFk = foreignKey("storage_referenceimage_referenceimageversions_referenceimagever", referenceimageverisonid, Referenceimageversions)(r => r.referenceimageversionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table StorageReferenceimage */
  lazy val StorageReferenceimage = new TableQuery(tag => new StorageReferenceimage(tag))

  /** Entity class storing rows of table StorageResultimage
   *  @param resultimageid Database column resultimageid SqlType(int8), PrimaryKey
   *  @param binary Database column binary SqlType(bytea) */
  case class StorageResultimageRow(resultimageid: Long, binary: Array[Byte])
  /** GetResult implicit for fetching StorageResultimageRow objects using plain SQL queries */
  implicit def GetResultStorageResultimageRow(implicit e0: GR[Long], e1: GR[Array[Byte]]): GR[StorageResultimageRow] = GR{
    prs => import prs._
    StorageResultimageRow.tupled((<<[Long], <<[Array[Byte]]))
  }
  /** Table description of table storage_resultimage. Objects of this class serve as prototypes for rows in queries. */
  class StorageResultimage(_tableTag: Tag) extends Table[StorageResultimageRow](_tableTag, "storage_resultimage") {
    def * = (resultimageid, binary) <> (StorageResultimageRow.tupled, StorageResultimageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(resultimageid), Rep.Some(binary)).shaped.<>({r=>import r._; _1.map(_=> StorageResultimageRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column resultimageid SqlType(int8), PrimaryKey */
    val resultimageid: Rep[Long] = column[Long]("resultimageid", O.PrimaryKey)
    /** Database column binary SqlType(bytea) */
    val binary: Rep[Array[Byte]] = column[Array[Byte]]("binary")

    /** Foreign key referencing Resultimages (database name storage_resultimage_resultimages_resultimageid_fk) */
    lazy val resultimagesFk = foreignKey("storage_resultimage_resultimages_resultimageid_fk", resultimageid, Resultimages)(r => r.resultimageid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table StorageResultimage */
  lazy val StorageResultimage = new TableQuery(tag => new StorageResultimage(tag))

  /** Entity class storing rows of table StorageTestexecutable
   *  @param testexecutableid Database column testexecutableid SqlType(int8), PrimaryKey
   *  @param binary Database column binary SqlType(bytea) */
  case class StorageTestexecutableRow(testexecutableid: Long, binary: Array[Byte])
  /** GetResult implicit for fetching StorageTestexecutableRow objects using plain SQL queries */
  implicit def GetResultStorageTestexecutableRow(implicit e0: GR[Long], e1: GR[Array[Byte]]): GR[StorageTestexecutableRow] = GR{
    prs => import prs._
    StorageTestexecutableRow.tupled((<<[Long], <<[Array[Byte]]))
  }
  /** Table description of table storage_testexecutable. Objects of this class serve as prototypes for rows in queries. */
  class StorageTestexecutable(_tableTag: Tag) extends Table[StorageTestexecutableRow](_tableTag, "storage_testexecutable") {
    def * = (testexecutableid, binary) <> (StorageTestexecutableRow.tupled, StorageTestexecutableRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(testexecutableid), Rep.Some(binary)).shaped.<>({r=>import r._; _1.map(_=> StorageTestexecutableRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column testexecutableid SqlType(int8), PrimaryKey */
    val testexecutableid: Rep[Long] = column[Long]("testexecutableid", O.PrimaryKey)
    /** Database column binary SqlType(bytea) */
    val binary: Rep[Array[Byte]] = column[Array[Byte]]("binary")

    /** Foreign key referencing Testexecutables (database name storage_testexecutable_testexecutables_testexecutableid_fk) */
    lazy val testexecutablesFk = foreignKey("storage_testexecutable_testexecutables_testexecutableid_fk", testexecutableid, Testexecutables)(r => r.testexecutableid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table StorageTestexecutable */
  lazy val StorageTestexecutable = new TableQuery(tag => new StorageTestexecutable(tag))

  /** Entity class storing rows of table Testexecutables
   *  @param testexecutableid Database column testexecutableid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param url Database column url SqlType(text)
   *  @param project Database column project SqlType(int8) */
  case class TestexecutablesRow(testexecutableid: Long, name: String, url: String, project: Long)
  /** GetResult implicit for fetching TestexecutablesRow objects using plain SQL queries */
  implicit def GetResultTestexecutablesRow(implicit e0: GR[Long], e1: GR[String]): GR[TestexecutablesRow] = GR{
    prs => import prs._
    TestexecutablesRow.tupled((<<[Long], <<[String], <<[String], <<[Long]))
  }
  /** Table description of table testexecutables. Objects of this class serve as prototypes for rows in queries. */
  class Testexecutables(_tableTag: Tag) extends Table[TestexecutablesRow](_tableTag, "testexecutables") {
    def * = (testexecutableid, name, url, project) <> (TestexecutablesRow.tupled, TestexecutablesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(testexecutableid), Rep.Some(name), Rep.Some(url), Rep.Some(project)).shaped.<>({r=>import r._; _1.map(_=> TestexecutablesRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column testexecutableid SqlType(bigserial), AutoInc, PrimaryKey */
    val testexecutableid: Rep[Long] = column[Long]("testexecutableid", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column url SqlType(text) */
    val url: Rep[String] = column[String]("url")
    /** Database column project SqlType(int8) */
    val project: Rep[Long] = column[Long]("project")

    /** Foreign key referencing Projects (database name testexecutables___fkproject) */
    lazy val projectsFk = foreignKey("testexecutables___fkproject", project, Projects)(r => r.projectid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (name) (database name TestExecutables_name_uindex) */
    val index1 = index("TestExecutables_name_uindex", name, unique=true)
    /** Uniqueness Index over (url) (database name TestExecutables_url_uindex) */
    val index2 = index("TestExecutables_url_uindex", url, unique=true)
  }
  /** Collection-like TableQuery object for table Testexecutables */
  lazy val Testexecutables = new TableQuery(tag => new Testexecutables(tag))

  /** Entity class storing rows of table Testexecutions
   *  @param testexecutionid Database column testexecutionid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param flavour Database column flavour SqlType(int8)
   *  @param timebegin Database column timebegin SqlType(timestamptz)
   *  @param timeend Database column timeend SqlType(timestamptz), Default(None)
   *  @param executable Database column executable SqlType(int8) */
  case class TestexecutionsRow(testexecutionid: Long, name: String, flavour: Long, timebegin: java.sql.Timestamp, timeend: Option[java.sql.Timestamp] = None, executable: Long)
  /** GetResult implicit for fetching TestexecutionsRow objects using plain SQL queries */
  implicit def GetResultTestexecutionsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Timestamp]]): GR[TestexecutionsRow] = GR{
    prs => import prs._
    TestexecutionsRow.tupled((<<[Long], <<[String], <<[Long], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<[Long]))
  }
  /** Table description of table testexecutions. Objects of this class serve as prototypes for rows in queries. */
  class Testexecutions(_tableTag: Tag) extends Table[TestexecutionsRow](_tableTag, "testexecutions") {
    def * = (testexecutionid, name, flavour, timebegin, timeend, executable) <> (TestexecutionsRow.tupled, TestexecutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(testexecutionid), Rep.Some(name), Rep.Some(flavour), Rep.Some(timebegin), timeend, Rep.Some(executable)).shaped.<>({r=>import r._; _1.map(_=> TestexecutionsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column testexecutionid SqlType(bigserial), AutoInc, PrimaryKey */
    val testexecutionid: Rep[Long] = column[Long]("testexecutionid", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column flavour SqlType(int8) */
    val flavour: Rep[Long] = column[Long]("flavour")
    /** Database column timebegin SqlType(timestamptz) */
    val timebegin: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("timebegin")
    /** Database column timeend SqlType(timestamptz), Default(None) */
    val timeend: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("timeend", O.Default(None))
    /** Database column executable SqlType(int8) */
    val executable: Rep[Long] = column[Long]("executable")

    /** Foreign key referencing Flavours (database name testexecutions_flavours_flavourid_fk) */
    lazy val flavoursFk = foreignKey("testexecutions_flavours_flavourid_fk", flavour, Flavours)(r => r.flavourid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Testexecutables (database name testexecutions_testexecutables_testexecutableid_fk) */
    lazy val testexecutablesFk = foreignKey("testexecutions_testexecutables_testexecutableid_fk", executable, Testexecutables)(r => r.testexecutableid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Testexecutions */
  lazy val Testexecutions = new TableQuery(tag => new Testexecutions(tag))

  /** Entity class storing rows of table Testexecutiontreeinnernodes
   *  @param nodeid Database column nodeid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param testexecution Database column testexecution SqlType(int8)
   *  @param parent Database column parent SqlType(int8), Default(None)
   *  @param index Database column index SqlType(int8)
   *  @param categoryname Database column categoryname SqlType(text) */
  case class TestexecutiontreeinnernodesRow(nodeid: Long, testexecution: Long, parent: Option[Long] = None, index: Long, categoryname: String)
  /** GetResult implicit for fetching TestexecutiontreeinnernodesRow objects using plain SQL queries */
  implicit def GetResultTestexecutiontreeinnernodesRow(implicit e0: GR[Long], e1: GR[Option[Long]], e2: GR[String]): GR[TestexecutiontreeinnernodesRow] = GR{
    prs => import prs._
    TestexecutiontreeinnernodesRow.tupled((<<[Long], <<[Long], <<?[Long], <<[Long], <<[String]))
  }
  /** Table description of table testexecutiontreeinnernodes. Objects of this class serve as prototypes for rows in queries. */
  class Testexecutiontreeinnernodes(_tableTag: Tag) extends Table[TestexecutiontreeinnernodesRow](_tableTag, "testexecutiontreeinnernodes") {
    def * = (nodeid, testexecution, parent, index, categoryname) <> (TestexecutiontreeinnernodesRow.tupled, TestexecutiontreeinnernodesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(nodeid), Rep.Some(testexecution), parent, Rep.Some(index), Rep.Some(categoryname)).shaped.<>({r=>import r._; _1.map(_=> TestexecutiontreeinnernodesRow.tupled((_1.get, _2.get, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column nodeid SqlType(bigserial), AutoInc, PrimaryKey */
    val nodeid: Rep[Long] = column[Long]("nodeid", O.AutoInc, O.PrimaryKey)
    /** Database column testexecution SqlType(int8) */
    val testexecution: Rep[Long] = column[Long]("testexecution")
    /** Database column parent SqlType(int8), Default(None) */
    val parent: Rep[Option[Long]] = column[Option[Long]]("parent", O.Default(None))
    /** Database column index SqlType(int8) */
    val index: Rep[Long] = column[Long]("index")
    /** Database column categoryname SqlType(text) */
    val categoryname: Rep[String] = column[String]("categoryname")

    /** Foreign key referencing Testexecutions (database name testexecutiontreeinnernodes_flavours_testexecutionid_fk) */
    lazy val testexecutionsFk = foreignKey("testexecutiontreeinnernodes_flavours_testexecutionid_fk", testexecution, Testexecutions)(r => r.testexecutionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (testexecution,index,parent) (database name testexecutiontreeinnernodes_flavour_index_parent_pk) */
    val index1 = index("testexecutiontreeinnernodes_flavour_index_parent_pk", (testexecution, index, parent), unique=true)
  }
  /** Collection-like TableQuery object for table Testexecutiontreeinnernodes */
  lazy val Testexecutiontreeinnernodes = new TableQuery(tag => new Testexecutiontreeinnernodes(tag))

  /** Entity class storing rows of table Testexecutiontreeleaves
   *  @param nodeid Database column nodeid SqlType(int8)
   *  @param index Database column index SqlType(int8)
   *  @param executionid Database column executionid SqlType(int8) */
  case class TestexecutiontreeleavesRow(nodeid: Long, index: Long, executionid: Long)
  /** GetResult implicit for fetching TestexecutiontreeleavesRow objects using plain SQL queries */
  implicit def GetResultTestexecutiontreeleavesRow(implicit e0: GR[Long]): GR[TestexecutiontreeleavesRow] = GR{
    prs => import prs._
    TestexecutiontreeleavesRow.tupled((<<[Long], <<[Long], <<[Long]))
  }
  /** Table description of table testexecutiontreeleaves. Objects of this class serve as prototypes for rows in queries. */
  class Testexecutiontreeleaves(_tableTag: Tag) extends Table[TestexecutiontreeleavesRow](_tableTag, "testexecutiontreeleaves") {
    def * = (nodeid, index, executionid) <> (TestexecutiontreeleavesRow.tupled, TestexecutiontreeleavesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(nodeid), Rep.Some(index), Rep.Some(executionid)).shaped.<>({r=>import r._; _1.map(_=> TestexecutiontreeleavesRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column nodeid SqlType(int8) */
    val nodeid: Rep[Long] = column[Long]("nodeid")
    /** Database column index SqlType(int8) */
    val index: Rep[Long] = column[Long]("index")
    /** Database column executionid SqlType(int8) */
    val executionid: Rep[Long] = column[Long]("executionid")

    /** Primary key of Testexecutiontreeleaves (database name testexecutiontreeleaves_nodeid_index_pk) */
    val pk = primaryKey("testexecutiontreeleaves_nodeid_index_pk", (nodeid, index))

    /** Foreign key referencing Executions (database name testexecutiontreeleaves_executions_executionid_fk) */
    lazy val executionsFk = foreignKey("testexecutiontreeleaves_executions_executionid_fk", executionid, Executions)(r => r.executionid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Testexecutiontreeinnernodes (database name testexecutiontreeleaves_flavourtesttreeinnernodes_nodeid_fk) */
    lazy val testexecutiontreeinnernodesFk = foreignKey("testexecutiontreeleaves_flavourtesttreeinnernodes_nodeid_fk", nodeid, Testexecutiontreeinnernodes)(r => r.nodeid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Testexecutiontreeleaves */
  lazy val Testexecutiontreeleaves = new TableQuery(tag => new Testexecutiontreeleaves(tag))

  /** Entity class storing rows of table Tests
   *  @param testid Database column testid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param project Database column project SqlType(int8) */
  case class TestsRow(testid: Long, name: String, project: Long)
  /** GetResult implicit for fetching TestsRow objects using plain SQL queries */
  implicit def GetResultTestsRow(implicit e0: GR[Long], e1: GR[String]): GR[TestsRow] = GR{
    prs => import prs._
    TestsRow.tupled((<<[Long], <<[String], <<[Long]))
  }
  /** Table description of table tests. Objects of this class serve as prototypes for rows in queries. */
  class Tests(_tableTag: Tag) extends Table[TestsRow](_tableTag, "tests") {
    def * = (testid, name, project) <> (TestsRow.tupled, TestsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(testid), Rep.Some(name), Rep.Some(project)).shaped.<>({r=>import r._; _1.map(_=> TestsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column testid SqlType(bigserial), AutoInc, PrimaryKey */
    val testid: Rep[Long] = column[Long]("testid", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column project SqlType(int8) */
    val project: Rep[Long] = column[Long]("project")

    /** Foreign key referencing Projects (database name tests___fkproject) */
    lazy val projectsFk = foreignKey("tests___fkproject", project, Projects)(r => r.projectid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Tests */
  lazy val Tests = new TableQuery(tag => new Tests(tag))

  /** Entity class storing rows of table Testversions
   *  @param testversionid Database column testversionid SqlType(bigserial), AutoInc, PrimaryKey
   *  @param test Database column test SqlType(int8)
   *  @param specification Database column specification SqlType(text)
   *  @param timecreated Database column timecreated SqlType(timestamptz)
   *  @param parent Database column parent SqlType(int8), Default(None) */
  case class TestversionsRow(testversionid: Long, test: Long, specification: String, timecreated: java.sql.Timestamp, parent: Option[Long] = None)
  /** GetResult implicit for fetching TestversionsRow objects using plain SQL queries */
  implicit def GetResultTestversionsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[Long]]): GR[TestversionsRow] = GR{
    prs => import prs._
    TestversionsRow.tupled((<<[Long], <<[Long], <<[String], <<[java.sql.Timestamp], <<?[Long]))
  }
  /** Table description of table testversions. Objects of this class serve as prototypes for rows in queries. */
  class Testversions(_tableTag: Tag) extends Table[TestversionsRow](_tableTag, "testversions") {
    def * = (testversionid, test, specification, timecreated, parent) <> (TestversionsRow.tupled, TestversionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(testversionid), Rep.Some(test), Rep.Some(specification), Rep.Some(timecreated), parent).shaped.<>({r=>import r._; _1.map(_=> TestversionsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column testversionid SqlType(bigserial), AutoInc, PrimaryKey */
    val testversionid: Rep[Long] = column[Long]("testversionid", O.AutoInc, O.PrimaryKey)
    /** Database column test SqlType(int8) */
    val test: Rep[Long] = column[Long]("test")
    /** Database column specification SqlType(text) */
    val specification: Rep[String] = column[String]("specification")
    /** Database column timecreated SqlType(timestamptz) */
    val timecreated: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("timecreated")
    /** Database column parent SqlType(int8), Default(None) */
    val parent: Rep[Option[Long]] = column[Option[Long]]("parent", O.Default(None))

    /** Foreign key referencing Tests (database name testversions___fktest) */
    lazy val testsFk = foreignKey("testversions___fktest", test, Tests)(r => r.testid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Testversions (database name testversions_testversions_testversionid_fk) */
    lazy val testversionsFk = foreignKey("testversions_testversions_testversionid_fk", parent, Testversions)(r => Rep.Some(r.testversionid), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Testversions */
  lazy val Testversions = new TableQuery(tag => new Testversions(tag))
}
