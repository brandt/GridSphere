/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletContext;

import java.util.Hashtable;
import java.util.Map;
import java.io.IOException;

public class PortletGridLayout extends BasePortletComponent implements LayoutManager {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletGridLayout.class);

    private int rows = 1;
    private int cols = 1;

    public PortletGridLayout() {}

    public PortletGridLayout(int rows, int cols) {
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

    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        super.doRender(ctx, req, res);
        log.debug("in doRender()");
        if (insets == null) insets = new PortletInsets();

        int i = 0, j = 0, k = 0;
        if (components.size() > rows*cols)
            throw new PortletLayoutException("Number of components specified exceeds rows * columns");

        int max = components.size();
        while ((i < rows) && (k < max)) {
            insets.doRender(ctx,req,res);
            while ((j < cols) && (k < max)) {
                PortletComponent p = (PortletComponent)components.get(k);
                p.doRender(ctx, req, res);
                j++; k++;
                insets.doRender(ctx,req,res);
            }
            i++;
        }

    }
}

