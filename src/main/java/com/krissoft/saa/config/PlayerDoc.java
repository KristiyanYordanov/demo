package com.krissoft.saa.config;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.krissoft.saa.config.BaseMongoObject;

@Document(collection = "playerdoc")
public class PlayerDoc extends BaseMongoObject implements Serializable {

	/**
	 * kris
	 */
	private static final long serialVersionUID = 1L;

	public PlayerDoc(String name, String location, String pos, int stars,
			String height, Double fortyDash, int weight, int gradYear,
			Double rating) {
		super();
		this.name = name;
		this.location = location;
		this.pos = pos;
		this.stars = stars;
		this.height = height;
		this.fortyDash = fortyDash;
		this.weight = weight;
		this.gradYear = gradYear;
		this.rating = rating;
	}

	public PlayerDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayerDoc(String name, String location) {
		super();
		this.name = name;
		this.location = location;
		// TODO Auto-generated constructor stub
	}

	private String name;
	private String location;
	private String pos;
	private int stars;
	private String height;
	private Double fortyDash;
	private int weight;
	private int gradYear;
	private Double rating;
	private String state;
	private String schoolName;
	private String schoolCity;
	private String maxprepsUrl;
	
	
	public String getMaxprepsUrl() {
		return maxprepsUrl;
	}

	public void setMaxprepsUrl(String maxprepsUrl) {
		this.maxprepsUrl = maxprepsUrl;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolCity() {
		return schoolCity;
	}

	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}

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

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Double getFortyDash() {
		return fortyDash;
	}

	public void setFortyDash(Double fortyDash) {
		this.fortyDash = fortyDash;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getGradYear() {
		return gradYear;
	}

	public void setGradYear(int gradYear) {
		this.gradYear = gradYear;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "[\"" + (name != null ? name + "\",\"" : "\",\"")

		+ (location != null ? location + "\", \"" : "\",\"")
				+ (pos != null ? pos + "\",\" " : "\"")
				+ (height != null ? height + ", \"" : "\",\"")
				+ (fortyDash != null ? fortyDash + "\", \"" : "\",\"")
				+ (weight != 0 ? +weight + "\", \"" : "\",\"")
				+ (stars != 0 ? +stars + "\", \"" : "\",\"")
				+ (rating != null ? rating + "\"" : "\"\"") + "]";
	}
	
	public static String[] STATE = {
//		"AL",
//		"AK",
//		"AZ",
//		"AR",
//		"CA",
//		"CO",
//		"CT",
//		"DE",
//		"FL",
//		"GA",
//		"HI",
//		"ID",
//		"IL",
//		"IN",
//		"IA",
//		"KS",
//		"KY",
//		"LA",
//		"ME",
//		"MD",
//		"MA",
//		"MI",
//		"MN",
//		"MS",
//		"MO",
//		"MT",
//		"NE",
//		"NV",
//		"NH",
//		"NJ",
//		"NM",
//		"NY",
//		"NC",
//		"ND",
//		"OH",
//		"OK",
//		"OR",
//		"PA",
//		"RI",
//		"SC",
//		"SD",
//		"TN",
//		"TX",
//		"UT",
//		"VT",
//		"VA",
//		"WA",
//		"DC",
//		"WV",
//		"WI",
		"WY"
	};
	
	public static String[] POSITIONS = {
	"LF",
	"CF",
	"RF",
	"SS",
	"2B",
	"3B",
	"P",
	"1B",
	"C"
	};
	
}
