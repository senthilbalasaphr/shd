package com.capt.dm.odata.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.capt.dm.model.FieldSet;
import com.capt.dm.model.TemplateGrpBean;
import com.capt.dm.odata.TemplateGrpOdata;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TemplateGrpOdataImpl implements TemplateGrpOdata {

	private static final Logger logger = LoggerFactory.getLogger(TemplateGrpOdataImpl.class);
	
	@Override
	public TemplateGrpBean getClients() throws Exception {
		// TODO Auto-generated method stub

		RestTemplate restTemplate = new RestTemplate();
		return null;
	}

	@Override
	public String getTemplateGrp(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("TemplateGrpOdataImpl: Inside getTemplateGrp Method");
//		logger.info("TemplateGrpOdataImpl: Services URL:"+servicesUrl);

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/TEMPLATEGRP?$filter=Client+eq+'"+client+"'";
		logger.info("TemplateGrpOdataImpl: getTemplateGrp: URL:"+url);

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));
		
		String jsonResult = restTemplate.getForObject(url, String.class);
		
		logger.info("Template Group:"+jsonResult);
		
		/*FieldSet resultObj = restTemplate.getForObject(url, FieldSet.class);
		logger.info("TemplateGrpOdataImpl:Results:" + resultObj.getD().getResults());
		logger.info("TemplateGrpOdataImpl:Results size:" + resultObj.getD().getResults().size());*/

		return jsonResult;
	}

	@Override
	public String getTemplate(String id) throws Exception {
		// TODO Auto-generated method stub
		logger.info("TemplateGrpOdataImpl: Inside getTemplate Method");
		logger.info("TemplateGrpOdataImpl: Selected Template Group:"+id);

		String[] input = id.split("-");
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/TEMPLATE"
				+ "?$filter=Client+eq+'"+input[0].trim()+"'&TEMPLATEGRP+eq+'"+input[1].trim()+"'";

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));

		String jsonResult = restTemplate.getForObject(url, String.class);
		
		return jsonResult;
	}

	@Override
	public FieldSet getFieldset(String client, String tmpGrp, String template) throws Exception {
		// TODO Auto-generated method stub

		logger.info("TemplateGrpOdataImpl: Inside getFieldset Method");

		RestTemplate restTemplate = new RestTemplate();
		/*String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/FIELDSET"
				+ "?Client='SHD'&TEMPLATEGRP='SHISEIDO'&TEMPLATE='DEPARTMENT'";*/
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/FIELDSET"
				+ "?$filter=Client+eq+'"+client+"'&TEMPLATEGRP+eq+'"+tmpGrp+"'&TEMPLATE+eq+'"+template+"'";

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));

		FieldSet objD = restTemplate.getForObject(url, FieldSet.class);

		/*
		 * String result = map.get("d").toString();
		 * 
		 * Gson gson = new Gson();
		 * 
		 * String jsonRes = gson.toJson(result);
		 * 
		 * logger.info("TemplateGrpOdataImpl: JSon:" +JSon);
		 * 
		 * logger.info("TemplateGrpOdataImpl: Field Maps:" + map);
		 * logger.info("TemplateGrpOdataImpl: result:" +result);
		 * logger.info("TemplateGrpOdataImpl: jsonRes:" +jsonRes);
		 */

		logger.info("TemplateGrpOdataImpl:Results:" + objD.getD().getResults());
		logger.info("TemplateGrpOdataImpl:Results size:" + objD.getD().getResults().size());

		return objD;
	}

}
