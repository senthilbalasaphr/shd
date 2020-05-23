package com.shd.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.shd.keymap.D;
import com.shd.keymap.KeyMap;
import com.shd.keymap.Result;

public class PositionReport {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	 Map<String, Object[]> FOPosXlxs = new TreeMap<String, Object[]>();
		
		RestTemplate restTemplate = new RestTemplate();
		Utility ut = new Utility();
		Map< String,String> m1=new HashMap< String,String>();

//		   For example, if the URI uses $top=50, $skip=20, the response is a subset from numbers 21 to number 70 of the whole result set.
//
//				   EmployeeTime with $top and $skip tokens example:  
//
//				      For example, to fetch the second page with 1000 records:
//
//				      https://api2.successfactors.eu/odata/v2/EmployeeTime?$select=externalCode,userId,startDate,endDate,cust_StartTime,cust_EndTime,approvalStatus&$filter=lastModifiedDateTime%20ge%20datetimeoffset%XXXXXX-XX-XXTXX:XX:XXZ%XX&$top=1000&$skip=1000
				    	  

		


////****** Get Divisions*******///
		
		
//		get position count
		
//		String PositionUrlCount = "https://api12preview.sapsf.eu/odata/v2/Position/$count?$format=JSON&" 
//								+ "&$filter=createdBy+eq+'VKUMAR'&fromDate=1900-12-31&toDate=9999-12-31";
		
		
		String PositionUrlCount = "https://api12preview.sapsf.eu/odata/v2/Position/$count?$format=JSON&" 
				+ "&$filter=company+eq+'A1000'&fromDate=1900-12-31&toDate=9999-12-31";
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@9"));
		
		String count = restTemplate.getForObject(PositionUrlCount, String.class);
		System.out.println(count);


	
		 List<com.shd.Position.Position> ListFORes= getPosList(count);
		


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
		multipleIncumbentsAllowed="",
		positionControlled="",
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
		KeyMapexternalCode,
		cust_SFID,
		cust_LegacyID,
		
		parentPosition_code,
		createdDateTime,
		createdBy,
		lastModifiedDateTime,
		lastModifiedBy;
		
		
		List <com.shd.Position.Result> FOPositions =null;
	
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
				"parentPosition_code ",
				"createdDateTime",
				"createdBy",
				"lastModifiedDateTime",
				"lastModifiedBy",
				"KeyMapexternalCode",
				"cust_SFID",
				"cust_LegacyID"
				
				
				
		}
		);
		
		
		for(com.shd.Position.Position ListFOPosRes: ListFORes) {
			com.shd.Position.D FOd = ListFOPosRes.getD();
			
			FOPositions= FOd.getResults();
		

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
			multipleIncumbentsAllowed="";
			if (FOPosition.getMultipleIncumbentsAllowed() != null) {
				multipleIncumbentsAllowed = FOPosition.getMultipleIncumbentsAllowed().toString();
			}
			positionControlled="";
			if (FOPosition.getPositionControlled() != null) {
			positionControlled =FOPosition.getPositionControlled().toString();
			}
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
			
			 createdDateTime = ut.getOdataEpochiToJava(FOPosition.getCreatedDateTime());
			 createdBy = FOPosition.getCreatedBy();
			 lastModifiedDateTime = ut.getOdataEpochiToJava(FOPosition.getLastModifiedDateTime());
			 lastModifiedBy = FOPosition.getLastModifiedBy();
			 
			 
			 m1=null;
			 KeyMapexternalCode="";
			 cust_SFID="";
			 cust_LegacyID="";
			 m1 = getPosKeyMap(code);
			 if(m1 != null) {
				 KeyMapexternalCode = m1.get("externalCode");
				 cust_SFID = m1.get("cust_SFID");
				 cust_LegacyID = m1.get("cust_LegacyID");
				 
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
					parentPosition_code,
					createdDateTime, 
					createdBy,
					lastModifiedDateTime,
					lastModifiedBy	,
					KeyMapexternalCode,
					cust_SFID,
					cust_LegacyID
			
			
			});
		
		} 
		}
		
		if ( System.getProperty("os.name").equalsIgnoreCase("Mac OS X")){
			writeexcel(FOPosXlxs,"/Users/baps/Downloads/","Position.xlsx","Position");
		}
		else
		{
			writeexcel(FOPosXlxs,"C:\\SHD\\Reports\\","Position.xlsx","Position");

		}

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



public static List<com.shd.Position.Position> getPosList(String count) {
	List<com.shd.Position.Position> ListPosRes= new ArrayList<com.shd.Position.Position>();
	String PositionUrl = null;
	int t=1000;
	long s=0;
	long x=0;
	long c = Long.parseLong(count);
	
	RestTemplate restTemplate1 = new RestTemplate();
	restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@9"));
	
	while (c>s) {
	PositionUrl = "https://api12preview.sapsf.eu/odata/v2/Position?$format=JSON&"
			+ "&$filter=company+eq+'A1000'&fromDate=1900-12-31&toDate=9999-12-31&$top="+t+"&$skip="+s+""
		//	+ "&$filter=createdBy+eq+'VKUMAR' and company+eq+'" + LegalEntity + "'&fromDate=1900-12-31&toDate=9999-12-31"
			+ "&$expand=positionMatrixRelationship,parentPosition"
			+ "&$select= code,externalName_defaultValue,externalName_localized,effectiveStatus,effectiveStartDate,type,cust_keyPosition,criticality,description,cust_JobFamily,cust_jobFunction,jobCode,jobLevel,employeeClass,payGrade,cust_PayGradeLevel,payRange,targetFTE,vacant, company,division,cust_deptLevel1,cust_deptLevel2,cust_deptLevel3,cust_deptLevel4,cust_deptLevel5,department,location,costCenter,\n" + 
			"cust_ProfitCenter,cust_line,cust_BrandsGroup,multipleIncumbentsAllowed,positionControlled,standardHours,changeReason,cust_BusinessCategory,cust_EmployeePayType,cust_Budgeted_Salary,cust_ParentDivision,cust_LegacyPositionID,cust_Bonus_STIP,cust_Region, positionMatrixRelationship/Position_code,  positionMatrixRelationship/matrixRelationshipType, positionMatrixRelationship/relatedPosition, parentPosition/code,createdDateTime, createdBy, lastModifiedDateTime, lastModifiedBy";
			
	
		com.shd.Position.Position FOPos = restTemplate1.getForObject(PositionUrl, com.shd.Position.Position.class);
		ListPosRes.add(FOPos);

		System.out.println(PositionUrl);
		s=s+t;
	}
	
	return ListPosRes;
	
}

public static Map< String,String> getPosKeyMap(String Position) {
	Map< String,String> m1 =  new HashMap< String,String>();
	RestTemplate restTemplate1 = new RestTemplate();
	
	String company = "06" ;
	String url1 = "https://api12preview.sapsf.eu/odata/v2/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '09' and cust_SFID eq '"+Position+"'&$select=cust_SFID,cust_LegacyID,externalCode";


	restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@9"));


	KeyMap result1 = restTemplate1.getForObject(url1,KeyMap.class);

	if ( result1 != null) {
		D d1 = result1.getD();
		List<Result> res1 = d1.getResults();
		for (Result result2 :res1) {
			m1.put("externalCode", result2.getExternalCode());
			m1.put("cust_SFID", result2.getCustSFID());
			m1.put("cust_LegacyID", result2.getCust_LegacyID());
			
		}
	
}
return m1;
}
}
