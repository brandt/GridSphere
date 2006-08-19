/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: DefaultTableModel.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.model;

import org.gridsphere.provider.portletui.beans.*;

import java.util.*;

/**
 * A <code>DefaultTableModel</code> provides a data model used by the <code>TableBean</code>
 */
public class DefaultTableModel extends BaseBean implements TagBean {

    protected List dataList = new Vector();

    /**
     * Constructs a default table model
     */
    public DefaultTableModel() {
        dataList = new Vector();
    }

    /**
     * Constructs a default table model from a list of <code>TableRowBean</code>s
     *
     * @param dataList a list of <code>TableRowBean</code>s
     */
    public DefaultTableModel(List dataList) {
        this.dataList = dataList;
    }

    /**
     * Constructs a default table model from a Map containing String name/value pairs
     *
     * @param paramMap a Map containing String name/value pairs
     */
    public DefaultTableModel(Map paramMap) {
        TableRowBean tableRow = null;
        TableCellBean cellbean = null;
        Set set = paramMap.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            tableRow = new TableRowBean();
            String key = (String) it.next();
            Object obj = paramMap.get(key);
            //if (obj instanceof List) {

            //}
            if (obj instanceof String) {
                String value = (String) paramMap.get(key);
                TextBean tb1 = new TextBean();
                tb1.setValue(key);
                cellbean = new TableCellBean();
                cellbean.addBean(tb1);
                tableRow.addBean(cellbean);
                TextBean tb2 = new TextBean();
                tb2.setValue(" " + value);
                cellbean = new TableCellBean();
                cellbean.addBean(tb2);
                tableRow.addBean(cellbean);
                dataList.add(tableRow);
            }
        }
    }

    /**
     * Adds a <code>TableRowBean</code> to the model
     *
     * @param rowBean a <code>TableRowBean</code>
     */
    public void addTableRowBean(TableRowBean rowBean) {
        dataList.add(rowBean);
    }

    /**
     * Clears the model
     */
    public void clear() {
        dataList.clear();
    }

    /**
     * Sets the model with a list of <code>TableRowBean</code>s
     *
     * @param rowBeans a list of <code>TableRowBean</code>s
     */
    public void setTableRowBeans(List rowBeans) {
        dataList = rowBeans;
    }

    /**
     * Returns a list of <code>TableRowBean</code>s
     *
     * @return a list of <code>TableRowBean</code>s
     */
    public List getTableRowBeans() {
        return dataList;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            TableRowBean trb = (TableRowBean) it.next();
            sb.append(trb.toStartString());
            sb.append(trb.toEndString());
        }
        return sb.toString();
    }

    public String toEndString() {
        return "";
    }
}
