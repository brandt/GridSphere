<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.PortletGroup"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% boolean isPublic = ((request.getAttribute("isPrivate") != null) ? false : true); %>

<ui:form>

<ui:messagebox beanId="msg"/>

<ui:hiddenfield beanId="groupId"/>

<h3><ui:text key="GROUP_INFO" style="nostyle"/></h3>
<ui:text key="GROUP_CREATE_NAME"/>&nbsp;&nbsp;<ui:textfield beanId="groupNameTF"/>
<p>
<ui:text key="GROUP_DESC_MSG"/>&nbsp;&nbsp;<ui:textfield beanId="groupDescTF"/>
</p>

<h3><ui:text key="GROUP_VISIBILITY_MSG" style="nostyle"/></h3>

<ui:text key="GROUP_VISIBILITY_DESC"/>
<ui:text key="GROUP_VISIBILITY_MOREDESC"/>
<p>
<% if (isPublic) { %>
    <ui:radiobutton beanId="groupVisibility" value="PUBLIC" selected="true"/>
    <ui:text value="PUBLIC"/>
    <ui:radiobutton beanId="groupVisibility" value="PRIVATE"/>
    <ui:text value="PRIVATE"/>
<% } else { %>
    <ui:radiobutton beanId="groupVisibility" value="PUBLIC"/>
    <ui:text value="PUBLIC"/>
    <ui:radiobutton beanId="groupVisibility" value="PRIVATE" selected="true"/>
    <ui:text value="PRIVATE"/>
<% } %>

<h3><ui:text key="GROUP_SELECT_PORTLETS" style="nostyle"/></h3>

<ui:text key="GROUP_SELECT_MSG"/>
<p>
<ui:panel beanId="panel"/>

<ui:actionsubmit action="doMakeGroup" key="SAVE"/>
<ui:actionsubmit action="doViewListGroup" key="CANCEL"/>

</ui:form>
