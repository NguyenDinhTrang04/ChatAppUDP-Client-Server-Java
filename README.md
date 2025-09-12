# UDP Chat Application

A UDP-based chat application written in Java with Swing GUI interface.

## Project Structure

```
ChatAppUDP/
├── resources/
│   ├── config.properties       # Configuration file
│   └── ui/                     # UI resources (if any)
├── src/
│   ├── Main.java              # Main launcher
│   ├── client/                # Client package
│   │   ├── ClientApp.java     # Main class for client
│   │   ├── ClientController.java # Client control logic
│   │   ├── ClientHandler.java # Message handler from server
│   │   └── gui/
│   │       └── ChatUI.java    # Client chat interface
│   ├── common/                # Common package
│   │   ├── Constants.java     # Constants
│   │   ├── Message.java       # Message class
│   │   └── Utils.java         # Utilities
│   └── server/                # Server package
│       ├── ServerApp.java     # Main class for server
│       ├── ServerController.java # Server control logic
│       ├── ServerHandler.java # Message handler from clients
│       └── gui/
│           └── ServerUI.java  # Server interface
```

## Features

### Server

-   Start/stop server on custom port
-   Manage connected clients list
-   Real-time activity logging
-   Visual administration interface

### Client

-   Connect to server with username
-   Send public messages to all users
-   Send private messages (@username message)
-   Display online users list
-   User-friendly chat interface

## Usage

### Running the application

1. **Compile the entire project:**

    ```bash
    javac -d . src/**/*.java
    ```

2. **Run the main launcher:**
    ```bash
    java Main
    ```
    Choose "Server" or "Client" from the dialog.

### Or run separately:

**Run Server:**

```bash
java server.ServerApp
```

**Run Client:**

```bash
java client.ClientApp
```

## User Guide

### Server

1. Enter the port you want to use (default: 8888)
2. Click "Start Server"
3. Server will display activity logs and client list

### Client

1. Enter server information (host, port)
2. Enter username (2-20 characters, no special characters)
3. Click "Connect"
4. After successful connection:
    - Type message and press Enter to send public message
    - Type `@username message` to send private message
    - Double-click on user in the list to start private chat

## Message Protocol

Messages are serialized in the format:

```
TYPE|SENDER|CONTENT|TIMESTAMP|RECIPIENT
```

### Message types:

-   `JOIN`: Request to join chat
-   `LEAVE`: Request to leave chat
-   `TEXT`: Text message
-   `NOTIFICATION`: System notification
-   `USER_LIST`: Online users list

## Configuration

File `resources/config.properties` contains default configurations:

-   Default server port
-   Buffer size
-   Connection timeout
-   UI settings

## System Requirements

-   Java 8 or higher
-   Operating system supporting Java Swing
-   Network access (firewall may need configuration)

## Notes

-   Server must be started before clients connect
-   Each client needs a unique username
-   Application uses UDP so messages may be lost on unstable networks
-   Private message format: `@recipient message content`

## Error Handling

-   Check that port is not occupied
-   Ensure firewall allows connections
-   Username must be valid (2-20 characters)
-   Server must be running before client connects
