package com.capt.dm.model;

public class TemplateBean {

	private String Client;
	private String TEMPLATEGRP;
	private String TEMPLATE;
	private String DESCRIPTION;
	private String ACTIVE;

	public String getTEMPLATE() {
		return TEMPLATE;
	}

	public void setTEMPLATE(String tEMPLATE) {
		TEMPLATE = tEMPLATE;
	}

	public String getACTIVE() {
		return ACTIVE;
	}

	public void setACTIVE(String aCTIVE) {
		ACTIVE = aCTIVE;
	}

	public String getClient() {
		return Client;
	}

	public void setClient(String client) {
		Client = client;
	}

	public String getTEMPLATEGRP() {
		return TEMPLATEGRP;
	}

	public void setTEMPLATEGRP(String tEMPLATEGRP) {
		TEMPLATEGRP = tEMPLATEGRP;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

}
