package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TableCellBean;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface TableCellTag extends ComponentTag {

    void setAlign(String align);

    String getAlign();

    void setValign(String valign);

    String getValign();

    void setWidth(String width);

    String getWidth();

    void setHeight(String height);

    String getHeight();

    void setCellBean(TableCellBean cellBean);

    TableCellBean getCellBean();
}
