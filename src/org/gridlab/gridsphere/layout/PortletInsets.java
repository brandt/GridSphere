/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

public class PortletInsets {

    public int right, left, top, bottom;

    public PortletInsets(int top, int left, int bottom, int right) {
        this.top = top;
        this.right = right;
        this.left = left;
        this.bottom = bottom;
    }

}
