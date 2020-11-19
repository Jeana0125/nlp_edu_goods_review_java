package com.nlp.code.java.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 현재 시간을 일자 YYYYMMDD 반환
     * @return
     */
    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(date);
        return dateStr;
    }
    
    /**
     * date의 담달 반환
     * @param date
     * @return
     */
    public static String getNextMonth(String month) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
    	SimpleDateFormat bsdf = new SimpleDateFormat("yyyyMM"); 
    	String date = month + "01";
    	Date dd;
    	
		try {
			dd = sdf.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.set(Calendar.DAY_OF_MONTH,1);
			calendar.add(Calendar.MONTH, 1);
			
			return bsdf.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

}
