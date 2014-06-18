package com.krissoft.saa.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playerdoc")
public class PlayerDoc extends ObjectIdSerializer {

	/**
	 * kris
	 */

	public String[] header;

	public PlayerDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayerDoc(String[] header) {
		super();
		this.header = header;
		// TODO Auto-generated constructor stub
	}

	public PlayerDoc(String name, String location) {
		super();
		this.name = name;
		this.location = location;
		// TODO Auto-generated constructor stub
	}

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	private double statGP;
	private double statAvg;
	private double statOBP;
	private double statH;
	private double statRBI;
	private double statR;
	private double statSB;
	private double statAB;
	private double statSLG;
	private double statPA;
	private double statFP;
	private double statIP;
	private double statK;

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

	public String getMaxprepsUrl() {
		return maxprepsUrl;
	}

	public void setMaxprepsUrl(String maxprepsUrl) {
		this.maxprepsUrl = maxprepsUrl;
	}

	public double getStatGP() {
		return statGP;
	}

	public void setStatGP(double statGP) {
		this.statGP = statGP;
	}

	public double getStatAvg() {
		return statAvg;
	}

	public void setStatAvg(double statAvg) {
		this.statAvg = statAvg;
	}

	public double getStatOBP() {
		return statOBP;
	}

	public void setStatOBP(double statOBP) {
		this.statOBP = statOBP;
	}

	public double getStatH() {
		return statH;
	}

	public void setStatH(double statH) {
		this.statH = statH;
	}

	public double getStatRBI() {
		return statRBI;
	}

	public void setStatRBI(double statRBI) {
		this.statRBI = statRBI;
	}

	public double getStatR() {
		return statR;
	}

	public void setStatR(double statR) {
		this.statR = statR;
	}

	public double getStatSB() {
		return statSB;
	}

	public void setStatSB(double statSB) {
		this.statSB = statSB;
	}

	public double getStatAB() {
		return statAB;
	}

	public void setStatAB(double statAB) {
		this.statAB = statAB;
	}

	public double getStatSLG() {
		return statSLG;
	}

	public void setStatSLG(double statSLG) {
		this.statSLG = statSLG;
	}

	public double getStatPA() {
		return statPA;
	}

	public void setStatPA(double statPA) {
		this.statPA = statPA;
	}

	public double getStatFP() {
		return statFP;
	}

	public void setStatFP(double statFP) {
		this.statFP = statFP;
	}

	public double getStatIP() {
		return statIP;
	}

	public void setStatIP(double statIP) {
		this.statIP = statIP;
	}

	public double getStatK() {
		return statK;
	}

