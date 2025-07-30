package hust.hoangson.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressResponse {
    private UUID id;
    private String recipientName;
    private String phone;
    private String addressLine;
    private String wardCode;
    private String districtCode;
    private String provinceCode;
    private Boolean isDefault;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
