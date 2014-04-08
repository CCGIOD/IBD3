#!/bin/bash

catalina_count=$( ps xf | grep -o "catalina" | wc -l )
user=$(echo "$USER")

if (( $catalina_count != 1 ))
then
	$CATALINA_HOME/bin/shutdown.sh
	sleep 1
fi

$CATALINA_HOME/bin/startup.sh





