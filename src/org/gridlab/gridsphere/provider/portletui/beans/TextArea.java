/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public interface TextArea extends ReadOnly {

    /**
     * Sets the number of columns of the TextArea.
     * @param cols number of cols
     */
    public void setCols(int cols);

    /**
     *  Gets the number of columns of the TextArea.
     * @return number of columns
     */
    public int getCols();

    /**
     * Sets the number of rows of the textarea.
     * @param rows number of rows
     */
    public void setRows(int rows);

    /**
     * Return the number of rows of the textarea.
     * @return number of rows
     */
    public int getRows();

}
