/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public interface TAElement extends ReadOnly {

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
