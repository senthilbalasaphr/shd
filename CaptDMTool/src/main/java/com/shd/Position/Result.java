
package com.shd.Position;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "code",
    "effectiveStartDate",
    "cust_LegacyPositionID",
    "cust_BrandsGroup",
    "cust_EmployeePayType",
    "criticality",
    "externalName_defaultValue",
    "description",
    "jobCode",
    "cust_jobFunction",
    "type",
    "cust_line",
    "cust_ProfitCenter",
    "cust_keyPosition",
    "positionControlled",
    "division",
    "cust_PayGradeLevel",
    "cust_Bonus_STIP",
    "cust_deptLevel2",
    "cust_deptLevel1",
    "cust_JobFamily",
    "cust_ParentDivision",
    "payGrade",
    "cust_Budgeted_Salary",
    "company",
    "department",
    "cust_BusinessCategory",
    "payRange",
    "employeeClass",
    "targetFTE",
    "changeReason",
    "cust_Region",
    "costCenter",
    "standardHours",
    "externalName_localized",
    "cust_deptLevel4",
    "cust_deptLevel3",
    "jobLevel",
    "vacant",
    "effectiveStatus",
    "cust_deptLevel5",
    "location",
    "multipleIncumbentsAllowed",
    "positionMatrixRelationship",
    "parentPosition",
    "createdDateTime",
    "createdBy",
    "lastModifiedDateTime",
    "lastModifiedBy" ,
    "effectiveEndDate"
    
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("code")
    private String code;
    @JsonProperty("effectiveStartDate")
    private String effectiveStartDate;
    @JsonProperty("cust_LegacyPositionID")
    private String custLegacyPositionID;
    @JsonProperty("cust_BrandsGroup")
    private String custBrandsGroup;
    @JsonProperty("cust_EmployeePayType")
    private String custEmployeePayType;
    @JsonProperty("criticality")
    private String criticality;
    @JsonProperty("externalName_defaultValue")
    private String externalNameDefaultValue;
    @JsonProperty("description")
    private String description;
    @JsonProperty("jobCode")
    private String jobCode;
    @JsonProperty("cust_jobFunction")
    private String custJobFunction;
    @JsonProperty("type")
    private String type;
    @JsonProperty("cust_line")
    private String custLine;
    @JsonProperty("cust_ProfitCenter")
    private String custProfitCenter;
    @JsonProperty("cust_keyPosition")
    private String custKeyPosition;
    @JsonProperty("positionControlled")
    private Boolean positionControlled;
    @JsonProperty("division")
    private String division;
    @JsonProperty("cust_PayGradeLevel")
    private String custPayGradeLevel;
    @JsonProperty("cust_Bonus_STIP")
    private String custBonusSTIP;
    @JsonProperty("cust_deptLevel2")
    private String custDeptLevel2;
    @JsonProperty("cust_deptLevel1")
    private String custDeptLevel1;
    @JsonProperty("cust_JobFamily")
    private String custJobFamily;
    @JsonProperty("cust_ParentDivision")
    private String custParentDivision;
    @JsonProperty("payGrade")
    private String payGrade;
    @JsonProperty("cust_Budgeted_Salary")
    private String custBudgetedSalary;
    @JsonProperty("company")
    private String company;
    @JsonProperty("department")
    private String department;
    @JsonProperty("cust_BusinessCategory")
    private String custBusinessCategory;
    @JsonProperty("payRange")
    private String payRange;
    @JsonProperty("employeeClass")
    private String employeeClass;
    @JsonProperty("targetFTE")
    private String targetFTE;
    @JsonProperty("changeReason")
    private String changeReason;
    @JsonProperty("cust_Region")
    private String custRegion;
    @JsonProperty("costCenter")
    private String costCenter;
    @JsonProperty("standardHours")
    private String standardHours;
    @JsonProperty("externalName_localized")
    private String externalNameLocalized;
    @JsonProperty("cust_deptLevel4")
    private String custDeptLevel4;
    @JsonProperty("cust_deptLevel3")
    private String custDeptLevel3;
    @JsonProperty("jobLevel")
    private String jobLevel;
    @JsonProperty("vacant")
    private Boolean vacant;
    @JsonProperty("effectiveStatus")
    private String effectiveStatus;
    @JsonProperty("cust_deptLevel5")
    private String custDeptLevel5;
    @JsonProperty("location")
    private String location;
    @JsonProperty("multipleIncumbentsAllowed")
    private Boolean multipleIncumbentsAllowed;
    @JsonProperty("positionMatrixRelationship")
    private PositionMatrixRelationship positionMatrixRelationship;
    @JsonProperty("parentPosition")
    private ParentPosition parentPosition;
    @JsonProperty("effectiveEndDate")
    private String effectiveEndDate;
    
    public String getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	private String createdDateTime;
    public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}

	public void setLastModifiedDateTime(String lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	private String createdBy;
    private String lastModifiedDateTime;
    private String lastModifiedBy;

    @JsonProperty("__metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata metadata) {
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

    @JsonProperty("effectiveStartDate")
    public String getEffectiveStartDate() {
        return effectiveStartDate;
    }

    @JsonProperty("effectiveStartDate")
    public void setEffectiveStartDate(String effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    @JsonProperty("cust_LegacyPositionID")
    public String getCustLegacyPositionID() {
        return custLegacyPositionID;
    }

    @JsonProperty("cust_LegacyPositionID")
    public void setCustLegacyPositionID(String custLegacyPositionID) {
        this.custLegacyPositionID = custLegacyPositionID;
    }

    @JsonProperty("cust_BrandsGroup")
    public String getCustBrandsGroup() {
        return custBrandsGroup;
    }

    @JsonProperty("cust_BrandsGroup")
    public void setCustBrandsGroup(String custBrandsGroup) {
        this.custBrandsGroup = custBrandsGroup;
    }

    @JsonProperty("cust_EmployeePayType")
    public String getCustEmployeePayType() {
        return custEmployeePayType;
    }

    @JsonProperty("cust_EmployeePayType")
    public void setCustEmployeePayType(String custEmployeePayType) {
        this.custEmployeePayType = custEmployeePayType;
    }

    @JsonProperty("criticality")
    public String getCriticality() {
        return criticality;
    }

    @JsonProperty("criticality")
    public void setCriticality(String criticality) {
        this.criticality = criticality;
    }

    @JsonProperty("externalName_defaultValue")
    public String getExternalNameDefaultValue() {
        return externalNameDefaultValue;
    }

    @JsonProperty("externalName_defaultValue")
    public void setExternalNameDefaultValue(String externalNameDefaultValue) {
        this.externalNameDefaultValue = externalNameDefaultValue;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("jobCode")
    public String getJobCode() {
        return jobCode;
    }

    @JsonProperty("jobCode")
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    @JsonProperty("cust_jobFunction")
    public String getCustJobFunction() {
        return custJobFunction;
    }

    @JsonProperty("cust_jobFunction")
    public void setCustJobFunction(String custJobFunction) {
        this.custJobFunction = custJobFunction;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("cust_line")
    public String getCustLine() {
        return custLine;
    }

    @JsonProperty("cust_line")
    public void setCustLine(String custLine) {
        this.custLine = custLine;
    }

    @JsonProperty("cust_ProfitCenter")
    public String getCustProfitCenter() {
        return custProfitCenter;
    }

    @JsonProperty("cust_ProfitCenter")
    public void setCustProfitCenter(String custProfitCenter) {
        this.custProfitCenter = custProfitCenter;
    }

    @JsonProperty("cust_keyPosition")
    public String getCustKeyPosition() {
        return custKeyPosition;
    }

    @JsonProperty("cust_keyPosition")
    public void setCustKeyPosition(String custKeyPosition) {
        this.custKeyPosition = custKeyPosition;
    }

    @JsonProperty("positionControlled")
    public Boolean getPositionControlled() {
        return positionControlled;
    }

    @JsonProperty("positionControlled")
    public void setPositionControlled(Boolean positionControlled) {
        this.positionControlled = positionControlled;
    }

    @JsonProperty("division")
    public String getDivision() {
        return division;
    }

    @JsonProperty("division")
    public void setDivision(String division) {
        this.division = division;
    }

    @JsonProperty("cust_PayGradeLevel")
    public String getCustPayGradeLevel() {
        return custPayGradeLevel;
    }

    @JsonProperty("cust_PayGradeLevel")
    public void setCustPayGradeLevel(String custPayGradeLevel) {
        this.custPayGradeLevel = custPayGradeLevel;
    }

    @JsonProperty("cust_Bonus_STIP")
    public String getCustBonusSTIP() {
        return custBonusSTIP;
    }

    @JsonProperty("cust_Bonus_STIP")
    public void setCustBonusSTIP(String custBonusSTIP) {
        this.custBonusSTIP = custBonusSTIP;
    }

    @JsonProperty("cust_deptLevel2")
    public String getCustDeptLevel2() {
        return custDeptLevel2;
    }

    @JsonProperty("cust_deptLevel2")
    public void setCustDeptLevel2(String custDeptLevel2) {
        this.custDeptLevel2 = custDeptLevel2;
    }

    @JsonProperty("cust_deptLevel1")
    public String getCustDeptLevel1() {
        return custDeptLevel1;
    }

    @JsonProperty("cust_deptLevel1")
    public void setCustDeptLevel1(String custDeptLevel1) {
        this.custDeptLevel1 = custDeptLevel1;
    }

    @JsonProperty("cust_JobFamily")
    public String getCustJobFamily() {
        return custJobFamily;
    }

    @JsonProperty("cust_JobFamily")
    public void setCustJobFamily(String custJobFamily) {
        this.custJobFamily = custJobFamily;
    }

    @JsonProperty("cust_ParentDivision")
    public String getCustParentDivision() {
        return custParentDivision;
    }

    @JsonProperty("cust_ParentDivision")
    public void setCustParentDivision(String custParentDivision) {
        this.custParentDivision = custParentDivision;
    }

    @JsonProperty("payGrade")
    public String getPayGrade() {
        return payGrade;
    }

    @JsonProperty("payGrade")
    public void setPayGrade(String payGrade) {
        this.payGrade = payGrade;
    }

    @JsonProperty("cust_Budgeted_Salary")
    public String getCustBudgetedSalary() {
        return custBudgetedSalary;
    }

    @JsonProperty("cust_Budgeted_Salary")
    public void setCustBudgetedSalary(String custBudgetedSalary) {
        this.custBudgetedSalary = custBudgetedSalary;
    }

    @JsonProperty("company")
    public String getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(String company) {
        this.company = company;
    }

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    @JsonProperty("department")
    public void setDepartment(String department) {
        this.department = department;
    }

    @JsonProperty("cust_BusinessCategory")
    public String getCustBusinessCategory() {
        return custBusinessCategory;
    }

    @JsonProperty("cust_BusinessCategory")
    public void setCustBusinessCategory(String custBusinessCategory) {
        this.custBusinessCategory = custBusinessCategory;
    }

    @JsonProperty("payRange")
    public String getPayRange() {
        return payRange;
    }

    @JsonProperty("payRange")
    public void setPayRange(String payRange) {
        this.payRange = payRange;
    }

    @JsonProperty("employeeClass")
    public String getEmployeeClass() {
        return employeeClass;
    }

    @JsonProperty("employeeClass")
    public void setEmployeeClass(String employeeClass) {
        this.employeeClass = employeeClass;
    }

    @JsonProperty("targetFTE")
    public String getTargetFTE() {
        return targetFTE;
    }

    @JsonProperty("targetFTE")
    public void setTargetFTE(String targetFTE) {
        this.targetFTE = targetFTE;
    }

    @JsonProperty("changeReason")
    public String getChangeReason() {
        return changeReason;
    }

    @JsonProperty("changeReason")
    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    @JsonProperty("cust_Region")
    public String getCustRegion() {
        return custRegion;
    }

    @JsonProperty("cust_Region")
    public void setCustRegion(String custRegion) {
        this.custRegion = custRegion;
    }

    @JsonProperty("costCenter")
    public String getCostCenter() {
        return costCenter;
    }

    @JsonProperty("costCenter")
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    @JsonProperty("standardHours")
    public String getStandardHours() {
        return standardHours;
    }

    @JsonProperty("standardHours")
    public void setStandardHours(String standardHours) {
        this.standardHours = standardHours;
    }

    @JsonProperty("externalName_localized")
    public String getExternalNameLocalized() {
        return externalNameLocalized;
    }

    @JsonProperty("externalName_localized")
    public void setExternalNameLocalized(String externalNameLocalized) {
        this.externalNameLocalized = externalNameLocalized;
    }

    @JsonProperty("cust_deptLevel4")
    public String getCustDeptLevel4() {
        return custDeptLevel4;
    }

    @JsonProperty("cust_deptLevel4")
    public void setCustDeptLevel4(String custDeptLevel4) {
        this.custDeptLevel4 = custDeptLevel4;
    }

    @JsonProperty("cust_deptLevel3")
    public String getCustDeptLevel3() {
        return custDeptLevel3;
    }

    @JsonProperty("cust_deptLevel3")
    public void setCustDeptLevel3(String custDeptLevel3) {
        this.custDeptLevel3 = custDeptLevel3;
    }

    @JsonProperty("jobLevel")
    public String getJobLevel() {
        return jobLevel;
    }

    @JsonProperty("jobLevel")
    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    @JsonProperty("vacant")
    public Boolean getVacant() {
        return vacant;
    }

    @JsonProperty("vacant")
    public void setVacant(Boolean vacant) {
        this.vacant = vacant;
    }

    @JsonProperty("effectiveStatus")
    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    @JsonProperty("effectiveStatus")
    public void setEffectiveStatus(String effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    @JsonProperty("cust_deptLevel5")
    public String getCustDeptLevel5() {
        return custDeptLevel5;
    }

    @JsonProperty("cust_deptLevel5")
    public void setCustDeptLevel5(String custDeptLevel5) {
        this.custDeptLevel5 = custDeptLevel5;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("multipleIncumbentsAllowed")
    public Boolean getMultipleIncumbentsAllowed() {
        return multipleIncumbentsAllowed;
    }

    @JsonProperty("multipleIncumbentsAllowed")
    public void setMultipleIncumbentsAllowed(Boolean multipleIncumbentsAllowed) {
        this.multipleIncumbentsAllowed = multipleIncumbentsAllowed;
    }

    @JsonProperty("positionMatrixRelationship")
    public PositionMatrixRelationship getPositionMatrixRelationship() {
        return positionMatrixRelationship;
    }

    @JsonProperty("positionMatrixRelationship")
    public void setPositionMatrixRelationship(PositionMatrixRelationship positionMatrixRelationship) {
        this.positionMatrixRelationship = positionMatrixRelationship;
    }

    @JsonProperty("parentPosition")
    public ParentPosition getParentPosition() {
        return parentPosition;
    }

    @JsonProperty("parentPosition")
    public void setParentPosition(ParentPosition parentPosition) {
        this.parentPosition = parentPosition;
    }

}
