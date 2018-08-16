CREATE TABLE cse_machine (
  id NUMBER(11) PRIMARY KEY,
  object_id CHAR(32) NULL,
  status NUMBER(1) NULL,
  name VARCHAR2(255) NULL,
  machine_no varchar2(255) NULL,
  patent VARCHAR2(255) NULL,
  structure_no VARCHAR2(255) NULL,
  ship_no VARCHAR2(255) NULL,
  type VARCHAR2(255) NULL,
  cylinder_amount VARCHAR2(255) NULL,
  classification_society VARCHAR2(255) NULL
);

CREATE TABLE cse_structure (
  id NUMBER(11) PRIMARY KEY,
  object_id CHAR(32) NULL,
  status NUMBER(1) NULL,
  machine_name VARCHAR2(255) NULL,
  structure_no VARCHAR2(255) NULL,
  material_no VARCHAR2(255) NULL,
  revision VARCHAR2(255) NULL,
  version NUMBER(10) NULL,
  amount NUMBER(10) NULL
);

CREATE TABLE cse_material (
  id NUMBER(11) PRIMARY KEY,
  object_id CHAR(32) NULL,
  status NUMBER(1) NULL,
  name VARCHAR2(255) NULL,
  parent_id CHAR(32) NULL,
  position NUMBER(11) NULL,
  patent NUMBER(11) NULL,
  src_level VARCHAR2(255) NULL,
  structure_no VARCHAR2(255) NULL,
  position_no VARCHAR2(255) NULL,
  sequence_no VARCHAR2(255) NULL,
  revision VARCHAR2(255) NULL,
  version NUMBER(11) NULL,
  latest_version NUMBER(11) NULL,
  material_no VARCHAR2(255) NULL,
  material_version VARCHAR2(255) NULL,
  material VARCHAR2(255) NULL,
  material_jis VARCHAR2(255) NULL,
  material_win VARCHAR2(255) NULL,
  drawing_no VARCHAR2(255) NULL,
  drawing_size VARCHAR2(255) NULL,
  drawing_version VARCHAR2(255) NULL,
  weight VARCHAR2(255) NULL,
  amount VARCHAR2(255) NULL,
  absolute_amount VARCHAR2(255) NULL,
  modify_note VARCHAR2(255) NULL,
  page VARCHAR2(255) NULL,
  child_count NUMBER(11) NULL,
  at_no VARCHAR2(255) NULL,
  at_revision VARCHAR2(255) NULL,
  chinese VARCHAR2(255) NULL,
  spare_exp VARCHAR2(255) NULL,
  type number(1) NULL
);

CREATE TABLE cse_name (
  id NUMBER(11) PRIMARY KEY,
  object_id CHAR(32) NULL,
  english VARCHAR2(255) NULL,
  chinese VARCHAR2(255) NULL
);

CREATE SEQUENCE CMB_CHINA_CITYS_ID
  MINVALUE 1
  NOMAXVALUE
  START WITH 1
  INCREMENT BY 1
  NOCYCLE
  NOCACHE;

