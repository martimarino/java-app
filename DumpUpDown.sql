CREATE DATABASE  IF NOT EXISTS `updown` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `updown`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: updown
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `archiviomisurazioni`
--

DROP TABLE IF EXISTS `archiviomisurazioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archiviomisurazioni` (
  `username` varchar(45) NOT NULL,
  `dataMisurazione` date NOT NULL,
  `pressioneMin` int(11) NOT NULL,
  `pressioneMax` int(11) NOT NULL,
  PRIMARY KEY (`username`,`dataMisurazione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archiviomisurazioni`
--

LOCK TABLES `archiviomisurazioni` WRITE;
/*!40000 ALTER TABLE `archiviomisurazioni` DISABLE KEYS */;
INSERT INTO `archiviomisurazioni` VALUES ('emma@roma.it','2019-04-15',75,130),('isabelle@paris.fr','2019-03-14',90,127),('isabelle@paris.fr','2019-05-18',75,114),('jacob@london.uk','2019-04-20',74,135),('martina.marino1996@gmail.com','2019-03-02',65,90),('martina.marino1996@gmail.com','2019-05-13',80,120),('martina.marino1996@gmail.com','2019-05-16',70,115),('martina.marino1996@gmail.com','2019-05-18',86,100),('martina.marino1996@gmail.com','2019-05-22',96,110),('martina.marino1996@gmail.com','2019-05-23',85,115),('martina.marino1996@gmail.com','2019-05-25',50,100),('michael@boston.us','2019-01-20',86,129);
/*!40000 ALTER TABLE `archiviomisurazioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valorisoglia`
--

DROP TABLE IF EXISTS `valorisoglia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valorisoglia` (
  `username` varchar(45) NOT NULL,
  `sogliaMin` int(11) DEFAULT NULL,
  `sogliaMax` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valorisoglia`
--

LOCK TABLES `valorisoglia` WRITE;
/*!40000 ALTER TABLE `valorisoglia` DISABLE KEYS */;
INSERT INTO `valorisoglia` VALUES ('bertimarta95@gmail.com',80,120),('isabelle@paris.fr',70,130),('jacob@london.uk',85,115),('luciano.marino.sp@gmail.com',85,135),('martina.marino1996@gmail.com',95,130),('michael@boston.us',90,120);
/*!40000 ALTER TABLE `valorisoglia` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-04  9:34:58
