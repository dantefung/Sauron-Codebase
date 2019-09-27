-- 新建表空间
create tablespace t_user_space 
    datafile 
    'D:\software\oracle_data\oracle_data_file.dbf' size 1G
    autoextend on next 100M;   

-- 新建一个用户
create user user_37 identified by 123 
      default tablespace t_user_space
      temporary tablespace temp;
-- 修改用户密码
alter user user_37 identified by 456;
-- 删除用户
drop user user_37 cascade;

-- 查询user_37用户
select * from dba_users
      where username='user_37';
      
-- 给用户user_37用户权限，回收权限
grant connect, resource to user_37; -- 授予connect和resource两个角色（角色是权限的集合）

grant select on scott.emp to user_37;-- 允许用户查看scott模式下的emp表的数据


-- revoke connect,resource from user_37;-- 撤销connect和resource两个角色 

-- 删除表空间
-- drop tablespace t_user_space;
-- drop tablespace t_user_space including contents and datafiles; -- 删除表空间及数据文件

-- 删除用户
-- drop user user_37 cascade;
