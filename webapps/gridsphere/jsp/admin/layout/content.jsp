<%@ page import="org.gridsphere.layout.PortletContent" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="controlUI" class="java.lang.String" scope="request"/>

<% PortletContent content = (PortletContent) request.getAttribute("portletComp"); %>

<ui:text var="msg" key="LAYOUT_EDIT_CONTENT"/>
<% String label = msg + " <b>" + content.getFileName() + "</b>"; %>

<ui:group label="<%= label%>">

    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_SEL_CONTENT"/>
                <ui:listbox beanId="contentLB"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_SEL_ROLES"/>
                <ui:listbox beanId="rolesLB"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_SEL_BOOKMARKING"/>
                <ui:textfield beanId="labelTF" value="<%= content.getLabel() %>"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:table>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doSaveContent" key="SAVE"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="doDeleteContent" key="DELETE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>

</ui:group>
