CREATE DATABASE IF NOT EXISTS health_code CHARSET = utf8mb4;
USE health_code;

CREATE TABLE IF NOT EXISTS college
(
    id   INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL COMMENT '学院名称'
) CHARSET = utf8mb4 COMMENT '学院';

create table profession
(
    id         INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    college_id INT         NOT NULL COMMENT '所属学院',
    name       VARCHAR(30) NOT NULL COMMENT '专业名称',
    CONSTRAINT FOREIGN KEY fk_college (college_id) REFERENCES college (id)
) CHARSET = utf8mb4 COMMENT '专业';

CREATE TABLE IF NOT EXISTS class
(
    id            INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(30) NOT NULL COMMENT '班级名称',
    profession_id INT         NOT NULL COMMENT '所属专业 id',
    CONSTRAINT FOREIGN KEY fk_profession (profession_id) REFERENCES profession (id)
) CHARSET = utf8mb4 COMMENT '班级';

CREATE TABLE IF NOT EXISTS admin
(
    id         INT                            NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(20)                    NOT NULL COMMENT '用户名',
    password   CHAR(60)                       NOT NULL COMMENT '密码',
    role       ENUM ('系统管理员','校级管理员','院级管理员') NOT NULL,
    college_id INT                            NULL COMMENT '院级管理员所在学院 id，其他管理员为null',
    CONSTRAINT FOREIGN KEY fk_college (college_id) REFERENCES college (id)
) CHARSET = utf8mb4 COMMENT '管理员';

CREATE TABLE IF NOT EXISTS student
(
    id       CHAR(12)    NOT NULL PRIMARY KEY COMMENT '学号',
    name     VARCHAR(10) NOT NULL COMMENT '姓名',
    password CHAR(60)    NOT NULL COMMENT '密码',
    class_id INT         NOT NULL COMMENT '所在班级 id',
    id_card  varchar(60) NOT NULL COMMENT '身份证号',
    CONSTRAINT FOREIGN KEY fk_class (class_id) REFERENCES class (id)
) CHARSET = utf8mb4 COMMENT '学生';

CREATE TABLE IF NOT EXISTS teacher
(
    id         CHAR(5)     NOT NULL PRIMARY KEY COMMENT '教师编号',
    name       VARCHAR(10) NOT NULL COMMENT '姓名',
    password   CHAR(60)    NOT NULL COMMENT '密码',
    college_id INT         NOT NULL COMMENT '所在学院 id',
    id_card    varchar(60) NOT NULL COMMENT '身份证号',
    CONSTRAINT FOREIGN KEY fk_college (college_id) REFERENCES college (id)
) CHARSET = utf8mb4 COMMENT '教师表';

CREATE TABLE IF NOT EXISTS student_daily_card
(
    id          INT                   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id  CHAR(12)              NOT NULL COMMENT '学生编号',
    result      ENUM ('绿码','黄码','红码') NOT NULL COMMENT '健康码种类',
    create_time TIMESTAMP             NOT NULL DEFAULT NOW() COMMENT '打卡时间',
    CONSTRAINT FOREIGN KEY fk_student (student_id) REFERENCES student (id)
) CHARSET = utf8mb4 COMMENT '学生每日打卡';

CREATE TABLE IF NOT EXISTS teacher_daily_card
(
    id          INT                   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    teacher_id  CHAR(6)               NOT NULL COMMENT '教师编号',
    result      ENUM ('绿码','黄码','红码') NOT NULL COMMENT '健康码种类',
    create_time TIMESTAMP             NOT NULL DEFAULT NOW() COMMENT '打卡时间',
    CONSTRAINT FOREIGN KEY fk_teacher (teacher_id) REFERENCES teacher (id)
) CHARSET = utf8mb4 COMMENT '教师每日打卡';