package com.capt.dm.odata;

import com.capt.dm.model.FieldSet;
import com.capt.dm.model.TemplateGrpBean;

public interface TemplateGrpOdata {
	public TemplateGrpBean getClients() throws Exception;
	public String getTemplateGrp(String client)  throws Exception;
	public String getTemplate(String id)  throws Exception;
	public FieldSet getFieldset(String client, String tmpGrp, String template) throws Exception;
}
