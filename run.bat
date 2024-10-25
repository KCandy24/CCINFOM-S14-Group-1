@echo off

del /s /q *.class
javac src/controller/Driver.java
java src/controller/Driver.java