package common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class đại diện cho một tin nhắn trong hệ thống chat
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String type;
    private String sender;
    private String content;
    private String timestamp;
    private String recipient; // null nếu là broadcast message
    
    // Constructor mặc định
    public Message() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    // Constructor với các tham số
    public Message(String type, String sender, String content) {
        this();
        this.type = type;
        this.sender = sender;
        this.content = content;
    }
    
    public Message(String type, String sender, String content, String recipient) {
        this(type, sender, content);
        this.recipient = recipient;
    }
    
    // Getters and Setters
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    // Chuyển đổi message thành string để gửi qua mạng
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(type != null ? type : "");
        sb.append(Constants.MESSAGE_SEPARATOR);
        sb.append(sender != null ? sender : "");
        sb.append(Constants.MESSAGE_SEPARATOR);
        sb.append(content != null ? content : "");
        sb.append(Constants.MESSAGE_SEPARATOR);
        sb.append(timestamp != null ? timestamp : "");
        sb.append(Constants.MESSAGE_SEPARATOR);
        sb.append(recipient != null ? recipient : "");
        
        return sb.toString();
    }
    
    // Tạo message từ string
    public static Message deserialize(String messageStr) {
        if (messageStr == null || messageStr.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = messageStr.split("\\" + Constants.MESSAGE_SEPARATOR);
        Message message = new Message();
        
        if (parts.length > 0) message.setType(parts[0].isEmpty() ? null : parts[0]);
        if (parts.length > 1) message.setSender(parts[1].isEmpty() ? null : parts[1]);
        if (parts.length > 2) message.setContent(parts[2].isEmpty() ? null : parts[2]);
        if (parts.length > 3) message.setTimestamp(parts[3].isEmpty() ? null : parts[3]);
        if (parts.length > 4) message.setRecipient(parts[4].isEmpty() ? null : parts[4]);
        
        return message;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: %s", timestamp, sender, content);
    }
}
