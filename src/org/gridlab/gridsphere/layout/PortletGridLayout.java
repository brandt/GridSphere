/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.Hashtable;
import java.util.Map;

public class PortletGridLayout implements LayoutManager {

    private int rows = 1;
    private int cols = 1;
    private int hgap = 1;
    private int vgap = 1;

    private Map components = new Hashtable();

    public PortletGridLayout() {}

    public PortletGridLayout(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public PortletGridLayout(int rows, int cols, int hgap, int vgap) {
        this.rows = rows;
        this.cols = cols;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    public void setColumns(int cols) {
        this.cols = cols;
    }

    public int getColumns() {
        return cols;
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

