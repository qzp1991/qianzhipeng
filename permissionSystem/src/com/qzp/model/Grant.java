package com.qzp.model;

/**
 * 角色授权类
 * @author Administrator
 *
 */
public class Grant {
	//角色号，权限号
	private int roleNo;
	private int permissionNo;
	public int getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(int roleNo) {
		this.roleNo = roleNo;
	}
	public int getPermissionNo() {
		return permissionNo;
	}
	public void setPermissionNo(int permissionNo) {
		this.permissionNo = permissionNo;
	}
	
}
