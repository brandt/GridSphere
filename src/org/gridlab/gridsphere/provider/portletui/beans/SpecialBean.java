/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class SpecialBean extends BaseComponentBean implements TagBean  {

    public static final String NAME = "sp";

    public static final String TEXTFIELD_STYLE = "portlet-frame-text";

    public SpecialBean() {
        super(NAME);
        this.cssStyle = TEXTFIELD_STYLE;
    }

    public SpecialBean(String name) {
        super(name);
        this.cssStyle = TEXTFIELD_STYLE;
    }

    public SpecialBean(String name, String id) {
        super(name);
        this.beanId = id;
        this.cssStyle = TEXTFIELD_STYLE;
    }

     public SpecialBean(PortletRequest request, String id) {
        super(NAME);
        this.cssStyle = TEXTFIELD_STYLE;
        this.request = request;
        this.beanId = id;
    }

    public String toStartString() {
        if (supportsJS) {
            return "<b>a javascript interface</b>";
        } else {
            return "<b>a non-javascript interface</b>";
        }
    }

}
