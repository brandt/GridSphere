<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.portlets.core.tomcat.TomcatWebAppResult,
                 org.gridlab.gridsphere.portlets.core.tomcat.TomcatWebAppDescription" %>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="result" class="List" scope="request"/>

<ui:panel>

    <ui:frame beanId="errorFrame"/>

    <ui:frame>
    <ui:tablerow>
        <ui:tablecell width="10">
            <ui:text value="Applications"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <ui:text value="Paths"/>
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
    <ui:tablecell><ui:text value="<%= description.getRunning() %>"/></ui:tablecell>
    <ui:tablecell><ui:text value="<%= description.getSessions() %>"/></ui:tablecell>
    <ui:tablecell>
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
    </ui:tablecell>
    </ui:tablerow>

<% } %>

</ui:frame>


</ui:panel>