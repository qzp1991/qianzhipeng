package com.qzp.model;

/**
 * 角色表
 * @author Administrator
 *
 */
public class Role {

	private int roleNo;
	private String roleName;
	private int ROLEPARENTNO;
	public int getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(int roleNo) {
		this.roleNo = roleNo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getROLEPARENTNO() {
		return ROLEPARENTNO;
	}
	public void setROLEPARENTNO(int rOLEPARENTNO) {
		ROLEPARENTNO = rOLEPARENTNO;
	}
	
}
