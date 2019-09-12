@echo off

cd src 
C:\prg\jdk8\bin\javac -d C:\prg\myapps\UpDown\build\classes ServerLogDiNavigazione.java -cp C:\prg\myapps\UpDown\build\classes

ECHO ************************* Registro Log *************************
c:\prg\jdk8\bin\java -classpath C:\prg\myapps\UpDown\build\classes ServerLogDiNavigazione

pause