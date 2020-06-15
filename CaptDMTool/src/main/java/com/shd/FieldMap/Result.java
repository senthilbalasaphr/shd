
package com.shd.FieldMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "FIELD_NAME",
    "FIELD_REQUIRED",
    "FIELD_KEY"
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("FIELD_NAME")
    private String fIELDNAME;
    @JsonProperty("FIELD_REQUIRED")
    private String fIELDREQUIRED;
    @JsonProperty("FIELD_KEY")
    private String fIELDKEY;

    @JsonProperty("__metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("FIELD_NAME")
    public String getFIELDNAME() {
        return fIELDNAME;
    }

    @JsonProperty("FIELD_NAME")
    public void setFIELDNAME(String fIELDNAME) {
        this.fIELDNAME = fIELDNAME;
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

}
