/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Nov 29, 2002
 * Time: 3:52:37 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletWindow;

public class PortletStateLink {

    public static final String minimizeImage = "images/window_minimize.gif";
    public static final String maximizeImage = "images/window_maximize.gif";
    public static final String restoreImage = "images/window_restore.gif";

    private String stateHref = "";
    private String imageSrc = "";

    /**
     * Given a portlet window state, create the necessary URIs to represent the state in a portlet border
     */
    public PortletStateLink(String state) throws Exception {

        // Set the image src
        if (state.equalsIgnoreCase(PortletWindow.State.MINIMIZED.toString())) {
            imageSrc = minimizeImage;
        } else if (state.equalsIgnoreCase(PortletWindow.State.MAXIMIZED.toString())) {
            imageSrc = maximizeImage;
        } else if (state.equalsIgnoreCase(PortletWindow.State.RESIZING.toString())) {
            imageSrc = restoreImage;
        } else {
            throw new Exception("No matching PortletWindow.State found for received window mode: " + state);
        }
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setStateHref(String stateHref) {
        this.stateHref = stateHref;
    }

    public String getStateHref() {
        return stateHref;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("image src: " + imageSrc + "\n");
        sb.append("state href: " + stateHref + "\n");
        return sb.toString();
    }
}
