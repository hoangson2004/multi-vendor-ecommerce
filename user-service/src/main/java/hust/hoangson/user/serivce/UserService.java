package hust.hoangson.user.serivce;

import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<UserResponse> searchUsers(SearchUserRequest request);
    void createUserFromEvent(UserCreatedEvent event);
}
