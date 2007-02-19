/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * An <code>ActionSubmitBean</code> is a visual bean that represents an HTML button and
 * has an associated <code>DefaultPortletAction</code>
 */
public class ActionSubmitBean extends ActionBean implements TagBean {

    public static final String SUBMIT_STYLE = "portlet-form-button";
    public static final String NAME = "as";

    protected String imageSrc = null;

    /**
     * Constructs a default action submit bean
     */
    public ActionSubmitBean() {
        super(NAME);
        this.cssClass = SUBMIT_STYLE;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    /**
     * Constructs an action submit bean from a supplied portlet request and bean identifier
     *
     * @param beanId the bean identifier
     */
    public ActionSubmitBean(String beanId) {
        super(NAME);
        this.cssClass = SUBMIT_STYLE;
        this.beanId = beanId;
    }

    public String toStartString() {
        String inputType = "submit";
        if (useAjax) inputType = "button";
        if (imageSrc != null) inputType = "image";
        return new StringBuffer().append("<input ").append(getFormattedCss()).append(" type=\"").append(inputType).append("\" ").append(checkDisabled()).toString();
    }

    public String toEndString() {
        String sname = (name == null) ? "" : name;
        StringBuffer sb = new StringBuffer();
        if (action != null) sname = action;
        if (anchor != null) sname += "#" + anchor;
        if (onClick != null) {
            // 'onClick' replaced by 'onclick' for XHTML 1.0 Strict compliance
            sb.append(" onclick=\"").append(onClick).append("\" ");
        }
        if (imageSrc != null) sb.append(" src=\"").append(imageSrc).append("\"");

        if (id != null) sb.append("id=\"").append(id).append("\" ");
        sb.append("name=\"").append(sname).append("\" value=\"").append(value).append("\"/>");
        return sb.toString();
    }

}
