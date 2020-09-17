package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.Rental;
import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.domain.UserDtoRequest;
import com.project.LibraryManager.domain.UserDtoResponse;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserMapperTestSuite {


    @Test
    public void testMapToUserDtoResponse(){
        //given
        UserMapper userMapper = new UserMapper();
        User user = new User(1L, "Bob", "Smith","bobs@gmail.com", LocalDateTime.now(), new ArrayList<Rental>());
        Rental rental = new Rental(2L, null, user, LocalDateTime.now(), null, true);
        user.getRentals().add(rental);
        //when
        UserDtoResponse userDtoResponse = userMapper.mapToUserDtoResponse(user);
        //then
        assertEquals(user.getId(), userDtoResponse.getId());
        assertEquals(user.getFirstName(), userDtoResponse.getFirstName());
        assertEquals(user.getLastName(), userDtoResponse.getLastName());
        assertEquals(user.getEmail(), userDtoResponse.getEmail());
        assertEquals(user.getUserCreationDate(), userDtoResponse.getUserCreationDate());
        assertEquals(user.getRentals().get(0).getId(), userDtoResponse.getRentalsIds().get(0).longValue());
    }

    @Test
    public void testMapToUser(){
        //given
        UserDtoRequest userDtoRequest = new UserDtoRequest(1L, "Bob", "Smith","bobs@gmail.com", LocalDateTime.now(), new ArrayList<Rental>());
        Rental rental = new Rental(2L, null, null, LocalDateTime.now(), null, true);
        userDtoRequest.getRentals().add(rental);
        UserMapper userMapper = new UserMapper();
        //when
        User user = userMapper.mapToUser(userDtoRequest);
        //then
        assertEquals(userDtoRequest.getId(), user.getId());
        assertEquals(userDtoRequest.getFirstName(), user.getFirstName());
        assertEquals(userDtoRequest.getLastName(), user.getLastName());
        assertEquals(userDtoRequest.getEmail(), user.getEmail());
        assertEquals(userDtoRequest.getUserCreationDate(), user.getUserCreationDate());
        assertEquals(userDtoRequest.getRentals(), user.getRentals());
    }


}
