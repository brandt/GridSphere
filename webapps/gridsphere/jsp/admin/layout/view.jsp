
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>


<portletAPI:init/>

<jsp:useBean id="actionURI"  class="java.lang.String" scope="request"/>
<jsp:useBean id="controlUI"  class="java.lang.String" scope="request"/>
<jsp:useBean id="layoutlabel"  class="java.lang.String" scope="request"/>


<% String guestPane = (String)request.getAttribute("pane"); %>

<ui:messagebox beanId="msg"/>

<ui:form>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                Select a layout to customize:
                <ui:listbox beanId="layoutsLB"/>
                <ui:actionsubmit action="selectLayout" value="Display"/>
            </ui:tablecell>
            <ui:tablecell>
                Select default theme for new users:
                <ui:listbox beanId="themesLB"/>
                <ui:actionsubmit action="selectTheme" value="Save"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:form>



<ui:frame>
  <ui:tablerow>
    <ui:tablecell width="60%">
    <%--     <ui:group label="<%= layoutlabel %>">  --%>
        <%= guestPane %>
    <%--     </ui:group>  --%>
    </ui:tablecell>
         </ui:tablerow>
  </ui:frame>

 <ui:form>

<ui:frame>
    <ui:tablerow>
    <ui:tablecell>

           <ui:hiddenfield beanId="compHF"/>
         <% if (controlUI.equals("frame")) { %>
            <jsp:include page="frame.jsp"/>
         <% } else if (controlUI.equals("content")) { %>
            <jsp:include page="content.jsp"/>
         <% } else if (controlUI.equals("tab")) { %>
            <jsp:include page="tab.jsp"/>
         <% } else if (controlUI.equals("subtab")) { %>
            <jsp:include page="subtab.jsp"/>
         <% } %>
      </ui:tablecell>
    </ui:tablerow>
    </ui:frame>

  </ui:form>


<hr/>

