package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletErrorFrame extends BasePortletComponent implements Cloneable, Serializable {

    private String message = null;
    private Exception cause = null;

    public PortletErrorFrame() {

    }

    public boolean hasError() {
        return ((message == null) && (cause == null)) ? false : true;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setException(PortletException cause) {
        this.cause = cause;
    }

    public void setError(String message, Exception cause) {
        this.message = message;
        this.cause = cause;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();
        PortletException portletException = (PortletException)req.getAttribute(SportletProperties.ERROR);
        if (portletException == null) {
            if (message != null) {
                out.println(message);
                message = null;
            }
            if (cause != null) {
                cause.printStackTrace(out);
                cause = null;
            }
        } else {
            out.println(portletException.getMessage() + "\n");
            out.println("<br>");
            portletException.printStackTrace(out);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletErrorFrame f = (PortletErrorFrame)super.clone();
        f.message = this.message;
        return f;
    }
}
