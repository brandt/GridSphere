<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

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





