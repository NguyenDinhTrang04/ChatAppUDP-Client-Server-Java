# 📋 BẢNG KIỂM THỬ CHATAPPUDP - TEST CHECKLIST

## 🎯 **TỔNG QUAN**

Bảng kiểm thử toàn diện cho ứng dụng Chat UDP Client-Server với các chức năng:

-   Quản lý Client (Add/Remove/Kick)
-   Gửi/Nhận tin nhắn
-   Private messaging
-   Server UI & Logging
-   Thread safety & Concurrency

---

## 📊 **I. QUẢN LÝ CLIENT (CLIENT MANAGEMENT)**

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

## 💬 **II. TIN NHẮN (MESSAGING)**

### **A. Gửi tin nhắn broadcast**

| Test ID    | Mô tả Test Case                      | Input                       | Expected Output             | Status  | Priority |
| ---------- | ------------------------------------ | --------------------------- | --------------------------- | ------- | -------- |
| TC_MSG_001 | Gửi tin nhắn đến tất cả              | "Hello all" from user1      | All users receive message   | ⚠️ TODO | High     |
| TC_MSG_002 | Gửi tin nhắn khi 1 client            | "Hello" from only user      | No crash, handle gracefully | ⚠️ TODO | Medium   |
| TC_MSG_003 | Gửi tin nhắn rất dài                 | Message 1000+ chars         | Handle large message        | ⚠️ TODO | Medium   |
| TC_MSG_004 | Gửi tin nhắn với ký tự đặc biệt      | Message with emojis/unicode | Proper encoding/decoding    | ⚠️ TODO | Medium   |
| TC_MSG_005 | Gửi tin nhắn từ client không tồn tại | Invalid sender              | Reject message              | ⚠️ TODO | High     |
| TC_MSG_006 | Gửi nhiều tin nhắn nhanh             | Rapid message sending       | All messages delivered      | ⚠️ TODO | High     |

### **B. Private messaging**

| Test ID   | Mô tả Test Case               | Input                     | Expected Output              | Status  | Priority |
| --------- | ----------------------------- | ------------------------- | ---------------------------- | ------- | -------- |
| TC_PM_001 | Gửi private message hợp lệ    | "@user2 Hello" from user1 | Only user2 receives          | ⚠️ TODO | High     |
| TC_PM_002 | Gửi PM đến user không tồn tại | "@notexist Hello"         | Error/notification to sender | ⚠️ TODO | High     |
| TC_PM_003 | Gửi PM với format sai         | "user2 Hello" (no @)      | Treat as broadcast           | ⚠️ TODO | Medium   |
| TC_PM_004 | Gửi PM đến chính mình         | "@user1 Hello" from user1 | Handle self-message          | ⚠️ TODO | Low      |
| TC_PM_005 | PM với nội dung rỗng          | "@user2" (no message)     | Handle empty PM              | ⚠️ TODO | Low      |

---

## 🖥️ **III. SERVER UI & LOGGING**

### **A. Server logging**

| Test ID    | Mô tả Test Case       | Input             | Expected Output          | Status  | Priority |
| ---------- | --------------------- | ----------------- | ------------------------ | ------- | -------- |
| TC_LOG_001 | Log client connect    | User joins        | Log entry created        | ✅ PASS | High     |
| TC_LOG_002 | Log client disconnect | User leaves       | Log entry created        | ✅ PASS | High     |
| TC_LOG_003 | Log message sent      | Message broadcast | Message logged           | ⚠️ TODO | Medium   |
| TC_LOG_004 | Log kick action       | Admin kicks user  | Kick action logged       | ✅ PASS | High     |
| TC_LOG_005 | Log server start/stop | Server lifecycle  | Start/stop logged        | ⚠️ TODO | Medium   |
| TC_LOG_006 | Log với timestamp     | Any log action    | Correct timestamp format | ✅ PASS | Medium   |

### **B. Server UI features**

