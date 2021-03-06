package com.shd.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capt.dm.model.MetaDataObj;

public class Utility {

	public String getOdataEpochiToJava(String Epochi) {
		
		


		Epochi = Epochi.substring(Epochi.indexOf("(") + 1,Epochi.indexOf(")"));
		if (Epochi.contains("+")) {
			Epochi = Epochi.substring(0,Epochi.indexOf("+") );
		}

		String d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			sdf.setTimeZone(TimeZone.getTimeZone("SGT"));
			d = sdf.format(new Date(Long.parseLong(Epochi)));
		} catch (Exception e) {

		}
		return d;

	}
	
	public String getOdataEpochiToJavaDsh(String Epochi) {
		
		


		Epochi = Epochi.substring(Epochi.indexOf("(") + 1,Epochi.indexOf(")"));
		if (Epochi.contains("+")) {
			Epochi = Epochi.substring(0,Epochi.indexOf("+") );
		}

		String d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			sdf.setTimeZone(TimeZone.getTimeZone("SGT"));
			d = sdf.format(new Date(Long.parseLong(Epochi)));
		} catch (Exception e) {

		}
		return d;

	}


}
