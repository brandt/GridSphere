/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class TextFieldBean extends InputBean implements TagBean  {

    public static final String NAME = "tf";

    public static final String TEXTFIELD_STYLE = "portlet-frame-text";

    public TextFieldBean() {
        super(NAME);
        this.inputtype = "text";
        this.cssStyle = TEXTFIELD_STYLE;
    }

    public TextFieldBean(String name) {
        super(name);
        this.inputtype = "text";
        this.cssStyle = TEXTFIELD_STYLE;
    }

    public TextFieldBean(String name, String id) {
        super(name);
        this.inputtype = "text";
        this.beanId = id;
        this.cssStyle = TEXTFIELD_STYLE;
    }

     public TextFieldBean(PortletRequest request, String id) {
        super(NAME);
        this.inputtype = "text";
        this.cssStyle = TEXTFIELD_STYLE;
        this.request = request;
        this.beanId = id;
    }

}
