package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ActionFormTag extends ActionTag {
    void setMethod(String method);

    String getMethod();

    void setMultiPartFormData(boolean isMultipart);

    boolean getMultiPartFormData();
}
