<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
<ui:panel>

<ui:frame beanId="msgFrame"/>

Configure supported locales by entering two letter lower-case ISO language codes in the comma separated list:
<ui:frame>
    <ui:tablerow>
        <ui:tablecell><ui:text value="Supported locales: "/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="localesTF"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>


<ui:frame>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doSaveLocales" value="Save Changes"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:frame>

</ui:panel>

</ui:form>



