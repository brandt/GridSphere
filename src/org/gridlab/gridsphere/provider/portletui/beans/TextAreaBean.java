/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class TextAreaBean extends ReadOnlyBaseBean implements TextArea {

    private int cols;
    private int rows;

    public TextAreaBean(String name, String value, boolean disabled, boolean readonly, int rows, int cols) {
        super(name, value, disabled, readonly);
        this.cols = cols;
        this.rows = rows;
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
        return "<textarea name='" + name + "' cols='" + cols + "' rows='" + rows + "' " + checkDisabled() + " " + checkReadonly() + ">" +
                value + "</textarea>";

    }

}
