package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface PanelTag extends ComponentTag {
    void setCellpadding(String cellPadding);

    String getCellpadding();

    void setAlign(String align);

    String getAlign();

    void setBorder(String border);

    String getBorder();

    void setWidth(String width);

    String getWidth();

    void setCols(String cols);

    String getCols();

    int getNumCols();

    String[] getColArray();

    void setColumnCounter(int counter);

    int getColumnCounter();

    void setCellSpacing(String cellSpacing);

    String getCellSpacing();
}
