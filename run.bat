@echo off
echo Compiling UDP Chat Application...

REM Create output directory
if not exist "bin" mkdir bin

REM Compile all Java files
cd src
javac -d ..\bin common\*.java server\*.java server\gui\*.java client\*.java client\gui\*.java Main.java

if %errorlevel% == 0 (
    echo Compilation successful!
    echo.
    echo Choose an option:
    echo 1. Run Main Launcher
    echo 2. Run Server
    echo 3. Run Client
    echo 4. Exit
    echo.
    set /p choice="Enter your choice (1-4): "
    
    cd ..\bin
    if "%choice%"=="1" (
        echo Starting Main Launcher...
        java Main
    ) else if "%choice%"=="2" (
        echo Starting Server...
        java server.ServerApp
    ) else if "%choice%"=="3" (
        echo Starting Client...
        java client.ClientApp
    ) else (
        echo Exiting...
    )
) else (
    echo Compilation failed! Please check for errors.
    cd ..
    pause
)
