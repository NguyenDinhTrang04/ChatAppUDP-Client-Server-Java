package server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite cho tất cả Server tests
 * Chạy tất cả test cases liên quan đến Server functionality
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ServerControllerClientTest.class,
    ServerControllerIntegrationTest.class
})
public class ServerTestSuite {
    // Test suite - không cần implementation
    // JUnit sẽ tự động chạy tất cả test classes được list trong @Suite.SuiteClasses
}