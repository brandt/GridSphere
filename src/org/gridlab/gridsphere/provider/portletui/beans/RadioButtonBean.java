/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import javax.servlet.http.HttpServletRequest;

/**
 * A <code>RadioButtonBean</code> represents a radio button element
 */
public class RadioButtonBean extends SelectElementBean {

    public static final String RADIO_STYLE = "portlet-form-field";

    public static final String NAME = "rb";

    /**
     * Constructs a default radio button bean
     */
    public RadioButtonBean() {
        super(NAME);
        this.cssClass = RADIO_STYLE;
    }

    /**
     * Constructs a radio button bean using a supplied portlet request and bean identifier
     *
     * @param id the bean identifier
     */
    public RadioButtonBean(String id) {
        super(NAME);
        this.cssClass = RADIO_STYLE;
        this.beanId = id;
    }

    /**
     * Constructs a radio button bean using a supplied portlet request and bean identifier
     *
     * @param request the portlet request
     * @param id      the bean identifier
     */
    public RadioButtonBean(HttpServletRequest request, String id) {
        super(NAME);
        this.cssClass = RADIO_STYLE;
        this.request = request;
        this.beanId = id;
    }

    public String toStartString() {
        return super.toStartString("radio");
    }

}
