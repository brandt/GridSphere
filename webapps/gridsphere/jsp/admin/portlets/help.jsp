<p>
    <b>Using the Portlet Manager portlet</b>
</p>

<p>
    The Portlet Manager portlet allows a portal administrator to stop and start and deploy portlet web
    applications to GridSphere. Once a portlet has been stopped it is no longer accessible. A new portlet
    web application may be deployed but it assumes that the WAR has already been placed into the Tomcat
    webapps directory. File uploading of portlet applications doesn't work currently due to a Tomcat
    cross context loading problem. In addition, only those portlets that contain no "portlet services" that
    are placed in the shared library directory of Tomcat will work, since shared libraries cannot be
    properly reloaded.
</p>
