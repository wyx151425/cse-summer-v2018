CREATE TABLE `cse_machine` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `object_id` CHAR(32),
  `status` INT(1),
  `name` VARCHAR(255),
  PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE `cse_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `object_id` char(32),
  `status` int(1),
  `name` varchar(255),
  `machine_name` varchar(255),
  `parent_id` char(32),
  `level` int(11),
  `type` int(11),
  `src_level` varchar(255),
  `structure_no` varchar(255),
  `position_no` varchar(255),
  `sequence_no` varchar(255),
  `revision` varchar(255),
  `version` int(11),
  `latest_version` int(11),
  `material_no` varchar(255),
  `material_version` varchar(255),
  `material` varchar(255),
  `material_jis` varchar(255),
  `material_win` varchar(255),
  `drawing_no` varchar(255),
  `drawing_size` varchar(255),
  `drawing_version` varchar(255),
  `weight` varchar(255),
  `amount` varchar(255),
  `absolute_amount` varchar(255),
  `modify_note` varchar(255),
  `page` varchar(255),
  `child_count` int(11),
  `active` boolean,
  PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

ALTER TABLE cse_machine ADD `type` varchar(255);