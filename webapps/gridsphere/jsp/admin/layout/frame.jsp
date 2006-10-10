<%@ page import="org.gridsphere.layout.PortletFrame"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="controlUI"  class="java.lang.String" scope="request"/>

<% PortletFrame frame = (PortletFrame)request.getAttribute("portletComp"); %>

<% String label = "Edit portlet frame: <b>" + frame.getPortletName() + "</b>"; %>

<ui:group label="<%= label%>">

<ui:table>
<ui:tablerow>
<ui:tablecell>
    Select portlet: <ui:listbox beanId="portletsLB"/>
 </ui:tablecell>
</ui:tablerow>

<ui:tablerow>
<ui:tablecell>
Select required role: <ui:listbox beanId="rolesLB"/>
</ui:tablecell>
</ui:tablerow>

<ui:tablerow>
<ui:tablecell>
Choose a label for bookmarking: <ui:textfield beanId="labelTF" value="<%= frame.getLabel() %>"/>
</ui:tablecell>
</ui:tablerow>

<ui:tablerow>
<ui:tablecell>
Display titlebar? : Yes
          <ui:radiobutton beanId="istitleRB" selected="<%= (request.getAttribute("isTitle") != null) %>" value="yes"/>
       No
  <ui:radiobutton beanId="istitleRB" selected="<%= (request.getAttribute("isTitle") == null) %>" value="no"/>
</ui:tablecell>
</ui:tablerow>

</ui:table>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doSaveFrame" value="Save"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:actionsubmit action="doDeleteFrame" value="Delete"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>

</ui:group>
