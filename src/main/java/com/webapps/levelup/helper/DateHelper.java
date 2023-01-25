package com.webapps.levelup.helper;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Component
public class DateHelper {

    /**
     * Get current datetime.
     *
     * @param pattern - datetime format (YYYY-MM-dd HH:mm:ss)
     * @return String
     */
    public static String currentDate(String pattern) {
        Calendar cal = Calendar.getInstance();
        Date dateNow = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(dateNow);
    }

    /**
     * Returns Date at the end of day that begins at given Date.
     *
     * @param dayBegin Date
     * @return Date
     */
    public static Date dayEnd(Date dayBegin) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dayBegin);
        cal.add(Calendar.HOUR, 24);
        return cal.getTime();
    }

    /**
     * Returns Date of Java epoch 1970.
     *
     * @return Date
     */
    public static Date sinceEpoch() {
        return new Date(java.time.Instant.now().getEpochSecond());
    }

    /**
     * Current datetime in given format (yyyy-MM-dd HH:mm:ss:SSS).
     *
     * @param format - datetime format
     * @return String
     */
    public static String dateFormat(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        return formatter.format(date);
    }

    /**
     * Date to String.
     *
     * @param date   Date
     * @param format String (yyyy-MM-dd)
     * @return String
     */
    public static String date2string(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * String to Date.
     *
     * @param date   String
     * @param format String
     * @return Date
     */
    public static Date string2date(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault());
        LocalDate localDate = LocalDate.parse(date, formatter);
        return java.sql.Date.valueOf(localDate);
    }

    /**
     * Date to unix.
     *
     * @param date Date
     * @return long
     */
    public static long date2unix(Date date) {
        return date.getTime() / 1000;
    }

    /**
     * Increase - Decrease Date.
     *
     * @param date String
     * @param days long
     * @return String
     */
    public static String increaseDecreaseDate(String date, long days) {
        return LocalDate.parse(date).plusDays(days).toString();
    }

    /**
     * Return current Date.
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * Adds or substructs (when negative number is given) number of days from current date.
     *
     * @param days Integer
     * @return Date
     */
    public static Date addOrSubstructDaysFromCurrentDate(Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
