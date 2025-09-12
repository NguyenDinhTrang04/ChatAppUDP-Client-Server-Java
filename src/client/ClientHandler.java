package client;

import common.*;
import java.net.*;

/**
 * Handler xử lý các tin nhắn từ server
 */
public class ClientHandler {
    private ClientController clientController;
    
    public ClientHandler(ClientController clientController) {
        this.clientController = clientController;
    }
    
    /**
     * Xử lý tin nhắn từ server
     */
    public void handleMessage(DatagramPacket packet) {
        try {
            String messageStr = new String(packet.getData(), 0, packet.getLength());
            Message message = Message.deserialize(messageStr);
            
            if (message == null) {
                System.err.println("Received invalid message format");
                return;
            }
            
            switch (message.getType()) {
                case Constants.MESSAGE_TYPE_TEXT:
                    handleTextMessage(message);
                    break;
                    
                case Constants.MESSAGE_TYPE_NOTIFICATION:
                    handleNotificationMessage(message);
                    break;
                    
                case Constants.MESSAGE_TYPE_USER_LIST:
                    handleUserListMessage(message);
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
     * Xử lý tin nhắn text
     */
    private void handleTextMessage(Message message) {
        // Notify controller về tin nhắn mới
        clientController.notifyMessageReceived(message);
    }
    
    /**
     * Xử lý tin nhắn thông báo từ hệ thống
     */
    private void handleNotificationMessage(Message message) {
        // Notify controller về thông báo
        clientController.notifyMessageReceived(message);
    }
    
    /**
     * Xử lý danh sách users
     */
    private void handleUserListMessage(Message message) {
        // Notify controller về danh sách users
        clientController.notifyMessageReceived(message);
    }
}