| Test ID   | Mô tả Test Case         | Input                      | Expected Output           | Status  | Priority |
| --------- | ----------------------- | -------------------------- | ------------------------- | ------- | -------- |
| TC_UI_001 | Display client list     | Clients connect/disconnect | UI updates in real-time   | ⚠️ TODO | High     |
| TC_UI_002 | Kick user từ UI         | Select user + kick button  | User removed + UI updated | ⚠️ TODO | High     |
| TC_UI_003 | Start/stop server từ UI | Click start/stop buttons   | Server state changes      | ⚠️ TODO | High     |
| TC_UI_004 | Display log messages    | Log events occur           | Logs appear in UI         | ✅ PASS | High     |
| TC_UI_005 | Clear log messages      | Clear log button           | Logs cleared from UI      | ⚠️ TODO | Medium   |

---

## ⚡ **IV. CONCURRENCY & THREAD SAFETY**

### **A. Thread safety tests**

| Test ID   | Mô tả Test Case              | Input                          | Expected Output        | Status  | Priority |
| --------- | ---------------------------- | ------------------------------ | ---------------------- | ------- | -------- |
| TC_TS_001 | Concurrent add operations    | 10 threads add clients         | All added correctly    | ✅ PASS | High     |
| TC_TS_002 | Concurrent remove operations | 10 threads remove clients      | All removed correctly  | ✅ PASS | High     |
| TC_TS_003 | Mixed add/remove operations  | Simultaneous add & remove      | Consistent final state | ✅ PASS | High     |
| TC_TS_004 | Concurrent message sending   | Multiple threads send messages | All messages delivered | ⚠️ TODO | High     |
| TC_TS_005 | Race condition test          | Add/remove same user           | No data corruption     | ✅ PASS | High     |

### **B. Performance tests**

| Test ID     | Mô tả Test Case          | Input                   | Expected Output        | Status  | Priority |
| ----------- | ------------------------ | ----------------------- | ---------------------- | ------- | -------- |
| TC_PERF_001 | Many clients stress test | Add 100+ clients        | Performance acceptable | ⚠️ TODO | Medium   |
| TC_PERF_002 | Message throughput test  | Send 1000+ messages/sec | No message loss        | ⚠️ TODO | Medium   |
| TC_PERF_003 | Memory usage test        | Long running server     | No memory leaks        | ⚠️ TODO | Medium   |

---

## 🔌 **V. NETWORK & CONNECTIVITY**

### **A. Network error handling**

| Test ID    | Mô tả Test Case            | Input                     | Expected Output              | Status  | Priority |
| ---------- | -------------------------- | ------------------------- | ---------------------------- | ------- | -------- |
| TC_NET_001 | Client disconnect suddenly | Force client disconnect   | Server handles gracefully    | ⚠️ TODO | High     |
| TC_NET_002 | Network timeout            | Slow/timeout network      | Appropriate timeout handling | ⚠️ TODO | High     |
| TC_NET_003 | Port already in use        | Start server on used port | Error handling               | ⚠️ TODO | High     |
| TC_NET_004 | Invalid IP address         | Connect to invalid IP     | Connection fails gracefully  | ⚠️ TODO | Medium   |
| TC_NET_005 | Network packet loss        | Simulated packet loss     | Message retry/recovery       | ⚠️ TODO | Medium   |

---

## 🛡️ **VI. SECURITY & VALIDATION**

### **A. Input validation**

| Test ID    | Mô tả Test Case           | Input                               | Expected Output            | Status  | Priority |
| ---------- | ------------------------- | ----------------------------------- | -------------------------- | ------- | -------- |
| TC_SEC_001 | SQL injection in username | username="'; DROP TABLE--"          | Sanitized/rejected         | ⚠️ TODO | High     |
| TC_SEC_002 | XSS in message            | message="<script>alert(1)</script>" | Escaped/sanitized          | ⚠️ TODO | High     |
| TC_SEC_003 | Very long username        | 10000+ character username           | Length limit enforced      | ⚠️ TODO | Medium   |
| TC_SEC_004 | Special chars in username | unicode/emoji username              | Proper handling            | ⚠️ TODO | Medium   |
| TC_SEC_005 | Buffer overflow attempt   | Extremely large message             | Protected against overflow | ⚠️ TODO | High     |

