<%@ page import="org.gridlab.gridsphere.portletcontainer.GridSphereProperties,
                 org.gridlab.gridsphere.portlet.*,
                 org.gridlab.gridsphere.portlets.core.LoginPortlet"%>

<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="rss_url" class="java.lang.String" scope="request"/>
<jsp:useBean id="rss_support" class="java.lang.String" scope="request"/>

    <gs:form action="rss_edit">
        <gs:textfield name="rss_url" size="40" maxlength="40" value="http://www.xml.com/2002/12/18/examples/rss20.xml.txt"></gs:textfield>
        <gs:submit name="show" value="Show"></gs:submit>
        <gs:submit name="check" value="Check for Support"></gs:submit>
        <gs:submit name="cancel" value="Cancel"></gs:submit>
    </gs:form>
