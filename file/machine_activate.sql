/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.15 : Database - machine_activate
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `tb_activate` */

DROP TABLE IF EXISTS `tb_activate`;

CREATE TABLE `tb_activate` (
  `id` varchar(36) NOT NULL,
  `order_id` varchar(50) DEFAULT NULL,
  `chip_sn` varchar(50) DEFAULT NULL,
  `activate_times` smallint(5) DEFAULT '0',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_activate` */

insert  into `tb_activate`(`id`,`order_id`,`chip_sn`,`activate_times`,`update_time`,`create_time`) values ('3b84b1c0-4643-456d-9b0e-f1e852c35947','001_12345678AABBCCDDEE_20190529_0001','12345678AABBCCDDEE',4,'2019-05-29 17:40:31','2019-05-29 17:35:06');

/*Table structure for table `tb_company` */

DROP TABLE IF EXISTS `tb_company`;

CREATE TABLE `tb_company` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(50) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:正常,2:删除,3:异常',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `tb_company` */

insert  into `tb_company`(`company_id`,`company_name`,`status`) values (1,'测试公司_1',1),(3,'客户1',1),(4,'客户2',1),(5,'修改客户',1),(6,'客户4',2),(7,'客户5',1),(8,'客户6',1),(9,'客户7',1),(10,'客户8',1),(11,'客户9',1),(12,'客户10',1),(13,'客户11',1);

/*Table structure for table `tb_order_config` */

DROP TABLE IF EXISTS `tb_order_config`;

CREATE TABLE `tb_order_config` (
  `id` varchar(36) NOT NULL,
  `company_id` int(11) NOT NULL,
  `order_id` varchar(50) DEFAULT NULL,
  `licence_count` int(11) DEFAULT '0',
  `key1` varchar(50) DEFAULT NULL COMMENT 'aes加密key值',
  `activate_count` int(11) DEFAULT '0',
  `is_close` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:开启，2:关闭',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:正常，2:删除,3:其它',
  `order_date` date DEFAULT NULL COMMENT '订单日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_order_config` */

insert  into `tb_order_config`(`id`,`company_id`,`order_id`,`licence_count`,`key1`,`activate_count`,`is_close`,`status`,`order_date`,`create_time`) values ('1fc9cb42-b546-44f1-86fe-3ddddfc6d005',1,'001_12345678AABBCCDDEE_20190529_0001',50,'ca242bef336c46a9b4e80902f3fe6d1b',2,1,1,'2019-05-29','2019-05-29 16:37:00'),('7e0f2cc9-3b06-4931-ab07-8a9db5c04097',1,'001_12345678AABBCCDDEE_20190529_0002',50,'903e5ca9d5f94fb59724fb82294b34f8',0,1,1,'2019-05-29','2019-05-29 16:37:18');

/*Table structure for table `tb_resource` */

DROP TABLE IF EXISTS `tb_resource`;

CREATE TABLE `tb_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL,
  `resource_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tb_resource` */

insert  into `tb_resource`(`id`,`url`,`resource_name`) values (1,'/userPage','用户页面'),(2,'/adminPage','管理员页面'),(3,'/allPage','所有人可见页面'),(4,'/home','主页');

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) NOT NULL,
  `role_name_zh` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_role` */

insert  into `tb_role`(`id`,`role_name`,`role_name_zh`) values (1,'ROLE_ADMIN','管理员'),(2,'ROLE_USER','普通用户');

/*Table structure for table `tb_role_resource` */

DROP TABLE IF EXISTS `tb_role_resource`;

CREATE TABLE `tb_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `tb_role_resource` */

insert  into `tb_role_resource`(`id`,`role_id`,`resource_id`) values (1,1,1),(2,1,2),(3,1,3),(4,2,1),(5,2,3),(6,1,4);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `salt` varchar(5) NOT NULL,
  `is_login` tinyint(1) NOT NULL DEFAULT '0' COMMENT '登录状态:0未登录，1已登录',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`user_id`,`user_name`,`password`,`salt`,`is_login`,`create_time`) values (5,'defi1','7b37226667cd2cd66d88b78368f71f4a','6058',1,'2019-05-22 18:17:22'),(6,'miou','0e3586002f05f790e9f5a7faa7a87659','3684',0,'2019-05-30 18:06:01');

/*Table structure for table `tb_user_role` */

DROP TABLE IF EXISTS `tb_user_role`;

CREATE TABLE `tb_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user_role` */

insert  into `tb_user_role`(`id`,`user_name`,`role_id`) values (1,'defi',1),(2,'miou',2),(3,'defi1',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
