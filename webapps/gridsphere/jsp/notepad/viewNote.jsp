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
    <ui:frame>
        <ui:hiddenfield beanId="noteoid" value="<%= note.getOid() %>"/>
        <ui:hiddenfield beanId="np_action" value="<%= np_action.toString() %>"/>


        <ui:tablerow>

            <ui:tablecell>
<%
            if (np_action.equals("view")) {
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
                System.out.println("getting context");
/*                RenderContext context = new BaseRenderContext();
                System.out.println("getting context 2");
                String text = EngineManager.getInstance().render(note.getContent(), context);
                System.out.println("t:"+text); */
            %>
                <ui:text value="<%= note.getRadeoxMarkup() %>"/>
            <%
            } else { %>
                <ui:textarea rows="20" cols="60" beanId="content"  value="<%= note.getContent() %>"/>
            <%
            }
            %>
            </ui:tablecell>
        </ui:tablerow>

     <ui:textfield beanId="search"/><ui:actionsubmit action="doSearch" value="Search"/>
     <% if (np_action.equals("view")) { %>
            <ui:actionsubmit action="doShowEdit" value="Edit Note"/>
            <ui:actionsubmit action="doShowNew" value="New Note"/>
            <%
        }
        %>
     <% if (np_action.equals("edit")) { %>
            <ui:actionsubmit action="doUpdate" value="Update Note"/>
            <%
     }
        %>
     <% if (np_action.equals("new")) { %>
            <ui:actionsubmit action="doAdd" value="Add Note"/>
            <%
     }
        %>
    <ui:actionsubmit action="doDelete" value="Delete Note"/>
    <ui:actionsubmit action="showList" value="Show List"/>

    </ui:frame>

</ui:form>