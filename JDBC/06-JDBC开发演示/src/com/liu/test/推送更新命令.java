package com.liu.test;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class 推送更新命令 {
    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/bjpowernode";
        String sql = "update dept set loc='广州' where deptno>=90";
        //1.将MySQL服务器提供的jar包中Driver接口实现类，注册到JVM中
        Class.forName("com.mysql.jdbc.Driver");

        //2.通过DriverManager在Java工程与MySQL服务器之间建立一个连接通道
        Connection con = DriverManager.getConnection(url, "root", "123456");

        //3.在通道上创建一个交通工具
        PreparedStatement ps = con.prepareStatement("");

        //4.通过交通工具将SQL命令推送到MySQL服务器上来执行并带回处理结果
        int result = ps.executeUpdate(sql);

        //5.销毁相关资源
        if(ps != null) {
            ps.close();
        }
        if(con != null) {
            con.close();
        }
        System.out.println("本次交易中，在Dept表文件更新" + result + "行数据");
    }
}
