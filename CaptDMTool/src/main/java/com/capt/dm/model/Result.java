
package com.capt.dm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "__metadata", "Client", "TEMPLATEGRP", "TEMPLATE", "SEQNR", "FIELD_NAME", "FIELD_TYPE",
		"FIELD_LENGTH", "VALUE_MAPPING", "FUNCTION_ID", "FUNCTION_ROUTINE", "SOURCE_VAL", "TARGET_VAL" })
public class Result {

	@JsonProperty("__metadata")
	private Metadata metadata;
	@JsonProperty("Client")
	private String client;
	@JsonProperty("TEMPLATEGRP")
	private String tEMPLATEGRP;
	@JsonProperty("TEMPLATE")
	private String tEMPLATE;
	@JsonProperty("SEQNR")
	private Integer sEQNR;
	@JsonProperty("FIELD_NAME")
	private String fIELDNAME;
	@JsonProperty("FIELD_TYPE")
	private String fIELDTYPE;
	@JsonProperty("FIELD_LENGTH")
	private Integer fIELDLENGTH;
	@JsonProperty("VALUE_MAPPING")
	private String vALUEMAPPING;
	@JsonProperty("FUNCTION_ID")
	private String fUNCTIONID;
	@JsonProperty("FUNCTION_ROUTINE")
	private String fUNCTIONROUTINE;
	@JsonProperty("FUNCTION_ROUTINE_ID")
	private String fUNCTIONROUTINEID;
	@JsonProperty("CLASS")
	private String cLASS;
	@JsonProperty("METHOD")
	private String mETHOD;
	@JsonProperty("SOURCE_VAL")
	private String sourceVal;
	@JsonProperty("TARGET_VAL")
	private String targetVal;
	@JsonProperty("cust_SFID")
	private String custSFID;

	@JsonProperty("cust_SFID")
	public String getCustSFID() {
		return custSFID;
	}

	@JsonProperty("cust_SFID")
	public void setCustSFID(String custSFID) {
		this.custSFID = custSFID;
	}

	@JsonProperty("__metadata")
	public Metadata getMetadata() {
		return metadata;
	}

	@JsonProperty("__metadata")
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@JsonProperty("Client")
	public String getClient() {
		return client;
	}

	@JsonProperty("Client")
	public void setClient(String client) {
		this.client = client;
	}

	@JsonProperty("TEMPLATEGRP")
	public String getTEMPLATEGRP() {
		return tEMPLATEGRP;
	}

	@JsonProperty("TEMPLATEGRP")
	public void setTEMPLATEGRP(String tEMPLATEGRP) {
		this.tEMPLATEGRP = tEMPLATEGRP;
	}

	@JsonProperty("TEMPLATE")
	public String getTEMPLATE() {
		return tEMPLATE;
	}

	@JsonProperty("TEMPLATE")
	public void setTEMPLATE(String tEMPLATE) {
		this.tEMPLATE = tEMPLATE;
	}

	@JsonProperty("SEQNR")
	public Integer getSEQNR() {
		return sEQNR;
	}

	@JsonProperty("SEQNR")
	public void setSEQNR(Integer sEQNR) {
		this.sEQNR = sEQNR;
	}

	@JsonProperty("FIELD_NAME")
	public String getFIELDNAME() {
		return fIELDNAME;
	}

	@JsonProperty("FIELD_NAME")
	public void setFIELDNAME(String fIELDNAME) {
		this.fIELDNAME = fIELDNAME;
	}

	@JsonProperty("FIELD_TYPE")
	public String getFIELDTYPE() {
		return fIELDTYPE;
	}

	@JsonProperty("FIELD_TYPE")
	public void setFIELDTYPE(String fIELDTYPE) {
		this.fIELDTYPE = fIELDTYPE;
	}

	@JsonProperty("FIELD_LENGTH")
	public Integer getFIELDLENGTH() {
		return fIELDLENGTH;
	}

