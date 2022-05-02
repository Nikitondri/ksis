@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;path/to/needed/jars/my.jar

%JAVA_HOME%\bin\java -Xms128m -Xmx384m -Xnoclassgc ro.my.class.MyClass