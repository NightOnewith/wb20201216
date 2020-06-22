/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : ryds

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2020-06-21 15:34:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'admin', '保存用户', 'com.ethan.ryds.controller.sys.SysUserController.save()', '[{\"username\":\"test12\",\"email\":\"test12@ryds.com\",\"mobile\":19809898765,\"status\":1,\"roleIdList\":[3]}]', '8', '0:0:0:0:0:0:0:1', '2020-06-21 14:31:44');
INSERT INTO `sys_log` VALUES ('2', 'admin', '保存用户', 'com.ethan.ryds.controller.sys.SysUserController.save()', '[{\"userId\":19,\"username\":\"test13\",\"password\":\"a5ae212bc5cff8abe514c09f912d18d184ac2c9862e98e87d953ed9a8edd8958\",\"salt\":\"XFTXsGTv4K7bZIUxdkg4\",\"email\":\"test13@ryds.com\",\"mobile\":19809898765,\"introduction\":\"此用户很懒~，未填写个人简介。\",\"status\":1,\"createUserId\":1,\"createTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":21},\"time\":{\"hour\":14,\"minute\":32,\"second\":19,\"nano\":179000000}},\"roleIdList\":[3]}]', '55', '0:0:0:0:0:0:0:1', '2020-06-21 14:32:19');
INSERT INTO `sys_log` VALUES ('3', 'admin', '修改用户', 'com.ethan.ryds.controller.sys.SysUserController.update()', '[{\"userId\":19,\"username\":\"test13\",\"email\":\"test13@test.com\",\"mobile\":19809898765,\"status\":1,\"createUserId\":1,\"roleIdList\":[3]}]', '43', '0:0:0:0:0:0:0:1', '2020-06-21 14:39:08');
INSERT INTO `sys_log` VALUES ('4', 'test13', '修改密码', 'com.ethan.ryds.controller.sys.SysUserController.updatePwd()', '[{\"userId\":19,\"username\":\"test13\",\"password\":\"12345678\"}]', '24', '0:0:0:0:0:0:0:1', '2020-06-21 14:42:37');
INSERT INTO `sys_log` VALUES ('5', 'admin', '删除用户', 'com.ethan.ryds.controller.sys.SysUserController.delete()', '[19]', '6', '0:0:0:0:0:0:0:1', '2020-06-21 14:43:46');
INSERT INTO `sys_log` VALUES ('6', 'admin', '修改用户', 'com.ethan.ryds.controller.sys.SysUserController.update()', '[{\"userId\":12,\"username\":\"test6\",\"email\":\"test2@ryds.com\",\"mobile\":12313213132,\"status\":1,\"createUserId\":1,\"roleIdList\":[]}]', '28', '0:0:0:0:0:0:0:1', '2020-06-21 15:05:14');
INSERT INTO `sys_log` VALUES ('7', 'admin', '修改用户', 'com.ethan.ryds.controller.sys.SysUserController.update()', '[{\"userId\":1,\"username\":\"admin\",\"email\":\"ethan@admin.io\",\"mobile\":18888888888,\"status\":1,\"createUserId\":1,\"roleIdList\":[1]}]', '18', '0:0:0:0:0:0:0:1', '2020-06-21 15:05:31');
INSERT INTO `sys_log` VALUES ('8', 'admin', '删除用户', 'com.ethan.ryds.controller.sys.SysUserController.delete()', '[1]', '0', '0:0:0:0:0:0:0:1', '2020-06-21 15:05:38');
INSERT INTO `sys_log` VALUES ('9', 'admin', '删除用户', 'com.ethan.ryds.controller.sys.SysUserController.delete()', '[1]', '0', '0:0:0:0:0:0:0:1', '2020-06-21 15:05:46');
INSERT INTO `sys_log` VALUES ('10', 'admin', '删除用户', 'com.ethan.ryds.controller.sys.SysUserController.delete()', '[1]', '0', '0:0:0:0:0:0:0:1', '2020-06-21 15:05:48');
INSERT INTO `sys_log` VALUES ('11', 'admin', '删除用户', 'com.ethan.ryds.controller.sys.SysUserController.delete()', '[1]', '0', '0:0:0:0:0:0:0:1', '2020-06-21 15:05:49');
INSERT INTO `sys_log` VALUES ('12', 'admin', '删除用户', 'com.ethan.ryds.controller.sys.SysUserController.delete()', '[1]', '0', '0:0:0:0:0:0:0:1', '2020-06-21 15:05:50');
INSERT INTO `sys_log` VALUES ('13', 'admin', '删除用户', 'com.ethan.ryds.controller.sys.SysUserController.delete()', '[1]', '0', '0:0:0:0:0:0:0:1', '2020-06-21 15:05:51');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', null, '0', 'component', '1');
INSERT INTO `sys_menu` VALUES ('2', '1', '用户管理', 'sys/user', null, '1', 'user', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '菜单管理', 'sys/menu', null, '1', 'nested', '3');
INSERT INTO `sys_menu` VALUES ('4', '1', '角色管理', 'sys/role', null, '1', 'tree', '2');
INSERT INTO `sys_menu` VALUES ('5', '1', '系统日志', 'sys/log', '', '1', 'form', '4');
INSERT INTO `sys_menu` VALUES ('6', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('7', '2', '新增', null, 'sys:user:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '2', '修改', null, 'sys:user:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '4', '查看', null, 'sys:role:list,sys:role:select,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '4', '新增', null, 'sys:role:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '4', '修改', null, 'sys:role:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '4', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '3', '查看', null, 'sys:menu:list,sys:menu:select,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '3', '新增', null, 'sys:menu:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '3', '修改', null, 'sys:menu:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '3', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '5', '查看', null, 'sys:log:list', '2', null, '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `status` int(10) DEFAULT NULL COMMENT '状态： 0：正常   1：删除',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '管理员角色', '0', '1', '2020-06-18 14:16:27');
INSERT INTO `sys_role` VALUES ('2', '运营人员', '运营人员角色', '0', '2', '2020-06-18 15:16:27');
INSERT INTO `sys_role` VALUES ('3', '测试人员', '测试人员角色', '0', '1', '2020-06-18 16:16:27');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1273524225478496384 DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1273524225478496351', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496352', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496353', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496354', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496355', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496356', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496357', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496358', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496359', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496360', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496361', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496362', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496363', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496364', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496365', '1', '12');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496366', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496367', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496368', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496369', '3', '14');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496370', '3', '5');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496371', '3', '18');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496372', '3', '1');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496373', '3', '3');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496374', '2', '6');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496375', '2', '8');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496376', '2', '14');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496377', '2', '16');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496378', '2', '10');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496379', '2', '12');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496380', '2', '1');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496381', '2', '2');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496382', '2', '3');
INSERT INTO `sys_role_menu` VALUES ('1273524225478496383', '2', '4');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` bigint(11) DEFAULT NULL COMMENT '手机号',
  `introduction` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常   2 ：已删除',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '50179e0d4183d5594dd6c9b907f78085d03a2073e359cc86425cd87a80559672', 'TS4wcRHQ7w3tY1MtXTSc', 'ethan@admin.io', '18888888888', '吾本放荡不羁，为何囚困于此！哈撒给~', '1', '1', '2020-05-30 10:10:05');
INSERT INTO `sys_user` VALUES ('2', '运营账号', '988113bad41d6f4ac42afc7494e03811145658f538a2d8c8282d85d13b810c8f', 'nWBfRG2XHEHRu7UqcyPP', 'yunying@admin.io', '18222212121', '此用户很懒~，未填写个人简介。', '1', '1', '2020-05-30 22:13:53');
INSERT INTO `sys_user` VALUES ('3', '测试账号', 'b1f7ea1d2b2314b3fc3af605c91eff7b8c68363b8670182f6a112e4fdfed6abb', 'JpAloSyxFcLZidEbNAWK', 'test@admin.io', '18776767777', '此用户很懒~，未填写个人简介。', '1', '1', '2020-05-30 22:14:57');
INSERT INTO `sys_user` VALUES ('4', 'test2', '453bbcf2e677ad57e1b122a1fe920ed86ecbecc8e8b6803d4f205abe42cf44d0', 'K0btkxLUhYAUo0b4NDwm', 'test2@ryds.com', '16789876789', '此用户很懒~，未填写个人简介。', '1', '1', '2020-06-16 12:49:19');
INSERT INTO `sys_user` VALUES ('6', 'test3', '1e9415beb2580bc41dd3766e8381b94a34a34a56c547bd64ba5236e487d818a5', 'i4IenCchPexTrnf4ZxJU', 'test3@ryds.com', '10989877232', '此用户很懒~，未填写个人简介。', '0', '1', '2020-06-16 12:58:06');
INSERT INTO `sys_user` VALUES ('7', 'test4', 'e0630b040fb691b1431b8d2c6bc2c167dc687b9dbb111b2756973ed39417ac57', 'ofPl7Ev8sruk2TFVvg0o', 'test2@ryds.com', '12345323232', '此用户很懒~，未填写个人简介。', '2', '1', '2020-06-16 12:58:25');
INSERT INTO `sys_user` VALUES ('8', 'test5', 'd5c2123fc09da867d8bef3d2c9b1834c93f18375188600c9d10b768ece8f5c85', 'HRRg5qXFvjsMFgiQiVBg', 'test2@ryds.com', '12313213132', '此用户很懒~，未填写个人简介。', '1', '1', '2020-06-16 12:58:37');
INSERT INTO `sys_user` VALUES ('12', 'test6', 'b34363463c404a1a4520f91c7c97d8e52e8254b57a00e9e4065ce1248001ac74', 'J5zHQzmuhL7kv2FWiTEg', 'test2@ryds.com', '12313213132', '此用户很懒~，未填写个人简介。', '1', '1', '2020-06-16 14:11:43');
INSERT INTO `sys_user` VALUES ('13', 'test7', 'b803d3837638a3902990cc09e88e986bec855890e15db86b8835a049abb8f137', 'y49ClQaw6kPkFQswMrgy', 'test2@ryds.com', '12313213132', '此用户很懒~，未填写个人简介。', '2', '1', '2020-06-16 14:12:26');
INSERT INTO `sys_user` VALUES ('14', 'test8', '5072149dc4101afac419b3ddf7060586fbe2777746b349292cda96b068b338cc', 'UrB20j1RQoqau6OjXUGK', 'test2@ryds.com', '12313213132', '此用户很懒~，未填写个人简介。', '0', '1', '2020-06-16 15:30:44');
INSERT INTO `sys_user` VALUES ('15', 'test9', 'e4199ec751643c0865ac5f5ab0e3089c6d7fd04d24ebda145fc0af7c6c89ff32', 'RK9kzjSBG9HAzrOgiFju', 'test2@ryds.com', '12333222232', '此用户很懒~，未填写个人简介。', '1', '1', '2020-06-16 15:30:51');
INSERT INTO `sys_user` VALUES ('16', 'test10', '21e1ba83877c8e4593b66cd525ef6c41972d32004cf7ef2c4899db13554ac84e', 'sERR0lGZ1wlJHd6FTsZ6', 'test2@ryds.com', '10224322222', '此用户很懒~，未填写个人简介。', '1', '1', '2020-06-16 15:31:04');
INSERT INTO `sys_user` VALUES ('17', 'test11', 'd5eb5036958b9cd2e01ef1ce386d2017849500e523a4346730ce1b6b16bfdaca', 'uW3bpWMlAdLTQqqYqplG', 'test2@ryds.com', '23456343211', '此用户很懒~，未填写个人简介。', '1', '1', '2020-06-17 10:37:35');
INSERT INTO `sys_user` VALUES ('18', 'test12', '9cb88107c0ef8d045f0cb1a747854d2e2c1eaef99bf916fa65be2fb652e3481c', '3iSF7YMxnPwQN8M5AWkA', 'test', '18909878765', '此用户很懒~，未填写个人简介。', '1', '1', '2020-06-19 10:07:44');
INSERT INTO `sys_user` VALUES ('19', 'test13', '0c2b4d83dbecb5ed1408112c64c15b92e040e0c4d2e1cd96fb8e6e56c8f5774b', 'XFTXsGTv4K7bZIUxdkg4', 'test13@test.com', '19809898765', '此用户很懒~，未填写个人简介。', '2', '1', '2020-06-21 14:32:19');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('6', '2', '2');
INSERT INTO `sys_user_role` VALUES ('12', '17', '3');
INSERT INTO `sys_user_role` VALUES ('16', '18', '3');
INSERT INTO `sys_user_role` VALUES ('18', '3', '3');
INSERT INTO `sys_user_role` VALUES ('20', '19', '3');
INSERT INTO `sys_user_role` VALUES ('21', '1', '1');

-- ----------------------------
-- Table structure for todo_list
-- ----------------------------
DROP TABLE IF EXISTS `todo_list`;
CREATE TABLE `todo_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `label` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `status` int(10) DEFAULT NULL COMMENT '状态： 0：执行中   1：已完成 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of todo_list
-- ----------------------------
INSERT INTO `todo_list` VALUES ('1', '登录-token', '1');
INSERT INTO `todo_list` VALUES ('2', '登录-动态菜单', '1');
INSERT INTO `todo_list` VALUES ('3', '登录-授权', '1');
INSERT INTO `todo_list` VALUES ('4', '个人中心-UserCard', '1');
INSERT INTO `todo_list` VALUES ('5', '个人中心-TodoList', '1');
INSERT INTO `todo_list` VALUES ('6', '个人中心-Weather', '1');
INSERT INTO `todo_list` VALUES ('7', '用户管理', '1');
INSERT INTO `todo_list` VALUES ('8', '角色管理', '1');
INSERT INTO `todo_list` VALUES ('9', '菜单管理', '1');
INSERT INTO `todo_list` VALUES ('10', '日志记录', '1');
