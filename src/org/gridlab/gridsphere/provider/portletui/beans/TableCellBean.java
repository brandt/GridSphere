/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class TableCellBean extends TagBeanContainer implements TagBean {

    public TableCellBean() {
        super();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<td class=\"" + cssStyle + "\">");
        sb.append(super.toString());
        sb.append("</td>");
        return sb.toString();
    }

}
