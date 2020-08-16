
package com.shdcutover.Template;

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
    "DESCRIPTION",
    "ACTIVE"
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
    @JsonProperty("DESCRIPTION")
    private String dESCRIPTION;
    @JsonProperty("ACTIVE")
    private String aCTIVE;
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

    @JsonProperty("DESCRIPTION")
    public String getDESCRIPTION() {
        return dESCRIPTION;
    }

    @JsonProperty("DESCRIPTION")
    public void setDESCRIPTION(String dESCRIPTION) {
        this.dESCRIPTION = dESCRIPTION;
    }

    @JsonProperty("ACTIVE")
    public String getACTIVE() {
        return aCTIVE;
    }

    @JsonProperty("ACTIVE")
    public void setACTIVE(String aCTIVE) {
        this.aCTIVE = aCTIVE;
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
