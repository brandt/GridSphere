/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface Selectable extends NameValue {

    public void setSelected(boolean flag);

    public boolean isSelected();
}
