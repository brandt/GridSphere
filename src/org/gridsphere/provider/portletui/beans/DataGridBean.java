package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.PortletURI;
import org.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: DataGridBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */

public class DataGridBean extends BeanContainer implements TagBean {

    private int size = 10;
    private String header = null;
    // list of db results we scroll through
    private List list = null;
    private int startPos = 0;
    private PortletURI uri = null;
    // holds the controlelemenet
    private StringBuffer control = null;
    private String width = null;
    private HttpServletRequest request;

    public DataGridBean() {
        super();
    }

    public DataGridBean(String beanId) {
        super();
        this.beanId = beanId;
    }

    // TODO place Oliver's hack here and not in base bean
    public void setHttpServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Returns the startposition of the scrolling
     *
     * @return startpositio of the scrolling
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * Sets the startposition of the scrolling
     *
     * @param startPos position of the scrolling
     */
    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    /**
     * Returns the size of the scrollwindow.
     *
     * @return size of the scrollwindow
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the scrollwindow.
     *
     * @param size size of the scrollwindow
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the header of the datagrid.
     *
     * @return header of the datagrid
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the header of the datagrid.
     *
     * @param header header of the datagrid
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Returns the list of object which will be ieterated over.
     *
     * @return list of objects
     */
    public List getList() {
        return list;
    }

    /**
     * Sets the list of object which will be iterated over
     *
     * @param list
     */
    public void setList(List list) {
        this.list = list;
    }

    /**
     * Returns the PortletURI for this ui component
     *
     * @return portletURI for this component
     */
    public PortletURI getUri() {
        return uri;
    }


    /**
     * Sets the PortletURI for this component
     *
     * @param uri
     */
    public void setUri(PortletURI uri) {
        this.uri = uri;
    }

    /**
     * Gets the width of the datagrid.
     *
     * @return width of the datagrid
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the width of the elemet
     *
     * @param width width of the element
     */
    public void setWidth(String width) {
        this.width = width;
    }

    private String createLink(PortletURI uri, int pos, String desc, boolean renderlink) {
        StringBuffer result = new StringBuffer();

        if (renderlink) {
            uri.addParameter(beanId + "_pos", new Integer(pos).toString());
            result.append("<a class=\"ui-datagrid-controls-element-active\" href=\"" + uri.toString() + "\"> " + desc + "</a>");
        } else {
            result.append("<span class=\"ui-datagrid-controls-element-inactive\">" + desc + "</span>");
        }
        return result.toString();
    }


    public String toStartString() {


        // header output
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"ui-datagrid\"");
        if (width != null) {
            sb.append(" width=\"" + width + "\"");
        }
        sb.append("><tr><td>");

        if (key != null) {
            header = this.getLocalizedText(key, "DataGrid");
        }

        if (header != null) sb.append("<div class=\"ui-datagrid-title\">" + header + "</div></td></tr>\n");


        String prefix = request.getParameter(SportletProperties.PREFIX);
        if (prefix == null) {
            prefix = "";
        } else {
            prefix += "_";
        }

        String req_beanId = request.getParameter(prefix + "beanId");

        if (req_beanId != null) {
            if (req_beanId.equals(beanId)) {
                String req_startPos = request.getParameter(prefix + req_beanId + "_pos");
                startPos = Integer.parseInt(req_startPos);
                if (startPos > list.size()) startPos = list.size();
            }
        }

        boolean renderlink = true;

        uri.addParameter("beanId", beanId);


        // only show controls when the datagridsize is smaller then the size of the list
        if (this.size < list.size()) {

            control = new StringBuffer();
            control.append("<tr><td>");
            control.append("<div class=\"ui-datagrid-controls\">");

            // go to first link
            uri.addParameter(beanId + "_pos", "0");
            if (startPos == 0) {
                renderlink = false;
            } else {
                renderlink = true;
            }
            control.append(createLink(uri, 0, this.getLocalizedText("UI_DATAGRID_START"), renderlink));

            // go to prev link
            int newPrevPos = startPos - size;
            if (newPrevPos < 0) newPrevPos = 0;
            if (startPos == 0) {
                renderlink = false;
            } else {
                renderlink = true;
            }
            control.append(createLink(uri, newPrevPos, this.getLocalizedText("UI_DATAGRID_PREV"), renderlink));

            // go to next link
            int newNextPos = startPos + size;
            if (newNextPos >= list.size()) {
                newNextPos = list.size();
                renderlink = false;
            } else {
                renderlink = true;
            }
            if (newNextPos < 0) newNextPos = 0;
            control.append(createLink(uri, newNextPos, this.getLocalizedText("UI_DATAGRID_NEXT"), renderlink));

            // go to end link
            int newEndPos = list.size() - size;
            if (newEndPos < 0) newEndPos = 0;
            if (startPos <= list.size() && startPos >= (list.size() - size)) {
                renderlink = false;
            } else {
                renderlink = true;
            }
            control.append(createLink(uri, newEndPos, this.getLocalizedText("UI_DATAGRID_END"), renderlink));

            // showing the number
            int endPos = startPos + size;
            if (endPos > list.size()) {
                endPos = list.size();
            }
            if (list != null) control.append(" " + (startPos + 1) + "-" + endPos + "(" + list.size() + ")");

            control.append("</div>");

            control.append("</td></tr>");

            sb.append(control);
        }

        // new row for datagrid
        sb.append("<tr><td>");
        sb.append("<table class=\"ui-datagrid-content\" cellspacing=\"0\">");
        return sb.toString();
    }


    public String toEndString() {
        StringBuffer sb = new StringBuffer();


        // iterate through cells to put out the header
        for (int i = 0; i < container.size(); i++) {
            DataGridColumnBean cellBean = (DataGridColumnBean) container.get(i);
            sb.append(cellBean.toStartString());
        }

        // now..

        // loop over the elements of the data
        if (list != null) {

            int endPos = startPos + size;
            if (endPos > list.size()) endPos = list.size();
            for (int j = startPos; j < endPos; j++) {
                int mod = j % 2;
                sb.append("<tr class=\"ui-datagrid-row-");
                if (mod == 0) {
                    sb.append("odd");
                } else {
                    sb.append("even");
                }
                sb.append("\">");
                for (int i = 0; i < container.size(); i++) {
                    DataGridColumnBean columnBean = (DataGridColumnBean) container.get(i);

                    sb.append("<td class=\"ui-datagrid-cell\" cellpadding=\"0\" cellspacing=\"0\">");

                    columnBean.setData(list.get(j));
                    sb.append(columnBean.toEndString());

                    sb.append("</td>");
                }
                sb.append("</tr>");
            }
        }

        sb.append("</table>");
        // appends the controls again at the bottom
        if (control != null) {
            sb.append(control);
        }
        sb.append("</td></tr></table>");
        return sb.toString();
    }
}