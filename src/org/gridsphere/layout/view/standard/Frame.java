/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Frame.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.standard;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletFrame;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.FrameView;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.PortletURI;
import org.gridsphere.portlet.PortletWindow;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import java.util.Locale;
import java.util.ResourceBundle;


public class Frame extends BaseRender implements FrameView {

    protected static StringBuffer END_FRAME_BORDER = new StringBuffer("</div>\n");
    protected static StringBuffer END_FRAME = new StringBuffer("</div><!--- PORTLET ENDS HERE -->\n");
    protected static StringBuffer MINIMIZE_FRAME = new StringBuffer("\n\t<div class=\"window-content-minimize\">&nbsp;</div>");

    /**
     * Constructs an instance of PortletFrame
     */
    public Frame() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        PortletFrame frame = (PortletFrame)comp;
        ///// begin portlet frame
        StringBuffer frameBuffer = new StringBuffer();
        frameBuffer.append("\n<!-- PORTLET STARTS HERE -->");
        frameBuffer.append("<div id=\"");
        frameBuffer.append(frame.getComponentID());
        frameBuffer.append("\"");
        if (frame.getOuterPadding().equals("")) {
            frameBuffer.append(" class=\"window-main\"");
        } else {
            frameBuffer.append(" style=\"padding:");
            frameBuffer.append(frame.getOuterPadding());
            frameBuffer.append("px\" class=\"window-main\" "); // this is the main div around one portlet
        }
        frameBuffer.append(">");
        return frameBuffer;
    }


    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        PortletFrame frame = (PortletFrame)comp;
        StringBuffer postframe = new StringBuffer();
        if (!frame.getTransparent()) {
            postframe.append("\n<div ");      // now the portlet content begins
            if (!frame.getInnerPadding().equals("")) {
                postframe.append("style=\"padding:");
                postframe.append(frame.getInnerPadding());
                postframe.append("px\"");
            }
            postframe.append(" class=\"window-content\"> ");
        } else {
            postframe.append("<div >");
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
        PortletResponse res = event.getPortletResponse();
        PortletRequest req = event.getPortletRequest();

        PortletURI portletURI = res.createURI();
        portletURI.addParameter(frame.getComponentIDVar(req), String.valueOf(frame.getPortletTitleBar().getComponentID()));
        portletURI.addParameter(SportletProperties.PORTLET_WINDOW, PortletWindow.State.CLOSED.toString());
        String tmp = "<form action=\"" + portletURI.toString() + "\" method=\"post\"";
        postframe.append(tmp);
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String value = bundle.getString("UNSUBSCRIBE_MESSAGE");
        String ok = bundle.getString("OK");
        String cancel = bundle.getString("CANCEL");
        tmp = "<p><b>" + value + "</b></p>";
        postframe.append(tmp);

        portletURI = res.createURI();

        portletURI.addParameter(PortletWindow.State.CLOSED.toString(), Boolean.TRUE.toString());
        tmp = "<p><input class=\"portlet-form-button\" type=\"submit\" name=\"" + SportletProperties.DEFAULT_PORTLET_ACTION + "=" + PortletFrame.FRAME_CLOSE_OK_ACTION + "\" value=\"" + ok + "\"";
        postframe.append(tmp);
        portletURI = res.createURI();

        portletURI.addParameter(PortletWindow.State.CLOSED.toString(), Boolean.FALSE.toString());
        tmp = "<input class=\"portlet-form-button\" type=\"submit\" name=\"" + SportletProperties.DEFAULT_PORTLET_ACTION + "=" + PortletFrame.FRAME_CLOSE_CANCEL_ACTION + "\" value=\"" + cancel + "\"";
        postframe.append(tmp);
        postframe.append("</p></form>");
        tmp = null;
        return postframe;
    }

}
