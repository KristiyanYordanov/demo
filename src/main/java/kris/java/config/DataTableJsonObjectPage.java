package kris.java.config;

import org.springframework.data.domain.Page;

import com.google.gson.annotations.Expose;

public class DataTableJsonObjectPage {

	@Expose
	private Integer iTotalDisplayRecords;
	@Expose
	private Integer iTotalRecords;
	@Expose
	private Page<PlayerDoc> aaData;
	@Expose
	private String sEcho;

	public Integer getITotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setITotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public DataTableJsonObjectPage withITotalDisplayRecords(
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

	public DataTableJsonObjectPage withITotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
		return this;
	}

	public Page<PlayerDoc> getAaData() {
		return aaData;
	}

	public void setAaData(Page<PlayerDoc> aaData) {
		this.aaData = aaData;
	}

	public DataTableJsonObjectPage withAaData(Page<PlayerDoc> aaData) {
		this.aaData = aaData;
		return this;
	}

	public String getSEcho() {
		return sEcho;
	}

	public void setSEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public DataTableJsonObjectPage withSEcho(String sEcho) {
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
				+ (aaData != null ? "\"aaData\":" + aaData.getContent() + ", " : "")
				+ (sEcho != null ? "\"sEcho\":\"" + sEcho : "") + "\"}";
	}

}