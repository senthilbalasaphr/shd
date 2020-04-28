package com.sfec.freeman.EmployeeUpdate;

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

public class EmpJobUpdate {

	public static final String APPLICATION_JSON = "application/json";
	public static String serviceUrl = null;
	public static String usedFormat = null;
	public static Edm edm = null;
	public static OlingoV2Main app;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		serviceUrl = "https://api8preview.sapsf.com/odata/v2/EmpJob";
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

		List<EmpJob> EmpJoblst = new ArrayList();

		File file = new File("/Users/baps/Documents/FREEMAN/Workspace/Testing/Data Prep/EmpJob.txt");
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

				EmpJob EmpJob = createEmpJob(attributes);
				EmpJoblst.add(EmpJob);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read record and update

		for (EmpJob EmpJob : EmpJoblst) {
			update(EmpJob);

		}

	}

	private static EmpJob createEmpJob(String[] metadata) {

		String userId;
		Calendar startDate=null;
		int seqNumber=01;
		String eventReason;

		
		Calendar calendar = new GregorianCalendar(2019,9,01);



//		Key

		userId = metadata[0];
//		seqNumber = Integer.parseInt(metadata[2]) ;

//		Data
		eventReason = metadata[3];
		
		
		
		
		EmpJob EmpJob1 = new EmpJob();
		EmpJob1.userId = userId;
		EmpJob1.startDate = calendar;
		EmpJob1.seqNumber = seqNumber;
		EmpJob1.eventReason = eventReason;
		EmpJob1.company=metadata[4];
		EmpJob1.customString2=metadata[5];
		EmpJob1.jobCode=metadata[6];
		EmpJob1.department=metadata[7];
		EmpJob1.division=metadata[8] ;
		EmpJob1.location=metadata[9] ;
		EmpJob1.standardHours=metadata[10] ;
		EmpJob1.managerId=metadata[11] ;
		EmpJob1.customString40=metadata[12] ;
		EmpJob1.customString44=metadata[13] ;

		return EmpJob1;
	}

	public static void update(EmpJob EmpJob) {

		String Status = null;
		Map<String, Object> data = new HashMap<String, Object>();
//	data.put("__metadata", "{uri:cust_W4IntegrationONBMapping}");
		data.put("userId", EmpJob.userId);
		data.put("startDate", EmpJob.startDate);
		
		EmpJob.seqNumber=01;
		data.put("seqNumber", EmpJob.seqNumber);


		
		data.put("eventReason", EmpJob.eventReason);
		data.put("company", EmpJob.company);
		data.put("customString2", EmpJob.customString2);
		data.put("jobCode", EmpJob.jobCode);
		data.put("department", EmpJob.department);
		data.put("division", EmpJob.division);
		data.put("location", EmpJob.location);
	//	data.put("standardHours", EmpJob.standardHours);
		data.put("managerId", EmpJob.managerId);
		data.put("customString40", EmpJob.customString40);
		data.put("customString44", EmpJob.customString44);
		
		
		ODataEntry createdEntry = null;
		try {
			Status = app.createEntrySf(edm, serviceUrl, usedFormat, "EmpJob", data,
					"SFEC_EE_OASS@freemantest", "SFEC_EE_OASS01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(Status);

	}

}
