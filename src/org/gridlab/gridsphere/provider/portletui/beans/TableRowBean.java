/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class TableRowBean extends BeanContainer {

    protected boolean isHeader = false;
    protected String TABLE_HEADER_STYLE = "portlet-frame-header";

    public TableRowBean() {
        super();
    }

    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    public boolean getHeader() {
        return isHeader;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<tr>");
        if (isHeader) {
            Iterator it = container.iterator();
            while (it.hasNext()) {
                BaseComponentBean tagBean = (BaseComponentBean)it.next();
                tagBean.setCssStyle(TABLE_HEADER_STYLE);
            }
        }
        sb.append(super.toString());
        sb.append("</tr>");
        return sb.toString();
    }
}
