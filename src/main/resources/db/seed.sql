create database foxbank;
use foxbank;

create table `users` (
    `id` int not null auto_increment,
    `username` varchar(45) not null,
    `password` varchar(45) not null,
    `enabled` int not null,
    primary key (`id`)
);

create table `authorities` (
    `id` int not null auto_increment,
    `username` varchar(45) not null,
    `authority` varchar(45) not null,
    primary key (`id`)
);

insert ignore into `users` values (null, 'test', '12345', 1);
insert ignore into `authorities` values (null, 'test', 'read');

select * from users;
select * from authorities;