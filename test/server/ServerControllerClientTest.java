package server;

import org.junit.*;
import static org.junit.Assert.*;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.Map;

/**
 * JUnit Test cho chức năng Add/Remove Client trong ServerController
 */
public class ServerControllerClientTest {
    
    private ServerController serverController;
    private InetSocketAddress testAddress1;
    private InetSocketAddress testAddress2;
    private InetSocketAddress testAddress3;
    
    @Before
    public void setUp() {
        // Khởi tạo ServerController cho mỗi test
        serverController = new ServerController(8889); // Sử dụng port khác để tránh conflict
        
        // Tạo các địa chỉ test
        testAddress1 = new InetSocketAddress("127.0.0.1", 12345);
        testAddress2 = new InetSocketAddress("127.0.0.1", 12346);
        testAddress3 = new InetSocketAddress("127.0.0.1", 12347);
    }
    
    @After
    public void tearDown() {
        // Dọn dẹp sau mỗi test
        if (serverController != null && serverController.isRunning()) {
            serverController.stopServer();
        }
        serverController = null;
    }
    
    /**
     * Test thêm client mới thành công
     */
    @Test
    public void testAddClient_Success() {
        // Arrange
        String username = "testUser1";
        
        // Act
        serverController.addClient(username, testAddress1);
        
        // Assert
        Set<String> connectedUsers = serverController.getConnectedUsers();
        assertEquals("Số lượng client phải là 1", 1, connectedUsers.size());
        assertTrue("User phải có trong danh sách", connectedUsers.contains(username));
        assertEquals("Client count phải là 1", 1, serverController.getClientCount());
        
        // Kiểm tra địa chỉ được lưu đúng
        Map<String, InetSocketAddress> clients = serverController.getConnectedClients();
        assertEquals("Địa chỉ client phải đúng", testAddress1, clients.get(username));
    }
    
    /**
     * Test thêm nhiều clients
     */
    @Test
    public void testAddMultipleClients_Success() {
        // Arrange
        String user1 = "testUser1";
        String user2 = "testUser2"; 
        String user3 = "testUser3";
        
        // Act
        serverController.addClient(user1, testAddress1);
        serverController.addClient(user2, testAddress2);
        serverController.addClient(user3, testAddress3);
        
        // Assert
        Set<String> connectedUsers = serverController.getConnectedUsers();
        assertEquals("Số lượng client phải là 3", 3, connectedUsers.size());
        assertTrue("User1 phải có trong danh sách", connectedUsers.contains(user1));
        assertTrue("User2 phải có trong danh sách", connectedUsers.contains(user2));
        assertTrue("User3 phải có trong danh sách", connectedUsers.contains(user3));
        assertEquals("Client count phải là 3", 3, serverController.getClientCount());
    }
    
    /**
     * Test thêm client trùng username (không được phép)
     */
    @Test
    public void testAddClient_DuplicateUsername() {
        // Arrange
        String username = "duplicateUser";
        
        // Act
        serverController.addClient(username, testAddress1);
        serverController.addClient(username, testAddress2); // Thêm lần 2 với cùng username
        
        // Assert
        Set<String> connectedUsers = serverController.getConnectedUsers();
        assertEquals("Chỉ nên có 1 client dù thêm 2 lần", 1, connectedUsers.size());
        assertTrue("User phải có trong danh sách", connectedUsers.contains(username));
        
        // Địa chỉ phải là địa chỉ đầu tiên (không bị ghi đè)
        Map<String, InetSocketAddress> clients = serverController.getConnectedClients();
        assertEquals("Địa chỉ phải là địa chỉ đầu tiên", testAddress1, clients.get(username));
    }
    
    /**
     * Test xóa client thành công
     */
    @Test
    public void testRemoveClient_Success() {
        // Arrange
        String username = "testUser1";
        serverController.addClient(username, )testAddress1;
        
        // Kiểm tra client đã được thêm
        assertEquals("Client count phải là 1 trước khi xóa", 1, serverController.getClientCount());
        
        // Act
        serverController.removeClient(username);
        
        // Assert
        Set<String> connectedUsers = serverController.getConnectedUsers();
        assertEquals("Số lượng client phải là 0 sau khi xóa", 0, connectedUsers.size());
        assertFalse("User không còn trong danh sách", connectedUsers.contains(username));
        assertEquals("Client count phải là 0", 0, serverController.getClientCount());
    }
    
    /**
     * Test xóa client không tồn tại
     */
    @Test
    public void testRemoveClient_NonExistent() {
        // Arrange
        String existingUser = "existingUser";
        String nonExistentUser = "nonExistentUser";
        
        serverController.addClient(existingUser, testAddress1);
        
        // Act
        serverController.removeClient(nonExistentUser); // Xóa user không tồn tại
        
        // Assert
        Set<String> connectedUsers = serverController.getConnectedUsers();
        assertEquals("Client count không thay đổi", 1, connectedUsers.size());
        assertTrue("Existing user vẫn còn", connectedUsers.contains(existingUser));
        assertFalse("Non- existentuser không có", connectedUsers.contains(nonExistentUser));
    }
    
