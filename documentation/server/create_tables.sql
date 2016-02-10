CREATE TABLE public.Projects
(
  projectID BIGSERIAL PRIMARY KEY NOT NULL,
  name      TEXT                  NOT NULL--,
  --tags      TEXT ARRAY            NOT NULL
);
CREATE UNIQUE INDEX "Projects__id" ON public.Projects (projectID);
CREATE INDEX "Projects__name" ON public.Projects (name);


CREATE TABLE public.Flavours
(
  flavourID BIGSERIAL PRIMARY KEY NOT NULL,
  name      TEXT                  NOT NULL,
  --tags      TEXT ARRAY            NOT NULL,
  project   BIGINT                NOT NULL,
  CONSTRAINT Flavours___fkProject FOREIGN KEY (project) REFERENCES projects (projectID)
);

CREATE UNIQUE INDEX "Flavours__id" ON public.Flavours (flavourID);
CREATE INDEX "Flavours__name" ON public.Flavours (name);


CREATE TABLE public.Tests
(
  testID  BIGSERIAL PRIMARY KEY NOT NULL,
  name    TEXT                  NOT NULL,
  --tags    TEXT ARRAY            NOT NULL,
  project BIGINT                NOT NULL,
  CONSTRAINT Tests___fkProject FOREIGN KEY (project) REFERENCES projects (projectid)
);
CREATE UNIQUE INDEX "Tests_testID_uindex" ON public.Tests (testID);
CREATE INDEX "Tests_projectID" ON public.Tests (testID);

CREATE TABLE public.ReferenceImages
(
  referenceImageID BIGSERIAL PRIMARY KEY NOT NULL,
  project          BIGINT                NOT NULL,
  name             TEXT                  NOT NULL,
  --tags             TEXT ARRAY            NOT NULL,
  CONSTRAINT ReferenceImages___fkProject FOREIGN KEY (project) REFERENCES projects (projectid)
);
CREATE UNIQUE INDEX "ReferenceImages_referenceImageID_uindex" ON public.ReferenceImages (referenceImageID);
--

CREATE TABLE public.ResultImages
(
  resultImageID BIGSERIAL PRIMARY KEY NOT NULL,
  name          TEXT                  NOT NULL,
  URL           TEXT                  NOT NULL,
  project       BIGINT                NOT NULL,
  timeCreated   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
  CONSTRAINT ResultImages___fkProject FOREIGN KEY (project) REFERENCES projects (projectid)
  --tags          TEXT ARRAY            NOT NULL,
);
CREATE UNIQUE INDEX "ResultImages_resultImageID_uindex" ON public.ResultImages (resultImageID);



CREATE TABLE public.ReferenceImageVersions
(
  referenceImageVersionID BIGSERIAL PRIMARY KEY NOT NULL,
  URL                     TEXT                  NOT NULL,
  referenceImage          BIGINT                NOT NULL,
  timeCreated   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
  parent        BIGINT,
  CONSTRAINT ReferenceImageVersions_ReferenceImageVersions_referenceImageVersionID_fk FOREIGN KEY (parent) REFERENCES ReferenceImageVersions (referenceImageVersionID),
  CONSTRAINT ReferenceImageVersions___fkReferenceImage FOREIGN KEY (referenceImage) REFERENCES referenceimages (referenceimageid)
);
CREATE UNIQUE INDEX "ReferenceImageVersions_resultImageVersionID_uindex" ON public.ReferenceImageVersions (referenceImageVersionID);
CREATE UNIQUE INDEX "ReferenceImageVersions_URL_uindex" ON public.ReferenceImageVersions (URL);


CREATE TABLE public.Sequences
(
  sequenceID BIGSERIAL PRIMARY KEY NOT NULL,
  name       TEXT                  NOT NULL,
  --tags       TEXT ARRAY            NOT NULL,
  project    BIGINT                NOT NULL,
  CONSTRAINT Sequences___fkProject FOREIGN KEY (project) REFERENCES projects (projectid)
);
CREATE UNIQUE INDEX "Sequences_sequencesID_uindex" ON public.Sequences (sequenceID);
CREATE INDEX "Sequences_projectID" ON public.Sequences (sequenceID);

