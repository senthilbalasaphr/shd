package com.shd.cutover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.shdcutover.Template.Metadata;



public class CopyFuncRule {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = new RestTemplate();
	
		Map< String,String> m1=new HashMap< String,String>();
		
		
		String UrlCount = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata/FUNCTION_RULE/$count?" ;
		//		+ "&$filter=company+eq+'A2000'&fromDate=1900-01-01&toDate=9999-12-31";
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		String count = restTemplate.getForObject(UrlCount, String.class);
		System.out.println(count);
		
		 List<com.shdcutover.FuncRule.FuncRule> Listall= getFuncRule(count);
		 
			List <com.shdcutover.FuncRule.Result> records =null;
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String Url = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata";
			String Urlx = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata/";
			
		 for(com.shdcutover.FuncRule.FuncRule List1: Listall) {
			 com.shdcutover.FuncRule.D d = List1.getD();
			 

				
			 	records= d.getResults();
			

			for (com.shdcutover.FuncRule.Result record : records) {
		
						
					
					System.out.println(record.getClient());
					System.out.println(record.getFUNCTIONID());
					System.out.println(record.getFUNCTIONROUTINEID());
					System.out.println(record.getCLASS());
					System.out.println(record.getMETHOD());
					
	
					System.out.println("-------------------");
					
					String postrul = Url + "/FUNCTION_RULE";

					
						

					JSONObject TemplateObject = new JSONObject();
					TemplateObject.put("Client", "SHD_PROD") ;
					TemplateObject.put("FUNCTION_ID", record.getFUNCTIONID()) ;
					TemplateObject.put("FUNCTION_ROUTINE_ID", record.getFUNCTIONROUTINEID()) ;
					TemplateObject.put("CLASS", record.getCLASS()) ;
					TemplateObject.put("METHOD", record.getMETHOD()) ;
					

					 
					 
					 
					RestTemplate restTemplate1 = new RestTemplate();
					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
					 HttpEntity<String> request = 
						      new HttpEntity<String>(TemplateObject.toString(), headers);
					
						try {
					String response = restTemplate1.postForObject( postrul, request , String.class );
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
						}
					
					
					
				}
				
			}


	}

	
	public static List<com.shdcutover.FuncRule.FuncRule> getFuncRule(String count) {
		List<com.shdcutover.FuncRule.FuncRule> ListFuncRule= new ArrayList<com.shdcutover.FuncRule.FuncRule>();
		String Url = null;
		int t=1000;
		long s=0;
		long x=0;
		long c = Long.parseLong(count);
		
		RestTemplate restTemplate1 = new RestTemplate();
		restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		while (c>s) {
		Url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/FUNCTION_RULE?$format=json&"
				+ "$top="+t+"&$skip="+s+"";
			//	+ "&$filter=createdBy+eq+'VKUMAR' and company+eq+'" + LegalEntity + "'&fromDate=1900-12-31&toDate=9999-12-31"
			//	+ "&$expand=positionMatrixRelationship,parentPosition"
				
		
		com.shdcutover.FuncRule.FuncRule FieldSet = restTemplate1.getForObject(Url, com.shdcutover.FuncRule.FuncRule.class);
		ListFuncRule.add(FieldSet);

			System.out.println(Url);
			s=s+t;
		}
		
		return ListFuncRule;
		
	}
	
	
}
