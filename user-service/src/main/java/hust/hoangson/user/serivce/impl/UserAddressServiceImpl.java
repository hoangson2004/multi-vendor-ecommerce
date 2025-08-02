package hust.hoangson.user.serivce.impl;

import hust.hoangson.user.domain.entity.UserAddressEntity;
import hust.hoangson.user.repository.UserAddressRepository;
import hust.hoangson.user.request.CreateUserAddressRequest;
import hust.hoangson.user.request.UpdateUserAddressRequest;
import hust.hoangson.user.response.UserAddressResponse;
import hust.hoangson.user.serivce.UserAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressRepository userAddressRepository;

    @Override
    public Page<UserAddressResponse> getUserAddress(String userId) {
        Page<UserAddressEntity> addresses = userAddressRepository.findAddressByUserId(userId, Pageable.unpaged());
        return addresses.map(UserAddressResponse::of);
    }

    @Override
    @Transactional
    public UserAddressResponse addUserAddress(CreateUserAddressRequest req, String userId) {
        if (Boolean.TRUE.equals(req.getIsDefault())) {
            userAddressRepository.resetDefaultAddress(userId);
        }

        UserAddressEntity address = UserAddressEntity.builder()
                .userId(userId)
                .recipientName(req.getRecipientName())
                .phone(req.getPhone())
                .addressLine(req.getAddressLine())
                .wardCode(req.getWardCode())
                .districtCode(req.getDistrictCode())
                .provinceCode(req.getProvinceCode())
                .isDefault(Boolean.TRUE.equals(req.getIsDefault()))
                .isActive(true)
                .build();

        userAddressRepository.save(address);

        return UserAddressResponse.of(address);
    }

    @Override
    @Transactional
    public UserAddressResponse updateUserAddress(UUID addressId, UpdateUserAddressRequest req, String userId) {

        UserAddressEntity address = userAddressRepository.findById(addressId);

        if (address == null) {
            return null;
        }

        if (!address.getUserId().equals(userId)) {
            return null;
        }

        if (Boolean.TRUE.equals(req.getIsDefault())) {
            userAddressRepository.resetDefaultAddress(userId);
        }

        if (req.getRecipientName() != null) address.setRecipientName(req.getRecipientName());
        if (req.getPhone() != null) address.setPhone(req.getPhone());
        if (req.getAddressLine() != null) address.setAddressLine(req.getAddressLine());
        if (req.getWardCode() != null) address.setWardCode(req.getWardCode());
        if (req.getDistrictCode() != null) address.setDistrictCode(req.getDistrictCode());
        if (req.getProvinceCode() != null) address.setProvinceCode(req.getProvinceCode());
        if (req.getIsDefault() != null) address.setIsDefault(req.getIsDefault());
        if (req.getIsActive() != null) address.setIsActive(req.getIsActive());

        userAddressRepository.save(address);

        return UserAddressResponse.of(address);
    }

}
