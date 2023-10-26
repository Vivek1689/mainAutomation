package utils.dateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /*To get date after/before some specific year*/
    public static String getFuturePastYearDate(String format, int years) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, years);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currDate = sdf.format(c.getTime());
        System.out.println(currDate);
        return currDate;
    }

    /*To get date after/before some specific months*/
    public String getFuturePastMonthDate(String format, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, months);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currDate = sdf.format(c.getTime());
        System.out.println(currDate);
        return currDate;
    }

    /*To get date after/before some specific days*/
    public static String getFuturePastDaysDate(String format, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currDate = sdf.format(c.getTime());
        System.out.println("My Date is " + currDate);
        return currDate.toString();
    }

    /*To get date after/before some specific hours*/
    public String getFuturePastHoursDate(String format, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR, hours);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currDate = sdf.format(c.getTime());
        System.out.println("My Date is " + currDate);
        return currDate.toString();
    }

    /*To get date after/before some specific minutes*/
    public static String getFuturePastMinutesDate(String format, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, minutes);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currDate = sdf.format(c.getTime());
        return currDate.toString();
    }

    // New Date functions


    public static void main(String[] args) {
        System.out.println( getFuturePastYearDate("MMM d,yyyy" , 2));
    }

}
