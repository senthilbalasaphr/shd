package com.sfec.shd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import com.sfec.freeman.EmployeeUpdate.EmpJob;

public class FieldMapUpdate {

	public static final String APPLICATION_JSON = "application/json";
	public static String serviceUrl = null;
	public static String usedFormat = null;
	public static Edm edm = null;
	public static OlingoV2_1Main app;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
//https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata/FIELDSET
		
		serviceUrl = "https://bs1e3cc05196.eu2.hana.ondemand.com/sap/SHDdev/services.xsodata";
		usedFormat = APPLICATION_JSON;
		app = new OlingoV2_1Main();

		// Read Edm
		try {
			edm = app.readEdmSf(serviceUrl, "DM_APP_USER", "Welcome123456789$X$$");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ODataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Read files form local or FTP

		List<FieldSet> FieldSetLst = new ArrayList();

		File file = new File("/Users/baps/Documents/BAPS/SHISEIDO - DM Project/02 Development/Config Files/FIELD_MAPP.txt");
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

				FieldSet FieldSet = createFieldSet(attributes);
				FieldSetLst.add(FieldSet);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read record and update

		for (FieldSet FieldSet : FieldSetLst) {
				update(FieldSet);
		//	System.out.println(FieldSet.NAME);
		}

	}

	private static FieldSet createFieldSet(String[] metadata) {


		
		
		
		FieldSet FieldSet = new FieldSet();
		FieldSet.Client = metadata[0];
		FieldSet.TEMPLATEGRP = metadata[1];
		FieldSet.TEMPLATE = metadata[2];
		FieldSet.SEQNR = Integer.parseInt(metadata[3]);
		FieldSet.NAME = metadata[4];
		FieldSet.NAME_DESC = metadata[5];
	
		FieldSet.TYPE = metadata[6];
		FieldSet.FIELD_LENGTH = Integer.parseInt(metadata[7]);
		FieldSet.FIELD_REQUIRED = metadata[8];
		FieldSet.FIELD_Key = metadata[9];
		FieldSet.VALUE_MAPPING = metadata[10];
//		FieldSet.FUNCTION_ID = metadata[11];
//
//		FieldSet.FUNCTION_ROUTINE = metadata[12];
		
		
	
		return FieldSet;
	}

	public static void update(FieldSet FieldSet) {

		String Status = null;
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("Client", FieldSet.Client);
		data.put("TEMPLATEGRP", FieldSet.TEMPLATEGRP);
		data.put("TEMPLATE", FieldSet.TEMPLATE);
		data.put("SEQNR", FieldSet.SEQNR);
		data.put("FIELD_NAME", FieldSet.NAME);
		data.put("FIELD_DESCRIPTION", FieldSet.NAME_DESC);
		data.put("FIELD_TYPE", FieldSet.TYPE);
		data.put("FIELD_LENGTH", FieldSet.FIELD_LENGTH);
		data.put("FIELD_REQUIRED", FieldSet.FIELD_REQUIRED);
		data.put("FIELD_KEY", FieldSet.FIELD_Key);
		data.put("VALUE_MAPPING", FieldSet.VALUE_MAPPING);

		
		
		ODataEntry createdEntry = null;
		try {
			Status = app.createEntrySf(edm, serviceUrl, usedFormat, "FIELDSET", data,
					"DM_APP_USER", "Welcome123456789$X$$");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(Status);

	}

}
