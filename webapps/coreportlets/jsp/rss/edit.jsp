<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<gs:form action="rss_edit">


    <gs:text bean="textid"/>

    <gs:textfield bean="f"/>

    <gs:checkbox bean="cbb"/>

    <gs:textarea bean="tab"/>

    <gs:submit name="show" value="Show"/>

    <gs:submit name="ok" value="Ok"/>


    <gs:dropdownlist bean="ddl"/>

    <gs:listbox bean="lbb"/>


</gs:form>