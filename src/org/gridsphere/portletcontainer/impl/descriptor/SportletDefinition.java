/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletDefinition.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.impl.descriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * A <code>SportletDefinition</code> is the XML portlet definition descriptor
 * containing the application portlet definition and one or more concrete portlet
 * definitions.
 */
public class SportletDefinition {

    private ApplicationSportletConfig appSportletConfig = new ApplicationSportletConfig();
    private List concSportletList = new ArrayList();

    /**
     * Constructs an instance of SportletDefinition
     */
    public SportletDefinition() {
    }

    /**
     * Internal method used by Castor
     * <p/>
     * Returns a list of concrete portlet definitions
     *
     * @return a list of concrete portlet definitions
     * @see org.gridsphere.portletcontainer.impl.descriptor.ConcreteSportletDefinition
     */
    public List getConcreteSportletList() {
        return concSportletList;
    }

    /**
     * Internal method used by Castor
     * <p/>
     * Sets a list of concrete portlet definitions
     *
     * @param concSportletList a <code>ArrayList</code> of concrete portlet definitions
     * @see org.gridsphere.portletcontainer.impl.descriptor.ConcreteSportletDefinition
     */
    public void setConcreteSportletList(ArrayList concSportletList) {
        this.concSportletList = concSportletList;
    }

    /**
     * Sets the application portlet definition
     *
     * @param appSportletConfig the application portlet definition
     */
    public void setApplicationSportletConfig(ApplicationSportletConfig appSportletConfig) {
        this.appSportletConfig = appSportletConfig;
    }

    /**
     * Returns the application portlet definition
     *
     * @return the application portlet definition
     */
    public ApplicationSportletConfig getApplicationSportletConfig() {
        return appSportletConfig;
    }

}


