CREATE TABLE `cse_machine` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `object_id` CHAR(32),
  `status` INT(1),
  `name` VARCHAR(255),
  `machine_no` varchar(255),
  `ship_no` varchar(255),
  `type` varchar(255),
  `cylinder_amount` varchar(255),
  `classification_society` varchar(255),
  PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE `cse_structure` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `object_id` CHAR(32),
  `status` INT(1),
  `machine_name` VARCHAR(255),
  `structure_no` varchar(255),
  `material_no` VARCHAR(255),
  `version` int(11),
  `amount` int(11),
  PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE `cse_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `object_id` char(32),
  `status` int(1),

  `level` int(11),
  `position_no` varchar(255),
  `material_no` varchar(255),
  `drawing_no` varchar(255),
  `drawing_size` varchar(255),
  `name` varchar(255),
  `chinese` varchar(255),
  `material` varchar(255),
  `standard` varchar(255),
  `absolute_amount` int(11),
  `source` varchar(255),
  `weight` varchar(255),
  `spareSrc` varchar(255),
  `design_note` varchar(255),
  `paint_protect` varchar(255),
  `modify_note` varchar(255),
  `erp_parent` varchar(255),

  `sequence_no` varchar(255),
  `page` varchar(255),
  `spare_exp` VARCHAR(255),
  `parent_id` char(32),
  `at_no` VARCHAR(255),
  `version` int(11),
  `latest_version` int(11),
  `child_count` int(11),
  PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE `cse_name` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `object_id` CHAR(32),
  `english` VARCHAR(255),
  `chinese` varchar(255),
  PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE `cse_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `object_id` CHAR(32),
  `status` INT(1),
  `name` VARCHAR(32),
  `username` varchar(32),
  `password` varchar(32),
  PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE `cse_structure_note` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `object_id` CHAR(32),
  `status` INT(1),
  `create_at` DATETIME,
  `update_at` DATETIME,
  `organizer` VARCHAR(16),
  `proofreader` varchar(16),
  `auditor` varchar(16),
  `materialNo` varchar(32),
  `version` INT(2),
  `note` varchar(255),
  PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE `cse_structure_feature` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `object_id` CHAR(32),
  `status` INT(1),
  `create_at` DATETIME,
  `update_at` DATETIME,
  `efficiency` varchar(32),
  `rotate_rate` varchar(32),
  `debug_mode` varchar(32),
  `cylinder_amount` varchar(8),
  `supercharger_type` varchar(32),
  `ice_area_enhance` varchar(32),
  `supercharger_arrange` varchar(32),
  `exhaust_back_pressure` varchar(32),
  `host_rotate_direction` varchar(32),
  `propeller_type` varchar(32),
  `host_electric` varchar(32),
  `heating_medium` varchar(32),
  `top_support_mode` varchar(32),
  `free_end_sec_compensator` varchar(32),
  `out_end_sec_compensator` varchar(32),
  `stem_material` varchar(64),
  `fiva_valve_manufacturer` varchar(64),
  `electric_start_pump_manufacturer` varchar(64),
  `hydraulic_pump_manufacturer` varchar(64),
  `cylinder_fuel_injector_manufacturer` varchar(64),
  `egb` varchar(32),
  `torsional_shock_absorber` varchar(32),
  `scavenger_fire_ext_method` varchar(32),
  `hydraulic_oil_filter_manufacturer` varchar(64),
  `remote_control_manufacturer` varchar(64),
  `pmi_sensor_manufacturer` varchar(64),
  `oil_mist_detector_manufacturer` varchar(64),
  `pto` varchar(32),
  `lift_method` varchar(32),
  `scr` varchar(32),
  `exhaust_valve_grinder` varchar(32),
  `exhaust_valve_workbench` varchar(32),
  `universal` varchar(8),
  `pending` varchar(8),
  `fuel_oil_sulphur_content` varchar(8),
  `machine_type` varchar(32),
  `material_id` int(11) not null,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_CSE_STRUCTURE_FEATURE_MATERIAL_ID` (`material_id`),
  CONSTRAINT FK_CSE_STRUCTURE_FEATURE_MATERIAL_ID_REFERENCE
  FOREIGN KEY(material_id) REFERENCES CSE_MATERIAL(id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;