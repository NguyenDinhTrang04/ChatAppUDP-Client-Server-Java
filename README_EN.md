# UDP Chat Application

A Java Swing-based UDP chat application featuring server/client modes, public & private messaging, and a simple modular architecture.

## Features

-   Server start/stop with dynamic port
-   Online users list
-   Public & private messages (@username message)
-   Real-time activity log
-   Simple Swing UI

## Quick Start

```
# Compile
javac -d bin src/common/*.java src/server/*.java src/server/gui/*.java src/client/*.java src/client/gui/*.java src/Main.java

# Run main launcher
java -cp bin Main
```

Run individually:

```
java -cp bin server.ServerApp
java -cp bin client.ClientApp
```

## Message Protocol

Format: `TYPE|SENDER|CONTENT|TIMESTAMP|RECIPIENT`
Types: JOIN, LEAVE, TEXT, NOTIFICATION, USER_LIST

## Config

Edit `resources/config.properties` for defaults (port, buffer size, timeouts, UI settings).

## Roadmap

-   Encryption (optional)
-   File transfer
-   Chat rooms
-   Logging to file

## License

MIT. See `LICENSE`.
