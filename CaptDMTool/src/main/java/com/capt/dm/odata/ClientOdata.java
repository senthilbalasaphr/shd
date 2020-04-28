package com.capt.dm.odata;

import java.util.Map;

import com.capt.dm.model.FieldSet;

public interface ClientOdata {

	Map<String, String> getClientSystem(String client) throws Exception;

	Map<String, String> getClient() throws Exception;

}
