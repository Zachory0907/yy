-- create database
CREATE DATABASE yy DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

--use database
use yy

-- 测试表
create table test(
    id int(5) not null primary key auto_increment,
    text char(100) default 'Hey Guys!',
    ext char(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 测试表 insert
insert into test (id, text, ext) values (null, 'Hey Guys !', 'Hello World !');

-- 用户表
create table YY_USER(
    ID INT(8) not null primary key auto_increment,
    UNAME VARCHAR(50) NOT NULL,
    MAIL VARCHAR(50),
    TEL BIGINT,
    PWD VARCHAR(50),
    ISCHECK INT(1),
    EXT VARCHAR(20),
    EXT_1 VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户表插入admin，密码123456
INSERT INTO YY_USER(ID, UNAME, MAIL, PWD, ISCHECK) VALUES (1, 'admin', 'vip_zgt@163.com', 'E10ADC3949BA59ABBE56E057F20F883E', 1);

-- 权限表
create table YY_AUTH(
    ID INT(8) not null primary key auto_increment,
    UNAME VARCHAR(50),
    AUTH VARCHAR(50),
    EXT VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入admin权限
INSERT INTO YY_AUTH(ID, UNAME, AUTH) VALUES (1, 'admin', 'FULL');

-- GT3系统清册表——名称
create table YY_GT3_QC_NAME(
    ID INT(8) not null primary key auto_increment,
    OWNER VARCHAR(32),
    USER VARCHAR(32),    
    NAME_EN VARCHAR(128),
    NAME_ZH VARCHAR(128),    
    FIELDS VARCHAR(8),
    BZ VARCHAR(128),
    TYPE VARCHAR(16),
    EXT VARCHAR(32)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- GT3系统清册表——字段
create table YY_GT3_QC_FIELD(
    ID INT(8) not null primary key auto_increment
) ENGINE=InnoDB DEFAULT CHARSET=utf8;