-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: quest
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `point_in_quest`
--

DROP TABLE IF EXISTS `point_in_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `point_in_quest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `point_order` int(11) DEFAULT NULL,
  `point_id` int(11) DEFAULT NULL,
  `quest_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2xdctl739h81r23gwcuskkf03` (`point_id`),
  KEY `FKftpqse16ojfojlj4wacx99ffe` (`quest_id`),
  CONSTRAINT `FK2xdctl739h81r23gwcuskkf03` FOREIGN KEY (`point_id`) REFERENCES `point` (`id`),
  CONSTRAINT `FKftpqse16ojfojlj4wacx99ffe` FOREIGN KEY (`quest_id`) REFERENCES `quest` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `point_in_quest`
--

LOCK TABLES `point_in_quest` WRITE;
/*!40000 ALTER TABLE `point_in_quest` DISABLE KEYS */;
INSERT INTO `point_in_quest` VALUES (1,1,1,1),(2,2,2,1);
/*!40000 ALTER TABLE `point_in_quest` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-22 14:13:25
