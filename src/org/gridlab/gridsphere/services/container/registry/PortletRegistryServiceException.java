/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry;

import org.gridlab.gridsphere.portlet.PortletException;

public class PortletRegistryServiceException extends PortletException {

    /**
     * Constructs a new portlet exception.
     */
    public PortletRegistryServiceException() {
        super();
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     */
    public PortletRegistryServiceException(String text) {
        super(text);
    }

    public PortletRegistryServiceException(String text, Throwable t) {
        super(text, t);
    }

}
