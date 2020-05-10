package com.shd.reports;

import java.util.List;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.capt.dm.model.FieldSet;
import com.shd.reports.department.CustDivision;
import com.shd.reports.department.CustLegalEntity;
import com.shd.reports.department.Department;
import com.shd.reports.department.Result;
import com.shd.reports.department.Result_;
import com.shd.reports.department.Result__;

public class Departments {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String Code="60000990,60000991,60000992,60000993";
		RestTemplate restTemplate = new RestTemplate();
		String checkUrl = "https://api12preview.sapsf.eu/odata/v2/FODepartment?$format=JSON&$expand=cust_Division,cust_LegalEntity&$filter=externalCode+in+"+Code+"&$select=externalCode,startDate,endDate,createdOn,createdBy,lastModifiedOn,lastModifiedBy,name,cust_LegalEntity/externalCode,cust_LegalEntity/description_defaultValue,cust_Division/externalCode,cust_Division/name";
		System.out.println(checkUrl);
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1","Welcome@3"));
		Department depObject = restTemplate.getForObject(checkUrl, Department.class);
		
		List<Result> results = depObject.getD().getResults();
		for (Result result1 :results) {
			
			
			System.out.println(result1.getExternalCode());
			System.out.println(result1.getName());
			
			CustLegalEntity LE = result1.getCustLegalEntity();
			CustDivision Div = result1.getCustDivision();
		
			List<Result__> LERes = LE.getResults();
			List<Result_> DivRes = Div.getResults();
			
			for (Result__ LERec :LERes) {
				LERec.getExternalCode();
				LERec.getDescriptionDefaultValue();
			
				System.out.println(LERec.getExternalCode());
				System.out.println(LERec.getDescriptionDefaultValue());
			}
			
			for (Result_ DivRec :DivRes) {
				
				DivRec.getExternalCode();
				DivRec.getName();
			}
			
			
			
			
		}
		

	}

}
