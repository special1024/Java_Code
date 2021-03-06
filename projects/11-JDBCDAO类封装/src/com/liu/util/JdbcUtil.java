package com.liu.util;

import java.sql.*;

/*
    将JDBC规范下相关对象的【创建】与【销毁】功能封装到方法

    一、JDBC规范的开发步骤：
        1.注册数据库服务器提供的Driver接口的实现类
        2.创建一个连接通道交给Connection接口的洗哪里对象 【JDBC4Connection】管理
        3.创建一个交通工具交给PreparedStatement接口的实例对象【JDBC4PreparedStatement】管理
        4.由交通工具在Java工程与数据库服务器之间进行传输，推送SQL命令并带回执行结果
        5.在交易结束后，销毁相关资源【Connection，PreparedStatement，ResultSet】

 */
public class JdbcUtil {

    private Connection connection = null;//类文件中的属性,可以在类文件中所有的方法中使用
    private PreparedStatement ps = null;

    //静态语句块 static
    //在当前类文件第一次被加载到JVM是，JVM将会自动调用当前类文件静态语句块
    static {
        //1.注册数据库服务器提供的Driver接口实现类
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Driver接口实现类被注册了");
    }

    //封装Connection创建的细节
    public Connection createConnection() {

        //Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection对象创建失败");
        }
        return connection;
    }

    //封装PreparedStatement对象细节
    public PreparedStatement createStatement(String sql) {

        //PreparedStatement ps = null;
        Connection connection = this.createConnection();
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    //封装PreparedStatement对象与Connection对象销毁细节
    public void close() {

        if(connection != null) {

            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //封装PreparedStatement对象与Connection对象与ResultSet对象销毁细节
    public void close(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.close();
    }

}
