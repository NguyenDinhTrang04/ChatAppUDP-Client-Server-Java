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
     * Khởi động server
     */
    public boolean startServer() {
        try {
            socket = new DatagramSocket(port);
            isRunning = true;
            
            // Khởi động thread lắng nghe
            threadPool.submit(this::listenForMessages);
            
            System.out.println("Server started on port " + port);
            return true;
        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
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
        System.out.println("Server stopped");
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
                    System.err.println("Error receiving message: " + e.getMessage());
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
            System.err.println("Error sending message to client: " + e.getMessage());
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
    public void addClient(String username, InetSocketAddress address) {
        connectedClients.put(username, address);
        System.out.println("Client connected: " + username + " from " + address);
        
        // Gửi thông báo user joined
        Message joinMessage = Utils.createSystemMessage(username + " joined the chat");
        broadcastMessage(joinMessage, username);
        
        // Gửi danh sách users cho tất cả clients
        broadcastUserList();
    }
    
    /**
     * Xóa client
     */
    public void removeClient(String username) {
        InetSocketAddress removed = connectedClients.remove(username);
        if (removed != null) {
            System.out.println("Client disconnected: " + username);
            
            // Thông báo user left
            Message leaveMessage = Utils.createSystemMessage(username + " left the chat");
            broadcastMessage(leaveMessage);
            
            // Cập nhật danh sách users cho tất cả clients
            broadcastUserList();
        }
    }
    
    /**
     * Gửi danh sách users cho một client cụ thể
     */
    private void sendUserList(InetSocketAddress clientAddress) {
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
    }
    
    /**
     * Broadcast danh sách users cho tất cả clients
     */
    public void broadcastUserList() {
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
        
        broadcastMessage(userListMessage);
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
