/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Nov 29, 2002
 * Time: 3:52:37 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.Portlet;

public class PortletModeLink {

    public static final String configImage = "images/window_configure.gif";
    public static final String editImage = "images/window_edit.gif";
    public static final String helpImage = "images/window_help.gif";

    private String modeHref = "";
    private String imageSrc = "";

    /**
     * Given a potlet mode, create the necessary URIs to represnt the mode in a portlet border
     */
    public PortletModeLink(String mode) throws Exception {

        // Set the image src
        if (mode.equalsIgnoreCase(Portlet.Mode.CONFIGURE.toString())) {
            imageSrc = configImage;
        } else if (mode.equalsIgnoreCase(Portlet.Mode.EDIT.toString())) {
            imageSrc = editImage;
        } else if (mode.equalsIgnoreCase(Portlet.Mode.HELP.toString())) {
            imageSrc = helpImage;
        } else {
            throw new Exception("No matching Portlet.Mode found for received portlet mode: " + mode);
        }
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setModeHref(String modeHref) {
        this.modeHref = modeHref;
    }

    public String getModeHref() {
        return modeHref;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("image src: " + imageSrc + "\n");
        sb.append("mode href: " + modeHref + "\n");
        return sb.toString();
    }
}
