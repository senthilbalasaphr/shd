package com.capt.dm.odata.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.capt.dm.model.FieldSet;
import com.capt.dm.odata.FuncMapOdata;

public class FuncMapOdataImpl implements FuncMapOdata{

	private static final Logger logger = LoggerFactory.getLogger(FuncMapOdataImpl.class);
	@Override
	public FieldSet getFuncMap(String client, String tempGrp, String template) throws Exception {
		
		logger.info("FuncMapOdataImpl: Inside getFuncMap Method");

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/FIELD_FUNC"
				+ "?$filter=Client+eq+'"+client+"' and TEMPLATEGRP+eq+'"+tempGrp+"' and TEMPLATE+eq+'"+template+"'";

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));

		FieldSet resultObj = restTemplate.getForObject(url, FieldSet.class);
		logger.info("FuncMapOdataImpl:getFuncMap:Results:" + resultObj.getD().getResults());
		logger.info("FuncMapOdataImpl:getFuncMap:Results size:" + resultObj.getD().getResults().size());
		return resultObj; 
	}
	
	@Override
	public FieldSet getInitFuncMap(String client, String tempGrp, String template) throws Exception {
		
		logger.info("FuncMapOdataImpl: Inside getFuncMap Method");

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/INIT_FUNC"
				+ "?$filter=Client+eq+'"+client+"' and TEMPLATEGRP+eq+'"+tempGrp+"' and TEMPLATE+eq+'"+template+"'";

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));

		FieldSet resultObj = restTemplate.getForObject(url, FieldSet.class);
		logger.info("FuncMapOdataImpl:getFuncMap:Results:" + resultObj.getD().getResults());
		logger.info("FuncMapOdataImpl:getFuncMap:Results size:" + resultObj.getD().getResults().size());
		return resultObj;
	}
	
	@Override
	public FieldSet getFuncRules(String client) throws Exception {
		
		logger.info("FuncMapOdataImpl: Inside getFuncRules Method");

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/FUNCTION_RULE"
				+ "?$filter=Client+eq+'"+client+"'";

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));

		FieldSet resultObj = restTemplate.getForObject(url, FieldSet.class);
		logger.info("FuncMapOdataImpl:getFuncRules:Results:" + resultObj.getD().getResults());
		logger.info("FuncMapOdataImpl:getFuncRules:Results size:" + resultObj.getD().getResults().size());
		return resultObj;
	}

}
