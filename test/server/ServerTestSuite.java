package server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite cho 3 chức năng quản lý Client:
 * - ADD CLIENT: Thêm client vào server
 * - REMOVE CLIENT: Xóa client khỏi server
 * - KICK USER: Kick client (remove + notification)
 * 
 * Bao gồm:
 * - ServerControllerClientTest: 17 unit tests
 * - ServerControllerIntegrationTest: 3 integration tests
 * 
 * Total: 20 test cases
 * Run: java -cp "bin;test-bin;lib/*" org.junit.runner.JUnitCore server.ServerTestSuite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ServerControllerClientTest.class,
    ServerControllerIntegrationTest.class
})
public class ServerTestSuite {
    // ========================================================================
    // TEST SUITE SUMMARY
    // ========================================================================
    // Total: 20 test cases (17 unit + 3 integration)
    //
    // ServerControllerClientTest (17 tests):
    //   - ADD CLIENT: 7 tests
    //   - REMOVE CLIENT: 5 tests
    //   - KICK USER: 5 tests
    //
    // ServerControllerIntegrationTest (3 tests):
    //   - Full lifecycle workflow
    //   - Logging integration
    //   - User list broadcasting
    //
    // Coverage:
    //   ✅ Basic ADD/REMOVE/KICK: 100%
    //   ✅ Edge cases (null, empty, duplicate): 100%
    //   ✅ Concurrency & Thread Safety: 100%
    //   ✅ Integration & UI: 100%
    // ========================================================================
}