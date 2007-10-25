/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */

package org.gridsphere.provider.portletui.beans;

/**
 * A <code>RadioButtonBean</code> represents a radio button element
 */
public class RadioButtonBean extends SelectElementBean {

    public static final String RADIO_STYLE = "portlet-form-field";

    /**
     * Constructs a default radio button bean
     */
    public RadioButtonBean() {
        super(TagBean.RADIOBUTTON_NAME);
        this.cssClass = RADIO_STYLE;
    }

    /**
     * Constructs a radio button bean using a supplied portlet request and bean identifier
     *
     * @param id the bean identifier
     */
    public RadioButtonBean(String id) {
        super(TagBean.RADIOBUTTON_NAME);
        this.cssClass = RADIO_STYLE;
        this.beanId = id;
    }

    public String toStartString() {
        return super.toStartString("radio");
    }

}
