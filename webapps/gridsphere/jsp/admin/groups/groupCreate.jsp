<%@ page import="org.gridsphere.services.core.security.group.PortletGroup"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>


<h2><ui:text key="GROUP_WIZARD_1"/></h2>
<ui:form>

    <ui:group>

        <% PortletGroup.Type gtype = (PortletGroup.Type) request.getAttribute("groupType"); %>
        <% if (gtype == null) gtype = PortletGroup.Type.PUBLIC; %>

        <ui:messagebox beanId="msg"/>
        <p>
            <ui:hiddenfield beanId="groupName"/>
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
        </p>

        <p>
            <ui:radiobutton beanId="groupVisibility" value="PUBLIC"
                            selected="<%= (gtype.equals(PortletGroup.Type.PUBLIC)) %>"/>
            <ui:text key="GROUP_PUBLIC"/>
            <ui:radiobutton beanId="groupVisibility" value="PRIVATE"
                            selected="<%= (gtype.equals(PortletGroup.Type.PRIVATE)) %>"/>
            <ui:text key="GROUP_PRIVATE"/>
            <ui:radiobutton beanId="groupVisibility" value="HIDDEN"
                            selected="<%= (gtype.equals(PortletGroup.Type.HIDDEN)) %>"/>
            <ui:text key="GROUP_HIDDEN"/>
        </p>

        <h3><ui:text key="GROUP_SELECT_PORTLETS" style="nostyle"/></h3>

        <p>
            <ui:text key="GROUP_SELECT_MSG"/>
            </p>
        <% if (request.getAttribute("newgroup") != null) { %>
            <p><ui:text key="GROUP_NEWLAYOUT_MSG"/></p>
        <% } %>
            <ui:panel beanId="panel"/>
            <ui:actionsubmit action="doMakeGroup" key="SAVE"/>
            <ui:actionsubmit action="doViewListGroup" key="CANCEL"/>
    </ui:group>
</ui:form>