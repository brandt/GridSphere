/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Iterator;
import java.util.Map;

public interface ConcretePortlet {

    public String getPortletName();

    public String getDefaultLocale();

    public PortletLanguageInfo[] getPortletLanguageInfo();

}
