<?xml version="1.0" encoding="UTF-8"?>
<portlet-app-collection>

<portlet-app-def>

<portlet-app id="org.gridlab.gridsphere.portlets.examples.HelloWorld">
    <portlet-name>Hello World Portlet Application</portlet-name>
    <servlet-name>HelloWorld</servlet-name>
    <portlet-config>
        <param-name>Portlet Master</param-name>
        <param-value>yourid@yourdomain.com</param-value>
    </portlet-config>
    <cache>
        <expires>120</expires>
        <shared>true</shared>
    </cache>
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

<concrete-portlet-app id="org.gridlab.gridsphere.portlets.examples.HelloWorld.1">
    <context-param>
        <param-name>foobar</param-name>
        <param-value>a value</param-value>
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
            <param-name>Portlet Master</param-name>
            <param-value>yourid@yourdomain.com</param-value>
        </config-param>
    </concrete-portlet>
</concrete-portlet-app>

<concrete-portlet-app id="org.gridlab.gridsphere.portlets.examples.HelloWorld.2">
    <context-param>
        <param-name>foobar</param-name>
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
            <param-name>Portlet Master</param-name>
            <param-value>yourid@yourdomain.com</param-value>
        </config-param>
    </concrete-portlet>
</concrete-portlet-app>

</portlet-app-def>

</portlet-app-collection>
