package org.gridsphere.portlets.core.rss;

import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.services.core.rss.RssService;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import com.sun.syndication.io.FeedException;
import com.sun.syndication.feed.synd.SyndFeed;

import java.net.MalformedURLException;
import java.io.IOException;

public class RSSPortlet extends ActionPortlet {

    public final static String VIEW_RSS_JSP = "rss/viewRSS.jsp";
    private RssService rssService = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        rssService = (RssService)createPortletService(RssService.class);
        DEFAULT_VIEW_PAGE = "doView";
    }


    public void doView(RenderFormEvent event) throws PortletException {
        SyndFeed feed = null;
        PortletPreferences prefs = event.getRenderRequest().getPreferences();
        String feedURL = prefs.getValue("feedurl", "");

        //  todo localize the errormessages
        if (feedURL.equals("")) return;
        try {
            feed = rssService.getFeed(feedURL);
        } catch (FeedException e) {
            createErrorMessage(event, "Could not create Feed.");
        } catch (MalformedURLException e) {
            createErrorMessage(event, "RSS URL " + feedURL + " is not valid.");
        } catch (IOException e) {
            createErrorMessage(event, "Could not read RSS feed from " + feedURL);
        }
        event.getRenderRequest().setAttribute("rssfeed", feed);
        setNextState(event.getRenderRequest(), VIEW_RSS_JSP);
    }

}
