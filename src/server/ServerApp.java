package server;

import server.gui.ServerUI;
import javax.swing.*;

/**
 * Main class cho ứng dụng Server
 */
public class ServerApp {
    
    public static void main(String[] args) {
        // Khởi chạy giao diện server trong EDT
        SwingUtilities.invokeLater(() -> {
            try {
                ServerUI serverUI = new ServerUI();
                serverUI.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error starting server application: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}