INSERT INTO user(id,username,full_name,password,phone,latitude,longitude,type)
        VALUES (null,"user1","full name 1","pass1",6900000001,38.290000000,21.810000000,"driver");
INSERT INTO user(id,username,full_name,password,phone,latitude,longitude,type)
        VALUES (null,"user2","full name 2","pass2",6900000002,38.270508,21.757026,"passenger");
INSERT INTO user(id,username,full_name,password,phone,latitude,longitude,type)
        VALUES (null,"user3","full name 3","pass3",6900000003,38.273308000,21.759302000,"passenger");
INSERT INTO driver(user_id,car_model,car_id,car_color,max_passenger_capacity)
        VALUES (1,"model 1","AAA1234",'red',4);
INSERT INTO passenger(user_id,total_trips,reviews_rank)
        VALUES (2,11,4.20);
INSERT INTO passenger(user_id,total_trips,reviews_rank)
        VALUES (3,6,4.50);
        
        
-- INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip)
-- 		VALUES(null,1,"2024-01-04 14:30:00","00:23:00",38.255309,21.746003,38.289863,21.795714,4,0);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,status)
		VALUES(null,1,"2024-01-08 13:30:00","00:16:00",38.255309,21.746003,38.289863,21.795714,4,0,'completed');

-- INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip)
-- 		VALUES(null,1,"2024-01-16 13:00:00","00:30:00",38.255309,21.746003,38.289863,21.795714,4,0);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,status)
		VALUES(null,1,"2024-02-05 14:30:00","00:10:00",38.255309,21.746003,38.289863,21.795714,4,0,'completed');
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,status)
		VALUES(null,1,"2024-02-15 09:30:00","00:12:00",38.255309,21.746003,38.289863,21.795714,4,0,'completed');
-- INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip)
-- 		VALUES(null,1,"2024-02-21 13:00:00","00:08:00",38.255309,21.746003,38.289863,21.795714,4,0);   
-- INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip)
-- 		VALUES(null,1,"2024-03-14 13:00:00","00:15:54",38.255309,21.746003,38.289863,21.795714,1,0); 
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip)
		VALUES(null,1,"2024-06-10 13:00:00","00:13:54",38.267711,21.752934,38.289221,21.794528,2,0);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip)
		VALUES(null,1,"2024-06-12 13:00:00","00:06:54",38.245730236,21.731042862,38.256514756,21.743402481,2,0);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip)
		VALUES(null,1,"2024-06-13 13:00:00","00:12:54",38.245730236,21.731042862,38.256514756,21.743402481,2,0);
        
        
INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,1,1,2,"2024-01-08 13:30:00","00:10:00",38.270508,21.757026,38.296191,21.794768,1.21);
INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,1,1,3,"2024-01-08 13:30:00","00:12:00",38.273653,21.759664,38.295354,21.787916,1.58);

INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,2,1,2,"2024-02-05 14:30:00","00:07:00",38.270508,21.757026,38.296191,21.794768,1.04);
INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,2,1,3,"2024-02-05 14:30:00","00:09:00",38.273659,21.759664,38.295354,21.787916,1.34);        

INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,3,1,2,"2024-02-15 09:30:00","00:09:00",38.270508,21.757026,38.296191,21.794768,1.25);
INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,3,1,3,"2024-02-15 09:30:00","00:11:20",38.273659,21.759664,38.295354,21.787916,1.62); 
        
INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,4,1,2,"2024-06-14 13:00:00","00:08:00",38.270508,21.757026,38.286417,21.7864,2.1);
INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,4,1,3,"2024-01-14 09:30:00","00:09:00",38.277246,21.763197,38.287642,21.791767,0.85);
        
-- INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
-- 		VALUES(null,5,1,2,"2024-01-14 09:30:00","00:07:00",38.279237000,21.765754000,38.287705000,21.791896000,1.1);
-- INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)
-- 		VALUES(null,5,1,3,"2024-01-14 09:30:00","00:07:00",38.273308000,21.759302000,38.286151000,21.785920000,0.65);  
        
        
INSERT INTO post(id,driver_id,trip_id,post_datetime)
        VALUES (1,1,1,"2024-10-31 14:30:00");