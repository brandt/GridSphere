/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public interface Updateable extends ElementBean {

    /**
     * Updates the bean with given values.
     * @param values array of strings containing the values of the bean
     */
    public void update(String[] values);

}
