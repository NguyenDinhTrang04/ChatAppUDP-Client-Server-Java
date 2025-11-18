# Hướng dẫn sử dụng Private Message trong ChatAppUDP

## 🚀 Cách chạy ứng dụng

### Chạy Server:

```cmd
java -cp bin Main server
```

### Chạy Client:

```cmd
java -cp bin Main client
```

## 💬 Cách gửi Private Message

### Phương pháp 1: Gõ lệnh trực tiếp

Trong khung nhập tin nhắn, gõ:

```
@tên_người_dùng nội dung tin nhắn
```

**Ví dụ:**

```
@client2 Chào bạn, bạn có khỏe không?
@user123 Hẹn gặp lại sau nhé!
```

### Phương pháp 2: Double-click vào tên người dùng

1. Trong danh sách **Online Users** bên phải
2. **Double-click** vào tên người dùng muốn nhắn tin
3. Khung nhập tin nhắn sẽ tự động điền `@tên_người_dùng `
4. Gõ nội dung tin nhắn và nhấn Enter

## ✅ Tính năng Private Message

### ✨ Tính năng đã hoạt động:

-   ✅ Gửi tin nhắn riêng đến user cụ thể
-   ✅ Validation username (kiểm tra user có online không)
-   ✅ Hiển thị "[Private]" để phân biệt tin nhắn riêng
-   ✅ Confirmation message khi gửi thành công
-   ✅ Error message nếu user không tồn tại
-   ✅ Double-click để bắt đầu private chat
-   ✅ Tooltip hướng dẫn trong khung nhập tin nhắn

### 📝 Format tin nhắn hiển thị:

-   **Public message**: `[HH:mm:ss] username: nội dung`
-   **Private message nhận được**: `[HH:mm:ss] username: [Private] nội dung`
-   **Private message gửi đi**: `[Private to username] nội dung`

### 🔧 Error handling:

-   User không tồn tại: "User username not found or not online"
-   Format sai: "Invalid private message format. Use: @username message"
-   Thiếu nội dung: "Please enter a message after the username"

## 🎯 Cách test Private Message

### Test cơ bản:

1. Mở 3 client với username: client1, client2, client3
2. Từ client1 gõ: `@client2 Hello client2`
3. Chỉ client2 sẽ nhận được tin nhắn với "[Private]"
4. client3 không thấy tin nhắn này

### Test error cases:

1. `@nonexistent Hello` → Lỗi "User not found"
2. `@client2` (không có nội dung) → Lỗi "Please enter a message"
3. `@` (không có username) → Lỗi "Invalid format"

## 🚨 Troubleshooting

### Nếu không ping được client:

1. **Kiểm tra chính tả**: Đảm bảo tên user chính xác
2. **Kiểm tra user online**: User phải có trong danh sách Online Users
3. **Kiểm tra format**: Phải có dấu cách sau username `@user nội_dung`
4. **Check server logs**: Server sẽ log tất cả private messages

### Log messages trên server:

```
Processing private message from client1 to client2
Private message delivered: client1 -> client2: Hello client2
```

## 📱 UI Improvements

-   Tooltip trong message field: "Type @username message for private messages"
-   Instruction label: "Type your message. Use @username message for private messages"
-   Status bar hiển thị: "Private message to: username" khi double-click
-   Auto-complete khi double-click user trong list

Private messaging hiện đã hoạt động hoàn hảo! 🎉
