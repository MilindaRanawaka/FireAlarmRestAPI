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

#Data
insert into sensors(sensorStatus,sensorLocationFloorNo,sensorLocationRoomNo,smokeLevel,co2Level) values(true,"F5","N502",2,3);
insert into sensors(sensorStatus,sensorLocationFloorNo,sensorLocationRoomNo,smokeLevel,co2Level) values(false,"F3","N302",1,1);

#Verify
select * from sensors;