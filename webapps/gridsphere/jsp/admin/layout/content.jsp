<%@ page import="org.gridsphere.layout.PortletContent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="controlUI"  class="java.lang.String" scope="request"/>

<% PortletContent content = (PortletContent)request.getAttribute("portletComp"); %>

<% String label = "Edit content: <b>" + content.getFileName() + "</b>"; %>

<ui:group label="<%= label%>">

<ui:table>
<ui:tablerow>
<ui:tablecell>
    Select content: <ui:listbox beanId="contentLB"/>
 </ui:tablecell>
</ui:tablerow>

<ui:tablerow>
<ui:tablecell>
Select required role: <ui:listbox beanId="rolesLB"/>
</ui:tablecell>
</ui:tablerow>

<ui:tablerow>
<ui:tablecell>
Choose a label for bookmarking: <ui:textfield beanId="labelTF" value="<%= content.getLabel() %>"/>
</ui:tablecell>
</ui:tablerow>

</ui:table>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doSaveContent" value="Save"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:actionsubmit action="doDeleteContent" value="Delete"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>

</ui:group>
