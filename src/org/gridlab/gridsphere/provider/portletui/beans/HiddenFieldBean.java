/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class HiddenFieldBean extends TextFieldBean {

    public static final String NAME = "hf";

    public HiddenFieldBean() {
        super(NAME);
    }

    public String toString() {
        this.inputtype = "hidden";
        return super.toString();
    }


}
