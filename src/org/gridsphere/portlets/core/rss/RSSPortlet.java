package org.gridsphere.portlets.core.rss;

import org.gridsphere.provider.portlet.ActionPortlet;
import org.gridsphere.provider.event.FormEvent;
import org.gridsphere.provider.portletui.beans.MessageBoxBean;
import org.gridsphere.provider.portletui.beans.MessageStyle;
import org.gridsphere.portlet.*;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.services.core.rss.RssService;
import org.gridsphere.services.core.security.password.PasswordManagerService;

import javax.servlet.UnavailableException;

import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.feed.synd.SyndFeed;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

public class RSSPortlet extends ActionPortlet {

    public final static String VIEW_RSS_JSP = "rss/viewRSS.jsp";
    private RssService rssService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            rssService = (RssService) getPortletConfig().getContext().getService(RssService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        DEFAULT_VIEW_PAGE = "doView";
    }



    public void doView(FormEvent event) throws PortletException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        PortletApplicationSettings pas = getPortletSettings().getApplicationSettings();
        String feedURL = pas.getAttribute("feedurl");

        //  todo localize the errormessages
        try {
            feed = rssService.getFeed(feedURL);
        } catch (FeedException e) {
            createErrorMessage(event, "Could not create Feed.");
        } catch (MalformedURLException e) {
           createErrorMessage(event, "RSS URL "+feedURL+" is not valid.");
        } catch (IOException e) {
           createErrorMessage(event, "Could not read RSS feed from "+feedURL);
        }
        event.getPortletRequest().setAttribute("rssfeed", feed);
        setNextState(event.getPortletRequest(), VIEW_RSS_JSP);
    }

    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("errormsg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        msgBox.appendText(msg);
    }

    private void createSuccessMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("successmsg");
        msgBox.setMessageType(MessageStyle.MSG_SUCCESS);
        msgBox.appendText(msg);
    }

}
