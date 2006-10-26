<%@ page import="org.gridsphere.layout.PortletTab"%>
<%@ page import="org.gridsphere.layout.PortletComponent"%>
<%@ page import="org.gridsphere.layout.PortletTableLayout"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="controlUI"  class="java.lang.String" scope="request"/>

<% PortletTab tab = (PortletTab)request.getAttribute("portletComp"); %>

<%  String label;
    if (request.getAttribute("isnewtab") != null) {
        label = "Create new tab";
    } else {
        label = "Edit tab: <b>" + tab.getTitle("en") + "</b>";
    }

%>

<ui:group label="<%= label %>">
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit beanId="moveLeftButton" action="doMoveTabLeft" value="Move left"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit beanId="moveRightButton" action="doMoveTabRight" value="Move right"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                Edit tab name: <ui:textfield beanId="nameTF" value="<%= tab.getTitle("en") %>"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                Select required role: <ui:listbox beanId="rolesLB"/>

            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                Edit label used for bookmarking: <ui:textfield beanId="labelTF" value="<%= tab.getLabel() %>"/>
            </ui:tablecell>
        </ui:tablerow>
        <% // in case this is a single level tab
           PortletComponent c = tab.getPortletComponent();
           if (c instanceof PortletTableLayout) { %>
         <ui:tablerow>
            <ui:tablecell>
                Select column layout:
                <ui:listbox beanId="colsLB"/>
            </ui:tablecell>
        </ui:tablerow>
        <% } %>
        <% if (request.getAttribute("isnewtab") != null) { %>
        <ui:tablerow>
            <ui:tablecell>
                Double tabbed pane
                <ui:radiobutton beanId="subcompRB" selected="true" value="double"/>
                Single tabbed pane
                <ui:radiobutton beanId="subcompRB" value="single"/>
            </ui:tablecell>
        </ui:tablerow>
        <% } %>
    </ui:table>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <% if (request.getAttribute("isnewtab") != null) { %>
                <ui:actionsubmit action="doSaveNewTab" value="Save"/>
                <% } else { %>
                <ui:actionsubmit action="doSaveTab" value="Save"/>
                <% } %>
            </ui:tablecell>
            <ui:tablecell>
                <% if (request.getAttribute("isnewtab") != null) { %>
                <ui:actionsubmit action="doCancel" value="Cancel"/>
                <% } else { %>
                <ui:actionsubmit action="doDeleteTab" value="Delete"/>
                <% } %>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>

</ui:group>