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
    private List statesAsStrings = null;

    public void setWindowStates(Vector windowStates) {
        this.windowStates = windowStates;
    }

    public List getWindowStates() {
        return windowStates;
    }

    protected void convertStates() {
        AnyNode a = null;
        statesAsStrings = new Vector();
        for (int i = 0; i < windowStates.size(); i++) {
            a = (AnyNode)windowStates.get(i);
            statesAsStrings.add(a.getLocalName());
        }
    }

    public List getWindowStatesAsStrings() {
        if (statesAsStrings == null) {
            convertStates();
        }
        return statesAsStrings;
    }

}