    /**
     * Test xóa một trong nhiều clients
     */
    @Test
    public void testRemoveOneOfMultipleClients() {
        // Arrange
        String user1 = "testUser1";
        String user2 = "testUser2";
        String user3 = "testUser3";
        
        serverController.addClient(user1, testAddress1);
        serverController.addClient(user2, testAddress2);
        serverController.addClient(user3, testAddress3);
        
        // Act - Xóa user2
        serverController.removeClient(user2);
        
        // Assert
        Set<String> connectedUsers = serverController.getConnectedUsers();
        assertEquals("Số lượng client phải là 2", 2, connectedUsers.size());
        assertTrue("User1 vẫn còn", connectedUsers.contains(user1));
        assertFalse("User2 đã bị xóa", connectedUsers.contains(user2));
        assertTrue("User3 vẫn còn", connectedUsers.contains(user3));
    }
    
    /**
     * Test kick user thành công
     */
    @Test
    public void testKickUser_Success() {
        // Arrange
        String username = "userToKick";
        serverController.addClient(username, testAddress1);
        
        // Kiểm tra user đã được thêm
        assertTrue("User phải có trước khi kick", 
                   serverController.getConnectedUsers().contains(username));
        
        // Act
        serverController.kickUser(username);
        
        // Assert
        Set<String> connectedUsers = serverController.getConnectedUsers();
        assertEquals("Số lượng client phải là 0 sau khi kick", 0, connectedUsers.size());
        assertFalse("User đã bị kick", connectedUsers.contains(username));
    }
    
    /**
     * Test kick user không tồn tại
     */
    @Test
    public void testKickUser_NonExistent() {
        // Arrange
        String existingUser = "existingUser";
        String nonExistentUser = "nonExistentUser";
        
        serverController.addClient(existingUser, testAddress1);
        
        // Act
        serverController.kickUser(nonExistentUser); // Kick user không tồn tại
        
        // Assert
        Set<String> connectedUsers = serverController.getConnectedUsers();
        assertEquals("Client count không thay đổi", 1, connectedUsers.size());
        assertTrue("Existing user vẫn còn", connectedUsers.contains(existingUser));
    }
    
    /**
     * Test thread safety - thêm và xóa clients đồng thời
     */
    @Test
    public void testConcurrentAddRemove() throws InterruptedException {
        // Arrange
        final int NUM_THREADS = 10;
        Thread[] threads = new Thread[NUM_THREADS];
        
        // Act - Tạo nhiều thread thêm và xóa clients đồng thời
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                String username = "user" + threadId;
                InetSocketAddress address = new InetSocketAddress("127.0.0.1", 10000 + threadId);
                
                // Thêm client
                serverController.addClient(username, address);
                
                // Sleep ngắn
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Xóa client
                serverController.removeClient(username);
            });
        }
        
        // Chạy tất cả threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Đợi tất cả threads hoàn thành
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Assert
        assertEquals("Tất cả clients phải được xóa", 0, serverController.getClientCount());
    }
    
    /**
     * Test edge case - username null
     */
    @Test
    public void testAddClient_NullUsername() {
        // Act & Assert
        try {
            serverController.addClient(null, testAddress1);
            // Nếu không có exception, kiểm tra client không được thêm
            assertEquals("Client null không được thêm", 0, serverController.getClientCount());
        } catch (Exception e) {
            // Exception là acceptable cho null username
            assertTrue("Exception khi thêm null username là acceptable", true);
        }
    }
    
    /**
     * Test edge case - username empty
     */
    @Test
    public void testAddClient_EmptyUsername() {
        // Act
        serverController.addClient("", testAddress1);
        
        // Assert - Empty username có thể được accept hoặc reject tùy implementation
        // Chỉ test không crash
        assertTrue("Test không crash với empty username", true);
    }
    
    /**
     * Test getConnectedUsers returns immutable collection
     */
    @Test
    public void testGetConnectedUsers_Immutable() {
        // Arrange
        serverController.addClient("user1", testAddress1);
        
        // Act
        Set<String> users = serverController.getConnectedUsers();
        
        // Assert - Thử modify collection trả về
        try {
            users.add("newUser"); // Nên throw exception nếu immutable
            // Nếu không throw, ít nhất phải không ảnh hưởng đến internal state
            assertFalse("Internal state không bị ảnh hưởng", 
                       serverController.getConnectedUsers().contains("newUser"));
        } catch (UnsupportedOperationException e) {
            // Exception là mong đợi cho immutable collection
            assertTrue("Immutable collection throw exception", true);
        }
    }
}