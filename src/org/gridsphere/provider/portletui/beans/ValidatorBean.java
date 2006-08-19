package org.gridsphere.provider.portletui.beans;

/**
 * The <code>TextBean</code> represents text to be displayed
 */
public class ValidatorBean extends BaseComponentBean implements TagBean {

    protected String type = "";

    /**
     * Constructs a default text bean
     */
    public ValidatorBean() {

    }

    /**
     * Constructs a text bean using a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public ValidatorBean(String beanId) {
        this.beanId = beanId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toStartString() {
        return "<input type=\"hidden\" name=\"" + name + "#" + type + "\" value=\"" + value + "\"/>";
    }

    public String toEndString() {
        return "";
    }

}
