/*
 * @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridsphere.layout.view.brush;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletFrame;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.FrameView;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Locale;
import java.util.ResourceBundle;


public class Frame extends BaseRender implements FrameView {

    protected static StringBuffer END_FRAME_BORDER = new StringBuffer("</div>\n");
    protected static StringBuffer END_FRAME = new StringBuffer("\n </div><!--- |||||||||||||| PORTLET ENDS HERE -->\n");
    protected static StringBuffer MINIMIZE_FRAME = new StringBuffer("\n\t<div class=\"gridsphere-window-portlet-minimized\">&nbsp;</div>");

    /**
     * Constructs an instance of PortletFrame
     */
    public Frame() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        PortletFrame frame = (PortletFrame) comp;
        ///// begin portlet frame
        StringBuffer frameBuffer = new StringBuffer();
        frameBuffer.append("\n<!-- |||||||||||| PORTLET STARTS HERE -->\n");
        frameBuffer.append("<div id=\"").append(frame.getComponentID()).append("\"");
        if (frame.getOuterPadding().equals("")) {
            frameBuffer.append(" class=\"gridsphere-window-portlet\"");
        } else {
            frameBuffer.append(" style=\"padding: ").append(frame.getOuterPadding()).append(";\" class=\"gridsphere-window-portlet\" "); // this is the main div around one portlet
        }
        frameBuffer.append(">\n");
        return frameBuffer;
    }


    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        PortletFrame frame = (PortletFrame) comp;
        StringBuffer postframe = new StringBuffer();
        if (!frame.getTransparent()) {
            postframe.append("\n<div ");      // now the portlet content begins
            if (!frame.getInnerPadding().equals("")) {
                postframe.append("style=\"padding: ").append(frame.getInnerPadding()).append(";\"");
            }
            postframe.append(" class=\"gridsphere-window-content\"> \n");
        } else {
            postframe.append("<div>\n");
        }
        return postframe;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return END_FRAME_BORDER;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return END_FRAME;
    }

    public StringBuffer doRenderMinimizeFrame(GridSphereEvent event, PortletFrame frame) {
        return MINIMIZE_FRAME;
    }

    public StringBuffer doRenderCloseFrame(GridSphereEvent event, PortletFrame frame) {
        StringBuffer postframe = new StringBuffer();
        RenderResponse res = event.getRenderResponse();
        RenderRequest req = event.getRenderRequest();

        PortletURL portletURI = res.createRenderURL();
        portletURI.setParameter(SportletProperties.COMPONENT_ID, String.valueOf(frame.getPortletTitleBar().getComponentID()));
        portletURI.setParameter(SportletProperties.PORTLET_WINDOW, "CLOSED");
        postframe.append("<form action=\"").append(portletURI.toString()).append("\" method=\"post\"");
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String value = bundle.getString("UNSUBSCRIBE_MESSAGE");
        String ok = bundle.getString("OK");
        String cancel = bundle.getString("CANCEL");
        postframe.append("<p><b>").append(value).append("</b></p>");

        portletURI = res.createRenderURL();

        portletURI.setParameter("CLOSED", Boolean.TRUE.toString());

        postframe.append("<p><input class=\"portlet-form-button\" type=\"submit\" name=\"" + SportletProperties.DEFAULT_PORTLET_ACTION + "=" + PortletFrame.FRAME_CLOSE_OK_ACTION + "\" value=\"").append(ok).append("\"");
        portletURI = res.createRenderURL();

        portletURI.setParameter("CLOSED", Boolean.FALSE.toString());
        postframe.append("<input class=\"portlet-form-button\" type=\"submit\" name=\"" + SportletProperties.DEFAULT_PORTLET_ACTION + "=" + PortletFrame.FRAME_CLOSE_CANCEL_ACTION + "\" value=\"").append(cancel).append("\"");
        postframe.append("</p></form>");

        return postframe;
    }
}
