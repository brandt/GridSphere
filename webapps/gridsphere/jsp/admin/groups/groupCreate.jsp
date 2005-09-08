<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.PortletGroup"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>


<h2><ui:text key="GROUP_WIZARD_1"/></h2>
<ui:form>

<ui:group>

<% PortletGroup.Type gtype = (PortletGroup.Type)request.getAttribute("groupType"); %>
<% if (gtype == null) gtype = PortletGroup.PUBLIC; %>

<ui:messagebox beanId="msg"/>
<p>
<ui:hiddenfield beanId="groupId"/>
</p>
<h3><ui:text key="GROUP_INFO" style="nostyle"/></h3>
<p>
<ui:text key="GROUP_CREATE_NAME"/>&nbsp;&nbsp;<ui:textfield beanId="groupNameTF"/>
<ui:text key="GROUP_DESC_MSG"/>&nbsp;&nbsp;<ui:textfield beanId="groupDescTF"/>
</p>

<h3><ui:text key="GROUP_VISIBILITY_MSG" style="nostyle"/></h3>

<p>
<ui:text key="GROUP_VISIBILITY_DESC"/>
<ui:text key="GROUP_VISIBILITY_MOREDESC"/>

<ui:radiobutton beanId="groupVisibility" value="PUBLIC" selected="<%= (gtype.equals(PortletGroup.PUBLIC)) ? true : false %>"/>
    <ui:text key="GROUP_PUBLIC"/>
    <ui:radiobutton beanId="groupVisibility" value="PRIVATE" selected="<%= (gtype.equals(PortletGroup.PRIVATE)) ? true : false %>"/>
    <ui:text key="GROUP_PRIVATE"/>
    <ui:radiobutton beanId="groupVisibility" value="HIDDEN" selected="<%= (gtype.equals(PortletGroup.HIDDEN)) ? true : false %>"/>
    <ui:text key="GROUP_HIDDEN"/>
</p>
<h3><ui:text key="GROUP_SELECT_PORTLETS" style="nostyle"/></h3>

<p>
<ui:text key="GROUP_SELECT_MSG"/>
<ui:panel beanId="panel"/>
<ui:actionsubmit action="doMakeGroup" key="SAVE"/>
<ui:actionsubmit action="doViewListGroup" key="CANCEL"/>
</p>
</ui:group>
</ui:form>