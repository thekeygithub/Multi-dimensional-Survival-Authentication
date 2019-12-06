drop database if exists gzyldb;
create database gzyldb;
use gzyldb;

DROP TABLE IF EXISTS tbl_user_status;
CREATE TABLE `tbl_user_status` (
  `user_id` varchar(20) NOT NULL COMMENT '身份证号作为主键',
  `point_s` tinyint(1) unsigned DEFAULT NULL COMMENT '验证分数S',
  `point_t` tinyint(1) unsigned DEFAULT NULL COMMENT '验证分数T',
  `user_status` tinyint(1) DEFAULT '1' COMMENT '生存状态ID',
  `status_desc` tinyint(1) DEFAULT NULL COMMENT '状态说明',
  `alive_data_src` varchar(10) DEFAULT NULL COMMENT '用减号隔开的生存状态数据源id',
  `note` varchar(100) DEFAULT NULL COMMENT '说明',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tbl_death_data_src;
CREATE TABLE `tbl_death_data_src` (
  `user_id` varchar(20) NOT NULL COMMENT '用户身份证号',
  `death_time` datetime DEFAULT NULL COMMENT '数据源传回的死亡时间',
  `data_src` char(2) DEFAULT NULL COMMENT '数据源id',
  `data_id` char(2) DEFAULT NULL COMMENT '数据id',
  `note` varchar(100) DEFAULT NULL COMMENT '死亡原因',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
