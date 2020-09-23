package com.project.LibraryManager.controller;

import com.google.gson.Gson;
import com.project.LibraryManager.domain.Rental;
import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.domain.UserDtoRequest;
import com.project.LibraryManager.domain.UserDtoResponse;
import com.project.LibraryManager.mapper.UserMapper;
import com.project.LibraryManager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void testCreateUser() throws Exception {
        //given
        User user = new User(1L, "Bob", "Smith", "bobs@gmail.com", null, new ArrayList<Rental>());
        UserDtoRequest userDtoRequest = new UserDtoRequest(1L, "Bob", "Smith", "bobs@gmail.com", null, new ArrayList<Rental>());
        Gson gson = new Gson();
        String requestBody = gson.toJson(userDtoRequest);
        //when & then
        when(userService.saveUser(ArgumentMatchers.any(User.class))).thenReturn(user);
        when(userMapper.mapToUser(ArgumentMatchers.any(UserDtoRequest.class))).thenReturn(user);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestBody))
                .andExpect(status().isOk());

        Mockito.verify(userService, times(1)).saveUser(user);
        Mockito.verify(userMapper, times(1)).mapToUser(any());
    }


    @Test
    public void testGetUser() throws Exception {
        //given
        LocalDateTime creationDate = LocalDateTime.now();
        User user = new User(1L, "Bob", "Smith", "bobs@gmail.com", creationDate, new ArrayList<Rental>());
        UserDtoResponse userDtoResponse = new UserDtoResponse(1L, "Bob", "Smith", "bobs@gmail.com", creationDate, new ArrayList<Long>());

        //when & then
        when(userMapper.mapToUserDtoResponse(ArgumentMatchers.any(User.class))).thenReturn(userDtoResponse);
        when(userService.getUser(1L)).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Bob")))
                .andExpect(jsonPath("$.lastName", is("Smith")));
    }


    @Test
    public void testDeleteUser() throws Exception {
        //given:
        //when & then:
        mockMvc.perform(delete("/v1/users/1"))
                .andExpect(status().isOk());

        Mockito.verify(userService, times(1)).dleteUser(1L);
    }


    @Test
    public void testUpdateUser() throws Exception {
        //given:
        User user = new User(1L, "Andre", "Gold", "andre@mail.com", null, new ArrayList<>());
        UserDtoRequest userDtoRequest = new UserDtoRequest(2L, "Bob", "Smith", "bobs@gmail.com", null, new ArrayList<>());

        Gson gson = new Gson();
        String requestBody = gson.toJson(userDtoRequest);
        //when:
        when(userMapper.mapToUser(ArgumentMatchers.any())).thenReturn(user);
        //then:
        mockMvc.perform(put("/v1/users")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        Mockito.verify(userService, times(1)).saveUser(user);
    }


    @Test
    public void testGetUsersList() throws Exception {
        //given:
        UserDtoResponse userDtoResponse = new UserDtoResponse(2L, "Bob", "Smith", "bobs@gmail.com", null, new ArrayList<Long>());
        List<UserDtoResponse> usersList = new ArrayList<>();
        usersList.add(userDtoResponse);
        //when:
        when(userService.getAllUsers()).thenReturn(new ArrayList<User>());
        when(userMapper.mapToUserDtoResponseList(ArgumentMatchers.anyList())).thenReturn(usersList);
        //then:
        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].firstName", is("Bob")));

    }












}
