package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TableRowBean;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface TableRowTag extends ComponentTag {
    void setTableRowBean(TableRowBean tableRowBean);

    TableRowBean getTableRowBean();

    void setHeader(boolean isHeader);

    boolean getHeader();

    void setAlign(String align);

    String getAlign();

    void setValign(String valign);

    String getValign();
}
