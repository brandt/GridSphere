package org.gridlab.gridsphere.provider.portletui.tags;

import java.util.List;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface DataGridTag extends ContainerTag {
    String getWidth();

    void setWidth(String width);

    String getKey();

    void setKey(String key);

    int getSize();

    void setSize(int size);

    String getHeader();

    void setHeader(String header);

    List getDataList();

    void setDataList(List list);

    int getStartPos();

    void setStartPos(int startPos);
}
