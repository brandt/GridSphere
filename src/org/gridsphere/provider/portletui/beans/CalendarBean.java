package org.gridsphere.provider.portletui.beans;

import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;

import javax.portlet.RenderResponse;


/**
 * The <code>TextBean</code> represents text to be displayed
 */
public class CalendarBean extends InputBean implements TagBean {

    public static final String NAME = "ca";

    public RenderResponse renderResponse;

    /**
     * Constructs a default text bean
     */
    public CalendarBean() {
        super(CalendarBean.NAME);
        this.inputtype = "text";
    }

    /**
     * Constructs a text bean using a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public CalendarBean(String beanId) {
        super(CalendarBean.NAME);
        this.beanId = beanId;
        this.inputtype = "text";
    }

    public RenderResponse getRenderResponse() {
        return renderResponse;
    }

    public void setRenderResponse(RenderResponse renderResponse) {
        this.renderResponse = renderResponse;
    }


    public String toStartString() {

        PortalConfigService configService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        // deal with ROOT context case
        String contextPath = configService.getProperty("gridsphere.deploy");
        if (!contextPath.equals("")) contextPath = "/" + contextPath;
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/scw.js");
        StringBuffer sb = new StringBuffer();
        sb.append("<input " + getFormattedCss() + " ");
        sb.append("id=\"" + id + "\" ");
        sb.append("type=\"text\" ");
        String sname = createTagName(name);
        sb.append("name=\"" + sname + "\" ");
        if (value != null) sb.append("value=\"" + value + "\" ");
        if (size != 0) sb.append("size=\"" + size + "\" ");
        if (maxlength != 0) sb.append("maxlength=\"" + maxlength + "\" ");
        sb.append(checkReadOnly());
        sb.append(checkDisabled());
        sb.append("/>");
        sb.append("<button class=\"cal\" type=\"submit\" title=\"Calendar\" alt=\"Calendar\" onclick=\"scwShow(document.getElementById('" + id + "'),this);\">Calendar</button>");
        return sb.toString();
        //return "<input id='" + id + "' type='text' value='" + value + "'/><img src='images/scw.gif' title='Click Here' alt='Click Here' onclick=\"scwShow(document.getElementById('date2'),this,3);\"/>";
    }

}



