/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.note;

import org.radeox.EngineManager;
import org.radeox.engine.context.BaseRenderContext;
import org.radeox.engine.context.RenderContext;

import java.util.Date;

/**
 *
 */
public class Note {


    /**
     * Objectid
     */
    private String oid = null;

    /**
     * shortname of the note
     */
    private String name = null;
    /**
     * content of the note
     */
    private String content = null;
    /**
     * the user it belongs to
     */
    private String userid = null;

    /**
     * date note was created
     */
    private Date dateCreated = null;

    /**
     * date not was modified
     */
    private Date dateModified = null;

    /**
     * if it is a to do item
     */
    private boolean todo = false;

    /**
     * priority of the to do item
     */
    private int priority = 0;

    /**
     * date when (if to do item) was closed
     */
    private Date dateClosed = null;

    /**
     * until when this to do item is due
     */
    private Date dateDue = null;

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public boolean isTodo() {
        return todo;
    }

    public void setTodo(boolean todo) {
        this.todo = todo;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRadeoxMarkup() {
        RenderContext context = new BaseRenderContext();
        return EngineManager.getInstance().render(content, context);
    }

}