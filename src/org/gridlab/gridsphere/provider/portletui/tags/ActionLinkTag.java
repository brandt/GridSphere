package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ActionLinkTag extends ActionTag {
    void setStyle(String style);

    String getStyle();

    void setKey(String key);

    String getKey();

    void setImageBean(ImageBean imageBean);

    ImageBean getImageBean();
}
