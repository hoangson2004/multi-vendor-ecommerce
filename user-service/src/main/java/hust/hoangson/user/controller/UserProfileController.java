package hust.hoangson.user.controller;

import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.request.UpdateUserProfileRequest;
import hust.hoangson.user.response.BaseResponse;
import hust.hoangson.user.response.UserDetailResponse;
import hust.hoangson.user.response.UserResponse;
import hust.hoangson.user.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserProfileController {
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@ModelAttribute SearchUserRequest req) {
        Page<UserResponse> result = userService.searchUsers(req);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetail(@PathVariable String userId) {
        UserDetailResponse result = userService.getUserDetail(userId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUsers(@RequestBody UpdateUserProfileRequest req,
                                       @RequestHeader("X-User-Id") String userId) {
        UserDetailResponse result =  userService.updateUserProfile(req, userId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

}
