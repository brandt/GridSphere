<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.core.note.Note"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="notes" class="java.util.List" scope="request"/>
<jsp:useBean id="search" class="java.lang.String" scope="request"/>

<ui:frame beanId="errorFrame"/>

<ui:form>
    <ui:frame>


<%
                Iterator it = notes.iterator();
                while (it.hasNext()) {
                    // Get next user
                    Note sheet = (Note)it.next();
%>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:actionlink beanId="link" action="viewNote" value="<%= sheet.getName() %>">
                                <ui:actionparam name="oid" value="<%= sheet.getOid() %>"/>
                                <ui:actionparam name="search" value="<%= search %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                    </ui:tablerow>
<%
                }

%>
    <ui:textfield beanId="search"/><ui:actionsubmit action="doSearch" value="Search"/>
    <ui:actionsubmit action="doShowNew" value="Create a Note"/>
    <ui:actionsubmit action="showList" value="Show all Notes"/>
    </ui:frame>

</ui:form>