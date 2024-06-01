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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-01 11:15:01
