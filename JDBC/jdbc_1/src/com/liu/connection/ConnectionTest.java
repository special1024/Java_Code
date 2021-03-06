package com.liu.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    //方式一：
    @Test
    public void testConnection1() throws SQLException {

        //获取Driver的实现类对象
        Driver driver = new com.mysql.jdbc.Driver();

        //jdbc:mysql:协议
        //localhost:ip地址
        //3306:默认的mysql端口号
        //test:test数据库名
        String url = "jdbc:mysql://localhost:3306/test";
        //将用户名和密码封装在Properties中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");

        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }

    //方式二：对方式一的迭代：在如下的程序中不出现第三方的api，是程序具有更好的可移植性
    @Test
    public void testConnection2() throws Exception {
        //1.获取Driver的实现类对象：使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2.提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/test";

        //3.提供连接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");

        //4.获取连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    //方式三：使用DriverManager替换Driver
    @Test
    public void testConnection3() throws Exception {

        //1.获取Driver的实现类对象
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2.提供另外三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";

        //注册驱动
        DriverManager.registerDriver(driver);

        //3.取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    //方法四：对方式三的优化可以只是加载驱动，不用显示注册驱动
    @Test
    public void testConnection4() throws Exception {
        //1.提供另外三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";

        //2.加载Driver
        Class.forName("com.mysql.jdbc.Driver");
        //为什么可以省略方式三的这部分呢？
        /*
        在mysql的Driver的实现类中，声明了如下操作：
            static {
                try {
                    java.sql.DriverManager.registerDriver(new Driver());
                } catch (SQLException E) {
                    throw new RuntimeException("Can't register driver!");
                }
            }
         */

        //3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    //方式五（最终版）：将数据库连接需要的4个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
    /*
        此方法的好处？
            1.实现了数据与代码的分离，实现了解耦
            2.如果需要修改配置文件信息，可以避免程序重新打包
     */
    @Test
    public void testConnection5() throws Exception {
        //1.读取配置文件中的的4个基本信息
        InputStream input = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(input);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //2.加载驱动
        Class.forName(driverClass);

        //3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);

    }
}
