package com.sfec.freeman;
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

import com.sf.Olingo.*;

public class PositionUpload {

	public static final String APPLICATION_JSON = "application/json";
	
	public static void main(String[] args) throws IOException, ODataException {
		// TODO Auto-generated method stub

		
		
		readPosition("hi");
		
		
	}
	
	
	
	public static void readPosition(String pos) throws IOException, ODataException {
		
		
		String serviceUrl = "https://api8preview.sapsf.com/odata/v2/Position";

		String usedFormat = APPLICATION_JSON;

		OlingoV2Main app = new OlingoV2Main();
		Edm edm = app.readEdmSf(serviceUrl, "SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");



	    
		ODataFeed Fed = app.readFeedSf(edm, serviceUrl, usedFormat, "Position","SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");

	
		 	
		 for (ODataEntry entry : Fed.getEntries()) {
			 Map<String, Object> properties = entry.getProperties();
			 Set<Entry<String, Object>> entries = properties.entrySet();
			 for (Entry<String, Object> entry1 : entries) {
				 System.out.print (entry1.getKey()+":");
				
				 
				 if (entry1.getValue() instanceof Calendar ) {
				 Calendar cal = (Calendar) entry1.getValue();
				 Object value = SimpleDateFormat.getInstance().format(cal.getTime());
				 System.out.println(value);
				 
			 }else {
				 System.out.println(entry1.getValue());
			 }
			
		    }
			 System.out.println("-----------");
		 }
		
		
		
		
	}

	
	
}
