package com.shd.reports.department;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "externalCode",
    "description_defaultValue"
})
public class Result__ {

    @JsonProperty("__metadata")
    private Metadata__ metadata;
    @JsonProperty("externalCode")
    private String externalCode;
    @JsonProperty("description_defaultValue")
    private String descriptionDefaultValue;

    @JsonProperty("__metadata")
    public Metadata__ getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata__ metadata) {
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

    @JsonProperty("description_defaultValue")
    public String getDescriptionDefaultValue() {
        return descriptionDefaultValue;
    }

    @JsonProperty("description_defaultValue")
    public void setDescriptionDefaultValue(String descriptionDefaultValue) {
        this.descriptionDefaultValue = descriptionDefaultValue;
    }

}
