/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public interface TAElement extends ReadOnly {

    public void setCols(int cols);

    public int getCols();

    public void setRows(int rows);

    public int getRows();

    public void setText(String text);

    public String getText();

}
