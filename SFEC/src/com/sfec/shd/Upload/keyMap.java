package com.sfec.shd.Upload;

import java.util.Date;

public class keyMap {
	
	String externalCode;
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getCust_Company() {
		return cust_Company;
	}
	public void setCust_Company(String cust_Company) {
		this.cust_Company = cust_Company;
	}
	public String getCust_ObjectType() {
		return cust_ObjectType;
	}
	public void setCust_ObjectType(String cust_ObjectType) {
		this.cust_ObjectType = cust_ObjectType;
	}
	public String getCust_LegacyID() {
		return cust_LegacyID;
	}
	public void setCust_LegacyID(String cust_LegacyID) {
		this.cust_LegacyID = cust_LegacyID;
	}
	public String getCust_SFID() {
		return cust_SFID;
	}
	public void setCust_SFID(String cust_SFID) {
		this.cust_SFID = cust_SFID;
	}
	Date effectiveStartDate;
	String cust_Company;
	String cust_ObjectType;
	String cust_LegacyID;
	String cust_SFID;
	

}
