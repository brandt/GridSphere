/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class TableRowBean extends BaseBean {

    public List rowList = new ArrayList();

    public TableRowBean() {
        super();
    }

    public void addTableCellBean(TableCellBean tableCellBean) {
        rowList.add(tableCellBean);
    }

    public List getTableCellBeans() {
        return rowList;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<tr>");
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            TableCellBean cellbean = (TableCellBean)it.next();
            sb.append(cellbean.toString());
        }
        sb.append("</tr>");
        return sb.toString();
    }
}
