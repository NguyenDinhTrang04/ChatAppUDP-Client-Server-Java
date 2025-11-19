@echo off
REM ========================================
REM Script chay tung test case - OPTIMIZED
REM Method: Boundary Value Analysis (BVA)
REM Total: 12 test cases (Giam tu 17 = -29%)
REM ========================================

echo ========================================
echo CHATAPPUDP - RUN INDIVIDUAL TEST CASES
echo OPTIMIZED WITH BVA (12 Tests)
echo ========================================
echo.

:MENU
echo.
echo Chon test case muon chay:
echo.
echo === ADD CLIENT TEST CASES (4 tests) ===
echo 1.  TC_AC_001 - Add first client (0 to 1)
echo 2.  TC_AC_002 - Add duplicate username
echo 3.  TC_AC_003 - Concurrent add (10 threads)
echo 4.  TC_AC_004 - Add null username
echo.
echo === REMOVE CLIENT TEST CASES (3 tests) ===
echo 5.  TC_RC_001 - Remove only client (1 to 0)
echo 6.  TC_RC_002 - Concurrent remove (10 threads)
echo 7.  TC_RC_003 - Remove non-existent client
echo.
echo === KICK USER TEST CASES (5 tests) ===
echo 8.  TC_KU_001 - Kick existing user
echo 9.  TC_KU_002 - Kick non-existent user
echo 10. TC_KU_003 - Kick null username
echo 11. TC_KU_004 - Kick already kicked user
echo 12. TC_KU_005 - Kick multiple users (sequential)
echo.
echo === RUN MULTIPLE TESTS ===
echo 20. Run ALL ADD tests (4 tests)
echo 21. Run ALL REMOVE tests (3 tests)
echo 22. Run ALL KICK tests (5 tests)
echo 23. Run ALL tests (12 tests)
echo.
echo 0. Exit
echo.

set /p choice="Nhap lua chon cua ban (0-23): "

if "%choice%"=="0" goto END
if "%choice%"=="1" goto TC_AC_001
if "%choice%"=="2" goto TC_AC_002
if "%choice%"=="3" goto TC_AC_003
if "%choice%"=="4" goto TC_AC_004
if "%choice%"=="5" goto TC_RC_001
if "%choice%"=="6" goto TC_RC_002
if "%choice%"=="7" goto TC_RC_003
if "%choice%"=="8" goto TC_KU_001
if "%choice%"=="9" goto TC_KU_002
if "%choice%"=="10" goto TC_KU_003
if "%choice%"=="11" goto TC_KU_004
if "%choice%"=="12" goto TC_KU_005
if "%choice%"=="20" goto RUN_ALL_ADD
if "%choice%"=="21" goto RUN_ALL_REMOVE
if "%choice%"=="22" goto RUN_ALL_KICK
if "%choice%"=="23" goto RUN_ALL_TESTS

echo Lua chon khong hop le!
goto MENU

REM ========================================
REM ADD CLIENT TEST CASES (4 TESTS - OPTIMIZED)
REM ========================================

:TC_AC_001
echo.
echo ========================================
echo Running: TC_AC_001 - Add first client
echo BVA: Boundary 0 to 1 client
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testAddClient_Success"
pause
goto MENU

:TC_AC_002
echo.
echo ========================================
echo Running: TC_AC_002 - Add duplicate username
echo BVA: Boundary duplicate (unique to duplicate)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testAddClient_DuplicateUsername"
pause
goto MENU

:TC_AC_003
echo.
echo ========================================
echo Running: TC_AC_003 - Concurrent add operations
echo BVA: Boundary concurrency (0 to 10 threads)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testConcurrentAddOperations"
pause
goto MENU

:TC_AC_004
echo.
echo ========================================
echo Running: TC_AC_004 - Add null username
echo BVA: Boundary null value
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testAddClient_NullUsername"
pause
goto MENU

REM ========================================
REM REMOVE CLIENT TEST CASES (3 TESTS - OPTIMIZED)
REM ========================================

