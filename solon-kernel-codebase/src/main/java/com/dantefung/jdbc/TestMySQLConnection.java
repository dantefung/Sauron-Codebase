package com.dantefung.jdbc;

import java.sql.*;
/**
 * JDBC Test!
 * 结合设计模式的思想来理解代码为何要这样的书写。
 * 站在设计者的角度来思考。
 * 以下的注释是我个人添加的，目的是为了理清脉络，理清书写代码的思路。
 * @author DanteFung
 * @since 2015.2.19
 * @version 0.1
 * */
public class TestMySQLConnection {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try
		{
			//加载数据库驱动。
			Class.forName("com.mysql.jdbc.Driver");
			//通过数据库驱动管理取得数据库连接。
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/dantefung?user=root&password=");
			//获得一个字段的对象
			stmt = conn.createStatement();
			//通过sql语句取得查询结果封装在一个结果集对象里面。
			rs = stmt.executeQuery("select * from dept");
			//沿用了迭代器的思想。
			while(rs.next())
			{
				System.out.println(rs.getString("deptno"));
			}
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException ex)
		{
			//handle any errors
			System.out.println("SQLException:" + ex.getMessage());
			System.out.println("SQLState:" + ex.getSQLState());
			System.out.println("VendorError:" + ex.getErrorCode());
		}
		finally
		{//最后当然是要关闭数据库的各项资源。
			try
			{
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
				if(stmt != null)
				{
					stmt.close();
					stmt = null;
				}
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
	}

}


