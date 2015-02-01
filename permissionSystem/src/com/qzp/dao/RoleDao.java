package com.qzp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qzp.model.Role;
import com.qzp.util.C3P0ConnentionProvider;

public class RoleDao {

	private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    public List<Role> roleList() throws SQLException{
    	List<Role> roleList = new ArrayList<Role>();
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select F_ROLENO,F_ROLENAME,F_ROLEPARENTNO from t_role";
    	ps=conn.prepareStatement(sql);
    	rs=ps.executeQuery();
    	while(rs.next()){
    		Role role=new Role();
    		role.setRoleNo(rs.getInt(1));
    		role.setRoleName(rs.getString(2));
    		role.setROLEPARENTNO(rs.getInt(3));
    		roleList.add(role);
    	}
		return roleList;
    	
    }
	
    public int roleListCount() throws SQLException{
    	int total;
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select count(*) as total from t_role";
    	ps=conn.prepareStatement(sql);
    	rs=ps.executeQuery();
    	if(rs.next()){
    		total=rs.getInt(1);
    	}
    	else{
    		total=0;
    	}
		return total;
    	
    }
	
    public int updateRole(String roleNo,String roleName,String roleParentNo) throws Exception    
    {    
		int result;
		conn = C3P0ConnentionProvider.getConnection();
		String sql = "UPDATE t_role set F_ROLENAME=?,F_ROLEPARENTNO=? where F_ROLENO=? " ; 
        ps = conn.prepareStatement(sql);
        ps.setString(1, roleName);
        ps.setInt(2, Integer.parseInt(roleParentNo));  
        ps.setInt(3, Integer.parseInt(roleNo));
        result=ps.executeUpdate();
        return result;     
    }    
    
    public static void main(String[] args) throws Exception {
    	RoleDao pd=new RoleDao();
    	System.out.println(pd.roleListCount());
    	System.out.println(pd.roleList().get(1).getRoleNo());
    	System.out.println(pd.updateRole("1", "系统管理员", "0"));
	}
}
