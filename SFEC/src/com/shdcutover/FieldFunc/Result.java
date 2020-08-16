
package com.shdcutover.FieldFunc;

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
    "FIELD_NAME",
    "SEQNR",
    "FUNCTION_ID",
    "FUNCTION_ROUTINE"
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
    @JsonProperty("FIELD_NAME")
    private String fIELDNAME;
    @JsonProperty("SEQNR")
    private Integer sEQNR;
    @JsonProperty("FUNCTION_ID")
    private String fUNCTIONID;
    @JsonProperty("FUNCTION_ROUTINE")
    private String fUNCTIONROUTINE;
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

    @JsonProperty("FIELD_NAME")
    public String getFIELDNAME() {
        return fIELDNAME;
    }

    @JsonProperty("FIELD_NAME")
    public void setFIELDNAME(String fIELDNAME) {
        this.fIELDNAME = fIELDNAME;
    }

    @JsonProperty("SEQNR")
    public Integer getSEQNR() {
        return sEQNR;
    }

    @JsonProperty("SEQNR")
    public void setSEQNR(Integer sEQNR) {
        this.sEQNR = sEQNR;
    }

    @JsonProperty("FUNCTION_ID")
    public String getFUNCTIONID() {
        return fUNCTIONID;
    }

    @JsonProperty("FUNCTION_ID")
    public void setFUNCTIONID(String fUNCTIONID) {
        this.fUNCTIONID = fUNCTIONID;
    }

    @JsonProperty("FUNCTION_ROUTINE")
    public String getFUNCTIONROUTINE() {
        return fUNCTIONROUTINE;
    }

    @JsonProperty("FUNCTION_ROUTINE")
    public void setFUNCTIONROUTINE(String fUNCTIONROUTINE) {
        this.fUNCTIONROUTINE = fUNCTIONROUTINE;
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
