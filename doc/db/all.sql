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

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
   `id` char(8) NOT NULL DEFAULT '' COMMENT 'ID',
   `name` varchar(50) NOT NULL COMMENT 'Name',
   `description` varchar(2000) NULL DEFAULT NULL COMMENT 'Description',
   `time` int(11) NULL DEFAULT 0 COMMENT 'Time (in secs)',
   `price` decimal(8, 2) NULL DEFAULT 0 COMMENT 'Price (USD)',
   `thumb` varchar(100) NULL DEFAULT NULL COMMENT 'Thumb',
   `level` char(1) NULL DEFAULT NULL COMMENT 'Level|ENUM[CourseLevelEnum]: ONE(\"1\", \"Easy\"), TWO(\"2\", \"Medium\"), THREE(\"3\", \"Hard\")',
   `charge` char(1) NULL DEFAULT NULL COMMENT 'Charge|ENUM[CourseChargeEnum]: CHARGE(\"C\", \"Charge\"), FREE(\"F\", \"Free\")',
   `status` char(1) NULL DEFAULT NULL COMMENT 'Status|ENUM[CourseStatusEnum]: PUBLISHED(\"P\", \"Published\"), DRAFT(\"D\", \"Draft\")',
   `enrolled` int(11) NULL DEFAULT 0 COMMENT 'Enrolled Count',
   `idx` int(11) NULL DEFAULT NULL COMMENT 'Index',
   `created_at` datetime(3) NULL DEFAULT NULL COMMENT 'Created At',
   `updated_at` datetime(3) NULL DEFAULT NULL COMMENT 'Updated At',
   PRIMARY KEY (`id`)
) ENGINE = InnoDB default charset=utf8mb4 COMMENT = 'COURSE';

alter table `course` add column (`instructor_id` char(8) comment 'Instructor ID');

DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
    `id` CHAR(8) NOT NULL DEFAULT '' COMMENT 'ID',
    `title` VARCHAR(50) NOT NULL COMMENT 'TITLE',
    `course_id` CHAR(8) COMMENT 'COURSE_ID',
    `chapter_id` CHAR(8) COMMENT 'CHAPTER_ID',
    `video` VARCHAR(200) COMMENT 'VIDEO',
    `length` INT COMMENT 'TIME IN SECS',
    `charge` CHAR(1) COMMENT 'C: CHARGE; F: FREE',
    `idx` INT COMMENT 'INDEX',
    `created_at` DATETIME(3) COMMENT 'CREATE TIME',
    `updated_at` DATETIME(3) COMMENT 'UPDATE TIME',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SECTION';

INSERT INTO `section` (id, title, course_id, chapter_id, video, length, charge, idx, created_at, updated_at)
VALUES ('00000001', 'TEST SECTION 1', '00000001', '00000000', '', 500, 'F', 1, now(), now());


drop table if exists `category`;
create table `category` (
    `id` char(8) not null default '' comment 'ID',
    `parent` char(8) not null default '' comment 'Parent ID',
    `name` varchar(50) not null comment 'Name',
    `idx` int comment 'Index',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='CATEGORY';

insert into `category` (id, parent, name, idx) values ('00000100', '00000000', 'Algorithms', 100);
insert into `category` (id, parent, name, idx) values ('00000101', '00000100', 'Sort', 101);
insert into `category` (id, parent, name, idx) values ('00000102', '00000100', 'Greedy', 102);
insert into `category` (id, parent, name, idx) values ('00000103', '00000100', 'Dynamic Programming', 103);
insert into `category` (id, parent, name, idx) values ('00000200', '00000000', 'Operating Systems', 200);
insert into `category` (id, parent, name, idx) values ('00000201', '00000200', 'Thread/Process', 201);
insert into `category` (id, parent, name, idx) values ('00000202', '00000200', 'Deadlock', 202);

# course-category relationship
drop table if exists `course_category`;
create table `course_category` (
    `id` char(8) not null default '' comment 'ID',
    `course_id` char(8) comment 'Course|course.id',
    `category_id` char(8) comment 'Category|course.id',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='CourseCategory';

# course content
drop table if exists `course_content`;
create table `course_content` (
    `id` char(8) not null default '' comment 'CourseID',
    `content` mediumtext not null comment 'Course Content',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='CourseContent';

-- course content files
drop table if exists `course_content_file`;
create table `course_content_file` (
    `id` char(8) not null default '' comment 'ID',
    `course_id` char(8) not null comment 'Course ID',
    `url` varchar(100) comment 'URL',
    `name` varchar(100) comment 'File Name',
    `size` int comment 'Size (bits)',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='CourseContentFile';

# instructor
drop table if exists `instructor`;
create table `instructor` (
    `id` char(8) not null default '' comment 'ID',
    `name` varchar(50) not null comment 'Name',
    `alias` varchar(50) comment 'Alias',
    `avatar` varchar(100) comment 'Avatar',
    `title` varchar(50) comment 'Title',
    `motto` varchar(50) comment 'Motto',
    `intro` varchar(500) comment 'Intro',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='Instructor';

-- file
drop table if exists `file`;
create table `file` (
    `id` char(8) not null default '' comment 'ID',
    `path` varchar(100) not null comment 'Relative Path',
    `name` varchar(100) comment 'File Name',
    `suffix` varchar(10) comment 'File Extension',
    `size` int comment 'Size|Bit',
    `use` char(1) comment 'Use|ENUM[FileUseEnum]: COURSE(\"C\", \"Course\"), INSTRUCTOR(\"I\", \"Instructor\")',
    `created_at` datetime(3) comment 'Create Time',
    `updated_at` datetime(3) comment 'Update Time',
    primary key (`id`),
    unique key `path_unique` (`path`)
) engine=innodb default charset=utf8mb4 comment='File';

alter table `file` add column (`shard_index` int comment 'Shard Index');
alter table `file` add column (`shard_size` int comment 'Shard Size|Bit');
alter table `file` add column (`shard_total` int comment 'Shard Total');
alter table `file` add column (`key` varchar(32) comment 'File Key');
alter table `file` add unique key_unique (`key`);

drop table if exists `test`;
CREATE TABLE `test`  (
   `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

insert into `test` (`id`, `name`) value (1, 'test');
insert into `test` (`id`, `name`) value (2, 'test');