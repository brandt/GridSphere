/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.note.impl;

import org.gridlab.gridsphere.services.core.note.NoteService;
import org.gridlab.gridsphere.services.core.note.Note;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;

/**
 *
 */
public class NoteServiceImpl implements NoteService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(NoteServiceImpl.class);

    PersistenceManagerRdbms pm = null;


    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        this.pm = PersistenceManagerFactory.createGridSphereRdbms();
    }

    public void destroy() {
        try {
            pm.destroy();
        } catch (PersistenceManagerException e) {
            log.info("Problems shutting down NotePadService.");
        }
    }


    public Note getNoteByOid(String Oid) {
        Note result = null;
        try {
            result = (Note) pm.restore("from " + Note.class.getName() + " as note where note.oid='" + Oid + "'");
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean noteExists(User user, String name) {
        Note result = null;
        try {
            result = (Note) pm.restore("from " + Note.class.getName() + " as note where note.name='" + name + "' and note.Userid='" + user.getID() + "'");
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return (result != null);
    }

    public String addNote(User user, String name, String text) {
        if (!noteExists(user, name)) {
            Note Note = new Note();
            Note.setName(name);
            Note.setContent(text);
            Note.setUserid(user.getID());
            try {
                pm.create(Note);
                return "";
            } catch (PersistenceManagerException e) {
                e.printStackTrace();
                return "Error: Problem creating note " + name + " in storage.";
            }
        }
        return "Error: Note with name " + name + " already exists.";
    }

    public void deleteNote(Note Note) {
        try {
            pm.delete(Note);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
    }

    public List getNotes(User user) {
        List result = null;
        try {
            String oql = "from " + Note.class.getName() + " as note where note.Userid='" + user.getID() + "'";
            log.debug("Query with " + oql);
            result = pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List searchNotes(User user, String searchstring) {
        List result = null;
        try {
            String oql =
                    "from " + Note.class.getName() + " as note where note.Userid='" + user.getID() + "' and (note.content like '%" + searchstring + "%' or note.name like '%" + searchstring + "%')";
            log.debug("Query with " + oql);
            result = pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String update(Note Note) {
        try {
            pm.update(Note);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
            return "Error: updating note " + Note.getName() + " failed.";
        }
        return "";
    }
}
