<%@ page import="org.gridlab.gridsphere.portletcontainer.GridSphereProperties,
                 org.gridlab.gridsphere.portlet.*,
                 org.gridlab.gridsphere.portlets.core.LoginPortlet"%>

<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="rss_url" class="java.lang.String" scope="request"/>

    <gs:form action="rss_edit">
        <gs:textfield name="rss_url" size="8" maxlength="20" value=""></gs:textfield>
        <gs:input type="submit" name="option" value="Show"></gs:input>
    </gs:form>