package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.provider.portletui.beans.TableBean;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface TableTag extends ComponentTag {
    void setTableModel(DefaultTableModel tableModel);

    DefaultTableModel getTableModel();

    void setTableBean(TableBean tableBean);

    TableBean getTableBean();

    void setAlign(String align);

    String getAlign();

    void setCellspacing(String cellSpacing);

    String getCellspacing();

    void setCellpadding(String cellPadding);

    String getCellpadding();

    void setBorder(String border);

    String getBorder();

    void setWidth(String width);

    String getWidth();
}
