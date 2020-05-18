package com.capt.dm;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;

import com.capt.dm.util.OlingoV2_1Main;

import com.capt.dm.util.OlingoV2_1Main;;

public class GetFieldsOdataSF {

	public static final String APPLICATION_JSON = "application/json";
	

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String serviceUrl = "https://api12preview.sapsf.eu/odata/v2/";

		String usedFormat = APPLICATION_JSON;

		OlingoV2_1Main app = new OlingoV2_1Main();
		Edm edm=null;
		try {
			edm = app.readEdmSf(serviceUrl, "VKUMAR@shiseidocoT1", "Welcome@3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ODataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		serviceUrl="https://api12preview.sapsf.eu/odata/v2/Position?$format=JSON&$filter=code+eq+%2760054983%27&$expand=jobCodeNav";
		ODataFeed Fed=null;
		try {
			Fed = app.readFeedSf(edm, serviceUrl, usedFormat, "Position","VKUMAR@shiseidocoT1", "Welcome@3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ODataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		 	
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
				 System.out.println(value+",");
				 
			 }else {
				 System.out.println(entry1.getValue()+",");
			 }
			
		    }
			 System.out.println("");
		 }
		
		


	}

}
