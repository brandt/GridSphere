package org.gridlab.gridsphere.services.core.utils;

import org.gridlab.gridsphere.portlet.User;

import java.text.DateFormat;
import java.util.*;


/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class DateUtil {

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

        TimeZone tz = null;
        String tzStr = (String) user.getAttribute(User.TIMEZONE);
        if (tzStr == null) {
            tz = TimeZone.getDefault();
        } else {
            tz = TimeZone.getTimeZone(tzStr);
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        Calendar cal = Calendar.getInstance(tz, locale);
        cal.setTimeInMillis(milisec);

        DateFormat uformatter = DateFormat.getDateTimeInstance(timeFormat, dateFormat, locale);
        uformatter.setCalendar(cal);

        return uformatter.format(cal.getTime());
    }


    /**
     * Returns a map of localized nice TimeZonesNames.
     * Does not return localized names yet.
     *
     * @param locale localized to that locale
     * @return Map containing TimeZoneIDs as Key and localized names as values
     */
    public static Map getLocalizedTimeZoneNames(Locale locale) {

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
