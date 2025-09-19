@echo off
title ChatApp UDP - Server
echo Starting ChatApp UDP Server...
echo.

REM Check if JAR file exists
if not exist "ChatAppUDP.jar" (
    echo Error: ChatAppUDP.jar not found!
    echo Please make sure the JAR file is in the same directory.
    pause
    exit /b 1
)

REM Start server
java -cp ChatAppUDP.jar server.ServerApp

echo.
echo Server stopped.
pause