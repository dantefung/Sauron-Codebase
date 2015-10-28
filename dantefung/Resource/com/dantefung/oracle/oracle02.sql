/******************************7.运算符********************************/

--DROP TABLE student;
--执行步骤一：创建student表
CREATE TABLE student 
(    
     stu_no CHAR(4) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0)  --年龄
);
/**###7.2 比较（关系）运算符####**/
insert into student(stu_no,stu_name,stu_id,stu_age) values('a008','张8',null,18);-- stu_id值为null
insert into student(stu_no,stu_name,stu_id,stu_age) values('a009','张9','',18);-- stu_id值为null
insert into student(stu_no,stu_name,stu_id,stu_age) values('a010','张10','null',18);-- stu_id值不为null
insert into student(stu_no,stu_name,stu_id,stu_age) values('a011','张11','   ',18);-- stu_id值不为null

select * from student;
/**###7.3  逻辑运算符####**/

/**###7.4  连接运算符####**/
select stu_id||stu_name as result from student;

/**###7.5  集合运算符####**/
/*
\创建学生表
*/
--drop table student;
--drop table student_bak
CREATE TABLE student 
(    
     stu_no CHAR(4) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0)  --年龄
);
--插入数据
insert into student(stu_no,stu_name,stu_id,stu_age) values('a001','张大','441521199909092111',19);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a002','张二','441521199909092112',19);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a003','张三','441521199909092113',18);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a004','张四','441521199909092114',18);
--利用现有表创建新表，select 后边有多少字段，新表将有多少个字段
CREATE TABLE student_bak AS SELECT * FROM student;

--处理数据
update student set stu_id='331521199909092115' where stu_no='a003';
insert into student(stu_no,stu_name,stu_id,stu_age) values('a005','张五','441521199909092115',18);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a006','张六','441521199909092116',19);
delete from  student where stu_no ='a004';
commit;
select * from student;
select * from student_bak;

-- union（并集无重复）
-- union all（并集有重复）
-- intersect（交集，共有部分）
-- minus（减集，差集，第一个查询具有，第二个查询不具有的数据）

--union使用示例，客户需要查看所有完整的学生的信息
select stu_no,stu_name,stu_id,stu_age from student 
union 
select stu_no,stu_name,stu_id,stu_age from student_bak order by stu_no;

--扩展练习：具体找出哪些被改过的，包括删除、修改、新增的
-- A集合：修改、删除
select stu_no,stu_name,stu_id,stu_age from student_bak 
   minus select stu_no,stu_name,stu_id,stu_age from student; 
-- B集合：新增、修改
select stu_no,stu_name,stu_id,stu_age from student 
   minus select stu_no,stu_name,stu_id,stu_age from student_bak; 
-- C集合：删除 、新增



--使用order by时 selectr后不能用* 号，如：select * from student union select * from student_bak order by stu_no;

--union all，例如可用于，体店和网络虚拟店销售的所有商品情况（同样的表结构中）
--这里仍然以学生表为示例，查询所有学生的信息，可重复
select stu_no,stu_name,stu_id,stu_age from student
     union all select stu_no,stu_name,stu_id,stu_age from student_bak order by stu_no;
 
--intersect使用示例，找出信息没有变动的学生
select stu_no,stu_name,stu_id,stu_age from student 
    intersect  select stu_no,stu_name,stu_id,stu_age from student_bak order by stu_no;
    
--mimus使用示例，找出新增的学生信息
select * from student where stu_no in(
select stu_no from student minus  select stu_no from student_bak
);

/*********************************8.常用函数**************************************/

/**###8.1 聚合函数###**/
-- sum:求和
-- avg:求平均数
-- count：计数
-- max：求最大值
-- min：求最小值

/**###8.2 转换函数###**/
-- 一般用来格式化日期或者取值
-- to_char
SELECT TO_CHAR（sysdate,'YYYY-MM-DD HH24:MI:SS'） FROM dual;
SELECT to_char（1210.7, '$9,999.00'） FROM dual;
-- to_date
insert into testdate values('10',to_date('2015-10-18 12:12:12','YYYY-MM-DD HH24:MI:SS'));
select * from testdate;
-- to_number
SELECT to_number（'16'）+3 FROM dual; -- dual 伪表
SELECT '16'+3 FROM dual;

