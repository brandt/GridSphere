package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.TextEditorBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * The <code>TextEditorTag</code> uses the SimpleTextEditor javscript provided
 * by Cezary Tomczak at http://gosu.pl/dhtml/SimpleTextEditor.html
 */
public class TextEditorTag extends ActionTag {

    protected TextEditorBean textEditorBean = null;
    private int cols = 0;
    private int rows = 0;
    private String value = null;
    private boolean viewsource = true;

    /**
     * Returns true if text editor should allow users to edit/view HTML source
     *
     * @return true if text editor should allow users to edit/view HTML source
     */
    public boolean getViewsource() {
        return viewsource;
    }

    /**
     * Set to true if text editor should allow users to edit/view HTML source
     *
     * @return true if text editor should allow users to edit/view HTML source
     */
    public void setViewsource(boolean viewsource) {
        this.viewsource = viewsource;
    }

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
            textEditorBean = (TextEditorBean) getTagBean();
            if (textEditorBean == null) {
                textEditorBean = new TextEditorBean();
                textEditorBean.setRows(rows);
                textEditorBean.setCols(cols);
                textEditorBean.setViewsource(viewsource);
                this.setBaseComponentBean(textEditorBean);
            } else {
                if ((textEditorBean.getCols() == 0) && (cols != 0)) {
                    textEditorBean.setCols(cols);
                }
                if ((textEditorBean.getRows() == 0) && (rows != 0)) {
                    textEditorBean.setRows(rows);
                }
                this.updateBaseComponentBean(textEditorBean);
                this.viewsource = textEditorBean.getViewsource();
            }
        } else {
            textEditorBean = new TextEditorBean();
            textEditorBean.setRows(rows);
            textEditorBean.setCols(cols);
            textEditorBean.setViewsource(viewsource);
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
