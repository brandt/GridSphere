<%@ page import="java.util.Iterator,
                 java.lang.String,
                 org.gridlab.gridsphere.services.core.note.Note,
                 org.radeox.engine.context.RenderContext,
                 org.radeox.engine.context.BaseRenderContext,
                 org.radeox.EngineManager"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="note" class="org.gridlab.gridsphere.services.core.note.Note" scope="request"/>
<jsp:useBean id="np_action" class="java.lang.String" scope="request"/>

<ui:form>

    <ui:frame beanId="errorFrame"/>

    <ui:frame>
        <ui:hiddenfield beanId="noteoid" value="<%= note.getOid() %>"/>
        <ui:hiddenfield beanId="np_action" value="<%= np_action.toString() %>"/>


        <ui:tablerow>

            <ui:tablecell>
<%
            if (np_action.equals("view") || np_action.equals("edit")) {
%>
                <ui:text value="<%= note.getName() %>"/>
                <%
            } else {
                %>
                <ui:text value="Notename:"/><ui:textfield beanId="head" value="<%= note.getName() %>"/>
                <%
            }
                %>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>

            <ui:tablecell>
            <%if (np_action.equals("view")) {
                RenderContext context = new BaseRenderContext();
                String text = EngineManager.getInstance().render(note.getContent(), context);
            %>
                <ui:text value="<%= text %>"/>
            <%
            } else { %>
                <ui:textarea rows="20" cols="60" beanId="content"  value="<%= note.getContent() %>"/>
            <%
            }
            %>
            </ui:tablecell>
        </ui:tablerow>

     <% if (np_action.equals("view")) { %>
            <ui:textfield beanId="search"/><ui:actionsubmit action="doSearch" key="NOTEPAD_SEARCH"/>
            <ui:actionsubmit action="doShowEdit" key="NOTEPAD_EDIT"/>
            <ui:actionsubmit action="doShowNew" key="NOTEPAD_CREATE"/>
            <ui:actionsubmit action="doDelete" key="NOTEPAD_DELETE"/>
            <%
        }
        %>
     <% if (np_action.equals("edit")) { %>
            <ui:actionsubmit action="doUpdate" key="NOTEPAD_UPDATE"/>
            <%
     }
        %>
     <% if (np_action.equals("new")) { %>
            <ui:actionsubmit action="doAdd" key="NOTEPAD_ADD"/>
            <%
     }
        %>
    <ui:actionsubmit action="showList" key="NOTEPAD_SHOWLIST"/>

    </ui:frame>

</ui:form>