
package com.shd.FOJobCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__deferred"
})
public class CustJobFunction {

    @JsonProperty("__deferred")
    private Deferred deferred;

    @JsonProperty("__deferred")
    public Deferred getDeferred() {
        return deferred;
    }

    @JsonProperty("__deferred")
    public void setDeferred(Deferred deferred) {
        this.deferred = deferred;
    }

}
