package org.gridsphere.services.core.request;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface Request {
    String getLabel();

    void setLabel(String label);

    String getOid();

    void setOid(String oid);

    String getUserID();

    void setUserID(String userID);

    Date getLifetime();

    void setLifetime(Date lifetime);

    Map getAttributes();

    void setAttributes(Map attributes);

    String getAttribute(String name);

    void setAttribute(String name, String value);
}
