package org.gridsphere.services.core.jcr.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.jcr.JCRService;

import java.util.TimerTask;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class BackupTask extends TimerTask {

    private String backupDir = "";
    private JCRService jcrService = null;
    private static Logger log = LogManager.getLogger(BackupTask.class);

    public BackupTask(String fullPathBackupDir) {
        this.backupDir = fullPathBackupDir;
        jcrService = (JCRService) PortletServiceFactory.createPortletService(JCRService.class, true);
    }

    public void run() {

        try {
            jcrService.backupContent(backupDir);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PortalContentBackup to " + backupDir + " failed.");
        }

    }
}
