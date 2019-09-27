
/*********************************5.伪列************************************/
-- rownum -- 分页
-- 注意：rownum大于１的其它整数时，条件不成立；等于2或2以上的正整数时条件不成立；
-- 所以查询某页的记录：例如第2到3条记录，就不能写成：
select rownum rn ,t.* from  testtime t where rownum<　3and rownum>1;
-- 可以写生如下语句：
select * from (select rownum rn ,t.* from testtime t where rownum<4) where rn>1;
-- sql执行顺序：
--1. select t.* from testtime t 同时，每查询出一条记录，
--2. 就临时生成rownum，rownum从1开始
--3. 且 where rownum<4
--4. 显示记录
--5. 执行 where rn>1

-- rownum -- 排序
--创建表
create table testtime(
ttid varchar2(2),
tttime timestamp
);
--修改时间，插入多条测试记录，
insert into testtime(ttid,tttime) values('1',to_Timestamp('2014-10-11 12:12:25.112233','YYYY-MM-DD HH24:MI:SS.FF'));
insert into testtime(ttid,tttime) values('2',to_Timestamp('2014-10-14 12:12:25.112233','YYYY-MM-DD HH24:MI:SS.FF'));
insert into testtime(ttid,tttime) values('3',to_Timestamp('2014-10-12 12:12:25.112233','YYYY-MM-DD HH24:MI:SS.FF'));
insert into testtime(ttid,tttime) values('4',to_Timestamp('2014-10-13 12:12:25.112233','YYYY-MM-DD HH24:MI:SS.FF'));
commit;

select * from testtime;
-- 按时间排序，rownum的顺序将是乱的
select rownum,t.* from testtime t order by t.tttime;
-- 原因：
-- sql的执行顺序（流程）：
-- 每查询出一条记录就给这条记录生成一个临时的rownum
select rownum,tt.* from ( select t.* from testtime t order by t.tttime) tt;



/******************************6.SQL语言简介**********************************/

/*####6.1数据定义语言#####*/

-- 创建表
create table test(
 id char(4) primary key not null,   -- 学号，主键，非空
 name varchar2(30) not null,        -- 姓名，非空
 idcard varchar2(18),               -- 身份证号，代表18位整数
 age number(3,0)                    -- 年龄，3位有效数字，其中小数部分有0位
);
-- 测试数据
insert into test values('1','张三','445281199206113518','19');
insert into test values('2','李四','445281199206113518','16');
insert into test values('3','王五','445281199206113518','30');
insert into test values('4','孙权','445281199206113518','70');
insert into test values('5','刘备','445281199206113518','18');

-- 根据现有的表的查询结果创建表(主键约束不会跟着创建)
create table test_2 as select t.id,t.name from test t;

select * from test_2;  

-- 修改表
-- 添加列
alter table test_2 add(address varchar2(30),phone varchar2(11));

-- 修改列的定义
alter table test_2 modify(id char(10),name varchar2(40));

-- 修改列名
alter table test_2 rename column address to address2;

-- 删除
-- 删除列
alter table test_2 drop(address2);

-- 删除表
-- 只删除表数据，每条删除的记录不写日志，省资源，效率比delete高
truncate table test_2;
-- 删除表结构及数据
drop table test_2;

-- 查看scott模式下的emp数据库对象（前提是system管理员授予了访问权限）
select * from scott.emp;


-- 去重：rowid 
-- 测试数据
create table testdate(
 tid varchar2(10),
 tname date
);
insert into testdate values('1',to_date('2015-10-18 12:12:12','YYYY-MM-DD HH24:MI:SS'));
insert into testdate values('1',to_date('2015-10-18 12:12:12','YYYY-MM-DD HH24:MI:SS'));
insert into testdate values('1',to_date('2015-10-18 12:12:12','YYYY-MM-DD HH24:MI:SS'));
insert into testdate values('2',to_date('2015-10-18 12:12:12','YYYY-MM-DD HH24:MI:SS'));
insert into testdate values('2',to_date('2015-10-18 12:12:12','YYYY-MM-DD HH24:MI:SS'));
insert into testdate values('2',to_date('2015-10-18 12:12:12','YYYY-MM-DD HH24:MI:SS'));
insert into testdate values('3',to_date('2015-10-18 12:12:12','YYYY-MM-DD HH24:MI:SS'));
-- 去重，删除重复的行
select rowid,t.* from testdate t;
-- 第一步
select min(rowid) from testdate t group by t.tid,t.tname;
-- 第二步
delete from testdate where rowid not in (select min(rowid) from testdate t group by t.tid,t.tname);

/*####6.2数据操纵语言#####*/
