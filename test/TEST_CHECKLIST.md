# 📋 BẢNG KIỂM THỬ CHATAPPUDP - CLIENT MANAGEMENT

## 🎯 **TỔNG QUAN**

Bảng kiểm thử cho 3 chức năng quản lý client:

-   **ADD CLIENT** - Thêm client vào server
-   **REMOVE CLIENT** - Xóa client khỏi server
-   **KICK USER** - Kick client (remove + thông báo)

---

## 📊 **QUẢN LÝ CLIENT (CLIENT MANAGEMENT)**

### **A. Chức năng ADD CLIENT**

| Test ID   | Mô tả Test Case                            | Input                            | Expected Output                       | Status  | Priority |
| --------- | ------------------------------------------ | -------------------------------- | ------------------------------------- | ------- | -------- |
| TC_AC_001 | Thêm client đầu tiên                       | username="user1", valid_address  | Client count=1, list contains "user1" | ✅ PASS | High     |
| TC_AC_002 | Thêm client thứ 2,3,4...                   | Multiple valid users             | Client count increases correctly      | ✅ PASS | High     |
| TC_AC_003 | Thêm client với username null              | username=null                    | Reject/Handle gracefully              | ✅ PASS | Medium   |
| TC_AC_004 | Thêm client với username empty             | username=""                      | Handle appropriately                  | ✅ PASS | Medium   |
| TC_AC_005 | Thêm client trùng username                 | Same username, different address | Reject duplicate                      | ✅ PASS | High     |
| TC_AC_006 | Thêm client với address null               | valid username, address=null     | Handle error gracefully               | ⚠️ TODO | Medium   |
| TC_AC_007 | Thêm client với username có ký tự đặc biệt | username="user@123"              | Accept/Reject based on policy         | ⚠️ TODO | Low      |
| TC_AC_008 | Thêm client với username rất dài           | username=1000 chars              | Handle large username                 | ⚠️ TODO | Low      |
| TC_AC_009 | Thêm nhiều clients đồng thời               | Concurrent add operations        | Thread safety maintained              | ✅ PASS | High     |
| TC_AC_010 | Thêm client khi server đang stop           | Add after server.stop()          | Handle gracefully                     | ⚠️ TODO | Medium   |

### **B. Chức năng REMOVE CLIENT**

| Test ID   | Mô tả Test Case               | Input                                 | Expected Output             | Status  | Priority |
| --------- | ----------------------------- | ------------------------------------- | --------------------------- | ------- | -------- |
| TC_RC_001 | Xóa client duy nhất           | Remove only client                    | Empty list, count=0         | ✅ PASS | High     |
| TC_RC_002 | Xóa 1 trong nhiều clients     | Remove user2 from [user1,user2,user3] | List=[user1,user3], count=2 | ✅ PASS | High     |
| TC_RC_003 | Xóa client không tồn tại      | username="notexist"                   | No change to list           | ✅ PASS | High     |
| TC_RC_004 | Xóa client với username null  | username=null                         | Handle gracefully           | ⚠️ TODO | Medium   |
| TC_RC_005 | Xóa client với username empty | username=""                           | Handle gracefully           | ⚠️ TODO | Medium   |
| TC_RC_006 | Xóa tất cả clients tuần tự    | Remove all one by one                 | Eventually empty list       | ✅ PASS | Medium   |
| TC_RC_007 | Xóa nhiều clients đồng thời   | Concurrent remove operations          | Thread safety maintained    | ✅ PASS | High     |
| TC_RC_008 | Xóa client khi server stop    | Remove after server.stop()            | Handle gracefully           | ⚠️ TODO | Medium   |

### **C. Chức năng KICK USER**

| Test ID   | Mô tả Test Case            | Input                | Expected Output                  | Status  | Priority |
| --------- | -------------------------- | -------------------- | -------------------------------- | ------- | -------- |
| TC_KU_001 | Kick client tồn tại        | username="user1"     | User removed + notification sent | ✅ PASS | High     |
| TC_KU_002 | Kick client không tồn tại  | username="notexist"  | No error, no change              | ✅ PASS | High     |
| TC_KU_003 | Kick với username null     | username=null        | Handle gracefully                | ⚠️ TODO | Medium   |
| TC_KU_004 | Kick client sau khi kicked | Kick same user twice | Second kick handled gracefully   | ⚠️ TODO | Medium   |
| TC_KU_005 | Kick notification delivery | Kick user1           | User1 receives kick notification | ⚠️ TODO | High     |

---

## 📈 **TEST COVERAGE SUMMARY**

### **Test Cases by Function**

| **Chức năng**     | **Total Tests** | **Passed** | **Failed** | **TODO** | **Coverage** |
| ----------------- | --------------- | ---------- | ---------- | -------- | ------------ |
| **ADD CLIENT**    | 10              | 5          | 0          | 5        | 50%          |
| **REMOVE CLIENT** | 8               | 5          | 0          | 3        | 63%          |
| **KICK USER**     | 5               | 2          | 0          | 3        | 40%          |
| **TOTAL**         | **23**          | **12**     | **0**      | **11**   | **52%**      |

### **Priority Breakdown**

| **Priority** | **Count** | **Completed** | **Remaining** |
| ------------ | --------- | ------------- | ------------- |
| **High**     | 15        | 10            | 5             |
| **Medium**   | 7         | 2             | 5             |
| **Low**      | 1         | 0             | 1             |

---

## 📝 **TEST EXECUTION COMMANDS**

### **Run Individual Tests:**

```bash
# Run specific test method
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest#testAddClient_Success

# Run all ADD CLIENT tests (TC_AC_001 - TC_AC_005)
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest

# Run all client management tests
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerTestSuite
```

### **Use Interactive Scripts:**

```bash
# Windows CMD menu
run-individual-tests.bat

# PowerShell with colored output
.\run-individual-tests.ps1
```

### **Compile Tests:**

```bash
javac -cp "bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" -d test-bin test/server/*.java
```

---

## ✅ **SIGN-OFF CRITERIA**

Để hoàn thành testing cho 3 chức năng ADD/REMOVE/KICK:

-   [ ] **All High priority tests PASS** (10/15 completed)
-   [ ] **90%+ test coverage** for each function
-   [ ] **No critical bugs** in basic functionality
-   [ ] **Thread safety verified** for concurrent operations
-   [ ] **Edge cases handled** (null, empty, duplicates)

---

_Bảng kiểm thử này sẽ được cập nhật liên tục khi thêm test cases và fix bugs._

**Ngày tạo:** 2025-11-18  
**Version:** 1.0  
**Tác giả:** ChatAppUDP Test Team
