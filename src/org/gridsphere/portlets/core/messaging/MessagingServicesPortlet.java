package org.gridsphere.portlets.core.messaging;

import org.gridsphere.portlet.PortletConfig;
import org.gridsphere.portlet.PortletSettings;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.provider.event.FormEvent;
import org.gridsphere.provider.portlet.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridsphere.services.core.messaging.TextMessagingService;
import org.gridsphere.tmf.services.TMService;
import org.gridsphere.tmf.services.TextMessageServiceConfig;
import org.gridsphere.tmf.services.config.BaseConfig;
import org.gridsphere.tmf.TextMessagingException;

import javax.servlet.UnavailableException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.io.IOException;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: MessagingServicesPortlet.java 4617 2006-03-08 20:09:00Z novotny $
 */

public class MessagingServicesPortlet extends ActionPortlet {

    private TextMessagingService tms = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            this.tms = (TextMessagingService) config.getContext().getService(TextMessagingService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        config.getServletContext().getServletContextName();

    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        DEFAULT_VIEW_PAGE = "doView";
        DEFAULT_HELP_PAGE = "doHelp";
    }


    private DefaultTableModel getMessagingService(FormEvent event) {
        boolean zebra = false;
        DefaultTableModel dtm = new DefaultTableModel();
        Set services = tms.getServices();
        for (Iterator iterator = services.iterator(); iterator.hasNext();) {
            // get the service
            TMService service = (TMService) iterator.next();

            TableRowBean trb = new TableRowBean();
            TableCellBean tcbDescription = new TableCellBean();
            TableCellBean tcbConfiguration = new TableCellBean();

            if (!zebra) {
                trb.setZebra(true);
            }
            zebra = !zebra;

            // description
            CheckBoxBean restartBox = new CheckBoxBean();
            restartBox.setBeanId("restartBox");
            TextBean description = event.getTextBean("description"+service.getClass().getName());

            TextMessageServiceConfig config = service.getServiceConfig();
            description.setValue(config.getProperty(BaseConfig.SERVICE_DESCRIPTION));

            tcbDescription.addBean(description);
            tcbDescription.setValign("top");

            // configuration
            List params = config.getConfigPropertyKeys();
            DefaultTableModel configTable = new DefaultTableModel();

            for (int j=0;j<params.size();j++) {

                TableRowBean configRow = new TableRowBean();
                TableCellBean configDescription = new TableCellBean();
                TableCellBean configValue = new TableCellBean();

                TextBean paramName = event.getTextBean(service.getClass().getName()+"paramKey"+params.get(j));
                paramName.setValue((String)params.get(j));
                configDescription.addBean(paramName);

                TextFieldBean paramValue = event.getTextFieldBean(service.getClass().getName()+"paramValue"+params.get(j));
                paramValue.setValue(config.getProperty((String)params.get(j)));
                configValue.addBean(paramValue);

                configRow.addBean(configDescription); configDescription.setAlign("top");
                configRow.addBean(configValue);

                configTable.addTableRowBean(configRow);
            }

            FrameBean frameConfig = new FrameBean();
            frameConfig.setTableModel(configTable);
            tcbConfiguration.addBean(frameConfig);
            trb.addBean(tcbDescription);


            trb.addBean(tcbConfiguration);

            dtm.addTableRowBean(trb);

        }
        return dtm;

    }

    public void doView(FormEvent event)  {
        FrameBean serviceFrame = event.getFrameBean("serviceframe");
        serviceFrame.setTableModel(getMessagingService(event));
        Set services = tms.getServices();
        event.getPortletRequest().setAttribute("services", ""+services.size());
        setNextState(event.getPortletRequest(), "admin/messaging/view.jsp");
    }

    public void doSaveValues(FormEvent event) {
        Set services = tms.getServices();
        for (Iterator iterator = services.iterator(); iterator.hasNext();) {
            // get the service
            TMService service = (TMService) iterator.next();
            TextMessageServiceConfig config = service.getServiceConfig();

            List params = config.getConfigPropertyKeys();

            boolean isDirty = false;
            for (int j=0;j<params.size();j++) {
                String propKey = (String)params.get(j);
                String propValue = config.getProperty(propKey);

                TextFieldBean paramValue = event.getTextFieldBean(service.getClass().getName()+"paramValue"+propKey);
                String propTextBeanValue = paramValue.getValue();

                // if a parameter changed, restart the service....
                if (!propTextBeanValue.equals(propValue)) {
                    config.setProperty((String)params.get(j), paramValue.getValue());
                    isDirty = true;
                }
            }
            if (isDirty) {
                try {
                    config.saveConfig();
                    service.shutdown();
                    service.startup();
                    String msg = this.getLocalizedText(event.getPortletRequest(), "MESSAGING_SERVICE_SERVICERESTARTED");
                    createSuccessMessage(event, msg + ": "+config.getProperty(TextMessagingService.SERVICE_NAME));
                } catch (IOException e) {
                    createErrorMessage(event, this.getLocalizedText(event.getPortletRequest(), "MESSAGING_SERVICE_SAVEFAILURE"));
                } catch (TextMessagingException e) {
                    String msg = this.getLocalizedText(event.getPortletRequest(), "MESSAGING_SERVICE_RESTARTFAILURE");
                    createErrorMessage(event, msg+" : "+config.getProperty(TextMessagingService.SERVICE_NAME));
                }
            }
        }

        setNextState(event.getPortletRequest(), "doView");
    }

    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        msgBox.appendText(msg);
    }

    private void createSuccessMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_SUCCESS);
        msgBox.appendText(msg);
    }

}
