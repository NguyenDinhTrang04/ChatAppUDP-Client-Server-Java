@echo off
title ChatApp UDP - Create Deployment Package
echo ========================================
echo    ChatApp UDP - Deployment Package
echo ========================================
echo.

set DEPLOY_DIR=ChatAppUDP-Deploy
set VERSION=v1.0

echo Creating deployment package...

REM Create deployment directory
if exist "%DEPLOY_DIR%" rmdir /s /q "%DEPLOY_DIR%"
mkdir "%DEPLOY_DIR%"

REM Copy essential files
echo Copying files...
copy "ChatAppUDP.jar" "%DEPLOY_DIR%\"
copy "start-launcher.bat" "%DEPLOY_DIR%\"
copy "start-server.bat" "%DEPLOY_DIR%\"
copy "start-client.bat" "%DEPLOY_DIR%\"
copy "README-DEPLOY.md" "%DEPLOY_DIR%\"
copy "LICENSE" "%DEPLOY_DIR%\"

REM Create version info
echo ChatApp UDP %VERSION% > "%DEPLOY_DIR%\VERSION.txt"
echo Built on %date% %time% >> "%DEPLOY_DIR%\VERSION.txt"

echo.
echo Deployment package created in: %DEPLOY_DIR%\
echo.
echo Package contents:
dir "%DEPLOY_DIR%"
echo.
echo You can now ZIP this folder and distribute it.
echo Users only need Java installed to run the application.
echo.
pause