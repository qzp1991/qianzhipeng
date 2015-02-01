package com.qzp.model;

/**
 * 对象表
 * @author qzp
 *
 */
public class Obj {

	//对象编号、对象名称、父对象编号、排序号、描述
	private int objectNo;
	private String objectName;
	private int parentNo;
	private int sortNo;
	private String description;
	
	
	public int getObjectNo() {
		return objectNo;
	}
	public void setObjectNo(int objectNo) {
		this.objectNo = objectNo;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public int getParentNo() {
		return parentNo;
	}
	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
