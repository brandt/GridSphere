/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.util.List;

public class PortletText extends BasePortletComponent {

    private String text;

    public PortletText() {}

    public PortletText(String text) {
        setText(text);
    }

    public List init(List list) {
        COMPONENT_ID = list.size();
        return list;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        PortletContext ctx = event.getPortletContext();
        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();
        try {
            ctx.include(text, req, res);
        } catch (PortletException e) {
            throw new PortletLayoutException("Unable to include text: " + text, e);
        }
    }

}
