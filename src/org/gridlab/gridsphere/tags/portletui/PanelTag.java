package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.provider.ui.beans.TableCellBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PanelTag extends BaseComponentTag {

    public static final String PANEL_WIDTH = "200";
    public static final String PANEL_SPACING = "1";

    protected String width = PANEL_WIDTH;
    protected String cellSpacing = PANEL_SPACING;

    protected PanelBean panelBean = null;

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    public String getCellSpacing() {
        return cellSpacing;
    }

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            panelBean = (PanelBean)pageContext.getSession().getAttribute(getBeanKey());
        }
        if (panelBean == null) {
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

        try {
            JspWriter out = pageContext.getOut();
            out.print(panelBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
