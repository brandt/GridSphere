/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.BaseComponentBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public abstract class BaseComponentTag extends BaseBeanTag   {

    protected String name = null;
    protected String value = null;
    protected boolean readonly = false;
    protected boolean disabled = false;
    protected String cssStyle = null;
    protected boolean supportsJS = false;

    /**
     * Sets the name of the bean.
     * @param name the name of the bean
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the bean.
     * @return name of the bean
     */
    public String getName() {
        return this.name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the tag
     * @return label of the beans
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns true if bean is in disabled state.
     * @return state
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled attribute of the bean to be 'flag' state.
     * @param flag status
     */
    public void setDisabled(boolean flag) {
        this.disabled = flag;
    }

    /**
     * Returns disabled String if bean is disabled
     * @return String depending if bean is disabled
     */
    protected String checkDisabled() {
        if (disabled) {
            return " disabled='disabled' ";
        } else {
            return "";
        }
    }

    /**
     * Sets the bean to readonly.
     * @param flag status of the bean
     */
    public void setReadonly(boolean flag) {
        this.readonly = flag;
    }

    /**
     * Returns the readonly status of the bean
     * @return readonly status
     */
    public boolean isReadonly() {
        return readonly;
    }

    protected String checkReadonly() {
        if (readonly) {
            return " disabled='disabled' ";
        } else {
            return "";
        }
    }

    /**
     * Returns the CSS style name of the beans.
     * @return the name of the css style
     */
    public String getCssStyle() {
        return cssStyle;
    }

    /**
     * Sets the CSS style of the beans.
     * @param style css style name to set for the beans
     */
    public void setCssStyle(String style) {
        this.cssStyle = style;
    }

    protected void setBaseComponentBean(BaseComponentBean componentBean) {
        if (cssStyle != null) componentBean.setCssStyle(cssStyle);

        componentBean.setDisabled(disabled);
        if (name != null) componentBean.setName(name);
        if (value != null) componentBean.setValue(value);
        if (supportsJavaScript()) {
            supportsJS = true;
        } else {
            supportsJS = false;
        }
        componentBean.setSupportsJS(supportsJS);
        componentBean.setReadOnly(readonly);
    }

    protected void updateBaseComponentBean(BaseComponentBean componentBean) {
        if ((cssStyle != null) && (!componentBean.getCssStyle().equals(""))) {
            componentBean.setCssStyle(cssStyle);
        }
        if ((name != null) && (componentBean.getName() == null)) {
            componentBean.setName(name);
        }
        if ((value != null) && (componentBean.getValue() == null)) {
            componentBean.setValue(value);
        }
        if (supportsJavaScript()) {
            supportsJS = true;
        } else {
            supportsJS = false;
        }
    }

    public void doEndTag(TagBean tagBean) throws JspException {
        /*
        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(tagBean);
        }
        */

    }

}
