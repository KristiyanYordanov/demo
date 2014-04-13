package kris.java.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

public class Example {

	@Expose
	private Integer iTotalDisplayRecords;
	@Expose
	private Integer iTotalRecords;
	@Expose
	private List<List<PlayerDoc>> aaData = new ArrayList<List<PlayerDoc>>();
	@Expose
	private String sEcho;

	public Integer getITotalDisplayRecords() {
	return iTotalDisplayRecords;
	}

	public void setITotalDisplayRecords(Integer iTotalDisplayRecords) {
	this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public Example withITotalDisplayRecords(Integer iTotalDisplayRecords) {
	this.iTotalDisplayRecords = iTotalDisplayRecords;
	return this;
	}

	public Integer getITotalRecords() {
	return iTotalRecords;
	}

	public void setITotalRecords(Integer iTotalRecords) {
	this.iTotalRecords = iTotalRecords;
	}

	public Example withITotalRecords(Integer iTotalRecords) {
	this.iTotalRecords = iTotalRecords;
	return this;
	}

	public List<List<PlayerDoc>> getAaData() {
	return aaData;
	}

	public void setAaData(List<List<PlayerDoc>> aaData) {
	this.aaData = aaData;
	}

	public Example withAaData(List<List<PlayerDoc>> aaData) {
	this.aaData = aaData;
	return this;
	}

	public String getSEcho() {
	return sEcho;
	}

	public void setSEcho(String sEcho) {
	this.sEcho = sEcho;
	}

	public Example withSEcho(String sEcho) {
	this.sEcho = sEcho;
	return this;
	}

	@Override
	public String toString() {
	return ToStringBuilder.reflectionToString(this);
	}

	}