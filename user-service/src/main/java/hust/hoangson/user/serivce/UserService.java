package hust.hoangson.user.serivce;

import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> searchUsers(SearchUserRequest request);
}
