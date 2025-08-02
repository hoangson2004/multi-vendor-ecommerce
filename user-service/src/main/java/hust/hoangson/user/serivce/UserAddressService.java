package hust.hoangson.user.serivce;

import hust.hoangson.user.request.CreateUserAddressRequest;
import hust.hoangson.user.request.UpdateUserAddressRequest;
import hust.hoangson.user.response.UserAddressResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserAddressService {
    Page<UserAddressResponse> getUserAddress(String userId);
    UserAddressResponse addUserAddress(CreateUserAddressRequest req, String userId);
    UserAddressResponse updateUserAddress(UUID addressId, UpdateUserAddressRequest req, String userId);
}
