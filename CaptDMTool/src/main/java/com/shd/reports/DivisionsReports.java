package com.shd.reports;

import java.util.List;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.shd.DepartmentLevel1.DepartmentLevel1;
import com.shd.DepartmentLevel2.DepartmentLevel2;
import com.shd.DepartmentLevel3.DepartmentLevel3;
import com.shd.DepartmentLevel4.DepartmentLevel4;
import com.shd.DepartmentLevel5.DepartmentLevel5;
import com.shd.FODivision.FODivision;
import com.shd.Util.Utility;

public class DivisionsReports {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestTemplate restTemplate = new RestTemplate();
		Utility ut = new Utility();

		String LegalEntity = "A1400";
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
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
		FODivision FODiv = restTemplate.getForObject(checkUrl, FODivision.class);

		com.shd.FODivision.D FODivd = FODiv.getD();
		List<com.shd.FODivision.Result> FODivResults = FODivd.getResults();
		for (com.shd.FODivision.Result FODivResult : FODivResults) {
			System.out.print("Division>" + FODivResult.getExternalCode() + " - " + FODivResult.getName());
			System.out.println(ut.getOdataEpochiToJava(FODivResult.getStartDate()));
			// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
			DivisionFilter = FODivResult.getExternalCode();

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

				// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
				DepartmentLevel1Filter = DL1Result.getExternalCode();

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
					DepartmentLevel2Filter = DL2Result.getExternalCode();

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
						// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
						DepartmentLevel3Filter = DL3Result.getExternalCode();

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
							// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
							DepartmentLevel4Filter = DL4Result.getExternalCode();

							//// ****** Get Department Level 5*******///

							String DL5Url = "https://api12preview.sapsf.eu/odata/v2/cust_emeaDeptLevel5?"
									+ "&$filter=cust_emeaDeptLevel4/externalCode in " + DepartmentLevel4Filter
									+ "&$expand=cust_emeaDeptLevel4" + "&$format=JSON"
									+ "&$select=externalCode,effectiveStartDate,cust_department,externalName_localized,externalName_defaultValue,externalName_en_US,mdfSystemStatus,cust_emeaDeptLevel4/externalCode";

							DepartmentLevel5 DepartmentLevel5 = restTemplate.getForObject(DL4Url,
									DepartmentLevel5.class);

							com.shd.DepartmentLevel5.D DL5d = DepartmentLevel5.getD();

							List<com.shd.DepartmentLevel5.Result> DL5results = DL5d.getResults();

							for (com.shd.DepartmentLevel5.Result DL5Result : DL5results) {
								System.out.print("Department Level 4====>" + DL5Result.getExternalCode() + " - "
										+ DL5Result.getExternalNameDefaultValue());
								System.out.println("," + ut.getOdataEpochiToJava(DL5Result.getEffectiveStartDate()));
								// DivisionFilter = DivisionFilter +","+ FODivResult.getExternalCode();
								DepartmentLevel5Filter = DL5Result.getExternalCode();

							} // For Department level 5

						} // For Department level 4

					} // For Department level 3

				} // For Department level 2

			} // For Department level 1

		} // For Division

		

	}

}
