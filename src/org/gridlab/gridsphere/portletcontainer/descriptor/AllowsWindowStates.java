/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.exolab.castor.types.AnyNode;
import org.gridlab.gridsphere.portlet.PortletWindow;
import org.gridlab.gridsphere.portlet.impl.SportletWindow;

import java.util.ArrayList;
import java.util.List;

public class AllowsWindowStates {

    private List windowStates = new ArrayList();
    private List statesAsStrings = null;
    private List statesAsStates = null;

    public void setWindowStates(ArrayList windowStates) {
        this.windowStates = windowStates;
    }

    public List getWindowStates() {
        return windowStates;
    }

    protected void convertStates() {
        AnyNode a = null;
        statesAsStrings = new ArrayList();
        for (int i = 0; i < windowStates.size(); i++) {
            a = (AnyNode) windowStates.get(i);
            statesAsStrings.add(a.getLocalName());
        }
    }

    public List getWindowStatesAsStrings() {
        if (statesAsStrings == null) {
            convertStates();
        }
        return statesAsStrings;
    }

    protected void convert2WindowStates() {
        AnyNode a = null;
        PortletWindow.State state;
        statesAsStates = new ArrayList();
        for (int i = 0; i < windowStates.size(); i++) {
            a = (AnyNode) windowStates.get(i);
            try {
                state = SportletWindow.State.toPortletWindowState(a.getLocalName());
                statesAsStates.add(state);
            } catch (Exception e) {
            }
        }
    }

    public List getPortletWindowStates() {
        if (statesAsStates == null) {
            convert2WindowStates();
        }
        return statesAsStates;
    }
}
