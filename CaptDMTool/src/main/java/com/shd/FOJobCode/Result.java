
package com.shd.FOJobCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "externalCode",
    "startDate",
    "name_defaultValue",
    "name_localized",
    "endDate",
    "description",
    "description_defaultValue",
    "jobFunction",
    "grade",
    "name",
    "status",
    "cust_JobFunction",
    "jobFunctionNav",
    "defaultJobLevelNav",
    "gradeNav"
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("externalCode")
    private String externalCode;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("name_defaultValue")
    private String nameDefaultValue;
    @JsonProperty("name_localized")
    private String nameLocalized;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("description_defaultValue")
    private String descriptionDefaultValue;
    @JsonProperty("jobFunction")
    private String jobFunction;
    @JsonProperty("grade")
    private String grade;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("cust_JobFunction")
    private CustJobFunction custJobFunction;
    @JsonProperty("jobFunctionNav")
    private JobFunctionNav jobFunctionNav;
    @JsonProperty("defaultJobLevelNav")
    private DefaultJobLevelNav defaultJobLevelNav;
    @JsonProperty("gradeNav")
    private GradeNav gradeNav;

    @JsonProperty("__metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata metadata) {
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

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("name_defaultValue")
    public String getNameDefaultValue() {
        return nameDefaultValue;
    }

    @JsonProperty("name_defaultValue")
    public void setNameDefaultValue(String nameDefaultValue) {
        this.nameDefaultValue = nameDefaultValue;
    }

    @JsonProperty("name_localized")
    public String getNameLocalized() {
        return nameLocalized;
    }

    @JsonProperty("name_localized")
    public void setNameLocalized(String nameLocalized) {
        this.nameLocalized = nameLocalized;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("description_defaultValue")
    public String getDescriptionDefaultValue() {
        return descriptionDefaultValue;
    }

    @JsonProperty("description_defaultValue")
    public void setDescriptionDefaultValue(String descriptionDefaultValue) {
        this.descriptionDefaultValue = descriptionDefaultValue;
    }

    @JsonProperty("jobFunction")
    public String getJobFunction() {
        return jobFunction;
    }

    @JsonProperty("jobFunction")
    public void setJobFunction(String jobFunction) {
        this.jobFunction = jobFunction;
    }

    @JsonProperty("grade")
    public String getGrade() {
        return grade;
    }

    @JsonProperty("grade")
    public void setGrade(String grade) {
        this.grade = grade;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("cust_JobFunction")
    public CustJobFunction getCustJobFunction() {
        return custJobFunction;
    }

    @JsonProperty("cust_JobFunction")
    public void setCustJobFunction(CustJobFunction custJobFunction) {
        this.custJobFunction = custJobFunction;
    }

    @JsonProperty("jobFunctionNav")
    public JobFunctionNav getJobFunctionNav() {
        return jobFunctionNav;
    }

    @JsonProperty("jobFunctionNav")
    public void setJobFunctionNav(JobFunctionNav jobFunctionNav) {
        this.jobFunctionNav = jobFunctionNav;
    }

    @JsonProperty("defaultJobLevelNav")
    public DefaultJobLevelNav getDefaultJobLevelNav() {
        return defaultJobLevelNav;
    }

    @JsonProperty("defaultJobLevelNav")
    public void setDefaultJobLevelNav(DefaultJobLevelNav defaultJobLevelNav) {
        this.defaultJobLevelNav = defaultJobLevelNav;
    }

    @JsonProperty("gradeNav")
    public GradeNav getGradeNav() {
        return gradeNav;
    }

    @JsonProperty("gradeNav")
    public void setGradeNav(GradeNav gradeNav) {
        this.gradeNav = gradeNav;
    }

}
