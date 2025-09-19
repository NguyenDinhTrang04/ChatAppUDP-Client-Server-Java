@echo off
title ChatApp UDP - Client
echo Starting ChatApp UDP Client...
echo.

REM Check if JAR file exists
if not exist "ChatAppUDP.jar" (
    echo Error: ChatAppUDP.jar not found!
    echo Please make sure the JAR file is in the same directory.
    pause
    exit /b 1
)

REM Start client
java -cp ChatAppUDP.jar client.ClientApp

echo.
echo Client stopped.
pause