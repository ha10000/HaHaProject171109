create database ha_iot;
use ha_iot;
create table user(
id integer primary key auto_increment,
email text,
name text,
userId text,
password text,
mobileNo text,
age integer);

create table channel(
id integer primary key auto_increment,
channelName text, 
channelDesc text,
latitude text,
longitude text,
field1 text,
field2 text,
ReadApiKey text,
WriteAPiKey text,
isPublic integer,
saveInterval integer,
updated_at DATETIME,
created_at DATETIME default current_timestamp);

ALTER TABLE channel ADD channelId text;

insert into channel(channelName, channelDesc, 
latitude, longitude, field1, field2, ReadApiKey, 
WriteAPiKey, isPublic, saveInterval
)
values('채널명', '채널명세', 
'37.293881', '127.045641', '필드1', '필드2', '읽기키',
'쓰기키', 1, 30);

insert into channel(channelName, channelDesc, 
latitude, longitude, field1, field2, ReadApiKey, 
WriteAPiKey, isPublic, saveInterval , updated_at
)
values('채널명2', '채널명세2', 
'37.293881', '127.045641', '필드1-2', '필드2-2', '읽기키-2',
'쓰기키-2', 1, 30, current_timestamp);

insert into channel(channelName, channelDesc, 
latitude, longitude, field1, field2, ReadApiKey, 
WriteAPiKey, isPublic, saveInterval , updated_at
)
values('채널명3', '채널명세3', 
'37.293881', '127.045641', '필드1-3', '필드2-3', '읽기키-3',
'쓰기키-3', 1, 30, current_timestamp);
select * from channel;

create table device(
id integer primary key auto_increment,
user_id integer,
mac_address text,
created_at DATETIME default current_timestamp);

create table user(
id integer primary key auto_increment,
email text,
password text,
mobileNo text,
name text,
age integer);