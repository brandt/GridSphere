/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

public interface Button extends Label {

    /**
     * Sets the type of the button
     * @type type of the button
     */
    public void setType(String type);

    /**
     * Returns the type of the button
     * @return type of the button
     */
    public String getType();
}