	public void setStatK(double statK) {
		this.statK = statK;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public String toString55() {
		String res = "[\"";
		for (int i = 0; i < header.length; i++) {
			if (header[i].equals("name")) {
				res = res + (name != null ? name + "\",\"" : "\",\"");
			} else if (header[i].equals("state")) {
				res = res + (state != null ? state + "\", \"" : "\",\"");
			} else if (header[i].equals("schoolName")) {
				res = res
						+ (schoolName != null ? schoolName + "\", \"" : "\",\"");
			} else if (header[i].equals("schoolCity")) {
				res = res
						+ (schoolCity != null ? schoolCity + "\", \"" : "\",\"");
			}
		}
		res = res.substring(0, res.length() - 2);
		res = res + "\"]";
		return res;
	}

	public String toString111() {
		String res = "{" + "\"DT_RowId\":\"" + id + "\"," + "\"name\":"
				+ (name != null ? "\"" + name + "\"," : "\",\"") + "\"state\":"
				+ (state != null ? "\"" + state + "\"," : "\",\"")
				+ "\"schoolName\":"
				+ (schoolName != null ? "\"" + schoolName + "\", " : "\",\"")
				+ "\"schoolCity\":"
				+ (schoolCity != null ? "\"" + schoolCity + "\"" : "\"") + "}";

		return res;
	}

	@Override
	public String toString() {
		return "{" + "\"DT_RowId\":\""
				+ id
				+ "\",\""
				+ "name\":"
				+ (name != null ? "\"" + name + "\",\"" : "\"\",\"")
				+ "state\":"
				+ (state != null ? "\"" + state + "\",\"" : "\"\",\"")
				+ "schoolName\":"
				+ (schoolName != null ? "\"" + schoolName + "\",\"" : "\"\",\"")
				+ "schoolCity\":"
				+ (schoolCity != null ? "\"" + schoolCity + "\",\"" : "\"\",\"")
				+ "maxprepsUrl\":"
				+ (maxprepsUrl != null ? "\"" + maxprepsUrl + "\",\""
						: "\"\",\"")
				+ "pos\":"
				+ (pos != null ? "\"" + pos + "\",\"" : "\"\",\"")
				+ "height\":"
				+ (height != null ? "\"" + height.replaceAll("\"", "")
						+ "\",\"" : "\"\",\"") + "fortyDash\":"
				+ (fortyDash != null ? "\"" + fortyDash + "\", \"" : "\"\",\"")
				+ "weight\":"
				+ (weight != 0 ? "\"" + weight + "\", \"" : "\"\",\"")
				+ "stars\":"
				+ (stars != 0 ? "\"" + stars + "\", \"" : "\"\",\"")
				+ "rating\":"
				+ (rating != null ? "\"" + rating + "\", \"" : "\"\",\"")
				+ "gradYear\":"
				+ (gradYear != 0 ? "\"" + gradYear + "\", \"" : "\"\",\"")
				+ "statGP\":"
				+ (statGP != 0 ? "\"" + +statGP + "\", \"" : "\"\",\"")
				+ "statAvg\":"
				+ (statAvg != 0 ? "\"" + +statAvg + "\", \"" : "\"\",\"")
				+ "statOBP\":"
				+ (statOBP != 0 ? "\"" + +statOBP + "\", \"" : "\"\",\"")
				+ "statH\":" + (statH != 0 ? "\"" + +statH + "\", \"" : "\"\",\"")
				+ "statRBI\":"
				+ (statRBI != 0 ? "\"" + +statRBI + "\", \"" : "\"\",\"")
				+ "statR\":" + (statR != 0 ? "\"" + +statR + "\", \"" : "\"\",\"")
				+ "statSB\":"
				+ (statSB != 0 ? "\"" + +statSB + "\", \"" : "\"\",\"")
				+ "statAB\":"
				+ (statAB != 0 ? "\"" + +statAB + "\", \"" : "\"\",\"")
				+ "statSLG\":"
				+ (statSLG != 0 ? "\"" + +statSLG + "\", \"" : "\"\",\"")
				+ "statPA\":"
				+ (statPA != 0 ? "\"" + +statPA + "\", \"" : "\"\",\"")
				+ "statFP\":"
				+ (statFP != 0 ? "\"" + +statFP + "\", \"" : "\"\",\"")
				+ "statK\":" + (statK != 0 ? "\"" + +statK + "\", \"" : "\"\",\"")
				+ "statIP\":" + (statIP != 0 ? "\"" + statIP + "\"" : "\"\"") + "}";
	}

	public static String[] STATE = {
	// "AL",
	// "AK",
	// "AZ",
	// "AR",
	// "CA",
	// "CO",
	// "CT",
	// "DE",
	// "FL",
	// "GA",
	// "HI",
	// "ID",
	// "IL",
	// "IN",
	// "IA",
	// "KS",
	// "KY",
	// "LA",
	// "ME",
	// "MD",
	// "MA",
	// "MI",
	// "MN",
	// "MS",
	// "MO",
	// "MT",
	// "NE",
	// "NV",
	// "NH",
	// "NJ",
	// "NM",
	// "NY",
	// "NC",
	// "ND",
	// "OH",
	// "OK",
	// "OR",
	// "PA",
	// "RI",
	// "SC",
	// "SD",
	// "TN",
	// "TX",
	// "UT",
	// "VT",
	// "VA",
	// "WA",
	// "DC",
	// "WV",
	// "WI",
	"WY" };

	public static String[] POSITIONS = { "LF", "CF", "RF", "SS", "2B", "3B",
			"P", "1B", "C", "OF" };

	public static String[] STATS = { "GP", "Avg", "OBP", "H", "RBI", "R", "SB",
			"AB", "SLG", "PA", "FP", "K", "IP" };

}
