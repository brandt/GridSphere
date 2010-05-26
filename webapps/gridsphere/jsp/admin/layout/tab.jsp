<%@ page import="org.gridsphere.layout.PortletComponent" %>
<%@ page import="org.gridsphere.layout.PortletTab" %>
<%@ page import="org.gridsphere.layout.PortletTableLayout" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="controlUI" class="java.lang.String" scope="request"/>

<% PortletTab tab = (PortletTab) request.getAttribute("portletComp"); %>

<ui:text var="msg1" key="LAYOUT_CREATE_TAB"/>
<ui:text var="msg2" key="LAYOUT_EDIT_TAB2"/>

<% String label;
    if (request.getAttribute("isnewtab") != null) {
        label = msg1;
    } else {
        label = msg2 + " <b>" + tab.getTitle("en") + "</b>";
    }

%>

<ui:group label="<%= label %>">
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit beanId="moveLeftButton" action="doMoveTabLeft" key="LAYOUT_MV_LEFT"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit beanId="moveRightButton" action="doMoveTabRight" key="LAYOUT_MV_RIGHT"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_EDIT_TABNAME"/>
                <ui:textfield beanId="nameTF" value="<%= tab.getTitle(\"en\") %>"/>
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
                <ui:textfield beanId="labelTF" value="<%= tab.getLabel() %>"/>
            </ui:tablecell>
        </ui:tablerow>
        <% // in case this is a single level tab
            PortletComponent c = tab.getPortletComponent();
            if (c instanceof PortletTableLayout) { %>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_SEL_COLLAYOUT"/>
                <ui:listbox beanId="colsLB"/>
            </ui:tablecell>
        </ui:tablerow>
        <% } %>
        <%--
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
        --%>
    </ui:table>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <% if (request.getAttribute("isnewtab") != null) { %>
                <ui:actionsubmit action="doSaveNewTab" key="SAVE"/>
                <% } else { %>
                <ui:actionsubmit action="doSaveTab" key="SAVE"/>
                <% } %>
            </ui:tablecell>
            <ui:tablecell>
                <% if (request.getAttribute("isnewtab") != null) { %>
                <ui:actionsubmit action="doCancel" key="CANCEL"/>
                <% } else { %>
                <ui:actionsubmit action="doDeleteTab" key="DELETE"/>
                <% } %>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>

</ui:group>
