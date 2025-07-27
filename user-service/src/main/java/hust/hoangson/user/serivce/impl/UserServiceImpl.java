package hust.hoangson.user.serivce.impl;

import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.user.domain.dto.UserDTO;
import hust.hoangson.user.domain.entity.UserProfileEntity;
import hust.hoangson.user.repository.UserRepository;
import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.response.UserResponse;
import hust.hoangson.user.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Page<UserResponse> searchUsers(SearchUserRequest req) {
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getLimit());

        String email = req.getEmail();
        if (req.getEmail() != null && !req.getEmail().isEmpty()) {
            email = email.toLowerCase().trim();
        }

        String fullname = req.getFullname();
        if (req.getFullname() != null && !req.getFullname().isEmpty()) {
            fullname = fullname.toLowerCase().trim();
        }

        String phone = req.getPhone();
        if (req.getPhone() != null && !req.getPhone().isEmpty()) {
            phone = phone.toLowerCase().trim();
        }

        Page<UserProfileEntity> pageUser = userRepository.getListUser(
                req.getUserId(),
                req.getUsername(),
                email,
                phone,
                fullname,
                req.getRole(),
                pageRequest);
        return pageUser.map(UserResponse::of);
    }

    public void createUserFromEvent(UserCreatedEvent event) {
        UserProfileEntity user = new UserProfileEntity();
        user.setUserId(event.getUserId());
        user.setUsername(event.getUsername());
        user.setEmail(event.getEmail());
        user.setFullName(event.getFullname());
        userRepository.save(user);
    }
}
