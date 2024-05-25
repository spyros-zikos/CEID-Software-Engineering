drop database casheri;
create database casheri;
use casheri;
CREATE TABLE `User`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL,
    `full_name` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `phone` BIGINT NOT NULL,
    `latitude` DECIMAL(18,9) NOT NULL,
    `longitude` DECIMAL(18,9) NOT NULL,
	`type` VARCHAR(255) NOT NULL
);
ALTER TABLE
    `User` ADD UNIQUE `user_username_unique`(`username`);
ALTER TABLE
    `User` ADD UNIQUE `user_phone_unique`(`phone`);
CREATE TABLE `Driver`(
    `user_id` INT UNSIGNED NOT NULL,
    `car_model` VARCHAR(255) NOT NULL,
    `car_id` VARCHAR(255) NOT NULL,
    `car_color` VARCHAR(255) NOT NULL,
    `max_passenger_capacity` INT NOT NULL,
    PRIMARY KEY(`user_id`)
);
ALTER TABLE
    `Driver` ADD UNIQUE `driver_car_id_unique`(`car_id`);
CREATE TABLE `Trip`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `driver_id` INT UNSIGNED NOT NULL,
    `date_time` DATETIME NOT NULL,
    `duration` TIME NOT NULL,
    `start_latitude` DECIMAL(18,9) NOT NULL,
    `start_longitude` DECIMAL(18,9) NOT NULL,
    `end_latitude` DECIMAL(18,9) NOT NULL,
    `end_longitude` DECIMAL(18,9) NOT NULL,
    `passenger_capacity` INT(4) NOT NULL,
    `repeat_trip` TINYINT(1) NOT NULL,
    `status` ENUM('completed', 'incomplete', 'inprogress', 'canceled') NOT NULL default 'incomplete'
);
CREATE TABLE `Passenger`(
    `user_id` INT UNSIGNED NOT NULL,
    `total_trips` INT NOT NULL,
    `reviews_rank` DECIMAL(4,2) NOT NULL,
    PRIMARY KEY(`user_id`)
);
CREATE TABLE `Post`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `driver_id` INT UNSIGNED NOT NULL,
    `trip_id` INT UNSIGNED NOT NULL,
    `post_datetime` DATETIME NOT NULL
);
CREATE TABLE `Ride`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `trip_id` INT UNSIGNED NOT NULL,
	`driver_id` INT UNSIGNED NOT NULL,
    `passenger_id` INT UNSIGNED NOT NULL,
    `date_time` DATETIME NOT NULL,
    `duration` TIME NOT NULL,
    `start_latitude` DECIMAL(18,9) NOT NULL,
    `start_longitude` DECIMAL(18,9) NOT NULL,
    `end_latitude` DECIMAL(18,9) NOT NULL,
    `end_longitude` DECIMAL(18,9) NOT NULL,
    `cost` DOUBLE(8, 2) NOT NULL,
    `status` ENUM('inthefuture', 'waiting', 'inprogress', 'completed') NOT NULL default 'inthefuture'
);
ALTER TABLE
    `Post` ADD CONSTRAINT `post_driver_id_foreign` FOREIGN KEY(`driver_id`) REFERENCES `Driver`(`user_id`);
ALTER TABLE
    `Ride` ADD CONSTRAINT `ride_trip_id_foreign` FOREIGN KEY(`trip_id`) REFERENCES `Trip`(`id`);
ALTER TABLE
    `Post` ADD CONSTRAINT `post_trip_id_foreign` FOREIGN KEY(`trip_id`) REFERENCES `Trip`(`id`);
ALTER TABLE
    `Driver` ADD CONSTRAINT `driver_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`id`);
ALTER TABLE
    `Trip` ADD CONSTRAINT `trip_driver_id_foreign` FOREIGN KEY(`driver_id`) REFERENCES `Driver`(`user_id`);
ALTER TABLE
    `Ride` ADD CONSTRAINT `ride_passenger_id_foreign` FOREIGN KEY(`passenger_id`) REFERENCES `Passenger`(`user_id`);
ALTER TABLE
    `Passenger` ADD CONSTRAINT `passenger_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`id`);