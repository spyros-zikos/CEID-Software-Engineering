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
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS notifications;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE notifications (
  id int unsigned NOT NULL AUTO_INCREMENT,
  sender_id int unsigned NOT NULL,
  receiver_id int unsigned NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (id),
  KEY notifications_receiver_id_foreign (receiver_id),
  KEY notifications_sender_id_foreign (sender_id),
  CONSTRAINT notifications_receiver_id_foreign FOREIGN KEY (receiver_id) REFERENCES `user` (id),
  CONSTRAINT notifications_sender_id_foreign FOREIGN KEY (sender_id) REFERENCES `user` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES notifications WRITE;
/*!40000 ALTER TABLE notifications DISABLE KEYS */;
/*!40000 ALTER TABLE notifications ENABLE KEYS */;
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
  reviews_rank decimal(4,2) NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT passenger_user_id_foreign FOREIGN KEY (user_id) REFERENCES `user` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES passenger WRITE;
/*!40000 ALTER TABLE passenger DISABLE KEYS */;
INSERT INTO passenger VALUES (2,11,4.20),(3,6,4.50),(4,100,4.50),(5,800,4.20),(6,51,3.20);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES post WRITE;
/*!40000 ALTER TABLE post DISABLE KEYS */;
INSERT INTO post VALUES (2,1,7,'2024-02-17 10:00:00',37.988700,22.353900,'ski.jpg','Ski Trip Kalavrita',3),(3,1,8,'2024-03-10 15:30:00',38.246600,21.734600,'carnival.jpg','Patras Carnival',4),(4,1,9,'2024-08-25 18:45:00',37.591600,23.022800,'epidaurus.jpg','Theater of Epidaurus',2);
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
INSERT INTO post_passenger VALUES (2,2),(4,2),(2,3),(3,3),(4,3),(3,4),(3,5),(2,6),(3,6);
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
  driver_id int unsigned NOT NULL,
  passenger_id int unsigned NOT NULL,
  date_time datetime NOT NULL,
  duration time NOT NULL,
  start_latitude decimal(18,9) NOT NULL,
  start_longitude decimal(18,9) NOT NULL,
  end_latitude decimal(18,9) NOT NULL,
  end_longitude decimal(18,9) NOT NULL,
  cost double(8,2) NOT NULL,
  `status` enum('inthefuture','waiting','inprogress','completed') NOT NULL DEFAULT 'inthefuture',
  PRIMARY KEY (id),
  KEY ride_trip_id_foreign (trip_id),
  KEY ride_passenger_id_foreign (passenger_id),
  CONSTRAINT ride_passenger_id_foreign FOREIGN KEY (passenger_id) REFERENCES passenger (user_id),
  CONSTRAINT ride_trip_id_foreign FOREIGN KEY (trip_id) REFERENCES trip (id)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride`
--

LOCK TABLES ride WRITE;
/*!40000 ALTER TABLE ride DISABLE KEYS */;
INSERT INTO ride VALUES (1,1,1,2,'2024-01-08 13:30:00','00:10:00',38.270508000,21.757026000,38.296191000,21.794768000,1.21,'inthefuture'),(2,1,1,3,'2024-01-08 13:30:00','00:12:00',38.273653000,21.759664000,38.295354000,21.787916000,1.58,'inthefuture'),(3,2,1,2,'2024-02-05 14:30:00','00:07:00',38.270508000,21.757026000,38.296191000,21.794768000,1.04,'inthefuture'),(4,2,1,3,'2024-02-05 14:30:00','00:09:00',38.273659000,21.759664000,38.295354000,21.787916000,1.34,'inthefuture'),(5,3,1,2,'2024-02-15 09:30:00','00:09:00',38.270508000,21.757026000,38.296191000,21.794768000,1.25,'inthefuture'),(6,3,1,3,'2024-02-15 09:30:00','00:11:20',38.273659000,21.759664000,38.295354000,21.787916000,1.62,'inthefuture'),(7,4,1,2,'2024-06-14 13:00:00','00:08:00',38.270508000,21.757026000,38.286417000,21.786400000,2.10,'inthefuture'),(8,4,1,3,'2024-01-14 09:30:00','00:09:00',38.277246000,21.763197000,38.287642000,21.791767000,0.85,'inthefuture');
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
  duration time NOT NULL,
  start_latitude decimal(18,9) NOT NULL,
  start_longitude decimal(18,9) NOT NULL,
  end_latitude decimal(18,9) NOT NULL,
  end_longitude decimal(18,9) NOT NULL,
  passenger_capacity int NOT NULL,
  repeat_trip tinyint(1) NOT NULL,
  `status` enum('completed','incomplete','inprogress','canceled') NOT NULL DEFAULT 'incomplete',
  PRIMARY KEY (id),
  KEY trip_driver_id_foreign (driver_id),
  CONSTRAINT trip_driver_id_foreign FOREIGN KEY (driver_id) REFERENCES driver (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES trip WRITE;
/*!40000 ALTER TABLE trip DISABLE KEYS */;
INSERT INTO trip VALUES (1,1,'2024-01-08 13:30:00','00:16:00',38.255309000,21.746003000,38.289863000,21.795714000,4,0,'completed'),(2,1,'2024-02-05 14:30:00','00:10:00',38.255309000,21.746003000,38.289863000,21.795714000,4,0,'completed'),(3,1,'2024-02-15 09:30:00','00:12:00',38.255309000,21.746003000,38.289863000,21.795714000,4,0,'completed'),(4,1,'2024-06-10 13:00:00','00:13:54',38.267711000,21.752934000,38.289221000,21.794528000,2,0,'incomplete'),(5,1,'2024-06-12 13:00:00','00:06:54',38.245730236,21.731042862,38.256514756,21.743402481,2,0,'incomplete'),(6,1,'2024-06-13 13:00:00','00:12:54',38.245730236,21.731042862,38.256514756,21.743402481,2,0,'incomplete'),(7,1,'2024-02-17 10:00:00','00:16:00',38.035700000,22.055400000,37.999300000,22.107900000,3,0,'incomplete'),(8,1,'2024-03-10 15:30:00','00:11:00',38.246600000,21.734600000,37.983800000,23.727500000,4,0,'incomplete'),(9,1,'2024-08-25 18:45:00','00:07:30',37.591600000,23.022800000,37.676400000,22.870100000,2,0,'incomplete');
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
  latitude decimal(18,9) NOT NULL,
  longitude decimal(18,9) NOT NULL,
  `type` varchar(255) NOT NULL,
  balance decimal(10,2) NOT NULL DEFAULT '50.00',
  gps tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE KEY user_username_unique (username),
  UNIQUE KEY user_phone_unique (phone)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES user WRITE;
/*!40000 ALTER TABLE user DISABLE KEYS */;
INSERT INTO user VALUES (1,'user1','full name 1','pass1',6900000001,38.290000000,21.810000000,'driver',50.00,0),(2,'user2','full name 2','pass2',6900000002,38.270508000,21.757026000,'passenger',50.00,0),(3,'user3','full name 3','pass3',6900000003,38.273308000,21.759302000,'passenger',50.00,0),(4,'johndoe','John Doe','pass4',6912236588,40.712800000,-74.006000000,'passenger',100.00,0),(5,'janesmith','Jane Smith','pass123',9876543210,34.052200000,-118.243700000,'passenger',150.00,0),(6,'johnpap','Jake Papadopoulos','password123',1234567899,40.712800000,-74.006000000,'passenger',50.00,0);
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

-- Dump completed on 2024-06-01 10:01:01
