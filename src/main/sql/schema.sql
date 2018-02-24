CREATE DATABASE seckill;

USE seckill;

CREATE TABLE seckill (
	seckill_Id BIGINT NOT NULL auto_increment,
	NAME VARCHAR (120) NOT NULL,
	number INT NOT NULL,
	start_time TIMESTAMP NOT NULL,
	end_time TIMESTAMP NOT NULL,
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (seckill_id),
	KEY idx_start_time (start_time),
	KEY idx_end_time (end_time),
	KEY idx_create_time (create_time)
) ENGINE = INNODB auto_increment = 1000 DEFAULT charset = utf8;

INSERT INTO seckill (NAME,number,start_time,end_time)
VALUES('1000 iphone6',100,'2017-01-01 00:00:00','2017-01-02 00:00:00');

INSERT INTO seckill (NAME,number,start_time,end_time)
VALUES('2000 iphone7',100,'2017-01-01 00:00:00','2017-01-02 00:00:00');

INSERT INTO seckill (NAME,number,start_time,end_time)
VALUES('3000 iphone8',100,'2017-01-01 00:00:00','2017-01-02 00:00:00');

INSERT INTO seckill (NAME,number,start_time,end_time)
VALUES('4000 iphoneX',100,'2017-01-01 00:00:00','2017-01-02 00:00:00');

CREATE TABLE success_killed (
	seckill_Id BIGINT NOT NULL,
	user_phone BIGINT NOT NULL,
	state TINYINT NOT NULL DEFAULT - 1,
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (seckill_id, user_phone),
	KEY idx_create_time (create_time)
) ENGINE = INNODB auto_increment = 1000 DEFAULT charset = utf8;