# ChatApp UDP - Hướng dẫn chạy ứng dụng

## Các file chính để chạy:

### 1. File JAR (Khuyến nghị)

-   **ChatAppUDP.jar** - File chính chứa toàn bộ ứng dụng

### 2. Batch Files để chạy dễ dàng:

-   **start-launcher.bat** - Khởi động launcher chính (chọn Server/Client)
-   **start-server.bat** - Chạy trực tiếp Server
-   **start-client.bat** - Chạy trực tiếp Client

## Cách sử dụng:

### Cách 1: Sử dụng Launcher (Dễ nhất)

1. Double-click `start-launcher.bat`
2. Chọn "Server" hoặc "Client" trong dialog
3. Sử dụng ứng dụng

### Cách 2: Chạy riêng Server và Client

1. **Chạy Server trước:**

    - Double-click `start-server.bat`
    - Server sẽ khởi động và chờ kết nối

2. **Chạy Client:**
    - Double-click `start-client.bat`
    - Nhập username và connect tới server

### Cách 3: Sử dụng command line

```bash
# Chạy launcher
java -jar ChatAppUDP.jar

# Hoặc chạy trực tiếp:
java -cp ChatAppUDP.jar server.ServerApp    # Server
java -cp ChatAppUDP.jar client.ClientApp    # Client
```

## Yêu cầu hệ thống:

-   Java Runtime Environment (JRE) 8 trở lên
-   Windows OS (cho batch files)
-   Port 8888 phải available

## Lưu ý:

-   Server phải được khởi động trước Client
-   Default server IP: 26.189.120.180:8888
-   Có thể thay đổi IP trong Constants.java và compile lại

## Troubleshooting:

-   Nếu lỗi "jar not found": Đảm bảo file ChatAppUDP.jar ở cùng thư mục
-   Nếu không kết nối được: Kiểm tra firewall và IP server
-   Nếu lỗi "java not found": Cài đặt Java và thêm vào PATH

## File structure cho distribution:

```
ChatAppUDP/
├── ChatAppUDP.jar          # Main application
├── start-launcher.bat      # Easy launcher
├── start-server.bat        # Direct server start
├── start-client.bat        # Direct client start
└── README-DEPLOY.md        # This file
```
