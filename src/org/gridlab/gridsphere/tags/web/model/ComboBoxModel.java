/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

public interface ComboBoxModel extends ListModel {

	public Object getSelectedItem();
    public void setSelectedItem(Object object);
}
