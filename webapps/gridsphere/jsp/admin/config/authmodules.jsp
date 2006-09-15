<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridsphere.services.core.security.auth.modules.LoginAuthModule" %>
<%@ page import="javax.portlet.RenderRequest"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>
<jsp:useBean id="services" class="java.lang.String" scope="request"/>

<% RenderRequest pReq = (RenderRequest) pageContext.getAttribute("renderRequest"); %>
<% List authModules = (List) request.getAttribute("authModules"); %>

<ui:form>
    <ui:group key="LOGIN_AUTHMODULES_MSG">
        <% if (authModules.size() == 1) { %>
        <ui:messagebox key="LOGIN_AUTHMODULES_ONEREQ"/>
        <% } %>

        <ui:frame>
            <ui:tablerow header="true" zebra="true">
                <ui:tablecell>
                    <ui:text key="LOGIN_MODULE_NAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text key="LOGIN_MODULE_ISACTIVE"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text key="LOGIN_MODULE_PRIORITY"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text key="LOGIN_MODULE_DESC"/>
                </ui:tablecell>
            </ui:tablerow>

            <% Iterator it = authModules.iterator(); %>
            <% while (it.hasNext()) {
                LoginAuthModule authModule = (LoginAuthModule) it.next(); %>

            <ui:tablerow>
                <ui:tablecell>
                    <ui:text value="<%= authModule.getModuleName() %>"/>
                </ui:tablecell>
                <% if (authModules.size() == 1) { %>
                <ui:tablecell>
                    <ui:checkbox beanId="authModCB" disabled="true" selected="true"
                                 value="<%= authModule.getModuleName() %>"/>
                </ui:tablecell>
                <% } else { %>
                <ui:tablecell>
                    <ui:checkbox beanId="authModCB" selected="<%= authModule.isModuleActive() %>"
                                 value="<%= authModule.getModuleName() %>"/>
                </ui:tablecell>
                <% } %>
                <ui:tablecell>
                    <ui:textfield name="<%= authModule.getModuleName() %>"
                                  value="<%= String.valueOf(authModule.getModulePriority()) %>" size="3"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%= authModule.getModuleDescription(pReq.getLocale()) %>"/>
                </ui:tablecell>
            </ui:tablerow>

            <% } %>

        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doSaveAuthModules" key="SAVE"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

    </ui:group>
</ui:form>


