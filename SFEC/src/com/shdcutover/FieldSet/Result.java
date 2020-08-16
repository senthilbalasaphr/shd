
package com.shdcutover.FieldSet;


import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "Client",
    "TEMPLATEGRP",
    "TEMPLATE",
    "SEQNR",
    "FIELD_NAME",
    "FIELD_DESCRIPTION",
    "FIELD_TYPE",
    "FIELD_LENGTH",
    "FIELD_REQUIRED",
    "FIELD_KEY",
    "VALUE_MAPPING"
})
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
    @JsonProperty("FIELD_DESCRIPTION")
    private String fIELDDESCRIPTION;
    @JsonProperty("FIELD_TYPE")
    private String fIELDTYPE;
    @JsonProperty("FIELD_LENGTH")
    private Integer fIELDLENGTH;
    @JsonProperty("FIELD_REQUIRED")
    private String fIELDREQUIRED;
    @JsonProperty("FIELD_KEY")
    private String fIELDKEY;
    @JsonProperty("VALUE_MAPPING")
    private String vALUEMAPPING;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("FIELD_DESCRIPTION")
    public String getFIELDDESCRIPTION() {
        return fIELDDESCRIPTION;
    }

    @JsonProperty("FIELD_DESCRIPTION")
    public void setFIELDDESCRIPTION(String fIELDDESCRIPTION) {
        this.fIELDDESCRIPTION = fIELDDESCRIPTION;
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

    @JsonProperty("FIELD_REQUIRED")
    public String getFIELDREQUIRED() {
        return fIELDREQUIRED;
    }

    @JsonProperty("FIELD_REQUIRED")
    public void setFIELDREQUIRED(String fIELDREQUIRED) {
        this.fIELDREQUIRED = fIELDREQUIRED;
    }

    @JsonProperty("FIELD_KEY")
    public String getFIELDKEY() {
        return fIELDKEY;
    }

    @JsonProperty("FIELD_KEY")
    public void setFIELDKEY(String fIELDKEY) {
        this.fIELDKEY = fIELDKEY;
    }

    @JsonProperty("VALUE_MAPPING")
    public String getVALUEMAPPING() {
        return vALUEMAPPING;
    }

    @JsonProperty("VALUE_MAPPING")
    public void setVALUEMAPPING(String vALUEMAPPING) {
        this.vALUEMAPPING = vALUEMAPPING;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
