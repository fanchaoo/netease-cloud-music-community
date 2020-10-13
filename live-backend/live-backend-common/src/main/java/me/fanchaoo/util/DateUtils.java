package me.fanchaoo.util;

import com.alibaba.fastjson.JSON;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalTime;

public class DateUtils {
    public static void main(String[] args) {
//        String time = DateTime.now().toString("yyyy-MM-dd ") + "23:59:59";
//        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//        DateTime dateTime = format.parseDateTime(time);
//        System.out.println(dateTime);


//        String time1 = "2020-01-06 01:00:00";
//        String time2 = "2020-01-09 00:00:00";
//        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//        DateTime t1 = format.parseDateTime(time1);
//        DateTime t2 = format.parseDateTime(time2);
//        Period p = new Period(t1, t2, PeriodType.days());
//        int reserveDays = p.getDays();
//        System.out.println(reserveDays);

        String suffix = DateTime.now().toString("_yyyy_MM");
        System.out.println(suffix);
    }
}
