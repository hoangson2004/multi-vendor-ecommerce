package hust.hoangson.user.serivce.impl;

import hust.hoangson.user.domain.dto.UserDTO;
import hust.hoangson.user.repository.UserRepository;
import hust.hoangson.user.request.SearchUserRequest;
import hust.hoangson.user.response.UserResponse;
import hust.hoangson.user.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponse> searchUsers(SearchUserRequest req) {
        List<UserDTO> listUser = userRepository.getListUser(req.getUserId(), req.getUsername(), req.getEmail(), req.getPhone(), req.getFullname(), req.getRole());
        return null;
    }
}
