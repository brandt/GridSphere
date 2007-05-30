package org.gridsphere.services.core.content.impl;

import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.content.ContentFile;
import org.gridsphere.services.core.content.ContentManagerService;
import org.gridsphere.services.core.customization.SettingsService;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class ContentManagerServiceImpl implements ContentManagerService, PortletServiceProvider {

    private List<ContentFile> contents = new ArrayList();

    public void init(PortletServiceConfig config) {
        ServletContext ctx = config.getServletContext();
        SettingsService settingsService = (SettingsService) PortletServiceFactory.createPortletService(SettingsService.class, true);
        String contentDirPath = settingsService.getRealSettingsPath("content");
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
