package com.dantefung.spi.mydriver;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description: TODO
 * @Author: DANTE FUNG
 * @Date:2019/9/29 11:31
 */
public class OldLoadDriverTest {

    @Test
    public void testMySQL() throws ClassNotFoundException, SQLException {
        // 为了调用com.dantefung.spi.mydriver.MyDriver的静态代码块
        // JDK5的写法
        //Class.forName("com.dantefung.spi.mydriver.MyDriver");
        //System.setProperty("jdbc.drivers","com.dantefung.spi.mydriver.MyDriver");
        String url = "jdbc:mysql:///consult?serverTimezone=UTC";
        String user = "root";
        String password = "root";

        Connection connection = DriverManager.getConnection(url, user, password);
    }
}
