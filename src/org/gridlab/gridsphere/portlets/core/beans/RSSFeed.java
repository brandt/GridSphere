/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portlets.core.beans;

import org.jdom.Document;

public class RSSFeed {

    public String url = new String();
    public String label = new String();
    public long lastfetched = 0;
    Document feed = null;

    public RSSFeed(String url, String label) {
        this.url = url;
        this.label = label;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getLastfetched() {
        return lastfetched;
    }

    public void setLastfetched(long lastfetched) {
        this.lastfetched = lastfetched;
    }

    public Document getFeed() {
        return feed;
    }

    public void setFeed(Document feed) {
        this.feed = feed;
    }
}
