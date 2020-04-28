package com.sf.Olingo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;

public class UpdateONBValueMapping {
	public static final String APPLICATION_JSON = "application/json";
	public static String serviceUrl =null;
	public static String usedFormat=null;
	public static Edm edm=null;
	public static OlingoV2Main app;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 serviceUrl = "https://api8preview.sapsf.com/odata/v2/cust_W4IntegrationONBValueMapping";
		 usedFormat = APPLICATION_JSON;
		

		 app = new OlingoV2Main();
		
		try {
			edm = app.readEdmSf(serviceUrl, "SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ODataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
// Read files form local or FTP
		
		
		List<cust_W4IntegrationONBValueMapping> W4MappingLst = new ArrayList();
		
		File file = new File("/Users/baps/Documents/FREEMAN/Workspace/w4 state forms/ValueMap.txt");
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				// process the line

				String[] attributes = line.split("\t");

				cust_W4IntegrationONBValueMapping W4Mapping = createW4IntegrationONBValueMapping(attributes);
				W4MappingLst.add(W4Mapping);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


//Read record and update
		
		for (cust_W4IntegrationONBValueMapping ONBMapping : W4MappingLst) {
			update(ONBMapping);
			
		}
	
	

	}
	
	private static cust_W4IntegrationONBValueMapping createW4IntegrationONBValueMapping(String[] metadata) {
		
		String externalCode;
		String externalName;
		String cust_valueMapCode;
		String cust_ONBValue;
		String cust_ECValue="";
		int x=0;
		x=metadata.length;
		
		externalCode = metadata[0];
		externalName = metadata[1];
		cust_valueMapCode = metadata[2];
		cust_ONBValue = metadata[3];
		System.out.println(x);
		if ( x == 5)
		cust_ECValue = metadata[4];

		
		return new cust_W4IntegrationONBValueMapping(externalCode, externalName,cust_valueMapCode,cust_ONBValue,cust_ECValue);
	}
	
	
	public static void update(cust_W4IntegrationONBValueMapping ONBMapping )
	{

		String Status=null;
		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("__metadata", "{uri:cust_W4IntegrationONBMapping}");
	    data.put("externalCode", ONBMapping.getExternalCode());
	    data.put("externalName", ONBMapping.getExternalName());
	    data.put("cust_valueMapCode", ONBMapping.getCust_valueMapCode());
	    data.put("cust_ONBValue", ONBMapping.getCust_ONBValue());
	    data.put("cust_ECValue", ONBMapping.getCust_ECValue());


	    try {
	    	Status = app.createEntrySf(edm, serviceUrl, usedFormat, "cust_W4IntegrationONBValueMapping", data,"SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
	    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	    System.out.println(Status);


	}

}
