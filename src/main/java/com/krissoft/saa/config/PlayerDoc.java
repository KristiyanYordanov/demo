package com.krissoft.saa.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playerdoc")
public class PlayerDoc  extends ObjectIdSerializer{

	/**
	 * kris
	 */
	
	public  String[] header;

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
	private double GP;
	private double Avg;
	private double OBP;
	private double H;
	private double RBI;
	private double R;
	private double SB;
	private double AB;
	private double SLG;
	private double PA;
	private double FP;
	private double K;
	private double IP;

	public double getGP() {
		return GP;
	}

	public void setGP(double gP) {
		GP = gP;
	}

	public double getAvg() {
		return Avg;
	}

	public void setAvg(double avg) {
		Avg = avg;
	}

	public double getOBP() {
		return OBP;
	}

	public void setOBP(double oBP) {
		OBP = oBP;
	}

	public double getH() {
		return H;
	}

	public void setH(double h) {
		H = h;
	}

	public double getRBI() {
		return RBI;
	}

	public void setRBI(double rBI) {
		RBI = rBI;
	}

	public double getR() {
		return R;
	}

	public void setR(double r) {
		R = r;
	}

	public double getSB() {
		return SB;
	}

	public void setSB(double sB) {
		SB = sB;
	}

	public double getAB() {
		return AB;
	}

	public void setAB(double aB) {
		AB = aB;
	}

	public double getSLG() {
		return SLG;
	}

	public void setSLG(double sLG) {
		SLG = sLG;
	}

	public double getPA() {
		return PA;
	}

	public void setPA(double pA) {
		PA = pA;
	}

	public double getFP() {
		return FP;
	}

	public void setFP(double fP) {
		FP = fP;
	}

	public double getK() {
		return K;
	}

	public void setK(double k) {
		K = k;
	}

	public double getIP() {
		return IP;
	}

	public void setIP(double iP) {
		IP = iP;
	}
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
	
	
	public String toString55() {
		String res = "[\"";
		for (int i = 0; i < header.length; i++) {
			if (header[i].equals("name")) {
				res = res + (name != null ? name + "\",\"" : "\",\""); 
			}
			else if (header[i].equals("state")){
				res = res + (state != null ? state + "\", \"" : "\",\""); 
			}
			else if (header[i].equals("schoolName")){
				res = res + (schoolName != null ? schoolName + "\", \"" : "\",\""); 
			}
			else if (header[i].equals("schoolCity")){
				res = res + (schoolCity != null ? schoolCity + "\", \"" : "\",\""); 
			}
		}
		res = res.substring(0, res.length()-2);
		res  = res + "\"]";
		return res;
	}
	public String toString111() {
		String res =
				"{" 
				+"\"DT_RowId\":\""+ id + "\","
				+"\"name\":"+ (name != null ?  "\"" + name + "\"," : "\",\"")
				+"\"state\":"+ (state != null ? "\"" +  state + "\"," : "\",\"")
				+"\"schoolName\":"+ (schoolName != null ? "\"" +  schoolName + "\", " : "\",\"")
				+"\"schoolCity\":"+ (schoolCity != null ?"\"" +   schoolCity + "\"" : "\"")
				+ "}";
		
		return res;
	}
	@Override
	public String toString() {
		return "{"
		+"\"DT_RowId\":\""+ id + "\",\""
		+"name\":"+ (name != null ?  "\"" + name + "\",\"" : "\"\",\"")
		+"state\":"+ (state != null ? "\"" +  state + "\",\"" : "\"\",\"")
		+"schoolName\":"+ (schoolName != null ? "\"" +  schoolName + "\",\"" : "\"\",\"")
		+"schoolCity\":"+ (schoolCity != null ? "\"" + schoolCity + "\",\"" : "\"\",\"")
		+"maxprepsUrl\":"+ (maxprepsUrl != null ? "\"" + maxprepsUrl + "\",\"" : "\"\",\"")
		+"pos\":"+ (pos != null ? "\"" +  pos + "\",\"" : "\"\",\"")
		+"height\":"+ (height != null ? "\"" + height.replaceAll("\"", "") + "\",\"" : "\"\",\"")
		+"fortyDash\":"+  (fortyDash != null ? "\"" + fortyDash + "\", \"" : "\"\",\"")
		+"weight\":"+  (weight != 0 ? "\"" + weight + "\", \"" : "\"\",\"")
		+"stars\":"+  (stars != 0 ? "\"" + stars + "\", \"" : "\"\",\"")
		+"rating\":"+  (rating != null ? "\"" + rating + "\", \"" : "\"\",\"")
		+"gradYear\":"+  (gradYear != 0 ? "\"" + gradYear + "\", \"" : "\"\",\"")
		+"GP\":"+  (GP != 0 ? "\"" + +GP + "\", \"" : "\"\",\"")
		+"Avg\":"+  (Avg != 0 ? "\"" + +Avg + "\", \"" : "\"\",\"")
		+"OBP\":"+  (OBP != 0 ? "\"" + +OBP + "\", \"" : "\"\",\"")
		+"H\":"+  (H != 0 ? "\"" + +H + "\", \"" : "\"\",\"")
		+"RBI\":"+  (RBI != 0 ? "\"" + +RBI + "\", \"" : "\"\",\"")
		+"R\":"+  (R != 0 ? "\"" + +R + "\", \"" : "\"\",\"")
		+"SB\":"+  (SB != 0 ? "\"" + +SB + "\", \"" : "\"\",\"")
		+"AB\":"+  (AB != 0 ? "\"" + +AB + "\", \"" : "\"\",\"")
		+"SLG\":"+  (SLG != 0 ? "\"" + +SLG + "\", \"" : "\"\",\"")
		+"PA\":"+  (PA != 0 ? "\"" + +PA + "\", \"" : "\"\",\"")
		+"FP\":"+  (FP != 0 ? "\"" + +FP + "\", \"" : "\"\",\"")
		+"K\":"+  (K != 0 ? "\"" + +K + "\", \"" : "\"\",\"")
		+"IP\":"+  (IP != 0 ? "\"" + IP + "\"" : "\"\"") 
		+ "}";
	}

		
	public static String[] STATE = {
		"AL",
		"AK",
		"AZ",
		"AR",
		"CA",
		"CO",
		"CT",
		"DE",
		"FL",
		"GA",
		"HI",
		"ID",
		"IL",
		"IN",
		"IA",
		"KS",
		"KY",
		"LA",
		"ME",
		"MD",
		"MA",
		"MI",
		"MN",
		"MS",
		"MO",
		"MT",
		"NE",
		"NV",
		"NH",
		"NJ",
		"NM",
		"NY",
		"NC",
		"ND",
		"OH",
		"OK",
		"OR",
		"PA",
		"RI",
		"SC",
		"SD",
		"TN",
		"TX",
		"UT",
		"VT",
		"VA",
		"WA",
		"DC",
		"WV",
		"WI",
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
	"C",
	"OF"
	};
	
	public static String[] STATS = {
		"GP", "Avg", "OBP", "H", "RBI", "R", "SB", "AB", "SLG", "PA", "FP", "K", "IP" 
	};

}
