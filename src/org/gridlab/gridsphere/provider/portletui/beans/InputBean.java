/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * An abstract <code>InputBean</code> provides a generic input HTML element
 */
public abstract class InputBean extends BaseComponentBean implements TagBean {

    public static final String INPUT_STYLE = "portlet-form-input-field";

    protected String inputtype = "";
    protected int size = 0;
    protected int maxlength = 0;
    private String beanIdSource = null;


    /**
     * Constructs a default input bean
     */
    public InputBean() {
        super();
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

    public String getEncodedName() {
        return createTagName(name);
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<input ");
        sb.append(getFormattedCss());
        sb.append(" type=\"");
        sb.append(inputtype);
        sb.append("\" ");
        String sname = createTagName(name);
        sb.append("name=\"");
        sb.append(sname);
        sb.append("\" ");
        if (value != null) {
            sb.append("value=\"");
            sb.append(value);
            sb.append("\" ");
        }
        if (id != null) sb.append("id=\"").append(id).append("\" ");
        if (size != 0) {
            sb.append("size=\"");
            sb.append(size);
            sb.append("\" ");
        }
        if (maxlength != 0) {
            sb.append("maxlength=\"");
            sb.append(maxlength);
            sb.append("\" ");
        }
        sb.append(checkReadOnly());
        sb.append(checkDisabled());
        sb.append("/>");
        return sb.toString();
    }

    public String toEndString() {
        return "";
    }

}
