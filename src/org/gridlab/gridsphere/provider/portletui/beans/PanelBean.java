/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * A <code>PanelBean</code> provides a stylized table that is generally used as a container for
 * <code>FrameBean</code>s
 */
public class PanelBean extends BeanContainer implements TagBean {

    public static final String PANEL_STYLE = "portlet-pane";
    public static final String PANEL_WIDTH = "100%";
    public static final String PANEL_SPACING = "1";

    protected String cellSpacing = PANEL_SPACING;
    protected String width = PANEL_WIDTH;

    /**
     * Constructs a default panel bean
     */
    public PanelBean() {
        this.cssStyle = PANEL_STYLE;
    }

    /**
     * Constructs a panel bean from a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public PanelBean(PortletRequest req, String beanId) {
        this.cssStyle = PANEL_STYLE;
        this.beanId = beanId;
        this.request = req;
    }

    /**
     * Sets the panel (table) width
     *
     * @param width the panel width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Returns the panel (table) width
     *
     * @return the panel width
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the panel (table) cell spacing
     *
     * @param cellSpacing the panel cell spacing
     */
    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    /**
     * Returns the panel (table) cell spacing
     *
     * @return  the panel cell spacing
     */
    public String getCellSpacing() {
        return cellSpacing;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"" + cssStyle + "\" ");
        sb.append(" cellspacing=\"" + cellSpacing + "\" ");
        sb.append(" width=\"" + width + "\" ");
        sb.append(">");
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();

        int numRows = container.size();
        int i = 0;
        //System.err.println("in PanelBean: # of tags: " + container.size());
        while (i < container.size()) {
            sb.append("<tr>");
            int j = 0;
            while ((j < numRows) && (i < container.size())) {
                //sb.append("<td>");
                TagBean tagBean = (TagBean)container.get(i);
                //System.err.println("in PanelBean: its " + tagBean.toString());
                sb.append(tagBean.toStartString());
                sb.append(tagBean.toEndString());
                //sb.append("</td>");
                j++; i++;
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

}
