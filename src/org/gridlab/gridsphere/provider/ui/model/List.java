/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.model;

import java.util.ArrayList;

public class List implements ListModel {

    public List() {
        super();
    }

    ArrayList list = new ArrayList();

    public List(ArrayList list) {
        super();
        this.list = list;
    }

    public Object getElementAt(int index) {
        return list.get(index);
    }

    public int getSize() {
        return list.size();
    }
}
