/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

public class TableBean extends BaseBean implements TagBean {

    private DefaultTableModel defaultModel = new DefaultTableModel();

    public TableBean() {}

    public TableBean(DefaultTableModel defaultModel) {
        this.defaultModel = defaultModel;
    }

    public void setTableModel(DefaultTableModel defaultModel) {
        this.defaultModel = defaultModel;
    }

    public DefaultTableModel getTableModel() {
        return defaultModel;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table>");
        sb.append(defaultModel.toString());
        sb.append("</table>");
        return sb.toString();
    }
}
