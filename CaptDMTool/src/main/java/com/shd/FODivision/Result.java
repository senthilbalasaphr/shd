
package com.shd.FODivision;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "externalCode",
    "startDate",
    "name_defaultValue",
    "name_localized",
    "lastModifiedDateTime",
    "endDate",
    "lastModifiedBy",
    "description_defaultValue",
    "createdOn",
    "name_vi_VN",
    "name_en_US",
    "createdBy",
    "name",
    "description_localized",
    "status",
    "cust_LegalEntity"
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("externalCode")
    private String externalCode;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("name_defaultValue")
    private String nameDefaultValue;
    @JsonProperty("name_localized")
    private String nameLocalized;
    @JsonProperty("lastModifiedDateTime")
    private String lastModifiedDateTime;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("description_defaultValue")
    private String descriptionDefaultValue;
    @JsonProperty("createdOn")
    private String createdOn;
    @JsonProperty("name_vi_VN")
    private String nameViVN;
    @JsonProperty("name_en_US")
    private String nameEnUS;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description_localized")
    private String descriptionLocalized;
    @JsonProperty("status")
    private String status;
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

    @JsonProperty("name_defaultValue")
    public String getNameDefaultValue() {
        return nameDefaultValue;
    }

    @JsonProperty("name_defaultValue")
    public void setNameDefaultValue(String nameDefaultValue) {
        this.nameDefaultValue = nameDefaultValue;
    }

    @JsonProperty("name_localized")
    public String getNameLocalized() {
        return nameLocalized;
    }

    @JsonProperty("name_localized")
    public void setNameLocalized(String nameLocalized) {
        this.nameLocalized = nameLocalized;
    }

    @JsonProperty("lastModifiedDateTime")
    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    @JsonProperty("lastModifiedDateTime")
    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("lastModifiedBy")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @JsonProperty("lastModifiedBy")
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @JsonProperty("description_defaultValue")
    public String getDescriptionDefaultValue() {
        return descriptionDefaultValue;
    }

    @JsonProperty("description_defaultValue")
    public void setDescriptionDefaultValue(String descriptionDefaultValue) {
        this.descriptionDefaultValue = descriptionDefaultValue;
    }

    @JsonProperty("createdOn")
    public String getCreatedOn() {
        return createdOn;
    }

    @JsonProperty("createdOn")
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty("name_vi_VN")
    public String getNameViVN() {
        return nameViVN;
    }

    @JsonProperty("name_vi_VN")
    public void setNameViVN(String nameViVN) {
        this.nameViVN = nameViVN;
    }

    @JsonProperty("name_en_US")
    public String getNameEnUS() {
        return nameEnUS;
    }

    @JsonProperty("name_en_US")
    public void setNameEnUS(String nameEnUS) {
        this.nameEnUS = nameEnUS;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
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