CREATE TABLE public.TestVersions
(
  testVersionID BIGSERIAL PRIMARY KEY    NOT NULL,
  test          BIGINT                   NOT NULL,
  specification TEXT                     NOT NULL,
  timeCreated   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
  parent        BIGINT,
  CONSTRAINT TestVersions___fkTest FOREIGN KEY (test) REFERENCES tests (testid),
  CONSTRAINT TestVersions_testversions_testVersionID_fk FOREIGN KEY (parent) REFERENCES testversions (testVersionID)
);
CREATE UNIQUE INDEX "TestVersions_testVersionID_uindex" ON public.TestVersions (testVersionID);


CREATE TABLE public.SequenceVersions
(
  sequenceVersionID BIGSERIAL PRIMARY KEY    NOT NULL,
  sequence          BIGINT                   NOT NULL,
  specification     TEXT                     NOT NULL,
  timeCreated       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
  parent            BIGINT,
  CONSTRAINT SequenceVersions___fkSequence FOREIGN KEY (sequence) REFERENCES sequences (sequenceID),
  CONSTRAINT SequenceVersions_sequenceversions_sequenceVersionID_fk FOREIGN KEY (parent) REFERENCES SequenceVersions (sequenceVersionID)
);
CREATE UNIQUE INDEX "SequenceVersions_sequenceVersionID_uindex" ON public.SequenceVersions (sequenceVersionID);


CREATE TABLE public.TestExecutables
(
  testExecutableID BIGSERIAL PRIMARY KEY NOT NULL,
  name             TEXT                  NOT NULL,
  url              TEXT                  NOT NULL,
  project          BIGINT                NOT NULL,
  timeCreated       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
  --tags             TEXT ARRAY            NOT NULL,
  CONSTRAINT TestExecutables___fkProject FOREIGN KEY (project) REFERENCES projects (projectid)
);
CREATE UNIQUE INDEX "TestExecutables_testExecutableID_uindex" ON public.TestExecutables (testExecutableID);
CREATE UNIQUE INDEX "TestExecutables_name_uindex" ON public.TestExecutables (name);
CREATE UNIQUE INDEX "TestExecutables_url_uindex" ON public.TestExecutables (url);

CREATE TABLE public.FlavourTestTreeInnerNodes
(
  nodeID       BIGSERIAL NOT NULL,
  flavour      BIGINT    NOT NULL,
  parent       BIGINT,
  index        BIGINT    NOT NULL,
  categoryName TEXT      NOT NULL,
  CONSTRAINT FlavourTestTreeInnerNodes_nodeId_pk PRIMARY KEY (nodeID),
  CONSTRAINT FlavourTestTreeInnerNodes_flavour_index_parent_pk UNIQUE (flavour, index, parent),
  CONSTRAINT FlavourTestTreeInnerNodes_flavours_flavourid_fk FOREIGN KEY (flavour) REFERENCES flavours (flavourid)
);
CREATE INDEX "FlavourTestTreeInnerNodes_flavour_index" ON public.FlavourTestTreeInnerNodes (flavour);


CREATE TABLE public.FlavourTestTreeLeaves
(
  nodeID        BIGINT NOT NULL,
  testID BIGINT NOT NULL,
  index         BIGINT NOT NULL,
  CONSTRAINT FlavourTestTreeLeaves_nodeID_index_pk PRIMARY KEY (nodeID, index),
  CONSTRAINT FlavourTestTreeLeaves_flavourtesttreeinnernodes_nodeid_fk FOREIGN KEY (nodeID) REFERENCES flavourtesttreeinnernodes (nodeid),
  CONSTRAINT FlavourTestTreeLeaves_tests_testid_fk FOREIGN KEY (testID) REFERENCES tests (testid)
);
CREATE INDEX "FlavourTestTreeLeaves_nodeID_index" ON public.FlavourTestTreeLeaves (nodeID);

CREATE TABLE public.TestExecutions
(
    testExecutionID BIGSERIAL PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    flavour BIGINT NOT NULL,
    timeBegin TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
    timeEnd TIMESTAMP WITH TIME ZONE,
    executable BIGINT NOT NULL,
    CONSTRAINT TestExecutions_flavours_flavourid_fk FOREIGN KEY (flavour) REFERENCES flavours (flavourid),
    CONSTRAINT TestExecutions_testexecutables_testexecutableid_fk FOREIGN KEY (executable) REFERENCES testexecutables (testexecutableid)
);
CREATE UNIQUE INDEX "TestExecutions_testExecutionID_uindex" ON public.TestExecutions (testExecutionID);



