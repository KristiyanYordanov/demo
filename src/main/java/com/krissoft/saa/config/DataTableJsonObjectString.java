package com.krissoft.saa.config;

import com.google.gson.annotations.Expose;

public class DataTableJsonObjectString {

	@Expose
	private Integer iTotalDisplayRecords;
	@Expose
	private Integer iTotalRecords;
	@Expose
	private String aaData;
	@Expose
	private String sEcho;

	public Integer getITotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setITotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public DataTableJsonObjectString withITotalDisplayRecords(
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

	public DataTableJsonObjectString withITotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
		return this;
	}

	public String getAaData() {
		return aaData;
	}

	public void setAaData(String aaData) {
		this.aaData = aaData;
	}

	public DataTableJsonObjectString withAaData(String aaData) {
		this.aaData = aaData;
		return this;
	}

	public String getSEcho() {
		return sEcho;
	}

	public void setSEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public DataTableJsonObjectString withSEcho(String sEcho) {
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
				+ (aaData != null ? "\"aaData\":[" + aaData + " " : "")
				+ (sEcho != null ? "]\"sEcho\":\"" + sEcho : "") + "\"}";
	}

}