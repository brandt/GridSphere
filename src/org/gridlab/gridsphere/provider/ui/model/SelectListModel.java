/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.ui.model;

import org.gridlab.gridsphere.provider.ui.beans.Selectable;

public interface SelectListModel extends DefaultListModel {

    public void add(int index, Selectable item);

    public void addElement(Selectable item);

    public void setSelected(int index, boolean flag);

    public void unselectAll();

}
