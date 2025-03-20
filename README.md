### Ứng dụng chat

### Công nghệ sử dụng 
- Netty Websocket, Kafka, Redis, MongoDB, Restful API, .. 
- Thiết kế hệ thống theo mô hình Microservices

### Các service chính trong hệ thống 
- Auth Service: Đăng kí, verify token khi gửi qua websocket, lấy thông tin session user 
- User Service: Lấy thông tin user, lấy danh sách bạn bè, lấy danh sách tin nhắn, lấy danh sách group chat, lấy danh sách group chat theo user
- WebSocket Service: Xử lý gửi tin nhắn qua websocket, gọi các service khác để lấy thông tin
### Hiện tại trong code base có 3 service này 

- Các service khác sẽ được phát triển
- Message Consumer: Listen message từ kafka để lưu thông tin vào DB 
- Message Service: Truy vấn lấy message cho user 
- Group Service: Quản lý group chat, lấy thông tin group chat, lấy danh sách group chat theo user
- Push Notification Service: Gửi thông báo khi có tin nhắn mới

### Chạy ứng dụng 
- Cài đặt mongodb, redis, kafka 
- Chạy 3 service Auth, User, WebSocket
- Đăng ký user mới thông qua auth service. Api: sign-up. User sẽ lưu trong redis, và mongodb
- Giả lập gửi tin nhắn qua websocket tại url: http://localhost:8181 .
Chỉnh sửa chính xác các userId, destId đã tạo phía trên thông qua /resources/message/*.json file
- Thử 2 request login và gửi tin nhắn bạn bè qua websocket 

### Add git workflow
- Fork project về tài khoản github của bạn

### Các bước chạy project 
- Chạy authen service:
- Gọi api sign up để tạo user mới. Tạo 2 user