<?xml version="1.0" encoding="ISO-8859-1"?>

<!--
 $Id$
-->

<!DOCTYPE web-app
    PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd">

<web-app>
    <display-name>@PROJECT_TITLE@</display-name>

    <description>
        Provides @PROJECT_TITLE@
    </description>

    <servlet>
        <servlet-name>HelloWorld</servlet-name>
        <servlet-class>org.gridsphere.portlets.examples.HelloWorld</servlet-class>
    </servlet>

    <servlet-mapping>
       <servlet-name>HelloWorld</servlet-name>
       <url-pattern>/hello</url-pattern>
    </servlet-mapping>

    <mime-mapping>
      <extension>wbmp</extension>
      <mime-type>image/vnd.wap.wbmp</mime-type>
    </mime-mapping>

    <mime-mapping>
      <extension>wml</extension>
      <mime-type>text/vnd.wap.wml</mime-type>
    </mime-mapping>

    <mime-mapping>
      <extension>wmls</extension>
      <mime-type>text/vnd.wap.wmlscript</mime-type>
    </mime-mapping>

</web-app>
