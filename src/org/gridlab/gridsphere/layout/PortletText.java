/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;

import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.IOException;

public class PortletText extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletText.class);

    private String text;

    public PortletText() {}

    public PortletText(String text) {
        setText(text);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRender()");
        try {
            ctx.include(text, req, res);
        } catch (PortletException e) {
            log.error("Unable to include component JSP", e);
            throw new PortletLayoutException("Unable to include component JSP", e);
        }
    }

    public void doRenderFirst(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        doRender(ctx, req, res);
    }

    public void doRenderLast(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {}


}
