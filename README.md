# ce3-server

## MySQL

DDL

```mysql
create database finance;
use finance;
create table card
(
    id        bigint auto_increment
        primary key,
    number    varchar(50)                        not null,
    create_at datetime default CURRENT_TIMESTAMP not null,
    update_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint id
        unique (id),
    constraint unq_idx_num
        unique (number)
);

# insert some sample data

insert into finance.card (id, number, create_at, update_at)
values  (100, '8349859489', '2022-12-31 17:29:52', '2022-12-31 17:29:52'),
        (101, '934875454', '2023-01-18 20:10:40', '2023-01-18 20:10:40'),
        (102, '47387493874', '2023-01-18 20:10:40', '2023-01-18 20:10:40'),
        (103, '7483', '2023-01-19 23:05:17', '2023-01-19 23:05:17'),
        (104, '7489', '2023-01-19 23:06:49', '2023-01-19 23:06:49');
```
