package com.sf.Olingo;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.google.gson.Gson;

public class ReadEmpJob {

	public static final String APPLICATION_JSON = "application/json";

	public static void main(String[] paras) throws Exception {

		String serviceUrl = "https://api8.successfactors.com/odata/v2/";

		String usedFormat = APPLICATION_JSON;

		OlingoV2Main app = new OlingoV2Main();
		Edm edm = app.readEdmSf(serviceUrl, "SFEC_SYNC_ALL@freemanT1", "FreemanHR1");

		serviceUrl="https://api8.successfactors.com/odata/v2/EmpJob?$filter=userId+eq+'87107078'&fromDate=2002-12-31&toDate=9999-12-31&$select=userId,startDate,endDate";
		ODataFeed Fed = app.readFeedSf(edm, serviceUrl, usedFormat, "EmpJob","SFEC_SYNC_ALL@freemanT1", "FreemanHR1");

	
		 	
		 for (ODataEntry entry : Fed.getEntries()) {
			 Map<String, Object> properties = entry.getProperties();
			 Set<Entry<String, Object>> entries = properties.entrySet();
			 for (Entry<String, Object> entry1 : entries) {
				 System.out.print (entry1.getKey()+":");
				
				 
				 if (entry1.getValue() instanceof Calendar ) {
				 Calendar cal = (Calendar) entry1.getValue();
				 String pattern = "MM-dd-yyyy";
				 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				 
				 Object value = simpleDateFormat.format(cal.getTime());
				 System.out.print(value+",");
				 
			 }else {
				 System.out.print(entry1.getValue()+",");
			 }
			
		    }
			 System.out.println("");
		 }
		
		

		
		
	}
	
	
	public static  ArrayList<String> getONBMapping() throws IOException, ODataException{
		
		ArrayList <String> lst = new ArrayList();
		String serviceUrl = "https://api8.successfactors.com/odata/v2/";

		String usedFormat = APPLICATION_JSON;

		OlingoV2Main app = new OlingoV2Main();
		Edm edm = app.readEdmSf(serviceUrl, "SFEC_SYNC_ALL@freemanT1", "FreemanHR1");
		serviceUrl="https://api8.successfactors.com/odata/v2/EmpJob?$format=ATOM&$filter=userId+eq+'87107078'&fromDate=2014-12-31&toDate=2020-12-31&$select=userId,endDate,startDate";

		ODataFeed Fed = app.readFeedSf(edm, serviceUrl, usedFormat, "EmpJob","SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");

	
		 	
		 for (ODataEntry entry : Fed.getEntries()) {
			 Map<String, Object> properties = entry.getProperties();
			 Set<Entry<String, Object>> entries = properties.entrySet();
			 for (Entry<String, Object> entry1 : entries) {
				// System.out.print (entry1.getKey()+":");
				
				 
				 if (entry1.getValue() instanceof Calendar ) {
				 Calendar cal = (Calendar) entry1.getValue();
				 Object value = SimpleDateFormat.getInstance().format(cal.getTime());
				 //System.out.println(value);
				 
			 }else {
			
				 if (entry1.getValue() != null && entry1.getKey().equalsIgnoreCase("cust_ONBField"))
				 lst.add(entry1.getValue().toString()) ;
			 }
			
		    }
			 //System.out.println("-----------");
		 }
		
		
		return lst;
	}
	
}
