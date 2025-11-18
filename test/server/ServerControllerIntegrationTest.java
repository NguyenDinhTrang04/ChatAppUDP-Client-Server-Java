package server;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import static org.junit.Assert.*;
import java.net.InetSocketAddress;
import java.util.Set;
import common.*;

/**
 * Integration Tests cho chức năng Add/Remove Client
 * Test tương tác giữa các components
 */
public class ServerControllerIntegrationTest {
    
    private ServerController serverController;
    private MockServerUI mockUI;
    
    @Before
    public void setUp() {
        serverController = new ServerController(8890);
        mockUI = new MockServerUI();
        serverController.setServerUI(mockUI);
    }
    
    @After
    public void tearDown() {
        if (serverController != null && serverController.isRunning()) {
            serverController.stopServer();
        }
    }
    
    /**
     * Test full workflow: start server -> add clients -> remove clients -> stop server
     */
    @Test
    public void testFullClientLifecycle() {
        // Arrange
        InetSocketAddress addr1 = new InetSocketAddress("127.0.0.1", 11111);
        InetSocketAddress addr2 = new InetSocketAddress("127.0.0.1", 11112);
        
        // Act & Assert
        
        // 1. Start server
        assertTrue("Server phải start thành công", serverController.startServer());
        assertTrue("Server phải đang chạy", serverController.isRunning());
        
        // 2. Add clients
        serverController.addClient("user1", addr1);
        serverController.addClient("user2", addr2);
        
        assertEquals("Phải có 2 clients", 2, serverController.getClientCount());
        assertTrue("User1 phải có trong danh sách", 
                   serverController.getConnectedUsers().contains("user1"));
        assertTrue("User2 phải có trong danh sách", 
                   serverController.getConnectedUsers().contains("user2"));
        
        // 3. Remove one client
        serverController.removeClient("user1");
        
        assertEquals("Phải còn 1 client", 1, serverController.getClientCount());
        assertFalse("User1 đã bị xóa", 
                    serverController.getConnectedUsers().contains("user1"));
        assertTrue("User2 vẫn còn", 
                   serverController.getConnectedUsers().contains("user2"));
        
        // 4. Kick remaining client
        serverController.kickUser("user2");
        
        assertEquals("Không còn client nào", 0, serverController.getClientCount());
        
        // 5. Stop server
        serverController.stopServer();
        assertFalse("Server đã stop", serverController.isRunning());
    }
    
    /**
     * Test logging integration với UI
     */
    @Test
    public void testLoggingIntegration() {
        // Arrange
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 11113);
        mockUI.clearLogs();
        
        // Act
        serverController.addClient("logTestUser", addr);
        serverController.removeClient("logTestUser");
        
        // Assert
        assertTrue("Phải có log cho add client", 
                   mockUI.hasLogContaining("Client added: logTestUser"));
        assertTrue("Phải có log cho remove client", 
                   mockUI.hasLogContaining("Client removed: logTestUser"));
    }
    
    /**
     * Test user list broadcasting
     */
    @Test
    public void testUserListBroadcasting() {
        // Arrange
        InetSocketAddress addr1 = new InetSocketAddress("127.0.0.1", 11114);
        InetSocketAddress addr2 = new InetSocketAddress("127.0.0.1", 11115);
        
        assertTrue("Server phải start", serverController.startServer());
        
        // Act
        serverController.addClient("broadcast1", addr1);
        serverController.addClient("broadcast2", addr2);
        
        // Assert - User list phải được broadcast
        assertTrue("Phải có log broadcast user list", 
                   mockUI.hasLogContaining("Broadcasting user list"));
        assertTrue("User list phải chứa cả 2 users", 
                   mockUI.hasLogContaining("broadcast1,broadcast2") || 
                   mockUI.hasLogContaining("broadcast2,broadcast1"));
        
        // Act - Remove một user
        serverController.removeClient("broadcast1");
        
        // Assert - User list phải được update
        assertTrue("User list phải được update sau remove", 
                   mockUI.hasLogContaining("broadcast2"));
    }
    
    /**
     * Mock UI class để test logging
     */
    private static class MockServerUI {
        private StringBuilder logs = new StringBuilder();
        
        public void appendLog(String message) {
            logs.append(message).append("\n");
        }
        
        public void clearLogs() {
            logs.setLength(0);
        }
        
        public boolean hasLogContaining(String text) {
            return logs.toString().contains(text);
        }
        
        public String getAllLogs() {
            return logs.toString();
        }
    }
}