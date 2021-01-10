package com.liu.preparedstatement_crud;

import com.liu.bean.Customer;
import com.liu.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对Customer表的查询操作
 */
public class CustomerForQuery {

    @Test
    public void testQueryForCustomers() {
        String sql = "select id,name,birth,name from customers where id = ?";
        Customer customer = queryForCustomers(sql, 13);
        System.out.println(customer);

        String sql1 = "select name,email from customers where name = ?";
        Customer customer1 = queryForCustomers(sql1, "周杰伦");
        System.out.println(customer1);
    }

    /**
     * 针对customers表的通用的查询操作
     */
    public Customer queryForCustomers(String sql, Object ...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);
            for(int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            //获取结果集的元数据：ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if(rs.next()) {
                Customer customer = new Customer();

                //处理结果集一行数据中的每一列
                for(int i  = 0; i < columnCount; i++) {
                    //获取列值
                    Object ColumnValue = rs.getObject(i + 1);

                    //获取每个列的列名
                    //String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //给customers对象指定的columnName属性，赋值为columnName，通过反射
                    Field field = Customer.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(customer, ColumnValue);
                }
                return customer;
            }
        } catch (Exception e) {
                e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }


    @Test
    public void testQuery1()  {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            //执行，并返回结果集
            rs = ps.executeQuery();
            //处理结果集
            while(rs.next()) {//判断结果集的下一条是否有数据，如果有数据返回true，并指针下移，如果返回false，指针不会下移。

                //获取当前这条数据的各个字段值
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);

                //方式一：
                System.out.println("id = " + id + ",name = " + name + ",email" + email + ",birth = " + birth);

                //方式二：
                Object[] data = new Object[]{id, name, email, birth};

                //方式三：将数据封装为一个对象
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
    }
}
