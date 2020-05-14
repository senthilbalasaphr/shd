
package com.shd.DepartmentLevel2;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "results"
})
public class CustEmeaDeptLevel1 {

    @JsonProperty("results")
    private List<Result_> results = null;

    @JsonProperty("results")
    public List<Result_> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Result_> results) {
        this.results = results;
    }

}
