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

    public static Date setClock(Date date, int hour, int minute, int second) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        if (hour > -1)
            cal.set(Calendar.HOUR, hour);
        if (minute > -1)
            cal.set(Calendar.MINUTE, minute);
        if (second > -1)
            cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    public static Date setDate(Date date, int month, int day) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        if (month > -1)
            cal.set(Calendar.MONTH, month);
        if (day > -1)
            cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static Date setDay(Date date, int day) {
        return setDate(date, -1, day);
    }

    public static Date setWeekDay(Date date, int weekDay) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, weekDay);
        return cal.getTime();
    }

    public static Date setMonth(Date date, int month) {
        return setDate(date, month, -1);
    }

    public static Date rollDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.roll(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    public static Date rollMonths(Date date, int months) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.roll(Calendar.MONTH, months);
        return cal.getTime();
    }

    public static Date rollYears(Date date, int years) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.roll(Calendar.YEAR, years);
        return cal.getTime();
    }

    public static Date rollHours(Date date, int hours) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.roll(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    public static Date rollMinutes(Date date, int minutes) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.roll(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static Date rollSeconds(Date date, int seconds) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.roll(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    public static Date now() {
        return new Date();
    }

    public static Date today() {
        return today(0, 0, 0);
    }

    public static Date today(int hour, int minute, int second) {
        return setClock(new Date(), hour, minute, second);
    }

    public static Date yesterday() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date yesterday(int hour, int minute, int second) {
        return setClock(yesterday(), hour, minute, second);
    }

    public static Date tomorrow() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.roll(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date tomorrow(int hour, int minute, int second) {
        return setClock(tomorrow(), hour, minute, second);
    }
}