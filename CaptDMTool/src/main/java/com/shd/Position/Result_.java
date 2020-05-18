
package com.shd.Position;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "Position_code",
    "matrixRelationshipType",
    "relatedPosition"
})
public class Result_ {

    @JsonProperty("__metadata")
    private Metadata_ metadata;
    @JsonProperty("Position_code")
    private String positionCode;
    @JsonProperty("matrixRelationshipType")
    private String matrixRelationshipType;
    @JsonProperty("relatedPosition")
    private String relatedPosition;

    @JsonProperty("__metadata")
    public Metadata_ getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata_ metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("Position_code")
    public String getPositionCode() {
        return positionCode;
    }

    @JsonProperty("Position_code")
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @JsonProperty("matrixRelationshipType")
    public String getMatrixRelationshipType() {
        return matrixRelationshipType;
    }

    @JsonProperty("matrixRelationshipType")
    public void setMatrixRelationshipType(String matrixRelationshipType) {
        this.matrixRelationshipType = matrixRelationshipType;
    }

    @JsonProperty("relatedPosition")
    public String getRelatedPosition() {
        return relatedPosition;
    }

    @JsonProperty("relatedPosition")
    public void setRelatedPosition(String relatedPosition) {
        this.relatedPosition = relatedPosition;
    }

}
