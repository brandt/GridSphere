
package org.gridsphere.portlets.core.date;

import javax.portlet.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class DatePortlet extends GenericPortlet {

    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        Locale locale;
        TimeZone tz;
        String tzStr = null;
        Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
        tzStr = (userInfo!=null) ? (String) userInfo.get("user.timezone") : null;

        if (tzStr != null) {
            tz = TimeZone.getTimeZone(tzStr);
        } else {
            tz = TimeZone.getDefault();
        }
        locale = request.getLocale();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        Calendar date = Calendar.getInstance(tz, locale);
        DateFormat uformater = DateFormat.getDateInstance(DateFormat.LONG, locale);
        uformater.setCalendar(date);
        request.setAttribute("date", uformater.format(date.getTime()));
        getPortletContext().getRequestDispatcher("/jsp/date/date.jsp").include(request, response);
    }

}
