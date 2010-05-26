<%@ page import="org.gridsphere.portletcontainer.PortletStatus,
                 org.gridsphere.services.core.tomcat.TomcatWebAppDescription" %>
<%@ page import="java.util.List" %>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<ui:group key="PORTLET_CURRENT">

    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text key="PORTLET_VIEW_DETAILS"/>
            </ui:tablecell>
            <ui:tablecell width="250">
                <ui:text key="PORTLET_DESC"/>
            </ui:tablecell>
            <ui:tablecell width="100">
                <ui:text key="PORTLET_RUNNING"/>
            </ui:tablecell>
            <ui:tablecell width="80" align="center">
                <ui:text key="PORTLET_SESSIONS"/>
            </ui:tablecell>
            <ui:tablecell width="250">
                <ui:text key="PORTLET_ACTIONS"/>
            </ui:tablecell>
            <ui:tablecell width="100">
                <ui:text key="PORTLET_STATUS"/>
            </ui:tablecell>
        </ui:tablerow>

        <% List result = (List) request.getAttribute("result"); %>
        <% for (int i = 0; i < result.size(); i++) { %>
        <% TomcatWebAppDescription description = (TomcatWebAppDescription) result.get(i); %>

        <ui:tablerow>
            <ui:tablecell>
                <ui:actionlink action="displayWebapp" value="<%= description.getContextPath() %>">
                    <ui:actionparam name="appname" value="<%= description.getContextPath() %>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= description.getDescription() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= description.getRunning() %>"/>
            </ui:tablecell>
            <ui:tablecell align="center">
                <ui:text value="<%= description.getSessions() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <% if (request.getContextPath().equalsIgnoreCase("/" + description.getContextPath())) { %>
                <ui:text key="PORTLET_GS_MSG"/>
                <% } else { %>
                <% if (description.getRunningState() == TomcatWebAppDescription.STOPPED) { %>
                &nbsp;&nbsp;
                <ui:actionlink action="doPortletManager" key="PORTLET_START">
                    <ui:actionparam name="operation" value="start"/>
                    <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
                </ui:actionlink>
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                <ui:text key="PORTLET_STOP"/>
                &nbsp;&nbsp;
                <% } else { %>
                &nbsp;&nbsp;
                <ui:text key="PORTLET_START"/>
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                <ui:actionlink action="doPortletManager" key="PORTLET_STOP">
                    <ui:actionparam name="operation" value="stop"/>
                    <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
                </ui:actionlink>
                &nbsp;&nbsp;
                <% } %>
                &nbsp;&nbsp;
                <ui:actionlink action="doPortletManager" key="PORTLET_RELOAD">
                    <ui:actionparam name="operation" value="reload"/>
                    <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
                </ui:actionlink>
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                <ui:actionlink action="doPortletManager" key="PORTLET_REMOVE">&nbsp;&nbsp;
                    <ui:actionparam name="operation" value="remove"/>
                    <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
                </ui:actionlink>
                <% } %>
            </ui:tablecell>
            <ui:tablecell align="center">
                <% if (description.getStatus().equals(PortletStatus.SUCCESS)) { %>
                <ui:image
                        src="<%= request.getContextPath() + \"/themes/brush/default/images/msgicons/portlet-msg-success.gif\" %>"/>
                <ui:text key="SUCCESS"/>
                <% } else { %>
                <ui:image
                        src="<%= request.getContextPath() + \"/themes/brush/default/images/msgicons/portlet-msg-error.gif\" %>"/>
                <ui:text style="underline" id="<%= description.getContextPath() %>" key="FAILURE"/>
                <ui:tooltip name="<%= description.getContextPath() %>"
                            id="<%= description.getContextPath() + \"tooltip\" %>"
                            value="<%= description.getStatusMessage() %>"/>
                <% } %>
            </ui:tablecell>
        </ui:tablerow>

        <% } %>

    </ui:frame>
</ui:group>

<%-- @TODO do we really need to show non-portlet webapps here?

<ui:group key="PORTLET_NON">

    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text key="PORTLET_NONWEBAPP"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="PORTLET_DESC"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="PORTLET_RUNNING"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="PORTLET_SESSIONS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="PORTLET_ACTIONS"/>
            </ui:tablecell>
        </ui:tablerow>

        <% List results = (List) request.getAttribute("others"); %>
        <% Iterator its = results.iterator(); %>
        <% while (its.hasNext()) { %>
        <% TomcatWebAppDescription description = (TomcatWebAppDescription) its.next(); %>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="<%= description.getContextPath() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= description.getDescription() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= description.getRunning() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= description.getSessions() %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <% if (description.getRunningState() == TomcatWebAppDescription.STOPPED) { %>
                &nbsp;&nbsp;<ui:actionlink action="doPortletManager" key="PORTLET_START">
                <ui:actionparam name="operation" value="start"/>
                <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
            </ui:actionlink>&nbsp;&nbsp;
                &nbsp;&nbsp;<ui:text key="PORTLET_STOP"/>&nbsp;&nbsp;
                <% } else { %>
                &nbsp;&nbsp;<ui:text key="PORTLET_START"/>&nbsp;&nbsp;
                &nbsp;&nbsp;<ui:actionlink action="doPortletManager" key="PORTLET_STOP">
                <ui:actionparam name="operation" value="stop"/>
                <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
            </ui:actionlink>&nbsp;&nbsp;
                <% } %>
                &nbsp;&nbsp;<ui:actionlink action="doPortletManager" key="PORTLET_RELOAD">
                <ui:actionparam name="operation" value="reload"/>
                <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
            </ui:actionlink>&nbsp;&nbsp;
                &nbsp;&nbsp;<ui:actionlink action="doPortletManager" key="PORTLET_REMOVE">&nbsp;&nbsp;
                <ui:actionparam name="operation" value="remove"/>
                <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
            </ui:actionlink>
            </ui:tablecell>
        </ui:tablerow>

        <% } %>

    </ui:frame>
</ui:group>

--%>

<%-- @TODO@ This doesn't quite work either
<ui:group key="PORTLET_DEPLOY_MSG">

    <ui:form>

                    <h3></h3><ui:text key="PORTLET_WEBAPP_MSG"/>&nbsp; </h3>
                    <ui:textfield beanId="webappNameTF" size="20" maxlength="20"/>
        <br>
                    <ui:actionsubmit action="deployWebapp" key="PORTLET_DEPLOY"/>

    </ui:form>
</ui:group>
--%>

<%-- uploading portlet WAR files TBD


<ui:group key="PORTLET_UPLOAD">
    <ui:fileform action="uploadPortletWAR">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="50">
                    <ui:text key="PORTLET_FILE"/>&nbsp;
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:fileinput beanId="userfile" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="uploadPortletWAR" key="PORTLET_UPLOAD"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:fileform>
</ui:group>

 --%>
