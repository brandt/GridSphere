/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere..tags.event;

import org.gridlab.gridsphere.portlet.PortletException;

public class FileFormException extends PortletException {

    public FileFormException() {
        super();
    }

    public FileFormException(String message) {
        super(message);
    }

    public FileFormException(String message, Throwable ex) {
        super(message, ex);
    }
}
