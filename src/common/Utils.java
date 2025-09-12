package common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * Class chứa các phương thức tiện ích được sử dụng chung
 */
public class Utils {
    
    private static final Pattern IP_PATTERN = Pattern.compile(
        "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$"
    );
    
    /**
     * Kiểm tra xem một string có phải là địa chỉ IP hợp lệ không
     */
    public static boolean isValidIP(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }
        return IP_PATTERN.matcher(ip.trim()).matches();
    }
    
    /**
     * Kiểm tra xem một port có hợp lệ không
     */
    public static boolean isValidPort(int port) {
        return port >= 1 && port <= 65535;
    }
    
    /**
     * Kiểm tra xem một port string có hợp lệ không
     */
    public static boolean isValidPort(String portStr) {
        if (portStr == null || portStr.trim().isEmpty()) {
            return false;
        }
        
        try {
            int port = Integer.parseInt(portStr.trim());
            return isValidPort(port);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Kiểm tra xem host có thể kết nối được không
     */
    public static boolean isHostReachable(String host) {
        if (host == null || host.trim().isEmpty()) {
            return false;
        }
        
        try {
            InetAddress address = InetAddress.getByName(host.trim());
            return address.isReachable(Constants.CONNECTION_TIMEOUT);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Lấy địa chỉ IP local
     */
    public static String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }
    
    /**
     * Kiểm tra username có hợp lệ không
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        String trimmed = username.trim();
        // Username không được chứa ký tự separator
        if (trimmed.contains(Constants.MESSAGE_SEPARATOR)) {
            return false;
        }
        
        // Username phải có độ dài từ 2-20 ký tự
        return trimmed.length() >= 2 && trimmed.length() <= 20;
    }
    
    /**
     * Làm sạch string input
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().replaceAll("\\s+", " ");
    }
    
    /**
     * Format thời gian hiện tại
     */
    public static String getCurrentTimeString() {
        return java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    /**
     * Tạo thông báo hệ thống
     */
    public static Message createSystemMessage(String content) {
        return new Message(Constants.MESSAGE_TYPE_NOTIFICATION, "SYSTEM", content);
    }
    
    /**
     * Kiểm tra xem message có phải là message hệ thống không
     */
    public static boolean isSystemMessage(Message message) {
        return message != null && 
               Constants.MESSAGE_TYPE_NOTIFICATION.equals(message.getType()) &&
               "SYSTEM".equals(message.getSender());
    }
}
