/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class RadioButtonBean extends SelectElementBean {

    public RadioButtonBean(String name, String value, boolean selected, boolean disabled) {
        super(name, value, selected, disabled);
    }

    public String toString() {
        return super.toString("radio");
    }
}
