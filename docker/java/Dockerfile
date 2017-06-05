FROM		ubuntu:14.04

# Avoid ERROR: invoke-rc.d: policy-rc.d denied execution of start.
RUN		echo "#!/bin/sh\nexit 0" > /usr/sbin/policy-rc.d && \
# install `repository` tool - necessary to install oracle
		apt-get update && \
		apt-get install -y  software-properties-common && \
		add-apt-repository ppa:webupd8team/java && \
		apt-get update && \
# install oracle java
		echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
		apt-get install -y oracle-java7-set-default && \
# install tomcat
		apt-get -qq install tomcat7 && \
		mkdir $CATALINA_BASE/temp && \
		chmod 777 $CATALINA_BASE/temp && \
# clean cache
		apt-get purge --auto-remove -y software-properties-common && \
		apt-get clean && \
# set time
		echo "Europe/Warsaw" > /etc/timezone && \
		dpkg-reconfigure -f noninteractive tzdata && \
		mkdir /mpp-db

# set env used in next commands
ENV             CATALINA_BASE=/var/lib/tomcat7 JAVA_OPTS="$JAVA_OPTS -DauthType= -Xms512m -Xmx2048m -XX:PermSize=128m -XX:MaxPermSize=256m -Duser.language=pl"
