package client;

import client.gui.ChatUI;
import javax.swing.*;

/**
 * Main class cho ứng dụng Client
 */
public class ClientApp {
    
    public static void main(String[] args) {
        // Khởi chạy giao diện client trong EDT
        SwingUtilities.invokeLater(() -> {
            try {
                ChatUI chatUI = new ChatUI();
                chatUI.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error starting client application: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
