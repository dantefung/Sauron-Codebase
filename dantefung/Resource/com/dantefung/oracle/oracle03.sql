/*******************************14.PL/SQL**********************************/
--pl/sql:块结构语言，是sql（Structured Query Language）语言的一种扩展，结合了oracle过程语言（procedural language）进行使用。
--pl/sql块由三部分构成：声明部分、执行部分、异常部分。
/*
  		[DECLARE]
   			 --声明变量等；
		BEGIN
   	         --程序主要部分，一般用来执行过程语句或SQL语句；
		[EXCEPTION]
			--异常处理；
		END;
*/
/**##14.2 常量与变量##**/
--【声明语法】
--   变量：variable_name data_type[(size)][:=init_value];
--   常量：variable_name CONSTANT  data_type[(size)] :=init_value;

--   变量：          变量名     变量类型  [变量长度] [:=变量初值]
--   常量：          常量名     CONSTANT   常量类型[常量的长度] := 常量的初值

--【类型】
--  1.常用标准类型：
                      -- CHAR(CHARATER,NCHAR),VARCHAR2,NUMBER(P,S),DATE,BOOLEAN等
                      -- 1.1 字符类型
                      -- 1.2 数值类型
                      -- 1.3 日期类型

--  2.属性类型：%TYPE与%ROWTYPE
-- 单个    %TYPE:可以用来定义数据变量的类型与已定义的数据变量（表中的列）一致。
-- 整行    %ROWTYPE：与某一数据库表的结构一致(修改数据库表结构，可以实时保持一致）；访问方式声明为rowtype的变量名.字段名.

/**##14.2 常量与变量基本使用##**/
/*
 准备工作：创建学生表，插入测试数据
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
select * from student;
/*
定义常量或变量、赋值使用示例：定义常量s_class直接存放学生班级，
定义变量s_name,s_age分别用来存放学号为a001的学生（通过查询表）的姓名及年龄，
定义变量s_mark存放学生成绩，设定学生成绩为95.5,最后输出该生的班级、姓名、年龄及成绩。
*/
declare
     s_class constant varchar2(20) :='信息13-2班';
     s_name VARCHAR2(30);  -- 跟所要查询的表的字段的类型要一样
     s_age VARCHAR2(18);     -- 跟所要查询的表的字段的类型要一样
     s_mark number(3,1);
begin
     -- select into 方式赋值
     select stu_name, stu_age  into s_name, s_age  from student  where stu_no='a001';
     -- := 赋值语句  赋值
     s_mark :=95.5;
     -- 输出
	dbms_output.put_line('班级：'||s_class||'，学生姓名：'||s_name||'，年龄'||s_age||'，分数'||s_mark);
end;

-- 输出的结果为：
-- 班级：信息13-2班，学生姓名：张大，年龄19，分数95.5
/**##14.2 %TYPE类型的使用##**/
declare
     s_class constant varchar2(20) :='信息13-2班';
     s_name student.stu_name%type;
     s_age student.stu_age%type;
     s_mark number(3,1);
begin
     -- select into 方式赋值
     select stu_name, stu_age  into s_name, s_age  from student  where stu_no='a001';
     -- := 赋值语句  赋值
     s_mark :=95.5;
     -- 输出
	dbms_output.put_line('班级：'||s_class||'，学生姓名：'||s_name||'，年龄'||s_age||'，分数'||s_mark);
end;
/**##14.2 %ROWTYPE类型的使用##**/
declare
  s_class constant varchar2(20) :='信息13-2班';
  s_info student%rowtype;
  s_mark number(3,1);
begin 
    -- select into 的方式赋值
    select   stu_no,stu_name,stu_id,stu_age  into  s_info      -- 这种写法提高了数据库的查询效率，比select *  into s_info from stuent where stu_no='a001';
    from   student
    where stu_no = 'a001';
    -- :=  赋值符号  赋值
    s_mark :=95.5;
    -- 输出
    dbms_output.put_line('班级：'||s_class||'，学生姓名：'||s_info.stu_name||'，年龄'||s_info.stu_age||'，分数'||s_mark);
end;

-- 运行结果：
-- 班级：信息13-2班，学生姓名：张大，年龄19，分数95.5
/**##14.3 控制语句##**/
/**##14.3.1 条件控制##**/
/*
==============================
语法1：
if <boolean表达式> then
	执行语句；
end if;
==============================
语法2：
if <boolean表达式 >then
	执行语句；
else
	执行语句；
end if;
==============================
语法3：
if <boolean表达式> then
	执行语句；
elsif <boolean表达式> then
	执行语句；
elsif <boolean表达式> then
	执行语句；
else
	执行语句；
end if;
=========================
*/

/*
 准备工作：创建学生表，插入测试数据
*/
--drop table student;
CREATE TABLE student 
(    
     stu_no CHAR(4) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位整数
     stu_age NUMBER(3,0)  --年龄
);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a001','张大','441521199909092111',5);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a002','张二','441521199909092112',6);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a003','张三','441521199909092113',7);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a004','张四','441521199909092114',4);
commit;

