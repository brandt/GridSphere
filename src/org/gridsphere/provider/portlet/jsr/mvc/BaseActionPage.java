package org.gridsphere.provider.portlet.jsr.mvc;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class BaseActionPage implements ActionPage {


    public void doView(RenderRequest request,
                       RenderResponse response)
            throws PortletException, java.io.IOException {

    }


    public void doEdit(RenderRequest request,
                       RenderResponse response)
            throws PortletException, java.io.IOException {

    }

    public void doHelp(RenderRequest request,
                       RenderResponse response)
            throws PortletException, java.io.IOException {

    }

    public void setErrorMessage(PortletRequest request, String errorMessage) {
        request.getPortletSession(true).setAttribute(ERROR_PAGE, errorMessage);
    }

    public String getErrorMessage(PortletRequest request) {
        return (String)request.getPortletSession(true).getAttribute(ERROR_PAGE);
    }

    public void setSuccessMessage(PortletRequest request, String successMessage) {
        request.getPortletSession(true).setAttribute(SUCCESS_PAGE, successMessage);
    }

    public String getSuccessMessage(PortletRequest request) {
        return (String)request.getPortletSession(true).getAttribute(SUCCESS_PAGE);
    }

}