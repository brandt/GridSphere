/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import java.util.Collection;

public interface PortletWebApplication {
    void destroy();

    String getWebApplicationName();

    String getWebApplicationDescription();

    ApplicationPortlet getApplicationPortlet(String portletApplicationID);

    Collection getAllApplicationPortlets();
}
