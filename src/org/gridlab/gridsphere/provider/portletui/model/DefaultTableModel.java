/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.model;

import org.gridlab.gridsphere.provider.portletui.beans.*;

import java.util.*;

public class DefaultTableModel extends BaseBean implements TagBean {

    protected List dataList = new ArrayList();

    public DefaultTableModel() {}

    public DefaultTableModel(List dataList) {
        this.dataList = dataList;
    }

    public DefaultTableModel(Map paramMap) {
        TableRowBean tableRow = null;
        TableCellBean cellbean = null;
        Set set = paramMap.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            tableRow = new TableRowBean();
            String key = (String)it.next();

            Object obj = paramMap.get(key);
            if (obj instanceof List) {

            }
            if (obj instanceof String) {
                System.err.println("its an instanceif String!!!");
                String value = (String)paramMap.get(key);
                TextBean tb1 = new TextBean(key);
                cellbean = new TableCellBean();
                cellbean.addTagBean(tb1);
                tableRow.addTableCellBean(cellbean);
                tb1 = new TextBean(" " + value);
                cellbean = new TableCellBean();
                cellbean.addTagBean(tb1);
                tableRow.addTableCellBean(cellbean);
                dataList.add(tableRow);
            }
            store(this);
        }
    }

    public void addTableRowBean(TableRowBean rowBean) {
        dataList.add(rowBean);
        store(this);
    }

    public void clear() {
        dataList.clear();
        store(this);
    }

    public void setTableRowBeans(List rowBeans) {
        dataList = rowBeans;
        store(this);
    }

    public List getTableRowBeans() {
        return dataList;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            System.err.println("printing DTM rows");
            TableRowBean trb = (TableRowBean)it.next();
            sb.append(trb.toString());
        }
        return sb.toString();
    }

}
