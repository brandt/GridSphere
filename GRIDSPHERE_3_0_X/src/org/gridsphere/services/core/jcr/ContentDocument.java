package org.gridsphere.services.core.jcr;

import java.util.Date;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class ContentDocument {

    private String content = "";
    private Date created = new Date();
    private Date modified = new Date();
    private String author = "";
    private String modifiedBy = "";
    private String mimeType = JCRNode.RENDERKIT_HTML;
    private String title = "";
    private String parentPath = null;
    private String uuid = null;


    public ContentDocument() {
        super();
        parentPath = JCRNode.GS_ROOT_CONTENTDOCUMENT_PATH;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String path) {
        this.parentPath = path;
    }

    public String getUuid() {
        return uuid;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
