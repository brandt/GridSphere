
Configuring the Database
------------------------

This file contains the hibernate.properties file which configures the 
database settings used by GridSphere. The default is to use the HsqlDB
database, a pure java SQL database. 

The default HsqlDB is contained in the file "gridsphere" by default 

To use another database, configure the hibernate.properties file and place 
the required JDBC driver jar file in the $CATALINA_HOME/common/lib directory
of Tomcat. Next, invoke the "ant deploy" followed by "ant create-database" 
commands in the gridsphere directory.

