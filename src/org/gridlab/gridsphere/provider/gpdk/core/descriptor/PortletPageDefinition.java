/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.gpdk.core.descriptor;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.ConfigParam;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * The <code>SportletServiceDefinition</code> defines a portlet service
 * definition that is defined in the portlet service descripor.
 */
public class PortletPageDefinition {

    protected String action = "";
    protected String presentation = "";
    protected String pageImplementation = "";

    /**
     * Sets the portlet service name
     *
     * @param action the portlet service name
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Returns the portlet service name
     *
     * @return the portlet service name
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Sets the portlet presentation resource
     *
     * @param presentation the portlet service description
     */
    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    /**
     * Returns the portlet presentation resource
     *
     * @return the portlet presentation resource
     */
    public String getPresentation() {
        return this.presentation;
    }

    /**
     * Returns the portlet page implementation
     *
     * @return the portlet service implementation
     */
    public String getPageImplementation() {
        return this.pageImplementation;
    }

    /**
     * Sets the portlet page implementation
     *
     * @param pageImplementation the portlet service implementation
     */
    public void setPageImplementation(String pageImplementation) {
        this.pageImplementation = pageImplementation;
    }

    /**
     * Returns a <code>String</code> representation if this portlet service
     * definition
     *
     * @return the service definition as a <code>String</code>
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("action name: " + this.action + "\n");
        sb.append("action page class: " + this.pageImplementation + "\n");
        sb.append("action presentation: " + this.presentation + "\n");
        return sb.toString();
    }

}
