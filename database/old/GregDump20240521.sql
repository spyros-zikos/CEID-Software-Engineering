CREATE DATABASE  IF NOT EXISTS `casheri` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `casheri`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: casheri
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS driver;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE driver (
  user_id int unsigned NOT NULL,
  car_model varchar(255) NOT NULL,
  car_id varchar(255) NOT NULL,
  car_color varchar(255) NOT NULL,
  max_passenger_capacity int NOT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY driver_car_id_unique (car_id),
  CONSTRAINT driver_user_id_foreign FOREIGN KEY (user_id) REFERENCES `user` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES driver WRITE;
/*!40000 ALTER TABLE driver DISABLE KEYS */;
INSERT INTO driver VALUES (1,'model 1','AAA1234','red',4);
/*!40000 ALTER TABLE driver ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS passenger;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE passenger (
  user_id int unsigned NOT NULL,
  total_trips int NOT NULL,
  reviews_rank varchar(255) NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT passenger_user_id_foreign FOREIGN KEY (user_id) REFERENCES `user` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES passenger WRITE;
/*!40000 ALTER TABLE passenger DISABLE KEYS */;
INSERT INTO passenger VALUES (2,11,'not specified'),(3,100,'4.5'),(4,80,'4.2'),(6,51,'3.2');
/*!40000 ALTER TABLE passenger ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS post;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE post (
  id int unsigned NOT NULL AUTO_INCREMENT,
  driver_id int unsigned NOT NULL,
  trip_id int unsigned NOT NULL,
  post_datetime datetime NOT NULL,
  post_location_lat decimal(9,6) DEFAULT NULL,
  post_location_long decimal(9,6) DEFAULT NULL,
  photo varchar(255) DEFAULT NULL,
  title varchar(255) DEFAULT NULL,
  max_passengers int DEFAULT NULL,
  PRIMARY KEY (id),
  KEY post_driver_id_foreign (driver_id),
  KEY post_trip_id_foreign (trip_id),
  CONSTRAINT post_driver_id_foreign FOREIGN KEY (driver_id) REFERENCES driver (user_id),
  CONSTRAINT post_trip_id_foreign FOREIGN KEY (trip_id) REFERENCES trip (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES post WRITE;
/*!40000 ALTER TABLE post DISABLE KEYS */;
INSERT INTO post VALUES (1,1,5,'2024-02-17 10:00:00',37.988700,22.353900,'ski.jpg','Ski Trip Kalavrita',3),(2,1,6,'2024-03-10 15:30:00',38.246600,21.734600,'carnival.jpg','Patras Carnival',4),(3,1,7,'2024-08-25 18:45:00',37.591600,23.022800,'epidaurus.jpg','Theater of Epidaurus',2);
/*!40000 ALTER TABLE post ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_passenger`
--

DROP TABLE IF EXISTS post_passenger;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE post_passenger (
  post_id int unsigned NOT NULL,
  passenger_id int unsigned NOT NULL,
  PRIMARY KEY (post_id,passenger_id),
  KEY passenger_id (passenger_id),
  CONSTRAINT post_passenger_ibfk_1 FOREIGN KEY (post_id) REFERENCES post (id),
  CONSTRAINT post_passenger_ibfk_2 FOREIGN KEY (passenger_id) REFERENCES passenger (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_passenger`
--

LOCK TABLES post_passenger WRITE;
/*!40000 ALTER TABLE post_passenger DISABLE KEYS */;
INSERT INTO post_passenger VALUES (1,2),(2,2),(1,3),(2,3),(1,4),(2,4),(3,4),(2,6),(3,6);
/*!40000 ALTER TABLE post_passenger ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ride`
--

DROP TABLE IF EXISTS ride;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE ride (
  id int unsigned NOT NULL AUTO_INCREMENT,
  trip_id int unsigned NOT NULL,
  passenger_id int unsigned NOT NULL,
  date_time datetime NOT NULL,
  start_latitude double(8,2) NOT NULL,
  start_longitude double(8,2) NOT NULL,
  end_latitude double(8,2) NOT NULL,
  end_longitude double(8,2) NOT NULL,
  cost double(8,2) NOT NULL,
  PRIMARY KEY (id),
  KEY ride_trip_id_foreign (trip_id),
  KEY ride_passenger_id_foreign (passenger_id),
  CONSTRAINT ride_passenger_id_foreign FOREIGN KEY (passenger_id) REFERENCES passenger (user_id),
  CONSTRAINT ride_trip_id_foreign FOREIGN KEY (trip_id) REFERENCES trip (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride`
--

LOCK TABLES ride WRITE;
/*!40000 ALTER TABLE ride DISABLE KEYS */;
INSERT INTO ride VALUES (1,1,2,'2024-12-31 14:30:00',38.30,21.81,38.24,21.77,1.04);
/*!40000 ALTER TABLE ride ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS trip;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE trip (
  id int unsigned NOT NULL AUTO_INCREMENT,
  driver_id int unsigned NOT NULL,
  date_time datetime NOT NULL,
  start_latitude double(8,2) NOT NULL,
  start_longitude double(8,2) NOT NULL,
  end_latitude double(8,2) NOT NULL,
  end_longitude double(8,2) NOT NULL,
  cost float NOT NULL,
  PRIMARY KEY (id),
  KEY trip_driver_id_foreign (driver_id),
  CONSTRAINT trip_driver_id_foreign FOREIGN KEY (driver_id) REFERENCES driver (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES trip WRITE;
/*!40000 ALTER TABLE trip DISABLE KEYS */;
INSERT INTO trip VALUES (1,1,'2024-12-31 14:30:00',38.26,21.81,38.28,21.81,1.34),(5,1,'2024-02-17 10:00:00',38.00,22.11,38.04,22.06,50),(6,1,'2024-03-10 15:30:00',37.98,23.73,38.25,21.73,35),(7,1,'2024-08-25 18:45:00',37.68,22.87,37.59,23.02,60);
/*!40000 ALTER TABLE trip ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS user;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  id int unsigned NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  full_name varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  phone bigint NOT NULL,
  latitude double(8,2) NOT NULL,
  longitude double(8,2) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY user_username_unique (username),
  UNIQUE KEY user_phone_unique (phone)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES user WRITE;
/*!40000 ALTER TABLE user DISABLE KEYS */;
INSERT INTO user VALUES (1,'user1','full name 1','pass1',6900000001,38.29,21.81),(2,'user2','full name 2','pass2',6900000002,38.24,21.73),(3,'johndoe','John Doe','password123',1234567890,40.71,-74.01),(4,'janesmith','Jane Smith','pass123',9876543210,34.05,-118.24),(6,'johnpap','Jake Papadopoulos','password123',1234567899,40.71,-74.01);
/*!40000 ALTER TABLE user ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-21 17:08:56
