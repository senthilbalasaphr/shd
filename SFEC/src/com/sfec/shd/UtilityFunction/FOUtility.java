package com.sfec.shd.UtilityFunction;

//git clone https://bapsSKB@bitbucket.org/kumuruge/dmtool.git

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;

import com.sf.Olingo.OlingoV2Main;

public class FOUtility {

	
	
	public String getSplitRight(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)
			throws Exception {

		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();

		String newValue = "";
		if (null != legacyValue && !legacyValue.isEmpty()) {
			newValue = legacyValue.substring(legacyValue.lastIndexOf("-") + 1, legacyValue.length()).trim();
		}

		return newValue;
	}

	public String getSplitLeft(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)
			throws Exception {

		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();

		String newValue = "";
		if (null != legacyValue && !legacyValue.isEmpty()) {
			newValue = legacyValue.substring(0, legacyValue.lastIndexOf("-")).trim();
		}

		return newValue;
	}

///*****************Utility functions*******************/// 

///*****************Default email *******************/// 
	public String getDefaultEmail(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem) throws Exception {

		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();

		String newValue = "";
		if (null != legacyValue && !legacyValue.isEmpty()) {
			newValue = "DummyEmail@sapac.shiseido.com";
		}

		return newValue;
	}

///*****************Default Date of Birth *******************/// 
	
	public String getScrambleDOB(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)
			throws Exception {

		String strDate = ((MetaDataObj) rowData.get(index)).getFieldValue();

		String newValue = "";
		if (null != strDate && !strDate.isEmpty()) {
			Date start = null;
			try {
			
		 start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(strDate);

			}
			catch(Exception e)
			{
				 return (strDate + ": Invalid date");
			
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(start);
			int startVal = cal.get(Calendar.YEAR);

			int endVal = startVal + 10;

			GregorianCalendar gc = new GregorianCalendar();
		
			int year = randBetween(startVal, endVal);

			gc.set(gc.YEAR, year);

			int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

			gc.set(gc.DAY_OF_YEAR, dayOfYear);

			cal.setTime(start);

			newValue = (String.format("%02d", gc.get(gc.MONTH) + 1)) + "/"
					+ String.format("%02d", gc.get(gc.DAY_OF_MONTH) + 1) + "/" + gc.get(gc.YEAR) + "";

		}

		return newValue;
	}

	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	
///*****************Scramble amount / Percentage *******************/// 	
	
	public String getScrambleAmount(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem) throws Exception {
		
		float Actualamount =  Float.parseFloat(((MetaDataObj) rowData.get(index)).getFieldValue());	
		
		
		if ( randBetween(1, 2) == 1) {
		
		Actualamount = Actualamount + ( randBetween(1, 50))* Actualamount/100 ;
		}
		else {
			Actualamount = Actualamount - ( randBetween(1, 50))* Actualamount/100 ;	
		}
		
		
		
		return String.valueOf(Math.round(Actualamount));
	}

///*****************Default Sequence Number *******************/// 	
	public String getDefaultSeqnr(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem) throws Exception {
		
		return ("01"); 
	
	}
	
///*****************Default Sequence Number *******************/// 	
	
	public String getDepLevel1(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem) throws Exception {
		
		
		String url = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");	
		
		
		String LegacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String usedFormat="application/json";
		
		OlingoV2_1Main app = new OlingoV2_1Main();
		Edm edm = app.readEdmSf(url, userID, password);
		
		String query =  url+ "/cust_Keymapping?$filter=cust_Company+eq+'"+company+"' and cust_ObjectType+eq+'09' and cust_LegacyID+eq+'"+LegacyValue+"'&$select=cust_SFID";
		
		ODataFeed Fed = app.readFeedSf(edm, query, usedFormat, "cust_Keymapping",userID, password);
		
		String SFValue=null;
		
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
			
				 if (entry1.getValue() != null && entry1.getKey().equalsIgnoreCase("cust_SFID"))
					 SFValue = entry1.getValue().toString() ;
				 	 
			 }
			
		    }
			 //System.out.println("-----------");
		 }
		
		return SFValue;
	}
	


	public String CheckDate(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)
			throws Exception {

		String strDate = ((MetaDataObj) rowData.get(index)).getFieldValue();
		Date start = null;

		if (null != strDate && !strDate.isEmpty()) {
			
			try {
			
		 start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(strDate);

			}
			catch(Exception e)
			{
				 return (strDate + "<--Invalid date");
			
			}
		
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);

		String value = cal.get(Calendar.DAY_OF_MONTH)+"/"+ cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR);
		
	
		 
		return value;
	}
}
