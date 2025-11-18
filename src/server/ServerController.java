package server;

import common.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Controller quản lý logic chính của server
 */
public class ServerController {
    private DatagramSocket socket;
    private boolean isRunning;
    private int port;
    private Map<String, InetSocketAddress> connectedClients;
    private ExecutorService threadPool;
    private ServerHandler serverHandler;
    private Object serverUI; // Tránh import circular dependency
    
    public ServerController() {
        this.port = Constants.DEFAULT_PORT;
        this.connectedClients = new ConcurrentHashMap<>();
        this.threadPool = Executors.newCachedThreadPool();
        this.serverHandler = new ServerHandler(this);
    }
    
    public ServerController(int port) {
        this();
        this.port = port;
    }
    
    /**
     * Set UI reference để có thể log messages
     */
    public void setServerUI(Object ui) {
        this.serverUI = ui;
    }
    
    /**
     * Log message vào UI hoặc console
     */
    public void log(String message) {
        String logMessage = "[" + Utils.getCurrentTimeString() + "] " + message;
        
        // In ra console
        System.out.println(logMessage);
        
        // Gửi đến UI nếu có
        if (serverUI != null) {
            try {
                // Sử dụng reflection để tránh circular import
                java.lang.reflect.Method appendLogMethod = serverUI.getClass().getMethod("appendLog", String.class);
                appendLogMethod.invoke(serverUI, logMessage);
            } catch (Exception e) {
                // Nếu không có UI hoặc có lỗi, chỉ log ra console
            }
        }
    }
    
