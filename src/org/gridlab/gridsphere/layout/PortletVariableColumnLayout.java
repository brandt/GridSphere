/*
 * @author <a href="mailto:makub@ics.muni.cz">Martin Kuba</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 * The <code>PortletVariableColumnLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a column, but displays only one of its children.
 * Displays only the child, which has the same label as is value of session attribute
 * with name <code>variant.layout.mylabel</code> where mylabel is label of this component.
 * If such session attribute doesn't exist, displays the child specified by "variant" attribute.
 */
public class PortletVariableColumnLayout extends PortletFrameLayout implements Cloneable {

    /**
     * Prefix to be prepended to component label when searching session for variant.
     */
    public static final String LABEL_PREFIX = "variant.layout.";

    protected String variant = "";

    public PortletVariableColumnLayout() {
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getVariant() {
        return variant;
    }

    /**
     * Renders the component
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletResponse res = event.getPortletResponse();
        PortletRequest req = event.getPortletRequest();
        PrintWriter out = res.getWriter();

        PortletComponent p = null;

        // starting of the gridtable

        if (!components.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            sb.append("<table width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"> <!-- START COLUMN -->");

            sb.append("<tbody>");

            //find which variant to display
            String thisLabel = this.getLabel();
            String sesvariant = null;
            if (thisLabel != null) {
                Object _sesvariant = req.getSession(true).getAttribute(this.LABEL_PREFIX + thisLabel);
                if (_sesvariant instanceof String) {
                    sesvariant = (String) _sesvariant;
                }
            }
            if (sesvariant == null) sesvariant = this.getVariant();

            List scomponents = Collections.synchronizedList(components);
            synchronized (scomponents) {
                for (int i = 0; i < scomponents.size(); i++) {
                    sb.append("<tr><td valign=\"top\">");

                    p = (PortletComponent) scomponents.get(i);
                    String plabel = p.getLabel();

                    if (p.getVisible() && sesvariant.equals(plabel)) {
                        p.doRender(event);
                        sb.append(p.getBufferedOutput(req));
                    }

                    sb.append("</td></tr>");
                }
            }
            sb.append("</tbody></table> <!-- END COLUMN -->");
            req.setAttribute(SportletProperties.RENDER_OUTPUT + componentIDStr, sb);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletVariableColumnLayout g = (PortletVariableColumnLayout) super.clone();
        return g;
    }

}



