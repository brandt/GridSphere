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
    <ui:tablerow>
        <ui:tablecell>
            <ui:text value="Portlet web application"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="Description"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="Sessions"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="Running"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="Actions"/>
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
        <ui:text value="  GridSphere core portlets cannot be redeployed  "/>
   <% } else { %>
        <ui:actionlink action="doPortletManager" value="  start  ">
            <ui:actionparam name="operation" value="start"/>
            <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
        </ui:actionlink>
        <ui:actionlink action="doPortletManager" value="  stop  ">
            <ui:actionparam name="operation" value="stop"/>
            <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
        </ui:actionlink>
        <ui:actionlink action="doPortletManager" value="  reload  ">
            <ui:actionparam name="operation" value="reload"/>
            <ui:actionparam name="context" value="<%= description.getContextPath() %>"/>
        </ui:actionlink>
        <ui:actionlink action="doPortletManager" value="  remove  ">
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
            <ui:tablecell width="100">
                <ui:text value="Deploy new portlet webapp"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text value="Enter webapp name: "/>
                <ui:textfield beanId="webappNameTF" size="20" maxlength="20"/>
                <ui:actionsubmit action="deployWebapp" value="Deploy"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
</ui:form>

<ui:fileform>
<ui:panel>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text value="File: "/>
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
                    <ui:actionsubmit action="uploadPortletWAR" value="Upload Portlet WAR"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
 </ui:panel>
 </ui:fileform>