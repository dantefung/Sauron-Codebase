-- 查询 存储过程 触发器
/*************************17.触发器************************************/
/**##示例练习一：备份删除的数据##**/
/*场景一：*/
/*删除学生信息时，同时把要删除学生信息备份到另一张表中，
以防止误操作时数据丢失或便于以后对数据的查找恢复*/

-- １.准备工作：创建学生表，插入测试数据
--drop table student;
--drop table stu_del_rec;
CREATE TABLE student 
(    
     stu_no number(4,0) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0)  --年龄
);
CREATE TABLE stu_del_rec 
(    
     stu_no number(4,0)  NOT NULL,   --学号，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0),  --年龄
     stu_operator_date date--操作日期
);
insert into student(stu_no,stu_name,stu_id,stu_age) values(1001,'张大','441521199909092111',19);
insert into student(stu_no,stu_name,stu_id,stu_age) values(1002,'张二','441521199909092111',18);
insert into student(stu_no,stu_name,stu_id,stu_age) values(1003,'张三','441521199909092113',-4);
insert into student(stu_no,stu_name,stu_id,stu_age) values(1004,'张四','441521199909092114',17);
commit;

-- 2.创建触发器：在删除数据前将数据备份到其他表中
create or replace trigger tri_del_bak
before 
        delete on student for each row 
declare

begin
     --:old.列名，可以引用在增删改前原表字段数据，没有数据为空；
     -- :new.列名，可以引用在增删改后字段新值，处理后对应记录不存在，则数据为空
	insert into stu_del_rec(stu_no,stu_name,stu_id,stu_age,stu_operator_date)values(:old.stu_no,:old.stu_name,:old.stu_id,:old.stu_age,sysdate);
end;

-- 3. 测试触发器
delete from student where stu_no=1002;
select * from student;
select * from stu_del_rec;

--  删除表会连同触发器也删除！！ 

/**##示例练习二：实现主键自动增长##**/
/*场景二：通过触发器+序列完成自动增长*/
-- １.准备工作：创建学生表，插入测试数据
--drop table student;
--drop table stu_del_rec;
CREATE TABLE student 
(    
     stu_no number(4,0) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0)  --年龄
);
-- 2.创建序列
create sequence seq_auto_pk;
-- 3.创建触发器
create or replace trigger tri_auto_pk
before
	insert on student for each row 
declare
	
begin 
	select  seq_auto_pk.nextval  into  :new.stu_no  from dual;
end;

-- 4.测试触发器
insert into student(stu_name,stu_id,stu_age) values('张7','4444',90);
insert into student(stu_name,stu_id,stu_age) values('张8','4444',19);
insert into student(stu_name,stu_id,stu_age) values('张9','4444',20);
select * from student;

-- 删除触发器
drop trigger tri_auto_pk;

/**##示例练习三：表级触发器，删除时整表备份##**/
-- 注意：表级触发器里不能再用:old或:new。
/*场景三：删除时，整表备份*/
/*
 创建学生表，插入测试数据
*/
--drop table student;
--drop table stu_del_rec;
CREATE TABLE student 
(    
     stu_no number(4,0) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0)  --年龄
);
CREATE TABLE stu_del_rec 
(    
     stu_no number(4,0)  NOT NULL,   --学号，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0),  --年龄
     stu_operator_date date default sysdate --操作日期
);
insert into student(stu_no,stu_name,stu_id,stu_age) values(1001,'张大','441521199909092111',19);
insert into student(stu_no,stu_name,stu_id,stu_age) values(1002,'张二','441521199909092111',18);
insert into student(stu_no,stu_name,stu_id,stu_age) values(1003,'张三','441521199909092113',-4);
insert into student(stu_no,stu_name,stu_id,stu_age) values(1004,'张四','441521199909092114',17);
commit;

select * from student;
-- 2.创建触发器
create or replace trigger tri_tb_bak
--  创建触发器，删除数据时可以把删除的数据备份到其它表中,要使用before,不能用after
before
	delete on student
declare
	
begin 
	insert into stu_del_rec(stu_no,stu_name,stu_id,stu_age) select stu_no,stu_name,stu_id,stu_age from student;
end;

--3. 测试触发器
delete from student;
select * from student;
select * from stu_del_rec;

-- 4.删除触发器
drop trigger tri_tb_bak;

/*********************************18.视图****************************************/
/**##18.1 视图简介##**/
-- WHAT?
  /* 也称虚表, 不占用物理空间，这个也是相对概念，因为视图本身的定义语句
       还是要存储在数据字典里的。视图只有逻辑定义。每次使用的时候只是重新执行SQL.
	  视图是从一个或多个实际表中获得的，这些表的数据存放在数据库中。
       那些用于产生视图的表叫做该视图的基表。一个视图也可以从另一个视图中产生。
      
     语法：
              创建视图：     create view 视图名 as 查询语句;
              删除视图：     drop view 视图名;
 
       表的列经常被进行查询，可以考虑建立视图。*/
 
 -- WHY?
