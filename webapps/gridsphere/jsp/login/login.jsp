<%@ page import="org.gridlab.gridsphere.portlet.impl.SportletProperties"%>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
    <ui:panel>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:errortext beanId="errorMsg"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="LOGIN_NAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:textfield name="username" size="20" maxlength="20"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="LOGIN_PASS"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:password name="password" size="20" maxlength="20"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="<%= SportletProperties.LOGIN %>" name="login" key="LOGIN_ACTION"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:panel>
</ui:form>
