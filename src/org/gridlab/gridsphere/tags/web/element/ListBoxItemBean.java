/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class ListBoxItemBean extends SelectElementBean {

    public String toString() {
        return "<option value='" + value + " " + checkDisabled() + " " + checkSelected("selected") + "'>" + name + "</option>";
    }
}