--使用示例
-- 1。判断学号为a001的学生是否可以上小学（国家规定需要6周岁才可以上小学），
--      如果可以输出相关信息

-- 2。判断学号为a001的学生是否可以上小学（国家规定需要6周岁才可以上小学），
--      不论可不可以都输出相关信息

-- 3。判断学号为a001学生是否适全上小学（国家规定需要6周岁才可以上小学），
--      超过6岁属超龄，小于六周岁不能上小学，并根据学生情况输出相关结果。

-- 练习一：
declare
	s_age student.stu_no%type;
begin
			-- select    into 赋值
		    select  stu_age   into   s_age   
   			from   student
   			where  stu_no='a001';
   			-- 判断
            if  s_age >=6 then
             	dbms_output.put_line('可以上小学了！');
            end if;
end;

-- 练习二：
-- 2。判断学号为a001的学生是否可以上小学（国家规定需要6周岁才可以上小学），
--      不论可不可以都输出相关信息
declare
	s_age student.stu_no%type;
begin
			-- select    into 赋值
		    select  stu_age   into   s_age   
   			from   student
   			where  stu_no='a001';
   			-- 判断
            if  s_age >=6 then
             	dbms_output.put_line('可以上小学了！');
            else
                dbms_output.put_line('年龄太小！还不能上学！');
            end if;
end

-- 练习三：
-- 3。判断学号为a001学生是否适合上小学（国家规定需要6周岁才可以上小学），
--      超过6岁属超龄，小于六周岁不能上小学，并根据学生情况输出相关结果。
declare
			s_age student.stu_age%type;
begin
			 -- select into 方式赋值
			 select stu_age into s_age 
			 from student 
			 where stu_no='a001';
			 -- 判断
			 if s_age>6 then
			             dbms_output.put_line('此学生年龄为：'||s_age||'，超龄了，赶紧上小学了！');
			 elsif s_age=6 then
			             dbms_output.put_line('此学生年龄为：'||s_age||'，刚好可以上小学了！');
			 else
			             dbms_output.put_line('此学生年龄为：'||s_age||'，尚不能上小学！');
			 end if;  
end;
 
/**##14.3.2 循环控制##**/

/*loop循环
	语法：
			loop
			执行语句；
			exit when <条件语句>
			end loop;
*/
-- 示例：
-- 由于某次考试难度太大，需要给整个班级的学生加分，每次加5分，但最高分不能超过90分
/*
 准备工作：创建学生表，插入测试数据
*/
--drop table student;
CREATE TABLE student 
(    
     stu_no CHAR(4) PRIMARY KEY NOT NULL,   --学号，主键，非空
     stu_name VARCHAR2(30) NOT NULL,--姓名,非空      
     stu_id VARCHAR2(18), --身份证号，代表18位字符
     stu_mark NUMBER(3,1)  --分数
);
insert into student(stu_no,stu_name,stu_id,stu_mark) values('a001','张大','441521199909092111',50);
insert into student(stu_no,stu_name,stu_id,stu_mark) values('a002','张二','441521199909092112',60);
insert into student(stu_no,stu_name,stu_id,stu_mark) values('a003','张三','441521199909092113',30);
insert into student(stu_no,stu_name,stu_id,stu_mark) values('a004','张四','441521199909092114',40);
commit;
select * from student;

--由于某次考试难度太大，需要给整个班级的学生加分，每次加5分，但最高分不能超过90分
declare
	s_max_mark student.stu_mark%type;
begin
        -- 
	  loop
           select max(stu_mark) into s_mark from student;      
    exit when (s_mark+5)>90;         -- 相当于在循环体内加了个if(满足某个条件时){break;}
            update student set stu_mark=stu_mark+5;   
    end loop;
    commit;
end;

/*
while循环
	语法：
	while <布尔表达式> loop
		执行语句；
	end loop;
*/
declare
		s_mark number(3):=0;
begin
		  while s_mark+5<90 loop
		     update student set stu_mark=stu_mark+5;   
		     select max(stu_mark) into s_mark from student;      
		  end loop;
		  commit;
 end;
 --广东或香港等有些地方不喜欢4的数字，在使用数字时跳过4
