package hust.hoangson.user.serivce.impl;

import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.user.domain.entity.UserProfileEntity;
import hust.hoangson.user.repository.UserProfileRepository;
import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.request.UpdateUserProfileRequest;
import hust.hoangson.user.response.UserDetailResponse;
import hust.hoangson.user.response.UserResponse;
import hust.hoangson.user.serivce.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

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

        Page<UserProfileEntity> pageUser = userProfileRepository.getListUser(
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
        userProfileRepository.save(user);
    }

    public UserDetailResponse getUserDetail(String userId) {
        UserProfileEntity user = userProfileRepository.findByUserId(userId).get();
        return UserDetailResponse.of(user);
    }

    public UserDetailResponse updateUserProfile(UpdateUserProfileRequest req, String userId) {
        UserProfileEntity user = userProfileRepository.findByUserId(userId).orElse(null);
        if (user == null || !validateUserProfile(req.getEmail(), req.getPhone())) {
            return null;
        }
        if (req.getFullName() != null) user.setFullName(req.getFullName());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getAvatarUrl() != null) user.setAvatarUrl(req.getAvatarUrl());

        userProfileRepository.save(user);
        return UserDetailResponse.of(user);
    }

    public boolean validateUserProfile(String email, String phone) {
        return !userProfileRepository.existsByEmail(email) && !userProfileRepository.existsByPhone(phone);
    }

}
