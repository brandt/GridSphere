/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class SubmitButtonBean extends BaseButtonBean {

    public SubmitButtonBean(String name, String value) {
        super(name, value);
    }

    public String toString() {
        this.type = "submit";
        return super.toString();
    }
}
