package server;

import common.*;
import java.net.*;

/**
 * Handler xử lý các tin nhắn từ clients
 */
public class ServerHandler {
    private ServerController serverController;
    
    public ServerHandler(ServerController serverController) {
        this.serverController = serverController;
    }
    
    /**
     * Xử lý tin nhắn từ client
     */
    public void handleMessage(DatagramPacket packet) {
        try {
            String messageStr = new String(packet.getData(), 0, packet.getLength());
            Message message = Message.deserialize(messageStr);
            
            if (message == null) {
                System.err.println("Received invalid message format");
                return;
            }
            
            InetSocketAddress clientAddress = new InetSocketAddress(
                packet.getAddress(), packet.getPort()
            );
            
            switch (message.getType()) {
                case Constants.MESSAGE_TYPE_JOIN:
                    handleJoinRequest(message, clientAddress);
                    break;
                    
                case Constants.MESSAGE_TYPE_LEAVE:
                    handleLeaveRequest(message);
                    break;
                    
                case Constants.MESSAGE_TYPE_TEXT:
                    handleTextMessage(message);
                    break;
                    
                default:
                    System.err.println("Unknown message type: " + message.getType());
                    break;
            }
            
        } catch (Exception e) {
            System.err.println("Error handling message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Xử lý yêu cầu join
     */
    private void handleJoinRequest(Message message, InetSocketAddress clientAddress) {
        String username = message.getSender();
        
        if (!Utils.isValidUsername(username)) {
            // Gửi thông báo lỗi về client
            Message errorMessage = new Message(
                Constants.MESSAGE_TYPE_NOTIFICATION,
                "SERVER",
                "Invalid username. Username must be 2-20 characters and not contain special characters."
            );
            serverController.sendToClient(errorMessage, clientAddress);
            return;
        }
        
        // Kiểm tra username đã tồn tại chưa
        if (serverController.getConnectedUsers().contains(username)) {
            Message errorMessage = new Message(
                Constants.MESSAGE_TYPE_NOTIFICATION,
                "SERVER",
                "Username already exists. Please choose another username."
            );
            serverController.sendToClient(errorMessage, clientAddress);
            return;
        }
        
        // Thêm client vào danh sách (sẽ broadcast user list cho tất cả clients)
        serverController.addClient(username, clientAddress);
        
        // Gửi thông báo thành công
        Message successMessage = new Message(
            Constants.MESSAGE_TYPE_NOTIFICATION,
            "SERVER",
            "Successfully joined the chat!"
        );
        serverController.sendToClient(successMessage, clientAddress);
        
        // Đảm bảo client mới nhận được danh sách user hiện tại (gửi lại để chắc chắn)
        // Delay nhỏ để đảm bảo client đã sẵn sàng nhận
        try {
            Thread.sleep(100); // 100ms delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        serverController.sendUserListToClient(clientAddress);
        
        System.out.println("User " + username + " joined from " + clientAddress);
    }
    
    /**
     * Xử lý yêu cầu leave
     */
    private void handleLeaveRequest(Message message) {
        String username = message.getSender();
        serverController.removeClient(username);
        
        System.out.println("User " + username + " left the chat");
    }
    
    /**
     * Xử lý tin nhắn text
     */
    private void handleTextMessage(Message message) {
        String sender = message.getSender();
        
        // Kiểm tra sender có trong danh sách clients không
        if (!serverController.getConnectedUsers().contains(sender)) {
            System.err.println("Received message from unregistered user: " + sender);
            return;
        }
        
        // Kiểm tra nội dung tin nhắn
        String content = Utils.sanitizeInput(message.getContent());
        if (content.isEmpty()) {
            return;
        }
        
        message.setContent(content);
        
        // Nếu có recipient cụ thể, gửi private message
        if (message.getRecipient() != null && !message.getRecipient().trim().isEmpty()) {
            handlePrivateMessage(message);
        } else {
            // Broadcast tin nhắn public
            serverController.broadcastMessage(message);
            System.out.println("Broadcast message from " + sender + ": " + content);
        }
    }
    
    /**
     * Xử lý tin nhắn private
     */
    private void handlePrivateMessage(Message message) {
        String recipient = message.getRecipient().trim();
        String sender = message.getSender();
        
        // Kiểm tra recipient có tồn tại không
        if (!serverController.getConnectedUsers().contains(recipient)) {
            // Gửi thông báo lỗi về sender
            Message errorMessage = new Message(
                Constants.MESSAGE_TYPE_NOTIFICATION,
                "SERVER",
                "User " + recipient + " not found."
            );
            
            InetSocketAddress senderAddress = serverController.getConnectedClients().get(sender);
            if (senderAddress != null) {
                serverController.sendToClient(errorMessage, senderAddress);
            }
            return;
        }
        
        // Gửi tin nhắn đến recipient
        InetSocketAddress recipientAddress = serverController.getConnectedClients().get(recipient);
        if (recipientAddress != null) {
            serverController.sendToClient(message, recipientAddress);
            
            // Gửi confirmation về sender
            Message confirmMessage = new Message(
                Constants.MESSAGE_TYPE_NOTIFICATION,
                "SERVER",
                "Private message sent to " + recipient
            );
            
            InetSocketAddress senderAddress = serverController.getConnectedClients().get(sender);
            if (senderAddress != null) {
                serverController.sendToClient(confirmMessage, senderAddress);
            }
            
            System.out.println("Private message from " + sender + " to " + recipient + ": " + message.getContent());
        }
    }
}
