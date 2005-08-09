/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.tracker;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.services.core.tracker.TrackerService;
import org.gridlab.gridsphere.services.core.tracker.impl.TrackerInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import javax.servlet.UnavailableException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.FileOutputStream;

public class TrackerStatisticsPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String DO_VIEW_LABELS = "admin/tracker/doViewLabels.jsp";
    public static final String DO_DISPLAY_LABEL = "admin/tracker/doShowLabelInfo.jsp";

    // Portlet services
    private TrackerService trackerService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            this.trackerService = (TrackerService) config.getContext().getService(TrackerService.class);
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
        setNextState(req, DO_VIEW_LABELS);
    }

    public void showLabel(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        String label = evt.getAction().getParameter("label");
        List trackInfo = trackerService.getTrackingInfoByLabel(label);
        req.setAttribute("trackInfoList", trackInfo);
        req.setAttribute("label", label);
        setNextState(req, DO_DISPLAY_LABEL);
    }


    public void createSpreadsheet() {

        String label = "";
        List trackInfoList = trackerService.getTrackingInfoByLabel(label);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short)0);
        row.createCell((short)0).setCellValue("Label");
        row.createCell((short)1).setCellValue("User-Agent");
        row.createCell((short)2).setCellValue("Date");
        TrackerInfo info;

        for (int i = 0; i < trackInfoList.size(); i++) {
            info = (TrackerInfo)trackInfoList.get(i);
            row = sheet.createRow((short)i+1);
            row.createCell((short)0).setCellValue(info.getLabel());
            row.createCell((short)1).setCellValue(info.getUserAgent());
            row.createCell((short)2).setCellValue(info.getDate());
        }

        try {
            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream("workbook.xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}





