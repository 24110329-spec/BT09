package vn.iotstar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    private String brand;

    private String madein;

    @PositiveOrZero(message = "Giá phải >= 0")
    private float price;

    @NotNull(message = "Vui lòng chọn người quản lý")
    private Long userId;

    private String userFullName;
    private String userEmail;
}
