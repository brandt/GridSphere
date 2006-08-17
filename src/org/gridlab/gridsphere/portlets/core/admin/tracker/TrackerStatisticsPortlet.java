/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.tracker;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.tracker.TrackerService;
import org.gridlab.gridsphere.services.core.tracker.impl.TrackerAction;
import org.gridlab.gridsphere.services.core.tracker.impl.TrackerInfo;

import javax.servlet.UnavailableException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

public class TrackerStatisticsPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String DO_VIEW_LABELS = "admin/tracker/doViewLabels.jsp";
    public static final String DO_DISPLAY_LABEL = "admin/tracker/doShowLabelInfo.jsp";
    public static final String DO_DISPLAY_ACTIONS = "admin/tracker/doShowActions.jsp";

    // Portlet services
    private TrackerService trackerService = null;
    private PortalConfigService portalConfigService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            this.trackerService = (TrackerService) config.getContext().getService(TrackerService.class);
            this.portalConfigService = (PortalConfigService) config.getContext().getService(PortalConfigService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        log.debug("Exiting initServices()");
        DEFAULT_VIEW_PAGE = "doViewLabels";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doViewLabels(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        List labels = trackerService.getTrackingLabels();
        Set labelSet = new HashSet(labels);
        req.setAttribute("labelSet", labelSet);
        String isCounterEnabled = portalConfigService.getProperty("ENABLE_PORTAL_COUNTER");
        CheckBoxBean trackCB = evt.getCheckBoxBean("trackPortletsCB");
        if (isCounterEnabled != null) {
            trackCB.setSelected(Boolean.valueOf(isCounterEnabled).booleanValue());
        }
        setNextState(req, DO_VIEW_LABELS);
    }

    public void savePortletCounter(FormEvent evt) {

        CheckBoxBean trackCB = evt.getCheckBoxBean("trackPortletsCB");

        boolean isSelected = trackCB.isSelected();

        portalConfigService.setProperty("ENABLE_PORTAL_COUNTER", Boolean.valueOf(isSelected).toString());
        try {
            portalConfigService.storeProperties();
        } catch (IOException e) {
            log.error("Unable to set ENABLE_PORTAL_COUNTER in properties file");
        }
    }

    public void showLabel(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        String label = evt.getAction().getParameter("label");
        List trackInfo = trackerService.getTrackingInfoByLabel(label);
        req.setAttribute("trackInfoList", trackInfo);
        req.setAttribute("label", label);
        setNextState(req, DO_DISPLAY_LABEL);
    }

    public void doEditActions(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        List trackerActionList = trackerService.getTrackingActions();
        req.setAttribute("trackerActionList", trackerActionList);
        setNextState(req, DO_DISPLAY_ACTIONS);
    }

    public void doSaveAction(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        TextFieldBean tf = evt.getTextFieldBean("createActionTF");
        String action = tf.getValue();
        if (!action.equals("")) {
            TrackerAction ta = trackerService.getTrackingAction(action);
            if (ta == null) {
                TrackerAction newaction = new TrackerAction();
                newaction.setAction(action);
                newaction.setEnabled(true);
                trackerService.addTrackingAction(newaction);
            }
        }
        setNextState(req, "doEditActions");
    }

    public void doModifyAction(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        CheckBoxBean cb = evt.getCheckBoxBean("enabledCB");
        List vals = cb.getSelectedValues();
        List trackerActionList = trackerService.getTrackingActions();
        Iterator it = trackerActionList.iterator();
        while (it.hasNext()) {
            TrackerAction ta = (TrackerAction) it.next();
            if (vals.contains(ta.getAction())) {
                ta.setEnabled(true);
            } else {
                ta.setEnabled(false);
            }
            trackerService.addTrackingAction(ta);
        }
        setNextState(req, "doEditActions");
    }

    public void doDeleteAction(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        String action = evt.getAction().getParameter("actionName");
        trackerService.removeTrackingAction(action);
        setNextState(req, "doEditActions");
    }

    public void doReturn(FormEvent evt) {
        setNextState(evt.getPortletRequest(), DEFAULT_VIEW_PAGE);
    }

    public void doDownload(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        String label = evt.getAction().getParameter("label");
        List trackInfoList = trackerService.getTrackingInfoByLabel(label);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        row.createCell((short) 0).setCellValue("Date");
        row.createCell((short) 1).setCellValue("User-Agent");
        row.createCell((short) 2).setCellValue("User Name");
        TrackerInfo info;

        for (int i = 0; i < trackInfoList.size(); i++) {
            info = (TrackerInfo) trackInfoList.get(i);
            row = sheet.createRow((short) i + 1);
            row.createCell((short) 0).setCellValue(DateFormat.getDateTimeInstance().format(new Date(info.getDate())));
            row.createCell((short) 1).setCellValue(info.getUserAgent());
            row.createCell((short) 2).setCellValue(info.getUserName());
        }

        try {
            // Write the output to a file
            File f = new File(label + "Statistics.xls");
            FileOutputStream fileOut = new FileOutputStream(f);
            wb.write(fileOut);
            fileOut.close();
            this.setFileDownloadEvent(req, label + "Statistics.xls", f.getAbsolutePath(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}





