<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<% List authModules = (List)request.getAttribute("authModules"); %>

<portletAPI:init/>

<ui:form>
<ui:group>
<ui:text style="bold" key="LOGIN_CONFIG_MSG"/>
<p>
<ui:checkbox beanId="acctCB" value="TRUE"/>
<ui:text key="LOGIN_CONFIG_ALLOW"/>
<p>
<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="setUserCreateAccount" key="APPLY"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>
</ui:group>
</ui:form>

<ui:form>
<ui:group>
<ui:text style="bold" key="MAIL_CONFIG_MSG"/>
<p>
<ui:text key="MAIL_SERVER_MSG"/>
<p>
<ui:textfield beanId="mailHostTF"/>
<p>
<ui:text key="MAIL_FROM_MSG"/>
<p>
<ui:textfield beanId="mailFromTF"/>


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
<ui:text style="bold" key="LOGIN_AUTHMODULES_MSG"/>
<p>

<ui:frame>
    <ui:tablerow header="true" zebra="true">
        <ui:tablecell>
            <ui:text key="LOGIN_MODULE_NAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text key="LOGIN_MODULE_DESC"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text key="LOGIN_MODULE_ISACTIVE"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text key="LOGIN_MODULE_PRIORITY"/>
        </ui:tablecell>
    </ui:tablerow>

<% Iterator it = authModules.iterator(); %>
<% while (it.hasNext()) { 
       LoginAuthModule authModule = (LoginAuthModule)it.next(); %>

    <ui:tablerow>
        <ui:tablecell>
            <ui:text value="<%= authModule.getModuleName() %>"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="<%= authModule.getModuleDescription() %>"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:checkbox beanId="authModCB" selected="<%= authModule.isModuleActive() %>" value="<%= authModule.getModuleName() %>"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield name="<%= authModule.getModuleName() %>" value="<%= String.valueOf(authModule.getModulePriority()) %>"/>
        </ui:tablecell>
    </ui:tablerow>

<% } %>

</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="saveAuthModules" key="APPLY"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

</ui:group>
</ui:form>




