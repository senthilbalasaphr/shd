package com.capt.dm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		sdf.setTimeZone(TimeZone.getTimeZone("SGT"));
		 String d = sdf.format(new Date(Long.parseLong("-2208988800000") ));
		    System.out.println(d);


	}

}
