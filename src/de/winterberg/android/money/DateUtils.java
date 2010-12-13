package de.winterberg.android.money;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Benjamin Winterberg
 */
public final class DateUtils {

    private DateUtils() {
        // private
    }

    public static long newTimestamp(int year, int month, int day, int hour, int minute, int second) {
        return new GregorianCalendar(year, month, day, hour, minute, second).getTimeInMillis();
    }

    public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
        return new Date(newTimestamp(year, month, day, hour, minute, second));
    }

    public static long newTimestamp(int year, int month, int day) {
        return newTimestamp(year, month, day, 0, 0, 0);
    }

    public static Date newDate(int year, int month, int day) {
        return new Date(newTimestamp(year, month, day));
    }

    public static Date rollClock(Date date, int hour, int minute, int second) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date rollDay(Date date, int month, int day) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static Date now() {
        return new Date();
    }

    public static Date today() {
        return today(0, 0, 0);
    }

    public static Date today(int hour, int minute, int second) {
        return rollClock(new Date(), hour, minute, second);
    }

    public static Date yesterday() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date yesterday(int hour, int minute, int second) {
        return rollClock(yesterday(), hour, minute, second);
    }

    public static Date tomorrow() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.roll(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date tomorrow(int hour, int minute, int second) {
        return rollClock(tomorrow(), hour, minute, second);
    }
}