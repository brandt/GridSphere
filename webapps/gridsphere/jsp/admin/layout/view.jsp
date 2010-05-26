<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="actionURI" class="java.lang.String" scope="request"/>
<jsp:useBean id="controlUI" class="java.lang.String" scope="request"/>
<jsp:useBean id="layoutlabel" class="java.lang.String" scope="request"/>
<jsp:useBean id="pageName" class="java.lang.String" scope="request"/>


<% String pane = (String) request.getAttribute("pane"); %>

<ui:messagebox beanId="msg"/>

<ui:form>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_CUSTOMIZE"/>
                <ui:listbox beanId="layoutsLB"/>
                <ui:actionsubmit action="selectLayout" key="LAYOUT_DISPLAY"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="LAYOUT_SEL_THEME"/>
                <ui:listbox beanId="themesLB"/>
                <ui:actionsubmit action="selectTheme" key="SAVE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:form>

<ui:group key="LAYOUT_NAVIGATION">
    <ui:form>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LAYOUT_NAVIGATION_HOME"/>
                <ui:textfield beanId="homenavigationTF"/>
                <ui:actionsubmit action="saveHomeNavigation" key="SAVE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:form>
</ui:group>

<ui:group key="LAYOUT_PAGE_DETAILS">
    <ui:form>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="LAYOUT_EDIT_TITLE"/>
                    <ui:textfield beanId="titleTF"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text key="LAYOUT_EDIT_KEYWORDS"/>
                    <ui:textfield beanId="keywordsTF"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
        <ui:actionsubmit action="savePageDetails" key="SAVE"/>
    </ui:form>
</ui:group>

<h3>
    <ui:text key="LAYOUT_EDIT_COMPS"/>
    &nbsp;<b><%= pageName %>
</b></h3>

<ui:group>
    <ui:form>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="LAYOUT_SEL_TOPS"/>
                    <ui:listbox beanId="navigationLB"/>
                    <ui:text key="LAYOUT_SEL_SHOW_MODES"/> <ui:checkbox beanId="showModesCB"/>
                    <ui:text key="LAYOUT_SEL_SHOW_STATES"/><ui:checkbox beanId="showStatesCB"/>
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
                    <% if (!controlUI.equals("")) { %>
                    <jsp:include page="<%= controlUI + \".jsp\" %>"/>
                    <% } %>
                    <%--
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
                <% } %>  --%>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

    </ui:form>
</ui:group>

<hr/>

*
<ui:text key="LAYOUT_EDIT_MSG"/>
&nbsp; <b>$CATALINA_HOME/webapps<%= request.getContextPath() %>/WEB-INF/CustomPortal/layouts/{guest.xml,
    loggedin.xml}</b>
