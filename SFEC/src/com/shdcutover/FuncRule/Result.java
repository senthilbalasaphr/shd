
package com.shdcutover.FuncRule;

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
    "FUNCTION_ID",
    "FUNCTION_ROUTINE_ID",
    "CLASS",
    "METHOD"
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("Client")
    private String client;
    @JsonProperty("FUNCTION_ID")
    private String fUNCTIONID;
    @JsonProperty("FUNCTION_ROUTINE_ID")
    private String fUNCTIONROUTINEID;
    @JsonProperty("CLASS")
    private String cLASS;
    @JsonProperty("METHOD")
    private String mETHOD;
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

    @JsonProperty("FUNCTION_ID")
    public String getFUNCTIONID() {
        return fUNCTIONID;
    }

    @JsonProperty("FUNCTION_ID")
    public void setFUNCTIONID(String fUNCTIONID) {
        this.fUNCTIONID = fUNCTIONID;
    }

    @JsonProperty("FUNCTION_ROUTINE_ID")
    public String getFUNCTIONROUTINEID() {
        return fUNCTIONROUTINEID;
    }

    @JsonProperty("FUNCTION_ROUTINE_ID")
    public void setFUNCTIONROUTINEID(String fUNCTIONROUTINEID) {
        this.fUNCTIONROUTINEID = fUNCTIONROUTINEID;
    }

    @JsonProperty("CLASS")
    public String getCLASS() {
        return cLASS;
    }

    @JsonProperty("CLASS")
    public void setCLASS(String cLASS) {
        this.cLASS = cLASS;
    }

    @JsonProperty("METHOD")
    public String getMETHOD() {
        return mETHOD;
    }

    @JsonProperty("METHOD")
    public void setMETHOD(String mETHOD) {
        this.mETHOD = mETHOD;
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
