package hust.hoangson.user.controller;

import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.response.BaseResponse;
import hust.hoangson.user.response.UserResponse;
import hust.hoangson.user.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@ModelAttribute SearchUserRequest request) {
        Page<UserResponse> result = userService.searchUsers(request);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

}
