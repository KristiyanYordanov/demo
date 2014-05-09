package com.krissoft.saa.config;

import org.springframework.data.domain.Page;

import com.google.gson.annotations.Expose;

public class DataTableJsonObject {

	@Expose
	private Integer recordsFiltered;
	@Expose
	private Integer recordsTotal;
	@Expose
	private Page<PlayerDoc> aaData;
	@Expose
	private String draw;

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public DataTableJsonObject withRecordsFiltered(
			Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
		return this;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public DataTableJsonObject withRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
		return this;
	}

	public Page<PlayerDoc> getAaData() {
		return aaData;
	}

	public void setAaData(Page<PlayerDoc> aaData) {
		this.aaData = aaData;
	}

	public DataTableJsonObject withAaData(Page<PlayerDoc> aaData) {
		this.aaData = aaData;
		return this;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public DataTableJsonObject withDraw(String draw) {
		this.draw = draw;
		return this;
	}

	@Override
	public String toString() {
		return "{"
				+ (recordsFiltered != null ? "\"recordsFiltered\":\""
						+ recordsFiltered + "\", " : "")
				+ (recordsTotal != null ? "\"recordsTotal\":\"" + recordsTotal
						+ "\", " : "")
				+ (aaData != null ? "\"aaData\":" + aaData.getContent() + " " : "")
				+ (draw != null ? ",\"draw\":\"" + draw +"\"" : "") + "}";
	}

}