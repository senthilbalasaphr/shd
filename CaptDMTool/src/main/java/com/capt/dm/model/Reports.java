package com.capt.dm.model;

import javax.validation.constraints.Size;

public class Reports {
	
	@Size(min=1,message="required") 
	private String query;
	@Size(min=1,message="required") 
	private String company;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	

}
