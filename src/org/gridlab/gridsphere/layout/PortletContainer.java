/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.List;
import java.util.Vector;

public class PortletContainer extends PortletComponent {

    private List components = new Vector();
    private List constraintsList = new Vector();
    private PortletDimension minSize;
    private PortletDimension maxSize;
    private PortletDimension preferredSize;
    private PortletInsets insets;
    private LayoutManager layoutManager;

    public PortletContainer() {}

    public void add(PortletComponent comp) {
        components.add(comp);
    }

    public void add(PortletComponent comp, int index) {
        components.add(index, comp);
    }

    public void add(PortletComponent comp, Object constraints) {
        components.add(comp);
        constraintsList.add(constraints);
    }

    public void add(PortletComponent comp, Object constraints, int index) {
        components.add(index, comp);
        constraintsList.add(index, constraints);
    }

    public PortletComponent getPortletComponent(int n) {
        return (PortletComponent)components.get(n);
    }

    public int getPortletComponentCount() {
        return components.size();
    }

    public PortletComponent[] getPortletComponents() {
        return (PortletComponent[])components.toArray();
    }

    public PortletInsets getPortletInsets() {
        return insets;
    }

    public PortletDimension getMaximumSize() {
        return maxSize;
    }

    public PortletDimension getMinimumSize() {
        return minSize;
    }

    public PortletDimension getPreferredSize() {
        return preferredSize;
    }

    public void remove(int index) {
        components.remove(index);
    }

    public void remove(PortletComponent comp) {
        components.remove(comp);
    }

    public void removeAll() {
        for (int i = 0; i < components.size(); i++) {
            components.remove(i);
        }
    }

    public LayoutManager getLayout() {
        return layoutManager;
    }

    public void setLayout(LayoutManager mgr) {
        layoutManager = mgr;
    }

    public void doLayout() {}

    public void invalidate() {}

    public void validate() {}

}
