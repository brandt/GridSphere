/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.model;

import org.gridlab.gridsphere.provider.portletui.beans.*;

import java.util.*;

public class DefaultTableModel extends BaseBean implements TagBean {

    protected List dataList = new Vector();

    public DefaultTableModel() {
       dataList = new Vector();
    }

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
            //System.err.println("key= " + key);
            Object obj = paramMap.get(key);
            if (obj instanceof List) {

            }
            if (obj instanceof String) {
                String value = (String)paramMap.get(key);
                TextBean tb1 = new TextBean();
                tb1.setValue(key);
                cellbean = new TableCellBean();
                cellbean.addTagBean(tb1);
                tableRow.addTagBean(cellbean);
                TextBean tb2 = new TextBean();
                tb2.setValue(" " + value);
                cellbean = new TableCellBean();
                cellbean.addTagBean(tb2);
                tableRow.addTagBean(cellbean);
                dataList.add(tableRow);
            }
        }
    }

    public void addTableRowBean(TableRowBean rowBean) {
        dataList.add(rowBean);
    }

    public void clear() {
        dataList.clear();
    }

    public void setTableRowBeans(List rowBeans) {
        dataList = rowBeans;
    }

    public List getTableRowBeans() {
        return dataList;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            //System.err.println("printing DTM rows");
            TableRowBean trb = (TableRowBean)it.next();
            sb.append(trb.toString());
        }
        return sb.toString();
    }

}
