package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.exception.UserInvalidNameException;
import com.project.LibraryManager.repository.UserReposiotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserReposiotry userReposiotry;

    public User saveUser(final User user) {
        return userReposiotry.save(user);
    }

    public Optional<User> getUser(Long userId) {
        return userReposiotry.findById(userId);
    }

    public void validateUserName(String name) throws UserInvalidNameException {
        if (name.length() < 3) {
            throw new UserInvalidNameException();
        }
    }

    public void dleteUser(long userId) {
        userReposiotry.deleteById(userId);
    }


}
