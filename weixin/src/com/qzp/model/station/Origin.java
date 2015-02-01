package com.qzp.model.station;

import java.util.List;

public class Origin {

	private String cityName;
	private List<Results> results;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<Results> getResults() {
		return results;
	}
	public void setResults(List<Results> results) {
		this.results = results;
	}
	
}
