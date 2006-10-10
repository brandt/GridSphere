package org.gridsphere.portlets.core.rss;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridsphere.services.core.rss.RssService;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import java.io.IOException;
import java.net.MalformedURLException;

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
        String[] feedURL = prefs.getValues("feedurl", new String[]{null});
        //  todo localize the errormessages
        if (feedURL.equals("")) return;

        ListBoxBean feedsLB = event.getListBoxBean("feedsLB");
        feedsLB.clear();
        feedsLB.setSize(1);

        SyndFeed selectedFeed = null;
        String feedurl = (String)event.getRenderRequest().getPortletSession(true).getAttribute("selectedfeed");
        if (feedurl == null) feedurl = feedURL[0];
        for (int i = 0; i < feedURL.length; i++) {
            try {
                feed = rssService.getFeed(feedURL[i]);
            } catch (FeedException e) {
                log.error("Could not create Feed.", e);
                createErrorMessage(event, "Could not create Feed.");
            } catch (MalformedURLException e) {
                log.error("RSS URL " + feedURL + " is not valid.", e);
                createErrorMessage(event, "RSS URL " + feedURL[i] + " is not valid.");
            } catch (IOException e) {
                log.error("Could not read RSS feed from " + feedURL[i], e);
                createErrorMessage(event, "Could not read RSS feed from " + feedURL[i]);
            }
            ListBoxItemBean item = new ListBoxItemBean();
            item.setName(feedURL[i]);
            item.setValue((feed != null) ? feed.getTitle() : feedURL[i]);
            if (feedurl.equals(feedURL[i])) {
                selectedFeed = feed;
                item.setSelected(true);
            }
            feedsLB.addBean(item);
        }
        event.getRenderRequest().setAttribute("rssfeed", selectedFeed);
        setNextState(event.getRenderRequest(), VIEW_RSS_JSP);
    }

    public void selectFeed(ActionFormEvent event) {
        ListBoxBean feedsLB = event.getListBoxBean("feedsLB");
        String selectedFeed = feedsLB.getSelectedValue();
        event.getActionRequest().getPortletSession(true).setAttribute("selectedfeed", selectedFeed);
    }

}
