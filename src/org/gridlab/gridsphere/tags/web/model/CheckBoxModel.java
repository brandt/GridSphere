/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

import java.util.Iterator;

public class CheckBoxModel extends MultipleSelectListModel {

    public CheckBoxModel() {
        super();
    }

    public void disableAll() {
        Iterator it = elements.iterator();
        while(it.hasNext()) {
            ((CheckBoxItem)it.next()).disable();
        }
    }

}