DECLARE
flag number(3,0);
BEGIN     
   flag:=0;     
   while flag<10 loop
      flag:=flag+1;      
      if flag=4 then
		--去掉null会的报错
        null;
	  else
       dbms_output.put_line('flag ='||flag);

      end if;     
   end loop;
END;

/**##14.3.3 顺序控制##**/
-- null语句
--广东或香港等有些地方不喜欢4的数字，在使用数字时跳过4
DECLARE
flag number(3,0);
BEGIN     
   flag:=0;     
   while flag<10 loop
      flag:=flag+1;      
      if flag=4 then
		--去掉null会的报错
        null;
	  else
       dbms_output.put_line('flag ='||flag);

      end if;     
   end loop;
END;

-- goto 语句
--广东或香港等有些地方不喜欢4的数字，在使用数字时跳过4
DECLARE
flag number(3,0);
BEGIN     
   flag:=0;
   --<<>>号,使用goto可以跑到的标记，括号内标记名top不是固定关键字，也可以给其它名称
   <<top>>     
   while flag<10 loop
      flag:=flag+1;      
      if flag=4 then
        --这样可以相当于使用java的continue关键字，跳到上边定义的top开始执行
        goto top;
      end if;     
      dbms_output.put_line('flag ='||flag);
   end loop;
END;
/**##14.2 异常处理##**/
/**##14.2.1 异常处理语法##**/
/*
Exception
       when 异常名 then
        执行处理语句；
         --使用others确保不会漏过任何异常
       when others then
		执行处理语句；
*/

/*
经常配套使用的函数：
    SQLCODE函数：返回错误代码，
	 SQLERRM函数：返回错误信息

例如输出异常信息： 
DBMS_OUTPUT.PUT_LINE('其它异常，代码号：'||SQLCODE||'，异常描述:'||SQLERRM);
*/

/**##14.2.2 预定义异常##**/
/*
预定义异常指PL/SQL 程序违反 Oracle 规则或超越系统限制时隐式引发(由oracle自动引发)。
常见的预定义异常：

        ZERO_DIVIDE:以零作为除数时出现
		DUP_VAL_ON_INDEX：试图将重复的值存储在使用唯一索引的数据库列中
		INVALID_NUMBER：试图将一个非有效的字符串转换成数字 
		TOO_MANY_ROWS ：在执行SELECT INTO语句后返回多行时出现
		VALUE_ERROR:变量的值超出变量大小
		CURSOR_ALREADY_OPEN:试图打开已经打开的游标
		ACCESS_INTO_NULL：试图给一个没有初始化的对象赋值
		LOGIN_DENIED ：使用无效的用户名和口令登录Oracle
		NO_DATA_FOUND ：语句无法返回请求的数据

*/
--预定义异常
DECLARE    
     s_result NUMBER(3,1);    
BEGIN
    s_result:=10/0;
    --输出相关信息，DBMS_OUTPUT.PUT_LINE为具有输出功能的函数
       DBMS_OUTPUT.PUT_LINE
     ('没有异常！');
     Exception
       when ZERO_DIVIDE then
         DBMS_OUTPUT.PUT_LINE('除数不能为０，请核查！');
         --使用others确保不会漏过任何异常
       when others then
         --SQLCODE函数返回错误代码，SQLERRM函数返回错误信息
         DBMS_OUTPUT.PUT_LINE('其它异常，代码号：'||SQLCODE||'，异常描述:'||SQLERRM);
END;

