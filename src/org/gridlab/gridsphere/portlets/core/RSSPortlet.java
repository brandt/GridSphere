/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.tags.event.FormEvent;
import org.gridlab.gridsphere.tags.event.impl.FormEventImpl;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.tags.web.element.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.*;

public class RSSPortlet extends AbstractPortlet {

    String _url = "http://www.xml.com/2002/12/18/examples/rss20.xml.txt";  // the serverside should be default rss feed
    long _lastFetched = 0;
    int _fetch_interval = 5*6000;        // in millisec
    Document RSSFeed = new Document(new Element("rss"));

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        System.err.println("init() in RSSPortlet");
    }

    private Document getRSSFeed(String url) {
        Document doc = new Document(new Element("rss"));
        try {
            SAXBuilder builder = new SAXBuilder(false);
            URL feedurl = new URL(url);
            doc = builder.build(feedurl);
            _lastFetched = System.currentTimeMillis();
            log.info("Fetched rss feed from "+url);
        } catch (MalformedURLException e) {
        } catch (JDOMException e) {
        }

        return doc;

    }

    public void actionPerformed(ActionEvent evt) {

        System.out.println("========================================================\n\n\n\n\n");

        DefaultPortletAction _action = evt.getAction();
        PortletRequest req = evt.getPortletRequest();
        FormEvent form = new FormEventImpl(evt);

        form.printRequestParameter(req);

        TextFieldBean fieldBean = (TextFieldBean)form.getElementBean("f");
        System.out.println("\n\n\n\nVALUE 1 :"+fieldBean.getValue());

        CheckBoxBean cbb = (CheckBoxBean)form.getElementBean("cbb");
        System.out.println("checkbox ");
        if (cbb.isSelected()) {
            System.out.println("selected ");
        } else {
            System.out.println("not selected ");
        }

        DropDownListBean ddl = (DropDownListBean)form.getElementBean("ddl");
        System.out.println("Selected Item ddl (value)"+((Selectable)((ddl.getSelectedItems()).get(0))).getValue());

        ListBoxBean lbb = (ListBoxBean)form.getElementBean("lbb");
        ArrayList result = lbb.getSelectedValues();
        System.out.println("SELECTED IN MULTIPLE");

        for (int i=0;i<result.size();i++) {
            System.out.println("RES: "+result.get(i));
        }

        TextAreaBean tab = (TextAreaBean)form.getElementBean("tab");
        System.out.println("TextArea value :"+tab.getValue());

        LabelBean lb = (LabelBean)form.getElementBean("lbb-label");
        System.out.println("COLOR of the label was:" +lb.getColor());

        CheckBoxListBean cblb = (CheckBoxListBean)form.getElementBean("os");
        result = cblb.getSelectedValues();
        System.out.println("SELECTEDIN IN OS");
        for (int i=0;i<result.size();i++) {
            System.out.println("RES: "+result.get(i));
        }


        RadioButtonListBean rblb = (RadioButtonListBean)form.getElementBean("music");
        result = rblb.getSelectedValues();
        System.out.println("SELECTEDIN IN Music");
        for (int i=0;i<result.size();i++) {
            System.out.println("RES: "+result.get(i));
        }


        //System.out.println("Selected Item lbb (value)"+lbb.getSelectedItem().getValue());



        System.out.println("========================================================\n\n\n\n\n");

        PortletData data = req.getData();
        data.setAttribute("myattr","testing");
        try {
            data.store();
        } catch (IOException e) {
            System.out.println("ERROR: "+e);
        }
        System.out.println("\n\n\n\nATTRIBUTE "+data.getAttribute("myattr"));

        String button = form.getPressedSubmitButton();

        System.out.println(">>>>>>>>>>>>> Button "+button);

        if (_action.getName().equals("rss_edit")) {
            if (button.equals("show")) {
               /* String temp_url = req.getParameter("rss_url");
                if (!temp_url.equals(_url)) {
                    _lastFetched = 0;
                    _url=temp_url;
                } */
                req.setMode(Portlet.Mode.VIEW);
            }
            if (button.equals("cancel")) {
                req.setMode(Portlet.Mode.VIEW);
            }
            if (button.equals("check")) {
                //@TODO need to put the RSS feed into a bean!
                req.setMode(Portlet.Mode.EDIT);
               /* Document doc = getRSSFeed(req.getParameter("rss_url"));
                Element root = doc.getRootElement();
                String version = root.getAttributeValue("version");
                if (version.equals("2.0")) { req.setAttribute("rss_support","yes");} else {req.setAttribute("rss_support","no");}
            */}
        }

        if (_action.getName().equals("rss_configure")) {
            if (button.equals("cancel")) {
                req.setMode(Portlet.Mode.VIEW);
            }
            if (button.equals("delete")) {

            }
            if (button.equals("ok")) {
               // String[] result = form.getSelectedValues("Labeltest");
                req.setMode(Portlet.Mode.VIEW);
            }

            if (button.equals("add")) {
                String name = req.getParameter("rss_name");
                String url = req.getParameter("rss_url");
            }
        }
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {

        PrintWriter out = response.getWriter();
        if ((System.currentTimeMillis()-_lastFetched)>_fetch_interval) {
            RSSFeed = getRSSFeed(_url);

        } else {
            out.println("Document was cached.<br>");
        }
        Element root = RSSFeed.getRootElement();
        PortletURI loginURI = response.createURI();
        String version = "unknown";
        version = root.getAttributeValue("version");
        out.println("RSS freed from URL: "+_url);

        try {
            if (version.equals("2.0") || version.equals("3.14159265359")) {
                List items = root.getChild("channel").getChildren("item");
                Iterator it = items.iterator();
                out.println("<ul>");
                while (it.hasNext()) {
                    Element item = (Element)it.next();
                    String title = item.getChild("title").getText();
                    String link = item.getChild("link").getText();
                    String desc = item.getChild("description").getText();
                    out.println("<li><a target=\"_new\" href=\""+link+"\">"+title+"</a><br/>"+desc+"</li>");
                }
                out.println("</ul>");
            } else {
                out.println("Unsupported RSS feed version ("+version+")");
            }
        } catch (Exception e) {
            out.println("This is a not a RSS feed.");
            System.out.println("============> "+e);
        }
    }

    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {

        PortletURI returnURI = response.createReturnURI();

        DefaultPortletAction defAction = new DefaultPortletAction("rss_edit");
        returnURI.addAction(defAction);


        // code prototyping
        TextFieldBean fieldBean = new TextFieldBean("jason", "oliver", false, false, 20, 30);
        fieldBean.setColor("blue");fieldBean.setBackgroundcolor("red");
        fieldBean.setDisabled(true);
        fieldBean.store("f", request);

        TextBean text = new TextBean("Text lalalal");
        text.setColor("white");
        text.setBackgroundcolor("black");
        text.store("textid",request);

        TextAreaBean tab = new TextAreaBean("tab","",false, false, 5,30);
        tab.store("tab",request);

        CheckBoxBean cbb = new CheckBoxBean("test","test",false, false);
        cbb.setSelected(true);
        cbb.store("cbb", request);

        DropDownListBean ddl = new DropDownListBean("apple");
        ddl.add("powerbook","pb");
        ddl.add("ibook","ib");
        ddl.add("powermac","pm");
        ddl.store("ddl", request);

        ListBoxBean lbb = new ListBoxBean("microsoft");
        lbb.add("Word 2000","Word");
        lbb.add("Excel 2000","Excel");
        lbb.add("Powerpoint 2000","Powerpoint");
        lbb.add("Access 2000","Access");
        lbb.add("Word XP","WordX");
        lbb.add("Excel XP","ExcelX");
        lbb.add("Powerpoint XP","PowerpointX");
        lbb.add("Access XP","AccessX");
        lbb.add("Outlook XP","OutlookX");
        lbb.setSize(7);
        lbb.setMultiple(true);
        lbb.setLabel("Microsoft");
        Label label = lbb.getLabel();
        label.setColor("red");
        lbb.store("lbb",request);

        CheckBoxListBean cblb = new CheckBoxListBean("OS");
        cblb.add("linux","Linux");
        cblb.add("windows","Windows 95");
        cblb.add("win2000","Windows 2000/XP");
        cblb.add("macosx","MacOS X", true);
        cblb.setLabel("OS");
        cblb.store("os",request);

        RadioButtonListBean rblb = new RadioButtonListBean("Music");
        rblb.add("fast","fast");
        rblb.add("medium","medium");
        rblb.add("slow","slow", true);
        rblb.setLabel("music style");
        rblb.store("music",request);

        // under the covers, a fieldbean is stored and mapped to f_name
        getPortletConfig().getContext().include("/jsp/rss/edit.jsp", request, response);
    }

    public void doConfigure(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PortletURI returnURI = response.createReturnURI();


        DefaultPortletAction defAction = new DefaultPortletAction("rss_configure");
        returnURI.addAction(defAction);
        request.setAttribute("rss_url", returnURI.toString());

        getPortletConfig().getContext().include("/jsp/rss/configure.jsp", request, response);
    }

    public void doHelp(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        doView(request,response);
    }


}
