<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="date" class="java.lang.String" scope="request"/>


<table>
    <tr><td align="center">
        <ui:text value="<%= date %>"/>
    </td></tr>
</table>
