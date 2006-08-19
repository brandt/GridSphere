/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: FileManagerPortlet.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlets.core.file;

import org.gridsphere.portlet.*;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.provider.event.FormEvent;
import org.gridsphere.provider.portlet.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.services.core.file.FileManagerService;

import javax.servlet.UnavailableException;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class FileManagerPortlet extends ActionPortlet {

    private FileManagerService userStorage = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            userStorage = (FileManagerService) config.getContext().getService(FileManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize FileManagerService", e);
        }
        DEFAULT_VIEW_PAGE = "doViewUserFiles";
        DEFAULT_HELP_PAGE = "filemanager/help.jsp";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doViewUserFiles(FormEvent event) throws PortletException {
        log.debug("in FileManagerPortlet: doViewUser");
        PortletRequest request = event.getPortletRequest();
        User user = request.getUser();

        ListBoxBean lb = event.getListBoxBean("filelist");
        lb.clear();
        String[] list = userStorage.getUserFileList(user);
        // set the list box size to number of files plus padding
        if (list == null) {
            ListBoxItemBean item = new ListBoxItemBean();
            item.setValue(this.getLocalizedText(request, "FILE_EMPTY_DIRECTORY"));
            item.setDisabled(true);
            lb.setSize(4);
            lb.addBean(item);
        } else {
            lb.setSize(list.length + 3);
            for (int i = 0; i < list.length; i++) {
                ListBoxItemBean item = new ListBoxItemBean();
                item.setValue(list[i]);
                lb.addBean(item);
            }
        }
        setNextState(request, "filemanager/view.jsp");
    }

    public void uploadFile(FormEvent event) throws PortletException {
        log.debug("in FileManagerPortlet: doUploadFile");
        try {
            FileInputBean fi = event.getFileInputBean("userfile");
            User user = event.getPortletRequest().getUser();
            String fileName = fi.getFileName();
            int size = fi.getSize();
            if (size > FileInputBean.MAX_UPLOAD_SIZE) {
                FrameBean errMsg = event.getFrameBean("errorFrame");
                errMsg.setValue(this.getLocalizedText(event.getPortletRequest(), "FILE_UPLOAD_TOOBIG"));
                errMsg.setStyle("error");
                return;
            }
            log.info("filename = " + fileName);
            if (fileName.equals("")) return;

            String userLoc = userStorage.getLocationPath(user, "");
            File f = new File(userLoc);
            if (!f.exists()) {
                if (!f.mkdirs()) throw new IOException("Unable to create dir: " + userLoc);
            }
            String path = userStorage.getLocationPath(user, fileName);

            log.info("storeFile: " + path);
            fi.saveFile(path);

            //userStorage.storeFile(user, fi, fileName);
            log.debug("fileinputbean value=" + fi.getValue());
        } catch (Exception e) {
            FrameBean errMsg = event.getFrameBean("errorFrame");
            errMsg.setValue(this.getLocalizedText(event.getPortletRequest(), "FILE_UPLOAD_FAIL"));
            errMsg.setStyle("error");
            log.error("Unable to store uploaded file ", e);
        }
        setNextState(event.getPortletRequest(), DEFAULT_VIEW_PAGE);
    }

    public void deleteFile(FormEvent event) throws PortletException {
        log.debug("in FileManagerPortlet: deleteFile");

        // Files can be deleted from edit or view pages
        // In view page the file is in a listbox
        ListBoxBean lb = event.getListBoxBean("filelist");
        List files = lb.getSelectedValues();
        User user = event.getPortletRequest().getUser();
        Iterator it = files.iterator();
        String fname = null;
        while (it.hasNext()) {
            fname = (String) it.next();
            userStorage.deleteFile(user, fname);
        }

        // In edit page the file is in a hidden field
        HiddenFieldBean hf = event.getHiddenFieldBean("fileName");
        fname = hf.getValue();
        if (fname != null) userStorage.deleteFile(user, fname);
    }

    public void cancelEdit(FormEvent event) throws PortletException {
        log.debug("in FileManagerPortlet: cancelEdit");
        setNextState(event.getPortletRequest(), DEFAULT_VIEW_PAGE);
    }

    public void saveFile(FormEvent event) throws PortletException {
        log.debug("in FileManagerPortlet: saveFile");

        User user = event.getPortletRequest().getUser();
        //PortletRequest req = event.getPortletRequest();
        //String fname = event.getPortletAction().getParameter("fileName");

        HiddenFieldBean hf = event.getHiddenFieldBean("fileName");
        String fileName = hf.getValue();

        TextAreaBean ta = event.getTextAreaBean("fileTextArea");
        String newText = ta.getValue();
        try {
            File tmpFile = userStorage.getFile(user, fileName);
            FileWriter f = new FileWriter(tmpFile);

            f.write(newText);
            f.close();
            //userStorage.storeFile(user, tmpFile, fname);
            //tmpFile.delete();
        } catch (IOException e) {
            log.error("Error saving file:" + fileName, e);
            FrameBean error = event.getFrameBean("editError");
            error.setValue(this.getLocalizedText(event.getPortletRequest(), "FILE_SAVE_FAIL") + " " + fileName);
        }
    }

    public void downloadFile(FormEvent event) throws PortletException {
        log.debug("in FileManagerPortlet: downloadFile");

        ListBoxBean lb = event.getListBoxBean("filelist");
        List files = lb.getSelectedValues();
        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();
        Iterator it = files.iterator();
        String fileName = null;
        while (it.hasNext()) {
            fileName = (String) it.next();
        }
        String path = userStorage.getLocationPath(user, fileName);
        setFileDownloadEvent(req, fileName, path);
    }

    public void editFile(FormEvent event) throws PortletException {
        log.debug("in FileManagerPortlet: viewFile");
        ListBoxBean lb = event.getListBoxBean("filelist");
        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();

        if (lb.hasSelectedValue()) {
            String file = lb.getSelectedValue();
            try {
                req.setAttribute("fileName", file);
                TextBean textBean = event.getTextBean("msg");
                textBean.setValue(this.getLocalizedText(req, "FILE_DISPLAY") + " " + file);
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
                reader.close();
                fileTextArea.setValue(sb.toString());
                setNextState(req, "filemanager/edit.jsp");
            } catch (FileNotFoundException e) {
                log.error("Error opening file:" + file, e);
                FrameBean error = event.getFrameBean("errorFrame");
                error.setValue(this.getLocalizedText(req, "FILE_OPEN_FAIL") + " " + file);
                error.setStyle(FrameBean.ERROR_TYPE);
                setNextState(req, DEFAULT_VIEW_PAGE);
            } catch (IOException e) {
                log.error("Error opening file:" + file, e);
                FrameBean error = event.getFrameBean("errorFrame");
                error.setStyle(FrameBean.ERROR_TYPE);
                error.setValue(this.getLocalizedText(req, "FILE_OPEN_FAIL") + " " + file);
                setNextState(req, DEFAULT_VIEW_PAGE);
            }
        }
    }


}
