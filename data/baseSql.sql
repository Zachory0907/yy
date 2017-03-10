-- create database
CREATE DATABASE yy DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- 测试表
create table test(
    id int(5) not null primary key auto_increment,
    text char(100) default 'Hey Guys!',
    ext char(50)
);

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
);

-- 用户表插入admin
INSERT INTO YY_USER(ID, UNAME, MAIL, PWD, ISCHECK) VALUES (1, 'admin', 'vip_zgt@163.com', 'E10ADC3949BA59ABBE56E057F20F883E', 1);

-- 权限表
create table YY_AUTH(
    ID INT(8) not null primary key auto_increment,
    UNAME VARCHAR(50),
    AUTH VARCHAR(50),
    EXT VARCHAR(20)
);

-- 插入admin权限
INSERT INTO YY_AUTH(ID, UNAME, AUTH) VALUES (1, 'admin', 'FULL');