/*
SQLyog Ultimate v8.32 
MySQL - 5.1.44-community-log : Database - bbs_vcity
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bbs_vcity` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bbs_vcity`;

/*Table structure for table `jrun_access` */

DROP TABLE IF EXISTS `jrun_access`;

CREATE TABLE `jrun_access` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `allowview` tinyint(1) NOT NULL DEFAULT '0',
  `allowpost` tinyint(1) NOT NULL DEFAULT '0',
  `allowreply` tinyint(1) NOT NULL DEFAULT '0',
  `allowgetattach` tinyint(1) NOT NULL DEFAULT '0',
  `allowpostattach` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`fid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_access` */

insert  into `jrun_access`(`uid`,`fid`,`allowview`,`allowpost`,`allowreply`,`allowgetattach`,`allowpostattach`) values (1,2,1,1,1,1,1),(1,21,1,1,1,1,1),(1,8,1,1,1,1,1),(1,9,1,1,1,1,1),(1,10,1,1,1,1,1),(1,12,1,1,1,1,1),(1,13,1,1,1,1,1),(1,15,1,1,1,1,1),(1,16,1,1,1,1,1),(2,16,0,1,0,0,0),(2,15,0,1,0,0,0),(2,13,0,1,0,0,0),(2,12,0,1,0,0,0),(2,10,0,1,0,0,0),(2,9,0,1,0,0,0),(2,8,0,1,0,0,0),(1,26,1,1,1,1,1),(2,2,0,1,0,0,0),(5,2,1,1,0,1,1),(1,24,1,1,1,1,1),(5,8,1,1,0,1,1),(5,9,1,1,0,1,1),(5,10,1,1,0,1,1),(5,12,1,1,0,1,1),(5,13,1,1,0,1,1),(5,15,1,1,0,1,1),(5,16,1,1,0,1,1),(1,23,1,1,1,1,1),(1,20,1,1,1,1,1),(1,22,1,1,1,1,1),(1,17,1,1,1,1,1),(1,27,1,1,1,1,1),(1,28,1,1,1,1,1),(1,29,1,1,1,1,1),(1,18,1,1,1,1,1),(1,19,1,1,1,1,1);

/*Table structure for table `jrun_activities` */

DROP TABLE IF EXISTS `jrun_activities`;

CREATE TABLE `jrun_activities` (
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cost` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `starttimefrom` int(10) unsigned NOT NULL DEFAULT '0',
  `starttimeto` int(10) unsigned NOT NULL DEFAULT '0',
  `place` char(40) NOT NULL DEFAULT '',
  `class` char(25) NOT NULL DEFAULT '',
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `number` smallint(5) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`tid`),
  KEY `uid` (`uid`,`starttimefrom`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_activities` */

insert  into `jrun_activities`(`tid`,`uid`,`cost`,`starttimefrom`,`starttimeto`,`place`,`class`,`gender`,`number`,`expiration`) values (3,1,100,1280297160,1280556360,'珠海','出外郊游',0,0,1280297160),(5,1,23,1281494640,0,'fsdfd','朋友聚会',0,0,0);

/*Table structure for table `jrun_activityapplies` */

DROP TABLE IF EXISTS `jrun_activityapplies`;

CREATE TABLE `jrun_activityapplies` (
  `applyid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `message` char(200) NOT NULL DEFAULT '',
  `verified` tinyint(1) NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `payment` mediumint(8) NOT NULL DEFAULT '0',
  `contact` char(200) NOT NULL,
  PRIMARY KEY (`applyid`),
  KEY `uid` (`uid`),
  KEY `tid` (`tid`),
  KEY `dateline` (`tid`,`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_activityapplies` */

insert  into `jrun_activityapplies`(`applyid`,`tid`,`username`,`uid`,`message`,`verified`,`dateline`,`payment`,`contact`) values (1,3,'admin',1,'哈哈。。。测试数据。。。。',1,1280210945,-1,'13680336424'),(2,3,'huchangyuan',3,'我参加',1,1280211082,-1,'15992638630');

/*Table structure for table `jrun_adminactions` */

DROP TABLE IF EXISTS `jrun_adminactions`;

CREATE TABLE `jrun_adminactions` (
  `admingid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `disabledactions` text NOT NULL,
  PRIMARY KEY (`admingid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_adminactions` */

/*Table structure for table `jrun_admingroups` */

DROP TABLE IF EXISTS `jrun_admingroups`;

CREATE TABLE `jrun_admingroups` (
  `admingid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `alloweditpost` tinyint(1) NOT NULL DEFAULT '0',
  `alloweditpoll` tinyint(1) NOT NULL DEFAULT '0',
  `allowstickthread` tinyint(1) NOT NULL DEFAULT '0',
  `allowmodpost` tinyint(1) NOT NULL DEFAULT '0',
  `allowdelpost` tinyint(1) NOT NULL DEFAULT '0',
  `allowmassprune` tinyint(1) NOT NULL DEFAULT '0',
  `allowrefund` tinyint(1) NOT NULL DEFAULT '0',
  `allowcensorword` tinyint(1) NOT NULL DEFAULT '0',
  `allowviewip` tinyint(1) NOT NULL DEFAULT '0',
  `allowbanip` tinyint(1) NOT NULL DEFAULT '0',
  `allowedituser` tinyint(1) NOT NULL DEFAULT '0',
  `allowmoduser` tinyint(1) NOT NULL DEFAULT '0',
  `allowbanuser` tinyint(1) NOT NULL DEFAULT '0',
  `allowpostannounce` tinyint(1) NOT NULL DEFAULT '0',
  `allowviewlog` tinyint(1) NOT NULL DEFAULT '0',
  `allowbanpost` tinyint(1) NOT NULL DEFAULT '0',
  `disablepostctrl` tinyint(1) NOT NULL DEFAULT '0',
  `supe_allowpushthread` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`admingid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_admingroups` */

insert  into `jrun_admingroups`(`admingid`,`alloweditpost`,`alloweditpoll`,`allowstickthread`,`allowmodpost`,`allowdelpost`,`allowmassprune`,`allowrefund`,`allowcensorword`,`allowviewip`,`allowbanip`,`allowedituser`,`allowmoduser`,`allowbanuser`,`allowpostannounce`,`allowviewlog`,`allowbanpost`,`disablepostctrl`,`supe_allowpushthread`) values (1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),(2,1,0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0),(3,1,0,1,1,1,0,0,0,1,0,0,1,1,0,0,1,1,0),(16,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0);

/*Table structure for table `jrun_adminnotes` */

DROP TABLE IF EXISTS `jrun_adminnotes`;

CREATE TABLE `jrun_adminnotes` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `admin` varchar(15) NOT NULL DEFAULT '',
  `access` tinyint(3) NOT NULL DEFAULT '0',
  `adminid` tinyint(3) NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_adminnotes` */

/*Table structure for table `jrun_adminsessions` */

DROP TABLE IF EXISTS `jrun_adminsessions`;

CREATE TABLE `jrun_adminsessions` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `ip` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `errorcount` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_adminsessions` */

insert  into `jrun_adminsessions`(`uid`,`ip`,`dateline`,`errorcount`) values (1,'119.132.75.238',1324524396,4);

/*Table structure for table `jrun_advertisements` */

DROP TABLE IF EXISTS `jrun_advertisements`;

CREATE TABLE `jrun_advertisements` (
  `advid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `type` varchar(50) NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `title` varchar(50) NOT NULL DEFAULT '',
  `targets` text NOT NULL,
  `parameters` text NOT NULL,
  `code` text NOT NULL,
  `starttime` int(10) unsigned NOT NULL DEFAULT '0',
  `endtime` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`advid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_advertisements` */

/*Table structure for table `jrun_announcements` */

DROP TABLE IF EXISTS `jrun_announcements`;

CREATE TABLE `jrun_announcements` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `author` varchar(15) NOT NULL DEFAULT '',
  `subject` varchar(250) NOT NULL DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `starttime` char(25) NOT NULL DEFAULT '0',
  `endtime` char(25) NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `groups` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `timespan` (`starttime`,`endtime`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_announcements` */

insert  into `jrun_announcements`(`id`,`author`,`subject`,`type`,`displayorder`,`starttime`,`endtime`,`message`,`groups`) values (10,'admin','第一体验俱乐部正式上线',0,0,'2010-12-08','0','“第一体验俱乐部”由中国电信广州研究院（www.gsta.com）建立，专门用于用户体验与交流在线社区。在这里，您可以通过多种方式，参与中国电信在产品和服务的体验和评估，帮助中国电信提升用户体验水平！ “第一体验俱乐部”共包括了3D虚拟社区、在线交流区、在线论坛三个不同的体验和互动区，欢迎所有会员前往体验。\r\n','');

/*Table structure for table `jrun_answertemplate` */

DROP TABLE IF EXISTS `jrun_answertemplate`;

CREATE TABLE `jrun_answertemplate` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `context` text NOT NULL,
  `creatTime` int(10) NOT NULL,
  `creator` mediumint(8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_answertemplate` */

insert  into `jrun_answertemplate`(`id`,`context`,`creatTime`,`creator`) values (3,'在开会，晚点再回你电话.可以吗',1283500078,1),(7,'新年好，提前祝愿大家在新的一年里万事如意',1284519458,1);

/*Table structure for table `jrun_appointment` */

DROP TABLE IF EXISTS `jrun_appointment`;

CREATE TABLE `jrun_appointment` (
  `appointment_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '建创者id',
  `appointmentname` varchar(512) DEFAULT NULL,
  `appointment_starttime` datetime DEFAULT NULL,
  `appointment_endtime` datetime DEFAULT NULL,
  `appointment_description` text,
  `addTime` datetime DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `ispassword` int(11) DEFAULT NULL COMMENT '否是启用密码',
  `numberOfPartizipants` int(11) DEFAULT NULL COMMENT '参会人数',
  `user_pid` int(11) DEFAULT NULL COMMENT '主持人id',
  `isEnd` int(11) DEFAULT NULL COMMENT '是否结束会议(0否、1是)',
  `modeType` int(11) DEFAULT NULL,
  `imagepath` varchar(512) DEFAULT NULL,
  `isPublished` int(11) DEFAULT NULL,
  PRIMARY KEY (`appointment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_appointment` */

insert  into `jrun_appointment`(`appointment_id`,`user_id`,`appointmentname`,`appointment_starttime`,`appointment_endtime`,`appointment_description`,`addTime`,`password`,`ispassword`,`numberOfPartizipants`,`user_pid`,`isEnd`,`modeType`,`imagepath`,`isPublished`) values (55,75,'labmeeting','2011-02-21 21:24:00','2011-02-25 08:54:00','dd <BR>','2011-02-22 09:06:21',NULL,NULL,25,75,0,0,'',1),(59,68,'视频会议','2011-03-16 04:49:00','2012-03-17 19:19:00','视频会议<BR>','2011-03-07 12:21:07',NULL,NULL,25,68,0,0,'attachments/day_110307/2011030765b0ff24849f2faf260321a2bb4beb0d.jpg',1);

/*Table structure for table `jrun_attachments` */

DROP TABLE IF EXISTS `jrun_attachments`;

CREATE TABLE `jrun_attachments` (
  `aid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `pid` int(10) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `readperm` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `price` smallint(6) unsigned NOT NULL DEFAULT '0',
  `filename` char(100) NOT NULL DEFAULT '',
  `description` char(100) NOT NULL DEFAULT '',
  `filetype` char(50) NOT NULL DEFAULT '',
  `filesize` int(10) unsigned NOT NULL DEFAULT '0',
  `attachment` char(100) NOT NULL DEFAULT '',
  `downloads` mediumint(8) NOT NULL DEFAULT '0',
  `isimage` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `thumb` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `remote` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`aid`),
  KEY `tid` (`tid`),
  KEY `pid` (`pid`,`aid`),
  KEY `uid` (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_attachments` */

insert  into `jrun_attachments`(`aid`,`tid`,`pid`,`dateline`,`readperm`,`price`,`filename`,`description`,`filetype`,`filesize`,`attachment`,`downloads`,`isimage`,`uid`,`thumb`,`remote`) values (2,12,25,1281593465,0,0,'code19882.rar','','application/zip',1708170,'month_1008/20100812f7873ec69a6b5288507c2524c7138a49.rar',1,0,1,0,0),(3,66,122,1282544827,0,0,'20100806Book.jpg','','image/pjpeg',833961,'month_1008/2010082386be3486112736cb2d78ed4330fc90f9.jpg',1,1,1,0,0),(7,160,303,1296206460,0,0,'未命名-2.jpg','','image/pjpeg',135032,'month_1101/20110128b8fec8fe7bd83fd23f4782ee61462beb.jpg',2,1,74,0,0),(6,160,303,1296206460,0,0,'4555.jpg','','image/pjpeg',126399,'month_1101/201101280a30e7f1bdcef9770d16f3a9c0de5a34.jpg',0,1,74,0,0),(8,160,303,1296206460,0,0,'未命名-2.jpg','','image/pjpeg',135032,'month_1101/201101283732b96e431d077450258c567aac7036.jpg',0,1,74,0,0),(9,160,303,1296206460,0,0,'未命名-3.jpg','','image/pjpeg',116507,'month_1101/20110128a73a8a3397509945d983178eddd5c3b0.jpg',0,1,74,0,0),(10,160,303,1296206460,0,0,'未命名-4.jpg','','image/pjpeg',127224,'month_1101/201101289684b244023964398fc37a8b5b60db5f.jpg',0,1,74,0,0),(11,160,303,1296206460,0,0,'未命名-5.jpg','','image/pjpeg',108949,'month_1101/2011012843096c23c53b88cc10efe1ff6a24870a.jpg',0,1,74,0,0),(15,174,484,1296355341,0,0,'演示.jpg','','image/pjpeg',300505,'month_1101/2011013063cf523dd5ceb8b52265899be94f12f2.jpg',98,1,74,0,0);

/*Table structure for table `jrun_attachpaymentlog` */

DROP TABLE IF EXISTS `jrun_attachpaymentlog`;

CREATE TABLE `jrun_attachpaymentlog` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `aid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `authorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `amount` int(10) unsigned NOT NULL DEFAULT '0',
  `netamount` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`aid`,`uid`),
  KEY `uid` (`uid`),
  KEY `authorid` (`authorid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_attachpaymentlog` */

/*Table structure for table `jrun_attachtypes` */

DROP TABLE IF EXISTS `jrun_attachtypes`;

CREATE TABLE `jrun_attachtypes` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `extension` char(12) NOT NULL DEFAULT '',
  `maxsize` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_attachtypes` */

/*Table structure for table `jrun_banned` */

DROP TABLE IF EXISTS `jrun_banned`;

CREATE TABLE `jrun_banned` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `ip1` smallint(3) NOT NULL DEFAULT '0',
  `ip2` smallint(3) NOT NULL DEFAULT '0',
  `ip3` smallint(3) NOT NULL DEFAULT '0',
  `ip4` smallint(3) NOT NULL DEFAULT '0',
  `admin` varchar(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_banned` */

/*Table structure for table `jrun_bbcodes` */

DROP TABLE IF EXISTS `jrun_bbcodes`;

CREATE TABLE `jrun_bbcodes` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `tag` varchar(100) NOT NULL DEFAULT '',
  `icon` varchar(255) NOT NULL,
  `replacement` text NOT NULL,
  `example` varchar(255) NOT NULL DEFAULT '',
  `explanation` text NOT NULL,
  `params` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `prompt` text NOT NULL,
  `nest` tinyint(3) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_bbcodes` */

insert  into `jrun_bbcodes`(`id`,`available`,`tag`,`icon`,`replacement`,`example`,`explanation`,`params`,`prompt`,`nest`) values (1,0,'fly','bb_fly.gif','<marquee width=\"90%\" behavior=\"alternate\" scrollamount=\"3\">{1}</marquee>','[fly]This is sample text[/fly]','使内容横向滚动，这个效果类似 HTML 的 marquee 标签，注意：这个效果只在 Internet Explorer 浏览器下有效。',1,'请输入滚动显示的文字:',1),(2,1,'flash','bb_flash.gif','<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" width=\"550\" height=\"400\"><param name=\"allowScriptAccess\" value=\"sameDomain\"><param name=\"movie\" value=\"{1}\"><param name=\"quality\" value=\"high\"><param name=\"bgcolor\" value=\"#ffffff\"><embed src=\"{1}\" quality=\"high\" bgcolor=\"#ffffff\" width=\"550\" height=\"400\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" /></object>','Flash Movie','嵌入 Flash 动画',1,'请输入 Flash 动画的 URL:',1),(3,1,'qq','bb_qq.gif','<a href=\"http://wpa.qq.com/msgrd?V=1&Uin={1}&amp;Site=[JspRun!]&amp;Menu=yes\" target=\"_blank\"><img src=\"http://wpa.qq.com/pa?p=1:{1}:1\" border=\"0\"></a>','[qq]688888[/qq]','显示 QQ 在线状态，点这个图标可以和他（她）聊天',1,'请输入显示在线状态 QQ 号码:',1),(4,0,'sup','bb_sup.gif','<sup>{1}</sup>','X[sup]2[/sup]','上标',1,'请输入上标文字：',1),(5,0,'sub','bb_sub.gif','<sub>{1}</sub>','X[sub]2[/sub]','下标',1,'请输入下标文字：',1);

/*Table structure for table `jrun_buddys` */

DROP TABLE IF EXISTS `jrun_buddys`;

CREATE TABLE `jrun_buddys` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `buddyid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `grade` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `description` char(255) NOT NULL DEFAULT '',
  `buddygroupid` mediumint(8) NOT NULL,
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_buddys` */

insert  into `jrun_buddys`(`uid`,`buddyid`,`grade`,`dateline`,`description`,`buddygroupid`) values (1,5,1,1284338556,'ldkjklf',1),(8,1,1,1284426386,'',1),(8,5,1,1284434516,'',1),(1,9,1,1284877793,'',1),(1,8,1,1284877799,'',1),(1,7,1,1284877807,'',1),(1,6,1,1284877812,'',5),(1,4,1,1284877817,'',11),(1,3,1,1284877823,'',1),(1,2,1,1284877827,'',1),(5,2,1,1284881625,'',1);

/*Table structure for table `jrun_caches` */

DROP TABLE IF EXISTS `jrun_caches`;

CREATE TABLE `jrun_caches` (
  `cachename` varchar(32) NOT NULL,
  `type` tinyint(3) unsigned NOT NULL,
  `dateline` int(10) unsigned NOT NULL,
  `expiration` int(10) unsigned NOT NULL,
  `data` mediumtext NOT NULL,
  PRIMARY KEY (`cachename`),
  KEY `expiration` (`type`,`expiration`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_caches` */

/*Table structure for table `jrun_campaigns` */

DROP TABLE IF EXISTS `jrun_campaigns`;

CREATE TABLE `jrun_campaigns` (
  `id` mediumint(8) unsigned NOT NULL,
  `type` tinyint(1) unsigned NOT NULL,
  `fid` smallint(6) unsigned NOT NULL,
  `tid` mediumint(8) unsigned NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `begintime` int(10) unsigned NOT NULL,
  `starttime` int(10) unsigned NOT NULL,
  `endtime` int(10) unsigned NOT NULL,
  `expiration` int(10) unsigned NOT NULL,
  `nextrun` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`,`type`),
  KEY `tid` (`tid`),
  KEY `nextrun` (`nextrun`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_campaigns` */

/*Table structure for table `jrun_comment` */

DROP TABLE IF EXISTS `jrun_comment`;

CREATE TABLE `jrun_comment` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `threadId` mediumint(9) NOT NULL,
  `commentName` varchar(255) NOT NULL,
  `clickTimes` int(11) NOT NULL DEFAULT '0',
  `img` varchar(50) NOT NULL DEFAULT '1.gif',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=780 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_comment` */

insert  into `jrun_comment`(`id`,`threadId`,`commentName`,`clickTimes`,`img`) values (649,122,'yes',0,'1.gif'),(627,118,'gdsg',0,'2.gif'),(650,122,'no',0,'2.gif'),(644,116,'高兴',1,'13.gif'),(626,118,'fsdf',0,'1.gif'),(624,117,'啊',1,'1.gif'),(625,117,'吧',0,'2.gif'),(615,115,'4564',0,'2.gif'),(614,115,'警方对旅客',0,'1.gif'),(611,88,'晕',2,'3.gif'),(610,88,'唉',0,'2.gif'),(609,113,'jfksdlf',0,'2.gif'),(608,113,'fjsdkl',0,'1.gif'),(607,112,'jflkds',0,'2.gif'),(606,112,'fdslj',0,'1.gif'),(605,111,'jfsdlk',0,'2.gif'),(604,111,'fldjs',0,'1.gif'),(603,110,'fdsfsd',0,'2.gif'),(602,110,'fsd',0,'1.gif'),(601,109,'fdsfsd',0,'2.gif'),(600,109,'fsdf',0,'1.gif'),(599,108,'jlkfsdjflkd',0,'2.gif'),(598,108,'fjdslk',0,'1.gif'),(597,107,'jlkfdsjflkds',0,'2.gif'),(596,107,'sdfjlk',0,'1.gif'),(595,106,'jfkldsjf',0,'2.gif'),(594,106,'fjdslk',0,'1.gif'),(593,105,'fjdsklf',0,'2.gif'),(592,105,'hfdsjk',0,'1.gif'),(591,104,'hfksdf',0,'2.gif'),(590,104,'fdsjh',0,'1.gif'),(589,103,'jfklsdjf',0,'2.gif'),(588,103,'fjldks',0,'1.gif'),(587,102,'jfklds',0,'2.gif'),(586,102,'in2',0,'1.gif'),(585,101,'d',0,'2.gif'),(584,101,'d',0,'1.gif'),(583,100,'b',0,'2.gif'),(582,100,'b',0,'1.gif'),(550,85,'我顶',1,'1.gif'),(646,119,'抵制日货',0,'2.gif'),(581,99,'a',0,'2.gif'),(549,85,'悲',1,'50.gif'),(548,85,'顶下',7,'4.gif'),(580,99,'a',0,'1.gif'),(579,98,'b',0,'2.gif'),(385,96,'O(∩_∩)O~',0,'2.gif'),(577,87,'昆明好强啊',2,'44.gif'),(384,96,'顶电信',0,'1.gif'),(379,94,'cc',0,'3.gif'),(378,94,'bb',0,'2.gif'),(377,94,'aa',0,'1.gif'),(576,87,'顶',2,'3.gif'),(524,95,'a',0,'1.gif'),(373,92,'',0,'1.gif'),(374,92,'',0,'2.gif'),(354,91,'还是无名好',0,'4.gif'),(353,91,'顶下',0,'3.gif'),(352,91,'看好',0,'2.gif'),(351,91,'不看好',0,'1.gif'),(347,89,'现在的人啊',0,'1.gif'),(346,89,'鸟人',1,'2.gif'),(345,89,'社会的败类',0,'3.gif'),(350,90,'很看好',0,'3.gif'),(349,90,'没戏',0,'2.gif'),(348,90,'顶摩托罗拉',1,'1.gif'),(525,95,'b',0,'2.gif'),(648,121,' ',1,'3.gif'),(647,121,' ',1,'3.gif'),(547,85,'HH',0,'1.gif'),(575,87,'酷',0,'2.gif'),(578,98,'aaaaaaa',0,'1.gif'),(565,93,'bbbbbbbb',0,'2.gif'),(516,97,'电信新闻1',0,'1.gif'),(515,97,'电信新闻2',0,'2.gif'),(564,93,'aaaaaaa',0,'1.gif'),(623,114,'呵呵',0,'2.gif'),(622,114,'哈哈',0,'1.gif'),(630,120,'aa',0,'1.gif'),(631,120,'bb',0,'2.gif'),(643,116,'悲',0,'1.gif'),(632,86,'和毛泽东有关',0,'7.gif'),(633,86,'靠自己的本事',2,'0.gif'),(634,86,'靠人缘',0,'3.gif'),(645,119,'小日本',0,'1.gif'),(651,123,'sdf',0,'1.gif'),(652,123,'sdf',0,'2.gif'),(653,124,'好',0,'0.gif'),(654,124,'恶心',0,'8.gif'),(655,124,'扰民',0,'3.gif'),(656,124,'腐败',0,'4.gif'),(657,124,'无所谓',0,'5.gif'),(665,127,'',0,'2.gif'),(664,127,'',0,'1.gif'),(660,126,'测试',0,'1.gif'),(661,126,'测试测试',0,'2.gif'),(662,125,'好',0,'1.gif'),(663,125,'不好',0,'2.gif'),(666,128,'',0,'1.gif'),(667,128,'',0,'2.gif'),(668,129,'',0,'1.gif'),(669,129,'',0,'2.gif'),(670,130,'',0,'1.gif'),(671,130,'',0,'2.gif'),(672,131,'',0,'1.gif'),(673,131,'',0,'2.gif'),(674,132,'',0,'1.gif'),(675,132,'',0,'2.gif'),(676,133,'',0,'1.gif'),(677,133,'',0,'2.gif'),(678,134,'',0,'1.gif'),(679,134,'',0,'2.gif'),(680,135,'',0,'1.gif'),(681,135,'',0,'2.gif'),(682,136,'1分',0,'1.gif'),(683,136,'2分',1,'2.gif'),(684,136,'3分',2,'3.gif'),(685,136,'4分',0,'4.gif'),(686,136,'5分',1,'5.gif'),(687,137,'一般',0,'49.gif'),(688,137,'很好',0,'2.gif'),(689,137,'非常好',0,'13.gif'),(690,137,'很差',0,'4.gif'),(691,137,'中等的好',0,'5.gif'),(698,139,'',0,'1.gif'),(694,138,'同意',2,'2.gif'),(695,138,'反对',0,'1.gif'),(696,138,'坚决反对',0,'1.gif'),(697,138,'坚决支持',0,'1.gif'),(699,139,'',2,'2.gif'),(700,140,'',0,'1.gif'),(701,140,'',0,'2.gif'),(702,141,'',0,'1.gif'),(703,141,'',0,'2.gif'),(704,142,'',0,'1.gif'),(705,142,'',0,'2.gif'),(706,143,'',0,'1.gif'),(707,143,'',0,'2.gif'),(708,144,'',0,'1.gif'),(709,144,'',0,'2.gif'),(710,145,'',0,'1.gif'),(711,145,'',0,'2.gif'),(712,146,'',0,'1.gif'),(713,146,'',0,'2.gif'),(714,147,'',0,'1.gif'),(715,147,'',0,'2.gif'),(716,148,'',0,'1.gif'),(717,148,'',0,'2.gif'),(718,149,'',0,'1.gif'),(719,149,'',0,'2.gif'),(720,150,'',0,'1.gif'),(721,150,'',0,'2.gif'),(722,151,'',0,'1.gif'),(723,151,'',0,'2.gif'),(724,152,'',0,'1.gif'),(725,152,'',0,'2.gif'),(726,153,'',0,'1.gif'),(727,153,'',0,'2.gif'),(728,154,'',0,'1.gif'),(729,154,'',0,'2.gif'),(730,155,'',0,'1.gif'),(731,155,'',0,'2.gif'),(732,156,'',0,'1.gif'),(733,156,'',0,'2.gif'),(734,157,'',0,'1.gif'),(735,157,'',0,'2.gif'),(736,158,'',0,'1.gif'),(737,158,'',0,'2.gif'),(738,159,'',0,'1.gif'),(739,159,'',0,'2.gif'),(740,160,'',0,'1.gif'),(741,160,'',0,'2.gif'),(742,161,'',0,'1.gif'),(743,161,'',0,'2.gif'),(744,162,'',0,'1.gif'),(745,162,'',0,'2.gif'),(746,163,'',0,'1.gif'),(747,163,'',0,'2.gif'),(748,164,'',0,'1.gif'),(749,164,'',0,'2.gif'),(750,165,'',0,'1.gif'),(751,165,'',0,'2.gif'),(752,166,'',0,'1.gif'),(753,166,'',0,'2.gif'),(754,167,'',0,'1.gif'),(755,167,'',0,'2.gif'),(756,168,'',0,'1.gif'),(757,168,'',0,'2.gif'),(758,169,'',0,'1.gif'),(759,169,'',0,'2.gif'),(760,170,'',0,'1.gif'),(761,170,'',0,'2.gif'),(762,171,'',0,'1.gif'),(763,171,'',0,'2.gif'),(764,172,'',0,'1.gif'),(765,172,'',0,'2.gif'),(766,173,'',0,'1.gif'),(767,173,'',0,'2.gif'),(768,174,'',0,'1.gif'),(769,174,'',0,'2.gif'),(770,175,'',0,'1.gif'),(771,175,'',0,'2.gif'),(772,176,'',0,'1.gif'),(773,176,'',0,'2.gif'),(774,177,'',0,'1.gif'),(775,177,'',0,'2.gif'),(776,178,'',0,'1.gif'),(777,178,'',0,'2.gif'),(778,179,'',0,'1.gif'),(779,179,'',0,'2.gif');

/*Table structure for table `jrun_creditslog` */

DROP TABLE IF EXISTS `jrun_creditslog`;

CREATE TABLE `jrun_creditslog` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fromto` char(15) NOT NULL DEFAULT '',
  `sendcredits` tinyint(1) NOT NULL DEFAULT '0',
  `receivecredits` tinyint(1) NOT NULL DEFAULT '0',
  `send` int(10) unsigned NOT NULL DEFAULT '0',
  `receive` int(10) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `operation` char(3) NOT NULL DEFAULT '',
  KEY `uid` (`uid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_creditslog` */

insert  into `jrun_creditslog`(`uid`,`fromto`,`sendcredits`,`receivecredits`,`send`,`receive`,`dateline`,`operation`) values (1,'zhongwei',2,2,10,0,1284538207,'TFR'),(5,'admin',2,2,0,8,1284538207,'RCV');

/*Table structure for table `jrun_crons` */

DROP TABLE IF EXISTS `jrun_crons`;

CREATE TABLE `jrun_crons` (
  `cronid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `type` enum('user','system') NOT NULL DEFAULT 'user',
  `name` char(50) NOT NULL DEFAULT '',
  `filename` char(50) NOT NULL DEFAULT '',
  `lastrun` int(10) unsigned NOT NULL DEFAULT '0',
  `nextrun` int(10) unsigned NOT NULL DEFAULT '0',
  `weekday` tinyint(1) NOT NULL DEFAULT '0',
  `day` tinyint(2) NOT NULL DEFAULT '0',
  `hour` tinyint(2) NOT NULL DEFAULT '0',
  `minute` char(36) NOT NULL DEFAULT '',
  PRIMARY KEY (`cronid`),
  KEY `nextrun` (`available`,`nextrun`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_crons` */

insert  into `jrun_crons`(`cronid`,`available`,`type`,`name`,`filename`,`lastrun`,`nextrun`,`weekday`,`day`,`hour`,`minute`) values (1,1,'system','清空今日发帖数','todayposts_daily.jsp',1329768696,1329840000,-1,-1,0,'0'),(2,1,'system','清空本月在线时间','onlinetime_monthly.jsp',1328028062,1330531200,-1,1,0,'0'),(3,1,'system','每日数据清理','cleanup_daily.jsp',1329768696,1329773400,-1,-1,5,'30'),(4,1,'system','生日统计与邮件祝福','birthdays_daily.jsp',1329768696,1329840000,-1,-1,0,'0'),(5,1,'system','主题回复通知','notify_daily.jsp',1329768696,1329771600,-1,-1,5,'00'),(6,1,'system','每日公告清理','announcements_daily.jsp',1329768696,1329840000,-1,-1,0,'0'),(7,1,'system','限时操作清理','threadexpiries_hourly.jsp',1329768696,1329771600,-1,-1,5,'0'),(8,1,'system','论坛推广清理','promotions_hourly.jsp',1329768696,1329840000,-1,-1,0,'00'),(9,1,'system','每月主题清理','cleanup_monthly.jsp',1328076264,1330552800,-1,1,6,'00'),(12,1,'system','道具自动补货','magics_daily.jsp',1329768696,1329840000,-1,-1,0,'0'),(13,1,'system','每日验证问答更新','secqaa_daily.jsp',1329768696,1329775200,-1,-1,6,'0'),(14,1,'system','每日标签更新','tags_daily.jsp',1329768696,1329840000,-1,-1,0,'0'),(15,0,'system','勋章自动授予','awardmedals.jsp',0,1170600452,-1,-1,0,'0');

/*Table structure for table `jrun_debateposts` */

DROP TABLE IF EXISTS `jrun_debateposts`;

CREATE TABLE `jrun_debateposts` (
  `pid` int(10) unsigned NOT NULL DEFAULT '0',
  `stand` tinyint(1) NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `voters` mediumint(10) unsigned NOT NULL DEFAULT '0',
  `voterids` text NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `pid` (`pid`,`stand`),
  KEY `tid` (`tid`,`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_debateposts` */

/*Table structure for table `jrun_debates` */

DROP TABLE IF EXISTS `jrun_debates`;

CREATE TABLE `jrun_debates` (
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `starttime` int(10) unsigned NOT NULL DEFAULT '0',
  `endtime` int(10) unsigned NOT NULL DEFAULT '0',
  `affirmdebaters` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `negadebaters` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `affirmvotes` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `negavotes` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `umpire` varchar(15) NOT NULL DEFAULT '',
  `winner` tinyint(1) NOT NULL DEFAULT '0',
  `bestdebater` varchar(50) NOT NULL DEFAULT '',
  `affirmpoint` text NOT NULL,
  `negapoint` text NOT NULL,
  `umpirepoint` text NOT NULL,
  `affirmvoterids` text NOT NULL,
  `negavoterids` text NOT NULL,
  `affirmreplies` mediumint(8) unsigned NOT NULL,
  `negareplies` mediumint(8) unsigned NOT NULL,
  PRIMARY KEY (`tid`),
  KEY `uid` (`uid`,`starttime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_debates` */

insert  into `jrun_debates`(`tid`,`uid`,`starttime`,`endtime`,`affirmdebaters`,`negadebaters`,`affirmvotes`,`negavotes`,`umpire`,`winner`,`bestdebater`,`affirmpoint`,`negapoint`,`umpirepoint`,`affirmvoterids`,`negavoterids`,`affirmreplies`,`negareplies`) values (6,1,1281063506,0,0,0,0,0,'admin',0,'','fddddddddddddddddddddddd','ssssssssssssssssssssssssssssssssssssss','','','',0,0);

/*Table structure for table `jrun_failedlogins` */

DROP TABLE IF EXISTS `jrun_failedlogins`;

CREATE TABLE `jrun_failedlogins` (
  `ip` char(15) NOT NULL DEFAULT '',
  `count` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `lastupdate` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ip`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_failedlogins` */

insert  into `jrun_failedlogins`(`ip`,`count`,`lastupdate`) values ('59.42.126.20',2,1325748307),('125.89.10.36',3,1325745291),('125.89.11.175',2,1324949074),('125.89.9.159',1,1324605695),('119.132.75.238',3,1324524303),('119.132.73.101',5,1324523454);

/*Table structure for table `jrun_faqs` */

DROP TABLE IF EXISTS `jrun_faqs`;

CREATE TABLE `jrun_faqs` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `fpid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `identifier` varchar(20) NOT NULL,
  `keyword` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `displayplay` (`displayorder`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_faqs` */

insert  into `jrun_faqs`(`id`,`fpid`,`displayorder`,`identifier`,`keyword`,`title`,`message`) values (1,0,1,'','','用户须知',''),(2,1,1,'','','我必须要注册吗？','这取决于管理员如何设置 JspRun! 论坛的用户组权限选项，您甚至有可能必须在注册成正式用户后后才能浏览帖子。当然，在通常情况下，您至少应该是正式用户才能发新帖和回复已有帖子。请 <a href=\"register.jsp\" target=\"_blank\">点击这里</a> 免费注册成为我们的新用户！\r\n<br /><br />强烈建议您注册，这样会得到很多以游客身份无法实现的功能。'),(3,1,2,'login','登录帮助','我如何登录论坛？','如果您已经注册成为该论坛的会员，哪么您只要通过访问页面右上的<a href=\"logging.jsp?action=login\" target=\"_blank\">登录</a>，进入登陆界面填写正确的用户名和密码（如果您设有安全提问，请选择正确的安全提问并输入对应的答案），点击“提交”即可完成登陆如果您还未注册请点击这里。<br /><br />\r\n如果需要保持登录，请选择相应的 Cookie 时间，在此时间范围内您可以不必输入密码而保持上次的登录状态。'),(4,1,3,'','','忘记我的登录密码，怎么办？','当您忘记了用户登录的密码，您可以通过注册时填写的电子邮箱重新设置一个新的密码。点击登录页面中的 <a href=\"member.jsp?action=lostpasswd\" target=\"_blank\">取回密码</a>，按照要求填写您的个人信息，系统将自动发送重置密码的邮件到您注册时填写的 Email 信箱中。如果您的 Email 已失效或无法收到信件，请与论坛管理员联系。'),(5,0,2,'','','帖子相关操作',''),(6,0,3,'','','基本功能操作',''),(7,0,4,'','','其他相关问题',''),(8,1,4,'','','我如何使用个性化头像','在<a href=\"memcp.jsp\" target=\"_blank\">控制面板</a>中的“编辑个人资料”，有一个“头像”的选项，可以使用论坛自带的头像或者自定义的头像。'),(9,1,5,'','','我如何修改登录密码','在<a href=\"memcp.jsp\" target=\"_blank\">控制面板</a>中的“编辑个人资料”，填写“原密码”，“新密码”，“确认新密码”。点击“提交”，即可修改。'),(10,1,6,'','','我如何使用个性化签名和昵称','在<a href=\"memcp.jsp\" target=\"_blank\">控制面板</a>中的“编辑个人资料”，有一个“昵称”和“个人签名”的选项，可以在此设置。'),(11,5,1,'','','我如何发表新主题','在论坛版块中，点“新帖”，如果有权限，您可以看到有“投票，悬赏，活动，交易”，点击即可进入功能齐全的发帖界面。\r\n<br /><br />注意：一般论坛都设置为高级别的用户组才能发布这四类特殊主题。如发布普通主题，直接点击“新帖”，当然您也可以使用版块下面的“快速发帖”发表新帖(如果此选项打开)。一般论坛都设置为需要登录后才能发帖。'),(12,5,2,'','','我如何发表回复','回复有分三种：第一、贴子最下方的快速回复； 第二、在您想回复的楼层点击右下方“回复”； 第三、完整回复页面，点击本页“新帖”旁边的“回复”。'),(13,5,3,'','','我如何编辑自己的帖子','在帖子的右下角，有编辑，回复，报告等选项，点击编辑，就可以对帖子进行编辑。'),(14,5,4,'','','我如何出售购买主题','<li>出售主题：\r\n当您进入发贴界面后，如果您所在的用户组有发买卖贴的权限，在“售价(金钱)”后面填写主题的价格，这样其他用户在查看这个帖子的时候就需要进入交费的过程才可以查看帖子。</li>\r\n<li>购买主题：\r\n浏览你准备购买的帖子，在帖子的相关信息的下面有[查看付款记录] [购买主题] [返回上一页] \r\n等链接，点击“购买主题”进行购买。</li>'),(15,5,5,'','','我如何出售购买附件','<li>上传附件一栏有个售价的输入框，填入出售价格即可实现需要支付才可下载附件的功能。</li>\r\n<li>点击帖子中[购买附件]按钮或点击附件的下载链接会跳转至附件购买页面，确认付款的相关信息后点提交按钮，即可得到附件的下载权限。只需购买一次，就有该附件的永远下载权限。</li>'),(16,5,6,'','','我如何上传附件','<li>发表新主题的时候上传附件，步骤为：写完帖子标题和内容后点上传附件右方的浏览，然后在本地选择要上传附件的具体文件名，最后点击发表话题。</li>\r\n<li>发表回复的时候上传附件，步骤为：写完回复楼主的内容，然后点上传附件右方的浏览，找到需要上传的附件，点击发表回复。</li>'),(17,5,7,'','','我如何实现发帖时图文混排效果','<li>发表新主题的时候点击上传附件左侧的“[插入]”链接把附件标记插入到帖子中适当的位置即可。</li>'),(18,5,8,'Wingotechcode','Wingotech!代码','我如何使用Wingotech代码','<table width=\"99%\" cellpadding=\"2\" cellspacing=\"2\">\r\n  <tr>\r\n    <th width=\"50%\">Wingotech!代码</th>\r\n    <th width=\"402\">效果</th>\r\n  </tr>\r\n  <tr>\r\n    <td>[b]粗体文字 Abc[/b]</td>\r\n    <td><strong>粗体文字 Abc</strong></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[i]斜体文字 Abc[/i]</td>\r\n    <td><em>斜体文字 Abc</em></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[u]下划线文字 Abc[/u]</td>\r\n    <td><u>下划线文字 Abc</u></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[color=red]红颜色[/color]</td>\r\n    <td><font color=\"red\">红颜色</font></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[size=3]文字大小为 3[/size] </td>\r\n    <td><font size=\"3\">文字大小为 3</font></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[font=仿宋]字体为仿宋[/font] </td>\r\n    <td><font face=\"仿宋\">字体为仿宋</font></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[align=Center]内容居中[/align] </td>\r\n    <td><div align=\"center\">内容居中</div></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[url]http://www.wingotech.com[/url]</td>\r\n    <td><a href=\"http://www.wingotech.com\" target=\"_blank\">http://www.wingotech.com</a>（超级链接）</td>\r\n  </tr>\r\n  <tr>\r\n    <td>[url=http://www.wingotech.com]Wingotech 论坛[/url]</td>\r\n    <td><a href=\"http://www.wingotech.com\" target=\"_blank\">Wingotech 论坛</a>（超级链接）</td>\r\n  </tr>\r\n  <tr>\r\n    <td>[email]myname@mydomain.com[/email]</td>\r\n    <td><a href=\"mailto:myname@mydomain.com\">myname@mydomain.com</a>（E-mail链接）</td>\r\n  </tr>\r\n  <tr>\r\n    <td>[email=support@wingotech.com]Wingotech! 技术支持[/email]</td>\r\n    <td><a href=\"mailto:support@Wingotech.com\">Wingotech! 技术支持（E-mail链接）</a></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[quote]Wingotech! 是由网佳科技有限公司开发的论坛软件[/quote] </td>\r\n    <td><div style=\"font-size: 12px\"><br /><br /><div class=\"quote\"><h5>引用:</h5><blockquote>原帖由 <i>admin</i> 于 2006-12-26 08:45 发表<br />Wingotech! 是由网佳科技有限公司开发的论坛软件</blockquote></div></td>\r\n  </tr>\r\n   <tr>\r\n    <td>[code]Wingotech! 是由网佳科技有限公司开发的论坛软件[/code] </td>\r\n    <td><div style=\"font-size: 12px\"><br /><br /><div class=\"blockcode\"><h5>代码:</h5><code id=\"code0\">Wingotech! 是网佳科技有限公司开发的论坛软件</code></div></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[hide]隐藏内容 Abc[/hide]</td>\r\n    <td>效果:只有当浏览者回复本帖时，才显示其中的内容，否则显示为“<b>**** 隐藏信息 跟帖后才能显示 *****</b>”</td>\r\n  </tr>\r\n  <tr>\r\n    <td>[hide=20]隐藏内容 Abc[/hide]</td>\r\n    <td>效果:只有当浏览者积分高于 20 点时，才显示其中的内容，否则显示为“<b>**** 隐藏信息 积分高于 20 点才能显示 ****</b>”</td>\r\n  </tr>\r\n  <tr>\r\n    <td>[list][*]列表项 #1[*]列表项 #2[*]列表项 #3[/list]</td>\r\n    <td><ul>\r\n      <li>列表项 ＃1</li>\r\n      <li>列表项 ＃2</li>\r\n      <li>列表项 ＃3 </li>\r\n    </ul></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[img]http://www.wingotech.com] </td>\r\n    <td>帖子内显示为：<img src=\"http://www.wingotech.com\" /></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[img=88,31]http://www.wingotech.com] </td>\r\n    <td>帖子内显示为：<img src=\"http://www.wingotech.com\" /></td>\r\n  </tr> <tr>\r\n    <td>[media=400,300,1]多媒体 URL[/media]</td>\r\n    <td>帖子内嵌入多媒体，宽 400 高 300 自动播放</td>\r\n  </tr>\r\n <tr>\r\n    <td>[fly]飞行的效果[/fly]</td>\r\n    <td><marquee scrollamount=\"3\" behavior=\"alternate\" width=\"90%\">飞行的效果</marquee></td>\r\n  </tr>\r\n  <tr>\r\n    <td>[flash]Flash网页地址 [/flash] </td>\r\n    <td>帖子内嵌入 Flash 动画</td>\r\n  </tr>\r\n  <tr>\r\n    <td>[qq]123456789[/qq]</td>\r\n    <td>在帖子内显示 QQ 在线状态，点这个图标可以和他（她）聊天</td>\r\n  </tr>\r\n  <tr>\r\n    <td>X[sup]2[/sup]</td>\r\n    <td>X<sup>2</sup></td>\r\n  </tr>\r\n  <tr>\r\n    <td>X[sub]2[/sub]</td>\r\n    <td>X<sub>2</sub></td>\r\n  </tr>\r\n  \r\n</table>'),(19,6,1,'','','我如何使用短消息功能','您登录后，点击导航栏上的短消息按钮，即可进入短消息管理。\r\n点击[发送短消息]按钮，在\"发送到\"后输入收信人的用户名，填写完标题和内容，点提交(或按 Ctrl+Enter 发送)即可发出短消息。\r\n<br /><br />如果要保存到发件箱，以在提交前勾选\"保存到发件箱中\"前的复选框。\r\n<ul>\r\n<li>点击收件箱可打开您的收件箱查看收到的短消息。</li>\r\n<li>点击发件箱可查看保存在发件箱里的短消息。 </li>\r\n<li>点击已发送来查看对方是否已经阅读您的短消息。 </li>\r\n<li>点击搜索短消息就可通过关键字，发信人，收信人，搜索范围，排序类型等一系列条件设定来找到您需要查找的短消息。 </li>\r\n<li>点击导出短消息可以将自己的短消息导出htm文件保存在自己的电脑里。 </li>\r\n<li>点击忽略列表可以设定忽略人员，当这些被添加的忽略用户给您发送短消息时将不予接收。</li>\r\n</ul>'),(20,6,2,'','','我如何向好友群发短消息','登录论坛后，点击短消息，然后点发送短消息，如果有好友的话，好友群发后面点击全选，可以给所有的好友群发短消息。'),(21,6,3,'','','我如何查看论坛会员数据','点击导航栏上面的会员，然后显示的是此论坛的会员数据。注：需要论坛管理员开启允许你查看会员资料才可看到。'),(22,6,4,'','','我如何使用搜索','点击导航栏上面的搜索，输入搜索的关键字并选择一个范围，就可以检索到您有权限访问论坛中的相关的帖子。'),(23,6,5,'','','我如何使用“我的”功能','<li>会员必须首先<a href=\"logging.jsp?action=login\" target=\"_blank\">登录</a>，没有用户名的请先<a href=\"register.jsp\" target=\"_blank\">注册</a>；</li>\r\n<li>登录之后在论坛的左上方会出现一个“我的”的超级链接，点击这个链接之后就可进入到有关于您的信息。</li>'),(24,7,1,'','','我如何向管理员报告帖子','打开一个帖子，在帖子的右下角可以看到：“编辑”、“引用”、“报告”、“评分”、“回复”等等几个按钮，点击其中的“报告”按钮进入报告页面，填写好“我的意见”，单击“报告”按钮即可完成报告某个帖子的操作。'),(25,7,2,'','','我如何“打印”，“推荐”，“订阅”，“收藏”帖子','当你浏览一个帖子时，在它的右上角可以看到：“打印”、“推荐”、“订阅”、“收藏”，点击相对应的文字连接即可完成相关的操作。'),(26,7,3,'','','我如何设置论坛好友','设置论坛好友有3种简单的方法。\r\n<ul>\r\n<li>当您浏览帖子的时候可以点击“发表时间”右侧的“加为好友”设置论坛好友。</li>\r\n<li>当您浏览某用户的个人资料时，可以点击头像下方的“加为好友”设置论坛好友。</li>\r\n<li>您也可以在控制面板中的好友列表增加您的论坛好友。</li>\r\n<ul>'),(27,7,4,'','','我如何使用RSS订阅','在论坛的首页和进入版块的页面的右上角就会出现一个rss订阅的小图标<img src=\"images/common/xml.gif\" border=\"0\">，鼠标点击之后将出现本站点的rss地址，你可以将此rss地址放入到你的rss阅读器中进行订阅。'),(28,7,5,'','','我如何清除Cookies','cookie是由浏览器保存在系统内的，在论坛的右下角提供有\"清除 Cookies\"的功能，点击后即可帮您清除系统内存储的Cookies。 <br /><br />\r\n以下介绍3种常用浏览器的Cookies清除方法(注：此方法为清除全部的Cookies,请谨慎使用)\r\n<ul>\r\n<li>Internet Explorer: 工具（选项）内的Internet选项→常规选项卡内，IE6直接可以看到删除Cookies的按钮点击即可，IE7为“浏 览历史记录”选项内的删除点击即可清空Cookies。对于Maxthon,腾讯TT等IE核心浏览器一样适用。 </li>\r\n<li>FireFox:工具→选项→隐私→Cookies→显示Cookie里可以对Cookie进行对应的删除操作。 </li>\r\n<li>Opera:工具→首选项→高级→Cookies→管理Cookies即可对Cookies进行删除的操作。</li>\r\n</ul>'),(29,7,6,'','','我如何联系管理员','您可以通过论坛底部右下角的“联系我们”链接快速的发送邮件与我们联系。也可以通过管理团队中的用户资料发送短消息给我们。'),(30,7,7,'','','我如何开通个人空间','如果您有权限开通“我的个人空间”，当用户登录论坛以后在论坛首页，用户名的右方点击开通我的个人空间，进入个人空间的申请页面。'),(31,7,8,'','','我如何将自己的主题加入个人空间','如果您有权限开通“我的个人空间”，在您发表的主题上方点击“加入个人空间”，您发表的主题以及回复都会加入到您空间的日志里。'),(32,5,9,'smilies','表情','我如何使用表情代码','表情是一些用字符表示的表情符号，如果打开表情功能，JspRun! 会把一些符号转换成小图像，显示在帖子中，更加美观明了。目前支持下面这些表情：<br /><br />\r\n<table cellspacing=\"0\" cellpadding=\"4\" width=\"30%\" align=\"center\">\r\n<tr><th width=\"25%\" align=\"center\">表情符号</td>\r\n<th width=\"75%\" align=\"center\">对应图像</td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:)</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/smile.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:(</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/sad.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:D</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/biggrin.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:\\\'(</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/cry.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:@</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/huffy.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:o</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/shocked.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:P</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/tongue.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:#</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/shy.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">;P</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/titter.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:L</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/sweat.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:Q</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/mad.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:lol</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/lol.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:hug:</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/hug.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:victory:</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/victory.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:time:</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/time.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:kiss:</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/kiss.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:handshake</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/handshake.gif\" alt=\"\" /></td>\r\n</tr>\r\n<tr>\r\n<td width=\"25%\" align=\"center\">:call:</td>\r\n<td width=\"75%\" align=\"center\"><img src=\"images/smilies/default/call.gif\" alt=\"\" /></td>\r\n</tr>\r\n</table>\r\n</div></div>\r\n<br />'),(33,0,5,'','','论坛高级功能使用',''),(34,33,0,'forwardmessagelist','','论坛快速跳转关键字列表','Wingotech! 支持自定义快速跳转页面，当某些操作完成后，可以不显示提示信息，直接跳转到新的页面，从而方便用户进行下一步操作，避免等待。 在实际使用当中，您根据需\r\n\r\n要，把关键字添加到快速跳转设置里面(后台 -- 基本设置 --  界面与显示方式 -- [<a href=\"admincp.jsp?action=settings&do=styles&frames=yes\" target=\"_blank\">提示信\r\n\r\n息跳转设置</a> ])，让某些信息不显示而实现快速跳转。以下是 Wingotech! 当中的一些常用信息的关键字:\r\n</br></br>\r\n\r\n<table width=\"99%\" cellpadding=\"2\" cellspacing=\"2\">\r\n  <tr>\r\n    <td width=\"50%\">关键字</td>\r\n    <td width=\"50%\">提示信息页面或者作用</td>\r\n  </tr>\r\n  <tr>\r\n    <td>login_succeed</td>\r\n    <td>登录成功</td>\r\n  </tr>\r\n  <tr>\r\n    <td>logout_succeed</td>\r\n    <td>退出登录成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>thread_poll_succeed</td>\r\n    <td>投票成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>thread_rate_succeed</td>\r\n    <td>评分成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>register_succeed</td>\r\n    <td>注册成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>usergroups_join_succeed</td>\r\n    <td>加入扩展组成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td height=\"22\">usergroups_exit_succeed</td>\r\n    <td>退出扩展组成功</td>\r\n  </tr>\r\n  <tr>\r\n    <td>usergroups_update_succeed</td>\r\n    <td>更新扩展组成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>buddy_update_succeed</td>\r\n    <td>好友更新成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>post_edit_succeed</td>\r\n    <td>编辑帖子成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>post_edit_delete_succeed</td>\r\n    <td>删除帖子成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>post_reply_succeed</td>\r\n    <td>回复成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>post_newthread_succeed</td>\r\n    <td>发表新主题成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>post_reply_blog_succeed</td>\r\n    <td>文集评论发表成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>post_newthread_blog_succeed</td>\r\n    <td>blog 发表成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>profile_avatar_succeed</td>\r\n    <td>头像设置成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>profile_succeed</td>\r\n    <td>个人资料更新成功</td>\r\n  </tr>\r\n    <tr>\r\n    <td>pm_send_succeed</td>\r\n    <td>短消息发送成功</td>\r\n  </tr>\r\n  </tr>\r\n    <tr>\r\n    <td>pm_delete_succeed</td>\r\n    <td>短消息删除成功</td>\r\n  </tr>\r\n  </tr>\r\n    <tr>\r\n    <td>pm_ignore_succeed</td>\r\n    <td>短消息忽略列表更新</td>\r\n  </tr>\r\n    <tr>\r\n    <td>admin_succeed</td>\r\n    <td>管理操作成功〔注意：设置此关键字后，所有管理操作完毕都将直接跳转〕</td>\r\n  </tr>\r\n    <tr>\r\n    <td>admin_succeed_next </td>\r\n    <td>管理成功并将跳转到下一个管理动作</td>\r\n  </tr> \r\n    <tr>\r\n    <td>search_redirect</td>\r\n    <td>搜索完成，进入搜索结果列表</td>\r\n  </tr>\r\n</table>');

/*Table structure for table `jrun_favorites` */

DROP TABLE IF EXISTS `jrun_favorites`;

CREATE TABLE `jrun_favorites` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fid` smallint(6) unsigned NOT NULL DEFAULT '0',
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_favorites` */

insert  into `jrun_favorites`(`uid`,`tid`,`fid`) values (1,29,0),(5,87,0),(20,129,0),(51,129,0),(109,0,29);

/*Table structure for table `jrun_forumfields` */

DROP TABLE IF EXISTS `jrun_forumfields`;

CREATE TABLE `jrun_forumfields` (
  `fid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `password` varchar(12) NOT NULL DEFAULT '',
  `icon` varchar(255) NOT NULL DEFAULT '',
  `postcredits` varchar(255) NOT NULL DEFAULT '',
  `replycredits` varchar(255) NOT NULL DEFAULT '',
  `getattachcredits` varchar(255) NOT NULL DEFAULT '',
  `postattachcredits` varchar(255) NOT NULL DEFAULT '',
  `digestcredits` varchar(255) NOT NULL DEFAULT '',
  `redirect` varchar(255) NOT NULL DEFAULT '',
  `attachextensions` varchar(255) NOT NULL DEFAULT '',
  `formulaperm` text NOT NULL,
  `moderators` text NOT NULL,
  `rules` text NOT NULL,
  `threadtypes` text NOT NULL,
  `viewperm` text NOT NULL,
  `postperm` text NOT NULL,
  `replyperm` text NOT NULL,
  `getattachperm` text NOT NULL,
  `postattachperm` text NOT NULL,
  `keywords` text NOT NULL,
  `supe_pushsetting` text NOT NULL,
  `modrecommend` text NOT NULL,
  `tradetypes` text NOT NULL,
  `typemodels` mediumtext NOT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_forumfields` */

insert  into `jrun_forumfields`(`fid`,`description`,`password`,`icon`,`postcredits`,`replycredits`,`getattachcredits`,`postattachcredits`,`digestcredits`,`redirect`,`attachextensions`,`formulaperm`,`moderators`,`rules`,`threadtypes`,`viewperm`,`postperm`,`replyperm`,`getattachperm`,`postattachperm`,`keywords`,`supe_pushsetting`,`modrecommend`,`tradetypes`,`typemodels`) values (1,'','','','','','','','','','','','','','','','','','','','','','','',''),(2,'ghghgf','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','aaaaaaaaaaaaaa','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','bbbbbbbbbbbb','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(6,'','','','','','','','','','','','','','','','','','','','','','','',''),(11,'','','','','','','','','','','','','','','','','','','','','','','',''),(5,'','','','','','','','','','','','','','','','','','','','','','','',''),(8,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(9,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(10,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:7:{s:4:\"open\";s:1:\"1\";s:9:\"cachelife\";i:900;s:3:\"num\";i:10;s:4:\"sort\";N;s:9:\"maxlength\";i:0;s:7:\"orderby\";N;s:8:\"dateline\";i:0;}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(12,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(13,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(14,'','','','','','','','','','','','','','','','','','','','','','','',''),(15,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(16,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(17,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(18,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(19,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(20,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(21,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(22,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(23,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','wingotech','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(24,'','','','','','','','','','','a:2:{i:0;s:0:\"\";i:1;s:0:\"\";}','','','','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	17	','','','a:1:{s:4:\"open\";s:1:\"0\";}','','a:3:{i:1;a:1:{s:4:\"name\";s:18:\"房屋交易信息\";}i:2;a:1:{s:4:\"name\";s:18:\"车票交易信息\";}i:3;a:1:{s:4:\"name\";s:18:\"兴趣交友信息\";}}'),(26,'','','','','','','','','','','','yanyang1230	cannymok','','','','','','','','','','','',''),(27,'','','','','','','','','','','','','','','','','','','','','','','',''),(28,'','','','','','','','','','','','','','','','','','','','','','','',''),(29,'','','','','','','','','','','','','','','','','','','','','','','','');

/*Table structure for table `jrun_forumlinks` */

DROP TABLE IF EXISTS `jrun_forumlinks`;

CREATE TABLE `jrun_forumlinks` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `url` varchar(100) NOT NULL DEFAULT '',
  `description` mediumtext NOT NULL,
  `logo` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_forumlinks` */

insert  into `jrun_forumlinks`(`id`,`displayorder`,`name`,`url`,`description`,`logo`) values (1,0,'wingotech','http://www.wingotech.com','网佳公司','images/logo.gif'),(2,1,'aaa','http://www.aa.com','haha','');

/*Table structure for table `jrun_forumrecommend` */

DROP TABLE IF EXISTS `jrun_forumrecommend`;

CREATE TABLE `jrun_forumrecommend` (
  `fid` smallint(6) unsigned NOT NULL,
  `tid` mediumint(8) unsigned NOT NULL,
  `displayorder` tinyint(1) NOT NULL,
  `subject` char(80) NOT NULL,
  `author` char(15) NOT NULL,
  `authorid` mediumint(8) NOT NULL,
  `moderatorid` mediumint(8) NOT NULL,
  `expiration` int(10) unsigned NOT NULL,
  PRIMARY KEY (`tid`),
  KEY `displayorder` (`fid`,`displayorder`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_forumrecommend` */

/*Table structure for table `jrun_forums` */

DROP TABLE IF EXISTS `jrun_forums`;

CREATE TABLE `jrun_forums` (
  `fid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `fup` smallint(6) unsigned NOT NULL DEFAULT '0',
  `type` enum('group','forum','sub') NOT NULL DEFAULT 'forum',
  `name` char(50) NOT NULL DEFAULT '',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `styleid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `threads` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `posts` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `todayposts` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `lastpost` char(110) NOT NULL DEFAULT '',
  `allowsmilies` tinyint(1) NOT NULL DEFAULT '0',
  `allowhtml` tinyint(1) NOT NULL DEFAULT '0',
  `allowbbcode` tinyint(1) NOT NULL DEFAULT '0',
  `allowimgcode` tinyint(1) NOT NULL DEFAULT '0',
  `allowmediacode` tinyint(1) NOT NULL DEFAULT '0',
  `allowanonymous` tinyint(1) NOT NULL DEFAULT '0',
  `allowshare` tinyint(1) NOT NULL DEFAULT '0',
  `allowpostspecial` smallint(6) unsigned NOT NULL DEFAULT '0',
  `allowspecialonly` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `alloweditrules` tinyint(1) NOT NULL DEFAULT '0',
  `recyclebin` tinyint(1) NOT NULL DEFAULT '0',
  `modnewposts` tinyint(1) NOT NULL DEFAULT '0',
  `jammer` tinyint(1) NOT NULL DEFAULT '0',
  `disablewatermark` tinyint(1) NOT NULL DEFAULT '0',
  `inheritedmod` tinyint(1) NOT NULL DEFAULT '0',
  `autoclose` smallint(6) NOT NULL DEFAULT '0',
  `forumcolumns` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `threadcaches` tinyint(1) NOT NULL DEFAULT '0',
  `allowpaytoauthor` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `alloweditpost` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `simple` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`fid`),
  KEY `forum` (`status`,`type`,`displayorder`),
  KEY `fup` (`fup`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_forums` */

insert  into `jrun_forums`(`fid`,`fup`,`type`,`name`,`status`,`displayorder`,`styleid`,`threads`,`posts`,`todayposts`,`lastpost`,`allowsmilies`,`allowhtml`,`allowbbcode`,`allowimgcode`,`allowmediacode`,`allowanonymous`,`allowshare`,`allowpostspecial`,`allowspecialonly`,`alloweditrules`,`recyclebin`,`modnewposts`,`jammer`,`disablewatermark`,`inheritedmod`,`autoclose`,`forumcolumns`,`threadcaches`,`allowpaytoauthor`,`alloweditpost`,`simple`) values (1,0,'group','社区体验',1,1,0,0,0,0,'',0,0,1,1,1,0,1,63,0,0,0,0,0,0,0,0,0,0,1,1,0),(2,1,'forum','营业厅体验',1,0,0,0,21,0,'130	产品体验区隆重上线	1296393021	CDIMS03',1,0,1,1,1,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(6,0,'group','调查专题',1,2,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0),(11,0,'group','管理员讨论区',1,4,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0),(5,0,'group','论坛公告',1,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0),(23,14,'forum','意见反馈',1,2,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(8,6,'forum','产品体验调查',1,0,0,4,18,0,'133	电信营业厅产品体验	1296315170	CDIMS02',1,0,1,1,0,0,1,3,0,0,0,0,0,0,1,0,0,0,1,1,0),(9,6,'forum','社区体验调查',1,0,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(10,5,'forum','网站公告',1,0,0,1,23,0,'129	第一体验俱乐部正式上线	1296392935	CDIMS03',1,0,1,1,0,0,1,3,0,0,0,1,0,0,0,0,0,0,1,1,0),(12,6,'forum','用户体验调查',1,0,0,3,20,0,'131	电信营业厅场景体验调查	1296393170	CDIMS03',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(13,11,'forum','论坛管理建议',1,0,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(14,0,'group','交流反馈',1,3,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0),(15,14,'forum','产品交流',1,1,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(16,14,'forum','产品推荐',1,0,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(17,2,'sub','电信营业厅',1,0,0,1,22,0,'130	产品体验区隆重上线	1296393021	CDIMS03',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(18,2,'sub','移动营业厅',1,1,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(19,2,'sub','联通营业厅',1,2,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(20,1,'forum','生活住宅区',1,2,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(21,1,'forum','数码电子商场',1,1,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(22,1,'forum','产品体验',1,3,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(24,11,'forum','平台意见收集',1,0,0,0,0,0,'',1,0,1,1,0,0,1,3,0,0,0,0,0,0,0,0,0,0,1,1,0),(26,6,'forum','尼尔森会员体验反馈',1,0,0,0,245,0,'179	1月30日补充主题	1297067899	SHGT01',1,0,1,1,0,0,1,127,0,0,0,0,0,0,1,0,0,0,1,1,0),(27,26,'sub','GDT BBS - GS',1,0,0,7,97,0,'172	1月29日主题二-付费问题	1296459726	NJGS02',1,0,1,1,0,0,1,127,0,0,0,0,0,0,0,0,0,0,1,1,0),(28,26,'sub','GDT BBS - GT',1,0,0,7,89,0,'179	1月30日补充主题	1297067899	SHGT01',1,0,1,1,0,0,1,127,0,0,0,0,0,0,0,0,0,0,1,1,0),(29,26,'sub','GDT BBS - IMS',1,0,0,5,77,0,'166	1月29日主题一-自我介绍	1296530526	NJIMS01',1,0,1,1,0,0,1,127,0,0,0,0,0,0,0,0,0,0,1,1,0);

/*Table structure for table `jrun_friendgroups` */

DROP TABLE IF EXISTS `jrun_friendgroups`;

CREATE TABLE `jrun_friendgroups` (
  `id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(50) NOT NULL,
  `createTime` int(10) NOT NULL,
  `uid` mediumint(8) NOT NULL,
  `type` mediumint(8) NOT NULL,
  `updateflag` mediumint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_friendgroups` */

insert  into `jrun_friendgroups`(`id`,`groupName`,`createTime`,`uid`,`type`,`updateflag`) values (1,'我的好友',1283501465,1,1,1),(5,'陌生人',1284370507,1,1,2),(7,'tongshi',1284370589,2,2,2),(8,'同事',1284456706,8,2,2),(9,'同学',1284457771,8,2,2),(10,'哈哈',1284457804,8,2,2),(11,'哈哈',1284530611,1,2,2);

/*Table structure for table `jrun_image` */

DROP TABLE IF EXISTS `jrun_image`;

CREATE TABLE `jrun_image` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `imagepath` varchar(255) NOT NULL,
  `url` varchar(50) DEFAULT NULL,
  `imageType` smallint(2) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` mediumint(9) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_image` */

insert  into `jrun_image`(`id`,`imagepath`,`url`,`imageType`,`remark`,`status`) values (4,'attachments/day_101102/20101102c549653fba4de5b71cab41d4d55e4f48.jpg','http://www.ct10000.com/',1,'天翼导航',1),(5,'attachments/day_101102/2010110241127e84daf4870476dbc9451748bda0.jpg','http://www.ct10000.com/',1,'翼起来wifi就趁现在',1),(6,'attachments/day_101013/20101013696f1071fb7052f8affe8a06ff87fb29.jpg','http://www.qq.com',1,'河涌捞出炸弹直冒白烟',2),(7,'attachments/day_101013/20101013ac9a779dca50db63683f3142c039c020.jpg','http://www.aa.com',1,'中国驻韩大使馆新馆舍动工 规模庞大为使馆之最',2);

/*Table structure for table `jrun_imagetypes` */

DROP TABLE IF EXISTS `jrun_imagetypes`;

CREATE TABLE `jrun_imagetypes` (
  `typeid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL,
  `type` enum('smiley','icon','avatar') NOT NULL DEFAULT 'smiley',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `directory` char(100) NOT NULL,
  PRIMARY KEY (`typeid`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_imagetypes` */

insert  into `jrun_imagetypes`(`typeid`,`name`,`type`,`displayorder`,`directory`) values (1,'默认表情','smiley',1,'default');

/*Table structure for table `jrun_invites` */

DROP TABLE IF EXISTS `jrun_invites`;

CREATE TABLE `jrun_invites` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `inviteip` char(15) NOT NULL,
  `invitecode` char(16) NOT NULL,
  `reguid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `regdateline` int(10) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL DEFAULT '1',
  KEY `uid` (`uid`,`status`),
  KEY `invitecode` (`invitecode`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_invites` */

/*Table structure for table `jrun_itempool` */

DROP TABLE IF EXISTS `jrun_itempool`;

CREATE TABLE `jrun_itempool` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) unsigned NOT NULL,
  `question` text NOT NULL,
  `answer` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_itempool` */

/*Table structure for table `jrun_magiclog` */

DROP TABLE IF EXISTS `jrun_magiclog`;

CREATE TABLE `jrun_magiclog` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `magicid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `action` tinyint(1) NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `amount` smallint(6) unsigned NOT NULL DEFAULT '0',
  `price` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `targettid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `targetpid` int(10) unsigned NOT NULL DEFAULT '0',
  `targetuid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  KEY `uid` (`uid`,`dateline`),
  KEY `targetuid` (`targetuid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_magiclog` */

/*Table structure for table `jrun_magicmarket` */

DROP TABLE IF EXISTS `jrun_magicmarket`;

CREATE TABLE `jrun_magicmarket` (
  `mid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `magicid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL,
  `price` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `num` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`mid`),
  KEY `num` (`magicid`,`num`),
  KEY `price` (`magicid`,`price`),
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_magicmarket` */

/*Table structure for table `jrun_magics` */

DROP TABLE IF EXISTS `jrun_magics`;

CREATE TABLE `jrun_magics` (
  `magicid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `type` tinyint(3) NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL,
  `identifier` varchar(40) NOT NULL,
  `description` varchar(255) NOT NULL,
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `price` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `num` smallint(6) unsigned NOT NULL DEFAULT '0',
  `salevolume` smallint(6) unsigned NOT NULL DEFAULT '0',
  `supplytype` tinyint(1) NOT NULL DEFAULT '0',
  `supplynum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `weight` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `filename` varchar(50) NOT NULL,
  `magicperm` text NOT NULL,
  PRIMARY KEY (`magicid`),
  UNIQUE KEY `identifier` (`identifier`),
  KEY `displayorder` (`available`,`displayorder`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_magics` */

insert  into `jrun_magics`(`magicid`,`available`,`type`,`name`,`identifier`,`description`,`displayorder`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`weight`,`filename`,`magicperm`) values (1,1,1,'变色卡','CCK','可以变换主题的颜色,并保存24小时',0,10,999,0,0,0,20,'magic_color.jsp',''),(2,1,3,'金钱卡','MOK','可以随机获得一些金币',0,10,999,0,0,0,30,'magic_money.jsp',''),(3,1,1,'IP卡','SEK','可以查看帖子作者的IP',0,15,999,0,0,0,30,'magic_see.jsp',''),(4,1,1,'提升卡','UPK','可以提升某个主题',0,10,999,0,0,0,30,'magic_up.jsp',''),(5,1,1,'置顶卡','TOK','可以将主题置顶24小时',0,20,999,0,0,0,40,'magic_top.jsp',''),(6,1,1,'悔悟卡','REK','可以删除自己的帖子',0,10,999,0,0,0,30,'magic_del.jsp',''),(7,1,2,'狗仔卡','RTK','查看某个用户是否在线',0,15,999,0,0,0,30,'magic_reporter.jsp',''),(8,1,1,'沉默卡','CLK','24小时内不能回复',0,15,999,0,0,0,30,'magic_close.jsp',''),(9,1,1,'喧嚣卡','OPK','使贴子可以回复',0,15,999,0,0,0,30,'magic_open.jsp',''),(10,1,1,'隐身卡','YSK','可以将自己的帖子匿名',0,20,999,0,0,0,30,'magic_hidden.jsp',''),(11,1,1,'恢复卡','CBK','将匿名恢复为正常显示的用户名,匿名终结者',0,15,999,0,0,0,20,'magic_renew.jsp',''),(12,1,1,'移动卡','MVK','可将自已的帖子移动到其他版面（隐含、特殊限定版面除外）',0,50,989,0,0,0,50,'magic_move.jsp','');

/*Table structure for table `jrun_medals` */

DROP TABLE IF EXISTS `jrun_medals`;

CREATE TABLE `jrun_medals` (
  `medalid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `image` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`medalid`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_medals` */

insert  into `jrun_medals`(`medalid`,`name`,`available`,`image`) values (1,'Medal No.1',0,'medal1.gif'),(2,'Medal No.2',0,'medal2.gif'),(3,'Medal No.3',0,'medal3.gif'),(4,'Medal No.4',0,'medal4.gif'),(5,'Medal No.5',0,'medal5.gif'),(6,'Medal No.6',0,'medal6.gif'),(7,'Medal No.7',0,'medal7.gif'),(8,'Medal No.8',0,'medal8.gif'),(9,'Medal No.9',0,'medal9.gif'),(10,'Medal No.10',0,'medal10.gif');

/*Table structure for table `jrun_meetingmember` */

DROP TABLE IF EXISTS `jrun_meetingmember`;

CREATE TABLE `jrun_meetingmember` (
  `meetingMember_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `appointment_id` int(11) DEFAULT NULL,
  `isfiltrate` int(11) DEFAULT NULL COMMENT '是否审核（0否、1是）',
  `isregister` int(11) DEFAULT NULL COMMENT '是否报名（0否、1是）',
  `iskickout` int(11) DEFAULT NULL COMMENT '是否筛选（0否、1是）',
  `isscreenout` int(11) DEFAULT NULL COMMENT '是否踢出（0否、1是）',
  `addTime` datetime DEFAULT NULL,
  PRIMARY KEY (`meetingMember_id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_meetingmember` */

insert  into `jrun_meetingmember`(`meetingMember_id`,`user_id`,`appointment_id`,`isfiltrate`,`isregister`,`iskickout`,`isscreenout`,`addTime`) values (7,3,4,0,0,0,0,'2010-11-03 19:12:36'),(8,24,4,0,0,0,0,'2010-11-03 19:12:36'),(20,21,11,0,0,0,0,'2010-11-22 13:27:42'),(21,22,11,0,0,0,0,'2010-11-22 13:27:42'),(22,23,11,0,0,0,0,'2010-11-22 13:27:42'),(23,21,12,0,1,0,0,'2010-11-22 13:28:27'),(24,22,12,0,0,0,0,'2010-11-22 13:28:32'),(25,23,12,0,0,0,0,'2010-11-22 13:28:32'),(151,21,41,0,0,0,0,'2010-12-12 06:44:10'),(152,22,41,0,0,0,0,'2010-12-12 06:44:11'),(153,23,41,0,0,0,0,'2010-12-12 06:44:12'),(154,21,42,0,1,0,0,'2010-12-12 06:46:51'),(155,22,42,0,1,0,0,'2010-12-12 06:46:52'),(156,23,42,0,1,0,0,'2010-12-12 06:46:53'),(157,39,42,0,1,0,0,'2010-12-12 06:46:58'),(158,24,42,0,1,0,0,'2010-12-12 06:46:59'),(162,23,44,0,1,0,0,'2010-12-15 14:34:26'),(163,24,44,0,1,0,0,'2010-12-15 14:34:26'),(164,39,44,0,1,0,0,'2010-12-15 14:34:26'),(165,42,45,0,1,0,0,'2010-12-29 09:10:58'),(197,91,55,0,1,1,0,'2011-02-22 08:58:59'),(198,86,55,0,1,1,0,'2011-02-22 08:58:59'),(199,97,55,0,1,1,0,'2011-02-22 08:59:00'),(200,85,55,0,1,1,0,'2011-02-22 08:59:00'),(201,95,55,0,1,1,0,'2011-02-22 08:59:00'),(202,119,55,0,1,1,0,'2011-02-22 08:59:04'),(203,120,55,0,1,1,0,'2011-02-22 08:59:09'),(204,121,55,0,1,1,0,'2011-02-22 08:59:14'),(205,90,55,0,1,1,0,'2011-02-22 08:59:14'),(206,96,55,0,1,1,0,'2011-02-22 08:59:14'),(207,122,55,0,1,1,0,'2011-02-22 08:59:18'),(208,123,55,0,1,1,0,'2011-02-22 08:59:23'),(209,88,55,0,1,1,0,'2011-02-22 08:59:23'),(210,68,55,0,1,1,0,'2011-02-22 09:06:07');

/*Table structure for table `jrun_memberfields` */

DROP TABLE IF EXISTS `jrun_memberfields`;

CREATE TABLE `jrun_memberfields` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `nickname` varchar(30) NOT NULL DEFAULT '',
  `site` varchar(75) NOT NULL DEFAULT '',
  `alipay` varchar(50) NOT NULL DEFAULT '',
  `icq` varchar(12) NOT NULL DEFAULT '',
  `qq` varchar(12) NOT NULL DEFAULT '',
  `yahoo` varchar(40) NOT NULL DEFAULT '',
  `msn` varchar(40) NOT NULL DEFAULT '',
  `taobao` varchar(40) NOT NULL DEFAULT '',
  `location` varchar(30) NOT NULL DEFAULT '',
  `customstatus` varchar(30) NOT NULL DEFAULT '',
  `medals` varchar(255) NOT NULL DEFAULT '',
  `avatar` varchar(255) NOT NULL DEFAULT '',
  `avatarwidth` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `avatarheight` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `bio` text NOT NULL,
  `sightml` text NOT NULL,
  `ignorepm` text NOT NULL,
  `groupterms` text NOT NULL,
  `authstr` varchar(20) NOT NULL DEFAULT '',
  `spacename` varchar(40) NOT NULL,
  `buyercredit` smallint(6) NOT NULL DEFAULT '0',
  `sellercredit` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_memberfields` */

insert  into `jrun_memberfields`(`uid`,`nickname`,`site`,`alipay`,`icq`,`qq`,`yahoo`,`msn`,`taobao`,`location`,`customstatus`,`medals`,`avatar`,`avatarwidth`,`avatarheight`,`bio`,`sightml`,`ignorepm`,`groupterms`,`authstr`,`spacename`,`buyercredit`,`sellercredit`) values (1,'','123123','','121212','121212','123','','','zhuhai','','','images/avatars/04.gif',0,0,'121212	','121212','','','','',0,0),(2,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(3,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(4,'','','','','251524096','','changyuan@126163.com','','江西赣州','','','',0,0,'','','','','','',0,0),(5,'','','','','','','','','','','','',0,0,'','','','a:1:{s:3:\"ext\";N;}','','',0,0),(6,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(7,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(8,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(9,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(10,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(11,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(12,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(13,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(14,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(15,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(16,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(17,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(18,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(19,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(25,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(0,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(26,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(27,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(28,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(29,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(30,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(31,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(32,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(33,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(34,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(35,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(36,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(37,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(38,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(39,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(40,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(41,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(42,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(43,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(44,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(45,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(46,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(47,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(48,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(49,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(50,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(51,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(52,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(53,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(54,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(55,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(56,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(57,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(58,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(59,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(60,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(61,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(62,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(63,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(64,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(65,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(66,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(67,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(68,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(69,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(70,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(71,'','','','','17458558','','','','','','','',0,0,'','','','','','',0,0),(72,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(73,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(74,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(75,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(76,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(77,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(78,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(79,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(80,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(81,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(82,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(83,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(84,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(85,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(86,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(87,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(88,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(89,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(90,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(91,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(92,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(93,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(94,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(95,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(96,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(97,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(98,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(99,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(100,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(101,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(102,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(103,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(104,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(105,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(106,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(107,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(108,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(109,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(110,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(111,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(112,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(113,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(114,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(115,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(116,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(117,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(118,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(119,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(120,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(121,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(122,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(123,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(124,'','','','','','','','','','','','',0,0,'','','','','','',0,0),(125,'','','','','','','','','','','','',0,0,'','','','','','',0,0);

/*Table structure for table `jrun_membermagics` */

DROP TABLE IF EXISTS `jrun_membermagics`;

CREATE TABLE `jrun_membermagics` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `magicid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `num` smallint(6) unsigned NOT NULL DEFAULT '0',
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_membermagics` */

/*Table structure for table `jrun_members` */

DROP TABLE IF EXISTS `jrun_members`;

CREATE TABLE `jrun_members` (
  `uid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `username` char(15) NOT NULL DEFAULT '',
  `password` char(32) NOT NULL DEFAULT '',
  `secques` char(8) NOT NULL DEFAULT '',
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `adminid` tinyint(1) NOT NULL DEFAULT '0',
  `groupid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `groupexpiry` int(10) unsigned NOT NULL DEFAULT '0',
  `extgroupids` char(20) NOT NULL DEFAULT '',
  `regip` char(15) NOT NULL DEFAULT '',
  `regdate` int(10) unsigned NOT NULL DEFAULT '0',
  `lastip` char(15) NOT NULL DEFAULT '',
  `lastvisit` int(10) unsigned NOT NULL DEFAULT '0',
  `lastactivity` int(10) unsigned NOT NULL DEFAULT '0',
  `lastpost` int(10) unsigned NOT NULL DEFAULT '0',
  `posts` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `digestposts` smallint(6) unsigned NOT NULL DEFAULT '0',
  `oltime` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pageviews` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `credits` int(10) NOT NULL DEFAULT '0',
  `extcredits1` int(10) NOT NULL DEFAULT '0',
  `extcredits2` int(10) NOT NULL DEFAULT '0',
  `extcredits3` int(10) NOT NULL DEFAULT '0',
  `extcredits4` int(10) NOT NULL DEFAULT '0',
  `extcredits5` int(10) NOT NULL DEFAULT '0',
  `extcredits6` int(10) NOT NULL DEFAULT '0',
  `extcredits7` int(10) NOT NULL DEFAULT '0',
  `extcredits8` int(10) NOT NULL DEFAULT '0',
  `email` char(40) NOT NULL DEFAULT '',
  `bday` date NOT NULL DEFAULT '0000-00-00',
  `sigstatus` tinyint(1) NOT NULL DEFAULT '0',
  `tpp` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ppp` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `styleid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `dateformat` tinyint(1) NOT NULL DEFAULT '0',
  `timeformat` tinyint(1) NOT NULL DEFAULT '0',
  `pmsound` tinyint(1) NOT NULL DEFAULT '0',
  `showemail` tinyint(1) NOT NULL DEFAULT '0',
  `newsletter` tinyint(1) NOT NULL DEFAULT '0',
  `invisible` tinyint(1) NOT NULL DEFAULT '0',
  `timeoffset` char(4) NOT NULL DEFAULT '',
  `newpm` tinyint(1) NOT NULL DEFAULT '0',
  `accessmasks` tinyint(1) NOT NULL DEFAULT '0',
  `editormode` tinyint(1) unsigned NOT NULL DEFAULT '2',
  `customshow` tinyint(1) unsigned NOT NULL DEFAULT '26',
  `xspacestatus` tinyint(1) NOT NULL DEFAULT '0',
  `salt` char(6) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`),
  KEY `email` (`email`),
  KEY `groupid` (`groupid`)
) ENGINE=MyISAM AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_members` */

insert  into `jrun_members`(`uid`,`username`,`password`,`secques`,`gender`,`adminid`,`groupid`,`groupexpiry`,`extgroupids`,`regip`,`regdate`,`lastip`,`lastvisit`,`lastactivity`,`lastpost`,`posts`,`digestposts`,`oltime`,`pageviews`,`credits`,`extcredits1`,`extcredits2`,`extcredits3`,`extcredits4`,`extcredits5`,`extcredits6`,`extcredits7`,`extcredits8`,`email`,`bday`,`sigstatus`,`tpp`,`ppp`,`styleid`,`dateformat`,`timeformat`,`pmsound`,`showemail`,`newsletter`,`invisible`,`timeoffset`,`newpm`,`accessmasks`,`editormode`,`customshow`,`xspacestatus`,`salt`) values (1,'administrator','1dbbf3734c3bf546a6db1018171e9e10','',1,1,1,0,'','121.13.86.75',1170596852,'119.132.73.219',1329096732,1329463990,1291943453,99,1,105,3480,181,50,50,0,15,0,0,0,0,'ling5516@126.com','2010-11-20',0,20,10,2,1,0,0,1,1,0,'9999',0,1,2,2,0,'2394d1'),(67,'yanyang1230','be9bcdf5b1be1a4d1beb0e12e8b9cd54','',0,3,3,0,'','219.137.58.20',1296097236,'219.137.58.20',1296533462,1296533627,1296274049,1,0,1,0,0,0,0,0,0,0,0,0,0,'yanyang1230@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(68,'dongbin','e10adc3949ba59abbe56e057f20f883e','',0,0,17,0,'','183.33.204.55',1296097387,'119.132.72.199',1327908817,1328057293,1296370549,0,0,6,0,0,0,0,0,0,0,0,0,0,'dongbin@ctbri.com.cn,groupid=17','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(69,'SHGS01','6ef0af4e4085edabd7b4a51230995fee','',0,0,10,0,'','219.137.58.20',1296097629,'180.155.183.174',1296298280,1296453522,1296453886,7,0,0,0,13,0,0,0,5,0,0,0,0,'BBSSH_1@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(70,'wingotech','a147284eba1f6acfa9401ad814bd951e','',0,3,10,0,'','183.33.207.8',1296097753,'125.89.11.222',1326348041,1329096967,1296205123,1,0,6,0,10,0,0,0,5,0,0,0,0,'yanjie@wingotech.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(71,'gzl503','60bd73bb68182790bbb9cda7b08c064d','',2,0,17,0,'','59.42.126.44',1296099169,'113.108.150.132',1296207695,1296208496,1296282099,5,0,0,0,2,0,0,0,0,0,0,0,0,'gzl503@hotmail.com','1983-05-23',0,0,0,0,0,0,0,0,1,0,'9999',1,0,2,26,0,'0'),(72,'SHGT01','9e939338627a358e6fc92682b2e94842','',0,0,10,0,'','219.137.58.20',1296105364,'116.234.53.30',1296319488,1297067348,1297067899,10,0,0,0,15,0,0,0,5,0,0,0,0,'BBSSH_4@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(73,'SHGT02','8e866548f826658bf165ad3c9ee599cf','',0,0,11,0,'','219.137.58.20',1296107269,'58.218.204.225',1296307634,1296465433,1296466718,30,0,1,0,55,0,0,0,20,0,0,0,0,'BBSSH_5@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(74,'cannymok','436050b67eacf8637ba3477420463aed','',0,3,17,0,'','219.137.58.20',1296109247,'219.137.58.20',1297841551,1298454111,1296382524,39,0,0,0,19,0,0,0,0,0,0,0,0,'canny.mo@nielsen.com,groupid=17','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(75,'00000','d0970714757783e6cf17b26fb8e2298f','',0,0,17,0,'','59.42.126.47',1296113726,'125.89.11.175',1324954344,1324955084,1296192737,2,0,0,0,1,0,0,0,0,0,0,0,0,'wanghongj@gsta.com,groupid=17','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(76,'SHGS02','6cf8bec6d5d7abf25b49d6466e8d866d','',0,0,10,0,'','219.137.58.20',1296114716,'222.67.224.216',1296297818,1296363221,1296365000,10,0,0,0,15,0,0,0,5,0,0,0,0,'BBSSH_2@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(77,'GZGS01','5f9ad6de9bfb62ed35e76ee4aac3755c','',0,0,10,0,'','219.137.58.20',1296116131,'113.111.45.116',1296302420,1296450126,1296450918,10,0,0,0,15,0,0,0,5,0,0,0,0,'gzbbs01@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',1,0,2,26,0,'0'),(78,'wangbin','48c60e571d5eb3444d38a2f41523b01c','',0,0,17,0,'','59.42.126.21',1296116504,'59.42.126.45',1296435216,1296463811,1296360982,2,0,0,0,1,0,0,0,0,0,0,0,0,'wangbin@gsta.com,groupid=17','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(79,'NJGS01','214ef608aedb8ff28ba9e8d1c4378fdd','',0,0,10,0,'','219.137.58.20',1296116614,'114.222.1.160',1296364453,1296444846,1296364576,7,0,0,0,3,0,0,0,0,0,0,0,0,'NJyule001@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(80,'GZIMS01','f50c482e4975e0e84f0805e94556a9cd','',0,0,10,0,'','219.137.58.20',1296116672,'113.96.114.34',1296385473,1296404830,1296406834,10,0,0,0,25,0,0,0,10,0,0,0,0,'gzbbs07@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(81,'NJIMS02','37d353b33d1efb1bd62cf9a896de7661','',0,0,10,0,'','219.137.58.20',1296117599,'180.111.106.216',1296624184,1297322879,1296385928,11,0,0,0,25,0,0,0,10,0,0,0,0,'NJyule008@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(82,'SHGS03','82793b0ec5eda617d91c305b9c7071b0','',0,0,10,0,'','219.137.58.20',1296122929,'114.84.38.255',1296272972,1296274484,1296364191,19,0,0,0,39,0,0,0,15,0,0,0,0,'BBSSH_3@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(83,'GZGS03','995592a9bb7866788f5d73f422082bd3','',0,0,11,0,'','219.137.58.20',1296131390,'116.28.66.33',1296285970,1296308227,1296391251,18,0,1,0,59,0,0,0,25,0,0,0,0,'gzbbs03@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(84,'GZGT01','d8e1376f4d0e0969c341a1dd856a1c9d','',0,0,10,0,'','219.137.58.20',1296131569,'113.68.143.34',1296389667,1296470696,1296378291,9,0,0,0,14,0,0,0,5,0,0,0,0,'gzbbs04@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(85,'000000','e10adc3949ba59abbe56e057f20f883e','',0,0,10,0,'','null',1296143223,'183.33.205.166',1298341254,1298508571,1296272877,4,0,0,0,2,0,0,0,0,0,0,0,0,'wanghongj@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(86,'suddenlyliu','0342eea94386871a0fc6d7bd62fef399','',0,0,10,0,'','null',1296176385,'59.42.126.27',1296456873,1297647640,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'liusq@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(87,'zhangliang','ae0b8c3851370f0df2761faa65b4357c','',0,0,17,0,'','59.42.126.20',1296176536,'59.42.126.20',1296176536,1296176547,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'zhangliang@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(106,'NJGT01','d024e3e5f727bb22563d8e22ef05164b','',0,0,10,0,'','219.137.58.20',1296279941,'58.213.141.115',1296351339,1296527924,1296528590,10,0,0,0,5,0,0,0,0,0,0,0,0,'NJyule004@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(107,'SHIMS02','6462d4afaf658561a1c2cf5effef5b01','',0,0,10,0,'','219.137.58.20',1296280365,'116.237.203.227',1296376611,1296397326,1296377700,11,0,1,0,15,0,0,0,5,0,0,0,0,'BBSSH_8@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(108,'NJGT02','c92ac69e8ba7eaa01bec909c10e2c10d','',0,0,10,0,'','219.137.58.20',1296287324,'180.111.114.100',1296287345,1296527848,1296528281,7,0,0,0,3,0,0,0,0,0,0,0,0,'NJyule005@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(109,'SHIMS01','86dada9697d404c09d9257866ca1c330','',0,0,10,0,'','219.137.58.20',1296306120,'122.225.10.239',1296306205,1296429449,1296432248,8,0,0,0,4,0,0,0,0,0,0,0,0,'BBSSH_7@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(88,'liaolx','4aa668c1f97ff536d1330f2f2d0b7901','',0,0,10,0,'','59.42.126.48',1296176799,'59.42.126.48',1296176799,1296176807,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'liaolx@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(89,'CDIMS01','5229e80539b4fa30c998c9a8e4f0ec0e','',0,0,10,0,'','219.137.58.20',1296187274,'60.255.31.52',1296405534,1296437188,1296408542,6,0,0,0,23,0,0,0,10,0,0,0,0,'kido_1437@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(90,'kangjun','09cb8266c2b11bf58c57a968eadff757','',0,0,17,0,'','59.42.126.46',1296188221,'59.42.126.47',1296188221,1296188264,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'kangb@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(91,'moonsonwang','48c60e571d5eb3444d38a2f41523b01c','',0,0,10,0,'','null',1296192549,'59.42.126.26',1296192557,1296204162,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'moonsonwang@sina.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(92,'NJGS02','aff5145943a90f32ea08644930f3b4d3','',0,0,10,0,'','219.137.58.20',1296192624,'121.229.200.178',1296300416,1296446851,1296459726,8,0,0,0,4,0,0,0,0,0,0,0,0,'NJyule002@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(93,'GZGS02','12b41b56f66daae19a796f4b807e9845','',0,0,10,0,'','219.137.58.20',1296196360,'113.111.45.116',1296315098,1296449390,1296449993,6,0,0,0,3,0,0,0,0,0,0,0,0,'gzbbs02@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(94,'GZGT02','f86fb275d6093b74f9623ffccbdeb640','',0,0,10,0,'','219.137.58.20',1296198612,'119.131.44.213',1296658770,1297219131,1296458779,8,0,0,0,4,0,0,0,0,0,0,0,0,'gzbbs05@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(95,'fries10','e10adc3949ba59abbe56e057f20f883e','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'xujing@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(96,'mselam','ae0b8c3851370f0df2761faa65b4357c','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'zhangliang@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(97,'lfgsta','d6a8d74bf5a556223b3bbfdb4fab1b89','',0,0,10,0,'','null',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'liangfang@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(98,'','d41d8cd98f00b204e9800998ecf8427e','',0,0,10,0,'','59.42.126.45',1296204555,'218.14.17.169',1296204572,1296275697,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'qiub@21cn.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(99,'xlqb_1314','1bddd20310fdd349577c0b24fc4986f4','',0,0,10,0,'','183.33.206.82',1296205376,'119.132.74.188',1324610493,1326183425,0,0,0,1,0,10,0,0,0,5,0,0,0,0,'393125716@qq.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(100,'CDIMS03','ff66ee44879ea3323f877888f9f74619','',0,0,10,0,'','219.137.58.20',1296222025,'222.212.220.34',1296392876,1296400700,1296401945,11,0,0,0,25,0,0,0,10,0,0,0,0,'yjav123@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(101,'SHGT03','299b7f319234e20da33572eabf578817','',0,0,10,0,'','219.137.58.20',1296224155,'222.71.50.43',1296474150,1296474412,1296474765,14,0,0,0,27,0,0,0,10,0,0,0,0,'BBSSH_6@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(102,'GZIMS02','7129d1d8f40f40adffb5a6f7cb6c4446','',0,0,10,0,'','219.137.58.20',1296265885,'121.33.210.106',1296366172,1296376539,1296377340,6,0,0,0,23,0,0,0,10,0,0,0,0,'gzbbs08@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(103,'SHIMS03','ceaf925e170ac0ad7f44568c99af9f51','',0,0,10,0,'','219.137.58.20',1296271097,'220.181.149.225',1296401461,1296445253,1296384275,7,0,1,0,23,0,0,0,10,0,0,0,0,'BBSSH_9@126.COM','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(104,'GZIMS03','ec6fa787ed98c725246efcfcf48d6b4f','',0,0,10,0,'','219.137.58.20',1296273975,'58.248.7.185',1296398721,1296443364,1296378885,5,0,0,0,22,0,0,0,10,0,0,0,0,'gzbbs09@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(105,'NJGS03','b972fd235cf171764310f9220de4bcc7','',0,0,10,0,'','219.137.58.20',1296276820,'122.193.13.18',1296276820,1296276833,1296368142,7,0,0,0,3,0,0,0,0,0,0,0,0,'NJyule003@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(110,'CDIMS02','835f7801717145a40a3275a3920f638f','',0,0,10,0,'','219.137.58.20',1296309639,'222.183.15.235',1296361243,1296400752,1296402662,10,0,0,0,25,0,0,0,10,0,0,0,0,'kimmy1437@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(111,'linqingyi','5935bac85393b6f9b34d55f8bdaef8f7','',0,0,17,0,'','59.42.126.46',1296349874,'59.42.126.46',1296349874,1296349943,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'linqy@gsta.com,groupid=17','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(112,'NJIMS01','5c5b84c886d22db130e3245f4d494752','',0,0,10,0,'','219.137.58.20',1296365905,'117.89.11.40',1296530211,1296562041,1296530526,5,0,0,0,2,0,0,0,0,0,0,0,0,'NJyule007@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(113,'GZGT03','2134b66d494644c6cc1b13e94e0dfa63','',0,0,10,0,'','219.136.9.111',1296375606,'219.136.9.111',1296375606,1296375616,1296377681,8,0,0,0,4,0,0,0,0,0,0,0,0,'gzbbs06@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(114,'NJIMS03','3f04422d8e17c690ee899668dd37666c','',0,0,10,0,'','219.137.58.20',1296376692,'180.110.59.69',1296473652,1296563379,1296378362,7,0,0,0,13,0,0,0,5,0,0,0,0,'NJyule009@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(115,'NJGT03','286da916c154e663890931f2938eea49','',0,0,10,0,'','219.137.58.20',1296390755,'58.213.142.112',1296528293,1296533502,1296529388,7,0,0,0,13,0,0,0,5,0,0,0,0,'NJyule006@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(116,'songjacky','0c9886a7b1b998980c7c61bb7f07eaac','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'13316090086@189.cn','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(117,'gzhexm','2a524d498b7eb9a7ca271439783cada7','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'hexiaom@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(118,'zeldadxnds','f5bb0c8de146c67b44babbf4e6584cc0','',0,0,10,0,'','219.136.204.201',1297410039,'219.136.204.201',1297410095,1297411357,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'298032210@qq.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(119,'merryrun','0342eea94386871a0fc6d7bd62fef399','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'suddenlyliu@163.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(120,'lfgsta1','b06ab6c4e5f5c06578c857f8fc4e9af8','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'lfjj02@126.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(121,'aaaaaa','e10adc3949ba59abbe56e057f20f883e','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'esayingnet@21cn.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(122,'nadiawang','a838e104a8f3489949b4d63501c9cbaa','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'wangyh@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(123,'melody_maimai','98746d1670749be18d9f1a6124a10fec','',0,0,10,0,'','',0,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'lijuan@gsta.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(124,'f411csh','c33367701511b4f6020ec61ded352059','',0,0,10,0,'','183.33.204.215',1324348312,'119.132.73.101',1324348347,1324434598,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'f411csh@qq.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0'),(125,'huangjinbo','525be2b377c11b8a2c03f635d60196e5','',0,0,10,0,'','119.132.74.4',1324436571,'119.132.74.188',1324438487,1326072094,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'huangjinbo@wingotech.com','0000-00-00',0,0,0,0,0,0,0,0,1,0,'9999',0,0,2,26,0,'0');

/*Table structure for table `jrun_memberspaces` */

DROP TABLE IF EXISTS `jrun_memberspaces`;

CREATE TABLE `jrun_memberspaces` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `style` char(20) NOT NULL,
  `description` char(100) NOT NULL,
  `layout` char(200) NOT NULL,
  `side` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_memberspaces` */

insert  into `jrun_memberspaces`(`uid`,`style`,`description`,`layout`,`side`) values (1,'default5','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(2,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(3,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(4,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(5,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(9,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(7,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(8,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(6,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(10,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(12,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(13,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(14,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(16,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(15,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(11,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(17,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(18,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(19,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(36,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(38,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(20,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(40,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(41,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(43,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(52,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(53,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(70,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(74,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(67,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(89,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(82,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(83,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(73,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(103,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(99,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(100,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(71,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(81,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(72,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(101,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(84,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(108,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(107,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(85,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(106,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(102,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(80,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(79,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(105,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(92,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(110,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(115,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(112,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(114,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(77,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(93,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(94,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(113,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(104,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(76,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(69,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(109,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(118,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(123,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1),(75,'default','','[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	',1);

/*Table structure for table `jrun_moderators` */

DROP TABLE IF EXISTS `jrun_moderators`;

CREATE TABLE `jrun_moderators` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `fid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `inherited` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`fid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_moderators` */

insert  into `jrun_moderators`(`uid`,`fid`,`displayorder`,`inherited`) values (67,26,2,0),(74,26,2,0),(67,27,0,1),(67,28,0,1),(67,29,0,1),(74,27,0,1),(74,28,0,1),(74,29,0,1),(70,23,0,0);

/*Table structure for table `jrun_modworks` */

DROP TABLE IF EXISTS `jrun_modworks`;

CREATE TABLE `jrun_modworks` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `modaction` char(3) NOT NULL DEFAULT '',
  `dateline` date NOT NULL DEFAULT '2006-01-01',
  `count` smallint(6) unsigned NOT NULL DEFAULT '0',
  `posts` smallint(6) unsigned NOT NULL DEFAULT '0',
  KEY `uid` (`uid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_modworks` */

/*Table structure for table `jrun_myposts` */

DROP TABLE IF EXISTS `jrun_myposts`;

CREATE TABLE `jrun_myposts` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `pid` int(10) unsigned NOT NULL DEFAULT '0',
  `position` smallint(6) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `special` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`tid`),
  KEY `tid` (`tid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_myposts` */

/*Table structure for table `jrun_mythreads` */

DROP TABLE IF EXISTS `jrun_mythreads`;

CREATE TABLE `jrun_mythreads` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `special` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`tid`),
  KEY `tid` (`tid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_mythreads` */

/*Table structure for table `jrun_onlinelist` */

DROP TABLE IF EXISTS `jrun_onlinelist`;

CREATE TABLE `jrun_onlinelist` (
  `groupid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `title` varchar(30) NOT NULL DEFAULT '',
  `url` varchar(30) NOT NULL DEFAULT ''
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_onlinelist` */

insert  into `jrun_onlinelist`(`groupid`,`displayorder`,`title`,`url`) values (1,1,'管理员','online_admin.gif'),(2,2,'超级版主','online_supermod.gif'),(3,3,'版主','online_moderator.gif'),(0,4,'会员','online_member.gif');

/*Table structure for table `jrun_onlinetime` */

DROP TABLE IF EXISTS `jrun_onlinetime`;

CREATE TABLE `jrun_onlinetime` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `thismonth` smallint(6) unsigned NOT NULL DEFAULT '0',
  `total` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `lastupdate` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_onlinetime` */

insert  into `jrun_onlinetime`(`uid`,`thismonth`,`total`,`lastupdate`) values (1,10,6290,1328496390),(2,0,10,1279597396),(3,0,140,1286942545),(5,0,720,1284882209),(8,0,240,1284457756),(7,0,10,1284884803),(10,0,10,1287034523),(15,0,30,1288238847),(18,0,10,1288937381),(20,0,240,1293595635),(40,0,20,1292208750),(21,0,30,1292381717),(43,0,20,1296005183),(44,0,10,1295319277),(51,0,10,1295425336),(61,0,10,1296039032),(54,0,10,1296041662),(63,0,10,1296048399),(70,0,330,1326348670),(73,0,30,1296109430),(67,0,80,1296532784),(77,0,10,1296127931),(83,0,50,1296285736),(71,0,20,1296208371),(75,0,20,1324953337),(81,0,10,1296189265),(82,0,10,1296201825),(99,0,70,1326184468),(101,0,20,1296225661),(69,0,10,1296228784),(103,0,50,1296384373),(100,0,20,1296401337),(107,0,30,1296293039),(104,0,10,1296287756),(80,0,10,1296293672),(110,0,30,1296401582),(85,0,10,1296349591),(89,0,20,1296362567),(68,0,350,1326940760),(74,0,10,1296444317),(94,0,10,1296458891),(115,0,10,1296529403),(125,0,10,1326079634);

/*Table structure for table `jrun_orders` */

DROP TABLE IF EXISTS `jrun_orders`;

CREATE TABLE `jrun_orders` (
  `orderid` char(32) NOT NULL DEFAULT '',
  `status` char(3) NOT NULL DEFAULT '',
  `buyer` char(50) NOT NULL DEFAULT '',
  `admin` char(15) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `amount` int(10) unsigned NOT NULL DEFAULT '0',
  `price` float(7,2) unsigned NOT NULL DEFAULT '0.00',
  `submitdate` int(10) unsigned NOT NULL DEFAULT '0',
  `confirmdate` int(10) unsigned NOT NULL DEFAULT '0',
  UNIQUE KEY `orderid` (`orderid`),
  KEY `submitdate` (`submitdate`),
  KEY `uid` (`uid`,`submitdate`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_orders` */

/*Table structure for table `jrun_paymentlog` */

DROP TABLE IF EXISTS `jrun_paymentlog`;

CREATE TABLE `jrun_paymentlog` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `authorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `amount` int(10) unsigned NOT NULL DEFAULT '0',
  `netamount` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`tid`,`uid`),
  KEY `uid` (`uid`),
  KEY `authorid` (`authorid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_paymentlog` */

/*Table structure for table `jrun_permlist` */

DROP TABLE IF EXISTS `jrun_permlist`;

CREATE TABLE `jrun_permlist` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `tid` smallint(6) NOT NULL,
  `account` char(20) NOT NULL,
  `email` char(40) DEFAULT NULL,
  `issendemail` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1403 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_permlist` */

insert  into `jrun_permlist`(`id`,`tid`,`account`,`email`,`issendemail`) values (187,91,'admin','',NULL),(188,91,'411','',NULL),(189,91,'huchangyuan','',NULL),(190,91,'hcy','',NULL),(191,91,'zhongwei','',NULL),(192,91,'111','',NULL),(193,94,'admin','',NULL),(194,94,'411','',NULL),(195,94,'huchangyuan','',NULL),(196,94,'hcy','',NULL),(197,94,'zhongwei','',NULL),(198,94,'111','',NULL),(199,96,'admin','',NULL),(200,96,'411','',NULL),(201,96,'huchangyuan','',NULL),(202,96,'hcy','',NULL),(203,96,'zhongwei','',NULL),(204,96,'111','',NULL),(205,110,'admin','',NULL),(206,110,'411','',NULL),(207,110,'huchangyuan','',NULL),(208,110,'hcy','',NULL),(209,110,'zhongwei','',NULL),(210,110,'111','',NULL),(211,111,'admin','',NULL),(212,111,'411','',NULL),(213,111,'huchangyuan','',NULL),(214,111,'hcy','',NULL),(215,111,'zhongwei','',NULL),(216,111,'111','',NULL),(217,112,'admin','',NULL),(218,112,'411','',NULL),(219,112,'huchangyuan','',NULL),(220,112,'hcy','',NULL),(221,112,'zhongwei','',NULL),(222,112,'111','',NULL),(223,113,'admin','',NULL),(224,113,'411','',NULL),(225,113,'huchangyuan','',NULL),(226,113,'hcy','',NULL),(227,113,'111','',NULL),(228,118,'admin','',NULL),(229,118,'411','',NULL),(230,118,'huchangyuan','',NULL),(231,118,'hcy','',NULL),(232,118,'zhongwei','',NULL),(233,118,'111','',NULL),(234,141,'wingotech','',NULL),(235,142,'wingotech','',NULL),(236,143,'wingotech','',NULL),(237,143,'xlqb_1314','',NULL),(238,153,'moonsonwang','',NULL),(239,153,'000000','',NULL),(240,153,'admin','',NULL),(241,154,'000000','',NULL),(242,154,'GZIMS01','',NULL),(243,154,'admin','',NULL),(587,155,'GZGS01','',NULL),(586,155,'GZGS02','',NULL),(585,155,'GZGS03','',NULL),(584,155,'SHGS01','',NULL),(583,155,'SHGS02','',NULL),(582,155,'SHGS03','',NULL),(581,155,'NJGS01','',NULL),(580,155,'NJGS02','',NULL),(579,155,'NJGS03','',NULL),(578,155,'gzl503','',NULL),(577,155,'wangbin','',NULL),(576,155,'moonsonwang','',NULL),(575,155,'suddenlyliu','',NULL),(574,155,'liaolx','',NULL),(573,155,'fries10','',NULL),(572,155,'mselam','',NULL),(571,155,'000000','',NULL),(570,155,'lfgsta','',NULL),(569,155,'kangjun','',NULL),(568,155,'yanyang1230','',NULL),(567,155,'admin','',NULL),(265,156,'GZGS01','',NULL),(266,156,'GZGS02','',NULL),(267,156,'GZGS03','',NULL),(268,156,'SHGS01','',NULL),(269,156,'SHGS02','',NULL),(270,156,'SHGS03','',NULL),(271,156,'NJGS01','',NULL),(272,156,'NJGS02','',NULL),(273,156,'NJGS03','',NULL),(274,156,'gzl503','',NULL),(275,156,'wangbin','',NULL),(276,156,'moonsonwang','',NULL),(277,156,'suddenlyliu','',NULL),(278,156,'liaolx','',NULL),(279,156,'fries10','',NULL),(280,156,'mselam','',NULL),(281,156,'000000','',NULL),(282,156,'lfgsta','',NULL),(283,156,'kangjun','',NULL),(284,156,'yanyang1230','',NULL),(285,156,'admin','',NULL),(286,157,'GZGT01','',NULL),(287,157,'GZGT02','',NULL),(288,157,'GZGT03','',NULL),(289,157,'SHGT01','',NULL),(290,157,'SHGT02','',NULL),(291,157,'SHGT03','',NULL),(292,157,'NJGT01','',NULL),(293,157,'NJGT02','',NULL),(294,157,'NJGT03','',NULL),(295,157,'gzl503','',NULL),(296,157,'wangbin','',NULL),(297,157,'moonsonwang','',NULL),(298,157,'suddenlyliu','',NULL),(299,157,'liaolx','',NULL),(300,157,'fries10','',NULL),(301,157,'mselam','',NULL),(302,157,'000000','',NULL),(303,157,'lfgsta','',NULL),(304,157,'kangjun','',NULL),(305,157,'yanyang1230','',NULL),(306,157,'admin','',NULL),(650,159,'GZGT01','',NULL),(649,159,'GZGT02','',NULL),(648,159,'GZGT03','',NULL),(647,159,'SHGT01','',NULL),(646,159,'SHGT02','',NULL),(645,159,'SHGT03','',NULL),(644,159,'NJGT01','',NULL),(643,159,'NJGT02','',NULL),(642,159,'NJGT03','',NULL),(641,159,'gzl503','',NULL),(640,159,'wangbin','',NULL),(639,159,'moonsonwang','',NULL),(638,159,'suddenlyliu','',NULL),(637,159,'liaolx','',NULL),(636,159,'fries10','',NULL),(635,159,'mselam','',NULL),(634,159,'0','',NULL),(633,159,'lfgsta','',NULL),(632,159,'kangjun','',NULL),(631,159,'yanyang1230','',NULL),(630,159,'admin','',NULL),(388,161,'yanyang1230','',NULL),(387,161,'GZGS01','',NULL),(386,161,'GZGS02','',NULL),(385,161,'GZGS03','',NULL),(384,161,'SHGS01','',NULL),(383,161,'SHGS02','',NULL),(382,161,'SHGS03','',NULL),(381,161,'NJGS01','',NULL),(380,161,'NJGS02，NJGS03','',NULL),(379,161,'gzl503','',NULL),(378,161,'wangbin','',NULL),(377,161,'moonsonwang','',NULL),(376,161,'suddenlyliu','',NULL),(375,161,'liaolx','',NULL),(374,161,'ries10','',NULL),(373,161,'mselam','',NULL),(372,161,'000000','',NULL),(371,161,'lfgsta','',NULL),(370,161,'kangjun','',NULL),(369,161,'admin','',NULL),(1159,171,'CDIMS02','',NULL),(1158,171,'CDIMS03','',NULL),(715,163,'kangjun','',NULL),(714,163,'lfgsta','',NULL),(713,163,'0','',NULL),(712,163,'mselam','',NULL),(711,163,'fries10','',NULL),(710,163,'liaolx','',NULL),(709,163,'suddenlyliu','',NULL),(708,163,'moonsonwang','',NULL),(707,163,'wangbin','',NULL),(706,163,'gzl503','',NULL),(705,163,'yanyang1230','',NULL),(704,163,'NJGS03','',NULL),(703,163,'NJGS02','',NULL),(702,163,'NJGS01','',NULL),(701,163,'SHGS03','',NULL),(700,163,'SHGS02','',NULL),(699,163,'SHGS01','',NULL),(698,163,'GZGS03','',NULL),(697,163,'GZGS02','',NULL),(1157,171,'NJIMS03','',NULL),(1156,171,'CDIMS01','',NULL),(695,164,'kangjun','',NULL),(694,164,'lfgsta','',NULL),(693,164,'0','',NULL),(692,164,'mselam','',NULL),(691,164,'fries10','',NULL),(690,164,'liaolx','',NULL),(689,164,'suddenlyliu','',NULL),(688,164,'moonsonwang','',NULL),(687,164,'wangbin','',NULL),(686,164,'gzl503','',NULL),(685,164,'yanyang1230','',NULL),(684,164,'NJGS03','',NULL),(683,164,'NJGS02','',NULL),(682,164,'NJGS01','',NULL),(681,164,'SHGS03','',NULL),(680,164,'SHGS02','',NULL),(679,164,'SHGS01','',NULL),(678,164,'GZGS03','',NULL),(677,164,'GZGS02','',NULL),(676,164,'GZGS01','',NULL),(778,165,'admin','',NULL),(777,165,'kangjun','',NULL),(776,165,'lfgsta','',NULL),(775,165,'000000','',NULL),(774,165,'mselam','',NULL),(773,165,'ries10','',NULL),(772,165,'liaolx','',NULL),(771,165,'suddenlyliu','',NULL),(770,165,'moonsonwang','',NULL),(769,165,'wangbin','',NULL),(768,165,'gzl503','',NULL),(767,165,'yanyang1230','',NULL),(766,165,'GZGT01','',NULL),(765,165,'GZGT02','',NULL),(764,165,'GZGT03','',NULL),(763,165,'SHGT01','',NULL),(762,165,'SHGT02','',NULL),(761,165,'SHGT03','',NULL),(760,165,'NJGT01','',NULL),(759,165,'NJGT02','',NULL),(758,165,'NJGT03','',NULL),(757,165,'admin','',NULL),(1132,166,'admin','',NULL),(1131,166,'kangjun','',NULL),(1130,166,'lfgsta','',NULL),(1129,166,'000000','',NULL),(1128,166,'mselam','',NULL),(1127,166,'ries10','',NULL),(1126,166,'liaolx','',NULL),(1125,166,'suddenlyliu','',NULL),(1124,166,'moonsonwang','',NULL),(1123,166,'wangbin','',NULL),(1122,166,'gzl503','',NULL),(1121,166,'yanyang1230','',NULL),(1120,166,'GZIMS01','',NULL),(1119,166,'GZIMS02','',NULL),(1118,166,'GZIMS03','',NULL),(1117,166,'SHIMS01','',NULL),(1116,166,'SHIMS02','',NULL),(1115,166,'SHIMS03','',NULL),(1114,166,'NJIMS01','',NULL),(1113,166,'NJIMS02','',NULL),(1112,166,'NJIMS03','',NULL),(1111,166,'CDIMS01','',NULL),(1110,166,'CDIMS02','',NULL),(1109,166,'CDIMS03','',NULL),(1108,166,'admin','',NULL),(992,169,'GZGT01','',NULL),(991,169,'GZGT02','',NULL),(990,169,'GZGT03','',NULL),(989,169,'SHGT01','',NULL),(988,169,'SHGT02','',NULL),(987,169,'SHGT03','',NULL),(986,169,'NJGT01','',NULL),(985,169,'NJGT02','',NULL),(984,169,'NJGT03','',NULL),(983,169,'yanyang1230','',NULL),(982,169,'gzl503','',NULL),(981,169,'wangbin','',NULL),(980,169,'moonsonwang','',NULL),(979,169,'suddenlyliu','',NULL),(978,169,'liaolx','',NULL),(977,169,'fries10','',NULL),(976,169,'mselam','',NULL),(975,169,'0','',NULL),(974,169,'lfgsta','',NULL),(973,169,'kangjun','',NULL),(972,169,'admin','',NULL),(1155,171,'admin','',NULL),(1012,170,'kangjun','',NULL),(1011,170,'lfgsta','',NULL),(1010,170,'0','',NULL),(1009,170,'mselam','',NULL),(1008,170,'fries10','',NULL),(1007,170,'liaolx','',NULL),(1006,170,'suddenlyliu','',NULL),(1005,170,'moonsonwang','',NULL),(1004,170,'wangbin','',NULL),(1003,170,'gzl503','',NULL),(1002,170,'yanyang1230','',NULL),(1001,170,'NJGT03','',NULL),(1000,170,'NJGT02','',NULL),(999,170,'NJGT01','',NULL),(998,170,'SHGT03','',NULL),(997,170,'SHGT02','',NULL),(996,170,'SHGT01','',NULL),(995,170,'GZGT03','',NULL),(994,170,'GZGT02','',NULL),(993,170,'GZGT01','',NULL),(696,163,'GZGS01','',NULL),(1154,171,'NJIMS02','',NULL),(1153,171,'NJIMS01','',NULL),(1152,171,'SHIMS03','',NULL),(1151,171,'SHIMS02','',NULL),(1150,171,'SHIMS01','',NULL),(1149,171,'GZIMS03','',NULL),(1148,171,'GZIMS02','',NULL),(1147,171,'GZIMS01','',NULL),(1146,171,'yanyang1230','',NULL),(1145,171,'gzl503','',NULL),(1144,171,'wangbin','',NULL),(1143,171,'moonsonwang','',NULL),(1142,171,'suddenlyliu','',NULL),(1141,171,'liaolx','',NULL),(1140,171,'ries10','',NULL),(1139,171,'mselam','',NULL),(1138,171,'000000','',NULL),(1137,171,'lfgsta','',NULL),(1136,171,'kangjun','',NULL),(1135,171,'admin','',NULL),(1134,171,'admin','',NULL),(1055,173,'admin','',NULL),(1033,172,'kangjun','',NULL),(1032,172,'lfgsta','',NULL),(1031,172,'000000','',NULL),(1030,172,'mselam','',NULL),(1029,172,'ries10','',NULL),(1028,172,'liaolx','',NULL),(1027,172,'suddenlyliu','',NULL),(1026,172,'moonsonwang','',NULL),(1025,172,'wangbin','',NULL),(1024,172,'gzl503','',NULL),(1023,172,'yanyang1230','',NULL),(1022,172,'GZGS01','',NULL),(1021,172,'GZGS02','',NULL),(1020,172,'GZGS03','',NULL),(1019,172,'SHGS01','',NULL),(1018,172,'SHGS02','',NULL),(1017,172,'SHGS03','',NULL),(1016,172,'NJGS01','',NULL),(1015,172,'NJGS02','',NULL),(1014,172,'NJGS03','',NULL),(1013,172,'admin','',NULL),(1054,173,'NJGS03','',NULL),(1053,173,'NJGS02','',NULL),(1052,173,'NJGS01','',NULL),(1051,173,'SHGS03','',NULL),(1050,173,'SHGS02','',NULL),(1049,173,'SHGS01','',NULL),(1048,173,'GZGS03','',NULL),(1047,173,'GZGS02','',NULL),(1046,173,'GZGS01','',NULL),(1045,173,'yanyang1230','',NULL),(1044,173,'gzl503','',NULL),(1043,173,'wangbin','',NULL),(1042,173,'moonsonwang','',NULL),(1041,173,'suddenlyliu','',NULL),(1040,173,'liaolx','',NULL),(1039,173,'ries10','',NULL),(1038,173,'mselam','',NULL),(1037,173,'000000','',NULL),(1036,173,'lfgsta','',NULL),(1035,173,'kangjun','',NULL),(1034,173,'admin','',NULL),(1133,166,'linqingyi','',NULL),(1160,171,'linqingyi','',NULL),(1235,174,'GZIMS01','',NULL),(1234,174,'GZIMS02','',NULL),(1233,174,'GZIMS03','',NULL),(1232,174,'SHIMS01','',NULL),(1231,174,'SHIMS02','',NULL),(1230,174,'SHIMS03','',NULL),(1229,174,'NJIMS01','',NULL),(1228,174,'NJIMS02','',NULL),(1227,174,'NJIMS03','',NULL),(1226,174,'CDIMS01','',NULL),(1225,174,'CDIMS02','',NULL),(1224,174,'CDIMS03','',NULL),(1223,174,'yanyang1230','',NULL),(1222,174,'gzl503','',NULL),(1221,174,'w angbin','',NULL),(1220,174,'moonsonwang','',NULL),(1219,174,'suddenlyliu','',NULL),(1218,174,'liaolx','',NULL),(1217,174,'fries10','',NULL),(1216,174,'mselam','',NULL),(1215,174,'000000','',NULL),(1214,174,'lfgsta','',NULL),(1213,174,'kangjun','',NULL),(1212,174,'linqingyi','',NULL),(1211,174,'admin','',NULL),(1236,175,'yanyang1230','',NULL),(1237,175,'GZGS01','',NULL),(1238,175,'GZGS02','',NULL),(1239,175,'GZGS03','',NULL),(1240,175,'SHGS01','',NULL),(1241,175,'SHGS02','',NULL),(1242,175,'SHGS03','',NULL),(1243,175,'NJGS01','',NULL),(1244,175,'NJGS02','',NULL),(1245,175,'NJGS03','',NULL),(1246,175,'gzl503','',NULL),(1247,175,'wangbin','',NULL),(1248,175,'moonsonwang','',NULL),(1249,175,'suddenlyliu','',NULL),(1250,175,'liaolx','',NULL),(1251,175,'ries10','',NULL),(1252,175,'mselam','',NULL),(1253,175,'000000','',NULL),(1254,175,'lfgsta','',NULL),(1255,175,'kangjun','',NULL),(1256,175,'admin','',NULL),(1257,175,'admin','',NULL),(1334,176,'GZIMS01','',NULL),(1333,176,'GZIMS02','',NULL),(1332,176,'GZIMS03','',NULL),(1331,176,'SHIMS01','',NULL),(1330,176,'SHIMS02','',NULL),(1329,176,'SHIMS03','',NULL),(1328,176,'NJIMS01','',NULL),(1327,176,'NJIMS02','',NULL),(1326,176,'NJIMS03','',NULL),(1325,176,'CDIMS01','',NULL),(1324,176,'CDIMS02','',NULL),(1323,176,'CDIMS03','',NULL),(1322,176,'yanyang1230','',NULL),(1321,176,'gzl503','',NULL),(1320,176,'wangbin','',NULL),(1319,176,'moonsonwang','',NULL),(1318,176,'suddenlyliu','',NULL),(1317,176,'liaolx','',NULL),(1316,176,'fries10','',NULL),(1315,176,'mselam','',NULL),(1314,176,'000000','',NULL),(1313,176,'lfgsta','',NULL),(1312,176,'kangjun','',NULL),(1311,176,'linqingyi','',NULL),(1310,176,'admin','',NULL),(1283,177,'wingotech','',NULL),(1284,177,'admin','',NULL),(1335,178,'admin','',NULL),(1336,178,'linqingyi','',NULL),(1337,178,'kangjun','',NULL),(1338,178,'lfgsta','',NULL),(1339,178,'000000','',NULL),(1340,178,'mselam','',NULL),(1341,178,'fries10','',NULL),(1342,178,'liaolx','',NULL),(1343,178,'suddenlyliu','',NULL),(1344,178,'moonsonwang','',NULL),(1345,178,'wangbin','',NULL),(1346,178,'gzl503','',NULL),(1347,178,'yanyang1230','',NULL),(1348,178,'CDIMS03','',NULL),(1349,178,'CDIMS02','',NULL),(1350,178,'CDIMS01','',NULL),(1351,178,'NJIMS03','',NULL),(1352,178,'NJIMS02','',NULL),(1353,178,'NJIMS01','',NULL),(1354,178,'SHIMS03','',NULL),(1355,178,'SHIMS02','',NULL),(1356,178,'SHIMS01','',NULL),(1357,178,'GZIMS03','',NULL),(1358,178,'GZIMS02','',NULL),(1359,178,'GZIMS01','',NULL),(1360,178,'admin','',NULL),(1402,179,'yanyang1230','',NULL),(1401,179,'GZGT01','',NULL),(1400,179,'GZGT02','',NULL),(1399,179,'GZGT03','',NULL),(1398,179,'SHGT01','',NULL),(1397,179,'SHGT02','',NULL),(1396,179,'SHGT03','',NULL),(1395,179,'NJGT01','',NULL),(1394,179,'NJGT02','',NULL),(1393,179,'NJG','',NULL),(1392,179,'gzl503','',NULL),(1391,179,'wangbin','',NULL),(1390,179,'moonsonwang','',NULL),(1389,179,'suddenlyliu','',NULL),(1388,179,'liaolx','',NULL),(1387,179,'fries10','',NULL),(1386,179,'mselam','',NULL),(1385,179,'000000','',NULL),(1384,179,'lfgsta','',NULL),(1383,179,'kangjunT03','',NULL),(1382,179,'admin','',NULL);

/*Table structure for table `jrun_pluginhooks` */

DROP TABLE IF EXISTS `jrun_pluginhooks`;

CREATE TABLE `jrun_pluginhooks` (
  `pluginhookid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `pluginid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL DEFAULT '',
  `description` mediumtext NOT NULL,
  `code` mediumtext NOT NULL,
  PRIMARY KEY (`pluginhookid`),
  KEY `pluginid` (`pluginid`),
  KEY `available` (`available`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_pluginhooks` */

/*Table structure for table `jrun_plugins` */

DROP TABLE IF EXISTS `jrun_plugins`;

CREATE TABLE `jrun_plugins` (
  `pluginid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `adminid` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `name` varchar(40) NOT NULL DEFAULT '',
  `identifier` varchar(40) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `datatables` varchar(255) NOT NULL DEFAULT '',
  `directory` varchar(100) NOT NULL DEFAULT '',
  `copyright` varchar(100) NOT NULL DEFAULT '',
  `modules` text NOT NULL,
  PRIMARY KEY (`pluginid`),
  UNIQUE KEY `identifier` (`identifier`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_plugins` */

/*Table structure for table `jrun_pluginvars` */

DROP TABLE IF EXISTS `jrun_pluginvars`;

CREATE TABLE `jrun_pluginvars` (
  `pluginvarid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `pluginid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `title` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `variable` varchar(40) NOT NULL DEFAULT '',
  `type` varchar(20) NOT NULL DEFAULT 'text',
  `value` text NOT NULL,
  `extra` text NOT NULL,
  PRIMARY KEY (`pluginvarid`),
  KEY `pluginid` (`pluginid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_pluginvars` */

/*Table structure for table `jrun_pms` */

DROP TABLE IF EXISTS `jrun_pms`;

CREATE TABLE `jrun_pms` (
  `pmid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `msgfrom` varchar(15) NOT NULL DEFAULT '',
  `msgfromid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `msgtoid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `folder` enum('inbox','outbox') NOT NULL DEFAULT 'inbox',
  `new` tinyint(1) NOT NULL DEFAULT '0',
  `subject` varchar(75) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `delstatus` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`pmid`),
  KEY `msgtoid` (`msgtoid`,`folder`,`dateline`),
  KEY `msgfromid` (`msgfromid`,`folder`,`dateline`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_pms` */

insert  into `jrun_pms`(`pmid`,`msgfrom`,`msgfromid`,`msgtoid`,`folder`,`new`,`subject`,`dateline`,`message`,`delstatus`) values (11,'cannymok',74,71,'inbox',1,'[系统消息] 您发表的主题被执行管理操作',1296270357,'这是由论坛系统自动发送的通知短消息。\n\n[b]以下您所发表的回帖被 [url=http://bbs.esaying.cn:80/bbs/space.jsp?uid=74 ][i]cannymok[/i][/url] 执行 删除回复 操作。[/b]\n\n[quote]我平时最喜爱的游戏社区是盛大的糖果社区，因为发现糖果社区上，能查看玩过游戏的好友，比如我很喜欢玩三国杀，我能通过糖果社区和玩三国杀的好友进行交流。同时我也会去尝试玩玩其他好友玩过的游戏。\r\n 我喜欢游戏社区的理由，其实最重要就是既能玩游戏、结识新朋友、还能发微博、传照片。[/quote]\n[b]发表时间:[/b] 2011-01-28 16:27\n[b]所在论坛:[/b] [url=http://bbs.esaying.cn:80/bbs/forumdisplay.jsp?fid=27 ]GDT BBS - GS[/url]\n\n[b]操作理由:[/b]研究员不要发帖子啦\n\n如果您对本管理操作有异议，请与我取得联系。',0),(12,'cannymok',74,77,'inbox',1,'爱游戏社区',1296361293,'有个问题，我还不是很懂：\r\n1）通过昨天对糖果社区的体验，以及这个产品的介绍，大家觉得这个爱游戏怎样？哪些地方吸引你？ \r\n   虽然比起其他社区有点创新的思维，但我觉得这是在别人暂时还没有的基础上的创新。但是还暂时大同小异。 \r\n\r\n那你觉得怎样的创新才是真正的创新，才对你有吸引力呢？\r\n',0);

/*Table structure for table `jrun_pmsearchindex` */

DROP TABLE IF EXISTS `jrun_pmsearchindex`;

CREATE TABLE `jrun_pmsearchindex` (
  `searchid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `searchstring` varchar(255) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `pms` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pmids` text NOT NULL,
  PRIMARY KEY (`searchid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_pmsearchindex` */

/*Table structure for table `jrun_polloptions` */

DROP TABLE IF EXISTS `jrun_polloptions`;

CREATE TABLE `jrun_polloptions` (
  `polloptionid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `votes` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `polloption` varchar(80) NOT NULL DEFAULT '',
  `voterids` mediumtext NOT NULL,
  PRIMARY KEY (`polloptionid`),
  KEY `tid` (`tid`,`displayorder`)
) ENGINE=MyISAM AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_polloptions` */

insert  into `jrun_polloptions`(`polloptionid`,`tid`,`votes`,`displayorder`,`polloption`,`voterids`) values (16,86,13,0,'支持','01	1	'),(17,115,12,0,'哈哈\r','0'),(18,115,80,0,'呵呵\r','0'),(19,115,15,0,'嘻嘻\r','011	'),(12,86,15,0,'好样的','0'),(13,86,16,0,'顶下','0'),(14,86,17,0,'强','0'),(15,86,18,0,'高兴','0'),(20,115,19,0,'哦哦','0'),(21,116,23,0,'支持','012	12	1	'),(22,116,21,0,'不好吧 ','0'),(23,116,22,0,'顶下','0'),(24,116,23,0,'遏制膨胀','0'),(25,116,24,0,'希望有效果','0'),(26,119,50,0,'选项一',''),(27,119,51,0,'选项二',''),(28,119,52,0,'选项三',''),(29,119,53,0,'选项四',''),(30,119,54,0,'选项五',''),(31,124,0,0,'请您用一个词表达对 y运的联想\r',''),(32,124,0,0,'请您用一个词表达对 y运的联想\r',''),(33,125,1,0,'好','1	'),(34,125,0,0,'不好',''),(35,125,0,0,'十分不好',''),(36,126,1,0,'好\r','1	'),(37,126,0,0,'恶心\r',''),(38,126,0,0,'扰民\r',''),(39,126,0,0,'腐败\r',''),(40,126,0,0,'无所谓',''),(41,127,0,0,'n\r',''),(42,127,0,0,'b\r',''),(43,127,0,0,'c\r',''),(44,127,0,0,'d',''),(45,131,2,0,'前台咨询区\r','102	114	'),(46,131,12,0,'产品展示区\r','44	70	69	83	81	101	72	99	104	80	110	89	'),(47,131,0,0,'VIP区\r',''),(48,131,5,0,'产品体验区','73	82	103	100	115	'),(52,133,1,0,'电信天翼手机\r','80	'),(53,133,16,0,'我的e家\r','73	76	77	81	82	113.96.120.134	83	101	84	103	100	104	107	110	89	102	'),(54,133,0,0,'商务领航\r',''),(55,133,0,0,'189邮箱/爱音乐','');

/*Table structure for table `jrun_polls` */

DROP TABLE IF EXISTS `jrun_polls`;

CREATE TABLE `jrun_polls` (
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `multiple` tinyint(1) NOT NULL DEFAULT '0',
  `visible` tinyint(1) NOT NULL DEFAULT '0',
  `maxchoices` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `reporttype` mediumint(8) NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_polls` */

insert  into `jrun_polls`(`tid`,`multiple`,`visible`,`maxchoices`,`expiration`,`reporttype`) values (86,0,0,1,0,1),(115,0,0,1,0,2),(116,0,0,1,0,3),(119,0,0,1,0,1),(124,1,0,2,1290927291,3),(125,0,0,1,0,1),(126,0,0,1,0,1),(127,0,0,1,0,1),(131,0,0,1,0,1),(133,0,0,1,0,1);

/*Table structure for table `jrun_posts` */

DROP TABLE IF EXISTS `jrun_posts`;

CREATE TABLE `jrun_posts` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `first` tinyint(1) NOT NULL DEFAULT '0',
  `author` varchar(15) NOT NULL DEFAULT '',
  `authorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `subject` varchar(80) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `message` mediumtext NOT NULL,
  `useip` varchar(15) NOT NULL DEFAULT '',
  `invisible` tinyint(1) NOT NULL DEFAULT '0',
  `anonymous` tinyint(1) NOT NULL DEFAULT '0',
  `usesig` tinyint(1) NOT NULL DEFAULT '0',
  `htmlon` tinyint(1) NOT NULL DEFAULT '0',
  `bbcodeoff` tinyint(1) NOT NULL DEFAULT '0',
  `smileyoff` tinyint(1) NOT NULL DEFAULT '0',
  `parseurloff` tinyint(1) NOT NULL DEFAULT '0',
  `attachment` tinyint(1) NOT NULL DEFAULT '0',
  `rate` smallint(6) NOT NULL DEFAULT '0',
  `ratetimes` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `groups` char(20) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `fid` (`fid`),
  KEY `authorid` (`authorid`),
  KEY `dateline` (`dateline`),
  KEY `invisible` (`invisible`),
  KEY `displayorder` (`tid`,`invisible`,`dateline`),
  KEY `first` (`tid`,`first`)
) ENGINE=MyISAM AUTO_INCREMENT=610 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_posts` */

insert  into `jrun_posts`(`pid`,`fid`,`tid`,`first`,`author`,`authorid`,`subject`,`dateline`,`message`,`useip`,`invisible`,`anonymous`,`usesig`,`htmlon`,`bbcodeoff`,`smileyoff`,`parseurloff`,`attachment`,`rate`,`ratetimes`,`status`,`groups`) values (428,27,163,0,'SHGS03',82,'',1296301209,'[color=Purple]1）我觉得盛大的糖果社区还在起步阶段，很多东西还在慢慢发展中。。\r\n首先它的界面很时尚.看着很舒服.\r\n其次它的附属网页游戏多.加上盛大就是做游戏起家的,游戏风格也都是甜美可爱的风格，自然这是他的强项.\r\n2）我觉得它的游戏都单一了点，挑战性不大，所以不是很耐玩\r\n其实也可以出点类似鱼开心网偷菜那样的游戏，虽然简单，但是互动强，吸引人去泡在上面[/color]','114.84.43.66',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(429,27,163,0,'SHGS03',82,'',1296301312,'[color=DarkOrchid]也许对于年长一些的，或者办公室的白领来说会稍微有些幼稚，但是我个人喜欢它的风格~继续加油啦[/color]','114.84.43.66',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(430,27,155,0,'SHGS03',82,'',1296301467,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-29 14:48 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=387&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url][quote] 原帖由 [i]SHGS03[/i] 于 2011-01-28 20:10 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=315&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url][quote] 原帖由 [i]cannymok[/i] 于 2011-01-28 18:16 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=311&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url][quote] 原帖由 [i]SHGS03[/i] 于 2011-01-28 15:25 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=289&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]我平时上的游戏社区还蛮多的，比如会在17173看看最近的游戏介绍或公告、也会去开心、人人上面玩玩偷菜，抢车位之类的小游戏，还有糖果社区的三国杀，泡泡鱼之类的游戏。这些游戏的画面都比较精致，像糖果社区就是比较女孩子点，画面都是可爱甜美的风格，但是很多游戏就比较简单了一点，不是很耐玩~ [/quote] 那你喜欢怎样的游戏画面啊？ [/quote] 我就是觉得它里面的游戏单一了点，没有很大的挑战性，时间长了会不耐玩噢，但是我个人还是很喜欢他的画面都，比如泡泡鱼里面眼睛会转悠的小鱼，梦幻小镇中超可爱的人物等等。~ [/quote] 像糖果社区里，还有一些和大型网友结合的，比如龙之谷，你觉得如何呢？你希望网友小游戏和大型网游都能在社区里玩吗？ [/quote][b][u][color=darkorchid]嗯，我希望网页小游戏和大型的网游都能在社区里玩，一来省去了下载的时间，二来无论是在外面还是在家里都能及时玩到我想要玩的游戏，这样很好！~[/color][/u][/b]','114.84.43.66',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(431,27,155,0,'NJGS02',92,'',1296301665,'我平时上的游戏社区蛮多的，比如开心网、QQ跑的快，欢乐斗地主，QQ农场牧场，抢车位，Q宠大乐斗之类的小游戏，因为画面做的蛮精细的，操作方便，大多免费等等，最主要的这些小游戏都是QQ号上的熟人（同学，朋友，同事，亲人等等）不但可以在大家间找到乐子，同时也可以增进大家之间的感情！我觉得游戏不必太过于复杂，但需要有一定的技术含量这样可以经久耐玩！','121.229.201.161',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(432,27,163,0,'NJGS02',92,'',1296302594,'我觉得盛大的糖果社区刚刚起步，画质做的不错，不是夸张的那种。盛大做的游戏还蛮多的\r\n，许多网游在一定时间内虽然让人玩起来感觉刺激，如果不去花钱什么的就得花费很多的时间耗在上面，这样很影响身体健康，学习和工作等！这样的游戏也不耐玩，我很喜欢互动的游戏，比如QQ农牧场，抢车位，QQ餐厅，Q宠大乐斗什么的，游戏很简单，在朋友，同事，同学，亲人之间找乐子！','121.229.201.161',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(433,29,171,0,'SHIMS03',103,'',1296302818,'1）目前主要使用的通讯工具有固定电话、手机、网上聊天工具。网上聊天工具用的最多，其次是手机。\r\n2）一般使用固定电话主要就是和家里的亲戚联系。\r\n3）手机就是和朋友啊，同事联系，范围蛮广的。\r\n4）网上聊天工具是和朋友，亲戚，还有外地的同事，工作上面的事情。方便。\r\n\r\n\r\n1)听说过视频通话，网络上知道的\r\n2)经常用视频通话。 \r\n\r\na)i.使用过QQ，msn等视频通话工具，一般是和外地的家人，朋友联系，晚上下班回到家里的时候使用。\r\n每个礼拜5，6次。\r\nii.视频通话工具使用起来还是蛮方便的。需求也基本能够满足。\r\n视频通话工具还需要改进的地方就是画质最好更流畅清晰，画质真实度要提升，音质与画面衔接要更加紧密些，视频延时要短。','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(434,27,163,0,'GZGS01',77,'',1296302953,'糖果社区是不错，一切都很自然，一顺手。\r\n和其他平台的话对比，互动性和交友性的游戏还很欠缺。但日后应该会有很多发展得机会。\r\n（个人对盛大的评价：网络游戏里面，盛大虽有名气，但某些方面的口碑还是令众多用户失望的。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(435,27,164,0,'GZGS01',77,'',1296303575,'1）通过昨天对糖果社区的体验，以及这个产品的介绍，大家觉得这个爱游戏怎样？哪些地方吸引你？ \r\n   虽然比起其他社区有点创新的思维，但我觉得这是在别人暂时还没有的基础上的创新。但是还暂时大同小异。\r\n2）对这12个功能点，你最喜欢的是哪些？为什么？ \r\n   最喜欢的是第九点，因为用户可以第一时间拿到测试游戏的账号，相信会吸引很多为游戏账号而来的短暂用户（如：前段时间的《龙之谷》）。\r\n3）关于第7点“社区活动”，你们有兴趣参与吗？为什么？ \r\n   有兴趣。想看看奖品到底能不能牵动用户的心。\r\na)如果你玩的游戏积分可以在社区里使用，你觉得如何？\r\n  这个还是非常的精明的方法。但要看看社区的东西值不值得用户们关注。而且，希望社区里面的是纯游戏积分。\r\n4）关于第3点“微博”，你会使用吗？为什么？ \r\n   这里的微博是指新浪的微博还是指腾讯的微博，或者是指你们的微博呢。（个人评价：腾讯微博比较拒绝。）\r\n5）如果你看到别人的信息里有介绍他玩过的游戏，你会有冲动去玩玩看吗？什么时候会\r\n   要看是什么游戏。（如:龙之谷测试前，用户们一直在疯狂等待的状态，很多时候都是望梅止渴。）','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(436,27,164,0,'SHGS01',69,'',1296306750,'1）我觉得还不错，它几乎包括了现有网络社区的所有热门功能，而且还在它们的基础上有些变动，更加人性化   了~ \r\n2）看了之后感觉有几条还是不错的，比如1 2 5 6 7 9 10 一方面朋友与朋友之间可以有更多的交流 看到些热门话题 大家可以一起讨论 \r\n3）比较有兴趣 一方面运气能抽个奖什么的 另一方面能让玩家不管是线上还是线下都能有更多的互动\r\n4）会使用 本人平时比较喜欢和朋友家人一起聊天 或者分享彼此之间的快乐 这样就更拉近了彼此的距离\r\n5）会 至于什么时候玩还是看到时有没有空 或者心情吧','180.155.179.123',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(437,29,171,0,'SHIMS01',109,'谈谈对IPTV游戏的期望与建议',1296306973,'大家好\r\n目前我使用的通讯工具包括固定电话、手机、网上聊天工具\r\n手机使用最多\r\n其次是网上聊天工具\r\n\r\n一般使用固定电话主要是和我的家人联系\r\n\r\n使用手机主要是家人、同事、朋友。\r\n\r\n网上聊天工具主要包括老同学、朋友及认识的网友等\r\n\r\n下面想向大家了解一些视频通话的一些使用情况（2个问题都需要回答哦，大家记得踊跃回答，每个回答最好标明对应的题号） \r\n\r\n1)大家听说过视频通话吗？是如何知道的？\r\n知道视频通话，是一次参观中国电信的体验馆时知道的。图像声音非常清晰\r\n\r\n2)大家使用过视频通话吗？\r\n是在上次参观中使用的。其他没有\r\n\r\na)如果有： \r\ni.使用过哪种视频通话工具？用来跟哪些人联系？在什么情况下使用？使用频率如何？\r\n使用中国电信的视频通话，主要是用于工作上的视频会议及联系外地的亲戚。\r\n\r\nii.大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工具还需要哪些改进？\r\n目前还没有在生活中真正用过\r\n\r\nb)如果没有，为什么没有使用？\r\n担心资费太高，速度慢影响通话效果。','58.218.204.235',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(438,27,163,0,'SHGS01',69,'',1296307048,'我觉得盛大的糖果社区还在起步阶段，总体来说感觉不错吧 画面做的很细腻 游戏也很多 就是缺少点游戏性 不是很耐玩 和有点女性化。许多网游在一定时间内虽然让人玩起来感觉刺激，如果不去花钱什么的就得花费很多的时间耗在上面，这样很影响身体健康，学习和工作等！','180.155.179.123',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(439,27,172,1,'cannymok',74,'1月29日主题二-付费问题',1296307363,'1）通过以上游戏社区的了解，你是否有兴趣里面的游戏产品，或者购买道具、各种会员服务资格等？愿意付费的金额大概多少？请填写下表：\r\n	是否愿意支付？	愿意付费的金额？\r\n游戏产品		\r\n道具		\r\n会员服务资格\r\n	\r\n\r\n2）你希望通过什么形式进行付费，如手机短信扣费、网上银行、支付宝等等？\n[[i] 本帖最后由 cannymok 于 2011-01-29  21:25 编辑 [/i]]			','121.201.254.252',0,0,0,0,0,0,0,0,0,0,0,NULL),(440,27,173,1,'cannymok',74,'1月29日主题三-给爱游戏社区的建议',1296307475,'聊了2天爱游戏社区，以你们对心目中游戏社区的理解，还有什么好的建议，希望能有什么功能，让你们更有兴趣去使用？\n[[i] 本帖最后由 cannymok 于 2011-01-29  21:26 编辑 [/i]]			','121.201.254.252',0,0,0,0,0,0,0,0,0,0,0,NULL),(441,27,164,0,'NJGS01',79,'',1296307556,'觉得满不错的 以后会经常使用的','114.221.44.192',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(442,27,173,0,'NJGS01',79,'',1296307614,'可以多增加点互动的游戏','114.221.44.192',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(443,27,172,0,'NJGS01',79,'',1296307669,'愿意支付的 一般20-30 当然支付宝最方便咯','114.221.44.192',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(444,29,171,0,'SHIMS01',109,'哈哈 还有视频聊天之经历',1296307772,'看了前面大家的讨论，确实，哈哈，还有视频聊天哦\r\n所以，视频通话包括视频聊天了\r\n我家里有三台电脑，前两台电脑一直是不带摄像头的\r\n去年我和爱人去百思买，我们看中上网本促销，在我爱人犹豫的时候我决定买了一个三星本本，后来老婆说买的真划算。\r\n我家是24小时不限时上网，有了这个本本后我们就可以和许多朋友网上视频聊天了。\r\n我女儿三岁，她有一个同年同月同日生的小妹妹。他们经常碰头去公园玩。但是前段时间天气下雪，不能出去了，这是她的妈妈也装了摄像头，小朋友可以网上视频见面了，声音画面总算不错，小宝宝开心死了。','58.218.204.235',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(445,27,172,0,'cannymok',74,'',1296307813,'[quote] 原帖由 [i]NJGS01[/i] 于 2011-01-29 21:27 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=443&ptid=172][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]愿意支付的 一般20-30 当然支付宝最方便咯 [/quote]\r\n\r\n你愿意支付购买上述提及的哪样商品或服务呢？','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(446,27,173,0,'cannymok',74,'',1296307857,'[quote] 原帖由 [i]NJGS01[/i] 于 2011-01-29 21:26 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=442&ptid=173][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]可以多增加点互动的游戏 [/quote]\r\n\r\n可以举例说明一下吗？','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(447,27,164,0,'GZGS03',83,'',1296307863,'1.覺得不錯。比較創新\r\n2.比較喜歡1.2.3.4.5.6.10以上幾點都可以跟朋友共同分享身邊的事情。\r\n3.比較有興趣。可以碰運氣抽獎。或者玩玩在線遊戲\r\n4.微博我會使用的。可以立刻跟所有人分享現在的心情\r\n5.會的。等有空再去玩這個遊戲','116.28.66.33',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(448,27,163,0,'GZGS03',83,'',1296307961,'感覺不錯吧。可能是剛開始的緣故吧，缺少了一點互動性','116.28.66.33',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(449,29,171,0,'cannymok',74,'',1296308003,'大家要记得踊跃发言哦！每天我们会评出发帖最多的前三名灌水英雄榜，有现金奖励的哦～～～ \r\n但那些纯粹灌水的帖子，例如：沙发，路过啊，我们是不算进去的。 \r\n所以，大家多点发有质量、有内容的内涵帖哦！！！！！！','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(450,27,172,0,'GZGS03',83,'',1296308038,'可能不會付款吧.因為畢竟是遊戲，不想花錢在這些地方上','116.28.66.33',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(451,27,173,0,'GZGS03',83,'',1296308082,'希望可以跟不同地方的朋友交友','116.28.66.33',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(452,29,166,0,'SHIMS02',107,'',1296308100,'我是RED，年龄嘛，保密。IT行业，吃喝玩乐我都喜欢，是电子产品发烧友。','116.237.203.227',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(453,29,166,0,'cannymok',74,'',1296308191,'看来很多人都喜欢电子产品喔','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(454,28,165,0,'SHGT01',72,'',1296308301,'1.个人觉得付费的问题可以使用IPTV宽带代收的方式比较方便，也很直观。\r\n\r\n2.付费渠道方面还是直接跟着宽带的账单支付即可。当然也可以使用点卡，支付宝，网银等方式，可以方便年付的客户。\r\n\r\n3.如果设虚拟币，以点卡形式销售，也可以满足年付费的客户。可以接受。但是账单相对更方便。\r\n\r\n4.两种收费形式，我更喜欢第一种，年付的感觉让人觉得很方便。按次收费会导致费用过高，减少游戏客户。\r\n\r\n5我对这两种收费形式可接受的价格差不多是300/年，30/月。如果一定要按次收费的话，1块钱/次差不多。 \r\n\r\n6.道具最好不要按次收费，不同的道具价格不同，而且都应该设定一个有效期，比如1个月或半年等。价格可以控制在1元~100元之内。或者可以引进游戏金币的概念，使用金币购买，而金币可以通过充值或者账单支付或者社区奖励等。这样在社区里面还可以通过金币购买一些游戏周边。如果有攻略或者道具之类的还可以通过金币交易。','58.37.146.233',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(426,29,166,0,'SHIMS03',103,'',1296299825,'大家好，我叫 蓉蓉。27岁，物流业后勤方面的。性格开朗。我喜欢旅游，shopping，上网。K歌/蛮喜欢电子产品的','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(427,27,164,0,'SHGS03',82,'',1296300884,'1）我觉得还不错，它几乎包括了现有网络社区的所有热门功能，而且还在它们的基础上有些变动，更加人性化了~\r\n2）看了这个介绍后，我比较喜欢  6.互动资讯  7.社区活动  11.勋章\r\n像互动资讯这是社区必须的，靠着这类资讯我们可以第一时间讨论到热门话题，还可以了了解到朋友的最新动态，里面的成员推荐也很好，可以让朋友的朋友变成自己的朋友，增强了交际圈~\r\n社区活动不同于在线网游，更加强了互动性，而且不像游戏那么有，它会让我觉得很亲切，就像在家里附近的社区一样~\r\n另外喜欢勋章是因为它可以提高我对游戏的积极性和荣誉感\r\n3）如果有这个社区活动我一定会参与，因为我觉得通过网络也能感受到现实生活中的互动，会更加使我有亲切感\r\na：游戏积分如果能在社区里使用，我觉得很好，这样也会让我对游戏上瘾，而且能让我同时玩游戏和社区活动，不会时间一长就腻掉\r\n4）微薄我可能不会使用，因为个人觉得目前微薄做的最好的还是新浪，而且用习惯了吧\r\n5）可能会也可能不会，具体要看我那个时候是不是很想玩新的游戏咯，也会会去看看，但不一定会玩\r\n[color=DarkOrchid][/color]','114.84.43.66',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(455,27,172,0,'SHGS01',69,'',1296308332,'如果真的很喜欢这个游戏的话 应该会适当的付吧 具体多少还是要看的吧\r\n付费的话 手机短信 或者支付宝都可以','180.155.179.123',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(456,27,173,0,'SHGS01',69,'',1296308379,'个人觉得可以借鉴下开心网这样的模式吧 个人觉得开心这样的模式还是比较喜欢的','180.155.179.123',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(316,27,155,0,'SHGS03',82,'',1296216744,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-28 17:37 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=306&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]再补充2个问题： 3）你平时有在这些社区里支付过一定费用吗？ 4）你有使用过这些社区的客服吗？ [/quote]\r\n以前在人人网办过vip会员支付过费用，后来有了登入积分就会自动赠送会员哦~我觉得还蛮人性化的','114.84.39.106',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(318,8,133,0,'GZGS01',77,'',1296220229,'发现这个区，除了这个贴。其他贴完全进不去。说什么没权进入。这该怎么办。版主快解决一下','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(319,8,133,0,'GZGS03',83,'',1296220786,'我的E家這個套餐。很優惠，網速也比較好。相對穩定','218.14.17.163',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(320,27,155,0,'GZGS03',83,'',1296221522,'平時在開心網都是玩轉帖和農場的。農場的畫面不錯。色調很搭配很好。玩開心網又可以跟朋友同學交流，不過有時轉的帖子被刪了之後，我就再找不了','218.14.17.163',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(321,27,156,0,'GZGS03',83,'',1296221634,'大家好~我叫yimru。24歲。做在工程公司做文員。我喜歡上社交網站交朋友。空閒的時候 會玩玩QQ遊戲。還喜歡逛街聽歌旅遊:lol','218.14.17.163',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(322,27,155,0,'GZGS03',83,'',1296221710,'喜歡開心網。','218.14.17.163',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(323,10,129,0,'匿名',0,'',1296221733,'加油,大家又多了个交流的平台','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(324,12,131,0,'GZGS03',83,'',1296221818,'產品展示注意力吸引多一點','218.14.17.163',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(325,28,160,0,'SHGT02',73,'',1296222105,'希望加入点wii类的体感操作就好了.又比较符合电视机游戏的特点,又能\"减肥\"','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(326,8,133,0,'SHGT02',73,'',1296222310,'我已经办理了好几年拉.算是老用户啦.(也给了你们不少GDP)啦','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(327,12,131,0,'SHGT02',73,'',1296222395,'没有亲自体验.说的再好.都无法领会.一位伟人说的好.实践是检验真理的唯一方法','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(328,28,157,0,'SHGT02',73,'',1296222565,'怎么没人的内.难道就我一个人啊:L','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(329,28,159,0,'SHGT02',73,'',1296222619,'就我一个人回答问题啊,没人啊.太孤独了啊','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(330,10,129,0,'SHGT01',72,'',1296222742,'灰常不错，很给力！严重支持！','202.64.157.105',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(331,17,130,0,'SHGT01',72,'',1296222803,'天翼手机，我的e家和189邮箱我都在用，很给力！','202.64.157.105',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(332,28,159,0,'SHGT01',72,'',1296224073,'1)IPTV平台中很容易就找到了游戏频道，就在IPTV的首页。\r\n2)对目前IPTV游戏内容个人感觉有点单一，无非就是一些棋牌类的小游戏和操作简便的休闲游戏。\r\n3)我最钟情于棋牌类的游戏。对益智、教育类等没有太多兴趣。如果要增加游戏内容，我建议增加角色扮演、飞行射击等游戏，可以有效利用网络平台优势，扩大人人对话的趣味性和刺激性； \r\n4)我一般使用IPTV使用时间有5个小时左右/天，但玩IPTV游戏时间最多只有1小时； \r\n5)我目前对使用遥控器玩IPTV游戏是非常不习惯，我觉得遥控器功能过于简单，没办法唤起玩家对游戏的积极性；如果增加相应手柄玩IPTV游戏，我会很有兴趣的，比如可以设置类似Wii或者XBox360的无线游戏手柄； \r\n6)说到IPTV社区，我觉得还可以接受。已经申请注册，但是觉得打字很不方便，社区交友等功能对我来说要求不高。不过如果将来会有规模化的网络游戏入驻IPTV游戏频道，我觉得一个好的社区建设时很重要的！ \r\n7)你用户体验方面，我建议可以延长体验时间，增加内容！','202.64.157.105',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(333,28,157,0,'SHGT01',72,'',1296224475,'大家好，我是张一鸣，在一家外资的快消品公司做销售主管。因为工作的关系，平时比较忙，也很累，所以空下来就喜欢玩点游戏神马的。也可以上微博和朋友们沟通，聊天。我最喜欢的网络游戏包括RO，WOW和Travian。RO现在基本不玩了，因为很多朋友都退出了。WOW和Travian还在坚持（其实是我乐在其中）。单机游戏我喜欢飞行射击类的，比如荣誉勋章，使命召唤，战地之类的。最近在和我弟弟一起玩虐杀原型，听名字是不是感觉很暴力，其实。。。也很暴力，哈哈。另外我喜欢玩实况足球，为了这个，我特地买了PSP可以在路上玩。另外Wii也是我很喜欢的游戏机，我经常用wii锻炼身体。\r\nOK，除了游戏，我平时也爱打桌球，看体育比赛，逛街，唱歌之类的。毕竟人要在现实生活中才能找到自己的价值，对吧？','202.64.157.105',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(334,17,130,0,'SHGT03',101,'',1296224504,'产品体验区包括的真全面，刚申请了189邮箱，很方便','114.94.200.131',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(335,12,131,0,'SHGT03',101,'',1296224637,'产品展示区 不错，可以更清楚的看到产品，有真实感哦','114.94.200.131',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(336,28,160,0,'SHGT01',72,'',1296224693,'嗯，这些我已经了解了。我认为IPTV因为跟上世界游戏的主轨道，把握主力消费群的需求。看看都是一些什么游戏在大行其道。我建议可以和一个游戏平台合作，比如说PS3或者XBox360，或者Wii。在IPTV上面设一个客户端，可以联系我们的游戏平台，然后通过IPTV的网络，和网络上面的其他用户一起竞技或者分享游戏的快乐。','202.64.157.105',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(337,8,133,0,'SHGT03',101,'',1296224788,'我的E家非常的不错，速度很快也很稳，不大会出错，用了好几年了，之前换过别的，发现还是我的E家比较好，之后又换回来了，以后还是会坚持使用的','114.94.200.131',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(338,12,131,0,'SHGT01',72,'',1296224845,'产品展示和体验，都是消费者获取产品信息的重要渠道，应该是重点！','202.64.157.105',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(339,27,155,0,'SHGS02',76,'',1296224852,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-28 17:37 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=306&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]再补充2个问题： 3）你平时有在这些社区里支付过一定费用吗？ 4）你有使用过这些社区的客服吗？ [/quote]\r\n\r\n\r\n    我平时有在游戏社区支付过费用的 买些游戏道具 游戏币之类的\r\n\r\n    我使用过社区客服的 问一些游戏道具使用方法之类的问题还有就是碰到问题的时候我会在第一时间和在线客服联系的','180.155.122.164',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(340,28,159,0,'SHGT03',101,'',1296225327,'1-还是很容易就找的到，首页很明显\r\n2-觉得一般，有点幼稚，没很大的兴趣\r\n3-个人觉得可以增加角色扮演类的，一般游戏都是用来打发时间的，现在玩的最多也就是棋牌类了\r\n4-IPTV，一般一直都会看一些回播或者是电影电视剧，游戏也会玩，每天都会进去玩玩，相对看片子更多一些\r\n5-不太习惯用遥控器玩游戏，总觉得很不搭调，而且老是按错，希望有所改进\r\n6-我对IPTV社区，还是比较陌生一点，总觉得有点奇怪，兴趣不是很大，不太能吸引到我\r\n7-最后希望可以增加内容和类别，延长时间','114.94.200.131',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(341,28,160,0,'SHGT03',101,'',1296225612,'这个已经了解了，我觉得IPTV很能更上时代的步伐哦，可能游戏还是较为幼稚，\r\n在无聊或者比较累的时候，放松自己时，玩玩还不错，看了画面也很舒服，总体觉得还比较满意\r\n如果多点角色类的或者更能吸引我玩的时间更长','114.94.200.131',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(342,10,129,0,'SHGT03',101,'',1296225767,'顶一个，电信的服务可是越来越好了','114.94.200.131',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(343,27,156,0,'GZGS01',77,'',1296226534,'姓名：kaihin\r\n年龄：20\r\n职业：待业 \r\n性格：乐天开放 \r\n爱好：上网，打球，游戏，认识异性朋友。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(344,27,156,0,'SHGS01',69,'',1296226931,'姓名：Ice\r\n年龄：24\r\n职业：自由职业\r\n性格：外向开朗\r\n爱好：上网 唱歌 打牌 运动 健身','180.155.176.196',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(345,27,155,0,'GZGS01',77,'',1296227473,'1）你平时有使用哪些游戏社区（QQ空间《QQ农场，牧场，餐厅》，微博《创世纪2012》，三国杀）\r\n      觉得它们的整体界面怎样，哪里喜欢（DIY界面自由度较高，画面较好，能及时了解多方面信息，会用电脑    的都会很快上手，电脑不太熟悉的可能要花点时间了解）？哪里不喜欢？ （例如：QQ空间，某些功能要收钱）\r\n2）你们最喜欢社区里的哪几个功能（QQ空间《QQ农场，牧场，餐厅》，微博《创世纪2012》，三国杀）？有没有不满意的地方，是哪些？为什么？ \r\n    （1：QQ空间的黄钻之类的服务，觉得一个网络的平台，代理商应该提供多一点的服务给用户选择。收费之类的动作，明显就是站在“利”字的立场上面。唤作在国外的某些游戏或者代理商。他们就算要收费，适当的时候所送的礼品价值比用户原来付出的价钱高，频率也高。）\r\n   （2：微博这方面做的很好，但是每次上传的图片只能是一张。《可能考虑到用户有手机客户端关系》）\r\n   （3：三国杀也是某些功能要收费。但它只是游戏而已。所以对于本人关系不大）\r\n3）你平时有在这些社区里支付过一定费用吗？\r\n   （QQ空间：绝不。微博：没有。三国杀：没必要）\r\n4）你有使用过这些社区的客服吗？\r\n   （有。如三国杀忘记密码的时候给客服打电话查询如何操作才能重获密码。）','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(346,27,156,0,'GZGS02',93,'',1296227765,'哈哈，大家好，我叫Match，个个都叫我Match哥~\r\n 24 years old。。。\r\n职业：学生\r\n酷爱上网，踩roller，爱交朋友，热情奔放。。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(347,27,155,0,'SHGS01',69,'',1296227799,'1 我平时上的游戏社区还蛮多的 例如开心网 玩玩烧菜 钓鱼 开心庄园 ，17173上面看看有没有新的游戏   还有糖果社区泡泡鱼之类的游戏。它们的整体界面总体感觉都还不错 个人觉得简洁明了就可以了 不需要很花哨 \r\n2 功能的话我感觉目前这些论坛的功能都能满足玩家的需求  比如开心网查找好友的功能 因为现在都昵称都用实名了 查找起来很方便很快 还有就是能看到好友的好友分享出来的帖 这样信息网又广了 这几点我都很喜欢\r\n3 暂时还没有 \r\n4 有','180.155.176.196',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(348,27,155,0,'GZGS02',93,'',1296228665,'1）你平时有使用哪些游戏社区？觉得它们的整体界面怎样\r\n\r\n？哪里喜欢？哪里不喜欢？ \r\nQQ空间、人人网里面的基于网页的游戏和它们的社区囖，开\r\n\r\n心网都有上的。整体来说觉得它们针对用户自定义的开放性\r\n\r\n比较大，web3.0风格的界面很喜欢；不喜欢的就是有些地方\r\n\r\n太过繁杂，因为官方偶尔加入了AD。。\r\n2）你们最喜欢社区里的哪几个功能？有没有不满意的地方\r\n\r\n，是哪些？为什么？\r\n 喜欢的：人人网里面的游戏分类入口，分类明了，一目了然，想玩就玩，另外不同游戏有不同的论坛分类，而在游戏里面有时也能见到入口，交流方便，主要是看秘籍方便。QQ的就不用说了，偷菜人人都知道，利用强大用户群的IM软件，有啥动静都知道，哈哈，上瘾了都。\r\n不喜欢的：A.收费 B.广告  C.不让用外挂  D.手机支持性尚不高\r\n3）你平时有在这些社区里支付过一定费用吗？\r\n从未投资过腾讯一分钱； 一般是happy的，不付钱，付钱不如用挂，省时省力省心省钱……\r\n4）你有使用过这些社区的客服吗？ \r\n有，例如有一次人人网的账号被黑了，被删了很多东西，call客服，客服在网上用Q同我沟通，并帮忙拿回。有时无聊都有找过客服，嘿嘿。一般都是账号问题。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(349,28,160,0,'GZIMS02',102,'',1296266288,'知道有这些，但没有玩过','183.5.58.92',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(350,27,155,0,'cannymok',74,'',1296270277,'3）你平时有在这些社区里支付过一定费用吗？\r\n   （QQ空间：绝不。微博：没有。三国杀：没必要）\r\n4）你有使用过这些社区的客服吗？\r\n   （有。如三国杀忘记密码的时候给客服打电话查询如何操作才能重获密码。） [/quote]\r\n\r\n\r\n我有点不是很懂：\r\n1。为什么认为三国杀没必要使用支付功能呢？\r\n2。当时使用客服找回密码的经历感觉如何？对客服的服务满意吗？','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(355,27,164,1,'cannymok',74,'1月29日主题一-给大家介绍个新的游戏平台',1296271096,'昨天咱们已经讨论过平时会玩的游戏平台，今天，我给大家隆重推荐一个新的游戏平台（此处应有掌声~~~~~~~~~~~）\r\n\r\n爱游戏游戏社区！！！！！！！！\r\n\r\n它是中国电信爱游戏平台（game.189.cn）下的一个网络社区，主要是整合爱游戏平台下的各款游戏资源的社区平台。由于目前该平台尚未开通。以下是未来爱游戏平台的游戏社区建设可能包含的应用：\r\n1.好友/群组：添加/搜索/邀请（）/删除/ 搜索好友，申请/加入/创建/解散群组；\r\n2.日志/话题/投票：用户可以发起日志/话题/投票；\r\n3.微博：用户可将自己生活中或玩游戏的心得以简短文字、视频、表情等发送出来；\r\n4.相册：玩家可以上传自己的照片到相册中；\r\n5.消息：包括系统消息和好友消息两部分；\r\n6.互动资讯：提供社区互动的资讯展示，包括好友动态、群组动态和热门群组、成员推荐、热门话题/日志展示等；\r\n7.社区活动：包括有奖问答、 幸运猜奖、社区博彩、游戏主题活动等；\r\n8.道具商场：提供社区道具供用户消费，道具可以有易名、隐身、八卦等；\r\n9.网游试玩：玩家可以参与各种联合发布的网游公测时候的抢号活动。抢到号的玩家可以在预先免费试玩某款网游；\r\n10.在线游戏：可以在线玩各种FLASH游戏、网页游戏等；\r\n11.勋章：当用户完成某项任务后就能获得勋章，同时可以将勋章荣誉展现给社区好友；\r\n\r\n\r\n看了这个平台的介绍，有几个问题想了解下的：\r\n1）通过昨天对糖果社区的体验，以及这个产品的介绍，大家觉得这个爱游戏怎样？哪些地方吸引你？\r\n2）对这12个功能点，你最喜欢的是哪些？为什么？\r\n3）关于第7点“社区活动”，你们有兴趣参与吗？为什么？\r\na)如果你玩的游戏积分可以在社区里使用，你觉得如何？\r\n4）关于第3点“微博”，你会使用吗？为什么？\r\n5）如果你看到别人的信息里有介绍他玩过的游戏，你会有冲动去玩玩看吗？什么时候会？\n[[i] 本帖最后由 cannymok 于 2011-01-29  14:56 编辑 [/i]]			','121.201.254.252',0,0,0,0,0,0,0,0,0,0,0,NULL),(356,28,159,0,'SHGT02',73,'',1296271604,'是的，我也有同感','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(357,28,160,0,'SHIMS03',103,'',1296271611,'这些游戏相对来说就是小游戏，互动比较少。个人还喜欢去网站上玩游戏。','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(358,28,165,1,'cannymok',74,'1月29日主题一-ITV游戏收费问题',1296271766,'相信大家都遇到过玩游戏要付费的问题，\r\n（1）以下几种付费渠道，你最喜欢哪两种？为什么？\r\na)IPTV宽带代收\r\nb)网上银行、\r\nc)付宝\r\nd)手机短信进行扣费\r\n（2）你们还喜欢哪些付费渠道？（       ）\r\n（3）如果设虚拟币，以点卡形式销售，大家有兴趣购买吗？为什么？\r\n（4）以下两种收费形式，你最喜欢哪种？为什么？\r\na)包年/包月形式（IPTV游戏平台有免费游戏，但部分精品游戏用户不能免费玩，需要通过包年/包月形式定购）\r\nb)按次收费（玩一次游戏收一次费）\r\n（5）你对这两种收费形式可接受的价格分别是多少？\r\na)包年/包月形式：（     ）元/年；（      ）元/月\r\nb)按次收费：（      ）元/次\r\n（6）用户需要获得更多的经验、积分值或者得到道具更加个性化、更具优势玩IPTV，此时用户可以购买道具，道具是按次收费。你希望每次的收费是怎样的？（     ）元/次\n[[i] 本帖最后由 cannymok 于 2011-01-29  14:59 编辑 [/i]]			','121.201.254.252',0,0,0,0,0,0,0,0,0,0,0,NULL),(359,17,130,0,'匿名',0,'',1296271968,'现在服务也越来越全面了。天翼手机和189邮箱我都在用，蛮不错的。','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(360,17,130,0,'匿名',0,'',1296271993,'现在服务也越来越全面了。天翼手机和189邮箱我都在用，蛮不错的。','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(361,29,166,1,'cannymok',74,'1月29日主题一-自我介绍',1296272003,'大家好，欢迎你们参加我们的论坛！ \r\n我先来自我介绍一下吧。 \r\n大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游，到处走走逛逛，认识不同的朋友。 \r\n\r\n下面，轮到你们来自我介绍一下啦 \r\n譬如说， \r\n姓名，年龄，职业？ \r\n性格？ \r\n爱好？？\n[[i] 本帖最后由 cannymok 于 2011-01-30  09:08 编辑 [/i]]			','121.201.254.252',0,0,0,0,0,0,0,0,0,0,0,NULL),(362,17,130,0,'SHIMS03',103,'',1296272170,'现在服务越来越全面了。天翼手机和189邮箱我都在用，蛮不错的','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(363,8,133,0,'SHGT02',73,'',1296272217,'我也希望３g无线上网费能降下来．上海是个国际化的大都市（什么东东都贵，就是工资不贵）无线应该普及．就象我们的邻国韩国一样．这些以后都是人们的生活必须品啦．','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(364,28,157,0,'000000',85,'也来回复',1296272621,'叫我大卫吧。34岁了，普通工人\r\n我喜欢旅游啊，I玩T数码产品\r\n对通信类的产品也比较感兴趣啊','58.248.7.163',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(365,29,166,0,'000000',85,'',1296272790,'叫我大卫吧。34岁了，普通工人 \r\n我喜欢旅游啊，I玩T数码产品 \r\n对通信类的产品也比较感兴趣啊','58.248.7.163',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(366,28,160,0,'000000',85,'',1296272877,'在电视上玩，还是多放一些适合家庭成员共同玩的游戏比较好','58.248.7.163',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(367,17,130,0,'CDIMS03',100,'如此方便',1296272995,'现在电信搞推出的心活动越来越多了\r\n我到觉得 多实惠的用起来','222.212.213.55',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(368,10,129,0,'CDIMS03',100,'',1296273078,'就是   越来越方便了','222.212.213.55',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(369,12,131,0,'CDIMS03',100,'',1296273142,'我觉得前台咨询区 是最重要了','222.212.213.55',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(370,8,133,0,'CDIMS03',100,'',1296273616,'速度不错了，比之前好很多了， 也没有想过会换过 现在用起了 也确实方便','222.212.213.55',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(371,27,155,0,'yanyang1230',67,'反馈',1296274049,'你好上海督导反应，GS第一第二个帖子无法回复，谢谢请尽快解决！','222.67.59.196',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(374,27,156,0,'NJGS03',105,'',1296277004,'姓名  欧陈（不晓得怎么带我打成了屈成）\r\n年龄  22岁~~虽然我想永远18岁啊\r\n职业  马上大学毕业了~~\r\n性格  超级无敌狂非常活泼开朗\r\n爱好  上上网，打打游戏啊','122.193.13.18',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(375,27,155,0,'NJGS03',105,'',1296277290,'1）你平时有使用哪些游戏社区？觉得它们的整体界面怎样？哪里喜欢？哪里不喜欢？ \r\n人人网  因为每天必上   所以顺便玩玩游戏   方便啦\r\n2）你们最喜欢社区里的哪几个功能？有没有不满意的地方，是哪些？为什么？ \r\n斗地主不错~~就是里面人素质太差了~~很多人注册小号  打完骂   真无聊','122.193.13.18',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(376,27,155,0,'gzl503',71,'',1296280152,'我不清楚开心网能不能算作游戏社区，毕竟上面很多应用还不只是游戏','183.46.11.229',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(377,28,159,0,'gzl503',71,'',1296280362,'IPTV的游戏，觉得目前最不满意的还是在操作和内容上。操作是用遥控器操作的，明显很不“专业”。另外内容上，偏向小游戏为多，希望以后机顶盒的功能可以改进，变成类似XBOX就好了。呵呵','183.46.11.229',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(378,29,166,0,'gzl503',71,'',1296280466,'大家好，我是小壁虎，28岁，属于IT打工人士。可能和行业有关吧，个人非常喜欢电子、通信类产品。当然视频通信体验也是不会少的了，呵呵！','183.46.11.229',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(379,10,129,0,'SHIMS02',107,'',1296280527,'来报道了。\r\n在线交流区的会议好象不让进嘛','180.171.176.167',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(380,28,157,0,'NJGT01',106,'',1296280654,'大家好，我是王玉民，在南京市城管局工作，今年34岁了。我比较喜欢数码类产品，运动，旅游和网游。我的性格外表大大咧咧，其实，内心还是很细腻的;P ，我喜欢足球，毕竟曾经训练过十几年，也通过足球认识了很多的朋友。平时也喜欢摄影，虽然技术还很不入门，还在学习中，但从中获得乐趣，我觉得是最大的收获！在新的一年里，也希望认识或不认识的朋友，新春快乐，心想事成！','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(381,17,130,0,'SHIMS02',107,'',1296280671,'我家用的好象也是我的e家。爱音乐平台那个是什么？','180.171.176.167',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(382,28,160,0,'NJGT01',106,'',1296280946,'这个已经了解了，我觉得IPTV更应该跟上时代的步伐哦，可是游戏还是较为幼稚。\r\n包括电视机目前的分辨率，画面的精致程度，糊糊的影像会让人没有玩的欲望。而且，希望难度不要太大，比较是用遥控器来操纵的。','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(383,28,159,0,'NJGT01',106,'',1296281805,'1,很方便找到.在首业上就有.很容易找到. \r\n2.不太满意,有点简单和幼稚.\r\n3.个人觉得棋牌类和射击类比较喜欢.\r\n4.IPTV天天都看,玩游戏的时间,比较少.\r\n5.我目前对使用遥控器玩IPTV游戏是非常不习惯，我觉得遥控器功能过于简单,如果增加相应手柄玩IPTV游戏，我会很有兴趣的.\r\n6.我对IPTV社区，还是比较陌生一点,我觉得一个好的社区建设时很重要的!\r\n7.感觉一般,我建议可以延长体验时间，增加内容！','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(384,28,159,0,'gzl503',71,'',1296282099,'IPTV游戏之前一直是不知道的，当然最近是了解了，感觉对这个产品的认识非常少，也没有太多的期待','183.46.11.229',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(385,28,169,1,'cannymok',74,'1.29主题二—客服咨询、投拆及流程的满意度及改进建议',1296283339,'[align=left][font=宋体][size=12pt]刚刚了解了大家对IPTV游戏收费的看法及建议后，现在需求了解大家对IPTV客服的满意度及建议，因为一旦用户对收费产生疑问，可以第一时间通过客服了解和确认情况。希望大家可以主动的通过10000号进行一次IPTV游戏的客服咨询，问题可以自己依情况询问，问什么都可以，然后请大家回答以下几个问题：[/size][/font][/align]\r\n[align=left][font=宋体][size=12pt]1)[/size][/font][font=宋体][size=12pt]目前咨询是否方便快捷，对客服人员态度是否满意；[/size][/font][/align]\r\n[align=left][font=宋体][size=12pt]2)[/size][/font][font=宋体][size=12pt]客服对投拆的反应是否迅速，解决投拆的效果如何，是否满意；[/size][/font][/align]\r\n[align=left][font=宋体][size=12pt]3)[/size][/font][font=宋体][size=12pt]对目前咨询、投拆流程是否感到满意，流程哪些地方需要改进；[/size][/font][/align]\r\n[align=left][font=宋体][size=12pt]4)[/size][/font][font=宋体][size=12pt]是否觉得要区分普通客服和VIP客服。[/size][/font][/align]\n[[i] 本帖最后由 cannymok 于 2011-01-29  21:25 编辑 [/i]]			','183.46.11.229',0,0,0,0,0,0,0,0,0,0,0,NULL),(386,28,170,1,'cannymok',74,'1.29主题三—大家对IPTV游戏的期望与建议',1296283572,'[size=3]hello！今天想和大家探讨的最后一个话题是，大家对IPTV游戏的期望与建议，[font=宋体][size=12pt]经过这两天的了解，大家期望IPTV游戏还能做到什么？可以通过什么改进来达到大家的期望，包括功能、支付、客服等等，大家可以提出自己的建议，畅所欲言。[/size][/font][/size]\n[[i] 本帖最后由 cannymok 于 2011-01-29  21:25 编辑 [/i]]			','183.46.11.229',0,0,0,0,0,0,0,0,0,0,0,NULL),(387,27,155,0,'cannymok',74,'',1296283709,'[quote] 原帖由 [i]SHGS03[/i] 于 2011-01-28 20:10 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=315&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url][quote] 原帖由 [i]cannymok[/i] 于 2011-01-28 18:16 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=311&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url][quote] 原帖由 [i]SHGS03[/i] 于 2011-01-28 15:25 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=289&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]我平时上的游戏社区还蛮多的，比如会在17173看看最近的游戏介绍或公告、也会去开心、人人上面玩玩偷菜，抢车位之类的小游戏，还有糖果社区的三国杀，泡泡鱼之类的游戏。这些游戏的画面都比较精致，像糖果社区就是比较女孩子点，画面都是可爱甜美的风格，但是很多游戏就比较简单了一点，不是很耐玩~ [/quote] 那你喜欢怎样的游戏画面啊？ [/quote] 我就是觉得它里面的游戏单一了点，没有很大的挑战性，时间长了会不耐玩噢，但是我个人还是很喜欢他的画面都，比如泡泡鱼里面眼睛会转悠的小鱼，梦幻小镇中超可爱的人物等等。~ [/quote]\r\n\r\n像糖果社区里，还有一些和大型网友结合的，比如龙之谷，你觉得如何呢？你希望网友小游戏和大型网游都能在社区里玩吗？','183.46.11.229',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(388,29,171,1,'cannymok',74,'1.29主题二/三—聊聊目前使用的通讯工具，以及对视频通话的感受',1296285283,'[size=3][font=宋体][size=10.5pt][font=Helvetica]请[/font]大家要记得踊跃发言哦！每天我们会评出发帖最多的前三名灌水英雄榜，有现金奖励的哦～～～但那些纯粹灌水的帖子，例如：沙发，路过啊，我们是不算进去的。\r\n所以，大家多点发有质量、有内容的内涵帖哦！！！！！！ \r\n[/size][/font]\r\n[font=宋体][size=10.5pt]下面我们来了解下大家通信工具的使用情况[/size][/font]\r\n[/size][align=left][font=宋体][size=12pt]1[/size][/font][font=宋体][size=12pt]）大家目前主要使用哪些通讯工具？（固定电话、手机、网上聊天工具）哪个通讯工具使用最多？其次呢？[/size][/font][/align]\r\n[align=left][font=宋体][size=12pt]2）[/size][/font][font=宋体][size=12pt]您一般使用固定电话主要是和谁联系？[/size][/font][/align]\r\n[align=left][font=宋体][size=12pt]3）[/size][/font][font=宋体][size=12pt]手机呢？[/size][/font][/align]\r\n[align=left][font=宋体][size=12pt]4）[/size][/font][font=宋体][size=12pt]网上聊天工具呢？[/size][/font][/align]\r\n[font=宋体][size=10.5pt][/size][/font]\r\n[size=3][font=宋体][size=10.5pt]下面想向大家了解一些视频通话的一些使用情况[/size][/font][font=宋体][size=10.5pt]（[/size][/font][u][font=宋体]2[/font][/u][u][font=宋体]个问题都需要回答哦，大家记得踊跃回答，每个回答最好标明对应的题号[/font][/u][font=宋体][size=10.5pt]） [/size][/font][font=宋体][size=10.5pt][/size][/font]\r\n[/size][align=left][size=3][font=宋体][size=10.5pt]1)[/size][/font][font=宋体][size=10.5pt]大家听说过视频通话吗？是如何知道的？[/size][/font][/size][/align][size=3][/size]\r\n[size=3][/size][align=left][size=3][font=宋体][size=10.5pt]2)[/size][/font][font=宋体][size=10.5pt]大家使用过视频通话吗？[/size][/font][/size][/align][size=3][/size]\r\n[size=3][/size][align=left][size=3][font=宋体][size=10.5pt]a)[/size][/font][font=宋体][size=10.5pt]如果有：[/size][/font][/size][font=宋体][size=10.5pt]\r\n[size=3]i.[/size][/size][/font][font=宋体][size=3][size=10.5pt]使用过哪种视频通话工具？用来跟哪些人联系？在什么情况下使用？使用频率如何？[/size][/size][/font][/align][size=3][/size]\r\n[size=3][/size][align=left][font=宋体][size=10.5pt][size=3]ii.[/size][/size][/font][font=宋体][size=3][size=10.5pt]大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工[/size][/size][/font][font=宋体][size=3][size=10.5pt]具还需要哪些改进？[/size][/size][/font][/align][size=3][/size]\r\n[size=3][/size][align=left]\r\n[size=3][font=宋体][size=10.5pt]b)[/size][/font][font=宋体][size=10.5pt]如果没有，为什么没有使用？[/size][/font][/size][/align][size=3][/size]\n[[i] 本帖最后由 cannymok 于 2011-01-30  09:09 编辑 [/i]]			','183.46.11.229',0,0,0,0,0,0,0,0,0,0,0,NULL),(390,10,129,0,'GZGS03',83,'',1296286102,'期待有更多新優惠新活動','116.28.66.33',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(391,27,155,0,'GZGS03',83,'',1296286212,'開心網很好啊，可以轉別人的帖','116.28.66.33',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(392,27,155,0,'GZGS03',83,'',1296286254,'可以知道很多諮詢，和平時看不到的新聞。','116.28.66.33',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(389,17,130,0,'GZGS03',83,'',1296285351,'中國電信推出的優惠活動越來越多了。讓不少人受惠。贊一個','116.28.66.33',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(393,27,155,0,'NJGS01',79,'',1296287639,'平时一般都上开心网 人人网 西祠论坛 腾讯游戏论坛啊 页面都不错的\r\n进去满方便的','114.221.63.196',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(394,28,157,0,'NJGT02',108,'',1296287678,'大家好  我是张鹏  我32岁   是中石化员工   平时喜欢游戏 喜欢郊游  喜欢自己开车出去玩  喜欢交朋友  有机会一同出去旅游','112.80.203.153',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(395,27,156,0,'NJGS01',79,'',1296287707,'姓名刘健 年龄 22  职业：数控\r\n性格 男\r\n爱好 体育 美食 书法','114.221.63.196',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(396,29,171,0,'GZIMS03',104,'对IPTV游戏的期望与建议',1296287737,'1）目前主要使用的通讯工具：固定电话、手机、网上聊天工具，使用最多的是手机，其次是网上聊天工具。\r\n2）一般使用固定电话主要是查询热线，如800开头那些，因为用固话是免费的。\r\n3）手机为主要通讯工具，因为都储存好所有电话号码，直接选择拨打就开了。\r\n4）与不在本地的朋友联系，工作联系，资料传递等\r\n\r\n1)有听说过视频通话，通过广告，网络知道。\r\n2)没有使用过视频通话。\r\nb)如果没有，为什么没有使用（因为目前视频电话比没有大范围覆盖，有运营商限制，所有未广泛使用！）','58.248.7.141',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(397,28,160,0,'NJGT02',108,'',1296287993,'我玩过的   不过认为这个比较简单的游戏  我喜欢益智类在这里玩   感觉画面还是比较粗糙的   家里的液晶电视比较大 认为现阶段不要老想收费问题  应该尽量免费让大家玩  要想收费应该在功能上和道具上收费','112.80.203.153',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(398,27,163,0,'NJGS01',79,'',1296288089,'玩过的啊 很不错的哦 比其他社区游戏多哦','114.221.63.196',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(399,28,165,0,'NJGT02',108,'',1296288360,'相信大家都遇到过玩游戏要付费的问题， \r\n（1）以下几种付费渠道，你最喜欢哪两种？为什么？ \r\na)IPTV宽带代收 \r\nb)网上银行、 \r\nc)付宝 \r\nd)手机短信进行扣费\r\n我认为iptv代收比较好   \r\n（2）你们还喜欢哪些付费渠道？（       ） \r\n我喜欢由手机费里代交\r\n（3）如果设虚拟币，以点卡形式销售，大家有兴趣购买吗？为什么？\r\n 如果游戏比较好玩的话  我是会买的\r\n（4）以下两种收费形式，你最喜欢哪种？为什么？ \r\na)包年/包月形式（IPTV游戏平台有免费游戏，但部分精品游戏用户不能免费玩，需要通过包年/包月形式定购） \r\nb)按次收费（玩一次游戏收一次费） \r\n包月的形式 比较好     包年 时间太长的  \r\n（5）你对这两种收费形式可接受的价格分别是多少？ \r\na)包年/包月形式：（    500 ）元/年；（    60  ）元/月 \r\nb)按次收费：（      2）元/次 \r\n（6）用户需要获得更多的经验、积分值或者得到道具更加个性化、更具优势玩IPTV，此时用户可以购买道具，道具是按次收费。你希望每次的收费是怎样的？（   5  ）元/次','112.80.203.153',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(400,10,129,0,'SHGT03',101,'',1296288695,'新的一天，新的开始，顶一个','58.37.98.108',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(401,28,159,0,'NJGT02',108,'',1296288884,'很容易找到的\r\n不是很满意的  感觉比较单调  内容不是很紧凑  精彩\r\n我喜欢iptv益智类游戏  希望加教育益智类的   \r\niptv每天都用  每天都有看电视回放和电视剧    游戏是每周3-4次的\r\n不是很习惯   但是能接受  喜欢由手柄   但不希望花钱另外购买  套餐内送比较好\r\n社区还好了  我注册了  但是打字不方便   交流不是很好   能有更多交流活动\r\n体验时间太少   体验是提示不多','112.80.203.153',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(402,17,130,0,'SHGT03',101,'',1296288964,'[quote] 原帖由 [i]CDIMS03[/i] 于 2011-01-29 11:49 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=367&ptid=130][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]现在电信搞推出的心活动越来越多了我到觉得 多实惠的用起来 [/quote]越来越喜欢电信了感觉很人性化，也很放心','58.37.98.108',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(403,28,169,0,'NJGT02',108,'',1296289102,'咨询是比较方便的  态度还挺好  \r\n投诉反应比较快  效果一般   具体内容 专业度不是很强\r\n流程比较满意  希望提高人员专业技能\r\n一定要区分的vip的','112.80.203.153',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(404,28,170,0,'NJGT02',108,'',1296289268,'支付手段多一点  游戏内容更丰富  画面更加精细   能提供附件  比如游戏手柄  打字键盘等\r\n体验时间更多   免费内容更多','112.80.203.153',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(405,28,165,0,'SHGT03',101,'',1296289319,'1、IPTV宽带代收 \r\n2、充值卡\r\n3、有兴趣啊，因为会比较真实\r\n4、喜欢包年/包月形式，因为随时随地都可以玩，比价经济实惠，不用玩一次付一次，较为麻烦\r\n5、包年/包月形式：（    500 ）元/年；（    60  ）元/月  差不多还比较实惠\r\n6、2元一次','58.37.98.108',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(406,28,157,0,'SHGT03',101,'',1296289444,'大家好，可以称呼我为，YUKI\r\n很高兴在这里可以和大家交朋友\r\n我今年23岁，是日企的一名小员工\r\n比较喜欢玩玩游戏，比如斗地主，飞行棋，大家来找茬，连连看\r\n还喜欢上上开心网','58.37.98.108',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(407,28,170,0,'SHGT03',101,'',1296289514,'希望游戏的种类更多一些比如增加一点角色类的，\r\n当然收费便宜也很重要哦\r\n画面颜色都很喜欢，会继续支持','58.37.98.108',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(408,28,169,0,'SHGT03',101,'',1296289604,'1、态度很好，非常有耐心\r\n2、专业的回答也很迅速\r\n3、流程也很人性化\r\n4、支持分开普通区和VIP区','58.37.98.108',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(409,28,159,0,'SHGT03',101,'',1296289649,'[quote] 原帖由 [i]SHGT02[/i] 于 2011-01-28 21:50 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=329&ptid=159][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]就我一个人回答问题啊,没人啊.太孤独了啊 [/quote]不孤独爱玩游戏的YUKI在呢。。。哈哈','58.37.98.108',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(410,28,169,0,'SHGT02',73,'',1296292158,'１．态度是不错的，太有耐心啦．我问完了还老是问我还有什么需要吗．弄的我都不好意思挂电话啦．\r\n２．还算可以的．可语言太官方啦．有点慢．（有可能我是急性子:) ）\r\n３．不错啦．可以给我满意的答复\r\n４．需要区别对代．所谓一分价格一分货','58.218.204.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(411,28,170,0,'SHGT02',73,'',1296292689,'我想还是要针对iptv的特性应该增加些体感游戏．这些游戏可以老少皆益．又有对抗又能锻炼身体．有些游戏可以先给普通用户用个几级，到了８级后就需要负款．这样及能吸引新玩家体验也能循序渐进的把客户套老．哈哈．还有不知能不能把游戏画面提升一下．都２０１１年啦．都１０８０p拉．可现在的画面还是上世纪的画面，太不爽啦','58.218.204.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(412,28,170,0,'SHGT02',73,'',1296292827,'还有要是用手摈就一定要融合电视的基本操作．这样家里才不会显的到处是遥控器．我认为这个也很重要','58.218.204.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(413,29,171,0,'SHIMS02',107,'',1296292854,'1）日前主要使用的是手机网上聊天工具，最多的是网上聊天工具，手机其次。\r\n\r\n2）固定电话主要是和家人联系长途什么的，或是费时比较长的电话，如定票啊什么的。\r\n\r\n3）手机比较常用，因为方便，随时随地可以通话。\r\n\r\n4）网上聊天工具是最常用的，平常上班时工作中和同事都是以这个沟通。还有和外地的朋友同学联系也是用网上聊天工具比较多。\r\n \r\n\r\n1)大家听说过视频通话吗？是如何知道的？\r\n听说过视频通话，朋友告诉或是网络上知道的。\r\n\r\n2)大家使用过视频通话吗？\r\n经常用视频通话。\r\n\r\na)如果有： \r\ni.使用过哪种视频通话工具？用来跟哪些人联系？在什么情况下使用？使用频率如何？\r\n使用过QQ、skype、msn等视频通话工具，一般是和外地的家人和朋友联系，晚上下班以后，一周三四次的样子，节假日也用得多。\r\n\r\n\r\n\r\nii.大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工具还需要哪些改进？\r\n还行吧，基本能满足。就是画片有时清楚有时不太清楚，而且受网速的限制，有时候会卡，而且苹果系统的电脑还只能用skype视频，登录时间也蛮长的。\r\n\r\n\r\n\r\n\r\nb)如果没有，为什么没有使用？','116.237.203.227',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(414,28,160,0,'SHIMS02',107,'',1296293452,'呵呵，还不知道呢。IPTV电视还是挺好的，游戏说实话，没太多时间去玩它，不过，小孩长大一点以后和她一起玩倒也蛮好的。比在电脑跟前玩好。','116.237.203.227',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(415,10,129,0,'GZIMS01',80,'不错的',1296293473,'这个不错啊,可以听一下大家的意见','113.96.179.89',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(416,17,130,0,'GZIMS01',80,'产品',1296293591,'现在中国电信做的产品越来越多,质量做得不错,希望以后会更加好','113.96.179.89',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(417,28,165,0,'SHGT02',73,'',1296293655,'１．c)付宝 这个现在比较流行，也方便．也不太会被骗．\r\n　　d)手机短信进行扣费 　这个么还要简单方便，每人都有手机，一发短信就能扣费．\r\n２．以下两种都可以，付费方式越多越好a)IPTV宽带代收 \r\n　　b)网上银行\r\n３．我不太喜欢，太繁了．现在有怎么多好的付费方式何必用这种．\r\n４．a包的方式最好．这样一次付完不用担心是否超资．\r\n５．３００－５００一年　５０块一月　２元一次\r\n６．５元','58.218.204.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(418,12,131,0,'GZIMS01',80,'展示',1296293736,'有产品展示给人看这个是最好的,可以让客人选择到自己喜欢的品种','113.96.179.89',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(419,8,133,0,'GZIMS01',80,'',1296293857,'这个产品比较好,信号好,功能比较多,很多人现在都用天翼手机了','113.96.179.89',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(420,28,160,0,'GZIMS01',80,'',1296294032,'这个都挺好啊,好多游戏玩,操作上方便,','113.96.179.89',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(421,29,171,0,'GZIMS01',80,'',1296294556,'1.我一般用天翼手机,固话跟朋友联系的\r\n2.用是最多的手机,因比较方便的,\r\n3.网上的话我会用QQ跟朋友聊天,因方便快捷,没有限制\r\n\r\n知道视频通话,平时我都会用的,一般我会用QQ多一点,MSN少一点,一个星期大约有2次左右\r\n经常跟朋友,亲戚或同事等聊天\r\n希望以后视频通话的网速会更加快','113.96.179.89',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(422,29,166,0,'GZIMS01',80,'',1296294855,'大家好,我是阿雄,我的爱好有很多:上网,旅游,钓鱼,健身等,喜欢一些新的电子产品','113.96.179.89',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(423,27,163,0,'SHGS02',76,'',1296298226,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-29 11:13 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=352&ptid=163][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]1）大家这两天都有尝试玩过盛大的糖果社区。你们感觉怎样？ 2）对糖果社区这样的游戏社区理念，和你平时常玩的游戏平台相比，觉得有什么不同的感受? [/quote]\r\n\r\n\r\n\r\n   总体来说感觉不错吧 画面做的很细腻 游戏也很多 就是缺少点游戏性 不是很耐玩 和有点女性化（太可爱类型的） \r\n   在线客服也是很不错的 查询问题的速度也很快 不用等多久就能回答我所有的问题 除了游戏性和风格方面 大致我都很喜欢的 希望有所改善 那就更完美了 :lol','222.67.224.216',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(424,27,164,0,'wangbin',78,'',1296298401,'挺好的，我觉得这个社区的感觉挺不错的','113.114.204.191',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(425,27,164,0,'SHGS02',76,'',1296299456,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-29 11:18 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=355&ptid=164][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]昨天咱们已经讨论过平时会玩的游戏平台，今天，我给大家隆重推荐一个新的游戏平台（此处应有掌声~~~~~~~~~~~） 爱游戏游戏社区！！！！！！！！ 它是中国电信爱游戏平台（game.189.cn）下的一个网络社区，主要是整合爱游戏平台下的各款游戏资源的社区平台。由于目前该平台尚未开通。以下是未来爱游戏平台的游戏社区建设可能包含的应用： 1.好友/群组：添加/搜索/邀请（）/删除/ 搜索好友，申请/加入/创建/解散群组； 2.日志/话题/投票：用户可以发起日志/话题/投票； 3.微博：用户可将自己生活中或玩游戏的心得以简短文字、视频、表情等发送出来； 4.相册：玩家可以上传自己的照片到相册中； 5.消息：包括系统消息和好友消息两部分； 6.互动资讯：提供社区互动的资讯展示，包括好友动态、群组动态和热门群组、成员推荐、热门话题/日志展示等； 7.社区活动：包括有奖问答、 幸运猜奖、社区博彩、游戏主题活动等； 8.道具商场：提供社区道具供用户消费，道具可以有易名、隐身、八卦等； 9.网游试玩：玩家可以参与各种联合发布的网游公测时候的抢号活动。抢到号的玩家可以在预先免费试玩某款网游； 10.在线游戏：可以在线玩各种FLASH游戏、网页游戏等； 11.勋章：当用户完成某项任务后就能获得勋章，同时可以将勋章荣誉展现给社区好友； 看了这个平台的介绍，有几个问题想了解下的： 1）通过昨天对糖果社区的体验，以及这个产品的介绍，大家觉得这个爱游戏怎样？哪些地方吸引你？ 2）对这12个功能点，你最喜欢的是哪些？为什么？ 3）关于第7点“社区活动”，你们有兴趣参与吗？为什么？ a)如果你玩的游戏积分可以在社区里使用，你觉得如何？ 4）关于第3点“微博”，你会使用吗？为什么？ 5）如果你看到别人的信息里有介绍他玩过的游戏，你会有冲动去玩玩看吗？什么时候会？ [/quote]\r\n\r\n\r\n \r\n1）通过昨天对糖果社区的体验，以及这个产品的介绍，大家觉得这个爱游戏怎样？哪些地方吸引你？ \r\n 应该说上述12条和开心玩差不多 应该是不错的 我会去看看的\r\n\r\n2）对这12个功能点，你最喜欢的是哪些？为什么？ \r\n最喜欢的应该是1 3 7 9这几项吧 本人喜欢交朋友还有能组群这样就更加方便朋友之间的交流了 还有有奖问答、 幸运猜奖 呵呵 大家应该都会喜欢的 有交流还有抽奖 不错啊\r\n3）关于第7点“社区活动”，你们有兴趣参与吗？为什么？\r\n我有兴趣参加的 抽奖看看自己的运气好不好 还有些游戏主题什么的 很不错\r\n \r\na)如果你玩的游戏积分可以在社区里使用，你觉得如何？ \r\n 我觉得很好啊 这样更加方便了 \r\n\r\n4）关于第3点“微博”，你会使用吗？为什么？\r\n我会使用的  平时我爱和朋友交流游戏和生活方面的事情 微薄不就是这个功能吗 呵呵\r\n5）如果你看到别人的信息里有介绍他玩过的游戏，你会有冲动去玩玩看吗？什么时候会？\r\n会的 我先会去看看游戏的介绍和画面感觉不错 再去亲身体验一下','222.67.224.216',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(315,27,155,0,'SHGS03',82,'',1296216634,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-28 18:16 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=311&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url][quote] 原帖由 [i]SHGS03[/i] 于 2011-01-28 15:25 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=289&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]我平时上的游戏社区还蛮多的，比如会在17173看看最近的游戏介绍或公告、也会去开心、人人上面玩玩偷菜，抢车位之类的小游戏，还有糖果社区的三国杀，泡泡鱼之类的游戏。这些游戏的画面都比较精致，像糖果社区就是比较女孩子点，画面都是可爱甜美的风格，但是很多游戏就比较简单了一点，不是很耐玩~ [/quote] 那你喜欢怎样的游戏画面啊？ [/quote]\r\n我就是觉得它里面的游戏单一了点，没有很大的挑战性，时间长了会不耐玩噢，但是我个人还是很喜欢他的画面都，比如泡泡鱼里面眼睛会转悠的小鱼，梦幻小镇中超可爱的人物等等。~','114.84.39.106',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(314,27,155,0,'SHGS03',82,'',1296216303,'像糖果社区这样的画面我就很喜欢阿~~简单可爱，3d效果的','114.84.39.106',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(313,8,133,0,'GZGS02',93,'',1296211400,'之前用过，不过现在用的是不是，99%都是的了，账单就写着是~~哈哈。AD好用，电信V5，如果费用降下来，而且3G无线费用降下来，就好啦，那就一定用啦，那就全天候无线上网，随时随地，想上就上~:lol','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(312,28,157,0,'SHGT02',73,'',1296210941,'有相同爱好的朋友可以联系我.大家一块玩.','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(304,12,131,0,'SHGT02',73,'',1296207195,'产品展示区也不错.可以让我跟上时代.3d的感觉真的不错.有点象在网游.电信的努力大家应该给予肯定','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(305,28,160,0,'SHGT02',73,'',1296207403,':lol :lol 这个我早知道了.可游戏都是些小游戏(有点幼稚)对我吸引力有限.对我妈到有点吸引力.哈哈','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(306,27,155,0,'cannymok',74,'',1296207447,'再补充2个问题：\r\n3）你平时有在这些社区里支付过一定费用吗？\r\n4）你有使用过这些社区的客服吗？','219.137.58.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(353,10,129,0,'GZGT01',84,'',1296270816,'期待哦~~~~','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(308,28,159,0,'SHGT02',73,'',1296208445,'1,很方便找到.在首业上就有.很容易找到.\r\n2.不太满意,有点小儿科.都是些小游戏.\r\n3.比较起来还是其牌类的还好玩些.但用遥控器操作起来有点不顺手.建议加点如wii那样的体感游戏.这样比较试用与遥控器的操作.教育类应该也不错.以后有了小孩还是很用的着的\r\n4iptv我天天看的啊,它的点播和回看都比较实用.玩iptv的游戏就少啦.没什么它自己的特色.不太适用我这样的游戏\"粉丝\"\r\n5.不太好用,增加手摈是个好主意,但一定要兼容电视遥控器的功能就最好了.不然家里到处都是遥控器.\r\n6.一般.因为没有习惯在电视上社区.还是电脑方便.可以增加相同爱好的提醒.如都爱打牌.\r\n7.体验太少,太短了.希望时间长些.','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(309,28,157,0,'SHGT02',73,'',1296209024,'嗨!大家好.我叫李杰思,33岁(我老了啊,:Q 可我的心态只有18岁哦:lol )我是做绿地管理的.属于事业单位.我平时喜欢上上网,玩玩魔兽,怪物猎人边境.我还是游戏机的忠实\"游粉\"什么wii,x360.ps3我都玩.我还加入了车游会.放假会和会里的朋友一起出去自驾游.我还喜欢健身,打牌................(好象我好堕落啊:o )','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(310,17,130,0,'SHGT02',73,'',1296209218,'内容月多月好啊.这也是对我们用户的负责啊.谢谢电信','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(311,27,155,0,'cannymok',74,'',1296209790,'[quote] 原帖由 [i]SHGS03[/i] 于 2011-01-28 15:25 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=289&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]我平时上的游戏社区还蛮多的，比如会在17173看看最近的游戏介绍或公告、也会去开心、人人上面玩玩偷菜，抢车位之类的小游戏，还有糖果社区的三国杀，泡泡鱼之类的游戏。\r\n这些游戏的画面都比较精致，像糖果社区就是比较女孩子点，画面都是可爱甜美的风格，但是很多游戏就比较简单了一点，不是很耐玩~ [/quote]\r\n\r\n那你喜欢怎样的游戏画面啊？','219.137.58.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(223,17,130,1,'admin',1,'产品体验区隆重上线',1291789639,'产品体验区包括：\r\n\r\n1、电信天翼手机\r\n     提供最新的电信天翼手机咨询体验产品信息。\r\n\r\n2、我的e家\r\n     提供我的e家最新套餐及产品介绍信息。\r\n\r\n3、商务领航\r\n     提供企业商务推荐信息及相关产品介绍。\r\n\r\n4、爱音乐及189邮箱\r\n     爱音乐平台信息及189邮箱介绍。','183.33.205.98',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(224,12,131,1,'gzhexm',40,'电信营业厅场景体验调查',1291860111,'电信营业厅场景主要分为前台咨询区，产品展示区，VIP区，产品体验区四大区域，请选出你喜欢的区域。','183.33.207.137',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(226,8,133,1,'admin',1,'电信营业厅产品体验',1291860503,'第一体验俱乐部电信营业厅产品体验区分为电信天翼手机、我的e家、商务领航及189邮箱/爱音乐四大区域，请选出你喜欢的产品体验区域。','183.33.207.137',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(284,12,153,0,'000000',85,'',1296192588,'dfasdfadsfadsfdas','59.42.126.44',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(285,12,154,1,'00000',75,'test2',1296192737,'gfdsgfdsgfsdgfsdgfdsg','59.42.126.44',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(286,27,155,1,'cannymok',74,'1月28日主题二-游戏社区使用体验',1296193161,'大家要记得踊跃发言哦！每天我们会评出发帖最多的前三名灌水英雄榜，有现金奖励的哦～～～ \r\n但那些纯粹灌水的帖子，例如：沙发，路过啊，我们是不算进去的。 \r\n所以，大家多点发有质量、有内容的内涵帖哦！！！！！！ \r\n\r\n下面我们来聊聊平时会玩的游戏社区（[color=darkred]2个问题都需要回答哦，我还会不定时添加些问题上来的，大家记得踊跃回答[/color]） \r\n1）你平时有使用哪些游戏社区？觉得它们的整体界面怎样？哪里喜欢？哪里不喜欢？ \r\n2）你们最喜欢社区里的哪几个功能？有没有不满意的地方，是哪些？为什么？\n[[i] 本帖最后由 cannymok 于 2011-01-29  14:49 编辑 [/i]]			','59.42.126.44',0,0,0,0,0,0,0,0,0,0,0,NULL),(281,12,131,0,'NJIMS02',81,'',1296189338,'去过南京的电信营业厅，那边的产品展示区做的很不错。蛮喜欢的，支持','180.111.106.160',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(282,8,133,0,'NJIMS02',81,'',1296189473,'我现在的两个房子里都装的是我e家的e9套餐，用的很好，稳定，快速。。。。','180.111.106.160',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(236,10,129,0,'admin',1,'',1291892869,'很好啊','0.0.0.0',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(237,10,129,0,'admin',1,'',1291892892,'3D的耶','0.0.0.0',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(283,12,153,1,'00000',75,'text tem',1296192502,'bbbbbbbbbbbbbbbbbbbbbdbd','59.42.126.44',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(239,10,129,0,'xlqb_1314',43,'',1296005311,'呵呵。头一次在互联网上见到3的的网页','183.33.207.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(289,27,155,0,'SHGS03',82,'',1296199557,'我平时上的游戏社区还蛮多的，比如会在17173看看最近的游戏介绍或公告、也会去开心、人人上面玩玩偷菜，抢车位之类的小游戏，还有糖果社区的三国杀，泡泡鱼之类的游戏。\r\n这些游戏的画面都比较精致，像糖果社区就是比较女孩子点，画面都是可爱甜美的风格，但是很多游戏就比较简单了一点，不是很耐玩~','114.84.40.201',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(247,12,131,0,'匿名',0,'',1296045410,'dfasf fdasdasfdasfasfsad','58.248.7.158',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(251,10,129,0,'wingotech',70,'',1296098686,'给力，顶一下','183.33.204.55',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(252,10,129,0,'SHGT02',73,'',1296107354,'不错,希望大家把这里顶起来.','122.225.61.175',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(254,17,130,0,'SHGT02',73,'',1296107597,'产品越来越丰富了呀.请继续','122.225.61.175',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(287,27,156,1,'cannymok',74,'1月28日主题一-自我介绍',1296193246,'大家好，欢迎你们参加我们的论坛！ \r\n  今天由于网络关系（可能靠近春节上网的人多吧），帖子一直发不上来。幸亏咱们工作人员及时抢修了，在这里先给他们敬个礼O(∩_∩)O哈哈~ \r\n\r\n  我先来自我介绍一下吧。 \r\n大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游，到处走走逛逛，认识不同的朋友。 \r\n\r\n下面，轮到你们来自我介绍一下啦 \r\n譬如说， \r\n姓名，年龄，职业？ \r\n性格？ \r\n爱好？？','59.42.126.44',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(257,12,131,0,'SHGT02',73,'',1296107892,'没有亲自体验.说的再好.都无法领会.一位伟人说的好.实践是检验真理的唯一方法:lol','122.225.61.175',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(288,27,156,0,'SHGS03',82,'',1296199171,'[color=Plum]哈哈~貌似我第一个到嘛\r\n先自我介绍下拉，我叫杨璐，20岁，还是名在校学生，性格比较外向，开朗\r\n平时喜欢和朋友一起逛街，看电影，k歌~\r\n但是像这样的大冬天就比较喜欢宅在家里，一个人上上论坛，听听歌，感受一下慢节奏的的生活[/color]','114.84.40.201',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(280,17,130,0,'NJIMS02',81,'',1296189255,'1、电信天翼手机 \r\n     我现在就有一个电信的天翼手机，用的还不错，支持。\r\n\r\n2、我的e家 \r\n     现在我的两个房子里都装的我的e家，e9套餐，用的不错，信号稳定，比较适合我这种天天泡网上的人。\r\n\r\n3、商务领航 \r\n     我是开公司的，商务领航能给我提供我需要的信息和服务。 \r\n\r\n4、189邮箱 \r\n     申请了一个189邮箱，蛮不错的，呵呵，支持。','180.111.106.160',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(261,8,133,0,'SHGT02',73,'',1296108235,'我已经办理了好几年拉.算是老用户啦.(也给了你们不少GDP)啦:loveliness:','122.225.61.175',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(278,10,129,0,'NJIMS02',81,'',1296188542,'有了这个体验俱乐部，我们可以更好的了解电信的服务和产品，不错不错，支持','180.111.106.160',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(264,8,146,1,'cannymok',74,'GS论坛－1月27日主题一－自我介绍',1296112223,'大家好，欢迎你们参加我们的论坛！\r\n我先来自我介绍一下吧。\r\n大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游、认识不同的朋友什么的。\r\n\r\n下面，轮到你们来自我介绍一下啦 \r\n譬如说，姓名，年龄，职业？ 性格？爱好？？\n[[i] 本帖最后由 cannymok 于 2011-01-27  15:11 编辑 [/i]]			','219.137.58.20',0,0,0,0,0,0,0,0,0,0,0,NULL),(265,8,147,1,'cannymok',74,'GT论坛－1月27日主题一－自我介绍',1296112817,'大家好，欢迎你们参加我们的论坛！ \r\n我先来自我介绍一下吧。 \r\n大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游、认识不同的朋友什么的。 \r\n\r\n下面，轮到你们来自我介绍一下啦 \r\n譬如说，姓名，年龄，职业？ 性格？爱好？？','219.137.58.20',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(266,8,148,1,'cannymok',74,'GS论坛－1月27日主题二－游戏社区使用体验',1296113814,'大家要记得踊跃发言哦！每天我们会评出发帖最多的前三名灌水英雄榜，有现金奖励的哦～～～\r\n但那些纯粹灌水的帖子，例如：沙发，路过啊，我们是不算进去的。\r\n所以，大家多点发有质量、有内容的内涵帖哦！！！！！！\r\n\r\n下面我们来聊聊咱们平时使用的游戏社区（2个问题都必须回答哦，我还会不断发新的问题上去的哦）\r\n1）你平时有使用哪些游戏社区？觉得它们的整体界面怎样？哪里喜欢？哪里不喜欢？\r\n2）你们最喜欢社区里的哪几个功能？有没有不满意的地方，是哪些？为什么？','219.137.58.20',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(267,8,133,0,'SHGS02',76,'',1296122680,'我也办理了1年左右了 呵呵:lol','180.155.122.164',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(268,17,130,0,'SHGS03',82,'',1296123021,'有产品体验区还是不错的~方便了解自己需要的咨询噢~呵呵:lol','114.84.37.243',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(270,8,133,0,'GZGS01',77,'',1296127163,'我的E家是不是ADSL。如果是ADSL。办了都快8年了。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(271,10,129,0,'GZGS03',83,'',1296131557,'頂一下，有不同體驗和互動區，給力','218.14.17.164',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(272,17,130,0,'GZGS03',83,'',1296131650,'現在的體驗區越來越多了。可以有多選擇，沒有以前的那麼單一了','218.14.17.164',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(274,12,131,0,'GZGS03',83,'',1296131838,'展示區通常都是比較引人注意的','218.14.17.164',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(290,27,155,0,'SHGS03',82,'',1296199700,'我喜欢人人网里面的分享，可以分享到很多有趣有用的信息，保留着以后还能慢慢看~但是有时候分享的东西一多就找不到自己想要的东西了，或者是过了很久再去看以前分享的东西，已经被删掉了~就很可惜呢！~','114.84.40.201',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(291,27,156,0,'SHGS02',76,'',1296199840,'[color=red] 大家好 我叫李天翔也可以叫我Terry  今年27岁  我是在一家日企工作 是金融投资方面的 所以有的时候日本中国两地跑跑 性格比较外向把爱交朋友 爱好么旅游 上网 泡吧 唱歌 自驾游什么的:lol  [/color]','180.155.122.164',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(302,28,159,1,'cannymok',74,'1月28日主题二－大家对IPTV游戏产品功能的认知情况',1296205679,'[font=宋体][size=10.5pt]大家要记得踊跃发言哦！每天我们会评出发帖最多的前三名灌水英雄榜，有现金奖励的哦～～～[/size][/font]\r\n[font=宋体][size=10.5pt]但那些纯粹灌水的帖子，例如：沙发，路过啊，我们是不算进去的。[/size][/font]\r\n[font=宋体][size=10.5pt]所以，大家多点发有质量、有内容的内涵帖哦！！！！！！[/size][/font]\r\n\r\n\r\n\r\n下面我们来聊聊你们玩过的IPTV游戏的感受（[color=darkred][u][b]7个问题都需要回答哦，大家记得踊跃回答，每个回答最好标明对应的题号[/b][/u][/color]）\r\n1)IPTV平台中是否可以方便找到IPTV游戏频道，如果不方便，你觉得如何进入IPTV游戏频道是方便的；\r\n2)对目前IPTV游戏内容是否感到满意，是否觉得内容过于单调、简单；\r\n3)你最钟情于哪类IPTV游戏，棋牌、益智、教育类等；如果要增加游戏内容，建议增加哪些类型的游戏，如角色扮演、教育益智、飞行射击等；\r\n4)你一般使用IPTV使用时间有多长，玩IPTV游戏时间有多长；\r\n5)你目前使用遥控器玩IPTV游戏是否习惯，是否觉得遥控器功能过于简单；如果增加相应手柄玩IPTV游戏，是否有兴趣；\r\n6)你对目前IPTV社区是否感到满意，是否申请注册，觉得社区哪些方面需要改进，如社区交友可以增加哪些功能；\r\n7)你是否满意目前IPTV游戏的用户体验，建议哪些用户体验功能可以改进和更加细化。\n[[i] 本帖最后由 cannymok 于 2011-01-29  14:52 编辑 [/i]]			','59.42.126.24',0,0,0,0,0,0,0,0,0,0,0,NULL),(292,17,130,0,'NJIMS02',81,'',1296199891,'[quote] 原帖由 [i]SHGS03[/i] 于 2011-01-27 18:10 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=268&ptid=130][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]有产品体验区还是不错的~方便了解自己需要的咨询噢~呵呵:lol [/quote]\r\n\r\n确实很方便的，呵呵','180.111.106.160',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(293,10,129,0,'NJIMS02',81,'',1296199958,'[quote] 原帖由 [i]wingotech[/i] 于 2011-01-27 11:24 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=251&ptid=129][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]给力，顶一下 [/quote]\r\n\r\n现在最时髦的词“给力” 哈哈','180.111.106.160',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(294,8,133,0,'SHGS03',82,'',1296201109,'我家办理的也是ADSL，不过ADSL网速慢，拨号总是678错误。。。','114.84.40.201',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(295,12,131,0,'SHGS03',82,'',1296201268,'没有亲自去过，不过我觉得产品体验区应该是很重要的一块~','114.84.40.201',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(296,27,155,0,'SHGS02',76,'',1296201369,'[color=red]   我平时上上开心网 看看转帖 玩玩摆摊位 偷菜 争车位之类的之类的小游戏 糖果 我就去玩一下泡泡鱼 比较带有技术性的游戏  还有就是通讯的棋牌类游戏和一些大型网游的游戏论坛 看看别人分享的经验  界面方面我是喜欢糖果 都做的比较细腻和精致 就是少了点带有技术性的游戏 [/color]','180.155.122.164',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(297,10,129,0,'SHGS03',82,'',1296201477,':loveliness: 希望可以越办越好噢~想在这里有更多的互动\r\n顶贴啦！！','114.84.40.201',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(298,27,155,0,'SHGS02',76,'',1296201800,'[color=red] 查找好友的功能 因为现在都昵称都用实名了 查找起来很方便很快 还有就是能看到好友的好友分享出来的帖 这样信息网又广了 这几点我都很喜欢 至于不满意的地方到真没什么[/color]:lol','180.155.122.164',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(352,27,163,1,'cannymok',74,'1月28日主题三-对糖果社区的评价',1296270805,'1）大家这两天都有尝试玩过盛大的糖果社区。你们感觉怎样？\r\n2）对糖果社区这样的游戏社区理念，和你平时常玩的游戏平台相比，觉得有什么不同的感受?\n[[i] 本帖最后由 cannymok 于 2011-01-29  14:56 编辑 [/i]]			','121.201.254.252',0,0,0,0,0,0,0,0,0,0,0,NULL),(351,28,160,0,'SHGT02',73,'',1296270682,'我很同意３楼的意见．可以和wii连接．实行远程交流和跨平台交流那就太棒了．那不是幻想因为x360的有些游戏可以跟pc玩家共同连动．这应该也是个趋势','60.169.25.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(354,17,130,0,'GZGT01',84,'',1296270867,'希望以后可以越办越好吧~','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(300,28,157,1,'cannymok',74,'1月28日主题一-自我介绍',1296203893,'大家好，欢迎你们参加我们的论坛！ \r\n今天由于网络关系（可能靠近春节上网的人多吧），帖子一直发不上来。幸亏咱们工作人员及时抢修了，在这里先给他们敬个礼O(∩_∩)O哈哈~ \r\n\r\n我先来自我介绍一下吧。 \r\n大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游，到处走走逛逛，认识不同的朋友。 \r\n\r\n下面，轮到你们来自我介绍一下啦 \r\n譬如说， \r\n姓名，年龄，职业？ \r\n性格？ \r\n爱好？？','59.42.126.22',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(303,28,160,1,'cannymok',74,'1月28日主题三－什么是iptv游戏呢',1296206460,'考虑到大家可能对IPTV游戏的概念有些不理解，这里做点补充：\r\n[font=宋体][size=12pt]IPTV[/size][/font][font=宋体][size=12pt]游戏频道是基于中国电信基于IPTV平台，为用户打造的IPTV游戏娱乐专区，IPTV用户可通过IPTV 进入“游戏频道”，利用电视荧屏，使用IPTV遥控器就可享受棋牌、休闲益智等类型的游戏服务。[/size][/font]\r\n[font=宋体][size=12pt]下面对对IPTV游戏的示例介绍，由于各个地方的IPTV游戏界面有所区别，一下介绍仅供参考，具体还得靠大家去用自己的IPTV体验下。[/size][/font]\r\n[align=center][font=宋体][size=12pt][attach]6[/attach][/size][/font][/align][align=center][font=宋体][size=12pt]IPTV游戏主界面[/size][/font][/align][align=center][font=宋体][size=12pt][font=宋体][size=12pt]用户进入[/size][/font]IPTV[font=宋体][size=12pt]游戏频道后，可以进行游戏各种游戏、社区交友、完成任务、购买道具、账户充值等[/size][/font]IPTV[font=宋体][size=12pt]游戏娱乐服务[/size][/font][/size][/font][/align][align=center][font=宋体][size=12pt][font=宋体][size=12pt][attach]8[/attach][/size][/font][/size][/font][/align][align=center][font=宋体][size=12pt][font=宋体][size=12pt]IPTV[font=宋体][size=10.5pt]游戏内容、用户可以选择不同的游戏类型[/size][/font][/size][/font][/size][/font][/align][align=center][font=宋体][size=12pt][font=宋体][size=12pt][font=宋体][size=10.5pt][attach]9[/attach][/size][/font][/size][/font][/size][/font][/align][font=宋体][size=12pt][font=宋体][size=12pt][font=宋体][size=10.5pt][align=center][align=center][font=宋体]玩酷农场界面[/font][/align][/align][/size][/font][/size][/font][/size][/font][align=center][font=宋体][size=12pt][font=宋体][size=12pt][font=宋体][size=10.5pt][attach]10[/attach][/size][/font][/size][/font][/size][/font][/align][font=宋体][size=12pt][font=宋体][size=12pt][font=宋体][size=10.5pt][align=center][align=center][font=宋体]好友管理示例[/font][/align][/align][align=center][font=宋体][size=14pt][size=4]在“我的好友”界面里，用户可以进行交友，好友管理等[/size][/size][/font][/align][align=center][font=宋体][size=14pt][attach]11[/attach][/size][/font][/align][font=宋体][size=14pt][align=center][align=center][font=宋体][size=3]用户中心示例[/size][/font][/align][/align][align=center][align=center][font=宋体][size=3][/size][/font] [/align][/align][align=center][font=宋体][size=12pt]用户可以在“用户中心”购买道具、充值代金币、头像修改、密码修改等[/size][/font][/size][/font][/align][/size][/font][/size][/font][/size][/font]\n[[i] 本帖最后由 cannymok 于 2011-01-28  17:43 编辑 [/i]]','59.42.126.24',0,0,0,0,0,0,0,1,0,0,0,NULL),(222,10,129,1,'admin',1,'第一体验俱乐部正式上线',1291789284,'“第一体验俱乐部”由中国电信广州研究院（[url=http://www.gsta.com]www.gsta.com[/url]）建立，专门用于用户体验与交流在线社区。\r\n\r\n        在这里，您可以通过多种方式，参与中国电信在产品和服务的体验和评估，帮助中国电信提升用户体验水平！ “第一体验俱乐部”共包括了3D虚拟社区、在线交流区、在线论坛三个不同的体验和互动区。','183.33.205.98',0,0,0,0,0,-1,0,0,0,0,0,NULL),(457,29,171,0,'SHIMS01',109,'视频通话的质量与宽带息息相关',1296308666,'视频通话的质量与宽带息息相关，这点我觉得挺有体会的。\r\n我单位用的是带宽为2M的宽带，坑子 哈哈\r\n所以别说是视频通话了，下载文件，打开网页都超级慢\r\n还好本人有远见，哈哈，用的长城\r\n前段时间它们促销，2M一年只有440元，超级划算哦\r\nSORRY 有广告之嫌疑 主要自己觉得合算了\r\n所以在家里上网快，视频聊天不延迟','58.218.204.235',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(458,29,171,0,'SHIMS02',107,'',1296311882,'希望以后打电话也能可视通话最好了，不象电脑上用的视频通话工具，大家要约好上网啊，才能来视频通话。如果就象一般随时随地拨打手机或是固话，也可以视频通话就方便了。','116.237.203.227',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(459,29,166,0,'SHIMS02',107,'',1296312696,'现在的电子产品越做越新颖，功能外观都还蛮吸引人的，肯定很多人喜欢啦','116.237.203.227',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(460,29,171,0,'SHIMS02',107,'',1296312881,'当然最好也是免费的就好了，毕竟现在用的视频通话工具都是免费的，呵呵！一个便宜三个爱呀','116.237.203.227',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(461,29,171,0,'NJIMS02',81,'',1296313739,'1）大家目前主要使用哪些通讯工具？（固定电话、手机、网上聊天工具）哪个通讯工具使用最多？其次呢？\r\n我目前使用的通讯工具有固定电话，手机，和上网聊天工具，上网聊天工具用的MSN,QQ,YY,ICQ等等，其中手机是使用的最多的通讯工具，因为现在经常在外面，有个手机联系起来就比较方便了。其次就是网上聊天工具，其中主要的就两个，MSN和QQ各有各的用处和联系对象，一般MSN使用多是工作上的人群，QQ主要是朋友了。\r\n\r\n\r\n2）您一般使用固定电话主要是和谁联系？\r\n固定电话是公司使用的多，主要还是和公司的客户进行联系。\r\n\r\n\r\n3）手机呢？\r\n手机联系的人群都包括，一般现在首先让朋友想起来的联系通讯工具还就是手机了。\r\n\r\n\r\n4）网上聊天工具呢？\r\n网上聊天工具要分情况了，MSN主要的是工作客户，工作上的人群。QQ主要的是朋友了，因为她的娱乐项目还是比较多的。ICQ主要还是和国外的朋友联系的比较多，不过有的国外朋友现在也使用MSN和QQ了。YY主要是和我玩多玩游戏的人群联系的多。\r\n\r\n\r\n\r\n1)大家听说过视频通话吗？是如何知道的？\r\n听说过视频通话，知道的途径还是通过网络的宣传，3G的推广宣传。\r\n\r\n\r\n2)大家使用过视频通话吗？\r\n有\r\ni.使用过哪种视频通话工具？用来跟哪些人联系？在什么情况下使用？使用频率如何？\r\n现在用的比较多的还是QQ，呵呵，但是一般还只和朋友时间联系，一般也只是比较好的朋友，但是好长时间没有见面了，大家见见，看看啥样子了。使用的频率不是太高，因为还是不是太需要这种需求。\r\n\r\n\r\nii.大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工具还需要哪些改进？\r\n方不方便这要看是什么情况了，我觉得不是所有的通话都是需要视频的，这样通起话来，不熟悉的朋友之间我觉得会有尴尬的。现在有的工具我觉得还是能满足我的需求的；需要改进的就是象素，画面质量等等。','180.111.105.121',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(462,29,166,0,'NJIMS02',81,'',1296313941,'大家好，我叫申玮，26岁，现在是家婚庆公司的负责人。我平时喜欢旅游，听音乐和在电脑上玩玩电子竞技类的游戏，比如篮球，实况，极品飞车等等。对IT类产品也有很大的兴趣，喜欢接触新的IT技术。','180.111.105.121',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(463,29,166,0,'SHIMS01',109,'小刚来也',1296313966,'大家好 我是小刚，不是周传雄，更不是李刚哦\r\n年龄37 房产公司经理\r\n喜欢上网 网上四国 网络聊天\r\n爱好开车出去旅游\r\n很高兴认识大家','58.218.204.235',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(464,10,129,0,'CDIMS02',110,'给力',1296314549,'新生 新颖 新视野\r\n希望第一体验俱乐部越办越好~~','116.55.228.92',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(465,17,130,0,'CDIMS02',110,'',1296314680,'用户体验做的感觉不做，至少我个人而言是很喜欢这种一条龙服务的，把生活的方方面面都考虑的很全面，用户至上应该是一切的前提。','116.55.228.92',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(466,12,131,0,'CDIMS02',110,'',1296314980,'去过一次，恰恰就是去专门体验真机，广告做的再好，也没有亲自体验来的真切，也许是简单的一次体验，但是每个人在用手机的习惯都是有的，也许你在意的是摄像功能，也许是界面的体验，也许是系统的风格，都是需要适合自己才能去选择，体验区是真的让大家真正接触的真实平台，期望所有的人都能去体验看看','116.55.228.92',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(467,8,133,0,'CDIMS02',110,'',1296315170,'座机和手机一体结合的用户最多，做的最好的可能就是大家所熟知的电信了。一体化的服务，把用户体验和用户需求牢牢的结合在一起，为客户考虑的深入全面','116.55.228.92',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(468,27,164,0,'GZGS02',93,'',1296315400,'1）通过昨天对糖果社区的体验，以及这个产品的介绍，大家觉得这个爱游戏怎样？哪些地方吸引你？ \r\n   相对于其他的游戏社区，多了一份新鲜感，哈哈\r\n2）对这12个功能点，你最喜欢的是哪些？为什么？ \r\n   3,6,7,10。微博自然不用说了。其他三项主要是其他游戏社区不常见。\r\n3）关于第7点“社区活动”，你们有兴趣参与吗？为什么？\r\n   有。可以有更多的互动。而且主要还是看看有没有这样的运气。赢了个什么东西，呵呵 \r\na)如果你玩的游戏积分可以在社区里使用，你觉得如何？ \r\n   这个我觉得比较正常，在游戏的积分用在社区上。\r\n4）关于第3点“微博”，你会使用吗？为什么？ \r\n   会。可以了解更多的一些资讯，视频等一些有趣，有用的信息。\r\n5）如果你看到别人的信息里有介绍他玩过的游戏，你会有冲动去玩玩看吗？什么时候会？\r\n   如果是小游戏还可以。大型网络游戏就不会了，因为时间的关系。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(469,27,172,0,'GZGS02',93,'',1296315497,'1）通过以上游戏社区的了解，你是否有兴趣里面的游戏产品，或者购买道具、各种会员服务资格等？愿意付费的金额大概多少？请填写下表： \r\n        是否愿意支付？ （是）       愿意付费的金额？ （5-10RMB）\r\n游戏产品  不会              \r\n道具      不会          \r\n会员服务资格  会\r\n        \r\n\r\n2）你希望通过什么形式进行付费，如手机短信扣费、网上银行、支付宝等等？\r\n   手机短信扣费、网上银行、支付宝','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(470,29,166,0,'CDIMS02',110,'',1296315519,'NICE TO MEET YOU\r\n我是佳佳，我的英文名是LUCY，我真有个叫LILY，但我们不是双胞胎，她是我表姐\r\n我是给力的80后，28岁，我做服装的\r\n很臭美，很喜欢结交朋友\r\n爱好旅游，逛街，上网，运动\r\n动静结合，外柔内刚\r\n很高兴认识更多的朋友','116.55.228.92',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(471,27,173,0,'GZGS02',93,'',1296315548,'如果能做到不同社区与不同社区的好友都可以互动，那么就更加完美了。当然，这个事情不太可能。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(472,29,171,0,'CDIMS02',110,'',1296315944,'我目前使用的固话，手机，QQ。手机用的是最多的，其次是QQ，最后是固话。\r\n固定电话我一般都选择跟同样有固定电话的亲人联系，比如爷爷，姨妈，妈妈\r\n手机联系最多的是老公，爸爸，朋友\r\nQQ最多的是远方的朋友和身边的好友，同事。\r\n\r\n听说过，\r\n但没有用过，因为目前使用的手机没有副摄像头，手机不支持','116.55.228.92',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(473,27,172,0,'GZGS01',77,'',1296315946,'1）除非真的沉醉在某个游戏里面，而且游戏的平衡程度不太好，会选择考虑购买游戏产品，道具，会员服务。游戏产品：10RMB以内,道具：10-30RMB,会员服务：10RMB。但通常还是考虑谨慎，不会星期付费。\r\n因为这是一个平台而已，没必要付出呢么多金钱来把精力投入进去。而且也要考虑年龄段的消费（个人给的建议，考虑到不同年龄段的消费，对于青少年消费还是一个不错的前景。\r\n2）手机短信扣费、网上银行、支付宝 。如果是中国电信的老用户，我觉得应该给予优惠或者在套餐里面有相关的费用。当然，价格还是一个挺关键的问题。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(474,27,173,0,'GZGS01',77,'',1296316020,'[quote] 原帖由 [i]GZGS02[/i] 于 2011-01-29 23:39 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=471&ptid=173][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]如果能做到不同社区与不同社区的好友都可以互动，那么就更加完美了。当然，这个事情不太可能。 [/quote]这建议确实不错，但如果这样做，就变得有点想网络游戏了。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(475,27,173,0,'GZGS01',77,'',1296316292,'在游戏社区的经验告诉我，一个游戏社区或者游戏，太过注重于利字，会令到玩家或用户感觉被游戏牵着走。当然，开发商也需要一定的资金。但向往玩家或用户口袋里掏钱，这并非一个容易的事情。主要在与，你的产品能不能给顾客自己觉得掏这个钱是值得的。而不是游戏从侧面令到顾客不得不掏钱。','113.96.120.134',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(476,28,169,0,'SHGT01',72,'',1296319031,'目前咨询很方便诶，直接打电话就可以了。态度很不错，很有耐心。\r\n客服对投诉或者咨询的反应也蛮及时的，可能也是因为我没有问太棘手的问题吧。\r\n流程还不错，基本很清晰，比较满意。\r\n如果是VIP的话，建议区别对待，不过建议专门设立一条VIP的号码。','58.37.146.233',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(477,28,170,0,'SHGT01',72,'',1296319466,'我觉得最重要的是要改善游戏吸引力。毕竟玩游戏的目的都是由兴趣吸引，没吸引力直接导致没人玩。我认为可以内建一些游戏平台的端口，比如PS3，XBox360，或者是Wii。充分利用IPTV网络快速的优势，结合游戏平台本身的吸引力，加上一些其自带的有趣游戏（比如RPG，FPS等），配合社区和周边商品(正版游戏光碟，攻略，手柄，控制器之类。)加入金币充值的概念，一定可以很好的用好IPTV的游戏功能！','58.37.146.233',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(478,28,170,0,'NJGT01',106,'',1296351838,'希望能够针对各种人群，建议按种类来划分！例如：儿童类，成人类，老年类等等。','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(479,28,169,0,'NJGT01',106,'',1296351957,'咨询还是比较方便的  态度不错 \r\n投诉反应比较快  效果一般   具体内容 专业度不是很强 \r\n流程比较满意  希望提高人员专业技能 \r\nVIP的话，还是建议可以设置的。','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(480,28,165,0,'NJGT01',106,'',1296352250,'相信大家都遇到过玩游戏要付费的问题， \r\n（1）以下几种付费渠道，你最喜欢哪两种？为什么？ \r\na)IPTV宽带代收 \r\nb)网上银行、 \r\nc)付宝 \r\nd)手机短信进行扣费 \r\n答：a,c我认为这两种方式最方便，快捷，安全。\r\n（2）你们还喜欢哪些付费渠道？（ 去营业厅缴纳      ） \r\n（3）如果设虚拟币，以点卡形式销售，大家有兴趣购买吗？为什么？\r\n答：我会购买！比较方便 \r\n（4）以下两种收费形式，你最喜欢哪种？为什么？ \r\na)包年/包月形式（IPTV游戏平台有免费游戏，但部分精品游戏用户不能免费玩，需要通过包年/包月形式定购） \r\nb)按次收费（玩一次游戏收一次费） \r\n答：b，这样可以避免浪费\r\n（5）你对这两种收费形式可接受的价格分别是多少？ \r\na)包年/包月形式：（150     ）元/年；（ 18     ）元/月 \r\nb)按次收费：（  1    ）元/次 \r\n（6）用户需要获得更多的经验、积分值或者得到道具更加个性化、更具优势玩IPTV，此时用户可以购买道具，道具是按次收费。你希望每次的收费是怎样的？（  2   ）元/次','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(481,28,160,0,'NJGT01',106,'',1296352600,'我也是比较赞同4楼的看法。既然做，就把它做好，做大，做的有竞争力！','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(482,28,159,0,'NJGT01',106,'',1296353109,'而且，在宣传上要跟的上！要让大家熟知这个游戏平台，游戏内容要全，但可以有侧重点！','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(483,28,157,0,'NJGT01',106,'',1296353205,'我也希望借助这个平台，能够认识更多的，有共同爱好的新朋友！多一个朋友，多一条路！','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(484,29,174,1,'cannymok',74,'1.30主题一：高清视频产品概念测试',1296354012,'下面的图片是对可能推广的某个高清视频产品的介绍。请仔细看这个介绍。\r\n\r\n请问：\r\n（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？\r\n（2） 哪些方面符合你的需要？为什么？\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？\n[[i] 本帖最后由 cannymok 于 2011-01-30  10:42 编辑 [/i]]			','183.33.205.117',0,0,0,0,0,0,0,1,0,0,0,NULL),(485,29,174,0,'SHIMS02',107,'',1296357563,'1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n挺喜欢的。一、使用简便，和普通电话机一样的用法，老人操作起来方便。二、超大高清屏幕，使得视频看起来更清晰。三、随时可以视频通话，不象一般的网上视频工具，要约好一起上网才能视频。\r\n\r\n（2） 哪些方面符合你的需要？为什么？ \r\n首先功能符合了我们的需要，直接拨打电话时就可以视频了。还具有通讯录功能，查找电话方便多了，家里老人喜欢用本子记录电话号码，可是经常被极具破坏力的小孩子撕坏或是不晓得扔到哪里去了。特别是可以作为电子相框，兼具了功能性和装饰性。很好。\r\n\r\n\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？\r\n外观实在一般，很山寨电话机的样子，难道不能作得有点设计感或是质感，可以参照手机或是电子相框的样子来作嘛。','118.242.1.66',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(486,27,173,0,'NJGS03',105,'',1296358857,'聊了2天爱游戏社区，以你们对心目中游戏社区的理解，还有什么好的建议，希望能有什么功能，让你们更有兴趣去使用？\r\n\r\n我也觉得增加交友就好啦~','122.193.13.18',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(487,27,172,0,'NJGS03',105,'',1296359011,'1）通过以上游戏社区的了解，你是否有兴趣里面的游戏产品，或者购买道具、各种会员服务资格等？愿意付费的金额大概多少？请填写下表： \r\n        是否愿意支付？   愿意     愿意付费的金额？ 一个月30-50\r\n游戏产品                愿意\r\n道具                    愿意\r\n会员服务资格            愿意\r\n        \r\n\r\n2）你希望通过什么形式进行付费，如手机短信扣费、网上银行、支付宝等等？\r\n支付宝  有第三方平台  放心\r\n网银是因为要U盾  必须另外付钱买  不想~~~~~\r\n手机的话我还是不放心  怕另外收费……','122.193.13.18',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(488,27,164,0,'NJGS03',105,'',1296359240,'看了这个平台的介绍，有几个问题想了解下的： \r\n1）通过昨天对糖果社区的体验，以及这个产品的介绍，大家觉得这个爱游戏怎样？哪些地方吸引你？\r\n比较创新  很有吸引力 \r\n2）对这12个功能点，你最喜欢的是哪些？为什么？ \r\n3   最近比较迷恋微薄\r\n3）关于第7点“社区活动”，你们有兴趣参与吗？为什么？ \r\n当然啦   可以试试运气  \r\na)如果你玩的游戏积分可以在社区里使用，你觉得如何？ \r\n相当棒   积分换东西这个活动值得推崇\r\n4）关于第3点“微博”，你会使用吗？为什么？ \r\n会的   方便\r\n5）如果你看到别人的信息里有介绍他玩过的游戏，你会有冲动去玩玩看吗？什么时候会？\r\n会的  反正无聊  可以尝试一下','122.193.13.18',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(489,27,163,0,'NJGS03',105,'',1296359326,'1）大家这两天都有尝试玩过盛大的糖果社区。你们感觉怎样？ \r\n我一直都很喜欢盛大的游戏   相当的支持\r\n2）对糖果社区这样的游戏社区理念，和你平时常玩的游戏平台相比，觉得有什么不同的感受?\r\n很不错~~因为我学习的就是制作3D  盛大 就是我的目标','122.193.13.18',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(490,27,155,0,'cannymok',74,'',1296359867,'[quote] 原帖由 [i]NJGS03[/i] 于 2011-01-29 13:01 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=375&ptid=155][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]1）你平时有使用哪些游戏社区？觉得它们的整体界面怎样？哪里喜欢？哪里不喜欢？ \r\n人人网  因为每天必上   所以顺便玩玩游戏   方便啦\r\n2）你们最喜欢社区里的哪几个功能？有没有不满意的地方，是哪些？为什么？ \r\n斗地主不错~~就是里面人素质太差了~~很多人注册小号  打完骂   真无聊 [/quote]\r\n\r\n什么叫注册小号啊？？','219.137.58.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(491,27,163,0,'cannymok',74,'',1296360332,'[quote] 原帖由 [i]SHGS02[/i] 于 2011-01-29 18:50 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=423&ptid=163][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url][quote] 原帖由 [i]cannymok[/i] 于 2011-01-29 11:13 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=352&ptid=163][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]1）大家这两天都有尝试玩过盛大的糖果社区。你们感觉怎样？ 2）对糖果社区这样的游戏社区理念，和你平时常玩的游戏平台相比，觉得有什么不同的感受? [/quote]\r\n\r\n\r\n\r\n   总体来说感觉不错吧 画面做的很细腻 游戏也很多 就是缺少点游戏性 不是很耐玩 和有点女性化（太可爱类型的） \r\n   在线客服也是很不错的 查询问题的速度也很快 不用等多久就能回答我所有的问题 除了游戏性和风格方面 大致我都很喜欢的 希望有所改善 那就更完美了 :lol [/quote]\r\n\r\n你希望在哪些方面改善呢？','219.137.58.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(492,27,163,0,'cannymok',74,'',1296360495,'[quote] 原帖由 [i]GZGS01[/i] 于 2011-01-29 20:09 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=434&ptid=163][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]糖果社区是不错，一切都很自然，一顺手。\r\n和其他平台的话对比，互动性和交友性的游戏还很欠缺。但日后应该会有很多发展得机会。\r\n（个人对盛大的评价：网络游戏里面，盛大虽有名气，但某些方面的口碑还是令众多用户失望的。 [/quote]\r\n\r\n盛大哪些方面做得不够令人满意呢？可以跟大家分享一下吗？','219.137.58.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(493,10,129,0,'匿名',0,'',1296360655,'感觉很不错，电信推出第一体验俱乐部给我们带来更多的方便和快捷的信息及交流平台。给力！！','118.122.85.165',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(494,29,174,0,'GZIMS03',104,'',1296360818,'[b]（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？\r\n   本人比较喜欢这个产品，操作简单，信号稳定，适合普通家庭使用，不需过大投资。\r\n（2） 哪些方面符合你的需要？为什么？ \r\n  能和传统电话，手机进行语言交流，对门户的直接点击呼叫，方便查询信息。具备通讯录，改善重复输入号码的麻烦，新颖的数码相框功能更丰富生活，记录生活的点滴。\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？[/b]\r\n  [b]第一眼感觉误以为传真机，外形缺乏吸引力，在设计方面应再添加时尚感。[/b]','58.248.7.142',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(495,27,156,0,'wangbin',78,'',1296360982,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-28 13:40 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=287&ptid=156][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]大家好，欢迎你们参加我们的论坛！ \r\n  今天由于网络关系（可能靠近春节上网的人多吧），帖子一直发不上来。幸亏咱们工作人员及时抢修了，在这里先给他们敬个礼O(∩_∩)O哈哈~ \r\n\r\n  我先来自我介绍一下吧。 \r\n大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游，到处走走逛逛，认识不同的朋友。 \r\n\r\n下面，轮到你们来自我介绍一下啦 \r\n譬如说， \r\n姓名，年龄，职业？ \r\n性格？ \r\n爱好？？ [/quote]\r\n\r\n大家好,我是过客,这个论坛挺不错的','59.42.126.26',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(496,10,129,0,'CDIMS01',89,'',1296361026,'感觉很不错，电信推出第一体验俱乐部给我们带来更多的方便和快捷的信息及交流平台。给力！！','118.122.85.165',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(497,27,156,0,'cannymok',74,'',1296361447,'[quote] 原帖由 [i]wangbin[/i] 于 2011-01-30 12:16 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=495&ptid=156][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url][quote] 原帖由 [i]cannymok[/i] 于 2011-01-28 13:40 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=287&ptid=156][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]大家好，欢迎你们参加我们的论坛！ \r\n  今天由于网络关系（可能靠近春节上网的人多吧），帖子一直发不上来。幸亏咱们工作人员及时抢修了，在这里先给他们敬个礼O(∩_∩)O哈哈~ \r\n\r\n  我先来自我介绍一下吧。 \r\n大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游，到处走走逛逛，认识不同的朋友。 \r\n\r\n下面，轮到你们来自我介绍一下啦 \r\n譬如说， \r\n姓名，年龄，职业？ \r\n性格？ \r\n爱好？？ [/quote]\r\n\r\n大家好,我是过客,这个论坛挺不错的 [/quote]\r\n\r\n欢迎～～～～','219.137.58.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(498,27,175,1,'cannymok',74,'1月30日补充主题',1296361743,'非常感谢大家之前2天对论坛的支持，为我们提供了很宝贵的意见。\r\n但我还有几个问题想补充问一下：\r\n\r\n1．你们认为什么才是有技术含量的游戏？ \r\n2．怎样的游戏才是互动性强？\r\n3．什么游戏才对你们有吸引力？\r\n4．怎样的新的游戏社区才是真正创新的，有吸引力的？\r\n5．大家认为开心网对你们最有吸引力的地方是什么？','219.137.58.20',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(499,29,174,0,'CDIMS02',110,'',1296361974,'（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n喜欢高清带给人们的真实感，特别喜欢高清的分辨率，感觉中国的通信网络还不是很稳定，有时感觉有局限性\r\n（2） 哪些方面符合你的需要？为什么？ \r\n能随时随地打电话视频，这样能让关心你的人更加真切的感受你是否开心，你是否安好。对于我个人来说，我是比较臭美的人，本身就是做服装的，我喜欢把自己新到的衣服秀给身边的好友或者顾客，也同样在换另外一种宣传产品的方式，达到营销产品的目的。\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？\r\n屏幕的分辨率希望能提高，能让简单沉稳的电话机时尚一点，例如可以做一些触屏的电话机，简单方便，大方时尚','222.183.15.235',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(500,17,130,0,'CDIMS01',89,'',1296362449,'我家就用的我的e家e9套餐服务，话费网费节约不少，宽带网络也很稳定，希望以后有更多的优惠服务。','118.122.85.165',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(501,27,172,0,'SHGS02',76,'',1296363515,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-29 21:22 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=439&ptid=172][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]1）通过以上游戏社区的了解，你是否有兴趣里面的游戏产品，或者购买道具、各种会员服务资格等？愿意付费的金额大概多少？请填写下表： 是否愿意支付？ 愿意付费的金额？游戏产品 道具 会员服务资格 2）你希望通过什么形式进行付费，如手机短信扣费、网上银行、支付宝等等？ [/quote]\r\n\r\n\r\n   我愿意支付的 道具么10元以内 会员30以内吧  支付方式支付宝或是网上银行简单又安全','222.67.224.216',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(502,27,175,0,'SHGS03',82,'',1296363609,'[color=DarkOrchid]1）游戏就是用来娱乐休闲的，容易上手让大家都能玩上瘾，但是这个游戏必须有足够的游戏背景，熟练度，思考程度的表现。\r\n2）如果要互动性强就需要游戏能吸引各种人群一起玩，那么肯定要是比较欢快的大型游戏或者就是要共同协作通关的战略游戏。\r\n3）我个人喜欢可以消耗时间带有益智性，同时有可爱画面的游戏，如：盛大的泡泡堂，跑跑卡丁车，龙之谷\r\n4）新的游戏社区一定要有新的内容，是一个与家人、朋友、同学，游戏好友一块玩、一块赚钱的地方，可以通过游戏认识更多人，大家一起交流游戏。\r\n5）我喜欢开心网的投票，通过它可爱看到朋友不为人知的一面，也可以说是很真实的一面。另外一点就是它的游戏可以满足人的虚荣心（如：开心富翁，开心餐厅，停车等）[/color]','114.84.35.192',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(503,27,172,0,'SHGS03',82,'',1296363802,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-29 21:22 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=439&ptid=172][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]1）通过以上游戏社区的了解，你是否有兴趣里面的游戏产品，或者购买道具、各种会员服务资格等？愿意付费的金额大概多少？请填写下表： \r\n[color=darkorchid]1）是否愿意支付：看情况，如果真的很喜欢这个游戏可能会付[/color]\r\n[color=darkorchid] 愿意付费的金额：适当的付费，不会超过50元[/color]\r\n[color=darkorchid]游戏产品 道具 会员服务资格都有可能，如果是社区类的话会买会员，因为很多功能需要用到会员[/color]\r\n[color=darkorchid] 2）你希望通过什么形式进行付费：我觉得还是支付宝安全些吧~[/color]','114.84.35.192',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(504,27,173,0,'SHGS02',76,'',1296363856,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-29 21:24 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=440&ptid=173][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]聊了2天爱游戏社区，以你们对心目中游戏社区的理解，还有什么好的建议，希望能有什么功能，让你们更有兴趣去使用？ [/quote]\r\n\r\n\r\n     我个人喜欢交朋友 希望能在社区里增加交友功能 就好比大家不认识的 我把自己的照片名字出生年月日兴趣爱好什么的挂上去 想和我交朋友的人就能直接看到我的信息和我聊天了 我很希望有这样的功能','222.67.224.216',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(505,27,172,0,'SHGS03',82,'',1296363905,'[color=DarkOrchid]游戏产品 ：做多不会超过20元              \r\n道具 :20元               \r\n会员服务资格：10元[/color]','114.84.35.192',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(506,27,173,0,'SHGS03',82,'',1296364191,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-29 21:24 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=440&ptid=173][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]聊了2天爱游戏社区，以你们对心目中游戏社区的理解，还有什么好的建议，希望能有什么功能，让你们更有兴趣去使用？ [/quote]\r\n[color=darkorchid]刚开始做千万不能抱着赚钱的态度，要以人为本，让别人信任这个社区[/color]\r\n[color=darkorchid]至于里面的功能，我觉得如果都能做到列出来的那几点，已经很吸引我了，相信大部分的人都会去尝试[/color]\r\n[color=darkorchid]大型的网游如果能在网页上玩，这点就非常不错，既然是社区游戏，朋友间的互动一定要有哦[/color]','114.84.35.192',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(507,27,175,0,'NJGS01',79,'',1296364576,'玩的时间一定要久 不要一会就没人玩咯\r\n可以连接对战啊\r\n玩得人多的游戏\r\n游戏要经常跟新啊 \r\n可以找到以前的朋友啊','114.221.61.101',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(508,27,175,0,'SHGS02',76,'',1296365000,'[quote] 原帖由 [i]cannymok[/i] 于 2011-01-30 12:29 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=498&ptid=175][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]非常感谢大家之前2天对论坛的支持，为我们提供了很宝贵的意见。但我还有几个问题想补充问一下： 1．你们认为什么才是有技术含量的游戏？ 2．怎样的游戏才是互动性强？ 3．什么游戏才对你们有吸引力？ 4．怎样的新的游戏社区才是真正创新的，有吸引力的？ 5．大家认为开心网对你们最有吸引力的地方是什么？ [/quote]\r\n\r\n1 我感觉技术含量就是游戏的操作方面 我玩了社区的几个游戏感觉都千篇一律大同小异的玩玩就感觉没意思了 我感觉是不是能在有些游戏里增加些任务 这样的话就不会感觉无聊了\r\n2 互动性 就是有些游戏一个人是无法完成任务的 那时候就需要朋友的的帮助了 这样的话朋友叫朋友一个叫一个越叫越多\r\n3我个人比较喜欢动作类的游戏 比较耐玩\r\n4这个怎么说呢 我感觉创新的游戏就是要适合不同年龄层次的人都能玩的游戏也是都能喜欢的游戏 就好比QQ农村 连外我父亲也在玩 这个就是一个热点\r\n5开心网就好比一个很大的人际平台 我能看到好友的好友发的贴 能看到别人在做什么说些什么 能让我获取很多宝贵的信息','222.67.224.216',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(509,29,171,0,'GZIMS02',102,'',1296366410,'1.29主题二/三—聊聊目前使用的通讯工具，以及对视频通话的感受\r\n请大家要记得踊跃发言哦！每天我们会评出发帖最多的前三名灌水英雄榜，有现金奖励的哦～～～但那些纯粹灌水的帖子，例如：沙发，路过啊，我们是不算进去的。 \r\n所以，大家多点发有质量、有内容的内涵帖哦！！！！！！ \r\n\r\n下面我们来了解下大家通信工具的使用情况 \r\n\r\n1）大家目前主要使用哪些通讯工具？（固定电话、手机、网上聊天工具）哪个通讯工具使用最多？其次呢？\r\n[img][b]我主要使用的通讯工具是固话和手机,其次是MSN和QQ,这是因为工作需要.[/[/b]\r\n\r\n2）您一般使用固定电话主要是和谁联系？\r\n联系朋友,家人为主\r\n\r\n\r\n3）手机呢？\r\n手机用于工作\r\n\r\n\r\n4）网上聊天工具呢？\r\n用于工作.\r\n\r\n\r\n下面想向大家了解一些视频通话的一些使用情况（2个问题都需要回答哦，大家记得踊跃回答，每个回答最好标明对应的题号） \r\n\r\n\r\n1)大家听说过视频通话吗？是如何知道的？\r\n知道.\r\n最开始了解这个是从电视上知道,因为在香港几年前就有视频电话.上年好像中国移动和电信都开始才搞这个视频电话\r\n\r\n\r\n2)大家使用过视频通话吗？\r\n没有.\r\n\r\n\r\na)如果有： \r\ni.使用过哪种视频通话工具？用来跟哪些人联系？在什么情况下使用？使用频率如何？\r\n\r\n\r\n\r\nii.大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工具还需要哪些改进？\r\n\r\n\r\n\r\n\r\nb)如果没有，为什么没有使用？\r\n没有','121.33.210.106',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(510,29,174,0,'GZIMS02',102,'',1296366829,'下面的图片是对可能推广的某个高清视频产品的介绍。请仔细看这个介绍。 \r\n\r\n请问： \r\n（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n喜欢,功能多,用途广泛.贴近人的生活.\r\n不喜欢,可能屏幕太小了,如果用来看电视不太适合\r\n\r\n（2） 哪些方面符合你的需要？为什么？ \r\n屏幕上显示呼叫门户,这样使我了解更多关于店铺的信息\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？\r\n改成便提式,好像I-PAD的样子','121.33.210.106',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(511,29,174,0,'NJIMS01',112,'',1296367497,'（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n本人比较喜欢这个,很好的产品,和家人通话的时候,特别想念他们,这样可以随时看到,包括表情,家里人的变化,\r\n（2） 哪些方面符合你的需要？为什么？ \r\n符合现代社会发展的需要,对于我个人来说,更多的是可以相见,如同见面,高清的也很好,功能强大,还可以做电子相册,方便.信息定制的多功能,也不错,出行这样更方便了.\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？\r\n外观一般,不是很时尚,可以做的更漂亮一点,象笔记本一样,可以有多种色彩,','180.110.35.24',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(512,27,175,0,'NJGS03',105,'',1296368142,'1．你们认为什么才是有技术含量的游戏？ \r\n画面比较流畅  尤其是3D的游戏  游戏分面要细致一点 虽然很多靠的是贴图  但是我觉得中国有必要做出一款很牛X的游戏\r\n2．怎样的游戏才是互动性强？ \r\n适合朋友一起玩的游戏  尤其是情侣游戏  现在看到很多男生玩DOTA葫芦额女朋友  有点小悲剧啊\r\n3．什么游戏才对你们有吸引力？ \r\n任何游戏  因为我学的就是游戏专业  所以我愿意尝试各种游戏\r\n4．怎样的新的游戏社区才是真正创新的，有吸引力的？ \r\n我玩过一款很幼稚的  摩尔庄园  里面有游戏解说  我觉得很不错\r\n5．大家认为开心网对你们最有吸引力的地方是什么？ \r\n实名制   不像很多网名跋扈的网站\r\n一个个脏乎连篇  到底是玩游戏还是发泄啊？','122.193.13.18',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(513,29,176,1,'cannymok',74,'1.30专题二：高清视频通信产品的主要功能评价',1296370271,'关于高清视频通信产品的价格建议\r\n(1)你是否愿意使用这项产品？为什么？\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\n[[i] 本帖最后由 cannymok 于 2011-01-30  15:20 编辑 [/i]]			','59.42.126.46',0,0,0,0,0,0,0,0,0,0,0,NULL),(515,29,178,1,'cannymok',74,'1月30日主题三：高清视频通信主要功能评价',1296372121,'（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。\r\n   i.你为什么最喜欢这3个功能？\r\n（2）请再选出3个你觉得最没有吸引力的功能？\r\n   i.你为什么觉得这三个功能没有吸引力？\r\n（3）你还希望增加哪些功能？','219.137.58.20',0,0,0,0,-1,-1,0,0,0,0,0,NULL),(516,28,179,1,'cannymok',74,'1月30日补充主题',1296372640,'首先，非常感谢大家之前2天对我们论坛的支持。下面还有几个问题，希望大家继续探讨，给我们提供宝贵的意见：\r\n\r\n1.你认为目前ITV游戏社区怎么样？为什么不能吸引你？\r\n2.希望增加教育益智类哪些游戏？\r\n3.有人提议说ITV的游戏毕竟是通过遥控器操作的，希望难度不要太大。你们认为怎么样？怎样的游戏才算难度不大适合用于ITV游戏呢？\r\n4.有人提议说游戏应分别针对小孩、老人、成人、家庭主妇。你们认为如何？那么针对上述这几类人群，应分别设立哪些游戏呢？\r\n5.目前体验试验多长，收费如何？希望如何延长？\r\n6.客服方面，目前是通过拨打10000号，你们认为怎么样？是否还需要开通其他客服的方式呢？			','219.137.58.20',0,0,0,0,0,0,0,0,0,0,0,NULL),(517,28,157,0,'GZGT01',84,'',1296375720,'我叫子乐,今年20岁.现在在一个私人服装店做销售员,平时放工之后最喜欢玩玩网游(例如龙之谷),还有手机小游戏.希望今后可以和大家多多交流.','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(518,28,170,0,'GZGT03',113,'',1296376363,'提供更具个性化的服务才能吸引并留住用户，游戏多扩充一点就更好了。特别是针对以电视为终端的非常广泛的人群，包括妇女、老人、儿童，还有白领等等这样一些人群。能休闲的，家庭互动的，有锻炼身体作用的游戏就更好。','219.136.9.111',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(519,28,160,0,'GZGT03',113,'',1296376457,'IPTV做游戏业务还可以建立一个新的收入增长点，因为国内现在的基于内容的支付其实还是相对比较困难，尤其互联网的开放使得很多破解、盗版这样的方式比较盛行。IPTV其实是一个很好的平台，因为在这个平台中，运营商以及内容提供方有比较强的管控能力，又有非常容易使用的特点，基于IPTV来尝试在中国引入游戏内容的收费，建立起一个Game Store的概念，基于游戏内容的分发、计费，来运营，来提升用户的ARPU值。','219.136.9.111',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(520,28,159,0,'GZGT01',84,'',1296376493,'1)其实还是非常方便的,能够直接在首页找到,很明显.\r\n2)基本上算是满意的,可是其实还可以做得更好.例如游戏更新速度快一些,内容更加丰富一些.\r\n3)我最钟情于IPTV的益智类游戏.如果要增加游戏内容的话,我希望是角色扮演类型的. \r\n4)我一般使用IPTV使用时间3个小时左右,玩游戏2个小时. \r\n5)我觉得目前玩IPTV游戏还是习惯的,因为不算是太过困难.游戏比较简单所以遥控器功能简单也比较合适.如果增加相应手柄玩IPTV游戏的话,我是有兴趣尝试的. \r\n6)我觉得目前IPTV社区的内容有点少,而且交流的朋友人数也不是很多.如果可以把社区弄得更加丰富,更加吸引人的话,相信会有更多的朋友参与交流.\r\n7)我对目前IPTV游戏的用户体验比较满意,如果可以的话,建议在游戏画面处理得更加精细.','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(521,28,169,0,'GZGT03',113,'',1296376598,'1)目前咨询是否方便快捷，对客服人员态度是否满意；\r\n咨询还是挺快捷的，客服人员态度也可以，基本满意。\r\n\r\n2)客服对投拆的反应是否迅速，解决投拆的效果如何，是否满意；\r\n客服对投诉反映还算比较快，解决投诉的效果一般，不够专业，满意度一般\r\n\r\n3)对目前咨询、投拆流程是否感到满意，流程哪些地方需要改进；\r\n投诉流程比较满意，希望更加能针对性解决问题，提供更多个性化服务。\r\n\r\n4)是否觉得要区分普通客服和VIP客服。\r\n需要，这样才能更有效服务。','219.136.9.111',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(522,28,160,0,'GZGT01',84,'',1296376616,'其实这些小游戏在其他的地方也是可以玩到的,不过在电视上玩到就是一个小小的突破.如果可以的话,希望接下来可以增加更多类型的游戏,而且现有的游戏也可以作出更加复杂更加丰富的游戏内容.','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(523,29,166,0,'GZIMS02',102,'',1296376672,'我叫Karl,30岁,男\r\n从事物流业\r\n平常爱好是运动','121.33.210.106',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(524,28,165,0,'GZGT03',113,'',1296376792,'（1）以下几种付费渠道，你最喜欢哪两种？为什么？ \r\nb)网上银行、 因为网上银行的使用已经很普偏，自己也了解消费的情况。\r\n（2）你们还喜欢哪些付费渠道？（  上门收费     ） \r\n（3）如果设虚拟币，以点卡形式销售，大家有兴趣购买吗？为什么？ \r\n会有兴趣，比较方便。\r\n（4）以下两种收费形式，你最喜欢哪种？为什么？ \r\nb)按次收费（玩一次游戏收一次费） ，因为这样更能按个人喜好选择喜欢的又系。\r\n（5）你对这两种收费形式可接受的价格分别是多少？ \r\na)包年/包月形式：（ 100    ）元/年；（   10   ）元/月 \r\nb)按次收费：（     1 ）元/次 \r\n（6）用户需要获得更多的经验、积分值或者得到道具更加个性化、更具优势玩IPTV，此时用户可以购买道具，道具是按次收费。你希望每次的收费是怎样的？（   1-3  ）元/次','219.136.9.111',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(525,29,176,0,'SHIMS02',107,'',1296376795,'(1)你是否愿意使用这项产品？为什么？ \r\n愿意使用，因为方便可以随时视频通话\r\n\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n三百以内，也可以出些好看的个性的高端机器。\r\n\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n二十元','118.242.1.66',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(526,12,131,0,'NJIMS03',114,'',1296376880,'最重要的是有人接待顾客啊，不要每次到柜台接待人员都忙的不理人~~','180.109.148.9',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(527,28,157,0,'GZGT03',113,'',1296376903,'大家好，我叫蔚怡，女，32岁，职业：文员\r\n平时爱好上网，玩游戏、旅游、网购、看电影、与家人共享天伦，等等。','219.136.9.111',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(528,29,166,0,'GZIMS03',104,'',1296376936,'HELLO，各位！我叫bebee，是一名保险顾问，爱好羽毛球，上网，SHOPPPING，看电影！非常喜欢电子产品！大家有理财需要可以联系我！','58.248.7.142',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(529,28,159,0,'GZGT02',94,'',1296376979,'同意楼上的，“如果增加相应手柄玩IPTV游戏的话,我是有兴趣尝试的. ”\r\n现在控制起来不是很爽！','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(530,28,165,0,'GZGT01',84,'',1296377008,'（1）以下几种付费渠道，你最喜欢哪两种？为什么？ \r\na)IPTV宽带代收 \r\nc)付宝 \r\n\r\n首先喜欢IPTV代收是觉得很方便.\r\n喜欢支付宝付费就是觉得可以像淘宝购物那样交易完之后还可以给予评价.也方便我们用户及时给予电信商最真实的评价以及反馈我们的需求.\r\n\r\n（2）你们还喜欢哪些付费渠道？（       ） \r\n上门收取,方便我们用户.\r\n\r\n（3）如果设虚拟币，以点卡形式销售，大家有兴趣购买吗？为什么？\r\n有兴趣购买,因为也是一种方便用户付费的一个渠道,不过购买地点一定要足够.\r\n \r\n（4）以下两种收费形式，你最喜欢哪种？为什么？ \r\na)包年/包月形式（IPTV游戏平台有免费游戏，但部分精品游戏用户不能免费玩，需要通过包年/包月形式定购） \r\nb)按次收费（玩一次游戏收一次费） \r\n\r\n我会喜欢A多一点.一次性包年/包月不用每次玩都要付费那么麻烦,可是希望能够给予用户一些适当的优惠(例如包几个月送几个月之类的)\r\n\r\n（5）你对这两种收费形式可接受的价格分别是多少？ \r\na)包年/包月形式：（ 300    ）元/年；（  50   ）元/月 \r\nb)按次收费：（  1    ）元/次 \r\n（6）用户需要获得更多的经验、积分值或者得到道具更加个性化、更具优势玩IPTV，此时用户可以购买道具，道具是按次收费。你希望每次的收费是怎样的？（ 0.5    ）元/次','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(531,29,176,0,'GZIMS02',102,'',1296377030,'关于高清视频通信产品的价格建议 \r\n(1)你是否愿意使用这项产品？为什么？ \r\n如果我个人而言,暂时不需要用到高清视频,因为平常网络上也可以用到.\r\n\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n收费应该和手机费用差不多\r\n\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n这个方法也可以作为收费方式之一.但我觉得应该推荐多个付费方式,例如包月.','121.33.210.106',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(532,28,179,0,'GZGT03',113,'',1296377208,'1.你认为目前ITV游戏社区怎么样？为什么不能吸引你？ \r\n觉得目前游戏区功能还是挺好的，但是感觉游戏类别不够多\r\n\r\n2.希望增加教育益智类哪些游戏？ \r\n希望增加一些适合小朋友，启蒙类的游戏，能手脑一起锻炼的。\r\n\r\n3.有人提议说ITV的游戏毕竟是通过遥控器操作的，希望难度不要太大。你们认为怎么样？怎样的游戏才算难度不大适合用于ITV游戏呢？ \r\n我觉得通过摇控操作是有一定的难度，控制在2-3个键内完成的游戏，这样就比较适合\r\n\r\n4.有人提议说游戏应分别针对小孩、老人、成人、家庭主妇。你们认为如何？那么针对上述这几类人群，应分别设立哪些游戏呢？\r\n我觉得这个提议很好，因为这些人群就是电视的日常使用者，特别是妇女和老人、小孩。能出一些简单初级或者家庭共同完成的游戏比较好，能让一家人更亲近。\r\n \r\n5.目前体验试验多长，收费如何？希望如何延长？ \r\n能再延迟一倍就更好\r\n\r\n6.客服方面，目前是通过拨打10000号，你们认为怎么样？是否还需要开通其他客服的方式呢？\r\n我觉得拨1万号还是方便的，或者加上114更好。','219.136.9.111',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(533,28,157,0,'GZGT02',94,'',1296377272,'我叫 Harry,\r\n中意上网睇电影，玩玩在线小游戏，5子棋，打扑克，出外游山玩水结交朋友，^_^\r\n祝各位网友新年好！','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(534,29,176,0,'GZIMS03',104,'',1296377303,'(1)你是否愿意使用这项产品？为什么？ \r\n  比较愿意使用，工作上的需要和远方家人联系的需要。\r\n\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n 应设立多个等级，不同等级价格来满足不同需要的人群\r\n\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n20-30元之间','58.248.7.142',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(535,28,169,0,'GZGT01',84,'',1296377332,'1)目前咨询是否方便快捷，对客服人员态度是否满意；\r\n至今为止还是比较满意咨询结果的,还未曾碰到要等待很久才能够解决问题的情况出现.工作人员的服务也很好,都是很专业的客服.\r\n\r\n\r\n2)客服对投拆的反应是否迅速，解决投拆的效果如何，是否满意；\r\n客服对待客户投诉的情况还是很真诚很诚恳的,都是抱着聆听客户需要的态度来帮助我们解决困难,反馈后的处理结果也很令人满意.\r\n\r\n\r\n3)对目前咨询、投拆流程是否感到满意，流程哪些地方需要改进；\r\n比较满意,处理速度也比较快.希望可以继续保持.\r\n\r\n\r\n4)是否觉得要区分普通客服和VIP客服。\r\n需要,这样可以使工作效率有所提高.','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(536,29,178,0,'GZIMS02',102,'',1296377340,'月30日主题三：高清视频通信主要功能评价\r\n（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。 \r\n   i.你为什么最喜欢这3个功能？ \r\n说真,没有什么可以吸引我,现在除了视频和呼叫门户,其他功能也可以在手机上可以执行,不必要再另外在买一台视频手机,除非以后大量普及我才会考虑.\r\n\r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n   i.你为什么觉得这三个功能没有吸引力？ \r\n通讯录/播放相片功能都不太吸引我.\r\n\r\n（3）你还希望增加哪些功能？\r\n便提,有导航作用.','121.33.210.106',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(537,28,160,0,'GZGT03',113,'',1296377396,'用遥控器的，上下左右来选择，再加上确定和取消，6个按键就足够了。\r\n我妈妈老是和我抢电脑打麻将，能在电视上让她玩倒是个好办法。','219.136.9.111',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(538,29,171,0,'NJIMS03',114,'',1296377486,'1）目前主要使用的通讯工具？（固定电话、手机、网上聊天工具）；手机，固定电话。手机使用最多\r\n\r\n\r\n2）您一般使用固定电话主要是和谁联系？：一般使用固定电话和父母公司联系\r\n\r\n\r\n\r\n3）手机呢？：手机一般和老公父母和公司联系\r\n\r\n\r\n\r\n4）网上聊天工具呢？上网用QQ','180.109.148.9',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(539,29,171,0,'NJIMS03',114,'',1296377557,'1)大家听说过视频通话吗？是如何知道的？听说过视频通话，从网上广告知道了\r\n\r\n\r\n\r\n2)大家使用过视频通话吗？目前暂时没有\r\n\r\n\r\n\r\na)如果有： \r\ni.使用过哪种视频通话工具？用来跟哪些人联系？在什么情况下使用？使用频率如何？\r\n\r\n\r\n\r\nii.大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工具还需要哪些改进？\r\n\r\n\r\n\r\n\r\nb)如果没有，为什么没有使用？手机不支持视频通话，最近考虑换手机，使用3D功能，想要用视频通话','180.109.148.9',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(540,28,170,0,'GZGT01',84,'',1296377566,'希望可以制作出属于有自己特色的游戏,因为现有的游戏在很多其他地方都可以玩到类似的.拥有自己特色的游戏自然就可以给予用户不同的新鲜感.\r\n\r\n同时游戏类型也可以有所增加,因为现在还只有不多的游戏类型供用户娱乐,而用户的品位兴趣是多种多样的,不同的游戏类型可以满足不同客户的需要.\r\n\r\n如果配合游戏内容推出一些外置的游戏小道具提供给用户,那会是一个比较不错的设置.','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(541,28,159,0,'GZGT03',113,'',1296377681,'1)比较容易找到\r\n2)有点单调，更新不够快，游戏类别太少，与网络游戏不能有本质上的区别\r\n3)喜欢棋牌、对益智、教育类有兴趣，希望能更加更多家庭性一起参与的游戏\r\n4)每天都用，晚上一般都玩20-30分钟左右。 \r\n5)开始用摇控器觉得有些不方便，手也有点累，如果用摇控杆，可能更好 \r\n6)目前感觉还可以，社区有注册，不过输入不方便，交流内容也很少。\r\n7)还算满意，不过需要增加更多个性化服务吸引用户','219.136.9.111',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(542,29,178,0,'SHIMS02',107,'',1296377700,'1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。\r\n最喜欢的三个功能：1、 视频通信和语音通信。2、具备和手机相同的通讯录功能。3、可作为数码相框循环播放照片或图片。\r\n   i.你为什么最喜欢这3个功能？ \r\n一方便随时视频通话。二记录存储和查找电话号码方便。三兼具了装饰性，很实用。\r\n\r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n1、振铃、语音通话时屏幕自动播放无声广告、图片、视频等流媒体。2、屏幕上显示呼叫门户（本市和小区周边的各类商户的电话和简介），直接点击呼叫。3、通过屏幕进行信息浏览。\r\n\r\ni.你为什么觉得这三个功能没有吸引力？ \r\n一没什么用，干扰通话。二容易不小心触到拨出号码，产生费用（特别是家里有小孩子）。三内容少，速度慢，没有必要的用处。\r\n\r\n（3）你还希望增加哪些功能？ \r\n我希望还能增加录音、截屏等功能','118.242.1.66',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(543,29,174,0,'NJIMS03',114,'',1296377825,'（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ 还是比较喜欢的，功能比较齐全，而且通话时可以看到对方非常方便，对于亲人在外地不能时常回家的人非常有用。\r\n（2） 哪些方面符合你的需要？为什么？ 可以视频通话，非常方便，亲人在外地常年不能回去，这个电话太方便了\r\n（3）你认为其终端（该电话机）的外观如何个？可以如何改进？外观一般，屏幕再大一点比较好','180.109.148.9',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(544,29,166,0,'NJIMS03',114,'',1296377919,'大家好，我叫雪，29岁，公司客服，性格比较外向，喜欢网游，电影等等，很高兴认识大家啊','180.109.148.9',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(545,29,176,0,'NJIMS03',114,'',1296378053,'1)你是否愿意使用这项产品？为什么？ 愿意使用这个产品，因为功能很实用\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？价格当然越便宜越好啊，合理价位 最好能控制在4百以下\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n如果按月收费，希望能在50元以下','180.109.148.9',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(546,28,179,0,'GZGT01',84,'',1296378291,'1.你认为目前ITV游戏社区怎么样？为什么不能吸引你？ \r\n我觉得社区的人气不是太足够,参与讨论的人不是很多.如果内容更加丰富的话相信可以更加吸引人.\r\n\r\n2.希望增加教育益智类哪些游戏？\r\n可以加深亲子间交流的游戏,让小朋友在游戏中学到东西的.\r\n \r\n3.有人提议说ITV的游戏毕竟是通过遥控器操作的，希望难度不要太大。你们认为怎么样？怎样的游戏才算难度不大适合用于ITV游戏呢？ \r\n因为遥控器只有那几个按键可以使用,太大难度的游戏我们是无法完成的.所以如果游戏难度加大的话,可以适当增加一些外置的用具.而现有的益智类,棋牌类游戏我觉得都是适合ITV的.\r\n\r\n4.有人提议说游戏应分别针对小孩、老人、成人、家庭主妇。你们认为如何？那么针对上述这几类人群，应分别设立哪些游戏呢？ \r\n有针对性是好,可是这样的话会出现一人游戏多人旁观的情况出现,毕竟电视只有一部,如果针对上述几类人群设置游戏的话,很难聚合一家人共同的兴趣,无法体会到共同游戏的乐趣.所以我觉得应该多设置一家老少都适合玩的游戏是最好的.\r\n\r\n5.目前体验试验多长，收费如何？希望如何延长？ \r\n目前体验的三个月,48元/月.希望可以通过使用几个月赠送几个月这样让客户有更多时间体会同时会费用上也有所优惠.\r\n\r\n6.客服方面，目前是通过拨打10000号，你们认为怎么样？是否还需要开通其他客服的方式呢？ \r\n我觉得是需要开通其他客服的方式的,因为毕竟这是一个新的东西,有一个独立的客服方式处理它效率会有一点,针对性也强一些.','113.67.26.113',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(547,29,178,0,'NJIMS03',114,'',1296378362,'1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。 \r\n   i.你为什么最喜欢这3个功能？ 1、视频通信和语音通信2、显示呼叫门户3、具备和手机相同的通讯录功能。这三个功能是最实用的功能\r\n（2）请再选出3个你觉得最没有吸引力的功能？ 1、振铃、语音通话时屏幕自动播放无声广告、图片、视频等流媒体。2、通过屏幕进行信息浏览。 3、循环播放照片\r\n\r\n   i.你为什么觉得这三个功能没有吸引力？ 这几项功能用处不大，可有可无\r\n（3）你还希望增加哪些功能？ \r\n希望可以增加录音的功能，实用','180.109.148.9',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(548,29,178,0,'GZIMS03',104,'',1296378885,'（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。\r\n 最喜欢：1.视频通信和语音通信  2.具备与手机相同的通讯录  3.通过屏幕进行信息浏览。\r\n  i.你为什么最喜欢这3个功能？\r\n1.一个与远方联系的桥梁  2. 解决重复输入号码的麻烦  3.及时了解资讯，查询简单资料。\r\n\r\n（2）请再选出3个你觉得最没有吸引力的功能？\r\n1.振铃、语音通话时屏幕自动播放无声广告、图片、视频等流媒体。\r\n2.屏幕上显示呼叫门户（本市和小区周边的各类商户的电话和简介），直接点击呼叫\r\n3.号码百事通\r\ni.你为什么觉得这三个功能没有吸引力？ \r\n1.使用性太低，不太实际\r\n2.114也有这类服务，这功能所产生的不必要的费用。不太实际\r\n3.直接拨打114就有专人服务，没必要增加。\r\n\r\n（3）你还希望增加哪些功能？ \r\n无线WIFI，便携式','58.248.7.142',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(549,29,174,0,'cannymok',74,'',1296382131,'很多人都提到想屏幕大点，能不能具体说说希望有多大呢？','219.137.58.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(550,29,178,0,'cannymok',74,'',1296382524,'[quote] 原帖由 [i]GZIMS02[/i] 于 2011-01-30 16:49 发表 [url=http://bbs.esaying.cn/bbs/redirect.jsp?goto=findpost&pid=536&ptid=178][img]http://bbs.esaying.cn/bbs/images/common/back.gif[/img][/url]月30日主题三：高清视频通信主要功能评价\r\n（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。 \r\n   i.你为什么最喜欢这3个功能？ \r\n说真,没有什么可以吸引我,现在除了视频和呼叫门户,其他功能也可以在手机上可以执行,不必要再另外在买一台视频手机,除非以后大量普及我才会考虑.\r\n\r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n   i.你为什么觉得这三个功能没有吸引力？ \r\n通讯录/播放相片功能都不太吸引我.\r\n\r\n（3）你还希望增加哪些功能？\r\n便提,有导航作用. [/quote]\r\n\r\n为什么通讯录/播放相片不吸引你呢?','219.137.58.20',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(551,29,174,0,'SHIMS03',103,'',1296382549,'（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？\r\n  蛮喜欢的。使用起来方便，功能也比较多，能外地的家人，朋友经常联系。而且高清视频很有真实性.屏幕也够大。\r\n\r\n（2） 哪些方面符合你的需要？为什么？\r\n   首先视频通话就很符合个人的要求，有时候想外地的亲人，还有他们也想我们的时候，无奈没有时间回去，这个就是很好的解决这个问题的办法了。\r\n   还有一个号码百事通个人也蛮喜欢的，这样就不要再到网上去找这些信息了，方便快捷。\r\n\r\n3）你认为其终端（该电话机）的外观如何个？可以如何改进？\r\n   外观中规中矩，应该再时尚一点，譬如颜色什么的。','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(552,29,176,0,'SHIMS03',103,'',1296382905,'1)你是否愿意使用这项产品？为什么？\r\n  愿意使用这项产品。因为使用起来方便，而且功能很多。能够随时视频通话。\r\n\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？\r\n  我认为在600-1000左右可以接受。\r\n\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n50元左右。','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(553,29,178,0,'SHIMS03',103,'',1296384275,'1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。\r\n   最喜欢的1，视频通信和语音通信。2，号码百事通。3，通讯录。\r\n   i.你为什么最喜欢这3个功能？ \r\n   因为视频通话能够解决和家人的相思之苦。\r\n   号码百事通平时使用频率还是蛮高的，所以蛮喜欢的。\r\n   通讯录就是能够很好的储存和查找电话号码。\r\n\r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n   没有吸引力的1，语音通话时屏幕自动播放无声广告，图片等。2，通过屏幕进行信息浏览。3，语音，视频随时切换。\r\n   i.你为什么觉得这三个功能没有吸引力？ \r\n  1，因为你通话的时候播放的这些广告，图片你都没有心思去看的。所以不大实用。\r\n  2，信息浏览我可以网站，相对电脑他的吸引力不大。\r\n  3，这个可有可无的。\r\n\r\n（3）你还希望增加哪些功能？\r\n便携式 。语音导航，录音','220.181.149.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(554,29,174,0,'NJIMS02',81,'',1296384329,'（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n还是蛮喜欢的，因为我还是比较能够接受新的IT技术产品。在现有的通讯设备中，确实有的不能满足我的需求，比如视频通话等等。有了这款高清的视频通信产品应该就能满足这样的视频聊天需求了。\r\n\r\n（2） 哪些方面符合你的需要？为什么？ \r\n它的5-7寸触屏，高清摄像头，稳定的信号基础，简单易懂的操作方式都是我觉得不错的地方。首先5-7寸的屏幕虽说不是很大，但是能够看到对方就可以满足了。其次为触屏，现在这个技术应该不是很难的，就不知道采用的是电阻式触屏还是电容式触屏。第三，高清摄像头，这个我也觉得不错，有了图像了就应该要有好的成像质量，有了高清的摄像头，就不会看到对方是许多小方块组成了。第四，稳定的信号，这个我觉得是基础关键。不能卡，不能成不了像，在成像的过程中影响了通话的质量，等等。那样还不如不要视频功能。最后一点就是简单易懂的操作方式，因为这样的一款IT产品，我觉得家人的使用客户应该是比较多的，对于现在的长辈来说，他们不想去理解更加繁琐的IT产品，简单易懂是他们现在的追求，我觉得这个到是蛮不错的。\r\n\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？\r\n外观如果1-10分，10为最高分的话我想我会给7分，基本上是比较不错的。其他的改进意见，我觉得它的屏可以放大，再放大。但是想想，如果放大到电脑的14寸的话就不怎么好看了，那我觉得可以折叠嘛，折叠起来是6寸，可以展开到14寸。还是那句话，用这样的产品，我会首先买给我父母，爷爷奶奶，外婆外公他们使用，大的屏的好处一是图像大，二就是操作的字体大。','117.89.94.155',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(555,29,176,0,'NJIMS02',81,'',1296384834,'关于高清视频通信产品的价格建议 \r\n(1)你是否愿意使用这项产品？为什么？ \r\n愿意使用，用这样的产品，我会首先买给我父母，爷爷奶奶，外婆外公他们使用。他们在家里想看看儿子，孙子的都可以得到实现。\r\n\r\n\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n作为一部电话我觉得价格跟现在的中低价位的手机差不多就可以了，如：1000元-1500元。因为这款终端需要建立在宽带的基础上，要想使用这样的终端则必须要开通宽带业务，虽然现在对于我们年青人来说宽带业务应该是都有的，但是如果是长辈们使用，他们对于电脑的需求，对于网络世界的需求没有我们这么大，所以导致现在还有许多长辈家庭还没有装宽带业务。所以对于他们来说，要想使用这样的终端就应该需要付两笔的费用，所以这部终端的费用不应太高。或者电信可以推广不同的宽带业务套餐嘛。有了多种选择，选择的人会更多的。\r\n\r\n\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n我觉得合理的月功能费用应该是15-20元月功能费里面有大约600分钟的视频通话时长。','117.89.94.155',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(556,29,178,0,'NJIMS02',81,'',1296385928,'（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。\r\n我最喜欢的3个，1、视频通信和语音通信。2、具备和手机相同的通讯录功能。3、屏幕显示文字即时信息。这三个功能  \r\n   i.你为什么最喜欢这3个功能？\r\n喜欢这三个的原因是前两个是应该要具备的，只是他能过比现在的电话终端能够做的更好，能够有图像上的内容显示。第三个是可以当信息简要预览这样的作用使用。所以我觉得是不错的三个功能。\r\n \r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n没有吸引力的功能，1、振铃、语音通话时屏幕自动播放无声广告、图片、视频等流媒体。2、屏幕上显示呼叫门户（本市和小区周边的各类商户的电话和简介），直接点击呼叫。3、可作为数码像框循环播放照片和图片。\r\n   i.你为什么觉得这三个功能没有吸引力？ \r\n没有吸引力的原因。1，觉得是做广告之嫌，我没有必要在我的自用终端上播放任何我没有自己操作加进去的广告，图片，视频等流媒体。那样强加的我比较讨厌，或者就可能因为这样的原因使我放弃该终端设备。2、我觉得就是个没用的功能，也有强加广告之嫌，也可能因为这样我放弃该终端设备。3、我第一个想法就是不环保，应该很费电，该终端用的是电源还是电池呢，要是电池，肯定几节电池用不了多久就有更换了，如果是电源，那就直接费电了。还有，谁会没事跑电话机旁边去看那个循环播放的照片也，无聊。\r\n\r\n（3）你还希望增加哪些功能？ \r\n可以增加点游戏，字典，翻译，录音，WIFI，截屏，可以跟电脑连接，蓝牙，USB接口，等等等等应用内容，就要像IPad，I phone等等这些终端一样，既然该终端已经有了屏幕，那就往应用方向去做，多放点应用功能软件，应该会吸引更多的用户的。','117.89.94.155',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(557,17,130,0,'NJGT03',115,'',1296390912,'现在E家还有什么新的活动吗？\r\n正好宽带也要到期了，上次是送了ITV和天翼手机，不知道今年有什么活动哦？','221.226.196.53',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(558,12,131,0,'NJGT03',115,'',1296391030,'我觉得应该在营业厅多放一点产品给用户进行体验，因为只有亲身体验和使用了，才能更直观的对产品进行了解，进而去购买使用。','221.226.196.53',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(559,27,175,0,'GZGS03',83,'',1296391251,'1.遊戲內容首先吸引人的眼球，如果第一眼不吸引的話。我想對這個遊戲興趣都不大了。而且遊戲的畫面和顏色都很重要，要搭配很好。玩遊戲最重要是容易明白容易上手。\r\n2.可以聯機對戰\r\n3.比較喜歡益智遊戲，圖案比較可愛的就最好了。\r\n4.遊戲不太複雜就好了，有些所謂的創新就是把遊戲搞的複雜又難理解。\r\n5.開心網能夠跟好友和同學交流，可以互相轉帖。把一些不開心的東西都可以發洩出來','113.106.209.98',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(560,10,129,0,'CDIMS03',100,'',1296392935,'顶个   支持','222.212.214.159',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(561,17,130,0,'CDIMS03',100,'',1296393021,'希望以后可以越办越好吧\r\n\r\n当然实惠最顶力的','222.212.214.159',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(562,12,131,0,'CDIMS03',100,'',1296393170,'尽量多推出体验区\r\n这样大家都能感受到最新的服务','222.212.214.159',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(563,29,176,0,'CDIMS02',110,'',1296401193,'(1)你是否愿意使用这项产品？为什么？ \r\n愿意，特别适合给父母用，现在大家很多都没有跟父母住在一起，但是又常常牵挂，这样就能随时真切的跟父母每日交流了。\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n跟市话比起，每分钟在市话的2-3倍还是能接受的。\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n68元，300分钟，平均就2-3毛钱比较合算','222.183.15.235',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(564,29,171,0,'CDIMS03',100,'',1296401279,'1）大家目前主要使用哪些通讯工具？（固定电话、手机、网上聊天工具）哪个通讯工具使用最多？其次呢？\r\n固定电话和手机， 网上聊天的工具都是每天会需要的缺一不可的，\r\n手机最多，其次就是网上聊天工具和固话了\r\n\r\n\r\n2）您一般使用固定电话主要是和谁联系？\r\n和家人联系， 其次就是朋友最多了\r\n\r\n\r\n3）手机呢？\r\n就是朋友， 因为在外面联系工具很方便的\r\n\r\n\r\n4）网上聊天工具呢？\r\n和朋友啊， 摆摆龙门阵  开心的\r\n\r\n\r\n1)大家听说过视频通话吗？是如何知道的？\r\n听说过的啊， 之前是在电视上了解的 后来有朋友使用就了解了一些\r\n\r\n\r\n2)大家使用过视频通话吗？\r\n我没有了， 我看过我朋友使用过的， 自己到没有实际操作过的\r\n\r\n\r\n\r\n\r\n\r\n\r\nii.大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工具还需要哪些改进？\r\n我觉得挺新鲜的， 也挺方便的， 主要是能看到对方在做什么 我觉得很有意思的\r\n喔到没有使用过 还不知道哪里需要改进的或者满足的\r\n\r\n\r\n\r\n\r\nb)如果没有，为什么没有使用？\r\n因为我身边的朋友只有一个使用， 如果我有这种功能， 但是我没有开通的话 我觉得就不是很方便了','222.212.220.34',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(565,29,176,0,'CDIMS02',110,'',1296401413,'对于刚才回答的更正一下\r\n话机的价格区间200-400之间能接受','222.183.15.235',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(566,29,166,0,'CDIMS03',100,'',1296401433,'大家好， 我叫Jesse， 很高兴能在这个地方和一些有缘的朋友一起讨论 \r\n很开心 也最给力了','222.212.220.34',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(567,29,174,0,'CDIMS03',100,'',1296401643,'（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n很喜欢， 屏幕够大， 很清晰的， 主要是设计的很时尚， 没有不喜欢的\r\n\r\n（2） 哪些方面符合你的需要？为什么？ \r\n就是很大的屏幕\r\n大屏幕看的清晰很爽的\r\n\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？\r\n很好， 颜色也是我非常喜欢的\r\n看似简单了点 但是设计很独特','222.212.220.34',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(568,29,178,0,'CDIMS03',100,'',1296401945,'i.你为什么最喜欢这3个功能？ \r\n视频通话本来也是代表着一个科技先进的因素， 也能直接看到对方通话 直接交流 给人带来了很多的方便\r\n通讯录很强大， 带来了不少的方便\r\n屏幕显示文字即时信息，当信息简要预览这样的作用使用很好\r\n\r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n没有了， 都很喜欢\r\n\r\n（3）你还希望增加哪些功能\r\n蓝牙， 多种语言的翻译','222.212.220.34',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(569,29,178,0,'CDIMS02',110,'',1296402662,'（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。 \r\n   i.你为什么最喜欢这3个功能？ \r\n1、视频通信和语音通信。2、具备和手机相同的通讯录功能。3、屏幕显示文字即时信息\r\n既然是视频电话，肯定最在意就是视频通信，同样语音通信也是视频通信的基础，对于我个人来说我买视频电话就是为家人买的，老年人都是用不来什么拼音，眼睛也不好使，能具备跟手机一样的通讯录，也能更加方便他们与我们联系。有时工作太忙，或者出差的时候，简单的即时信息能简单的告知家人的情况，免得家人担心不停的打电话。\r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n   i.你为什么觉得这三个功能没有吸引力？ \r\n1.语音通话时屏幕自动播放无声广告，图片等  2.通过屏幕进行信息浏览  3.屏幕上显示呼叫门户（本市和小区周边的各类商户的电话和简介）\r\n感觉广告是一种被逼消费的非自愿的推销，特别不喜欢\r\n信息浏览对于老年人太不适合，对于我自己来说我更加喜欢上网浏览\r\n呼叫门户不是很在意，在意的是呼入门户，这样能过滤一些推销或者欺诈电话\r\n\r\n（3）你还希望增加哪些功能？ \r\n触摸，字体大小变化，游戏','222.183.15.235',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(570,29,171,0,'CDIMS01',89,'',1296404574,'1）大家目前主要使用哪些通讯工具？（固定电话、手机、网上聊天工具）哪个通讯工具使用最多？其次呢？\r\n我目前使用的通讯工具是：固定电话、手机、网上聊天、固话相对用得多一点。其次是手机和网上聊天。\r\n2）您一般使用固定电话主要是和谁联系？\r\n固话主要是和父母及亲戚朋友联系\r\n3）手机呢？\r\n手机主要亲朋好友及同事等\r\n4）网上聊天工具呢？\r\n网上聊天主要是老同学、好朋友、工作同事及网上的网友等\r\n下面想向大家了解一些视频通话的一些使用情况（2个问题都需要回答哦，大家记得踊跃回答，每个回答最好标明对应的题号） \r\n\r\n1)大家听说过视频通话吗？是如何知道的？\r\n知道视频通话，网上及媒体报道\r\n2)大家使用过视频通话吗？\r\n固话视频通话没使用过\r\na)如果有： \r\ni.使用过哪种视频通话工具？用来跟哪些人联系？在什么情况下使用？使用频率如何？\r\n网上聊天使用QQ视频通话。用来跟亲戚朋友、老同学等。很久没见面了在聊天的情况下使用。每周2-3次吧\r\nii.大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工具还需要哪些改进？\r\n视频通话用起来挺方便的，基本能满足自己的要求。改进的地方希望网速更快一些这样就不会造成画质卡及声音不顺畅的情况。呵呵！','118.122.85.165',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(571,29,166,0,'CDIMS01',89,'',1296405331,'姓名，年龄，职业？ \r\n大家好！我叫袁顺河，今年34岁，工程师。\r\n性格？\r\n性格比较内向，稳重。\r\n爱好？？\r\n喜欢上网浏览网上新闻、影视、旅游等','118.122.85.165',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(572,29,174,0,'GZIMS01',80,'',1296405841,'（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n喜欢它的高清视频实现零距离就可以跟别人通话,如是不需要的话可以随时关掉.\r\n不喜欢是不能够下载文件和收看动画视频等,刷新速度慢\r\n\r\n（2） 哪些方面符合你的需要？为什么？ \r\n可以跟手机差不多的功能,信号稳定,方便快捷\r\n\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？\r\n屏幕一定要大和清晰度高，外观要时尚和美观，要省电','113.96.114.34',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(573,29,176,0,'GZIMS01',80,'',1296406099,'(1)你是否愿意使用这项产品？为什么？ \r\n愿意，因跟手机的功能差不多\r\n\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n一个月最好不要超过100元，有包月的方式就最好，价格不能太贵，因太贵的话少了很多人用的\r\n\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n一个月最好就是50元左右，最好就有几个套餐可以选择','113.96.114.34',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(574,29,178,0,'GZIMS01',80,'',1296406834,'（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。 \r\n   i.你为什么最喜欢这3个功能？\r\n 1。3和4是我最喜欢的\r\n因有高清的视频，可以随时关闭视频，可以帮我计算话费，帮我计算所用的话费的余额，让人收费明白\r\n（2）请再选出3个你觉得最没有吸引力的功能？\r\n2。6和9 \r\n   i.你为什么觉得这三个功能没有吸引力？ \r\n因不想看太多的广告，循环看相片这个功能可有可无，因打电话时才会用电话，平时会上电脑上看，号码百事通这个可以在网上查找\r\n（3）你还希望增加哪些功能？ \r\n不要断网就可以了，通话和视频一定要清晰','113.96.114.34',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(575,29,174,0,'CDIMS01',89,'',1296407743,'1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n很喜欢。看上去屏幕特别大，视频通话时视野更开阔更清晰。不太喜欢的就是看上去像传真机。\r\n（2） 哪些方面符合你的需要？为什么？ \r\n产品功能很全面，很多功能与手机功能一样，挺好的。\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？ \r\n外观最好再时尚美观一点。希望在视频通话时清晰度高及流畅。','118.122.85.165',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(576,29,176,0,'CDIMS01',89,'',1296408542,'(1)你是否愿意使用这项产品？为什么？ \r\n  感觉挺好的，还是愿意使用此产品，与父母通话时可以视频相互看到对方，彼此拉近我们之间的距离。\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n从产品功能上看应该不会很便宜，区间价格在500-800之间可以接收\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？ \r\n30-50元之间','118.122.85.165',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(577,29,176,0,'SHIMS01',109,'视频通信 期待中',1296429878,'关于高清视频通信产品的价格建议 \r\n(1)你是否愿意使用这项产品？为什么？ \r\n愿意使用这项产品，也非常期待哦\r\n至于原因1、本人非常乐意了解、接受新科技带来的方便；2、可以和外地的老妈通过银屏喊话了，哈哈\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n为了此项业务的长期、稳定发展，运营商有合理的利润是必须的\r\n我认为价格区间包括：1、月租费 此项费用月50元/月；2、视频通过费1元/分钟，按实际时长计费\r\n呵呵，以上运营商不会亏损吧\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，我觉得合理的月功能费大约在每月50-100元左右，可以接受。','122.225.10.239',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(578,29,178,0,'SHIMS01',109,'',1296431828,'1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。 \r\n最喜欢的三个功能：1、 视频通信和语音通信。2、可作为数码相框循环播放照片或图片。 3.通过屏幕进行信息浏览\r\n   i.你为什么最喜欢这3个功能？ \r\n    视频通信很期待，很给力，这是首要。二这项功能很吸引我，因为我家宝宝肯定很期待，天天她的照片循环播放，她一定乐坏了；3的功能可以信息浏览，查询信息，这个我喜欢。 \r\n\r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n1、振铃、语音通话时屏幕自动播放无声广告、图片、视频等流媒体。2、屏幕上显示呼叫门户（本市和小区周边的各类商户的电话和简介），直接点击呼叫。3，号码百事通\r\ni.你为什么觉得这三个功能没有吸引力？ \r\n    一没必要吧，干扰通话。二三都觉得没必要，我喜欢在手机存放呼叫号码，移动办公及朋友联系了。\r\n\r\n\r\n（3）你还希望增加哪些功能？ \r\n     希望增加录音、截屏等功能','122.225.10.239',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(579,29,176,0,'SHIMS01',109,'昨晚单位聚会还提起 哈哈',1296432033,'昨天单位聚会，某君中了个三奖，IPHONE\r\n他说最好在家给他装视频，这样他在家就可以参加会议了 理想想象中\r\n聚会时开心的，回来差点忘了上BBS了\r\n大家讨论好热闹','122.225.10.239',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(580,29,174,0,'SHIMS01',109,'喜欢不喜欢 顾客说了算 给力',1296432248,'（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？ \r\n  喜欢高清视频能实现面对面与别人通话,真是世界近在咫尺呀 \r\n    不喜欢刷新速度慢 \r\n\r\n（2） 哪些方面符合你的需要？为什么？ \r\n    信号稳定,方便快捷，易于操作\r\n\r\n（3）你认为其终端（该电话机）的外观如何？可以如何改进？ \r\n    屏幕要大，清晰度要高，外观时尚美观，最好超薄的\r\n哈哈 好难哦','122.225.10.239',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(581,28,165,0,'GZGT02',94,'',1296433443,'（1）以下几种付费渠道，你最喜欢哪两种？为什么？ （B和C，因为方便安全，不怕出现乱收费。）\r\na)IPTV宽带代收 \r\nb)网上银行、 \r\nc)付宝 \r\nd)手机短信进行扣费 \r\n\r\n（2）你们还喜欢哪些付费渠道？（ 打电话划扣，通过打客服电话在电话账户预付费用，到时月结话费一起扣除  ） \r\n\r\n（3）如果设虚拟币，以点卡形式销售，大家有兴趣购买吗？为什么？ （没兴趣，不方便，要专程去购买，太麻烦。）\r\n\r\n（4）以下两种收费形式，你最喜欢哪种？为什么？ （B，因为我喜欢的游戏才会去玩，即使有很多免费的游戏，对我来说没意思呢。）\r\na)包年/包月形式（IPTV游戏平台有免费游戏，但部分精品游戏用户不能免费玩，需要通过包年/包月形式定购） \r\nb)按次收费（玩一次游戏收一次费） \r\n\r\n（5）你对这两种收费形式可接受的价格分别是多少？ \r\na)包年/包月形式：（ 200 ）元/年；（  20 ）元/月 \r\nb)按次收费：（ 0.5  ）元/次 \r\n\r\n（6）用户需要获得更多的经验、积分值或者得到道具更加个性化、更具优势玩IPTV，此时用户可以购买道具，道具是按次收费。你希望每次的收费是怎样的？（ 0.5 ）元/次','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(582,28,170,0,'GZGT02',94,'',1296434638,'现在偷菜，拍车，漂流瓶这么流行，建议可以增加这类游戏。','121.201.254.252',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(583,27,156,0,'NJGS02',92,'',1296447314,'姓名  张磊 \r\n年龄  24岁\r\n职业  技术人员\r\n性格  活泼开朗 \r\n爱好  上上网，打打游戏啊 看电影 听音乐','121.229.200.178',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(584,27,175,0,'GZGS02',93,'',1296449993,'1．你们认为什么才是有技术含量的游戏？ 玩家的操控率占95%以上，道具，辅助品占5%，毕竟技术含量，要不然那里来的一些RMB战士呢，而且又不强。\r\n2．怎样的游戏才是互动性强？可以在网吧的地方，一起玩起来乱叫乱吼的。 \r\n3．什么游戏才对你们有吸引力？ 能和朋友一起玩的游戏\r\n4．怎样的新的游戏社区才是真正创新的，有吸引力的？ 能令到不同年龄层次都会玩的游戏。（我身边有个朋友，我每天帮他老公，儿子，女儿偷菜的。）\r\n5．大家认为开心网对你们最有吸引力的地方是什么？可以在认识到不同的人，和找到以前的同学，朋友。','113.111.45.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(585,27,175,0,'GZGS01',77,'',1296450918,'1．你们认为什么才是有技术含量的游戏？\r\n   在游戏里面，玩家用意识，操控等一方面和玩家有关都是技术含量。不只是靠RMB完全辅助。我玩游戏的时候，经常遇到一些冲了很多钱的玩家，但技术确实不怎么。就好像你玩QQ的斗地主，冲了个蓝钻又不会玩斗地主。还进房就乱出牌，乱T人。 \r\n2．怎样的游戏才是互动性强？ \r\n   有很多有意义的活动节目的游戏，互动性强\r\n3．什么游戏才对你们有吸引力？ 动作，科幻，古典。视觉强，自由度高。\r\n4．怎样的新的游戏社区才是真正创新的，有吸引力的？ 能够通过社区游戏来赚取现实金钱的游戏，这又创新，又有吸引力。\r\n5．大家认为开心网对你们最有吸引力的地方是什么？ \r\n    开心网吸引我的地方只是一开始还没有这样的社区的时候，觉得挺有意思。但现在越来越多。也很少去了','113.111.45.116',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(586,27,175,0,'SHGS01',69,'',1296453886,'1）画面，趣味性，操作 ，耐玩\r\n2）互动性 就是有些游戏一个人是无法完成任务的 那时候就需要朋友的的帮助了 这样的话朋友叫朋友一个叫一个越叫越多 \r\n3）能和朋友一起玩的游戏 \r\n4）能令到不同年龄层次都会玩的游戏\r\n5）可以找到以前的朋友，认识到不同的人，获取很多宝贵的信息','180.155.183.174',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(587,28,169,0,'GZGT02',94,'',1296457185,'1)目前咨询是否方便快捷，对客服人员态度是否满意；\r\n\r\n咨询尚算方便快捷, 对客服人员的态度基本满意, 但是还有提高的空间, 有碰过情绪不好,而显不耐烦的客服\r\n\r\n2)客服对投拆的反应是否迅速，解决投拆的效果如何，是否满意；\r\n\r\n暂时没有投诉的经历, 不发表任何的意见\r\n\r\n3)对目前咨询、投拆流程是否感到满意，流程哪些地方需要改进；\r\n\r\n咨询上面基本满意, 投诉没有经验; 流程上面需要改进的是: 对客服人员增加心态上的培训, 不要把私人情绪带到工作. \r\n\r\n4)是否觉得要区分普通客服和VIP客服。\r\n\r\n需要, VIP应该有独立的专线, 让vip客户更显尊贵','219.137.129.14',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(588,28,160,0,'GZGT02',94,'',1296457317,'游戏控制上,希望出一些专门的游戏手柄','219.137.129.14',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(589,27,164,0,'NJGS02',92,'',1296458000,'1，蛮不错的， 单机和联网游戏都有，如角色扮演、动作格斗、体育竞技、射击飞行、策略经营、益智休闲、棋牌、冒险推理等多个类别。游戏类型很丰富！可以在各种游戏里跳出自己喜欢的类型游戏！\r\n2，看了之后感觉有几条还是不错的，比如 2 3 4 5 6 7 9 10 朋友与朋友之间可以有更多的交流 看到些热门话题 大家可以一起讨论对游戏的看法 一起玩的时候怎么能做到配合默契\r\n3，很有兴趣 可以免费的获得游戏里的一些物品 药品 装备什么的 是一个吸引玩家的一个不不错的策略！增加人气的一个好办法！\r\na，非常好   积分换东西这个活动值得推崇 游戏无非是娱乐 搞些活动积分 吸引人气\r\n4，会使用 本人平时比较喜欢和朋友家人一起聊天 或者分享彼此之间的快乐 这样就更拉近了彼此的距离 \r\n5）会 至于什么时候玩还是看到时有没有空 或者心情吧','117.88.172.96',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(590,28,159,0,'GZGT02',94,'',1296458086,'1)IPTV平台中是否可以方便找到IPTV游戏频道，如果不方便，你觉得如何进入IPTV游戏频道是方便的； \r\n方便,放在首页很清晰.\r\n\r\n2)对目前IPTV游戏内容是否感到满意，是否觉得内容过于单调、简单；\r\n如果没有电脑游戏,玩玩还可以, 可以考虑给小孩子玩玩.\r\n \r\n3)你最钟情于哪类IPTV游戏，棋牌、益智、教育类等；如果要增加游戏内容，建议增加哪些类型的游戏，如角色扮演、教育益智、飞行射击等；\r\n中意棋牌类游戏, 建议增加教育益智类游戏.\r\n \r\n4)你一般使用IPTV使用时间有多长，玩IPTV游戏时间有多长； \r\n很少玩,玩过几次.\r\n\r\n5)你目前使用遥控器玩IPTV游戏是否习惯，是否觉得遥控器功能过于简单；如果增加相应手柄玩IPTV游戏，是否有兴趣；\r\n觉得简单,不好操作,希望可以增加游戏手柄.\r\n \r\n6)你对目前IPTV社区是否感到满意，是否申请注册，觉得社区哪些方面需要改进，如社区交友可以增加哪些功能； \r\n没有注册,社区交友是不错的,但是难以用文字交流\r\n\r\n7)你是否满意目前IPTV游戏的用户体验，建议哪些用户体验功能可以改进和更加细化。\r\n一般,可以游戏游戏演示.','219.137.129.14',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(591,28,179,0,'GZGT02',94,'',1296458779,'3.有人提议说ITV的游戏毕竟是通过遥控器操作的，希望难度不要太大。你们认为怎么样？怎样的游戏才算难度不大适合用于ITV游戏呢？ \r\n其实也不是遥控就难操作,我玩手机游戏还不是一样,主要是控制键能安排更加合理一样能玩.\r\n\r\n\r\n6.客服方面，目前是通过拨打10000号，你们认为怎么样？是否还需要开通其他客服的方式呢？ \r\n除了客服电话,最好可以开通QQ群,网上客服,可以供大家咨询交流.','219.137.129.14',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(592,27,175,0,'NJGS02',92,'',1296459252,'1，游戏就是用来娱乐休闲的，容易上手让大家都很喜欢，，如格斗类的 靠的就是手法 ！但我不喜欢用RMB砸出来的技术，很多游戏要想厉害就得花钱，我觉得游戏本身就是娱乐花钱就是没意义了，而且这样的游戏也不耐玩，逼近穷人多过有钱人！\r\n2，如果要互动性强就需要游戏能吸引各种人群一起玩，那么肯定要是比较欢快的大型游戏或者就是要共同协作通关的战略游戏。 \r\n3，我个人喜欢休闲类如 连连看 QQ跑的快 欢乐斗地主，同时有可爱画面的游戏，如：盛大的泡泡堂，跑跑卡丁车，QQ堂  QQ农牧场\r\n4，新的游戏社区一定要有新的内容，是一个与家人、朋友、同学，同事，游戏好友一块玩、，可以通过游戏认识更多人，大家一起交流游戏。 \r\n5，我喜欢开心网，游戏可以给人带来好的心情，和好朋友们一起玩 人多了互动多了，配合好了自然游戏也就能玩的持久了！','117.88.172.96',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(593,27,173,0,'NJGS02',92,'',1296459600,'游戏不但可以让人心情娱快 也是一个很好的交朋友途径，可以增加语音功能 可以忍 玩家的配合更默契！','117.88.172.96',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(594,27,173,0,'NJGS02',92,'',1296459626,'游戏不但可以让人心情娱快 也是一个很好的交朋友途径，可以增加语音功能 可以让 玩家的配合更默契！','117.88.172.96',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(595,27,172,0,'NJGS02',92,'',1296459726,'愿意支付的 20-30 当然支付宝  网银 都可以','117.88.172.96',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(596,28,179,0,'SHGT02',73,'',1296466718,'1.游戏社区都是小游戏,能加入点大作或体感游戏就太棒了.\r\n2.可以分几个阶段,如0-3岁 3-7岁 7-12岁........教育益智类应该增对每个阶段做出每个阶段的教育益智类.我认为这类是iptv努力发展的重点.游戏类型应该具有互动交流.让小孩子看了喜欢玩和有启发的.(这里更应该有想wii一样的体感操作).\r\n3.我认为这不是问题,用体感什么游戏都迎刃而解.什么类型的游戏就都适宜啦.看看wii就知道啦.还有最好体感操作器要带电视基本功能.这样一个手摈就都搞定啦.老少都能用\r\n4.分区那是一定要有的.毕竟小孩的游戏成人都不太要玩的.老人可以玩点棋牌.成人可以玩点动作和rpg.女人可以玩点偷菜类的.小孩可以玩点启发类的.动动脑动动手的.\r\n5.厂商一定要做出优秀的游戏这是第一不然不用钱都没人玩.你们可以象网游一样,设个等几,如0---30级不要钱,吸引玩家.把他们叼进来,以后有的是钱.哈哈\r\n6这个到不是最重要的.但应该分一个vip和普通用户','58.218.204.225',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(597,28,179,0,'SHGT03',101,'',1296474765,'1、个人觉得IPTV的游戏还是不错的，只是希望增加点角色类的，可能更容易吸引玩家\r\n2、一些关于英文的，可以帮助小朋友更早学习英文\r\n3、总觉得用遥控玩游戏很奇怪，不打习惯最好是有手柄的那种就好了\r\n4、个人觉得，最好是增加一点可以全家总动员的游戏一来比较有新意，二来也可以增加家庭人员之间的感情\r\n5、收费希望越实惠越好，试玩的时间也希望可以在长一些\r\n6、我觉得10000号很好，已经习惯','222.71.50.43',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(598,29,176,0,'NJIMS01',112,'',1296479821,'关于高清视频通信产品的价格建议 \r\n(1)你是否愿意使用这项产品？为什么？ \r\n愿意使用,因为和我有密切的关系,方便快捷,即时通迅,很好\r\n(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？ \r\n其实,我觉得和宽带一样,使用就免费赠送,这样挺好,如果成本不是很高的话.\r\n(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？\r\n不超过100吧,因为现在网络也十分发达,便宜了,','114.221.61.26',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(599,29,178,0,'NJIMS01',112,'',1296480295,'（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。 \r\n   i.你为什么最喜欢这3个功能？ 1,视频通信与语音通迅,2屏蔽上显示呼叫门户,3,可作为数码相框使用\r\n（2）请再选出3个你觉得最没有吸引力的功能？ \r\n   i.你为什么觉得这三个功能没有吸引力？ \r\n1,语音通话时屏幕自动播放无声广告，图片等,就是对广告有点烦感,别的功能觉得都还好,可以有,\r\n（3）你还希望增加哪些功能？ \r\n定时问候提醒,比如,家里人的生日呀,提前设置好,就自动定时打过去了,这样不要太好.','114.221.61.26',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(600,29,171,0,'NJIMS01',112,'',1296480688,'1,手机最多,其次固话,互联网\r\n2,家里人,父母,远方的亲戚.\r\n3.是客户,用手机最多\r\n4,QQ,使用较多\r\n\r\n2)大家使用过视频通话吗？\r\n有的,\r\na)如果有： \r\ni.使用过哪种视频通话工具？用来跟哪些人联系？在什么情况下使用？使用频率如何？\r\n网上QQ,爸妈联系最多,还有姐妹,一般在过年过节的时候,特想见面,不是很高,\r\nii.大家觉得该视频通话工具使用起来方便吗？是否能够满足您的需求？您觉得现在的视频通话工具还需要哪些改进？\r\n方便,可以满足需求了,费用要少,使用要方便,','114.221.61.26',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(601,28,179,0,'NJGT02',108,'',1296528281,'1.你认为目前ITV游戏社区怎么样？为什么不能吸\r\nj觉得一般   内容不是很丰富  画面不精细 \r\n2.希望增加教育益智类哪些游戏？\r\n是的   这个我比较喜欢 \r\n3.有人提议说ITV的游戏毕竟是通过遥控器操作的，希望难度不要太大。你们认为怎么样？怎样的游戏才算难度不大适合用于ITV游戏呢？\r\n益智类的可以  ，    可以考虑送手柄一类的东西 \r\n4.有人提议说游戏应分别针对小孩、老人、成人、家庭主妇。你们认为如何？那么针对上述这几类人群，应分别设立哪些游戏呢？\r\n小孩可以用学习类的\r\n老人可以棋牌类的\r\n成人可以角色扮演类的\r\n家庭主妇可以考虑知识类的 \r\n\r\n5.目前体验试验多长，收费如何？希望如何延长？\r\n体验两个星期左右， 从开始试玩时候算起 \r\n6.客服方面，目前是通过拨打10000号，你们认为怎么样？\r\n感觉还好  有短信客服可以考虑的','180.111.114.100',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(602,28,179,0,'NJGT01',106,'',1296528590,'1.你认为目前ITV游戏社区怎么样？为什么不能吸引你？ \r\n答：感觉都是小游戏，分类感不强。\r\n2.希望增加教育益智类哪些游戏？\r\n答： 可以分几个阶段,如0-3岁 3-7岁 7-12岁........教育益智类应该增对每个阶段做出每个阶段的教育益智类.\r\n3.有人提议说ITV的游戏毕竟是通过遥控器操作的，希望难度不要太大。你们认为怎么样？怎样的游戏才算难度不大适合用于ITV游戏呢？ \r\n答：可以配手柄啊，得有分类！不同的人有不同的需求！\r\n4.有人提议说游戏应分别针对小孩、老人、成人、家庭主妇。你们认为如何？那么针对上述这几类人群，应分别设立哪些游戏呢？ \r\n答：我认为可以。孩子的可以设置一些寓教于乐的，老人可以是一些简单容易上手的，主妇可以是一些连续性强的游戏。\r\n5.目前体验试验多长，收费如何？希望如何延长？ \r\n答：收费希望越实惠越好，试玩的时间也希望可以在长一些\r\n6.客服方面，目前是通过拨打10000号，你们认为怎么样？是否还需要开通其他客服的方式呢？ \r\n答：我觉得可以了，可以根据游戏的关注度来发展配套设施。','58.213.141.115',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(603,28,159,0,'NJGT03',115,'',1296528614,'1-在首页明显，很容易就找的到 \r\n2-很有兴趣 \r\n3-个人觉得可以增加类似于开心网偷菜类的游戏，增加互动 \r\n4-IPTV，一般一直都会看电影，游戏偶尔会玩，每天都会进去玩玩\r\n5-不喜欢用遥控器玩游戏，总觉得很不搭调，而且老是按错，建议可以改成可以通过付费购买的无线手柄类控制器，或者类似于WIF那种 \r\n6-我对IPTV社区，还是比较陌生一点，总觉得有点奇怪，兴趣不是很大，不太能吸引到我 \r\n7-感觉一般，没多大兴趣','58.213.142.112',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(604,28,160,0,'NJGT03',115,'',1296528764,'感觉用遥制器玩游戏很不习惯，建议改进。','58.213.142.112',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(605,28,169,0,'NJGT03',115,'',1296528946,'1、有些客服态度很好，非常有耐心，但是有遇过不能解决问题的情况，比较郁闷，建议加强学习。\r\n2、接听时间很迅速，大部分问题都可以解决。\r\n3、流程也很人性化，比较好。\r\n4、支持分开普通区和VIP区，让人有种花钱值得的感觉。','58.213.142.112',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(606,28,170,0,'NJGT03',115,'',1296529052,'建议多弄点类似于偷种、抢车位之类的游戏，或者是像沙罗曼蛇类的小游戏或者射击类游戏\r\n建议改进遥控器型的控制器','58.213.142.112',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(607,28,165,0,'NJGT03',115,'',1296529388,'1、支付宝 这个现在比较流行，也方便．也不太会被骗． \r\n2、网银或者PTV柜台支付\r\n3、没兴趣 \r\n4、包月的方式最好，这样一次付完不用担心是否超资，并且我觉得包月最好，如果包年的话太长了 \r\n5、200一年　30块一月　2元一次 \r\n6、1元','58.213.142.112',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(608,29,166,0,'NJIMS01',112,'',1296530526,'大家好 我是小王,年龄32, 在一家公司做财务\r\n喜欢上网 打打游戏,看看电影,爱好开车出去旅游 ,比较喜欢IT\r\n认识大家,是很开心的事,大家要一起多多交流呀','58.212.113.249',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL),(609,28,179,0,'SHGT01',72,'',1297067899,'1.你认为目前ITV游戏社区怎么样？为什么不能吸引你？\r\n因为游戏过于简单，单调，美工也显得很幼稚，吸引不了14岁以上的消费群。\r\n \r\n2.希望增加教育益智类哪些游戏？\r\n教育益智类主要针对学前教育阶段或小学初级教育阶段，比如童话故事学单词，成语接龙什么的。\r\n \r\n3.有人提议说ITV的游戏毕竟是通过遥控器操作的，希望难度不要太大。你们认为怎么样？怎样的游戏才算难度不大适合用于ITV游戏呢？\r\n使用遥控器控制游戏确实不方便。一般游戏可以选择手柄或者鼠标键盘操作，比较适合。\r\n \r\n4.有人提议说游戏应分别针对小孩、老人、成人、家庭主妇。你们认为如何？那么针对上述这几类人群，应分别设立哪些游戏呢？ \r\n应该分类。针对小孩可以安排设置一些简单的智力拓展游戏，或者说故事等等。成人主要喜欢通过游戏平台，玩一些刺激，操作感强的游戏。家庭主妇可以对于棋牌类比较热衷。而老人则喜欢象棋，围棋等，\r\n\r\n5.目前体验试验多长，收费如何？希望如何延长？\r\n目前好像是48元/月。我建议初期不要收费，使用半收费模式，比如游戏免费，道具收费的方法，以吸引消费者为首要目的。\r\n \r\n6.客服方面，目前是通过拨打10000号，你们认为怎么样？是否还需要开通其他客服的方式呢？ \r\n拨打10000已经足够，可以增加网络客服，多一个网络的渠道可以更方便。','116.234.53.30',0,-1,0,-1,-1,-1,-1,0,0,0,0,NULL);

/*Table structure for table `jrun_present` */

DROP TABLE IF EXISTS `jrun_present`;

CREATE TABLE `jrun_present` (
  `id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `presenturl` varchar(255) NOT NULL,
  `creattime` int(11) NOT NULL,
  `creator` mediumint(8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_present` */

insert  into `jrun_present`(`id`,`name`,`message`,`presenturl`,`creattime`,`creator`) values (1,'生日蛋糕','祝朋友生日快乐','attachments/day_100919/201009195a44efdfddb2a178e0594c29a6287382.gif',1284605895,1),(2,'银联家庭终端','银联家庭终端','attachments/day_100919/20100919da5f664f044e800a780ac847ca25743a.gif',1284607539,1),(3,'玫瑰','玫瑰花','attachments/day_100919/201009196f9dcdebd1a0b13efc3f5bbc31b90fd8.gif',1284607539,1),(4,'哈根达斯月饼','哈根达斯月饼','attachments/day_100919/20100919d049512a393c8dfd7930a509782a6ae1.gif',1284607539,1),(5,'红包','红包','attachments/day_100919/201009197219f73e41e50a9cd13107531f70f757.gif',1284711390,1),(6,'钻石','有钱人','attachments/day_100919/201009192468c389a0c3ba3e992f79fed9df1797.gif',1284711427,1),(7,'香槟酒','好喝的酒啊','attachments/day_100919/201009193d71e9b40a7a1f5a60dfa7e21187cc37.gif',1284711451,1),(8,'金苹果','值钱','attachments/day_100919/201009198f6cb15ccd426c4d93cd442dffba459e.gif',1284711479,1),(9,'玩具熊','拿去花','attachments/day_100919/201009198a28eb4c76aa6e4c7c2ab61256de711d.gif',1284711497,1),(10,'幸运天使','很美','attachments/day_100919/2010091981dc61f4c80d31e21d30f71dadecdaec.gif',1284711523,1);

/*Table structure for table `jrun_profilefields` */

DROP TABLE IF EXISTS `jrun_profilefields`;

CREATE TABLE `jrun_profilefields` (
  `fieldid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `invisible` tinyint(1) NOT NULL DEFAULT '0',
  `title` varchar(50) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `size` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `displayorder` smallint(6) NOT NULL DEFAULT '0',
  `required` tinyint(1) NOT NULL DEFAULT '0',
  `unchangeable` tinyint(1) NOT NULL DEFAULT '0',
  `showinthread` tinyint(1) NOT NULL DEFAULT '0',
  `selective` tinyint(1) NOT NULL DEFAULT '0',
  `choices` text NOT NULL,
  PRIMARY KEY (`fieldid`),
  KEY `available` (`available`,`required`,`displayorder`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_profilefields` */

/*Table structure for table `jrun_projects` */

DROP TABLE IF EXISTS `jrun_projects`;

CREATE TABLE `jrun_projects` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type` varchar(10) NOT NULL,
  `description` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`type`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_projects` */

insert  into `jrun_projects`(`id`,`name`,`type`,`description`,`value`) values (1,'技术性论坛','extcredit','如果您不希望会员通过灌水、页面访问等方式得到积分，而是需要发布一些技术性的帖子获得积分。','a:4:{s:10:\"savemethod\";a:2:{i:0;s:1:\"1\";i:1;s:1:\"2\";}s:14:\"creditsformula\";s:49:\"posts*0.5+digestposts*5+extcredits1*2+extcredits2\";s:13:\"creditspolicy\";s:299:\"a:12:{s:4:\"post\";a:0:{}s:5:\"reply\";a:0:{}s:6:\"digest\";a:1:{i:1;i:10;}s:10:\"postattach\";a:0:{}s:9:\"getattach\";a:0:{}s:2:\"pm\";a:0:{}s:6:\"search\";a:0:{}s:15:\"promotion_visit\";a:1:{i:3;i:2;}s:18:\"promotion_register\";a:1:{i:3;i:2;}s:13:\"tradefinished\";a:0:{}s:8:\"votepoll\";a:0:{}s:10:\"lowerlimit\";a:0:{}}\";s:10:\"extcredits\";s:1450:\"a:8:{i:1;a:8:{s:5:\"title\";s:6:\"威望\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:2;a:8:{s:5:\"title\";s:6:\"金钱\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:3;a:8:{s:5:\"title\";s:6:\"贡献\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:4;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:5;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:6;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:7;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:8;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}}\";}'),(2,'娱乐性论坛','extcredit','此类型论坛的会员可以通过发布一些评论、回复等获得积分，同时扩大论坛的访问量。更重要的是希望会员发布一些有价值的娱乐新闻等。','a:4:{s:10:\"savemethod\";a:2:{i:0;s:1:\"1\";i:1;s:1:\"2\";}s:14:\"creditsformula\";s:81:\"posts+digestposts*5+oltime*5+pageviews/1000+extcredits1*2+extcredits2+extcredits3\";s:13:\"creditspolicy\";s:315:\"a:12:{s:4:\"post\";a:1:{i:1;i:1;}s:5:\"reply\";a:1:{i:2;i:1;}s:6:\"digest\";a:1:{i:1;i:10;}s:10:\"postattach\";a:0:{}s:9:\"getattach\";a:0:{}s:2:\"pm\";a:0:{}s:6:\"search\";a:0:{}s:15:\"promotion_visit\";a:1:{i:3;i:2;}s:18:\"promotion_register\";a:1:{i:3;i:2;}s:13:\"tradefinished\";a:0:{}s:8:\"votepoll\";a:0:{}s:10:\"lowerlimit\";a:0:{}}\";s:10:\"extcredits\";s:1042:\"a:8:{i:1;a:6:{s:5:\"title\";s:6:\"威望\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;}i:2;a:6:{s:5:\"title\";s:6:\"金钱\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;}i:3;a:6:{s:5:\"title\";s:6:\"贡献\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;}i:4;a:6:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;}i:5;a:6:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;}i:6;a:6:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;}i:7;a:6:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;}i:8;a:6:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;}}\";}'),(3,'动漫、摄影类论坛','extcredit','此类型论坛需要更多的图片附件发布给广大会员，因此增加一项扩展积分：魅力。','a:4:{s:10:\"savemethod\";a:2:{i:0;s:1:\"1\";i:1;s:1:\"2\";}s:14:\"creditsformula\";s:86:\"posts+digestposts*2+pageviews/2000+extcredits1*2+extcredits2+extcredits3+extcredits4*3\";s:13:\"creditspolicy\";s:324:\"a:12:{s:4:\"post\";a:1:{i:2;i:1;}s:5:\"reply\";a:0:{}s:6:\"digest\";a:1:{i:1;i:10;}s:10:\"postattach\";a:1:{i:4;i:3;}s:9:\"getattach\";a:1:{i:2;i:-2;}s:2:\"pm\";a:0:{}s:6:\"search\";a:0:{}s:15:\"promotion_visit\";a:1:{i:3;i:2;}s:18:\"promotion_register\";a:1:{i:3;i:2;}s:13:\"tradefinished\";a:0:{}s:8:\"votepoll\";a:0:{}s:10:\"lowerlimit\";a:0:{}}\";s:10:\"extcredits\";s:1462:\"a:8:{i:1;a:8:{s:5:\"title\";s:6:\"威望\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:2;a:8:{s:5:\"title\";s:6:\"金钱\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:3;a:8:{s:5:\"title\";s:6:\"贡献\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:4;a:8:{s:5:\"title\";s:6:\"魅力\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:5;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:6;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:7;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:8;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}}\";}'),(4,'文章、小说类论坛','extcredit','此类型的论坛更重视会员的原创文章或者是转发的文章，因此增加一项扩展积分：文采。','a:4:{s:10:\"savemethod\";a:2:{i:0;s:1:\"1\";i:1;s:1:\"2\";}s:14:\"creditsformula\";s:57:\"posts+digestposts*8+extcredits2+extcredits3+extcredits4*2\";s:13:\"creditspolicy\";s:307:\"a:12:{s:4:\"post\";a:1:{i:2;i:1;}s:5:\"reply\";a:0:{}s:6:\"digest\";a:1:{i:4;i:10;}s:10:\"postattach\";a:0:{}s:9:\"getattach\";a:0:{}s:2:\"pm\";a:0:{}s:6:\"search\";a:0:{}s:15:\"promotion_visit\";a:1:{i:3;i:2;}s:18:\"promotion_register\";a:1:{i:3;i:2;}s:13:\"tradefinished\";a:0:{}s:8:\"votepoll\";a:0:{}s:10:\"lowerlimit\";a:0:{}}\";s:10:\"extcredits\";s:1462:\"a:8:{i:1;a:8:{s:5:\"title\";s:6:\"威望\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:2;a:8:{s:5:\"title\";s:6:\"金钱\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:3;a:8:{s:5:\"title\";s:6:\"贡献\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:4;a:8:{s:5:\"title\";s:6:\"文采\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:5;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:6;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:7;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:8;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}}\";}'),(5,'调研性论坛','extcredit','此类型论坛更期望的是得到会员的建议和意见，主要是通过投票的方式体现会员的建议，因此增加一项积分策略为：参加投票，增加一项扩展积分为：积极性。','a:4:{s:10:\"savemethod\";a:2:{i:0;s:1:\"1\";i:1;s:1:\"2\";}s:14:\"creditsformula\";s:63:\"posts*0.5+digestposts*2+extcredits1*2+extcredits3+extcredits4*2\";s:13:\"creditspolicy\";s:306:\"a:12:{s:4:\"post\";a:0:{}s:5:\"reply\";a:0:{}s:6:\"digest\";a:1:{i:1;i:8;}s:10:\"postattach\";a:0:{}s:9:\"getattach\";a:0:{}s:2:\"pm\";a:0:{}s:6:\"search\";a:0:{}s:15:\"promotion_visit\";a:1:{i:3;i:2;}s:18:\"promotion_register\";a:1:{i:3;i:2;}s:13:\"tradefinished\";a:0:{}s:8:\"votepoll\";a:1:{i:4;i:5;}s:10:\"lowerlimit\";a:0:{}}\";s:10:\"extcredits\";s:1465:\"a:8:{i:1;a:8:{s:5:\"title\";s:6:\"威望\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:2;a:8:{s:5:\"title\";s:6:\"金钱\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:3;a:8:{s:5:\"title\";s:6:\"贡献\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:4;a:8:{s:5:\"title\";s:9:\"积极性\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:5;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:6;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:7;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:8;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}}\";}'),(6,'贸易性论坛','extcredit','此类型论坛更注重的是会员之间的交易，因此使用积分策略：交易成功，增加一项扩展积分：诚信度。','a:4:{s:10:\"savemethod\";a:2:{i:0;s:1:\"1\";i:1;s:1:\"2\";}s:14:\"creditsformula\";s:55:\"posts+digestposts+extcredits1*2+extcredits3+extcredits4\";s:13:\"creditspolicy\";s:306:\"a:12:{s:4:\"post\";a:0:{}s:5:\"reply\";a:0:{}s:6:\"digest\";a:1:{i:1;i:5;}s:10:\"postattach\";a:0:{}s:9:\"getattach\";a:0:{}s:2:\"pm\";a:0:{}s:6:\"search\";a:0:{}s:15:\"promotion_visit\";a:1:{i:3;i:2;}s:18:\"promotion_register\";a:1:{i:3;i:2;}s:13:\"tradefinished\";a:1:{i:4;i:6;}s:8:\"votepoll\";a:0:{}s:10:\"lowerlimit\";a:0:{}}\";s:10:\"extcredits\";s:1465:\"a:8:{i:1;a:8:{s:5:\"title\";s:6:\"威望\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:2;a:8:{s:5:\"title\";s:6:\"金钱\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:3;a:8:{s:5:\"title\";s:6:\"贡献\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:4;a:8:{s:5:\"title\";s:9:\"诚信度\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:5;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:6;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:7;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}i:8;a:8:{s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";s:5:\"ratio\";i:0;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:12:\"showinthread\";N;s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;}}\";}'),(7,'坛内事务类版块','forum','该板块设置了不允许其他模块共享，以及设置了需要很高的权限才能浏览该版块。也适合于保密性高版块。','a:33:{s:7:\"styleid\";s:1:\"0\";s:12:\"allowsmilies\";s:1:\"1\";s:9:\"allowhtml\";s:1:\"0\";s:11:\"allowbbcode\";s:1:\"1\";s:12:\"allowimgcode\";s:1:\"1\";s:14:\"allowanonymous\";s:1:\"0\";s:10:\"allowshare\";s:1:\"0\";s:16:\"allowpostspecial\";s:1:\"0\";s:14:\"alloweditrules\";s:1:\"1\";s:10:\"recyclebin\";s:1:\"1\";s:11:\"modnewposts\";s:1:\"0\";s:6:\"jammer\";s:1:\"0\";s:16:\"disablewatermark\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:9:\"autoclose\";s:1:\"0\";s:12:\"forumcolumns\";s:1:\"0\";s:12:\"threadcaches\";s:2:\"40\";s:16:\"allowpaytoauthor\";s:1:\"0\";s:13:\"alloweditpost\";s:1:\"1\";s:6:\"simple\";s:1:\"0\";s:11:\"postcredits\";s:0:\"\";s:12:\"replycredits\";s:0:\"\";s:16:\"getattachcredits\";s:0:\"\";s:17:\"postattachcredits\";s:0:\"\";s:13:\"digestcredits\";s:0:\"\";s:16:\"attachextensions\";s:0:\"\";s:11:\"threadtypes\";s:0:\"\";s:8:\"viewperm\";s:7:\"	1	2	3	\";s:8:\"postperm\";s:7:\"	1	2	3	\";s:9:\"replyperm\";s:7:\"	1	2	3	\";s:13:\"getattachperm\";s:7:\"	1	2	3	\";s:14:\"postattachperm\";s:7:\"	1	2	3	\";s:16:\"supe_pushsetting\";s:0:\"\";}'),(8,'技术交流类版块','forum','该设置开启了主题缓存系数。其他的权限设置级别较低。','a:33:{s:7:\"styleid\";s:1:\"0\";s:12:\"allowsmilies\";s:1:\"1\";s:9:\"allowhtml\";s:1:\"0\";s:11:\"allowbbcode\";s:1:\"1\";s:12:\"allowimgcode\";s:1:\"1\";s:14:\"allowanonymous\";s:1:\"0\";s:10:\"allowshare\";s:1:\"1\";s:16:\"allowpostspecial\";s:1:\"5\";s:14:\"alloweditrules\";s:1:\"0\";s:10:\"recyclebin\";s:1:\"1\";s:11:\"modnewposts\";s:1:\"0\";s:6:\"jammer\";s:1:\"0\";s:16:\"disablewatermark\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:9:\"autoclose\";s:1:\"0\";s:12:\"forumcolumns\";s:1:\"0\";s:12:\"threadcaches\";s:2:\"40\";s:16:\"allowpaytoauthor\";s:1:\"1\";s:13:\"alloweditpost\";s:1:\"1\";s:6:\"simple\";s:1:\"0\";s:11:\"postcredits\";s:0:\"\";s:12:\"replycredits\";s:0:\"\";s:16:\"getattachcredits\";s:0:\"\";s:17:\"postattachcredits\";s:0:\"\";s:13:\"digestcredits\";s:0:\"\";s:16:\"attachextensions\";s:0:\"\";s:11:\"threadtypes\";s:0:\"\";s:8:\"viewperm\";s:0:\"\";s:8:\"postperm\";s:0:\"\";s:9:\"replyperm\";s:0:\"\";s:13:\"getattachperm\";s:0:\"\";s:14:\"postattachperm\";s:0:\"\";s:16:\"supe_pushsetting\";s:0:\"\";}'),(9,'发布公告类版块','forum','该设置开启了发帖审核，限制了允许发帖的用户组。','a:33:{s:7:\"styleid\";s:1:\"0\";s:12:\"allowsmilies\";s:1:\"1\";s:9:\"allowhtml\";s:1:\"0\";s:11:\"allowbbcode\";s:1:\"1\";s:12:\"allowimgcode\";s:1:\"1\";s:14:\"allowanonymous\";s:1:\"0\";s:10:\"allowshare\";s:1:\"1\";s:16:\"allowpostspecial\";s:1:\"1\";s:14:\"alloweditrules\";s:1:\"0\";s:10:\"recyclebin\";s:1:\"1\";s:11:\"modnewposts\";s:1:\"1\";s:6:\"jammer\";s:1:\"1\";s:16:\"disablewatermark\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:9:\"autoclose\";s:1:\"0\";s:12:\"forumcolumns\";s:1:\"0\";s:12:\"threadcaches\";s:1:\"0\";s:16:\"allowpaytoauthor\";s:1:\"1\";s:13:\"alloweditpost\";s:1:\"0\";s:6:\"simple\";s:1:\"0\";s:11:\"postcredits\";s:0:\"\";s:12:\"replycredits\";s:0:\"\";s:16:\"getattachcredits\";s:0:\"\";s:17:\"postattachcredits\";s:0:\"\";s:13:\"digestcredits\";s:0:\"\";s:16:\"attachextensions\";s:0:\"\";s:11:\"threadtypes\";s:0:\"\";s:8:\"viewperm\";s:0:\"\";s:8:\"postperm\";s:7:\"	1	2	3	\";s:9:\"replyperm\";s:0:\"\";s:13:\"getattachperm\";s:0:\"\";s:14:\"postattachperm\";s:0:\"\";s:16:\"supe_pushsetting\";s:0:\"\";}'),(10,'发起活动类版块','forum','该类型设置里发起主题一个月之后会自动关闭主题。','a:33:{s:7:\"styleid\";s:1:\"0\";s:12:\"allowsmilies\";s:1:\"1\";s:9:\"allowhtml\";s:1:\"0\";s:11:\"allowbbcode\";s:1:\"1\";s:12:\"allowimgcode\";s:1:\"1\";s:14:\"allowanonymous\";s:1:\"0\";s:10:\"allowshare\";s:1:\"1\";s:16:\"allowpostspecial\";s:1:\"9\";s:14:\"alloweditrules\";s:1:\"0\";s:10:\"recyclebin\";s:1:\"1\";s:11:\"modnewposts\";s:1:\"0\";s:6:\"jammer\";s:1:\"0\";s:16:\"disablewatermark\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"1\";s:9:\"autoclose\";s:2:\"30\";s:12:\"forumcolumns\";s:1:\"0\";s:12:\"threadcaches\";s:2:\"40\";s:16:\"allowpaytoauthor\";s:1:\"1\";s:13:\"alloweditpost\";s:1:\"1\";s:6:\"simple\";s:1:\"0\";s:11:\"postcredits\";s:0:\"\";s:12:\"replycredits\";s:0:\"\";s:16:\"getattachcredits\";s:0:\"\";s:17:\"postattachcredits\";s:0:\"\";s:13:\"digestcredits\";s:0:\"\";s:16:\"attachextensions\";s:0:\"\";s:11:\"threadtypes\";s:0:\"\";s:8:\"viewperm\";s:0:\"\";s:8:\"postperm\";s:22:\"	1	2	3	11	12	13	14	15	\";s:9:\"replyperm\";s:0:\"\";s:13:\"getattachperm\";s:0:\"\";s:14:\"postattachperm\";s:0:\"\";s:16:\"supe_pushsetting\";s:0:\"\";}'),(11,'娱乐灌水类版块','forum','该设置了主题缓存系数，开启了所有的特殊主题按钮。','a:33:{s:7:\"styleid\";s:1:\"0\";s:12:\"allowsmilies\";s:1:\"1\";s:9:\"allowhtml\";s:1:\"0\";s:11:\"allowbbcode\";s:1:\"1\";s:12:\"allowimgcode\";s:1:\"1\";s:14:\"allowanonymous\";s:1:\"0\";s:10:\"allowshare\";s:1:\"1\";s:16:\"allowpostspecial\";s:2:\"15\";s:14:\"alloweditrules\";s:1:\"0\";s:10:\"recyclebin\";s:1:\"1\";s:11:\"modnewposts\";s:1:\"0\";s:6:\"jammer\";s:1:\"0\";s:16:\"disablewatermark\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:9:\"autoclose\";s:1:\"0\";s:12:\"forumcolumns\";s:1:\"0\";s:12:\"threadcaches\";s:2:\"40\";s:16:\"allowpaytoauthor\";s:1:\"1\";s:13:\"alloweditpost\";s:1:\"1\";s:6:\"simple\";s:1:\"0\";s:11:\"postcredits\";s:0:\"\";s:12:\"replycredits\";s:0:\"\";s:16:\"getattachcredits\";s:0:\"\";s:17:\"postattachcredits\";s:0:\"\";s:13:\"digestcredits\";s:0:\"\";s:16:\"attachextensions\";s:0:\"\";s:11:\"threadtypes\";s:0:\"\";s:8:\"viewperm\";s:0:\"\";s:8:\"postperm\";s:0:\"\";s:9:\"replyperm\";s:0:\"\";s:13:\"getattachperm\";s:0:\"\";s:14:\"postattachperm\";s:0:\"\";s:16:\"supe_pushsetting\";s:0:\"\";}');

/*Table structure for table `jrun_promotions` */

DROP TABLE IF EXISTS `jrun_promotions`;

CREATE TABLE `jrun_promotions` (
  `ip` char(15) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`ip`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_promotions` */

/*Table structure for table `jrun_ranks` */

DROP TABLE IF EXISTS `jrun_ranks`;

CREATE TABLE `jrun_ranks` (
  `rankid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `ranktitle` varchar(30) NOT NULL DEFAULT '',
  `postshigher` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `stars` tinyint(3) NOT NULL DEFAULT '0',
  `color` varchar(7) NOT NULL DEFAULT '',
  PRIMARY KEY (`rankid`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_ranks` */

insert  into `jrun_ranks`(`rankid`,`ranktitle`,`postshigher`,`stars`,`color`) values (1,'新生入学',0,1,''),(2,'小试牛刀',50,2,''),(3,'实习记者',300,5,''),(4,'自由撰稿人',1000,4,''),(5,'特聘作家',3000,5,'');

/*Table structure for table `jrun_ratelog` */

DROP TABLE IF EXISTS `jrun_ratelog`;

CREATE TABLE `jrun_ratelog` (
  `pid` int(10) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `extcredits` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `score` smallint(6) NOT NULL DEFAULT '0',
  `reason` char(40) NOT NULL DEFAULT '',
  KEY `pid` (`pid`,`dateline`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_ratelog` */

insert  into `jrun_ratelog`(`pid`,`uid`,`username`,`extcredits`,`dateline`,`score`,`reason`) values (15,1,'admin',1,1281075857,10,'我很赞同\r\n');

/*Table structure for table `jrun_regips` */

DROP TABLE IF EXISTS `jrun_regips`;

CREATE TABLE `jrun_regips` (
  `ip` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `count` smallint(6) NOT NULL DEFAULT '0',
  KEY `ip` (`ip`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_regips` */

insert  into `jrun_regips`(`ip`,`dateline`,`count`) values ('183.33.204.189',1288936640,1),('113.125.185.180',1289981882,1),('183.33.207.142',1290500449,1),('183.33.207.142',1290500538,1),('0.0.0.0',1290500378,1),('183.33.207.142',1290500682,1),('183.33.207.142',1290500754,1),('183.33.207.142',1290500901,1),('183.33.207.142',1290500969,1),('183.33.207.142',1290501314,1),('183.33.207.142',1290501499,1),('183.33.207.142',1290502751,1),('183.33.207.142',1290502776,1),('183.33.205.98',1291779354,1),('183.33.207.137',1291879579,1),('183.33.206.82',1294622942,1),('183.33.207.245',1294887108,1),('183.33.205.198',1295424707,1),('202.104.55.55',1295425756,1),('113.108.150.132',1296009491,1),('183.33.207.20',1296010312,1),('219.137.58.20',1296036632,1),('219.137.58.20',1296036705,1),('61.152.200.34',1296036934,1),('61.152.200.34',1296036974,1),('211.148.4.6',1296037077,1),('211.148.4.6',1296037120,1),('59.42.126.45',1296037699,1),('183.3.66.118',1296046668,1),('113.113.133.196',1296047698,1),('27.115.32.243',1296088356,1),('113.108.150.132',1296092073,1),('183.33.204.55',1296094617,1),('59.42.126.20',1296097236,1),('183.33.204.55',1296097387,1),('211.148.4.6',1296097629,1),('183.33.204.55',1296097753,1),('59.42.126.22',1296099169,1),('211.148.4.6',1296105364,1),('122.225.61.175',1296107269,1),('219.137.58.20',1296109247,1),('59.42.126.47',1296113726,1),('180.155.122.164',1296114716,1),('219.137.58.20',1296116131,1),('59.42.126.21',1296116504,1),('180.111.52.122',1296116614,1),('59.42.126.21',1296116672,1),('180.111.106.137',1296117599,1),('114.84.37.243',1296122929,1),('218.14.17.164',1296131390,1),('113.68.137.80',1296131569,1),('58.248.7.139',1296143223,1),('59.42.126.40',1296176385,1),('59.42.126.20',1296176536,1),('59.42.126.48',1296176799,1),('113.108.150.132',1296182338,1),('219.137.58.20',1296182446,1),('219.137.58.20',1296182485,1),('219.137.58.20',1296182668,1),('59.42.126.23',1296182762,1),('59.42.126.23',1296182826,1),('59.42.126.44',1296182994,1),('59.42.126.23',1296183180,1),('113.108.150.132',1296183435,1),('113.108.150.132',1296183465,1),('113.108.150.132',1296183488,1),('113.108.150.132',1296183547,1),('113.108.150.132',1296183584,1),('59.42.126.44',1296183848,1),('183.33.205.177',1296183923,1),('59.42.126.44',1296184100,1),('182.99.155.144',1296184382,1),('182.99.155.144',1296184796,1),('182.99.155.144',1296184869,1),('182.99.155.144',1296184934,1),('183.33.205.177',1296185343,1),('182.99.155.144',1296185398,1),('183.33.205.177',1296185533,1),('182.99.155.144',1296185644,1),('182.99.155.144',1296186321,1),('182.99.155.144',1296186735,1),('182.99.155.144',1296187076,1),('183.33.205.177',1296187257,1),('125.69.107.196',1296187274,1),('183.33.205.177',1296187634,1),('59.42.126.48',1296187721,1),('59.42.126.47',1296188221,1),('59.42.126.23',1296188589,1),('59.42.126.41',1296190472,1),('59.42.126.23',1296192549,1),('59.42.126.23',1296192624,1),('219.137.58.20',1296196360,1),('219.137.58.20',1296198493,1),('219.137.58.20',1296198612,1),('219.137.58.20',1296198694,1),('61.152.200.34',1296198714,1),('219.137.58.20',1296198717,1),('59.42.126.24',1296198878,1),('60.169.25.116',1296201976,1),('59.42.126.24',1296204126,1),('219.137.58.20',1296204555,1),('183.33.205.177',1296205376,1),('60.169.25.116',1296206954,1),('59.42.126.24',1296207591,1),('183.3.77.29',1296209393,1),('183.3.77.29',1296209397,1),('183.3.77.29',1296209403,1),('180.111.106.160',1296210176,1),('113.96.120.134',1296210195,1),('113.96.120.134',1296210996,1),('113.96.120.134',1296211039,1),('113.96.120.134',1296211062,1),('113.96.120.134',1296211139,1),('113.96.120.134',1296211171,1),('113.96.120.134',1296211215,1),('113.96.120.134',1296211418,1),('116.19.127.122',1296219283,1),('113.96.120.134',1296220149,1),('60.169.25.116',1296221911,1),('222.212.211.94',1296222025,1),('116.19.127.122',1296223015,1),('114.94.200.131',1296224155,1),('180.155.122.164',1296224390,1),('113.96.120.134',1296225902,1),('113.96.120.134',1296225907,1),('180.155.176.196',1296226626,1),('116.28.66.56',1296227404,1),('113.96.120.134',1296227601,1),('59.42.126.46',1296260395,1),('183.5.58.92',1296265885,1),('58.248.7.176',1296270170,1),('60.169.25.116',1296270388,1),('220.181.149.225',1296271097,1),('121.201.254.252',1296272043,1),('58.248.7.163',1296272371,1),('58.248.7.163',1296272754,1),('58.248.7.141',1296273975,1),('218.14.17.169',1296275110,1),('218.14.17.169',1296275630,1),('218.14.17.169',1296275758,1),('116.19.112.166',1296276494,1),('122.193.13.18',1296276820,1),('220.181.149.225',1296277116,1),('116.19.112.166',1296279527,1),('58.213.141.115',1296279941,1),('180.171.176.167',1296280365,1),('220.181.149.225',1296281857,1),('112.80.203.153',1296287324,1),('58.37.98.108',1296288593,1),('180.155.179.123',1296289521,1),('116.237.203.227',1296291488,1),('58.218.204.225',1296291709,1),('183.3.94.73',1296296770,1),('183.3.94.73',1296296777,1),('222.67.224.216',1296297798,1),('180.155.179.123',1296298271,1),('220.181.149.225',1296299206,1),('220.181.149.225',1296302134,1),('113.96.120.134',1296302412,1),('180.155.179.123',1296305593,1),('220.181.149.225',1296305685,1),('58.218.204.235',1296306120,1),('58.218.204.225',1296307626,1),('116.237.203.227',1296307887,1),('60.255.31.52',1296309639,1),('116.237.203.227',1296311180,1),('180.111.105.121',1296312858,1),('113.67.26.113',1296314356,1),('113.96.120.134',1296315090,1),('113.96.120.134',1296315567,1),('113.96.120.134',1296315593,1),('180.111.105.121',1296315741,1),('58.37.146.233',1296318858,1),('59.42.126.27',1296348254,1),('113.108.150.132',1296348745,1),('59.42.126.27',1296348880,1),('59.42.126.46',1296349874,1),('59.42.126.27',1296349980,1),('183.33.205.117',1296350279,1),('183.33.205.117',1296350467,1),('59.42.126.27',1296352755,1),('118.242.1.66',1296355910,1),('222.67.224.216',1296363212,1),('59.42.126.27',1296365144,1),('180.110.35.24',1296365905,1),('180.111.52.26',1296365960,1),('118.242.1.66',1296368723,1),('183.33.205.117',1296370648,1),('183.33.205.117',1296370807,1),('114.84.35.192',1296373169,1),('218.107.6.84',1296373183,1),('113.67.26.113',1296373494,1),('219.137.58.20',1296375164,1),('219.137.58.20',1296375412,1),('113.67.26.113',1296375552,1),('219.136.9.111',1296375606,1),('118.242.1.66',1296376596,1),('180.109.148.9',1296376692,1),('58.248.7.142',1296376737,1),('59.42.126.29',1296378848,1),('180.111.52.26',1296379154,1),('220.181.149.225',1296379939,1),('220.181.149.225',1296380893,1),('218.81.92.237',1296381934,1),('117.89.94.155',1296383224,1),('220.181.149.225',1296388004,1),('117.89.94.155',1296388107,1),('113.67.26.113',1296389660,1),('221.226.196.53',1296390755,1),('116.237.203.227',1296397311,1),('114.222.1.62',1296398114,1),('58.248.7.142',1296398707,1),('220.181.149.225',1296401453,1),('118.122.85.165',1296403541,1),('180.155.125.60',1296405357,1),('121.201.254.252',1296432541,1),('59.42.126.41',1296434796,1),('60.255.31.52',1296437175,1),('58.248.7.185',1296443353,1),('220.181.149.225',1296445244,1),('180.111.30.9',1296445669,1),('114.93.240.206',1296446345,1),('180.111.30.9',1296446988,1),('58.248.193.154',1296447267,1),('113.111.45.116',1296449382,1),('113.111.45.116',1296450072,1),('113.111.45.116',1296450118,1),('58.248.193.154',1296451343,1),('58.248.193.154',1296451395,1),('180.155.183.174',1296453516,1),('59.42.126.41',1296455093,1),('219.137.129.14',1296456575,1),('117.89.10.168',1296458897,1),('58.218.204.225',1296465425,1),('113.68.143.34',1296470691,1),('114.93.240.206',1296471072,1),('116.234.55.115',1296474142,1),('222.71.50.43',1296474402,1),('114.93.240.206',1296476019,1),('180.111.104.119',1296485101,1),('180.111.104.119',1296485468,1),('180.111.104.119',1296485934,1),('180.111.104.119',1296486102,1),('180.111.28.107',1296527471,1),('180.111.28.107',1296527894,1),('114.93.234.162',1296529213,1),('180.111.104.135',1296531941,1),('219.137.58.20',1296532169,1),('180.111.28.107',1296533131,1),('180.111.104.135',1296542858,1),('58.212.100.77',1296564418,1),('180.111.104.149',1296618159,1),('180.111.104.149',1296624174,1),('121.201.254.252',1296658761,1),('116.234.53.30',1297067340,1),('119.131.44.213',1297219109,1),('113.108.150.132',1297240527,1),('180.111.106.216',1297322863,1),('219.136.204.201',1297410039,1),('119.132.75.163',1324348312,1),('119.132.73.101',1324436571,1);

/*Table structure for table `jrun_relatedthreads` */

DROP TABLE IF EXISTS `jrun_relatedthreads`;

CREATE TABLE `jrun_relatedthreads` (
  `tid` mediumint(8) NOT NULL DEFAULT '0',
  `type` enum('general','trade') NOT NULL DEFAULT 'general',
  `expiration` int(10) NOT NULL DEFAULT '0',
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `relatedthreads` text NOT NULL,
  PRIMARY KEY (`tid`,`type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_relatedthreads` */

/*Table structure for table `jrun_rewardlog` */

DROP TABLE IF EXISTS `jrun_rewardlog`;

CREATE TABLE `jrun_rewardlog` (
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `authorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `answererid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned DEFAULT '0',
  `netamount` int(10) unsigned NOT NULL DEFAULT '0',
  KEY `userid` (`authorid`,`answererid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_rewardlog` */

/*Table structure for table `jrun_rsscaches` */

DROP TABLE IF EXISTS `jrun_rsscaches`;

CREATE TABLE `jrun_rsscaches` (
  `lastupdate` int(10) unsigned NOT NULL DEFAULT '0',
  `fid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `forum` char(50) NOT NULL DEFAULT '',
  `author` char(15) NOT NULL DEFAULT '',
  `subject` char(80) NOT NULL DEFAULT '',
  `description` char(255) NOT NULL DEFAULT '',
  UNIQUE KEY `tid` (`tid`),
  KEY `fid` (`fid`,`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_rsscaches` */

insert  into `jrun_rsscaches`(`lastupdate`,`fid`,`tid`,`dateline`,`forum`,`author`,`subject`,`description`) values (1329563226,17,130,1291789639,'电信营业厅','admin','产品体验区隆重上线','产品体验区包括：1、电信天翼手机     提供最新的电信天翼手机咨询体验产品信息。2、我的e家     提供我的e家最新套餐及产品介绍信息。3、商务领航     提供企业商务推荐信息及相关产品介绍。4、爱音乐及189邮箱     爱音乐平台信息及189邮箱介绍。'),(1329563226,27,175,1296361743,'GDT BBS - GS','cannymok','1月30日补充主题','非常感谢大家之前2天对论坛的支持，为我们提供了很宝贵的意见。但我还有几个问题想补充问一下：1．你们认为什么才是有技术含量的游戏？ 2．怎样的游戏才是互动性强？3．什么游戏才对你们有吸引力？4．怎样的新的游戏社区才是真正创新的，有吸引力的？5．大家认为开心网对你们'),(1329563226,27,173,1296307475,'GDT BBS - GS','cannymok','1月29日主题三-给爱游戏社区的建议','聊了2天爱游戏社区，以你们对心目中游戏社区的理解，还有什么好的建议，希望能有什么功能，让你们更有兴趣去使用？			'),(1329563226,27,172,1296307363,'GDT BBS - GS','cannymok','1月29日主题二-付费问题','1）通过以上游戏社区的了解，你是否有兴趣里面的游戏产品，或者购买道具、各种会员服务资格等？愿意付费的金额大概多少？请填写下表：	是否愿意支付？	愿意付费的金额？游戏产品		道具		会员服务资格	2）你希望通过什么形式进行付费，如手机短信扣费、网上银行、支付宝等等？'),(1329563226,27,164,1296271096,'GDT BBS - GS','cannymok','1月29日主题一-给大家介绍个新的游戏平台','昨天咱们已经讨论过平时会玩的游戏平台，今天，我给大家隆重推荐一个新的游戏平台（此处应有掌声~~~~~~~~~~~）爱游戏游戏社区！！！！！！！！它是中国电信爱游戏平台（game.189.cn）下的一个网络社区，主要是整合爱游戏平台下的各款游戏资源的社区平台。由于目前该平台尚未开'),(1329563226,27,163,1296270805,'GDT BBS - GS','cannymok','1月28日主题三-对糖果社区的评价','1）大家这两天都有尝试玩过盛大的糖果社区。你们感觉怎样？2）对糖果社区这样的游戏社区理念，和你平时常玩的游戏平台相比，觉得有什么不同的感受?			'),(1329563226,27,156,1296193246,'GDT BBS - GS','cannymok','1月28日主题一-自我介绍','大家好，欢迎你们参加我们的论坛！   今天由于网络关系（可能靠近春节上网的人多吧），帖子一直发不上来。幸亏咱们工作人员及时抢修了，在这里先给他们敬个礼O(∩_∩)O哈哈~   我先来自我介绍一下吧。 大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢'),(1329563226,27,155,1296193161,'GDT BBS - GS','cannymok','1月28日主题二-游戏社区使用体验','大家要记得踊跃发言哦！每天我们会评出发帖最多的前三名灌水英雄榜，有现金奖励的哦～～～ 但那些纯粹灌水的帖子，例如：沙发，路过啊，我们是不算进去的。 所以，大家多点发有质量、有内容的内涵帖哦！！！！！！ 下面我们来聊聊平时会玩的游戏社区（			'),(1329563226,28,179,1296372640,'GDT BBS - GT','cannymok','1月30日补充主题','首先，非常感谢大家之前2天对我们论坛的支持。下面还有几个问题，希望大家继续探讨，给我们提供宝贵的意见：1.你认为目前ITV游戏社区怎么样？为什么不能吸引你？2.希望增加教育益智类哪些游戏？3.有人提议说ITV的游戏毕竟是通过遥控器操作的，希望难度不要太大。你们认为怎么'),(1329563226,28,170,1296283572,'GDT BBS - GT','cannymok','1.29主题三—大家对IPTV游戏的期望与建议','			'),(1329563226,28,169,1296283339,'GDT BBS - GT','cannymok','1.29主题二—客服咨询、投拆及流程的满意度及改进建议','			'),(1329563226,28,165,1296271766,'GDT BBS - GT','cannymok','1月29日主题一-ITV游戏收费问题','相信大家都遇到过玩游戏要付费的问题，（1）以下几种付费渠道，你最喜欢哪两种？为什么？a)IPTV宽带代收b)网上银行、c)付宝d)手机短信进行扣费（2）你们还喜欢哪些付费渠道？（       ）（3）如果设虚拟币，以点卡形式销售，大家有兴趣购买吗？为什么？（4）以下两种收费形式'),(1329563226,28,160,1296206460,'GDT BBS - GT','cannymok','1月28日主题三－什么是iptv游戏呢','考虑到大家可能对IPTV游戏的概念有些不理解，这里做点补充：'),(1329563226,28,159,1296205679,'GDT BBS - GT','cannymok','1月28日主题二－大家对IPTV游戏产品功能的认知情况','			'),(1329563226,28,157,1296203893,'GDT BBS - GT','cannymok','1月28日主题一-自我介绍','大家好，欢迎你们参加我们的论坛！ 今天由于网络关系（可能靠近春节上网的人多吧），帖子一直发不上来。幸亏咱们工作人员及时抢修了，在这里先给他们敬个礼O(∩_∩)O哈哈~ 我先来自我介绍一下吧。 大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游'),(1329563226,29,178,1296372121,'GDT BBS - IMS','cannymok','1月30日主题三：高清视频通信主要功能评价','（1）对于之前帖子产品介绍里列出来的9项主要功能。请选出3个你最喜欢的。   i.你为什么最喜欢这3个功能？（2）请再选出3个你觉得最没有吸引力的功能？   i.你为什么觉得这三个功能没有吸引力？（3）你还希望增加哪些功能？'),(1329563226,29,176,1296370271,'GDT BBS - IMS','cannymok','1.30专题二：高清视频通信产品的主要功能评价','关于高清视频通信产品的价格建议(1)你是否愿意使用这项产品？为什么？(2)你认为合理的终端（即该电话机）的价格区间应该是怎样的？(3)如果视频通话以月功能费方式收取，其中已经包含一定的视频通话时长，您觉得合理的月功能费区间是怎样的？			'),(1329563226,29,174,1296354012,'GDT BBS - IMS','cannymok','1.30主题一：高清视频产品概念测试','下面的图片是对可能推广的某个高清视频产品的介绍。请仔细看这个介绍。请问：（1）你是否喜欢这个高清视频通信产品？哪里喜欢？哪里不喜欢？（2） 哪些方面符合你的需要？为什么？（3）你认为其终端（该电话机）的外观如何？可以如何改进？			'),(1329563226,29,171,1296285283,'GDT BBS - IMS','cannymok','1.29主题二/三—聊聊目前使用的通讯工具，以及对视频通话的感受','			'),(1329563226,29,166,1296272003,'GDT BBS - IMS','cannymok','1月29日主题一-自我介绍','大家好，欢迎你们参加我们的论坛！ 我先来自我介绍一下吧。 大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游，到处走走逛逛，认识不同的朋友。 下面，轮到你们来自我介绍一下啦 譬如说， 姓名，年龄，职业？ 性格？ 爱好？？			'),(1329563226,12,154,1296192737,'用户体验调查','00000','test2','gfdsgfdsgfsdgfsdgfdsg'),(1329563226,12,153,1296192502,'用户体验调查','00000','text tem','bbbbbbbbbbbbbbbbbbbbbdbd'),(1329563226,12,131,1291860111,'用户体验调查','gzhexm','电信营业厅场景体验调查','电信营业厅场景主要分为前台咨询区，产品展示区，VIP区，产品体验区四大区域，请选出你喜欢的区域。'),(1329563226,10,129,1291789284,'网站公告','admin','第一体验俱乐部正式上线','“第一体验俱乐部”由中国电信广州研究院（）建立，专门用于用户体验与交流在线社区。        在这里，您可以通过多种方式，参与中国电信在产品和服务的体验和评估，帮助中国电信提升用户体验水平！ “第一体验俱乐部”共包括了3D虚拟社区、在线交流区、在线论坛三个不同的体'),(1329563226,8,148,1296113814,'产品体验调查','cannymok','GS论坛－1月27日主题二－游戏社区使用体验','大家要记得踊跃发言哦！每天我们会评出发帖最多的前三名灌水英雄榜，有现金奖励的哦～～～但那些纯粹灌水的帖子，例如：沙发，路过啊，我们是不算进去的。所以，大家多点发有质量、有内容的内涵帖哦！！！！！！下面我们来聊聊咱们平时使用的游戏社区（2个问题都必须回答哦，'),(1329563226,8,147,1296112817,'产品体验调查','cannymok','GT论坛－1月27日主题一－自我介绍','大家好，欢迎你们参加我们的论坛！ 我先来自我介绍一下吧。 大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游、认识不同的朋友什么的。 下面，轮到你们来自我介绍一下啦 譬如说，姓名，年龄，职业？ 性格？爱好？？'),(1329563226,8,146,1296112223,'产品体验调查','cannymok','GS论坛－1月27日主题一－自我介绍','大家好，欢迎你们参加我们的论坛！我先来自我介绍一下吧。大家可以叫我Canny。我的日常的工作就是上网跟大家聊聊天、发发帖。最喜欢旅游、认识不同的朋友什么的。下面，轮到你们来自我介绍一下啦 譬如说，姓名，年龄，职业？ 性格？爱好？？			'),(1329563226,8,133,1291860503,'产品体验调查','admin','电信营业厅产品体验','第一体验俱乐部电信营业厅产品体验区分为电信天翼手机、我的e家、商务领航及189邮箱/爱音乐四大区域，请选出你喜欢的产品体验区域。');

/*Table structure for table `jrun_scene` */

DROP TABLE IF EXISTS `jrun_scene`;

CREATE TABLE `jrun_scene` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `objName` char(255) NOT NULL,
  `objId` int(11) NOT NULL,
  `images` char(255) DEFAULT NULL,
  `sceneName` char(255) DEFAULT NULL,
  `eventType` int(11) DEFAULT NULL,
  `eventUrl` char(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_scene` */

insert  into `jrun_scene`(`id`,`pid`,`objName`,`objId`,`images`,`sceneName`,`eventType`,`eventUrl`) values (1,0,' 场景物件树',1,'','',0,''),(36,1,'电信营业厅',101,'attachments/scene/101.jpg','电信',0,''),(42,36,'前台咨询',1011,'','',0,'1011.htm'),(49,42,'服务员交流',10111,'','',1,'10111.htm'),(50,42,'宣传资料浏览',10112,'','',0,''),(51,36,'产品展示区',1012,'','',0,''),(52,36,'VIP区',1013,'','',0,''),(53,36,'产品体验区',1014,'','',0,''),(54,51,'电信天翼手机',10121,'','',0,''),(55,51,'我的e家',10122,'','',0,''),(56,51,'商务领航',10123,'','',0,''),(57,51,'189邮箱/爱音乐',10124,'','',0,''),(58,51,'业务推荐',10125,'','',0,''),(59,52,'前台咨询',10131,'','',0,'http://www.baidu.com'),(60,52,'屏幕连接',10132,'','',0,''),(61,52,'自助业务',10133,'attachments/scene/10133.jpg','',0,''),(62,53,'屏幕连接',10141,'','',0,''),(63,54,'手机体验连接',101211,'','',1,'showlist.htm'),(64,54,'屏幕连接',101212,'','',0,''),(65,63,'三星i909',1012111,'','',0,''),(66,63,'三星f369',1012112,'','',0,''),(67,63,'三星f519',1012113,'','',0,''),(68,64,'天翼官方首页',1012121,'','',2,'http://www.ct10000.com/esurfing/'),(69,64,'天翼live',1012122,'','',2,'http://elive.vnet.cn/'),(70,64,'天翼手机网',1012123,'','',2,'http://www.hicdma.com/'),(71,64,'天翼套餐',1012124,'','',2,'http://www.ct10000.com/esurfing/05/03/'),(72,55,'屏幕连接',101221,'','',0,''),(73,72,'官方首页',1012211,'','',2,'http://www.ct10000.com/onehome/'),(74,72,'产品介绍',1012212,'','',2,'http://www.ct10000.com/onehome/02/'),(75,72,'e家家园',1012213,'','',2,'http://www.bbapp.net/index.php?r=Default/NotLogin'),(76,72,'e家杂志',1012214,'','',2,'http://e8_maghn.xplus.com.cn/index.php?op=fpm-index'),(77,56,'屏幕连接',101231,'','',0,''),(78,77,'官方首页',1012311,'','',2,'http://www.gdbnet.cn/'),(79,77,'产品系列',1012312,'','',2,'http://www.gdbnet.cn/infopro/dcp/'),(80,77,'产品体验',1012313,'','',2,'http://www.gdbnet.cn/bnet_activity/cardlogin/login.action'),(81,77,'经贸资讯',1012314,'','',2,'http://www.gdbnet.cn/jingmao/'),(82,57,'屏幕连接',101241,'','',0,''),(83,82,'爱音乐官网',1012411,'','',2,'http://www.118100.cn/'),(84,82,'业务介绍',1012412,'','',2,'http://www.118100.cn/v5/Help'),(85,82,'189邮箱登录',1012413,'','',2,'http://webmail14.189.cn/webmail/'),(86,82,'189邮箱介绍',1012414,'','',2,'http://webmail14.189.cn/webmail/features/'),(87,58,'屏幕连接',101251,'','',0,''),(88,87,'优惠资讯',1012511,'','',0,''),(89,87,'活动介绍',1012512,'','',0,''),(90,87,'最新动态',1012513,'','',0,''),(91,87,'热点业务',1012514,'','',0,''),(92,62,'产品连接1',101411,'','',0,''),(93,62,'产品连接2',101412,'','',0,''),(94,1,'移动营业厅',102,'','',0,''),(95,94,'前台咨询',1021,'','',0,''),(96,94,'产品展示区',1022,'','',1,'1012113.htm'),(97,94,'VIP区',1023,'','',0,''),(98,94,'业务办理',1024,'','',0,''),(99,95,'服务员交流',10211,'','',2,'http://10086.cn/10086/'),(100,95,'宣传资料浏览',10212,'','',2,'http://10086.cn/focus/'),(101,96,'移动手机',10221,'','',0,'101211.htm'),(102,96,'电脑体验连接',10222,'','',0,''),(103,96,'宣传架',10223,'','',0,''),(104,101,'三星i909',102211,'','',0,''),(105,101,'三星f369',102212,'','',0,''),(106,101,'三星f519',102213,'','',0,''),(107,102,'全球通',102221,'','',0,''),(108,102,'动感地带',102222,'','',0,''),(109,102,'神州行',102223,'','',0,''),(110,102,'G3专区',102224,'','',0,''),(111,102,'动力100',102225,'','',0,''),(112,103,'积分商场',102231,'','',0,''),(113,103,'手机天地',102232,'','',0,''),(114,103,'最新动态',102233,'','',0,''),(115,97,'服务咨询',10231,'','',0,''),(116,97,'屏幕连接',10232,'','',0,''),(117,98,'业务咨询',10241,'','',2,'http://www.ct10000.com/main/services/02/'),(118,1,'联通营业厅',103,'','',0,''),(119,118,'前台咨询',1031,'','',0,'http://www.baidu.com'),(120,118,'产品展示区',1032,'','',0,''),(121,118,'VIP区',1033,'','',0,'http://www.baidu.com'),(122,118,'业务办理',1034,'','',0,''),(123,119,'服务员交流',10311,'','',0,'http://www.baidu.com'),(124,119,'宣传资料浏览',10312,'','',0,''),(125,120,'联通手机',10321,'','',0,''),(126,120,'电脑体验连接',10322,'','',0,''),(127,120,'宣传架',10323,'','',0,''),(128,125,'三星i909',103211,'','',0,''),(129,125,'三星f369',103212,'','',0,''),(130,125,'三星f519',103213,'','',0,''),(131,126,'全球通',103221,'','',0,''),(132,126,'动感地带',103222,'','',0,''),(133,126,'神州行',103223,'','',0,''),(134,126,'G3专区',103224,'','',0,''),(135,126,'动力100',103225,'','',0,''),(136,127,'积分商场',103231,'','',0,''),(137,127,'手机天地',103232,'','',0,''),(138,127,'最新动态',103233,'','',0,''),(139,121,'服务咨询',10331,'','',0,'http://www.baidu.com'),(140,121,'屏幕连接',10332,'','',0,''),(141,122,'业务咨询',10341,'','',2,'http://www.baidu.com'),(142,1,'数码电子商场1',104,'','',0,''),(143,1,'数码电子商场2',105,'','',0,''),(144,1,'生活住宅区1',106,'','',0,''),(145,1,'生活住宅区2',107,'','',0,''),(146,142,'三星手机体验',1041,'','',0,''),(147,142,'诺基亚手机体验',1042,'','',0,''),(148,142,'天翼手机体验',1043,'','',0,''),(149,142,'爱立信手机体验',1044,'','',0,''),(150,142,'摩托罗拉手机体验',1045,'','',0,''),(151,142,'夏普手机体验',1046,'','',0,''),(152,142,'服务台咨询',1047,'','',0,''),(153,146,'手机一',10411,'','',0,''),(154,146,'手机二',10412,'','',0,''),(155,147,'手机一',10421,'','',0,''),(156,147,'手机二',10422,'','',0,''),(157,148,'手机一',10431,'','',0,''),(158,148,'手机二',10432,'','',0,''),(159,149,'手机一',10441,'','',0,''),(160,149,'手机二',10442,'','',0,''),(161,150,'手机一',10451,'','',0,''),(162,150,'手机二',10452,'','',0,''),(163,151,'手机一',10461,'','',0,''),(164,151,'手机二',10462,'','',0,''),(165,143,'三星手机体验',1051,'','',0,''),(166,143,'诺基亚手机体验',1052,'','',0,''),(167,143,'天翼手机体验',1053,'','',0,''),(168,143,'爱立信手机体验',1054,'','',0,''),(169,143,'服务台咨询',1055,'','',0,''),(170,165,'手机一',10511,'','',0,''),(171,165,'手机二',10512,'','',0,''),(172,166,'手机一',10521,'','',0,''),(173,166,'手机二',10522,'','',0,''),(174,167,'手机一',10531,'','',0,''),(175,167,'手机二',10532,'','',0,''),(176,168,'手机一',10541,'','',0,''),(177,168,'手机二',10542,'','',0,''),(178,144,'客厅体验',1061,'','',0,''),(179,144,'餐厅体验',1062,'','',0,''),(180,144,'房间体验',1063,'','',0,''),(181,144,'书房体验',1064,'','',0,''),(182,145,'客厅体验',1071,'','',0,''),(183,145,'餐厅体验',1072,'','',0,''),(184,145,'房间体验',1073,'','',0,''),(185,145,'书房体验',1074,'','',0,''),(191,213,'诺基亚',801,'','',0,''),(192,191,'6700s',80101,'','',0,''),(194,192,'移动手机',801012,'','',0,''),(195,191,'c6',80102,'','',0,''),(196,195,'移动手机',801022,'','',0,''),(197,213,'酷派',802,'','',0,''),(198,197,'n930',80201,'','',0,''),(199,198,'电信手机',802011,'','',0,''),(201,213,'摩托罗拉',803,'','',0,''),(202,201,'xt301',80301,'','',0,''),(203,202,'电信手机',803011,'','',0,''),(204,201,'mt710',80302,'','',0,''),(205,204,'移动手机',803022,'','',0,''),(206,201,'MileStone',80303,'','',0,''),(207,206,'移动手机',803032,'','',0,''),(208,213,'三星',804,'','',0,''),(209,208,'S3830U',80401,'','',0,''),(210,209,'联通手机',804013,'','',0,''),(211,208,'b7732',80402,'','',0,''),(212,211,'联通手机',804023,'','',0,''),(213,1,'手机物件',8,'','',0,''),(214,42,'前台左栏广告区',10113,'attachments/scene/10113.jpg','我的e家',0,''),(215,42,'前台右栏广告区',10114,'','10000号形象广告',0,'');

/*Table structure for table `jrun_scenect` */

DROP TABLE IF EXISTS `jrun_scenect`;

CREATE TABLE `jrun_scenect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` int(11) NOT NULL,
  `userName` char(25) NOT NULL,
  `taskType` mediumint(9) NOT NULL,
  `surveyName` char(100) DEFAULT NULL,
  `surveyUrl` char(100) DEFAULT NULL,
  `objId` int(11) DEFAULT NULL,
  `taskNo` int(11) NOT NULL,
  `taskState` mediumint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_scenect` */

insert  into `jrun_scenect`(`id`,`taskId`,`userName`,`taskType`,`surveyName`,`surveyUrl`,`objId`,`taskNo`,`taskState`) values (278,26,'wingotech',2,NULL,NULL,102,0,1),(279,26,'wingotech',2,NULL,NULL,104,0,1),(284,26,'xlqb_1314',2,NULL,NULL,102,0,1),(285,26,'xlqb_1314',2,NULL,NULL,104,0,1),(286,27,'wingotech',2,NULL,NULL,101,0,1),(287,27,'wingotech',2,NULL,NULL,102,0,1),(290,27,'xlqb_1314',2,NULL,NULL,101,0,1),(291,27,'xlqb_1314',2,NULL,NULL,102,0,1);

/*Table structure for table `jrun_scenectorder` */

DROP TABLE IF EXISTS `jrun_scenectorder`;

CREATE TABLE `jrun_scenectorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` char(50) COLLATE utf8_czech_ci NOT NULL,
  `scenectId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

/*Data for the table `jrun_scenectorder` */

insert  into `jrun_scenectorder`(`id`,`userName`,`scenectId`) values (1,'liu_saying',225),(2,'liu_saying',222),(3,'liu_saying',221),(4,'liu_saying',224),(5,'liu_saying',223),(6,'liu_saying',220),(7,'liu_saying',227),(8,'liu_saying',226),(9,'xlqb_1314',243),(10,'xlqb_1314',256),(11,'xlqb_1314',254),(12,'xlqb_1314',255),(13,'xlqb_1314',257),(14,'xlqb_1314',259);

/*Table structure for table `jrun_scenetask` */

DROP TABLE IF EXISTS `jrun_scenetask`;

CREATE TABLE `jrun_scenetask` (
  `taskId` int(11) NOT NULL AUTO_INCREMENT,
  `taskName` char(255) NOT NULL,
  `taskIntro` text NOT NULL,
  `taskTime` char(20) DEFAULT NULL,
  `createUser` char(50) NOT NULL,
  `credit` int(11) DEFAULT NULL,
  `taskType` int(11) DEFAULT NULL,
  `beginTime` varchar(20) DEFAULT NULL,
  `endTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`taskId`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_scenetask` */

insert  into `jrun_scenetask`(`taskId`,`taskName`,`taskIntro`,`taskTime`,`createUser`,`credit`,`taskType`,`beginTime`,`endTime`) values (42,'场景体验任务','场景体验任务','2011-12-27','dongbin',10,1,'2011-12-27','2011-12-30'),(43,'手机体验满意度调查','手机体验满意度调查','2011-12-27','dongbin',10,2,'2011-12-27','2011-12-30'),(44,'tt','','2011-12-28','dongbin',12,2,'2011-12-18','2011-12-29'),(45,'123','12','2012-02-13','administrator',12,2,'2012-02-13','2012-02-14');

/*Table structure for table `jrun_scenetm` */

DROP TABLE IF EXISTS `jrun_scenetm`;

CREATE TABLE `jrun_scenetm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` int(11) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `taskState` mediumint(4) DEFAULT '1',
  `getCredit` mediumint(4) DEFAULT NULL,
  `taskTime` char(20) DEFAULT NULL,
  `lastUpdateTime` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_scenetm` */

insert  into `jrun_scenetm`(`id`,`taskId`,`userName`,`taskState`,`getCredit`,`taskTime`,`lastUpdateTime`) values (69,42,'xlqb_1314',0,0,NULL,0),(70,42,'wingotech',0,0,NULL,0),(71,43,'wingotech',0,0,NULL,1325041147),(72,43,'xlqb_1314',0,0,NULL,0),(73,44,'wingotech',0,0,NULL,0),(74,45,'xlqb_1314',0,0,NULL,0),(75,45,'wingotech',0,0,NULL,1329097426);

/*Table structure for table `jrun_searchindex` */

DROP TABLE IF EXISTS `jrun_searchindex`;

CREATE TABLE `jrun_searchindex` (
  `searchid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `searchstring` text NOT NULL,
  `useip` varchar(15) NOT NULL DEFAULT '',
  `uid` mediumint(10) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `threadtypeid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `threads` smallint(6) unsigned NOT NULL DEFAULT '0',
  `tids` text NOT NULL,
  PRIMARY KEY (`searchid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_searchindex` */

/*Table structure for table `jrun_sendgifts` */

DROP TABLE IF EXISTS `jrun_sendgifts`;

CREATE TABLE `jrun_sendgifts` (
  `id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `sendid` mediumint(8) NOT NULL,
  `receiveid` mediumint(8) NOT NULL,
  `presentid` mediumint(8) NOT NULL,
  `message` varchar(255) NOT NULL,
  `sendtime` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_sendgifts` */

insert  into `jrun_sendgifts`(`id`,`sendid`,`receiveid`,`presentid`,`message`,`sendtime`) values (1,1,1,2,'fsdfsdf',1284687396),(2,1,2,2,'祝大家中秋快乐',1284687460),(3,1,3,2,'祝大家中秋快乐',1284687460),(4,1,4,2,'祝大家中秋快乐',1284687460),(5,1,6,2,'祝大家中秋快乐',1284687460),(6,1,7,2,'祝大家中秋快乐',1284687460),(7,1,8,2,'祝大家中秋快乐',1284687460),(8,1,9,2,'祝大家中秋快乐',1284687460),(9,1,2,5,'红包',1284712239),(10,1,4,5,'红包',1284712239),(11,1,6,5,'红包',1284712239),(12,1,7,5,'红包',1284712239),(13,1,8,5,'红包',1284712239),(14,1,9,5,'红包',1284712239),(15,1,1,9,'拿去花',1284715215),(16,1,2,9,'拿去花',1284715215),(17,1,3,9,'拿去花',1284715215),(18,1,4,9,'拿去花',1284715215),(19,1,5,9,'拿去花',1284715215),(20,1,6,9,'拿去花',1284715215),(21,1,7,9,'拿去花',1284715215),(22,1,8,9,'拿去花',1284715215),(23,1,9,9,'拿去花',1284715215),(24,1,5,3,'ç«ç°è±',1284881572),(25,5,2,5,'çº¢å,æ­ååè´¢',1284881652),(26,5,2,8,'å¼é±åå',1284882174),(27,5,2,7,'å¥½åçéå',1284882201),(28,5,2,10,'å¾ç¾',1284882311),(29,5,2,10,'å¾ç¾',1284882363),(30,1,7,9,'æ¿å»è±åå',1284883472);

/*Table structure for table `jrun_sessions` */

DROP TABLE IF EXISTS `jrun_sessions`;

CREATE TABLE `jrun_sessions` (
  `sid` char(6) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `ip1` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip2` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip3` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip4` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `groupid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `styleid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `invisible` tinyint(1) NOT NULL DEFAULT '0',
  `action` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `lastactivity` int(10) unsigned NOT NULL DEFAULT '0',
  `lastolupdate` int(10) unsigned NOT NULL DEFAULT '0',
  `pageviews` smallint(6) unsigned NOT NULL DEFAULT '0',
  `seccode` mediumint(6) unsigned NOT NULL DEFAULT '0',
  `fid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `bloguid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  UNIQUE KEY `sid` (`sid`),
  KEY `uid` (`uid`),
  KEY `bloguid` (`bloguid`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8;

/*Data for the table `jrun_sessions` */

insert  into `jrun_sessions`(`sid`,`ip1`,`ip2`,`ip3`,`ip4`,`uid`,`username`,`groupid`,`styleid`,`invisible`,`action`,`lastactivity`,`lastolupdate`,`pageviews`,`seccode`,`fid`,`tid`,`bloguid`) values ('VaaYgZ',123,126,50,71,0,'',7,2,0,0,1329768696,0,0,115514,0,0,0);

/*Table structure for table `jrun_settings` */

DROP TABLE IF EXISTS `jrun_settings`;

CREATE TABLE `jrun_settings` (
  `variable` varchar(32) NOT NULL DEFAULT '',
  `value` text NOT NULL,
  PRIMARY KEY (`variable`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_settings` */

insert  into `jrun_settings`(`variable`,`value`) values ('accessemail',''),('adminipaccess',''),('allowcsscache','1'),('archiverstatus','1'),('attachbanperiods',''),('attachimgpost','1'),('attachrefcheck','0'),('attachsave','3'),('authkey','CrVSXaKzoyJb4Rd'),('bannedmessages','1'),('bbclosed','0'),('bbinsert','1'),('bbname','中国电信'),('bbrules','0'),('bbrulestxt',''),('bdaystatus','0'),('boardlicensed','0'),('censoremail',''),('censoruser',''),('closedreason',''),('creditsformula','posts*0.5+digestposts*2+extcredits1*2+extcredits3+extcredits4*2'),('creditsformulaexp','<u>总积分</u>=<u>发帖数</u>*0.5+<u>精华帖数</u>*2+<u>威望</u>*2+<u>贡献</u>+<u>积极性</u>*2'),('creditsnotify',''),('creditspolicy','a:12:{s:10:\"lowerlimit\";a:0:{}s:4:\"post\";a:0:{}s:6:\"search\";a:0:{}s:10:\"postattach\";a:0:{}s:2:\"pm\";a:0:{}s:5:\"reply\";a:0:{}s:6:\"digest\";a:1:{i:1;i:8;}s:8:\"votepoll\";a:1:{i:4;i:5;}s:9:\"getattach\";a:0:{}s:18:\"promotion_register\";a:1:{i:3;i:2;}s:15:\"promotion_visit\";a:1:{i:3;i:2;}s:13:\"tradefinished\";a:0:{}}'),('creditstax','0'),('creditstrans','1'),('custombackup',''),('dateformat','yyyy-MM-dd'),('debug','1'),('delayviewcount','0'),('deletereason',''),('doublee','0'),('dupkarmarate','0'),('ec_account',''),('ec_maxcredits','1000'),('ec_maxcreditspermonth','0'),('ec_mincredits','0'),('ec_ratio','0'),('editedby','1'),('editoroptions','2'),('edittimelimit',''),('exchangemincredits','0'),('extcredits','a:8:{i:1;a:8:{s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:5:\"ratio\";d:0.0;s:12:\"showinthread\";N;s:5:\"title\";s:6:\"威望\";s:4:\"unit\";s:0:\"\";}i:2;a:8:{s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:5:\"ratio\";d:0.0;s:12:\"showinthread\";N;s:5:\"title\";s:6:\"金钱\";s:4:\"unit\";s:0:\"\";}i:3;a:8:{s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:5:\"ratio\";d:0.0;s:12:\"showinthread\";N;s:5:\"title\";s:6:\"贡献\";s:4:\"unit\";s:0:\"\";}i:4;a:8:{s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;s:9:\"available\";s:1:\"1\";s:10:\"lowerlimit\";i:0;s:5:\"ratio\";d:0.0;s:12:\"showinthread\";N;s:5:\"title\";s:9:\"积极性\";s:4:\"unit\";s:0:\"\";}i:5;a:8:{s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:5:\"ratio\";d:0.0;s:12:\"showinthread\";N;s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";}i:6;a:8:{s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:5:\"ratio\";d:0.0;s:12:\"showinthread\";N;s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";}i:7;a:8:{s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:5:\"ratio\";d:0.0;s:12:\"showinthread\";N;s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";}i:8;a:8:{s:15:\"allowexchangein\";N;s:16:\"allowexchangeout\";N;s:9:\"available\";N;s:10:\"lowerlimit\";i:0;s:5:\"ratio\";d:0.0;s:12:\"showinthread\";N;s:5:\"title\";s:0:\"\";s:4:\"unit\";s:0:\"\";}}'),('fastpost','0'),('fastreply','1'),('floodctrl','15'),('forumjump','0'),('forumfounders','1'),('globalstick','1'),('gzipcompress','0'),('hideprivate','1'),('honorset','0'),('honorvalue','a:0:{}'),('hottopic','10'),('icp',''),('initcredits','0,0,0,0,0,0,0,0,0'),('ipaccess',''),('ipregctrl',''),('jscachelife','1800'),('jsmenustatus','15'),('jsrefdomains',''),('jsstatus','0'),('karmaratelimit','0'),('loadctrl','0'),('losslessdel','365'),('maxavatarpixel','120'),('maxavatarsize','20000'),('maxbdays','0'),('maxchargespan','0'),('maxfavorites','100'),('maxincperthread','0'),('maxmodworksmonths','3'),('maxonlinelist','0'),('maxonlines','5000'),('maxpolloptions','10'),('maxpostsize','10000'),('maxsearchresults','500'),('maxsigrows','100'),('maxsmilies','10'),('maxspm','0'),('maxsubscriptions','100'),('backupdir','II86Ko'),('membermaxpages','100'),('memberperpage','25'),('memliststatus','1'),('minpostsize','10'),('moddisplay','flat'),('modratelimit','0'),('modreasons','广告/SPAM\r\n恶意灌水\r\n违规内容\r\n文不对题\r\n重复发帖\r\n\r\n我很赞同\r\n精品文章\r\n原创内容'),('modworkstatus','0'),('myrecorddays','30'),('newbiespan','0'),('newsletter',''),('nocacheheaders','0'),('oltimespan','10'),('onlinerecord','13	1288953883'),('passport_expire','3600'),('passport_extcredits','0'),('passport_key',''),('passport_login_url',''),('passport_logout_url',''),('passport_register_url',''),('passport_status',''),('passport_url',''),('postbanperiods',''),('postmodperiods',''),('postperpage','10'),('pvfrequence','60'),('qihoo','a:9:{s:6:\"status\";i:0;s:9:\"searchbox\";i:6;s:7:\"summary\";i:1;s:6:\"jammer\";i:1;s:9:\"maxtopics\";i:10;s:8:\"keywords\";s:0:\"\";s:10:\"adminemail\";s:0:\"\";s:8:\"validity\";i:1;s:14:\"relatedthreads\";a:6:{s:6:\"bbsnum\";i:0;s:6:\"webnum\";i:0;s:4:\"type\";a:3:{s:4:\"blog\";s:4:\"blog\";s:4:\"news\";s:4:\"news\";s:3:\"bbs\";s:3:\"bbs\";}s:6:\"banurl\";s:0:\"\";s:8:\"position\";i:1;s:8:\"validity\";i:1;}}'),('ratelogrecord','5'),('regadvance','0'),('regctrl','0'),('regfloodctrl','0'),('regstatus','1'),('regverify','0'),('reportpost','1'),('rewritestatus','0'),('rssstatus','1'),('rssttl','60'),('runwizard','1'),('searchbanperiods',''),('searchctrl','30'),('seccodestatus','0'),('seodescription',''),('seohead',''),('seokeywords','中国电信虚拟社区,第一体验俱乐部'),('seotitle',''),('showemail',''),('showimages','1'),('showsettings','7'),('sitename','中国电信'),('siteurl','http://v.esaying.cn'),('smcols','4'),('smileyinsert','1'),('starthreshold','2'),('statscachelife','180'),('statstatus','0'),('styleid','2'),('stylejump','1'),('subforumsindex','1'),('supe_siteurl',''),('supe_sitename',''),('supe_status','0'),('supe_tablepre',''),('threadmaxpages','1000'),('threadsticky','全局置顶,分区置顶,本版置顶'),('timeformat','2'),('timeoffset','8'),('topicperpage','20'),('transfermincredits','0'),('transsidstatus','0'),('userstatusby','1'),('visitbanperiods',''),('visitedforums','10'),('vtonlinestatus','1'),('wapcharset','2'),('wapdateformat','MM/dd'),('wapmps','500'),('wapppp','5'),('wapstatus','1'),('waptpp','10'),('watermarkquality','80'),('watermarkstatus','0'),('watermarktrans','65'),('welcomemsg','0'),('welcomemsgtxt','尊敬的{username}，您已经注册成为{sitename}的会员，请您在发表言论时，遵守当地法律法规。\r\n如果您有什么疑问可以联系管理员，Email: {adminemail}。\r\n\r\n\r\n{bbname}\r\n{time}'),('whosonlinestatus','1'),('indexname','index.jsp'),('spacedata','a:11:{s:9:\"cachelife\";s:3:\"900\";s:14:\"limitmythreads\";s:1:\"5\";s:14:\"limitmyreplies\";s:1:\"5\";s:14:\"limitmyrewards\";s:1:\"5\";s:13:\"limitmytrades\";s:1:\"5\";s:13:\"limitmyvideos\";s:1:\"0\";s:12:\"limitmyblogs\";s:1:\"8\";s:14:\"limitmyfriends\";s:1:\"0\";s:16:\"limitmyfavforums\";s:1:\"5\";s:17:\"limitmyfavthreads\";s:1:\"0\";s:10:\"textlength\";s:3:\"300\";}'),('thumbstatus','0'),('thumbwidth','400'),('thumbheight','300'),('forumlinkstatus','1'),('pluginjsmenu','插件'),('magicstatus','1'),('magicmarket','1'),('maxmagicprice','50'),('upgradeurl','http://localhost/develop/dzhead/develop/upgrade.jsp'),('ftp','a:10:{s:2:\"on\";s:1:\"0\";s:3:\"ssl\";s:1:\"0\";s:4:\"host\";s:0:\"\";s:4:\"port\";s:2:\"21\";s:8:\"username\";s:0:\"\";s:8:\"password\";s:0:\"\";s:9:\"attachdir\";s:1:\".\";s:9:\"attachurl\";s:0:\"\";s:7:\"hideurl\";s:1:\"0\";s:7:\"timeout\";s:1:\"0\";}'),('wapregister','0'),('jswizard',''),('passport_shopex','0'),('seccodeanimator','1'),('welcomemsgtitle','{username}，您好，感谢您的注册，请阅读以下内容。'),('cacheindexlife','0'),('cachethreadlife','0'),('cachethreaddir','forumdata/threadcaches'),('jsdateformat',''),('seccodedata','a:13:{s:8:\"minposts\";s:0:\"\";s:16:\"loginfailedcount\";i:0;s:5:\"width\";i:150;s:6:\"height\";i:60;s:4:\"type\";s:1:\"0\";s:10:\"background\";s:1:\"1\";s:10:\"adulterate\";s:1:\"1\";s:3:\"ttf\";s:1:\"0\";s:5:\"angle\";s:1:\"0\";s:5:\"color\";s:1:\"1\";s:4:\"size\";s:1:\"0\";s:6:\"shadow\";s:1:\"1\";s:8:\"animator\";s:1:\"0\";}'),('frameon','0'),('framewidth','180'),('smrows','4'),('watermarktype','0'),('secqaa','a:2:{s:8:\"minposts\";s:1:\"1\";s:6:\"status\";i:0;}'),('supe_circlestatus','0'),('spacestatus','1'),('whosonline_contract','0'),('attachdir','./attachments'),('attachurl','attachments'),('onlinehold','15'),('msgforward','a:3:{s:8:\"messages\";a:13:{i:0;s:19:\"thread_poll_succeed\";i:1;s:19:\"thread_rate_succeed\";i:2;s:23:\"usergroups_join_succeed\";i:3;s:23:\"usergroups_exit_succeed\";i:4;s:25:\"usergroups_update_succeed\";i:5;s:20:\"buddy_update_succeed\";i:6;s:17:\"post_edit_succeed\";i:7;s:18:\"post_reply_succeed\";i:8;s:24:\"post_edit_delete_succeed\";i:9;s:22:\"post_newthread_succeed\";i:10;s:13:\"admin_succeed\";i:11;s:17:\"pm_delete_succeed\";i:12;s:15:\"search_redirect\";}s:5:\"quick\";i:1;s:11:\"refreshtime\";s:1:\"3\";}'),('smthumb','20'),('tagstatus','1'),('hottags','20'),('viewthreadtags','100'),('rewritecompatible',''),('imagelib','0'),('imageimpath',''),('regname','register.jsp'),('reglinkname','注册'),('activitytype','朋友聚会\r\n出外郊游\r\n自驾出行\r\n公益活动\r\n线上活动'),('userdateformat','yyyy-MM-dd\r\nyyyy/MM/dd\r\ndd-MM-yyyy\r\ndd/MM/yyyy'),('tradetypes',''),('tradeimagewidth','200'),('tradeimageheight','150'),('customauthorinfo','a:1:{i:0;a:9:{s:3:\"uid\";a:1:{s:4:\"menu\";s:1:\"1\";}s:8:\"readperm\";a:1:{s:4:\"menu\";s:1:\"1\";}s:7:\"regtime\";a:1:{s:4:\"menu\";s:1:\"1\";}s:8:\"location\";a:1:{s:4:\"menu\";s:1:\"1\";}s:5:\"posts\";a:1:{s:4:\"menu\";s:1:\"1\";}s:7:\"credits\";a:1:{s:4:\"menu\";s:1:\"1\";}s:6:\"digest\";a:1:{s:4:\"menu\";s:1:\"1\";}s:6:\"oltime\";a:1:{s:4:\"menu\";s:1:\"1\";}s:8:\"lastdate\";a:1:{s:4:\"menu\";s:1:\"1\";}}}'),('ec_credit','a:2:{s:18:\"maxcreditspermonth\";i:6;s:4:\"rank\";a:15:{i:1;i:4;i:2;i:11;i:3;i:41;i:4;i:91;i:5;i:151;i:6;i:251;i:7;i:501;i:8;i:1001;i:9;i:2001;i:10;i:5001;i:11;i:10001;i:12;i:20001;i:13;i:50001;i:14;i:100001;i:15;i:200001;}}'),('mail','a:10:{s:4:\"port\";s:2:\"25\";s:12:\"mailusername\";s:1:\"1\";s:13:\"maildelimiter\";s:1:\"0\";s:15:\"sendmail_silent\";s:1:\"1\";s:6:\"server\";s:15:\"mail.esaying.cn\";s:13:\"auth_password\";s:10:\"wingo!@345\";s:4:\"from\";s:14:\"bbs@esaying.cn\";s:8:\"mailsend\";s:1:\"2\";s:4:\"auth\";s:1:\"1\";s:13:\"auth_username\";s:14:\"bbs@esaying.cn\";}'),('watermarktext',''),('watermarkminwidth','0'),('watermarkminheight','0'),('inviteconfig','a:5:{s:14:\"inviteaddbuddy\";s:1:\"0\";s:13:\"invitegroupid\";s:1:\"0\";s:18:\"inviterewardcredit\";s:1:\"0\";s:15:\"inviteaddcredit\";s:0:\"\";s:16:\"invitedaddcredit\";s:0:\"\";}'),('historyposts','0	127'),('zoomstatus','1'),('postno','#'),('postnocustom',''),('maxbiotradesize','400'),('insenz',''),('videoinfo','a:9:{s:4:\"open\";i:0;s:5:\"vtype\";s:34:\"新闻 军事 音乐 影视 动漫\";s:6:\"bbname\";s:0:\"\";s:3:\"url\";s:0:\"\";s:5:\"email\";s:0:\"\";s:4:\"logo\";s:0:\"\";s:8:\"sitetype\";s:251:\"新闻	军事	音乐	影视	动漫	游戏	美女	娱乐	交友	教育	艺术	学术	技术	动物	旅游	生活	时尚	电脑	汽车	手机	摄影	戏曲	外语	公益	校园	数码	电脑	历史	天文	地理	财经	地区	人物	体育	健康	综合\";s:7:\"vsiteid\";s:0:\"\";s:9:\"vsitecode\";s:0:\"\";}'),('google','a:3:{s:4:\"lang\";s:0:\"\";s:9:\"searchbox\";s:1:\"1\";s:6:\"status\";s:1:\"0\";}'),('baidu','a:3:{s:4:\"lang\";N;s:9:\"searchbox\";s:1:\"7\";s:6:\"status\";s:1:\"0\";}'),('baidusitemap','1'),('baidusitemap_life','12'),('adminemail','admin@your.com'),('dbreport','0'),('errorreport','1'),('attackevasive','0'),('admincp_forcesecques','0'),('admincp_checkip','1'),('admincp_tpledit','1'),('admincp_runquery','1'),('admincp_dbimport','1'),('cookiepre','wingo_'),('cookiedomain',''),('cookiepath','/'),('languages','a:2:{i:1;a:4:{s:9:\"available\";i:1;s:7:\"default\";i:1;s:8:\"language\";s:5:\"zh_CN\";s:4:\"name\";s:12:\"中文简体\";}i:2;a:4:{s:9:\"available\";i:0;s:7:\"default\";i:0;s:8:\"language\";s:5:\"zh_TW\";s:4:\"name\";s:12:\"中文繁體\";}}'),('showjavacode','0'),('lastupdate','1329768696'),('status','0'),('bbid','SyrGoGR27570'),('dateline','1279606083'),('creditchange','50'),('rewardpoint','20'),('threadcaches','0');

/*Table structure for table `jrun_smilies` */

DROP TABLE IF EXISTS `jrun_smilies`;

CREATE TABLE `jrun_smilies` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `typeid` smallint(6) unsigned NOT NULL,
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `type` enum('smiley','icon') NOT NULL DEFAULT 'smiley',
  `code` varchar(30) NOT NULL DEFAULT '',
  `url` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_smilies` */

insert  into `jrun_smilies`(`id`,`typeid`,`displayorder`,`type`,`code`,`url`) values (1,1,0,'smiley',':)','smile.gif'),(2,1,0,'smiley',':(','sad.gif'),(3,1,0,'smiley',':D','biggrin.gif'),(4,1,0,'smiley',':\'(','cry.gif'),(5,1,0,'smiley',':@','huffy.gif'),(6,1,0,'smiley',':o','shocked.gif'),(7,1,0,'smiley',':P','tongue.gif'),(8,1,0,'smiley',':#','shy.gif'),(9,1,0,'smiley',';P','titter.gif'),(10,1,0,'smiley',':L','sweat.gif'),(11,1,0,'smiley',':Q','mad.gif'),(12,1,0,'smiley',':lol','lol.gif'),(13,1,0,'smiley',':hug:','hug.gif'),(14,1,0,'smiley',':victory:','victory.gif'),(15,1,0,'smiley',':time:','time.gif'),(16,1,0,'smiley',':kiss:','kiss.gif'),(17,1,0,'smiley',':handshake','handshake.gif'),(18,1,0,'smiley',':call:','call.gif'),(19,0,0,'icon','','icon1.gif'),(20,0,0,'icon','','icon2.gif'),(21,0,0,'icon','','icon3.gif'),(22,0,0,'icon','','icon4.gif'),(23,0,0,'icon','','icon5.gif'),(24,0,0,'icon','','icon6.gif'),(25,0,0,'icon','','icon7.gif'),(26,0,0,'icon','','icon8.gif'),(27,0,0,'icon','','icon9.gif'),(28,1,0,'smiley',':loveliness:','loveliness.gif'),(29,1,0,'smiley',':funk:','funk.gif');

/*Table structure for table `jrun_spacecaches` */

DROP TABLE IF EXISTS `jrun_spacecaches`;

CREATE TABLE `jrun_spacecaches` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `variable` varchar(20) NOT NULL,
  `value` text NOT NULL,
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`variable`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_spacecaches` */

insert  into `jrun_spacecaches`(`uid`,`variable`,`value`,`expiration`) values (1,'buyercredit','a:5:{s:8:\"thisweek\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}s:6:\"before\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}s:9:\"thismonth\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}s:8:\"halfyear\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}s:3:\"all\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}}',1291689496),(1,'sellercredit','a:5:{s:8:\"thisweek\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}s:6:\"before\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}s:9:\"thismonth\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}s:8:\"halfyear\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}s:3:\"all\";a:4:{s:5:\"total\";i:0;s:4:\"soso\";i:0;s:4:\"good\";i:0;s:3:\"bad\";i:0;}}',1291689496);

/*Table structure for table `jrun_special` */

DROP TABLE IF EXISTS `jrun_special`;

CREATE TABLE `jrun_special` (
  `id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `tid` smallint(6) NOT NULL,
  `perm` tinyint(1) NOT NULL DEFAULT '0',
  `starttime` int(10) NOT NULL,
  `endtime` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_special` */

insert  into `jrun_special`(`id`,`tid`,`perm`,`starttime`,`endtime`) values (50,87,2,1283325780,1285140180),(51,88,2,1283412360,1283930760),(52,89,2,1283326200,1284449400),(53,90,1,1283326380,1284535980),(54,91,0,1283326500,1285227300),(55,93,2,1283333280,1284542880),(56,94,0,1283420700,1285494300),(57,96,0,1283389380,1284598980),(58,99,2,1283327220,1285055220),(59,100,2,1283500020,1284450420),(60,101,2,1284537000,1285660200),(61,102,2,1285746900,1285833300),(62,103,2,1283500680,1284451080),(63,104,4,1283500680,1284451080),(64,105,3,1283500980,1284710580),(65,106,1,1283501160,1284537960),(66,107,3,1283501220,1285142820),(67,108,3,1283501400,1284970200),(68,109,3,1283501400,1284365400),(69,110,3,1283501580,1284451980),(70,111,4,1283501640,1285661640),(71,112,4,1283933640,1285056840),(72,113,3,1283501940,1285143540),(73,118,4,1284541200,1284627600),(74,120,2,1286960880,1288516080),(75,137,2,1291888200,1293789000),(76,141,3,1296041280,1298892480),(77,142,3,1296041760,1298460960),(78,143,3,1296042060,1298461260),(79,144,1,1296050580,1296482580),(80,146,3,0,0),(81,147,3,-1,-1),(82,148,3,-1,-1),(83,149,3,0,0),(84,150,3,-1,-1),(85,152,3,-1,-1),(86,153,3,1296191940,1296278340),(87,154,3,1296192240,1296451440),(88,155,3,1296192540,1296624540),(89,156,3,1296192720,1296451920),(90,157,3,1296203220,1296462420),(91,158,2,1296204660,1296291060),(92,159,3,1296204840,1296636840),(93,161,3,-1,-1),(94,162,2,1296218760,1296305160),(95,163,3,1296283320,1296628920),(96,164,3,1296283380,1296628980),(97,165,3,1296283920,1296629520),(98,166,3,1296272220,1296704220),(99,167,2,1294200780,1294211760),(100,168,2,-1,-1),(101,169,3,1296282600,1296628200),(102,170,3,1296282960,1296628560),(103,171,3,1296284460,1298876460),(104,172,3,1296100860,1298908740),(105,173,3,1296100860,1298908740),(106,174,3,1296316800,1296662400),(107,175,3,1296100860,1298908740),(108,176,3,1296100860,1298908740),(109,177,3,1296370020,1296456420),(110,178,3,1296100860,1298908740),(111,179,3,1296100860,1298908740);

/*Table structure for table `jrun_stats` */

DROP TABLE IF EXISTS `jrun_stats`;

CREATE TABLE `jrun_stats` (
  `type` char(10) NOT NULL DEFAULT '',
  `variable` char(10) NOT NULL DEFAULT '',
  `count` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`type`,`variable`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_stats` */

insert  into `jrun_stats`(`type`,`variable`,`count`) values ('total','hits',1),('total','members',0),('total','guests',1),('os','Windows',1),('os','Mac',0),('os','Linux',0),('os','FreeBSD',0),('os','SunOS',0),('os','OS/2',0),('os','AIX',0),('os','Spiders',0),('os','Other',0),('browser','MSIE',1),('browser','Netscape',0),('browser','Mozilla',0),('browser','Lynx',0),('browser','Opera',0),('browser','Konqueror',0),('browser','Other',0),('week','0',0),('week','1',1),('week','2',0),('week','3',0),('week','4',0),('week','5',0),('week','6',0),('hour','00',0),('hour','01',0),('hour','02',0),('hour','03',0),('hour','04',0),('hour','05',0),('hour','06',0),('hour','07',0),('hour','08',0),('hour','09',0),('hour','10',1),('hour','11',0),('hour','12',0),('hour','13',0),('hour','14',0),('hour','15',0),('hour','16',0),('hour','17',0),('hour','18',0),('hour','19',0),('hour','20',0),('hour','21',0),('hour','22',0),('hour','23',0);

/*Table structure for table `jrun_statvars` */

DROP TABLE IF EXISTS `jrun_statvars`;

CREATE TABLE `jrun_statvars` (
  `type` varchar(20) NOT NULL DEFAULT '',
  `variable` varchar(20) NOT NULL DEFAULT '',
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`type`,`variable`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_statvars` */

insert  into `jrun_statvars`(`type`,`variable`,`value`) values ('forumsrank','lastupdate','1289185956'),('forumsrank','threads','a:16:{i:0;a:3:{s:3:\"fid\";s:2:\"10\";s:7:\"threads\";s:2:\"19\";s:4:\"name\";s:12:\"网站公告\";}i:1;a:3:{s:3:\"fid\";s:1:\"2\";s:7:\"threads\";s:2:\"13\";s:4:\"name\";s:15:\"营业厅体验\";}i:2;a:3:{s:3:\"fid\";s:2:\"13\";s:7:\"threads\";s:2:\"13\";s:4:\"name\";s:18:\"论坛管理建议\";}i:3;a:3:{s:3:\"fid\";s:2:\"16\";s:7:\"threads\";s:1:\"9\";s:4:\"name\";s:12:\"产品推荐\";}i:4;a:3:{s:3:\"fid\";s:1:\"8\";s:7:\"threads\";s:1:\"5\";s:4:\"name\";s:18:\"产品体验调查\";}i:5;a:3:{s:3:\"fid\";s:1:\"9\";s:7:\"threads\";s:1:\"5\";s:4:\"name\";s:18:\"社区体验调查\";}i:6;a:3:{s:3:\"fid\";s:2:\"15\";s:7:\"threads\";s:1:\"4\";s:4:\"name\";s:12:\"产品交流\";}i:7;a:3:{s:3:\"fid\";s:2:\"12\";s:7:\"threads\";s:1:\"3\";s:4:\"name\";s:18:\"用户体验调查\";}i:8;a:3:{s:3:\"fid\";s:2:\"24\";s:7:\"threads\";s:1:\"0\";s:4:\"name\";s:18:\"平台意见收集\";}i:9;a:3:{s:3:\"fid\";s:2:\"21\";s:7:\"threads\";s:1:\"0\";s:4:\"name\";s:12:\"商铺体验\";}i:10;a:3:{s:3:\"fid\";s:2:\"23\";s:7:\"threads\";s:1:\"0\";s:4:\"name\";s:12:\"意见反馈\";}i:11;a:3:{s:3:\"fid\";s:2:\"20\";s:7:\"threads\";s:1:\"0\";s:4:\"name\";s:12:\"商场体验\";}i:12;a:3:{s:3:\"fid\";s:2:\"22\";s:7:\"threads\";s:1:\"0\";s:4:\"name\";s:12:\"产品体验\";}i:13;a:3:{s:3:\"fid\";s:2:\"17\";s:7:\"threads\";s:1:\"0\";s:4:\"name\";s:15:\"电信营业厅\";}i:14;a:3:{s:3:\"fid\";s:2:\"18\";s:7:\"threads\";s:1:\"0\";s:4:\"name\";s:15:\"移动营业厅\";}i:15;a:3:{s:3:\"fid\";s:2:\"19\";s:7:\"threads\";s:1:\"0\";s:4:\"name\";s:15:\"联通营业厅\";}}'),('forumsrank','forums','16'),('forumsrank','posts','a:16:{i:0;a:3:{s:5:\"posts\";s:2:\"38\";s:3:\"fid\";s:2:\"10\";s:4:\"name\";s:12:\"网站公告\";}i:1;a:3:{s:5:\"posts\";s:2:\"29\";s:3:\"fid\";s:2:\"13\";s:4:\"name\";s:18:\"论坛管理建议\";}i:2;a:3:{s:5:\"posts\";s:2:\"16\";s:3:\"fid\";s:1:\"2\";s:4:\"name\";s:15:\"营业厅体验\";}i:3;a:3:{s:5:\"posts\";s:2:\"10\";s:3:\"fid\";s:2:\"16\";s:4:\"name\";s:12:\"产品推荐\";}i:4;a:3:{s:5:\"posts\";s:1:\"7\";s:3:\"fid\";s:1:\"9\";s:4:\"name\";s:18:\"社区体验调查\";}i:5;a:3:{s:5:\"posts\";s:1:\"5\";s:3:\"fid\";s:2:\"15\";s:4:\"name\";s:12:\"产品交流\";}i:6;a:3:{s:5:\"posts\";s:1:\"5\";s:3:\"fid\";s:1:\"8\";s:4:\"name\";s:18:\"产品体验调查\";}i:7;a:3:{s:5:\"posts\";s:1:\"3\";s:3:\"fid\";s:2:\"12\";s:4:\"name\";s:18:\"用户体验调查\";}i:8;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"fid\";s:2:\"18\";s:4:\"name\";s:15:\"移动营业厅\";}i:9;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"fid\";s:2:\"17\";s:4:\"name\";s:15:\"电信营业厅\";}i:10;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"fid\";s:2:\"22\";s:4:\"name\";s:12:\"产品体验\";}i:11;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"fid\";s:2:\"20\";s:4:\"name\";s:12:\"商场体验\";}i:12;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"fid\";s:2:\"23\";s:4:\"name\";s:12:\"意见反馈\";}i:13;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"fid\";s:2:\"21\";s:4:\"name\";s:12:\"商铺体验\";}i:14;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"fid\";s:2:\"24\";s:4:\"name\";s:18:\"平台意见收集\";}i:15;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"fid\";s:2:\"19\";s:4:\"name\";s:15:\"联通营业厅\";}}'),('forumsrank','thismonth','a:2:{i:0;a:3:{s:5:\"posts\";s:1:\"2\";s:3:\"fid\";s:1:\"2\";s:4:\"name\";s:15:\"营业厅体验\";}i:1;a:3:{s:5:\"posts\";s:1:\"1\";s:3:\"fid\";s:2:\"10\";s:4:\"name\";s:12:\"网站公告\";}}'),('forumsrank','today','a:0:{}'),('main','lastupdate','1288983467'),('main','forums','16'),('main','threads','33'),('main','runtime','56.8201'),('main','posts','55'),('main','members','18'),('main','postsaddtoday','0'),('main','membersaddtoday','1'),('main','admins','3'),('main','memnonpost','11'),('main','hotforum','a:4:{s:5:\"posts\";s:2:\"38\";s:3:\"fid\";s:2:\"10\";s:7:\"threads\";s:2:\"19\";s:4:\"name\";s:12:\"网站公告\";}'),('main','bestmem','None'),('main','bestmemposts','0'),('monthposts','starttime','2010-07-01'),('dayposts','20101026','0'),('dayposts','20101025','0'),('creditsrank','credits','a:18:{i:0;a:3:{s:3:\"uid\";s:1:\"3\";s:7:\"credits\";s:4:\"1020\";s:8:\"username\";s:11:\"huchangyuan\";}i:1;a:3:{s:3:\"uid\";s:1:\"1\";s:7:\"credits\";s:2:\"50\";s:8:\"username\";s:5:\"admin\";}i:2;a:3:{s:3:\"uid\";s:1:\"5\";s:7:\"credits\";s:2:\"20\";s:8:\"username\";s:8:\"zhongwei\";}i:3;a:3:{s:3:\"uid\";s:2:\"15\";s:7:\"credits\";s:2:\"10\";s:8:\"username\";s:11:\"suddenlyliu\";}i:4;a:3:{s:3:\"uid\";s:2:\"16\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"liaolx\";}i:5;a:3:{s:3:\"uid\";s:2:\"17\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:5:\"APPLE\";}i:6;a:3:{s:3:\"uid\";s:2:\"14\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:7:\"wangbin\";}i:7;a:3:{s:3:\"uid\";s:2:\"13\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"yaojie051\";}i:8;a:3:{s:3:\"uid\";s:2:\"12\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"lfgsta\";}i:9;a:3:{s:3:\"uid\";s:2:\"11\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:5:\"00000\";}i:10;a:3:{s:3:\"uid\";s:2:\"10\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:4:\"demo\";}i:11;a:3:{s:3:\"uid\";s:1:\"9\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"胡耀宇\";}i:12;a:3:{s:3:\"uid\";s:1:\"8\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"yun\";}i:13;a:3:{s:3:\"uid\";s:1:\"7\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"钟卫\";}i:14;a:3:{s:3:\"uid\";s:1:\"6\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"111\";}i:15;a:3:{s:3:\"uid\";s:1:\"4\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"胡长远\";}i:17;a:3:{s:3:\"uid\";s:2:\"18\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"duoduo\";}i:16;a:3:{s:3:\"uid\";s:1:\"2\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"411\";}}'),('dayposts','20101024','0'),('dayposts','20101023','0'),('dayposts','20101022','0'),('dayposts','20101021','0'),('dayposts','20101020','1'),('dayposts','20101019','0'),('dayposts','20101018','0'),('dayposts','20101017','0'),('monthposts','201007','10'),('dayposts','20101016','0'),('dayposts','20101015','0'),('dayposts','20101014','0'),('dayposts','20101013','1'),('dayposts','20101012','0'),('dayposts','20101011','0'),('dayposts','20101010','0'),('monthposts','201008','0'),('dayposts','20101009','0'),('postsrank','lastupdate','1289940750'),('postsrank','posts','a:18:{i:0;a:3:{s:5:\"posts\";s:3:\"115\";s:3:\"uid\";s:1:\"1\";s:8:\"username\";s:5:\"admin\";}i:1;a:3:{s:5:\"posts\";s:2:\"77\";s:3:\"uid\";s:1:\"5\";s:8:\"username\";s:8:\"zhongwei\";}i:2;a:3:{s:5:\"posts\";s:1:\"8\";s:3:\"uid\";s:1:\"3\";s:8:\"username\";s:11:\"huchangyuan\";}i:3;a:3:{s:5:\"posts\";s:1:\"1\";s:3:\"uid\";s:2:\"15\";s:8:\"username\";s:11:\"suddenlyliu\";}i:4;a:3:{s:5:\"posts\";s:1:\"1\";s:3:\"uid\";s:1:\"6\";s:8:\"username\";s:3:\"111\";}i:5;a:3:{s:5:\"posts\";s:1:\"1\";s:3:\"uid\";s:1:\"2\";s:8:\"username\";s:3:\"411\";}i:6;a:3:{s:5:\"posts\";s:1:\"1\";s:3:\"uid\";s:2:\"12\";s:8:\"username\";s:6:\"lfgsta\";}i:7;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:2:\"17\";s:8:\"username\";s:5:\"APPLE\";}i:8;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:2:\"16\";s:8:\"username\";s:6:\"liaolx\";}i:9;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:2:\"14\";s:8:\"username\";s:7:\"wangbin\";}i:10;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:2:\"13\";s:8:\"username\";s:9:\"yaojie051\";}i:11;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:2:\"11\";s:8:\"username\";s:5:\"00000\";}i:12;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:2:\"10\";s:8:\"username\";s:4:\"demo\";}i:13;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:1:\"9\";s:8:\"username\";s:9:\"胡耀宇\";}i:14;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:1:\"8\";s:8:\"username\";s:3:\"yun\";}i:15;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:1:\"7\";s:8:\"username\";s:6:\"钟卫\";}i:17;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:2:\"18\";s:8:\"username\";s:6:\"duoduo\";}i:16;a:3:{s:5:\"posts\";s:1:\"0\";s:3:\"uid\";s:1:\"4\";s:8:\"username\";s:9:\"胡长远\";}}'),('postsrank','digestposts','a:18:{i:0;a:3:{s:3:\"uid\";s:1:\"1\";s:11:\"digestposts\";s:1:\"1\";s:8:\"username\";s:5:\"admin\";}i:1;a:3:{s:3:\"uid\";s:2:\"17\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:5:\"APPLE\";}i:2;a:3:{s:3:\"uid\";s:2:\"16\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:6:\"liaolx\";}i:3;a:3:{s:3:\"uid\";s:2:\"15\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:11:\"suddenlyliu\";}i:4;a:3:{s:3:\"uid\";s:2:\"14\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:7:\"wangbin\";}i:5;a:3:{s:3:\"uid\";s:2:\"13\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:9:\"yaojie051\";}i:6;a:3:{s:3:\"uid\";s:2:\"12\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:6:\"lfgsta\";}i:7;a:3:{s:3:\"uid\";s:2:\"11\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:5:\"00000\";}i:8;a:3:{s:3:\"uid\";s:2:\"10\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:4:\"demo\";}i:9;a:3:{s:3:\"uid\";s:1:\"9\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:9:\"胡耀宇\";}i:10;a:3:{s:3:\"uid\";s:1:\"8\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:3:\"yun\";}i:11;a:3:{s:3:\"uid\";s:1:\"7\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:6:\"钟卫\";}i:12;a:3:{s:3:\"uid\";s:1:\"6\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:3:\"111\";}i:13;a:3:{s:3:\"uid\";s:1:\"5\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:8:\"zhongwei\";}i:14;a:3:{s:3:\"uid\";s:1:\"4\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:9:\"胡长远\";}i:15;a:3:{s:3:\"uid\";s:1:\"3\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:11:\"huchangyuan\";}i:17;a:3:{s:3:\"uid\";s:2:\"18\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:6:\"duoduo\";}i:16;a:3:{s:3:\"uid\";s:1:\"2\";s:11:\"digestposts\";s:1:\"0\";s:8:\"username\";s:3:\"411\";}}'),('postsrank','thismonth','a:3:{i:0;a:2:{s:5:\"posts\";s:1:\"2\";s:8:\"username\";s:5:\"admin\";}i:1;a:2:{s:5:\"posts\";s:1:\"1\";s:8:\"username\";s:11:\"suddenlyliu\";}i:2;a:2:{s:5:\"posts\";s:1:\"1\";s:8:\"username\";s:6:\"lfgsta\";}}'),('postsrank','today','a:0:{}'),('trade','lastupdate','1289813261'),('team','lastupdate','1289907747'),('team','team','a:10:{s:6:\"forums\";a:5:{i:1;a:8:{i:17;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"17\";s:3:\"fup\";s:1:\"2\";s:4:\"type\";s:3:\"sub\";s:4:\"name\";s:15:\"电信营业厅\";}i:1;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:1:\"1\";s:3:\"fup\";s:1:\"0\";s:4:\"type\";s:5:\"group\";s:4:\"name\";s:12:\"社区体验\";}i:19;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"19\";s:3:\"fup\";s:1:\"2\";s:4:\"type\";s:3:\"sub\";s:4:\"name\";s:15:\"联通营业厅\";}i:2;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:1:\"2\";s:3:\"fup\";s:1:\"1\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:15:\"营业厅体验\";}i:18;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"18\";s:3:\"fup\";s:1:\"2\";s:4:\"type\";s:3:\"sub\";s:4:\"name\";s:15:\"移动营业厅\";}i:21;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"21\";s:3:\"fup\";s:1:\"1\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:12:\"商铺体验\";}i:20;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"20\";s:3:\"fup\";s:1:\"1\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:12:\"商场体验\";}i:22;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"22\";s:3:\"fup\";s:1:\"1\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:12:\"产品体验\";}}i:5;a:2:{i:5;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:1:\"5\";s:3:\"fup\";s:1:\"0\";s:4:\"type\";s:5:\"group\";s:4:\"name\";s:12:\"论坛公告\";}i:10;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"10\";s:3:\"fup\";s:1:\"5\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:12:\"网站公告\";}}i:6;a:4:{i:6;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:1:\"6\";s:3:\"fup\";s:1:\"0\";s:4:\"type\";s:5:\"group\";s:4:\"name\";s:12:\"调查专题\";}i:8;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"1\";s:3:\"fid\";s:1:\"8\";s:3:\"fup\";s:1:\"6\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:18:\"产品体验调查\";}i:9;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:1:\"9\";s:3:\"fup\";s:1:\"6\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:18:\"社区体验调查\";}i:12;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"12\";s:3:\"fup\";s:1:\"6\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:18:\"用户体验调查\";}}i:11;a:3:{i:24;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"24\";s:3:\"fup\";s:2:\"11\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:18:\"平台意见收集\";}i:11;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"11\";s:3:\"fup\";s:1:\"0\";s:4:\"type\";s:5:\"group\";s:4:\"name\";s:18:\"管理员讨论区\";}i:13;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"13\";s:3:\"fup\";s:2:\"11\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:18:\"论坛管理建议\";}}i:14;a:4:{i:16;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"16\";s:3:\"fup\";s:2:\"14\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:12:\"产品推荐\";}i:23;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"23\";s:3:\"fup\";s:2:\"14\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:12:\"意见反馈\";}i:14;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"14\";s:3:\"fup\";s:1:\"0\";s:4:\"type\";s:5:\"group\";s:4:\"name\";s:12:\"交流反馈\";}i:15;a:6:{s:10:\"moderators\";s:1:\"0\";s:12:\"inheritedmod\";s:1:\"0\";s:3:\"fid\";s:2:\"15\";s:3:\"fup\";s:2:\"14\";s:4:\"type\";s:5:\"forum\";s:4:\"name\";s:12:\"产品交流\";}}}s:13:\"avgmodactions\";d:0.0;s:10:\"avgoffdays\";d:6.0;s:14:\"avgthismonthol\";d:2.0;s:10:\"moderators\";a:0:{}s:10:\"categories\";a:0:{}s:6:\"admins\";a:1:{i:0;s:1:\"1\";}s:10:\"avgtotalol\";d:77.0;s:7:\"members\";a:1:{i:1;a:10:{s:3:\"uid\";s:1:\"1\";s:8:\"username\";s:5:\"admin\";s:5:\"posts\";s:3:\"115\";s:7:\"credits\";s:2:\"50\";s:7:\"totalol\";s:4:\"77.5\";s:12:\"lastactivity\";s:10:\"1289367610\";s:7:\"offdays\";s:1:\"6\";s:7:\"adminid\";s:1:\"1\";s:11:\"thismonthol\";s:4:\"2.17\";s:14:\"thismonthposts\";s:1:\"2\";}}s:17:\"avgthismonthposts\";d:2.0;}'),('onlines','lastupdate','0'),('onlines','total','a:9:{i:0;a:3:{s:3:\"uid\";s:1:\"1\";s:4:\"time\";s:4:\"77.5\";s:8:\"username\";s:5:\"admin\";}i:1;a:3:{s:3:\"uid\";s:1:\"5\";s:4:\"time\";s:4:\"12.0\";s:8:\"username\";s:8:\"zhongwei\";}i:2;a:3:{s:3:\"uid\";s:1:\"8\";s:4:\"time\";s:3:\"4.0\";s:8:\"username\";s:3:\"yun\";}i:3;a:3:{s:3:\"uid\";s:1:\"3\";s:4:\"time\";s:4:\"2.33\";s:8:\"username\";s:11:\"huchangyuan\";}i:4;a:3:{s:3:\"uid\";s:2:\"15\";s:4:\"time\";s:3:\"0.5\";s:8:\"username\";s:11:\"suddenlyliu\";}i:5;a:3:{s:3:\"uid\";s:1:\"2\";s:4:\"time\";s:4:\"0.17\";s:8:\"username\";s:3:\"411\";}i:6;a:3:{s:3:\"uid\";s:1:\"7\";s:4:\"time\";s:4:\"0.17\";s:8:\"username\";s:6:\"钟卫\";}i:7;a:3:{s:3:\"uid\";s:2:\"10\";s:4:\"time\";s:4:\"0.17\";s:8:\"username\";s:4:\"demo\";}i:8;a:3:{s:3:\"uid\";s:2:\"18\";s:4:\"time\";s:4:\"0.17\";s:8:\"username\";s:6:\"duoduo\";}}'),('onlines','thismonth','a:3:{i:0;a:3:{s:3:\"uid\";s:1:\"1\";s:4:\"time\";s:4:\"2.17\";s:8:\"username\";s:5:\"admin\";}i:1;a:3:{s:3:\"uid\";s:2:\"18\";s:4:\"time\";s:4:\"0.17\";s:8:\"username\";s:6:\"duoduo\";}i:2;a:3:{s:3:\"uid\";s:2:\"15\";s:4:\"time\";s:3:\"0.0\";s:8:\"username\";s:11:\"suddenlyliu\";}}'),('dayposts','20101008','0'),('dayposts','20101007','0'),('dayposts','20101105','0'),('trade','tradesums','a:0:{}'),('trade','totalitems','a:0:{}'),('dayposts','20101104','0'),('creditsrank','extendedcredits','a:2:{i:1;a:18:{i:0;a:3:{s:3:\"uid\";s:1:\"3\";s:7:\"credits\";s:4:\"1020\";s:8:\"username\";s:11:\"huchangyuan\";}i:1;a:3:{s:3:\"uid\";s:1:\"1\";s:7:\"credits\";s:2:\"50\";s:8:\"username\";s:5:\"admin\";}i:2;a:3:{s:3:\"uid\";s:1:\"5\";s:7:\"credits\";s:2:\"20\";s:8:\"username\";s:8:\"zhongwei\";}i:3;a:3:{s:3:\"uid\";s:2:\"15\";s:7:\"credits\";s:2:\"10\";s:8:\"username\";s:11:\"suddenlyliu\";}i:4;a:3:{s:3:\"uid\";s:2:\"16\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"liaolx\";}i:5;a:3:{s:3:\"uid\";s:2:\"17\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:5:\"APPLE\";}i:6;a:3:{s:3:\"uid\";s:2:\"14\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:7:\"wangbin\";}i:7;a:3:{s:3:\"uid\";s:2:\"13\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"yaojie051\";}i:8;a:3:{s:3:\"uid\";s:2:\"12\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"lfgsta\";}i:9;a:3:{s:3:\"uid\";s:2:\"11\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:5:\"00000\";}i:10;a:3:{s:3:\"uid\";s:2:\"10\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:4:\"demo\";}i:11;a:3:{s:3:\"uid\";s:1:\"9\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"胡耀宇\";}i:12;a:3:{s:3:\"uid\";s:1:\"8\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"yun\";}i:13;a:3:{s:3:\"uid\";s:1:\"7\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"钟卫\";}i:14;a:3:{s:3:\"uid\";s:1:\"6\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"111\";}i:15;a:3:{s:3:\"uid\";s:1:\"4\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"胡长远\";}i:17;a:3:{s:3:\"uid\";s:2:\"18\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"duoduo\";}i:16;a:3:{s:3:\"uid\";s:1:\"2\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"411\";}}i:2;a:18:{i:0;a:3:{s:3:\"uid\";s:1:\"3\";s:7:\"credits\";s:4:\"1030\";s:8:\"username\";s:11:\"huchangyuan\";}i:1;a:3:{s:3:\"uid\";s:1:\"1\";s:7:\"credits\";s:2:\"50\";s:8:\"username\";s:5:\"admin\";}i:2;a:3:{s:3:\"uid\";s:1:\"5\";s:7:\"credits\";s:2:\"23\";s:8:\"username\";s:8:\"zhongwei\";}i:3;a:3:{s:3:\"uid\";s:2:\"15\";s:7:\"credits\";s:2:\"15\";s:8:\"username\";s:11:\"suddenlyliu\";}i:4;a:3:{s:3:\"uid\";s:2:\"16\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"liaolx\";}i:5;a:3:{s:3:\"uid\";s:2:\"17\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:5:\"APPLE\";}i:6;a:3:{s:3:\"uid\";s:2:\"14\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:7:\"wangbin\";}i:7;a:3:{s:3:\"uid\";s:2:\"13\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"yaojie051\";}i:8;a:3:{s:3:\"uid\";s:2:\"12\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"lfgsta\";}i:9;a:3:{s:3:\"uid\";s:2:\"11\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:5:\"00000\";}i:10;a:3:{s:3:\"uid\";s:2:\"10\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:4:\"demo\";}i:11;a:3:{s:3:\"uid\";s:1:\"9\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"胡耀宇\";}i:12;a:3:{s:3:\"uid\";s:1:\"8\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"yun\";}i:13;a:3:{s:3:\"uid\";s:1:\"7\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"钟卫\";}i:14;a:3:{s:3:\"uid\";s:1:\"6\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"111\";}i:15;a:3:{s:3:\"uid\";s:1:\"4\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:9:\"胡长远\";}i:17;a:3:{s:3:\"uid\";s:2:\"18\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:6:\"duoduo\";}i:16;a:3:{s:3:\"uid\";s:1:\"2\";s:7:\"credits\";s:1:\"0\";s:8:\"username\";s:3:\"411\";}}}'),('dayposts','20101101','0'),('monthposts','201010','3'),('dayposts','20101029','0'),('monthposts','201009','52'),('dayposts','20101027','0'),('dayposts','20101028','1'),('dayposts','20101030','0'),('dayposts','20101031','0'),('dayposts','20101102','0'),('dayposts','20101103','0');

/*Table structure for table `jrun_styles` */

DROP TABLE IF EXISTS `jrun_styles`;

CREATE TABLE `jrun_styles` (
  `styleid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `available` tinyint(1) NOT NULL DEFAULT '1',
  `templateid` smallint(6) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`styleid`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_styles` */

insert  into `jrun_styles`(`styleid`,`name`,`available`,`templateid`) values (1,'默认模板',0,1),(2,'经典风格',1,2),(3,'喝彩奥运',0,3),(4,'深邃永恒',0,4),(5,'粉妆精灵',0,5),(6,'诗意田园',0,2),(7,'春意盎然',0,2);

/*Table structure for table `jrun_stylevars` */

DROP TABLE IF EXISTS `jrun_stylevars`;

CREATE TABLE `jrun_stylevars` (
  `stylevarid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `styleid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `variable` text NOT NULL,
  `substitute` text NOT NULL,
  PRIMARY KEY (`stylevarid`),
  KEY `styleid` (`styleid`)
) ENGINE=MyISAM AUTO_INCREMENT=329 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_stylevars` */

insert  into `jrun_stylevars`(`stylevarid`,`styleid`,`variable`,`substitute`) values (42,1,'available',''),(43,1,'commonboxborder','#1e3647'),(44,1,'noticebg','#FAFAFA'),(45,1,'tablebg','#F7F7F7'),(46,1,'highlightlink','#FF9D25'),(47,1,'commonboxbg','#FAFAFA'),(48,1,'bgcolor','#F7F7F7'),(49,1,'altbg1','#F7F7F7'),(50,1,'altbg2','#FFFFFF'),(51,1,'link','#123456'),(52,1,'bordercolor','#D7D7D7'),(53,1,'headercolor','#FAFAFA forumbox_head.gif'),(54,1,'headertext','#FFF'),(55,1,'tabletext','#184057'),(56,1,'text','#1e3647'),(57,1,'catcolor','#457590'),(58,1,'borderwidth','1px'),(59,1,'fontsize','12px'),(60,1,'tablespace','0px'),(61,1,'msgfontsize','14px'),(62,1,'msgbigsize','16px'),(63,1,'msgsmallsize','12px'),(64,1,'font','Arial,Helvetica,sans-serif'),(65,1,'smfontsize','12px'),(66,1,'smfont','Arial,Helvetica,sans-serif'),(67,1,'bgborder','#1e3647'),(68,1,'maintablewidth',''),(69,1,'imgdir','images/default'),(70,1,'boardimg','logo.gif'),(71,1,'inputborder','#1e3647'),(72,1,'catborder','#1e3647'),(73,1,'lighttext','#000000'),(74,1,'framebgcolor',''),(75,1,'headermenu',''),(76,1,'headermenutext','#fff'),(77,1,'boxspace','10px'),(78,1,'portalboxbgcode','#FFF portalbox_bg.gif'),(79,1,'noticeborder','#1e3647'),(80,1,'noticetext','#FF8800'),(81,1,'PBG','#4d7c96'),(82,1,'MENUCONTEXTCOLOR','#DEDEDE'),(83,1,'JPBG','#FFF {IMGDIR/jpbg.gif}'),(84,1,'INFOTEXT','#FAFAFA'),(85,1,'OBG','#F4F4F4'),(86,1,'OTHERBG','#1e3647'),(87,1,'stypeid','1'),(88,2,'available',''),(89,2,'commonboxborder','#E8E8E8'),(90,2,'noticebg','#FFFFF2'),(91,2,'tablebg','#FFF'),(92,2,'highlightlink','#069'),(93,2,'commonboxbg','#F7F7F7'),(94,2,'bgcolor','#FFF '),(95,2,'altbg1','#F5FAFE'),(96,2,'altbg2','#E8F3FD'),(97,2,'link','#000'),(98,2,'bordercolor','#9DB3C5'),(99,2,'headercolor','#2F589C forumbox_head.gif'),(100,2,'headertext','#FFF'),(101,2,'tabletext','#000'),(102,2,'text','#666'),(103,2,'catcolor','#E8F3FD cat_bg.gif'),(104,2,'borderwidth','1px'),(105,2,'fontsize','12px'),(106,2,'tablespace','1px'),(107,2,'msgfontsize','14px'),(108,2,'msgbigsize','16px'),(109,2,'msgsmallsize','12px'),(110,2,'font','Helvetica, Arial, sans-serif'),(111,2,'smfontsize','0.83em'),(112,2,'smfont','Verdana, Arial, Helvetica, sans-serif'),(113,2,'bgborder','#CAD9EA'),(114,2,'maintablewidth','98%'),(115,2,'imgdir','images/Classic'),(116,2,'boardimg','logo.gif'),(117,2,'inputborder','#DDD'),(118,2,'catborder','#CAD9EA'),(119,2,'lighttext','#999'),(120,2,'framebgcolor','frame_bg.gif'),(121,2,'headermenu','#FFF menu_bg.gif'),(122,2,'headermenutext','#333'),(123,2,'boxspace','10px'),(124,2,'portalboxbgcode','#FFF portalbox_bg.gif'),(125,2,'noticeborder','#EDEDCE'),(126,2,'noticetext','#090'),(127,2,'stypeid','1'),(128,3,'available',''),(129,3,'bgcolor','#FFF'),(130,3,'altbg1','#FFF'),(131,3,'altbg2','#F7F7F3'),(132,3,'link','#262626'),(133,3,'bordercolor','#C1C1C1'),(134,3,'headercolor','#FFF forumbox_head.gif'),(135,3,'headertext','#D00'),(136,3,'catcolor','#F90 cat_bg.gif'),(137,3,'tabletext','#535353'),(138,3,'text','#535353'),(139,3,'borderwidth','1px'),(140,3,'tablespace','1px'),(141,3,'fontsize','12px'),(142,3,'msgfontsize','14px'),(143,3,'msgbigsize','16px'),(144,3,'msgsmallsize','12px'),(145,3,'font','Arial,Helvetica,sans-serif'),(146,3,'smfontsize','11px'),(147,3,'smfont','Arial,Helvetica,sans-serif'),(148,3,'boardimg','logo.gif'),(149,3,'imgdir','./images/Beijing2008'),(150,3,'maintablewidth','98%'),(151,3,'bgborder','#C1C1C1'),(152,3,'catborder','#E2E2E2'),(153,3,'inputborder','#D7D7D7'),(154,3,'lighttext','#535353'),(155,3,'headermenu','#FFF menu_bg.gif'),(156,3,'headermenutext','#54564C'),(157,3,'framebgcolor',''),(158,3,'noticebg',''),(159,3,'commonboxborder','#F0F0ED'),(160,3,'tablebg','#FFF'),(161,3,'highlightlink','#535353'),(162,3,'commonboxbg','#F5F5F0'),(163,3,'boxspace','8px'),(164,3,'portalboxbgcode','#FFF portalbox_bg.gif'),(165,3,'noticeborder',''),(166,3,'noticetext','#DD0000'),(167,3,'stypeid','1'),(168,4,'available',''),(169,4,'bgcolor','#222D2D'),(170,4,'altbg1','#3E4F4F'),(171,4,'altbg2','#384747'),(172,4,'link','#CEEBEB'),(173,4,'bordercolor','#1B2424'),(174,4,'headercolor','#1B2424'),(175,4,'headertext','#94B3C5'),(176,4,'catcolor','#293838'),(177,4,'tabletext','#CEEBEB'),(178,4,'text','#999'),(179,4,'borderwidth','6px'),(180,4,'tablespace','0'),(181,4,'fontsize','12px'),(182,4,'msgfontsize','14px'),(183,4,'msgbigsize','16px'),(184,4,'msgsmallsize','12px'),(185,4,'font','Arial'),(186,4,'smfontsize','11px'),(187,4,'smfont','Arial,sans-serif'),(188,4,'boardimg','logo.gif'),(189,4,'imgdir','./images/Overcast'),(190,4,'maintablewidth','98%'),(191,4,'bgborder','#384747'),(192,4,'catborder','#1B2424'),(193,4,'inputborder','#EEE'),(194,4,'lighttext','#74898E'),(195,4,'headermenu','#3E4F4F'),(196,4,'headermenutext','#CEEBEB'),(197,4,'framebgcolor','#222D2D'),(198,4,'noticebg','#3E4F4F'),(199,4,'commonboxborder','#384747'),(200,4,'tablebg','#3E4F4F'),(201,4,'highlightlink','#9CB2A0'),(202,4,'commonboxbg','#384747'),(203,4,'boxspace','6px'),(204,4,'portalboxbgcode','#293838'),(205,4,'noticeborder','#384747'),(206,4,'noticetext','#C7E001'),(207,4,'stypeid','1'),(208,5,'noticetext','#C44D4D'),(209,5,'noticeborder','#D6D6D6'),(210,5,'portalboxbgcode','#FFF portalbox_bg.gif'),(211,5,'boxspace','6px'),(212,5,'commonboxbg','#FAFAFA'),(213,5,'highlightlink','#C44D4D'),(214,5,'tablebg','#FFF'),(215,5,'commonboxborder','#DEDEDE'),(216,5,'noticebg','#FAFAFA'),(217,5,'framebgcolor','#FFECF9'),(218,5,'headermenu','#FBFBFB'),(219,5,'headermenutext',''),(220,5,'lighttext','#999'),(221,5,'catborder','#D7D7D7'),(222,5,'inputborder',''),(223,5,'bgborder','#CECECE'),(224,5,'stypeid','1'),(225,5,'maintablewidth','920px'),(226,5,'imgdir','images/PinkDresser'),(227,5,'boardimg','logo.gif'),(228,5,'smfont','Arial,Helvetica,sans-serif'),(229,5,'smfontsize','12px'),(230,5,'font','Arial,Helvetica,sans-serif'),(231,5,'msgsmallsize','12px'),(232,5,'msgbigsize','16px'),(233,5,'msgfontsize','14px'),(234,5,'fontsize','12px'),(235,5,'tablespace','0'),(236,5,'borderwidth','1px'),(237,5,'text','#666'),(238,5,'tabletext','#666'),(239,5,'catcolor','#FAFAFA category_bg.gif'),(240,5,'headertext','#FFF'),(241,5,'headercolor','#E7BFC9 forumbox_head.gif'),(242,5,'bordercolor','#D88E9D'),(243,5,'link','#C44D4D'),(244,5,'altbg2','#F1F1F1'),(245,5,'available',''),(246,5,'altbg1','#FBFBFB'),(247,5,'bgcolor','#FBF4F5'),(248,5,'stypeid','1'),(249,6,'available',''),(250,6,'bgcolor','#FFF'),(251,6,'altbg1','#FFFBF8'),(252,6,'altbg2','#FBF6F1'),(253,6,'link','#54564C'),(254,6,'bordercolor','#D7B094'),(255,6,'headercolor','#BE6A2D forumbox_head.gif'),(256,6,'headertext','#FFF'),(257,6,'catcolor','#E9E9E9 cat_bg.gif'),(258,6,'tabletext','#7B7D72'),(259,6,'text','#535353'),(260,6,'borderwidth','1px'),(261,6,'tablespace','1px'),(262,6,'fontsize','12px'),(263,6,'msgfontsize','14px'),(264,6,'msgbigsize','16px'),(265,6,'msgsmallsize','12px'),(266,6,'font','Arial, sans-serif'),(267,6,'smfontsize','11px'),(268,6,'smfont','Arial, sans-serif'),(269,6,'boardimg','logo.gif'),(270,6,'imgdir','./images/Picnicker'),(271,6,'maintablewidth','98%'),(272,6,'bgborder','#E8C9B7'),(273,6,'catborder','#E6E6E2'),(274,6,'inputborder',''),(275,6,'lighttext','#878787'),(276,6,'headermenu','#FFF menu_bg.gif'),(277,6,'headermenutext','#54564C'),(278,6,'framebgcolor','frame_bg.gif'),(279,6,'noticebg','#FAFAF7'),(280,6,'commonboxborder','#E6E6E2'),(281,6,'tablebg','#FFF'),(282,6,'highlightlink',''),(283,6,'commonboxbg','#F5F5F0'),(284,6,'boxspace','6px'),(285,6,'portalboxbgcode','#FFF portalbox_bg.gif'),(286,6,'noticeborder','#E6E6E2'),(287,6,'noticetext','#FF3A00'),(288,6,'stypeid','1'),(289,7,'available',''),(290,7,'bgcolor','#FFF'),(291,7,'altbg1','#F5F5F0'),(292,7,'altbg2','#F9F9F9'),(293,7,'link','#54564C'),(294,7,'bordercolor','#D9D9D4'),(295,7,'headercolor','#80A400 forumbox_head.gif'),(296,7,'headertext','#FFF'),(297,7,'catcolor','#F5F5F0 cat_bg.gif'),(298,7,'tabletext','#7B7D72'),(299,7,'text','#535353'),(300,7,'borderwidth','1px'),(301,7,'tablespace','1px'),(302,7,'fontsize','12px'),(303,7,'msgfontsize','14px'),(304,7,'msgbigsize','16px'),(305,7,'msgsmallsize','12px'),(306,7,'font','Arial,sans-serif'),(307,7,'smfontsize','11px'),(308,7,'smfont','Arial,sans-serif'),(309,7,'boardimg','logo.gif'),(310,7,'imgdir','./images/GreenPark'),(311,7,'maintablewidth','98%'),(312,7,'bgborder','#D9D9D4'),(313,7,'catborder','#D9D9D4'),(314,7,'inputborder','#D9D9D4'),(315,7,'lighttext','#878787'),(316,7,'headermenu','#FFF menu_bg.gif'),(317,7,'headermenutext','#262626'),(318,7,'framebgcolor',''),(319,7,'noticebg','#FAFAF7'),(320,7,'commonboxborder','#E6E6E2'),(321,7,'tablebg','#FFF'),(322,7,'highlightlink','#535353'),(323,7,'commonboxbg','#F9F9F9'),(324,7,'boxspace','6px'),(325,7,'portalboxbgcode','#FFF portalbox_bg.gif'),(326,7,'noticeborder','#E6E6E2'),(327,7,'noticetext','#FF3A00'),(328,7,'stypeid','1');

/*Table structure for table `jrun_subscriptions` */

DROP TABLE IF EXISTS `jrun_subscriptions`;

CREATE TABLE `jrun_subscriptions` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `lastpost` int(10) unsigned NOT NULL DEFAULT '0',
  `lastnotify` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`tid`,`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_subscriptions` */

/*Table structure for table `jrun_tags` */

DROP TABLE IF EXISTS `jrun_tags`;

CREATE TABLE `jrun_tags` (
  `tagname` char(20) NOT NULL,
  `closed` tinyint(1) NOT NULL DEFAULT '0',
  `total` mediumint(8) unsigned NOT NULL,
  PRIMARY KEY (`tagname`),
  KEY `total` (`total`),
  KEY `closed` (`closed`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_tags` */

/*Table structure for table `jrun_task` */

DROP TABLE IF EXISTS `jrun_task`;

CREATE TABLE `jrun_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task` char(255) NOT NULL,
  `up_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_task` */

insert  into `jrun_task`(`id`,`task`,`up_id`) values (1,'任务',0),(2,'移动营业厅',1),(3,'电信营业厅',1),(4,'联通营业厅',1),(5,'移动3G',2),(6,'电信天翼',3),(7,'联通3G业务',4),(8,'电信江西',3),(9,'移动3G生活',5),(12,'a',9);

/*Table structure for table `jrun_tasklist` */

DROP TABLE IF EXISTS `jrun_tasklist`;

CREATE TABLE `jrun_tasklist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskname` char(255) NOT NULL,
  `tasks` char(255) NOT NULL,
  `starttime` char(20) NOT NULL,
  `endtime` char(20) NOT NULL,
  `search` char(255) DEFAULT NULL,
  `report_url` char(255) DEFAULT NULL,
  `report_remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_tasklist` */

insert  into `jrun_tasklist`(`id`,`taskname`,`tasks`,`starttime`,`endtime`,`search`,`report_url`,`report_remark`) values (1,'天翼商场','6,7','2010-2-12','2010-3-30','','',''),(2,'第二个',',7','2010-2-20','2010-3-30','',NULL,NULL);

/*Table structure for table `jrun_taskmember` */

DROP TABLE IF EXISTS `jrun_taskmember`;

CREATE TABLE `jrun_taskmember` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskid` int(11) NOT NULL,
  `member` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_taskmember` */

insert  into `jrun_taskmember`(`id`,`taskid`,`member`) values (1,1,'zhongwei'),(2,2,'zhongwei，haha');

/*Table structure for table `jrun_templates` */

DROP TABLE IF EXISTS `jrun_templates`;

CREATE TABLE `jrun_templates` (
  `templateid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL DEFAULT '',
  `directory` varchar(100) NOT NULL DEFAULT '',
  `copyright` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`templateid`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_templates` */

insert  into `jrun_templates`(`templateid`,`name`,`directory`,`copyright`) values (1,'默认模板套系','./templates/default','网佳科技'),(2,'经典风格','./templates/Classic','网佳科技'),(3,'喝彩奥运','./templates/Beijing2008','网佳科技'),(4,'深邃永恒','./templates/Overcast','网佳科技'),(5,'粉妆精灵','./templates/PinkDresser','网佳科技');

/*Table structure for table `jrun_themetemplate` */

DROP TABLE IF EXISTS `jrun_themetemplate`;

CREATE TABLE `jrun_themetemplate` (
  `id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) NOT NULL,
  `ispublic` smallint(6) NOT NULL,
  `subject` varchar(80) NOT NULL,
  `message` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_themetemplate` */

insert  into `jrun_themetemplate`(`id`,`uid`,`ispublic`,`subject`,`message`) values (3,5,0,'毛新宇成为中国最年轻少将','毛新宇成为中国最年轻少将 南都讯 记者陈宝成 发自北京 毛新宇目前已经晋升少将，不仅成为目前中国最年轻的少将，也是第一位“70后”少将。 据四川省广元市元坝区门户网站昨日披露，7月29日，军事科学院战争理论和战略研究部副部长、中华全国青年联合会常委、全国政协委员、少将毛新宇一行到昭化古城考察。(南方都市报 www.nddaily.com SouthernMetropolisDailyMark 南都网) 少将为正军职和副军职军官的主要军衔。该网站披露的照片显示，毛新宇身着07式陆军少将军服；松枝绿色底版上缀有金色枝叶和一颗金色星徽的肩章显示着他已晋升少将军衔。 毛新宇出生于1970年，是毛泽东次子毛岸青之子，其母邵华；毛新宇先后毕业于中国人民大学、中央党校和军事科学院，获博士学位，2008年7月，被任命为军事科学院战略部副部长，副军级。'),(5,1,1,'模板','<P>我的模板</P>'),(6,20,1,'今日有专题','今天发布了新产品，收集各位的意见。');

/*Table structure for table `jrun_threads` */

DROP TABLE IF EXISTS `jrun_threads`;

CREATE TABLE `jrun_threads` (
  `tid` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `fid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `iconid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `typeid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `readperm` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `price` smallint(6) NOT NULL DEFAULT '0',
  `author` char(15) NOT NULL DEFAULT '',
  `authorid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `subject` char(80) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `lastpost` int(10) unsigned NOT NULL DEFAULT '0',
  `lastposter` char(15) NOT NULL DEFAULT '',
  `views` int(10) unsigned NOT NULL DEFAULT '0',
  `replies` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(1) NOT NULL DEFAULT '0',
  `highlight` tinyint(1) NOT NULL DEFAULT '0',
  `digest` tinyint(1) NOT NULL DEFAULT '0',
  `rate` tinyint(1) NOT NULL DEFAULT '0',
  `blog` tinyint(1) NOT NULL DEFAULT '0',
  `special` tinyint(1) NOT NULL DEFAULT '0',
  `attachment` tinyint(1) NOT NULL DEFAULT '0',
  `subscribed` tinyint(1) NOT NULL DEFAULT '0',
  `moderated` tinyint(1) NOT NULL DEFAULT '0',
  `closed` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `itemid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `supe_pushstatus` tinyint(1) NOT NULL DEFAULT '0',
  `iscomment` tinyint(1) NOT NULL,
  `isreply` tinyint(1) NOT NULL,
  `showtype` tinyint(1) NOT NULL,
  PRIMARY KEY (`tid`),
  KEY `digest` (`digest`),
  KEY `displayorder` (`fid`,`displayorder`,`lastpost`),
  KEY `blog` (`blog`,`authorid`,`dateline`),
  KEY `typeid` (`fid`,`typeid`,`displayorder`,`lastpost`)
) ENGINE=MyISAM AUTO_INCREMENT=180 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_threads` */

insert  into `jrun_threads`(`tid`,`fid`,`iconid`,`typeid`,`readperm`,`price`,`author`,`authorid`,`subject`,`dateline`,`lastpost`,`lastposter`,`views`,`replies`,`displayorder`,`highlight`,`digest`,`rate`,`blog`,`special`,`attachment`,`subscribed`,`moderated`,`closed`,`itemid`,`supe_pushstatus`,`iscomment`,`isreply`,`showtype`) values (159,28,0,0,0,0,'cannymok',74,'1月28日主题二－大家对IPTV游戏产品功能的认知情况',1296205679,1296528614,'NJGT03',88,16,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(160,28,0,0,0,0,'cannymok',74,'1月28日主题三－什么是iptv游戏呢',1296206460,1296528764,'NJGT03',410,18,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0),(148,8,0,0,0,0,'cannymok',74,'GS论坛－1月27日主题二－游戏社区使用体验',1296113814,1296113814,'cannymok',3,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(147,8,0,0,0,0,'cannymok',74,'GT论坛－1月27日主题一－自我介绍',1296112817,1296112817,'cannymok',2,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(172,27,0,0,0,0,'cannymok',74,'1月29日主题二-付费问题',1296307363,1296459726,'NJGS02',60,11,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(171,29,0,0,0,0,'cannymok',74,'1.29主题二/三—聊聊目前使用的通讯工具，以及对视频通话的感受',1296285283,1296480688,'NJIMS01',163,18,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(146,8,0,0,0,0,'cannymok',74,'GS论坛－1月27日主题一－自我介绍',1296112223,1296112223,'cannymok',5,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(156,27,0,0,0,0,'cannymok',74,'1月28日主题一-自我介绍',1296193246,1296447314,'NJGS02',104,11,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(155,27,0,0,0,0,'cannymok',74,'1月28日主题二-游戏社区使用体验',1296193161,1296359867,'cannymok',214,26,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(163,27,0,0,0,0,'cannymok',74,'1月28日主题三-对糖果社区的评价',1296270805,1296360495,'cannymok',70,11,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(154,12,0,0,0,0,'00000',75,'test2',1296192737,1296192737,'00000',5,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(157,28,0,0,0,0,'cannymok',74,'1月28日主题一-自我介绍',1296203893,1296377272,'GZGT02',75,12,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(164,27,0,0,0,0,'cannymok',74,'1月29日主题一-给大家介绍个新的游戏平台',1296271096,1296458000,'NJGS02',90,10,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(166,29,0,0,0,0,'cannymok',74,'1月29日主题一-自我介绍',1296272003,1296530526,'NJIMS01',114,16,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(165,28,0,0,0,0,'cannymok',74,'1月29日主题一-ITV游戏收费问题',1296271766,1296529388,'NJGT03',50,9,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(170,28,0,0,0,0,'cannymok',74,'1.29主题三—大家对IPTV游戏的期望与建议',1296283572,1296529052,'NJGT03',53,10,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(175,27,0,0,0,0,'cannymok',74,'1月30日补充主题',1296361743,1296459252,'NJGS02',50,9,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(174,29,0,0,0,0,'cannymok',74,'1.30主题一：高清视频产品概念测试',1296354012,1296432248,'SHIMS01',110,13,0,0,0,0,0,2,1,0,0,0,0,0,0,1,1),(173,27,0,0,0,0,'cannymok',74,'1月29日主题三-给爱游戏社区的建议',1296307475,1296459626,'NJGS02',69,12,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(169,28,0,0,0,0,'cannymok',74,'1.29主题二—客服咨询、投拆及流程的满意度及改进建议',1296283339,1296528946,'NJGT03',45,9,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(133,8,0,0,0,0,'admin',1,'电信营业厅产品体验',1291860503,1296366074,'CDIMS02',225,14,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0),(178,29,0,0,0,0,'cannymok',74,'1月30日主题三：高清视频通信主要功能评价',1296372121,1296480295,'NJIMS01',79,12,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(153,12,0,0,0,0,'00000',75,'text tem',1296192502,1296192588,'000000',5,1,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1),(131,12,0,0,0,0,'gzhexm',40,'电信营业厅场景体验调查',1291860111,1296393170,'CDIMS03',234,16,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0),(176,29,0,0,0,0,'cannymok',74,'1.30专题二：高清视频通信产品的主要功能评价',1296370271,1296479821,'NJIMS01',90,13,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(130,17,0,0,0,0,'admin',1,'产品体验区隆重上线',1291789639,1296393021,'CDIMS03',479,21,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0),(179,28,0,0,0,0,'cannymok',74,'1月30日补充主题',1296372640,1297067899,'SHGT01',44,8,0,0,0,0,0,2,0,0,0,0,0,0,0,2,1),(129,10,0,0,0,0,'admin',1,'第一体验俱乐部正式上线',1291789284,1296392935,'CDIMS03',560,22,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0);

/*Table structure for table `jrun_threadsmod` */

DROP TABLE IF EXISTS `jrun_threadsmod`;

CREATE TABLE `jrun_threadsmod` (
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` char(15) NOT NULL DEFAULT '',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `action` char(5) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `magicid` smallint(6) unsigned NOT NULL,
  KEY `tid` (`tid`,`dateline`),
  KEY `expiration` (`expiration`,`status`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_threadsmod` */

/*Table structure for table `jrun_threadtags` */

DROP TABLE IF EXISTS `jrun_threadtags`;

CREATE TABLE `jrun_threadtags` (
  `tagname` char(20) NOT NULL,
  `tid` int(10) unsigned NOT NULL,
  KEY `tagname` (`tagname`),
  KEY `tid` (`tid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_threadtags` */

/*Table structure for table `jrun_threadtypes` */

DROP TABLE IF EXISTS `jrun_threadtypes`;

CREATE TABLE `jrun_threadtypes` (
  `typeid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `special` smallint(6) NOT NULL DEFAULT '0',
  `modelid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `expiration` tinyint(1) NOT NULL DEFAULT '0',
  `template` text NOT NULL,
  PRIMARY KEY (`typeid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_threadtypes` */

/*Table structure for table `jrun_tradecomments` */

DROP TABLE IF EXISTS `jrun_tradecomments`;

CREATE TABLE `jrun_tradecomments` (
  `id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `orderid` char(32) NOT NULL,
  `pid` int(10) unsigned NOT NULL,
  `type` tinyint(1) NOT NULL,
  `raterid` mediumint(8) unsigned NOT NULL,
  `rater` char(15) NOT NULL,
  `rateeid` mediumint(8) unsigned NOT NULL,
  `ratee` char(15) NOT NULL,
  `message` char(200) NOT NULL,
  `explanation` char(200) NOT NULL,
  `score` tinyint(1) NOT NULL,
  `dateline` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `raterid` (`raterid`,`type`,`dateline`),
  KEY `rateeid` (`rateeid`,`type`,`dateline`),
  KEY `orderid` (`orderid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_tradecomments` */

/*Table structure for table `jrun_tradelog` */

DROP TABLE IF EXISTS `jrun_tradelog`;

CREATE TABLE `jrun_tradelog` (
  `tid` mediumint(8) unsigned NOT NULL,
  `pid` int(10) unsigned NOT NULL,
  `orderid` varchar(32) NOT NULL,
  `tradeno` varchar(32) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `price` decimal(8,2) NOT NULL,
  `quality` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `itemtype` tinyint(1) NOT NULL DEFAULT '0',
  `number` smallint(5) unsigned NOT NULL DEFAULT '0',
  `tax` decimal(6,2) unsigned NOT NULL DEFAULT '0.00',
  `locus` varchar(100) NOT NULL,
  `sellerid` mediumint(8) unsigned NOT NULL,
  `seller` varchar(15) NOT NULL,
  `selleraccount` varchar(50) NOT NULL,
  `buyerid` mediumint(8) unsigned NOT NULL,
  `buyer` varchar(15) NOT NULL,
  `buyercontact` varchar(50) NOT NULL,
  `buyercredits` smallint(5) unsigned NOT NULL DEFAULT '0',
  `buyermsg` varchar(200) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `lastupdate` int(10) unsigned NOT NULL DEFAULT '0',
  `offline` tinyint(1) NOT NULL DEFAULT '0',
  `buyername` varchar(50) NOT NULL,
  `buyerzip` varchar(10) NOT NULL,
  `buyerphone` varchar(20) NOT NULL,
  `buyermobile` varchar(20) NOT NULL,
  `transport` tinyint(1) NOT NULL DEFAULT '0',
  `transportfee` smallint(6) unsigned NOT NULL DEFAULT '0',
  `baseprice` decimal(8,2) NOT NULL,
  `discount` tinyint(1) NOT NULL DEFAULT '0',
  `ratestatus` tinyint(1) NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  UNIQUE KEY `orderid` (`orderid`),
  KEY `sellerid` (`sellerid`),
  KEY `buyerid` (`buyerid`),
  KEY `status` (`status`),
  KEY `buyerlog` (`buyerid`,`status`,`lastupdate`),
  KEY `sellerlog` (`sellerid`,`status`,`lastupdate`),
  KEY `tid` (`tid`,`pid`),
  KEY `pid` (`pid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_tradelog` */

/*Table structure for table `jrun_tradeoptionvars` */

DROP TABLE IF EXISTS `jrun_tradeoptionvars`;

CREATE TABLE `jrun_tradeoptionvars` (
  `typeid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `pid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `optionid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `value` mediumtext NOT NULL,
  KEY `typeid` (`typeid`),
  KEY `pid` (`pid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_tradeoptionvars` */

/*Table structure for table `jrun_trades` */

DROP TABLE IF EXISTS `jrun_trades`;

CREATE TABLE `jrun_trades` (
  `tid` mediumint(8) unsigned NOT NULL,
  `pid` int(10) unsigned NOT NULL,
  `typeid` smallint(6) unsigned NOT NULL,
  `sellerid` mediumint(8) unsigned NOT NULL,
  `seller` char(15) NOT NULL,
  `account` char(50) NOT NULL,
  `subject` char(100) NOT NULL,
  `price` decimal(8,2) NOT NULL,
  `amount` smallint(6) unsigned NOT NULL DEFAULT '1',
  `quality` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `locus` char(20) NOT NULL,
  `transport` tinyint(1) NOT NULL DEFAULT '0',
  `ordinaryfee` smallint(4) unsigned NOT NULL DEFAULT '0',
  `expressfee` smallint(4) unsigned NOT NULL DEFAULT '0',
  `emsfee` smallint(4) unsigned NOT NULL DEFAULT '0',
  `itemtype` tinyint(1) NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `lastbuyer` char(15) NOT NULL,
  `lastupdate` int(10) unsigned NOT NULL DEFAULT '0',
  `totalitems` smallint(5) unsigned NOT NULL DEFAULT '0',
  `tradesum` decimal(8,2) NOT NULL DEFAULT '0.00',
  `closed` tinyint(1) NOT NULL DEFAULT '0',
  `aid` mediumint(8) unsigned NOT NULL,
  `displayorder` tinyint(1) NOT NULL,
  `costprice` decimal(8,2) NOT NULL,
  PRIMARY KEY (`tid`,`pid`),
  KEY `sellerid` (`sellerid`),
  KEY `totalitems` (`totalitems`),
  KEY `tradesum` (`tradesum`),
  KEY `displayorder` (`tid`,`displayorder`),
  KEY `sellertrades` (`sellerid`,`tradesum`,`totalitems`),
  KEY `typeid` (`typeid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_trades` */

/*Table structure for table `jrun_typemodels` */

DROP TABLE IF EXISTS `jrun_typemodels`;

CREATE TABLE `jrun_typemodels` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `options` mediumtext NOT NULL,
  `customoptions` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_typemodels` */

insert  into `jrun_typemodels`(`id`,`name`,`displayorder`,`type`,`options`,`customoptions`) values (1,'房屋交易信息',0,1,'7	10	13	65	66	68',''),(2,'车票交易信息',0,1,'55	56	58	67	7	13	68',''),(3,'兴趣交友信息',0,1,'8	9	31',''),(4,'公司招聘信息',0,1,'34	48	54	51	47	46	44	45	52	53','');

/*Table structure for table `jrun_typeoptions` */

DROP TABLE IF EXISTS `jrun_typeoptions`;

CREATE TABLE `jrun_typeoptions` (
  `optionid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `classid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `title` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `identifier` varchar(40) NOT NULL DEFAULT '',
  `type` varchar(20) NOT NULL DEFAULT '',
  `rules` mediumtext NOT NULL,
  PRIMARY KEY (`optionid`),
  KEY `classid` (`classid`)
) ENGINE=MyISAM AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_typeoptions` */

insert  into `jrun_typeoptions`(`optionid`,`classid`,`displayorder`,`title`,`description`,`identifier`,`type`,`rules`) values (1,0,0,'通用类','','','',''),(2,0,0,'房产类','','','',''),(3,0,0,'交友类','','','',''),(4,0,0,'求职招聘类','','','',''),(5,0,0,'交易类','','','',''),(6,0,0,'互联网类','','','',''),(7,1,0,'姓名','','name','text',''),(9,1,0,'年龄','','age','number',''),(10,1,0,'地址','','address','text',''),(11,1,0,'QQ','','qq','number',''),(12,1,0,'邮箱','','mail','email',''),(13,1,0,'电话','','phone','text',''),(14,5,0,'培训费用','','teach_pay','text',''),(15,5,0,'培训时间','','teach_time','text',''),(20,2,0,'楼层','','floor','number',''),(21,2,0,'交通状况','','traf','textarea',''),(22,2,0,'地图','','images','image',''),(24,2,0,'价格','','price','text',''),(26,5,0,'培训名称','','teach_name','text',''),(28,3,0,'身高','','heighth','number',''),(29,3,0,'体重','','weighth','number',''),(33,1,0,'照片','','photo','image',''),(35,5,0,'服务方式','','service_type','text',''),(36,5,0,'服务时间','','service_time','text',''),(37,5,0,'服务费用','','service_pay','text',''),(39,6,0,'网址','','site_url','url',''),(40,6,0,'电子邮件','','site_mail','email',''),(42,6,0,'网站名称','','site_name','text',''),(46,4,0,'职位','','recr_intend','text',''),(47,4,0,'工作地点','','recr_palce','text',''),(49,4,0,'有效期至','','recr_end','calendar',''),(51,4,0,'公司名称','','recr_com','text',''),(52,4,0,'年龄要求','','recr_age','text',''),(54,4,0,'专业','','recr_abli','text',''),(55,5,0,'始发','','leaves','text',''),(56,5,0,'终点','','boundfor','text',''),(57,6,0,'Alexa排名','','site_top','number',''),(58,5,0,'车次/航班','','train_no','text',''),(59,5,0,'数量','','trade_num','number',''),(60,5,0,'价格','','trade_price','text',''),(61,5,0,'有效期至','','trade_end','calendar',''),(63,1,0,'详细描述','','detail_content','textarea',''),(64,1,0,'籍贯','','born_place','text',''),(65,2,0,'租金','','money','text',''),(66,2,0,'面积','','acreage','text',''),(67,5,0,'发车时间','','time','calendar','N;'),(68,1,0,'所在地','','now_place','text','');

/*Table structure for table `jrun_typeoptionvars` */

DROP TABLE IF EXISTS `jrun_typeoptionvars`;

CREATE TABLE `jrun_typeoptionvars` (
  `typeid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `optionid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `expiration` int(10) unsigned NOT NULL DEFAULT '0',
  `value` mediumtext NOT NULL,
  KEY `typeid` (`typeid`),
  KEY `tid` (`tid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_typeoptionvars` */

/*Table structure for table `jrun_typevars` */

DROP TABLE IF EXISTS `jrun_typevars`;

CREATE TABLE `jrun_typevars` (
  `typeid` smallint(6) NOT NULL DEFAULT '0',
  `optionid` smallint(6) NOT NULL DEFAULT '0',
  `available` tinyint(1) NOT NULL DEFAULT '0',
  `required` tinyint(1) NOT NULL DEFAULT '0',
  `unchangeable` tinyint(1) NOT NULL DEFAULT '0',
  `search` tinyint(1) NOT NULL DEFAULT '0',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  UNIQUE KEY `optionid` (`typeid`,`optionid`),
  KEY `typeid` (`typeid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_typevars` */

/*Table structure for table `jrun_usergroups` */

DROP TABLE IF EXISTS `jrun_usergroups`;

CREATE TABLE `jrun_usergroups` (
  `groupid` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `radminid` tinyint(3) NOT NULL DEFAULT '0',
  `type` enum('system','special','member') NOT NULL DEFAULT 'member',
  `system` char(8) NOT NULL DEFAULT 'private',
  `grouptitle` char(30) NOT NULL DEFAULT '',
  `creditshigher` int(10) NOT NULL DEFAULT '0',
  `creditslower` int(10) NOT NULL DEFAULT '0',
  `stars` tinyint(3) NOT NULL DEFAULT '0',
  `color` char(7) NOT NULL DEFAULT '',
  `groupavatar` char(60) NOT NULL DEFAULT '',
  `readaccess` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `allowvisit` tinyint(1) NOT NULL DEFAULT '0',
  `allowpost` tinyint(1) NOT NULL DEFAULT '0',
  `allowreply` tinyint(1) NOT NULL DEFAULT '0',
  `allowpostpoll` tinyint(1) NOT NULL DEFAULT '0',
  `allowpostreward` tinyint(1) NOT NULL DEFAULT '0',
  `allowposttrade` tinyint(1) NOT NULL DEFAULT '0',
  `allowpostactivity` tinyint(1) NOT NULL DEFAULT '0',
  `allowpostvideo` tinyint(1) NOT NULL DEFAULT '0',
  `allowdirectpost` tinyint(1) NOT NULL DEFAULT '0',
  `allowgetattach` tinyint(1) NOT NULL DEFAULT '0',
  `allowpostattach` tinyint(1) NOT NULL DEFAULT '0',
  `allowvote` tinyint(1) NOT NULL DEFAULT '0',
  `allowmultigroups` tinyint(1) NOT NULL DEFAULT '0',
  `allowsearch` tinyint(1) NOT NULL DEFAULT '0',
  `allowavatar` tinyint(1) NOT NULL DEFAULT '0',
  `allowcstatus` tinyint(1) NOT NULL DEFAULT '0',
  `allowuseblog` tinyint(1) NOT NULL DEFAULT '0',
  `allowinvisible` tinyint(1) NOT NULL DEFAULT '0',
  `allowtransfer` tinyint(1) NOT NULL DEFAULT '0',
  `allowsetreadperm` tinyint(1) NOT NULL DEFAULT '0',
  `allowsetattachperm` tinyint(1) NOT NULL DEFAULT '0',
  `allowhidecode` tinyint(1) NOT NULL DEFAULT '0',
  `allowhtml` tinyint(1) NOT NULL DEFAULT '0',
  `allowcusbbcode` tinyint(1) NOT NULL DEFAULT '0',
  `allowanonymous` tinyint(1) NOT NULL DEFAULT '0',
  `allownickname` tinyint(1) NOT NULL DEFAULT '0',
  `allowsigbbcode` tinyint(1) NOT NULL DEFAULT '0',
  `allowsigimgcode` tinyint(1) NOT NULL DEFAULT '0',
  `allowviewpro` tinyint(1) NOT NULL DEFAULT '0',
  `allowviewstats` tinyint(1) NOT NULL DEFAULT '0',
  `disableperiodctrl` tinyint(1) NOT NULL DEFAULT '0',
  `reasonpm` tinyint(1) NOT NULL DEFAULT '0',
  `maxprice` smallint(6) unsigned NOT NULL DEFAULT '0',
  `maxpmnum` smallint(6) unsigned NOT NULL DEFAULT '0',
  `maxsigsize` smallint(6) unsigned NOT NULL DEFAULT '0',
  `maxattachsize` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `maxsizeperday` int(10) unsigned NOT NULL DEFAULT '0',
  `maxpostsperhour` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `attachextensions` char(100) NOT NULL DEFAULT '',
  `raterange` char(150) NOT NULL DEFAULT '',
  `mintradeprice` smallint(6) unsigned NOT NULL DEFAULT '1',
  `maxtradeprice` smallint(6) unsigned NOT NULL DEFAULT '0',
  `minrewardprice` smallint(6) NOT NULL DEFAULT '1',
  `maxrewardprice` smallint(6) NOT NULL DEFAULT '0',
  `magicsdiscount` tinyint(1) NOT NULL,
  `allowmagics` tinyint(1) unsigned NOT NULL,
  `maxmagicsweight` smallint(6) unsigned NOT NULL,
  `allowbiobbcode` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `allowbioimgcode` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `maxbiosize` smallint(6) unsigned NOT NULL DEFAULT '0',
  `allowinvite` tinyint(1) NOT NULL DEFAULT '0',
  `allowmailinvite` tinyint(1) NOT NULL DEFAULT '0',
  `maxinvitenum` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `inviteprice` smallint(6) unsigned NOT NULL DEFAULT '0',
  `maxinviteday` smallint(6) unsigned NOT NULL DEFAULT '0',
  `allowpostdebate` tinyint(1) NOT NULL DEFAULT '0',
  `tradestick` tinyint(1) unsigned NOT NULL,
  `allowviewdigest` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`groupid`),
  KEY `creditsrange` (`creditslower`,`creditshigher`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `jrun_usergroups` */

insert  into `jrun_usergroups`(`groupid`,`radminid`,`type`,`system`,`grouptitle`,`creditshigher`,`creditslower`,`stars`,`color`,`groupavatar`,`readaccess`,`allowvisit`,`allowpost`,`allowreply`,`allowpostpoll`,`allowpostreward`,`allowposttrade`,`allowpostactivity`,`allowpostvideo`,`allowdirectpost`,`allowgetattach`,`allowpostattach`,`allowvote`,`allowmultigroups`,`allowsearch`,`allowavatar`,`allowcstatus`,`allowuseblog`,`allowinvisible`,`allowtransfer`,`allowsetreadperm`,`allowsetattachperm`,`allowhidecode`,`allowhtml`,`allowcusbbcode`,`allowanonymous`,`allownickname`,`allowsigbbcode`,`allowsigimgcode`,`allowviewpro`,`allowviewstats`,`disableperiodctrl`,`reasonpm`,`maxprice`,`maxpmnum`,`maxsigsize`,`maxattachsize`,`maxsizeperday`,`maxpostsperhour`,`attachextensions`,`raterange`,`mintradeprice`,`maxtradeprice`,`minrewardprice`,`maxrewardprice`,`magicsdiscount`,`allowmagics`,`maxmagicsweight`,`allowbiobbcode`,`allowbioimgcode`,`maxbiosize`,`allowinvite`,`allowmailinvite`,`maxinvitenum`,`inviteprice`,`maxinviteday`,`allowpostdebate`,`tradestick`,`allowviewdigest`) values (1,1,'system','private','管理员',0,0,9,'','',200,1,1,1,1,1,1,1,1,3,1,1,1,1,2,3,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,30,200,500,2048000,0,0,'','1	-30	30	500',1,0,1,0,0,2,200,2,2,0,0,0,0,0,0,1,5,1),(2,2,'system','private','超级版主',0,0,8,'','',150,1,1,1,1,1,1,1,1,3,1,1,1,1,1,3,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,20,120,300,2048000,0,0,'chm, pdf, zip, rar, tar, gz, bzip2, gif, jpg, jpeg, png','1	-15	15	50',1,0,1,0,0,2,180,2,2,0,0,0,0,0,0,1,5,1),(3,3,'system','private','版主',0,0,7,'','',100,1,1,1,1,1,1,1,1,0,1,1,1,1,1,3,1,1,0,1,1,1,1,0,1,0,1,1,1,1,1,1,0,10,80,200,2048000,0,0,'chm, pdf, zip, rar, tar, gz, bzip2, gif, jpg, jpeg, png','1	-10	10	30	',1,0,1,0,0,2,160,2,2,0,0,0,0,0,0,1,5,1),(4,0,'system','private','禁止发言',0,0,0,'','',0,1,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,'','',1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1),(5,0,'system','private','禁止访问',0,0,0,'','',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,5,0),(6,0,'system','private','禁止 IP',0,0,0,'','',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,5,0),(7,0,'system','private','游客',0,0,0,'','',1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,'gif,jpg,jpeg,png','',1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,5,0),(8,0,'system','private','等待验证会员',0,0,0,'','',0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,50,0,0,0,'','',1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,5,0),(9,0,'member','private','普通会员',-999999999,0,0,'','',0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,'chm,pdf,zip,rar,tar,gz,bzip2,gif,jpg,jpeg,png','',1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,5,1),(10,0,'member','private','会员',0,999999999,1,'','',10,1,0,1,0,0,0,0,0,0,1,0,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,20,80,0,0,0,'chm, pdf, zip, rar, tar, gz, bzip2, gif, jpg, jpeg, png','',1,0,1,0,0,1,40,1,1,0,0,0,0,0,0,1,5,0),(17,0,'special','private','研究员',0,0,0,'','',1,1,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'','',1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1);

/*Table structure for table `jrun_validating` */

DROP TABLE IF EXISTS `jrun_validating`;

CREATE TABLE `jrun_validating` (
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `submitdate` int(10) unsigned NOT NULL DEFAULT '0',
  `moddate` int(10) unsigned NOT NULL DEFAULT '0',
  `admin` varchar(15) NOT NULL DEFAULT '',
  `submittimes` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `message` text NOT NULL,
  `remark` text NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `status` (`status`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_validating` */

/*Table structure for table `jrun_videos` */

DROP TABLE IF EXISTS `jrun_videos`;

CREATE TABLE `jrun_videos` (
  `vid` varchar(16) NOT NULL DEFAULT '',
  `uid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `dateline` int(10) unsigned NOT NULL DEFAULT '0',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `pid` int(10) unsigned NOT NULL DEFAULT '0',
  `vtype` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `vview` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `vtime` smallint(6) unsigned NOT NULL DEFAULT '0',
  `visup` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `vthumb` varchar(128) NOT NULL DEFAULT '',
  `vtitle` varchar(64) NOT NULL DEFAULT '',
  `vclass` varchar(32) NOT NULL DEFAULT '',
  `vautoplay` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`vid`),
  UNIQUE KEY `uid` (`vid`,`uid`),
  KEY `dateline` (`dateline`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_videos` */

/*Table structure for table `jrun_videotags` */

DROP TABLE IF EXISTS `jrun_videotags`;

CREATE TABLE `jrun_videotags` (
  `tagname` char(10) NOT NULL DEFAULT '',
  `vid` char(14) NOT NULL DEFAULT '',
  `tid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  UNIQUE KEY `tagname` (`tagname`,`vid`),
  KEY `tid` (`tid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_videotags` */

/*Table structure for table `jrun_words` */

DROP TABLE IF EXISTS `jrun_words`;

CREATE TABLE `jrun_words` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `admin` varchar(15) NOT NULL DEFAULT '',
  `find` varchar(255) NOT NULL DEFAULT '',
  `replacement` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `jrun_words` */

/*Table structure for table `vcity_accesstotal` */

DROP TABLE IF EXISTS `vcity_accesstotal`;

CREATE TABLE `vcity_accesstotal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resId` int(11) DEFAULT NULL,
  `userName` varchar(20) DEFAULT NULL,
  `accessIp` varchar(20) DEFAULT NULL,
  `accessTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_accesstotal` */

insert  into `vcity_accesstotal`(`id`,`resId`,`userName`,`accessIp`,`accessTime`) values (415,106,NULL,'119.132.72.102','2011-12-06'),(310,53,NULL,'127.0.0.1','2011-10-28'),(311,4,NULL,NULL,'2011-10-28'),(312,15,NULL,'127.0.0.1','2011-10-28'),(313,15,NULL,'127.0.0.1','2010-10-17'),(414,102,NULL,'125.89.9.166','2011-12-02'),(315,15,NULL,'127.0.0.1','2010-10-17'),(413,94,NULL,'125.89.9.166','2011-12-02'),(412,98,NULL,'125.89.9.166','2011-12-01'),(320,54,NULL,NULL,'2011-10-28'),(321,15,NULL,NULL,'2011-10-28'),(322,18,NULL,NULL,'2011-10-28'),(323,21,NULL,NULL,'2011-10-28'),(324,54,NULL,'127.0.0.1','2011-10-28'),(325,54,NULL,NULL,'2011-10-28'),(326,54,NULL,'127.0.0.1','2011-10-28'),(327,55,NULL,'127.0.0.1','2011-10-28'),(328,56,NULL,'127.0.0.1','2011-10-28'),(329,54,NULL,'127.0.0.1','2011-10-31'),(330,57,NULL,'127.0.0.1','2011-10-31'),(331,58,NULL,'127.0.0.1','2011-10-31'),(332,59,NULL,'127.0.0.1','2011-10-31'),(333,60,NULL,'127.0.0.1','2011-10-31'),(334,61,NULL,'127.0.0.1','2011-10-31'),(335,62,NULL,'127.0.0.1','2011-10-31'),(336,63,NULL,'127.0.0.1','2011-10-31'),(337,63,NULL,NULL,'2011-10-31'),(338,18,NULL,NULL,'2011-11-01'),(339,54,NULL,'127.0.0.1','2011-11-01'),(340,54,NULL,'127.0.0.1','2011-11-01'),(341,54,NULL,'127.0.0.1','2011-11-02'),(342,64,NULL,'127.0.0.1','2011-11-02'),(343,15,NULL,NULL,'2011-11-03'),(344,63,NULL,'127.0.0.1','2011-11-03'),(345,64,NULL,NULL,'2011-11-03'),(346,11,NULL,NULL,'2011-11-03'),(347,18,NULL,'127.0.0.1','2011-11-04'),(348,18,NULL,'127.0.0.1','2011-11-04'),(349,54,NULL,'127.0.0.1','2011-11-04'),(350,64,NULL,'127.0.0.1','2011-11-04'),(351,64,NULL,NULL,'2011-11-04'),(352,21,NULL,NULL,'2011-11-04'),(353,11,NULL,NULL,'2011-11-04'),(354,11,NULL,'127.0.0.1','2011-11-04'),(355,11,NULL,'127.0.0.1','2011-11-04'),(356,16,NULL,NULL,'2011-11-04'),(357,54,NULL,NULL,'2011-11-04'),(358,54,NULL,'127.0.0.1','2011-11-04'),(359,15,NULL,NULL,'2011-11-07'),(360,54,NULL,NULL,'2011-11-07'),(361,63,NULL,NULL,'2011-11-07'),(362,63,NULL,'127.0.0.1','2011-11-07'),(363,11,NULL,NULL,'2011-11-08'),(364,15,NULL,NULL,'2011-11-08'),(365,65,NULL,'119.132.73.242','2011-11-14'),(366,65,NULL,NULL,'2011-11-14'),(367,66,NULL,'119.132.73.242','2011-11-14'),(368,67,NULL,'119.132.73.242','2011-11-14'),(369,68,NULL,'119.132.73.242','2011-11-14'),(370,69,NULL,'183.45.53.252','2011-11-14'),(371,70,NULL,'183.45.53.252','2011-11-14'),(372,71,NULL,'183.45.53.252','2011-11-14'),(373,72,NULL,'183.45.53.252','2011-11-14'),(374,73,NULL,'183.45.53.252','2011-11-14'),(375,15,NULL,'113.108.150.153','2011-11-15'),(376,NULL,NULL,'113.108.150.152','2011-11-16'),(377,75,NULL,'119.132.73.47','2011-11-16'),(378,78,NULL,'119.132.73.47','2011-11-16'),(379,79,NULL,'119.132.73.48','2011-11-16'),(380,80,NULL,'116.132.73.47','2011-11-16'),(381,81,NULL,'119.132.73.47','2011-11-16'),(382,82,NULL,'119.132.73.47','2011-11-16'),(383,83,NULL,'119.132.73.47','2011-11-16'),(384,84,NULL,'119.132.73.47','2011-11-16'),(385,85,NULL,'119.132.73.47','2011-11-16'),(386,86,NULL,'119.132.73.47','2011-11-16'),(387,87,NULL,'119.132.73.47','2011-11-16'),(388,88,NULL,'119.132.73.47','2011-11-16'),(389,90,NULL,'119.132.73.47','2011-11-16'),(390,91,NULL,'119.132.73.47','2011-11-16'),(391,92,NULL,'119.132.73.47','2011-11-16'),(392,93,NULL,'119.132.73.47','2011-11-16'),(393,94,NULL,'119.132.73.47','2011-11-16'),(394,95,NULL,'119.132.73.47','2011-11-16'),(395,96,NULL,'119.132.73.45','2011-11-16'),(396,97,NULL,'119.132.73.46','2011-11-16'),(397,84,NULL,'113.108.150.153','2011-11-16'),(398,98,NULL,'119.132.73.47','2011-11-17'),(399,99,NULL,'119.132.74.181','2011-11-20'),(400,100,NULL,'119.132.74.181','2011-11-20'),(401,101,NULL,'119.132.74.181','2011-11-20'),(402,102,NULL,'119.132.74.181','2011-11-20'),(403,103,NULL,'119.132.74.181','2011-11-20'),(404,104,NULL,'119.132.74.181','2011-11-20'),(405,105,NULL,'119.132.74.181','2011-11-20'),(406,100,NULL,NULL,'2011-11-20'),(407,106,NULL,'119.132.74.181','2011-11-20'),(408,89,NULL,'119.132.74.181','2011-11-21'),(409,89,NULL,NULL,'2011-11-21'),(410,94,NULL,NULL,'2011-11-21'),(411,94,NULL,'119.132.74.181','2011-11-21'),(416,106,NULL,'119.132.72.102','2011-12-07'),(417,98,NULL,'119.132.72.102','2011-12-07'),(418,106,NULL,'119.132.72.102','2011-12-07'),(419,106,NULL,'119.132.75.209','2011-12-09'),(420,105,NULL,'119.132.74.45','2011-12-12'),(421,102,NULL,'119.132.74.45','2011-12-12'),(422,101,NULL,'119.132.74.45','2011-12-12'),(423,100,NULL,'119.132.74.45','2011-12-12'),(424,107,NULL,'119.132.74.4','2011-12-15'),(425,108,NULL,'119.132.74.4','2011-12-15'),(426,109,NULL,'119.132.74.4','2011-12-15'),(427,110,NULL,'119.132.74.4','2011-12-15'),(428,75,NULL,'119.132.74.4','2011-12-16'),(429,75,NULL,'119.132.75.163','2011-12-19'),(430,105,NULL,'119.132.75.163','2011-12-19'),(431,98,NULL,'119.132.75.163','2011-12-19'),(432,89,NULL,'119.132.75.163','2011-12-19'),(433,109,NULL,'119.132.75.163','2011-12-19'),(434,-1,'administrator','119.132.75.163','2011-12-19'),(435,106,NULL,'119.132.75.163','2011-12-19'),(436,-1,'administrator','119.132.75.163','2011-12-20'),(437,98,NULL,'119.132.75.163','2011-12-20'),(438,75,NULL,'119.132.75.163','2011-12-20'),(439,105,NULL,'119.132.73.101','2011-12-20'),(440,102,NULL,'119.132.73.101','2011-12-20'),(441,-1,'huangjinbo','119.132.73.101','2011-12-21'),(442,-1,'administrator','119.132.73.101','2011-12-21'),(443,-1,'administrator','119.132.73.101','2011-12-22'),(444,109,NULL,'113.108.150.153','2011-12-22'),(445,75,NULL,'59.41.240.208','2011-12-22'),(446,98,NULL,'113.108.150.153','2011-12-22'),(447,102,NULL,'59.41.240.208','2011-12-22'),(448,100,NULL,'59.41.240.208','2011-12-22'),(449,104,NULL,'59.41.240.208','2011-12-22'),(450,98,NULL,'59.41.240.208','2011-12-22'),(451,98,NULL,'125.89.9.159','2011-12-23'),(452,109,NULL,'117.136.31.147','2011-12-23'),(453,98,NULL,'117.136.31.147','2011-12-23'),(454,-1,'dongbin','125.89.9.159','2011-12-26'),(455,111,NULL,'125.89.11.175','2011-12-26'),(456,106,NULL,'125.89.11.175','2011-12-27'),(457,-1,'administrator','125.89.11.175','2011-12-27'),(458,98,NULL,'125.89.11.175','2011-12-27'),(459,103,NULL,'125.89.11.175','2011-12-27'),(460,-1,'dongbin','125.89.11.175','2011-12-27'),(461,105,NULL,'113.108.150.132','2011-12-28'),(462,-1,'dongbin','113.108.150.132','2011-12-28'),(463,75,NULL,'125.89.10.119','2011-12-28'),(464,109,NULL,'125.89.10.119','2011-12-28'),(465,105,NULL,'125.89.10.119','2011-12-28'),(466,109,NULL,'125.89.10.119','2011-12-29'),(467,105,NULL,'125.89.10.119','2011-12-29'),(468,-1,'dongbin','125.89.10.119','2011-12-29'),(469,98,NULL,'59.42.126.20','2012-01-05'),(470,109,NULL,'59.42.126.20','2012-01-05'),(471,98,NULL,'119.132.75.226','2012-01-06'),(472,109,NULL,'119.132.75.226','2012-01-06'),(473,98,NULL,'119.132.72.92','2012-01-06'),(474,106,NULL,'119.132.74.188','2012-01-09'),(475,108,NULL,'119.132.74.188','2012-01-09'),(476,75,NULL,'119.132.74.188','2012-01-09'),(477,98,NULL,'119.132.74.188','2012-01-09'),(478,109,NULL,'119.132.74.188','2012-01-09'),(479,107,NULL,'119.132.74.188','2012-01-09'),(480,-1,'administrator','119.132.74.188','2012-01-09'),(481,-1,'dongbin','119.132.74.188','2012-01-09'),(482,89,NULL,'119.132.74.188','2012-01-09'),(483,102,NULL,'119.132.74.188','2012-01-09'),(484,105,NULL,'119.132.74.188','2012-01-09'),(485,-1,'administrator','121.32.89.235','2012-01-10'),(486,108,NULL,'119.132.75.202','2012-01-12'),(487,75,NULL,'119.132.75.202','2012-01-12'),(488,109,NULL,'119.132.75.202','2012-01-12'),(489,89,NULL,'119.132.75.202','2012-01-12'),(490,-1,'administrator','119.132.75.202','2012-01-12'),(491,-1,'dongbin','119.132.75.202','2012-01-12'),(492,-1,'administrator','119.132.73.18','2012-01-16'),(493,-1,'dongbin','119.132.73.18','2012-01-16'),(494,105,NULL,'119.132.73.18','2012-01-16'),(495,109,NULL,'119.132.73.18','2012-01-16'),(496,98,NULL,'59.42.126.20','2012-01-16'),(497,89,NULL,'125.89.10.68','2012-01-19'),(498,-1,'administrator','119.132.72.199','2012-01-31'),(499,75,NULL,'119.132.72.199','2012-01-31'),(500,100,NULL,'119.132.72.199','2012-01-31'),(501,107,NULL,'119.132.72.199','2012-01-31'),(502,109,NULL,'119.132.72.199','2012-01-31'),(503,112,NULL,'119.132.72.199','2012-01-31'),(504,98,NULL,'119.132.72.231','2012-02-03'),(505,112,NULL,'119.132.74.49','2012-02-06'),(506,-1,'administrator','119.132.74.49','2012-02-06'),(507,109,NULL,'119.132.74.49','2012-02-06'),(508,106,NULL,'119.132.74.49','2012-02-06'),(509,105,NULL,'119.132.74.49','2012-02-06'),(510,98,NULL,'119.132.74.49','2012-02-06'),(511,-1,'administrator','119.132.74.49','2012-02-07'),(512,102,NULL,'119.132.74.49','2012-02-07'),(513,109,NULL,'115.171.15.102','2012-02-08'),(514,108,NULL,'115.171.15.102','2012-02-08'),(515,89,NULL,'115.171.15.102','2012-02-08'),(516,98,NULL,'183.62.12.28','2012-02-16'),(517,98,NULL,'101.226.65.111','2012-02-16'),(518,75,NULL,'59.42.126.27','2012-02-17'),(519,-1,'administrator','119.132.73.219','2012-02-17'),(520,98,NULL,'119.132.73.219','2012-02-17'),(521,105,NULL,'119.132.73.219','2012-02-17'),(522,102,NULL,'119.132.73.219','2012-02-17'),(523,102,NULL,'113.108.150.153','2012-02-17'),(524,104,NULL,'113.108.150.153','2012-02-17'),(525,109,NULL,'113.108.150.153','2012-02-17'),(526,108,NULL,'113.108.150.153','2012-02-17'),(527,75,NULL,'59.42.126.21','2012-02-17'),(528,107,NULL,'119.132.73.219','2012-02-17'),(529,100,NULL,'119.132.73.219','2012-02-17'),(530,101,NULL,'119.132.73.219','2012-02-17'),(531,106,NULL,'119.132.73.219','2012-02-17'),(532,98,NULL,'183.1.213.1','2012-02-17'),(533,104,NULL,'183.1.213.1','2012-02-17'),(534,75,NULL,'183.1.213.1','2012-02-17'),(535,101,NULL,'119.132.73.219','2012-02-20'),(536,106,NULL,'119.132.73.219','2012-02-20'),(537,109,NULL,'119.132.73.219','2012-02-20'),(538,100,NULL,'119.132.73.219','2012-02-20'),(539,103,NULL,'119.132.73.219','2012-02-20'),(540,98,NULL,'119.132.73.219','2012-02-20'),(541,101,NULL,'113.108.150.153','2012-02-20'),(542,93,NULL,'119.132.73.219','2012-02-20'),(543,93,NULL,'119.132.73.219','2012-02-21'),(544,106,NULL,'125.89.11.33','2012-02-21');

/*Table structure for table `vcity_chatroom` */

DROP TABLE IF EXISTS `vcity_chatroom`;

CREATE TABLE `vcity_chatroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roomName` varchar(50) DEFAULT NULL,
  `roomDescribe` varchar(250) DEFAULT NULL,
  `createdUser` varchar(50) DEFAULT NULL,
  `beginTime` varchar(20) DEFAULT NULL,
  `endTime` varchar(20) DEFAULT NULL,
  `isPublish` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_chatroom` */

insert  into `vcity_chatroom`(`id`,`roomName`,`roomDescribe`,`createdUser`,`beginTime`,`endTime`,`isPublish`) values (2,'2012了','test','administrator','2012-01-09 17:12:43','2012-01-30 17:12:45',0),(3,'会议室20120112','<p>会议室20120112会议室20120112会议室20120112</p>\r\n<p>会议室20120112会议室20120112会议室20120112</p>\r\n<p>会议室20120112会议室20120112会议室20120112</p>','administrator','2012-01-12 09:09:27','2012-01-13 09:09:34',1),(4,'测试会议0116','123','administrator','2012-01-16 09:05:55','2012-01-17 09:05:58',1),(5,'测试会议0116-02','12312','administrator','2012-01-16 09:09:47','2012-01-16 10:09:49',0),(6,'test','test','dongbin','2012-01-16 09:26:37','2012-01-26 09:26:40',0),(7,'test0117','test0117','dongbin','2012-01-17 10:31:18','2012-01-19 10:31:20',0),(8,'内部交流会议','内部交流会议','administrator','2012-02-13 09:33:36','2012-02-14 09:33:38',0);

/*Table structure for table `vcity_chatroominfo` */

DROP TABLE IF EXISTS `vcity_chatroominfo`;

CREATE TABLE `vcity_chatroominfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roomId` int(11) DEFAULT NULL,
  `chatTime` varchar(20) DEFAULT NULL,
  `chatFrom` varchar(20) DEFAULT NULL,
  `chatTo` varchar(20) DEFAULT NULL,
  `chatInfo` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_chatroominfo` */

insert  into `vcity_chatroominfo`(`id`,`roomId`,`chatTime`,`chatFrom`,`chatTo`,`chatInfo`) values (1,1,'2012-01-09 09:20:32','administrator','','Good good study,day day up!<br>'),(2,1,'2012-01-09 09:22:20','huangjinbo','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/11.gif\" alt=\"\" />'),(3,1,'2012-01-09 09:49:54','huangjinbo','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/12.gif\" alt=\"\" />'),(4,1,'2012-01-09 09:50:16','administrator','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/87.gif\" />'),(5,1,'2012-01-09 11:35:06','wingotech','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/13.gif\" alt=\"\" /><br />'),(6,1,'2012-01-09 15:44:00','huangjinbo','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/11.gif\" alt=\"\" />'),(7,1,'2012-01-09 15:44:20','wingotech','','居然还能在运行呀'),(8,1,'2012-01-09 15:44:42','administrator','','真奇怪呵<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/0.gif\" /><br>'),(9,3,'2012-01-12 09:16:31','wingotech','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" />'),(10,3,'2012-01-12 09:16:40','administrator','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/19.gif\" />'),(11,3,'2012-01-12 10:21:44','administrator','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/13.gif\" />'),(12,7,'2012-01-18 16:35:08','dongbin','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/28.gif\" />'),(13,7,'2012-01-18 16:35:47','huangjinbo','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/11.gif\" alt=\"\" />'),(14,7,'2012-01-18 16:36:20','huangjinbo','','九把刀<br />'),(15,7,'2012-01-18 16:36:33','dongbin','','柯景腾<br>');

/*Table structure for table `vcity_chatuser` */

DROP TABLE IF EXISTS `vcity_chatuser`;

CREATE TABLE `vcity_chatuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chatRoomId` int(11) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `lastUpdateTime` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_chatuser` */

insert  into `vcity_chatuser`(`id`,`chatRoomId`,`username`,`lastUpdateTime`) values (5,2,'huangjinbo',0),(6,2,'administrator',0),(7,3,'administrator',1326349411),(8,3,'huangjinbo',0),(9,3,'wingotech',1326343790),(10,3,'xlqb_1314',0),(11,4,'wingotech',0),(12,4,'administrator',1326679919),(13,4,'dongbin',0),(14,5,'administrator',0),(15,5,'huangjinbo',0),(16,6,'huangjinbo',0),(17,6,'dongbin',1326768840),(18,7,'dongbin',1326879016),(19,7,'huangjinbo',1326879038),(20,8,'administrator',1329097187),(21,8,'wingotech',0),(22,8,'huangjinbo',0);

/*Table structure for table `vcity_downloadinfo` */

DROP TABLE IF EXISTS `vcity_downloadinfo`;

CREATE TABLE `vcity_downloadinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resId` int(11) DEFAULT NULL,
  `downloadIp` varchar(20) DEFAULT NULL,
  `downloadTime` varchar(20) DEFAULT NULL,
  `downloadType` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_downloadinfo` */

insert  into `vcity_downloadinfo`(`id`,`resId`,`downloadIp`,`downloadTime`,`downloadType`) values (1,98,'119.132.72.102','2011-12-07',1),(2,106,'119.132.72.102','2011-12-07',1),(3,94,'119.132.72.102','2011-12-07',2),(4,106,'119.132.72.102','2011-12-07',2),(5,98,'116.19.85.24','2011-12-09',1),(6,98,'116.19.85.24','2011-12-09',1),(7,97,'119.132.74.177','2011-12-12',2),(8,94,'119.132.74.177','2011-12-12',2),(9,106,'119.132.74.4','2011-12-14',2),(10,89,'119.132.74.4','2011-12-14',2),(11,82,'119.132.74.4','2011-12-15',1),(12,81,'119.132.74.4','2011-12-15',1),(13,110,'119.132.74.4','2011-12-16',2),(14,109,'119.132.74.4','2011-12-16',2),(15,108,'119.132.74.4','2011-12-16',2),(16,107,'119.132.74.4','2011-12-16',2),(17,106,'119.132.74.4','2011-12-16',2),(18,89,'119.132.74.4','2011-12-16',2),(19,80,'119.132.74.4','2011-12-16',2),(20,80,'119.132.74.4','2011-12-16',1),(21,106,'119.132.75.163','2011-12-19',2),(22,99,'119.132.75.163','2011-12-19',2),(23,109,'119.132.75.163','2011-12-19',2),(24,108,'119.132.75.163','2011-12-19',2),(25,89,'119.132.75.163','2011-12-19',2),(26,75,'119.132.75.163','2011-12-19',2),(27,107,'119.132.75.163','2011-12-19',2),(28,98,'119.132.75.163','2011-12-19',2),(29,96,'119.132.75.163','2011-12-19',2),(30,95,'119.132.75.163','2011-12-19',2),(31,94,'119.132.75.163','2011-12-19',2),(32,93,'119.132.75.163','2011-12-19',2),(33,92,'119.132.75.163','2011-12-19',2),(34,91,'119.132.75.163','2011-12-19',2),(35,90,'119.132.75.163','2011-12-19',2),(36,88,'119.132.75.163','2011-12-19',2),(37,87,'119.132.75.163','2011-12-19',2),(38,85,'119.132.75.163','2011-12-19',2),(39,84,'119.132.75.163','2011-12-19',2),(40,83,'119.132.75.163','2011-12-19',2),(41,78,'119.132.75.163','2011-12-19',2),(42,78,'119.132.75.163','2011-12-19',1),(43,88,'119.132.75.163','2011-12-19',2),(44,83,'119.132.75.163','2011-12-19',2),(45,92,'119.132.75.163','2011-12-19',2),(46,105,'119.132.75.163','2011-12-19',2),(47,104,'119.132.75.163','2011-12-19',2),(48,103,'119.132.75.163','2011-12-19',2),(49,102,'119.132.75.163','2011-12-19',2),(50,101,'119.132.75.163','2011-12-19',2),(51,100,'119.132.75.163','2011-12-19',2),(52,103,'119.132.75.163','2011-12-19',2),(53,103,'119.132.75.163','2011-12-19',2),(54,88,'119.132.75.163','2011-12-19',2),(55,83,'119.132.75.163','2011-12-19',2),(56,78,'119.132.75.163','2011-12-19',2),(57,105,'119.132.75.163','2011-12-19',2),(58,96,'119.132.75.163','2011-12-19',2),(59,99,'119.132.75.163','2011-12-19',2),(60,98,'119.132.75.163','2011-12-19',2),(61,104,'119.132.75.163','2011-12-19',2),(62,102,'119.132.75.163','2011-12-19',2),(63,101,'119.132.75.163','2011-12-19',2),(64,100,'119.132.75.163','2011-12-19',2),(65,95,'119.132.75.163','2011-12-19',2),(66,94,'119.132.75.163','2011-12-19',2),(67,93,'119.132.75.163','2011-12-19',2),(68,90,'119.132.75.163','2011-12-19',2),(69,91,'119.132.75.163','2011-12-19',1),(70,91,'119.132.75.163','2011-12-19',2),(71,84,'119.132.75.163','2011-12-19',2),(72,75,'59.41.240.208','2011-12-22',1),(73,102,'59.41.240.208','2011-12-22',1),(74,105,'125.89.9.159','2011-12-22',2),(75,104,'125.89.9.159','2011-12-22',2),(76,109,'125.89.9.159','2011-12-22',2),(77,75,'125.89.9.159','2011-12-22',2),(78,102,'125.89.9.159','2011-12-22',2),(79,109,'117.136.31.147','2011-12-23',1),(80,89,'125.89.9.159','2011-12-26',2),(81,101,'125.89.9.159','2011-12-26',2),(82,92,'125.89.11.175','2011-12-26',2),(83,93,'125.89.11.175','2011-12-26',2),(84,94,'125.89.11.175','2011-12-26',2),(85,83,'125.89.11.175','2011-12-26',2),(86,95,'125.89.11.175','2011-12-26',2),(87,92,'125.89.11.175','2011-12-26',2),(88,108,'125.89.11.175','2011-12-26',2),(89,104,'125.89.11.175','2011-12-27',2),(90,101,'125.89.11.175','2011-12-27',2),(91,111,'125.89.11.175','2011-12-27',2),(92,111,'125.89.11.175','2011-12-27',2),(93,109,'125.89.11.175','2011-12-27',2),(94,105,'113.108.150.132','2011-12-28',1),(95,107,'113.108.150.132','2011-12-28',2),(96,99,'113.108.150.132','2011-12-28',2),(97,109,'125.89.10.119','2011-12-28',1),(98,105,'125.89.10.119','2011-12-29',1),(99,109,'125.89.10.119','2011-12-29',2),(100,108,'125.89.10.119','2011-12-29',2),(101,98,'59.42.126.20','2012-01-05',1),(102,109,'59.42.126.20','2012-01-05',1),(103,106,'59.42.126.20','2012-01-05',2),(104,107,'119.132.75.226','2012-01-06',2),(105,107,'119.132.75.226','2012-01-06',2),(106,107,'119.132.75.226','2012-01-06',2),(107,108,'119.132.75.226','2012-01-06',2),(108,106,'119.132.75.226','2012-01-06',2),(109,107,'119.132.75.226','2012-01-06',2),(110,106,'119.132.75.226','2012-01-06',2),(111,107,'119.132.75.226','2012-01-06',2),(112,107,'119.132.75.226','2012-01-06',2),(113,100,'119.132.75.226','2012-01-06',2),(114,107,'119.132.72.92','2012-01-06',2),(115,109,'119.132.72.92','2012-01-06',2),(116,107,'113.76.160.167','2012-01-08',2),(117,109,'113.76.160.167','2012-01-08',2),(118,89,'119.132.74.188','2012-01-09',2),(119,89,'119.132.74.188','2012-01-09',2),(120,100,'119.132.74.188','2012-01-09',2),(121,89,'119.132.74.188','2012-01-09',2),(122,100,'119.132.74.188','2012-01-09',2),(123,104,'119.132.75.202','2012-01-10',2),(124,109,'119.132.73.18','2012-01-16',1),(125,109,'119.132.73.18','2012-01-16',1),(126,104,'59.42.126.20','2012-01-16',2),(127,92,'219.131.193.214','2012-01-18',2),(128,93,'183.45.8.93','2012-01-23',2),(129,89,'183.45.8.93','2012-01-23',2),(130,83,'183.45.8.93','2012-01-23',2),(131,94,'183.45.81.38','2012-01-24',2),(132,94,'183.33.167.204','2012-01-27',2),(133,93,'183.33.167.204','2012-01-27',2),(134,92,'183.33.165.40','2012-01-29',2),(135,94,'183.33.165.40','2012-01-29',2),(136,93,'183.33.165.40','2012-01-29',2),(137,109,'119.132.103.52','2012-01-30',2),(138,107,'119.132.103.52','2012-01-30',2),(139,108,'119.132.103.52','2012-01-30',2),(140,106,'119.132.72.199','2012-01-31',2),(141,89,'59.42.126.24','2012-02-03',2),(142,89,'59.42.126.24','2012-02-03',2),(143,89,'59.42.126.24','2012-02-03',2),(144,106,'59.42.126.24','2012-02-03',2),(145,89,'59.42.126.24','2012-02-03',2),(146,106,'59.42.126.24','2012-02-03',2),(147,89,'59.42.126.24','2012-02-03',2),(148,109,'119.132.72.231','2012-02-03',2),(149,98,'119.132.72.231','2012-02-03',1),(150,106,'59.42.126.24','2012-02-03',2),(151,89,'59.42.126.24','2012-02-03',2),(152,112,'59.42.126.24','2012-02-03',2),(153,89,'59.42.126.24','2012-02-03',2),(154,93,'113.76.162.49','2012-02-06',2),(155,106,'119.132.74.49','2012-02-06',2),(156,106,'119.132.74.49','2012-02-06',2),(157,108,'119.132.74.49','2012-02-06',2),(158,108,'119.132.74.49','2012-02-06',2),(159,89,'119.132.74.49','2012-02-06',2),(160,89,'119.132.74.49','2012-02-06',2),(161,106,'119.132.74.49','2012-02-06',2),(162,89,'119.132.74.49','2012-02-06',2),(163,106,'119.132.74.49','2012-02-06',2),(164,89,'119.132.74.49','2012-02-06',2),(165,106,'119.132.74.49','2012-02-06',2),(166,100,'119.132.74.49','2012-02-06',2),(167,94,'218.13.230.253','2012-02-06',2),(168,100,'119.132.74.49','2012-02-06',2),(169,109,'119.132.74.49','2012-02-06',2),(170,105,'119.132.74.49','2012-02-06',2),(171,90,'119.132.74.49','2012-02-06',2),(172,109,'119.132.74.49','2012-02-06',1),(173,107,'119.132.74.49','2012-02-06',2),(174,109,'119.132.74.49','2012-02-06',2),(175,94,'119.132.74.49','2012-02-06',2),(176,107,'119.132.74.49','2012-02-06',2),(177,93,'119.132.74.49','2012-02-06',2),(178,109,'14.116.115.217','2012-02-07',2),(179,108,'14.116.115.217','2012-02-07',2),(180,104,'14.116.115.217','2012-02-07',2),(181,107,'14.116.115.217','2012-02-07',2),(182,106,'14.116.115.217','2012-02-07',2),(183,89,'14.116.115.217','2012-02-07',2),(184,89,'113.74.65.120','2012-02-07',2),(185,93,'119.132.96.96','2012-02-07',2),(186,107,'119.132.96.96','2012-02-07',2),(187,109,'119.132.96.96','2012-02-07',2),(188,93,'119.132.96.96','2012-02-07',2),(189,89,'119.132.74.49','2012-02-07',2),(190,100,'119.132.74.49','2012-02-07',2),(191,105,'119.132.74.49','2012-02-07',2),(192,108,'119.132.74.49','2012-02-07',2),(193,106,'119.132.74.49','2012-02-07',2),(194,89,'119.132.74.49','2012-02-07',2),(195,105,'119.132.74.49','2012-02-07',2),(196,106,'119.132.74.49','2012-02-07',2),(197,89,'59.42.126.27','2012-02-07',2),(198,84,'59.42.126.27','2012-02-07',2),(199,109,'119.132.74.49','2012-02-07',2),(200,106,'119.132.74.49','2012-02-07',2),(201,107,'119.132.73.251','2012-02-07',2),(202,102,'119.132.73.251','2012-02-08',2),(203,104,'119.132.73.251','2012-02-08',2),(204,103,'119.132.73.251','2012-02-08',2),(205,101,'119.132.73.251','2012-02-08',2),(206,98,'119.132.73.251','2012-02-08',2),(207,109,'115.171.15.102','2012-02-08',1),(208,109,'115.171.15.102','2012-02-08',1),(209,101,'119.132.73.251','2012-02-08',2),(210,103,'119.132.73.251','2012-02-08',2),(211,106,'119.132.73.251','2012-02-08',2),(212,102,'119.132.73.251','2012-02-08',2),(213,99,'119.132.73.251','2012-02-08',2),(214,100,'119.132.73.251','2012-02-08',2),(215,105,'119.132.73.251','2012-02-08',2),(216,89,'119.132.73.251','2012-02-08',2),(217,101,'119.132.73.251','2012-02-08',2),(218,105,'113.76.163.25','2012-02-10',2),(219,102,'113.76.163.25','2012-02-10',2),(220,100,'113.108.150.160','2012-02-10',2),(221,108,'125.89.11.222','2012-02-13',2),(222,107,'125.89.11.222','2012-02-13',2),(223,89,'125.89.11.222','2012-02-13',2),(224,109,'125.89.11.222','2012-02-13',2),(225,106,'125.89.11.222','2012-02-13',2),(226,106,'125.89.11.222','2012-02-13',2),(227,109,'125.89.11.222','2012-02-13',2),(228,107,'125.89.11.222','2012-02-13',2),(229,75,'125.89.11.222','2012-02-13',2),(230,102,'125.89.11.222','2012-02-13',2),(231,93,'125.89.11.222','2012-02-13',2),(232,94,'125.89.11.222','2012-02-13',2),(233,99,'125.89.11.222','2012-02-13',2),(234,75,'125.89.11.222','2012-02-13',2),(235,109,'125.89.11.222','2012-02-13',2),(236,75,'125.89.11.222','2012-02-13',2),(237,107,'125.89.11.222','2012-02-13',2),(238,75,'125.89.11.222','2012-02-13',2),(239,109,'125.89.11.222','2012-02-13',2),(240,94,'125.89.11.222','2012-02-13',2),(241,105,'125.89.11.222','2012-02-13',2),(242,89,'125.89.11.222','2012-02-13',2),(243,105,'125.89.11.222','2012-02-13',2),(244,104,'218.13.230.230','2012-02-15',2),(245,98,'183.62.12.28','2012-02-16',1),(246,98,'183.62.12.28','2012-02-16',1),(247,109,'183.62.12.28','2012-02-16',2),(248,108,'183.62.12.28','2012-02-16',2),(249,104,'183.62.12.28','2012-02-16',2),(250,105,'125.89.11.222','2012-02-16',2),(251,104,'125.89.11.222','2012-02-16',2),(252,98,'180.153.206.28','2012-02-16',1),(253,109,'219.131.193.214','2012-02-16',2),(254,109,'219.131.193.214','2012-02-16',2),(255,109,'113.76.236.56','2012-02-17',2),(256,109,'113.76.236.56','2012-02-17',2),(257,109,'113.76.236.56','2012-02-17',2),(258,109,'113.76.236.56','2012-02-17',2),(259,109,'113.76.236.56','2012-02-17',2),(260,109,'113.76.236.56','2012-02-17',2),(261,109,'113.76.236.56','2012-02-17',2),(262,109,'113.76.236.56','2012-02-17',2),(263,109,'113.76.236.56','2012-02-17',2),(264,109,'113.76.236.56','2012-02-17',2),(265,109,'113.76.236.56','2012-02-17',2),(266,109,'113.76.236.56','2012-02-17',2),(267,109,'113.76.236.56','2012-02-17',2),(268,104,'113.76.236.56','2012-02-17',2),(269,109,'113.76.236.56','2012-02-17',2),(270,75,'59.42.126.27','2012-02-17',1),(271,104,'219.131.193.214','2012-02-17',2),(272,109,'219.131.193.214','2012-02-17',2),(273,107,'119.132.73.219','2012-02-17',2),(274,98,'119.132.73.219','2012-02-17',2),(275,105,'119.132.73.219','2012-02-17',2),(276,105,'219.131.193.214','2012-02-17',2),(277,106,'119.132.73.219','2012-02-17',2),(278,108,'59.42.126.26','2012-02-17',2),(279,78,'59.42.126.26','2012-02-17',2),(280,106,'119.132.73.219','2012-02-17',2),(281,106,'119.132.73.219','2012-02-20',2),(282,109,'119.132.73.219','2012-02-20',2),(283,100,'119.132.73.219','2012-02-20',2),(284,103,'119.132.73.219','2012-02-20',2),(285,98,'119.132.73.219','2012-02-20',2),(286,93,'119.132.73.219','2012-02-20',2);

/*Table structure for table `vcity_manualchatinfo` */

DROP TABLE IF EXISTS `vcity_manualchatinfo`;

CREATE TABLE `vcity_manualchatinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskID` int(11) DEFAULT NULL,
  `chatTime` varchar(20) DEFAULT NULL,
  `chatFrom` varchar(20) DEFAULT NULL,
  `charTo` varchar(20) DEFAULT NULL,
  `charInfo` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=311 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_manualchatinfo` */

insert  into `vcity_manualchatinfo`(`id`,`taskID`,`chatTime`,`chatFrom`,`charTo`,`charInfo`) values (1,26,'2011-09-03 11:47:06','1','70','你好！'),(2,26,'2011-09-03 11:47:15','1','70','有人在吗？'),(3,26,'2011-09-03 11:47:23','1','70','出来</P>'),(4,26,'2011-09-03 11:48:47','1','70','很好很强大~~~~'),(5,26,'2011-09-03 11:49:04','1','70','1212121212'),(6,26,'2011-09-03 11:49:08','1','70','千万地区我的千万地区单位千万地区我的'),(7,26,'2011-09-03 11:49:12','1','70','下大雨枯干非走不可二'),(8,26,'2011-09-03 12:50:41','1','99','2222</P>'),(9,26,'2011-09-03 12:50:43','1','99','33333333'),(10,26,'2011-09-03 12:50:45','1','99','11111'),(11,26,'2011-09-03 12:50:47','1','99','aaaaaa'),(12,26,'2011-09-03 12:50:49','1','99','222'),(13,26,'2011-09-03 12:50:57','1','99','adfadfasdfadsfadfadsfffffffffffffffwwwwwwwww21316519618581851'),(14,26,'2011-09-03 12:57:39','1','99','aaaaaaaaaaaaaaaaaaaa'),(15,26,'2011-09-03 12:57:41','1','99','ffffffffffffffffffffffffffffff'),(16,26,'2011-09-03 12:57:44','1','99','eeeeeeeeeeeeeeeeeeeee'),(17,26,'2011-09-03 12:57:46','1','99','wwwwwwwwwwwwwwwww'),(18,26,'2011-09-03 12:57:48','1','99','本次交流任务已完成，感谢您的参与！'),(19,26,'2011-09-03 13:02:19','1','99','aaaaaaaaaaaaaaawwwwwwww'),(20,26,'2011-09-03 13:02:22','1','99','rrrrrrrrreeeeeee'),(21,26,'2011-09-03 13:02:23','1','99','本次交流任务已完成，感谢您的参与！'),(22,26,'2011-09-03 13:02:36','1','99','wwwwwwwwwwwwwwww'),(23,26,'2011-09-03 13:02:39','1','99','rrrrrrrrr333333'),(24,26,'2011-09-03 13:03:23','1','99','111111111'),(25,26,'2011-09-03 13:03:25','1','99','本次交流任务已完成，感谢您的参与！'),(26,26,'2011-09-03 13:04:09','1','70','11111111111'),(27,26,'2011-09-03 14:24:15','1','70','aaaaaaaaaaaffdfdfdfdfdf'),(28,26,'2011-09-03 14:24:20','1','70','1111111111111111111111111111111111111'),(29,26,'2011-09-03 14:31:17','1','99','111111111111111'),(30,26,'2011-09-03 14:50:30','1','99','awefaffefawef'),(31,26,'2011-09-03 15:23:14','1','99','222222'),(32,26,'2011-09-03 17:19:38','1','70','1111'),(33,26,'2011-09-03 17:19:41','1','70','2222222222'),(34,26,'2011-09-09 19:20:35','1','70','aaadfadfafafaa'),(35,26,'2011-09-09 19:20:52','1','70','奥威尔份额啊沃尔夫'),(36,26,'2011-09-09 19:21:10','1','70','爱爱爱玩儿法沃尔夫'),(37,26,'2011-09-09 19:23:13','1','99','awewaefawef'),(38,26,'2011-09-09 19:23:17','1','99','rewererwerrwerwer'),(39,26,'2011-09-09 19:23:19','1','99','aaaaaaaaaaa'),(40,26,'2011-09-09 19:23:23','1','99','vfbfbdfbdfsdfsdfbsdfb'),(41,26,'2011-09-09 19:23:26','1','99','awefawefawefaewef'),(42,26,'2011-09-09 19:23:30','1','99','333333334444444'),(43,26,'2011-09-09 19:26:01','1','70','awefawefawefawef'),(44,26,'2011-09-09 19:26:04','1','70','sdfsdfsdfsdsf'),(45,26,'2011-09-09 19:26:17','1','70','请参与XXX产品模型， <a href=\'http://www.baidu.com\' target=\'_blank\'>点击这里</a>'),(46,26,'2011-09-09 19:26:50','1','70','本次交流任务已完成，感谢您的参与！'),(47,26,'2011-09-16 19:44:33','1','70','aaaasdfaefawefaefawef'),(48,26,'2011-09-16 19:44:36','1','70','fawefawefawef'),(49,28,'2011-09-16 20:58:40','1','70','awefawfw'),(50,28,'2011-09-16 20:58:43','1','70','ffffffewewef'),(51,28,'2011-09-16 20:58:55','1','70','邀请您参与XXX模型， <a href=\'http://www.baidu.com\' target=\'_blank\'>进入体验>></a>'),(52,28,'2011-09-16 20:59:10','1','70','邀请您参与XXX模型， <a href=\'http://www.baidu.com\' target=\'_blank\'>进入体验>></a>'),(53,29,'2011-09-20 16:27:11','1','99','tstte'),(54,29,'2011-09-20 16:29:15','1','70','daf </P>\r\n<P>asf </P>'),(55,29,'2011-09-20 16:29:20','1','70','sa '),(56,29,'2011-09-20 16:29:32','1','70','有滴慢</P>'),(57,1,'2011-09-22 15:01:11','1','99','xxxx'),(58,1,'2011-09-22 15:01:54','1','99','tset'),(59,1,'2011-09-22 15:02:06','1','99','test'),(60,2,'2011-09-22 16:52:17','','','123</P>\r\n<P> </P>'),(61,1,'2011-09-23 14:24:23','1','99','<img src=\"/bbs/vcity/inc/plugins/emoticons/2.gif\" alt=\"\" /><br />'),(62,2,'2011-09-23 14:27:12','','','123'),(63,2,'2011-09-23 15:34:31','99','','s </P>\r\n<P> </P>'),(64,2,'2011-09-23 15:35:41','1','99','<p>你好吗</p>\r\n<p> </p>'),(65,2,'2011-09-23 15:35:51','1','99','<p>你好吗</p>\r\n<p> </p>'),(66,2,'2011-09-23 15:35:52','1','99','<p>你好吗</p>\r\n<p> </p>'),(67,2,'2011-09-23 15:35:52','1','99','<p>你好吗</p>\r\n<p> </p>'),(68,2,'2011-09-23 15:42:05','1','99','<p>很好</p>'),(69,2,'2011-09-23 15:42:10','1','99','<p>很好</p>'),(70,2,'2011-09-23 15:49:51','1','99','邀请您参与临时问卷调查， <a href=\"/bbs/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=27,28,30,31,29&taskid=3\" target=\"_blank\">进入调查</a>'),(71,2,'2011-09-23 15:51:32','1','99','<p>很好1</p>'),(72,2,'2011-09-23 15:51:38','1','99','<p>很好1</p>'),(73,1,'2011-09-24 15:10:45','','70','vvvvvv'),(74,1,'2011-09-24 15:10:53','','70','vsdvsdv'),(75,1,'2011-09-24 15:11:13','','70','vvsdvsdvs'),(76,1,'2011-09-24 15:11:58','','70','<img alt=\"\" src=\"/bbs/vcity/inc/plugins/emoticons/2.gif\" />'),(77,1,'2011-09-24 15:12:15','','70','<img alt=\"\" src=\"/bbs/vcity/inc/plugins/emoticons/11.gif\" />'),(78,1,'2011-09-24 15:14:34','','70','sdfsdf'),(79,1,'2011-09-24 15:14:42','','70','ffffffffffffffffffffffffffffffffff'),(80,1,'2011-09-24 15:40:25','','','<p>sa</p>'),(81,1,'2011-09-24 15:42:58','99','','<p>hello</p>\r\n<p> </p>'),(82,1,'2011-09-24 15:43:11','1','','tet'),(83,1,'2011-09-24 15:43:13','99','','<img alt=\"\" src=\"/bbs/vcity/inc/plugins/emoticons/13.gif\" />'),(84,1,'2011-09-24 15:43:53','1','','11111111111111111111111111111'),(85,1,'2011-09-24 15:46:39','1','99','test'),(86,1,'2011-09-24 15:46:54','99','','<p>收到</p>\r\n<p> </p>'),(87,1,'2011-09-24 15:47:00','1','99','tttttttttttttttttttttttttttttttttttttttttttt'),(88,1,'2011-09-24 15:47:20','1','99','888888888888888888888888888'),(89,1,'2011-09-24 15:47:22','99','','<p>hello</p>'),(90,1,'2011-09-24 15:47:26','1','99','test'),(91,1,'2011-09-24 15:47:31','99','','你好'),(92,1,'2011-09-24 15:48:03','1','99','sfsdfsdfdf'),(93,1,'2011-09-24 15:48:32','1','99','邀请您参与临时问卷调查， <a href=\"/bbs/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,2,24&taskid=8\" target=\"_blank\">进入调查</a>'),(94,1,'2011-09-24 15:49:42','70','','<img alt=\"\" src=\"/bbs/vcity/inc/plugins/emoticons/3.gif\" />'),(95,1,'2011-09-24 15:50:48','1','70','haha'),(96,1,'2011-09-24 15:51:04','1','70','邀请您参与临时问卷调查， <a href=\"/bbs/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,3&taskid=9\" target=\"_blank\">进入调查</a>'),(97,1,'2011-09-24 15:51:32','70','','<p><img alt=\"\" src=\"/bbs/vcity/inc/plugins/emoticons/78.gif\" /></p>'),(98,1,'2011-09-24 15:51:36','1','70','<p>看看答题是否有问题</p>'),(99,1,'2011-09-24 15:51:57','70','','<p>点击答题之后，直接返回一个空白页面了</p>'),(100,1,'2011-09-24 15:55:31','99','','<img alt=\"\" src=\"/bbs/vcity/inc/plugins/emoticons/13.gif\" />'),(101,1,'2011-09-24 16:30:08','1','70','邀请您参与临时问卷调查， <a href=\"/bbs/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=29&taskid=10\" target=\"_blank\">进入调查</a>'),(102,2,'2011-09-24 18:33:07','1','99','<p>你好</p>\r\n<p> </p>'),(103,2,'2011-09-24 18:33:44','99','','<img alt=\"\" src=\"/bbs/vcity/inc/plugins/emoticons/13.gif\" />'),(104,2,'2011-09-24 18:34:03','1','99','<p>笑p啊</p>'),(105,2,'2011-09-24 18:34:24','99','','<strong>g df吃饭</strong>'),(106,2,'2011-09-24 18:34:34','99','','<span style=\"font-size:32px;\">第三方刚</span>'),(107,2,'2011-09-24 18:35:31','99','','按时'),(108,2,'2011-09-24 18:37:40','1','99','本次交流任务已完成，感谢您的参与!'),(109,2,'2011-09-27 14:25:46','1','70','eeeeee<br />'),(110,2,'2011-09-27 14:25:59','70','','<img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /><br />'),(111,2,'2011-09-27 14:26:03','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(112,2,'2011-09-27 14:26:07','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(113,2,'2011-09-27 14:26:11','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(114,2,'2011-09-27 14:26:14','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(115,2,'2011-09-27 14:26:18','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(116,2,'2011-09-27 14:26:21','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(117,2,'2011-09-27 14:26:24','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(118,2,'2011-09-27 14:26:27','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(119,2,'2011-09-27 14:26:29','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/10.gif\" alt=\"\" /></div>'),(120,2,'2011-09-27 14:26:36','70','','<div style=\"margin-top:0px;margin-right:0px;margin-bottom:0px;margin-left:0px;padding-top:5px;padding-right:5px;padding-bottom:5px;padding-left:5px;background-color:#ffffff;color:#000000;font:normal normal normal 12px/1.5 \'sans serif\', tahoma, verdana, helvetica;font-family:Simsun;line-height:normal;font-size:medium;\"><img src=\"/bbs/vcity/inc/plugins/emoticons/9.gif\" alt=\"\" /><br />\n</div>'),(121,2,'2011-09-27 14:26:42','70','','<img src=\"/bbs/vcity/inc/plugins/emoticons/28.gif\" alt=\"\" /><br />'),(122,2,'2011-09-27 14:26:47','70','','<img src=\"/bbs/vcity/inc/plugins/emoticons/30.gif\" alt=\"\" /><br />'),(123,2,'2011-09-27 14:26:54','1','70','本次交流任务已完成，感谢您的参与!'),(124,2,'2011-09-27 14:31:42','1','70','邀请您参与 诺基亚N97  模型， <a href=\"/bbs/vcity/produceOrder/produceOrder.jsp?file=/vcity/produceOrder/n97&resid=4&cTask=11\" target=\"_blank\">进入体验</a>'),(125,2,'2011-09-27 14:46:41','1','70','邀请您参与 诺基亚N97  模型， <a href=\"/bbs/vcity/produceOrder/produceOrder.jsp?file=/vcity/produceOrder/n97&resid=4&cTask=12\" target=\"_blank\">进入体验</a>'),(126,2,'2011-09-27 14:48:02','1','70','邀请您参与临时问卷调查， <a href=\"/bbs/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=8,9,27,30,28,31,29&taskid=13\" target=\"_blank\">进入调查</a>'),(127,2,'2011-09-27 19:51:29','1','','<p>a</p>\r\n<p> </p>'),(128,2,'2011-09-27 20:01:31','1','99','<p>22</p>'),(129,2,'2011-09-27 20:01:36','1','99','sd'),(130,2,'2011-09-27 20:01:49','99','','sfsdf'),(131,2,'2011-09-28 15:29:33','99','','你好'),(132,2,'2011-09-28 15:30:06','1','99','您好，欢迎参加本次社区任务。<br>'),(133,2,'2011-09-28 15:31:23','1','99','邀请您参与 诺基亚N97  模型， <a href=\"/bbs/vcity/produceOrder/produceOrder.jsp?file=/vcity/produceOrder/n97&resid=4&cTask=14\" target=\"_blank\">进入体验</a>'),(134,2,'2011-09-28 15:31:39','1','99','邀请您参与临时问卷调查， <a href=\"/bbs/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=27,30,28,31,29&taskid=15\" target=\"_blank\">进入调查</a>'),(135,2,'2011-09-28 17:33:34','1','70','sdfsdfsdf'),(136,2,'2011-09-28 17:33:43','1','70','nihao'),(137,2,'2011-09-29 10:46:37','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/9.gif\" /><br>'),(138,2,'2011-09-29 10:47:27','1','70','本次交流任务已完成，感谢您的参与!'),(139,2,'2011-09-29 10:48:07','1','70','本次交流任务已完成，感谢您的参与!'),(140,2,'2011-09-29 10:49:23','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/19.gif\" /><br>'),(141,2,'2011-09-29 10:49:29','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/18.gif\" />'),(142,2,'2011-09-29 10:49:35','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/36.gif\" />'),(143,2,'2011-09-29 10:49:43','1','70','本次交流任务已完成，感谢您的参与!'),(144,2,'2011-09-29 11:34:29','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/10.gif\" />'),(145,2,'2011-09-29 11:45:21','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=55,56&task=16\" target=\"_blank\">进入调查</a>'),(146,2,'2011-09-29 12:57:58','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/28.gif\" /><br>'),(147,2,'2011-09-29 15:26:18','1','99','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=55,56&task=17\" target=\"_blank\">进入调查</a>'),(148,2,'2011-09-29 15:56:40','1','99','邀请您参与 诺基亚N97  模型， <a href=\"/bbs2/vcity/produceOrder/produceOrder.jsp?file=/vcity/produceOrder/n97&resid=4&cTask=14\" target=\"_blank\">进入体验</a>'),(149,2,'2011-09-29 17:06:45','1','99','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/9.gif\" />'),(150,2,'2011-09-29 17:06:55','99','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/19.gif\" alt=\"\" /><br />'),(151,2,'2011-09-29 17:07:13','99','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/40.gif\" alt=\"\" /><br />'),(152,2,'2011-09-29 17:07:22','1','99','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/86.gif\" />'),(153,2,'2011-09-29 17:07:30','1','99','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/118.gif\" />'),(154,2,'2011-09-29 17:07:44','99','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/87.gif\" alt=\"\" /><br />'),(155,2,'2011-09-29 17:17:48','1','99','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/11.gif\" />'),(156,2,'2011-09-29 17:53:42','1','99','邀请您参与 lenovoi909  模型， <a href=\"/bbs2/vcity/show3D/show.jsp?file=/uploadfile/591f39e6-295d-44e3-a923-583d79271545.unity3d&resid=16&cTask=19\" target=\"_blank\">进入体验</a>'),(157,2,'2011-09-29 17:53:42','1','99','邀请您参与 诺基亚N97  模型， <a href=\"/bbs2/vcity/produceOrder/produceOrder.jsp?file=/vcity/produceOrder/n97&resid=4&cTask=14\" target=\"_blank\">进入体验</a>'),(158,2,'2011-09-29 17:53:52','1','99','test'),(159,2,'2011-09-30 11:54:51','1','99','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/13.gif\" /><br>'),(160,2,'2011-09-30 11:54:59','1','99','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/10.gif\" />'),(161,2,'2011-09-30 11:55:14','99','','你好呀！<br />'),(162,2,'2011-09-30 11:55:40','1','99','还不错，下面请你参加一下体验模型任务。<br>'),(163,2,'2011-09-30 11:55:45','1','99','邀请您参与 诺基亚N97  模型， <a href=\"/bbs2/vcity/produceOrder/produceOrder.jsp?file=/vcity/produceOrder/n97&resid=4&cTask=14\" target=\"_blank\">进入体验</a>'),(164,2,'2011-09-30 11:55:56','99','','嗯，好的<br />'),(165,2,'2011-09-30 11:56:19','1','99','完成之后，请再完成这份调查。'),(166,2,'2011-09-30 11:56:24','1','99','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=55,56&task=17\" target=\"_blank\">进入调查</a>'),(167,2,'2011-09-30 11:56:32','99','','行<br />'),(168,2,'2011-10-09 14:50:11','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=55,56&task=16\" target=\"_blank\">进入调查</a>'),(169,11,'2011-10-11 09:05:55','1','70','邀请您参与 lenovoi909  模型， <a href=\"/bbs2/vcity/show3D/show.jsp?file=/uploadfile/591f39e6-295d-44e3-a923-583d79271545.unity3d&resid=16&cTask=46\" target=\"_blank\">进入体验</a>'),(170,11,'2011-10-11 09:26:57','1','70','邀请您参与 诺基亚N97  模型， <a href=\"/bbs2/vcity/produceOrder/produceOrder.jsp?file=/vcity/produceOrder/n97&resid=4&cTask=47\" target=\"_blank\">进入体验</a>'),(171,12,'2011-10-11 11:06:01','1','70','邀请您参与 摩托罗拉xt800  模型， <a href=\"/bbs2/vcity/show3D/show.jsp?file=/uploadfile/7601100e-2fff-4cbf-a6c3-5f62ffd49d9b.unity3d&resid=11&cTask=48\" target=\"_blank\">进入体验</a>'),(172,12,'2011-10-11 11:10:23','1','70','邀请您参与 摩托罗拉xt800  模型， <a href=\"/bbs2/vcity/show3D/show.jsp?file=/uploadfile/7601100e-2fff-4cbf-a6c3-5f62ffd49d9b.unity3d&resid=11&cTask=48\" target=\"_blank\">进入体验</a>'),(173,12,'2011-10-11 11:11:10','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,2,3,24&task=50\" target=\"_blank\">进入调查</a>'),(174,11,'2011-10-12 17:10:14','1','70','test'),(175,11,'2011-10-12 17:39:46','1','70','本次交流任务已完成，感谢您的参与!'),(176,11,'2011-10-12 17:49:41','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=51\" target=\"_blank\">进入调查</a>'),(177,11,'2011-10-12 18:00:36','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=51\" target=\"_blank\">进入调查</a>'),(178,11,'2011-10-12 18:01:43','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=51\" target=\"_blank\">进入调查</a>'),(179,11,'2011-10-12 18:03:29','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=51\" target=\"_blank\">进入调查</a>'),(180,11,'2011-10-12 18:07:54','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=51\" target=\"_blank\">进入调查</a>'),(181,11,'2011-10-12 18:15:46','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=51\" target=\"_blank\">进入调查</a>'),(182,11,'2011-10-12 18:16:01','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=51\" target=\"_blank\">进入调查</a>'),(183,11,'2011-10-21 17:08:36','1','70','本次交流任务已完成，感谢您的参与!'),(184,11,'2011-11-11 15:10:28','1','70','test'),(185,11,'2011-11-11 15:11:04','70','','xxxxx<br />'),(186,11,'2011-11-11 15:11:11','70','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/9.gif\" alt=\"\" />'),(187,11,'2011-11-11 15:11:24','1','70','邀请您参与 lenovoi909  模型， <a href=\"/bbs2/vcity/show3D/show.jsp?file=/uploadfile/591f39e6-295d-44e3-a923-583d79271545.unity3d&resid=16&cTask=46\" target=\"_blank\">进入体验</a>'),(188,11,'2011-11-11 15:11:37','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=80\" target=\"_blank\">进入调查</a>'),(189,11,'2011-11-11 15:15:41','1','70','111'),(190,11,'2011-11-11 15:16:30','1','70','本次交流任务已完成，感谢您的参与!'),(191,11,'2011-11-21 09:45:34','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=80\" target=\"_blank\">进入调查</a>'),(192,11,'2011-11-21 09:50:10','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=80\" target=\"_blank\">进入调查</a>'),(193,11,'2011-11-21 09:52:36','70','','test<br />'),(194,14,'2011-11-21 09:57:25','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=83\" target=\"_blank\">进入调查</a>'),(195,14,'2011-11-21 14:26:47','1','70','本次交流任务已完成，感谢您的参与!'),(196,18,'2011-11-23 11:43:22','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/0.gif\" />'),(197,18,'2011-11-23 13:37:09','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/9.gif\" />'),(198,18,'2011-11-23 13:50:37','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=88\" target=\"_blank\">进入调查</a>'),(199,18,'2011-11-23 14:20:45','1','70','邀请您参与e声网wingo测试问卷调查， <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=6\" target=\"_blank\">进入调查</a>'),(200,18,'2011-11-23 14:21:19','1','70','邀请您参与e声网wingo测试问卷调查， <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=6\" target=\"_blank\">进入调查</a>'),(201,18,'2011-11-23 14:22:02','1','70','邀请您参与e声网新调查问卷调查， <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=1\" target=\"_blank\">进入调查</a>'),(202,18,'2011-11-23 14:33:44','1','70','本次交流任务已完成，感谢您的参与!'),(203,19,'2011-11-24 11:20:07','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=89\" target=\"_blank\">进入调查</a>'),(204,19,'2011-11-25 09:19:16','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=96\" target=\"_blank\">进入调查</a>'),(205,19,'2011-11-28 09:07:47','1','70','本次交流任务已完成，感谢您的参与!'),(206,21,'2011-11-28 14:00:01','70','','124'),(207,21,'2011-11-28 14:02:49','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59&task=97\" target=\"_blank\">进入调查</a>'),(208,19,'2011-11-28 15:48:09','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=98\" target=\"_blank\">进入调查</a>'),(209,19,'2011-11-29 11:56:33','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=99\" target=\"_blank\">进入调查</a>'),(210,19,'2011-11-29 11:56:48','1','70','邀请您参与e声网wingo测试, <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=6\" target=\"_blank\">进入调查</a>'),(211,19,'2011-11-29 11:57:53','1','70','邀请您参与e声网测试文君, <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=8\" target=\"_blank\">进入调查</a>'),(212,19,'2011-11-29 11:59:45','1','70','邀请您参与e声网wingo测试, <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=6\" target=\"_blank\">进入调查</a>'),(213,19,'2011-11-29 12:02:57','1','70','邀请您参与e声网wingo测试, <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=6\" target=\"_blank\">进入调查</a>'),(214,23,'2011-12-06 11:56:16','68','70','hello<br>\r\n <br>'),(215,23,'2011-12-06 11:56:29','70','','你好啊<br>\r\n <br>'),(216,23,'2011-12-06 11:56:58','68','70','邀请您参与e声网新调查, <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=1\" target=\"_blank\">进入调查</a>'),(217,23,'2011-12-06 12:02:12','68','70','123'),(218,23,'2011-12-06 12:02:30','68','','123'),(219,23,'2011-12-06 12:02:44','70','','123'),(220,23,'2011-12-07 09:04:11','68','70','hello<br>\r\n <br>'),(221,23,'2011-12-07 09:04:21','68','70','你好！！'),(222,23,'2011-12-07 09:04:39','70','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/33.gif\" />'),(223,23,'2011-12-07 09:06:32','68','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=62,63,64,65,66&task=109\" target=\"_blank\">进入调查</a>'),(224,23,'2011-12-07 09:08:09','70','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/30.gif\" />回答完成了<br>\r\n <br>'),(225,23,'2011-12-07 09:11:19','68','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=62,63,64,65,66,67,68,69,70&task=110\" target=\"_blank\">进入调查</a>'),(226,27,'2011-12-07 14:00:54','68','70','123123'),(227,27,'2011-12-07 14:01:08','70','','123'),(228,27,'2011-12-07 14:01:24','68','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=67,68,69,70,62,63,64,65,66&task=111\" target=\"_blank\">进入调查</a>'),(229,27,'2011-12-07 14:04:05','68','70','邀请您参与e声网新调查, <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=1\" target=\"_blank\">进入调查</a>'),(230,27,'2011-12-07 14:12:53','68','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=62,64,65,63,66,70,69&task=112\" target=\"_blank\">进入调查</a>'),(231,19,'2011-12-07 18:56:18','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,1,59&task=113\" target=\"_blank\">进入调查</a>'),(232,19,'2011-12-07 19:00:36','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22,1,59,2,3&task=114\" target=\"_blank\">进入调查</a>'),(233,19,'2011-12-09 09:15:52','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=115\" target=\"_blank\">进入调查</a>'),(234,19,'2011-12-09 09:24:33','1','99','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=116\" target=\"_blank\">进入调查</a>'),(235,19,'2011-12-09 10:25:33','1','70','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/10.gif\" />'),(236,19,'2011-12-09 10:25:53','1','99','xlqb_1314'),(237,27,'2011-12-09 13:59:04','1','70','123'),(238,27,'2011-12-09 13:59:28','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=67,68,65,64,63&task=117\" target=\"_blank\">进入调查</a>'),(239,27,'2011-12-09 13:59:58','1','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=69,68,67,70,66,65,62,63,64&task=118\" target=\"_blank\">进入调查</a>'),(240,23,'2011-12-09 14:03:55','68','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=63,64,69,68&task=119\" target=\"_blank\">进入调查</a>'),(241,23,'2011-12-09 14:05:37','68','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=64,66,65&task=120\" target=\"_blank\">进入调查</a>'),(242,23,'2011-12-09 14:09:03','68','70','邀请您参与临时问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=62,63,64,65,66&task=121\" target=\"_blank\">进入调查</a>'),(243,23,'2011-12-09 17:35:51','68','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=62,63,64,65,66&task=122\" target=\"_blank\">进入调查</a>'),(244,23,'2011-12-09 17:53:28','68','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=62,63,64,65,66,67,68,69,70&task=123\" target=\"_blank\">进入调查</a>'),(245,19,'2011-12-12 09:02:59','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=124\" target=\"_blank\">进入调查</a>'),(246,19,'2011-12-12 10:18:46','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3,23,33,38,22&task=125\" target=\"_blank\">进入调查</a>'),(247,19,'2011-12-12 10:29:21','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=126\" target=\"_blank\">进入调查</a>'),(248,19,'2011-12-12 10:33:51','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=33,22,1,59&task=127\" target=\"_blank\">进入调查</a>'),(249,19,'2011-12-12 10:34:03','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=33,22,1,59&task=128\" target=\"_blank\">进入调查</a>'),(250,28,'2011-12-12 10:41:05','1','70','123'),(251,28,'2011-12-12 10:41:55','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=52,53,70,69,68&task=129\" target=\"_blank\">进入调查</a>'),(252,27,'2011-12-12 17:13:29','68','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=67,68,69,70&task=130\" target=\"_blank\">进入调查</a>'),(253,19,'2011-12-13 09:47:36','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=131\" target=\"_blank\">进入调查</a>'),(254,19,'2011-12-13 10:07:59','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=132\" target=\"_blank\">进入调查</a>'),(255,19,'2011-12-13 10:13:52','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=1,59,2,3&task=133\" target=\"_blank\">进入调查</a>'),(256,29,'2011-12-13 10:25:02','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=67,68,69,70&task=134\" target=\"_blank\">进入调查</a>'),(257,30,'2011-12-14 08:57:09','1','70','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=67,68,69,70&task=135\" target=\"_blank\">进入调查</a>'),(258,30,'2011-12-14 11:33:30','1','47','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/28.gif\" />'),(259,30,'2011-12-14 11:33:43','47','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/37.gif\" alt=\"\" />'),(260,30,'2011-12-14 11:33:55','1','47','xxxxxxxxxxxxxxx'),(261,30,'2011-12-14 11:34:10','47','','eeeeeee<br />'),(262,30,'2011-12-14 11:34:14','47','','sssssssssssssss<br />'),(263,30,'2011-12-14 11:34:21','47','','test<br />'),(264,30,'2011-12-14 11:34:35','47','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/19.gif\" alt=\"\" />'),(265,30,'2011-12-14 11:34:41','47','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/27.gif\" alt=\"\" />'),(266,30,'2011-12-14 11:34:47','47','','<img src=\"/bbs2/vcity/inc/plugins/emoticons/36.gif\" alt=\"\" />'),(267,30,'2011-12-14 11:35:05','47','','eee<br />'),(268,30,'2011-12-14 11:42:40','1','47','<a href=\"http://google.com\" target=\"_blank\">http://google.com</a>'),(269,19,'2011-12-15 14:57:45','1','34','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=23,33,38,22&task=142\" target=\"_blank\">进入调查</a>'),(270,19,'2011-12-15 15:08:08','1','34','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=59,2,33,38&task=143\" target=\"_blank\">进入调查</a>'),(271,19,'2011-12-15 15:28:58','1','34','本次交流任务已完成，感谢您的参与!'),(272,30,'2011-12-15 17:22:52','1','47','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=67,68,69,70&task=144\" target=\"_blank\">进入调查</a>'),(273,30,'2011-12-15 17:45:09','1','47','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=67,68,69,70&task=145\" target=\"_blank\">进入调查</a>'),(274,23,'2011-12-16 09:51:43','68','39','邀请您参与e声网wingo测试, <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=6\" target=\"_blank\">进入调查</a>'),(275,23,'2011-12-16 10:15:16','68','39','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=67,68,69,70&task=146\" target=\"_blank\">进入调查</a>'),(276,19,'2011-12-16 11:56:53','1','34','asdf'),(277,19,'2011-12-16 11:56:58','34','','dsffsd'),(278,19,'2011-12-16 11:57:22','1','34','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,82,83&task=147\" target=\"_blank\">进入调查</a>'),(279,19,'2011-12-19 11:25:03','34','','123'),(280,19,'2011-12-19 11:25:22','1','34','21213'),(281,19,'2011-12-19 11:25:31','34','','21321'),(282,19,'2011-12-19 11:25:43','1','34','有点慢'),(283,19,'2011-12-19 11:27:34','1','34','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,82,83&task=148\" target=\"_blank\">进入调查</a>'),(284,19,'2011-12-19 11:42:07','34','','111<br>\r\n1<br>\r\n <br>\r\n <br>'),(285,19,'2011-12-19 11:42:15','34','','1232334'),(286,19,'2011-12-19 11:42:58','1','34','2133214<br>\r\n <br>'),(287,34,'2011-12-20 08:50:25','1','59','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=62,63,64,65,66&task=149\" target=\"_blank\">进入调查</a>'),(288,34,'2011-12-20 08:51:00','1','59','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=73,74,75,76,77,78,79,62,63,64,65,66&task=150\" target=\"_blank\">进入调查</a>'),(289,34,'2011-12-20 08:55:16','1','59','邀请您参与e声网e声网调查, <a href=\"http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId=7\" target=\"_blank\">进入调查</a>'),(290,34,'2011-12-20 09:30:09','1','59','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=73,74,75,76,77,78,79&task=151\" target=\"_blank\">进入调查</a>'),(291,34,'2011-12-20 09:36:32','1','59','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,82,83&task=152\" target=\"_blank\">进入调查</a>'),(292,34,'2011-12-20 09:36:43','1','59','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=52,53,54&task=153\" target=\"_blank\">进入调查</a>'),(293,36,'2011-12-23 11:38:36','1','62','12312312'),(294,36,'2011-12-23 11:39:23','1','62','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,76,77,75,54,66,65,64,70,69&task=162\" target=\"_blank\">进入调查</a>'),(295,36,'2011-12-23 11:44:28','1','62','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,82,83&task=163\" target=\"_blank\">进入调查</a>'),(296,36,'2011-12-23 11:44:37','1','62','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=73,74,75,76,77,78,79&task=164\" target=\"_blank\">进入调查</a>'),(297,36,'2011-12-23 11:49:27','1','62','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,76,77,75,54,66,65,64,70,69&task=165\" target=\"_blank\">进入调查</a>'),(298,38,'2011-12-27 17:15:15','68','65','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/13.gif\" />'),(299,38,'2011-12-27 17:15:26','65','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/42.gif\" />'),(300,38,'2011-12-27 17:15:37','68','65','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,82,83&task=177\" target=\"_blank\">进入调查</a>'),(301,38,'2011-12-27 17:17:32','68','65','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=73,74,75,76,77,78,79,54,53,66,65,64,63&task=178\" target=\"_blank\">进入调查</a>'),(302,38,'2011-12-27 17:20:12','68','65','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,82,83,53,54,66,65,64,63,70,69,68&task=179\" target=\"_blank\">进入调查</a>'),(303,38,'2011-12-27 17:21:45','68','65','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=80,84,81,82,83,53,54,66,65,64,63,70,69,68&task=180\" target=\"_blank\">进入调查</a>'),(304,41,'2011-12-27 17:25:54','68','68','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=73,74,75,76,77,78,79,52,53,54,62,63,64,65,66&task=181\" target=\"_blank\">进入调查</a>'),(305,41,'2011-12-27 17:26:59','68','68','邀请您参与问卷调查， <a href=\"/bbs2/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask&ids=73,74,75,76,77,78,79,53,54&task=182\" target=\"_blank\">进入调查</a>'),(306,45,'2012-02-13 09:39:10','75','','1234'),(307,45,'2012-02-13 09:39:21','1','','123'),(308,45,'2012-02-13 09:39:51','1','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/13.gif\" />'),(309,45,'2012-02-13 09:40:02','75','','<img alt=\"\" src=\"/bbs2/vcity/inc/plugins/emoticons/13.gif\" />'),(310,45,'2012-02-13 09:41:00','1','75','safda');

/*Table structure for table `vcity_manualtasksetting` */

DROP TABLE IF EXISTS `vcity_manualtasksetting`;

CREATE TABLE `vcity_manualtasksetting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskID` int(11) DEFAULT NULL,
  `subTaskType` int(11) DEFAULT NULL,
  `subTaskResID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_manualtasksetting` */

insert  into `vcity_manualtasksetting`(`id`,`taskID`,`subTaskType`,`subTaskResID`) values (135,43,2,32),(136,43,2,35),(137,43,2,31);

/*Table structure for table `vcity_produceorder` */

DROP TABLE IF EXISTS `vcity_produceorder`;

CREATE TABLE `vcity_produceorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) DEFAULT NULL,
  `subTaskID` int(11) DEFAULT NULL,
  `resID` int(11) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_produceorder` */

insert  into `vcity_produceorder`(`id`,`userName`,`subTaskID`,`resID`,`code`) values (1,'127.0.0.1',0,4,'1016'),(2,'127.0.0.1',0,4,'1015'),(3,'127.0.0.1',0,4,'1014'),(4,'127.0.0.1',0,4,'1016'),(5,'127.0.0.1',0,4,'1014'),(6,'127.0.0.1',0,4,'1005'),(7,'127.0.0.1',0,4,'1016'),(8,'127.0.0.1',0,4,'1015'),(9,'127.0.0.1',0,4,'1016'),(10,'127.0.0.1',0,4,'1019'),(11,'127.0.0.1',0,4,'1016'),(12,'127.0.0.1',0,4,'1014'),(13,'127.0.0.1',0,4,'1001'),(14,'127.0.0.1',0,4,'1016'),(15,'127.0.0.1',0,4,'1014'),(16,'127.0.0.1',0,4,'1002'),(17,'127.0.0.1',0,4,'1016'),(18,'administrator',0,4,'1004'),(19,'administrator',0,4,'1018'),(20,'administrator',0,4,'1016'),(21,'administrator',0,4,'1020'),(22,'administrator',0,4,'1016'),(23,'administrator',0,4,'1023'),(24,'administrator',0,4,'1016'),(25,'wingotech',0,4,'1003'),(26,'wingotech',0,4,'1002'),(27,'wingotech',0,4,'1016'),(28,'xlqb_1314',14,4,'1015'),(29,'xlqb_1314',14,4,'1004'),(30,'administrator',0,4,'1016'),(31,'administrator',0,4,'1015'),(32,'administrator',0,4,'1014'),(33,'administrator',0,4,'1003');

/*Table structure for table `vcity_productclass` */

DROP TABLE IF EXISTS `vcity_productclass`;

CREATE TABLE `vcity_productclass` (
  `classId` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(64) DEFAULT NULL,
  `classDescript` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_productclass` */

insert  into `vcity_productclass`(`classId`,`className`,`classDescript`) values (1,'电信手机',''),(2,'移动手机',''),(3,'联通手机',''),(4,'应用下载','应用下载');

/*Table structure for table `vcity_question` */

DROP TABLE IF EXISTS `vcity_question`;

CREATE TABLE `vcity_question` (
  `questionId` int(11) NOT NULL AUTO_INCREMENT,
  `surveyId` int(11) DEFAULT NULL,
  `questionName` varchar(255) DEFAULT NULL,
  `questionDesc` varchar(255) DEFAULT NULL,
  `questType` int(11) DEFAULT NULL,
  `isTemplate` int(11) DEFAULT NULL,
  `questIndex` int(11) DEFAULT NULL,
  `itemLimit` int(11) DEFAULT NULL,
  PRIMARY KEY (`questionId`)
) ENGINE=MyISAM AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_question` */

insert  into `vcity_question`(`questionId`,`surveyId`,`questionName`,`questionDesc`,`questType`,`isTemplate`,`questIndex`,`itemLimit`) values (1,3,'你的年龄？1',NULL,2,0,1,0),(2,3,'你的手机型号？',NULL,2,0,2,0),(3,3,'你手机的操作系统',NULL,1,0,2,0),(4,4,'q1',NULL,1,0,4,0),(5,4,'q2',NULL,1,0,3,0),(6,4,'q3',NULL,1,0,2,0),(7,4,'q4',NULL,1,0,1,0),(8,13,'',NULL,1,0,1,0),(9,13,'',NULL,1,0,1,0),(35,16,'问题2',NULL,2,1,2,0),(34,16,'问题1',NULL,1,1,1,0),(12,2,'question1',NULL,2,0,3,0),(13,2,'question2',NULL,1,0,1,0),(14,2,'question3',NULL,1,0,2,0),(15,17,'1',NULL,1,0,1,0),(16,17,'2',NULL,1,0,2,0),(17,18,'q',NULL,1,0,2,0),(18,18,'w',NULL,1,0,1,0),(20,NULL,'ss',NULL,1,0,1,0),(21,NULL,'dd',NULL,1,0,2,0),(22,5,'问题1',NULL,1,0,2,0),(23,5,'q2222',NULL,1,0,1,0),(59,3,'你的爱好',NULL,2,0,1,0),(25,6,'12',NULL,1,0,1,0),(26,10,'调查问题',NULL,1,0,1,0),(27,14,'你好嘛！？',NULL,1,0,1,0),(28,14,'性别',NULL,1,1,2,0),(29,14,'你喜欢的手机品牌',NULL,2,0,3,0),(30,14,'',NULL,1,0,1,0),(31,14,'4',NULL,1,0,2,0),(33,5,'性别',NULL,1,0,1,0),(36,16,'问题2',NULL,1,0,9,0),(37,17,'问题1',NULL,1,0,1,0),(38,5,'问题1',NULL,1,0,1,0),(39,22,'性别',NULL,1,0,1,0),(40,22,'问题1',NULL,1,0,2,0),(41,22,'问题2',NULL,1,0,3,0),(42,22,'问题1',NULL,1,0,4,0),(43,23,'你的性别',NULL,1,0,1,0),(44,23,'你是否使用过天翼空间',NULL,1,0,2,0),(45,24,'你的性别？',NULL,1,0,1,0),(46,24,'你试用过哪个品牌的手机？',NULL,2,0,2,0),(47,24,'你试用过天翼手机吗？',NULL,1,0,3,0),(48,25,'性别',NULL,1,0,1,0),(49,25,'年龄阶段',NULL,1,0,2,0),(50,25,'你使用过哪些品牌的手机？',NULL,2,0,3,0),(51,25,'你能接受手机的价格范围？',NULL,1,0,4,0),(52,26,'性别',NULL,1,0,1,0),(53,26,'你使用过哪些品牌的手机',NULL,2,0,2,0),(54,26,'你愿意接受手机价格范围？',NULL,1,0,3,0),(55,27,'性别',NULL,1,0,1,0),(56,27,'年龄',NULL,1,0,2,0),(57,20,'test',NULL,1,0,1,0),(58,20,'xxxxxxxxx',NULL,1,0,2,0),(60,28,'f',NULL,1,0,1,0),(61,29,'123',NULL,1,0,1,0),(62,31,'你的性别？',NULL,1,0,1,0),(63,31,'你是否使用过智能手机？',NULL,1,0,2,0),(64,31,'你使用过哪类智能手机？',NULL,2,0,4,0),(65,31,'你喜欢哪些手机系统？',NULL,2,0,5,0),(66,31,'你是否付费使用手机上的应用？',NULL,1,0,6,0),(67,32,'你的性别？',NULL,1,0,1,0),(68,32,'你的月收入范围',NULL,1,0,2,0),(69,32,'你使用过哪些品牌的手机',NULL,2,0,3,0),(70,32,'你目前最愿意购买哪个品牌手机？',NULL,2,0,5,0),(71,34,'123',NULL,1,0,1,0),(89,37,'tf',NULL,2,1,1,0),(72,34,'123',NULL,1,0,4,0),(73,35,'你的性别？',NULL,1,0,1,0),(74,35,'你所在的年级？',NULL,1,0,2,0),(75,35,'请问您每月的生活费大致是多少？',NULL,1,0,3,0),(76,35,'你现在喜欢的手机品牌是？',NULL,2,0,4,0),(77,35,'你喜欢的手机款式是？',NULL,2,0,5,0),(78,35,'你比较关注的手机功能是？',NULL,2,0,6,0),(79,35,'你目前更换了多少次手机？',NULL,1,0,7,0),(80,36,'你的性别',NULL,1,0,1,0),(81,36,'你的月收入是多少？',NULL,1,0,2,0),(82,36,'你目前使用哪家运营商的产品？',NULL,2,0,3,0),(83,36,'使用该产品的时间是？',NULL,1,0,4,0),(84,36,'你每月消费多少钱？',NULL,1,0,1,0);

/*Table structure for table `vcity_questionitem` */

DROP TABLE IF EXISTS `vcity_questionitem`;

CREATE TABLE `vcity_questionitem` (
  `itemId` int(11) NOT NULL AUTO_INCREMENT,
  `questionId` int(11) DEFAULT NULL,
  `itemDesc` varchar(255) DEFAULT NULL,
  `itemIndex` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemId`)
) ENGINE=MyISAM AUTO_INCREMENT=360 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_questionitem` */

insert  into `vcity_questionitem`(`itemId`,`questionId`,`itemDesc`,`itemIndex`) values (1,1,'26－30',3),(2,1,'21－25',2),(3,1,'30以上',4),(4,1,'16－20',1),(5,2,'三星',3),(6,2,'诺基亚',1),(7,2,'HTC',2),(8,2,'Apple',4),(9,3,'Android',2),(10,3,'塞班',1),(11,3,'IOS',3),(12,3,'其它',4),(13,4,'2',2),(14,4,'4',4),(15,4,'1',1),(16,4,'3',3),(17,5,'1',1),(18,5,'3',3),(19,5,'4',4),(20,5,'2',2),(21,6,'3',3),(22,6,'4',4),(23,6,'2',2),(24,6,'1',1),(25,7,'1',1),(26,7,'4',4),(27,7,'2',2),(28,7,'3',3),(29,8,'',4),(30,8,'',2),(31,8,'',3),(32,8,'',1),(33,9,'',3),(34,9,'',2),(35,9,'',4),(36,9,'',1),(157,35,'d',4),(155,35,'b',2),(156,35,'e',5),(154,35,'c',3),(153,34,'1',1),(152,34,'2',2),(151,34,'3',3),(150,34,'4',4),(51,12,'a',1),(52,12,'b',2),(53,12,'g',7),(54,12,'f',6),(55,12,'d',4),(56,12,'e',5),(57,12,'c',3),(58,12,'h',8),(59,13,'1',1),(60,13,'4',4),(61,13,'2',2),(62,13,'3',3),(63,14,'11',1),(64,14,'44',4),(65,14,'33',3),(66,14,'22',2),(67,15,'11',3),(68,15,'1',4),(69,15,'1',1),(70,15,'1',2),(71,16,'3',1),(72,16,'6',4),(73,16,'4',2),(74,16,'5',3),(75,17,'1q',1),(76,17,'4',4),(77,17,'2',2),(78,17,'3',3),(79,18,'3',2),(80,18,'4',3),(81,18,'2',1),(82,18,'5',4),(87,20,'ss',3),(88,20,'ss',1),(89,20,'ss',4),(90,20,'ss',2),(91,21,'g',3),(92,21,'ff',1),(93,21,'gf',2),(94,21,'h',4),(95,22,'item1',1),(96,22,'item2',2),(97,22,'item7',7),(98,22,'item4',4),(99,22,'item5',5),(100,22,'item6',6),(101,22,'item3',3),(102,23,'i2222',2),(103,23,'i111',1),(104,23,'i333',3),(105,23,'i4444',4),(244,59,'打篮球',1),(243,59,'乒乓球',4),(242,59,'羽毛球',3),(112,25,'2',2),(113,25,'21',3),(114,25,'123',1),(115,25,'12',4),(116,26,'B',2),(117,26,'C',3),(118,26,'A',1),(119,26,'D',4),(120,27,'2',2),(121,27,'',4),(122,27,'3',3),(123,27,'1',1),(124,28,'',4),(125,28,'女',2),(126,28,'',3),(127,28,'男',1),(128,29,'苹果',4),(129,29,'摩托罗拉',5),(130,29,'三星',2),(131,29,'索爱',3),(132,29,'',6),(133,29,'诺基亚',1),(134,30,'',4),(135,30,'',3),(136,30,'',1),(137,30,'',2),(138,31,'',1),(139,31,'',2),(140,31,'',3),(141,31,'',4),(148,33,'',4),(147,33,'',3),(146,33,'男',1),(149,33,'女',2),(158,35,'a',1),(159,36,'a',1),(160,36,'b',2),(161,36,'c',3),(162,37,'2',2),(163,37,'3',3),(164,37,'4',4),(165,37,'1',1),(166,38,'4',4),(167,38,'3',3),(168,38,'1',1),(169,38,'2',2),(170,39,'女',2),(171,39,'男',1),(172,40,'3',3),(173,40,'2',2),(174,40,'4',4),(175,40,'1',1),(176,41,'d',4),(177,41,'a',1),(178,41,'b',2),(179,41,'c',3),(180,42,'8',8),(181,42,'4',4),(182,42,'2',2),(183,42,'7',7),(184,42,'3',3),(185,42,'6',6),(186,42,'9',9),(187,42,'1',1),(188,42,'5',5),(189,43,'男',1),(190,43,'女',2),(191,44,'愿意尝试',3),(192,44,'有',1),(193,44,'没有',2),(194,45,'女',2),(195,45,'男',1),(196,46,'三星',2),(197,46,'索尼爱立信',5),(198,46,'诺基亚',1),(199,46,'苹果',4),(200,46,'摩托罗拉',3),(201,47,'没有',2),(202,47,'愿意去尝试',3),(203,47,'有',1),(204,48,'女',2),(205,48,'男',1),(206,49,'41~50',4),(207,49,'21~30',2),(208,49,'10~20',1),(209,49,'31~40',3),(210,50,'摩托罗拉',3),(211,50,'索尼爱立信',4),(212,50,'诺基亚',1),(213,50,'三星',2),(214,51,'3001元以上',4),(215,51,'2001~3000元',3),(216,51,'1000元以下',1),(217,51,'1001~2000元',2),(218,52,'男',1),(219,52,'女',2),(220,53,'索尼爱立信',3),(221,53,'三星',2),(222,53,'摩托罗拉',4),(223,53,'诺基亚',1),(224,54,'1001元~2000元',2),(225,54,'1000元以下',1),(226,54,'2001元~3000元',3),(227,54,'3001元以上',4),(228,55,'女',2),(229,55,'男',1),(230,56,'31－35',4),(231,56,'21－25',2),(232,56,'10－20',1),(233,56,'26－30',3),(234,57,'ss',2),(235,57,'xx',1),(236,57,'6',4),(237,57,'5',3),(238,58,'xx',4),(239,58,'xx',1),(240,58,'xx',2),(241,58,'xx',3),(245,59,'踢足球',2),(246,60,'f',1),(247,60,'f',2),(248,60,'f',4),(249,60,'f',3),(250,61,'123',4),(251,61,'213',3),(252,61,'321',2),(253,61,'213',1),(255,62,'男',1),(256,62,'女',2),(257,63,'使用过',1),(258,63,'没有使用过',2),(259,64,'苹果系统',3),(260,64,'黑莓系统',4),(261,64,'android系统',2),(262,64,'塞班系统',1),(263,65,'android系统',2),(264,65,'塞班系统',1),(265,65,'黑莓系统',4),(266,65,'苹果系统',3),(267,66,'是',1),(268,66,'否',2),(269,67,'女',2),(270,67,'男',1),(271,68,'2001元~2000元',3),(272,68,'1000元以下',1),(273,68,'1001元~2000元',2),(274,68,'3001元以上',4),(275,69,'苹果',4),(276,69,'诺基亚',1),(277,69,'索爱',3),(278,69,'三星',2),(279,69,'摩托罗拉',286),(280,70,'苹果',1),(281,70,'摩托罗拉',3),(282,70,'三星',2),(283,70,'诺基亚',4),(284,71,'2321',3),(285,71,'12323',1),(286,71,'232',2),(287,72,'2321',3),(288,72,'12323',1),(289,72,'232',2),(290,73,'男',1),(291,73,'女',2),(292,74,'大四',4),(293,74,'大三',3),(294,74,'大一',1),(295,74,'大二',2),(296,75,'2001元以上',5),(297,75,'500元以下',1),(298,75,'1501元~2000元',4),(299,75,'500元~1000元',2),(300,75,'1001元~1500元',3),(301,76,'中兴',7),(302,76,'苹果',1),(303,76,'华为',6),(304,76,'三星',2),(305,76,'索爱',4),(306,76,'联想',8),(307,76,'诺基亚',3),(308,76,'摩托罗拉',5),(309,77,'滑盖',3),(310,77,'翻盖',2),(311,77,'旋转',4),(312,77,'直板',1),(313,77,'其他',5),(314,78,'拍照',2),(315,78,'操作系统',1),(316,78,'音乐',3),(317,78,'蓝牙',5),(318,78,'无线上网',4),(319,78,'存储扩展',6),(320,79,'没有换过',1),(321,79,'3次或者以上',4),(322,79,'2次',3),(323,79,'1次',2),(324,80,'女',2),(325,80,'男',1),(326,81,'1001元~2000元',3),(327,81,'3001元或以上',5),(328,81,'2001元~3000元',4),(329,81,'500元或以下',1),(330,81,'501元~1000元',2),(331,82,'中国联通',3),(332,82,'中国电信',1),(333,82,'中国移动',2),(334,83,'2年以上',4),(335,83,'1年至2年',3),(336,83,'半年至1年',2),(337,83,'半年内',1),(338,84,'50元或以下',1),(339,84,'51元至100元',2),(340,84,'151元至200元',4),(341,84,'201元或以上',5),(342,84,'101元至150元',3),(359,89,'tftf',2),(358,89,'12',4),(357,89,'123',3),(356,89,'tft',1);

/*Table structure for table `vcity_questionresult` */

DROP TABLE IF EXISTS `vcity_questionresult`;

CREATE TABLE `vcity_questionresult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `questionId` int(11) DEFAULT NULL,
  `subTaskID` int(11) DEFAULT NULL,
  `answerType` int(11) DEFAULT NULL,
  `answerUser` varchar(50) DEFAULT NULL,
  `answerTime` varchar(20) DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=685 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_questionresult` */

insert  into `vcity_questionresult`(`id`,`questionId`,`subTaskID`,`answerType`,`answerUser`,`answerTime`,`result`) values (150,3,0,1,'0','2011-10-24-16-19-08',NULL),(151,3,0,1,'0','2011-10-24-16-23-06',10),(152,2,0,1,'0','2011-10-24-16-23-06',6),(153,1,0,1,'0','2011-10-24-16-23-06',2),(154,3,0,1,'0','2011-10-24-16-41-45',10),(155,2,0,1,'0','2011-10-24-16-41-45',6),(156,1,0,1,'0','2011-10-24-16-41-45',1),(157,3,3,1,'0','2011-10-24-16-44-18',10),(158,2,3,1,'0','2011-10-24-16-44-18',7),(159,1,3,1,'0','2011-10-24-16-44-18',2),(160,59,3,1,'0','2011-10-24-16-44-18',244),(161,59,3,1,'0','2011-10-24-16-44-18',245),(162,59,3,1,'0','2011-10-24-16-44-18',242),(163,59,3,1,'0','2011-10-24-16-44-18',243),(164,3,3,1,'127.0.0.1','2011-10-25-18-19-59',9),(165,2,3,1,'127.0.0.1','2011-10-25-18-19-59',7),(166,1,3,1,'127.0.0.1','2011-10-25-18-19-59',2),(167,59,3,1,'127.0.0.1','2011-10-25-18-19-59',244),(168,59,3,1,'127.0.0.1','2011-10-25-18-19-59',245),(169,3,3,1,'administrator','2011-10-25-16-22-37',9),(170,2,3,1,'administrator','2011-10-25-16-22-37',7),(171,1,3,1,'administrator','2011-10-25-16-22-37',4),(172,59,3,1,'administrator','2011-10-25-16-22-37',244),(173,59,3,1,'administrator','2011-10-25-16-22-37',242),(174,3,3,1,'administrator','2011-10-25-16-35-51',10),(175,2,3,1,'administrator','2011-10-25-16-35-51',5),(176,1,3,1,'administrator','2011-10-25-16-35-51',4),(177,59,3,1,'administrator','2011-10-25-16-35-51',244),(178,59,3,1,'administrator','2011-10-25-16-35-51',245),(179,59,3,1,'administrator','2011-10-25-16-35-51',242),(180,3,3,1,'127.0.0.1','2011-10-26-08-47-13',10),(181,2,3,1,'127.0.0.1','2011-10-26-08-47-13',6),(182,1,3,1,'127.0.0.1','2011-10-26-08-47-13',2),(183,59,3,1,'127.0.0.1','2011-10-26-08-47-13',244),(184,3,3,1,'127.0.0.1','2011-10-26-11-53-57',11),(185,2,3,1,'127.0.0.1','2011-10-26-11-53-57',8),(186,1,3,1,'127.0.0.1','2011-10-26-11-53-57',3),(187,59,3,1,'127.0.0.1','2011-10-26-11-53-57',243),(188,3,3,1,'127.0.0.1','2011-10-27-08-51-18',10),(189,2,3,1,'127.0.0.1','2011-10-27-08-51-18',6),(190,1,3,1,'127.0.0.1','2011-10-27-08-51-18',1),(191,59,3,1,'127.0.0.1','2011-10-27-08-51-18',244),(192,59,3,1,'127.0.0.1','2011-10-27-08-51-18',243),(193,3,3,1,'127.0.0.1','2011-11-03-14-41-53',9),(194,2,3,1,'127.0.0.1','2011-11-03-14-41-53',6),(195,1,3,1,'127.0.0.1','2011-11-03-14-41-53',1),(196,59,3,1,'127.0.0.1','2011-11-03-14-41-53',245),(197,3,3,1,'127.0.0.1','2011-11-04-10-04-27',9),(198,2,3,1,'127.0.0.1','2011-11-04-10-04-27',7),(199,1,3,1,'127.0.0.1','2011-11-04-10-04-27',3),(200,59,3,1,'127.0.0.1','2011-11-04-10-04-27',245),(201,3,3,1,'administrator','2011-11-18-15-36-13',10),(202,2,3,1,'administrator','2011-11-18-15-36-13',7),(203,1,3,1,'administrator','2011-11-18-15-36-13',4),(204,1,3,1,'administrator','2011-11-18-15-36-13',2),(205,59,3,1,'administrator','2011-11-18-15-36-13',243),(206,3,80,2,'wingotech','2011-11-21-09-45-53',10),(207,2,80,2,'wingotech','2011-11-21-09-45-53',6),(208,1,80,2,'wingotech','2011-11-21-09-45-53',2),(209,59,80,2,'wingotech','2011-11-21-09-45-53',244),(210,3,80,2,'wingotech','2011-11-21-09-50-23',11),(211,2,80,2,'wingotech','2011-11-21-09-50-23',8),(212,1,80,2,'wingotech','2011-11-21-09-50-23',3),(213,59,80,2,'wingotech','2011-11-21-09-50-23',243),(214,3,83,2,'wingotech','2011-11-21-09-57-39',10),(215,2,83,2,'wingotech','2011-11-21-09-57-39',6),(216,1,83,2,'wingotech','2011-11-21-09-57-39',2),(217,59,83,2,'wingotech','2011-11-21-09-57-39',244),(218,3,88,2,'wingotech','2011-11-23-13-51-07',11),(219,2,88,2,'wingotech','2011-11-23-13-51-07',6),(220,2,88,2,'wingotech','2011-11-23-13-51-07',8),(221,1,88,2,'wingotech','2011-11-23-13-51-07',3),(222,59,88,2,'wingotech','2011-11-23-13-51-07',244),(223,3,89,2,'wingotech','2011-11-24-11-31-08',10),(224,2,89,2,'wingotech','2011-11-24-11-31-08',6),(225,1,89,2,'wingotech','2011-11-24-11-31-08',4),(226,3,89,2,'wingotech','2011-11-24-14-03-49',10),(227,2,89,2,'wingotech','2011-11-24-14-03-49',6),(228,1,89,2,'wingotech','2011-11-24-14-03-49',4),(229,59,89,2,'wingotech','2011-11-24-14-03-49',244),(230,3,89,2,'wingotech','2011-11-24-14-06-22',10),(231,2,89,2,'wingotech','2011-11-24-14-06-22',6),(232,1,89,2,'wingotech','2011-11-24-14-06-22',4),(233,59,89,2,'wingotech','2011-11-24-14-06-22',244),(234,3,92,1,'125.89.8.165','2011-11-24-14-13-30',10),(235,2,92,1,'125.89.8.165','2011-11-24-14-13-30',6),(236,1,92,1,'125.89.8.165','2011-11-24-14-13-30',4),(237,59,92,1,'125.89.8.165','2011-11-24-14-13-30',244),(238,3,89,2,'125.89.8.165','2011-11-24-14-16-25',10),(239,2,89,2,'125.89.8.165','2011-11-24-14-16-25',6),(240,1,89,2,'125.89.8.165','2011-11-24-14-16-25',4),(241,59,89,2,'125.89.8.165','2011-11-24-14-16-25',244),(242,3,92,1,'wingotech','2011-11-24-14-56-44',10),(243,2,92,1,'wingotech','2011-11-24-14-56-44',6),(244,1,92,1,'wingotech','2011-11-24-14-56-44',4),(245,59,92,1,'wingotech','2011-11-24-14-56-44',244),(246,3,96,2,'wingotech','2011-11-25-09-19-38',10),(247,2,96,2,'wingotech','2011-11-25-09-19-38',6),(248,1,96,2,'wingotech','2011-11-25-09-19-38',4),(249,59,96,2,'wingotech','2011-11-25-09-19-38',244),(250,3,96,2,'wingotech','2011-11-28-09-08-03',10),(251,3,96,2,'wingotech','2011-11-28-09-08-12',10),(252,2,96,2,'wingotech','2011-11-28-09-08-12',6),(253,1,96,2,'wingotech','2011-11-28-09-08-12',4),(254,59,96,2,'wingotech','2011-11-28-09-08-12',244),(255,3,96,2,'wingotech','2011-11-28-09-08-21',10),(256,2,96,2,'wingotech','2011-11-28-09-08-21',6),(257,1,96,2,'wingotech','2011-11-28-09-08-22',4),(258,59,96,2,'wingotech','2011-11-28-09-08-22',244),(259,3,96,2,'wingotech','2011-11-28-09-08-31',10),(260,2,96,2,'wingotech','2011-11-28-09-08-31',6),(261,1,96,2,'wingotech','2011-11-28-09-08-31',4),(262,1,97,2,'wingotech','2011-11-28-14-03-16',4),(263,59,97,2,'wingotech','2011-11-28-14-03-16',244),(264,1,97,2,'wingotech','2011-11-28-14-03-58',4),(265,59,97,2,'wingotech','2011-11-28-14-03-58',244),(266,1,97,2,'wingotech','2011-11-28-14-06-53',4),(267,59,97,2,'wingotech','2011-11-28-14-06-53',244),(268,22,98,2,'wingotech','2011-11-28-15-48-15',95),(269,22,98,2,'wingotech','2011-11-28-15-48-35',95),(270,23,98,2,'wingotech','2011-11-28-15-48-35',103),(271,33,98,2,'wingotech','2011-11-28-15-48-35',146),(272,38,98,2,'wingotech','2011-11-28-15-48-35',168),(273,22,98,2,'wingotech','2011-11-28-15-49-05',95),(274,23,98,2,'wingotech','2011-11-28-15-49-05',103),(275,33,98,2,'wingotech','2011-11-28-15-49-05',146),(276,38,98,2,'wingotech','2011-11-28-15-49-05',168),(277,3,93,2,'125.89.9.166','2011-12-01-10-09-26',10),(278,2,93,2,'125.89.9.166','2011-12-01-10-09-27',6),(279,1,93,2,'125.89.9.166','2011-12-01-10-09-27',4),(280,59,93,2,'125.89.9.166','2011-12-01-10-09-27',244),(281,66,109,2,'wingotech','2011-12-07-09-07-04',268),(282,64,109,2,'wingotech','2011-12-07-09-07-04',261),(283,65,109,2,'wingotech','2011-12-07-09-07-04',263),(284,65,109,2,'wingotech','2011-12-07-09-07-04',266),(285,62,109,2,'wingotech','2011-12-07-09-07-04',255),(286,63,109,2,'wingotech','2011-12-07-09-07-04',257),(287,67,110,2,'wingotech','2011-12-07-09-12-29',270),(288,66,110,2,'wingotech','2011-12-07-09-12-29',267),(289,69,110,2,'wingotech','2011-12-07-09-12-29',278),(290,69,110,2,'wingotech','2011-12-07-09-12-29',277),(291,69,110,2,'wingotech','2011-12-07-09-12-29',275),(292,69,110,2,'wingotech','2011-12-07-09-12-29',279),(293,68,110,2,'wingotech','2011-12-07-09-12-29',271),(294,64,110,2,'wingotech','2011-12-07-09-12-29',262),(295,64,110,2,'wingotech','2011-12-07-09-12-29',261),(296,64,110,2,'wingotech','2011-12-07-09-12-29',259),(297,65,110,2,'wingotech','2011-12-07-09-12-29',263),(298,65,110,2,'wingotech','2011-12-07-09-12-29',266),(299,70,110,2,'wingotech','2011-12-07-09-12-29',280),(300,70,110,2,'wingotech','2011-12-07-09-12-29',282),(301,70,110,2,'wingotech','2011-12-07-09-12-29',281),(302,62,110,2,'wingotech','2011-12-07-09-12-29',256),(303,63,110,2,'wingotech','2011-12-07-09-12-29',257),(304,67,32,1,'administrator','2011-12-07-13-46-16',270),(305,69,32,1,'administrator','2011-12-07-13-46-16',276),(306,68,32,1,'administrator','2011-12-07-13-46-16',271),(307,70,32,1,'administrator','2011-12-07-13-46-16',280),(308,67,111,2,'wingotech','2011-12-07-14-01-43',270),(309,66,111,2,'wingotech','2011-12-07-14-01-43',267),(310,69,111,2,'wingotech','2011-12-07-14-01-43',278),(311,68,111,2,'wingotech','2011-12-07-14-01-43',271),(312,64,111,2,'wingotech','2011-12-07-14-01-44',261),(313,65,111,2,'wingotech','2011-12-07-14-01-44',263),(314,70,111,2,'wingotech','2011-12-07-14-01-44',280),(315,62,111,2,'wingotech','2011-12-07-14-01-44',255),(316,63,111,2,'wingotech','2011-12-07-14-01-44',257),(317,67,101,1,'wingotech','2011-12-07-14-06-11',269),(318,69,101,1,'wingotech','2011-12-07-14-06-11',276),(319,68,101,1,'wingotech','2011-12-07-14-06-11',273),(320,70,101,1,'wingotech','2011-12-07-14-06-11',280),(321,1,113,2,'wingotech','2011-12-07-18-59-37',4),(322,1,113,2,'wingotech','2011-12-07-18-59-37',1),(323,59,113,2,'wingotech','2011-12-07-18-59-37',244),(324,59,113,2,'wingotech','2011-12-07-18-59-37',245),(325,23,113,2,'wingotech','2011-12-07-18-59-37',103),(326,33,113,2,'wingotech','2011-12-07-18-59-37',146),(327,3,114,2,'wingotech','2011-12-07-19-01-04',11),(328,2,114,2,'wingotech','2011-12-07-19-01-04',6),(329,1,114,2,'wingotech','2011-12-07-19-01-04',4),(330,59,114,2,'wingotech','2011-12-07-19-01-04',244),(331,22,114,2,'wingotech','2011-12-07-19-01-04',95),(332,23,114,2,'wingotech','2011-12-07-19-01-04',103),(333,33,114,2,'wingotech','2011-12-07-19-01-04',146),(334,38,114,2,'wingotech','2011-12-07-19-01-04',168),(335,3,115,2,'wingotech','2011-12-09-09-16-16',12),(336,2,115,2,'wingotech','2011-12-09-09-16-16',8),(337,1,115,2,'wingotech','2011-12-09-09-16-16',3),(338,59,115,2,'wingotech','2011-12-09-09-16-16',243),(339,3,116,2,'xlqb_1314','2011-12-09-09-24-58',11),(340,2,116,2,'xlqb_1314','2011-12-09-09-24-58',6),(341,2,116,2,'xlqb_1314','2011-12-09-09-24-58',7),(342,2,116,2,'xlqb_1314','2011-12-09-09-24-58',5),(343,2,116,2,'xlqb_1314','2011-12-09-09-24-58',8),(344,1,116,2,'xlqb_1314','2011-12-09-09-24-58',4),(345,1,116,2,'xlqb_1314','2011-12-09-09-24-58',2),(346,1,116,2,'xlqb_1314','2011-12-09-09-24-58',1),(347,1,116,2,'xlqb_1314','2011-12-09-09-24-58',3),(348,59,116,2,'xlqb_1314','2011-12-09-09-24-58',244),(349,59,116,2,'xlqb_1314','2011-12-09-09-24-58',245),(350,59,116,2,'xlqb_1314','2011-12-09-09-24-58',242),(351,59,116,2,'xlqb_1314','2011-12-09-09-24-58',243),(352,67,117,2,'wingotech','2011-12-09-14-00-19',270),(353,68,117,2,'wingotech','2011-12-09-14-00-19',272),(354,64,117,2,'wingotech','2011-12-09-14-00-19',262),(355,64,117,2,'wingotech','2011-12-09-14-00-19',259),(356,65,117,2,'wingotech','2011-12-09-14-00-19',265),(357,63,117,2,'wingotech','2011-12-09-14-00-19',257),(358,66,122,2,'wingotech','2011-12-09-17-36-04',267),(359,64,122,2,'wingotech','2011-12-09-17-36-04',262),(360,65,122,2,'wingotech','2011-12-09-17-36-04',264),(361,62,122,2,'wingotech','2011-12-09-17-36-04',255),(362,63,122,2,'wingotech','2011-12-09-17-36-04',257),(363,67,123,2,'wingotech','2011-12-09-17-53-46',270),(364,66,123,2,'wingotech','2011-12-09-17-53-46',267),(365,69,123,2,'wingotech','2011-12-09-17-53-46',276),(366,68,123,2,'wingotech','2011-12-09-17-53-46',272),(367,64,123,2,'wingotech','2011-12-09-17-53-46',262),(368,65,123,2,'wingotech','2011-12-09-17-53-46',264),(369,70,123,2,'wingotech','2011-12-09-17-53-46',280),(370,62,123,2,'wingotech','2011-12-09-17-53-46',255),(371,63,123,2,'wingotech','2011-12-09-17-53-46',257),(372,22,124,2,'wingotech','2011-12-12-09-03-11',95),(373,23,124,2,'wingotech','2011-12-12-09-03-11',104),(374,33,124,2,'wingotech','2011-12-12-09-03-11',149),(375,38,124,2,'wingotech','2011-12-12-09-03-11',168),(376,3,125,2,'wingotech','2011-12-12-10-19-57',12),(377,2,125,2,'wingotech','2011-12-12-10-19-57',6),(378,2,125,2,'wingotech','2011-12-12-10-19-57',7),(379,2,125,2,'wingotech','2011-12-12-10-19-57',5),(380,2,125,2,'wingotech','2011-12-12-10-19-57',8),(381,1,125,2,'wingotech','2011-12-12-10-19-57',4),(382,1,125,2,'wingotech','2011-12-12-10-19-57',2),(383,1,125,2,'wingotech','2011-12-12-10-19-57',1),(384,1,125,2,'wingotech','2011-12-12-10-19-57',3),(385,59,125,2,'wingotech','2011-12-12-10-19-57',244),(386,59,125,2,'wingotech','2011-12-12-10-19-57',245),(387,59,125,2,'wingotech','2011-12-12-10-19-57',242),(388,59,125,2,'wingotech','2011-12-12-10-19-57',243),(389,22,125,2,'wingotech','2011-12-12-10-19-58',97),(390,23,125,2,'wingotech','2011-12-12-10-19-58',105),(391,33,125,2,'wingotech','2011-12-12-10-19-58',148),(392,38,125,2,'wingotech','2011-12-12-10-19-58',166),(393,3,126,2,'wingotech','2011-12-12-10-29-38',10),(394,2,126,2,'wingotech','2011-12-12-10-29-38',6),(395,2,126,2,'wingotech','2011-12-12-10-29-38',7),(396,2,126,2,'wingotech','2011-12-12-10-29-38',5),(397,2,126,2,'wingotech','2011-12-12-10-29-38',8),(398,1,126,2,'wingotech','2011-12-12-10-29-38',4),(399,1,126,2,'wingotech','2011-12-12-10-29-38',2),(400,1,126,2,'wingotech','2011-12-12-10-29-38',1),(401,1,126,2,'wingotech','2011-12-12-10-29-38',3),(402,59,126,2,'wingotech','2011-12-12-10-29-38',244),(403,59,126,2,'wingotech','2011-12-12-10-29-38',245),(404,59,126,2,'wingotech','2011-12-12-10-29-38',242),(405,59,126,2,'wingotech','2011-12-12-10-29-38',243),(406,69,129,2,'wingotech','2011-12-12-10-42-39',276),(407,68,129,2,'wingotech','2011-12-12-10-42-39',272),(408,52,129,2,'wingotech','2011-12-12-10-42-39',218),(409,70,129,2,'wingotech','2011-12-12-10-42-39',280),(410,53,129,2,'wingotech','2011-12-12-10-42-39',223),(411,67,32,1,'dongbin','2011-12-12-17-12-27',270),(412,69,32,1,'dongbin','2011-12-12-17-12-27',276),(413,68,32,1,'dongbin','2011-12-12-17-12-27',272),(414,70,32,1,'dongbin','2011-12-12-17-12-27',280),(415,67,130,2,'wingotech','2011-12-12-17-13-41',270),(416,69,130,2,'wingotech','2011-12-12-17-13-41',276),(417,68,130,2,'wingotech','2011-12-12-17-13-41',272),(418,70,130,2,'wingotech','2011-12-12-17-13-41',280),(419,22,131,2,'wingotech','2011-12-13-09-47-48',95),(420,23,131,2,'wingotech','2011-12-13-09-47-48',105),(421,33,131,2,'wingotech','2011-12-13-09-47-48',146),(422,38,131,2,'wingotech','2011-12-13-09-47-48',168),(423,22,132,2,'wingotech','2011-12-13-10-08-31',95),(424,23,132,2,'wingotech','2011-12-13-10-08-31',104),(425,33,132,2,'wingotech','2011-12-13-10-08-31',146),(426,38,132,2,'wingotech','2011-12-13-10-08-31',168),(427,67,134,2,'wingotech','2011-12-13-10-25-13',270),(428,69,134,2,'wingotech','2011-12-13-10-25-13',278),(429,69,134,2,'wingotech','2011-12-13-10-25-13',277),(430,68,134,2,'wingotech','2011-12-13-10-25-13',272),(431,70,134,2,'wingotech','2011-12-13-10-25-13',280),(432,70,134,2,'wingotech','2011-12-13-10-25-13',282),(433,67,135,2,'wingotech','2011-12-14-08-57-20',270),(434,69,135,2,'wingotech','2011-12-14-08-57-20',276),(435,68,135,2,'wingotech','2011-12-14-08-57-20',272),(436,70,135,2,'wingotech','2011-12-14-08-57-20',280),(437,67,140,1,'wingotech','2011-12-15-09-18-01',270),(438,69,140,1,'wingotech','2011-12-15-09-18-01',276),(439,69,140,1,'wingotech','2011-12-15-09-18-01',278),(440,68,140,1,'wingotech','2011-12-15-09-18-01',272),(441,70,140,1,'wingotech','2011-12-15-09-18-01',280),(442,70,140,1,'wingotech','2011-12-15-09-18-01',282),(443,67,146,2,'wingotech','2011-12-16-10-15-47',270),(444,69,146,2,'wingotech','2011-12-16-10-15-47',276),(445,69,146,2,'wingotech','2011-12-16-10-15-47',278),(446,69,146,2,'wingotech','2011-12-16-10-15-47',277),(447,69,146,2,'wingotech','2011-12-16-10-15-47',275),(448,69,146,2,'wingotech','2011-12-16-10-15-47',279),(449,68,146,2,'wingotech','2011-12-16-10-15-47',272),(450,70,146,2,'wingotech','2011-12-16-10-15-47',280),(451,70,146,2,'wingotech','2011-12-16-10-15-47',282),(452,70,146,2,'wingotech','2011-12-16-10-15-47',281),(453,70,146,2,'wingotech','2011-12-16-10-15-47',283),(454,82,147,2,'wingotech','2011-12-16-11-57-40',332),(455,83,147,2,'wingotech','2011-12-16-11-57-40',335),(456,80,147,2,'wingotech','2011-12-16-11-57-40',325),(457,81,147,2,'wingotech','2011-12-16-11-57-40',328),(458,84,147,2,'wingotech','2011-12-16-11-57-40',339),(459,82,148,2,'administrator','2011-12-19-11-31-42',332),(460,82,148,2,'administrator','2011-12-19-11-31-42',333),(461,83,148,2,'administrator','2011-12-19-11-31-42',336),(462,80,148,2,'administrator','2011-12-19-11-31-42',324),(463,81,148,2,'administrator','2011-12-19-11-31-42',327),(464,84,148,2,'administrator','2011-12-19-11-31-42',340),(465,66,149,2,'wingotech','2011-12-20-08-51-19',267),(466,64,149,2,'wingotech','2011-12-20-08-51-19',261),(467,64,149,2,'wingotech','2011-12-20-08-51-19',259),(468,65,149,2,'wingotech','2011-12-20-08-51-19',266),(469,62,149,2,'wingotech','2011-12-20-08-51-19',256),(470,63,149,2,'wingotech','2011-12-20-08-51-19',257),(471,66,150,2,'wingotech','2011-12-20-08-52-06',268),(472,79,150,2,'wingotech','2011-12-20-08-52-06',322),(473,78,150,2,'wingotech','2011-12-20-08-52-06',315),(474,77,150,2,'wingotech','2011-12-20-08-52-06',312),(475,77,150,2,'wingotech','2011-12-20-08-52-06',309),(476,64,150,2,'wingotech','2011-12-20-08-52-06',262),(477,64,150,2,'wingotech','2011-12-20-08-52-06',261),(478,64,150,2,'wingotech','2011-12-20-08-52-06',259),(479,65,150,2,'wingotech','2011-12-20-08-52-06',263),(480,62,150,2,'wingotech','2011-12-20-08-52-06',255),(481,63,150,2,'wingotech','2011-12-20-08-52-06',257),(482,73,150,2,'wingotech','2011-12-20-08-52-06',290),(483,74,150,2,'wingotech','2011-12-20-08-52-06',292),(484,75,150,2,'wingotech','2011-12-20-08-52-06',299),(485,76,150,2,'wingotech','2011-12-20-08-52-06',302),(486,76,150,2,'wingotech','2011-12-20-08-52-06',304),(487,76,150,2,'wingotech','2011-12-20-08-52-06',305),(488,76,150,2,'wingotech','2011-12-20-08-52-06',308),(489,79,151,2,'wingotech','2011-12-20-09-30-30',322),(490,78,151,2,'wingotech','2011-12-20-09-30-30',315),(491,78,151,2,'wingotech','2011-12-20-09-30-30',314),(492,78,151,2,'wingotech','2011-12-20-09-30-30',318),(493,77,151,2,'wingotech','2011-12-20-09-30-30',310),(494,73,151,2,'wingotech','2011-12-20-09-30-30',290),(495,74,151,2,'wingotech','2011-12-20-09-30-30',292),(496,75,151,2,'wingotech','2011-12-20-09-30-30',298),(497,76,151,2,'wingotech','2011-12-20-09-30-30',305),(498,76,151,2,'wingotech','2011-12-20-09-30-30',306),(499,82,152,2,'wingotech','2011-12-20-09-37-28',332),(500,83,152,2,'wingotech','2011-12-20-09-37-28',334),(501,80,152,2,'wingotech','2011-12-20-09-37-28',324),(502,81,152,2,'wingotech','2011-12-20-09-37-28',327),(503,84,152,2,'wingotech','2011-12-20-09-37-28',340),(504,52,153,2,'wingotech','2011-12-20-09-37-38',218),(505,53,153,2,'wingotech','2011-12-20-09-37-38',223),(506,53,153,2,'wingotech','2011-12-20-09-37-38',220),(507,54,153,2,'wingotech','2011-12-20-09-37-38',226),(508,52,154,1,'xlqb_1314','2011-12-23-11-25-24',218),(509,53,154,1,'xlqb_1314','2011-12-23-11-25-24',223),(510,53,154,1,'xlqb_1314','2011-12-23-11-25-24',221),(511,53,154,1,'xlqb_1314','2011-12-23-11-25-24',220),(512,53,154,1,'xlqb_1314','2011-12-23-11-25-24',222),(513,54,154,1,'xlqb_1314','2011-12-23-11-25-24',225),(514,79,156,1,'xlqb_1314','2011-12-23-11-29-48',323),(515,78,156,1,'xlqb_1314','2011-12-23-11-29-48',316),(516,78,156,1,'xlqb_1314','2011-12-23-11-29-48',318),(517,77,156,1,'xlqb_1314','2011-12-23-11-29-48',309),(518,73,156,1,'xlqb_1314','2011-12-23-11-29-48',291),(519,74,156,1,'xlqb_1314','2011-12-23-11-29-48',295),(520,75,156,1,'xlqb_1314','2011-12-23-11-29-48',297),(521,76,156,1,'xlqb_1314','2011-12-23-11-29-48',307),(522,76,156,1,'xlqb_1314','2011-12-23-11-29-48',305),(523,76,156,1,'xlqb_1314','2011-12-23-11-29-48',303),(524,82,160,1,'xlqb_1314','2011-12-23-11-30-05',333),(525,82,160,1,'xlqb_1314','2011-12-23-11-30-05',331),(526,83,160,1,'xlqb_1314','2011-12-23-11-30-05',335),(527,80,160,1,'xlqb_1314','2011-12-23-11-30-05',325),(528,81,160,1,'xlqb_1314','2011-12-23-11-30-05',326),(529,84,160,1,'xlqb_1314','2011-12-23-11-30-05',342),(530,52,159,1,'wingotech','2011-12-23-11-31-59',218),(531,53,159,1,'wingotech','2011-12-23-11-31-59',221),(532,53,159,1,'wingotech','2011-12-23-11-31-59',220),(533,54,159,1,'wingotech','2011-12-23-11-31-59',226),(534,52,159,1,'wingotech','2011-12-23-11-32-33',219),(535,53,159,1,'wingotech','2011-12-23-11-32-33',223),(536,53,159,1,'wingotech','2011-12-23-11-32-33',221),(537,54,159,1,'wingotech','2011-12-23-11-32-33',224),(538,79,155,1,'wingotech','2011-12-23-11-32-47',322),(539,78,155,1,'wingotech','2011-12-23-11-32-47',316),(540,78,155,1,'wingotech','2011-12-23-11-32-47',318),(541,77,155,1,'wingotech','2011-12-23-11-32-47',309),(542,77,155,1,'wingotech','2011-12-23-11-32-47',311),(543,73,155,1,'wingotech','2011-12-23-11-32-47',291),(544,74,155,1,'wingotech','2011-12-23-11-32-47',295),(545,75,155,1,'wingotech','2011-12-23-11-32-47',300),(546,76,155,1,'wingotech','2011-12-23-11-32-47',303),(547,76,155,1,'wingotech','2011-12-23-11-32-47',301),(548,82,158,1,'wingotech','2011-12-23-11-33-05',333),(549,83,158,1,'wingotech','2011-12-23-11-33-05',334),(550,80,158,1,'wingotech','2011-12-23-11-33-05',325),(551,81,158,1,'wingotech','2011-12-23-11-33-05',327),(552,84,158,1,'wingotech','2011-12-23-11-33-05',341),(553,66,162,2,'wingotech','2011-12-23-11-41-44',267),(554,69,162,2,'wingotech','2011-12-23-11-41-44',277),(555,69,162,2,'wingotech','2011-12-23-11-41-44',275),(556,77,162,2,'wingotech','2011-12-23-11-41-44',309),(557,77,162,2,'wingotech','2011-12-23-11-41-44',311),(558,64,162,2,'wingotech','2011-12-23-11-41-44',262),(559,64,162,2,'wingotech','2011-12-23-11-41-44',261),(560,65,162,2,'wingotech','2011-12-23-11-41-44',263),(561,65,162,2,'wingotech','2011-12-23-11-41-44',266),(562,70,162,2,'wingotech','2011-12-23-11-41-44',283),(563,80,162,2,'wingotech','2011-12-23-11-41-44',325),(564,54,162,2,'wingotech','2011-12-23-11-41-44',227),(565,81,162,2,'wingotech','2011-12-23-11-41-44',326),(566,84,162,2,'wingotech','2011-12-23-11-41-44',342),(567,75,162,2,'wingotech','2011-12-23-11-41-44',298),(568,76,162,2,'wingotech','2011-12-23-11-41-44',308),(569,76,162,2,'wingotech','2011-12-23-11-41-44',303),(570,52,168,1,'wingotech','2011-12-27-16-33-32',219),(571,53,168,1,'wingotech','2011-12-27-16-33-32',223),(572,53,168,1,'wingotech','2011-12-27-16-33-32',221),(573,54,168,1,'wingotech','2011-12-27-16-33-32',225),(574,82,170,1,'wingotech','2011-12-27-16-37-07',332),(575,82,170,1,'wingotech','2011-12-27-16-37-07',333),(576,83,170,1,'wingotech','2011-12-27-16-37-07',335),(577,80,170,1,'wingotech','2011-12-27-16-37-07',325),(578,81,170,1,'wingotech','2011-12-27-16-37-07',330),(579,84,170,1,'wingotech','2011-12-27-16-37-07',339),(580,79,172,1,'wingotech','2011-12-27-16-49-57',320),(581,78,172,1,'wingotech','2011-12-27-16-49-57',315),(582,77,172,1,'wingotech','2011-12-27-16-49-57',312),(583,73,172,1,'wingotech','2011-12-27-16-49-57',290),(584,74,172,1,'wingotech','2011-12-27-16-49-57',294),(585,75,172,1,'wingotech','2011-12-27-16-49-57',297),(586,76,172,1,'wingotech','2011-12-27-16-49-57',302),(587,82,176,1,'wingotech','2011-12-27-17-04-55',332),(588,82,176,1,'wingotech','2011-12-27-17-04-55',333),(589,82,176,1,'wingotech','2011-12-27-17-04-55',331),(590,83,176,1,'wingotech','2011-12-27-17-04-55',334),(591,80,176,1,'wingotech','2011-12-27-17-04-55',324),(592,81,176,1,'wingotech','2011-12-27-17-04-55',327),(593,84,176,1,'wingotech','2011-12-27-17-04-55',341),(594,79,167,1,'wingotech','2011-12-27-17-08-23',320),(595,78,167,1,'wingotech','2011-12-27-17-08-23',315),(596,78,167,1,'wingotech','2011-12-27-17-08-23',314),(597,77,167,1,'wingotech','2011-12-27-17-08-23',312),(598,77,167,1,'wingotech','2011-12-27-17-08-23',310),(599,73,167,1,'wingotech','2011-12-27-17-08-23',290),(600,74,167,1,'wingotech','2011-12-27-17-08-23',294),(601,75,167,1,'wingotech','2011-12-27-17-08-23',297),(602,76,167,1,'wingotech','2011-12-27-17-08-23',302),(603,76,167,1,'wingotech','2011-12-27-17-08-23',304),(604,82,177,2,'wingotech','2011-12-27-17-16-13',333),(605,82,177,2,'wingotech','2011-12-27-17-16-13',331),(606,83,177,2,'wingotech','2011-12-27-17-16-13',336),(607,80,177,2,'wingotech','2011-12-27-17-16-13',325),(608,81,177,2,'wingotech','2011-12-27-17-16-13',329),(609,84,177,2,'wingotech','2011-12-27-17-16-13',339),(610,79,178,2,'dongbin','2011-12-27-17-18-19',321),(611,66,178,2,'dongbin','2011-12-27-17-18-19',267),(612,78,178,2,'dongbin','2011-12-27-17-18-19',314),(613,78,178,2,'dongbin','2011-12-27-17-18-19',316),(614,77,178,2,'dongbin','2011-12-27-17-18-19',312),(615,77,178,2,'dongbin','2011-12-27-17-18-19',311),(616,64,178,2,'dongbin','2011-12-27-17-18-19',261),(617,64,178,2,'dongbin','2011-12-27-17-18-19',259),(618,65,178,2,'dongbin','2011-12-27-17-18-19',266),(619,53,178,2,'dongbin','2011-12-27-17-18-19',223),(620,63,178,2,'dongbin','2011-12-27-17-18-19',257),(621,54,178,2,'dongbin','2011-12-27-17-18-19',225),(622,73,178,2,'dongbin','2011-12-27-17-18-19',291),(623,74,178,2,'dongbin','2011-12-27-17-18-19',295),(624,75,178,2,'dongbin','2011-12-27-17-18-19',300),(625,76,178,2,'dongbin','2011-12-27-17-18-19',302),(626,76,178,2,'dongbin','2011-12-27-17-18-19',304),(627,76,178,2,'dongbin','2011-12-27-17-18-19',305),(628,76,178,2,'dongbin','2011-12-27-17-18-19',308),(629,66,179,2,'wingotech','2011-12-27-17-20-51',267),(630,69,179,2,'wingotech','2011-12-27-17-20-51',277),(631,69,179,2,'wingotech','2011-12-27-17-20-51',275),(632,68,179,2,'wingotech','2011-12-27-17-20-51',273),(633,82,179,2,'wingotech','2011-12-27-17-20-51',332),(634,64,179,2,'wingotech','2011-12-27-17-20-51',262),(635,64,179,2,'wingotech','2011-12-27-17-20-51',261),(636,70,179,2,'wingotech','2011-12-27-17-20-51',281),(637,70,179,2,'wingotech','2011-12-27-17-20-51',283),(638,65,179,2,'wingotech','2011-12-27-17-20-51',263),(639,65,179,2,'wingotech','2011-12-27-17-20-51',266),(640,83,179,2,'wingotech','2011-12-27-17-20-51',337),(641,80,179,2,'wingotech','2011-12-27-17-20-51',325),(642,53,179,2,'wingotech','2011-12-27-17-20-51',223),(643,53,179,2,'wingotech','2011-12-27-17-20-51',221),(644,63,179,2,'wingotech','2011-12-27-17-20-51',257),(645,81,179,2,'wingotech','2011-12-27-17-20-51',328),(646,54,179,2,'wingotech','2011-12-27-17-20-51',225),(647,84,179,2,'wingotech','2011-12-27-17-20-51',338),(648,79,181,2,'wingotech','2011-12-27-17-26-19',320),(649,66,181,2,'wingotech','2011-12-27-17-26-19',267),(650,78,181,2,'wingotech','2011-12-27-17-26-19',315),(651,78,181,2,'wingotech','2011-12-27-17-26-19',314),(652,77,181,2,'wingotech','2011-12-27-17-26-19',312),(653,64,181,2,'wingotech','2011-12-27-17-26-19',261),(654,64,181,2,'wingotech','2011-12-27-17-26-19',259),(655,52,181,2,'wingotech','2011-12-27-17-26-19',218),(656,65,181,2,'wingotech','2011-12-27-17-26-19',265),(657,62,181,2,'wingotech','2011-12-27-17-26-19',255),(658,53,181,2,'wingotech','2011-12-27-17-26-19',223),(659,53,181,2,'wingotech','2011-12-27-17-26-19',221),(660,63,181,2,'wingotech','2011-12-27-17-26-19',257),(661,54,181,2,'wingotech','2011-12-27-17-26-19',227),(662,73,181,2,'wingotech','2011-12-27-17-26-19',290),(663,74,181,2,'wingotech','2011-12-27-17-26-19',295),(664,75,181,2,'wingotech','2011-12-27-17-26-19',296),(665,76,181,2,'wingotech','2011-12-27-17-26-19',302),(666,79,182,2,'wingotech','2011-12-27-17-27-19',320),(667,78,182,2,'wingotech','2011-12-27-17-27-19',314),(668,77,182,2,'wingotech','2011-12-27-17-27-19',313),(669,53,182,2,'wingotech','2011-12-27-17-27-19',223),(670,54,182,2,'wingotech','2011-12-27-17-27-19',225),(671,73,182,2,'wingotech','2011-12-27-17-27-19',290),(672,74,182,2,'wingotech','2011-12-27-17-27-19',293),(673,75,182,2,'wingotech','2011-12-27-17-27-19',299),(674,76,182,2,'wingotech','2011-12-27-17-27-19',307),(675,79,182,2,'dongbin','2011-12-27-17-28-35',320),(676,78,182,2,'dongbin','2011-12-27-17-28-35',314),(677,77,182,2,'dongbin','2011-12-27-17-28-35',310),(678,53,182,2,'dongbin','2011-12-27-17-28-35',223),(679,53,182,2,'dongbin','2011-12-27-17-28-35',221),(680,54,182,2,'dongbin','2011-12-27-17-28-35',225),(681,73,182,2,'dongbin','2011-12-27-17-28-35',290),(682,74,182,2,'dongbin','2011-12-27-17-28-35',293),(683,75,182,2,'dongbin','2011-12-27-17-28-35',297),(684,76,182,2,'dongbin','2011-12-27-17-28-35',305);

/*Table structure for table `vcity_resapp` */

DROP TABLE IF EXISTS `vcity_resapp`;

CREATE TABLE `vcity_resapp` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `parenID` int(11) DEFAULT NULL,
  `resID` int(11) DEFAULT NULL,
  `appDesc` varchar(64) DEFAULT NULL,
  `appCode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_resapp` */

insert  into `vcity_resapp`(`Id`,`parenID`,`resID`,`appDesc`,`appCode`) values (7,0,9,'test',NULL);

/*Table structure for table `vcity_rescommon` */

DROP TABLE IF EXISTS `vcity_rescommon`;

CREATE TABLE `vcity_rescommon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resId` int(11) DEFAULT NULL,
  `class` int(11) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `common` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=321 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_rescommon` */

insert  into `vcity_rescommon`(`id`,`resId`,`class`,`ip`,`userName`,`createTime`,`common`) values (254,54,0,'127.0.0.1',NULL,'2011-10-28','test'),(255,54,1,'127.0.0.1','','2011-10-28',''),(256,54,0,'127.0.0.1',NULL,'2011-10-28','test'),(258,56,1,NULL,NULL,'2011-10-28',NULL),(259,54,0,'127.0.0.1',NULL,'2011-10-31','test'),(260,63,1,'127.0.0.1','','2011-10-31',''),(261,18,2,'127.0.0.1','','2011-11-01',''),(262,54,0,'127.0.0.1',NULL,'2011-11-01','td>goodtd>'),(263,54,0,'127.0.0.1',NULL,'2011-11-01','input name=\"test\" type=\"button\" value=\"发表评论\" onclick=\"saveCom()\" /> '),(264,54,0,'127.0.0.1','dongbin','2011-11-02','getetete'),(265,15,1,'127.0.0.1','','2011-11-03',''),(266,63,0,'127.0.0.1',NULL,'2011-11-03','www.google.com'),(267,64,1,'127.0.0.1','','2011-11-03',''),(268,11,1,'127.0.0.1','','2011-11-03',''),(269,18,0,'127.0.0.1',NULL,'2011-11-04','good\r\ngood\r\ngood'),(270,18,0,'127.0.0.1',NULL,'2011-11-04','xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'),(271,54,0,'127.0.0.1',NULL,'2011-11-04',' <input name=\"test\" type=\"button\" value=\"发表评论\" onclick=\"saveCom()\" /> '),(272,64,0,'127.0.0.1',NULL,'2011-11-04',' input name=\"test\" type=\"button\" value=\"发表评论\" onclick=\"saveCom()\" /> '),(273,64,2,'127.0.0.1','','2011-11-04',''),(274,21,1,'127.0.0.1','','2011-11-04',''),(275,11,1,'127.0.0.1','','2011-11-04',''),(276,11,0,'127.0.0.1',NULL,'2011-11-04','test'),(277,11,0,'127.0.0.1',NULL,'2011-11-04','test'),(278,16,1,'127.0.0.1','','2011-11-04',''),(279,54,1,'127.0.0.1','','2011-11-04',''),(280,54,0,'127.0.0.1',NULL,'2011-11-04','test'),(281,15,1,'127.0.0.1','','2011-11-07',''),(282,54,1,'127.0.0.1','','2011-11-07',''),(283,63,2,'127.0.0.1','','2011-11-07',''),(284,63,0,'127.0.0.1',NULL,'2011-11-07','test'),(285,11,1,'127.0.0.1','','2011-11-08',''),(286,15,2,'127.0.0.1','','2011-11-08',''),(287,65,2,'119.132.73.242','','2011-11-14',''),(288,15,1,'119.132.73.242','','2011-11-15',''),(289,84,1,'119.132.73.47','','2011-11-16',''),(290,100,1,'119.132.74.181','','2011-11-20',''),(291,89,0,'119.132.74.181',NULL,'2011-11-21','test'),(292,89,1,'119.132.74.181','','2011-11-21',''),(293,94,1,'119.132.74.181','','2011-11-21',''),(294,94,0,'119.132.74.181',NULL,'2011-11-21','test'),(295,98,1,'125.89.9.166','administrator','2011-12-01',''),(296,94,1,'125.89.9.166','wingotech','2011-12-02',''),(297,102,2,'125.89.9.166','administrator','2011-12-02',''),(298,106,1,'119.132.72.102','administrator','2011-12-06',''),(299,106,1,'119.132.72.102','dongbin','2011-12-07',''),(300,98,0,'119.132.72.102',NULL,'2011-12-07','test'),(301,106,2,'119.132.72.102','','2011-12-07',''),(302,106,1,'119.132.75.209','','2011-12-09',''),(303,105,1,'119.132.74.45','dongbin','2011-12-12',''),(304,102,1,'119.132.74.45','dongbin','2011-12-12',''),(305,101,2,'119.132.74.45','dongbin','2011-12-12',''),(306,100,1,'119.132.74.45','dongbin','2011-12-12',''),(307,75,1,'119.132.74.4','administrator','2011-12-16',''),(308,75,2,'119.132.75.163','','2011-12-19',''),(309,105,1,'119.132.75.163','administrator','2011-12-19',''),(310,98,1,'119.132.75.163','administrator','2011-12-19',''),(311,89,1,'119.132.75.163','administrator','2011-12-19',''),(312,98,1,'119.132.75.163','','2011-12-19',''),(313,98,1,'125.89.11.175','administrator','2011-12-27',''),(314,103,1,'125.89.11.175','administrator','2011-12-27',''),(315,109,1,'119.132.75.202','administrator','2012-01-12',''),(316,109,1,'119.132.74.49','','2012-02-06',''),(317,105,1,'119.132.74.49','','2012-02-06',''),(318,102,1,'119.132.74.49','','2012-02-07',''),(319,93,1,'119.132.73.219','','2012-02-21',''),(320,106,1,'125.89.11.33','','2012-02-21','');

/*Table structure for table `vcity_resoprate` */

DROP TABLE IF EXISTS `vcity_resoprate`;

CREATE TABLE `vcity_resoprate` (
  `optId` int(11) NOT NULL AUTO_INCREMENT,
  `optName` varchar(64) DEFAULT NULL,
  `optCode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`optId`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_resoprate` */

insert  into `vcity_resoprate`(`optId`,`optName`,`optCode`) values (1,'愤怒的小鸟1','1010'),(4,'水果忍者','102'),(6,'植物大战僵尸','104'),(7,'通讯录','103'),(12,'UC','108'),(13,'来电通','109'),(14,'手机QQ','110'),(15,'谷歌地图','111'),(16,'金山词霸','112'),(17,'新浪微博','113'),(18,'凡客诚品','114'),(19,'米聊','115'),(20,'QQ音乐','116'),(21,'Goole音乐','117'),(22,'木马专杀','118'),(23,'仙剑','119'),(24,'X战警','120'),(25,'日历','1001'),(26,'通讯录','1002'),(27,'音乐','1003'),(28,'网络','1004'),(29,'信息','1005'),(30,'照片','1006'),(31,'下载','1007'),(32,'地图','1008'),(33,'视频','1009'),(34,'设置','1010'),(35,'游戏','1011'),(36,'应用程序','1012'),(37,'退出','1013'),(38,'菜单','1014'),(39,'绿键','1015'),(40,'红键','1016'),(41,'时间','1017'),(42,'模式','1018'),(43,'电话','1019'),(44,'收音机','1020'),(45,'邮件','1021'),(46,'手机网络','1022'),(47,'拨号','1023'),(48,NULL,'');

/*Table structure for table `vcity_subtask` */

DROP TABLE IF EXISTS `vcity_subtask`;

CREATE TABLE `vcity_subtask` (
  `subTaskID` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` int(11) DEFAULT NULL,
  `userName` varchar(20) DEFAULT NULL,
  `subTaskType` int(11) DEFAULT NULL,
  `subTaskDesc` varchar(255) DEFAULT NULL,
  `subTaskURL` varchar(255) DEFAULT NULL,
  `subTaskResoureceID` varchar(512) DEFAULT NULL,
  `subTaskState` int(11) DEFAULT NULL,
  `subTaskIndex` int(11) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`subTaskID`)
) ENGINE=MyISAM AUTO_INCREMENT=189 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_subtask` */

insert  into `vcity_subtask`(`subTaskID`,`taskId`,`userName`,`subTaskType`,`subTaskDesc`,`subTaskURL`,`subTaskResoureceID`,`subTaskState`,`subTaskIndex`,`flag`) values (184,42,'xlqb_1314',1,'前往：电信营业厅--服务员交流','','10111',0,1,NULL),(185,42,'wingotech',2,'大学生手机使用调查','/bbs2/surveyTest.do?id=35','35',0,3,NULL),(186,42,'xlqb_1314',2,'大学生手机使用调查','/bbs2/surveyTest.do?id=35','35',0,3,NULL),(187,42,'xlqb_1314',2,'通信行业调查','/bbs2/surveyTest.do?id=36','36',0,2,NULL),(183,42,'wingotech',1,'前往：电信营业厅--服务员交流','','10111',0,1,NULL),(188,42,'wingotech',2,'通信行业调查','/bbs2/surveyTest.do?id=36','36',0,2,NULL);

/*Table structure for table `vcity_surey` */

DROP TABLE IF EXISTS `vcity_surey`;

CREATE TABLE `vcity_surey` (
  `sureyId` int(11) NOT NULL AUTO_INCREMENT,
  `sureyName` varchar(255) DEFAULT NULL,
  `sureyDesc` varchar(255) DEFAULT NULL,
  `ceateTime` varchar(20) DEFAULT NULL,
  `createUser` varchar(50) DEFAULT NULL,
  `credit` int(11) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`sureyId`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_surey` */

insert  into `vcity_surey`(`sureyId`,`sureyName`,`sureyDesc`,`ceateTime`,`createUser`,`credit`,`flag`) values (36,'通信行业调查','通信行业调查','2011-12-26','administrator',0,0),(35,'大学生手机使用调查','大学生手机使用调查','2011-12-16','dongbin',0,0),(26,'手机使用调查','手机使用调查','2011-12-16','dongbin',0,0),(31,'手机应用调查','手机应用调查','2011-12-06','dongbin',0,0),(32,'手机喜欢度调查','手机喜欢度调查','2011-12-06','dongbin',0,0);

/*Table structure for table `vcity_taskuseronline` */

DROP TABLE IF EXISTS `vcity_taskuseronline`;

CREATE TABLE `vcity_taskuseronline` (
  `userName` varchar(50) NOT NULL,
  `lastUpdateTime` int(11) DEFAULT NULL,
  PRIMARY KEY (`userName`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `vcity_taskuseronline` */

/*Table structure for table `vcity_tempsurvey` */

DROP TABLE IF EXISTS `vcity_tempsurvey`;

CREATE TABLE `vcity_tempsurvey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `questionIds` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_tempsurvey` */

insert  into `vcity_tempsurvey`(`id`,`taskId`,`title`,`questionIds`) values (1,19,'临时问卷','1,59,2,3'),(2,27,'123','67,68,65,64,63'),(3,23,'test','62,63,64,65,66'),(4,23,'test','62,63,64,65,66'),(5,19,'测试问卷','33,22,1,59'),(6,28,'邀请体验问卷','52,53,70,69,68'),(7,19,'123','59,2,33,38'),(8,34,'学生手机调查','73,74,75,76,77,78,79,62,63,64,65,66'),(9,36,'1223任务问卷','80,84,81,76,77,75,54,66,65,64,70,69'),(10,38,'大学生群体调查','73,74,75,76,77,78,79,54,53,66,65,64,63'),(11,38,'通信行业调查','80,84,81,82,83,53,54,66,65,64,63,70,69,68'),(12,41,'123','73,74,75,76,77,78,79,52,53,54,62,63,64,65,66'),(13,41,'234','73,74,75,76,77,78,79,53,54');

/*Table structure for table `vcity_vresources` */

DROP TABLE IF EXISTS `vcity_vresources`;

CREATE TABLE `vcity_vresources` (
  `resId` int(11) NOT NULL AUTO_INCREMENT,
  `resName` varchar(255) DEFAULT NULL,
  `resDesc` varchar(512) DEFAULT NULL,
  `senceID` int(11) DEFAULT NULL,
  `resType` int(11) DEFAULT NULL,
  `resImage` varchar(255) DEFAULT NULL,
  `moduleFileName` varchar(255) DEFAULT NULL,
  `resURL` varchar(255) DEFAULT NULL,
  `productClass` int(11) DEFAULT NULL,
  `isRecommend` int(11) DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `createDate` varchar(20) DEFAULT NULL,
  `recommendImage` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`resId`)
) ENGINE=MyISAM AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

/*Data for the table `vcity_vresources` */

insert  into `vcity_vresources`(`resId`,`resName`,`resDesc`,`senceID`,`resType`,`resImage`,`moduleFileName`,`resURL`,`productClass`,`isRecommend`,`createUser`,`createDate`,`recommendImage`) values (88,'RockPlayer视频播放器','<p>软件简介：RockPlayer是一款嵌入式平台上的高性能全格式视频播放程序。凭借专为移动设备优化的播放核心以及高度优化的FFMpeg解码 器，RockPlayer已经成为了Android平台上性能最高支持格式最广泛的视频播放程序。<br />\r\nRockPlayer能支持的音视频容器格式有：avi mkv rmvb mp4 mov wmv asf wma wav mpg ts mpa dvd au mp3 mid ivf aiff ogm ogg cda flic d2v aac roq flac drc dsm swf pls pmp.音视频编码格式有AAC AC3 DTS FLV4 LPCM ALAW ULAW MPEG Audio MPEG-1 MPEG-2 RealVideo RealAideo Vorbis AVC VC1 XVID DIVX SVQ Theora Bink 基本包括了所有流行的多媒体格式。</p>',0,1,'c3cacc88-66d6-441a-9ccd-fc54caf25dca.gif','c3cacc88-66d6-441a-9ccd-fc54caf25dca.apk',NULL,NULL,0,NULL,NULL,NULL),(87,'PowerAMP音乐播放器','软件简介：PowerAMP是一款新生的android平台的音乐播放器，拥有自己的音乐解码，支持MP3播放，MP4/M4A，无损ALAC，，OGG格式，WAV，TTA，WMA等，支持歌词，能快速扫描SD卡音乐，UI界面和音质都非常好，优秀的音乐播放器.。</img /></img /></img /></img /></img />',0,1,'799dfeb1-3507-4f6a-b910-1edd94fa16f3.gif','799dfeb1-3507-4f6a-b910-1edd94fa16f3.apk',NULL,NULL,0,NULL,NULL,NULL),(83,'ES文件浏览器','软件简介：ES文件浏览器( ES File Explorer)是一个功能强大的免费的本地和网络文件管理器和应用程序管理器。',0,1,'72e047b5-5171-4188-bdbc-bd285588f381.gif','72e047b5-5171-4188-bdbc-bd285588f381.apk',NULL,NULL,0,NULL,NULL,NULL),(84,'iReader','软件简介：iReader是一款Android平台的读书软件,支持TXT, UMD, CHM, HTML/HTM, PDB, EBK2, EPUB等多种手机阅读格式的电子书阅读软件。 iReader支持手势翻页,有多种翻页特效,支持书签,全屏,自定义看书文字与背景,白天与夜间看书模式,自定义缩放,自定义看书时的屏幕亮度,备份看书历史,自定义编码等,功能非常之强大。',0,1,'113e3e2f-5299-47c7-968d-d3b50246c738.gif','113e3e2f-5299-47c7-968d-d3b50246c738.apk',NULL,NULL,0,NULL,NULL,NULL),(75,'摩托罗拉XT800','<p>产品型号：XT800<br />\r\n手机外型：直板<br />\r\n屏幕尺寸：3.7英寸<br />\r\n分 辨 率：480×854<br />\r\n操作系统：Android 2.0<br />\r\n上市时间：2009年</p>',0,2,'38dd6a18-57be-48b9-9548-fb7279df2029.png','38dd6a18-57be-48b9-9548-fb7279df2029.zip',NULL,NULL,5,NULL,NULL,'38dd6a18-57be-48b9-9548-fb7279df2029.jpg'),(78,'365日历','软件简介：本程序是365日历网的手机版，您手机上的数据将会与您电脑和网络上的数据同步，永不丢失。PC版与手机版结合使用会让您的时间管理更高效。',0,1,'0d34856b-ee99-4c13-8425-45499e6e0f89.gif','0d34856b-ee99-4c13-8425-45499e6e0f89.apk',NULL,NULL,0,NULL,NULL,NULL),(89,'三星 i909','<p>生产厂商：三星</p>\r\n<p>产品型号：i909</p>\r\n<p>手机外型：直板</p>\r\n<p>屏幕尺寸：4.0英寸</p>\r\n<p>分 辨 率：480×800</p>\r\n<p>操作系统：Android 2.1</p>\r\n<p>上市时间：2010年</p>',0,2,'3e38aabe-7dba-4ba6-9dc0-24fb06bad362.png','3e38aabe-7dba-4ba6-9dc0-24fb06bad362.zip',NULL,NULL,0,NULL,NULL,NULL),(90,'UC浏览器','软件简介：UC浏览器是UC优视科技开发的一款手机浏览器，支持WEB、WAP页面浏览，速度快而稳定，具有视频播放、网站导航、搜索、下载、个人数据管理等功能，您能随时随地通过UC浏览器进行无线冲浪，将互联网装进口袋，享受高质移动生活！',0,1,'1f5b2758-1457-465b-bf27-e6fd74648d05.gif','1f5b2758-1457-465b-bf27-e6fd74648d05.apk',NULL,NULL,0,NULL,NULL,NULL),(92,'都市摩天楼','软件简介：本游戏，是一个用户根据当前摇动的箱子的位置，来堆放箱子，当箱子达到一定高度，即过关。',0,1,'05cad5f3-14da-433d-b724-7a7b5dcbe333.gif','05cad5f3-14da-433d-b724-7a7b5dcbe333.apk',NULL,NULL,0,NULL,NULL,NULL),(93,'飞鸽传书','<p>软件简介：一款非常实用的小软件。电脑上只需要安装PC版的飞鸽或者飞秋，就可以与手机进行文件传送，局域网间发送信息，省去连接电脑的麻烦。使用时如果发现上线人数不全，点击右键刷新列表即可。</p>',0,1,'c949ba3c-ac0e-4482-961b-7408f3638b10.gif','c949ba3c-ac0e-4482-961b-7408f3638b10.apk',NULL,NULL,0,NULL,NULL,NULL),(94,'好用的罗盘','<p>软件简介：超级指南针，重力感应+指南针，支持桌面小部件；多种风格界面+罗盘自由DIY。支持桌面小控件,可以拖个指南针放在桌面； ?更换背景；&nbsp; 更换样式；&nbsp; 苹果Safari风格； iPhone 3GS风格； 夜视风格； 司南； 宋代水手； 带水平仪功能； 校准； GPS位置，地址； 显示GPS的状态； 显示联网的状态。</p>',0,1,'7742af2e-540e-4982-a6a0-d7976c7cf527.gif','7742af2e-540e-4982-a6a0-d7976c7cf527.apk',NULL,NULL,0,NULL,NULL,NULL),(98,'手机QQ','<p>视频通话</p>\r\n<p>语音聊天</p>\r\n<p>信手涂鸦</p>\r\n<p>截屏功能</p>',0,1,'982f2a27-41f7-4b65-814d-506c73467485.gif','982f2a27-41f7-4b65-814d-506c73467485.apk',NULL,NULL,5,NULL,NULL,'982f2a27-41f7-4b65-814d-506c73467485.jpg'),(99,'天翼空间','天翼空间是中国电信为用户提供各类手机应用、数字内容发现、下载、购买的一项移动业务服务。天翼空间由面向用户的应用商城和面向开发者的应用工厂（开发者社区）组成。<br />',0,1,'8635f0bc-9a4f-403e-9d89-c7ce4bcd93d8.gif','8635f0bc-9a4f-403e-9d89-c7ce4bcd93d8.apk',NULL,NULL,0,NULL,NULL,NULL),(100,'天翼阅读','“天翼阅读”客户端软件是利用CDMA无线接入方式与服务器进行交互和下载服务，为用户提供包括原创、书籍、杂志、漫画等各类电子书的在线和离线阅读，并支持书评、推荐等阅读参与方的互动服务.天翼阅读展现形式包括WAP、客户端、专用终端等方式。客户端作为业务形式中的一种重要展现形式，需着力打造畅快的阅读体验。',0,1,'ddd2994c-5369-4f34-9cae-6fc8f344de3c.gif','ddd2994c-5369-4f34-9cae-6fc8f344de3c.apk',NULL,NULL,0,NULL,NULL,NULL),(101,'天翼相册','天翼相册',0,1,'f597f793-d6b3-4d49-85a4-8e64a9c13948.gif','f597f793-d6b3-4d49-85a4-8e64a9c13948.apk',NULL,NULL,0,NULL,NULL,NULL),(102,'天翼助手','天翼助手是一款集成天翼3G应用管理平台的日程管理工具，不仅为用户提供日程管理功能，而且为用户提供访问各种天翼3G应用的统一门户。 用户使用天翼助手可以访问、管理、下载各种电信应用，方便用户使用电信3G业务。天翼助手主要包括6大类功能，分别是：日历、日程、记事、工作、应用、工具。',0,1,'93e8d4e3-1872-4c1c-875c-71db2f8d4876.gif','93e8d4e3-1872-4c1c-875c-71db2f8d4876.apk',NULL,NULL,0,NULL,NULL,NULL),(103,'天翼家园','天翼家园',0,1,'6b667654-1ec1-4dcf-98bf-0cb6a9864e56.gif','6b667654-1ec1-4dcf-98bf-0cb6a9864e56.apk',NULL,NULL,0,NULL,NULL,NULL),(104,'爱音乐','“爱音乐客户端 ”依托中国电信云音乐服务平台，是爱音乐倾力打造的新一代免费音乐软件，以云端海量正版音乐和特色服务为核心，向用户提供流畅的在线音乐和丰富的音乐服务。集成本地歌曲管理播放、在线试听及下载、手机彩铃振铃一键设置、音乐搜索分享、娱乐资讯浏览等功能；具备海量曲库、高品正版、歌星图片、精确歌词、全网接入等特色。<br />',0,1,'5589d103-81df-47aa-9a8c-89d91a307e49.gif','5589d103-81df-47aa-9a8c-89d91a307e49.apk',NULL,NULL,0,NULL,NULL,NULL),(105,'天翼live','<div class=\"yyxq_se_top_kang_nr1\">\r\n<p>天翼Live客户端是中国电信面向家庭和个人用户推出的集网络接入、免费发送短信、IM聊天、在线高清视频以及热门资讯等服务于一体的综合信息服务产品。</p>\r\n</div>',0,1,'ef878951-d832-4559-91bf-30045ae586ee.gif','ef878951-d832-4559-91bf-30045ae586ee.apk',NULL,NULL,0,NULL,NULL,NULL),(106,'三星I9000','<p>手机类型：智能手机,3G手机</p>\r\n<p>上市时间：2010年</p>\r\n<p>网络制式：支持 WCDMA，支持 GPRS/EDGE/HSDPA </p>\r\n<p>外形颜色：直板 黑色</p>\r\n<p>体积重量：122×64.2×9.99mm，124g </p>\r\n<p>摄像头：500万像素 </p>',0,2,'9904548e-2cd1-4fed-9164-cd33a63b9614.png','9904548e-2cd1-4fed-9164-cd33a63b9614.zip',NULL,NULL,0,NULL,NULL,NULL),(107,'摩托罗拉 ME525','<p>手机尺寸：107×59×13.4mm</p>\r\n<p>外观设计：直板</p>\r\n<p>操作系统：Android OS v2.2</p>\r\n<p>CPU：800MHz</p>\r\n<p>机身内存：2GB ROM+512MB RAM</p>\r\n<p>电池规格：1500毫安时锂电池</p>',0,2,'62cef764-7bac-4a49-94a3-b08492324b46.png','62cef764-7bac-4a49-94a3-b08492324b46.zip',NULL,NULL,0,'administrator',NULL,NULL),(108,'华为 Honor','<p>网络模式：GSM，WCDMA</p>\r\n<p>手机尺寸：122×61×11mm</p>\r\n<p>操作系统：Android OS v2.3</p>\r\n<p>CPU：1.4GHz</p>\r\n<p>机身内存：4GB ROM+512MB RAM</p>\r\n<p>电池规格：1930mAh</p>',0,2,'c086acb8-f066-448c-9ca3-9e33e7e1ffc5.png','c086acb8-f066-448c-9ca3-9e33e7e1ffc5.zip',NULL,NULL,0,'administrator',NULL,NULL),(109,'HTC Desire S','<p>手机尺寸：115×59.8×11.63mm</p>\r\n<p>外观设计：直板 </p>\r\n<p>操作系统：Android 2.3</p>\r\n<p>CPU：1024MHz </p>\r\n<p>机身内存：768MB RAM,512MB ROM</p>\r\n<p>电池规格：1450毫安时锂电池</p>',0,2,'1625a7ed-eb0a-4ac0-9100-81d13c60bcbb.png','1625a7ed-eb0a-4ac0-9100-81d13c60bcbb.zip',NULL,NULL,0,'administrator',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
