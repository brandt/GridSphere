/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import java.util.Collection;

public interface PortletWebApplication {

    public void destroy();

    public String getWebApplicationName();

    public String getWebApplicationDescription();

    public ApplicationPortlet getApplicationPortlet(String portletApplicationID);

    public Collection getAllApplicationPortlets();
}
