package org.gridsphere.portlets.core.admin.content;

import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.FormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.services.core.jcr.ContentDocument;
import org.gridsphere.services.core.jcr.ContentException;
import org.gridsphere.services.core.jcr.JCRNode;
import org.gridsphere.services.core.jcr.JCRService;
import org.gridsphere.services.core.jcr.impl.BackupTask;
import org.gridsphere.services.core.timer.TimerService;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
 * @version $Id$
 */

public class ContentManagementPortlet extends ActionPortlet {

    private JCRService jcrService = null;
    private final static String defaultViewJSP = "content/view.jsp";
    private final static String defaultConfigJSP = "content/config.jsp";
    private TimerService timerService = null;
    private List renderKits = new ArrayList();
    private String backupDir = "";


    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        jcrService = (JCRService) createPortletService(JCRService.class);
        timerService = (TimerService) createPortletService(TimerService.class);
        backupDir = config.getPortletContext().getRealPath("WEB-INF/CustomPortal/content/backupContent");
        BackupTask backup = new BackupTask(backupDir);
        timerService.schedule("contentbackup", backup, 1000 * 60, 1000 * 60 * 60 * 24); // 1000*60*60*24 one day
        DEFAULT_VIEW_PAGE = "doView";
        DEFAULT_HELP_PAGE = "content/help.jsp";
        DEFAULT_CONFIGURE_PAGE = "doConfigure";
        DEFAULT_EDIT_PAGE = "doEdit";
        renderKits.add(JCRNode.RENDERKIT_HTML);
        renderKits.add(JCRNode.RENDERKIT_RADEOX);
        renderKits.add(JCRNode.RENDERKIT_TEXT);
    }

    protected String getUsername(PortletRequest request) {
        Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
        return (String) userInfo.get("user.name");
    }

    public void listNodes(RenderFormEvent event) {
        try {
            List<ContentDocument> list = jcrService.listChildContentDocuments("/");
            event.getRenderRequest().setAttribute("contentDocs", list);
        } catch (ContentException e) {
            log.error("Could not retrieve list of content documents.", e);
        }
    }


    protected void clearInputs(FormEvent event) {
        TextFieldBean nodeid = event.getTextFieldBean("title");
        RichTextEditorBean nodecontent = event.getRichTextEditorBean("content");
        nodeid.setValue("");
        nodecontent.setValue("");
        HiddenFieldBean uuid = event.getHiddenFieldBean("uuid");
        uuid.setValue("");
        //setRenderKitValue(event, JCRNode.RENDERKIT_DEFAULT);
    }

    public void clearEditor(ActionFormEvent event) {
        clearInputs(event);
        //listNodes(event);
        setNextState(event.getActionRequest(), defaultViewJSP);
    }

    protected void setRenderKitValue(FormEvent event, String kit) {
        ListBoxBean renderkit = event.getListBoxBean("renderkit");
        renderkit.clear();
        for (int i = 0; i < renderKits.size(); i++) {
            ListBoxItemBean item = new ListBoxItemBean();
            String akit = (String) renderKits.get(i);
            String aname[] = akit.split("/");
            item.setName(akit);
            item.setValue(aname[1]);
            if (kit.equals(akit)) {
                item.setSelected(true);
            }
            renderkit.addBean(item);
        }
    }

    public void saveDocument(ActionFormEvent event) {
        PortletRequest request = event.getActionRequest();
        TextFieldBean nodeBean = event.getTextFieldBean("title");
        String title = nodeBean.getValue();
        RichTextEditorBean rteditor = event.getRichTextEditorBean("content");
        String nodecontent = rteditor.getValue();
        String uuid = event.getHiddenFieldBean("uuid").getValue();
        //String renderkit = event.getListBoxBean("renderkit").getSelectedName();
        String renderkit = "text/html";
        ContentDocument doc = null;
        String action = "";
        try {
            if (uuid.equals("")) {
                // new node
                doc = new ContentDocument();
                action = "NEW";
            } else {
                // edit node
                doc = jcrService.getDocumentByUUID(uuid);
                action = "EDIT";
            }

            doc.setContent(nodecontent);
            doc.setTitle(title);

            jcrService.saveDocument(doc);
            createSuccessMessage(event, getLocalizedText(request, "CM_SUCCESS_" + action + "DOCUMENT") + ": " + title + ".");

        } catch (ContentException e) {
            log.error("Err.", e);
            createErrorMessage(event, getLocalizedText(request, "CM_ERR_COULDNOTSAVEDOCUMENT") + ": " + title + ".");
        }
        clearInputs(event);
        //listNodes(event);
        //nodeBean.setReadOnly(false);
        setNextState(request, DEFAULT_VIEW_PAGE);
    }

    public void doView(RenderFormEvent event) throws PortletException {
        PortletRequest request = event.getRenderRequest();
        listNodes(event);
        clearInputs(event);
        //event.getTextFieldBean("title").setReadOnly(true);
        setNextState(request, defaultViewJSP);
    }


    public void showNode(RenderFormEvent event) throws PortletException {
        PortletRequest request = event.getRenderRequest();
        //ListBoxBean nodelist = event.getListBoxBean("nodelist");
        RichTextEditorBean content = event.getRichTextEditorBean("content");
        TextFieldBean title = event.getTextFieldBean("title");
        String renderkit = JCRNode.RENDERKIT_DEFAULT;

        //String uuid = nodelist.getSelectedName();
        String uuid = event.getRender().getParameter("nodeId");
        HiddenFieldBean uuidBean = event.getHiddenFieldBean("uuid");
        System.err.println("uuid= " + uuid);

        if (uuid != null) {
            try {
                ContentDocument doc = jcrService.getDocumentByUUID(uuid);
                content.setValue(doc.getContent());
                title.setValue(doc.getTitle());
                uuidBean.setValue(uuid);
                request.setAttribute("showContent", "true");
            } catch (ContentException e) {
                log.error("Unable to retrieve content: " + uuid, e);
                createErrorMessage(event, getLocalizedText(request, "CM_ERR_COULDNOTLOADDOCUMENT") + ": " + uuid);
            }
        } else {
            title.setValue("New Title");
            content.setValue("Please add content!");
            request.setAttribute("showContent", "true");
        }
        listNodes(event);
        setRenderKitValue(event, renderkit);
        //event.getTextFieldBean("title").setReadOnly(true);
        setNextState(request, defaultViewJSP);
    }

    public void removeNode(ActionFormEvent event) throws PortletException {
        //ListBoxBean nodelist = event.getListBoxBean("nodelist");
        //String uuid = nodelist.getSelectedName();
        //String value = nodelist.getSelectedValue();
        PortletRequest request = event.getActionRequest();

        String[] nodes = request.getParameterValues("nodeCB");
        if (nodes != null) {
            for (int i = 0; i < nodes.length; i++) {
                String uuid = nodes[i];
                try {
                    if (uuid != null && !uuid.equals((""))) {
                        jcrService.removeDocumentByUuid(uuid);
                        createSuccessMessage(event, getLocalizedText(request, "CM_DELETEDOCUMENT"));
                    } else {
                        createErrorMessage(event, getLocalizedText(request, "CM_ERR_SELECTNODE"));
                    }
                } catch (ContentException e) {
                    log.error("Unable to delete content: " + uuid);
                    createErrorMessage(event, getLocalizedText(request, "CM_ERR_COULDNOTLOADDOCUMENT") + ": " + uuid);
                }
            }
        } else {
            createErrorMessage(event, getLocalizedText(request, "CM_ERR_SELECTNODE"));
        }
        //listNodes(event);
        clearInputs(event);
        event.getTextFieldBean("title").setReadOnly(false);
        setNextState(request, DEFAULT_VIEW_PAGE);
    }

    public void backupContent(ActionFormEvent event) {
        MessageBoxBean msg = event.getMessageBoxBean("msg");
        try {
            jcrService.backupContent(backupDir);
            msg.setMessageType(MessageStyle.MSG_SUCCESS);
            msg.setKey("CM_BACKUPSUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error backing up.");
            msg.setMessageType(MessageStyle.MSG_ERROR);
            msg.setKey("CM_BACKUPERROR");
        }
        setNextState(event.getActionRequest(), "doEdit");
    }

    public void importContent(ActionFormEvent event) {
        ListBoxBean backupList = event.getListBoxBean("filelist");
        String filename = backupList.getSelectedName();
        MessageBoxBean msg = event.getMessageBoxBean("msg");

        try {
            jcrService.importContent(backupDir + File.separator + filename);
            msg.setMessageType(MessageStyle.MSG_SUCCESS);
            msg.setKey("CM_IMPORTSUCCESS");

        } catch (Exception e) {
            e.printStackTrace();
            msg.setMessageType(MessageStyle.MSG_ERROR);
            msg.setKey("CM_IMPORTERROR");

        }
        setNextState(event.getActionRequest(), "doEdit");
    }

    public void doMyEdit(FormEvent event) {
        ListBoxBean filelist = event.getListBoxBean("filelist");
        filelist.clear();
        File dir = new File(backupDir);
        File files[] = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isFile()) {
                ListBoxItemBean bean = new ListBoxItemBean();
                bean.setName(f.getName());
                bean.setValue(f.getName());
                filelist.addBean(bean);
            }
        }
    }

    public void doEdit(ActionFormEvent event) {
        doMyEdit(event);
        setNextState(event.getActionRequest(), defaultConfigJSP);
    }

    public void doEdit(RenderFormEvent event) {
        doMyEdit(event);
        setNextState(event.getRenderRequest(), defaultConfigJSP);
    }

}