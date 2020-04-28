package com.capt.dm.binding.objects;

import com.capt.dm.model.Metadata;

public class BindingObject {

	private String client;
	private String templateGrp;
	private String template;
	private String seqNo;
	private String fieldName;
	private String fieldType;
	private String fieldLength;
	private String valueMapping;
	private String functionId;
	private String functionRoutine;
	private String sourceVal;
	private String targetVal;

	// variables to hold values for upsert in util class
	private String externalCode;
	private String effectiveStartDate;
	private String cust_LegacyID;
	private String externalName;
	private String cust_Company;
	private String cust_SFID;
	private String cust_ObjectType;

	private Metadata __metadata;
	
	public Metadata getMetadata() {
		return __metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.__metadata = metadata;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getTemplateGrp() {
		return templateGrp;
	}

	public void setTemplateGrp(String templateGrp) {
		this.templateGrp = templateGrp;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getValueMapping() {
		return valueMapping;
	}

	public void setValueMapping(String valueMapping) {
		this.valueMapping = valueMapping;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionRoutine() {
		return functionRoutine;
	}

	public void setFunctionRoutine(String functionRoutine) {
		this.functionRoutine = functionRoutine;
	}

	public String getSourceVal() {
		return sourceVal;
	}

	public void setSourceVal(String sourceVal) {
		this.sourceVal = sourceVal;
	}

	public String getTargetVal() {
		return targetVal;
	}

	public void setTargetVal(String targetVal) {
		this.targetVal = targetVal;
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}

	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public String getCust_LegacyID() {
		return cust_LegacyID;
	}

	public void setCust_LegacyID(String cust_LegacyID) {
		this.cust_LegacyID = cust_LegacyID;
	}

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	public String getCust_Company() {
		return cust_Company;
	}

	public void setCust_Company(String cust_Company) {
		this.cust_Company = cust_Company;
	}

	public String getCust_SFID() {
		return cust_SFID;
	}

	public void setCust_SFID(String cust_SFID) {
		this.cust_SFID = cust_SFID;
	}

	public String getCust_ObjectType() {
		return cust_ObjectType;
	}

	public void setCust_ObjectType(String cust_ObjectType) {
		this.cust_ObjectType = cust_ObjectType;
	}

}
