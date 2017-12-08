package com.nju.onlineexam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateHelper {

    private static final String defaultFormatString = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(java.util.Date date){
        return dateToString(date,defaultFormatString);
    }

    public static String dateToString( java.util.Date date,String formatString){
        SimpleDateFormat format=new SimpleDateFormat(formatString);
        return format.format(date);
    }

    public static Date stringToDate(String s){
        SimpleDateFormat format=new SimpleDateFormat(defaultFormatString);
        try {
            return new Date(format.parse(s).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("parse ERROR,format="+s);
        }
    }

}
