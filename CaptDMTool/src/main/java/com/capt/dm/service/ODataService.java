package com.capt.dm.service;

import java.util.Map;

import com.capt.dm.model.FieldSet;
import com.capt.dm.model.TemplateGrpBean;

public interface ODataService {
	public TemplateGrpBean getClients() throws Exception;

	public String getTemplateGrp(String client) throws Exception;

	public String getTemplate(String id) throws Exception;

	public FieldSet getFieldset(String client, String tmpGrp, String template) throws Exception;

	public FieldSet getValueMap(String client) throws Exception;

	public FieldSet getFuncMap(String client, String tempGrp, String template) throws Exception;

	public FieldSet getFuncRules(String client) throws Exception;

	public Map<String, String> getClientSystem(String client) throws Exception;

	public Map<String, String> getClient() throws Exception;
}
