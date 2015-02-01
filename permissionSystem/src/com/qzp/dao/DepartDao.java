package com.qzp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qzp.model.Depart;
import com.qzp.util.C3P0ConnentionProvider;

public class DepartDao {

	private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    /**
     * 查询部门列表
     * @throws SQLException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Depart> departList() throws SQLException{
    	List departList = new ArrayList();
    	conn = C3P0ConnentionProvider.getConnection();
		String sql = "select f_departno,f_departname from t_depart " ; 
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery() ; 
        while(rs.next()){
        	// 查询出内容，之后将查询出的内容赋值给person对象    
            Depart depart=new Depart();  
            depart.setDepartNo(rs.getInt(1));
            depart.setDepartName(rs.getString(2));
            
            //将查询出来的结果，放到departList之中
            departList.add(depart);
         
        }
    	return departList;
    }
    
    public int departCount() throws SQLException{
    	//用于记数的
    	int total=0;
    	conn = C3P0ConnentionProvider.getConnection();
		String sql = "select count(*) as total from t_depart " ;
        ps = conn.prepareStatement(sql);
        rs=ps.executeQuery();
        if(rs.next()){
			total=rs.getInt(1);
        }
		return total;	
    }
    
    /**
     * 设置机构信息
     * @param departNo
     * @param departId
     * @param departName
     * @param departParetNo
     * @return
     * @throws Exception
     */
    public int update(String departNo ,String departId,String departName,String departParetNo) throws Exception    
    {    
		int result;
		conn = C3P0ConnentionProvider.getConnection();
		String sql = "UPDATE t_depart SET F_DEPARTID=?,F_DEPARTNAME=?,f_departParetNo=? WHERE F_DEPARTNO=? " ; 
        ps = conn.prepareStatement(sql);
        ps.setString(1, departId);
        ps.setString(2, departName);
        ps.setInt(3, Integer.parseInt(departParetNo));
        ps.setInt(4, Integer.parseInt(departNo));
        //返回的是一个数字
        result=ps.executeUpdate();
        return result;       
    }    
    
    public static void main(String[] args) throws Exception {
    	DepartDao departDao=new DepartDao();
    	System.out.println(departDao.departCount());
		System.out.println(departDao.departList().get(1).getDepartName());
		System.out.println(departDao.update("2", "研究院", "国光111", "1"));
		
	}
}
