package hust.hoangson.user.response;

import hust.hoangson.user.domain.entity.UserProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    private String userId;
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private String avatarUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserAddressResponse> addresses;

    public static UserDetailResponse of(UserProfileEntity dto) {
        UserDetailResponse res = new UserDetailResponse();

        res.userId = dto.getUserId();
        res.username = dto.getUsername();
        res.email = dto.getEmail();
        res.phone = dto.getPhone();
        res.fullName = dto.getFullName();
        res.avatarUrl = dto.getAvatarUrl();
        res.isActive = dto.getIsActive();
        res.createdAt = dto.getCreatedAt();
        res.updatedAt = dto.getUpdatedAt();

        res.addresses = new ArrayList<>();
        if (dto.getAddresses() != null) {
            dto.getAddresses().forEach(addr -> {
                UserAddressResponse a = new UserAddressResponse();
                a.setId(addr.getId());
                a.setRecipientName(addr.getRecipientName());
                a.setPhone(addr.getPhone());
                a.setAddressLine(addr.getAddressLine());
                a.setWardCode(addr.getWardCode());
                a.setDistrictCode(addr.getDistrictCode());
                a.setProvinceCode(addr.getProvinceCode());
                a.setIsDefault(addr.getIsDefault());
                a.setIsActive(addr.getIsActive());
                a.setCreatedAt(addr.getCreatedAt());
                a.setUpdatedAt(addr.getUpdatedAt());
                res.addresses.add(a);
            });
        }
        return res;
    }
}
