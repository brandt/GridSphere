/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

public class HiddenFieldBean extends TextFieldBean {

    public HiddenFieldBean() {
        super();
    }

    public HiddenFieldBean(String name, String value) {
        super(name, value);
    }

    public String toString() {
        this.inputtype = "hidden";
        return super.toString();
    }


}
