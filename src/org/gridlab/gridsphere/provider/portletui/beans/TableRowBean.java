/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.Iterator;

public class TableRowBean extends BeanContainer {

    protected boolean isHeader = false;
    public static final  String TABLE_HEADER_STYLE = "portlet-section-header";

    /**
     * Constructs a default table row bean
     */
    public TableRowBean() {
        super();
    }

    /**
     * Constructs a default table row bean
     */
    public TableRowBean(BaseComponentBean compBean) {
        super();
        this.addBean(compBean);
    }

    /**
     * Constructs a table row bean from a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public TableRowBean(PortletRequest req, String beanId) {
        super();
        this.request = req;
        this.beanId = beanId;
    }

    /**
     * Indicates if this table row is a header
     *
     * @param isHeader true if this table row is a header, false otherwise
     */
    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    /**
     * Returns true if this table row is a header, false otherwise
     *
     * @return true if this table row is a header, false otherwise
     */
    public boolean getHeader() {
        return isHeader;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<tr>");
        if (isHeader) {
            Iterator it = container.iterator();
            while (it.hasNext()) {
                BaseComponentBean tagBean = (BaseComponentBean)it.next();
                tagBean.setCssStyle(TABLE_HEADER_STYLE);
            }
        }
        Iterator it = container.iterator();
        while (it.hasNext()) {
            TableCellBean cellBean = (TableCellBean)it.next();
            sb.append(cellBean.toStartString());
            sb.append(cellBean.toEndString());
        }
        return sb.toString();
    }

    public String toEndString() {
        return "</tr>";
    }

}
