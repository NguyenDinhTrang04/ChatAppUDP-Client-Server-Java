# RUN INDIVIDUAL TEST CASES - Quick Reference

## 📋 HƯỚNG DẪN SỬ DỤNG

### Cách 1: Sử dụng file BAT (Command Prompt)

```cmd
run-individual-tests.bat
```

### Cách 2: Sử dụng file PowerShell

```powershell
.\run-individual-tests.ps1
```

### Cách 3: Chạy trực tiếp từng test case

#### ADD CLIENT TESTS

**TC_AC_001 - Add first client:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_Success
```

**TC_AC_002 - Add multiple clients:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddMultipleClients
```

**TC_AC_003 - Add null username:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_NullUsername
```

**TC_AC_004 - Add empty username:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_EmptyUsername
```

**TC_AC_005 - Add duplicate username:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_DuplicateUsername
```

#### REMOVE CLIENT TESTS

**TC_RC_001 - Remove only client:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_OnlyClient
```

**TC_RC_002 - Remove one of many:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_OneOfMany
```

**TC_RC_003 - Remove non-existent:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveClient_NonExistent
```

**TC_RC_006 - Remove all clients sequentially:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testRemoveAllClients
```

**TC_RC_007 - Concurrent remove operations:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testConcurrentRemove
```

#### KICK USER TESTS

**TC_KU_001 - Kick existing user:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testKickUser_Success
```

**TC_KU_002 - Kick non-existent user:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testKickUser_NonExistent
```

#### CONCURRENCY TESTS

**TC_TS_001 - Concurrent add operations:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testConcurrentAddRemove
```

#### RUN ALL TESTS

**Run entire test suite:**

```cmd
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest
```

---

## 📊 TEST CASE MAPPING

| Test ID   | Method Name                     | Category      | Status  |
| --------- | ------------------------------- | ------------- | ------- |
| TC_AC_001 | testAddClient_Success           | ADD CLIENT    | ✅ PASS |
| TC_AC_002 | testAddMultipleClients          | ADD CLIENT    | ✅ PASS |
| TC_AC_003 | testAddClient_NullUsername      | ADD CLIENT    | ✅ PASS |
| TC_AC_004 | testAddClient_EmptyUsername     | ADD CLIENT    | ✅ PASS |
| TC_AC_005 | testAddClient_DuplicateUsername | ADD CLIENT    | ✅ PASS |
| TC_RC_001 | testRemoveClient_OnlyClient     | REMOVE CLIENT | ✅ PASS |
| TC_RC_002 | testRemoveClient_OneOfMany      | REMOVE CLIENT | ✅ PASS |
| TC_RC_003 | testRemoveClient_NonExistent    | REMOVE CLIENT | ✅ PASS |
| TC_RC_004 | [Not implemented]               | REMOVE CLIENT | ⚠️ TODO |
| TC_RC_005 | [Not implemented]               | REMOVE CLIENT | ⚠️ TODO |
| TC_RC_006 | testRemoveAllClients            | REMOVE CLIENT | ✅ PASS |
| TC_RC_007 | testConcurrentRemove            | REMOVE CLIENT | ✅ PASS |
| TC_KU_001 | testKickUser_Success            | KICK USER     | ✅ PASS |
| TC_KU_002 | testKickUser_NonExistent        | KICK USER     | ✅ PASS |
| TC_TS_001 | testConcurrentAddRemove         | CONCURRENCY   | ✅ PASS |

---

## 🎯 LƯU Ý

1. **Compile tests trước khi chạy:**

    ```cmd
    javac -cp "bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" -d test-bin test/server/*.java
    ```

2. **Kiểm tra JUnit libraries:**

    - junit-4.13.2.jar phải có trong thư mục lib/
    - hamcrest-core-1.3.jar phải có trong thư mục lib/

3. **Test classes phải compile thành công:**
    - ServerControllerClientTest.class trong test-bin/server/
    - ServerController.class trong bin/server/

---

## 📝 TIPS

-   Sử dụng menu interactive (file .bat hoặc .ps1) để chạy dễ dàng hơn
-   Mỗi test case chạy độc lập, không ảnh hưởng lẫn nhau
-   Kết quả hiển thị ngay sau khi test hoàn thành
-   Pass rate: 100% (13/13 tests implemented)

---

**Last Updated:** 2025-11-19  
**Version:** 1.0
