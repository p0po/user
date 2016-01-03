package net.yongpo.utils;

import org.joda.time.DateTime;

import java.util.Random;

/**
 * Created by benben on 2015/9/20.
 */
public class DuThread1 implements Runnable {
    static Random random = new Random();
    @Override
    public void run() {
        int month = random.nextInt(12);
        month=month==0?1:month;
        int day = random.nextInt(28);
        day = day==0?1:day;
        DateUtils.get0OClock(new DateTime().withMonthOfYear(month).withDayOfMonth(day).toDate());
    }
}
