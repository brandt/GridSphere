<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<jsp:useBean id="theme" class="java.lang.String" scope="request"/>

<portletAPI:init/>

We're hoping to provide more layout customization-- this is a simple example to demonstrate how a users tabs
can be edited. Changes to a users layout could be saved, but are currently not after logout.

<p>

Current theme: <%= theme %>

</p>
<p>
Current top-level tabs:
</p>
<ui:panel>
<ui:frame>
    <ui:tablerow beanId="tabsRow"/>
</ui:frame>

</ui:panel>

Click <ui:actionlink portletMode="edit" value="EDIT"/> mode to make changes