/*视图：存sql    
	1.隔离原表，避免直接访问真实表名 （同义词），提高安全性  
	2.一般的用户更方便使用，将复杂的sql进行封装

区别：
           +-------------------------------------------+--------------------------------------+
           |    视图            |          存储过程                |                   存储函数                       |
           +---------------+---------------------------+--------------------------------------+
           |    存sql         |          存pl/sql程序块      |                    存pl/sql程序块            | 
           +---------------+---------------------------+--------------------------------------+
           |  将复杂的sql |            处理业务              |    通用代码，往往放到function里，|
           |  进行封装      |                                     |       类似java的封装的工具类           |
           +---------------+---------------------------+--------------------------------------+
                                 
 存储过程与存储函数：             
         存储过程与存储函数基本一样，两者能做的事情是一样的
          但有不成文的规定：1.存储过程一般用来处理业务 2.存储函数
         一般用来封装一些通用的代码 
视图：
       不仅仅有查询功能，也有增删改功能。因为视图是用来封装sql的。                             
*/
--drop table student;
--drop table my_class;
CREATE TABLE student
(    
     stu_no CHAR(4) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     mc_no CHAR(4)  --年龄
);
CREATE TABLE my_class
(    
     mc_no CHAR(4) PRIMARY KEY NOT NULL,   --编号，主键，非空
     mc_name VARCHAR2(30) NOT NULL--名称,非空      
);
insert into my_class(mc_no,mc_name) values('c001','传智一班');
insert into my_class(mc_no,mc_name) values('c002','传智二班');
insert into student(stu_no,stu_name,mc_no) values('a001','张一','c001');
insert into student(stu_no,stu_name,mc_no) values('a002','张二','c001');
insert into student(stu_no,stu_name,mc_no) values('a003','张三','c002');
commit;
--登陆system用户，给用户user_37授创建视图的权限
grant create view to user_37;

-- 创建视图(封装了sql)
-- /*##封装查询语句的视图##*/
create view  v_stu_r as select stu_no,stu_name,stu_id from student;

create or replace view view_student
as 
select stu_no,stu_name from student ;

-- 测试视图
select * from v_stu_r;

-- 通过视图查询
select * from view_student;

-- 通过视图修改数据
update view_student set stu_name='张思' where stu_no='a001';

-- 通过视图删除
delete from view_student where stu_no='a002';

-- 通过视图增加数据
insert into view_student values('a004','张久');

/*##基于多个基表的视图，不建议使用视图进行增删改操作##*/
create or replace view view_my_class_student
as 
select mc.mc_name,s.stu_no,s.stu_name 
from student s             -- 
inner join my_class mc  -- 
on s.mc_no=mc.mc_no;  -- 关联条件
 
 -- 测试基于多个基表的视图
select * from view_my_class_student;

/*##创建视图的视图##*/
create or replace view view_in_view 
as
select stu_no from view_student;

-- 测试视图的视图
select * from view_in_view;

--删除视图
drop view v_stu_r;
drop view view_in_view;
drop view view_my_class_student;
drop view view_student;

/***********************************19.数据闪回*****************************************/
/*##19.1简介##*/
/*				oracle可以在删除之后进行数据和表对象的闪回，
			任何闪回技术和恢复技术都是基于系统的某一个时间点的，
			因此oracle的恢复和闪回技术也是一样，

            SCN： System Change Number  和 系统的时间值一一对应,SCN可以作为恢复的时间点。
*/
-- 查看当前时刻的SCN号?
 select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'), timestamp_to_scn(sysdate) from dual;

-- 使用管理员sys,用dba角色查看undo表空间的参数

/*##19.2 闪回示例##*/
/*
创建学生表，及测试数据
*/
--drop table student;
CREATE TABLE student
(    
     stu_no CHAR(4) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0)  --年龄
);

insert into student(stu_no,stu_name,stu_id,stu_age) values('a001','张大','441521199909092111',19);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a002','张二','441521199909092112',19);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a003','张三','441521199909092113',18);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a004','张四','441521199909092114',18);
commit;
--查看并记录当前的ＳＣＮ号
select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'), timestamp_to_scn(sysdate) from dual;--S例如ＳCN号:1499531
--错误删除数据
delete student where stu_no='a003';
commit;
--查看表记录，stu_no为a003数据已被删除
select * from student;

--闪回刚才删除数据，需要先修改表的列可以移动： 
-- 登陆sys用户：alter table user_itcast.student enable row movement;
/*
    用户名：sys 
    密码：root 
    数据库：orcl 
               sysdba 
*/
flashback table student to scn 1499531;
--查看数据，发现已被恢复
 select * from student;
 
 /*********************************20.数据备份与恢复****************************************/
/**##20.1数据备份##**/
/*命令行：
　　1.完整备份
    exp user_itcast377/itcast@orcl  file=e:\oracle_bak.dmp full=y 
      2.指定表备份
    exp user_37/itcast@orcl  file=e:\oracle_student_bak.dmp tables=(student)
*/
/*##20.2恢复##*/
/*
导出后可以删除表，再恢复
指定表恢复
   1. imp user_37/itcast@orcl  ignore=y file=e:\oracle_student_bak.dmp tables=(student)
   2. imp  user_itcast377/itcast@orcl  ignore=y file=e:\oracle_bak.dmp  full=y
    导出后可以删除用户及表空间
	drop user user_itcast cascade;
	drop tablespace ts_itcast including contents and datafiles;
*/

-- 系统用户system  root
-- 2.创建一个自动增长的表空间ts_37,AUTOEXTEND ON不用时不会自动增长
CREATE TABLESPACE ts_37 
DATAFILE 'D:\software\oracle_data\data_37.DBF' 
SIZE 10M AUTOEXTEND ON;
/
-- 3.创建用户user_itcast,下面的user可以改为指定为上面建的表空间ts_itcast
CREATE USER  user_37
IDENTIFIED BY 123
DEFAULT TABLESPACE ts_37
TEMPORARY TABLESPACE TEMP;
/
--4.授予CONNECT和RESOURCE两个角色
GRANT connect, resource TO user_37;
grant create view to user_37;
grant create synonym to user_37;
grant create public synonym to user_37;
























































