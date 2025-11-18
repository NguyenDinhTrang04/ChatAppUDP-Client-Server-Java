# JUnit Tests cho ChatAppUDP

## 📋 Tổng quan

Test suite này test chức năng **Add/Remove Client** trong ServerController.

## 🧪 Test Classes

### 1. **ServerControllerClientTest.java**

**Unit Tests** cho các method cụ thể:

#### ✅ Test Cases:

-   `testAddClient_Success()` - Thêm client thành công
-   `testAddMultipleClients_Success()` - Thêm nhiều clients
-   `testAddClient_DuplicateUsername()` - Test username trùng
-   `testRemoveClient_Success()` - Xóa client thành công
-   `testRemoveClient_NonExistent()` - Xóa client không tồn tại
-   `testRemoveOneOfMultipleClients()` - Xóa 1 trong nhiều clients
-   `testKickUser_Success()` - Kick user thành công
-   `testKickUser_NonExistent()` - Kick user không tồn tại
-   `testConcurrentAddRemove()` - Test thread safety
-   `testAddClient_NullUsername()` - Test null username
-   `testAddClient_EmptyUsername()` - Test empty username
-   `testGetConnectedUsers_Immutable()` - Test immutable collection

### 2. **ServerControllerIntegrationTest.java**

**Integration Tests** cho workflow complete:

#### ✅ Test Cases:

-   `testFullClientLifecycle()` - Test toàn bộ lifecycle
-   `testLoggingIntegration()` - Test logging với UI
-   `testUserListBroadcasting()` - Test broadcast user list

### 3. **ServerTestSuite.java**

**Test Suite** chạy tất cả tests.

## 🚀 Cách chạy Tests

### 1. **Compile tests:**

```cmd
javac -cp "src;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" -d test-bin test/server/*.java src/server/*.java src/common/*.java
```

### 2. **Chạy tất cả tests:**

```cmd
java -cp "test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerTestSuite
```

### 3. **Chạy test cụ thể:**

```cmd
java -cp "test-bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore server.ServerControllerClientTest
```

## 📦 Dependencies cần thiết

### JUnit 4.13.2:

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

### Hoặc download manual:

-   `junit-4.13.2.jar`
-   `hamcrest-core-1.3.jar`

Đặt trong thư mục `lib/`

## 🎯 Test Coverage

### **Add Client functionality:**

-   ✅ Add client mới
-   ✅ Add multiple clients
-   ✅ Duplicate username handling
-   ✅ Null/empty username handling
-   ✅ Thread safety

### **Remove Client functionality:**

-   ✅ Remove client tồn tại
-   ✅ Remove client không tồn tại
-   ✅ Remove từ multiple clients
-   ✅ Kick user functionality

### **Integration tests:**

-   ✅ Complete lifecycle workflow
-   ✅ Logging integration
-   ✅ User list broadcasting
-   ✅ Server start/stop với clients

## 📊 Expected Results

### **Successful run output:**

```
JUnit version 4.13.2
.....................
Time: 0.125

OK (21 tests)
```

### **Test assertions:**

-   Client count accuracy
-   Username presence/absence in lists
-   Address mapping correctness
-   Thread safety validation
-   Logging integration
-   Immutable collections

## 🔧 Mock Objects

### **MockServerUI:**

-   Captures log messages
-   Tests UI integration
-   Validates logging workflow

## ⚠️ Test Requirements

### **Server port conflicts:**

-   Tests sử dụng ports khác nhau (8889, 8890)
-   Tránh conflict với main server (8888)

### **Thread safety:**

-   Concurrent add/remove tests
-   Synchronized method validation
-   Race condition prevention

### **Memory cleanup:**

-   `@After` methods cleanup resources
-   Prevent test interference
-   Server stop validation

## 🎉 Kết luận

Test suite này đảm bảo:

-   **Functional correctness** của add/remove client
-   **Thread safety** trong multi-client environment
-   **Integration** giữa các components
-   **Edge case handling** (null, empty, duplicates)
-   **Resource management** (proper cleanup)

**Chạy tests trước mỗi lần deploy để đảm bảo quality!** ✅
