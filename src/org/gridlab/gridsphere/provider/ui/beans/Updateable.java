/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.ui.beans;

/**
 * All elements which should be updatable (like most of the formelements)
 * needs to implement this interface.
 */

public interface Updateable extends TagBean {

    /**
     * Updates the bean with given values.
     * @param values array of strings containing the values of the bean
     */
    public void update(String[] values);

}
