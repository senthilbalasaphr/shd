package com.sfec.freeman.restax;

import java.util.Calendar;

public class TaxGrp {
	
	String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	Calendar	startDate;
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	long fromZipNo;

	public long getToZipNo() {
		return toZipNo;
	}
	public void setToZipNo(long toZipNo) {
		this.toZipNo = toZipNo;
	}
	long toZipNo;


}
