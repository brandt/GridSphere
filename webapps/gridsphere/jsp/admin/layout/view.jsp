
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="actionURI"  class="java.lang.String" scope="request"/>
<jsp:useBean id="controlUI"  class="java.lang.String" scope="request"/>
<jsp:useBean id="layoutlabel"  class="java.lang.String" scope="request"/>


<% String pane = (String)request.getAttribute("pane"); %>

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
                <ui:actionsubmit action="selectTheme" key="SAVE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:form>


<h3>Edit layout properties by selecting components below:</h3>

<ui:group>
  <ui:form>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                Select top-level navigation:
                <ui:listbox beanId="navigationLB"/>
                <ui:actionsubmit action="doSaveNav" key="SAVE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:form>


<ui:frame>
  <ui:tablerow>
    <ui:tablecell width="60%">
    <%--     <ui:group label="<%= layoutlabel %>">  --%>
        <%= pane %>
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
         <% } else if (controlUI.equals("menu")) { %>
            <jsp:include page="menu.jsp"/>
         <% } else if (controlUI.equals("bar")) { %>
            <jsp:include page="bar.jsp"/>
         <% } %>
      </ui:tablecell>
    </ui:tablerow>
    </ui:frame>

  </ui:form>
</ui:group>

<hr/>

* To refine the layout further, please hand edit <b>$CATALINA_HOME/webapps<%= request.getContextPath() %>/WEB-INF/CustomPortal/layouts/{GuestUserLayout.xml, LoggedInUserLayout.xml}</b>
