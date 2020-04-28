package com.capt.dm.delegate;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.capt.dm.model.FieldSet;
import com.capt.dm.service.ODataService;

public class ODataDelegate {

	private static final Logger logger = LoggerFactory.getLogger(ODataDelegate.class);

	@Autowired
	ODataService oDataService;

	public ODataService getODataService() {
		return oDataService;
	}

	public void setODataService(ODataService oDataService) {
		this.oDataService = oDataService;
	}

	public String getTemplateGrp(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataDelegate: Inside getTemplateGrp Method");
		String result = oDataService.getTemplateGrp(client);
		return result;
	}
	
	public String getTemplate(String id) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataDelegate: Inside getTemplate Method");
		String result = oDataService.getTemplate(id);
		return result;
	}

	public FieldSet getFieldset(String client, String tmpGrp, String template) throws Exception {
		logger.info("ODataDelegate: Inside getFieldset Method");
		FieldSet result = oDataService.getFieldset(client, tmpGrp, template);
		return result;
	}

	public FieldSet getValueMap(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataDelegate: Inside getValueMap Method");
		FieldSet result = oDataService.getValueMap(client);
		return result;
		
	}

	public FieldSet getFuncMap(String client, String tempGrp, String template) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataDelegate: Inside getFuncMap Method");
		FieldSet result = oDataService.getFuncMap(client, tempGrp, template);
		return result;
		
	}
	
	public FieldSet getFuncRules(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataDelegate: Inside getFuncRules Method");
		FieldSet result = oDataService.getFuncRules(client);
		return result;
		
	}

	public Map<String, String> getClientSystem(String client) throws Exception{
		logger.info("ODataDelegate: Inside getClientSystem Method");
		Map<String, String> result = oDataService.getClientSystem(client);
		return result;
	}
	
	public Map<String, String> getClient() throws Exception{
		logger.info("ODataDelegate: Inside getClient Method");
		Map<String, String> result = oDataService.getClient();
		return result;
	}
}
