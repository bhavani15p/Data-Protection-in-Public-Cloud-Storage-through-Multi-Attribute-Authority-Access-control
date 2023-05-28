/*

SQLyog Ultimate v8.55 
MySQL - 5.1.54-community : Database - decentralised

*********************************************************************

*/



/*!40101 SET NAMES utf8 */;



/*!40101 SET SQL_MODE=''*/;



/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`decentralised` /*!40100 DEFAULT CHARACTER SET latin1 */;



USE `decentralised`;



/*Table structure for table `aa` */



DROP TABLE IF EXISTS `aa`;



CREATE TABLE `aa` (
  `username` varchar(150) DEFAULT NULL,
  `cardno` varchar(150) DEFAULT NULL,
  `privatekey` varchar(150) DEFAULT NULL,
  `publickey` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*Data for the table `aa` */



insert  into `aa`(`username`,`cardno`,`privatekey`,`publickey`) values ('shaik','12345678','48443#44197','48443#47533'),('mohammed','12345678','42593#22337','42593#16253');



/*Table structure for table `ca` */



DROP TABLE IF EXISTS `ca`;



CREATE TABLE `ca` (
  `uid` varchar(150) DEFAULT NULL,
  `cardno` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*Data for the table `ca` */



insert  into `ca`(`uid`,`cardno`) values ('1','12345678'),('2','98745699'),('3','11112333'),('4','85858582'),('5','99999999'),('6','25825887'),('7','85258577'),('8','77777777'),('9','35789631'),('10','14785258');



/*Table structure for table `filelist` */



DROP TABLE IF EXISTS `filelist`;



CREATE TABLE `filelist` (
  `username` varchar(150) DEFAULT NULL,
  `filename` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*Data for the table `filelist` */



insert  into `filelist`(`username`,`filename`) values ('shaik','two.txt');



/*Table structure for table `login` */



DROP TABLE IF EXISTS `login`;



CREATE TABLE `login` (
  `username` varchar(150) DEFAULT NULL,
  `upassword` varchar(150) DEFAULT NULL,
  `gender` varchar(150) DEFAULT NULL,
  `designation` varchar(150) DEFAULT NULL,
  `location` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*Data for the table `login` */



insert  into `login`(`username`,`upassword`,`gender`,`designation`,`location`) values ('bhavani37','bhavani@15','Female','Project_Manager','Bangalore'),('sneha27','sneha@27','female','Project_Manager','Bangalore');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

