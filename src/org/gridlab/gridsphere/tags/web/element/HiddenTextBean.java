/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class HiddenTextBean extends TextFieldBean {

    public HiddenTextBean() {
        super();
    }

    public HiddenTextBean(String name, String value) {
        super(name, value);
    }

    public String toString() {
        this.inputtype = "hidden";
        return super.toString();
    }


}
