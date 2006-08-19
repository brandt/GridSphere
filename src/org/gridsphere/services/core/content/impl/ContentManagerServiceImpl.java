package org.gridsphere.services.core.content.impl;

import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.services.core.content.ContentFile;
import org.gridsphere.services.core.content.ContentManagerService;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * @author <a href="mailto:jnovotny@ucsd.edu">Jason Novotny</a>
 * @version $Id$
 */
public class ContentManagerServiceImpl implements ContentManagerService, PortletServiceProvider {

    private List<ContentFile> contents = new ArrayList();

    public void init(PortletServiceConfig config) {
        ServletContext ctx = config.getServletContext();
        String contentDirPath = ctx.getRealPath("/WEB-INF/CustomPortal/content");
        File contentDir = new File(contentDirPath);
        File[] contentFiles = contentDir.listFiles();
        ContentFile content = null;
        for (int i = 0; i < contentFiles.length; i++) {
            File contentFile = contentFiles[i];
            content = new ContentFileImpl(contentFile);
            contents.add(content);
        }
    }

    public void destroy() {

    }

    public List<ContentFile> getAllContent() {
        return contents;
    }

    public void addContent(ContentFile content) {
        contents.add(content);
    }

    public void removeContent(ContentFile content) {
        contents.remove(content);
    }

}
