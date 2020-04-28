package com.sfec.shd;

public class FieldFunc {
	
	String Client;
	public String getTEMPLATEGRP() {
		return TEMPLATEGRP;
	}
	public void setTEMPLATEGRP(String tEMPLATEGRP) {
		TEMPLATEGRP = tEMPLATEGRP;
	}
	public String getTEMPLATE() {
		return TEMPLATE;
	}
	public void setTEMPLATE(String tEMPLATE) {
		TEMPLATE = tEMPLATE;
	}
	public String getFIELD_NAME() {
		return FIELD_NAME;
	}
	public void setFIELD_NAME(String fIELD_NAME) {
		FIELD_NAME = fIELD_NAME;
	}


	public String getFUNCTION_ID() {
		return FUNCTION_ID;
	}
	public void setFUNCTION_ID(String fUNCTION_ID) {
		FUNCTION_ID = fUNCTION_ID;
	}
	public String getFUNCTION_ROUTINE() {
		return FUNCTION_ROUTINE;
	}
	public void setFUNCTION_ROUTINE(String fUNCTION_ROUTINE) {
		FUNCTION_ROUTINE = fUNCTION_ROUTINE;
	}
	String TEMPLATEGRP;
	String TEMPLATE;
	String FIELD_NAME;
	public String getClient() {
		return Client;
	}
	public void setClient(String client) {
		Client = client;
	}
	public int getSEQNR() {
		return SEQNR;
	}
	public void setSEQNR(int sEQNR) {
		SEQNR = sEQNR;
	}
	int SEQNR;
	String FUNCTION_ID;
	String FUNCTION_ROUTINE;

	
	

}
