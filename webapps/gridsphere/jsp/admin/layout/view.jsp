<%@ page import="java.util.Iterator,
                 java.util.Map"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<% Map groupNames = (Map)request.getAttribute("groupNames"); %>

<portletAPI:init/>

<ui:messagebox beanId="msg"/>

<ui:form>

<ui:hasrole role="super">

<h3><ui:text key="LAYOUTMGR_EDIT_BANNER" style="nostyle"/></h3>
<ui:text key="LAYOUTMGR_EDIT_MSG"/>
<p>
<ui:textarea beanId="bannerTA" rows="3" cols="80"/>
<p>
<ui:actionsubmit action="saveBanner" key="SAVE"/>

<h3><ui:text key="LAYOUTMGR_EDIT_THEME" style="nostyle"/></h3>
<ui:text key="LAYOUTMGR_THEME_MSG"/>&nbsp;

<ui:listbox beanId="themesLB"/>

<ui:actionsubmit action="saveDefaultTheme" key="SAVE"/>

<h3><ui:text key="LAYOUTMGR_GUEST" style="nostyle"/></h3>

<b><ui:actionlink action="editGuestLayout" key="LAYOUTMGR_EDIT_GUEST"/></b>

</ui:hasrole>


<h3><ui:text key="LAYOUTMGR_GROUPS" style="nostyle"/></h3>

<ui:text key="LAYOUTMGR_GROUP_MSG"/>

<ui:frame>
<ui:tablerow header="true">
    <ui:tablecell><ui:text key="GROUP_NAME"/></ui:tablecell>
    <ui:tablecell><ui:text key="GROUP_DESCRIPTION"/></ui:tablecell>
    <ui:tablecell><ui:text key="LAYOUTMGR_GROUP_EDIT"/></ui:tablecell>
    <ui:tablecell><ui:text key="LAYOUTMGR_GROUP_DELETE"/></ui:tablecell>
</ui:tablerow>
<p>

<%  Iterator it = groupNames.keySet().iterator();
    while (it.hasNext()) {
    String group = (String)it.next();
    String groupDesc = (String)groupNames.get(group);
%>

<ui:hasrole role="admin" group="<%= group %>">

<ui:tablerow>
<ui:tablecell>
<ui:text value="<%= group %>"/>
</ui:tablecell>
<ui:tablecell>
    <ui:text value="<%= groupDesc %>"/>
</ui:tablecell>



<ui:tablecell>
<% if (group.equals("gridsphere")) { %>
<ui:hasrole role="super">
<ui:actionlink action="editGroupLayout" key="EDIT">
    <ui:actionparam name="group" value="<%= group %>"/>
</ui:actionlink>
</ui:hasrole>
<% } else { %>
<ui:actionlink action="editGroupLayout" key="EDIT">
    <ui:actionparam name="group" value="<%= group %>"/>
</ui:actionlink>
<% } %>
</ui:tablecell>
<ui:tablecell>
<% if (!group.equals("gridsphere")) { %>
<ui:actionlink action="deleteLayout" key="DELETE">
    <ui:actionparam name="group" value="<%= group %>"/>
</ui:actionlink>
<% } %>
</ui:tablecell>
</ui:tablerow>

</ui:hasrole>

<% } %>
</ui:frame>

</ui:form>