CREATE TABLE public.TestExecutionTreeInnerNodes
(
  nodeID       BIGSERIAL NOT NULL,
  testExecution      BIGINT    NOT NULL,
  parent       BIGINT,
  index        BIGINT    NOT NULL,
  categoryName TEXT      NOT NULL,
  CONSTRAINT TestExecutionTreeInnerNodes_nodeId_pk PRIMARY KEY (nodeID),
  CONSTRAINT TestExecutionTreeInnerNodes_flavour_index_parent_pk UNIQUE (testExecution, index, parent),
  CONSTRAINT TestExecutionTreeInnerNodes_flavours_testExecutionID_fk FOREIGN KEY (testExecution) REFERENCES TestExecutions (testExecutionID)
);
CREATE INDEX "TestExecutionTreeInnerNodes_testExecution_index" ON public.TestExecutionTreeInnerNodes (testExecution);


CREATE TABLE public.executions
(
    executionid BIGSERIAL PRIMARY KEY NOT NULL,
    testversion BIGINT NOT NULL,
    status TEXT NOT NULL,
    result TEXT,
    startTime TIMESTAMP WITH TIME ZONE,
    durationSeconds INT
);
CREATE UNIQUE INDEX executions_executionid_uindex ON public.executions (executionid);

CREATE TABLE public.TestExecutionTreeLeaves
(
  nodeID        BIGINT NOT NULL,
  index         BIGINT NOT NULL,
  executionid BIGINT NOT NULL,
  CONSTRAINT TestExecutionTreeLeaves_nodeID_index_pk PRIMARY KEY (nodeID, index),
  CONSTRAINT TestExecutionTreeLeaves_flavourtesttreeinnernodes_nodeid_fk FOREIGN KEY (nodeID) REFERENCES TestExecutionTreeInnerNodes (nodeid),
  CONSTRAINT TestExecutionTreeLeaves_executions_executionid_fk FOREIGN KEY (executionid) REFERENCES executions (executionid)
);
CREATE INDEX "TestExecutionTreeLeaves_nodeID_index" ON public.TestExecutionTreeLeaves (nodeID);


CREATE TABLE public.flavourToSequence
(
    flavourID BIGINT NOT NULL,
    sequenceID BIGINT NOT NULL,
    sequenceVersionID BIGINT NOT NULL,
    CONSTRAINT flavourToSequence_flavourID_sequenceID_pk PRIMARY KEY (flavourID, sequenceID),
    CONSTRAINT flavourToSequence_flavours_flavourid_fk FOREIGN KEY (flavourID) REFERENCES flavours (flavourid),
    CONSTRAINT flavourToSequence_sequences_sequenceid_fk FOREIGN KEY (sequenceID) REFERENCES sequences (sequenceid),
    CONSTRAINT flavourToSequence_sequenceversions_sequenceversionid_fk FOREIGN KEY (sequenceVersionID) REFERENCES sequenceversions (sequenceversionid)
);


CREATE TABLE public.executionToSequence
(
    executionID BIGINT NOT NULL,
    sequenceID BIGINT NOT NULL,
    sequenceVersionID BIGINT NOT NULL,
    CONSTRAINT executionToSequence_executionID_sequenceID_pk PRIMARY KEY (executionID, sequenceID),
    CONSTRAINT executionToSequence_executions_flavourid_fk FOREIGN KEY (executionID) REFERENCES testexecutions (testexecutionid),
    CONSTRAINT executionToSequence_sequences_sequenceid_fk FOREIGN KEY (sequenceID) REFERENCES sequences (sequenceid),
    CONSTRAINT executionToSequence_sequenceversions_sequenceversionid_fk FOREIGN KEY (sequenceVersionID) REFERENCES sequenceversions (sequenceversionid)
);


