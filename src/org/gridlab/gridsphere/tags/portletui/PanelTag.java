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
public class PanelTag extends ContainerTag {

    public static final String PANEL_WIDTH = "200";
    public static final String PANEL_SPACING = "1";

    protected String width = PANEL_WIDTH;
    protected String cellSpacing = PANEL_SPACING;

    protected PanelBean panelBean = null;

    public void setPanelBean(PanelBean panelBean) {
        this.panelBean = panelBean;
    }

    public PanelBean getPanelBean() {
        return panelBean;
    }

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
        list = new Vector();

        if (!beanId.equals("")) {
            panelBean = (PanelBean)pageContext.getSession().getAttribute(getBeanKey());
            if (panelBean != null) {
                return SKIP_BODY;
            } else {
                panelBean = new PanelBean();
            }
        } else {
            panelBean = new PanelBean();
        }
        panelBean.setWidth(width);
        panelBean.setCellSpacing(cellSpacing);
        //if (panelBean == null)
        //System.err.println("creating new tablebean");
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        // do all the rendering for this table
        //System.err.println("in PanelTag:doEndTag");
        //System.err.println("in PanelTag: # of tags: " + list.size());
        // add all components to pane
        Iterator it = list.iterator();
        while (it.hasNext()) {
            BaseComponentBean bc = (BaseComponentBean)it.next();
            //System.err.println("adding " + t.toString() + " to panel");
            panelBean.addBean(bc);
        }

        /*
        if (!beanId.equals("")) {
            //System.err.println("setting tablemodel in table");
            store(getBeanKey(), panelBean);
        }*/

        try {
            JspWriter out = pageContext.getOut();
            //System.err.println("printing table pane " + panelBean.toString());
            out.print(panelBean.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }
}
