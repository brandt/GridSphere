/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

public class PortletErrorMessage {

    private String id = "";
    private Exception e = null;

    public PortletErrorMessage(Exception e) {
        this.e = e;
    }

    public PortletErrorMessage(String portletID, Exception e) {
        this.id = id;
        this.e = e;
    }

    public String getPortletID() {
        return id;
    }

    public String getMessage() {
        return e.getMessage();
    }

    public Exception getException() {
        return e;
    }
}
