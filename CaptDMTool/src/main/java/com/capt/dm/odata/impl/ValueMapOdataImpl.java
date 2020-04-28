package com.capt.dm.odata.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.capt.dm.model.FieldSet;
import com.capt.dm.odata.ValueMapOdata;

public class ValueMapOdataImpl implements ValueMapOdata {

	private static final Logger logger = LoggerFactory.getLogger(ValueMapOdataImpl.class);

	@Override
	public FieldSet getValueMap(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ValueMapOdataImpl: Inside getTemplateGrp Method");
//		logger.info("ValueMapOdataImpl: Services URL:"+servicesUrl);

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/VALUE_MAP?$filter=Client+eq+'"+client+"'";

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("DM_APP_USER", "Welcome123456789$X$$"));

		FieldSet resultObj = restTemplate.getForObject(url, FieldSet.class);
		logger.info("ValueMapOdataImpl:Results:" + resultObj.getD().getResults());
		logger.info("ValueMapOdataImpl:Results size:" + resultObj.getD().getResults().size());
		return resultObj;
	}

}
