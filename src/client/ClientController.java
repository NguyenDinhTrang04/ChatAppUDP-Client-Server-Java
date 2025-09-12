package client;

import common.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Controller quản lý logic chính của client
 */
public class ClientController {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;
    private String username;
    private boolean isConnected;
    private ExecutorService threadPool;
    private ClientHandler clientHandler;
    private MessageListener messageListener;
    
    // Interface để lắng nghe tin nhắn
    public interface MessageListener {
        void onMessageReceived(Message message);
        void onConnectionStatusChanged(boolean isConnected);
        void onError(String error);
    }
    
    public ClientController() {
        this.threadPool = Executors.newCachedThreadPool();
        this.clientHandler = new ClientHandler(this);
    }
    
    /**
     * Kết nối đến server
     */
    public boolean connectToServer(String host, int port, String username) {
        try {
            // Validate input
            if (!Utils.isValidUsername(username)) {
                notifyError("Invalid username. Username must be 2-20 characters.");
                return false;
            }
            
            if (!Utils.isValidPort(port)) {
                notifyError("Invalid port number.");
                return false;
            }
            
            // Create socket
            socket = new DatagramSocket();
            serverAddress = InetAddress.getByName(host);
            this.serverPort = port;
            this.username = username;
            
            // Send join request
            Message joinMessage = new Message(Constants.MESSAGE_TYPE_JOIN, username, "");
            sendMessage(joinMessage);
            
            // Start listening thread
            isConnected = true;
            threadPool.submit(this::listenForMessages);
            
            notifyConnectionStatusChanged(true);
            return true;
            
        } catch (Exception e) {
            notifyError("Failed to connect: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Ngắt kết nối khỏi server
     */
    public void disconnect() {
        if (isConnected && socket != null) {
            try {
                // Send leave message
                Message leaveMessage = new Message(Constants.MESSAGE_TYPE_LEAVE, username, "");
                sendMessage(leaveMessage);
                
                // Close connection
                isConnected = false;
                socket.close();
                
                if (threadPool != null && !threadPool.isShutdown()) {
                    threadPool.shutdown();
                    try {
                        if (!threadPool.awaitTermination(2, TimeUnit.SECONDS)) {
                            threadPool.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        threadPool.shutdownNow();
                    }
                }
                
                notifyConnectionStatusChanged(false);
                
            } catch (Exception e) {
                System.err.println("Error during disconnect: " + e.getMessage());
            }
        }
    }
    
    /**
     * Gửi tin nhắn text
     */
    public void sendTextMessage(String content) {
        sendTextMessage(content, null);
    }
    
    /**
     * Gửi tin nhắn text với recipient cụ thể
     */
    public void sendTextMessage(String content, String recipient) {
        if (!isConnected) {
            notifyError("Not connected to server");
            return;
        }
        
        if (content == null || content.trim().isEmpty()) {
            return;
        }
        
        Message message = new Message(Constants.MESSAGE_TYPE_TEXT, username, content.trim());
        if (recipient != null && !recipient.trim().isEmpty()) {
            message.setRecipient(recipient.trim());
        }
        
        sendMessage(message);
    }
    
    /**
     * Gửi message đến server
     */
    private void sendMessage(Message message) {
        try {
            String messageStr = message.serialize();
            byte[] data = messageStr.getBytes();
            
            DatagramPacket packet = new DatagramPacket(
                data, data.length, serverAddress, serverPort
            );
            
            socket.send(packet);
            
        } catch (Exception e) {
            notifyError("Failed to send message: " + e.getMessage());
        }
    }
    
    /**
     * Lắng nghe tin nhắn từ server
     */
    private void listenForMessages() {
        byte[] buffer = new byte[Constants.BUFFER_SIZE];
        
        while (isConnected && socket != null && !socket.isClosed()) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                // Xử lý tin nhắn trong thread riêng
                threadPool.submit(() -> clientHandler.handleMessage(packet));
                
            } catch (Exception e) {
                if (isConnected) {
                    System.err.println("Error receiving message: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Xử lý tin nhắn nhận được (được gọi từ ClientHandler)
     */
    public void notifyMessageReceived(Message message) {
        if (messageListener != null) {
            messageListener.onMessageReceived(message);
        }
    }
    
    /**
     * Thông báo thay đổi trạng thái kết nối
     */
    private void notifyConnectionStatusChanged(boolean connected) {
        if (messageListener != null) {
            messageListener.onConnectionStatusChanged(connected);
        }
    }
    
    /**
     * Thông báo lỗi
     */
    private void notifyError(String error) {
        if (messageListener != null) {
            messageListener.onError(error);
        }
    }
    
    // Getters and Setters
    public boolean isConnected() {
        return isConnected;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getServerHost() {
        return serverAddress != null ? serverAddress.getHostAddress() : null;
    }
    
    public int getServerPort() {
        return serverPort;
    }
    
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }
}

