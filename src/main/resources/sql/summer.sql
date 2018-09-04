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