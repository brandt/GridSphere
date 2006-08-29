<%@ page import="org.gridsphere.portlet.PortletRequest,
                 org.gridsphere.services.core.security.role.PortletRole,
                 java.util.Locale" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>
<jsp:useBean id="username" class="java.lang.String" scope="request"/>

<% Locale locale = (Locale) request.getAttribute("locale"); %>
<% PortletRequest req = (PortletRequest) pageContext.getAttribute("portletRequest"); %>
<% String flag = "images/flags/" + locale.getLanguage() + ".gif"; %>

<ui:messagebox beanId="msg"/>

<table>
<tr>
    <td valign="top">

        <ui:form>
            <ui:group key="PROFILE_SETTINGS">
                <p>
                    <ui:text key="PROFILE_LASTLOGIN"/>&nbsp;&nbsp;<b><%= logintime %></b>
                </p>
                <ui:frame beanId="errorFrame"/>


                <ui:panel cols="40%, 40%, 20%">
                    <ui:frame>
                        <ui:tablerow>
                            <ui:tablecell><ui:text key="USERNAME"/></ui:tablecell>
                            <ui:tablecell>
                                <% if (req.getRoles().contains(PortletRole.ADMIN.getName())) { %>
                                <ui:textfield beanId="userNameTF">
                                    <ui:validator type="checkNotEmpty" key="USER_NAME_BLANK"/>
                                </ui:textfield>
                                <% } else { %>
                                <ui:text beanId="userName"/>
                                <% } %>
                            </ui:tablecell>
                        </ui:tablerow>
                        <ui:tablerow>
                            <ui:tablecell><ui:text key="FULLNAME"/></ui:tablecell>
                            <ui:tablecell>
                                <ui:textfield beanId="fullName">
                                    <ui:validator type="checkNotEmpty" key="USER_FULLNAME_BLANK"/>
                                </ui:textfield>
                            </ui:tablecell>
                        </ui:tablerow>
                        <ui:tablerow>
                            <ui:tablecell><ui:text key="ORGANIZATION"/></ui:tablecell>
                            <ui:tablecell><ui:textfield beanId="organization"/></ui:tablecell>
                        </ui:tablerow>
                        <ui:tablerow>
                            <ui:tablecell><ui:text key="USER_ROLES"/></ui:tablecell>
                            <ui:tablecell><ui:text beanId="userRoles"/></ui:tablecell>
                        </ui:tablerow>
                    </ui:frame>
                    <ui:frame beanId="messagingFrame" valign="top"/>
                    <ui:frame>
                        <ui:tablerow>
                            <ui:tablecell><ui:text key="LOCALE"/></ui:tablecell>
                            <ui:tablecell><ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>"
                                                    title="<%= locale.getDisplayLanguage() %>"/><ui:listbox
                                    beanId="userlocale"/></ui:tablecell>
                        </ui:tablerow>
                        <ui:tablerow>
                            <ui:tablecell valign="top"><ui:text key="TIMEZONE"/></ui:tablecell>
                            <ui:tablecell><ui:listbox beanId="timezones"/></ui:tablecell>
                        </ui:tablerow>
                    </ui:frame>

                </ui:panel>
                <ui:actionsubmit action="doSaveUser" key="SAVE"/>
            </ui:group>
        </ui:form>


    </td>
</tr>
<tr>
    <td>


        <table>
            <tr>
                <td valign="top">
                    <ui:form>

                        <% if (request.getAttribute("savePass") != null) { %>


                        <ui:group key="PROFILE_UPDATE_PASS">
                            <ui:frame width="50%">
                                <ui:tablerow>
                                    <ui:tablecell><ui:text key="ORIG_PASSWORD"/></ui:tablecell>
                                    <ui:tablecell><ui:password beanId="origPassword"/></ui:tablecell>
                                </ui:tablerow>
                                <ui:tablerow>
                                    <ui:tablecell><ui:text key="PASSWORD"/></ui:tablecell>
                                    <ui:tablecell><ui:password beanId="password"/></ui:tablecell>
                                </ui:tablerow>
                                <ui:tablerow>
                                    <ui:tablecell><ui:text key="CONFIRM_PASS"/></ui:tablecell>
                                    <ui:tablecell><ui:password beanId="confirmPassword"/></ui:tablecell>
                                </ui:tablerow>
                            </ui:frame>
                            <p>
                                <ui:actionsubmit action="doSavePass" key="SAVE"/>
                            </p>
                        </ui:group>

                        <% } %>
                    </ui:form>
                </td>

            </tr>
        </table>

    </td>
</tr>
</table>

<ui:form>
    <ui:group key="LAYOUT_THEME">
        <p>
            <ui:text key="LAYOUT_SELECT_THEME"/>&nbsp;<ui:listbox beanId="themeLB"/> <ui:actionsubmit action="saveTheme"
                                                                                                      key="SAVE"/>
        </p>
    </ui:group>
</ui:form>


<h3><ui:actionlink action="doFinish" value="Return to portal"/></h3>
