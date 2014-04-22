package kris.java.config;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class DataTableJsonObjectList {

	@Expose
	private Integer iTotalDisplayRecords;
	@Expose
	private Integer iTotalRecords;
	@Expose
	private List<PlayerDoc> aaData = new ArrayList<PlayerDoc>();
	@Expose
	private String sEcho;

	public Integer getITotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setITotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public DataTableJsonObjectList withITotalDisplayRecords(
			Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		return this;
	}

	public Integer getITotalRecords() {
		return iTotalRecords;
	}

	public void setITotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public DataTableJsonObjectList withITotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
		return this;
	}

	public List<PlayerDoc> getAaData() {
		return aaData;
	}

	public void setAaData(List<PlayerDoc> aaData) {
		this.aaData = aaData;
	}

	public DataTableJsonObjectList withAaData(List<PlayerDoc> aaData) {
		this.aaData = aaData;
		return this;
	}

	public String getSEcho() {
		return sEcho;
	}

	public void setSEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public DataTableJsonObjectList withSEcho(String sEcho) {
		this.sEcho = sEcho;
		return this;
	}

	@Override
	public String toString() {
		return "{"
				+ (iTotalDisplayRecords != null ? "\"iTotalDisplayRecords\":"
						+ iTotalDisplayRecords + ", " : "")
				+ (iTotalRecords != null ? "\"iTotalRecords\":" + iTotalRecords
						+ ", " : "")
				+ (aaData != null ? "\"aaData\":" + aaData + ", " : "")
				+ (sEcho != null ? "\"sEcho\":\"" + sEcho : "") + "\"}";
	}

}