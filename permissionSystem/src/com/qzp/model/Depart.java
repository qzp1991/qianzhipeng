package com.qzp.model;

/**
 * 部门类
 * @author Administrator
 *
 */
public class Depart {

	private int departNo;
	private String departId;
	private String departName;
	private int departParetNo;
	
	public Depart(){
		super();
	}
	
	public Depart(int departNo, String departId, String departName,
			int departParetNo) {
		super();
		this.departNo = departNo;
		this.departId = departId;
		this.departName = departName;
		this.departParetNo = departParetNo;
	}
	public int getDepartNo() {
		return departNo;
	}
	public void setDepartNo(int departNo) {
		this.departNo = departNo;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public int getDepartParetNo() {
		return departParetNo;
	}
	public void setDepartParetNo(int departParetNo) {
		this.departParetNo = departParetNo;
	}
	
}
