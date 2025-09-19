package client.gui;

import common.*;
import client.ClientController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Giao diện người dùng cho Client Chat
 */
public class ChatUI extends JFrame implements ClientController.MessageListener {
    private ClientController clientController;
    
    // Connection components
    private JTextField hostField;
    private JTextField portField;
    private JTextField usernameField;
    private JButton connectButton;
    private JButton disconnectButton;
    
    // Chat components
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    
    // Status components
    private JLabel statusLabel;
    private JLabel connectionInfoLabel;
    
    // Chat panel
    private JPanel chatPanel;
    private JPanel connectionPanel;
    private JPanel mainPanel;
    
    public ChatUI() {
        clientController = new ClientController();
        clientController.setMessageListener(this);
        
        initializeUI();
        setupEventHandlers();
    }
    
    /**
     * Khởi tạo giao diện
     */
    private void initializeUI() {
        setTitle(Constants.CLIENT_TITLE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Main panel with CardLayout
        mainPanel = new JPanel(new CardLayout());
        
        // Connection panel
        connectionPanel = createConnectionPanel();
        mainPanel.add(connectionPanel, "CONNECTION");
        
        // Chat panel
        chatPanel = createChatPanel();
        mainPanel.add(chatPanel, "CHAT");
        
        add(mainPanel);
        
        // Show connection panel initially
        showConnectionPanel();
    }
    
    /**
     * Tạo panel kết nối
     */
    private JPanel createConnectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Connect to Chat Server", JLabel.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Host
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Server Host:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        hostField = new JTextField(Constants.DEFAULT_HOST, 20);
        formPanel.add(hostField, gbc);
        
        // Port
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Server Port:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        portField = new JTextField(String.valueOf(Constants.DEFAULT_PORT), 20);
        formPanel.add(portField, gbc);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        usernameField = new JTextField(20);
        formPanel.add(usernameField, gbc);
        
        // Connect button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension(120, 35));
        formPanel.add(connectButton, gbc);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Tạo panel chat
     */
    private JPanel createChatPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Top panel - Connection info and disconnect button
        JPanel topPanel = createChatTopPanel();
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Center panel - Chat area and user list
        JSplitPane centerPanel = createChatCenterPanel();
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel - Message input và status kết hợp
        JPanel bottomPanel = createCombinedBottomPanel();
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Tạo panel top của chat
     */
    private JPanel createChatTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        connectionInfoLabel = new JLabel("Connected to server");
        connectionInfoLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        disconnectButton = new JButton("Disconnect");
        disconnectButton.setPreferredSize(new Dimension(100, 30));
        
        panel.add(connectionInfoLabel, BorderLayout.CENTER);
        panel.add(disconnectButton, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Tạo panel center của chat
     */
    private JSplitPane createChatCenterPanel() {
        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatScrollPane.setBorder(BorderFactory.createTitledBorder("Chat Messages"));
        
        // User list
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setFixedCellHeight(20);
        
        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setBorder(BorderFactory.createTitledBorder("Online Users"));
        userScrollPane.setPreferredSize(new Dimension(150, 0));
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatScrollPane, userScrollPane);
        splitPane.setDividerLocation(550);
        splitPane.setResizeWeight(0.8);
        
        return splitPane;
    }
    
    /**
     * Tạo panel bottom kết hợp message input và status
     */
    private JPanel createCombinedBottomPanel() {
        JPanel combinedPanel = new JPanel(new BorderLayout());
        
        // Message input panel
        JPanel messagePanel = createChatBottomPanel();
        combinedPanel.add(messagePanel, BorderLayout.CENTER);
        
        // Status panel
        JPanel statusPanel = createChatStatusPanel();
        combinedPanel.add(statusPanel, BorderLayout.SOUTH);
        
        return combinedPanel;
    }

    /**
     * Tạo panel bottom của chat
     */
    private JPanel createChatBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Send Message"));
        
        messageField = new JTextField();
        messageField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        
        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(80, 30));
        
        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Tạo panel status của chat
     */
    private JPanel createChatStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        panel.add(statusLabel, BorderLayout.WEST);
        
