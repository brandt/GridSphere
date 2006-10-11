<%@ page import="org.gridsphere.services.core.security.role.PortletRole" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>

<% Locale locale = (Locale) request.getAttribute("locale"); %>
<% RenderRequest req = (RenderRequest) pageContext.findAttribute("renderRequest"); %>
<% String flag = req.getContextPath() + "/images/flags/" + locale.getLanguage() + ".gif"; %>

<ui:messagebox beanId="msg"/>

<ui:form>
<ui:group key="PROFILE_SETTINGS">

    <ui:text key="PROFILE_LASTLOGIN"/>
    &nbsp;&nbsp;<b><%= logintime %></b>

    <div style="float: left; width: 350px;">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="USERNAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <% if (req.isUserInRole(PortletRole.ADMIN.getName())) { %>
                    <ui:textfield beanId="userNameTF">
                        <ui:validator type="checkNotEmpty" key="USER_NAME_BLANK"/>
                    </ui:textfield>
                    <% } else { %>
                    <ui:text beanId="userName"/>
                    <% } %>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="GIVENNAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:textfield beanId="firstName">
                        <ui:validator type="checkNotEmpty" key="USER_GIVENNAME_BLANK"/>
                    </ui:textfield>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="FAMILYNAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:textfield beanId="lastName">
                        <ui:validator type="checkNotEmpty" key="USER_FAMILYNAME_BLANK"/>
                    </ui:textfield>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="ORGANIZATION"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:textfield beanId="organization"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="USER_ROLES"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="userRoles"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

    </div>

    <div style="float:left; width: 400px;">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="EMAILADDRESS"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:textfield size="30" beanId="emailTF"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="LOCALE"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>"
                              title="<%= locale.getDisplayLanguage() %>"/>
                    <ui:listbox beanId="userlocale"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell valign="top">
                    <ui:text key="TIMEZONE"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:listbox beanId="timezones"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
    </div>
</ui:group>


<% if (request.getAttribute("savePass") != null) { %>
<div style="float: left; width: 350px;">

    <ui:group key="PROFILE_UPDATE_PASS">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="ORIG_PASSWORD"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:password beanId="origPassword"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="NEW_PASSWORD"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:password beanId="password"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="CONFIRM_PASS"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:password beanId="confirmPassword"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
    </ui:group>


</div>
<% } %>

<div style="float:left; width: 400px;">
    <ui:group key="LAYOUT_THEME">
        <p>
            <ui:text key="LAYOUT_SELECT_THEME"/>
            &nbsp;
            <ui:listbox beanId="themeLB"/>
        </p>
    </ui:group>
</div>


<p style="clear: both;"/>

<ui:actionsubmit action="doSaveAll" key="SAVE"/>

</ui:form>


