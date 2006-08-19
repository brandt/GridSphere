/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: CheckBoxBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.provider.portletui.beans;

/**
 * A <code>CheckBoxBean</code> provides a check box element
 */
public class CheckBoxBean extends SelectElementBean {

    public static final String CHECKBOX_STYLE = "portlet-form-field";

    public static final String NAME = "cb";

    /**
     * Constructs a default check box bean
     */
    public CheckBoxBean() {
        super(NAME);
        this.cssClass = CHECKBOX_STYLE;
    }

    /**
     * Constructs a check box bean with a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public CheckBoxBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssClass = CHECKBOX_STYLE;
    }

    public String toStartString() {
        return super.toStartString("checkbox");
    }

}
