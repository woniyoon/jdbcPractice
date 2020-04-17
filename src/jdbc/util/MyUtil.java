package jdbc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyUtil {
	public static String getDate(int day) {
		
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, day);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(currentDate.getTime());
	}
}
