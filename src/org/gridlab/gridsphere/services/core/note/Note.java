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
 


