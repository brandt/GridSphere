<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat.TomcatWebAppResult,
                 org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat.TomcatWebAppDescription,
                 org.gridlab.gridsphere.portlet.PortletGroupFactory" %>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="result" class="List" scope="request"/>

<ui:panel>

    <ui:frame beanId="errorFrame"/>

    <ui:frame>
    <ui:tablerow header="true">
        <ui:tablecell>
            <ui:text key="PORTLET_WEBAPP"/>
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

<% Iterator it = result.iterator(); %>
<% while (it.hasNext()) { %>
<% TomcatWebAppDescription description = (TomcatWebAppDescription)it.next(); %>

    <ui:tablerow>
    <ui:tablecell><ui:text value="<%= description.getContextPath() %>"/></ui:tablecell>
    <ui:tablecell><ui:text value="<%= description.getDescription() %>"/></ui:tablecell>
    <ui:tablecell><ui:text value="<%= description.getRunning() %>"/></ui:tablecell>
    <ui:tablecell><ui:text value="<%= description.getSessions() %>"/></ui:tablecell>
    <ui:tablecell>
    <% if (PortletGroupFactory.GRIDSPHERE_GROUP.getName().equalsIgnoreCase(description.getContextPath())) { %>
        <ui:text key="PORTLET_GS_MSG"/>
   <% } else { %>
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
    <% } %>
    </ui:tablecell>
    </ui:tablerow>

    <% } %>

</ui:frame>

</ui:panel>

<ui:form>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="PORTLET_CONFIG_PORT"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="PORTLET_CONFIG_MSG"/>
                <ui:textfield beanId="tomcatPortTF"/>
                <ui:actionsubmit action="configPort" key="SAVE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
</ui:form>

<ui:form>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="PORTLET_DEPLOY_MSG"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="PORTLET_WEBAPP_MSG"/>&nbsp;
                <ui:textfield beanId="webappNameTF" size="20" maxlength="20"/>
                <ui:actionsubmit action="deployWebapp" key="PORTLET_DEPLOY"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
</ui:form>

<ui:fileform action="uploadPortletWAR">
<ui:panel>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
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
 </ui:panel>
 </ui:fileform>