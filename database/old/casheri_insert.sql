INSERT INTO user(id,username,full_name,password,phone,latitude,longitude)
        VALUES (1,"user1","full name 1",'pass1',6900000001,38.285946,21.809167);
INSERT INTO user(id,username,full_name,password,phone,latitude,longitude)
        VALUES (2,"user2","full name 2",'pass2',6900000002,38.237624,21.731185);
INSERT INTO driver(user_id,car_model,car_id,car_color,max_passenger_capacity)
        VALUES (1,"model 1","AAA1234",'red',4);
INSERT INTO passenger(user_id,total_trips,reviews_rank)
        VALUES (2,11,"not specified");
INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,cost) VALUES(1,1,"2024-12-31 14:30:00", "02:30:00",38.264684,21.806641,38.278856,21.809778,4,0,1.34);
INSERT INTO ride(id,trip_id,passenger_id,date_time,start_latitude,start_longitude,end_latitude,end_longitude,cost)
		VALUES(1,1,2,"2024-12-31 14:30:00",38.296733,21.805340,38.236510,21.769867,1.04);
INSERT INTO post(id,driver_id,trip_id,post_datetime)
        VALUES (1,1,1,"2024-10-31 14:30:00");