<%@ page import="java.util.List"%>

<%@ taglib uri="/portletWidgets" prefix="gs" %>

<% List tabList = (List)request.getAttribute("list"); %>

<gs:list listmodel="tablist"/>
