del /s /q *.class
set cp=.;lib/*
javac -cp %cp% src/controller/Driver.java
java -cp %cp% src/controller/Driver.java < password.in