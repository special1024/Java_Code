package com.liu.dao;

import com.liu.entity.Dept;
import com.liu.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    部门表的DAO类 增删改查
 */
public class DeptDao {

    private JdbcUtil util = new JdbcUtil();

    //添加数据行
    public int add(String deptNo, String dname, String loc) {
        String sql = "insert into dept1(deptno,dname,loc) value(?, ?, ?)";
        //JdbcUtil util  = new JdbcUtil();
        int result = 0;
        PreparedStatement ps = util.createStatement(sql);
        try {
            ps.setInt(1, Integer.parseInt(deptNo));
            ps.setString(2, dname);
            ps.setString(3, loc);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    //删除数据行
    public int delete(String deptNo) {
        String sql = "delete from dept1 where deptno=?";
        //JdbcUtil util = new JdbcUtil();
        PreparedStatement ps = util.createStatement(sql);
        int result = 0;
        try {
            ps.setInt(1, Integer.parseInt(deptNo));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    //更新数据行
    public int update(String deptNo, String dname, String loc) {
        String sql = "update dept1 set dname=?,loc=? where deptno=?";
        //JdbcUtil util = new JdbcUtil();
         PreparedStatement ps = util.createStatement(sql);
         int result = 0;
        try {
            ps.setString(1, dname);
            ps.setString(2, loc);
            ps.setInt(3, Integer.parseInt(deptNo));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    //查询数据行
    public List findAll() {
        String sql = "select * from dept1";

        PreparedStatement ps = util.createStatement(sql);
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            rs = ps.executeQuery();
            //将当前临时表数据行转换为实体类的实例对象来保管
            while(rs.next()) {
                int deptNo = rs.getInt("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                Dept dept = new Dept(deptNo, dname, loc);
                list.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            util.close(rs);
        }
        return  list;
    }

}
