/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.Iterator;

/**
 * A <code>TableCellBean</code> represents a table cell and is contained by a <code>TableRowBean</code>
 */
public class TableCellBean extends BeanContainer implements TagBean {

    protected String width = null;
    protected String TABLE_CELL_STYLE = "portlet-section-body";

    /**
     * Constructs a default table cell bean
     */
    public TableCellBean() {
        super();
        this.cssStyle = TABLE_CELL_STYLE;
    }

    public TableCellBean(BaseComponentBean compBean) {
        super();
        this.addBean(compBean);
        this.cssStyle = TABLE_CELL_STYLE;
    }

    /**
     * Constructs a table cell bean from a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public TableCellBean(PortletRequest req, String beanId) {
        super();
        this.request = req;
        this.beanId = beanId;
        this.cssStyle = TABLE_CELL_STYLE;
    }

    /**
     * Sets the table cell width
     *
     * @param width the table cell width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Returns the table cell width
     *
     * @return the table cell width
     */
    public String getWidth() {
        return width;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<td ");
        if (!cssStyle.equals("")) sb.append("class=\"" + cssStyle + "\"");
        if (width != null) sb.append(" width=\"" + width + "\"");
        sb.append(">");
        Iterator it = container.iterator();
        while (it.hasNext()) {
            BaseComponentBean bean = (BaseComponentBean)it.next();
            sb.append(bean.toStartString());
            sb.append(bean.toEndString());
        }
        return sb.toString();
    }

    public String toEndString() {
        return "</td>";
    }

}
