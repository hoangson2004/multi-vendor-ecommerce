package hust.hoangson.user.serivce.impl;

import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.user.domain.dto.UserDTO;
import hust.hoangson.user.domain.entity.UserProfileEntity;
import hust.hoangson.user.repository.UserRepository;
import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.request.UpdateUserProfileRequest;
import hust.hoangson.user.response.UserDetailResponse;
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

        String fullName = req.getFullName();
        if (req.getFullName() != null && !req.getFullName().isEmpty()) {
            fullName = fullName.toLowerCase().trim();
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
                fullName,
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

    public UserDetailResponse getUserDetail(String userId) {
        UserProfileEntity user = userRepository.findByUserId(userId).get();
        return UserDetailResponse.of(user);
    }

    public UserDetailResponse updateUserProfile(UpdateUserProfileRequest req, String userId) {
        UserProfileEntity user = userRepository.findByUserId(userId).orElse(null);
        if (user == null || !validateUserProfile(req.getEmail(), req.getPhone())) {
            return null;
        }
        if (req.getFullName() != null) user.setFullName(req.getFullName());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getAvatarUrl() != null) user.setAvatarUrl(req.getAvatarUrl());

        userRepository.save(user);
        return UserDetailResponse.of(user);
    }

    public boolean validateUserProfile(String email, String phone) {
        return !userRepository.existsByEmail(email) && !userRepository.existsByPhone(phone);
    }

}
