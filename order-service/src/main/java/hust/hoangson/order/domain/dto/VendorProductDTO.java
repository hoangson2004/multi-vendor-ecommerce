package hust.hoangson.order.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VendorProductDTO {
    private String vendorProductId;
    private String vendorId;
    private String brand;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
