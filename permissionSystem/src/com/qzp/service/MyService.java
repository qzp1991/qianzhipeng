package com.qzp.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import org.dom4j.DocumentException;

@WebService 
public interface MyService {

	//@WebParam给参数命名，提高可代码可读性。此项可选    
	public  String sayHi(@WebParam(name="text") String text);  
	public String loginService(String xmlPara) throws DocumentException; 
    public String userService(String xmlPara) throws Exception;
    public String updatePassword(String xmlPara) throws Exception;
    public String queryOrgTree(String  xmlPara) throws Exception;
    public String setOrgInfo(String  xmlPara) throws Exception;
    public String queryUserInfo(String  xmlPara) throws Exception;
    public String setUserInfo(String  xmlPara) throws Exception;
    public String queryObject(String  xmlPara) throws Exception;
    public String queryOperator(String  xmlPara) throws Exception;
    public String queryPermission(String  xmlPara) throws Exception;
    public String setObjInfo(String  xmlPara) throws Exception;
    public String setOptInfo(String  xmlPara) throws Exception;
    public String setPermission(String xmlPara) throws Exception;
    public String queryRole(String  xmlPara) throws Exception;
    public String setRoleInfo(String  xmlPara) throws Exception;
    public String queryGrant(String  xmlPara) throws Exception;
    public String setRoleGrant(String  xmlPara) throws Exception;
    public String queryRoleByUserNo(String  xmlPara) throws Exception;
    public String setRoleToUser(String  xmlPara) throws Exception;
    
}
