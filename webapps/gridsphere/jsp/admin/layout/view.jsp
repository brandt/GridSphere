<%@ page import="java.util.Iterator, java.util.List "%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<% List tabNames = (List)request.getAttribute("tabNames"); %>
<% List groupNames = (List)request.getAttribute("groupNames"); %>

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

<ui:text value="The following predefined layouts exist:"/>

<p>

<% Iterator it = tabNames.iterator();
    while (it.hasNext()) {
    String group = (String)it.next();
%>

<ui:text value="<%= group %>"/>
<ui:actionlink action="editGroupLayout" value="Edit">
    <ui:actionparam name="group" value="<%= group %>"/>
</ui:actionlink>
<p>

<% } %>

<ui:text value="The following group layouts exist:"/>

<p>

<%  it = groupNames.iterator();
    while (it.hasNext()) {
    String group = (String)it.next();
%>

<ui:text value="<%= group %>"/>
<ui:actionlink action="editGroupLayout" value="Edit">
    <ui:actionparam name="group" value="<%= group %>"/>
</ui:actionlink>
<ui:actionlink action="deleteLayout" value="Delete">
    <ui:actionparam name="group" value="<%= group %>"/>
</ui:actionlink>

<p>

<% } %>
</ui:form>
