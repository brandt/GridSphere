/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.List;
import java.util.Map;
import java.util.Hashtable;

public class PortletFlowLayout implements LayoutManager {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;

    private int align = LEFT;
    private int hgap = 1;
    private int vgap = 1;

    private Map components = new Hashtable();

    public PortletFlowLayout() {}

    public PortletFlowLayout(int align) {
        this.align = align;
    }

    public PortletFlowLayout(int align, int hgap, int vgap) {
        this.align = align;
        this.hgap = hgap;
        this.vgap = vgap;
    }

    public void setAlignment(int align) {
        this.align = align;
    }

    public int getAlign() {
        return align;
    }

    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    public int getHgap() {
        return hgap;
    }

    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    public int getVgap() {
        return vgap;
    }

    public void addLayoutComponent(String name, PortletComponent comp) {
        components.put(name, comp);
    }

    public void layoutContainer(PortletContainer parent) {

    }

    public void removeLayoutComponent(PortletComponent comp) {
        if (components.containsValue(comp))
            components.values().remove(comp);
    }

}

