package com.qzp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


import com.qzp.util.C3P0ConnentionProvider;

public class RoleToUserDao {

	private Connection conn = null;
    private PreparedStatement ps = null;
    
    //用户授权
	public int updateRoleToUser(String userName,String roleName) throws Exception{
		int result;
		conn=C3P0ConnentionProvider.getConnection();
		ps=conn.prepareStatement("update t_roletouser set F_ROLENO=(select F_ROLENO from t_role where f_rolename=?) where F_USERNO=(select F_USERNO from t_user where f_userid=?)");
		ps.setString(1, roleName);
		ps.setString(2, userName);
		result=ps.executeUpdate();
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		RoleToUserDao rtu=new RoleToUserDao();
		System.out.println(rtu.updateRoleToUser("qzp","部门管理员"));
	}
}
