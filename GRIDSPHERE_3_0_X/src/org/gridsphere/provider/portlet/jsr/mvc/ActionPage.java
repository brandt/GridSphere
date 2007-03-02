package org.gridsphere.provider.portlet.jsr.mvc;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public interface ActionPage {

    public String ERROR_PAGE = "org.gridsphere.provider.portlet.jsr.mvc.ActionPage.ERROR_PAGE";
    public String SUCCESS_PAGE = "org.gridsphere.provider.portlet.jsr.mvc.ActionPage.SUCCESS_PAGE";

    public void doView(RenderRequest request,
                       RenderResponse response)
            throws PortletException, java.io.IOException;


    public void doEdit(RenderRequest request,
                       RenderResponse response)
            throws PortletException, java.io.IOException;

    public void doHelp(RenderRequest request,
                       RenderResponse response)
            throws PortletException, java.io.IOException;

    public String getErrorMessage(PortletRequest request);

    public String getSuccessMessage(PortletRequest request);

    public void setErrorMessage(PortletRequest request, String errorMessage);

    public void setSuccessMessage(PortletRequest request, String successMessage);

}