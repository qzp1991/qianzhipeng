package com.qzp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qzp.model.Grant;
import com.qzp.model.Permission;
import com.qzp.util.C3P0ConnentionProvider;

public class GrantDao {

	private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    public List<Grant> grantList() throws SQLException{
    	List<Grant> grantList = new ArrayList<Grant>();
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select F_ROLENO,F_PERMISSIONNO from t_rolegrant";
    	ps=conn.prepareStatement(sql);
    	rs=ps.executeQuery();
    	while(rs.next()){
    		Grant grant=new Grant();
    		grant.setRoleNo(rs.getInt(1));
    		grant.setPermissionNo(rs.getInt(2));
    		grantList.add(grant);
    	}
		return grantList;
    	
    }
	
    public int grantListCount() throws SQLException{
    	int total;
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select count(*) as total from t_rolegrant";
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
	
    public int updateGrant(String roleNo,String permissionNo) throws Exception    
    {    
		
		conn = C3P0ConnentionProvider.getConnection();
		String sql = "UPDATE t_rolegrant set F_PERMISSIONNO=? where f_roleno=?" ; 
        ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(permissionNo));
        ps.setInt(2, Integer.parseInt(roleNo));        
        return ps.executeUpdate();   
    }    
    
    //根据角色号查询它所拥有的权限
    public List<Permission> queryPermissionByRoleNo(String roleNo) throws Exception{
    	List<Permission> permissionList=new ArrayList<Permission>();
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select f_permissionno,f_permissionname from t_permission where f_permissionno=(select F_PERMISSIONNO from t_rolegrant where F_ROLENO=?)";
    	ps=conn.prepareStatement(sql);
    	ps.setInt(1, Integer.parseInt(roleNo));
    	rs=ps.executeQuery(); 	
    	while(rs.next()){
    		Permission permission=new Permission();
    		permission.setPermissionNo(rs.getInt(1));
    		permission.setPermissionName(rs.getString(2));
    		permissionList.add(permission);
    	}
		return permissionList;
    	
    }
    public static void main(String[] args) throws Exception {
    	GrantDao grantDao= new GrantDao();
    	System.out.println(grantDao.grantListCount());
    	System.out.println(grantDao.grantList().get(0).getPermissionNo());
    	System.out.println(grantDao.updateGrant("1", "2"));
    	System.out.println(grantDao.queryPermissionByRoleNo("1").get(0).getPermissionName());
	}
}
