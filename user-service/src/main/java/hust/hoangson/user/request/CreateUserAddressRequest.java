package hust.hoangson.user.request;

import lombok.Data;

@Data
public class CreateUserAddressRequest {
    private String recipientName;
    private String phone;
    private String addressLine;
    private String wardCode;
    private String districtCode;
    private String provinceCode;
    private Boolean isDefault;
}
