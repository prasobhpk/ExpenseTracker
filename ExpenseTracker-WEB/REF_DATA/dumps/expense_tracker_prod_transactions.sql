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
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `TRAN_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(19,2) NOT NULL,
  `BROKERAGE` decimal(19,2) NOT NULL,
  `EXCHANGE` varchar(255) NOT NULL,
  `OTHER_CHRGS` decimal(19,2) DEFAULT NULL,
  `PRICE` decimal(19,2) NOT NULL,
  `QUANTITY` decimal(19,2) NOT NULL,
  `TOTAL_AMOUNT` decimal(19,2) NOT NULL,
  `TRADE_DATE` date NOT NULL,
  `TRADED` bit(1) NOT NULL,
  `TRANSACTION_TYPE` varchar(255) NOT NULL,
  `VER_NO` bigint(20) DEFAULT NULL,
  `BROKER_ID` bigint(20) DEFAULT NULL,
  `INSTRUMENT_ID` bigint(20) NOT NULL,
  `PORTFOLIO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TRAN_ID`),
  KEY `FKFE987155C31480FE` (`BROKER_ID`),
  KEY `FKFE98715571CCDB68` (`INSTRUMENT_ID`),
  KEY `FKFE9871551D2C528A` (`PORTFOLIO_ID`),
  CONSTRAINT `FKFE9871551D2C528A` FOREIGN KEY (`PORTFOLIO_ID`) REFERENCES `portfolio` (`PORTFOLIO_ID`),
  CONSTRAINT `FKFE98715571CCDB68` FOREIGN KEY (`INSTRUMENT_ID`) REFERENCES `equities` (`INSTRUMENT_ID`),
  CONSTRAINT `FKFE987155C31480FE` FOREIGN KEY (`BROKER_ID`) REFERENCES `brokerage_structures` (`EXPENSE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,42515.45,289.74,'NSE',NULL,293.21,145.00,42805.19,'2011-09-29','\0','BUY',0,1,1028,2),(2,10552.40,71.91,'NSE',NULL,45.88,230.00,10624.31,'2011-09-29','\0','BUY',0,1,838,2),(3,10290.00,70.13,'NSE',NULL,34.30,300.00,10360.13,'2011-09-29','\0','BUY',0,1,3175,2),(4,4235.00,33.08,'NSE',NULL,60.50,70.00,4268.08,'2011-09-29','\0','BUY',0,1,3502,2),(5,28912.00,197.04,'NSE',NULL,144.56,200.00,29109.04,'2011-09-29','\0','BUY',0,1,1310,2),(6,21519.40,146.65,'NSE',NULL,153.71,140.00,21666.05,'2011-09-29','\0','BUY',0,1,2710,2),(7,12120.00,82.60,'NSE',NULL,303.00,40.00,12202.60,'2011-09-29','\0','BUY',0,1,3714,2),(8,4075.00,32.87,'NSE',NULL,8.15,500.00,4107.87,'2011-09-29','\0','BUY',0,1,1955,2),(9,7498.50,51.10,'NSE',NULL,249.95,30.00,7549.60,'2011-10-01','\0','BUY',0,1,1028,2);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
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
