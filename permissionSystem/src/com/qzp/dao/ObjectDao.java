package com.qzp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.qzp.model.Obj;
import com.qzp.util.C3P0ConnentionProvider;

public class ObjectDao {

	private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    /**
     * 查询对象列表
     * @throws SQLException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Obj> objectList() throws SQLException{
    	List objectList = new ArrayList();
    	conn = C3P0ConnentionProvider.getConnection();
		String sql = "select F_OBJECTNO,F_OBJECTNAME,F_PARENTNO,F_DESCRIPTION from t_object " ; 
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery() ; 
        while(rs.next()){
           // 查询出内容，之后将查询出的内容赋值给obj对象    
           Obj obj=new Obj();
           obj.setObjectNo(rs.getInt(1));
           obj.setObjectName(rs.getString(2));
           obj.setParentNo(rs.getInt(3));
           obj.setDescription(rs.getString(4));  
           //将查询出来的结果，放到objcetList之中
           objectList.add(obj);         
        }
    	return objectList;
    }
    
    public int objectListCount()throws Exception{
		int total=0;
		conn = C3P0ConnentionProvider.getConnection();
        ps = conn.prepareStatement("select count(*) as total from t_object");
        rs = ps.executeQuery();       
		if(rs.next()){
			total=rs.getInt(1);
			return total;
		}else{
			return total;
		}
	}
    
    public int updateObject(String objectNo,String objectName,String parentNo,String description) throws Exception    
    {    
		int result;
		conn = C3P0ConnentionProvider.getConnection();
		String sql = "UPDATE t_object SET F_OBJECTNAME=?,F_PARENTNO=?,F_DESCRIPTION=? WHERE F_OBJECTNO=? " ; 
        ps = conn.prepareStatement(sql);
        ps.setString(1, objectName);
        ps.setString(2, parentNo);
        ps.setString(3, description);
        ps.setInt(4,Integer.parseInt(objectNo));
        result=ps.executeUpdate();
        return result;     
    } 
    
    /**
     * 查询该用户下的菜单操作列表
     * @param userno
     * @return
     * @throws SQLException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Obj> objList(String userno) throws SQLException{
		List objList = new ArrayList();
    	conn = C3P0ConnentionProvider.getConnection();
		String sql = "select a.f_objectNo,a.f_objectName,a.f_description from t_object a,t_permission b,t_rolegrant c,t_roletouser d where d.f_userno=? and c.f_roleno=d.f_roleno and b.f_permissionno=c.f_permissionno and a.f_objectNo=b.f_objectNo" ; 
		ps = conn.prepareStatement(sql);
		ps.setInt(1,Integer.parseInt(userno));
        rs = ps.executeQuery() ; 
        while(rs.next()){
           // 查询出内容，之后将查询出的内容赋值给obj对象    
           Obj obj=new Obj();
           obj.setObjectNo(rs.getInt(1));
           obj.setObjectName(rs.getString(2));
           obj.setDescription(rs.getString(3));   
           //将查询出来的结果，放到objList之中
           objList.add(obj);         
        }
    	return objList;
    }
    
    public int objListCount(String userno) throws SQLException{
    	int total=0;
    	conn = C3P0ConnentionProvider.getConnection();
		String sql = "select count(*) as total from t_object a,t_permission b,t_rolegrant c,t_roletouser d where d.f_userno=? and c.f_roleno=d.f_roleno and b.f_permissionno=c.f_permissionno and a.f_objectNo=b.f_objectNo" ; 
		ps = conn.prepareStatement(sql);
		ps.setInt(1,Integer.parseInt(userno));
        rs = ps.executeQuery();       
		if(rs.next()){
			total=rs.getInt(1);
			return total;
		}else{
			return total;
		}
    }
   
    public static void main(String[] args) throws Exception {
    	ObjectDao objectDao=new ObjectDao();
    	System.out.println(objectDao.objectListCount());
    	System.out.println(objectDao.updateObject("6", "定义权限的","2","用于定义权限的"));
    	System.out.println(objectDao.objectList().get(0).getObjectName());
    	System.out.println(objectDao.objList("1").get(1).getDescription());
    	System.out.println(objectDao.objListCount("1"));
	}
}
