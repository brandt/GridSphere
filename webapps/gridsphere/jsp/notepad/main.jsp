<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.core.note.Note"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="notes" class="java.util.List" scope="request"/>

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
                            <ui:actionlink action="viewNote" value="<%= sheet.getName() %>">
                                <ui:actionparam name="oid" value="<%= sheet.getOid() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                    </ui:tablerow>
<%
                }

%>
    <ui:textfield beanId="search"/><ui:actionsubmit action="doSearch" value="Search"/>
    <ui:actionsubmit action="doShowNew" value="New Sheet"/>
    <ui:actionsubmit action="showList" value="Show all Sheets"/>
    </ui:frame>

</ui:form>