/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.file;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.provider.ActionPortlet;
import org.gridlab.gridsphere.provider.event.FormEvent;

import org.gridlab.gridsphere.services.core.file.FileManagerService;

import javax.servlet.UnavailableException;
import java.io.*;
import java.util.List;
import java.util.Iterator;

public class FileManagerPortlet extends ActionPortlet {

    private FileManagerService userStorage = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            userStorage = (FileManagerService)config.getContext().getService(FileManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize FileManagerService", e);
        }
        DEFAULT_VIEW_PAGE = "doViewUserFiles";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doViewUserFiles(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: doViewUser");
        PortletRequest request = event.getPortletRequest();
        User user = request.getUser();

        ListBoxBean lb = event.getListBoxBean("filelist");
        lb.clear();
        String[] list = userStorage.getUserFileList(user);
        // set the list box size to number of files plus padding
        lb.setSize(list.length + 3);
        for (int i = 0; i < list.length; i++) {
            ListBoxItemBean item = new ListBoxItemBean();
            item.setValue(list[i]);
            lb.addBean(item);
        }
        setNextPresentation(request, "filemanager/view.jsp");
    }

    public void uploadFile(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: doUploadFile");
        try {
            FileInputBean fi = event.getFileInputBean("userfile");
            User user = event.getPortletRequest().getUser();
            File f = fi.getFile();
            String fileName = fi.getFileName();
            userStorage.storeFile(user, f, fileName);
            //String location = userStorage.getLocationPath(user, "myfile");
            //log.debug("fileinputbean value=" + fi.getValue() + " location to store=" + location);
            //fi.saveFile(location);
        } catch (IOException e) {
            setNextError(event.getPortletRequest(), "Unable to store uploaded file " + e.getMessage());
        }
        setNextPresentation(event.getPortletRequest(), DEFAULT_VIEW_PAGE);
    }

    public void deleteFile(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: deleteFile");
        ListBoxBean lb = event.getListBoxBean("filelist");
        List files = lb.getSelectedValues();
        User user = event.getPortletRequest().getUser();
        Iterator it = files.iterator();

        while (it.hasNext()) {
            String fname = (String)it.next();
            userStorage.deleteFile(user, fname);
        }
    }

    public void cancelEdit(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: cancelEdit");
        setNextPresentation(event.getPortletRequest(), DEFAULT_VIEW_PAGE);
    }

    public void saveFile(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: saveFile");
        User user = event.getPortletRequest().getUser();
        PortletRequest req = event.getPortletRequest();
        //String fname = event.getAction().getParameter("fileName");

        HiddenFieldBean hf = event.getHiddenFieldBean("fileName");
        String fileName = hf.getValue();

        TextAreaBean ta = event.getTextAreaBean("fileTextArea");
        String newText = ta.getValue();
        try {
            System.err.println(newText);
            File tmpFile = userStorage.getFile(user, fileName);
            FileWriter f = new FileWriter(tmpFile);

            f.write(newText);
            f.close();
            //userStorage.storeFile(user, tmpFile, fname);
            //tmpFile.delete();
        } catch (IOException e) {
            log.error("Error saving file:" + fileName, e);
            ErrorFrameBean error = event.getErrorFrameBean("editError");
            error.setValue("Unable to save file: " + fileName);
        }
    }

    public void downloadFile(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: downloadFile");
    }

    public void editFile(FormEvent event) throws PortletException {

        log.debug("in LoginPortlet: viewFile");
        ListBoxBean lb = event.getListBoxBean("filelist");
        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();

        if (lb.hasSelectedValue()) {
            String file = lb.getSelectedValue();
            try {
                req.setAttribute("fileName", file);
                TextBean textBean = event.getTextBean("msg");
                textBean.setValue("Displaying file: " + file);
                TextAreaBean fileTextArea = event.getTextAreaBean("fileTextArea");
                HiddenFieldBean hidden = event.getHiddenFieldBean("fileName");
                hidden.setValue(file);
                File editFile = userStorage.getFile(user, file);
                BufferedReader reader = new BufferedReader(new FileReader(editFile));
                String line = null;
                StringBuffer sb = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                fileTextArea.setValue(sb.toString());
                setNextPresentation(req, "filemanager/edit.jsp");
            } catch (FileNotFoundException e) {
                log.error("Error opening file:" + file, e);
                ErrorFrameBean error = event.getErrorFrameBean("errorFrame");
                error.setValue("Unable to open file: " + file);
                setNextPresentation(req, DEFAULT_VIEW_PAGE);
            } catch (IOException e) {
                log.error("Error opening file:" + file, e);
                ErrorFrameBean error = event.getErrorFrameBean("errorFrame");
                error.setValue("Unable to open file: " + file);
                setNextPresentation(req, DEFAULT_VIEW_PAGE);
            }
        }
    }


}
