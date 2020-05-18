package com.shd.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.shd.FODivision.FODivision;

import com.shd.Util.Utility;

public class PositionReport {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	 Map<String, Object[]> FOPosXlxs = new TreeMap<String, Object[]>();
		
		RestTemplate restTemplate = new RestTemplate();
		Utility ut = new Utility();

		String LegalEntity = "A0800";

////****** Get Divisions*******///

		String PositionUrl = "https://api12preview.sapsf.eu/odata/v2/Position?$format=JSON&" 
				+ "&$filter=company+eq+'" + LegalEntity + "'"
				+ "&$expand=positionMatrixRelationship,parentPosition"
				+ "&$select= code,externalName_defaultValue,externalName_localized,effectiveStatus,effectiveStartDate,type,cust_keyPosition,criticality,description,cust_JobFamily,cust_jobFunction,jobCode,jobLevel,employeeClass,payGrade,cust_PayGradeLevel,payRange,targetFTE,vacant, company,division,cust_deptLevel1,cust_deptLevel2,cust_deptLevel3,cust_deptLevel4,cust_deptLevel5,department,location,costCenter,\n" + 
				"cust_ProfitCenter,cust_line,cust_BrandsGroup,multipleIncumbentsAllowed,positionControlled,standardHours,changeReason,cust_BusinessCategory,cust_EmployeePayType,cust_Budgeted_Salary,cust_ParentDivision,cust_LegacyPositionID,cust_Bonus_STIP,cust_Region, positionMatrixRelationship/Position_code,  positionMatrixRelationship/matrixRelationshipType, positionMatrixRelationship/relatedPosition, parentPosition/code";
				
		// System.out.println(checkUrl);
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@9"));
		com.shd.Position.Position FOPos = restTemplate.getForObject(PositionUrl, com.shd.Position.Position.class);

		String code,
		externalName_defaultValue,
		externalName_localized,
		effectiveStatus,
		effectiveStartDate,
		type,
		cust_keyPosition,
		criticality,
		description,
		cust_JobFamily,
		cust_jobFunction,
		jobCode,
		jobLevel,
		employeeClass,
		payGrade,
		cust_PayGradeLevel,
		payRange,
		targetFTE,
		vacant,
		company,
		division,
		cust_deptLevel1,
		cust_deptLevel2,
		cust_deptLevel3,
		cust_deptLevel4,
		cust_deptLevel5,
		department,location,
		costCenter,
		cust_ProfitCenter,
		cust_line,
		cust_BrandsGroup,
		multipleIncumbentsAllowed,
		positionControlled,
		standardHours,
		changeReason,
		cust_BusinessCategory,
		cust_EmployeePayType,
		cust_Budgeted_Salary,
		cust_ParentDivision,
		cust_LegacyPositionID,
		cust_Bonus_STIP,
		cust_Region, 
		Position_code, 
		matrixRelationshipType,
		relatedPosition,
		parentPosition_code	;
		
		com.shd.Position.D FOd = FOPos.getD();

		List <com.shd.Position.Result> FOPositions = FOd.getResults();
		
		int i = 1;
		String si;
		
