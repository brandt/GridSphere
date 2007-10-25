/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */

package org.gridsphere.provider.portletui.beans;

public class TabBean extends BaseComponentBean {

    protected String jspPage;
    protected boolean isActive = false;
    protected String label = "";

    /**
     * Constructs a default table row bean
     */
    public TabBean() {
        super();
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getActive() {
        return isActive;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPage(String jspPage) {
        this.jspPage = jspPage;
    }

    public String getPage() {
        return jspPage;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

}


