
Initialized Portlet Web Applications
------------------------------------

When deployed, GridSphere requires access to deployed portlet web applications. Since portlets are
packaged according to the web application repository (WAR) format defined in the Java 2.3
Servlet Specification, the names of the WAR files or web applications needs to be added as an empty file
whose filename is defined by the name of portlet web application to this directory. The filename may
contain an additional integer suffix ".#" to ensure a priority when the container will load the portlet
application. By default, you will see *gridsphere.1* which instructs the container to load the set of
core gridsphere portlets first. Add other portlet applications to load in this directory.

