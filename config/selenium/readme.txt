This directory contains test scripts for running selenium against GridSphere.

From the website:

Selenium is a test tool for web applications. Selenium tests run directly in a browser, just as real users do.
And they run in Internet Explorer, Mozilla and Firefox on Windows, Linux, and Macintosh. No other test tool covers
such a wide array of platforms.


Get selenium: http://www.openqa.org/selenium/


Gridsphere is expected to run at localhost at port 8080 with /gridsphere/gridsphere default installation.


Scripts:

- SetupGridSphere.sel
  Deploy GridSphere to tomcat and start the container, selenium will setup GridSphere using the internal
  database and creates a root user with login 'root' and password 'user'. It will login this user and verify
  that he is logged in.