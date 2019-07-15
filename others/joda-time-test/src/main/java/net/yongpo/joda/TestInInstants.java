package net.yongpo.joda;

import org.joda.time.DateTime;

/**
 * Created by benben on 2016/4/4.
 */
public class TestInInstants {
    public static void main(String[] args) {
        DateTime dt = new DateTime();  // current time
        int month = dt.getMonthOfYear();     // gets the current month
        //int month = dt.monthOfYear().get();  // alternative way to get value
        String monthStr = dt.monthOfYear().getAsText();  // gets the month name
        System.out.println("month = [" + month + "]");
        System.out.println("monthStr = [" + monthStr + "]");
    }
}
