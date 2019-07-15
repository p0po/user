package net.yongpo.utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by benben on 2015/9/19.
 */
public class DUTestThread implements Runnable {
    static DateTime dateTime = new DateTime().withHourOfDay(8).withMinuteOfHour(0).withSecondOfMinute(0);
    static Date staticDate = dateTime.toDate();
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void run() {
        Date date = DateUtils.getCustomClock(new Date(),8,0,00);
        DateUtils.getCustomClock(new Date(),23,0,00);
        if(staticDate.getTime()-date.getTime()>1000){
            System.out.println(simpleDateFormat.format(date));
        }
    }
}
