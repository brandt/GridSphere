/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface Selectable extends Label {

    /**
     * Sets the selected status of the bean.
     * @param flag status of the bean
     */
    public void setSelected(boolean flag);

    /**
     * Returns the selected status of the bean
     * @return selected status
     */
    public boolean isSelected();
}
