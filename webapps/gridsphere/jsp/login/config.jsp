<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>

<ui:text style="bold" value="Login configuration options"/>
<p>
<ui:checkbox beanId="acctCB" value="TRUE"/>
<ui:text value="Allow users to create new accounts on the portal?"/>
<p>
<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="setUserCreateAccount" value="Apply Changes"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>
</ui:form>