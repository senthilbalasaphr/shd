
package com.shd.DepartmentLevel1;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "results"
})
public class CustLegalEntity {

    @JsonProperty("results")
    private List<Result__> results = null;

    @JsonProperty("results")
    public List<Result__> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Result__> results) {
        this.results = results;
    }

}
