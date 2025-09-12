# Hướng dẫn đóng góp

Cảm ơn bạn đã quan tâm đến dự án UDP Chat Application!

## Quy trình chung

1. Fork repository và tạo branch mới: `feature/ten-tinh-nang` hoặc `fix/ten-loi`.
2. Viết code tuân theo style hiện tại, giữ tên biến rõ ràng.
3. Đảm bảo build thành công bằng `run.bat`.
4. Kiểm tra thủ công chức năng liên quan (server, client, private message, ...).
5. Cập nhật `README.md` hoặc `CHANGELOG.md` nếu cần.
6. Tạo Pull Request mô tả rõ thay đổi, ảnh minh họa (nếu có).

## Commit message

Sử dụng định dạng đề xuất:

-   `feat: thêm tính năng ...`
-   `fix: sửa lỗi ...`
-   `refactor: chỉnh sửa cấu trúc ...`
-   `docs: cập nhật tài liệu ...`
-   `chore: việc lặt vặt ...`

## Code style

-   Java 8+.
-   Tránh logic phức tạp trong UI; đưa vào controller/handler.
-   Sử dụng hằng số trong `Constants`.

## Báo lỗi (Issue)

Khi tạo issue, vui lòng cung cấp:

-   Mô tả lỗi
-   Cách tái hiện
-   Kỳ vọng vs thực tế
-   Môi trường (OS, Java version)

## Tính năng gợi ý

Bạn có thể đề xuất: mã hóa tin nhắn, file transfer, chat room, logging ra file, v.v.

## Liên hệ

Mở issue hoặc tạo PR để thảo luận. Liên hệ trực tiếp: trangnguyendinh17@gmail.com
