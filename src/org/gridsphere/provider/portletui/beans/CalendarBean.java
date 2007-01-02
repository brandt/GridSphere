package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.portal.PortalConfigService;

import javax.portlet.RenderResponse;


/**
 * The <code>TextBean</code> represents text to be displayed
 */
public class CalendarBean extends InputBean implements TagBean {

    public RenderResponse renderResponse;

    /**
     * Constructs a default text bean
     */
    public CalendarBean() {
        super(TagBean.CALENDAR_NAME);
        this.inputtype = "text";
    }

    /**
     * Constructs a text bean using a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public CalendarBean(String beanId) {
        this();
        this.beanId = beanId;
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
    }

}



