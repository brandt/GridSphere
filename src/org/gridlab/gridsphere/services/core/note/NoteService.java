/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.note;

import org.gridlab.gridsphere.portlet.User;

import java.util.List;

public interface NoteService {
    /**
     * Adds a sheet to the storage
     * @param user
     * @param name
     * @param text
     */
    String addNote(User user, String name, String text);

    /**
     * Deletes the given note
     * @param note
     */
    void deleteNote(Note note);

    /**
     * returns the sheet with the oid
     * @param Oid
     * @return
     */
    Note getNoteByOid(String Oid);

    /**
     * Gets alls notes for this user
     * @param user
     * @return
     */
    List getNotes(User user);

    /**
     * updates the given sheet
     * @param note
     */
    String update(Note note);

    /**
     * retrns als notes containing the searchstring
     * @param user
     * @param searchstring
     * @return
     */
    List searchNotes(User user, String searchstring);
}
