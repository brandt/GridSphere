<?xml version="1.0" encoding="UTF-8"?>
<portlet-app xmlns="http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd"
             version="1.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd">
    <portlet>
        <!-- place portlet description here -->
        <description xml:lang="en">This Portlet is a sample</description>
        <!-- place unique portlet name here -->
        <portlet-name>SamplePortlet</portlet-name>
        <display-name xml:lang="en">A sample Portlet</display-name>
        <!-- place your portlet class name here -->
        <portlet-class>org.gridsphere.portlets.SamplePortlet</portlet-class>
        <expiration-cache>0</expiration-cache>
        <!-- place any initialization params here -->
        <init-param>
            <name>aname</name>
            <value>avalue</value>
        </init-param>
        <!-- place supported modes here -->
        <supports>
            <mime-type>text/html</mime-type>
            <portlet-mode>configure</portlet-mode>
            <portlet-mode>edit</portlet-mode>
            <portlet-mode>help</portlet-mode>
        </supports>
        <supports>
            <mime-type>text/wml</mime-type>
            <portlet-mode>edit</portlet-mode>
            <portlet-mode>help</portlet-mode>
        </supports>
        <supported-locale>en</supported-locale>
        <portlet-info>
            <title>A Sample Portlet</title>
            <short-title>Sample</short-title>
            <keywords>sample</keywords>
        </portlet-info>
        <!-- place portlet preferences here -->
        <portlet-preferences>
            <preference>
                <name>myPref</name>
                <value>avalue</value>
                <read-only>true</read-only>
            </preference>
        </portlet-preferences>
    </portlet>

    <user-attribute>
        <description xml:lang="en">User Name</description>
        <name>user.name</name>
    </user-attribute>
    <user-attribute>
        <description xml:lang="en">User Id</description>
        <name>user.id</name>
    </user-attribute>
    <user-attribute>
        <description xml:lang="en">User Full Name</description>
        <name>user.name.full</name>
    </user-attribute>
    <user-attribute>
        <description xml:lang="en">User E-Mail</description>
        <name>user.email</name>
    </user-attribute>
    <user-attribute>
        <description xml:lang="en">Company Organization</description>
        <name>user.organization</name>
    </user-attribute>
    <user-attribute>
        <description xml:lang="en">Last Login Time</description>
        <name>user.lastlogintime</name>
    </user-attribute>
    <user-attribute>
        <description xml:lang="en">Timezone</description>
        <name>user.timezone</name>
    </user-attribute>
    <user-attribute>
        <description xml:lang="en">Preferred Locale</description>
        <name>user.locale</name>
    </user-attribute>
    <user-attribute>
        <description xml:lang="en">Preferred Theme</description>
        <name>user.theme</name>
    </user-attribute>
    <!--
        <security-constraint>
            <portlet-collection>
                <portlet-name>TimeZoneClock</portlet-name>
            </portlet-collection>
            <user-data-constraint>
                <transport-guarantee>CONFIDENTIAL</transport-guarantee>
            </user-data-constraint>
        </security-constraint>
    -->
</portlet-app>
