/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl.descriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * A <code>SportletCollection</code> is a container for
 * a collection of <code>SportletDefinition</code>s.
 */
public class SportletCollection {

    /**
     * Constructs an instance of SportletCollection
     */
    public SportletCollection() {
    }

    private List portletDefList = new ArrayList();

    /**
     * Returns the list of portlet definitions
     *
     * @return a list of portlet definitions
     */
    public List getPortletDefinitionList() {
        return portletDefList;
    }

    /**
     * Sets the list of portlet definitions
     *
     * @param an <code>ArrayList</code> containing the portlet definitions
     */
    public void setPortletDefinitionList(ArrayList portletDefList) {
        this.portletDefList = portletDefList;
    }

}

