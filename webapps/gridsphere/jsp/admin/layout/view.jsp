<%@ page import="java.util.Iterator, java.util.List "%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<% List groupNames = (List)request.getAttribute("groupNames"); %>
<% List groupDescs = (List)request.getAttribute("groupDescs"); %>

<portletAPI:init/>
<ui:form>

<h3>Edit Banner</h3>
<ui:text value="Below you can edit the GridSphere banner HTML"/>
<p>
<ui:textarea beanId="bannerTA" rows="3" cols="80"/>
<p>
<ui:actionsubmit action="saveBanner" key="SAVE"/>


<h3>Guest Layout</h3>

<b><ui:actionlink action="editGuestLayout" value="Edit guest layout"/></b>

<h3>Group Layouts</h3>


<ui:text value="The following group layouts exist:"/>

<ui:frame>
<ui:tablerow header="true">
    <ui:tablecell><ui:text value="Group name"/></ui:tablecell>
    <ui:tablecell><ui:text value="Group description"/></ui:tablecell>
    <ui:tablecell><ui:text value="Edit layout"/></ui:tablecell>
    <ui:tablecell><ui:text value="Delete layout"/></ui:tablecell>
</ui:tablerow>
<p>

<%  Iterator it = groupNames.iterator();
    Iterator cit = groupDescs.iterator();
    while (it.hasNext() && cit.hasNext()) {
    String group = (String)it.next();
    String groupDesc = (String)cit.next();
%>
<ui:tablerow>
<ui:tablecell>
<ui:text value="<%= group %>"/>
</ui:tablecell>
<ui:tablecell>
    <ui:text value="<%= groupDesc %>"/>
</ui:tablecell>
<ui:tablecell>
<ui:actionlink action="editGroupLayout" value="Edit">
    <ui:actionparam name="group" value="<%= group %>"/>
</ui:actionlink>
</ui:tablecell>
<ui:tablecell>
<ui:actionlink action="deleteLayout" value="Delete">
    <ui:actionparam name="group" value="<%= group %>"/>
</ui:actionlink>
</ui:tablecell>
</ui:tablerow>
<% } %>

</ui:frame>

</ui:form>
