/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TableTag.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.PortletURI;
import org.gridsphere.provider.portletui.beans.TableBean;
import org.gridsphere.provider.portletui.model.DefaultTableModel;

import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>TableTag</code> represents a table element and is defined by a <code>DefaultTableModel</code>
 */
public class TableTag extends BaseComponentTag {

    protected TableBean tableBean = null;
    protected String title = null;
    protected String cellSpacing = null;
    protected String cellPadding = null;
    protected String border = null;
    protected String width = null;
    protected String align = null;
    protected String valign = null;
    protected String background = null;
    protected boolean sortable = false;
    protected boolean isZebra = false;
    protected int rowCount = 0;
    protected int maxRows = -1;
    protected int currentPage = 0;
    protected boolean isShowAll = false;
    protected boolean filter = false;
    protected int numEntries = 0;

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
     * Returns the horizontal table alignment e.g. "center" or "bottom"
     *
     * @return the table horizontal alignment
     */
    public String getValign() {
        return valign;
    }

    /**
     * Returns the horizontal table alignment e.g. "center" or "bottom"
     *
     * @param valign alignment of the table
     */
    public void setValign(String valign) {
        this.valign = valign;
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
     * @return the panel cell padding
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
     * @return the panel border
     */
    public String getBorder() {
        return border;
    }

    /**
     * Sets the table background
     *
     * @param background the table background
     */
    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * Returns the table background
     *
     * @return the tabel background
     */
    public String getBackground() {
        return background;
    }

    /**
     * Returns true if a query filter is associated with this table
     *
     * @return true if a query filter is associated with this table
     */
    public boolean getFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public int getNumentries() {
        return numEntries;
    }

    public void setNumentries(int numEntries) {
        this.numEntries = numEntries;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setZebra(boolean isZebra) {
        this.isZebra = isZebra;
    }

    public boolean getZebra() {
        return isZebra;
    }

    public void setMaxrows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getMaxrows() {
        return maxRows;
    }

    public void incrementRowCount() {
        this.rowCount++;
        tableBean.setRowCount(rowCount);

    }

    public int getRowCount() {
        return rowCount;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }


    public void release() {
        tableBean = null;
        cellSpacing = null;
        cellPadding = null;
        title = null;
        border = null;
        width = null;
        align = null;
        valign = null;
        background = null;
        sortable = false;
        isZebra = false;
        rowCount = 0;
        maxRows = -1;
        currentPage = 0;
        numEntries = 0;
        super.release();
    }

    public int doStartTag() throws JspException {

        super.doStartTag();

        boolean includeBody = true;

        // get any parameter values if data is divided


        if (!beanId.equals("")) {
            tableBean = (TableBean) getTagBean();
            if (tableBean == null) {
                tableBean = new TableBean();
                setBaseComponentBean(tableBean);
            } else {
                includeBody = false;
            }

        } else {
            tableBean = new TableBean();
            this.setBaseComponentBean(tableBean);
        }

        if (maxRows > 0) {
            String curPage = pageContext.getRequest().getParameter(TableBean.CURRENT_PAGE);
            if (curPage != null) {
                currentPage = Integer.valueOf(curPage).intValue();
                tableBean.setCurrentPage(currentPage);
            }
            String showAll = pageContext.getRequest().getParameter(TableBean.SHOW_ALL);
            if (showAll != null) {
                maxRows = 0;
                isShowAll = true;
            }
            String showpages = pageContext.getRequest().getParameter(TableBean.SHOW_PAGES);
            if (showpages != null) {
                isShowAll = false;
            }
        }

        if (background != null) tableBean.setBackground(background);
        if (align != null) tableBean.setAlign(align);
        if (valign != null) tableBean.setValign(valign);
        if (width != null) tableBean.setWidth(width);
        if (cellSpacing != null) tableBean.setCellSpacing(cellSpacing);
        if (cellPadding != null) tableBean.setCellPadding(cellPadding);
        if (border != null) tableBean.setBorder(border);
        if (sortable) {
            tableBean.setSortable(sortable);
            tableBean.setSortableID("td" + this.getUniqueId("gs_tableNum"));
        }
        if (title != null) tableBean.setTitle(title);
        tableBean.setMaxRows(maxRows);
        tableBean.setZebra(isZebra);
        tableBean.setRowCount(0);
        tableBean.setShowall(isShowAll);
        if (numEntries != 0) tableBean.setNumEntries(numEntries);

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
        if (isJSR()) {
            RenderResponse res = (RenderResponse) pageContext.getAttribute("renderResponse");
            RenderRequest req = (RenderRequest) pageContext.getAttribute("renderRequest");
            PortletURL url = res.createRenderURL();
            try {
                url.setPortletMode(req.getPortletMode());
            } catch (PortletModeException e) {
                throw new JspException(e);
            }
            tableBean.setJSR(true);
            tableBean.setURIString(url.toString());
        } else {
            PortletRequest req = (PortletRequest) pageContext.getAttribute("portletRequest");
            PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
            PortletURI uri = res.createURI((org.gridsphere.portlet.Mode)req.getMode());
            tableBean.setJSR(false);
            tableBean.setURIString(uri.toString());
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(tableBean.toEndString());
            rowCount = 0;
        } catch (Exception e) {
            throw new JspException(e);
        }
        super.doEndTag();
        release();
        return EVAL_PAGE;
    }
}