/**##14.2.3 自定义异常##**/
/*
自定义异常：
程序在运行过程中，编程人员根据业务等情况，认为非正常情况，可以自定义异常。
对于这种异常，主要分三步来处理：
			第一、定义相关异常；在声明部分定义相关异常，格式：<自定义异常名称>　EXCEPTION;
			第二、抛出异常；在出现异常部分抛出异常，格式：RAISE　<异常名称>；
			第三、处理异常；在异常处理部分对异常进行处理，格式：when <自定义异常名称> then ...，　　　　　　
             
 存储过程的方式（会弹出一个错误的警告框）：            
		   处理异常也可以使用RAISE_APPLICATION_ERROR(ERROR_NUMBER,ERROR_MESSAGE)存储过程进行处理，
             其中参数ERROR_NUMBER取值为-20999~-20000的负整数，
             参数ERROR_MESSAGE为异常文本消息。


使用示例，完成以下功能：
			根据业务的需要，提取学生的信息时，
			要校验年龄不能为负数，如果为负数，
			请自定义异常age_exception,并将其抛出，处理。
			例如以如下的测试数据，取出身份证号：'441521199909092113'的学生，
			判断其年龄，并进行异常处理。

*/
/*
 创建学生表，插入测试数据
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
insert into student(stu_no,stu_name,stu_id,stu_age) values('a002','张二','441521199909092111',19);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a003','张三','441521199909092113',-1);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a004','张四','441521199909092114',18);
commit;

DECLARE
     s_class CONSTANT VARCHAR2(20):='高一（1）班';
     s_name student.stu_name%type;
     s_age student.stu_age%type;
     s_mark  NUMBER(3,1);
     age_exception EXCEPTION; --定义异常
BEGIN
     --赋值方式一：使用SELECT INTO给变量赋值
     SELECT stu_name, stu_age  INTO   s_name, s_age
      FROM   student
      WHERE stu_id='441521199909092111';
      --年龄小于0岁为异常情况，使用raise抛出异常
      if  s_age<0    then
        raise age_exception;  -- 抛出异常
      end if;
      
    --赋值方式二：使用赋值操作符“:=”给变量赋值
    s_mark:=95.5; 
    --输出相关信息，DBMS_OUTPUT.PUT_LINE为具有输出功能的函数
       DBMS_OUTPUT.PUT_LINE
     ('班级:'||s_class||',姓名:'||s_name||',年龄:'||s_age||',分数:'||s_mark);
EXCEPTION   -- 捕捉异常
  when TOO_MANY_ROWS then
       DBMS_OUTPUT.PUT_LINE('异常描述'||Sqlerrm||',原因：身份证号码出现重复');
  WHEN age_exception THEN
       RAISE_APPLICATION_ERROR(-20001,'年龄不能为负数！');
       --DBMS_OUTPUT.PUT_LINE('异常描述：年龄不能为负数！');
  WHEN OTHERS THEN  -- 捕捉最大的异常
       DBMS_OUTPUT.PUT_LINE('其它异常，代码号：'||SQLCODE||'，异常描述:'||SQLERRM);
END;

/********************************15.游标******************************************/
/**##15.1 游标的概念##**/
/*
类似于Java的ResultSet
WHAT?
			游标是指oracle在执行增删改查操作时，
              会把执行结果放在内存分配的缓冲区中，
              游标就是指向该区的一个指针，
              可以对结果集的每一行数据分别进行处理。

游标属性：
　　 %found　　　是否存在结果集或影响的行数，如果存在返回true
	　%notfound 　是否存在结果集或影响的行数，如果不存在返回true
　　 %rowcount　 返回受影响的行数
　　 %isopen　　游标是否已经打开。隐式游标中一般是自动打开和关闭的，查询都返回False
*/
/**##15.2 使用示例##**/
/*
15.2.1隐式游标
			由Oracle在内部声明，数据库自动创建，管理。
			主要用途是用于增删改数据时，
			可以返回一个操作成功或失败的相关信息，名称是(SQL)
*/
-- 示例：访问游标的属性
/*
 创建学生表，插入测试数据
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
insert into student(stu_no,stu_name,stu_id,stu_age) values('a002','张二','441521199909092111',19);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a003','张三','441521199909092113',-1);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a004','张四','441521199909092114',18);
commit;
--隐匿游标使用
declare  
begin  

	    insert into student(stu_no,stu_name,stu_id,stu_age) values('a005','张5','441521199909092115',18);
	    --update student set stu_age=17 where stu_id='441521199909092111' ;
	    --delete from  student  where  stu_id='441521199909092115' ;
  
	      if sql%found  then
	           dbms_output.put_line('已经影响了数据');
	      end if;     
	      if  sql%notfound  then
	           dbms_output.put_line('没有影响数据');
	      end if; 
	      if  sql%isopen  then --隐式游标自动打开和关闭的，此时查询已经关闭
	              dbms_output.put_line('游标已打开 ' );
	      end if; 
	      
      dbms_output.put_line('受影响行数 : ' || sql%rowcount); 

end; 

/*
显式游标：
				１.在declare 声明游标：Cursor 游标名 is 查询语句(可以多个查询字段);
				2.打开游标：open 游标名;
				３.提取游标: fetch 游标名 into 变量1，变量2（变量的个数对应字段数）;
				４.关闭游标：close 游标名;
*/ 

