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



public class CopyInitFunc {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = new RestTemplate();
	
		Map< String,String> m1=new HashMap< String,String>();
		
		
		String UrlCount = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata/INIT_FUNC/$count?" ;
		//		+ "&$filter=company+eq+'A2000'&fromDate=1900-01-01&toDate=9999-12-31";
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		String count = restTemplate.getForObject(UrlCount, String.class);
		System.out.println(count);
		
		 List<com.shdcutover.InitFunc.InitFunc> Listall= getInitFunc(count);
		 
			List <com.shdcutover.InitFunc.Result> records =null;
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String Url = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata";
			String Urlx = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata/";
			
		 for(com.shdcutover.InitFunc.InitFunc List1: Listall) {
			 com.shdcutover.InitFunc.D d = List1.getD();
			 
			 List <com.shdcutover.Template.Result> Temp =null;
				
			 	records= d.getResults();
			

			for (com.shdcutover.InitFunc.Result record : records) {
				if (record.getClient().equalsIgnoreCase("SHD_V2")) {
					System.out.println(record.getClient());
					System.out.println(record.getTEMPLATEGRP());
					System.out.println(record.getTEMPLATE());
					System.out.println(record.getSEQNR());
					System.out.println(record.getFUNCTIONID());
					System.out.println(record.getFUNCTIONROUTINE());
					System.out.println("-------------------");
					
					String postrul = Url + "/INIT_FUNC";

			

					JSONObject TemplateObject = new JSONObject();
					TemplateObject.put("Client", "SHD_PROD") ;
					TemplateObject.put("TEMPLATEGRP", record.getTEMPLATEGRP()) ;
					TemplateObject.put("TEMPLATE", record.getTEMPLATE()) ;
					TemplateObject.put("SEQNR", record.getSEQNR()) ;
					TemplateObject.put("FUNCTION_ID", record.getFUNCTIONID()) ;
					TemplateObject.put("FUNCTION_ROUTINE", record.getFUNCTIONROUTINE()) ;
					 
					 
					 
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

	}

	
	public static List<com.shdcutover.InitFunc.InitFunc> getInitFunc(String count) {
		List<com.shdcutover.InitFunc.InitFunc> ListInitFunc= new ArrayList<com.shdcutover.InitFunc.InitFunc>();
		String Url = null;
		int t=1000;
		long s=0;
		long x=0;
		long c = Long.parseLong(count);
		
		RestTemplate restTemplate1 = new RestTemplate();
		restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		while (c>s) {
		Url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/INIT_FUNC?$format=json&"
				+ "$top="+t+"&$skip="+s+"";
			//	+ "&$filter=createdBy+eq+'VKUMAR' and company+eq+'" + LegalEntity + "'&fromDate=1900-12-31&toDate=9999-12-31"
			//	+ "&$expand=positionMatrixRelationship,parentPosition"
				
		
		com.shdcutover.InitFunc.InitFunc initfunc = restTemplate1.getForObject(Url, com.shdcutover.InitFunc.InitFunc.class);
		ListInitFunc.add(initfunc);

			System.out.println(Url);
			s=s+t;
		}
		
		return ListInitFunc;
		
	}
	
	
}
