package com.shd.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.shd.DepartmentLevel1.DepartmentLevel1;
import com.shd.DepartmentLevel2.DepartmentLevel2;
import com.shd.DepartmentLevel3.DepartmentLevel3;
import com.shd.DepartmentLevel4.DepartmentLevel4;
import com.shd.DepartmentLevel5.DepartmentLevel5;
import com.shd.FODepartment.FODepartment;
import com.shd.FODivision.FODivision;
import com.shd.Util.Utility;

public class DivisionsReports {

	public static Map<String, Object[]> DepDiv = new TreeMap<String, Object[]>();
	public static Map<String, Object[]> DepLE = new TreeMap<String, Object[]>();
	
	public static  Map<String, Object[]> DivisionXlxs = new TreeMap<String, Object[]>();
	public static Map<String, Object[]> DepartmentLevel1Xlxs = new TreeMap<String, Object[]>();
	public static Map<String, Object[]> DepartmentLevel2Xlxs = new TreeMap<String, Object[]>();
	public static Map<String, Object[]> DepartmentLevel3Xlxs = new TreeMap<String, Object[]>();
	public static Map<String, Object[]> DepartmentLevel4Xlxs = new TreeMap<String, Object[]>();
	public static Map<String, Object[]> DepartmentLevel5Xlxs = new TreeMap<String, Object[]>();
	
	 
	 
	public static int i = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestTemplate restTemplate = new RestTemplate();
		Utility ut = new Utility();

		String LegalEntity = "A0800";
		String DivisionFilter = "0";
		String DepartmentLevel1Filter = "0";
		String DepartmentLevel2Filter = "0";
		String DepartmentLevel3Filter = "0";
		String DepartmentLevel4Filter = "0";
		String DepartmentLevel5Filter = "0";


		
	
		
////****** Get Divisions*******///

		String checkUrl = "https://api12preview.sapsf.eu/odata/v2/FODivision?$format=JSON" + "&$expand=cust_LegalEntity"
				+ "&$select=externalCode,startDate,endDate,name_localized, status, name, name_defaultValue, name_en_US, name_vi_VN, description_localized, description_defaultValue, lastModifiedBy, lastModifiedDateTime, createdBy, createdOn, cust_LegalEntity/externalCode"
				+ "&$filter=cust_LegalEntity/externalCode+eq+'" + LegalEntity + "'";
		// System.out.println(checkUrl);
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@9"));
		FODivision FODiv = restTemplate.getForObject(checkUrl, FODivision.class);

