<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule"%>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
<ui:panel>

<ui:frame beanId="msgFrame"/>

<ui:text key="PROFILE_CONFIG_MSG"/>
<ui:frame>
    <ui:tablerow>
        <ui:tablecell><ui:text key="PROFILE_LOCALES"/>&nbsp;<ui:textfield beanId="localesTF"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>


<ui:frame>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doSaveLocales" key="PROFILE_SAVE"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:frame>

</ui:panel>

</ui:form>


<%@ include file="/jsp/login/configure.jsp" %>

