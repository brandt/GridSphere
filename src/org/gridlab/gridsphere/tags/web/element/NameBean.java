/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class NameBean extends BaseElementBean implements Nameable, Updateable {

    protected String name = new String();

    /**
     * Sets the name of the bean.
     * @param name the name of the bean
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the bean.
     * @return the name of the bean
     */
    public String getName() {
        return name;
    }


    public abstract void update(String[] values);

}
