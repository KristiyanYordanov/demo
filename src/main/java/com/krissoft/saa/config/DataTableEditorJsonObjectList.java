package com.krissoft.saa.config;

import java.util.List;

import com.google.gson.annotations.Expose;

public class DataTableEditorJsonObjectList {

	@Expose
	private Integer recordsFiltered;
	@Expose
	private Integer recordsTotal;
	@Expose
	private List<? extends ObjectIdSerializer> aaData;
	@Expose
	private String draw;

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public DataTableEditorJsonObjectList withRecordsFiltered(
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

	public DataTableEditorJsonObjectList withRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
		return this;
	}

	public List<? extends ObjectIdSerializer> getAaData() {
		return aaData;
	}

	public void setAaData(List<? extends ObjectIdSerializer> aaData) {
		this.aaData = aaData;
	}

	public DataTableEditorJsonObjectList withAaData(List<? extends ObjectIdSerializer> aaData) {
		this.aaData = aaData;
		return this;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public DataTableEditorJsonObjectList withDraw(String draw) {
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
				+ (aaData != null ? "\"aaData\":" + aaData + " " : "")
				+ (draw != null ? ",\"draw\":\"" + draw +"\"" : "") + "}";
	}

}