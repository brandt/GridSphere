<%@ page import="org.gridlab.gridsphere.portletcontainer.GridSphereProperties,
                 org.gridlab.gridsphere.portlet.*" %>

<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>


    <gs:form action="rss_configure">

        <table>
            <tr>
                <td><gs:label bean="url-label"/></td><td><gs:textfield bean="url"/>    </td>
            </tr>
            <tr>
                <td><gs:label bean="desc-label"/></td><td><gs:textfield bean="desc"/>    </td>
            </tr>
        </table>
        <gs:submit name="add" value="add Newsfeed"></gs:submit>

        <hr noshade size="1"/>

        <table>
            <tr>
                <td>
                    <gs:label bean="rssfeeds-label"/><br/>
                    <gs:listbox bean="rssfeeds"/>
                </td>
                <td>
                    <gs:radiobuttonlist bean="interval"/>
                </td>
            </tr>
            <tr>
                <td><gs:submit name="delete" value="Delete"/></td>
            </tr>
        </table>

        <hr noshade size="1"/>

        <gs:submit name="done" value="Done"></gs:submit>
    </gs:form>

<br/>
