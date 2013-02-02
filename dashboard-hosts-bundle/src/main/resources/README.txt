Installation
- Install a J2EE application serveur (for example Tomcat : http://tomcat.apache.org/download-70.cgi)
	- Requires a Java Runtime version >= 6
- Drop in the dashboard-hosts-web-XXX.war in the deployment folder 
	- In "webapps" forlder for Tomcat
- Extract the dashboard-hosts-classpath-XXX-zip.zip archive in a folder contained in the classpath
	- For Tomcat :
		- Create the folder conf/dashboard-hosts/
		- Open conf/catalina.properties and add ",${catalina.home}/conf/dashboard-hosts" in the end of the line starting by "common.loader"
		- Extract the archive in conf/dashboard-hosts/
- Configure the files (open both .properties file with a basic text editor).
	- The default database is a H2 embedded in the application and represented as a file in the server filesystem. You can configure anything else.

First Launch
- Launch the server
	- For Tomcat, execute bin/startub.bat or .sh depending on your operating system.
- Access the application
	- For Tomcat, http://localhost:8080/dashboard-hosts-web/ if deployed on your local computer.
- Create an account named admin, which will be granted an access to the database.
- Everything is ready

Embedded database access
- Connect as admin
- Enter the url /dashboard-hosts-web/h2/
- Enter the data supplied in the dashboard-hosts-web.properties file
- You can now access the database