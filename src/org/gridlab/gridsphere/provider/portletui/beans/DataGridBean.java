package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class DataGridBean extends BeanContainer implements TagBean {

    private int size = 10;
    private String header = null;
    // list of db results we scroll through
    private List list = null;
    private int startPos = 0;
    private PortletURI uri = null;

    public DataGridBean() {
        super();
    }

    public DataGridBean(String beanId) {
        super();
        this.beanId = beanId;
    }

    public DataGridBean(PortletRequest req, String beanId) {
        super();
        this.request = req;
        this.beanId = beanId;
    }

    /**
     * Returns the startposition of the scrolling
     * @return startpositio of the scrolling
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * Sets the startposition of the scrolling
     * @param startPos position of the scrolling
     */
    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    /**
     * Returns the size of the scrollwindow.
     * @return size of the scrollwindow
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the scrollwindow.
     * @param size size of the scrollwindow
     */
    public void setSize(int size) {
        this.size = size;
    }


    /**
     * Returns the header of the datagrid.
     * @return header of the datagrid
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the header of the datagrid.
     * @param header header of the datagrid
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Returns the list of object which will be ieterated over.
     * @return list of objects
     */
    public List getList() {
        return list;
    }

    /**
     * Sets the list of object which will be iterated over
     * @param list
     */
    public void setList(List list) {
        this.list = list;
    }

    /**
     * Returns the PortletURI for this ui component
     * @return portletURI for this component
     */
    public PortletURI getUri() {
        return uri;
    }


    /**
     * Sets the PortletURI for this component
     * @param uri
     */
    public void setUri(PortletURI uri) {
        this.uri = uri;
    }


    private String createLink(PortletURI uri, int pos, String desc, boolean renderlink, String prefix) {
        StringBuffer result = new StringBuffer();

        result.append("<span class=\"ui-datagrid-controls-element\">");
        if (renderlink) {
            uri.addParameter(prefix+beanId + "_pos", new Integer(pos).toString());
            result.append("<a href=\"" + uri.toString() + "\"> " + desc + "</a>");
        } else {
            result.append(desc);
        }
        result.append("</span>");
        return result.toString();
    }

    public String toStartString() {


        // header output
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"ui-datagrid\"><tr><td>");

        if (key!=null) {
            this.getLocalizedText(key, "DataGrid");
        }

        if (header != null) sb.append("<div class=\"ui-datagrid-title\">" + header + "</div></td></tr><tr><td>\n");


        String prefix = request.getParameter(SportletProperties.PREFIX);
        if (prefix==null) {
            prefix="";
        } else {
            prefix+="_";
        }
        String req_beanId = request.getParameter(prefix+"beanId");

        if (req_beanId != null) {
            if (req_beanId.equals(beanId)) {
                String req_startPos = request.getParameter(prefix+req_beanId + "_pos");
                startPos = new Integer(req_startPos).intValue();
                if (startPos > list.size()) startPos = list.size();
            }
        }

        boolean renderlink = true;

        uri.addParameter("beanId", beanId);

        sb.append("<div class=\"ui-datagrid-controls\">");

        // go to first linke
        uri.addParameter(beanId + "_pos", "0");
        if (startPos == 0) {
            renderlink = false;
        } else {
            renderlink = true;
        }
        sb.append(createLink(uri, 0, this.getLocalizedText("UI_DATAGRID_START"), renderlink, prefix));

        // go to prev link
        int newPrevPos = startPos - size;
        if (newPrevPos < 0) newPrevPos = 0;
        if (startPos == 0) {
            renderlink = false;
        } else {
            renderlink = true;
        }
        sb.append(createLink(uri, newPrevPos, this.getLocalizedText("UI_DATAGRID_PREV"), renderlink, prefix));

        // go to next link
        int newNextPos = startPos + size;
        if (newNextPos >= list.size()) {
            newNextPos = list.size();
            renderlink = false;
        } else {
            renderlink = true;
        }
        if (newNextPos < 0) newNextPos = 0;
        sb.append(createLink(uri, newNextPos, this.getLocalizedText("UI_DATAGRID_NEXT"), renderlink, prefix));

        // go to end link
        int newEndPos = list.size() - size;
        if (newEndPos < 0) newEndPos = 0;
        if (startPos <= list.size() && startPos >= (list.size() - size)) {
            renderlink = false;
        } else {
            renderlink = true;
        }
        sb.append(createLink(uri, newEndPos, this.getLocalizedText("UI_DATAGRID_END"), renderlink, prefix));

        int endPos = startPos + size;
        if (endPos > list.size()) {
            endPos = list.size();
        }
        if (list != null) sb.append(" " + startPos + "-" + endPos + "(" + list.size() + ")");

        sb.append("</div>");

        // new row for datagrid
        sb.append("</td></tr><tr><td>");

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
        sb.append("</td></tr></table>");
        return sb.toString();
    }
}
