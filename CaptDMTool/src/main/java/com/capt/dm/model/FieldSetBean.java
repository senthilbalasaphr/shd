package com.capt.dm.model;

public class FieldSetBean {

	private String Client;
	private String templateGrp;
	private String template;
	private String seqnr;
	private String fieldName;
	private String fieldType;
	private String fieldLength;
	private String valueMapping;
	private String functionId;
	private String functionRoutine;

	public String getClient() {
		return Client;
	}

	public void setClient(String client) {
		Client = client;
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

	public String getSeqnr() {
		return seqnr;
	}

	public void setSeqnr(String seqnr) {
		this.seqnr = seqnr;
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

}
