package org.gridsphere.services.core.rss;

import org.gridsphere.portlet.service.PortletService;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;

import java.net.MalformedURLException;
import java.io.IOException;

/**
 * Defines methods for accessing RSS feeds
 */
public interface RssService extends PortletService {

    /**
     * Returns an SyndFeed object containing the feed of the given URL
     * @param url url of the feed
     * @return com.sun.syndication.feed.synd.SyndFeed
     */
    SyndFeed getFeed(String url) throws FeedException, IOException;

}
