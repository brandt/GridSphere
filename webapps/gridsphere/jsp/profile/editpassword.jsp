<%@ page import="org.gridsphere.services.core.security.role.PortletRole" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>

<% Locale locale = (Locale) request.getAttribute("locale"); %>
<% RenderRequest req = (RenderRequest) pageContext.findAttribute("renderRequest"); %>


<div style="width: 400px; margin: 0px 0px 0px 15%;">

<ui:form>
<ui:messagebox beanId="msg"/>


<h3><ui:text value="Change Password" style="bold"/></h3>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="150">
                    <ui:text key="ORIG_PASSWORD"/>
                </ui:tablecell>
                <ui:tablecell width="150">
                    <ui:password beanId="origPassword" size="20"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell width="150">
                    <ui:text key="NEW_PASSWORD"/>
                </ui:tablecell>
                <ui:tablecell width="150">
                    <ui:password beanId="password" size="20"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell width="150">
                    <ui:text key="CONFIRM_PASS"/>
                </ui:tablecell>
    <ui:tablecell width="150">
        <ui:password beanId="confirmPassword" size="20"/>
    </ui:tablecell>
    </ui:tablerow>
    </ui:frame>

    <ui:frame>
    <ui:tablerow>
    <ui:tablecell >
        <ui:actionsubmit action="doSavePass" key="SAVE"/>
        <ui:actionsubmit action="doCancel" key="CANCEL"/>
    </ui:tablecell>
    </ui:tablerow>
    </ui:frame>


    </ui:form>

</div>


