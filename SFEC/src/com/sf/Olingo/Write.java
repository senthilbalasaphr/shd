package com.sf.Olingo;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;

public class Write { 

	public static final String APPLICATION_JSON = "application/json";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String serviceUrl = "https://api8preview.sapsf.com/odata/v2/cust_W4IntegrationONBMapping";
		String usedFormat = APPLICATION_JSON;
		String Status=null;
		OlingoV2Main app = new OlingoV2Main();
		Edm edm=null;
		try {
			edm = app.readEdmSf(serviceUrl, "SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ODataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
			Map<String, Object> data = new HashMap<String, Object>();
//			data.put("__metadata", "{uri:cust_W4IntegrationONBMapping}");
		    data.put("externalCode", "DC_taxexemptindicator");
		    data.put("externalName", "DC_taxexemptindicator");
		    data.put("cust_State", "DC");
		    data.put("cust_ONBField", "StateWithholding.DC.DC_D_4_IsExempt");
		    data.put("cust_ECField", "cust_taxexemptindicator");
		    data.put("cust_valueMapCode", "DC_taxexemptindicator");
		 		    
		    ODataEntry createdEntry=null;
		    try {
		    	Status = app.createEntrySf(edm, serviceUrl, usedFormat, "cust_W4IntegrationONBMapping", data,"SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		    System.out.println(Status);


	}

}
