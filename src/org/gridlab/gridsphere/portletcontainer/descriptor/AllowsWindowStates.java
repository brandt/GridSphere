/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.exolab.castor.types.AnyNode;

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

    public String[] getWindowStatesAsStrings() {
        String[] states = new String[windowStates.size()];
        for (int i = 0; i < windowStates.size(); i++) {
            AnyNode a = (AnyNode)windowStates.get(i);
            states[i] = a.getLocalName();
        }
        return states;
    }

}