	@JsonProperty("FIELD_LENGTH")
	public void setFIELDLENGTH(Integer fIELDLENGTH) {
		this.fIELDLENGTH = fIELDLENGTH;
	}

	@JsonProperty("VALUE_MAPPING")
	public String getVALUEMAPPING() {
		return vALUEMAPPING;
	}

	@JsonProperty("VALUE_MAPPING")
	public void setVALUEMAPPING(String vALUEMAPPING) {
		this.vALUEMAPPING = vALUEMAPPING;
	}

	@JsonProperty("FUNCTION_ID")
	public String getFUNCTIONID() {
		return fUNCTIONID;
	}

	@JsonProperty("FUNCTION_ID")
	public void setFUNCTIONID(String fUNCTIONID) {
		this.fUNCTIONID = fUNCTIONID;
	}

	@JsonProperty("FUNCTION_ROUTINE")
	public String getFUNCTIONROUTINE() {
		return fUNCTIONROUTINE;
	}

	@JsonProperty("FUNCTION_ROUTINE")
	public void setFUNCTIONROUTINE(String fUNCTIONROUTINE) {
		this.fUNCTIONROUTINE = fUNCTIONROUTINE;
	}

	@JsonProperty("SOURCE_VAL")
	public String getSourceVal() {
		return sourceVal;
	}

	@JsonProperty("SOURCE_VAL")
	public void setSourceVal(String sourceVal) {
		this.sourceVal = sourceVal;
	}

	@JsonProperty("TARGET_VAL")
	public String getTargetVal() {
		return targetVal;
	}

	@JsonProperty("TARGET_VAL")
	public void setTargetVal(String targetVal) {
		this.targetVal = targetVal;
	}

	@JsonProperty("FUNCTION_ROUTINE_ID")
	public String getfUNCTIONROUTINEID() {
		return fUNCTIONROUTINEID;
	}

	@JsonProperty("FUNCTION_ROUTINE_ID")
	public void setfUNCTIONROUTINEID(String fUNCTIONROUTINEID) {
		this.fUNCTIONROUTINEID = fUNCTIONROUTINEID;
	}

	@JsonProperty("CLASS")
	public String getcLASS() {
		return cLASS;
	}

	@JsonProperty("CLASS")
	public void setcLASS(String cLASS) {
		this.cLASS = cLASS;
	}

	@JsonProperty("METHOD")
	public String getmETHOD() {
		return mETHOD;
	}

	@JsonProperty("METHOD")
	public void setmETHOD(String mETHOD) {
		this.mETHOD = mETHOD;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(metadata).append(fIELDLENGTH).append(sEQNR).append(client).append(fIELDTYPE)
				.append(fUNCTIONID).append(tEMPLATE).append(tEMPLATEGRP).append(fIELDNAME).append(vALUEMAPPING)
				.append(fUNCTIONROUTINE).append(sourceVal).append(targetVal).append(fUNCTIONROUTINEID).append(cLASS)
				.append(mETHOD).append(custSFID).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Result) == false) {
			return false;
		}
		Result rhs = ((Result) other);
		return new EqualsBuilder().append(metadata, rhs.metadata).append(fIELDLENGTH, rhs.fIELDLENGTH)
				.append(sEQNR, rhs.sEQNR).append(client, rhs.client).append(fIELDTYPE, rhs.fIELDTYPE)
				.append(fUNCTIONID, rhs.fUNCTIONID).append(tEMPLATE, rhs.tEMPLATE).append(tEMPLATEGRP, rhs.tEMPLATEGRP)
				.append(fIELDNAME, rhs.fIELDNAME).append(vALUEMAPPING, rhs.vALUEMAPPING)
				.append(fUNCTIONROUTINE, rhs.fUNCTIONROUTINE).append(sourceVal, rhs.sourceVal)
				.append(targetVal, rhs.targetVal).append(fUNCTIONROUTINEID, rhs.targetVal).append(cLASS, rhs.cLASS)
				.append(mETHOD, rhs.mETHOD).append(custSFID, rhs.custSFID).isEquals();
	}

}
