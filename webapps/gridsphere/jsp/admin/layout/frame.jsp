<%@ page import="org.gridsphere.layout.PortletFrame" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="controlUI" class="java.lang.String" scope="request"/>

<% PortletFrame frame = (PortletFrame) request.getAttribute("portletComp"); %>

<ui:text var="msg" key="LAYOUT_EDIT_FRAME"/>
<% String label = msg + " <b>" + frame.getPortletName() + "</b>"; %>

<ui:group label="<%= label%>">

    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_SEL_PORTLETS"/>
                <ui:listbox beanId="portletsLB"/>
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
                <ui:textfield beanId="labelTF" value="<%= frame.getLabel() %>"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_DISP_TB"/>
                :
                <ui:text key="YES"/>
                <ui:radiobutton beanId="istitleRB" selected="<%= (request.getAttribute(\"isTitle\") != null) %>"
                                value="yes"/>
                <ui:text key="NO"/>
                <ui:radiobutton beanId="istitleRB" selected="<%= (request.getAttribute(\"isTitle\") == null) %>"
                                value="no"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:table>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doSaveFrame" key="OK"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="doCancel" key="CANCEL"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>

</ui:group>
