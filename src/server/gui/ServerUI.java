package server.gui;

import common.*;
import server.ServerController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Giao diện người dùng cho Server
 */
public class ServerUI extends JFrame {
    private ServerController serverController;
    private JTextField portField;
    private JButton startButton;
    private JButton stopButton;
    private JTextArea logArea;
    private JList<String> clientList;
    private DefaultListModel<String> clientListModel;
    private JButton kickUserButton;
    private JLabel statusLabel;
    private JLabel clientCountLabel;
    
    public ServerUI() {
        serverController = new ServerController();
        serverController.setServerUI(this);
        initializeUI();
        setupEventHandlers();
    }
    
    /**
     * Public method để ServerController có thể gọi để log
     */
    public void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    /**
     * Khởi tạo giao diện
     */
    private void initializeUI() {
        setTitle(Constants.SERVER_TITLE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Top panel - Controls
        JPanel topPanel = createControlPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center panel - Split pane for logs and client list
        JSplitPane centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel - Status
        JPanel bottomPanel = createStatusPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Initially disable stop button
        stopButton.setEnabled(false);
    }
    
    /**
     * Tạo panel điều khiển
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Server Controls"));
        
        // Port configuration
        JLabel portLabel = new JLabel("Port:");
        portField = new JTextField(String.valueOf(Constants.DEFAULT_PORT), 8);
        
        // Buttons
        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");
        
        startButton.setPreferredSize(new Dimension(120, 30));
        stopButton.setPreferredSize(new Dimension(120, 30));
        
        panel.add(portLabel);
        panel.add(portField);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(startButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(stopButton);
        
        return panel;
    }
    
    /**
     * Tạo panel trung tâm
     */
    private JSplitPane createCenterPanel() {
        // Log panel
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Server Logs"));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.GREEN);
        
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logPanel.add(logScrollPane, BorderLayout.CENTER);
        
        // Client list panel
        JPanel clientPanel = new JPanel(new BorderLayout());
        clientPanel.setBorder(BorderFactory.createTitledBorder("Connected Clients"));
        
        // Client list với selection
        clientListModel = new DefaultListModel<>();
        clientList = new JList<>(clientListModel);
        clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientList.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        
        // Context menu cho kick user
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem kickMenuItem = new JMenuItem("Kick User");
        kickMenuItem.addActionListener(e -> kickSelectedUser());
        contextMenu.add(kickMenuItem);
        clientList.setComponentPopupMenu(contextMenu);
        
        JScrollPane clientScrollPane = new JScrollPane(clientList);
        clientScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        clientPanel.add(clientScrollPane, BorderLayout.CENTER);
        
        // Kick user button
        kickUserButton = new JButton("Kick Selected User");
        kickUserButton.setEnabled(false);
        clientPanel.add(kickUserButton, BorderLayout.SOUTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logPanel, clientPanel);
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.7);
        
        return splitPane;
    }
    
    /**
     * Tạo panel trạng thái
     */
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        statusLabel = new JLabel("Server stopped");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        clientCountLabel = new JLabel("Clients: 0");
        clientCountLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        panel.add(statusLabel, BorderLayout.WEST);
        panel.add(clientCountLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Thiết lập event handlers
     */
    private void setupEventHandlers() {
        // Start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        
        // Stop button
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });
        
        // Kick user button
        kickUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kickSelectedUser();
            }
        });
        
        // Client list selection listener
        clientList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                kickUserButton.setEnabled(clientList.getSelectedValue() != null);
            }
        });
        
        // Window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        
        // Start timer để cập nhật client list
        Timer updateTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClientList();
                updateClientCount();
            }
        });
        updateTimer.start();
    }
    
    /**
     * Khởi động server
     */
    private void startServer() {
        try {
            String portText = portField.getText().trim();
            
            if (!Utils.isValidPort(portText)) {
                JOptionPane.showMessageDialog(this,
                    "Invalid port number. Please enter a port between 1 and 65535.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int port = Integer.parseInt(portText);
            serverController = new ServerController(port);
            serverController.setServerUI(this);
            
            if (serverController.startServer()) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                portField.setEnabled(false);
                
                statusLabel.setText("Server running on port " + port);
                appendLog("Server started successfully on port " + port);
                
                JOptionPane.showMessageDialog(this,
                    Constants.SERVER_STARTED + " on port " + port,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to start server. Port may be in use.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error starting server: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Dừng server
     */
    private void stopServer() {
        try {
            serverController.stopServer();
            
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            portField.setEnabled(true);
            
            statusLabel.setText("Server stopped");
            clientListModel.clear();
            kickUserButton.setEnabled(false);
            updateClientCount();
            
            appendLog("Server stopped");
            
            JOptionPane.showMessageDialog(this,
                Constants.SERVER_STOPPED,
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error stopping server: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Kick user được chọn
     */
    private void kickSelectedUser() {
        String selectedUser = clientList.getSelectedValue();
        if (selectedUser == null || selectedUser.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please select a user to kick.",
                "No User Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to kick user '" + selectedUser + "'?",
            "Confirm Kick User",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                serverController.kickUser(selectedUser);
                appendLog("Admin kicked user: " + selectedUser);
                
                JOptionPane.showMessageDialog(this,
                    "User '" + selectedUser + "' has been kicked from the server.",
                    "User Kicked",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                appendLog("Error kicking user " + selectedUser + ": " + e.getMessage());
                JOptionPane.showMessageDialog(this,
                    "Error kicking user: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Cập nhật danh sách clients
     */
    private void updateClientList() {
        if (serverController != null && serverController.isRunning()) {
            SwingUtilities.invokeLater(() -> {
                clientListModel.clear();
                for (String username : serverController.getConnectedUsers()) {
                    clientListModel.addElement(username);
                }
            });
        }
    }
    
    /**
     * Cập nhật số lượng clients
     */
    private void updateClientCount() {
        int count = serverController != null ? serverController.getClientCount() : 0;
        clientCountLabel.setText("Clients: " + count);
    }
    
    /**
     * Thêm log message vào log area (private)
     */
    private void appendLogMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            String timestamp = Utils.getCurrentTimeString();
            logArea.append("[" + timestamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    /**
     * Thoát ứng dụng
     */
    private void exitApplication() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to exit? This will stop the server.",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION);
            
        if (choice == JOptionPane.YES_OPTION) {
            if (serverController != null && serverController.isRunning()) {
                serverController.stopServer();
            }
            System.exit(0);
        }
    }
}
