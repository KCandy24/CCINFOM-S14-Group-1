del /s /q bin/*
javac -cp .;lib/* src/controller/Driver.java -d bin/

copy src\view\gui\records\*.json bin\src\view\gui\records\
copy src\view\gui\reports\*.json bin\src\view\gui\reports\
copy src\view\gui\transactions\*.json bin\src\view\gui\transactions\

cd bin
java -cp .;../lib/* src/controller/Driver  < ../password.in
cd ..