/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public interface BaseList extends TagBean {

    /**
     * Returns the name of the list.
     * @return the name of the list
     */
    public String getName();

    /**
     * Sets the name of the list
     * @param name name of the list
     */
    public void setName(String name);

    public abstract String toString();
}
