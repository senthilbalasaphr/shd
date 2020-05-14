
package com.shd.DepartmentLevel4;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "externalCode",
    "effectiveStartDate",
    "cust_department",
    "mdfSystemStatus",
    "externalName_defaultValue",
    "externalName_localized",
    "externalName_en_US",
    "cust_emeaDeptLevel3"
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("externalCode")
    private String externalCode;
    @JsonProperty("effectiveStartDate")
    private String effectiveStartDate;
    @JsonProperty("cust_department")
    private String custDepartment;
    @JsonProperty("mdfSystemStatus")
    private String mdfSystemStatus;
    @JsonProperty("externalName_defaultValue")
    private String externalNameDefaultValue;
    @JsonProperty("externalName_localized")
    private String externalNameLocalized;
    @JsonProperty("externalName_en_US")
    private String externalNameEnUS;
    @JsonProperty("cust_emeaDeptLevel3")
    private CustEmeaDeptLevel3 custEmeaDeptLevel3;

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

    @JsonProperty("effectiveStartDate")
    public String getEffectiveStartDate() {
        return effectiveStartDate;
    }

    @JsonProperty("effectiveStartDate")
    public void setEffectiveStartDate(String effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    @JsonProperty("cust_department")
    public String getCustDepartment() {
        return custDepartment;
    }

    @JsonProperty("cust_department")
    public void setCustDepartment(String custDepartment) {
        this.custDepartment = custDepartment;
    }

    @JsonProperty("mdfSystemStatus")
    public String getMdfSystemStatus() {
        return mdfSystemStatus;
    }

    @JsonProperty("mdfSystemStatus")
    public void setMdfSystemStatus(String mdfSystemStatus) {
        this.mdfSystemStatus = mdfSystemStatus;
    }

    @JsonProperty("externalName_defaultValue")
    public String getExternalNameDefaultValue() {
        return externalNameDefaultValue;
    }

    @JsonProperty("externalName_defaultValue")
    public void setExternalNameDefaultValue(String externalNameDefaultValue) {
        this.externalNameDefaultValue = externalNameDefaultValue;
    }

    @JsonProperty("externalName_localized")
    public String getExternalNameLocalized() {
        return externalNameLocalized;
    }

    @JsonProperty("externalName_localized")
    public void setExternalNameLocalized(String externalNameLocalized) {
        this.externalNameLocalized = externalNameLocalized;
    }

    @JsonProperty("externalName_en_US")
    public String getExternalNameEnUS() {
        return externalNameEnUS;
    }

    @JsonProperty("externalName_en_US")
    public void setExternalNameEnUS(String externalNameEnUS) {
        this.externalNameEnUS = externalNameEnUS;
    }

    @JsonProperty("cust_emeaDeptLevel3")
    public CustEmeaDeptLevel3 getCustEmeaDeptLevel3() {
        return custEmeaDeptLevel3;
    }

    @JsonProperty("cust_emeaDeptLevel3")
    public void setCustEmeaDeptLevel3(CustEmeaDeptLevel3 custEmeaDeptLevel3) {
        this.custEmeaDeptLevel3 = custEmeaDeptLevel3;
    }

}
