/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class BaseListBean extends NameBean implements BaseList {

    String name = new String();


    /**
     * Returns the name of the list.
     * @return the name of the list
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the list
     * @param name name of the list
     */
    public void setName(String name) {
        this.name = name;
    }

    public abstract String toString();
}
