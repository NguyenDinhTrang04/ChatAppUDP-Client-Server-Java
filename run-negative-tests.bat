@echo off
cd /d "%~dp0"
echo.
echo ============================================================
echo   COMPLETE TEST SUITE - POSITIVE + NEGATIVE TESTS
echo   - 6 Positive tests: Test cac truong hop dung
echo   - 22 Negative tests: Test cac truong hop loi
echo   - Total: 28 test cases
echo ============================================================
echo.

echo [Compile source...]
javac -encoding UTF-8 -d bin src\common\*.java 2>nul

echo [Compile complete test suite...]
javac -encoding UTF-8 -d bin -cp "bin;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" test\common\NegativeTests.java 2>nul

if errorlevel 1 (
    echo ERROR: Compile failed!
    pause
    exit /b 1
)

echo [Compile OK!]
echo.
echo ============================================================
echo   RUNNING 28 TESTS (6 POSITIVE + 22 NEGATIVE)
echo ============================================================
echo.

java -cp "bin;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore common.NegativeTests

echo.
echo ============================================================
echo   TEST COMPLETED!
echo ============================================================
pause
