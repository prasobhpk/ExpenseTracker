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
-- Table structure for table `holdings`
--

DROP TABLE IF EXISTS `holdings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holdings` (
  `HOLDING_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(19,2) NOT NULL,
  `BROKERAGE` decimal(19,2) NOT NULL,
  `PRICE` decimal(19,2) NOT NULL,
  `QUANTITY` decimal(19,2) NOT NULL,
  `TOTAL_AMOUNT` decimal(19,2) NOT NULL,
  `VER_NO` bigint(20) DEFAULT NULL,
  `BROKER_ID` bigint(20) DEFAULT NULL,
  `INSTRUMENT_ID` bigint(20) NOT NULL,
  `PORTFOLIO_ID` bigint(20) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`HOLDING_ID`),
  UNIQUE KEY `INSTRUMENT_ID` (`INSTRUMENT_ID`,`USER_ID`,`PORTFOLIO_ID`),
  KEY `FKFF1D210C31480FE` (`BROKER_ID`),
  KEY `FKFF1D21071CCDB68` (`INSTRUMENT_ID`),
  KEY `FKFF1D210A949721D` (`USER_ID`),
  KEY `FKFF1D2101D2C528A` (`PORTFOLIO_ID`),
  CONSTRAINT `FKFF1D2101D2C528A` FOREIGN KEY (`PORTFOLIO_ID`) REFERENCES `portfolio` (`PORTFOLIO_ID`),
  CONSTRAINT `FKFF1D21071CCDB68` FOREIGN KEY (`INSTRUMENT_ID`) REFERENCES `equities` (`INSTRUMENT_ID`),
  CONSTRAINT `FKFF1D210A949721D` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`),
  CONSTRAINT `FKFF1D210C31480FE` FOREIGN KEY (`BROKER_ID`) REFERENCES `brokerage_structures` (`EXPENSE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holdings`
--

LOCK TABLES `holdings` WRITE;
/*!40000 ALTER TABLE `holdings` DISABLE KEYS */;
INSERT INTO `holdings` VALUES (1,50013.95,340.84,285.79,175.00,50354.79,1,1,1028,2,50),(2,10552.40,71.91,45.88,230.00,10624.31,0,1,838,2,50),(3,10290.00,70.13,34.30,300.00,10360.13,0,1,3175,2,50),(4,4235.00,33.08,60.50,70.00,4268.08,0,1,3502,2,50),(5,28912.00,197.04,144.56,200.00,29109.04,0,1,1310,2,50),(6,21519.40,146.65,153.71,140.00,21666.05,0,1,2710,2,50),(7,12120.00,82.60,303.00,40.00,12202.60,0,1,3714,2,50),(8,4075.00,32.87,8.15,500.00,4107.87,0,1,1955,2,50);
/*!40000 ALTER TABLE `holdings` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-10-01  3:42:33