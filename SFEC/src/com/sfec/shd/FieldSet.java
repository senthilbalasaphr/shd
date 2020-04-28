package com.sfec.shd;

public class FieldSet {
	
	String	Client	;
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
	public int getSEQNR() {
		return SEQNR;
	}
	public void setSEQNR(int sEQNR) {
		SEQNR = sEQNR;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public int getFIELD_LENGTH() {
		return FIELD_LENGTH;
	}
	public void setFIELD_LENGTH(int fIELD_LENGTH) {
		FIELD_LENGTH = fIELD_LENGTH;
	}
	public String getVALUE_MAPPING() {
		return VALUE_MAPPING;
	}
	public void setVALUE_MAPPING(String vALUE_MAPPING) {
		VALUE_MAPPING = vALUE_MAPPING;
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
	String	TEMPLATEGRP	;
	String	TEMPLATE	;
	int SEQNR	;
	String	NAME	;
	String	NAME_DESC	;
	public String getNAME_DESC() {
		return NAME_DESC;
	}
	public void setNAME_DESC(String nAME_DESC) {
		NAME_DESC = nAME_DESC;
	}
	String	TYPE	;
	int FIELD_LENGTH	;
	String	VALUE_MAPPING	;
    String	FUNCTION_ID	;	
    String	FUNCTION_ROUTINE	;
    String	FIELD_REQUIRED	;
    String	FIELD_Key	;
	public String getFIELD_Key() {
		return FIELD_Key;
	}
	public void setFIELD_Key(String fIELD_Key) {
		FIELD_Key = fIELD_Key;
	}
	public String getFIELD_REQUIRED() {
		return FIELD_REQUIRED;
	}
	public void setFIELD_REQUIRED(String fIELD_REQUIRED) {
		FIELD_REQUIRED = fIELD_REQUIRED;
	}

}
