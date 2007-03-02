<!--

    Portlet Services XML Descriptor

    Edit this file to add/modify Portlet Services

    $Id$
-->

<portlet-services>

    <service>
        <name>Example Service</name>
        <description>Provides Capabilities</description>
        <interface>com.mycom.ExampleService</interface>
        <implementation>com.mycom.impl.ExampleServiceImpl</implementation>
    </service>

    <service>
        <name>Secure Example Service</name>
        <user-required>true</user-required>
        <description>Provides Secure Capabilities</description>
        <interface>com.mycom.SecureService</interface>
        <implementation>com.mycom.SecureServiceImpl</implementation>
    </service>

</portlet-services>

