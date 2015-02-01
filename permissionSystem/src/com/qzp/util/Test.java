package com.qzp.util;

import java.sql.Connection;
import java.sql.SQLException;


public class Test {
   
    public static void main(String[] args) throws SQLException {
		Connection conn = null;
		conn = C3P0ConnentionProvider.getConnection();
		if(conn==null){
			System.out.println("连接失败");
		}
		else{
			System.out.println("连接成功");
		}
	}
}
