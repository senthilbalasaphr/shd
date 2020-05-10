package com.shd.reports;

import java.util.Map;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.shd.reports.department.Department;

public class OrgChartReport {
	
	public static void main(String[] args) {
		
		
		
		String Code="60000990,60000992,60000993,60000994";
		RestTemplate restTemplate = new RestTemplate();
		String checkUrl = "https://api12preview.sapsf.eu/odata/v2/FODepartment?$format=JSON&$expand=cust_Division,cust_LegalEntity&$filter=externalCode+in+"+Code+"&$select=externalCode,startDate,endDate,createdOn,createdBy,lastModifiedOn,lastModifiedBy,name,cust_LegalEntity/externalCode,cust_LegalEntity/description_defaultValue,cust_Division/externalCode,cust_Division/name";
		System.out.println(checkUrl);
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1","Welcome@3"));
		String departments = restTemplate.getForObject(checkUrl, String.class);
		Map<String, String> m = restTemplate.getForObject(checkUrl, Map.class);
	
		System.out.println(m);

		
		System.out.println(departments);

		
		
		
		
	}

}