:TC_RC_001
echo.
echo ========================================
echo Running: TC_RC_001 - Remove only client
echo BVA: Boundary 1 to 0 client
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testRemoveClient_Success"
pause
goto MENU

:TC_RC_002
echo.
echo ========================================
echo Running: TC_RC_002 - Concurrent remove operations
echo BVA: Boundary concurrency (10 threads to 0)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testConcurrentRemoveOperations"
pause
goto MENU

:TC_RC_003
echo.
echo ========================================
echo Running: TC_RC_003 - Remove non-existent client
echo BVA: Boundary state (exists to not-exists)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testRemoveClient_NonExistent"
pause
goto MENU

REM ========================================
REM KICK USER TEST CASES (5 TESTS - OPTIMIZED)
REM ========================================

:TC_KU_001
echo.
echo ========================================
echo Running: TC_KU_001 - Kick existing user
echo BVA: Boundary state (online to kicked)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickUser_Success"
pause
goto MENU

:TC_KU_002
echo.
echo ========================================
echo Running: TC_KU_002 - Kick non-existent user
echo BVA: Boundary state (not-exists)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickUser_NonExistent"
pause
goto MENU

:TC_KU_003
echo.
echo ========================================
echo Running: TC_KU_003 - Kick null username
echo BVA: Boundary null value
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickUser_NullUsername"
pause
goto MENU

:TC_KU_004
echo.
echo ========================================
echo Running: TC_KU_004 - Kick already kicked user
echo BVA: Boundary double action (kicked to kicked)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickUser_AlreadyKicked"
pause
goto MENU

:TC_KU_005
echo.
echo ========================================
echo Running: TC_KU_005 - Kick multiple users
echo BVA: Boundary quantity (n to 0 sequential)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickMultipleUsers"
pause
goto MENU

REM ========================================
REM RUN MULTIPLE TESTS - OPTIMIZED
REM ========================================

:RUN_ALL_ADD
echo.
echo ========================================
echo Running ALL ADD CLIENT Tests (4 tests)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testAddClient_Success"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testAddClient_DuplicateUsername"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testConcurrentAddOperations"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testAddClient_NullUsername"
echo.
echo ========================================
echo Completed 4 ADD CLIENT tests
echo ========================================
pause
goto MENU

:RUN_ALL_REMOVE
echo.
echo ========================================
echo Running ALL REMOVE CLIENT Tests (3 tests)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testRemoveClient_Success"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testConcurrentRemoveOperations"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testRemoveClient_NonExistent"
echo.
echo ========================================
echo Completed 3 REMOVE CLIENT tests
echo ========================================
pause
goto MENU

:RUN_ALL_KICK
echo.
echo ========================================
echo Running ALL KICK USER Tests (5 tests)
echo ========================================
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickUser_Success"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickUser_NonExistent"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickUser_NullUsername"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickUser_AlreadyKicked"
powershell -ExecutionPolicy Bypass -File run-single-test.ps1 -TestMethod "testKickMultipleUsers"
echo.
echo ========================================
echo Completed 5 KICK USER tests
echo ========================================
pause
goto MENU

:RUN_ALL_TESTS
echo.
echo ========================================
echo Running ALL Tests (12 tests - OPTIMIZED)
echo Method: Boundary Value Analysis (BVA)
echo ========================================
java -cp "bin;test-bin;lib/*" org.junit.runner.JUnitCore server.ServerControllerClientTest
pause
goto MENU

:END
echo.
echo ========================================
echo OPTIMIZED TEST SUITE SUMMARY
echo ========================================
echo Total Test Cases: 12
echo - ADD CLIENT: 4 tests
echo - REMOVE CLIENT: 3 tests
echo - KICK USER: 5 tests
echo Method: Boundary Value Analysis (BVA)
echo Coverage: 100%% BVA boundaries
echo ========================================
echo Cam on ban da su dung! Goodbye!
exit
