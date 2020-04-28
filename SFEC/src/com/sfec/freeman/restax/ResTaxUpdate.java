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

public class ResTaxUpdate {

	public static final String APPLICATION_JSON = "application/json";
	public static String serviceUrl = null;
	public static String usedFormat = null;
	public static Edm edm = null;
	public static OlingoV2Main app;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		serviceUrl = "https://api8preview.sapsf.com/odata/v2/cust_ResTaxAreas";
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

		List<ResTax> ResTax = new ArrayList();

		File file = new File("/Users/baps/Documents/FREEMAN/Workspace/Cutover/test/TaxCreate20200101.txt");
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

				ResTax ResTaxdata = createResTax(attributes);
				ResTax.add(ResTaxdata);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read record and update

		for (ResTax ResTaxdata : ResTax) {
			update(ResTaxdata);

		}

	}

	private static ResTax createResTax(String[] metadata) {

		String code;
		Calendar startDate=null;


		
		Calendar calendar = new GregorianCalendar(2020,0,01);



		
		
		ResTax ResTax = new ResTax();
		ResTax.startDate = calendar;
		ResTax.code = metadata[0];


		return ResTax;
	}

	public static void update(ResTax ResTaxdata) {

		String Status = null;
		Map<String, Object> data = new HashMap<String, Object>();
//	data.put("__metadata", "{uri:cust_W4IntegrationONBMapping}");
		data.put("externalCode", ResTaxdata.code);
		data.put("effectiveStartDate", ResTaxdata.startDate);
		
		
		
		ODataEntry createdEntry = null;
		try {
			Status = app.createEntrySf(edm, serviceUrl, usedFormat, "cust_ResTaxAreas", data,
					"SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(Status);

	}

}
