/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;


/**
 *
 */
public abstract class JSPPortlet extends AbstractPortlet implements PortletActionCommand {

    public JSPPortlet() {
    }


    /**
     * Returns the portlet configuration.
     *
     * @return the portlet config
     */
    public PortletConfig getConfig() {
        return super.portletConfig;
    }

    /**
     * Called by the portlet container to ask this portlet to perform the required operational logic
     * using the given portlet request. This method is invoked before the service method.
     *
     * @param request the portlet request
     * @throws PortletException if the portlet has trouble fulfilling the execution request
     */
    public abstract void execute(PortletRequest request) throws PortletException;

}










































