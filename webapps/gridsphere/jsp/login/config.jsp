<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>

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
</ui:form>