		com.shd.FODivision.D FODivd = FODiv.getD();
		List<com.shd.FODivision.Result> FODivResults = FODivd.getResults();
		for (com.shd.FODivision.Result FODivResult : FODivResults) {
			System.out.print("Division>" + FODivResult.getExternalCode() + " - " + FODivResult.getName());
			System.out.println(ut.getOdataEpochiToJava(FODivResult.getStartDate()));
			// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
			DivisionFilter = FODivResult.getExternalCode();
			
			
			DivisionXlxs.put(FODivResult.getExternalCode(), new Object[] {FODivResult.getExternalCode(),
																		  ut.getOdataEpochiToJava( FODivResult.getStartDate()),
																		  FODivResult.getNameLocalized(),
																		  FODivResult.getNameDefaultValue(),
																		  FODivResult.getStatus(),
																		  FODivResult.getName(),
																		  FODivResult.getNameDefaultValue(),
																		  FODivResult.getNameEnUS(),
																		  FODivResult.getDescriptionDefaultValue(),
																		  FODivResult.getDescriptionLocalized(),
																		  FODivResult.getCustLegalEntity().getResults().get(0).getExternalCode(),
																		  ut.getOdataEpochiToJava(FODivResult.getCreatedOn()),
																		  FODivResult.getCreatedBy(),
																		  ut.getOdataEpochiToJava(FODivResult.getLastModifiedDateTime()),
																		  FODivResult.getLastModifiedBy() } );
			

			
			
			
			
			

			// System.out.println(DivisionFilter);

////****** Get Department Level 1*******///

			String DL1Url = "https://api12preview.sapsf.eu/odata/v2/cust_emeaDeptLevel1?"
					+ "&$filter=cust_Division/externalCode in " + DivisionFilter
					+ "&$expand=cust_Division,cust_LegalEntity" + "&$format=JSON"
					+ "&$select=externalCode,effectiveStartDate,mdfSystemEffectiveEndDate,externalName_localized,externalName_defaultValue,externalName_en_US,mdfSystemStatus,cust_Division/externalCode,cust_LegalEntity/externalCode";

			// System.out.println(DL1Url);

			DepartmentLevel1 DepartmentLevel1 = restTemplate.getForObject(DL1Url, DepartmentLevel1.class);

			com.shd.DepartmentLevel1.D DL1d = DepartmentLevel1.getD();

			List<com.shd.DepartmentLevel1.Result> DL1results = DL1d.getResults();

			for (com.shd.DepartmentLevel1.Result DL1Result : DL1results) {
				System.out.print("Department Level 1=>" + DL1Result.getExternalCode() + " - "
						+ DL1Result.getExternalNameDefaultValue());
				System.out.println("," + ut.getOdataEpochiToJava(DL1Result.getEffectiveStartDate()));

				// Check Department
				getDepartment(DL1Result.getExternalCode(), FODivResult.getExternalCode(), LegalEntity,"1");

				// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
				DepartmentLevel1Filter = DL1Result.getExternalCode();
				
				DepartmentLevel1Xlxs.put(DL1Result.getExternalCode(), new Object[] {DL1Result.getExternalCode(),
						  ut.getOdataEpochiToJava( DL1Result.getEffectiveStartDate()),
						  DL1Result.getExternalNameLocalized(),
						  DL1Result.getExternalNameDefaultValue(),
						  DL1Result.getMdfSystemStatus(),
						  DL1Result.getExternalNameEnUS(),
						  DL1Result.getCustDivision().getResults().get(0).getExternalCode(),
						  DL1Result.getCustLegalEntity().getResults().get(0).getExternalCode(),
				 } );
				

				//// ****** Get Department Level 2*******///

				String DL2Url = "https://api12preview.sapsf.eu/odata/v2/cust_emeaDeptLevel2?"
						+ "&$filter=cust_emeaDeptLevel1/externalCode in " + DepartmentLevel1Filter
						+ "&$expand=cust_emeaDeptLevel1" + "&$format=JSON"
						+ "&$select=externalCode,effectiveStartDate,cust_department,externalName_localized,externalName_defaultValue,externalName_en_US,mdfSystemStatus,cust_emeaDeptLevel1/externalCode";

				// System.out.println(DL2Url);

				DepartmentLevel2 DepartmentLevel2 = restTemplate.getForObject(DL2Url, DepartmentLevel2.class);

				com.shd.DepartmentLevel2.D DL2d = DepartmentLevel2.getD();

				List<com.shd.DepartmentLevel2.Result> DL2results = DL2d.getResults();

				for (com.shd.DepartmentLevel2.Result DL2Result : DL2results) {
					System.out.print("Department Level 2==>" + DL2Result.getExternalCode() + " - "
							+ DL2Result.getExternalNameDefaultValue());
					System.out.println("," + ut.getOdataEpochiToJava(DL2Result.getEffectiveStartDate()));
					// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();

					// Check Department
					getDepartment(DL2Result.getExternalCode(), FODivResult.getExternalCode(), LegalEntity,"2");

					DepartmentLevel2Filter = DL2Result.getExternalCode();
					
					DepartmentLevel2Xlxs.put(DL2Result.getExternalCode(), new Object[] {DL2Result.getExternalCode(),
							  ut.getOdataEpochiToJava( DL2Result.getEffectiveStartDate()),
							  DL2Result.getExternalNameLocalized(),
							  DL2Result.getExternalNameDefaultValue(),
							  DL2Result.getMdfSystemStatus(),
							  DL2Result.getExternalNameEnUS(),
							  DL2Result.getCustEmeaDeptLevel1().getResults().get(0).getExternalCode(),
					 } );
					

					//// ****** Get Department Level 3*******///

					String DL3Url = "https://api12preview.sapsf.eu/odata/v2/cust_emeaDeptLevel3?"
							+ "&$filter=cust_emeaDeptLevel2/externalCode in " + DepartmentLevel2Filter
							+ "&$expand=cust_emeaDeptLevel2" + "&$format=JSON"
							+ "&$select=externalCode,effectiveStartDate,cust_department,externalName_localized,externalName_defaultValue,externalName_en_US,mdfSystemStatus,cust_emeaDeptLevel2/externalCode";

					DepartmentLevel3 DepartmentLevel3 = restTemplate.getForObject(DL3Url, DepartmentLevel3.class);

					com.shd.DepartmentLevel3.D DL3d = DepartmentLevel3.getD();

					List<com.shd.DepartmentLevel3.Result> DL3results = DL3d.getResults();

					for (com.shd.DepartmentLevel3.Result DL3Result : DL3results) {
						System.out.print("Department Level 3===>" + DL3Result.getExternalCode() + " - "
								+ DL3Result.getExternalNameDefaultValue());
						System.out.println("," + ut.getOdataEpochiToJava(DL3Result.getEffectiveStartDate()));

						// Check Department
						getDepartment(DL3Result.getExternalCode(), FODivResult.getExternalCode(), LegalEntity,"3");

						// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
						DepartmentLevel3Filter = DL3Result.getExternalCode();
						
						DepartmentLevel3Xlxs.put(DL3Result.getExternalCode(), new Object[] {DL3Result.getExternalCode(),
								  ut.getOdataEpochiToJava( DL3Result.getEffectiveStartDate()),
								  DL3Result.getExternalNameLocalized(),
								  DL3Result.getExternalNameDefaultValue(),
								  DL3Result.getMdfSystemStatus(),
								  DL3Result.getExternalNameEnUS(),
								  DL3Result.getCustEmeaDeptLevel2().getResults().get(0).getExternalCode(),
						 } );
						

						//// ****** Get Department Level 4*******///

						String DL4Url = "https://api12preview.sapsf.eu/odata/v2/cust_emeaDeptLevel4?"
								+ "&$filter=cust_emeaDeptLevel3/externalCode in " + DepartmentLevel3Filter
								+ "&$expand=cust_emeaDeptLevel3" + "&$format=JSON"
								+ "&$select=externalCode,effectiveStartDate,cust_department,externalName_localized,externalName_defaultValue,externalName_en_US,mdfSystemStatus,cust_emeaDeptLevel3/externalCode";

						DepartmentLevel4 DepartmentLevel4 = restTemplate.getForObject(DL4Url, DepartmentLevel4.class);

						com.shd.DepartmentLevel4.D DL4d = DepartmentLevel4.getD();

						List<com.shd.DepartmentLevel4.Result> DL4results = DL4d.getResults();

						for (com.shd.DepartmentLevel4.Result DL4Result : DL4results) {
							System.out.print("Department Level 4====>" + DL4Result.getExternalCode() + " - "
									+ DL4Result.getExternalNameDefaultValue());
							System.out.println("," + ut.getOdataEpochiToJava(DL4Result.getEffectiveStartDate()));

							// Check Department
							getDepartment(DL4Result.getExternalCode(), FODivResult.getExternalCode(), LegalEntity,"4");

							// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
							DepartmentLevel4Filter = DL4Result.getExternalCode();
							
							DepartmentLevel4Xlxs.put(DL4Result.getExternalCode(), new Object[] {DL4Result.getExternalCode(),
									  ut.getOdataEpochiToJava( DL4Result.getEffectiveStartDate()),
									  DL4Result.getExternalNameLocalized(),
									  DL4Result.getExternalNameDefaultValue(),
									  DL4Result.getMdfSystemStatus(),
									  DL4Result.getExternalNameEnUS(),
									  DL4Result.getCustEmeaDeptLevel3().getResults().get(0).getExternalCode(),
							 } );
							

							//// ****** Get Department Level 5*******///

							String DL5Url = "https://api12preview.sapsf.eu/odata/v2/cust_emeaDeptLevel5?"
									+ "&$filter=cust_emeaDeptLevel4/externalCode in " + DepartmentLevel4Filter
									+ "&$expand=cust_emeaDeptLevel4" + "&$format=JSON"
									+ "&$select=externalCode,effectiveStartDate,cust_department,externalName_localized,externalName_defaultValue,externalName_en_US,mdfSystemStatus,cust_emeaDeptLevel4/externalCode";

							DepartmentLevel5 DepartmentLevel5 = restTemplate.getForObject(DL5Url,
									DepartmentLevel5.class);

							com.shd.DepartmentLevel5.D DL5d = DepartmentLevel5.getD();

							List<com.shd.DepartmentLevel5.Result> DL5results = DL5d.getResults();

							for (com.shd.DepartmentLevel5.Result DL5Result : DL5results) {
								System.out.print("Department Level 4====>" + DL5Result.getExternalCode() + " - "
										+ DL5Result.getExternalNameDefaultValue());
								System.out.println("," + ut.getOdataEpochiToJava(DL5Result.getEffectiveStartDate()));
								// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();

								// Check Department
								getDepartment(DL5Result.getExternalCode(), FODivResult.getExternalCode(), LegalEntity,"5");

								DepartmentLevel5Filter = DL5Result.getExternalCode();
								
								
								DepartmentLevel5Xlxs.put(DL5Result.getExternalCode(), new Object[] {DL5Result.getExternalCode(),
										  ut.getOdataEpochiToJava( DL5Result.getEffectiveStartDate()),
										  DL5Result.getExternalNameLocalized(),
										  DL5Result.getExternalNameDefaultValue(),
										  DL5Result.getMdfSystemStatus(),
										  DL5Result.getExternalNameEnUS(),
										  DL5Result.getCustEmeaDeptLevel4().getResults().get(0).getExternalCode(),
								 } );
								

							} // For Department level 5

						} // For Department level 4

					} // For Department level 3

				} // For Department level 2

			} // For Department level 1

		} // For Division

// Write to Excel

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet Department_Div = workbook.createSheet(" Department_Div ");
		XSSFSheet Department_LE = workbook.createSheet(" Department_LE ");

