package com.capt.dm.odata;

import com.capt.dm.model.FieldSet;

public interface FuncMapOdata {
	public FieldSet getFuncMap(String client, String tempGrp, String template) throws Exception;
	public FieldSet getFuncRules(String client) throws Exception;
}
