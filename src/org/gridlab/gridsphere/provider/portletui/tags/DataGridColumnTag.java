package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.DataGridAttributes;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface DataGridColumnTag extends ContainerTag {
    DataGridAttributes getVarAttributes();

    void setVarAttributes(DataGridAttributes varattributes);

    String getKey();

    void setKey(String key);

    String getParamValue();

    void setParamValue(String paramValue);

    String getParamName();

    void setParamName(String paramName);

    String getHeader();

    void setHeader(String header);

    String getVar();

    void setVar(String var);
}
