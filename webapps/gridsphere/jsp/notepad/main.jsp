<%@ page import="java.util.Iterator, java.util.List,
                 org.gridlab.gridsphere.services.core.note.Note"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="search" class="java.lang.String" scope="request"/>
<% List notes = (List)request.getAttribute("notes"); %>

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
    <ui:textfield beanId="search"/><ui:actionsubmit action="doSearch" key="NOTEPAD_SEARCH"/>
    <ui:actionsubmit action="doShowNew" key="NOTEPAD_CREATE"/>
    <ui:actionsubmit action="showList" key="NOTEPAD_SHOWLIST"/>
    </ui:frame>

</ui:form>
