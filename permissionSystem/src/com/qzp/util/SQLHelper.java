package com.qzp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SQLHelper {

	private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
   
 
    /**
     * 数据查询
     * @param sql语句
     * @return 返回结果集List<Object>
     */
    public List<Object> query(String sql) {
        if(sql.equals("") || sql == null){
            return null;
        }
        List<Object> list = new ArrayList<Object>();
        try {
            conn = C3P0ConnentionProvider.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            // 可以得到有多少列
            int columnNum = rsmd.getColumnCount();
            // 将数据封装到list中
            while (rs.next()) {
                Object[] objects = new Object[columnNum];
                for (int i = 0; i < objects.length; i++) {
                    objects[i] = rs.getObject(i + 1);
                }
                list.add(objects);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }
 
    /**
     * 插入、修改数据操作
     * @param sql语句
     * @return boolean 成功返回true，失败返回false
     */
    public boolean update(String sql) {
        boolean b = false;
        if(sql.equals("") || sql == null){
            return b;
        }
        try {
            conn = C3P0ConnentionProvider.getConnection();
            ps = conn.prepareStatement(sql);
            int i = ps.executeUpdate();
            if (i == 1) {
                b = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return b;
    }
    
    
 
}