    /**
     * Khởi động server
     */
    public boolean startServer() {
        try {
            socket = new DatagramSocket(port);
            isRunning = true;
            
            // Khởi động thread lắng nghe
            threadPool.submit(this::listenForMessages);
            
            log("Server started on port " + port);
            return true;
        } catch (Exception e) {
            log("Error starting server: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Dừng server
     */
    public void stopServer() {
        isRunning = false;
        
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
            }
        }
        
        connectedClients.clear();
        log("Server stopped");
    }
    
    /**
     * Lắng nghe tin nhắn từ clients
     */
    private void listenForMessages() {
        byte[] buffer = new byte[Constants.BUFFER_SIZE];
        
        while (isRunning) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                // Xử lý tin nhắn trong thread riêng
                threadPool.submit(() -> serverHandler.handleMessage(packet));
                
            } catch (Exception e) {
                if (isRunning) {
                    log("Error receiving message: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Gửi tin nhắn đến một client cụ thể
     */
    public void sendToClient(Message message, InetSocketAddress clientAddress) {
        try {
            String messageStr = message.serialize();
            byte[] data = messageStr.getBytes();
            
            DatagramPacket packet = new DatagramPacket(
                data, data.length, clientAddress.getAddress(), clientAddress.getPort()
            );
            
            socket.send(packet);
        } catch (Exception e) {
            log("Error sending message to client: " + e.getMessage());
        }
    }
    
    /**
     * Broadcast tin nhắn đến tất cả clients
     */
    public void broadcastMessage(Message message) {
        for (InetSocketAddress clientAddress : connectedClients.values()) {
            sendToClient(message, clientAddress);
        }
    }
    
    /**
     * Broadcast tin nhắn đến tất cả clients trừ sender
     */
    public void broadcastMessage(Message message, String excludeUsername) {
        for (Map.Entry<String, InetSocketAddress> entry : connectedClients.entrySet()) {
            if (!entry.getKey().equals(excludeUsername)) {
                sendToClient(message, entry.getValue());
            }
        }
    }
    
    /**
     * Thêm client mới
     */
  public synchronized boolean addClient(String username, InetSocketAddress address) {
    try {
        // Validation 1: Username null hoặc empty
        if (username == null || username.trim().isEmpty()) {
            sendJoinResponse(address, false, "Invalid username: Username cannot be empty");
            log("Join rejected - Invalid username from " + address);
            return false;
        }
        
        // Validation 2: Username đã tồn tại
        if (connectedClients.containsKey(username.trim())) {
            sendJoinResponse(address, false, "Username '" + username + "' is already taken. Please choose another name.");
            log("Join rejected - Duplicate username '" + username + "' from " + address);
            return false;
        }
        
        // Validation 3: Kiểm tra địa chỉ IP đã có username khác chưa
        String existingUser = findUserByAddress(address);
        if (existingUser != null) {
            sendJoinResponse(address, false, "This IP address is already connected as '" + existingUser + "'");
            log("Join rejected - IP " + address + " already connected as '" + existingUser + "'");
            return false;
        }
        
        // // Validation 4: Kiểm tra số lượng client tối đa
        // if (connectedClients.size() >= Constants.MAX_CLIENTS) {
        //     sendJoinResponse(address, false, "Server is full. Maximum " + Constants.MAX_CLIENTS + " clients allowed.");
        //     log("Join rejected - Server full from " + address);
        //     return false;
        // }
        
        // Thêm client thành công
        connectedClients.put(username.trim(), address);
        
        // Gửi response thành công
        sendJoinResponse(address, true, "Welcome to the chat, " + username + "!");
        
        log("Client added successfully: " + username + " from " + address + 
            " (Total clients: " + connectedClients.size() + ")");
        
        // Thông báo cho các client khác
        Message joinMessage = Utils.createSystemMessage(username + " joined the chat");
        broadcastMessage(joinMessage, username);
        
        // Gửi user list cho tất cả
        broadcastUserList();
        
        // Gửi user list riêng cho client mới (để đảm bảo)
        Thread.sleep(100); // Small delay để đảm bảo client đã sẵn sàng
        sendUserListToClient(address);
        
        return true;
        
    } catch (Exception e) {
        sendJoinResponse(address, false, "Server error occurred. Please try again.");
        log("Error adding client " + username + ": " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    
    /**
     * Kick user khỏi server (admin function)
     */
    public synchronized void kickUser(String username) {
        try {
            if (!connectedClients.containsKey(username)) {
                log("Attempted to kick non-existent user: " + username);
                return;
            }
            
            // Gửi notification đến user bị kick
            InetSocketAddress userAddress = connectedClients.get(username);
            if (userAddress != null) {
                Message kickMessage = new Message(
                    Constants.MESSAGE_TYPE_NOTIFICATION,
                    "SERVER",
                    "You have been kicked from the server by admin."
                );
                sendToClient(kickMessage, userAddress);
            }
            
            // Remove user khỏi danh sách
            removeClient(username);
            
            log("Admin kicked user: " + username);
            
        } catch (Exception e) {
            log("Error kicking user " + username + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Xóa client
     */
    public synchronized void removeClient(String username) {
        try {
            InetSocketAddress removed = connectedClients.remove(username);
            if (removed != null) {
                log("Client removed: " + username + 
                                 " (Remaining clients: " + connectedClients.size() + ")");
                
                // Thông báo user left
                Message leaveMessage = Utils.createSystemMessage(username + " left the chat");
                broadcastMessage(leaveMessage);
                
                // Cập nhật danh sách users cho tất cả clients
                broadcastUserList();
            } else {
                log("Attempted to remove non-existent client: " + username);
            }
        } catch (Exception e) {
            log("Error removing client " + username + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Gửi danh sách users cho một client cụ thể
     */
    public void sendUserListToClient(InetSocketAddress clientAddress) {
        StringBuilder userList = new StringBuilder();
        for (String username : connectedClients.keySet()) {
            if (userList.length() > 0) {
                userList.append(",");
            }
            userList.append(username);
        }
        
        Message userListMessage = new Message(
            Constants.MESSAGE_TYPE_USER_LIST, 
            "SERVER", 
            userList.toString()
        );
        
        sendToClient(userListMessage, clientAddress);
        log("Sent user list to client: " + clientAddress + " - Users: " + userList.toString());
    }
    
    /**
     * Broadcast danh sách users cho tất cả clients
     */
    public synchronized void broadcastUserList() {
        try {
            StringBuilder userList = new StringBuilder();
            for (String username : connectedClients.keySet()) {
                if (userList.length() > 0) {
                    userList.append(",");
                }
                userList.append(username);
            }
            
            Message userListMessage = new Message(
                Constants.MESSAGE_TYPE_USER_LIST, 
                "SERVER", 
                userList.toString()
            );
            
            log("Broadcasting user list to " + connectedClients.size() + 
                             " clients - Users: " + userList.toString());
            
            // Gửi từng client một để tránh lỗi
            int successCount = 0;
            for (Map.Entry<String, InetSocketAddress> entry : connectedClients.entrySet()) {
                try {
                    sendToClient(userListMessage, entry.getValue());
                    successCount++;
                } catch (Exception e) {
                    log("Failed to send user list to " + entry.getKey() + ": " + e.getMessage());
                }
            }
            
            log("User list sent to " + successCount + "/" + connectedClients.size() + " clients");
            
        } catch (Exception e) {
            log("Error broadcasting user list: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Getters
    public boolean isRunning() {
        return isRunning;
    }
    
    public int getPort() {
        return port;
    }
    
    public Set<String> getConnectedUsers() {
        return new HashSet<>(connectedClients.keySet());
    }
    
    public int getClientCount() {
        return connectedClients.size();
    }
    
    public Map<String, InetSocketAddress> getConnectedClients() {
        return new HashMap<>(connectedClients);
    }
}
