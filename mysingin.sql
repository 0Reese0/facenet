/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : mysingin

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 04/07/2022 21:01:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meeting
-- ----------------------------
DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting`  (
  `m_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会议ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会议主题',
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会议创建人',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '会议创建时间',
  `state` int(11) NULL DEFAULT NULL COMMENT '会议处于状态;0-未开始；1-进行中；3已结束；',
  `start_time` bigint(15) NULL DEFAULT NULL COMMENT '会议开始时间',
  `end_time` bigint(15) NULL DEFAULT NULL COMMENT '会议结束时间',
  PRIMARY KEY (`m_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会议表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for meeting_history
-- ----------------------------
DROP TABLE IF EXISTS `meeting_history`;
CREATE TABLE `meeting_history`  (
  `m_h_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会议记录ID',
  `m_id` int(11) NULL DEFAULT NULL COMMENT '会议ID',
  `u_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `reg_time` bigint(15) NULL DEFAULT NULL COMMENT '用户会议签到的时间',
  `mod` int(1) NULL DEFAULT NULL COMMENT '用户签到的方式（0未签到，1人脸签到，2手动补签）',
  PRIMARY KEY (`m_h_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会议记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `u_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户(手机号)',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户密码',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `face_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '百度人脸ID',
  `sex` int(11) NULL DEFAULT NULL COMMENT '用户性别',
  `age` int(11) NULL DEFAULT NULL COMMENT '用户年龄',
  `avatar` varchar(180) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户住址',
  `create_time` bigint(15) NULL DEFAULT NULL COMMENT '用户注册时间',
  `change_time` bigint(15) NULL DEFAULT NULL COMMENT '用户信息更新时间',
  `admin` int(11) NULL DEFAULT NULL COMMENT '是否为管理员',
  `audit` int(11) NULL DEFAULT NULL COMMENT '审核是否通过',
  PRIMARY KEY (`u_id`, `phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
