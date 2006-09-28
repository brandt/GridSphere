package org.gridsphere.portletcontainer;

/**
 * @author <a href="mailto:jnovotny@ucsd.edu">Jason Novotny</a>
 * @version $Id$
 */
public class PortletStatus {

    public static PortletStatus SUCCESS = new PortletStatus("success");

    public static PortletStatus FAILURE = new PortletStatus("failure");

    private String status;

    private PortletStatus(String status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if ((o != null) && (o instanceof PortletStatus)) {
            return (this.status.equals(((PortletStatus) o).toString()));
        }
        return false;
    }

    public String toString() {
        return status;
    }
    public int hashCode() {
        return status.hashCode();
    }

}
