/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class TextAreaBean extends BaseComponentBean implements TagBean {

    private int cols = 0;
    private int rows = 0;;

    public TextAreaBean() {
    }

    /**
     *  Gets the number of columns of the TextArea.
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns of the TextArea.
     * @param cols number of cols
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Return the number of rows of the textarea.
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows of the textarea.
     * @param rows number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<textarea ");
        sb.append("name=\"" + name + "\" ");
        if (cols != 0) sb.append(" cols=\"" + cols + "\" ");
        if (rows != 0) sb.append(" rows=\"" + rows + "\" ");
        sb.append(" " + checkDisabled());
        sb.append(" " + checkReadonly());
        sb.append(">");
        if (value != null) sb.append(value);
        sb.append("</textarea>");
        return sb.toString();
    }

}
