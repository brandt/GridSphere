/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

public class ErrorFrameBean extends FrameBean implements TagBean {

    protected String key = null;
    protected String value = null;

    public ErrorFrameBean() {
        super();
        makeErrorBean();
    }

    public ErrorFrameBean(PortletRequest req, String beanId) {
        super();
        this.beanId = beanId;
        this.request = req;
        makeErrorBean();
    }

    protected void makeErrorBean() {
        DefaultTableModel tm = new DefaultTableModel();
        TableRowBean tr = new TableRowBean();
        TableCellBean tc = new TableCellBean();
        TextBean text = new TextBean();
        text.setError(true);
        if (key != null) {
            System.err.println("adding key");
            text.setKey(key);
        }
        if (value != null) {
            System.err.println("adding value");
            text.setValue(value);
        }
        tc.addBean(text);
        tc.setCssStyle(text.getCssStyle());
        tr.addBean(tc);
        tm.addTableRowBean(tr);
        setTableModel(tm);
    }

    public void setKey(String key) {
        this.key = key;
        makeErrorBean();
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
        makeErrorBean();
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        if ((key == null) && (value == null)) {
            return "";
        } else {
            return super.toString();

        }
    }
}
