/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>TextAreaTag</code> represents a text area element
 */
public class TextAreaTag extends BaseComponentTag {

    protected TextAreaBean textAreaBean = null;
    protected int cols = 0;
    protected int rows = 0;
    protected String onFocus = null;

    /**
     * Returns the number of columns of the text area
     *
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns of the text area
     *
     * @param cols number of cols
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Return the number of rows of the text area
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows of the text area
     *
     * @param rows number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setOnfocus(String onFocus) {
        this.onFocus = onFocus;
    }

    public String getOnfocus() {
        return onFocus;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            textAreaBean = (TextAreaBean) getTagBean();
            if (textAreaBean == null) {
                textAreaBean = new TextAreaBean();
                textAreaBean.setRows(rows);
                textAreaBean.setCols(cols);
                this.setBaseComponentBean(textAreaBean);
            } else {
                if ((textAreaBean.getCols() == 0) && (cols != 0)) {
                    textAreaBean.setCols(cols);
                }
                if ((textAreaBean.getRows() == 0) && (rows != 0)) {
                    textAreaBean.setRows(rows);
                }
                this.updateBaseComponentBean(textAreaBean);
            }
        } else {
            textAreaBean = new TextAreaBean();
            textAreaBean.setRows(rows);
            textAreaBean.setCols(cols);
            this.setBaseComponentBean(textAreaBean);
        }

        //debug();

        try {
            JspWriter out = pageContext.getOut();
            out.print(textAreaBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        if ((bodyContent != null) && (value == null)) {
            textAreaBean.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(textAreaBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }


        return EVAL_PAGE;
    }

}
