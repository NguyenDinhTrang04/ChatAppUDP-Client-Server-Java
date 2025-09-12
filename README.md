# UDP Chat Application

Ứng dụng chat sử dụng giao thức UDP được viết bằng Java với giao diện Swing.

## Cấu trúc dự án

```
ChatAppUDP/
├── resources/
│   ├── config.properties       # File cấu hình
│   └── ui/                     # Tài nguyên UI (nếu có)
├── src/
│   ├── Main.java              # Launcher chính
│   ├── client/                # Package client
│   │   ├── ClientApp.java     # Main class cho client
│   │   ├── ClientController.java # Logic điều khiển client
│   │   ├── ClientHandler.java # Xử lý tin nhắn từ server
│   │   └── gui/
│   │       └── ChatUI.java    # Giao diện chat client
│   ├── common/                # Package chung
│   │   ├── Constants.java     # Các hằng số
│   │   ├── Message.java       # Class tin nhắn
│   │   └── Utils.java         # Các tiện ích
│   └── server/                # Package server
│       ├── ServerApp.java     # Main class cho server
│       ├── ServerController.java # Logic điều khiển server
│       ├── ServerHandler.java # Xử lý tin nhắn từ client
│       └── gui/
│           └── ServerUI.java  # Giao diện server
```

## Tính năng

### Server

- Khởi động/dừng server trên port tùy chọn
- Quản lý danh sách clients kết nối
- Hiển thị log hoạt động realtime
- Giao diện quản trị trực quan

### Client

- Kết nối đến server với username
- Gửi tin nhắn public cho tất cả users
- Gửi tin nhắn private (@username message)
- Hiển thị danh sách users online
- Giao diện chat thân thiện

## Cách sử dụng

### Chạy ứng dụng

1. **Compile toàn bộ dự án:**

   ```bash
   javac -d . src/**/*.java
   ```

2. **Chạy launcher chính:**
   ```bash
   java Main
   ```
   Chọn "Server" hoặc "Client" từ dialog.

### Hoặc chạy riêng biệt:

**Chạy Server:**

```bash
java server.ServerApp
```

**Chạy Client:**

```bash
java client.ClientApp
```

## Hướng dẫn sử dụng

### Server

1. Nhập port muốn sử dụng (mặc định: 8888)
2. Click "Start Server"
3. Server sẽ hiển thị log hoạt động và danh sách clients

### Client

1. Nhập thông tin server (host, port)
2. Nhập username (2-20 ký tự, không chứa ký tự đặc biệt)
3. Click "Connect"
4. Sau khi kết nối thành công:
   - Gõ tin nhắn và Enter để gửi public message
   - Gõ `@username message` để gửi private message
   - Double-click vào user trong danh sách để bắt đầu private chat

## Giao thức tin nhắn

Tin nhắn được serialize theo format:

```
TYPE|SENDER|CONTENT|TIMESTAMP|RECIPIENT
```

### Các loại tin nhắn:

- `JOIN`: Yêu cầu tham gia chat
- `LEAVE`: Yêu cầu rời khỏi chat
- `TEXT`: Tin nhắn text
- `NOTIFICATION`: Thông báo hệ thống
- `USER_LIST`: Danh sách users online

## Cấu hình

File `resources/config.properties` chứa các cấu hình mặc định:

- Port server mặc định
- Kích thước buffer
- Timeout kết nối
- Cài đặt UI

## Yêu cầu hệ thống

- Java 8 hoặc cao hơn
- Hệ điều hành hỗ trợ Java Swing
- Quyền truy cập mạng (firewall có thể cần cấu hình)

## Lưu ý

- Server phải được khởi động trước khi clients kết nối
- Mỗi client cần username unique
- Ứng dụng sử dụng UDP nên có thể mất tin nhắn trong mạng không ổn định
- Private message có format: `@recipient message content`

## Xử lý lỗi

- Kiểm tra port không bị chiếm dụng
- Đảm bảo firewall cho phép kết nối
- Username phải hợp lệ (2-20 ký tự)
- Server phải chạy trước khi client kết nối
