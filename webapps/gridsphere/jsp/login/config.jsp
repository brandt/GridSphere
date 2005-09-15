<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule,
                 org.gridlab.gridsphere.portlet.PortletRequest"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% PortletRequest pReq = (PortletRequest)pageContext.getAttribute("portletRequest"); %>
<% List authModules = (List)request.getAttribute("authModules"); %>

<portletAPI:init/>

<ui:form>
<ui:group>
<p>
<ui:text style="bold" key="LOGIN_CONFIG_MSG"/>
</p>
<p>
<ui:checkbox beanId="acctCB" value="TRUE"/>
<ui:text key="LOGIN_CONFIG_ALLOW"/>
</p>
<p>
<ui:checkbox beanId="notifyCB" value="TRUE"/>
<ui:text key="LOGIN_CONFIG_NOTIFY"/>
</p>
<p>
<ui:checkbox beanId="savepassCB" value="TRUE"/>
<ui:text key="LOGIN_CONFIG_PASSWD"/>
<br/>
<ui:text style="alert" key="LOGIN_CONFIG_PASSWD1"/>
<br/>
<ui:text style="alert" key="LOGIN_CONFIG_PASSWD2"/>
</p>
<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="setLoginSettings" key="APPLY"/>
</ui:tablecell>
</ui:tablerow>

</ui:frame>
</ui:group>
</ui:form>

<ui:form>
<ui:group>
<p>
<ui:text style="bold" key="MAIL_CONFIG_MSG"/>
<ui:text key="MAIL_SERVER_MSG"/>
</p>
<p>
<ui:textfield beanId="mailHostTF"/>
</p>
<p>
<ui:text key="MAIL_FROM_MSG"/>
</p>
<p>
<ui:textfield beanId="mailFromTF"/>
</p>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="configMailSettings" key="APPLY"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>
</ui:group>
</ui:form>

<ui:form>
<ui:group>
<p>
<ui:text style="bold" key="LOGIN_AUTHMODULES_MSG"/>
</p>
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
       LoginAuthModule authModule = (LoginAuthModule)it.next(); %>

    <ui:tablerow>
        <ui:tablecell>
            <ui:text value="<%= authModule.getModuleName() %>"/>
        </ui:tablecell>
        <% if (authModules.size() == 1) { %>
        <ui:tablecell>
            <ui:checkbox beanId="authModCB" disabled="true" selected="true" value="<%= authModule.getModuleName() %>"/>
        </ui:tablecell>
        <% } else { %>
        <ui:tablecell>
            <ui:checkbox beanId="authModCB" selected="<%= authModule.isModuleActive() %>" value="<%= authModule.getModuleName() %>"/>
        </ui:tablecell>
        <% } %>
        <ui:tablecell>
            <ui:textfield name="<%= authModule.getModuleName() %>" value="<%= String.valueOf(authModule.getModulePriority()) %>" size="3"/>
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
<ui:actionsubmit action="doSaveAuthModules" key="APPLY"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

</ui:group>
</ui:form>




