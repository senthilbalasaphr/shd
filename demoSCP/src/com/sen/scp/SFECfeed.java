package com.sen.scp;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;


public class SFECfeed {

	
	
	public void getSFECdata(HttpServletResponse response ) throws IOException
	{
		
		String serviceUrl = "https://api8preview.sapsf.com/odata/v2";

		String usedFormat = "application/json";

		OlingoV2Main app = new OlingoV2Main();
		Edm edm=null;
		try {
			edm = app.readEdmSf(serviceUrl, "SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	//		 response.getWriter().println(e.toString()); 
		}


		ODataFeed Fed=null;
		try {
			Fed = app.readFeedSf(edm, serviceUrl, usedFormat, "cust_W4IntegrationONBMapping","SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 response.getWriter().println(e.toString()); 
			 
		}

	
		 	
		 for (ODataEntry entry : Fed.getEntries()) {
			 Map<String, Object> properties = entry.getProperties();
			 Set<Entry<String, Object>> entries = properties.entrySet();
			 for (Entry<String, Object> entry1 : entries) {
				// System.out.print (entry1.getKey()+":");
				 response.getWriter().println(entry1.getKey()+":");
				 
				 if (entry1.getValue() instanceof Calendar ) {
				 Calendar cal = (Calendar) entry1.getValue();
				 Object value = SimpleDateFormat.getInstance().format(cal.getTime());
			//	 System.out.println(value);
				 response.getWriter().println(value); 
				 
			 }else {
	
				 response.getWriter().println(entry1.getValue()); 
			 }
			
		    }
	
			 response.getWriter().println("-----------"); 
		 }
		
	 
	 
	 
	 
		 
		 
	}
	
	
}
