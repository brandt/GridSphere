/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 0.9 2004/03/30
 */
package org.gridlab.gridsphere.services.core.secdir;

/**
 * Created by IntelliJ IDEA.
 * User: docentt
 * Date: Mar 22, 2004
 * Time: 1:20:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceInfo {
    private String resource;
    private boolean isDirectory;
    private long lastModified;
    private long length;

    public ResourceInfo(String resource, boolean directory, long lastModified, long length) {
        this.resource = resource;
        isDirectory = directory;
        this.lastModified = lastModified;
        this.length = length;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
