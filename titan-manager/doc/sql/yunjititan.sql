/*
Navicat MySQL Data Transfer

Source Server         : 172.16.0.2
Source Server Version : 50630
Source Host           : 172.16.0.2:3306
Source Database       : yunjititan

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2017-12-14 16:12:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_automatic_task
-- ----------------------------
DROP TABLE IF EXISTS `t_automatic_task`;
CREATE TABLE `t_automatic_task` (
  `automatic_task_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `scene_id` int(12) NOT NULL COMMENT '场景ID',
  `scene_name` varchar(128) NOT NULL COMMENT '场景名称',
  `start_time` time NOT NULL COMMENT '开始时间',
  `pressure_times` int(4) NOT NULL COMMENT '压测次数',
  `create_time` bigint(16) NOT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`automatic_task_id`),
  KEY `IDX_SCENE_ID` (`scene_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='自动压测任务表';

-- ----------------------------
-- Table structure for t_link
-- ----------------------------
DROP TABLE IF EXISTS `t_link`;
CREATE TABLE `t_link` (
  `link_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `link_name` varchar(128) NOT NULL COMMENT '链路名',
  `protocol_type` int(1) NOT NULL DEFAULT '0' COMMENT '协议类型（0：http、1：https）',
  `stresstest_url` varchar(256) NOT NULL COMMENT '压测URL',
  `request_type` int(1) NOT NULL DEFAULT '0' COMMENT '请求类型（0：get:1：post）',
  `content_type` int(2) NOT NULL COMMENT '内容类型',
  `charset_type` int(2) NOT NULL COMMENT '字符编码',
  `testfile_path` varchar(128) NOT NULL COMMENT '压测文件路径',
  `create_time` bigint(16) NOT NULL COMMENT '记录创建时间',
  `modify_time` bigint(16) NOT NULL COMMENT '记录最后修改时间',
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `IDX_LINK_NAME` (`link_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='链路表';

-- ----------------------------
-- Table structure for t_monitor
-- ----------------------------
DROP TABLE IF EXISTS `t_monitor`;
CREATE TABLE `t_monitor` (
  `monitor_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `server_type` int(2) NOT NULL COMMENT '服务机器类型（0：agent机器  1：目标机器）',
  `ip` varchar(64) NOT NULL COMMENT '机器IP',
  `cpu_usage` double(4,2) NOT NULL COMMENT 'CPU使用率',
  `memory_usage` double(4,2) NOT NULL COMMENT '内存使用率',
  `iops` double(4,0) NOT NULL COMMENT '磁盘IOPS',
  `create_time` bigint(16) NOT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`monitor_id`),
  KEY `IDX_IP` (`ip`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='监控机器资源情况表';

-- ----------------------------
-- Table structure for t_monitor_set
-- ----------------------------
DROP TABLE IF EXISTS `t_monitor_set`;
CREATE TABLE `t_monitor_set` (
  `monitor_set_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `scene_id` bigint(12) NOT NULL COMMENT '场景ID',
  `scene_name` varchar(128) NOT NULL COMMENT '场景名称',
  `intranet_ip` varchar(64) NOT NULL COMMENT '内网IP',
  `create_time` bigint(16) NOT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`monitor_set_id`),
  UNIQUE KEY `IDX_SCENE_NAME` (`scene_name`) USING BTREE,
  UNIQUE KEY `IDX_SCENE_ID` (`scene_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='监控集表';

-- ----------------------------
-- Table structure for t_report
-- ----------------------------
DROP TABLE IF EXISTS `t_report`;
CREATE TABLE `t_report` (
  `report_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `report_name` varchar(128) NOT NULL COMMENT '报告名称',
  `scene_id` int(12) NOT NULL COMMENT '场景ID',
  `scene_name` varchar(128) NOT NULL COMMENT '场景名称',
  `start_time` bigint(16) NOT NULL COMMENT '测试起始时间',
  `end_time` bigint(16) NOT NULL COMMENT '测试结束时间',
  `expect_tps` int(12) NOT NULL DEFAULT '0' COMMENT '期待吞吐量',
  `actual_tps` int(12) NOT NULL DEFAULT '0' COMMENT '实际吞吐量',
  `total_request` bigint(16) NOT NULL COMMENT '总并发请求数',
  `success_request` bigint(16) NOT NULL DEFAULT '0' COMMENT 'HTTP200成功请求数',
  `business_success_request` bigint(16) NOT NULL DEFAULT '0' COMMENT '业务成功请求数',
  `concurrent_user` int(12) NOT NULL COMMENT '并发用户数',
  `user_waittime` bigint(16) NOT NULL COMMENT '用户平均请求等待时间（单位：毫秒）',
  `server_waittime` bigint(16) NOT NULL COMMENT '服务器平均请求等待时间（单位：毫秒）',
  `conclusion` int(2) NOT NULL COMMENT '测试报告结论（0：合格 1：不合格）',
  `create_time` bigint(16) NOT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`report_id`),
  KEY `IDX_REPORT_NAME` (`report_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='测试报告表';

-- ----------------------------
-- Table structure for t_scene
-- ----------------------------
DROP TABLE IF EXISTS `t_scene`;
CREATE TABLE `t_scene` (
  `scene_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `scene_name` varchar(128) NOT NULL COMMENT '场景名称',
  `duration_hour` int(2) NOT NULL COMMENT '持续时间-时',
  `duration_min` int(2) NOT NULL COMMENT '持续时间-分',
  `duration_sec` int(2) NOT NULL COMMENT '持续时间-秒',
  `concurrent_user` int(12) NOT NULL DEFAULT '0' COMMENT '并发用户数',
  `concurrent_start` int(12) NOT NULL COMMENT '起步并发用户数',
  `total_request` bigint(16) NOT NULL DEFAULT '0' COMMENT '总并发请求数',
  `expect_tps` int(12) NOT NULL DEFAULT '0' COMMENT '期待吞吐量',
  `contain_linkid` varchar(256) NOT NULL COMMENT '包含的链路ID，多个链路ID以英文","隔开',
  `link_relation` varchar(256) NOT NULL COMMENT '串行链路关系，按链路顺序，链路ID以英文","隔开',
  `use_agent` int(8) NOT NULL DEFAULT '0' COMMENT '使用的agent数',
  `scene_status` int(2) NOT NULL DEFAULT '0' COMMENT '状态（0：未开始 ，1：压测进行中，2：停止压测中）',
  `create_time` bigint(16) NOT NULL COMMENT '记录创建时间',
  `modify_time` bigint(16) NOT NULL COMMENT '记录最后修改时间',
  PRIMARY KEY (`scene_id`),
  UNIQUE KEY `IDX_SCENE_NAME` (`scene_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='场景表';
