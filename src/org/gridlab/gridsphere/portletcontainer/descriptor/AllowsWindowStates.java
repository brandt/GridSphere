/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.List;
import java.util.Vector;

public class AllowsWindowStates {

    private List windowStates = new Vector();

    public void setWindowStates(Vector windowStates) {
        this.windowStates = windowStates;
    }

    public List getWindowStates() {
        return windowStates;
    }

}
