package com.capt.dm.odata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.capt.dm.odata.ClientOdata;

public class ClientOdataImpl implements ClientOdata {

	private static final Logger logger = LoggerFactory.getLogger(ClientOdataImpl.class);

	@Override
	public Map<String, String> getClientSystem(String client) throws Exception {
		// TODO Auto-generated method stub

		logger.info("ClientOdataImpl: Inside getClientSystem Method");

		RestTemplate restTemplate = new RestTemplate();
		/*
		 * String url =
		 * "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/CLIENT_SYSTEM?"
		 * + "?Client='"+client+"'";
		 */
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/CLIENT_SYSTEM('" + client
				+ "')";

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));

		Map result = restTemplate.getForObject(url, Map.class);
		Map resultDObj = (Map<String, String>) result.get("d");
		logger.info("ClientOdataImpl:getClientSystem:resultMap resultDObj:" + resultDObj.toString());
//		Map<String, String> resultMap = (Map<String, String>)resultDObj.get("results");

		logger.info("ClientOdataImpl:getClientSystem:resultMap size:" + resultDObj.size());
		logger.info("ClientOdataImpl:getClientSystem:resultMap Client:" + resultDObj.get("Client"));
		return resultDObj;

	}

	@Override
	public Map<String, String> getClient() throws Exception {
		// TODO Auto-generated method stub

		logger.info("ClientOdataImpl: Inside getClient Method");

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/CLIENT";

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));

		Map result = restTemplate.getForObject(url, Map.class);
		Map resultDObj = (Map<String, String>) result.get("d");
//		logger.info("ClientOdataImpl:getClient:resultMap resultDObj:" + resultDObj.toString());
//		Map<String, String> resultMap = (Map<String, String>)resultDObj.get("results");

		List<Map<String, String>> clients = (List<Map<String, String>>) resultDObj.get("results");

		Map<String, String> clientMap = new HashMap<String, String>();
		for (Map<String, String> client : clients) {
			clientMap.put(client.get("Client"), client.get("Client") + " - " + client.get("NAME"));
		}
//		logger.info("ClientOdataImpl:getClient: clientMap size:" + clientMap.size());
//		logger.info("ClientOdataImpl:getClient: clientMap:" + clientMap.toString());
		return clientMap;

	}

}
