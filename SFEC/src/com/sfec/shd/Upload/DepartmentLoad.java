package com.sfec.shd.Upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.sfec.shd.FieldSet;


public class DepartmentLoad {

	public static void main(String[] args) throws ParseException {
		

		String cust_Company="07";
		String cust_ObjectType="01";
		int i=100;
		
		
//************Read File - Department*************//
		
		File file = new File("/Users/baps/Documents/FREEMAN/Workspace/w4 state forms/ValueMap.txt");
		FileReader fr = null;
		
		List<Department> DepartmentList = new ArrayList();

		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			
		e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String line;
		try {
			while ((line = br.readLine()) != null) {
					
				String[] attributes = line.split("\t");
				Department d1 = new Department();
				Date effectivedate=null;
				effectivedate= new SimpleDateFormat("MM/DD/YYYY").parse(attributes[2]);
				Department d = d1.getDepartment(attributes[0] ,
												attributes[1],
												effectivedate,
											   attributes[3],
											   attributes[4],
											   attributes[5],
											   attributes[6],
											   attributes[7],
											   attributes[8],
											   attributes[9],
											   attributes[10],
											   attributes[11],
											   attributes[12],
											   attributes[13],
											   attributes[14],
											   attributes[15],
											   attributes[16],
											   attributes[17],
											   attributes[18],
											   attributes[19],
											   attributes[20],
											   attributes[21],
											   attributes[22],
											   attributes[23]
											   );
				DepartmentList.add(d);
		
				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


//********prepare data************//
		
		for (Department d : DepartmentList) {
			d.externalCode = d.getDepartmentExternalCode( String.valueOf(i), d.effectiveStartDate, cust_Company, cust_ObjectType, d.externalCode);
			String[] str=d.effectiveStatus.split("-");
			if (str[1]!=null){
			d.effectiveStatus=str[1];
			}
			

	}
		
		
		
		

	}

}