/*********************************16.存储过程与存储函数*************************************/
/**##16.1 存储过程类型##**/
-- １.不带参数
-- 2. 带输入参数
--３.带输出参数
--４. 带输入输出参数
/**##16.2 存储过程使用##**/
/**##16.2.1 创建与调用语法##**/
/*
CREATE [OR REPLACE] PROCEDURE procedure_name[(param_list)]
    IS|AS
[DECLARE]
BEGIN
   执行语句;
[EXCEPTION]
	异常处理;
END[procedure_name];
说明：
OR REPLACE：如果系统已存在该存储过程，将被替换
procedure_name：存储过程名称
param_list：参数列表，参数不需要声明长度，可选
DECLARE：局部声明，可选

调用语法
命令行调用：
exec[ute] procedure_name(paramlist);
pl/sql块调用：
begin
procedure_name(paramlist);
end;
*/
/**##16.2.2 准备测试表及数据##**/
/*
 创建学生表，插入测试数据
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
insert into student(stu_no,stu_name,stu_id,stu_age) values('a002','张二','441521199909092111',18);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a003','张三','441521199909092113',-4);
insert into student(stu_no,stu_name,stu_id,stu_age) values('a004','张四','441521199909092114',17);
commit;
select * from student;
/**##16.2.3 无参存储过程的调用##**/
-- 新建存储过程(不带参数)，可以新增学生信息
create or replace procedure pro_add_student
is
begin 
    insert into student(stu_no,stu_name,stu_id,stu_age) values('a005','张五','4444',19);
end;

-- 调用存储过程
begin
    pro_add_student;
end;
select * from student;
/**##16.2.4 有输入参数的存储过程的调用##**/
--新建存储过程(带输入参数,输入参数类型in可以省略不写），可以新增学生信息
create or replace procedure pro_add_student2
(
    -- 变量名 [in ] 类型  
	s_no  student.stu_no%type,
	s_name student.stu_name%type,
	s_id student.stu_id%type,
	s_age student.stu_age%type
)
is 
begin
    insert into student(stu_no,stu_name,stu_id,stu_age) values(s_no,s_name,s_id,s_age);
end;

-- 调用存储过程
begin
    -- 按顺序传参
	 pro_add_student2('a007','张7','44444',20);
	-- 关系符传参  （可以不按顺序）
	 pro_add_student2(s_id=>'44444',s_age=>20,s_no=>'a009',s_name=>'张9');
end;

select * from student;
/**##16.2.5 有输出参数存储过程的调用##**/
create or replace procedure pro_add_student3
(
    -- 变量 out 类型
	s_flag out number,
	s_message out varchar2
)
is
begin 
	insert into student(stu_no,stu_name,stu_id,stu_age) values('a0010','张师','4444',89);
	-- 赋值符号   :=
	s_flag := 1;
	s_message: = '添加成功！！！！';
	exception
	    -- 捕捉异常
	    when  DUP_VAL_ON_INDEX  then
	          s_flag: = 20001;
	          s_message: = '学号已经被使用，请核查！！！！';
	    -- 捕捉最大的异常
	    when others then
	    s_flag: = sqlcode;
	    s_massage: = SQLERRM;
end;

-- 调用存储过程
declare
 -- 变量名 类型
  p_flag number(10,0);
  p_message varchar2(50);
begin 
   pro_add_student(s_message=>p_message,s_flag => p_flag);
   dbms_output.put_line(p_flag||':'||p_message);
end;


/**##16.2.6 有输入输出的存储过程的调用##**/
create or replace procedure pro_add_student4(
	s_no student.stu_no%type,
	s_name student.stu_name%type,
	s_id student.stu_id%type,
	s_age student.stu_age%type,
	s_flag out number,
	s_msg out varchar2
)
is
begin

 insert into student(stu_no,stu_name,stu_id,stu_age,)values(s_no,s_name,s_id,s_age);
  commit;
 --   1-20000 oracle中自己有定义 
 s_flag:=20001;
  s_msg:='添加成功';
  exception 
     when others then
         s_flag:=sqlcode;
         s_msg:=sqlerrm;
end;


-- 调用存储过程
declare 
p_flag number(10,0);
p_message varchar2(50);
begin

 -- 按位置传递参数
 pro_add_student4('a009','张9','4444','29',p_flag,p_message);
  pbms_ouput.put_line('返回码：',||p_flag||',返回信息：'||p_msg);
end;

/**##16.2.7 基于JDBC的存储过程的调用##**/

/**##16.3 存储过程删除##**/
drop procedure pro_add_student3;

/**##16.4存储函数##**/
/**##16.4.1 语法##**/
/**##16.4.2使用示例##**/
/**##16.4.3 基于jdbc的调用##**/


/**##16.5 程序包对象##**/
/**##16.5.1 语法##**/
/**##16.5.2 使用示例一##**/
-- NO. 1:新建包及包体
-- NO. 2:基于JDBC调用 
/**##16.5.2 使用示例二##**/
-- NO.1:新建包及包体
-- NO.2:基于JDBC调用















































