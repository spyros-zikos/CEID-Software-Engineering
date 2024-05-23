INSERT INTO user(id,username,full_name,password,phone,latitude,longitude,type)
        VALUES (1,"user1","full name 1","pass1",6900000001,38.290000000,21.810000000,"driver");
INSERT INTO user(id,username,full_name,password,phone,latitude,longitude,type)
        VALUES (2,"user2","full name 2","pass2",6900000002,38.240000000,21.730000000,"passenger");
INSERT INTO user(id,username,full_name,password,phone,latitude,longitude,type)
        VALUES (3,"user3","full name 3","pass3",6900000003,38.273308000,21.759302000,"passenger");
INSERT INTO driver(user_id,car_model,car_id,car_color,max_passenger_capacity)
        VALUES (1,"model 1","AAA1234",'red',4);
INSERT INTO passenger(user_id,total_trips,reviews_rank)
        VALUES (2,11,4.20);
INSERT INTO passenger(user_id,total_trips,reviews_rank)
        VALUES (3,6,4.50);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,cost)
		VALUES(1,1,"2024-12-31 14:30:00","02:30:00",38.264684,21.806641,38.278856,21.809778,4,0,1.34);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,cost)
		VALUES(2,1,"2024-06-14 13:30:00","02:30:00",38.267435000,21.753505000,38.290093000,21.796008000,4,0,1.45);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,cost)
		VALUES(3,1,"2024-06-14 13:00:00","02:30:00",38.267395000,21.753517000,38.289861000,21.795713000,4,0,0.87);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,cost)
		VALUES(null,1,"2024-01-28 14:30:00","00:10:00",38.264684,21.806641,38.278856,21.809778,4,0,1.34);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,cost)
		VALUES(null,1,"2024-01-14 09:30:00","00:12:00",38.267435000,21.753505000,38.290093000,21.796008000,4,0,1.45);
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,cost)
		VALUES(null,1,"2024-02-11 13:00:00","00:08:00",38.267395000,21.753517000,38.289861000,21.795713000,4,0,0.87);                
INSERT INTO ride(id,trip_id,passenger_id,date_time,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(1,3,2,"2024-06-14 13:00:00",38.279237000,21.765754000,38.287705000,21.791896000,1.04);
INSERT INTO ride(id,trip_id,passenger_id,date_time,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(2,3,3,"2024-06-14 13:00:00",38.273308000,21.759302000,38.286151000,21.785920000,0.34);        
INSERT INTO ride(id,trip_id,passenger_id,date_time,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,6,2,"2024-06-14 13:00:00",38.279237000,21.765754000,38.287705000,21.791896000,2.1);
INSERT INTO ride(id,trip_id,passenger_id,date_time,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,7,3,"2024-06-14 13:00:00",38.273308000,21.759302000,38.286151000,21.785920000,0.85);
INSERT INTO ride(id,trip_id,passenger_id,date_time,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,8,2,"2024-06-14 13:00:00",38.279237000,21.765754000,38.287705000,21.791896000,1.1);
INSERT INTO ride(id,trip_id,passenger_id,date_time,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(null,6,3,"2024-06-14 13:00:00",38.273308000,21.759302000,38.286151000,21.785920000,0.65);        
INSERT INTO post(id,driver_id,trip_id,post_datetime)
        VALUES (1,1,1,"2024-10-31 14:30:00");