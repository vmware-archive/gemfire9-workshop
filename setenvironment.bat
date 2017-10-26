@echo OFF

rem set the IP address of your machine
set IP=192.168.1.129

set GEMFIRE_HOME=D:\GemFire\Pivotal_GemFire_810_b50625_Windows
set GEMFIRE=%GEMFIRE_HOME%

rem set the location of your JDK home. Comment this if already set in your environment variables
set JAVA_HOME=D:\Java\64\jdk1.8.0_40
set GF_JAVA=%JAVA_HOME%\bin\java

rem set JAVA_HOME in the path if it is not already there
echo %PATH%|findstr /C:"%JAVA_HOME%" >nul 2>&1
if %ERRORLEVEL% == 1  set PATH=%PATH%;%JAVA_HOME%\bin

rem set GEMFIRE in the path if it is not already there
echo %PATH%|findstr /C:"%GEMFIRE%" >nul 2>&1
if %ERRORLEVEL% == 1 set PATH=%PATH%;%GEMFIRE%\bin
