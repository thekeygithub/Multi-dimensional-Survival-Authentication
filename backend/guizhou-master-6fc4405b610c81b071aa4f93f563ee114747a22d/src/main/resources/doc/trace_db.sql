/*
 Navicat Premium Data Transfer

 Source Server         : 60.205.213.254
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 60.205.213.254:3306
 Source Schema         : trace_db

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 15/07/2019 10:21:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for YYS_date
-- ----------------------------
DROP TABLE IF EXISTS `YYS_date`;
CREATE TABLE `YYS_date`  (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `userName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'name',
  `userIdentity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `telNumber` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `factor3Result` int(2) NULL DEFAULT NULL COMMENT '三要素一致性验证结果',
  `result` int(2) NULL DEFAULT NULL COMMENT '在网状态验证结果',
  `monthId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of YYS_date
-- ----------------------------
INSERT INTO `YYS_date` VALUES (1, 'cj', '445281199412162354', '18811226699', 0, 1, NULL);

-- ----------------------------
-- Table structure for traceInfo
-- ----------------------------
DROP TABLE IF EXISTS `traceInfo`;
CREATE TABLE `traceInfo`  (
  `departmentId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `departmentName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` int(2) NULL DEFAULT NULL,
  `eventTime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dataHash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dataOwns` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `txId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dataType` int(2) NULL DEFAULT NULL,
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `isdelete` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1111371 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of traceInfo
-- ----------------------------
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-04-30 15:34:20', 'zdpuB3M6pdyi6aWPKSCoFktEeoJ43oLF9vGoEtLVuW8FXcMfB', '002', 'c432e0a9225a291e477facdeace42f8594ec79c397637d518fedbb96eaeddda9', 0, 1111113, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-04-30 15:54:31', 'zdpuB1kaJ5SEAdTcj5Rdko5zo6KBkdt5AsACypvcjMFgTNaTj', '002', '1edfe7072babe4b597f637caa78c4cc168964c80bc3bcfa64698836dae22b754', 0, 1111114, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-04-30 15:55:06', 'zdpuB1kaJ5SEAdTcj5Rdko5zo6KBkdt5AsACypvcjMFgTNaTj', '002', '', 0, 1111115, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-04-30 15:57:36', 'zdpuB1kaJ5SEAdTcj5Rdko5zo6KBkdt5AsACypvcjMFgTNaTj', '002', '', 0, 1111116, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-04-30 15:57:45', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '002', 'd729ab6b129c009acaa4de2ad4f9578ab406b8ea6d03a661882c9b25fac0be69', 0, 1111117, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-04-30 16:08:32', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '002', '', 0, 1111118, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-04-30 16:18:17', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '002', '', 0, 1111119, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-04-30 16:33:37', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '002', 'c38f4ab7acec6c731bbfc0f6c9d256bf58e53282fef82fac6caf2722f6eb6558', 0, 1111120, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-04-30 16:34:12', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '002', '', 0, 1111121, '0');
INSERT INTO `traceInfo` VALUES ('001', '医保局', 0, '2019-05-07 11:36:59', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '001', 'c38f4ab7acec6c731bbfc0f6c9d256bf58e53282fef82fac6caf2722f6eb6558', 0, 1111122, '0');
INSERT INTO `traceInfo` VALUES ('001', '医保局', 1, '2019-05-07 11:36:59', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '001', NULL, 0, 1111123, '0');
INSERT INTO `traceInfo` VALUES ('003', '民政局', 0, '2019-05-07 11:36:5916:34:12', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '003', 'c38f4ab7acec6c731bbfc0f6c9d256bf58e53282fef82fac6caf2722f6eb6558', 0, 1111124, '0');
INSERT INTO `traceInfo` VALUES ('003', '民政局', 1, '2019-05-07 11:36:59', 'zdpuB3EgGTe3BQFF653eTTYu2iiVShfNd2wbmy9WrR45vpRv6', '003', NULL, 0, 1111125, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-07 11:36:59', 'zdpuAofKvjyFWXUQP5GNePQbaHSKd6rCQpVc56r4Vyqa64ZQi', '002', '1b184de62f38781e7de988bc98a3e5190a6074348ef2a33c3700a11c06139e26', 0, 1111126, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-07 11:37:47', 'zdpuAofKvjyFWXUQP5GNePQbaHSKd6rCQpVc56r4Vyqa64ZQi', '002', '', 0, 1111127, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-07 11:41:25', 'zdpuAofKvjyFWXUQP5GNePQbaHSKd6rCQpVc56r4Vyqa64ZQi', '002', '', 0, 1111128, '0');
INSERT INTO `traceInfo` VALUES ('004', '公安局', 0, '2019-05-07 11:41:25', 'zdpuAofKvjyFWXUQP5GNePQbaHSKd6rCQpVc56r4Vyqa64ZQi', '004', '1b184de62f38781e7de988bc98a3e5190a6074348ef2a33c3700a11c06139e26', 0, 1111129, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:04:15', 'zdpuAy8Dyb4H5ozXZUHKHkzFkNh465AVWFELcxZdh1s5DAcWT', '002', '03ecd1a329468f0dce419e2a869d9bf7a0659cc9506277fe942cc09f47909dc8', 0, 1111130, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:05:37', 'zdpuAy8Dyb4H5ozXZUHKHkzFkNh465AVWFELcxZdh1s5DAcWT', '002', '', 0, 1111131, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:11:02', 'zdpuAofKvjyFWXUQP5GNePQbaHSKd6rCQpVc56r4Vyqa64ZQi', '002', '3579e306bedcea4e9e2c3f0d1cfb3a5e5d80b34c571a0bc2dd60057de5a57e2a', 0, 1111132, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:11:38', 'zdpuAofKvjyFWXUQP5GNePQbaHSKd6rCQpVc56r4Vyqa64ZQi', '002', '', 0, 1111133, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:17:54', 'zdpuAymDQn33T8sHqstjVdUn5fxcdPWoZ2F2HcjSDs6s5KR1j', '002', '54e1e4df11429a75182aed75331fe0a25ea34eef6a168f0224ab9230c4ca8c08', 0, 1111134, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:18:28', 'zdpuAymDQn33T8sHqstjVdUn5fxcdPWoZ2F2HcjSDs6s5KR1j', '002', '', 0, 1111135, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:19:09', 'zdpuAxKCBsAKQpEw456S49oVDkWJ9PZa44KGRfVBWHiXN3UH8', '002', '9c3021d244bef1a12fb7cfb60fd0960d0604175aff404ccba0817e3395c27224', 0, 1111136, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:21:00', 'zdpuAxKCBsAKQpEw456S49oVDkWJ9PZa44KGRfVBWHiXN3UH8', '002', '5a87e6dc0831c1437c5bf23817eb0e0f545586aa8495a0f9f06e504c04976e66', 0, 1111137, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:21:22', 'zdpuAxKCBsAKQpEw456S49oVDkWJ9PZa44KGRfVBWHiXN3UH8', '002', 'bab61ee7579f8e503f38cfdc2c6dce4551af093957fb43ce805a4c82c0a2952b', 0, 1111138, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:21:35', 'zdpuAxKCBsAKQpEw456S49oVDkWJ9PZa44KGRfVBWHiXN3UH8', '002', '9546ad1951759fc7f410857cded021a40d1b46edd4320e1a30cd008e3869ba7d', 0, 1111139, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:21:53', 'zdpuAxKCBsAKQpEw456S49oVDkWJ9PZa44KGRfVBWHiXN3UH8', '002', '89aeb86c2d4cca6ec2391dcfe4b9cebe634aca0911f08601bc0498c351a47d9a', 0, 1111140, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:22:00', 'zdpuAxKCBsAKQpEw456S49oVDkWJ9PZa44KGRfVBWHiXN3UH8', '002', 'eaff8f45b6ca31842f8dd5f4be99f58e4f48fc196e1e456a1db019ec2b81f3a8', 0, 1111141, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:22:48', 'zdpuAxKCBsAKQpEw456S49oVDkWJ9PZa44KGRfVBWHiXN3UH8', '002', 'b058836b6a60de215f95da054d69f2396ad963456b176cbdf623a0caed44197d', 0, 1111142, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:26:32', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '002', '67557c8dc1f159117ce5a56f33ad01832fcb2f9d8f2b50586ef03ecdf7db31d3', 0, 1111143, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:27:12', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111144, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:27:20', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111145, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:27:23', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111146, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:28:23', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111147, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:29:04', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111148, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:30:00', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111149, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:30:50', 'zdpuAzULNwWf1noMGyJp3S4uiRoNxcJMsfRqcPnwpM9mG2suA', '002', 'f507cf8c1475686f1e2296f2633e47fbd8c69c66ca20eba3e4777b9659998848', 0, 1111150, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:31:08', 'zdpuAzULNwWf1noMGyJp3S4uiRoNxcJMsfRqcPnwpM9mG2suA', '003', '', 0, 1111151, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:41:19', 'zdpuAzyYEhygFKxrjjyWz9YEpVYg29iMQFH1RsTG8RCwoG76w', '002', 'ef72067e498b7096048befe4c08de7919c8cd7f33958ba8d2bcc716af6975552', 0, 1111152, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:41:34', 'zdpuAzyYEhygFKxrjjyWz9YEpVYg29iMQFH1RsTG8RCwoG76w', '002', '', 0, 1111153, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:45:21', 'zdpuAzyYEhygFKxrjjyWz9YEpVYg29iMQFH1RsTG8RCwoG76w', '002', '', 0, 1111154, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:45:47', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111155, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:46:05', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111156, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:46:50', 'zdpuAzULNwWf1noMGyJp3S4uiRoNxcJMsfRqcPnwpM9mG2suA', '003', '', 0, 1111157, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:49:39', 'zdpuAtpkWvqp7n9zNw7jzyCN6hLN58jjGb2tMRTaJmPZHv2FQ', '002', '3f2f0d6f70a9eec5cc18043a50ae3d1f604c2f30d4c08ba6436813da629bd185', 0, 1111158, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:50:35', 'zdpuAtpkWvqp7n9zNw7jzyCN6hLN58jjGb2tMRTaJmPZHv2FQ', '002', '', 0, 1111159, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:51:05', 'zdpuAtpkWvqp7n9zNw7jzyCN6hLN58jjGb2tMRTaJmPZHv2FQ', '002', '', 0, 1111160, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 10:51:52', 'zdpuAwa5VYi96uSAeKm6pxb1dXxJ1v4eKx6xTxakdoty6Bc7Z', '002', '1629fb38270e43b2619a5f4afbf7e8e1df817bddb0ebccced1519225304aef13', 0, 1111161, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:52:12', 'zdpuAwa5VYi96uSAeKm6pxb1dXxJ1v4eKx6xTxakdoty6Bc7Z', '002', '', 0, 1111162, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 10:56:21', 'zdpuAwa5VYi96uSAeKm6pxb1dXxJ1v4eKx6xTxakdoty6Bc7Z', '002', '', 0, 1111163, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:07:31', 'zdpuAwa5VYi96uSAeKm6pxb1dXxJ1v4eKx6xTxakdoty6Bc7Z', '002', '', 0, 1111164, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 11:09:05', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', 'a6873e1da1d867c7308d83629f8d6d4d76a30bc397b0a88778153c39092ed4f1', 0, 1111165, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:10:31', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111166, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:10:40', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111167, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:10:44', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111168, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:10:53', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111169, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:11:00', 'zdpuAzULNwWf1noMGyJp3S4uiRoNxcJMsfRqcPnwpM9mG2suA', '003', '', 0, 1111170, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:11:15', 'zdpuAzULNwWf1noMGyJp3S4uiRoNxcJMsfRqcPnwpM9mG2suA', '003', '', 0, 1111171, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:11:20', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111172, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:12:54', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111173, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:12:58', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111174, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:13:01', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111175, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:13:04', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111176, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:13:07', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111177, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:13:38', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111178, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:13:46', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111179, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 11:22:49', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '002', 'e885b4881f282795c69a35a66c65d2a09f15ddd819395f106843348bed02d74c', 0, 1111180, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:23:09', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111181, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:24:58', 'zdpuAmpeW9tDdYA3QLHXbH3rxzFUAtSeQz4PVdjEus1SVjxjz', '002', '', 0, 1111182, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 11:25:12', 'zdpuAwC6ChwtNRJrx2k6xENormZW9YMccmB7aBVMQVFyAwg25', '002', '7840b6a99f245028b486ecb0a52d02b5875934a876aa951dd146dc120a76e0ca', 0, 1111183, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 11:25:25', 'zdpuAwC6ChwtNRJrx2k6xENormZW9YMccmB7aBVMQVFyAwg25', '002', '', 0, 1111184, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 13:45:31', 'zdpuAo9vpdXsb4gU2yE7me7rFZ6Q2w2FVp52yanDJLdz29kYe', '002', '7e5cef3ecd8169d2ec00e326b19ee2f8718f376c0030c23e683c537deaf9ff1a', 0, 1111185, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 13:46:32', 'zdpuAo9vpdXsb4gU2yE7me7rFZ6Q2w2FVp52yanDJLdz29kYe', '002', '', 0, 1111186, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 13:48:56', 'zdpuAzULNwWf1noMGyJp3S4uiRoNxcJMsfRqcPnwpM9mG2suA', '003', '', 0, 1111187, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 13:56:49', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111188, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 13:57:22', 'zdpuAzQL9f3ydMy7bEANkzhu6TciiJ7DnJaDTiFEg9r48yA9A', '001', '', 0, 1111189, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 14:15:01', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '002', 'bf1aa3e57169bfdeac021a206f10ab3d2eafc35e9ddbc83ebdedcd9594d4345b', 0, 1111190, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 14:15:21', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111191, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 14:16:44', 'zdpuAtknfsNYTKpbPMc68A11MDxU9cw1REHBvwyBUvSJuEBYY', '002', '944a642c9a94745347f5bf2d8955dddefe1243d780f8a55a9cda6aba9918fc56', 0, 1111192, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 14:17:07', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '002', 'e4b41f9d859a865aae92a4ea1cad00e4e84abb41835625d88a29556be4e9f3cb', 0, 1111193, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 14:17:18', 'zdpuAtknfsNYTKpbPMc68A11MDxU9cw1REHBvwyBUvSJuEBYY', '002', '', 0, 1111194, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 14:17:18', 'zdpuAxKCBsAKQpEw456S49oVDkWJ9PZa44KGRfVBWHiXN3UH8', '002', '7001003c83281716f3de8d7d63d552a9042bc4f9e702347329f94ce23837a88c', 0, 1111195, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 14:17:25', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111196, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 14:17:32', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111197, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 14:17:46', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111198, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:36:47', 'zdpuAzHwafix4ChTFu8od2v1xdT5eNBME95Xi7Ru6BgtTw83L', '002', 'aa99cde3b2fe22693916f1507c9a3765aa37e2fb7ea4b4fe27648da3d36b8f07', 0, 1111199, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:39:08', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '9c13a177b298ecb71469c3c6cc8d5dab27d255e4613d74363292df070e10e6b5', 0, 1111200, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:40:55', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', 'eb8720f382d2daeba7089c0aeccf320b7331bd06d914b6c332c9b554b5a702b3', 0, 1111201, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:41:02', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '2e4ba89401e08a9a66615a27ef0468266ea95ae139ea60e56f4a0e9cce153110', 0, 1111202, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:43:33', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '751ac4885910fe93e646d337f1068dc9cc288e7230bc255aa9ea2d8c4a1779ea', 0, 1111203, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:43:43', 'zdpuB2uAQCRWZsk95VmL2nALRGJgbg86PUFrGPyYV5ayLFVy6', '002', 'b4e2027085bafa328634f1bfbc81c25a48dfc2c53423206aaa1cf8c088b70076', 0, 1111204, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:43:50', 'zdpuAq8bq3qE2KvuWa1sdv8SVLojtYJPeskCr1h9rGoZSwabC', '002', '28169d17fab6f689c7941ea38852d43b9805424ddf618b37ea7cb33db3a77fdd', 0, 1111205, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:43:56', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', 'b4bf192d38c5204928387638d3f26a13d0ad0c31d4c97d7b91a5fed3cae0b8b3', 0, 1111206, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:44:06', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '2f6a0b6ca7b8377f35ada2e98f43aa3274211042886c6fc59ebcf44babfb32ac', 0, 1111207, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:44:32', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '005604ff4aec9d92c9fb6aa49a940875ae782fb5200b482d9e2abbdc0838734e', 0, 1111208, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-09 16:44:38', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '98f7cd86fb6164dea1bd7db23aafbe724c605081f05f55289cad10d0e8bf60a6', 0, 1111209, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 17:44:05', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111210, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 17:45:02', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111211, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 17:48:23', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111212, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 18:07:33', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111213, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 18:07:40', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111214, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 18:09:48', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111215, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 18:11:08', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111216, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 18:12:40', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111217, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 18:12:41', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111218, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 18:12:42', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111219, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-09 18:12:46', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111220, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:27:19', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '60cf2086ec1a6b558eb44038b0ae65d257b2a674d7c1efe503e8f42ad94777f8', 0, 1111221, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:28:07', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', 'ad9c2de8e5c56220038f335d962b352ae3715f45f9a52e9e149719db65662e51', 0, 1111222, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:30:22', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '6053428c9c5057d7d1b359c8932a0f17ec0836c1e143c98b1d82d90b96605675', 0, 1111223, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:33:11', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111224, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:33:31', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111225, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:34:27', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '14f3c60bf1f1f4600eae7db1c480cfb3a798e333ea347ea8ce058a57efc52c41', 0, 1111226, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:34:34', 'zdpuB2uAQCRWZsk95VmL2nALRGJgbg86PUFrGPyYV5ayLFVy6', '002', '4a15e42ba2c3bffbb5ccab0e549281538e4ea3252165483b93ac158462906573', 0, 1111227, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:34:38', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111228, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:34:40', 'zdpuAq8bq3qE2KvuWa1sdv8SVLojtYJPeskCr1h9rGoZSwabC', '002', '5a08c8db4ec950d50ebff8f5addf7f14b2091bc7dca96452d57ad57cb774b581', 0, 1111229, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:34:47', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', 'fba8c08b322f1c8eadb80039532da78fe1ceb36702fa3d84c49079f5bc143619', 0, 1111230, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:36:13', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '', 0, 1111231, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:36:26', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '', 0, 1111232, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:36:29', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '', 0, 1111233, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:37:38', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '13d85b4cf3ced6c851f12fdaef57b3bf5b4cafc42459c6fe1700de9b75b1b0e2', 0, 1111234, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:38:01', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111235, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:38:02', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111236, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:38:07', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111237, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:42:32', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111238, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:42:36', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111239, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:42:43', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111240, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:44:52', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', 'dbdac91e4fa850fcfdcd8239ec51661d4f9263b05bb2023c420f1f5d812a5bb8', 0, 1111241, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:46:23', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '', 0, 1111242, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:46:30', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111243, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:46:41', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111244, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:47:04', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '78f65f2405be9fb5ce9c3fed308e3d15bca7182c8a99fe6eb12cd6df6fc6be47', 0, 1111245, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:47:34', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111246, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:47:41', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111247, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:47:49', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111248, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:47:53', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111249, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:47:58', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111250, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:48:09', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111251, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:48:54', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '5d3f706bb7f07c226ffb5d16f5f19069a7fcfa2bf989a6616f2f6501c61b4b21', 0, 1111252, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:49:14', 'zdpuAq8bq3qE2KvuWa1sdv8SVLojtYJPeskCr1h9rGoZSwabC', '002', '66539281b1e9a735a0c4d3c8d7c50103d4c6be59e1ef76feed74d5b6b325b868', 0, 1111253, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:49:20', 'zdpuB2uAQCRWZsk95VmL2nALRGJgbg86PUFrGPyYV5ayLFVy6', '002', 'b285ff358d73a08304b2625ac831e1af5a6e3a99a89a2f41a65060ef7d06f446', 0, 1111254, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:49:27', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', 'f8f5f14b428713d7a3a26f259fe9ddca82dc1f6b3d476e1661d4102eb8074307', 0, 1111255, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:49:32', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '86e22025970264de9753251d686f4a904683cdee9a2091587d0b95a4269c3e9e', 0, 1111256, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:49:46', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '', 0, 1111257, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:49:50', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111258, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:49:53', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '', 0, 1111259, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:49:56', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111260, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:50:05', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111261, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:57:22', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111262, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:57:29', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111263, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 09:57:32', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '', 0, 1111264, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:57:45', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', 'd960c245307aa8e2bcfb2b561e444cefad607bd13637e7f9397ce627558b12e7', 0, 1111265, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 09:58:00', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '0893172d9e586edea593e98bde37b7eedaa6a89206e7552caf85488a289e09eb', 0, 1111266, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 10:08:07', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '4683b891b7d110627da38652c8d04b2d4e11d4217861e4bc06c917f84efb1454', 0, 1111267, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 10:08:49', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111268, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 10:10:11', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111269, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 10:10:25', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '69d2d534783b9890d3505acb4cab0d416083fa1846464ba44bdc9bd9b737619a', 0, 1111270, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 10:29:05', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111271, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 10:35:01', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111272, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 10:35:48', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111273, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 10:37:01', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111274, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 10:37:13', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111275, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 12:58:24', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111276, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 16:27:24', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111277, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 17:45:52', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111278, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:46:00', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', 'f55fee153213817b5568afc5b2c1b706891cca3378f7e398b0200bba3176c1c8', 0, 1111279, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 17:46:08', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111280, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 17:46:12', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111281, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 17:46:21', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111282, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 17:46:44', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111283, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-10 17:47:23', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111284, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:47:28', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '5e5e9f22a1d65f394216872ff787eef984dc737d63ac80a90d8b7d57ff391e04', 0, 1111285, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:47:35', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '45b7f928741dace6ba2eff83525a8c2180388ea21f86b9316cd9e6af7d7281ee', 0, 1111286, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:47:44', 'zdpuAq8bq3qE2KvuWa1sdv8SVLojtYJPeskCr1h9rGoZSwabC', '002', '9257a6f94a0d479d73fa9c32a03d69b3614f8e3aabefb482fa37b829cb2aa15d', 0, 1111287, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:47:49', 'zdpuB2uAQCRWZsk95VmL2nALRGJgbg86PUFrGPyYV5ayLFVy6', '002', 'dc19b80abf7d1e85edd5544cfb0866d333bfefe07aa32a2fd04d40861addffed', 0, 1111288, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:47:56', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', 'e0211b3019d9226f9d98b2259b2061d2b2dbe4f2c469ccdc085e5cc31cd13a0f', 0, 1111289, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:48:06', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '209f2fbe50b33c19d3c6f48ecd7bdceca18a8c5c798c598cfedc72beef726f39', 0, 1111290, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:48:13', 'zdpuAq8bq3qE2KvuWa1sdv8SVLojtYJPeskCr1h9rGoZSwabC', '002', 'ede2035abdde39470d5aa562a28face91d9f3d50fc118a8a715cf8205c87b4fb', 0, 1111291, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:48:19', 'zdpuB2uAQCRWZsk95VmL2nALRGJgbg86PUFrGPyYV5ayLFVy6', '002', 'ec220af7e534b4a0927b0174718e42b45662379efa6eeffb773bd4194b37fb34', 0, 1111292, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-10 17:52:22', 'zdpuB2uAQCRWZsk95VmL2nALRGJgbg86PUFrGPyYV5ayLFVy6', '002', '0f9312d9bfd569b62a3938aff4f435c401106628b4428861d2bb0230c6cb8502', 0, 1111293, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:18:57', 'zdpuB2uAQCRWZsk95VmL2nALRGJgbg86PUFrGPyYV5ayLFVy6', '002', '', 0, 1111294, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-13 09:19:05', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', 'bb431b3cefd8cf8113cdf5b0393859fb77b7a1cb40d581836e22f926d46d7d68', 0, 1111295, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:19:09', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111296, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:19:12', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111297, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:19:16', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111298, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:19:23', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111299, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:20:57', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111300, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:21:01', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '', 0, 1111301, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:21:04', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111302, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-13 09:21:12', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111303, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-13 09:21:18', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '9cc9534b2645c33944aadeac86c2ff53239ba3b1d756829ae4f3c5a40b9155f7', 0, 1111304, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-13 09:21:26', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '3713e35be6850da05ed3f43f57239390528dd5e8d9811bc20d44989b90ffb98e', 0, 1111305, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-13 09:21:31', 'zdpuAq8bq3qE2KvuWa1sdv8SVLojtYJPeskCr1h9rGoZSwabC', '002', '598ec7eb74a46b721d371b06a6d1b28c2df94fb9e1ef2267a0af372a971532ca', 0, 1111306, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-13 09:21:35', 'zdpuB2uAQCRWZsk95VmL2nALRGJgbg86PUFrGPyYV5ayLFVy6', '002', '0c7992d300dc7a3c33e590eb1a7982ec1229ac819f5ff774e63fa6aaaa9f8287', 0, 1111307, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-13 09:21:41', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '182b7ae57ffc5adc789a72332831227b28abb5e481508f83edfe3fae77163cbf', 0, 1111308, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-13 09:21:48', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', 'b85b173a0ea2c0734811e5f5ca2ea05d1f983cfe563c02cf519632ea0804c37d', 0, 1111309, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-13 09:29:11', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '92188376a44fc759f3ded9e44d5e1b4e5d1c6629beab9a9c6aa7ae7bc872c017', 0, 1111310, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:49:05', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '', 0, 1111311, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-14 14:49:19', 'zdpuApAWyw5kTSCCNtMVRrYyiuKVNmrKKmb1xz6qjZ85F2ce8', '002', '6ae26d764844823049f8878363bad98c72796b2dbe7438a7d5a0de5da0b9be51', 0, 1111312, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-14 14:49:33', 'zdpuAvnsfa8PVPSsrGTfajEqBXJ1MG8nprRcU3r82WhukxMFK', '002', '7fa1c76c4eb5fee58fb4a2cfa1f8ca66132f70950e9aac0696907222b7bf26e1', 0, 1111313, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-14 14:49:40', 'zdpuAq8bq3qE2KvuWa1sdv8SVLojtYJPeskCr1h9rGoZSwabC', '002', 'b863823b8bc8bc99d143680459a14f312f11ef5d56dd1021264b6daf40dc217e', 0, 1111314, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-14 14:49:53', 'zdpuAq8bq3qE2KvuWa1sdv8SVLojtYJPeskCr1h9rGoZSwabC', '002', '37da75edff76238da8b7c18e8196f119c7e4392bfddaa998ff9966a0f16f21a8', 0, 1111315, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-14 14:50:03', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', 'f8bd2d1841a494c047d809a918ef18635925f31b670e7904c1e0efa6f73c9867', 0, 1111316, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:50:17', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '', 0, 1111317, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:50:25', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111318, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:50:31', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111319, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:50:40', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111320, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:50:50', 'zdpuAu2LrDZPiJjUfhHnKVBqYfuWCC4vZyTfSQWh9AoVwKnvG', '001', '', 0, 1111321, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:50:53', 'zdpuAucDy6jNYr42T56sQQ5GjBuyuqybSZweFuLMGY4ZF14tq', '002', '', 0, 1111322, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:50:56', 'zdpuAmzxdevjTk4x3hRVZ8N481HWMVZnuUmxzzEWSv5vYECUF', '003', '', 0, 1111323, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-14 14:51:04', 'zdpuAorcE34YFWT5ZqFeYaRw7r2w5GXbNQz74zx5YvrMTivHU', '006', '', 0, 1111324, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-24 16:11:34', 'zdpuAnU3rHXj9Mzn29eeh25HLDvpefrHMjWDjsTTwC6VtdC4Y', '002', '2625da0dce91d5b0a17152975a1907ce44fcb2980d9591ae8d65d1e2e3916068', 0, 1111325, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-24 16:11:48', 'zdpuAnU3rHXj9Mzn29eeh25HLDvpefrHMjWDjsTTwC6VtdC4Y', '002', '', 0, 1111326, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-24 16:16:26', 'zdpuAnU3rHXj9Mzn29eeh25HLDvpefrHMjWDjsTTwC6VtdC4Y', '002', 'cb4a7f5e61ec4e281eb53773716b4eaa0b31ea6bc54db20cacf49155092fdeaf', 0, 1111327, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-24 16:16:32', 'zdpuAnU3rHXj9Mzn29eeh25HLDvpefrHMjWDjsTTwC6VtdC4Y', '002', '', 0, 1111328, '0');
INSERT INTO `traceInfo` VALUES ('05', '交通局', 0, '2019-05-27 14:46:10', 'zdpuAu5HN5vaZbdXqYqtYUHSFVKaDCzFR1QiMAx7Uo1Rx81Zp', '05', '2bdc6d8596a6f36e66e7d516ccb76ca89d1c2f13665357762588653a9e419e02', 0, 1111329, '0');
INSERT INTO `traceInfo` VALUES ('05', '交通局', 0, '2019-05-27 14:52:12', 'zdpuAu5HN5vaZbdXqYqtYUHSFVKaDCzFR1QiMAx7Uo1Rx81Zp', '05', '52f71bc4f5b884e52afd4bea80a712aaa3f65fa5db3022099fac8689a8ae62be', 0, 1111330, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 0, '2019-05-27 14:56:44', 'zdpuArCP2T4xmQEZMw3tfqhrXGUyvGNJS5sc5diamHWkw9m64', '01', '0399a9a06f218282a848e95292732bd5695f3c11e98986187bde1f5c5c9d53f8', 0, 1111331, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 0, '2019-05-27 15:18:41', 'zdpuArCP2T4xmQEZMw3tfqhrXGUyvGNJS5sc5diamHWkw9m64', '01', '9e48e0e64e4c54b0b022ecc70d8c1bb7a4f855355d31abc41db65b0795c913fa', 0, 1111332, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 0, '2019-05-27 15:21:05', 'zdpuAokx81mgqZTSpxH6wkJiqaasjxF7UE25xjLYc9AGkwpq1', '01', 'b15d5ff2aafe1b86bb9a3e8dd30b939d786a45e748187a3224bb08236b3043d2', 0, 1111333, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 1, '2019-05-27 15:22:24', 'zdpuAokx81mgqZTSpxH6wkJiqaasjxF7UE25xjLYc9AGkwpq1', '01', '', 0, 1111334, '0');
INSERT INTO `traceInfo` VALUES ('02', '社保局', 0, '2019-05-27 15:28:32', 'zdpuAkdMQsrybk7kswZ4uhoagUn7bLurCC37u4r5x8GdNtRPZ', '02', 'e389693637d78a0dab6e6890791996b92028c8c8a6fe0cfbc9b93d958af73654', 0, 1111335, '0');
INSERT INTO `traceInfo` VALUES ('02', '医保局', 0, '2019-05-27 15:36:22', 'zdpuApzEPcP9nu6vzETd1CnFDbbtZgRcMVuRvJzJSrk9JJRrY', '02', '14d72a425e0c67ecfd9d684c4ef2b4ba942efbf6ca9ba2bb3bf779ec82781129', 0, 1111336, '0');
INSERT INTO `traceInfo` VALUES ('03', '公安局', 0, '2019-05-27 16:11:44', 'zdpuAnos2vBaUU5UhDXp27b25yKQY5FLbcEAVPgt7iLLNudvm', '03', '6c528521d7216a48e0c2a64b6d8d746a56f6929fddccfeff2dda22573c2d82b4', 0, 1111337, '0');
INSERT INTO `traceInfo` VALUES ('03', '公安局', 0, '2019-05-27 16:20:08', 'zdpuAnos2vBaUU5UhDXp27b25yKQY5FLbcEAVPgt7iLLNudvm', '03', '7ae45dcd46f1fa90d923cb8b1982e2630b14db7d884f69e089678a8a91aceb87', 0, 1111338, '0');
INSERT INTO `traceInfo` VALUES ('04', '民政局', 0, '2019-05-27 16:27:44', 'zdpuAtxJWTzTw6Lq8QHsCKX6XSfrVQQmBWdFyvv6Sp65SX5u2', '04', 'f7515704a60a8919e1c61b473cc67f99157b42106ff0700c07874ad9f404dddb', 0, 1111339, '0');
INSERT INTO `traceInfo` VALUES ('04', '民政局', 0, '2019-05-27 16:34:17', 'zdpuAsWGbC67KdtexDQKAsFW2mX2Qn2kgkTakNR3B2nqdpMUo', '04', '8b5a1f27c284ec296d237a7525b723b8ac6d565bc4bdb89192d064ef4fa4c364', 0, 1111340, '0');
INSERT INTO `traceInfo` VALUES ('05', '交通局', 0, '2019-05-27 16:45:44', 'zdpuAxus3F1F2yABV1zjF6kHg9JoRY3m9S1ZF13ryktb8Abm7', '05', '67f02da556cc62c9a76036dbfb8c9208cddc688136356df80c365221ff98cf21', 0, 1111341, '0');
INSERT INTO `traceInfo` VALUES ('05', '交通局', 0, '2019-05-27 16:50:49', 'zdpuAsfXPrJHybCG3ZuR4S4DwVRUW7Qmv3JBxvxANBPf8FufW', '05', '1c0a0aec1bee14743bad569217566890d4180f260f7943bb556f300f5f98beef', 0, 1111342, '0');
INSERT INTO `traceInfo` VALUES ('06', '运营商', 0, '2019-05-27 16:59:29', 'zdpuB1cpdmoc8juGWdcuw8orjFFNy5BQQJBq3pR2u8cu13Xic', '06', '2b36f59c78c8eb3ac6f5fa503bfc30d93abc5da10e3908982779e0de3d7c950f', 0, 1111343, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 0, '2019-05-27 17:41:06', 'zdpuAtHdPLkFQZrKjmTcMJMRtGjxi13muWauJQ7Fifpa5n7Je', '01', '9a9f6e26f8a3f96253d8493472e29ad8678df4ec76fa401727966612345be7e5', 0, 1111344, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 1, '2019-05-27 17:42:36', 'zdpuAtHdPLkFQZrKjmTcMJMRtGjxi13muWauJQ7Fifpa5n7Je', '01', '', 0, 1111345, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 0, '2019-05-28 09:50:29', 'zdpuB2EDB6VhFQ2truKs56UEFjyHgpp9cnssqAAaQ24NNVjb6', '01', 'a1548220b1b2bcfd1bbc9c46a5c6761d786e2b79300ad75944d07255ebf890c1', 0, 1111346, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 1, '2019-05-28 09:51:03', 'zdpuB2EDB6VhFQ2truKs56UEFjyHgpp9cnssqAAaQ24NNVjb6', '01', '', 0, 1111347, '0');
INSERT INTO `traceInfo` VALUES ('01', '社保局', 1, '2019-05-28 10:37:40', 'zdpuB2EDB6VhFQ2truKs56UEFjyHgpp9cnssqAAaQ24NNVjb6', '01', '', 0, 1111348, '0');
INSERT INTO `traceInfo` VALUES ('06', '运营商', 0, '2019-05-28 11:35:09', 'zdpuB3F18JPcbJhDHiuSVegXQay6uF8PZ2wcEmUxL3PYj8g4C', '06', '794e08800c56e47b36e39ed9eaad3e1a24344a6dc1197f6671170c1bf73ec723', 0, 1111349, '0');
INSERT INTO `traceInfo` VALUES ('06', '运营商', 1, '2019-05-28 11:41:47', 'zdpuB3F18JPcbJhDHiuSVegXQay6uF8PZ2wcEmUxL3PYj8g4C', '06', '', 0, 1111350, '0');
INSERT INTO `traceInfo` VALUES ('06', '运营商', 0, '2019-05-28 13:55:12', 'zdpuAsBJqpB5YV9kqBguQbhEsXks2Uwy5VtHXkXHWcq5sBQEq', '06', 'a9fe85d8c6d5d77ee9c8b9bf6c389925691b288a4a85830570cf80827390214c', 0, 1111351, '0');
INSERT INTO `traceInfo` VALUES ('06', '运营商', 1, '2019-05-28 13:56:55', 'zdpuAsBJqpB5YV9kqBguQbhEsXks2Uwy5VtHXkXHWcq5sBQEq', '06', '', 0, 1111352, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-31 14:27:37', 'QmZXsSAhj7UovozTuJx7ypPMz3SQRDewjtDEtUYfpQUXWo', '002', '6ff38037e7983228203f19902537da1d1bb59d9c2672185d1e5e8f081605b65a', 0, 1111353, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-31 14:31:05', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '1a719655607356857f0e3351776f89c9f916dbb0b7b136727c26f0df7a707a71', 0, 1111354, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 14:31:40', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '', 0, 1111355, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 14:34:39', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '', 0, 1111356, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 14:50:23', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '', 0, 1111357, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 14:53:34', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '', 0, 1111358, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-31 15:04:03', 'QmYb4APf4zuBr54HdRg69aALR9S4RRdxgCwCHnm8kkohRz', '002', 'cc20abfd79f2b692497dd639a1b0e4521074afd4583c2515fa2caaf8e1164c0b', 0, 1111359, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 15:04:15', 'QmYb4APf4zuBr54HdRg69aALR9S4RRdxgCwCHnm8kkohRz', '002', '', 0, 1111360, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 15:05:43', 'QmYb4APf4zuBr54HdRg69aALR9S4RRdxgCwCHnm8kkohRz', '002', '', 0, 1111361, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-31 15:06:06', 'QmZxyDkV8CnVye37Xtz6zCQPaAaW7TJLfxGXvHRM2LGpui', '002', '92572cbaf23474d7f0551338115fa55a61c510c18eee565eda1f94a7adf9a326', 0, 1111362, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 15:06:13', 'QmZxyDkV8CnVye37Xtz6zCQPaAaW7TJLfxGXvHRM2LGpui', '002', '', 0, 1111363, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-31 15:09:00', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '2234d04347a816cfbae5ff25fdf6fd9734879444372955639a36ffd3f37c6d65', 0, 1111364, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 15:09:12', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '', 0, 1111365, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-31 15:20:26', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', 'f2c9700d1fa3b519ae90f2143f0ee5afe8f42979625a2340605409ec085b7a2a', 0, 1111366, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 15:20:49', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '', 0, 1111367, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 0, '2019-05-31 15:22:26', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', 'c3c1ffabbd716ed37301100821bbf4635476951bd0d27dfb9287f7f101fadcc6', 0, 1111368, '0');
INSERT INTO `traceInfo` VALUES ('002', '社保局', 1, '2019-05-31 15:22:36', 'QmZj9hf6QguLNa1k8G3dxxWsgVn3RC2GRdQiTvYPtwn18g', '002', '', 0, 1111369, '0');
INSERT INTO `traceInfo` VALUES ('06', '运营商', 0, '2019-06-05 17:08:11', 'zdpuB1MxvhrSS4R9L7s9d6rRR12pmFMoKW2SAX6JGi2enFJxz', '06', 'c6f47281d3dd7af32fac757ca4e14a36c4f7833b95c83e7f7fcdbd86340b8a9c', 1, 1111370, '0');
INSERT INTO `traceInfo` VALUES ('06', '运营商', 0, '2019-06-05 17:42:12', 'zdpuB1MxvhrSS4R9L7s9d6rRR12pmFMoKW2SAX6JGi2enFJxz', '06', '112df86792485ae06c148b38e5e79b73b4aa8858107027a659632306706b36b8', 1, 1111371, '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0, 'admin0', 0);
INSERT INTO `user` VALUES (1, 'admin1', 1);
INSERT INTO `user` VALUES (2, 'admin2', 2);
INSERT INTO `user` VALUES (3, 'admin3', 3);
INSERT INTO `user` VALUES (4, 'admin4', 4);
INSERT INTO `user` VALUES (5, 'admin5', 5);
INSERT INTO `user` VALUES (6, 'admin6', 6);
INSERT INTO `user` VALUES (7, 'admin7', 7);
INSERT INTO `user` VALUES (8, 'admin8', 8);
INSERT INTO `user` VALUES (9, 'admin9', 9);
INSERT INTO `user` VALUES (10, 'test10', 10);
INSERT INTO `user` VALUES (11, 'test11', 11);
INSERT INTO `user` VALUES (12, 'test12', 12);
INSERT INTO `user` VALUES (13, 'test13', 13);
INSERT INTO `user` VALUES (14, 'test14', 14);
INSERT INTO `user` VALUES (15, 'test15', 15);
INSERT INTO `user` VALUES (16, 'test16', 16);
INSERT INTO `user` VALUES (17, 'test17', 17);
INSERT INTO `user` VALUES (18, 'test18', 18);
INSERT INTO `user` VALUES (19, 'test19', 19);

SET FOREIGN_KEY_CHECKS = 1;
