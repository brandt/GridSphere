/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface SelectableBean extends NameValueBean {

    public void setSelected(boolean flag);

    public boolean isSelected();
}
