package com.nju.onlineexam.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public static String TimestampToString(Timestamp timestamp){

        DateFormat dateFormat = new SimpleDateFormat(defaultFormatString);
        return dateFormat.format(timestamp);

    }

}
