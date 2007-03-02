<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<h3>
    <ui:text key="USER_COMPOSE_EMAIL" style="nostyle"/>
</h3>

<ui:form>

    <table id="form-table">
        <tbody>
            <tr>
                <td style="font-weight:bold; text-align: right;" width="10%">
                 <ui:radiobutton beanId="toRB" value="TO" selected="true"/>
                    To:
                 <ui:radiobutton beanId="toRB" value="BCC"/>
                    Bcc:
                </td>
                <td>
                    <ui:textfield beanId="emailAddressTF" size="90" id="emailAddress"/>
                </td>
            </tr>

            <tr valign="top">
                <td style="font-weight:bold; text-align: right;" width="10%">
                    From:
                </td>
                <td>
                    <ui:textfield beanId="senderTF" value="" size="90"/>
                </td>
            </tr>

            <tr valign="top">
                <td style="font-weight:bold; text-align: right;" width="10%">
                    Subject:
                </td>
                <td>
                    <ui:textfield beanId="subjectTF" value="" size="90"/>
                </td>
            </tr>

            <tr valign="top">
                <td style="font-weight:bold; text-align: right;" width="10%">
                    Message:
                </td>
                <td>
                    <ui:textarea beanId="bodyTA" cols="74" rows="10"/>
                </td>
            </tr>

            <tr valign="top">
                <td>
                </td>
                <td>
                    <ui:actionsubmit action="doSendEmail" key="USER_SEND"/>
                    <ui:actionsubmit action="doListUsers" key="CANCEL"/>
                </td>
            </tr>
        </tbody>
    </table>


</ui:form>
