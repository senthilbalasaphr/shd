
package com.shd.DepartmentLevel3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "d"
})
public class DepartmentLevel3 {

    @JsonProperty("d")
    private D d;

    @JsonProperty("d")
    public D getD() {
        return d;
    }

    @JsonProperty("d")
    public void setD(D d) {
        this.d = d;
    }

}
