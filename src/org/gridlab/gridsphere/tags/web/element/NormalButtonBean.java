/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class NormalButtonBean extends BaseButtonBean {

    public String toString() {
        this.type = "button";
        return super.toString();
    }
}
