<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>
<ui:form>
    <ui:group key="LOGIN_CONFIG_SMTP">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="250">
                    <ui:text key="CONFIG_MAILHOST"/>
                </ui:tablecell>
                <ui:tablecell width="100">
                    <ui:textfield beanId="mailServerTF" maxlength="60"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell width="250">
                    <ui:text key="CONFIG_MAILPORT"/>
                </ui:tablecell>
                <ui:tablecell width="100">
                    <ui:textfield beanId="mailPortTF" maxlength="10"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell width="250">
                    <ui:text key="CONFIG_MAILFROM"/>
                </ui:tablecell>
                <ui:tablecell width="100">
                    <ui:textfield beanId="mailFromTF" maxlength="60"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell width="250">
                    <ui:text key="CONFIG_MAILADMIN"/>
                </ui:tablecell>
                <ui:tablecell width="100">
                    <ui:textfield beanId="adminTF" maxlength="60"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doSaveMailConfig" key="SAVE"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

    </ui:group>
</ui:form>



