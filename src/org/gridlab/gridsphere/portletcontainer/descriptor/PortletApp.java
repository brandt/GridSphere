/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Iterator;

public interface PortletApp {

    public String getUID();

    public String getName();

    public PortletAppInfo[] getPortletAppInfo();

}
