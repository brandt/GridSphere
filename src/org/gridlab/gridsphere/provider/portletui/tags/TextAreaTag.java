package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface TextAreaTag extends ComponentTag {
    int getCols();

    void setCols(int cols);

    int getRows();

    void setRows(int rows);
}
