/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;

public class PortletText extends BasePortletComponent {

    private String text;

    public PortletText() {
    }

    public void setInclude(String text) {
        this.text = text;
    }

    public String getInclude() {
        return text;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        PortletContext ctx = event.getPortletContext();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        try {
            ctx.include(text, req, res);
        } catch (PortletException e) {
            throw new PortletLayoutException("Unable to include text: " + text, e);
        }
    }

}
