<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>


<portletAPI:init/>


This portlet lets you send messages to other users which have specified the appropriate
values in the <ui:actionlink label="profile" value="Profile Portlet"/>
for the available services.
Please choose a user and a service and type in the message you want to send to that user.

<ui:form>
    <ui:actionsubmit action="cancelEdit" key="MESSAGING_OK"/>
</ui:form>
