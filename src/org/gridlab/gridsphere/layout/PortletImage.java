/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * A PortletImage is a wrapper for an image file to be displayed in the portal
 * page. A PortletImage is defined by an "img src" location of the image in the
 * web application directory
 */
public class PortletImage extends BasePortletComponent {

    private String image;

    /**
     * Constructs an instance of PortletImage
     */
    public PortletImage() {
    }

    /**
     * Constructs an instance of PortletImage from the provided location
     *
     * @param image the "img src" location of the image
     */
    public PortletImage(String image) {
        this.image = image;
    }

    /**
     * Sets the "img src" location of the image location
     *
     * @param image the "img src" location of the image location
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns the "img src" location of the image location
     *
     * @param image the "img src" location of the image location
     */
    public String getImage() {
        return image;
    }

    /**
     * Renders the portlet grid image component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();
        out.println("<table width=\"100%\"><td width=\"1\">");
        out.println("<spacer type=block width=\"100\"></td><td>");
        out.println("<img src=\"" + image + "\" align=\"right\">");
        out.println("</td></table>");
    }

}
