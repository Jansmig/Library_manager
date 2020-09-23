package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.domain.UserDtoRequest;
import com.project.LibraryManager.domain.UserDtoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDtoResponse mapToUserDtoResponse (User user) {
        return new UserDtoResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserCreationDate(),
                user.getRentals().stream()
                        .map(n -> n.getId())
                        .collect(Collectors.toList()));
    }

    public User mapToUser(UserDtoRequest userDtoRequest) {
        return new User(
                userDtoRequest.getId(),
                userDtoRequest.getFirstName(),
                userDtoRequest.getLastName(),
                userDtoRequest.getEmail(),
                userDtoRequest.getUserCreationDate(),
                userDtoRequest.getRentals());
    }

    public List<UserDtoResponse> mapToUserDtoResponseList(List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDtoResponse)
                .collect(Collectors.toList());
    }

}
