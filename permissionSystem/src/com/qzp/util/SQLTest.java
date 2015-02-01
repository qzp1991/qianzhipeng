package com.qzp.util;

import java.util.ArrayList;
import java.util.List;

import com.qzp.model.User;

public class SQLTest {

	private SQLHelper sqlHelper = new SQLHelper();
    
    /**
     * 测试query
     */
    @SuppressWarnings("rawtypes")
	public List<User> testQuery(){
        String sql = "select f_departname from t_depart where f_departno= (select f_departno from t_user where f_userno='100') ";
        //调用sqlHelper()返回一个结果集
        List list = sqlHelper.query(sql);
        List<User> userList = new ArrayList<User>();
        //对查询结果进行封装
        for (int i = 0; i < list.size(); i++) {
            Object object[] = (Object[]) list.get(i);
            User user=new User();
            user.setNo(Integer.parseInt(object[0].toString()));
            user.setId(object[1].toString());
            user.setName(object[2].toString());
            user.setPassword(object[3].toString());
            user.setDepartNo(Integer.parseInt(object[4].toString()));
            userList.add(user);
        }
        return userList;
    }
     
    /**
     * 测试insert、update、delete
     */
    public void testInsertOrUpdate(){
        String sql = "delete from user where userName ='qqqq'";
        boolean b = sqlHelper.update(sql);
        if(b){//b为true则操作成功
            System.out.println("操作成功");
        }else{//b为false则操作失败
            System.out.println("操作失败");
        }
    }
    
    public static void main(String[] args) {
		SQLTest test=new SQLTest();
		System.out.println(test.testQuery().get(0).getDepartNo());
		
	}
}
