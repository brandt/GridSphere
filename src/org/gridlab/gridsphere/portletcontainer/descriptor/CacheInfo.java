/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

public class CacheInfo {

    private long expires = -1;
    private String shared = "false";

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getShared() {
        return shared;
    }

}
