package hust.hoangson.user.controller;

import hust.hoangson.user.request.CreateUserAddressRequest;
import hust.hoangson.user.request.UpdateUserAddressRequest;
import hust.hoangson.user.response.BaseResponse;
import hust.hoangson.user.response.UserAddressResponse;
import hust.hoangson.user.response.UserDetailResponse;
import hust.hoangson.user.serivce.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/address")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @GetMapping
    public ResponseEntity<?> getUserAddresses(@RequestHeader("X-User-Id") String userId) {
        Page<UserAddressResponse> result = userAddressService.getUserAddress(userId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody CreateUserAddressRequest req,
                                        @RequestHeader("X-User-Id") String userId) {
        UserAddressResponse result = userAddressService.addUserAddress(req, userId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @PutMapping("/update/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable UUID addressId,
                                           @RequestBody UpdateUserAddressRequest req,
                                           @RequestHeader("X-User-Id") String userId) {
        UserAddressResponse result = userAddressService.updateUserAddress(addressId, req, userId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

}

