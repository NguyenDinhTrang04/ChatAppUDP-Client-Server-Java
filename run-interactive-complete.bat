@echo off
cd /d "%~dp0"
echo.
echo ============================================================
echo   INTERACTIVE COMPLETE TEST
echo   - Test ca POSITIVE va NEGATIVE
echo   - Chon test case 1-6
echo   - Nhap du lieu tuy y
echo ============================================================
echo.

echo [Compile source...]
javac -encoding UTF-8 -d bin src\common\*.java 2>nul

echo [Compile interactive test...]
javac -encoding UTF-8 -d bin -cp bin test\common\InteractiveCompleteTest.java 2>nul

if errorlevel 1 (
    echo ERROR: Compile failed!
    pause
    exit /b 1
)

echo [Compile OK!]
echo.
echo ============================================================
echo   STARTING INTERACTIVE TEST
echo ============================================================
echo.

java -cp bin common.InteractiveCompleteTest

echo.
pause
