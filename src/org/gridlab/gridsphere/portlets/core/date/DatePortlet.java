
package org.gridlab.gridsphere.portlets.core.date;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.AbstractPortlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Locale;
import java.text.DateFormat;


public class DatePortlet extends AbstractPortlet {

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        Locale locale;
        TimeZone tz;
        User user = request.getUser();
        String tzStr = (String)user.getAttribute(User.TIMEZONE);
        locale = request.getLocale();
        if (tzStr == null) {
            tz = TimeZone.getDefault();
        } else {
            tz = TimeZone.getTimeZone(tzStr);
        }
        if (locale==null) {
            locale = Locale.getDefault();
        }
        Calendar date = Calendar.getInstance(tz, locale);
        DateFormat uformater = DateFormat.getDateInstance(DateFormat.LONG, locale);
        uformater.setCalendar(date);
        request.setAttribute("date", uformater.format(date.getTime()));
        getPortletConfig().getContext().include("/jsp/date/date.jsp", request, response);
    }

}
