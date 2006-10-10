package org.gridsphere.filters;

import javax.servlet.ServletConfig;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface PortalFilterConfig {

    public ServletConfig getServletConfig();

    public String getInitParameter(String name);

}
