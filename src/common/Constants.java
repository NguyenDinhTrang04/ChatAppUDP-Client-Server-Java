package common;

/**
 * Class chứa các hằng số được sử dụng chung trong ứng dụng
 */
public class Constants {
    // Cấu hình mạng
    public static final String DEFAULT_HOST = "26.189.120.180";
    public static final int DEFAULT_PORT = 8888;
    public static final int BUFFER_SIZE = 1024;
    
    // Loại tin nhắn
    public static final String MESSAGE_TYPE_TEXT = "TEXT";
    public static final String MESSAGE_TYPE_JOIN = "JOIN";
    public static final String MESSAGE_TYPE_LEAVE = "LEAVE";
    public static final String MESSAGE_TYPE_USER_LIST = "USER_LIST";
    public static final String MESSAGE_TYPE_NOTIFICATION = "NOTIFICATION";
    
    // Separator cho message
    public static final String MESSAGE_SEPARATOR = "|";
    
    // UI Constants
    public static final String APP_TITLE = "UDP Chat Application";
    public static final String SERVER_TITLE = "UDP Chat Server";
    public static final String CLIENT_TITLE = "UDP Chat Client";
    
    // Timeout
    public static final int CONNECTION_TIMEOUT = 5000; // 5 seconds
    
    // Status messages
    public static final String SERVER_STARTED = "Server started successfully";
    public static final String SERVER_STOPPED = "Server stopped";
    public static final String CLIENT_CONNECTED = "Connected to server";
    public static final String CLIENT_DISCONNECTED = "Disconnected from server";
}
