package com.dantefung.spi.mydriver;
/**
 *
 * 在老版本的JDBC中，假设我们使用的是MySql，初始化JDBC的时候是需要显式调用Class.forName("com.mysql.jdbc.Driver")这一句的，
 * 但是在某个版本之后就不需要做这一步操作了，如上所说这是通过SPI实现的，怎么理解呢。Class.forName其实没有实际意义，
 * 其实既不会new对象也不会反射生成对象，
 * 它只是为了调用com.mysql.jdbc.Driver的static方法块而已：
 *
    public class Driver extends NonRegisteringDriver implements java.sql.Driver {
             //
             // Register ourselves with the DriverManager
             //
             static {
                 try {
                     java.sql.DriverManager.registerDriver(new Driver());
                 } catch (SQLException E) {
                     throw new RuntimeException("Can't register driver!");
                 }
             }

            public Driver() throws SQLException {
            // Required for Class.forName().newInstance()
            }
        }

 方法块的作用只有一个，通过jdk自带的DriverManager注册Driver，registerDrivers方法没什么套路，把Driver放到CopyOnArrayList里面而已：

     public static synchronized void registerDriver(java.sql.Driver driver, DriverAction da)
     throws SQLException {

            //Register the driver if it has not already been added to our list
            if(driver != null) {
                    registeredDrivers.addIfAbsent(new DriverInfo(driver, da));
            } else {
                    // This is for compatibility with the original DriverManager
                    throw new NullPointerException();
            }

            println("registerDriver: " + driver);

            }

    =================================================================================================================
    +---------------------JDK1.8---------------------------+
 java.sql.DriverManager的静态代码块
    static {
        loadInitialDrivers();
        println("JDBC DriverManager initialized");
    }

 private static void loadInitialDrivers() {
     String drivers;
     try {
        drivers = AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return System.getProperty("jdbc.drivers");
            }
        });
     } catch (Exception ex) {
        drivers = null;
     }
     // If the driver is packaged as a Service Provider, load it.
     // Get all the drivers through the classloader
     // exposed as a java.sql.Driver.class service.
     // ServiceLoader.load() replaces the sun.misc.Providers()

     AccessController.doPrivileged(new PrivilegedAction<Void>() {
         public Void run() {

             ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
             Iterator<Driver> driversIterator = loadedDrivers.iterator();

            try{
                    while(driversIterator.hasNext()) {
                         driversIterator.next();
                     }
            } catch(Throwable t) {
                // Do nothing
            }
            return null;
        }
     });

     println("DriverManager.initialize: jdbc.drivers = " + drivers);

    ... ...

    +--------------------MYSQL8的驱动-----------------------+
 package com.mysql.jdbc;

 import java.sql.SQLException;

 public class Driver extends com.mysql.cj.jdbc.Driver {
     public Driver() throws SQLException {
     }

     static {
     System.err.println("Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.");
     }
 }

 +----------------------JDK5 DriverManager的描述---------------------------+
 参见:https://docs.oracle.com/javase/1.5.0/docs/api/java/sql/DriverManager.html
 java.sql
 Class DriverManager
 java.lang.Object
 extended by java.sql.DriverManager
 public class DriverManager
 extends Object
 The basic service for managing a set of JDBC drivers.
 NOTE: The DataSource interface, new in the JDBC 2.0 API, provides another way to connect to a data source. The use of a DataSource object is the preferred means of connecting to a data source.

 As part of its initialization, the DriverManager class will attempt to load the driver classes referenced in the "jdbc.drivers" system property. This allows a user to customize the JDBC Drivers used by their applications. For example in your ~/.hotjava/properties file you might specify:

 jdbc.drivers=foo.bah.Driver:wombat.sql.Driver:bad.taste.ourDriver

 A program can also explicitly load JDBC drivers at any time. For example, the my.sql.Driver is loaded with the following statement:
 Class.forName("my.sql.Driver");

 When the method getConnection is called, the DriverManager will attempt to locate a suitable driver from amongst those loaded at initialization and those loaded explicitly using the same classloader as the current applet or application.

 Starting with the Java 2 SDK, Standard Edition, version 1.3, a logging stream can be set only if the proper permission has been granted. Normally this will be done with the tool PolicyTool, which can be used to grant permission java.sql.SQLPermission "setLog".

 See Also:

 +-------------------JDK6 JDK7等版本 DriverManager的描述-----------------------+
 参见:http://www.matools.com/api/java6
 java.sql
 Class DriverManager
 java.lang.Object
 extended by java.sql.DriverManager
 public class DriverManager
 extends Object
 The basic service for managing a set of JDBC drivers.
 NOTE: The DataSource interface, new in the JDBC 2.0 API, provides another way to connect to a data source. The use of a DataSource object is the preferred means of connecting to a data source.

 As part of its initialization, the DriverManager class will attempt to load the driver classes referenced in the "jdbc.drivers" system property. This allows a user to customize the JDBC Drivers used by their applications. For example in your ~/.hotjava/properties file you might specify:

 jdbc.drivers=foo.bah.Driver:wombat.sql.Driver:bad.taste.ourDriver

 The DriverManager methods getConnection and getDrivers have been enhanced to support the Java Standard Edition Service Provider mechanism. JDBC 4.0 Drivers must include the file META-INF/services/java.sql.Driver. This file contains the name of the JDBC drivers implementation of java.sql.Driver. For example, to load the my.sql.Driver class, the META-INF/services/java.sql.Driver file would contain the entry:

 my.sql.Driver

 Applications no longer need to explictly load JDBC drivers using Class.forName(). Existing programs which currently load JDBC drivers using Class.forName() will continue to work without modification.

 When the method getConnection is called, the DriverManager will attempt to locate a suitable driver from amongst those loaded at initialization and those loaded explicitly using the same classloader as the current applet or application.

 Starting with the Java 2 SDK, Standard Edition, version 1.3, a logging stream can be set only if the proper permission has been granted. Normally this will be done with the tool PolicyTool, which can be used to grant permission java.sql.SQLPermission "setLog".

 */