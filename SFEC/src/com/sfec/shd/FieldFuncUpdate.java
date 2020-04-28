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

public class FieldFuncUpdate { 

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

		List<FieldFunc> FieldFuncSetLst = new ArrayList();

		File file = new File("/Users/baps/Documents/BAPS/SHISEIDO - DM Project/02 Development/Config Files/FIELD_FUNC.txt");
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

				FieldFunc FieldFuncSet = createFieldFuncSet(attributes);
				FieldFuncSetLst.add(FieldFuncSet);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read record and update


		for (FieldFunc FieldFunc : FieldFuncSetLst) {
				update(FieldFunc);
		//	System.out.println(FieldSet.NAME);
		}

	}

	private static FieldFunc createFieldFuncSet(String[] metadata) {


		
		
		
		FieldFunc FieldFunc = new FieldFunc();
		FieldFunc.Client = metadata[0];
		FieldFunc.TEMPLATEGRP = metadata[1];
		FieldFunc.TEMPLATE = metadata[2];
		FieldFunc.FIELD_NAME = metadata[3];
		FieldFunc.SEQNR = Integer.parseInt(metadata[4]);
		FieldFunc.FUNCTION_ID = metadata[5];
		FieldFunc.FUNCTION_ROUTINE = metadata[6];
	

		
		
	
		return FieldFunc;
	}

	public static void update(FieldFunc FieldFunc) {

		String Status = null;
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("Client", FieldFunc.Client);
		data.put("TEMPLATEGRP", FieldFunc.TEMPLATEGRP);
		data.put("TEMPLATE", FieldFunc.TEMPLATE);
		data.put("SEQNR", FieldFunc.SEQNR);
		data.put("FIELD_NAME", FieldFunc.FIELD_NAME);
		data.put("FUNCTION_ID", FieldFunc.FUNCTION_ID);
		data.put("FUNCTION_ROUTINE", FieldFunc.FUNCTION_ROUTINE);


		
		
		ODataEntry createdEntry = null;
		try {
			Status = app.createEntrySf(edm, serviceUrl, usedFormat, "FIELD_FUNC", data,
					"DM_APP_USER", "Welcome123456789$X$$");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(Status);

	}

}
