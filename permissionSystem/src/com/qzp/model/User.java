package com.qzp.model;

/**
 * 用户表
 * @author Administrator
 *
 */
public class User {

	private int no;
	private String id;
	private String name;
	private String password;
	private int departNo;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDepartNo() {
		return departNo;
	}
	public void setDepartNo(int departNo) {
		this.departNo = departNo;
	}
	
}
