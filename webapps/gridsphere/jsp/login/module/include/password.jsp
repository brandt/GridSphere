<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>


<ui:frame>
    <ui:tablerow>
        <ui:tablecell width="5%">
            <ui:checkbox beanId="passCheck"
                         name="passwordModule"
                         value="Hello"
                         disabled="<%= disabled %>"
                         selected="<%= active %>"/>
        </ui:tablecell>
        <ui:tablecell width="45%">
            <ui:text key="LOGIN_PASS_MODULE"/>
        </ui:tablecell>
        <ui:tablecell width="50%"/>
    </ui:tablerow>
</ui:frame>