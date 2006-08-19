<%@ page import="java.util.List,
                 org.gridsphere.layout.PortletTab" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<% String lang = (String) request.getAttribute("lang"); %>
<% List tabs = (List) request.getAttribute("tabs"); %>

<ui:messagebox beanId="msg"/>



<ui:form>
    <ui:group key="LAYOUT_THEME">
        <p>
            <ui:text key="LAYOUT_SELECT_THEME"/>&nbsp;<ui:listbox beanId="themeLB"/> <ui:actionsubmit action="saveTheme"
                                                                                                      key="SAVE"/>
        </p>
    </ui:group>
</ui:form>


<ui:form>
    <ui:group key="LAYOUT_NEW_TAB">
        <p>
            <ui:text key="LAYOUT_TAB_NAME"/>&nbsp;&nbsp;<ui:textfield beanId="userTabTF"/>
        </p>
        <p>
            <ui:radiobutton beanId="colsRB" value="1"/><ui:text key="LAYOUT_ONE_COL"/>
            <ui:radiobutton beanId="colsRB" value="2" selected="true"/><ui:text key="LAYOUT_TWO_COL"/>
            <ui:radiobutton beanId="colsRB" value="3"/><ui:text key="LAYOUT_THREE_COL"/>
        </p>
        <p>
            <ui:actionsubmit action="createNewTab" key="CREATE"/>
        </p>
    </ui:group>
</ui:form>

<% if (tabs.size() > 0) { %>
<h3><ui:text key="LAYOUT_DISP_TABS" style="nostyle"/></h3>

<ui:table zebra="true">
    <ui:tablerow header="true">
        <ui:tablecell>
            <ui:text key="LAYOUT_SHOW_TAB"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text key="LAYOUT_EDIT_TAB"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text key="LAYOUT_DELETE_TAB"/>
        </ui:tablecell>
    </ui:tablerow>

    <% for (int i = 0; i < tabs.size(); i++) {
        PortletTab tab = (PortletTab) tabs.get(i);
        String title = tab.getTitle(lang);
    %>
    <ui:form>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="<%= title %>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield name="myTF" value="<%= title %>"/>
                <ui:actionsubmit action="saveTab" key="SAVE">
                    <ui:actionparam name="tabid" value="<%= tab.getLabel() %>"/>
                </ui:actionsubmit>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="deleteTab" key="DELETE">
                    <ui:actionparam name="tabid" value="<%= tab.getLabel() %>"/>
                </ui:actionsubmit>
            </ui:tablecell>
        </ui:tablerow>
    </ui:form>
    <% } %>

</ui:table>
<% } %>


