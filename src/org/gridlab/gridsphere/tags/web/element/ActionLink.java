/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public class ActionLink extends Text implements Link {

    protected String link = null;

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String toString() {
        return "<a href='" + link + "'/>" + label + "</a>";
    }
}
