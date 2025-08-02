package hust.hoangson.user.response;

import hust.hoangson.user.domain.entity.UserAddressEntity;
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

    public static UserAddressResponse of(UserAddressEntity addr) {
        UserAddressResponse res = new UserAddressResponse();

        res.setId(addr.getId());
        res.setRecipientName(addr.getRecipientName());
        res.setPhone(addr.getPhone());
        res.setAddressLine(addr.getAddressLine());
        res.setProvinceCode(addr.getProvinceCode());
        res.setIsDefault(addr.getIsDefault());
        res.setIsActive(addr.getIsActive());
        res.setCreatedAt(addr.getCreatedAt());
        res.setUpdatedAt(addr.getUpdatedAt());
        return res;
    }

}
