package com.qzp.model.MovieInform;

import java.util.List;

public class Result {

	private String cityname;
	private List<Movie> movie;
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public List<Movie> getMovie() {
		return movie;
	}
	public void setMovie(List<Movie> movie) {
		this.movie = movie;
	}
	
}
