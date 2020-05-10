
package com.shd.reports.department;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "externalCode",
    "startDate",
    "endDate",
    "createdBy",
    "lastModifiedBy",
    "name",
    "createdOn",
    "lastModifiedOn",
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
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("name")
    private String name;
    @JsonProperty("createdOn")
    private String createdOn;
    @JsonProperty("lastModifiedOn")
    private String lastModifiedOn;
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

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("lastModifiedBy")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @JsonProperty("lastModifiedBy")
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("createdOn")
    public String getCreatedOn() {
        return createdOn;
    }

    @JsonProperty("createdOn")
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty("lastModifiedOn")
    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    @JsonProperty("lastModifiedOn")
    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
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
