/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class TableCellBean extends BaseBeanImpl {

    public List container = new ArrayList();

    public TableCellBean() {

    }

    public void addTagBean(TagBean tagBean) {
        container.add(tagBean);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<td>");
        Iterator it = container.iterator();
        while (it.hasNext()) {
            TagBean tagBean = (TagBean)it.next();
            sb.append(tagBean.toString());
            System.err.println("its the tagbean out: " + tagBean.toString());
        }
        sb.append("</td>");
        return sb.toString();
    }

}
