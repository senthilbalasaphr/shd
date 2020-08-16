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



public class CopyFieldSet {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = new RestTemplate();
	
		Map< String,String> m1=new HashMap< String,String>();
		
		
		String UrlCount = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata/FIELDSET/$count?" ;
		//		+ "&$filter=company+eq+'A2000'&fromDate=1900-01-01&toDate=9999-12-31";
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		String count = restTemplate.getForObject(UrlCount, String.class);
		System.out.println(count);
		
		 List<com.shdcutover.FieldSet.FieldSet> Listall= getFieldSet(count);
		 
			List <com.shdcutover.FieldSet.Result> records =null;
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String Url = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata";
			String Urlx = "https://bs1e3cc05196.eu2.hana.ondemand.com:443/sap/SHDdev/services.xsodata/";
			
		 for(com.shdcutover.FieldSet.FieldSet List1: Listall) {
			 com.shdcutover.FieldSet.D d = List1.getD();
			 

				
			 	records= d.getResults();
			

			for (com.shdcutover.FieldSet.Result record : records) {
				if (record.getClient().equalsIgnoreCase("SHD")) {
					
					if (record.getTEMPLATE().equalsIgnoreCase("e-mail")) {
						
					
					System.out.println(record.getClient());
					System.out.println(record.getTEMPLATEGRP());
					System.out.println(record.getTEMPLATE());
					System.out.println(record.getSEQNR());
					System.out.println(record.getFIELDNAME());
					System.out.println(record.getFIELDDESCRIPTION());
					System.out.println(record.getFIELDTYPE());
					System.out.println(record.getFIELDLENGTH());
					System.out.println(record.getFIELDREQUIRED());
					System.out.println(record.getFIELDKEY());
					System.out.println(record.getVALUEMAPPING());
					System.out.println("-------------------");
					
					String postrul = Url + "/FIELDSET";

					
						

					JSONObject TemplateObject = new JSONObject();
					TemplateObject.put("Client", "SHD_PROD") ;
					TemplateObject.put("TEMPLATEGRP", record.getTEMPLATEGRP()) ;
					TemplateObject.put("TEMPLATE", record.getTEMPLATE()) ;
					TemplateObject.put("SEQNR", record.getSEQNR()) ;
					TemplateObject.put("FIELD_NAME", record.getFIELDNAME()) ;
					TemplateObject.put("FIELD_DESCRIPTION", record.getFIELDDESCRIPTION()) ;
					TemplateObject.put("FIELD_TYPE", record.getFIELDTYPE()) ;
					TemplateObject.put("FIELD_REQUIRED", record.getFIELDREQUIRED()) ;
					TemplateObject.put("FIELD_KEY", record.getFIELDKEY()) ;
					TemplateObject.put("FIELD_LENGTH", record.getFIELDLENGTH()) ;
					 
					 
					 
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

	}

	
	public static List<com.shdcutover.FieldSet.FieldSet> getFieldSet(String count) {
		List<com.shdcutover.FieldSet.FieldSet> ListFieldSet= new ArrayList<com.shdcutover.FieldSet.FieldSet>();
		String Url = null;
		int t=1000;
		long s=0;
		long x=0;
		long c = Long.parseLong(count);
		
		RestTemplate restTemplate1 = new RestTemplate();
		restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		while (c>s) {
		Url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/FIELDSET?$format=json&"
				+ "$top="+t+"&$skip="+s+"";
			//	+ "&$filter=createdBy+eq+'VKUMAR' and company+eq+'" + LegalEntity + "'&fromDate=1900-12-31&toDate=9999-12-31"
			//	+ "&$expand=positionMatrixRelationship,parentPosition"
				
		
		com.shdcutover.FieldSet.FieldSet FieldSet = restTemplate1.getForObject(Url, com.shdcutover.FieldSet.FieldSet.class);
		ListFieldSet.add(FieldSet);

			System.out.println(Url);
			s=s+t;
		}
		
		return ListFieldSet;
		
	}
	
	
}
