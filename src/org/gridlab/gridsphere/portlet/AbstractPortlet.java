/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

/**
 *
 */
public abstract class AbstractPortlet extends PortletAdapter implements PortletActionCommand {

    public AbstractPortlet() {
        super();
    }

    /**
     * Returns the portlet configuration.
     *
     * @return the portlet config
     */
    public PortletConfig getConfig() {
        return super.getPortletConfig();
    }

    /**
     * Called by the portlet container to ask this portlet to perform the required operational logic
     * using the given portlet request. This method is invoked before the service method.
     *
     * @param request the portlet request
     * @throws PortletException if the portlet has trouble fulfilling the execution request
     */
    public abstract void execute(PortletRequest request) throws PortletException;

}










































