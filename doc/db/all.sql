drop table if exists `chapter`;
create table `chapter` (
    `id` char(8) not null comment 'ID',
    `course_id` char(8) comment 'COURSE_ID',
    `name` varchar(50) comment 'NAME',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='CHAPTER';









insert into `chapter` (id, course_id, name) values ('00000000', '00000000', 'Test Chapter 00');
insert into `chapter` (id, course_id, name) values ('00000001', '00000000', 'Test Chapter 01');
insert into `chapter` (id, course_id, name) values ('00000002', '00000000', 'Test Chapter 02');
insert into `chapter` (id, course_id, name) values ('00000003', '00000000', 'Test Chapter 03');
insert into `chapter` (id, course_id, name) values ('00000004', '00000000', 'Test Chapter 04');
insert into `chapter` (id, course_id, name) values ('00000005', '00000000', 'Test Chapter 05');
insert into `chapter` (id, course_id, name) values ('00000006', '00000000', 'Test Chapter 06');
insert into `chapter` (id, course_id, name) values ('00000007', '00000000', 'Test Chapter 07');
insert into `chapter` (id, course_id, name) values ('00000008', '00000000', 'Test Chapter 08');
insert into `chapter` (id, course_id, name) values ('00000009', '00000000', 'Test Chapter 09');
insert into `chapter` (id, course_id, name) values ('00000010', '00000000', 'Test Chapter 10');
insert into `chapter` (id, course_id, name) values ('00000011', '00000000', 'Test Chapter 11');
insert into `chapter` (id, course_id, name) values ('00000012', '00000000', 'Test Chapter 12');
insert into `chapter` (id, course_id, name) values ('00000013', '00000000', 'Test Chapter 13');
insert into `chapter` (id, course_id, name) values ('00000014', '00000000', 'Test Chapter 14');




drop table if exists `test`;
CREATE TABLE `test`  (
   `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

insert into `test` (`id`, `name`) value (1, 'test');
insert into `test` (`id`, `name`) value (2, 'test');