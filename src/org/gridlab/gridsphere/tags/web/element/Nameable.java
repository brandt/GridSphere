/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public interface Nameable extends Element {

    /**
     * Sets the name of the bean
     * @param name name of the bean
     */
    public void setName(String name);

    /**
     * Gets the name of the bean.
     * @return the name of the bean
     */
    public String getName();
}
