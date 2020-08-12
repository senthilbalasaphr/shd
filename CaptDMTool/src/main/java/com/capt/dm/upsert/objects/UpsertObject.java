
package com.capt.dm.upsert.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "externalCode",
    "effectiveStartDate",
    "cust_LegacyID",
    "externalName",
    "cust_Company",
    "cust_SFID",
    "cust_ObjectType",
    "cust_sim"
})
public class UpsertObject {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("externalCode")
    private String externalCode;
    @JsonProperty("effectiveStartDate")
    private String effectiveStartDate;
    @JsonProperty("cust_LegacyID")
    private String custLegacyID;
    @JsonProperty("externalName")
    private String externalName;
    @JsonProperty("cust_Company")
    private String custCompany;
    @JsonProperty("cust_SFID")
    private String custSFID;
    @JsonProperty("cust_ObjectType")
    private String custObjectType;
    @JsonProperty("cust_sim")
    private String cust_sim;

    public String getCust_sim() {
		return cust_sim;
	}

	public void setCust_sim(String cust_sim) {
		this.cust_sim = cust_sim;
	}

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

    @JsonProperty("cust_LegacyID")
    public String getCustLegacyID() {
        return custLegacyID;
    }

    @JsonProperty("cust_LegacyID")
    public void setCustLegacyID(String custLegacyID) {
        this.custLegacyID = custLegacyID;
    }

    @JsonProperty("externalName")
    public String getExternalName() {
        return externalName;
    }

    @JsonProperty("externalName")
    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    @JsonProperty("cust_Company")
    public String getCustCompany() {
        return custCompany;
    }

    @JsonProperty("cust_Company")
    public void setCustCompany(String custCompany) {
        this.custCompany = custCompany;
    }

    @JsonProperty("cust_SFID")
    public String getCustSFID() {
        return custSFID;
    }

    @JsonProperty("cust_SFID")
    public void setCustSFID(String custSFID) {
        this.custSFID = custSFID;
    }

    @JsonProperty("cust_ObjectType")
    public String getCustObjectType() {
        return custObjectType;
    }

    @JsonProperty("cust_ObjectType")
    public void setCustObjectType(String custObjectType) {
        this.custObjectType = custObjectType;
    }

}
