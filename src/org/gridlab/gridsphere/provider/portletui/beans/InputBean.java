/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

/**
 * An abstract <code>InputBean</code> provides a generic input HTML element
 */
public abstract class InputBean extends BaseComponentBean implements TagBean {

    private transient static PortletLog log = SportletLog.getInstance(InputBean.class);
    public static final String INPUT_STYLE = "portlet-form-input-field";

    protected String inputtype = "";
    protected int size = 0;
    protected int maxlength = 0;
    private String beanIdSource = null;


    /**
     * Constructs a default input bean
     */
    public InputBean() {
        this.cssClass = INPUT_STYLE;
    }

    /**
     * Constructs an input bean with a supplied name
     *
     * @param name the bean name
     */
    public InputBean(String name) {
        super(name);
        this.cssClass = INPUT_STYLE;
    }

    /**
     * Returns the size of this input element
     *
     * @return the size of this input element
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of this input element
     *
     * @param size the size of this input element
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the maximum length of this input element
     *
     * @return the maximum length of this input element
     */
    public int getMaxLength() {
        return maxlength;
    }

    /**
     * Sets the maximum length of this input element
     *
     * @param maxlength the maximum length of this input element
     */
    public void setMaxLength(int maxlength) {
        this.maxlength = maxlength;
    }

    public String getBeanIdSource() {
        return beanIdSource;
    }

    public void setBeanIdSource(String beanIdSource) {
        this.beanIdSource = beanIdSource;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<input " + getFormattedCss() + " ");
        sb.append("type=\"" + inputtype + "\" ");

        String pname = (name == null) ? "" : name;
        String sname = pname;
        if (!beanId.equals("")) {
            if (request == null) {
                //log.debug("request is null");
                sname = "ui_" + vbName + "_" + beanId + "_" + pname;
            } else {
                //log.debug("request not null");
                String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
                if (compId == null) {
                    sname = "ui_" + vbName + "_" + beanId + "_" + pname;
                } else {
                    sname = "ui_" + vbName + "_" + compId + "%" + beanId + "_" + pname;
                }
            }
        }

        sb.append("name=\"" + sname + "\" ");
        if (value != null) sb.append("value=\"" + value + "\" ");
        if (size != 0) sb.append("size=\"" + size + "\" ");
        if (maxlength != 0) sb.append("maxlength=\"" + maxlength + "\" ");
        sb.append(checkReadOnly());
        sb.append(checkDisabled());
        sb.append("/>");
        return sb.toString();
    }

    public String toEndString() {
        return "";
    }

}
