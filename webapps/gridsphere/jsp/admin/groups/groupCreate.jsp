<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.PortletGroup"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% boolean isPublic = ((request.getAttribute("isPrivate") != null) ? false : true); %>

<ui:form>

<h3>Group information</h3>
<ui:text value="Enter group name:"/>&nbsp;&nbsp;<ui:textfield beanId="groupNameTF"/>
<p>
<ui:text value="Enter a brief description of group:"/>&nbsp;&nbsp;<ui:textfield beanId="groupDescTF"/>
</p>

<h3>Group visibility</h3>

<ui:text value="Select if group should be public or private. Anyone can add themselves to a public group, while private groups require authorization"/>
<p>
<% if (isPublic) { %>
    <ui:radiobutton name="groupVisibility" value="PUBLIC" selected="true"/>
    <ui:text value="PUBLIC"/>
    <ui:radiobutton name="groupVisibility" value="PRIVATE"/>
    <ui:text value="PRIVATE"/>
<% } else { %>
    <ui:radiobutton name="groupVisibility" value="PUBLIC"/>
    <ui:text value="PUBLIC"/>
    <ui:radiobutton name="groupVisibility" value="PRIVATE" selected="true"/>
    <ui:text value="PRIVATE"/>
<% } %>

<h3>Select portlets</h3>

<ui:text value="Select portlets that will be made available to the group. Users in this group will have the chance to
add these portlets to their layout. In addition, required roles may be associated with the portlets"/>
<p>
<ui:panel beanId="panel"/>


<ui:actionsubmit action="doMakeGroup" value="Save"/>
<ui:actionsubmit action="doViewListGroup" value="Cancel"/>

</ui:form>
