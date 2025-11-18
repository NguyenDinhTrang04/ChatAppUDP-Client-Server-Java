@echo off
REM ========================================
REM Script chay tung test case ADD/REMOVE
REM ========================================

echo ========================================
echo CHATAPPUDP - RUN INDIVIDUAL TEST CASES
echo ========================================
echo.

:MENU
echo.
echo Chon test case muon chay:
echo.
echo === ADD CLIENT TEST CASES ===
echo 1.  TC_AC_001 - Add first client
echo 2.  TC_AC_002 - Add multiple clients
echo 3.  TC_AC_003 - Add null username
echo 4.  TC_AC_004 - Add empty username
echo 5.  TC_AC_005 - Add duplicate username
echo.
echo === REMOVE CLIENT TEST CASES ===
echo 6.  TC_RC_001 - Remove only client
echo 7.  TC_RC_002 - Remove one of many
echo 8.  TC_RC_003 - Remove non-existent client
echo 9.  TC_RC_004 - Remove with null username
echo 10. TC_RC_005 - Remove with empty username
echo 11. TC_RC_006 - Remove all clients sequentially
echo 12. TC_RC_007 - Concurrent remove operations
echo.
echo === KICK USER TEST CASES ===
echo 13. TC_KU_001 - Kick existing user
echo 14. TC_KU_002 - Kick non-existent user
echo.
echo === RUN MULTIPLE TESTS ===
echo 20. Run ALL ADD tests
echo 21. Run ALL REMOVE tests
echo 22. Run ALL KICK tests
echo 23. Run ALL tests (ADD + REMOVE + KICK)
echo.
echo 0. Exit
echo.

set /p choice="Nhap lua chon cua ban (0-23): "

if "%choice%"=="0" goto END
if "%choice%"=="1" goto TC_AC_001
if "%choice%"=="2" goto TC_AC_002
if "%choice%"=="3" goto TC_AC_003
if "%choice%"=="4" goto TC_AC_004
if "%choice%"=="5" goto TC_AC_005
if "%choice%"=="6" goto TC_RC_001
if "%choice%"=="7" goto TC_RC_002
if "%choice%"=="8" goto TC_RC_003
if "%choice%"=="9" goto TC_RC_004
if "%choice%"=="10" goto TC_RC_005
if "%choice%"=="11" goto TC_RC_006
if "%choice%"=="12" goto TC_RC_007
if "%choice%"=="13" goto TC_KU_001
if "%choice%"=="14" goto TC_KU_002
if "%choice%"=="20" goto RUN_ALL_ADD
if "%choice%"=="21" goto RUN_ALL_REMOVE
if "%choice%"=="22" goto RUN_ALL_KICK
if "%choice%"=="23" goto RUN_ALL_TESTS

echo Lua chon khong hop le!
goto MENU

REM ========================================
REM ADD CLIENT TEST CASES
REM ========================================

:TC_AC_001
echo.
echo ========================================
echo Running: TC_AC_001 - Add first client
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_Success
pause
goto MENU

:TC_AC_002
echo.
echo ========================================
echo Running: TC_AC_002 - Add multiple clients
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddMultipleClients
pause
goto MENU

:TC_AC_003
echo.
echo ========================================
echo Running: TC_AC_003 - Add null username
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_NullUsername
pause
goto MENU

:TC_AC_004
echo.
echo ========================================
echo Running: TC_AC_004 - Add empty username
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_EmptyUsername
pause
goto MENU

:TC_AC_005
echo.
echo ========================================
echo Running: TC_AC_005 - Add duplicate username
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_DuplicateUsername
pause
goto MENU

REM ========================================
REM REMOVE CLIENT TEST CASES
REM ========================================

:TC_RC_001
echo.
echo ========================================
echo Running: TC_RC_001 - Remove only client
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_OnlyClient
pause
goto MENU

:TC_RC_002
echo.
echo ========================================
echo Running: TC_RC_002 - Remove one of many
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_OneOfMany
pause
goto MENU

:TC_RC_003
echo.
echo ========================================
echo Running: TC_RC_003 - Remove non-existent
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_NonExistent
pause
goto MENU

:TC_RC_004
echo.
echo ========================================
echo Running: TC_RC_004 - Remove null username
echo ========================================
echo [Test chua duoc implement]
pause
goto MENU

:TC_RC_005
echo.
echo ========================================
echo Running: TC_RC_005 - Remove empty username
echo ========================================
echo [Test chua duoc implement]
pause
goto MENU

:TC_RC_006
echo.
echo ========================================
echo Running: TC_RC_006 - Remove all sequentially
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveAllClients
pause
goto MENU

:TC_RC_007
echo.
echo ========================================
echo Running: TC_RC_007 - Concurrent remove
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testConcurrentRemove
pause
goto MENU

REM ========================================
REM KICK USER TEST CASES
REM ========================================

:TC_KU_001
echo.
echo ========================================
echo Running: TC_KU_001 - Kick existing user
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testKickUser_Success
pause
goto MENU

:TC_KU_002
echo.
echo ========================================
echo Running: TC_KU_002 - Kick non-existent
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testKickUser_NonExistent
pause
goto MENU

REM ========================================
REM RUN MULTIPLE TESTS
REM ========================================

:RUN_ALL_ADD
echo.
echo ========================================
echo Running ALL ADD CLIENT Tests
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_Success
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddMultipleClients
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_NullUsername
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_EmptyUsername
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_DuplicateUsername
pause
goto MENU

:RUN_ALL_REMOVE
echo.
echo ========================================
echo Running ALL REMOVE CLIENT Tests
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_OnlyClient
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_OneOfMany
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_NonExistent
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveAllClients
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testConcurrentRemove
pause
goto MENU

:RUN_ALL_KICK
echo.
echo ========================================
echo Running ALL KICK USER Tests
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testKickUser_Success
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testKickUser_NonExistent
pause
goto MENU

:RUN_ALL_TESTS
echo.
echo ========================================
echo Running ALL Tests (ADD + REMOVE + KICK)
echo ========================================
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest
pause
goto MENU

:END
echo.
echo Cam on ban da su dung! Goodbye!
exit
