package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.PortletURI;
import org.gridsphere.provider.portletui.beans.BaseComponentBean;
import org.gridsphere.provider.portletui.beans.DataGridBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: DataGridTag.java 4666 2006-03-27 17:47:56Z novotny $
 */

public class DataGridTag extends ContainerTag {

    private int size = 10;
    private String header = null;
    private List objectlist = null;
    private int startPos = 0;
    private DataGridBean dataGridBean = null;
    private String key = null;
    private String width = null;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List getDataList() {
        return objectlist;
    }

    public void setDataList(List list) {
        this.objectlist = list;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int doStartTag() throws JspException {

        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
        PortletURI uri = res.createURI();
        PortletRequest request = (PortletRequest) pageContext.getAttribute("portletRequest");

        list = new Vector();

        if (!beanId.equals("")) {
            dataGridBean = (DataGridBean) getTagBean();
            if (dataGridBean == null) {
                dataGridBean = new DataGridBean(beanId);
                if (header != null) dataGridBean.setHeader(header);
                dataGridBean.setSize(size);
                this.setBaseComponentBean(dataGridBean);
                dataGridBean.setList(objectlist);
                dataGridBean.setWidth(width);
            } else {
                this.updateBaseComponentBean(dataGridBean);
            }

        } else {
            dataGridBean = new DataGridBean();
            dataGridBean.setSize(size);
            if (header != null) dataGridBean.setHeader(header);
            dataGridBean.setList(objectlist);
            this.setBaseComponentBean(dataGridBean);
        }

        dataGridBean.setUri(uri);

        if (key != null) {
            dataGridBean.setHeader(getLocalizedText(key, "DataGrid"));
        }

        dataGridBean.setHttpServletRequest(request);

        try {
            JspWriter out = pageContext.getOut();
            out.print(dataGridBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        try {

            List beans = getTagBeans();
            Iterator it = beans.iterator();
            while (it.hasNext()) {
                dataGridBean.addBean((BaseComponentBean) it.next());
            }

            JspWriter out = pageContext.getOut();
            out.print(dataGridBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
