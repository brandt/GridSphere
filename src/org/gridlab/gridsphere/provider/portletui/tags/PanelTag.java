/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.PanelBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * A <code>PanelTag</code> represents a stylized table that generally conatins other <code>TableTag</code> or
 * <code>FrameTag</code> nested tags
 */
public class PanelTag extends BaseComponentTag {

    protected String width = PanelBean.PANEL_WIDTH;
    protected String cellSpacing = PanelBean.PANEL_SPACING;

    protected PanelBean panelBean = null;

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

        if (!beanId.equals("")) {
            panelBean = (PanelBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (panelBean == null) {
                return SKIP_BODY;
            }
        } else {
            panelBean = new PanelBean();
            panelBean.setWidth(width);
            panelBean.setCellSpacing(cellSpacing);
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(panelBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        if (panelBean == null) return EVAL_PAGE;

        try {
            JspWriter out = pageContext.getOut();
            out.print(panelBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
