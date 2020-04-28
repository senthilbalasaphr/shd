package com.sfec.freeman.restax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;

import com.sf.Olingo.OlingoV2Main;
import com.sf.Olingo.W4IntegrationONBMapping;

public class TaxGrpUpdate {

	public static final String APPLICATION_JSON = "application/json";
	public static String serviceUrl = null;
	public static String usedFormat = null;
	public static Edm edm = null;
	public static OlingoV2Main app;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		serviceUrl = "https://api8preview.sapsf.com/odata/v2/cust_taxGroupKey";
		usedFormat = APPLICATION_JSON;
		app = new OlingoV2Main();

		// Read Edm
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

		List<TaxGrp> TaxGrp = new ArrayList();

		File file = new File("/Users/baps/Documents/FREEMAN/Workspace/Cutover/test/TaxGrpKey2020.txt");
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

				TaxGrp TaxGrpKey = createTaxGrp(attributes);
				TaxGrp.add(TaxGrpKey);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read record and update

		for (TaxGrp TaxGrpKey : TaxGrp) {
			update(TaxGrpKey);

		}

	}

	private static TaxGrp createTaxGrp(String[] metadata) {

		String code;
		Calendar startDate=null;


		
		Calendar calendar = new GregorianCalendar(2020,0,01);



		
		
		TaxGrp TaxGrp = new TaxGrp();
		TaxGrp.code = metadata[0];
		TaxGrp.startDate = calendar;
		TaxGrp.fromZipNo = Long.parseLong(metadata[1]) ;
		TaxGrp.toZipNo = Long.parseLong(metadata[2]);
		
		

		return TaxGrp;
	}

	public static void update(TaxGrp TaxGrpKey) {

		String Status = null;
		Map<String, Object> data = new HashMap<String, Object>();
//	data.put("__metadata", "{uri:cust_W4IntegrationONBMapping}");
		data.put("externalCode", TaxGrpKey.code);
		data.put("externalName", TaxGrpKey.code);
		data.put("effectiveStartDate", TaxGrpKey.startDate);
		data.put("cust_FromZipCode", TaxGrpKey.fromZipNo);
		data.put("cust_ToZipCode", TaxGrpKey.toZipNo);
	    data.put("cust_fromZipNo", TaxGrpKey.fromZipNo);
	    data.put("cust_toZipNo", TaxGrpKey.toZipNo);
		
		
		
		ODataEntry createdEntry = null;
		try {
			Status = app.createEntrySf(edm, serviceUrl, usedFormat, "cust_taxGroupKey", data,
					"SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(Status);

	}

}
