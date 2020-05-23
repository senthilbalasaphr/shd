
package com.shd.DepartmentLevel1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "externalCode",
    "effectiveStartDate",
    "mdfSystemStatus",
    "mdfSystemEffectiveEndDate",
    "externalName_defaultValue",
    "externalName_localized",
    "externalName_en_US",
    "cust_Division",
    "cust_LegalEntity",
    "lastModifiedDateTime",
    "createdDateTime",
    "createdBy",
    "lastModifiedBy"
    
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("externalCode")
    private String externalCode;
    @JsonProperty("effectiveStartDate")
    private String effectiveStartDate;
    @JsonProperty("mdfSystemStatus")
    private String mdfSystemStatus;
    @JsonProperty("mdfSystemEffectiveEndDate")
    private String mdfSystemEffectiveEndDate;
    @JsonProperty("externalName_defaultValue")
    private String externalNameDefaultValue;
    @JsonProperty("externalName_localized")
    private String externalNameLocalized;
    @JsonProperty("externalName_en_US")
    private String externalNameEnUS;
    @JsonProperty("cust_Division")
    private CustDivision custDivision;
    @JsonProperty("cust_LegalEntity")
    private CustLegalEntity custLegalEntity;
    @JsonProperty("lastModifiedDateTime")
    private String lastModifiedDateTime;
	@JsonProperty("createdDateTime")
    private String createdDateTime;
    public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;

    
    
    public String getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}

	public void setLastModifiedDateTime(String lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

    @JsonProperty("mdfSystemStatus")
    public String getMdfSystemStatus() {
        return mdfSystemStatus;
    }

    @JsonProperty("mdfSystemStatus")
    public void setMdfSystemStatus(String mdfSystemStatus) {
        this.mdfSystemStatus = mdfSystemStatus;
    }

    @JsonProperty("mdfSystemEffectiveEndDate")
    public String getMdfSystemEffectiveEndDate() {
        return mdfSystemEffectiveEndDate;
    }

    @JsonProperty("mdfSystemEffectiveEndDate")
    public void setMdfSystemEffectiveEndDate(String mdfSystemEffectiveEndDate) {
        this.mdfSystemEffectiveEndDate = mdfSystemEffectiveEndDate;
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
