package com.qzp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qzp.model.Permission;
import com.qzp.util.C3P0ConnentionProvider;

public class PermissionDao {

	private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    public List<Permission> permissionList() throws SQLException{
    	List<Permission> permissionList = new ArrayList<Permission>();
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select f_permissionno,F_PERMISSIONNAME,F_OBJECTNO,F_OPERATORNO from t_permission";
    	ps=conn.prepareStatement(sql);
    	rs=ps.executeQuery();
    	while(rs.next()){
    		Permission permission=new Permission();
    		permission.setPermissionNo(rs.getInt(1));
    		permission.setPermissionName(rs.getString(2));
    		permission.setObjectNo(rs.getInt(3));
    		permission.setOperatorNo(rs.getInt(4));
    		permissionList.add(permission);
    	}
		return permissionList;
    	
    }
	
    public int permissionListCount() throws SQLException{
    	int total;
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select count(*) as total from t_permission";
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
	
    public int updatePermission(String permissionNo,String permissionName,String objectNo,String operatorNo) throws Exception    
    {    
		int result;
		conn = C3P0ConnentionProvider.getConnection();
		String sql = "UPDATE t_permission SET F_PERMISSIONNAME=?,F_OBJECTNO=?,F_OPERATORNO=? WHERE f_permissionno=?"; 
        ps = conn.prepareStatement(sql);
        ps.setString(1, permissionName);
        ps.setInt(2, Integer.parseInt(objectNo));  
        ps.setInt(3, Integer.parseInt(operatorNo));
        ps.setInt(4,Integer.parseInt(permissionNo));
        result=ps.executeUpdate();
        return result;     
    }    
    
    public static void main(String[] args) throws Exception {
    	PermissionDao pd=new PermissionDao();
    	System.out.println(pd.permissionListCount());
    	System.out.println(pd.permissionList().get(1).getPermissionName());
    	System.out.println(pd.updatePermission("2","权限控制","1","1"));
	}
}
