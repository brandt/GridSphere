/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlets.core.beans.RSSFeed;
import org.gridlab.gridsphere.portlets.core.beans.RSSNews;
import org.gridlab.gridsphere.tags.event.FormEvent;
import org.gridlab.gridsphere.tags.event.impl.FormEventImpl;
import org.gridlab.gridsphere.tags.web.element.ListBoxBean;
import org.gridlab.gridsphere.tags.web.element.TextFieldBean;
import org.gridlab.gridsphere.tags.web.element.RadioButtonListBean;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

public class RSSPortlet extends AbstractPortlet {

    RSSNews news = RSSNews.getInstance();

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        System.err.println("init() in RSSPortlet");
    }

    public void actionPerformed(ActionEvent evt) {
        DefaultPortletAction _action = evt.getAction();
        PortletRequest req = evt.getPortletRequest();
        FormEvent form = new FormEventImpl(evt);
        form.printRequestParameter(req);
        String button = form.getPressedSubmitButton();

        if (_action.getName().equals("rss_edit")) {
            if (button.equals("show")) {
                req.setMode(Portlet.Mode.VIEW);
                ListBoxBean feeds = (ListBoxBean) form.getElementBean("rssfeeds");

                PortletData data = req.getData();
                data.setAttribute("RSSPortletFeedUrl",(String) (feeds.getSelectedValues().get(0)));

                try {
                    data.store();
                } catch (IOException e) {
                    System.out.println("ERROR: "+e);
                }

               // req.getSession().setAttribute("rssfeed", (String) (feeds.getSelectedValues().get(0)));
            }
            if (button.equals("desc")) {
                req.setMode(Portlet.Mode.EDIT);
                ListBoxBean feeds = (ListBoxBean) form.getElementBean("rssfeeds");
                req.setAttribute("rssdescription", (String) (feeds.getSelectedValues().get(0)));
            }
        }

        if (_action.getName().equals("rss_configure")) {
            if (button.equals("cancel")) {
                req.setMode(Portlet.Mode.VIEW);
            }
            if (button.equals("delete")) {
                ListBoxBean lbb = (ListBoxBean) form.getElementBean("rssfeeds");
                news.delete((String) lbb.getSelectedValues().get(0));
            }
            if (button.equals("done")) {
                RadioButtonListBean interval = (RadioButtonListBean)form.getElementBean("interval");

                Integer x = new Integer(((String)interval.getSelectedValues().get(0)));
                news.setFetchinterval(x.intValue());
                req.setMode(Portlet.Mode.VIEW);
            }
            if (button.equals("add")) {
                String name = ((TextFieldBean) form.getElementBean("desc")).getValue();
                String url = ((TextFieldBean) form.getElementBean("url")).getValue();
                news.add(url, name);
            }
        }
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {

        PrintWriter out = response.getWriter();
        String url = null;
        if (!(request.getUser() instanceof GuestUser)) {
            PortletData data = request.getData();
            url = data.getAttribute("RSSPortletFeedUrl");
        }

        //String url = (String) request.getSession().getAttribute("rssfeed");
        if (url == null) {
            url = "http://www.xml.com/2002/12/18/examples/rss20.xml.txt";
        }
        out.println(news.getHTML(url));
    }

    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {

        PortletURI returnURI = response.createReturnURI();
        DefaultPortletAction defAction = new DefaultPortletAction("rss_edit");
        returnURI.addAction(defAction);
        ListBoxBean newsfeed = (ListBoxBean) request.getSession().getAttribute("rssfeeds");
        if (newsfeed == null) {
            newsfeed = new ListBoxBean("newsfeed");
            Iterator it = news.iterator();
            while (it.hasNext()) {
                RSSFeed feed = (RSSFeed) it.next();
                newsfeed.add(feed.getLabel(), feed.getUrl());
            }
        }
        newsfeed.store("rssfeeds", request);
        String value = (String) request.getAttribute("rssdescription");
        if (value == null) {
            value = "";
        }
        TextFieldBean desc = new TextFieldBean("desc", value, false, true, 40, 120);
        desc.store("desc", request);
        getPortletConfig().getContext().include("/jsp/rss/edit.jsp", request, response);
    }

    public void doConfigure(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PortletURI returnURI = response.createReturnURI();
        DefaultPortletAction defAction = new DefaultPortletAction("rss_configure");
        returnURI.addAction(defAction);
        request.setAttribute("rss_url", returnURI.toString());
        ListBoxBean newsfeed = new ListBoxBean("newsfeed");
        newsfeed.setLabel("available feeds");

        Iterator it = news.iterator();
        while (it.hasNext()) {
            RSSFeed feed = (RSSFeed) it.next();
            newsfeed.add(feed.getLabel(), feed.getUrl());
        }
        newsfeed.store("rssfeeds", request);
        TextFieldBean url = new TextFieldBean("url", "", false, false, 30, 60);
        url.setLabel("Url of RSSFeed");
        url.store("url", request);
        TextFieldBean desc = new TextFieldBean("label", "", false, false, 30, 60);
        desc.setLabel("Description");
        desc.store("desc", request);

        RadioButtonListBean interval = (RadioButtonListBean)request.getSession().getAttribute("interval");
        if (interval==null) {
            interval = new RadioButtonListBean("interval");
            interval.add("30","30 seconds");
            interval.add("1","1 minute");
            interval.add("5", "5 minutes", true);
            interval.setLabel("cache time");
        }
        interval.store("interval",request);

        getPortletConfig().getContext().include("/jsp/rss/configure.jsp", request, response);
    }

    public void doHelp(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        getPortletConfig().getContext().include("/jsp/rss/help.jsp", request, response);

    }


}
