/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface Button extends Valueable {

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
