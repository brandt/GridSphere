
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>
<jsp:useBean id="services" class="java.lang.String" scope="request"/>

<ui:form>
    <ui:group key="LOGIN_CONFIG_MSG">
        <p>
            <ui:checkbox beanId="acctCB" value="TRUE"/>
            <ui:text key="LOGIN_CONFIG_ALLOW"/>
        </p>
        <p>
            <ui:checkbox beanId="acctApproval" value="FALSE"/>
            <ui:text key="LOGIN_ACCOUNT_APPROVAL"/>
        </p>

        <p>
            <ui:checkbox beanId="notifyCB" value="TRUE"/>
            <ui:text key="LOGIN_CONFIG_NOTIFY"/>
        </p>

        <p>
            <ui:checkbox beanId="supportx509CB" value="TRUE"/>
            <ui:text key="LOGIN_CONFIG_X509"/>
        </p>

        <p>
            <ui:checkbox beanId="remUserCB" value="TRUE"/>
            <ui:text key="LOGIN_REMUSER"/>
        </p>

        <p>
            <ui:checkbox beanId="savepassCB" value="TRUE"/>
            <ui:text key="LOGIN_CONFIG_PASSWD"/>
            <br/>
            <ui:text style="alert" key="LOGIN_CONFIG_PASSWD1"/>
            <br/>
            <ui:text style="alert" key="LOGIN_CONFIG_PASSWD2"/>
        </p>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="setLoginSettings" key="APPLY"/>
                </ui:tablecell>
            </ui:tablerow>

        </ui:frame>
    </ui:group>
</ui:form>



<ui:form>
    <ui:group key="LOGIN_TRIES_CONFIG_MSG">

        <p>
            <ui:text key="LOGIN_TRIES_MSG"/>
        </p>

        <p>
            <ui:textfield beanId="numTriesTF"/>
        </p>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="configAccountSettings" key="APPLY"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
    </ui:group>
</ui:form>
