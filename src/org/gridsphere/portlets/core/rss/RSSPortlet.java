package org.gridsphere.portlets.core.rss;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.FormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridsphere.services.core.rss.RssService;

import javax.portlet.*;
import java.io.IOException;

public class RSSPortlet extends ActionPortlet {

    public final static String VIEW_RSS_JSP = "rss/viewRSS.jsp";
    private RssService rssService = null;
    private String defaultFeed = "http://rss.cnn.com/rss/cnn_topstories.rss";

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        rssService = (RssService) createPortletService(RssService.class);
        DEFAULT_VIEW_PAGE = "doView";
        DEFAULT_EDIT_PAGE = "doEdit";
    }

    protected SyndFeed createFeedListBox(FormEvent event, PortletRequest request, String selectedFeed) {

        PortletPreferences prefs = request.getPreferences();
        String[] feedURL = prefs.getValues("feedurl", new String[]{defaultFeed});

        ListBoxBean feedsLB = event.getListBoxBean("feedsLB");
        feedsLB.clear();
        feedsLB.setSize(1);

        SyndFeed result = null;
        SyndFeed feed = null;
        for (int i = 0; i < feedURL.length; i++) {
            try {
                feed = rssService.getFeed(feedURL[i]);
                if (result == null) result = feed;
            } catch (FeedException e) {
                log.error("Could not create Feed.", e);
                createErrorMessage(event, getLocalizedText(request, "RSS_ERR_COULDNOTCREATEFEED") + ": " + feedURL[i] + "<br/>");
            }
            ListBoxItemBean item = new ListBoxItemBean();
            item.setName(feedURL[i]);
            item.setValue((feed != null) ? feed.getTitle() : feedURL[i]);
            if (selectedFeed != null && selectedFeed.equals(feedURL[i])) {
                result = feed;
                item.setSelected(true);
            }
            feedsLB.addBean(item);
        }
        return result;
    }

    public void doView(RenderFormEvent event) throws PortletException {
        String feedurl = (String) event.getRenderRequest().getPortletSession(true).getAttribute("selectedfeed");
        SyndFeed selectedFeed = createFeedListBox(event, event.getRenderRequest(), feedurl);
        event.getRenderRequest().setAttribute("rssfeed", selectedFeed);
        setNextState(event.getRenderRequest(), VIEW_RSS_JSP);
    }

    public void selectFeed(ActionFormEvent event) {
        ListBoxBean feedsLB = event.getListBoxBean("feedsLB");
        String selectedFeed = feedsLB.getSelectedValue();
        event.getActionRequest().getPortletSession(true).setAttribute("selectedfeed", selectedFeed);
    }

    public void doEdit(RenderFormEvent event) {
        createFeedListBox(event, event.getRenderRequest(), null);
        setNextState(event.getRenderRequest(), "rss/edit.jsp");
    }

    public void doEdit(ActionFormEvent event) {
        createFeedListBox(event, event.getActionRequest(), null);
        setNextState(event.getActionRequest(), "rss/edit.jsp");
    }


    /**
     * Save the urls into the prefs and creates messages on success and failure.
     *
     * @param prefs
     * @param newURLs
     * @param event
     * @param successMsg
     */
    public void saveURLs(PortletPreferences prefs, String newURLs[], ActionFormEvent event, String successMsg) {
        try {
            prefs.setValues("feedurl", newURLs);
            prefs.store();
            createSuccessMessage(event, getLocalizedText(event.getActionRequest(), successMsg));
        } catch (ReadOnlyException e) {
            e.printStackTrace();
            createErrorMessage(event, getLocalizedText(event.getActionRequest(), "RSS_ERR_COULDNOTSAVE"));
        } catch (IOException e) {
            e.printStackTrace();
            createErrorMessage(event, getLocalizedText(event.getActionRequest(), "RSS_ERR_COULDNOTSAVE"));
        } catch (ValidatorException e) {
            e.printStackTrace();
            createErrorMessage(event, getLocalizedText(event.getActionRequest(), "RSS_ERR_COULDNOTSAVE"));
        }
    }

    /**
     * Saves the new url along with the existing ones
     *
     * @param event
     */
    public void saveFeed(ActionFormEvent event) {
        TextFieldBean newUrl = event.getTextFieldBean("newfeedurl");
        try {
            // cache and see if this url is valid and a feed
            rssService.getFeed(newUrl.getValue());

            PortletPreferences prefs = event.getActionRequest().getPreferences();
            String[] feedURL = prefs.getValues("feedurl", new String[]{defaultFeed});
            String[] newURLs = new String[feedURL.length + 1];
            for (int i = 0; i < feedURL.length; i++) {
                newURLs[i] = feedURL[i];
            }
            newURLs[feedURL.length] = newUrl.getValue();
            saveURLs(prefs, newURLs, event, "RSS_FEED_ADDED");
        } catch (FeedException e) {
            createErrorMessage(event, getLocalizedText(event.getActionRequest(), "RSS_ERR_NORSSFEED"));
        }
        setNextState(event.getActionRequest(), DEFAULT_EDIT_PAGE);
    }

    /**
     * Removes an url from the preferences.
     *
     * @param event
     */
    public void removeFeed(ActionFormEvent event) {
        PortletPreferences prefs = event.getActionRequest().getPreferences();
        String[] feedURL = prefs.getValues("feedurl", new String[]{null});
        ListBoxBean urls = event.getListBoxBean("feedsLB");
        if (feedURL[0] != null) {
            String url = urls.getSelectedValue();
            String[] newURLs = new String[feedURL.length - 1];
            int newUrlCounter = 0;
            for (int i = 0; i < feedURL.length; i++) {
                if (!feedURL[i].equals(url)) {
                    newURLs[newUrlCounter] = feedURL[i];
                    newUrlCounter++;
                }
            }
            saveURLs(prefs, newURLs, event, "RSS_FEED_DELETED");
        } else {
            createErrorMessage(event, getLocalizedText(event.getActionRequest(), "RSS_ERR_NORSSFEED"));
        }
        setNextState(event.getActionRequest(), DEFAULT_EDIT_PAGE);
    }
}
