create database foxbank;
use foxbank;

create table `customers` (
    `id` int not null auto_increment,
    `email` varchar(45) not null,
    `pwd` varchar(200) not null,
    `role` varchar(45) not null,
    primary key (`id`)
);

insert into `customers` (`email`, `pwd`, `role`) values ('john@example.com', '54321', 'admin');

select * from users;
select * from authorities;