package hust.hoangson.user.serivce;

import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.request.UpdateUserProfileRequest;
import hust.hoangson.user.response.UserDetailResponse;
import hust.hoangson.user.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<UserResponse> searchUsers(SearchUserRequest req);
    void createUserFromEvent(UserCreatedEvent event);
    UserDetailResponse getUserDetail(String userId);
    UserDetailResponse updateUserProfile(UpdateUserProfileRequest req, String userId);
}
