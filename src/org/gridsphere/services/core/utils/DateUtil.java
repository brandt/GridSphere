package org.gridsphere.services.core.utils;

import org.gridsphere.portlet.User;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.*;


/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: DateUtil.java 4496 2006-02-08 20:27:04Z wehrens $
 */

public class DateUtil {

    /**
     * Converts the given unix time stamp string to java time.
     * @param timeStamp The time stamp string
     * @return The resulting java date
     */
    public static long unixTimeStampToJavaTime(String timeStamp) {
        if (timeStamp == null || timeStamp.equals("")) return 0;
        return (long)(Double.parseDouble(timeStamp) * 1000);
    }

    /**
     * Returns a localized date for the given time using the "user.timezone" attribute and the locale stored in the request.
     * Displays the date and time formats using the <code>DateFormat.MEDIUM</code> format.
     * @param request The portlet request
     * @param milisec The time to display
     * @return The resulting date string
     * @see DateFormat
     */
    public static String getLocalizedDate(PortletRequest request, long milisec) {
        Locale locale = request.getLocale();
        TimeZone tz = getTimeZone(request);
        return getLocalizedDate(locale, tz, milisec, DateFormat.MEDIUM, DateFormat.MEDIUM);
    }

    /**
     * Returns a localized date for the given time using the "user.timezone" attribute and the locale stored in the request.
     * @param request The portlet request
     * @param milisec The time to display
     * @param dateFormat The date format
     * @param timeFormat  The time format
     * @return The resulting date string
     * @see DateFormat
     */
    public static String getLocalizedDate(PortletRequest request, long milisec, int dateFormat, int timeFormat) {
        Locale locale = request.getLocale();
        TimeZone tz = getTimeZone(request);
        return getLocalizedDate(locale, tz, milisec, dateFormat, timeFormat);
    }

    /**
     * Returns a localized date for the given time using the "user.timezone" attribute and the locale stored in the request.
     * Displays the date and time formats using the <code>DateFormat.MEDIUM</code> format.
     * @param request The portlet request
     * @param milisec The time to display
     * @return The resulting date string
     * @see DateFormat
     */
    public static String getLocalizedDate(HttpServletRequest request, long milisec) {
        Locale locale = request.getLocale();
        TimeZone tz = getTimeZone(request);
        return getLocalizedDate(locale, tz, milisec, DateFormat.MEDIUM, DateFormat.MEDIUM);
    }

    /**
     * Returns a localized date for the given time using the "user.timezone" attribute and the locale stored in the request.
     * @param request The portlet request
     * @param milisec The time to display
     * @param dateFormat The date format
     * @param timeFormat  The time format
     * @return The resulting date string
     * @see DateFormat
     */
    public static String getLocalizedDate(HttpServletRequest request, long milisec, int dateFormat, int timeFormat) {
        Locale locale = request.getLocale();
        TimeZone tz = getTimeZone(request);
        return getLocalizedDate(locale, tz, milisec, dateFormat, timeFormat);
    }

    /**
     * Returns a localized date for the given locale and time zone.
     * Displays the date and time formats using the <code>DateFormat.MEDIUM</code> format.
     * @param locale The locale
     * @param tz The time zone
     * @param milisec The time to display
     * @return The resulting date string
     * @see DateFormat
     */
    public static String getLocalizedDate(Locale locale, TimeZone tz, long milisec) {
        return getLocalizedDate(locale, tz, milisec, DateFormat.MEDIUM, DateFormat.MEDIUM);
    }


    /**
     * Returns a localized date for the given locale and time zone.
     * Displays the date and time formats using the <code>DateFormat.MEDIUM</code> format.
     * @param locale The locale
     * @param tz The time zone
     * @param milisec The time to display
     * @param dateFormat The date format
     * @param timeFormat  The time format
     * @return The resulting date string
     * @see DateFormat
     */
    public static String getLocalizedDate(Locale locale, TimeZone tz, long milisec, int dateFormat, int timeFormat) {
        if (milisec == 0) {
            return "";
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (tz == null) {
            tz = TimeZone.getDefault();
        }
        Calendar cal = Calendar.getInstance(tz, locale);
        cal.setTimeInMillis(milisec);
        DateFormat uformatter = DateFormat.getDateTimeInstance(timeFormat, dateFormat, locale);
        uformatter.setCalendar(cal);
        return uformatter.format(cal.getTime());
    }

    /**
     * Returns the time zone for the user.timezone portlet attribute in the given porlet request
     * @param request the portlet request
     * @return The time zone
     */
    public static TimeZone getTimeZone(PortletRequest request) {
        Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
        String tzStr = (String) userInfo.get(User.TIMEZONE);
        TimeZone tz;
        if (tzStr == null) {
            tz = TimeZone.getDefault();
        } else {
            tz = TimeZone.getTimeZone(tzStr);
        }
        return tz;
    }

    /**
     * Returns the time zone for the user.timezone portlet attribute in the given porlet request
     * @param request the portlet request
     * @return The time zone
     */
    public static TimeZone getTimeZone(HttpServletRequest request) {
        Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
        String tzStr = (String) userInfo.get(User.TIMEZONE);
        TimeZone tz;
        if (tzStr == null) {
            tz = TimeZone.getDefault();
        } else {
            tz = TimeZone.getTimeZone(tzStr);
        }
        return tz;
    }

    /**
     * Returns localized date for the timezone the user is in.
     *
     * @param user       User
     * @param locale     locale of the resulting output
     * @param milisec    time to convert in milisec
     * @param dateFormat the format for the date (DateFormat.{FULL|LONG|MEDIUM|SHORT}) @see DateFormat
     * @param timeFormat the format for the time (DateFormat.{FULL|LONG|MEDIUM|SHORT}) @see DateFormat
     * @return localized time string with timezones offset
     */
    public static String getLocalizedDate(User user, Locale locale, long milisec, int dateFormat, int timeFormat) {
        TimeZone tz;
        String tzStr = (String) user.getAttribute(User.TIMEZONE);
        if (tzStr == null) {
            tz = TimeZone.getDefault();
        } else {
            tz = TimeZone.getTimeZone(tzStr);
        }
        return getLocalizedDate(locale, tz, milisec, dateFormat, timeFormat);
    }


    /**
     * Returns a map of localized nice TimeZonesNames.
     * Does not return localized names yet.
     *
     * @return Map containing TimeZoneIDs as Key and localized names as values
     */
    public static Map getLocalizedTimeZoneNames() {
        Map result = new HashMap();
        String availableTZ[] = TimeZone.getAvailableIDs();
        for (int i = 0; i < availableTZ.length; i++) {
            if ((availableTZ[i].indexOf("/") > 1) && (!availableTZ[i].startsWith("System"))) {
                result.put(availableTZ[i], availableTZ[i]);
            }
        }
        return result;
    }
}
