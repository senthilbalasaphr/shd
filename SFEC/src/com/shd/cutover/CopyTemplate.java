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



public class CopyTemplate {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = new RestTemplate();
	
		Map< String,String> m1=new HashMap< String,String>();
		
		
		String UrlCount = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata/TEMPLATE/$count?" ;
		//		+ "&$filter=company+eq+'A2000'&fromDate=1900-01-01&toDate=9999-12-31";
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		String count = restTemplate.getForObject(UrlCount, String.class);
		System.out.println(count);
		
		 List<com.shdcutover.Template.Template> ListTemplate= getTemplate(count);
		 
			List <com.shdcutover.Template.Result> Temp =null;
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String Url = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata";
			String Urlx = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata/";
			
		 for(com.shdcutover.Template.Template ListTemp: ListTemplate) {
			 com.shdcutover.Template.D Tmd = ListTemp.getD();
				
			 Temp= Tmd.getResults();
			

			for (com.shdcutover.Template.Result Template : Temp) {
				if (Template.getClient().equalsIgnoreCase("SHD")) {
					System.out.println(Template.getClient());
					System.out.println(Template.getTEMPLATEGRP());
					System.out.println(Template.getTEMPLATE());
					System.out.println(Template.getACTIVE());
					System.out.println("-------------------");
					
					String postrul = Url + "/TEMPLATE";

			

					JSONObject TemplateObject = new JSONObject();
					TemplateObject.put("Client", "SHD_PROD") ;
					TemplateObject.put("TEMPLATEGRP", Template.getTEMPLATEGRP()) ;
					TemplateObject.put("TEMPLATE", Template.getTEMPLATE()) ;
					TemplateObject.put("DESCRIPTION", Template.getDESCRIPTION()) ;
					TemplateObject.put("ACTIVE", Template.getACTIVE()) ;
					 
					 
					 
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

	
	public static List<com.shdcutover.Template.Template> getTemplate(String count) {
		List<com.shdcutover.Template.Template> ListTemplate= new ArrayList<com.shdcutover.Template.Template>();
		String Url = null;
		int t=1000;
		long s=0;
		long x=0;
		long c = Long.parseLong(count);
		
		RestTemplate restTemplate1 = new RestTemplate();
		restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		while (c>s) {
		Url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/TEMPLATE?$format=json&"
					+ "$top="+t+"&$skip="+s+"";
			//	+ "&$filter=createdBy+eq+'VKUMAR' and company+eq+'" + LegalEntity + "'&fromDate=1900-12-31&toDate=9999-12-31"
			//	+ "&$expand=positionMatrixRelationship,parentPosition"
				
		
		com.shdcutover.Template.Template Template = restTemplate1.getForObject(Url, com.shdcutover.Template.Template.class);
		ListTemplate.add(Template);

			System.out.println(Url);
			s=s+t;
		}
		
		return ListTemplate;
		
	}
	
	
}
