<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<%-- This is an ugly logic JSP page that iterates over the authorization modules --%>

<ui:text key="LOGIN_CONF_AUTH_MODULES"/>

<% List supportedModules = (List)request.getAttribute("supportedModules"); %>
<% List activeModules = (List)request.getAttribute("activeModules"); %>

<ui:panel>

<ui:form>

<% boolean disabled = false; %>

<%
    Iterator it = supportedModules.iterator();
    if (supportedModules.size() == 1) {
        disabled = true;
    }
    while (it.hasNext()) {
        LoginAuthModule loginAuthModule = (LoginAuthModule)it.next();
        String authModuleName = loginAuthModule.getModuleName();
        System.err.println("auth mod name: " + authModuleName);
        // active boolean is available to individual module pages
        boolean active = false;

        if (activeModules.contains(loginAuthModule)) active = true;
        // Have to match to what we know
        if (authModuleName.equals("PASSWORD_AUTH_MODULE")) { %>

        <%@ include file="/jsp/login/module/include/password.jsp" %>

        <%      }

                if (authModuleName.equals("LDAP_AUTH_MODULE")) { %>

        <%@ include file="/jsp/login/module/include/ldap.jsp" %>

        <%      }

                if (authModuleName.equals("MYPROXY_AUTH_MODULE")) { %>

        <%@ include file="/jsp/login/module/include/myproxy.jsp" %>

        <%      }
   } %>

<ui:frame>
    <ui:tablerow>
        <ui:tablecell width="100">
            <ui:actionsubmit action="configAuthModules" name="apply" key="LOGIN_CONFIG_ACTION"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:frame>
</ui:form>

</ui:panel>
