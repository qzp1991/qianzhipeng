package com.qzp.model;

/**
 * 权限表
 * @author Administrator
 *
 */
public class Permission {

	//权限编号，权限名称，对象表(用户管理、部门管理、权限控制等)，操作表(显示、隐藏)
	private int permissionNo;
	private String permissionName;
	private int objectNo;
	private int operatorNo;
	public int getPermissionNo() {
		return permissionNo;
	}
	public void setPermissionNo(int permissionNo) {
		this.permissionNo = permissionNo;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public int getObjectNo() {
		return objectNo;
	}
	public void setObjectNo(int objectNo) {
		this.objectNo = objectNo;
	}
	public int getOperatorNo() {
		return operatorNo;
	}
	public void setOperatorNo(int operatorNo) {
		this.operatorNo = operatorNo;
	}
	
}
