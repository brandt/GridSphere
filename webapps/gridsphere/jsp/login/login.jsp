<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<jsp:useBean id="canUserCreateAcct" class="java.lang.Boolean" scope="request"/>

<portletAPI:init/>

<ui:form>
    <ui:frame beanId="errorFrame"/>

    <ui:table>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="LOGIN_NAME"/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:textfield beanId="username" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
    </ui:table>

    <ui:table>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="LOGIN_PASS"/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:password beanId="password" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
    </ui:table>

    <ui:table>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="gs_login" key="LOGIN_ACTION"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
    </ui:table>

    <% if (canUserCreateAcct.booleanValue()) { %>
    <ui:table>
         <ui:tablerow>
         <ui:tablecell>
                    <ui:actionlink action="doNewUser" key="LOGIN_SIGNUP"/>
         </ui:tablecell>
         </ui:tablerow>
    </ui:table>
    <% } %>

</ui:form>