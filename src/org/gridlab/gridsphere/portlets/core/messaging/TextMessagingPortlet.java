package org.gridlab.gridsphere.portlets.core.messaging;

import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.core.messaging.TextMessagingService;
import org.gridlab.gridsphere.tmf.Message;
import org.gridlab.gridsphere.tmf.config.User;
import org.gridlab.gridsphere.tmf.config.Messagetype;
import org.gridlab.gridsphere.tmf.config.TmfService;

import javax.servlet.UnavailableException;
import java.util.ArrayList;
import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class TextMessagingPortlet extends ActionPortlet {

    private TextMessagingService tms = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);

        try {
            tms = (TextMessagingService) config.getContext().getService(TextMessagingService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize TextMessagingService", e);
        }
        DEFAULT_VIEW_PAGE = "doStart";
        DEFAULT_EDIT_PAGE = "doEditAccounts";
    }

    // start form

    public void doStart(FormEvent event) {

        PortletRequest request = event.getPortletRequest();
        List users = tms.getUsers();
        ListBoxBean userlist = event.getListBoxBean("userlist");
        userlist.setSize(10);
        userlist.setMultipleSelection(false);
        userlist.clear();

        for (int i=0;i<users.size();i++)  {
            ListBoxItemBean u = new ListBoxItemBean();
            User user = (User)users.get(i);
            List services = user.getMessagetype();
            String types = new String();
            for (int j=0;j<services.size();j++) {
                types = types +" "+ ((Messagetype)services.get(j)).getMessagetype();
            }
            u.setValue(user.getName()+" ("+types+")");
            u.setName(user.getUserid());
            userlist.addBean(u);
        }

        ListBoxBean tmfservices = event.getListBoxBean("services");
        tmfservices.clear();

        List services = tms.getServices();
        for (int i=0;i<services.size();i++) {
            TmfService tmfservice = (TmfService) services.get(i);
            ListBoxItemBean s = new ListBoxItemBean();
            s.setValue(tmfservice.getMessageType()+" - " +tmfservice.getDescription());
            s.setName(tmfservice.getMessageType());
            tmfservices.addBean(s);
        }

        setNextState(event.getPortletRequest(), "messaging/text.jsp");

    }

    public void sendIM(FormEvent event) {
        TextFieldBean message = event.getTextFieldBean("message");
        ListBoxBean users = event.getListBoxBean("userlist");
        ListBoxBean services = event.getListBoxBean("services");

        Message m = tms.createNewMessage();
        m.setMessageType(services.getSelectedValue());
        m.setRecipient(users.getSelectedValue());
        m.setBody(message.getValue());
        tms.send(m);
        setNextState(event.getPortletRequest(), "doStart");

    }

    public void doEditAccounts(FormEvent event) {

        PortletRequest request = event.getPortletRequest();

        ListBoxBean tmfservices = event.getListBoxBean("services");
        tmfservices.setSize(1);
        tmfservices.clear();

        List services = tms.getServices();
        for (int i=0;i<services.size();i++) {
            TmfService tmfservice = (TmfService) services.get(i);
            ListBoxItemBean s = new ListBoxItemBean();
            s.setValue(tmfservice.getMessageType()+" - " +tmfservice.getDescription());
            s.setName(tmfservice.getMessageType());
            tmfservices.addBean(s);
        }

        setNextState(request, "messaging/edit.jsp");
    }

    public void editChoosenService(FormEvent event) {

        PortletRequest request = event.getPortletRequest();

        ListBoxBean tmfservices = event.getListBoxBean("services");
        List selection = tmfservices.getSelectedNames();

        String service = ((String)selection.get(0));
        List users = tms.getUsers();

        for (int i=0; i<users.size();i++) {
            User u = (User)users.get(i);
            if (u.getUserid().equals(request.getUser().getUserID())) {
                TextFieldBean username = event.getTextFieldBean("username");
                username.setValue(u.getUserNameForMessagetype(service));
            }
        }

        HiddenFieldBean h_service = event.getHiddenFieldBean("Hservice");
        h_service.setValue(service);
        HiddenFieldBean h_userid = event.getHiddenFieldBean("Huserid");
        h_userid.setValue(event.getPortletRequest().getUser().getUserID());

        setNextState(request, "messaging/edit2.jsp");
    }

    private void modifySettings(FormEvent event, boolean delete) {

        PortletRequest request = event.getPortletRequest();
        HiddenFieldBean h_service = event.getHiddenFieldBean("Hservice");
        HiddenFieldBean h_userid = event.getHiddenFieldBean("Huserid");
        TextFieldBean username = event.getTextFieldBean("username");

        User user = tms.getUser(h_userid.getValue());
        // if the user does not exist yet
        if (user==null) {
            user = new User();
            user.setName(event.getPortletRequest().getUser().getFullName());
            user.setUserid(h_userid.getValue());
            user.setPreferred(h_service.getValue());
        }

        user.removeMessageType(h_service.getValue());
        if (!delete) {
            user.setMessageType(h_service.getValue(), username.getValue());
        }
        tms.setUser(user);
        request.setMode(Portlet.Mode.VIEW);
        setNextState(request, "doStart");

    }


    public void saveServiceSettings(FormEvent event) {
        modifySettings(event, false);
    }

    public void deleteServiceSettings(FormEvent event){
        modifySettings(event, true);
    }

    public void cancelEdit(FormEvent event) {
        PortletRequest request = event.getPortletRequest();
        request.setMode(Portlet.Mode.VIEW);
        setNextState(request, "doStart");
    }
}
