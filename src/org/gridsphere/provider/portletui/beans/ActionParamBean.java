/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ActionParamBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.beans;

/**
 * An <code>ActionParamBean</code> is a visual bean that represents action parameters associated with a portlet action
 */
public class ActionParamBean extends BaseBean {

    private String name = "";
    private String value = "";

    /**
     * Constructs a default action param bean
     */
    public ActionParamBean() {
    }

    /**
     * Constructs an action param bean from a supplied portlet request and bean identifier
     *
     * @param name  the action param name
     * @param value the action param value
     */
    public ActionParamBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Constructs an action param bean from a supplied portlet request and bean identifier
     *
     * @param beanId the bean identifier
     */
    public ActionParamBean(String beanId) {
        this.beanId = beanId;
    }

    /**
     * Sets the action parameter name
     *
     * @param name the action parameter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the action parameter name
     *
     * @return the action parameter name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the action parameter value
     *
     * @return the action parameter value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the action parameter value
     *
     * @param value the action parameter value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        return "";
    }

}
