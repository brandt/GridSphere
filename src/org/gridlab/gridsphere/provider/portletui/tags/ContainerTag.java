package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TagBean;

import java.util.List;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ContainerTag extends ComponentTag {

    void addTagBean(TagBean tagBean);

    void removeTagBean(TagBean tagBean);

    List getTagBeans();
}
