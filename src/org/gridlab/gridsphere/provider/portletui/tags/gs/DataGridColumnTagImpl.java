package org.gridlab.gridsphere.provider.portletui.tags.gs;

import org.gridlab.gridsphere.provider.portletui.beans.BaseComponentBean;
import org.gridlab.gridsphere.provider.portletui.beans.DataGridColumnBean;
import org.gridlab.gridsphere.provider.portletui.beans.DataGridAttributes;

import javax.servlet.jsp.JspException;
import java.util.Iterator;
import java.util.Vector;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class DataGridColumnTagImpl extends ContainerTagImpl {

    private String header = null;
    private String var = null;
    private String paramName = null;
    private String paramValue = null;
    private String key = null;
    private DataGridAttributes varattributes = null;

    public DataGridAttributes getVarAttributes() {
        return varattributes;
    }

    public void setVarAttributes(DataGridAttributes varattributes) {
        this.varattributes = varattributes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }


    public int doStartTag() throws JspException {
        list = new Vector();

        return EVAL_BODY_INCLUDE;
    }


    public int doEndTag() throws JspException {

        DataGridTagImpl dataGridTag = (DataGridTagImpl) getParent();
        if (dataGridTag != null) {
            DataGridColumnBean dataGridColumnBean = new DataGridColumnBean();
            dataGridColumnBean.setHeader(this.header);
            if (key != null) {
                dataGridColumnBean.setHeader(this.getLocalizedText(key));
            }
            dataGridColumnBean.setVar(this.var);
            dataGridColumnBean.setParamName(this.paramName);
            dataGridColumnBean.setParamValue(this.paramValue);
            dataGridColumnBean.setAttributes(this.varattributes);

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
