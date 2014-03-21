#!/bin/bash

catalina_count=$( ps xf | grep -o "catalina" | wc -l )
user=$(echo "$USER")

if (( $catalina_count != 1 ))
then
	$CATALINA_HOME/bin/shutdown.sh
	sleep 1
fi
	#javac -classpath . -d WEB-INF/classes/ src/bdd/modeles/*.java
	#javac -classpath .:./WEB-INF/lib/jus.util.jar:./WEB-INF/lib/ojdbc14.jar:./WEB-INF/classes/ -d WEB-INF/classes/ src/bdd/accessBD/*.java	
	#javac -classpath .:../../lib/servlet-api.jar:./WEB-INF/lib/bd.jar:./WEB-INF/classes/ -d WEB-INF/classes/ src/servlets/utils/*.java	
	#javac -classpath .:../../lib/servlet-api.jar:./WEB-INF/lib/bd.jar:./WEB-INF/classes/ -d WEB-INF/classes/ src/servlets/*.java

	$CATALINA_HOME/bin/startup.sh