CREATE TABLE public.flavourToReferenceImageVersion
(
    flavourID BIGINT NOT NULL,
    referenceImageID BIGINT NOT NULL,
    referenceImageVersionID BIGINT NOT NULL,
    CONSTRAINT flavourToReferenceImageVersion_flavourID_sequenceID_pk PRIMARY KEY (flavourID, referenceImageID),
    CONSTRAINT flavourToReferenceImageVersion_flavours_flavourid_fk FOREIGN KEY (flavourID) REFERENCES flavours (flavourid),
    CONSTRAINT flavourToReferenceImageVersion_referenceImages_referenceImageID_fk FOREIGN KEY (referenceImageID) REFERENCES ReferenceImages (referenceImageID),
    CONSTRAINT flavourToReferenceImageVersion_referenceImageVersions_referenceImageVersionid_fk FOREIGN KEY (referenceImageVersionID) REFERENCES ReferenceImageVersions (referenceImageversionid)
);

CREATE TABLE public.executionToReferenceImageVersion
(
    executionID BIGINT NOT NULL,
    referenceImageID BIGINT NOT NULL,
    referenceImageVersionID BIGINT NOT NULL,
    CONSTRAINT executionToReferenceImageVersion_flavourID_sequenceID_pk PRIMARY KEY (executionID, referenceImageID),
    CONSTRAINT executionToReferenceImageVersion_executions_flavourid_fk FOREIGN KEY (executionID) REFERENCES testexecutions (testexecutionid),
    CONSTRAINT executionToReferenceImageVersion_referenceImages_referenceImageID_fk FOREIGN KEY (referenceImageID) REFERENCES ReferenceImages (referenceImageID),
    CONSTRAINT executionToReferenceImageVersion_referenceImageVersions_referenceImageVersionid_fk FOREIGN KEY (referenceImageVersionID) REFERENCES ReferenceImageVersions (referenceImageversionid)
);

CREATE TABLE public.flavourToTestVersion
(
    flavourID BIGINT NOT NULL,
    testID BIGINT NOT NULL,
    testVersionID BIGINT NOT NULL,
    CONSTRAINT flavourToTestVersion_flavourID_sequenceID_pk PRIMARY KEY (flavourID, testID),
    CONSTRAINT flavourToTestVersion_flavours_flavourid_fk FOREIGN KEY (flavourID) REFERENCES flavours (flavourid),
    CONSTRAINT flavourToTestVersion_tests_testID_fk FOREIGN KEY (testID) REFERENCES tests (testID),
    CONSTRAINT flavourToTestVersion_TestVersions_testVersionID_fk FOREIGN KEY (testVersionID) REFERENCES TestVersions (testVersionID)
);

CREATE TABLE public.execution_to_resultImage
(
    executionid BIGINT NOT NULL,
    resultImageID BIGINT NOT NULL,
    CONSTRAINT execution_to_resultImage_executions_executionid_fk FOREIGN KEY (executionid) REFERENCES executions (executionid),
    CONSTRAINT execution_to_resultImage_resultimages_resultimageid_fk FOREIGN KEY (resultImageID) REFERENCES resultimages (resultimageid)
);
CREATE UNIQUE INDEX "execution_to_resultImage_resultImageID_uindex" ON public.execution_to_resultImage (resultImageID);
CREATE INDEX "execution_to_resultImage_executionid_index" ON public.execution_to_resultImage (executionid);

CREATE TABLE public.storage_resultimage
(
    resultimageId BIGINT PRIMARY KEY NOT NULL,
    "binary" BYTEA NOT NULL,
    CONSTRAINT storage_resultimage_resultimages_resultimageid_fk FOREIGN KEY (resultimageId) REFERENCES resultimages (resultimageid)
);


CREATE TABLE public.storage_referenceimage
(
    referenceimageVerisonId BIGINT PRIMARY KEY NOT NULL,
    "binary" BYTEA NOT NULL,
    CONSTRAINT storage_referenceimage_referenceimageversions_referenceimageversionid_fk FOREIGN KEY (referenceimageVerisonId) REFERENCES referenceimageversions (referenceimageversionid)
);

CREATE TABLE public.storage_testexecutable
(
    testexecutableId BIGINT PRIMARY KEY NOT NULL,
    "binary" BYTEA NOT NULL,
    CONSTRAINT storage_testexecutable_testexecutables_testexecutableid_fk FOREIGN KEY (testexecutableId) REFERENCES testexecutables (testexecutableid)
);