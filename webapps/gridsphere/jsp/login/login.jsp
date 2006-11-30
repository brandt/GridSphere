<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="certificate" class="java.lang.String" scope="request"/>
<jsp:useBean id="useSecureLogin" class="java.lang.String" scope="request"/>

<ui:form secure="<%= Boolean.valueOf(useSecureLogin).booleanValue() %>">
    <ui:messagebox beanId="msg"/>

  <%-- <ui:dialoglink titleColor="green" id="foo" name="bar" value="Click Me" header="This is a header" body="This is a body" footer="This is a footer" width="200"/> --%>
 
    
    <% if (request.getAttribute("certificate") != null && ((String) request.getAttribute("certificate")).length() > 0)  { %>
    <ui:table>
            <ui:tablerow>
                <ui:tablecell width="160">
                    <ui:text key="LOGIN_CERTIFICATE"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell width="160">
                    <%= certificate %>
                </ui:tablecell>
            </ui:tablerow>
    </ui:table>
    <% } else { %>

    <ui:table>
        <% if (request.getAttribute("useUserName") != null) { %>
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="LOGIN_NAME"/>
            </ui:tablecell>
            <ui:tablecell width="60">
                <ui:text var="userkey" key="USER_NAME_BLANK"/>
                <input class="checkNotEmpty#" type="text" name="username" size="15" maxlength="50"/>
                <input type="hidden" name="val#username#checkNotEmpty" value="<%= userkey %>"/>
            </ui:tablecell>
            <ui:tablecell/>
        </ui:tablerow>

       <% } else { %>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="LOGIN_EMAIL_NAME"/>
            </ui:tablecell>
            <ui:tablecell width="60">
                <ui:text var="emailkey" key="USER_EMAIL_BLANK"/>
                <input class="checkNotEmpty#" type="text" name="username" size="25" maxlength="50"/>
                <input type="hidden" name="val#username#checkNotEmpty" value="<%= emailkey %>"/>
            </ui:tablecell>
            <ui:tablecell/>
        </ui:tablerow>
        <% } %>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="LOGIN_PASS"/>
            </ui:tablecell>
            <ui:tablecell width="60">
                <ui:text var="passkey" key="USER_PASSWORD_BLANK"/>
                <% if (request.getAttribute("useUserName") != null) { %>
                <input class="checkNotEmpty#" type="password" name="password" size="15" maxlength="50"/>
                <% } else { %>
                <input class="checkNotEmpty#" type="password" name="password" size="25" maxlength="50"/>
                <% } %>
                <input type="hidden" name="val#password#checkNotEmpty" value="<%= passkey %>"/>
            </ui:tablecell>
            <ui:tablecell/>
        </ui:tablerow>
    </ui:table>

    <% if (request.getAttribute("remUser") != null) { %>
    <p>
        <input type="checkbox" name="remlogin" value="yes"/><ui:text key="LOGIN_REMEMBER_ME"/>
    </p>
    <% } %>

    <% } %>


    <ui:actionsubmit cssStyle="margin-right: 30px;" action="<%= SportletProperties.LOGIN %>" key="LOGIN_ACTION">
        <% if (request.getParameter("cid") != null) { %>
        <ui:actionparam name="queryString" value="<%= request.getParameter("cid") %>"/>
        <% } %>
    </ui:actionsubmit>

    <%--
     <% if (request.getAttribute("canUserCreateAcct") != null) { %>
     <ui:actionlink action="doNewUser" key="LOGIN_SIGNUP"/>
     <% } %>
    --%>
    <% if ((request.getAttribute("dispPass") != null) && ((request.getAttribute("certificate") == null) || ((String) request.getAttribute("certificate")).length() == 0)) { %>
    <ui:actionlink action="displayForgotPassword" key="LOGIN_FORGOT_PASSWORD"/>
    <% } %>

</ui:form>


