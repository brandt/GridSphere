/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public interface Link extends TagBean {


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
