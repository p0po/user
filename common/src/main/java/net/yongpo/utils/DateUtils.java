/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yongpo.utils;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sobranie
 */
public class DateUtils {

    private static final GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

    public static final Date FIRST_DATE = new Date(0);

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static final  String DATE_FORMAT_TEMPLATE = "yyyy-MM-dd";

    private static final  String DATETIME_FORMAT_TEMPLATE = "yyyy-MM-dd HH:mm:ss";

    static Random random = new Random();
    /**
     * return the 0'clock time for a date, like 2013/8/1 22:59:59
     *
     * @param date
     * @return
     */
    public static Date getCustomClock(Date date, int hourOfDay, int minute, int second) {
        if (date == null) {
            return null;
        }
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hourOfDay, minute, second);
        return calendar.getTime();
    }


    public static List<Date> listDates(Date start, Date end) {
        List<Date> dates = new ArrayList<>();

        Date date = start;
        calendar.setTime(start);
        while (date.before(end)) {
            dates.add(date);
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            date = calendar.getTime();
        }
        if (date.equals(end)) {
            dates.add(end);
        }

        return dates;
    }

    public static Date get0OClock(Date date) {
        if (date == null) {
            return null;
        }

        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }



    private static void stop(int inte){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