		si = Integer.toString(i);
		FOPosXlxs.put(si, new Object[] { "code"	,
				"externalName_defaultValue"	,
				"externalName_localized"	,
				"effectiveStatus"	,
				"effectiveStartDate"	,
				"type"	,
				"cust_keyPosition"	,
				"criticality"	,
				"description"	,
				"cust_JobFamily"	,
				"cust_jobFunction"	,
				"jobCode"	,
				"jobLevel"	,
				"employeeClass"	,
				"payGrade"	,
				"cust_PayGradeLevel"	,
				"payRange"	,
				"targetFTE"	,
				"vacant"	,
				"company"	,
				"division"	,
				"cust_deptLevel1"	,
				"cust_deptLevel2"	,
				"cust_deptLevel3"	,
				"cust_deptLevel4"	,
				"cust_deptLevel5"	,
				"department",
				"location"	,
				"costCenter"	,
				"cust_ProfitCenter"	,
				"cust_line"	,
				"cust_BrandsGroup"	,
				"multipleIncumbentsAllowed"	,
				"positionControlled"	,
				"standardHours"	,
				"changeReason"	,
				"cust_BusinessCategory"	,
				"cust_EmployeePayType"	,
				"cust_Budgeted_Salary"	,
				"cust_ParentDivision"	,
				"cust_LegacyPositionID"	,
				"cust_Bonus_STIP"	,
				"cust_Region "	,
				"parentPosition_code "	
		}
		);
		
		
		
		
		
		
		
		
		
		
		for (com.shd.Position.Result FOPosition : FOPositions) {
			System.out.println("Position>" + FOPosition.getCode() + " - " + FOPosition.getExternalNameDefaultValue());
			i = i  + 1;
			si = Integer.toString(i);

			code = FOPosition.getCode();
			externalName_defaultValue = FOPosition.getExternalNameDefaultValue();
			externalName_localized = FOPosition.getExternalNameLocalized();
			effectiveStatus = FOPosition.getEffectiveStatus();
			effectiveStartDate = ut.getOdataEpochiToJava( FOPosition.getEffectiveStartDate());
			type = FOPosition.getType();
			cust_keyPosition = FOPosition.getCustKeyPosition();
			criticality = FOPosition.getCriticality();
			description = FOPosition.getDescription();
			cust_JobFamily = FOPosition.getCustJobFamily();
			cust_jobFunction = FOPosition.getCustJobFunction();
			jobCode = FOPosition.getJobCode();
			jobLevel = FOPosition.getJobLevel();
			employeeClass =FOPosition.getEmployeeClass();
			payGrade = FOPosition.getPayGrade();
			cust_PayGradeLevel =FOPosition.getCustPayGradeLevel();
			payRange = FOPosition.getPayRange();
			targetFTE  =FOPosition.getTargetFTE();
			vacant = FOPosition.getVacant().toString();
			company = FOPosition.getCompany();
			division = FOPosition.getDivision();
			cust_deptLevel1 = FOPosition.getCustDeptLevel1();
			cust_deptLevel2 = FOPosition.getCustDeptLevel2();
			cust_deptLevel3 = FOPosition.getCustDeptLevel3();
			cust_deptLevel4 = FOPosition.getCustDeptLevel4();
			cust_deptLevel5 = FOPosition.getCustDeptLevel5();
			department = FOPosition.getDepartment();
			location = FOPosition.getLocation();
			costCenter =FOPosition.getCostCenter();
			cust_ProfitCenter = FOPosition.getCustProfitCenter();
			cust_line = FOPosition.getCustLine();
			cust_BrandsGroup = FOPosition.getCustBrandsGroup();
			multipleIncumbentsAllowed = FOPosition.getMultipleIncumbentsAllowed().toString();
			positionControlled =FOPosition.getPositionControlled().toString();
			standardHours = FOPosition.getStandardHours();
			changeReason = FOPosition.getChangeReason();
			cust_BusinessCategory = FOPosition.getCustBusinessCategory();
			cust_EmployeePayType = FOPosition.getCustEmployeePayType();
			cust_Budgeted_Salary = FOPosition.getCustBudgetedSalary();
			cust_ParentDivision = FOPosition.getCustParentDivision();
			cust_LegacyPositionID = FOPosition.getCustLegacyPositionID();
			cust_Bonus_STIP = FOPosition.getCustBonusSTIP();
			cust_Region = FOPosition.getCustRegion();
			
			
			parentPosition_code="";
			//Position_code = FOPosition.getpo
			//matrixRelationshipType,
			//relatedPosition,
			 com.shd.Position.ParentPosition pp =	FOPosition.getParentPosition();
			 if ( pp != null  ) {
			  parentPosition_code = pp.getCode();
			 }
			
			
			
			
			
			FOPosXlxs.put(si, new Object[] { 
					
					code,
					externalName_defaultValue,
					externalName_localized,
					effectiveStatus,
					effectiveStartDate,
					type,
					cust_keyPosition,
					criticality,
					description,
					cust_JobFamily,
					cust_jobFunction,
					jobCode,
					jobLevel,
					employeeClass,
					payGrade,
					cust_PayGradeLevel,
					payRange,
					targetFTE,
					vacant,
					company,
					division,
					cust_deptLevel1,
					cust_deptLevel2,
					cust_deptLevel3,
					cust_deptLevel4,
					cust_deptLevel5,
					department,location,
					costCenter,
					cust_ProfitCenter,
					cust_line,
					cust_BrandsGroup,
					multipleIncumbentsAllowed,
					positionControlled,
					standardHours,
					changeReason,
					cust_BusinessCategory,
					cust_EmployeePayType,
					cust_Budgeted_Salary,
					cust_ParentDivision,
					cust_LegacyPositionID,
					cust_Bonus_STIP,
					cust_Region, 
				//	positionMatrixRelationship/Position_code, 
				//	positionMatrixRelationship/matrixRelationshipType,
				//	positionMatrixRelationship/relatedPosition,
					parentPosition_code		
					
					
					
			
			
			});
		
		}
		
		writeexcel(FOPosXlxs,"/Users/baps/Downloads/","Position.xlsx","Position");

	}
	
	
public static void writeexcel( Map<String, Object[]> Xlxs,String path, String FileName,String SheetName ) {
	
	
	// Create blank workbook
	XSSFWorkbook workbook = new XSSFWorkbook();

	// Create a blank sheet
	XSSFSheet Sheet = workbook.createSheet(SheetName);


	// Create row object
	XSSFRow row;

	// Iterate over data and write to sheet
	Set<String> keyid = Xlxs.keySet();
	int rowid = 0;

	for (String key : keyid) {
		row = Sheet.createRow(rowid++);
		Object[] objectArr = Xlxs.get(key);
		int cellid = 0;

		for (Object obj : objectArr) {
			Cell cell = row.createCell(cellid++);
			cell.setCellValue((String) obj);
		}
	}

	// Write the workbook in file system
	FileOutputStream out;
	try {
		out = new FileOutputStream(new File(path+FileName));

		workbook.write(out);
		out.close();

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		workbook.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	System.out.println(FileName+" written successfully");

	

}

}
