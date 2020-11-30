package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.exception.UserInvalidEmailException;
import com.project.LibraryManager.exception.UserInvalidNameException;
import com.project.LibraryManager.repository.UserReposiotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if(name == null) {
            throw new UserInvalidNameException();
        }
        else if (name.length() < 3) {
            throw new UserInvalidNameException();
        }
    }

    public void validateUserEmail(String email) {
        String reg = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        if(email == null) {
            throw new UserInvalidEmailException();
        }
        else if(!email.matches(reg)){
            throw new UserInvalidEmailException();
        }
    }

    public void dleteUser(long userId) {
        userReposiotry.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userReposiotry.findAll();
    }

}
