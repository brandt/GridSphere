<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
    <ui:messagebox beanId="msg"/>

    <ui:table>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="LOGIN_NAME"/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <input type="input" name="username" size="20" maxlength="20"/>
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
                    <input type="password" name="password" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
    </ui:table>

    <input type="checkbox" name="remlogin" value="yes"/><ui:text key="LOGIN_REMEMBER_ME"/>

    <ui:table>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="gs_login" key="LOGIN_ACTION"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
    </ui:table>

    <p>

    <% if (request.getAttribute("canUserCreateAcct") != null)  { %>
    <ui:table>
         <ui:tablerow>
         <ui:tablecell>
                    <ui:actionlink action="doNewUser" key="LOGIN_SIGNUP"/>
         </ui:tablecell>
         </ui:tablerow>
    </ui:table>
        <p>
        <% } %>

 <ui:actionlink action="displayForgotPassword" key="LOGIN_FORGOT_PASSWORD"/>

</ui:form>