		// Create row object
		XSSFRow row;

		// Iterate over data and write to sheet
		Set<String> keyid = DepDiv.keySet();
		int rowid = 0;

		for (String key : keyid) {
			row = Department_Div.createRow(rowid++);
			Object[] objectArr = DepDiv.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		// Iterate over data and write to sheet
		Set<String> keyidLE = DepLE.keySet();
		rowid = 0;

		for (String key : keyidLE) {
			row = Department_LE.createRow(rowid++);
			Object[] objectArr = DepLE.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		
		
		XSSFSheet Division = workbook.createSheet("Division");
		
		// Iterate over data and write to sheet
		Set<String> Divisionkeyid = DivisionXlxs.keySet();
		rowid = 0;

		for (String key : Divisionkeyid) {
			row = Division.createRow(rowid++);
			Object[] objectArr = DivisionXlxs.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		
		XSSFSheet DepartmentLevel1 = workbook.createSheet("DepartmentLevel1");
		
		// Iterate over data and write to sheet
		Set<String> DepartmentLevel1keyid = DepartmentLevel1Xlxs.keySet();
		rowid = 0;

		for (String key : DepartmentLevel1keyid) {
			row = DepartmentLevel1.createRow(rowid++);
			Object[] objectArr = DepartmentLevel1Xlxs.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		
		XSSFSheet DepartmentLevel2 = workbook.createSheet("DepartmentLevel2");
		
		// Iterate over data and write to sheet
		Set<String> DepartmentLevel2keyid = DepartmentLevel2Xlxs.keySet();
		rowid = 0;

		for (String key : DepartmentLevel2keyid) {
			row = DepartmentLevel2.createRow(rowid++);
			Object[] objectArr = DepartmentLevel2Xlxs.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		
XSSFSheet DepartmentLevel3 = workbook.createSheet("DepartmentLevel3");
		
		// Iterate over data and write to sheet
		Set<String> DepartmentLevel3keyid = DepartmentLevel3Xlxs.keySet();
		rowid = 0;

		for (String key : DepartmentLevel3keyid) {
			row = DepartmentLevel3.createRow(rowid++);
			Object[] objectArr = DepartmentLevel3Xlxs.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		
		
		
XSSFSheet DepartmentLevel4 = workbook.createSheet("DepartmentLevel4");
		
		// Iterate over data and write to sheet
		Set<String> DepartmentLevel4keyid = DepartmentLevel4Xlxs.keySet();
		rowid = 0;

		for (String key : DepartmentLevel4keyid) {
			row = DepartmentLevel4.createRow(rowid++);
			Object[] objectArr = DepartmentLevel4Xlxs.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		
		
		
XSSFSheet DepartmentLevel5 = workbook.createSheet("DepartmentLevel5");
		
		// Iterate over data and write to sheet
		Set<String> DepartmentLevel5keyid = DepartmentLevel5Xlxs.keySet();
		rowid = 0;

		for (String key : DepartmentLevel5keyid) {
			row = DepartmentLevel5.createRow(rowid++);
			Object[] objectArr = DepartmentLevel5Xlxs.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		
		
		
		

		// Write the workbook in file system
		FileOutputStream out;
		try {
			if ( System.getProperty("os.name").equalsIgnoreCase("Mac OS X")){
			out = new FileOutputStream(new File("/Users/baps/Downloads/Department.xlsx"));
			}
			else {
				out = new FileOutputStream(new File("C:\\SHD\\Reports\\Department.xlsx"));
			}
			workbook.write(out);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Department.xlsx written successfully");

	}

	public static void getDepartment(String DepID, String Div, String LE,String level) {

		Utility ut1 = new Utility();
		i++;

		RestTemplate restTemplate1 = new RestTemplate();
		restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@9"));

		String FODepUrl = "https://api12preview.sapsf.eu/odata/v2/FODepartment?" + "$filter=externalCode in " + DepID
				+ "&$expand=cust_Division,cust_LegalEntity" + "&$format=JSON"
				+ "&$select=externalCode,name, name_defaultValue, description_defaultValue, description_localized, startDate, endDate, cust_deptLevel, cust_LegalEntity/externalCode,cust_Division/externalCode";

		FODepartment FODep = restTemplate1.getForObject(FODepUrl, FODepartment.class);

		com.shd.FODepartment.D FODepD = FODep.getD();
		List<com.shd.FODepartment.Result> FODepResults = FODepD.getResults();
		for (com.shd.FODepartment.Result FODepResult : FODepResults) {
			System.out.print("Department ====>" + DepID + " - " + FODepResult.getName() + ","
					+ ut1.getOdataEpochiToJava(FODepResult.getStartDate()) + ",");

	
			String si = Integer.toString(i);
			com.shd.FODepartment.CustDivision FODepDiv = FODepResult.getCustDivision();
			com.shd.FODepartment.CustLegalEntity FODepLE = FODepResult.getCustLegalEntity();

			List<com.shd.FODepartment.Result_> FODepDivResults = FODepDiv.getResults();

			if (FODepDivResults.isEmpty()) {
				
				System.out.print("Div not found: " + Div);
				DepDiv.put(si, new Object[] { DepID, ut1.getOdataEpochiToJava(FODepResult.getStartDate()), Div, FODepResult.getDescriptionLocalized(),level });
			}

			for (com.shd.FODepartment.Result_ FODepDivResult : FODepDivResults) {
				if (FODepDivResult.getExternalCode() == "") {
					System.out.print("Div not found: " + Div);
				} else {
					System.out.print("Div  found: " + FODepDivResult.getExternalCode());
				}

			}

			List<com.shd.FODepartment.Result__> FODepLEResults = FODepLE.getResults();

			if (FODepLEResults.isEmpty()) {

				System.out.print("LE not found: " + LE);
				DepLE.put(si, new Object[] { DepID, ut1.getOdataEpochiToJava(FODepResult.getStartDate()), LE,FODepResult.getDescriptionLocalized(),level });
			}

			for (com.shd.FODepartment.Result__ FODepLEResult : FODepLEResults) {
				if (FODepLEResult.getExternalCode() == "") {

					System.out.print("LE not found: " + LE);
				}

				else {
					System.out.print(", Legal Entity found: " + FODepLEResult.getExternalCode());
				}

			}
		}

		System.out.println("");
		System.out.println("---------------");
	}

}
