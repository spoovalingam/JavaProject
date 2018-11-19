From tomcat:8-jre8 

# Maintainer yes MAINTAINER "youremailaddress" 

# Copy to images tomcat path 
COPY StaticWeb.war $CATALINA_HOME/webapps