/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface Label extends Nameable {

    /**
     * Sets the value of the bean.
     * @param value of the bean
     */
    public void setValue(String value);

    /**
     * Gets the vale of the bean.
     * @return the value of the bean
     */
    public String getValue();

}
