/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.jsr;

import org.gridlab.gridsphere.provider.portletui.beans.TableBean;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.provider.portletui.tags.jsr.BaseComponentTagImpl;
import org.gridlab.gridsphere.provider.portletui.tags.TableTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * A <code>TableTag</code> represents a table element and is defined by a <code>DefaultTableModel</code>
 */
public class TableTagImpl extends BaseComponentTagImpl implements TableTag {

    protected TableBean tableBean = null;
    protected String cellSpacing = null;
    protected String cellPadding = null;
    protected String border = null;
    protected String width = null;
    protected String align = null;
    protected boolean sortable = false;
    protected boolean isZebra = false;
    protected int rowCount = 0;
    protected int maxRows = -1;
    protected int currentPage = 0;

    /**
     * Sets the table model associated with this table
     *
     * @param tableModel the table model associated with this table
     */
    public void setTableModel(DefaultTableModel tableModel) {
        this.tableBean.setTableModel(tableModel);
    }

    /**
     * Returns the table model associated with this table
     *
     * @return the table model associated with this table
     */
    public DefaultTableModel getTableModel() {
        return tableBean.getTableModel();
    }

    /**
     * Sets the table bean
     *
     * @param tableBean the table bean
     */
    public void setTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
    }

    /**
     * Returns the table bean
     *
     * @return the table bean
     */
    public TableBean getTableBean() {
        return tableBean;
    }

    /**
     * Sets the table alignment e.g. "left", "center" or "right"
     *
     * @param align the table alignment
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * Returns the table alignment e.g. "left", "center" or "right"
     *
     * @return the table alignment
     */
    public String getAlign() {
        return align;
    }

    /**
     * Sets the table cell spacing
     *
     * @param cellSpacing the table cell spacing
     */
    public void setCellspacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    /**
     * Returns the table cell spacing
     *
     * @return the table cell spacing
     */
    public String getCellspacing() {
        return cellSpacing;
    }

    /**
     * Sets the panel (table) cell spacing
     *
     * @param cellPadding the panel cell padding
     */
    public void setCellpadding(String cellPadding) {
        this.cellPadding = cellPadding;
    }

    /**
     * Returns the panel (table) cell padding
     *
     * @return  the panel cell padding
     */
    public String getCellpadding() {
        return cellPadding;
    }

    /**
     * Sets the table border
     *
     * @param border the panel border
     */
    public void setBorder(String border) {
        this.border = border;
    }

    /**
     * Returns the panel border
     *
     * @return  the panel border
     */
    public String getBorder() {
        return border;
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

    public void setSortable(boolean isSortable) {
        sortable = isSortable;
    }

    public boolean getSortable() {
        return sortable;
    }

    public void setZebra(boolean isZebra) {
        this.isZebra = isZebra;
    }

    public boolean getZebra() {
        return isZebra;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void incrementRowCount() {
        this.rowCount++;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setMaxrows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getMaxrows() {
        return maxRows;
    }

    public int doStartTag() throws JspException {

        boolean includeBody = true;

        if (!beanId.equals("")) {
            tableBean = (TableBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (tableBean == null) {
                tableBean = new TableBean();
            } else {
                includeBody = false;
            }
        } else {
            tableBean = new TableBean();
            if (align != null) tableBean.setAlign(align);
            if (width != null) tableBean.setWidth(width);
            if (cellSpacing != null) tableBean.setCellSpacing(cellSpacing);
            if (cellPadding != null) tableBean.setCellPadding(cellPadding);
            if (border != null) tableBean.setBorder(border);
            if (sortable) {
                tableBean.setSortable(sortable);
                tableBean.setSortableID("td" + this.getUniqueId("gs_tableNum"));
            }
            tableBean.setMaxRows(maxRows);
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(tableBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        if (includeBody) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    public int doEndTag() throws JspException {
        tableBean.setRowCount(rowCount);
        try {
            JspWriter out = pageContext.getOut();
            out.print(tableBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
