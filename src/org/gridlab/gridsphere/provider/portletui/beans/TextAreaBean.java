/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * The <code>TextAreaBean</code> represents a text area element
 */
public class TextAreaBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "ta";

    private int cols = 0;
    private int rows = 0;
    protected String onFocus = null;

    /**
     * Constructs a default text area bean
     */
    public TextAreaBean() {
        super(NAME);
        this.cssClass = MessageStyle.MSG_INFO;
    }

    /**
     * Constructs a text area bean from a supplied portlet request and bean identifier
     *
     * @param beanId the bean identifier
     */
    public TextAreaBean(String beanId) {
        super(NAME);
        this.cssClass = MessageStyle.MSG_INFO;
        this.beanId = beanId;
    }

    /**
     * Gets the number of columns of the TextArea.
     *
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns of the TextArea.
     *
     * @param cols number of cols
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Return the number of rows of the textarea.
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows of the textarea.
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

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<textarea ");
        sb.append(getFormattedCss());

        String sname = createTagName(name);

        sb.append("name=\"" + sname + "\" ");
        if (cols != 0) sb.append(" cols=\"" + cols + "\" ");
        if (rows != 0) sb.append(" rows=\"" + rows + "\" ");
        sb.append(" " + checkDisabled());
        sb.append(" " + checkReadOnly());
        sb.append(">");
        return sb.toString();
    }

    public String toEndString() {
        String result = (value != null) ? value : "";
        return result + "</textarea>";
    }

}
