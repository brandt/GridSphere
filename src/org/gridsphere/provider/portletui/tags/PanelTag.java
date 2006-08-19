/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PanelTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.PanelBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.StringTokenizer;

/**
 * A <code>PanelTag</code> represents a stylized table that generally conatins other <code>TableTag</code> or
 * <code>FrameTag</code> nested tags
 */
public class PanelTag extends BaseComponentTag {

    protected String width = PanelBean.PANEL_WIDTH;
    protected String cellSpacing = PanelBean.PANEL_SPACING;
    protected String cellPadding = PanelBean.PANEL_PADDING;
    protected String align = null;
    protected String border = PanelBean.PANEL_BORDER;
    protected String cols = "100%";
    protected String[] colArray;
    protected int numCols = 1;
    protected PanelBean panelBean = null;
    protected int counter = 0;

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
     * Sets the panel width
     *
     * @param width the panel width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Returns the panel width
     *
     * @return the panel width
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the number of columns in the panel
     *
     * @param cols the number of columns
     */
    public void setCols(String cols) {
        this.cols = cols;
    }

    /**
     * Returns the number of columns in the panel
     *
     * @return the number of columns in the panel
     */
    public String getCols() {
        return cols;
    }

    /**
     * Returns the number of columns in the panel
     *
     * @return the number of columns in the panel
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Returns the number of columns in the panel
     *
     * @return the number of columns in the panel
     */
    public String[] getColArray() {
        return colArray;
    }

    public void setColumnCounter(int counter) {
        this.counter = counter;
    }

    public int getColumnCounter() {
        return counter;
    }

    /**
     * Sets the panel cell spacing
     *
     * @param cellSpacing the panel cell spacing
     */
    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }


    /**
     * Returns the panel cell spacing
     *
     * @return the panel cell spacing
     */
    public String getCellSpacing() {
        return cellSpacing;
    }

    public int doStartTag() throws JspException {
        boolean includeBody = true;

        counter = 0;
        if (!beanId.equals("")) {
            panelBean = (PanelBean) getTagBean();
            if (panelBean == null) {
                panelBean = new PanelBean();
                this.setBaseComponentBean(panelBean);
            } else {
                includeBody = false;
            }
        } else {
            panelBean = new PanelBean();
            this.setBaseComponentBean(panelBean);
            panelBean.setWidth(width);

            StringTokenizer st = new StringTokenizer(cols, ",");
            numCols = st.countTokens();
            colArray = new String[numCols];
            int i = 0;
            String colStr;
            while (st.hasMoreElements()) {
                colStr = (String) st.nextElement();
                colArray[i++] = colStr.trim();
            }

            panelBean.setCols(cols);
            panelBean.setColArray(colArray);
            panelBean.setNumCols(numCols);
            panelBean.setCellSpacing(cellSpacing);
            panelBean.setCellPadding(cellPadding);
            panelBean.setBorder(border);
            panelBean.setCssClass(cssClass);
            panelBean.setCssStyle(cssStyle);
            if (align != null) panelBean.setAlign(align);
        }

        try {
            JspWriter out = pageContext.getOut();

            out.print(panelBean.toStartString());
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
        try {
            JspWriter out = pageContext.getOut();
            out.print(panelBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
