package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.BaseComponentBean;
import org.gridlab.gridsphere.provider.portletui.beans.DataGridColumnBean;

import javax.servlet.jsp.JspException;
import java.util.Iterator;
import java.util.Vector;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class DataGridColumnTag extends ContainerTag {

    private String header = null;
    private String source = null;
    private String paramName = null;
    private String paramValue = null;
    private String key = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParamvalue() {
        return paramValue;
    }

    public void setParamvalue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamname() {
        return paramName;
    }

    public void setParamname(String paramName) {
        this.paramName = paramName;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public int doStartTag() throws JspException {
        list = new Vector();

        return EVAL_BODY_INCLUDE;
    }


    public int doEndTag() throws JspException {

        DataGridTag dataGridTag = (DataGridTag) getParent();
        if (dataGridTag != null) {
            DataGridColumnBean dataGridColumnBean = new DataGridColumnBean();
            dataGridColumnBean.setHeader(this.header);
            if (key != null) {
                dataGridColumnBean.setHeader(this.getLocalizedText(key));
            }
            dataGridColumnBean.setSource(this.source);
            dataGridColumnBean.setParamName(this.paramName);
            dataGridColumnBean.setParamValue(this.paramValue);

            this.setBaseComponentBean(dataGridColumnBean);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                dataGridColumnBean.addBean((BaseComponentBean) it.next());
            }
            dataGridTag.addTagBean(dataGridColumnBean);
        }
        return EVAL_PAGE;
    }
}
