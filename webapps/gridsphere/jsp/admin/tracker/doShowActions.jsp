<%@ page import="java.util.Iterator" %>
<%@ page import="org.gridsphere.services.core.tracker.impl.TrackerAction" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="label" class="java.lang.String" scope="request"/>
<jsp:useBean id="trackerActionList" class="java.util.ArrayList" scope="request"/>

<% if (!trackerActionList.isEmpty()) { %>

<h3><ui:text key="TRACKING_DISPLAY_ACTION" style="nostyle"/></h3>

<ui:form>
    <ui:table sortable="true" zebra="true" maxrows="25">
        <ui:tablerow header="true">

            <ui:tablecell><ui:text key="TRACKING_ACTION"/></ui:tablecell>
            <ui:tablecell><ui:text key="TRACKING_ENABLED"/></ui:tablecell>
            <ui:tablecell><ui:text key="TRACKING_DELETE"/></ui:tablecell>
        </ui:tablerow>
        <%
            Iterator it = trackerActionList.iterator();
            while (it.hasNext()) {
                TrackerAction action = (TrackerAction) it.next();
        %>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="<%= action.getAction() %>" style="plain"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:checkbox beanId="enabledCB" selected="<%= action.isEnabled() %>" value="<%= action.getAction() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="doDeleteAction" key="DELETE">
                    <ui:actionparam name="actionName" value="<%= action.getAction() %>"/>
                </ui:actionsubmit>
            </ui:tablecell>
        </ui:tablerow>
        <%
            }
        %>
    </ui:table>
    <p>
        <ui:actionsubmit action="doModifyAction" key="SAVE"/>
    </p>
</ui:form>

<% } %>

<h3><ui:text key="TRACKING_CREATE_ACTION" style="nostyle"/></h3>
<ui:form>
    <p>
        <ui:text key="TRACKING_CREATE_MSG"/>&nbsp;&nbsp;<ui:textfield beanId="createActionTF"/>
        <ui:actionsubmit action="doSaveAction" key="SAVE"/>
    </p>
</ui:form>
<ui:actionlink action="doViewLabels" key="TRACKING_DISPLAY_ACTION"/>



