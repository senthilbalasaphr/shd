import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestTimeStamp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 Calendar calendar = Calendar.getInstance();
		 
		 calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		
		 Date date = calendar.getTime();
		 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		 
		System.out.println(calendar.getTimeInMillis()); 
		System.out.println(format1.format(date));

	
	
	}

}
