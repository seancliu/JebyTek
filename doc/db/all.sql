drop table if exists `chapter`;
create table `chapter` (
    `id` char(8) not null comment 'ID',
    `course_id` char(8) comment 'COURSE_ID',
    `name` varchar(50) comment 'NAME',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='CHAPTER';









insert into `chapter` (id, course_id, name) values ('00000000', '00000000', 'Test Chapter One');
insert into `chapter` (id, course_id, name) values ('00000001', '00000000', 'Test Chapter Two');



drop table if exists `test`;
CREATE TABLE `test`  (
   `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

insert into `test` (`id`, `name`) value (1, 'test');
insert into `test` (`id`, `name`) value (2, 'test');