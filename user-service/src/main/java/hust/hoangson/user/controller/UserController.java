package hust.hoangson.user.controller;

import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.response.BaseResponse;
import hust.hoangson.user.response.UserResponse;
import hust.hoangson.user.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestBody SearchUserRequest request) {
        List<UserResponse> result = userService.searchUsers(request);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

}
