/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: CheckBoxBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.provider.portletui.beans;

/**
 * A <code>CheckBoxBean</code> provides a check box element
 */
public class CheckBoxBean extends SelectElementBean {

    public static final String CHECKBOX_STYLE = "portlet-form-field";

    /**
     * Constructs a default check box bean
     */
    public CheckBoxBean() {
        super(TagBean.CHECKBOX_NAME);
        this.cssClass = CHECKBOX_STYLE;
    }

    /**
     * Constructs a check box bean with a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public CheckBoxBean(String beanId) {
        this();
        this.beanId = beanId;
    }

    public String toStartString() {
        return super.toStartString("checkbox");
    }

}
