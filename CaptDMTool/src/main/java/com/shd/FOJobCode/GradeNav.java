
package com.shd.FOJobCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "name"
})
public class GradeNav {

    @JsonProperty("__metadata")
    private Metadata___ metadata;
    @JsonProperty("name")
    private String name;

    @JsonProperty("__metadata")
    public Metadata___ getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata___ metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

}
