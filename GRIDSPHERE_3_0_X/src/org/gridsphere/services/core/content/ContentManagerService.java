package org.gridsphere.services.core.content;

import org.gridsphere.portlet.service.PortletService;

import java.util.List;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface ContentManagerService  extends PortletService {

    public List<ContentFile> getAllContent();

    public void addContent(ContentFile content);

    public void removeContent(ContentFile content);

}
