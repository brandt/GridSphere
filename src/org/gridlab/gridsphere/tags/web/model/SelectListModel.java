/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.model;

import org.gridlab.gridsphere.tags.web.element.Selectable;

public interface SelectListModel extends DefaultListModel {

    public void add(int index, Selectable item);

    public void addElement(Selectable item);

    public void setSelected(int index);

    public void unselectAll();

}
