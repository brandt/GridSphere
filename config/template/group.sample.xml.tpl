<?xml version="1.0" encoding="UTF-8"?>
<!--
 Sample group layout 

 $Id$
-->
<portlet-group>
    <group-name>mygroupsample</group-name>
    <group-description>A sample group</group-description>
    <group-visibility>PUBLIC</group-visibility>
    <portlet-role-info>
        <portlet-class>org.myorg..portlets.userportlet.UserPortlet</portlet-class>
        <required-role>USER</required-role>
    </portlet-role-info>
    <portlet-role-info>
        <portlet-class>org.myorg.portlets.adminportlet.AdminPortlet</portlet-class>
        <required-role>ADMIN</required-role>
    </portlet-role-info>
</portlet-group>
