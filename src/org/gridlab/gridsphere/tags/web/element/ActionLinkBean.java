/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public class ActionLinkBean extends TextBean implements LinkBean {

    protected String link = null;

    /**
     * Sets the action
     * @param link the actionlink
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets the actionlink string
     */
    public String getLink() {
        return link;
    }

    public String toString() {
        return "<a href='" + link + "'/>" + label + "</a>";
    }
}
