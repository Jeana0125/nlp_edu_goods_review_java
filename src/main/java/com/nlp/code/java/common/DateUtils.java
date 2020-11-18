package com.nlp.code.java.common;

import java.text.SimpleDateFormat;
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

}
