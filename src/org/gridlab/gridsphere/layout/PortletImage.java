/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;

import java.io.PrintWriter;
import java.io.IOException;

public class PortletImage extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletImage.class);

    private String image;

    public PortletImage() {}

    public PortletImage(String image) {
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        super.doRender(ctx, req, res);
        req.setAttribute("image", image);
        try {
            ctx.include("/WEB-INF/conf/layout/portlet-image.jsp", req, res);
        } catch (PortletException e) {
            log.error("Unable to include component JSP", e);
            throw new PortletLayoutException("Unable to include component JSP", e);
        }
    }

}
