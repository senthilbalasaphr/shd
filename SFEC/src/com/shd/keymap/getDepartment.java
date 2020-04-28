package com.shd.keymap;

import java.util.List;
import java.util.Map;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class getDepartment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String Company = "04";
		String ObjectType = "09";
		String cust_LegacyID= "HR_AM1";
	//	String cust_LegacyID= "1216";
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api12preview.sapsf.eu/odata/v2/cust_Keymapping?$filter=cust_Company eq '"+Company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_LegacyID eq '"+cust_LegacyID+"'&$select=cust_SFID";
	//	String url = "https://api12preview.sapsf.eu/odata/v2/cust_Keymapping?$filter=externalCode eq '"+cust_LegacyID+"'&$format=JSON&$select=cust_SFID";
		//String url = "https://apisalesdemo2.successfactors.eu/odata/v2/cust_Keymapping?$filter=externalCode eq '"+cust_LegacyID+"'&$format=JSON&$select=externalCode";
		
		
		System.out.println(url);	
		//	String url = "https://api12preview.sapsf.eu/odata/v2/cust_Keymapping?$select=cust_SFID";
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
		//restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

		KeyMap result = restTemplate.getForObject(url,KeyMap.class);
	
		if ( result != null) {
			D d = result.getD();
			List<Result> res = d.getResults();
			for (Result result1 :res) {
			System.out.println(result1.getCustSFID());	
				
			}
			
			
		}
		
		

	}

}
