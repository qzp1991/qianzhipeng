package com.qzp.model.extend;

import java.util.List;



public class Data {

	private String title;
	private String imtro;
	private List<Steps> steps;
	public List<Steps> getSteps() {
		return steps;
	}
	public void setSteps(List<Steps> steps) {
		this.steps = steps;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImtro() {
		return imtro;
	}
	public void setImtro(String imtro) {
		this.imtro = imtro;
	}
	
}
