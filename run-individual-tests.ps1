# Script chạy từng test case riêng lẻ - PowerShell version

function Show-Menu {
    Clear-Host
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "CHATAPPUDP - RUN INDIVIDUAL TEST CASES" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Chọn test case muốn chạy:" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "=== ADD CLIENT TEST CASES ===" -ForegroundColor Green
    Write-Host "1.  TC_AC_001 - Add first client"
    Write-Host "2.  TC_AC_002 - Add multiple clients"
    Write-Host "3.  TC_AC_003 - Add null username"
    Write-Host "4.  TC_AC_004 - Add empty username"
    Write-Host "5.  TC_AC_005 - Add duplicate username"
    Write-Host ""
    Write-Host "=== REMOVE CLIENT TEST CASES ===" -ForegroundColor Green
    Write-Host "6.  TC_RC_001 - Remove only client"
    Write-Host "7.  TC_RC_002 - Remove one of many"
    Write-Host "8.  TC_RC_003 - Remove non-existent client"
    Write-Host "9.  TC_RC_004 - Remove with null username"
    Write-Host "10. TC_RC_005 - Remove with empty username"
    Write-Host "11. TC_RC_006 - Remove all clients sequentially"
    Write-Host "12. TC_RC_007 - Concurrent remove operations"
    Write-Host ""
    Write-Host "=== KICK USER TEST CASES ===" -ForegroundColor Green
    Write-Host "13. TC_KU_001 - Kick existing user"
    Write-Host "14. TC_KU_002 - Kick non-existent user"
    Write-Host ""
    Write-Host "=== RUN MULTIPLE TESTS ===" -ForegroundColor Magenta
    Write-Host "20. Run ALL ADD tests"
    Write-Host "21. Run ALL REMOVE tests"
    Write-Host "22. Run ALL KICK tests"
    Write-Host "23. Run ALL tests (ADD + REMOVE + KICK)"
    Write-Host ""
    Write-Host "0. Exit" -ForegroundColor Red
    Write-Host ""
}

function Run-Test {
    param (
        [string]$TestName,
        [string]$TestMethod
    )
    
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Running: $TestName" -ForegroundColor Yellow
    Write-Host "========================================" -ForegroundColor Cyan
    
    $command = "java -cp `"bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar`" org.junit.runner.JUnitCore server.ServerControllerClientTest#$TestMethod"
    
    Invoke-Expression $command
    
    Write-Host ""
    Write-Host "Press any key to continue..." -ForegroundColor Gray
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
}

function Run-MultipleTests {
    param (
        [string]$Category,
        [string[]]$TestMethods
    )
    
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Running ALL $Category Tests" -ForegroundColor Yellow
    Write-Host "========================================" -ForegroundColor Cyan
    
    foreach ($method in $TestMethods) {
        Write-Host ""
        Write-Host "Running: $method" -ForegroundColor Green
        $command = "java -cp `"bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar`" org.junit.runner.JUnitCore server.ServerControllerClientTest#$method"
        Invoke-Expression $command
    }
    
    Write-Host ""
    Write-Host "Press any key to continue..." -ForegroundColor Gray
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
}

# Main Loop
while ($true) {
    Show-Menu
    
    $choice = Read-Host "Nhập lựa chọn của bạn (0-23)"
    
    switch ($choice) {
        "0" {
            Write-Host ""
            Write-Host "Cảm ơn bạn đã sử dụng! Goodbye!" -ForegroundColor Green
            exit
        }
        
        # ADD CLIENT TESTS
        "1" { Run-Test "TC_AC_001 - Add first client" "testAddClient_Success" }
        "2" { Run-Test "TC_AC_002 - Add multiple clients" "testAddMultipleClients" }
        "3" { Run-Test "TC_AC_003 - Add null username" "testAddClient_NullUsername" }
        "4" { Run-Test "TC_AC_004 - Add empty username" "testAddClient_EmptyUsername" }
        "5" { Run-Test "TC_AC_005 - Add duplicate username" "testAddClient_DuplicateUsername" }
        
        # REMOVE CLIENT TESTS
        "6" { Run-Test "TC_RC_001 - Remove only client" "testRemoveClient_OnlyClient" }
        "7" { Run-Test "TC_RC_002 - Remove one of many" "testRemoveClient_OneOfMany" }
        "8" { Run-Test "TC_RC_003 - Remove non-existent" "testRemoveClient_NonExistent" }
        "9" { 
            Write-Host ""
            Write-Host "[Test chưa được implement]" -ForegroundColor Yellow
            Write-Host "Press any key to continue..." -ForegroundColor Gray
            $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        }
        "10" { 
            Write-Host ""
            Write-Host "[Test chưa được implement]" -ForegroundColor Yellow
            Write-Host "Press any key to continue..." -ForegroundColor Gray
            $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        }
        "11" { Run-Test "TC_RC_006 - Remove all sequentially" "testRemoveAllClients" }
        "12" { Run-Test "TC_RC_007 - Concurrent remove" "testConcurrentRemove" }
        
        # KICK USER TESTS
        "13" { Run-Test "TC_KU_001 - Kick existing user" "testKickUser_Success" }
        "14" { Run-Test "TC_KU_002 - Kick non-existent" "testKickUser_NonExistent" }
        
        # RUN MULTIPLE TESTS
        "20" {
            Run-MultipleTests "ADD CLIENT" @(
                "testAddClient_Success",
                "testAddMultipleClients",
                "testAddClient_NullUsername",
                "testAddClient_EmptyUsername",
                "testAddClient_DuplicateUsername"
            )
        }
        "21" {
            Run-MultipleTests "REMOVE CLIENT" @(
                "testRemoveClient_OnlyClient",
                "testRemoveClient_OneOfMany",
                "testRemoveClient_NonExistent",
                "testRemoveAllClients",
                "testConcurrentRemove"
            )
        }
        "22" {
            Run-MultipleTests "KICK USER" @(
                "testKickUser_Success",
                "testKickUser_NonExistent"
            )
        }
        "23" {
            Write-Host ""
            Write-Host "========================================" -ForegroundColor Cyan
            Write-Host "Running ALL Tests (ADD + REMOVE + KICK)" -ForegroundColor Yellow
            Write-Host "========================================" -ForegroundColor Cyan
            
            $command = "java -cp `"bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar`" org.junit.runner.JUnitCore server.ServerControllerClientTest"
            Invoke-Expression $command
            
            Write-Host ""
            Write-Host "Press any key to continue..." -ForegroundColor Gray
            $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        }
        
        default {
            Write-Host ""
            Write-Host "Lựa chọn không hợp lệ!" -ForegroundColor Red
            Start-Sleep -Seconds 2
        }
    }
}
