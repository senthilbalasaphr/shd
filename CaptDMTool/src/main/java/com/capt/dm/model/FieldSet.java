
package com.capt.dm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "d"
})
public class FieldSet {

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(d).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FieldSet) == false) {
            return false;
        }
        FieldSet rhs = ((FieldSet) other);
        return new EqualsBuilder().append(d, rhs.d).isEquals();
    }

}
