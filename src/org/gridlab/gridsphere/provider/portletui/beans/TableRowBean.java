/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class TableRowBean extends TagBeanContainer {

    public TableRowBean() {
        super();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<tr>");
        sb.append(super.toString());
        sb.append("</tr>");
        return sb.toString();
    }
}
