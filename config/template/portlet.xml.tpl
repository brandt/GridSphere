<?xml version="1.0" encoding="UTF-8"?>
<portlet-app-collection>

<portlet-app-def>

<portlet-app id="org.gridsphere.portlets.examples.HelloWorld">
    <portlet-name>Hello World Portlet Application</portlet-name>
    <servlet-name>HelloWorld</servlet-name>
    <portlet-config>
        <param-name>PortletConfig Param</param-name>
        <param-value>some config param value</param-value>
    </portlet-config>
    <allows>
        <maximized/>
        <minimized/>
        <resizing/>
    </allows>
    <supports>
        <view/>
        <edit/>
        <help/>
        <configure/>
    </supports>
</portlet-app>

<concrete-portlet-app id="org.gridsphere.portlets.examples.HelloWorld.1">
    <context-param>
        <param-name>A PortletApplicationSettings parameter</param-name>
        <param-value>the value</param-value>
    </context-param>
    <concrete-portlet>
        <portlet-name>Hello World</portlet-name>
        <default-locale>en</default-locale>
        <language locale="en_US">
            <title>Hello World - Sample Portlet #1</title>
            <title-short>Hello World</title-short>
            <description>Hello World - Sample Portlet #1</description>
            <keywords>portlet hello world</keywords>
        </language>
        <config-param>
            <param-name>A PortletSettings parameter</param-name>
            <param-value>the value</param-value>
        </config-param>
    </concrete-portlet>
</concrete-portlet-app>

<concrete-portlet-app id="org.gridsphere.portlets.examples.HelloWorld.2">
    <context-param>
        <param-name>Yet another PortletApplicationSettings param</param-name>
        <param-value>another value</param-value>
    </context-param>
    <concrete-portlet>
        <portlet-name>Hello World 2</portlet-name>
        <default-locale>en</default-locale>
        <language locale="en_US">
            <title>Hello World - Sample Portlet #2</title>
            <title-short>Hello World</title-short>
            <description>Hello World - Sample Portlet #2</description>
            <keywords>portlet hello world</keywords>
        </language>
        <config-param>
            <param-name>Yet another PortletSettings param</param-name>
            <param-value>another value</param-value>
        </config-param>
    </concrete-portlet>
</concrete-portlet-app>

</portlet-app-def>

</portlet-app-collection>
