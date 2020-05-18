
package com.shd.Position;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "code"
})
public class ParentPosition {

    @JsonProperty("__metadata")
    private Metadata__ metadata;
    @JsonProperty("code")
    private String code;

    @JsonProperty("__metadata")
    public Metadata__ getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata__ metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

}
