package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableCellTag extends ContainerTag {

    protected TableCellBean cellBean = null;
    protected String width = "";
    protected String cellSpacing = "";

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

    public void setCellBean(TableCellBean cellBean) {
        this.cellBean = cellBean;
    }

    public TableCellBean getCellBean() {
        return cellBean;
    }

    public int doStartTag() throws JspException {

        //System.err.println("in TableCellTag:doStartTag");
        list = new Vector();

        //System.err.println("creating new list");

        if (!beanId.equals("")) {
            cellBean = (TableCellBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (cellBean == null) cellBean = new TableCellBean();
        } else {
            cellBean = new TableCellBean();
        }

        /*
        if (cellBean == null) {
            System.err.println("creating new cellbean");
            cellBean = new TableCellBean();
        }
        */
        ContainerTag rowTag = (ContainerTag)getParent();
        if (rowTag == null) return SKIP_BODY;
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        //System.err.println("in TableCellTag:doEndTag");
        cellBean.setCellSpacing(cellSpacing);
        cellBean.setWidth(width);
        ContainerTag rowTag = (ContainerTag)getParent();
        if (rowTag != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                BaseComponentBean bean = (BaseComponentBean)it.next();
                if (bean.toString() != null) {
                    //System.err.println("adding cell tagbean " + tagBean.toString());
                    cellBean.addBean(bean);
                    cellBean.setCssStyle(bean.getCssStyle());
                }
            }
            rowTag.addTagBean(cellBean);
        }
        /*
        if (!beanId.equals("")) {
            store(beanId, cellBean);
        }
        */
        return EVAL_BODY_INCLUDE;
    }
}
