/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface LinkBean extends ElementBean {


    /**
     * Sets the link.
     * @param link the link
     */
    public void setLink(String link);

    /**
     * Gets the link string.
     * @return the linkstring
     */
    public String getLink();

}
