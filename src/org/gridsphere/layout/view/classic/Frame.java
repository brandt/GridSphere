/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Frame.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.classic;

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

    protected static StringBuffer END_FRAME_BORDER = new StringBuffer("</td></tr>\n");
    protected static StringBuffer END_FRAME = new StringBuffer("</table><!--- PORTLET ENDS HERE -->\n");
    protected static StringBuffer MINIMIZE_FRAME = new StringBuffer("\n\t<tr><td class=\"window-content-minimize\"></td></tr>");

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
        frameBuffer.append("<table  ");
        if (frame.getOuterPadding().equals("")) {
            frameBuffer.append(" cellspacing=\"0\" class=\"window-main\"");
        } else {
            frameBuffer.append(" cellspacing=\"0\" style=\"margin:" + frame.getOuterPadding() + "px\" class=\"window-main\" ");        // this is the main table around one portlet
        }
        frameBuffer.append(">");
        return frameBuffer;
    }


    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        PortletFrame frame = (PortletFrame)comp;
        StringBuffer postframe = new StringBuffer();
        if (!frame.getTransparent()) {
            postframe.append("\n<tr><td  ");      // now the portlet content begins
            if (!frame.getInnerPadding().equals("")) {
                postframe.append("style=\"padding:" + frame.getInnerPadding() + "px\"");
            }
            postframe.append(" class=\"window-content\"> ");
        } else {
            postframe.append("<tr><td >");
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
        postframe.append("<form action=\"" + portletURI.toString() + "\" method=\"POST\"");
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String value = bundle.getString("UNSUBSCRIBE_MESSAGE");
        String ok = bundle.getString("OK");
        String cancel = bundle.getString("CANCEL");
        postframe.append("<p><b>" + value + "</b></p>");

        portletURI = res.createURI();

        portletURI.addParameter(PortletWindow.State.CLOSED.toString(), Boolean.TRUE.toString());

        postframe.append("<p><input class=\"portlet-form-button\" type=\"submit\" name=\"" + SportletProperties.DEFAULT_PORTLET_ACTION + "=" + PortletFrame.FRAME_CLOSE_OK_ACTION + "\" value=\"" + ok + "\"");
        portletURI = res.createURI();

        portletURI.addParameter(PortletWindow.State.CLOSED.toString(), Boolean.FALSE.toString());
        postframe.append("<input class=\"portlet-form-button\" type=\"submit\" name=\"" + SportletProperties.DEFAULT_PORTLET_ACTION + "=" + PortletFrame.FRAME_CLOSE_CANCEL_ACTION + "\" value=\"" + cancel + "\"");
        postframe.append("</p></form>");

        return postframe;
    }

}
