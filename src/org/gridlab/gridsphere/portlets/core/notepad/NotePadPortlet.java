/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.notepad;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.HiddenFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.services.core.note.NoteService;
import org.gridlab.gridsphere.services.core.note.Note;

import javax.servlet.UnavailableException;
import java.util.List;

/**
 *
 */
public class NotePadPortlet extends ActionPortlet {
    private static PortletLog log = SportletLog.getInstance(NotePadPortlet.class);
private NoteService noteservice = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);

        try {
            noteservice = (NoteService)config.getContext().getService(NoteService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize NoteService", e);
        }
        DEFAULT_VIEW_PAGE = "showList";
        DEFAULT_EDIT_PAGE = "notepad/showAdd.jsp";
        DEFAULT_HELP_PAGE = "notepad/help.jsp";
    }

    public void showList(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        User user = request.getUser();
        List allNotes = noteservice.getNotes(user);
        request.setAttribute("Notes", allNotes);
        setNextState(request, "notepad/main.jsp");
    }

    public void viewNote(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        String oid = event.getAction().getParameter("oid");
        Note Note = noteservice.getNoteByOid(oid);
        request.setAttribute("np_action", "view");
        request.setAttribute("Note", Note);
        setNextState(request, "notepad/viewNote.jsp");
    }

    public void doShowNew(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        request.setAttribute("np_action", "new");
        request.setAttribute("Note", new Note());
        setNextState(request, "notepad/viewNote.jsp");
    }

    public void doShowEdit(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        HiddenFieldBean oid = event.getHiddenFieldBean("Noteoid");
        request.setAttribute("np_action", "edit");
        request.setAttribute("Note", noteservice.getNoteByOid(oid.getValue()));
        setNextState(request, "notepad/viewNote.jsp");
    }

    public void doAdd(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        User user = request.getUser();
        TextAreaBean content = event.getTextAreaBean("content");
        TextFieldBean head = event.getTextFieldBean("head");
        noteservice.addNote(user, head.getValue(), content.getValue());
        request.setMode(Portlet.Mode.VIEW);
        setNextState(request, "showList");
    }

    public void doCancel(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        request.setMode(Portlet.Mode.VIEW);
        setNextState(request, "showList");
    }

    public void doDelete(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        HiddenFieldBean oid = event.getHiddenFieldBean("Noteoid");
        noteservice.deleteNote(noteservice.getNoteByOid(oid.getValue()));
        setNextState(request, "showList");
    }

    public void doUpdate(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        HiddenFieldBean oid = event.getHiddenFieldBean("Noteoid");
        TextAreaBean content = event.getTextAreaBean("content");
        TextFieldBean head = event.getTextFieldBean("head");
        Note Note = noteservice.getNoteByOid(oid.getValue());
        Note.setContent(content.getValue());
        Note.setName(head.getValue());
        noteservice.update(Note);
        request.setAttribute("np_action", "view");
        request.setAttribute("Note", Note);
        setNextState(request, "notepad/viewNote.jsp");
    }

    public void doSearch(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        TextFieldBean search = event.getTextFieldBean("search");
        List result = noteservice.searchNotes(request.getUser(), search.getValue());
        request.setAttribute("Notes", result);
        setNextState(request, "notepad/main.jsp");
    }
}
 


