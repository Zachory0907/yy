--create database
CREATE DATABASE yy DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

--create table
create table test(
    id int(5) not null primary key auto_increment,
    text char(100) default 'Hey Guys!',
    ext char(50)
);
--insert 
insert into test (id, text, ext) values (null, 'Hey Guys !', 'Hello World !');