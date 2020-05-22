package com.capt.dm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//0123456789
		//03/04/1996
		String startDate = "03/04/1996";
		startDate = startDate.substring(6,10)+"-"+startDate.substring(3,5)+"-"+startDate.substring(0,2);
		System.out.println(startDate);
		//System.out.println(startDate.substring(6,4));
		//System.out.println(startDate.substring(3,5));
		//System.out.println(startDate.substring(0,2));

	//	19-04-03
	}

}
