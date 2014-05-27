package com.krissoft.saa.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "school")
public class School extends ObjectIdSerializer {

	private String ets;
	private String schoolName;
	private String schoolAddress;
	private String schoolCity;
	private String schoolState;
	private String schoolZip;
	private String schoolAreaCode;
	private String schoolPhone;
	private String schoolCoachOffice;
	private String schoolFax;

	@Id
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getEts() {
		return ets;
	}

	public void setEts(String ets) {
		this.ets = ets;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public String getSchoolCity() {
		return schoolCity;
	}

	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}

	public String getSchoolState() {
		return schoolState;
	}

	public void setSchoolState(String schoolState) {
		this.schoolState = schoolState;
	}

	public String getSchoolZip() {
		return schoolZip;
	}

	public void setSchoolZip(String schoolZip) {
		this.schoolZip = schoolZip;
	}

	public String getSchoolAreaCode() {
		return schoolAreaCode;
	}

	public void setSchoolAreaCode(String schoolAreaCode) {
		this.schoolAreaCode = schoolAreaCode;
	}

	public String getSchoolPhone() {
		return schoolPhone;
	}

	public void setSchoolPhone(String schoolPhone) {
		this.schoolPhone = schoolPhone;
	}

	public String getSchoolCoachOffice() {
		return schoolCoachOffice;
	}

	public void setSchoolCoachOffice(String schoolCoachOffice) {
		this.schoolCoachOffice = schoolCoachOffice;
	}

	public String getSchoolFax() {
		return schoolFax;
	}

	public void setSchoolFax(String schoolFax) {
		this.schoolFax = schoolFax;
	}
	
	@Override
	public String toString() {
		return "{"
		+"\"DT_RowId\":\""+ id + "\",\""
		+"ets\":"+ (ets != null ?  "\"" + ets + "\",\"" : "\"\",\"")
		+"schoolName\":"+ (schoolName != null ? "\"" +  schoolName + "\",\"" : "\"\",\"")
		+"schoolAddress\":"+ (schoolAddress != null ? "\"" +  schoolAddress + "\",\"" : "\"\",\"")
		+"schoolCity\":"+ (schoolCity != null ? "\"" + schoolCity + "\",\"" : "\"\",\"")
		+"schoolState\":"+ (schoolState != null ? "\"" + schoolState + "\",\"" : "\"\",\"")
		+"schoolZip\":"+ (schoolZip != null ? "\"" +  schoolZip + "\",\"" : "\"\",\"")
		+"schoolAreaCode\":"+ (schoolAreaCode != null ? "\"" + schoolAreaCode + "\",\"" : "\"\",\"")
		+"schoolPhone\":"+  (schoolPhone != null ? "\"" + schoolPhone + "\", \"" : "\"\",\"")
		+"schoolCoachOffice\":"+  (schoolCoachOffice != null ? "\"" + schoolCoachOffice + "\", \"" : "\"\",\"")
		+"schoolFax\":"+  (schoolFax != null ? "\"" + schoolFax + "\"" : "\"\"")
		+ "}";
	}

}
