/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

/**
 * The PortletActionCommand defines a single method execute() that is invoked
 * during the portlet lifecycle by the portlet container. The execute() method
 * contains all business logic required before doXXX is invoked to render content.
 */
public interface PortletActionCommand {

    /**
     * Called by the portlet container to ask this portlet to perform the required operational logic
     * using the given portlet request. This method is invoked before the service method.
     *
     * @param request the portlet request
     * @throws PortletException if the portlet has trouble fulfilling the execution request
     */
    public void execute(PortletRequest req) throws PortletException;

}