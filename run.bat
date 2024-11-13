del /s /q *.class

javac -cp .;lib/* src/controller/Driver.java -d bin/

copy src\view\gui\*.json bin\src\view\gui\

cd bin
java -cp .;../lib/* src/controller/Driver  < ../password.in
cd ..