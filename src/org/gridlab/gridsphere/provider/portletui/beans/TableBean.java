/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

/**
 * A <code>TableBean</code> provides a table element
 */
public class TableBean extends BaseComponentBean implements TagBean {

    protected DefaultTableModel defaultModel = null;
    protected String cellSpacing = "0";
    protected String width = null;

    /**
     * Constructs a default table bean
     */
    public TableBean() {
        super();
    }

    /**
     * Constructs a table bean with a supplied CSS style
     *
     * @param cssStyle the CSS style
     */
    public TableBean(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    /**
     * Constructs a table bean from a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public TableBean(PortletRequest req, String beanId) {
        super();
        this.request = req;
        this.beanId = beanId;
    }

    /**
     * Sets the default table model for this table
     *
     * @param defaultModel the table model
     */
    public void setTableModel(DefaultTableModel defaultModel) {
        this.defaultModel = defaultModel;
    }

    /**
     * Returns the default table model
     *
     * @return the default table model
     */
    public DefaultTableModel getTableModel() {
        return defaultModel;
    }

    /**
     * Sets the table width
     *
     * @param width the table width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Returns the table width
     *
     * @return the table width
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the table cell spacing
     *
     * @param cellSpacing the table cell spacing
     */
    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    /**
     * Returns the table cell spacing
     *
     * @return the table cell spacing
     */
    public String getCellSpacing() {
        return cellSpacing;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table");
        if (cellSpacing != null) sb.append(" cellspacing=\"" + cellSpacing + "\" ");
        if (width != null) sb.append(" width=\"" + width + "\" >");
        if (defaultModel != null) sb.append(defaultModel.toStartString());
        return sb.toString();
    }

    public String toEndString() {
        return "</table>";
    }

}
