# Ví dụ 1 + Ví dụ 3 — Spring Boot 4 + Security 7 + MapStruct
Dựa trên project Ví dụ 1 và hướng dẫn Spring Boot + Security 7 + MapStruct.

Chức năng: Register + OTP email, Login session, Logout, Forgot Password + OTP, CRUD/Search/Pagination User, Role USER/ADMIN, đếm User/Product, CRUD/Search/Pagination Product, upload Cloudinary, MapStruct DTO/Entity, Thymeleaf, SQL Server.

## Chạy
1. SQL Server chạy và có thể tạo DB `webst3` (Hibernate update).
2. Sửa `.env` từ `.env.example` nếu cần, đặc biệt MAIL và CLOUDINARY.
3. Maven: `mvn clean spring-boot:run`.
4. Tài khoản mẫu: `admin01 / 123456`, `user01 / 123456`.

OTP và upload ảnh cần cấu hình email/Cloudinary thật.
