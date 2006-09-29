
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:form>
    <ui:group key="LOGIN_CONFIG_SMTP">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="CONFIG_MAILHOST"/>
                    <ui:textfield beanId="mailServerTF"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="CONFIG_MAILPORT"/>
                    <ui:textfield beanId="mailPortTF"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="CONFIG_MAILFROM"/>
                    <ui:textfield beanId="mailFromTF"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="CONFIG_MAILADMIN"/>
                    <ui:textfield beanId="adminTF"/>
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



