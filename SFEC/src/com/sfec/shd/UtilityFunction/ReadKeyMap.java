package com.sfec.shd.UtilityFunction;


import java.util.Map;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class ReadKeyMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/CLIENT";
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		Map result = restTemplate.getForObject(url, Map.class);
		Map resultDObj = (Map<String, String>) result.get("entry");
		Map ContentDObj = (Map<String, String>) resultDObj.get("content");
		Map mpropertiesDObj = (Map<String, String>) ContentDObj.get("properties");
		Map dClientDObj = (Map<String, String>) mpropertiesDObj.get("Client");
		
		System.out.print(mpropertiesDObj.get("Client"));
	//	logger.info("ClientOdataImpl:getClientSystem:resultMap resultDObj:" + resultDObj.toString());
//		Map<String, String> resultMap = (Map<String, String>)resultDObj.get("results");

	//	logger.info("ClientOdataImpl:getClientSystem:resultMap size:" + resultDObj.size());
	//	logger.info("ClientOdataImpl:getClientSystem:resultMap Client:" + resultDObj.get("Client"));
	// resultDObj;

	}

}
