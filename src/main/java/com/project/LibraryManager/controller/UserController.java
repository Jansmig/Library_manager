package com.project.LibraryManager.controller;

import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.domain.UserDtoRequest;
import com.project.LibraryManager.domain.UserDtoResponse;
import com.project.LibraryManager.exception.UserInvalidNameException;
import com.project.LibraryManager.exception.UserNotFoundException;
import com.project.LibraryManager.mapper.UserMapper;
import com.project.LibraryManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDtoRequest userDtoRequest) throws UserInvalidNameException {
        userService.validateUserName(userDtoRequest.getFirstName());
        userService.validateUserName(userDtoRequest.getLastName());
        userService.validateUserEmail(userDtoRequest.getEmail());
        User tempUser = userMapper.mapToUser(userDtoRequest);
        tempUser.setUserCreationDate(LocalDateTime.now());
        userService.saveUser(tempUser);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public UserDtoResponse getUser(@PathVariable long userId) throws UserNotFoundException {
        return userMapper.mapToUserDtoResponse(userService.getUser(userId).orElseThrow(UserNotFoundException::new));
    }

    @RequestMapping(value ="/users", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody UserDtoRequest userDtoRequest) throws UserInvalidNameException {
        userService.validateUserName(userDtoRequest.getFirstName());
        userService.validateUserName(userDtoRequest.getLastName());
        userService.validateUserEmail(userDtoRequest.getEmail());
        userService.saveUser(userMapper.mapToUser(userDtoRequest));
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable long userId) {
        try {
            userService.dleteUser(userId);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserDtoResponse> getUsersList(){
       return userMapper.mapToUserDtoResponseList((userService.getAllUsers()));
    }

}
