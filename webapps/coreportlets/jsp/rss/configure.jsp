<%@ page import="org.gridlab.gridsphere.portletcontainer.GridSphereProperties,
                 org.gridlab.gridsphere.portlet.*" %>

<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="rss_url" class="java.lang.String" scope="request"/>
<jsp:useBean id="rss_name" class="java.lang.String" scope="request"/>
<jsp:useBean id="rss_listbox" class="org.gridlab.gridsphere.tags.web.model.ListBoxModel" scope="request"/>
<jsp:useBean id="rss_checkboxlist" class="org.gridlab.gridsphere.tags.web.model.CheckBoxModel" scope="request"/>
<jsp:useBean id="rss_selcheckboxes" class="java.lang.String" scope="request"/>


Press add to add url/name combo to the list and see your selection of checkboxes.   <p/>

Your selection: > <%= rss_selcheckboxes %>

<hr/>

    <gs:form action="rss_configure">



        Url :<gs:textfield name="rss_url" size="20" maxlength="40" value=""></gs:textfield>
        <p/>
        Name :<gs:textfield name="rss_name" size="20" maxlength="30" value=""></gs:textfield>
        <p/>
        <gs:listbox id="rss_feed" collection="<%= rss_listbox %>"/>
        <gs:checkboxlist id="rss_check" collection="<%= rss_checkboxlist %>"/>

        <gs:submit name="add" value="Add"></gs:submit>
        <gs:submit name="cancel" value="Cancel"></gs:submit>
        <gs:submit name="delete" value="Delete"></gs:submit>
        <gs:submit name="ok" value="Ok"></gs:submit>
    </gs:form>

<br/>
