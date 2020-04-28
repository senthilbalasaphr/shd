package com.capt.dm.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.capt.dm.model.FieldSet;
import com.capt.dm.model.TemplateGrpBean;
import com.capt.dm.odata.ClientOdata;
import com.capt.dm.odata.FuncMapOdata;
import com.capt.dm.odata.TemplateGrpOdata;
import com.capt.dm.odata.ValueMapOdata;
import com.capt.dm.service.ODataService;

public class ODataServiceImpl implements ODataService {

	private static final Logger logger = LoggerFactory.getLogger(ODataServiceImpl.class);

	@Autowired
	TemplateGrpOdata templateGrpOdata;

	@Autowired
	ValueMapOdata valueMapOdata;

	@Autowired
	FuncMapOdata funcMapOdata;

	@Autowired
	ClientOdata clientOdata;

	public ClientOdata getClientOdata() {
		return clientOdata;
	}

	public void setClientOdata(ClientOdata clientOdata) {
		this.clientOdata = clientOdata;
	}

	public FuncMapOdata getFuncMapOdata() {
		return funcMapOdata;
	}

	public void setFuncMapOdata(FuncMapOdata funcMapOdata) {
		this.funcMapOdata = funcMapOdata;
	}

	public ValueMapOdata getValueMapOdata() {
		return valueMapOdata;
	}

	public void setValueMapOdata(ValueMapOdata valueMapOdata) {
		this.valueMapOdata = valueMapOdata;
	}

	public TemplateGrpOdata getTemplateGrpOdata() {
		return templateGrpOdata;
	}

	public void setTemplateGrpOdata(TemplateGrpOdata templateGrpOdata) {
		this.templateGrpOdata = templateGrpOdata;
	}

	@Override
	public TemplateGrpBean getClients() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateGrp(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataServiceImpl: Inside getTemplateGrp Method");
		String result = templateGrpOdata.getTemplateGrp(client);
		return result;
	}

	@Override
	public String getTemplate(String id) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataServiceImpl: Inside getTemplate Method");
		String result = templateGrpOdata.getTemplate(id);
		return result;
	}

	@Override
	public FieldSet getFieldset(String client, String tmpGrp, String template) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataServiceImpl: Inside getFieldset Method");
		FieldSet result = templateGrpOdata.getFieldset(client, tmpGrp, template);
		return result;
	}

	@Override
	public FieldSet getValueMap(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataServiceImpl: Inside getFieldset Method");
		FieldSet result = valueMapOdata.getValueMap(client);
		return result;
	}

	@Override
	public FieldSet getFuncMap(String client, String tempGrp, String template) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataServiceImpl: Inside getFuncMap Method");
		FieldSet result = funcMapOdata.getFuncMap(client, tempGrp, template);
		return result;
	}

	@Override
	public FieldSet getFuncRules(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataServiceImpl: Inside getFuncRules Method");
		FieldSet result = funcMapOdata.getFuncRules(client);
		return result;
	}

	@Override
	public Map<String, String> getClientSystem(String client) throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataServiceImpl: Inside getClientSystem Method");
		Map<String, String> result = clientOdata.getClientSystem(client);
		return result;
	}

	@Override
	public Map<String, String> getClient() throws Exception {
		// TODO Auto-generated method stub
		logger.info("ODataServiceImpl: Inside getClient Method");
		Map<String, String> result = clientOdata.getClient();
		return result;
	}

}
