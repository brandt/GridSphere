/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

public class CacheInfo {

    private long expires;
    private String shared;

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
