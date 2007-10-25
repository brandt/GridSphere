/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */

package org.gridsphere.provider.portletui.beans;

import java.util.Locale;

/**
 * The abstract <code>BaseComponentBean</code> defines the visual bean properties of all ui tag beans
 */
public abstract class BaseComponentBean extends BaseBean implements Comparable {

    protected String name = null;
    protected String value = null;
    protected boolean readonly = false;
    protected boolean disabled = false;
    protected String key = null;

    protected String cssStyle = null;
    protected String cssClass = null;
    protected String id = null;

    /**
     * Constructs a default base component bean
     */
    public BaseComponentBean() {
        super();
    }

    /**
     * Constructs a base component bean using the supplied visual bean type identifier
     *
     * @param vbName the supplied visual bean type identifier
     */
    public BaseComponentBean(String vbName) {
        super(vbName);
    }

    /**
     * Sets the id of the bean  (not to be confused with beanId)
     *
     * @param id the id of the bean
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the id of the bean
     *
     * @return id of the bean
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the name of the bean
     *
     * @param name the name of the bean
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the bean
     *
     * @return name of the bean
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the bean value
     *
     * @param value the bean value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the bean value
     *
     * @return the bean value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the key used to identify localized text
     *
     * @return the key used to identify localized text
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key used to identify localized text
     *
     * @param key the key used to identify localized text
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns true if bean is in disabled state.
     *
     * @return state
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled attribute of the bean to be 'flag' state
     *
     * @param flag is true if the bean is to be disabled, false otherwise
     */
    public void setDisabled(boolean flag) {
        this.disabled = flag;
    }

    /**
     * Returns disabled String if bean is disabled
     *
     * @return disabled String if bean is disabled or blank otherwise
     */
    protected String checkDisabled() {
        if (disabled) {
            // 'disabled' replaced by 'disabled="disabled"' for XHTML 1.0 Strict compliance
            return " disabled=\"disabled\" ";
        } else {
            return "";
        }
    }

    /**
     * Sets the bean to readonly
     *
     * @param flag is true if the bean is read-only, false otherwise
     */
    public void setReadOnly(boolean flag) {
        this.readonly = flag;
    }

    /**
     * Returns the read-only status of the bean
     *
     * @return true if bean is read-only, false otherwise
     */
    public boolean isReadOnly() {
        return readonly;
    }

    /**
     * Returns 'readonly' string if bean is read-only, blank string otherwise
     *
     * @return 'readonly' string if bean is read-only, blank string otherwise
     */
    protected String checkReadOnly() {
        if (readonly) {
            return " readonly ";
        } else {
            return "";
        }
    }

    public int compareTo(Object o) {
        BaseComponentBean otherBean = (BaseComponentBean) o;
        return value.compareToIgnoreCase(otherBean.getValue());
    }

    /**
     * Returns the current css style
     *
     * @return css style of the element
     */
    public String getCssStyle() {
        return cssStyle;
    }

    /**
     * Sets the css style of the element.
     *
     * @param cssStyle the css style
     */
    public void setCssStyle(String cssStyle) {
        if (cssStyle != null) this.cssStyle = cssStyle;
    }

    /**
     * Returns the current css class of the element.
     *
     * @return css class of the element
     */
    public String getCssClass() {
        return cssClass;
    }

    /**
     * Sets the css class of the element. If null nothing will be changed.
     *
     * @param cssClass the css class
     */
    public void setCssClass(String cssClass) {
        if (cssClass != null) this.cssClass = cssClass;
    }

    public void clearCssClass() {
        this.cssClass = null;
    }

    public void addCssClass(String cssClass) {
        if (this.cssClass == null) this.cssClass = "";
        if (cssClass != null) this.cssClass += " " + cssClass;
    }

    /**
     * Returns the formatted css commands for inserting into html components.
     *
     * @return formatted css string for style/class attribute
     */
    protected String getFormattedCss() {
        String result = "";
        if (this.cssStyle != null) {
            result = " style=\"" + this.cssStyle + "\"";
        }
        if (this.cssClass != null) {
            result += " class=\"" + this.cssClass + "\"";
        }
        return result;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Adds a css style to the exiting style.
     *
     * @param style style to be added
     */
    public void addCssStyle(String style) {
        if (cssStyle != null) {
            cssStyle += style;
        } else {
            this.setCssStyle(style);
        }

    }

    /**
     * Deletes the current css style.
     */
    public void deleteCssStyle() {
        cssStyle = null;
    }

    /**
     * Deletes the current css class setting.
     */
    public void deleteCssClass() {
        cssClass = null;
    }
}

