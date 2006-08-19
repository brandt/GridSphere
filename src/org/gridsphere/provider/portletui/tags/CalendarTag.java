package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.portletui.beans.CalendarBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>TextFieldTag</code> represents a text field element
 */
public class CalendarTag extends BaseComponentTag {

    private transient static PortletLog log = SportletLog.getInstance(CalendarTag.class);

    protected CalendarBean calendarBean = null;
    protected int size = 0;
    protected int maxlength = 0;

    /**
     * Returns the (html) size of the field
     *
     * @return size of the field
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the (html) size of the field
     *
     * @param size size of the field
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the (html) max length of the field
     *
     * @return the max length of the field
     */
    public int getMaxlength() {
        return maxlength;
    }

    /**
     * Sets the (html) max length of the field
     *
     * @param maxlength the max length of the field
     */
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            calendarBean = (CalendarBean) getTagBean();
            if (calendarBean == null) {
                //log.debug("Creating new text field bean");
                calendarBean = new CalendarBean();
                if (maxlength != 0) calendarBean.setMaxLength(maxlength);
                if (size != 0) calendarBean.setSize(size);
                this.setBaseComponentBean(calendarBean);
            } else {
                //log.debug("Using existing text field bean");
                if (maxlength != 0) calendarBean.setMaxLength(maxlength);
                if (size != 0) calendarBean.setSize(size);
                this.updateBaseComponentBean(calendarBean);
            }
        } else {
            calendarBean = new CalendarBean();
            if (maxlength != 0) calendarBean.setMaxLength(maxlength);
            if (size != 0) calendarBean.setSize(size);
            this.setBaseComponentBean(calendarBean);
        }

        calendarBean.setId("cal_" + pageContext.findAttribute(SportletProperties.COMPONENT_ID));
        try {
            JspWriter out = pageContext.getOut();
            out.print(calendarBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

}
