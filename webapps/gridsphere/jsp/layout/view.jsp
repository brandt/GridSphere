<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

We're hoping to provide more layout customization-- this is a simple example to demonstrate how a users tabs
can be edited. Changes to a users layout could be saved, but are currently not after logout.
<ui:panel>

Current top-level tabs:
<ui:frame>
    <ui:tablerow beanId="tabsRow"/>
</ui:frame>

</ui:panel>

Click <ui:actionlink portletMode="edit" value="EDIT"/> mode to make changes

