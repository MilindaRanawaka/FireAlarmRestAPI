#Database Creation
create database fireAlarmAPI;
use fireAlarmAPI;

#Table
create table sensors(
	sensorID int NOT NULL AUTO_INCREMENT,
	sensorStatus boolean,
	sensorLocationFloorNo varchar(20),
	sensorLocationRoomNo varchar(20),
	smokeLevel int,
	co2Level int,
    primary key (sensorID)
);

create table users(
	userID int NOT NULL AUTO_INCREMENT,
	username varchar(20),
	password varchar(20),
    email varchar(500),
	contactno varchar(20),
    primary key (userID)
);

#Data
insert into sensors(sensorStatus,sensorLocationFloorNo,sensorLocationRoomNo,smokeLevel,co2Level) values(true,"F5","N502",2,3);
insert into sensors(sensorStatus,sensorLocationFloorNo,sensorLocationRoomNo,smokeLevel,co2Level) values(false,"F3","N302",1,1);

insert into users(username,password,email,contactno) values('admin','admin','milindaranawaka@gmail.com','+94718956912');
#Verify
select * from sensors;
select * from users;