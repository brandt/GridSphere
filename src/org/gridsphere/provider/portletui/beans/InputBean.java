/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: InputBean.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.provider.portletui.beans;

/**
 * An abstract <code>InputBean</code> provides a generic input HTML element
 */
public abstract class InputBean extends BaseComponentBean implements TagBean {

    public static final String INPUT_STYLE = "portlet-form-input-field";

    protected String inputtype = "";
    protected int size = 0;
    protected int maxlength = 0;

    protected String onFocus = null;
    protected String onClick = null;
    protected String onChange = null;
    protected String onBlur = null;
    protected String onSelect = null;

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
     * Returns the onChange JavaScript function
     *
     * @return onChange JavaScript function
     */
    public String getOnChange() {
        return onChange;
    }

    /**
     * Sets the onChange JavaScript function
     *
     * @param onChange the onChange JavaScript function
     */
    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    /**
     * Returns the onBlur JavaScript function
     *
     * @return onBlur JavaScript function
     */
    public String getOnBlur() {
        return onBlur;
    }

    /**
     * Sets the onBlur JavaScript function
     *
     * @param onBlur the onBlur JavaScript function
     */
    public void setOnBlur(String onBlur) {
        this.onBlur = onBlur;
    }

    /**
     * Returns the onSelect JavaScript function
     *
     * @return onSelect JavaScript function
     */
    public String getOnSelect() {
        return onSelect;
    }

    /**
     * Sets the onSelect JavaScript function
     *
     * @param onSelect the onSelect JavaScript function
     */
    public void setOnSelect(String onSelect) {
        this.onSelect = onSelect;
    }

    /**
     * Sets the onFocus JavaScript function
     *
     * @param onFocus the onFocus JavaScript function
     */
    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }

    /**
     * Returns the onFocus JavaScript function
     *
     * @return onFocus JavaScript function
     */
    public String getOnFocus() {
        return onFocus;
    }

    /**
     * Returns the onClick javascript function
     *
     * @return the onClick javascript function
     */
    public String getOnClick() {
        return onClick;
    }

    /**
     * Sets the onClick JavaScript function
     *
     * @param onClick the onFocus JavaScript function
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
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
        if (value != null) sb.append("value=\"").append(value).append("\" ");
        if (size != 0) sb.append("size=\"").append(size).append("\" ");
        if (maxlength != 0) sb.append("maxlength=\"").append(maxlength).append("\" ");
        if (onFocus != null) sb.append("onfocus=\"").append(onFocus).append("\"");
        if (onClick != null) sb.append("onclick=\"").append(onClick).append("\"");
        if (onChange != null) sb.append("onchange=\"").append(onChange).append("\"");
        if (onBlur != null) sb.append("onblur=\"").append(onBlur).append("\"");
        if (onSelect != null) sb.append("onselect=\"").append(onSelect).append("\"");

        sb.append(checkReadOnly());
        sb.append(checkDisabled());
        sb.append("/>");
        return sb.toString();
    }

    public String toEndString() {
        return "";
    }

}
