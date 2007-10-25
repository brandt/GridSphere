/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portletui.beans;

/**
 * A <code>HiddenFieldBean</code> represents a hidden field element
 */
public class HiddenFieldBean extends TextFieldBean {

    /**
     * Constructs a default hidden field bean
     */
    public HiddenFieldBean() {
        this.vbName = TagBean.HIDDENFIELD_NAME;
    }

    /**
     * Constructs a hidden field bean with the supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public HiddenFieldBean(String beanId) {
        super(TagBean.HIDDENFIELD_NAME, beanId);
    }

    public String toStartString() {
        this.inputtype = "hidden";
        return super.toStartString();
    }

}
