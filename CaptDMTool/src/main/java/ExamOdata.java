/*public class ExamOdata {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Method meth = Class.forName("com.capt.dm.util.FOUtility").getMethod("getDepartmentID", String.class);
			Object utilObject = Class.forName("com.capt.dm.util.FOUtility").newInstance();
			String value = (String)meth.invoke(utilObject, "Hi");
			String value = (String)Class.forName("com.capt.dm.util.FOUtility").getDeclaredMethod("getDepartmentID", String.class).invoke("");
//			System.out.println("Value:"+value);
			String input = "Mon Jun 18 00:00:00 IST 2012";
			DateTimeFormatter f = DateTimeFormatter.ofPattern( "E MMM dd HH:mm:ss z uuuu" )
			                                       .withLocale( Locale.US );
			ZonedDateTime zdt = ZonedDateTime.parse( input , f );
			LocalDate ld = zdt.toLocalDate();
			DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern( "dd/MM/uuuu" );
			String output = ld.format( fLocalDate) ;
			
			System.out.println( "input: " + input );
			System.out.println( "zdt: " + zdt );
			System.out.println( "ld: " + ld );
			System.out.println( "output: " + output );
		
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
*/

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class ExamOdata {
	 
	public static void main(String[] args) {
		
//		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'"
				+ "HH:mm");
	
		formatter.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		String formatedDate = "2000-01-01T05:30";
		Date today =null;;
		try {
			today = (Date) formatter.parse(formatedDate);
			formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//			dat = formatter.format(date);
//			System.out.println("Date::::" + dat);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Date today = Calendar.getInstance().getTime();
	 
	// Constructs a SimpleDateFormat using the given pattern
	SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
	 
	// format() formats a Date into a date/time string.
	String currentTime = crunchifyFormat.format(today);
	log("Current Time = " + currentTime);
	 
	try {
	 
	// parse() parses text from the beginning of the given string to produce a date.
	Date date = crunchifyFormat.parse(currentTime);
	 
	// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
	long epochTime = date.getTime();
	 
	log("Current Time in Epoch: " + epochTime);
	 
	} catch (ParseException e) {
	e.printStackTrace();
	}
	 
	// Local ZoneID
	ZoneId defaultZoneId = ZoneId.systemDefault();
	log("defaultZoneId: " + defaultZoneId);
	 
	Date date = new Date();
	 
	// Default Zone: UTC+0
	Instant instant = date.toInstant();
	System.out.println("instant : " + instant);
	 
	// Local TimeZone
	LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
	System.out.println("localDateTime : " + localDateTime);
	 
	}
	 
	// Simple logging
	private static void log(String string) {
	System.out.println(string);
	 
	}}