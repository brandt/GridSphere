/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public interface ReadOnly extends Label {

    /**
     * Sets the bean to readonly.
     * @param flag status of the bean
     */
    public void setReadonly(boolean flag);

    /**
     * Returns the readonly status of the bean
     * @return readonly status
     */
    public boolean isReadonly();
}
