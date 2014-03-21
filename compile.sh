javac -classpath . -d WEB-INF/classes/ src/bdd/modeles/*.java
javac -classpath .:./WEB-INF/lib/jus.util.jar:./WEB-INF/lib/ojdbc14.jar:./WEB-INF/classes/ -d WEB-INF/classes/ src/bdd/accessBD/*.java	
javac -classpath .:../../lib/servlet-api.jar:./WEB-INF/lib/bd.jar:./WEB-INF/classes/ -d WEB-INF/classes/ src/servlets/utils/*.java	
javac -classpath .:../../lib/servlet-api.jar:./WEB-INF/lib/bd.jar:./WEB-INF/classes/ -d WEB-INF/classes/ src/servlets/*.java
