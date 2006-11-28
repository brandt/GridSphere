package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.RichTextEditorBean;
import org.gridsphere.provider.portletui.beans.TextEditorBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class RichTextEditorTag extends ActionTag {


    protected RichTextEditorBean textEditorBean = null;
    private int cols = 0;
    private int rows = 0;
    private String value = null;


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

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            textEditorBean = (RichTextEditorBean) getTagBean();
            if (textEditorBean == null) {
                textEditorBean = new RichTextEditorBean();
                textEditorBean.setRows(rows);
                textEditorBean.setCols(cols);
                this.setBaseComponentBean(textEditorBean);
            } else {
                if ((textEditorBean.getCols() == 0) && (cols != 0)) {
                    textEditorBean.setCols(cols);
                }
                if ((textEditorBean.getRows() == 0) && (rows != 0)) {
                    textEditorBean.setRows(rows);
                }
                this.updateBaseComponentBean(textEditorBean);

            }
        } else {
            textEditorBean = new RichTextEditorBean();
            textEditorBean.setRows(rows);
            textEditorBean.setCols(cols);
            this.setBaseComponentBean(textEditorBean);
        }

        textEditorBean.setAction(createActionURI());

        //debug();

        try {
            JspWriter out = pageContext.getOut();
            out.print(textEditorBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        if ((bodyContent != null) && (value == null)) {
            textEditorBean.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(textEditorBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
