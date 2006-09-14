
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>
<jsp:useBean id="services" class="java.lang.String" scope="request"/>

<SCRIPT LANGUAGE="JavaScript">
function checkUserCreate( elem, id ) {
    if (elem == true) {
        document.getElementById(id).checked = true;
    }
}

function checkAllowUsers( elem, id ) {
    if (elem != true) {
        document.getElementById(id).checked = false;
    }
}

//  End -->
</script>
<ui:form>
    <ui:group key="LOGIN_CONFIG_MSG">
        <ul style="list-style-type: none;">
            <li>
                <ui:checkbox id="allowCreateCB" beanId="acctCB" value="TRUE" onClick="checkAllowUsers( this.checked, 'userApprovalCB' )"/>
                <ui:text key="LOGIN_CONFIG_ALLOW"/>
            </li>
            <li><ul style="list-style-type: none;"><li>
                <ui:checkbox id="userApprovalCB" onClick="checkUserCreate( this.checked, 'allowCreateCB' )" beanId="acctApproval" value="FALSE"/>
                <ui:text key="LOGIN_ACCOUNT_APPROVAL"/>
            </li></ul></li>
            <li>
                <ui:checkbox id="notifyCB" onClick="checkUserCreate( this.checked, 'savePassCB' )" beanId="notifyCB" value="TRUE"/>
                <ui:text key="LOGIN_CONFIG_NOTIFY"/>
            </li>
            <li>
                <ui:checkbox beanId="supportx509CB" value="TRUE"/>
                <ui:text key="LOGIN_CONFIG_X509"/>
            </li>
            <li>
                <ui:checkbox beanId="remUserCB" value="TRUE"/>
                <ui:text key="LOGIN_REMUSER"/>
            </li>
            <li>
                <ui:checkbox id="savePassCB" onClick="checkUserCreate( this.checked, 'notifyCB' )" beanId="savepassCB" value="TRUE"/>
                <ui:text key="LOGIN_CONFIG_PASSWD"/>
                <br/>
                <ui:text style="alert" key="LOGIN_CONFIG_PASSWD2"/>
            </li>
        </ul>
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
