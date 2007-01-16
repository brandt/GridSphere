package org.gridsphere.services.core.rss;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.gridsphere.portlet.service.PortletService;

/**
 * Defines methods for accessing RSS feeds
 */
public interface RssService extends PortletService {

    /**
     * Returns an SyndFeed object containing the feed of the given URL
     *
     * @param url url of the feed
     * @return com.sun.syndication.feed.synd.SyndFeed
     */
    SyndFeed getFeed(String url) throws FeedException;

}
