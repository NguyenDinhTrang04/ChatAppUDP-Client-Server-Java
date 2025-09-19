@echo off
title ChatApp UDP - Build Script
echo ========================================
echo       ChatApp UDP - Build Script
echo ========================================
echo.

echo [1/4] Cleaning old build files...
if exist "bin" rmdir /s /q bin
if exist "ChatAppUDP.jar" del ChatAppUDP.jar
mkdir bin

echo [2/4] Compiling Java source files...
cd src
javac -d ..\bin Main.java common\*.java server\*.java server\gui\*.java client\*.java client\gui\*.java

if %errorlevel% neq 0 (
    echo ERROR: Compilation failed!
    cd ..
    pause
    exit /b 1
)

cd ..
echo Compilation successful!

echo [3/4] Creating JAR file...
"C:\Program Files\Java\jdk-24\bin\jar.exe" cfm ChatAppUDP.jar MANIFEST.MF -C bin .

if %errorlevel% neq 0 (
    echo ERROR: JAR creation failed!
    pause
    exit /b 1
)

echo [4/4] Build completed successfully!
echo.
echo Files created:
echo - ChatAppUDP.jar (Main application)
echo - start-launcher.bat (Easy launcher)
echo - start-server.bat (Direct server start)
echo - start-client.bat (Direct client start)
echo.
echo You can now distribute these files to run the application.
echo.
pause