/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

public class PortletGridLayout extends BaseLayoutManager {

    private int numColumns;
    private int[] colSizes;
    private String columnString;

    public PortletGridLayout() {}

    public String getClassName() {
        return PortletGridLayout.class.getName();
    }

    public void setColumns(String columnString) {
        this.columnString = columnString;
    }

    public String getColumns() {
        return columnString;
    }

    public List init(List list) {
        list = super.init(list);
        if (columnString != null) {
            StringTokenizer st = new StringTokenizer(columnString, ",");
            numColumns = st.countTokens();
            colSizes = new int[numColumns];
            int i = 0;
            while (st.hasMoreTokens()) {
                String col = st.nextToken();
                colSizes[i] = Integer.parseInt(col);
                i++;
            }
        } else {
            numColumns = 1;
            colSizes = new int[1];
            colSizes[0] = 100;
        }
        return list;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();

        if (insets == null) insets = new PortletInsets();

        int j = 0, k = 0;

        //out.println("row: "+rows+" columns "+cols);

        int max = components.size();
        PortletComponent p = null;

        int gwidth = 100 / 1;

        //out.println("<table width=\"" + gwidth + "%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\">");
        out.println("<table border=\"0\" width=\"100%\"> <!-- overall gridlayout table -->");
        //@todo this rendering has to change anyway with  the placement of the portlets in rows and position
        while (k < max) {
            out.println("<tr> <!-- gridlayout row starts here -->");
            //insets.doRenderFirst(ctx,req,res);
            //insets.doRenderLast(ctx,req,res);
            while (j < 1) {
                p = (PortletComponent)components.get(k);

                if (p.getWidth().equals("100%")) {
                    out.println("<td width=\"100%\" valign=\"top\"> <!-- this one is maximized -->");
                } else if(p.isVisible()) {
                    out.println("<td valign=\"top\" width=\""+gwidth+"%\"> <!-- this is a place for a portlet -->");
                }
                if (p.isVisible()) {
                    p.doRender(event);
                }
                j++; k++;
                out.println("</td> <!-- portlet ends here -->");
            }
            j = 0;
           out.println("</tr> <!-- gridlayout row ends here -->");
        }

        out.println("</table> <!-- end overall gridlayout table -->");
    }

}