/**###8.3 分析函数###**/
/*
\创建学生表
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
insert into student(stu_no,stu_name,stu_id,stu_age) values('a005','张三','441521199909092113',18);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a006','张四','441521199909092114',18);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a007','张7','441521199909092114',18);
commit;

-- 8.3.2使用示例
-- 分析函数可以每个组返回多行。RANK,DENSE_RANK，ROW_NUMBER三个函数的使用示例： 
-- 查询各个年龄的情况,PARTITION BY类似group by,根据ORDER BY排序字段的值重新由一开始排序。
-- RANK使用相同排序排名一样，后继数据空出排名
SELECT t.*,
 RANK( ) OVER (PARTITION BY stu_age ORDER BY stu_ID DESC) as myRANK
 from student t;
-- DENSE_RANK使用，使用相同排序排名一样，后继数据不空出排名
SELECT t.*,
 DENSE_RANK( ) OVER (PARTITION BY stu_age ORDER BY stu_ID DESC) as myDENSERANK
 from student t;
--ROW_NUMBER使用，不管排名是否一样，都按顺序排名
SELECT t.*,
 ROW_NUMBER( ) OVER (PARTITION BY stu_age ORDER BY stu_ID DESC) as myDENSERANK
 from student t;

/**###8.4 其他函数###**/
-- 创建学生表,插入测试数据
-- drop table student;
CREATE TABLE student
(    
     stu_no CHAR(4) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_address varchar2(30)  
);
insert into student(stu_no,stu_name,stu_id,stu_address) values('a001','张大','441521199909092111','4401');
insert into student(stu_no,stu_name,stu_id,stu_address) values('a002','张二','441521199909092112','4403');
insert into student(stu_no,stu_name,stu_id,stu_address) values('a003','张三','441521199909092113','4401');
insert into student(stu_no,stu_name,stu_id) values('a004','张四','441521199909092114');
commit;
-- NVL(EXP1,EXP2): EXP1的值不为null，返回自己，否则返回EXP2; 常用来替换空值
select stu_no as 学号,nvl(stu_address,'4401') as 住址 from student;
-- NVL2(EXP1,EXP2,EXP3): EXP1的值不为null，将返回EXP2,否则返回EXP3;
select stu_no　学号,nvl2(stu_address,'4401','0000') 住址 from student;
-- DECODE(VALUE,IF1,THEN1,IF2,THEN2,...,ELSE):如果value等于if1,则返回then1,如果value等于if2,则返回then2,...否则返回else的值.
select stu_no 学号,decode(stu_address,4401,'广州','4403','深圳','其它') 住址  from student;

/******************************9.索引************************************/
/**##9.1 创建索引##**/
-- what?
-- 与表关联，可提供快速访问数据方式，也会影响增删改的效率。
-- 常用类型（按逻辑分类）：单列索引和组合索引、唯一索引和非唯一索引
-- 语法：
-- CREATE [UNIQUE] INDEX  index_name ON table_name(column_list);
-- 创建 唯一索引
create unique index index_stu_id on student(stu_id);
-- 创建 非唯一索引
create index index_stu_stu_id on student(stu_id);
--创建组合列、唯一索引
CREATE unique INDEX  index_stu_stu_name_stu_id ON student(stu_name,stu_age);
--创建组合列、非唯一索引
CREATE INDEX  index_stu_stu_name_stu_id ON student(stu_name,stu_age);

/**##9.2 删除索引##**/
-- 语法：DROP INDEX  index_name;
--删除索引
drop index index_stu_stu_name_stu_id;
-- 注意： 删除表时会把表的索引也删除

/***********************************10.序列***********************************/

-- 语法：
/*
CREATE SEQUENCE sequence_name
[START WITH integer]
[INCREMENT BY integer]
[MAXVALUE integer| NOMAXVALUE]
[MINVALUE integer| NOMINVALUE]
[CYCLE| NOCYCLE]
[CACHE integer|NOCACHE];
*/
/**##10.1创建序列##**/
create sequence seq_test
start with 1
increment by 1
maxvalue 2000
nocycle
cache 30


-- NEXTVAL:第一次访问时，返回序列的初始值，后继每次调用时，按步长增加的值返回。
-- CURRVAL:返回序列当前的值，创建新序列后，不能直接使用CURRVAL访问序列，使用过NEXTVAL访问序列后才能使用。
select seq_test.nextval from dual;
select seq_test.currval from dual;

-- 示例：

/*
\创建学生表
*/
--drop table student;
--drop table student_bak
CREATE TABLE student
(    
     stu_no number(4) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0)  --年龄
);
--创建序列
CREATE SEQUENCE seq_stu_no
START WITH 1
INCREMENT BY 1
MAXVALUE 2000
MINVALUE 1
NOCYCLE
CACHE 30;

/**##10.2 使用序列##**/

--使用序列插入数据
insert into student(stu_no,stu_name,stu_id,stu_age) values(seq_stu_no.nextval,'张大','441521199909092111',18);


--查询数据,如果发现表中的数据从2开始，可以修改deferred segment creation（创建延迟片断）的参数
--执行alter session set deferred_segment_creation=false; 把参数的值高为false,此参数为11gR2版本新参数
SELECT * FROM student;
SELECT seq_stu_no.CURRVAL FROM dual;

--修改序列
ALTER SEQUENCE seq_stu_no
MAXVALUE 5000
CYCLE;
-- 删除序列
drop sequence seq_stu_no;

/**##10.3 序列与SYS_GUID函数##**/
--使用SYS_GUID函数，32位，由时间戳和机器标识符生成，保证在不同数据库是唯一
SELECT sys_guid() FROM dual;

/********************************11.同义词*************************************/
/**##11.1 私有同义词##**/
-- system管理员授予创建同义词的角色（权限的集合）给user_37后
create synonym syn_student for student;
-- 使用私有同义词
select * from syn_student;
-- 没有使用私有同义词
select * from student;

-- 删除同义词
drop synonym syn_student;

/**##11.2 公有同义词##**/



























