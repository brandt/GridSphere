/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.Vector;
import java.util.List;

public class PortletDock extends PortletComponent {

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private int orientation;
    private String title = "";
    private PortletInsets margin;
    private List components = new Vector();

    public PortletDock() {}

    public PortletDock(String title) {
        this.title = title;
    }

    public PortletDock(int orientation) {
        this.orientation = orientation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getComponentIndex(PortletComponent c) {
        return components.indexOf(c);
    }

    public PortletComponent getComponentAtIndex(int i) {
        return (PortletComponent)components.get(i);
    }

    public void setMargin(PortletInsets margin) {
        this.margin = margin;
    }

    public PortletInsets getMargin() {
        return margin;
    }

}







