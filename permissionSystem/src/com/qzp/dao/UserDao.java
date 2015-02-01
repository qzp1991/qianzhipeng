package com.qzp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.qzp.model.User;
import com.qzp.util.C3P0ConnentionProvider;


public class UserDao {

	private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    /**
     * 查询该用户名所在的部门
     * @param no 用户名
     * @return 部门名
     */
	public String query(String no) { 
       String departname=null;
       try {
            conn = C3P0ConnentionProvider.getConnection();
            ps = conn.prepareStatement("select f_departname from t_depart where f_departno= (select f_departno from t_user where f_userno=?)");
            ps.setString(1, no);
            rs = ps.executeQuery();
            if(rs.next()){
            departname=rs.getString(1);
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        }    
        return departname;
    }
	
	/**
	 * 查询用户名和密码是否正确
	 * trim() 因为password在数据库中定义时是char(128)的，而实际过程中传进来的密码没有128位，因此就要去掉数据库中存储密码后面的空格，这样比对才能成功
	 * @param name 用户名
	 * @param password 密码
	 * @return 是否正确
	 * @throws Exception
	 */
	public int userCount(String name,String password)throws Exception{
		int total=0;
		conn = C3P0ConnentionProvider.getConnection();
        ps = conn.prepareStatement("select count(*) as total from t_user where trim(f_userno) = ? and trim(f_userpwd) = ?");
        ps.setString(1, name);
        ps.setString(2, password);
        rs = ps.executeQuery();       
		if(rs.next()){
			total=rs.getInt(1);
			return total;
		}else{
			return total;
		}
	}
	
	/**
	 * 修改用户密码
	 * @param name 用户名
	 * @param newPassword 新的密码
	 * @return 修改是否成功
	 * @throws Exception
	 */
	public int update(String name,String newPassword) throws Exception    
    {    
		int result;
		conn = C3P0ConnentionProvider.getConnection();
		String sql = "UPDATE t_user SET F_USERPWD=? WHERE F_USERNO=? " ; 
        ps = conn.prepareStatement(sql);
        ps.setString(1, newPassword);
        ps.setString(2, name);
        result=ps.executeUpdate();
        return result;     
    }    
	
	/**
	 * 根据员工类型和所属部门查询员工信息
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> userList(String userType,String departNo) throws SQLException{
    	List userList = new ArrayList();
    	conn = C3P0ConnentionProvider.getConnection();
		String sql = "select F_USERNO,trim(F_USERID),trim(F_USERNAME),trim(F_USERPWD),F_DEPARTNO from t_user where trim(F_USERNAME)=? and trim(F_DEPARTNO)=?" ; 
        ps = conn.prepareStatement(sql);
        ps.setString(1, userType);
        ps.setString(2, departNo);
        rs = ps.executeQuery(); 
        while(rs.next()){
        	// 查询出内容，之后将查询出的内容赋值给person对象    
            User user=new User();
            user.setNo(rs.getInt(1));
            user.setId(rs.getString(2));
            user.setName(rs.getString(3));
            user.setPassword(rs.getString(4));
            user.setDepartNo(rs.getInt(5));
            //将查询出来的结果，放到departList之中
            userList.add(user); 
        }
    	return userList;
    }
	
	public int userListCount(String userType,String departNo)throws Exception{
		int total=0;
		conn = C3P0ConnentionProvider.getConnection();
        ps = conn.prepareStatement("select count(*) as total from t_user where F_USERNAME=? and F_DEPARTNO=?");
        ps.setString(1, userType);
        ps.setString(2, departNo);
        rs = ps.executeQuery();       
		if(rs.next()){
			total=rs.getInt(1);
			return total;
		}else{
			return total;
		}
	}
	
	public int updateUserInfo(String userNo,String userId,String userName,String userDepartNo) throws Exception    
    {    
		int result;
		conn = C3P0ConnentionProvider.getConnection();
		String sql = "UPDATE t_user SET F_USERID=?,F_USERNAME=?,F_DEPARTNO=? WHERE F_USERNO=? " ; 
        ps = conn.prepareStatement(sql);
        ps.setString(1, userId);
        ps.setString(2, userName);
        ps.setInt(3, Integer.parseInt(userDepartNo));
        ps.setInt(4, Integer.parseInt(userNo));
        result=ps.executeUpdate();
        return result;     
    }    
	
	//查询某角色的所有用户信息
	public List<User> queryRoleByUserNo(String roleNo) throws Exception{
		List<User> userRoleList=new ArrayList<User>();
		conn = C3P0ConnentionProvider.getConnection();
		String sql="select f_userid,f_userno,f_username from t_user where f_userno=(select f_userno from t_roletouser where f_roleno=?)";
		ps=conn.prepareStatement(sql);
		ps.setInt(1,Integer.parseInt(roleNo));
		rs=ps.executeQuery();
		while(rs.next()){
			User user2=new User();
			user2.setId(rs.getString(1));
			user2.setNo(rs.getInt(2));
			user2.setName(rs.getString(3));
			userRoleList.add(user2);
		}
		return userRoleList;
	}
	
	public int queryRoleByUserNoListCount(String roleNo)throws Exception{
		int total=0;
		conn = C3P0ConnentionProvider.getConnection();
        ps = conn.prepareStatement("select count(*) as total from t_user where f_userno=(select f_userno from t_roletouser where f_roleno=?)");
        ps.setInt(1,Integer.parseInt(roleNo));
        rs = ps.executeQuery();       
		if(rs.next()){
			total=rs.getInt(1);
			return total;
		}else{
			return total;
		}
	}
	
	public static void main(String[] args) throws Exception {
		UserDao userDao= new UserDao();
		System.out.println(userDao.query("1"));
		System.out.println(userDao.userCount("1", "111111"));
		System.out.println(userDao.update("1", "111111"));
		System.out.println(userDao.userListCount("系统管理员", "1"));
		System.out.println(userDao.userList("系统管理员", "1").get(0).getPassword());
		System.out.println(userDao.updateUserInfo("1", "admin", "系统管理员","1"));
		System.out.println(userDao.queryRoleByUserNo("1").get(0).getName());
	}
}
