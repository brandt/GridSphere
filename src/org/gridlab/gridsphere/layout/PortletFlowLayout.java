/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.List;
import java.util.Map;
import java.util.Hashtable;
import java.io.IOException;

public class PortletFlowLayout extends BasePortletComponent implements LayoutManager {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletFlowLayout.class);

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;

    private int align = LEFT;
    private int hgap = 1;
    private int vgap = 1;

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

    public int getAlignment() {
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

    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        super.doRender(ctx, req, res);
        log.debug("in doRender()");
    }

    public void doRenderFirst(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        doRender(ctx, req, res);
    }

    public void doRenderLast(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {}


}

