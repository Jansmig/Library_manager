package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.exception.UserInvalidNameException;
import com.project.LibraryManager.repository.UserReposiotry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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



}
