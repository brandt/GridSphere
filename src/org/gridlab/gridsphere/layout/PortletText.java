/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderFirst(ctx, req, res);
        log.debug("in doRenderFirst()");
        try {
            RequestDispatcher rd = ctx.getRequestDispatcher(text);
            if (rd != null) rd.include(req, res);
        } catch (ServletException e) {
            log.error("Unable to include text: " + text, e);
            throw new PortletLayoutException("Unable to include text: " + text, e);
        }
    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderLast(ctx, req, res);
        log.debug("in doRenderLast()");
    }


}
