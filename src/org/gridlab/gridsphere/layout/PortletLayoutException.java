/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletException;

public class PortletLayoutException extends PortletException {

    public PortletLayoutException() {
        super();
    }

    public PortletLayoutException(String msg) {
        super(msg);
    }

    public PortletLayoutException(String msg, Throwable t) {
        super(msg, t);
    }

}

