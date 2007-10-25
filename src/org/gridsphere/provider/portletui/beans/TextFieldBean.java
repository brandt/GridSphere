/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portletui.beans;

/**
 * The <code>TextFieldBean</code> represents a text field element
 */
public class TextFieldBean extends InputBean implements TagBean {

    /**
     * Constructs a default text field bean
     */
    public TextFieldBean() {
        super(TagBean.TEXTFIELD_NAME);
        this.inputtype = "text";
    }

    /**
     * Constructs a text field bean using the supplied bean name
     *
     * @param beanId the bean identifier
     */
    public TextFieldBean(String beanId) {
        super(TagBean.TEXTFIELD_NAME);
        this.beanId = beanId;
        this.inputtype = "text";
    }

    /**
     * Constructs a text field bean using the supplied bean name and identifier
     *
     * @param vbName the visual bean name, a 2 character identifier
     * @param beanId the bean identifier
     */
    public TextFieldBean(String vbName, String beanId) {
        super(vbName);
        this.inputtype = "text";
        this.beanId = beanId;
    }

    public String toStartString() {
        return super.toStartString();
    }

}
