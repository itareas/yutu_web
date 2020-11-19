/*
SQLyog Enterprise v12.08 (64 bit)
MySQL - 8.0.18 : Database - web
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`web` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `web`;

/*Table structure for table `t_log_landing` */

DROP TABLE IF EXISTS `t_log_landing`;

CREATE TABLE `t_log_landing` (
  `uuid` varchar(64) NOT NULL COMMENT '主键编码',
  `login_userid` varchar(64) DEFAULT NULL COMMENT '用户id',
  `login_account` varchar(64) DEFAULT NULL COMMENT '登录账号',
  `login_ip` varchar(128) DEFAULT NULL COMMENT '登陆者的ip',
  `login_sessionid` varchar(128) DEFAULT NULL COMMENT '登陆sessionId',
  `login_date` datetime DEFAULT NULL COMMENT '登陆时间',
  `login_type` varchar(32) DEFAULT NULL COMMENT '登陆类型： 登陆/退出登录',
  `login_result` int(11) DEFAULT NULL COMMENT '登陆结果   0：登陆成功   1：登陆失败',
  `login_appname` varchar(256) DEFAULT NULL COMMENT '登录子系统/模块名称',
  `login_address` varchar(256) DEFAULT NULL COMMENT '登陆系统地址',
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '备注',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统日志_用户登录表';

/*Data for the table `t_log_landing` */

insert  into `t_log_landing`(`uuid`,`login_userid`,`login_account`,`login_ip`,`login_sessionid`,`login_date`,`login_type`,`login_result`,`login_appname`,`login_address`,`remarks`) values ('0349343d-0c2b-4839-b471-8fddcfd7742b','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','F43D54644771E460B878037126A288D4','2019-12-23 11:28:46','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('0880dd8b-dfc7-4418-bf1b-5d8824ba3a35','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','2C35B941C9003B48D243B3BE2C33B742','2019-12-23 10:11:43','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('09e51c67-1ace-4fc4-9b47-d6cdf4db5862',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-19 10:46:15','门户注销',1,'系统门户网站','90FF3D256190698D3284545192E152D7',NULL),('0e30bcc1-f2de-403b-9f01-36e6cb31fc78','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','3B71C6CFA873BACE6C9E8945A3B15A7C','2019-12-29 09:53:19','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('210fc429-dc29-470e-a20c-669293d1a987','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','DEC44F14D8A95A07DCB71E0A44CA48BF','2019-12-29 10:17:16','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('21bfd7d3-de94-4cc1-8587-57d2723692ab',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-23 10:26:02','门户登陆',NULL,'系统门户网站','/login/getLoginVerification',NULL),('2b2d53f6-f4f4-4796-bb62-2daa83033a0e','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','890D9C31E401E593DDDA7DA485BE294B','2019-12-29 12:07:01','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('2bc33a0f-8cca-4bea-8e3d-60b82fc479cc','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','01F4EFC97344E23F84009B5CF7034791','2019-12-29 10:16:21','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('2fc4750d-d809-4448-9a6f-b70ad340def5','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','9D8D222B705BE1F03277D301BDD5760B','2019-12-29 10:21:06','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('35278a1a-f148-4b5e-9bd5-62728e913cb6','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','D5C294FC7B14BFA2C0DE8F21C28D6593','2019-12-19 11:09:26','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('36b0f0bf-dc18-4562-a3a3-28699bcad26c','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','B6E1FE3039B2E14C538D3A051273404B','2019-12-29 10:14:47','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('37c054af-b0ae-4705-9b1c-d7c7ff74273a','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','8954B84CD10AA4B6F2092FAAE182C157','2019-12-29 11:45:20','子系统一登陆',0,'子系统一','/login/getLoginVerification',NULL),('40029950-4f11-44f2-b474-5678b38684bb','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','0285DF2D19FA1AF4372CC562AEBD0D51','2019-12-29 10:03:12','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('51f28d67-b5e7-45f5-8082-fc575c158f6a','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','924A6299FE59C678A11C389C98444296','2019-12-23 10:43:42','门户登陆',0,'系统门户网站','/login/getLoginVerification',NULL),('548b294b-6991-4039-9754-873554d1a22e','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','5353D07A3C30E979B7F79C6343FE617F','2019-12-29 10:49:10','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('55b3b821-9211-4d69-9318-a2ca16affdfb',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-23 10:18:22','门户登陆',NULL,'系统门户网站','/login/getLoginVerification',NULL),('55c7e35b-36ec-49cf-a59c-3b9c72235688','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','344DD932FA2B4CC7B83C53439BAB44FD','2019-12-29 12:21:24','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('65c2860e-54fb-4fa9-904d-61388f7e77e0','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','9E1DB0F5DA8B269A38387EC0EC1BC1C5','2019-12-29 10:52:28','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('73bfc1a9-bcc3-4672-a114-219982a49312','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','165E94C6B71BD5850959F97806A8DD8B','2019-12-21 10:35:30','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('7450756e-1d2c-4156-a2bd-010f12771281','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','8C6E6ECD0F40ADD46C47E90C8EC9BC20','2019-12-19 11:33:05','门户注销',1,'系统门户网站','/login/logout',NULL),('7efcfa90-4825-4022-be57-879a7324b552','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','C0616AD9EA3245501165EA39E82C386C','2019-12-29 10:31:17','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('8058db07-0c28-4e60-95b3-12a22fb47b95','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','8C6E6ECD0F40ADD46C47E90C8EC9BC20','2019-12-19 11:32:58','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('8152524f-aa82-44d8-b5fa-584e5175c319','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','203C42ED2FE56603F6CF07AA0CCE8BF2','2019-12-29 10:19:02','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('83898eb4-defa-4411-966c-89d574aa2113',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-23 10:14:14','门户登陆',NULL,'系统门户网站','/login/getLoginVerification',NULL),('8491eb22-69c8-4383-882f-1b94c82945f3','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','5B1C8299CA086B6DEAD17AA9369DE4B0','2019-12-29 10:26:15','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('86d86e0e-8a74-4d44-b68a-6c42ee2281e0',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-23 10:18:11','门户登陆',NULL,'系统门户网站','/login/getLoginVerification',NULL),('8a948a24-144d-4f5d-b226-8cf78d0d7672','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','11396287EBFF788806D40A39D2EFDAFD','2019-12-19 11:08:04','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('8f4e1a76-37e0-47be-9462-6bbb25a747ba','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','69CE8218220C1E87900F090260D293F6','2019-12-23 10:44:02','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('94652153-54ad-488e-b71d-66918a06075b','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','F9E0BCE0817682F743BABBFA5B454C09','2019-12-29 11:45:54','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('975f3267-4fe2-49c2-810b-efd08465e3e3','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','E93C6B65486CEE49F6A0E7FD34687B8B','2019-12-29 10:13:27','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('9c6c4c79-906b-4cde-93a7-c308b4f1e919','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','E60B50B4A7DBFDCCE7DFAE3258A37145','2019-12-29 10:22:42','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('9ea47ffa-98c5-4e79-9f07-d4d103c4d25d','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','8954B84CD10AA4B6F2092FAAE182C157','2019-12-29 11:45:31','子系统一登陆',0,'子系统一','/login/getLoginVerification',NULL),('9eb7a61e-3dbd-495c-9066-ccd3ee183b8d','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','8954B84CD10AA4B6F2092FAAE182C157','2019-12-29 11:45:24','子系统一登陆',0,'子系统一','/login/getLoginVerification',NULL),('a1b85fd9-072e-4508-9a72-2429bf3d2029','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','90FF3D256190698D3284545192E152D7','2019-12-19 10:45:15','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('a459e4a4-368f-42f0-bd64-6ec6bd80c2d9',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-23 10:22:01','门户登陆',NULL,'系统门户网站','/login/getLoginVerification',NULL),('aeef1ab6-47bf-4f93-87e6-bad7b84f2370','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','6240CE8DDA3625B90D8B2C224D5366DB','2019-12-29 10:32:54','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('b6f4643f-f523-4edd-bcdf-2dd1c603c9b0','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','A76DEEA19061A88C033040F0C80FDE4D','2019-12-23 10:20:27','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('bdb17908-6ce9-4a38-9a35-b084d171481e','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','FA78B4B4363494BBF0E0D8D2A500AF39','2019-12-29 10:29:02','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('c1a4dcd1-6269-4dd2-b241-b7090814981b',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-23 10:15:26','门户登陆',NULL,'系统门户网站','/login/getLoginVerification',NULL),('c243118e-7d42-4c27-adb4-14f5da71c652','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','11396287EBFF788806D40A39D2EFDAFD','2019-12-19 11:08:21','门户注销',1,'系统门户网站','/login/logout',NULL),('c7e7e5ff-8646-48bd-995a-f9fcc2d262df','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','924A6299FE59C678A11C389C98444296','2019-12-23 10:43:07','门户登陆',0,'系统门户网站','/login/getLoginVerification',NULL),('c8f9aca1-e651-4f63-b1d4-c11e6cac708e',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-23 10:14:52','门户登陆',NULL,'系统门户网站','/login/getLoginVerification',NULL),('cc368df0-99aa-4dcd-99fc-7cdb58ac143d','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','127.0.0.1','8954B84CD10AA4B6F2092FAAE182C157','2019-12-29 11:45:06','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('cd2d921a-de95-4f38-a22c-57252f04bb6f','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','BB5075A3F87D4FB2BB61C17AE9A1F5DD','2019-12-29 11:59:48','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('cd70a165-6f1b-4d3c-a05f-e5b2220892db','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','99ECF0786B7CD23A73BA66C22ABF4D97','2019-12-29 10:49:57','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('d2fe35a8-d20b-4056-8322-1be5379f470b','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','8FCF20DB577DAC6B8E8F75446C24EC6E','2019-12-23 11:23:28','门户登陆',1,'系统门户网站','/login/getLoginVerification',NULL),('d38ddb00-beed-4def-b8a9-80ea094a9ce7','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','924A6299FE59C678A11C389C98444296','2019-12-23 10:42:23','门户登陆',0,'系统门户网站','/login/getLoginVerification',NULL),('d6d98ffb-b079-48e5-b790-8763f9f7e2f8','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','7DBC94C0493AB08EE3C7A906B39B95EF','2019-12-29 10:01:45','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('d6e538be-cb50-4845-84a4-b34e8f176b7b','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','31F0AA28865C232463035B4685F441AF','2019-12-29 11:43:30','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('d972f5e0-8c01-4119-a216-9d3e5213269e','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','924A6299FE59C678A11C389C98444296','2019-12-23 10:41:54','门户登陆',0,'系统门户网站','/login/getLoginVerification',NULL),('dff51bf2-6b61-45fa-b879-8f1d88f2463b','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','E60B50B4A7DBFDCCE7DFAE3258A37145','2019-12-29 10:23:44','子系统一登陆',0,'子系统一','/login/getLoginVerification',NULL),('e8665486-b885-4957-88be-539aa036b842','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','FD9642588A7A6F25AB77E1D0036CE2CF','2019-12-29 10:12:09','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('eb8fe005-97c8-4f06-bcfd-c6752dd011bc','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','7D5023267E3CC59A0220C0908FFAF1B2','2019-12-29 10:58:14','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('fdf20bb5-7956-45a2-a52e-a9c741a3fdd3','ae771cf1-23f0-4e8e-b235-fc12a6fb4884','admin','0:0:0:0:0:0:0:1','5FFFC16F09ED3BD13DF516C32FFA6213','2019-12-29 12:01:21','子系统一登陆',1,'子系统一','/login/getLoginVerification',NULL),('ff36e829-3d85-45ca-9552-a4fd2082004b',NULL,'admin','0:0:0:0:0:0:0:1',NULL,'2019-12-23 10:26:00','门户登陆',NULL,'系统门户网站','/login/getLoginVerification',NULL);

/*Table structure for table `t_sys_organization` */

DROP TABLE IF EXISTS `t_sys_organization`;

CREATE TABLE `t_sys_organization` (
  `uuid` varchar(64) NOT NULL COMMENT '编码',
  `org_parent` varchar(64) DEFAULT NULL COMMENT '部门父类id(表格树)',
  `org_code` varchar(64) DEFAULT NULL COMMENT '部门特殊编码',
  `org_name` varchar(512) DEFAULT NULL COMMENT '部门名称',
  `org_phone` varchar(64) DEFAULT NULL COMMENT '部门电话',
  `org_address` varchar(2048) DEFAULT NULL COMMENT '部门地址',
  `org_Manager` varchar(64) DEFAULT NULL COMMENT '部门负责人',
  `org_status` int(11) DEFAULT '0' COMMENT '部门状态 0：未锁定  1：已锁定',
  `order_by` int(11) DEFAULT '1' COMMENT '排序',
  `delete_status` int(11) DEFAULT '0' COMMENT '逻辑删除 0:未删除,  1:已删除',
  `remarks` text COMMENT '备注',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统_组织表';

/*Data for the table `t_sys_organization` */

insert  into `t_sys_organization`(`uuid`,`org_parent`,`org_code`,`org_name`,`org_phone`,`org_address`,`org_Manager`,`org_status`,`order_by`,`delete_status`,`remarks`) values ('8fc09900-1f11-11ea-a564-f875a40f96a2','0','yutu','宇图',NULL,NULL,NULL,0,1,0,NULL),('ef1e8ba1-9cca-4660-afac-9bfc45473cb5','0','yutu','宇图',NULL,NULL,NULL,0,1,0,NULL);

/*Table structure for table `t_sys_user` */

DROP TABLE IF EXISTS `t_sys_user`;

CREATE TABLE `t_sys_user` (
  `uuid` varchar(64) NOT NULL COMMENT '主键',
  `user_code` varchar(64) DEFAULT NULL COMMENT '用户特殊编码',
  `user_account` varchar(128) DEFAULT NULL COMMENT '用户登陆名',
  `user_pwd` varchar(64) DEFAULT NULL COMMENT '用户密码',
  `user_question` varchar(256) DEFAULT NULL COMMENT '密码提示',
  `user_name` varchar(256) DEFAULT NULL COMMENT '用户真实姓名',
  `user_sex` int(11) DEFAULT NULL COMMENT '用户性别  0：保密   1：男   2：女''',
  `user_title` varchar(256) DEFAULT NULL COMMENT '用户职称',
  `user_phone` varchar(64) DEFAULT NULL COMMENT '手机号',
  `user_email` varchar(256) DEFAULT NULL COMMENT '用户邮件',
  `user_status` int(11) DEFAULT '0' COMMENT '用户锁定  0：正常   1：锁定',
  `order_by` int(11) DEFAULT '1' COMMENT '排序',
  `delete_status` int(11) DEFAULT '0' COMMENT '用户逻辑删除  0 ：正常   1：删除',
  `remarks` text COMMENT '备注',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';

/*Data for the table `t_sys_user` */

insert  into `t_sys_user`(`uuid`,`user_code`,`user_account`,`user_pwd`,`user_question`,`user_name`,`user_sex`,`user_title`,`user_phone`,`user_email`,`user_status`,`order_by`,`delete_status`,`remarks`) values ('ae771cf1-23f0-4e8e-b235-fc12a6fb4884','yt-001','admin','1168d53c7f5094945a60aa6d2320e7c4','admin','管理员',1,NULL,'18001030020','it_zbc@outlook.com',0,1,0,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
