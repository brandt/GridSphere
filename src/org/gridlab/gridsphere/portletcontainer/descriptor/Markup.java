/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Vector;
import java.util.List;

public class Markup {

    private String name;
    private List portletModes = new Vector();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List getPortletModes() {
        return portletModes;
    }

    public void setPortletModes(Vector portletModes) {
        this.portletModes = portletModes;
    }
}

