/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.portlet.PortletRequest;

public class TableBean extends BaseComponentBean implements TagBean {

    protected DefaultTableModel defaultModel = null;
    protected String cellSpacing = "1";
    protected String width = null;

    public TableBean(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public TableBean() {
        super();
    }

    public TableBean(PortletRequest req, String beanId) {
        super();
        this.request = req;
        this.beanId = beanId;
    }

    public TableBean(DefaultTableModel defaultModel) {
        this.defaultModel = defaultModel;
    }

    public void setTableModel(DefaultTableModel defaultModel) {
        this.defaultModel = defaultModel;
    }

    public DefaultTableModel getTableModel() {
        return defaultModel;
    }

    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    public String getCellSpacing() {
        return cellSpacing;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table");
        sb.append(" cellspacing=\"" + cellSpacing + "\" ");
        sb.append(" width=\"" + width + "\" >");
        if (defaultModel != null) sb.append(defaultModel.toStartString());
        return sb.toString();
    }

    public String toEndString() {
        return "</table>";
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("<table ");
        sb.append("cellspacing=\"" + cellSpacing + "\" ");
        if (width != null) {
            sb.append("width=\"" + width + "\" ");
        }
        sb.append(">");
        sb.append(defaultModel.toString());
        sb.append("</table>");

        return sb.toString();
    }
}
