import javax.swing.*;
import server.ServerApp;
import client.ClientApp;

/**
 * Main class - Launcher cho ứng dụng Chat UDP
 * Cho phép user chọn chạy Server hoặc Client
 */
public class Main {
    
    public static void main(String[] args) {
        // Hiển thị dialog chọn chế độ
        String[] options = {"Server", "Client", "Exit"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose application mode:",
            "UDP Chat Application",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1] // Default to Client
        );
        
        switch (choice) {
            case 0: // Server
                ServerApp.main(args);
                break;
            case 1: // Client
                ClientApp.main(args);
                break;
            default: // Exit or closed dialog
                System.exit(0);
                break;
        }
    }
}
