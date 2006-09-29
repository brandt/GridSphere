package org.gridsphere.services.core.mail;

import org.gridsphere.portlet.service.PortletServiceException;

public interface MailService {

    public void sendMail(MailMessage msg) throws PortletServiceException;

}
