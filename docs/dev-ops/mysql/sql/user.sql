create database if not exists `web_last` default character set utf8mb4;

use `web_last`;

drop table if exists `user`;

create table `user`
(
    `id`       int(8) unsigned not null auto_increment comment '自增id',
    `username` varchar(10)     not null comment '用户名',
    `password` char(64)        not null comment '用户密码',
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

insert into user (username, password)
values ('admin', 'admin');

