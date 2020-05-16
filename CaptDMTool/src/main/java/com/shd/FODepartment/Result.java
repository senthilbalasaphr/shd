
package com.shd.FODepartment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "externalCode",
    "startDate",
    "cust_deptLevel",
    "name_defaultValue",
    "endDate",
    "name",
    "description_localized",
    "description_defaultValue",
    "cust_Division",
    "cust_LegalEntity"
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("externalCode")
    private String externalCode;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("cust_deptLevel")
    private String custDeptLevel;
    @JsonProperty("name_defaultValue")
    private String nameDefaultValue;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description_localized")
    private String descriptionLocalized;
    @JsonProperty("description_defaultValue")
    private String descriptionDefaultValue;
    @JsonProperty("cust_Division")
    private CustDivision custDivision;
    @JsonProperty("cust_LegalEntity")
    private CustLegalEntity custLegalEntity;

    @JsonProperty("__metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("externalCode")
    public String getExternalCode() {
        return externalCode;
    }

    @JsonProperty("externalCode")
    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("cust_deptLevel")
    public String getCustDeptLevel() {
        return custDeptLevel;
    }

    @JsonProperty("cust_deptLevel")
    public void setCustDeptLevel(String custDeptLevel) {
        this.custDeptLevel = custDeptLevel;
    }

    @JsonProperty("name_defaultValue")
    public String getNameDefaultValue() {
        return nameDefaultValue;
    }

    @JsonProperty("name_defaultValue")
    public void setNameDefaultValue(String nameDefaultValue) {
        this.nameDefaultValue = nameDefaultValue;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description_localized")
    public String getDescriptionLocalized() {
        return descriptionLocalized;
    }

    @JsonProperty("description_localized")
    public void setDescriptionLocalized(String descriptionLocalized) {
        this.descriptionLocalized = descriptionLocalized;
    }

    @JsonProperty("description_defaultValue")
    public String getDescriptionDefaultValue() {
        return descriptionDefaultValue;
    }

    @JsonProperty("description_defaultValue")
    public void setDescriptionDefaultValue(String descriptionDefaultValue) {
        this.descriptionDefaultValue = descriptionDefaultValue;
    }

    @JsonProperty("cust_Division")
    public CustDivision getCustDivision() {
        return custDivision;
    }

    @JsonProperty("cust_Division")
    public void setCustDivision(CustDivision custDivision) {
        this.custDivision = custDivision;
    }

    @JsonProperty("cust_LegalEntity")
    public CustLegalEntity getCustLegalEntity() {
        return custLegalEntity;
    }

    @JsonProperty("cust_LegalEntity")
    public void setCustLegalEntity(CustLegalEntity custLegalEntity) {
        this.custLegalEntity = custLegalEntity;
    }

}
