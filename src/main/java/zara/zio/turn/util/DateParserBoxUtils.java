package zara.zio.turn.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DateParserBoxUtils {
	
	public static List<String> Paser(Date start, Date end) {
		
		Calendar cal = Calendar.getInstance();
		String nal = "";
//		String now = "";
		List<String> dateList = new ArrayList<>();
		
		long diff = end.getTime() - start.getTime(); 
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
//		now = cal.get(Calendar.YEAR) + "";
//		now += cal.get(Calendar.MONTH) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : "" + (cal.get(Calendar.MONTH) + 1);
//		now += cal.get(Calendar.DATE)  < 10 ? "0" + cal.get(Calendar.DATE) : "" + cal.get(Calendar.DATE);
		
		for(int i=0; i<diffDays+1; i++) {
			cal.setTime(start);
			int date = cal.get(Calendar.DATE) + i;
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, date);
			nal = cal.get(Calendar.YEAR) + "";
			nal += cal.get(Calendar.MONTH) < 10 ? "0" + cal.get(Calendar.MONTH) : "" + cal.get(Calendar.MONTH);
			nal += cal.get(Calendar.DATE)  < 10 ? "0" + cal.get(Calendar.DATE) : "" + cal.get(Calendar.DATE);
			dateList.add(nal);
//			if(Integer.parseInt(now) == Integer.parseInt(nal)) {
//				return dateList;
//			}
		}
		
		return dateList;
	}

	
}
