package com.krissoft.saa.bean;

public class PlayerString {
	//"name","stars","pos","location","height","weight","fortyDash","rating","gradYear"
	
	private String name;
	private String location;
	private String pos;
	private String stars;
	private String height;
	private String fortyDash;
	private String weight;
	private String gradYear;
	private String rating;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getFortyDash() {
		return fortyDash;
	}
	public void setFortyDash(String fortyDash) {
		this.fortyDash = fortyDash;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getGradYear() {
		return gradYear;
	}
	public void setGradYear(String gradYear) {
		this.gradYear = gradYear;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "PlayerString [name=" + name + ", location=" + location
				+ ", gradYear=" + gradYear + ", rating=" + rating + "]";
	}
	

}
