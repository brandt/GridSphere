<%@ page import="java.util.List,
                 org.gridlab.gridsphere.layout.PortletTab"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<% String lang = (String)request.getAttribute("lang"); %>
<% List tabs = (List)request.getAttribute("tabs"); %>

<ui:form>

<ui:messagebox beanId="messageBox"/>

<h3><ui:text key="LAYOUT_THEME" style="nostyle"/></h3>
<ui:group>
<ui:text key="LAYOUT_SELECT_THEME"/>&nbsp;<ui:listbox beanId="themeLB"/> <ui:actionsubmit action="saveTheme" key="SAVE"/>
</ui:group>

<h3>Create new tab</h3>
<ui:group>
<ui:text value="Enter new tab name"/>&nbsp;&nbsp;<ui:textfield beanId="userTabTF"/>
<p>

<ui:radiobutton beanId="colsRB" value="1"/><ui:text value="One column"/>
<ui:radiobutton beanId="colsRB" value="2"/><ui:text value="Two columns"/>
<ui:radiobutton beanId="colsRB" value="3"/><ui:text value="Three columns"/>

<p>
<ui:actionsubmit action="createNewTab" value="Create"/>
</ui:group>

<h3>Display existing tabs</h3>

<% if (tabs.size() > 0) { %>
<ui:table sortable="true" zebra="true">
    <ui:tablerow header="true">
    <ui:tablecell>
        <ui:text value="Tab name"/>
    </ui:tablecell>
     <ui:tablecell>
        <ui:text value="Edit tab name"/>
    </ui:tablecell>
     <ui:tablecell>
        <ui:text value="Delete tab"/>
    </ui:tablecell>
    </ui:tablerow>

    <% for (int i = 0; i < tabs.size(); i++) { 
        PortletTab tab = (PortletTab)tabs.get(i);
        String title = tab.getTitle(lang);
    %>
    <ui:tablerow>
    <ui:tablecell>        
        <ui:text value="<%= title %>"/>
    </ui:tablecell>    
    <ui:tablecell>
        <ui:textfield name="myTF" value="<%= title %>"/>
        <ui:actionsubmit action="saveTab" value="Save">
            <ui:actionparam name="tabid" value="<%= tab.getLabel() %>"/>
        </ui:actionsubmit>
    </ui:tablecell>
    <ui:tablecell>
        <ui:actionsubmit action="deleteTab" value="Delete">
            <ui:actionparam name="tabid" value="<%= tab.getLabel() %>"/>
        </ui:actionsubmit>
    </ui:tablecell>
    </ui:tablerow>
    <% } %>

</ui:table>
<% } %>

</ui:form>
