/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

import java.util.ArrayList;

public interface DropDownList extends Updateable {

    public ArrayList getSelectedValues();

    public ArrayList getSelectedItems();

    public void add(Selectable item);

    public void add(String name, String value);

}
