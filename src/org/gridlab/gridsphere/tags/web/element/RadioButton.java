/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class RadioButton extends SelectElement {

    public RadioButton(String name, String value, boolean selected, boolean disabled) {
        super(name, value, selected, disabled);
    }

    public String toString() {
        return super.toString("radio");
    }
}
