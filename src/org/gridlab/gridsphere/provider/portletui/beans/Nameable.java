/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public interface Nameable extends TagBean {

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
