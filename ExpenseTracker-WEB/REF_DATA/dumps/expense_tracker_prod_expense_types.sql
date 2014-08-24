CREATE DATABASE  IF NOT EXISTS `expense_tracker_prod` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `expense_tracker_prod`;
-- MySQL dump 10.13  Distrib 5.5.15, for Win32 (x86)
--
-- Host: localhost    Database: expense_tracker_prod
-- ------------------------------------------------------
-- Server version	5.5.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `expense_types`
--

DROP TABLE IF EXISTS `expense_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expense_types` (
  `TYPE_ID` bigint(20) NOT NULL,
  `DESCRIPTION` longtext,
  `SHOW_IN_DB` bit(1) DEFAULT NULL,
  `EXP_TYPE` varchar(255) NOT NULL,
  `VER_NO` bigint(20) DEFAULT NULL,
  `USER_FK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TYPE_ID`),
  KEY `FK11740D92A94971C7` (`USER_FK`),
  CONSTRAINT `FK11740D92A94971C7` FOREIGN KEY (`USER_FK`) REFERENCES `users` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_types`
--

LOCK TABLES `expense_types` WRITE;
/*!40000 ALTER TABLE `expense_types` DISABLE KEYS */;
INSERT INTO `expense_types` VALUES (1,'Daily Expense','','Daily Expense',1,50),(2,'Transport','','Transport',1,50),(3,'Room Expense','','Room Expense',1,50),(4,'Shopping','','Shopping',1,50),(5,'Investments','\0','Investments',0,50),(6,'Kadangal','','Kadangal',1,50);
/*!40000 ALTER TABLE `expense_types` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-10-01  3:42:31
