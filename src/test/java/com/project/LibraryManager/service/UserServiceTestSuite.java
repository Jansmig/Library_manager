package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.exception.UserInvalidEmailException;
import com.project.LibraryManager.exception.UserInvalidNameException;
import com.project.LibraryManager.repository.UserReposiotry;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTestSuite {

    @Autowired
    private UserService userService;

    @Autowired
    private UserReposiotry userReposiotry;

    @Test
    public void testSaveUser() {
        //given
        User user = new User("Bob", "Smith");
        //when
        userService.saveUser(user);
        long userId = user.getId();
        //then
        assertTrue(userReposiotry.existsById(userId));
        //clean up
        userReposiotry.deleteById(userId);
    }

    @Test
    public void testGetUser() {
        //given
        User user = new User("Bob", "Smith");
        //when
        userService.saveUser(user);
        long userId = user.getId();
        User fetchedUser = userService.getUser(userId).orElse(null);
        //then
        assertEquals("Bob", fetchedUser.getFirstName());
        assertEquals("Smith", fetchedUser.getLastName());
        //clean up
        userReposiotry.deleteById(userId);
    }

    @Test (expected = UserInvalidNameException.class)
    public void testValidateUserInvalidName(){
        //when
        userService.validateUserName("aa");
    }

    @Test
    public void testValidateUserValidName(){
        //when
        userService.validateUserName("aaA");
    }

    @Test
    public void testValidateValidEmailAddress(){
        List<String> validEmails = new ArrayList<>();

        validEmails.add("email@example.com");
        validEmails.add("firstname.lastname@example.com");
        validEmails.add("email@subdomain.example.com");
        validEmails.add("firstname+lastname@example.com");
        validEmails.add("email@123.123.123.123");
        validEmails.add("email@[123.123.123.123]");
        validEmails.add("\"email\"@example.com");
        validEmails.add("1234567890@example.com");
        validEmails.add("email@example-one.com");
        validEmails.add("_______@example.com");
        validEmails.add("email@example.name");
        validEmails.add("email@example.museum");
        validEmails.add("email@example.co.jp");
        validEmails.add("firstname-lastname@example.com");

        for (String s : validEmails){
            userService.validateUserEmail(s);
        }
    }


    @Test
    public void testValidateInvalidEmailAddress(){

        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("plainaddress"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("#@%^%#$@#$@#.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("@example.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("Joe Smith <email@example.com>"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email.example.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email@example@example.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail(".email@example.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email.@example.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email..email@example.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("あいうえお@example.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email@example.com (Joe Smith)"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email@example"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email@-example.com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email@gmail,com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("email@example..com"));
        Assertions.assertThrows(UserInvalidEmailException.class, () -> userService.validateUserEmail("Abc..123@example.com"));
    }

}
