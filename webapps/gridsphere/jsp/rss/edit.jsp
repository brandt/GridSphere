<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<gs:form action="rss_edit">


    <table>
        <tr>
            <td><gs:listbox bean="rssfeeds"/> </td>
         </tr>
         <tr>
            <td><gs:textarea bean="desc"/> </td>
        </tr>
    </table>

    <gs:submit name="show" value="Show this feed"/>

    <gs:submit name="desc" value="Show url"/>


</gs:form>