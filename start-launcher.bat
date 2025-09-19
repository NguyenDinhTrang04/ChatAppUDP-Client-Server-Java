@echo off
title ChatApp UDP - Launcher
echo ========================================
echo       ChatApp UDP - Main Launcher
echo ========================================
echo.

REM Check if JAR file exists
if not exist "ChatAppUDP.jar" (
    echo Error: ChatAppUDP.jar not found!
    echo Please make sure the JAR file is in the same directory.
    pause
    exit /b 1
)

REM Start main launcher
java -jar ChatAppUDP.jar

echo.
echo Application stopped.
pause