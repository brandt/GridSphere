/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.model;

import org.gridlab.gridsphere.provider.portletui.beans.TableRowBean;
import org.gridlab.gridsphere.provider.portletui.beans.BaseBeanImpl;
import org.gridlab.gridsphere.provider.portletui.beans.TagBean;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class DefaultTableModel extends BaseBeanImpl implements TagBean {

    private List dataList = new ArrayList();

    public DefaultTableModel() {}

    public DefaultTableModel(List dataList) {
        this.dataList = dataList;
    }

    public void addTableRowBean(TableRowBean rowBean) {
        dataList.add(rowBean);
        store(this);
    }

    public void clear() {
        dataList.clear();
        store(this);
    }

    public List getDataList() {
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