        return panel;
    }
    
    /**
     * Thiết lập event handlers
     */
    private void setupEventHandlers() {
        // Connect button
        connectButton.addActionListener(e -> connectToServer());
        
        // Disconnect button
        disconnectButton.addActionListener(e -> disconnectFromServer());
        
        // Send button
        sendButton.addActionListener(e -> sendMessage());
        
        // Message field enter key
        messageField.addActionListener(e -> sendMessage());
        
        // Username field enter key
        usernameField.addActionListener(e -> connectToServer());
        
        // User list double click for private message
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    startPrivateMessage();
                }
            }
        });
        
        // Window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
    }
    
    /**
     * Kết nối đến server
     */
    private void connectToServer() {
        String host = hostField.getText().trim();
        String portText = portField.getText().trim();
        String username = usernameField.getText().trim();
        
        if (host.isEmpty() || portText.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int port = Integer.parseInt(portText);
            
            connectButton.setEnabled(false);
            connectButton.setText("Connecting...");
            
            // Connect in background thread
            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    return clientController.connectToServer(host, port, username);
                }
                
                @Override
                protected void done() {
                    try {
                        boolean success = get();
                        if (!success) {
                            connectButton.setEnabled(true);
                            connectButton.setText("Connect");
                        }
                    } catch (Exception e) {
                        connectButton.setEnabled(true);
                        connectButton.setText("Connect");
                        JOptionPane.showMessageDialog(ChatUI.this,
                            "Connection failed: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid port number.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Ngắt kết nối khỏi server
     */
    private void disconnectFromServer() {
        clientController.disconnect();
    }
    
    /**
     * Gửi tin nhắn
     */
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (message.isEmpty()) {
            return;
        }
        
        // Check if it's a private message (starts with @username)
        if (message.startsWith("@")) {
            int spaceIndex = message.indexOf(' ');
            if (spaceIndex > 1) {
                String recipient = message.substring(1, spaceIndex);
                String content = message.substring(spaceIndex + 1);
                clientController.sendTextMessage(content, recipient);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid private message format. Use: @username message",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            clientController.sendTextMessage(message);
        }
        
        messageField.setText("");
    }
    
    /**
     * Bắt đầu private message với user được chọn
     */
    private void startPrivateMessage() {
        String selectedUser = userList.getSelectedValue();
        if (selectedUser != null && !selectedUser.equals(clientController.getUsername())) {
            messageField.setText("@" + selectedUser + " ");
            messageField.requestFocus();
            messageField.setCaretPosition(messageField.getText().length());
        }
    }
    
    /**
     * Hiển thị panel kết nối
     */
    private void showConnectionPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "CONNECTION");
        hostField.requestFocus();
    }
    
    /**
     * Hiển thị panel chat
     */
    private void showChatPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "CHAT");
        messageField.requestFocus();
    }
    
    /**
     * Thêm tin nhắn vào chat area
     */
    private void appendToChatArea(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }
    
    /**
     * Cập nhật danh sách users
     */
    private void updateUserList(String userListStr) {
        SwingUtilities.invokeLater(() -> {
            userListModel.clear();
            if (userListStr != null && !userListStr.trim().isEmpty()) {
                String[] users = userListStr.split(",");
                for (String user : users) {
                    user = user.trim();
                    if (!user.isEmpty()) {
                        userListModel.addElement(user);
                    }
                }
            }
        });
    }
    
    /**
     * Thoát ứng dụng
     */
    private void exitApplication() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to exit?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION);
            
        if (choice == JOptionPane.YES_OPTION) {
            if (clientController.isConnected()) {
                clientController.disconnect();
            }
            System.exit(0);
        }
    }
    
    // MessageListener implementation
    @Override
    public void onMessageReceived(Message message) {
        SwingUtilities.invokeLater(() -> {
            switch (message.getType()) {
                case Constants.MESSAGE_TYPE_TEXT:
                    if (message.getRecipient() != null) {
                        // Private message
                        appendToChatArea("[PRIVATE] " + message.toString());
                    } else {
                        // Public message
                        appendToChatArea(message.toString());
                    }
                    break;
                    
                case Constants.MESSAGE_TYPE_NOTIFICATION:
                    appendToChatArea("*** " + message.getContent() + " ***");
                    break;
                    
                case Constants.MESSAGE_TYPE_USER_LIST:
                    updateUserList(message.getContent());
                    break;
            }
        });
    }
    
    @Override
    public void onConnectionStatusChanged(boolean isConnected) {
        SwingUtilities.invokeLater(() -> {
            if (isConnected) {
                showChatPanel();
                connectionInfoLabel.setText("Connected to " + 
                    clientController.getServerHost() + ":" + clientController.getServerPort() +
                    " as " + clientController.getUsername());
                statusLabel.setText("Connected");
                appendToChatArea("*** Connected to server ***");
            } else {
                showConnectionPanel();
                connectButton.setEnabled(true);
                connectButton.setText("Connect");
                chatArea.setText("");
                userListModel.clear();
                statusLabel.setText("Disconnected");
            }
        });
    }
    
    @Override
    public void onError(String error) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                error,
                "Error",
                JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Error: " + error);
        });
    }
}