---

## 🔄 **VII. INTEGRATION TESTS**

### **A. End-to-end workflows**

| Test ID    | Mô tả Test Case          | Workflow                          | Expected Result               | Status  | Priority |
| ---------- | ------------------------ | --------------------------------- | ----------------------------- | ------- | -------- |
| TC_E2E_001 | Complete chat session    | Start→Connect→Chat→Disconnect     | All functions work together   | ⚠️ TODO | High     |
| TC_E2E_002 | Multiple users chatting  | 3 users join, exchange messages   | All see all messages          | ⚠️ TODO | High     |
| TC_E2E_003 | Admin kick workflow      | User joins→misbehaves→gets kicked | Kick process works end-to-end | ⚠️ TODO | High     |
| TC_E2E_004 | Server restart scenario  | Users connected→server restart    | Proper disconnection handling | ⚠️ TODO | Medium   |
| TC_E2E_005 | Private message workflow | User1→PM to User2→User2 responds  | PM conversation works         | ⚠️ TODO | High     |

---

## 📈 **VIII. SUMMARY DASHBOARD**

### **Test Coverage Summary**

| **Chức năng**         | **Total Tests** | **Passed** | **Failed** | **TODO** | **Coverage** |
| --------------------- | --------------- | ---------- | ---------- | -------- | ------------ |
| **Client Management** | 25              | 15         | 0          | 10       | 60%          |
| **Messaging**         | 11              | 0          | 0          | 11       | 0%           |
| **Server UI/Logging** | 11              | 3          | 0          | 8        | 27%          |
| **Concurrency**       | 8               | 5          | 0          | 3        | 63%          |
| **Network**           | 5               | 0          | 0          | 5        | 0%           |
| **Security**          | 5               | 0          | 0          | 5        | 0%           |
| **Integration**       | 5               | 0          | 0          | 5        | 0%           |
| **TOTAL**             | **70**          | **23**     | **0**      | **47**   | **33%**      |

### **Priority Breakdown**

| **Priority** | **Count** | **Completed** | **Remaining** |
| ------------ | --------- | ------------- | ------------- |
| **High**     | 42        | 15            | 27            |
| **Medium**   | 23        | 8             | 15            |
| **Low**      | 5         | 0             | 5             |

---

## 🎯 **IX. NEXT STEPS - ROADMAP**

### **Phase 1: Core Messaging (Week 1)**

-   [ ] Implement basic message broadcasting tests
-   [ ] Add private messaging test cases
-   [ ] Test message validation and encoding

### **Phase 2: Network & Reliability (Week 2)**

-   [ ] Network error handling tests
-   [ ] Connection stability tests
-   [ ] Performance and stress tests

### **Phase 3: Security & Edge Cases (Week 3)**

-   [ ] Input validation and sanitization
-   [ ] Security vulnerability tests
-   [ ] Edge case coverage

### **Phase 4: Integration & Polish (Week 4)**

-   [ ] End-to-end workflow tests
-   [ ] UI integration testing
-   [ ] Final performance optimization

---

## 📝 **X. TEST EXECUTION COMMANDS**

### **Run Specific Test Categories:**

```bash
# Client Management Tests
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest

# Integration Tests
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerIntegrationTest

# All Tests
java -cp "bin;test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerTestSuite
```

### **Compile Tests:**

```bash
javac -cp "bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" -d test-bin test/server/*.java
```

---

## ✅ **XI. SIGN-OFF CRITERIA**

Để coi như hoàn thành testing, cần đạt:

-   [ ] **90%+ test coverage** cho core functions
-   [ ] **All High priority tests PASS**
-   [ ] **No critical bugs** in basic functionality
-   [ ] **Performance acceptable** under normal load
-   [ ] **Security validation** complete
-   [ ] **Integration tests** all passing

---

_Bảng kiểm thử này sẽ được cập nhật liên tục khi thêm test cases và fix bugs._

**Ngày tạo:** 2025-11-18  
**Version:** 1.0  
**Tác giả:** ChatAppUDP Test Team
