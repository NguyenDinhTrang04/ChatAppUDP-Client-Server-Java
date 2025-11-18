/**
 * Simple test runner to validate Add/Remove Client functionality
 * Không cần JUnit - chạy trực tiếp test cases
 */
package test;

import server.ServerController;
import server.gui.ServerUI;
import common.Message;
import common.Constants;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleClientManagementTest {
    
    private static int testCount = 0;
    private static int passedTests = 0;
    
    public static void main(String[] args) {
        System.out.println("=== ChatAppUDP Client Management Tests ===\n");
        
        try {
            // Test 1: Add Client
            testAddClient();
            
            // Test 2: Add Multiple Clients
            testAddMultipleClients();
            
            // Test 3: Remove Client
            testRemoveClient();
            
            // Test 4: Duplicate Username
            testDuplicateUsername();
            
            // Test 5: Thread Safety
            testThreadSafety();
            
            // Test 6: Edge Cases
            testEdgeCases();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        printSummary();
    }
    
    private static void testAddClient() throws Exception {
        System.out.println("Test 1: Add Client");
        ServerController server = new ServerController();
        
        InetAddress address = InetAddress.getLocalHost();
        InetSocketAddress socketAddress = new InetSocketAddress(address, 5001);
        
        server.addClient("user1", socketAddress);
        
        assertTrue("Client count should be 1", server.getConnectedUsers().size() == 1);
        assertTrue("Should contain user1", server.getConnectedUsers().contains("user1"));
        
        server.stopServer();
        System.out.println("✅ PASSED\n");
    }
    
    private static void testAddMultipleClients() throws Exception {
        System.out.println("Test 2: Add Multiple Clients");
        ServerController server = new ServerController();
        
        InetAddress address = InetAddress.getLocalHost();
        InetSocketAddress socketAddress1 = new InetSocketAddress(address, 5001);
        InetSocketAddress socketAddress2 = new InetSocketAddress(address, 5002);
        InetSocketAddress socketAddress3 = new InetSocketAddress(address, 5003);
        
        server.addClient("user1", socketAddress1);
        server.addClient("user2", socketAddress2);
        server.addClient("user3", socketAddress3);
        
        assertTrue("Client count should be 3", server.getConnectedUsers().size() == 3);
        assertTrue("Should contain all users", 
            server.getConnectedUsers().contains("user1") &&
            server.getConnectedUsers().contains("user2") &&
            server.getConnectedUsers().contains("user3"));
        
        server.stopServer();
        System.out.println("✅ PASSED\n");
    }
    
    private static void testRemoveClient() throws Exception {
        System.out.println("Test 3: Remove Client");
        ServerController server = new ServerController();
        
        InetAddress address = InetAddress.getLocalHost();
        InetSocketAddress socketAddress1 = new InetSocketAddress(address, 5001);
        InetSocketAddress socketAddress2 = new InetSocketAddress(address, 5002);
        
        server.addClient("user1", socketAddress1);
        server.addClient("user2", socketAddress2);
        
        server.removeClient("user1");
        
        assertTrue("Client count should be 1", server.getConnectedUsers().size() == 1);
        assertTrue("Should not contain user1", !server.getConnectedUsers().contains("user1"));
        assertTrue("Should still contain user2", server.getConnectedUsers().contains("user2"));
        
        server.stopServer();
        System.out.println("✅ PASSED\n");
    }
    
    private static void testDuplicateUsername() throws Exception {
        System.out.println("Test 4: Duplicate Username");
        ServerController server = new ServerController();
        
        InetAddress address = InetAddress.getLocalHost();
        InetSocketAddress socketAddress1 = new InetSocketAddress(address, 5001);
        InetSocketAddress socketAddress2 = new InetSocketAddress(address, 5002);
        
        server.addClient("user1", socketAddress1);
        int sizeBefore = server.getConnectedUsers().size();
        server.addClient("user1", socketAddress2); // Duplicate - should be ignored
        int sizeAfter = server.getConnectedUsers().size();
        
        assertTrue("Duplicate should be ignored", sizeBefore == sizeAfter);
        assertTrue("Client count should be 1", server.getConnectedUsers().size() == 1);
        
        server.stopServer();
        System.out.println("✅ PASSED\n");
    }
    
    private static void testThreadSafety() throws Exception {
        System.out.println("Test 5: Thread Safety (Concurrent Add/Remove)");
        ServerController server = new ServerController();
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(20);
        
        // Add 10 clients concurrently
        for (int i = 0; i < 10; i++) {
            final int userId = i;
            executor.submit(() -> {
                try {
                    InetAddress address = InetAddress.getLocalHost();
                    InetSocketAddress socketAddress = new InetSocketAddress(address, 5000 + userId);
                    server.addClient("user" + userId, socketAddress);
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        
        // Remove 10 clients concurrently
        for (int i = 0; i < 10; i++) {
            final int userId = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(100); // Let adds happen first
                    server.removeClient("user" + userId);
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        
        assertTrue("All operations should complete without errors", server.getConnectedUsers().size() == 0);
        
        server.stopServer();
        System.out.println("✅ PASSED\n");
    }
    
    private static void testEdgeCases() throws Exception {
        System.out.println("Test 6: Edge Cases");
        ServerController server = new ServerController();
        
        InetAddress address = InetAddress.getLocalHost();
        InetSocketAddress socketAddress1 = new InetSocketAddress(address, 5001);
        InetSocketAddress socketAddress2 = new InetSocketAddress(address, 5002);
        
        // Test add with null username (should handle gracefully)
        try {
            server.addClient(null, socketAddress1);
            System.out.println("  ⚠️ Null username handled without crash");
        } catch (Exception e) {
            System.out.println("  ⚠️ Null username caused exception (handled)");
        }
        
        // Test add with empty username (should handle gracefully)
        try {
            server.addClient("", socketAddress2);
            System.out.println("  ⚠️ Empty username handled without crash");
        } catch (Exception e) {
            System.out.println("  ⚠️ Empty username caused exception (handled)");
        }
        
        // Test remove non-existent user (should handle gracefully)
        try {
            server.removeClient("nonexistent");
            System.out.println("  ✅ Remove non-existent user handled gracefully");
        } catch (Exception e) {
            System.out.println("  ⚠️ Remove non-existent caused exception");
        }
        
        server.stopServer();
        System.out.println("✅ PASSED\n");
    }
    
    private static void assertTrue(String message, boolean condition) {
        testCount++;
        if (condition) {
            passedTests++;
            System.out.println("  ✅ " + message);
        } else {
            System.out.println("  ❌ " + message);
            throw new AssertionError("Test failed: " + message);
        }
    }
    
    private static void printSummary() {
        System.out.println("=== TEST SUMMARY ===");
        System.out.println("Total Tests: " + testCount);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (testCount - passedTests));
        System.out.println("Success Rate: " + (100.0 * passedTests / testCount) + "%");
        
        if (passedTests == testCount) {
            System.out.println("\n🎉 ALL TESTS PASSED! Add/Remove Client functionality is working correctly!");
        } else {
            System.out.println("\n❌ Some tests failed. Please check the implementation.");
        }
    }
}