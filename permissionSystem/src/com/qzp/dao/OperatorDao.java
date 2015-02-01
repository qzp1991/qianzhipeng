package com.qzp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qzp.model.Operator;
import com.qzp.util.C3P0ConnentionProvider;

public class OperatorDao {

	private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    public List<Operator> operatorList() throws SQLException{
    	List<Operator> operatorList = new ArrayList<Operator>();
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select F_OPERATORNO,F_OPERATORNAME from t_operator";
    	ps=conn.prepareStatement(sql);
    	rs=ps.executeQuery();
    	while(rs.next()){
    		Operator operator=new Operator();
    		operator.setOperatorNo(rs.getInt(1));
    		operator.setOperatorName(rs.getString(2));
    		operatorList.add(operator);
    	}
		return operatorList;
    	
    }
	
    public int operatorListCount() throws SQLException{
    	int total;
    	conn=C3P0ConnentionProvider.getConnection();
    	String sql="select count(*)  from t_operator";
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
    
    public int updateOperator(String operatorNo,String operatorName) throws Exception    
    {    
		int result;
		conn = C3P0ConnentionProvider.getConnection();
		String sql = "UPDATE t_operator SET F_OPERATORNAME=? WHERE F_OPERATORNO=? " ; 
        ps = conn.prepareStatement(sql);
        ps.setString(1, operatorName);
        ps.setInt(2, Integer.parseInt(operatorNo));    
        result=ps.executeUpdate();
        return result;     
    }    
    
    public static void main(String[] args) throws Exception {
    	OperatorDao operatorDao=new OperatorDao();
    	System.out.println(operatorDao.operatorListCount());
    	System.out.println(operatorDao.operatorList().get(1).getOperatorName());
    	System.out.println(operatorDao.updateOperator("0", "显示菜单"));
	}
}
