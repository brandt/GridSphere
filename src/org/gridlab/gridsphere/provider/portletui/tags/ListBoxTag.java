package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ListBoxTag extends ContainerTag {
    int getSize();

    void setSize(int size);

    String getOnChange();

    void setOnChange(String onChange);

    void setMultipleSelection(boolean isMultiple);

    void setListBoxBean(ListBoxBean listbox);

    ListBoxBean getListBoxBean();

    boolean getMultipleSelection();
}
