<%@ page import="java.util.Iterator,
                 java.util.Map" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<% Map groupNames = (Map) request.getAttribute("groupNames"); %>
<% String coregroup = (String) request.getAttribute("coreGroup"); %>
<portletAPI:init/>

<ui:messagebox beanId="msg"/>

<ui:form>

    <ui:hasrole role="super">

        <ui:table cellpadding="5">
            <ui:tablerow>
                <ui:tablecell>
                    <ui:group key="LAYOUTMGR_EDIT_BANNER">
                        <ui:text key="LAYOUTMGR_EDIT_MSG"/><br/>
                        <ui:textarea beanId="bannerTA" rows="8" cols="70"/>
                        <br/>
                        <ui:actionsubmit action="saveBanner" key="SAVE"/>
                    </ui:group>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:group key="LAYOUTMGR_EDIT_FOOTER">
                        <ui:text key="LAYOUTMGR_EDIT_FOOTER_MSG"/><br/>
                        <ui:textarea beanId="footerTA" rows="8" cols="70"/>
                        <br>
                        <ui:actionsubmit action="saveFooter" key="SAVE"/>
                    </ui:group>
                </ui:tablecell>
            </ui:tablerow>
        </ui:table>

        <ui:group key="LAYOUTMGR_EDIT_THEME">
            <ui:text key="LAYOUTMGR_THEME_MSG"/>&nbsp;
            <ui:listbox beanId="themesLB"/>
            <ui:actionsubmit action="saveDefaultTheme" key="SAVE"/>
        </ui:group>

        <ui:group key="LAYOUTMGR_GUEST">
            <ui:actionlink action="editGuestLayout" key="LAYOUTMGR_EDIT_GUEST"/>
        </ui:group>

    </ui:hasrole>


    <ui:group key="LAYOUTMGR_GROUPS">

        <p>
            <ui:text key="LAYOUTMGR_GROUP_MSG"/>
        </p>
        <ui:frame>
            <ui:tablerow header="true">
                <ui:tablecell><ui:text key="GROUP_NAME"/></ui:tablecell>
                <ui:tablecell><ui:text key="GROUP_DESCRIPTION"/></ui:tablecell>
                <ui:tablecell><ui:text key="LAYOUTMGR_GROUP_EDIT"/></ui:tablecell>
                <ui:tablecell><ui:text key="LAYOUTMGR_GROUP_DELETE"/></ui:tablecell>
            </ui:tablerow>

            <% Iterator it = groupNames.keySet().iterator();
                while (it.hasNext()) {
                    String group = (String) it.next();
                    String groupDesc = (String) groupNames.get(group);
            %>

            <ui:hasrole role="admin" group="<%= group %>">

                <ui:tablerow>
                    <ui:tablecell>
                        <ui:text value="<%= group %>"/>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text value="<%= groupDesc %>"/>
                    </ui:tablecell>


                    <ui:tablecell>
                        <% if (group.equals(coregroup)) { %>
                        <ui:hasrole role="super">
                            <ui:actionlink action="editGroupLayout" key="EDIT">
                                <ui:actionparam name="group" value="<%= group %>"/>
                            </ui:actionlink>
                        </ui:hasrole>
                        <% } else { %>
                        <ui:actionlink action="editGroupLayout" key="EDIT">
                            <ui:actionparam name="group" value="<%= group %>"/>
                        </ui:actionlink>
                        <% } %>
                    </ui:tablecell>
                    <%--                    <ui:tablecell>
                                            <% if (!group.equals(coregroup)) { %>
                                            <ui:actionlink action="deleteLayout" key="DELETE">
                                                <ui:actionparam name="group" value="<%= group %>"/>
                                            </ui:actionlink>
                                            <% } %>
                                        </ui:tablecell>
                    --%>
                </ui:tablerow>

            </ui:hasrole>

            <% } %>
        </ui:frame>
    </ui:group>

</ui:form>