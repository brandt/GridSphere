/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The <code>PortletGridLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of columns.
 * Portlets are arranged in column-wise order starting from the left most column.
 */
public class PortletGridLayout extends PortletFrameLayout {

    private int numColumns;
    private int[] colSizes;
    private String columnString;

    /**
     * Constructs an instance of PortletGridLayout
     */
    public PortletGridLayout() {
    }

    /*
    public String getClassName() {
        return PortletGridLayout.class.getName();
    }
     */

    /**
     * Sets the columns definition string. Normally specified in the layout.xml
     * as a comma delimted list of numbers that must add to 100.
     * <p>e.g. 40,30,30
     *
     * @param columnString the column definition string
     */
    public void setColumns(String columnString) {
        this.columnString = columnString;
    }

    /**
     * Returns the columns definition string. Normally specified in the layout.xml
     * as a comma delimted list of numbers that must add to 100.
     * <p>e.g. 40,30,30
     */
    public String getColumns() {
        return columnString;
    }

    /**
     * Initializes the portlet grid layout. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(List list) {
        list = super.init(list);
        if (columnString != null) {
            StringTokenizer st = new StringTokenizer(columnString, ",");
            numColumns = st.countTokens();
            if (numColumns < 1) numColumns = 1;
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

    /**
     * Renders the portlet grid layout component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();

        //int j = 0, k = 0;

        //out.println("row: "+rows+" columns "+cols);

        int numComponents = components.size();
        PortletComponent p = null;

        int portletsPerColumns = numComponents / numColumns;
        int portletCount = 0;

        // ok this one is maximized show only this window
        for (int i = 0; i < numComponents; i++) {
            p = (PortletComponent) components.get(i);
            if (p.getWidth().equals("100%")) {
                // make another table around this, just for the padding
                out.println("<table border=\"0\" width=\"100%\" cellpadding=\"2\" cellspacing=\"0\"> ");
                out.println("<tr><td>");
                p.doRender(event);
                out.println("</td></tr></table>");
                return;
            }
        }

        //out.println("<table width=\"" + gwidth + "%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\">");
        out.println("<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"> <!-- overall gridlayout table -->");

        out.println("<tr> <!-- overall one row -->");
        for (int i = 0; i < numColumns; i++) {
            // new column
            out.println("<td width=\"" + colSizes[i] + "%\" valign=\"top\"> <!-- this is a row -->");
            // construct a table inside this column
            out.println("<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"> <!-- this is table inside row (" + i + ")-->");
            // now render the portlets in this column
            //out.println("<tr>");
            for (int j = 1; j <= portletsPerColumns; j++) {
                out.println("<tr><td>");
                p = (PortletComponent) components.get(portletCount);
                if (p.getVisible()) {
                    p.doRender(event);
                }
                out.println("</td></tr>");
                portletCount++;

                // if we have some (1) portlet left because of odd number of
                // portlets to display just render the last ones in that column here
                if ((portletCount < numComponents) && (i == numColumns - 1) && (j == portletsPerColumns)) {
                    j--;
                }
            }
            // close this row again
            out.println("</table> <!-- end table inside row -->");
            out.println("</td>");
        }
        out.println("</tr> <!-- end overall one row -->");
        out.println("</table> <!-- end overall gridlayout table -->");
    }

}

