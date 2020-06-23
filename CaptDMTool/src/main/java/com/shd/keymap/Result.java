
package com.shd.keymap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "cust_SFID",
    "cust_LegacyID",
    "externalName",
    "externalCode",
    "cust_sim"
    
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("cust_SFID")
    private String custSFID;
    @JsonProperty("cust_LegacyID")
    private String cust_LegacyID;
    @JsonProperty("externalName")
    private String externalName; 
    @JsonProperty("externalCode")
    private String externalCode; 
    @JsonProperty("cust_ObjectType")
    private String cust_ObjectType; 
    @JsonProperty("cust_sim")
    private String cust_sim; 
    
    

    

    public String getCust_sim() {
		return cust_sim;
	}

	public void setCust_sim(String cust_sim) {
		this.cust_sim = cust_sim;
	}

	public String getCust_ObjectType() {
		return cust_ObjectType;
	}

	public void setCust_ObjectType(String cust_ObjectType) {
		this.cust_ObjectType = cust_ObjectType;
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	public String getCust_LegacyID() {
		return cust_LegacyID;
	}

	public void setCust_LegacyID(String cust_LegacyID) {
		this.cust_LegacyID = cust_LegacyID;
	}

	@JsonProperty("__metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("cust_SFID")
    public String getCustSFID() {
        return custSFID;
    }

    @JsonProperty("cust_SFID")
    public void setCustSFID(String custSFID) {
        this.custSFID = custSFID;
    }

}
